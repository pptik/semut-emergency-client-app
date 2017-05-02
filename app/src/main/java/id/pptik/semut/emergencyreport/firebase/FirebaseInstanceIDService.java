package id.pptik.semut.emergencyreport.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import id.pptik.semut.emergencyreport.connections.httprequest.ConnectionHandler;
import id.pptik.semut.emergencyreport.connections.httprequest.Request;
import id.pptik.semut.emergencyreport.helpers.PreferenceManager;
import id.pptik.semut.emergencyreport.setup.Constants;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        context = getApplicationContext();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        PreferenceManager preferenceManager = new PreferenceManager(context);
        preferenceManager.save(token, Constants.PREFS_PUSH_ID);
        preferenceManager.apply();
        if(preferenceManager.getBoolean(Constants.IS_LOGGED_IN)){
            new Request(context, (pResult, type) -> {
                Log.i(TAG, pResult);
            }).updatePushID(token);
        }
    }
}