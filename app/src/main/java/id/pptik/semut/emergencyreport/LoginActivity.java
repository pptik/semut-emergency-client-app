package id.pptik.semut.emergencyreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.radiorealbutton.library.RadioRealButton;
import co.ceryle.radiorealbutton.library.RadioRealButtonGroup;
import id.pptik.semut.emergencyreport.connections.httprequest.ConnectionHandler;
import id.pptik.semut.emergencyreport.connections.httprequest.Request;
import id.pptik.semut.emergencyreport.helpers.PreferenceManager;
import id.pptik.semut.emergencyreport.models.Profile;
import id.pptik.semut.emergencyreport.setup.Constants;
import id.pptik.semut.emergencyreport.ui.CommonAlerts;


public class LoginActivity extends AppCompatActivity implements ConnectionHandler.ResponHandler {

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.email)
    EditText emailEditText;
    @BindView(R.id.password)
    EditText passEditText;
    @BindView(R.id.skip_btn)
    Button skipBtn;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radio_group_login_type)
    RadioRealButtonGroup mLoginTypeGroup;
    @BindView(R.id.radio_email)
    RadioRealButton mRadioEmail;
    @BindView(R.id.radio_phone)
    RadioRealButton mRadioPhone;
    @BindView(R.id.textInputEmail)
    TextInputLayout textInputLayout;


    private Context context;
    private Request rest;
    private String TAG = this.getClass().getSimpleName();
    PreferenceManager preferenceManager;
    private int loginType = 0;
    private ProgressDialog loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        context = this;
        preferenceManager = new PreferenceManager(context);
        loadingIndicator = new ProgressDialog(context);
        loadingIndicator.setMessage("Memuat...");
        loadingIndicator.setCancelable(false);

        if(preferenceManager.getBoolean(Constants.IS_LOGGED_IN)){
            toDashBoard();
        }

        loginBtn.setOnClickListener(view -> {
            if(emailEditText.getText().toString().equals("") || passEditText.getText().toString().equals("")){
                Snackbar.make(loginBtn, "Kolom tidak boleh kosong", Snackbar.LENGTH_LONG).show();
            }else doLogin(emailEditText.getText().toString(), passEditText.getText().toString());
        });

        signupBtn.setOnClickListener(v -> startActivity(new Intent(context, LoginActivity.class)));
        mLoginTypeGroup.setOnClickedButtonPosition(position -> {
            loginType = position;
            if(loginType == 0){
                textInputLayout.setHint("Email");
                emailEditText.setText("");
            }else {
                textInputLayout.setHint("Nomor Telepon");
                emailEditText.setText("");
            }
        });
    }


    private void doLogin(String email, String password){
        rest = new Request(context, this);
        rest.login(email, password, loginType);
        loadingIndicator.show();
    }



    private void populateUserData(String profile, String sessionID){
        Profile profileObj = new Gson().fromJson(profile, Profile.class);
        if(profileObj.getIdRole() == 5) {
            preferenceManager.save(profile, Constants.PREF_PROFILE);
            preferenceManager.save("{SessionID:" + sessionID + "}", Constants.PREF_SESSION_ID);
            preferenceManager.save(true, Constants.IS_LOGGED_IN);
            preferenceManager.apply();

            new Request(context, (pResult, type) -> {
                switch (type){
                    case Constants.REST_UPDATE_PUSH_ID:
                        loadingIndicator.dismiss();
                        Log.i(TAG, pResult);
                        try {
                            JSONObject object = new JSONObject(pResult);
                            boolean success = (object.getBoolean("success"));
                            if(success) toDashBoard();
                            else CommonAlerts.commonError(context, object.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Constants.REST_ERROR:
                        loadingIndicator.dismiss();
                        CommonAlerts.commonError(context, Constants.MESSAGE_HTTP_ERROR);

                        break;
                }
            }).updatePushID(preferenceManager.getString(Constants.PREFS_PUSH_ID));


        }else {
            CommonAlerts.commonError(context, "Maaf, akun Anda tidak diizinkan untuk menggunakan aplikasi ini");
        }
    }


    private void toDashBoard(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //------------------------- success request
    @Override
    public void onSuccessRequest(String pResult, String type) {
        switch (type){
            case Constants.REST_USER_LOGIN:
                loadingIndicator.dismiss();
                Log.i(TAG, pResult);
                try {
                    JSONObject object = new JSONObject(pResult);
                    boolean success = (object.getBoolean("success"));
                    if(success) populateUserData(object.getJSONObject("Profile").toString(), object.getString("SessionID"));
                    else Snackbar.make(loginBtn, object.getString("message"), Snackbar.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants.REST_ERROR:
                loadingIndicator.dismiss();
                CommonAlerts.commonError(context, Constants.MESSAGE_HTTP_ERROR);

                break;
        }
    }
}
