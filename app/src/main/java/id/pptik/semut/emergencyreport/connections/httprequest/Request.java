package id.pptik.semut.emergencyreport.connections.httprequest;

import com.loopj.android.http.AsyncHttpClient;

public class Request extends ConnectionHandler {

    protected static AsyncHttpClient mClient = new AsyncHttpClient();
    private String TAG = this.getClass().getSimpleName();

    @Override
    public String getAbsoluteUrl(String relativeUrl) {
        return null;
    }
}
