package id.pptik.semut.emergencyreport.connections.httprequest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import id.pptik.semut.emergencyreport.helpers.PreferenceManager;
import id.pptik.semut.emergencyreport.models.Session;
import id.pptik.semut.emergencyreport.setup.Constants;

public class Request extends ConnectionHandler {

    protected static AsyncHttpClient mClient = new AsyncHttpClient();
    private String TAG = this.getClass().getSimpleName();
    private PreferenceManager preferenceManager;

    public Request(Context context, ResponHandler handler) {
        this.mContext = context;
        this.responseHandler = handler;
        preferenceManager = new PreferenceManager(mContext);
    }

    @Override
    public String getAbsoluteUrl(String relativeUrl) {
        return Constants.REST_BASE_URL + relativeUrl;
    }


    public void login(String uniqueParam, String pass, int loginType){
        RequestParams params = new RequestParams();
        if(loginType == 0) params.put("Email", uniqueParam);
        else params.put("Phonenumber", uniqueParam);

        params.put("Password", pass);
        Log.i(TAG, params.toString());
        post(Constants.REST_USER_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "Sending request");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "Success");
                responseHandler.onSuccessRequest(response.toString(), Constants.REST_USER_LOGIN);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, e, errorResponse);
                Log.e(TAG, "Failed");
                responseHandler.onSuccessRequest(String.valueOf(statusCode), Constants.REST_ERROR);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG, "Disconnected");
            }

        }, mClient);
    }


    public void register(String uniqueParam, String pass, int gender, String name, String username, String birthday, int loginType){
        RequestParams params = new RequestParams();
        if(loginType == 0) params.put("Email", uniqueParam);
        else params.put("Phonenumber", uniqueParam);

        params.put("Password", pass);
        params.put("Birthday", birthday);
        params.put("Name", name);
        params.put("Username", username);
        params.put("Gender", gender);
        Log.i(TAG, params.toString());
        post(Constants.REST_USER_REGISTER, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "Sending request");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "Success");
                responseHandler.onSuccessRequest(response.toString(), Constants.REST_USER_REGISTER);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, e, errorResponse);
                Log.e(TAG, "Failed");
                responseHandler.onSuccessRequest(String.valueOf(statusCode), Constants.REST_ERROR);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG, "Disconnected");
            }

        }, mClient);
    }



    public void getAllCctv(){
        Session session = new Gson().fromJson(preferenceManager.getString(Constants.PREF_SESSION_ID), Session.class);
        RequestParams params = new RequestParams();
        params.put("SessionID", session.getSessionID());

        Log.i(TAG, params.toString());
        post(Constants.REST_GET_ALL_CCTV, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "Sending request");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "Success");
                responseHandler.onSuccessRequest(response.toString(), Constants.REST_GET_ALL_CCTV);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, e, errorResponse);
                Log.e(TAG, "Failed");
                responseHandler.onSuccessRequest(String.valueOf(statusCode), Constants.REST_ERROR);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG, "Disconnected");
            }

        }, mClient);
    }
}
