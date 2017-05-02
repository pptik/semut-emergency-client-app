package id.pptik.semut.emergencyreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.semut.emergencyreport.adapters.EmergencyAdapter;
import id.pptik.semut.emergencyreport.connections.httprequest.ConnectionHandler;
import id.pptik.semut.emergencyreport.connections.httprequest.Request;
import id.pptik.semut.emergencyreport.models.Emergency;
import id.pptik.semut.emergencyreport.setup.Constants;
import id.pptik.semut.emergencyreport.ui.CommonAlerts;

public class MainActivity extends AppCompatActivity implements ConnectionHandler.ResponHandler {

    private final String TAG = this.getClass().getSimpleName();
    private ProgressDialog loadingIndicator;
    private Context mContext;

    @BindView(R.id.recycler_view)
    RecyclerView mRecycleView;

    ArrayList<Emergency> arrayList = new ArrayList<Emergency>();
    EmergencyAdapter mAdapter;
    private Request httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mContext = this;
        loadingIndicator = new ProgressDialog(mContext);
        loadingIndicator.setMessage("Memuat...");
        loadingIndicator.setCancelable(false);
        httpRequest = new Request(mContext, this);
        getData();
    }


    private void getData(){
        loadingIndicator.show();
        httpRequest.getAllEmergency();
    }


    private void createList(JSONArray array){
            try {
                for (int i =0; i < array.length(); i++) {
                    arrayList.add(new Gson().fromJson(array.get(i).toString(), Emergency.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        initListView();
    }


    private void initListView(){
        mAdapter = new EmergencyAdapter(arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
    }


    @Override
    public void onSuccessRequest(String pResult, String type) {
        switch (type){
            case Constants.REST_GET_ALL_EMERGENCY:
                loadingIndicator.hide();
                Log.i(TAG, pResult);
                try {
                    JSONObject object = new JSONObject(pResult);
                    boolean success = (object.getBoolean("success"));
                    if(success) createList(object.getJSONArray("data"));
                    else CommonAlerts.commonError(mContext, object.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants.REST_ERROR:
                CommonAlerts.commonError(mContext, Constants.MESSAGE_HTTP_ERROR);
                loadingIndicator.hide();
                break;
        }
    }
}
