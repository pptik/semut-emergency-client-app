package id.pptik.semut.emergencyreport.firebase;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationService extends FirebaseMessagingService {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, remoteMessage.getCollapseKey());
        Log.d(TAG, "payload : "+remoteMessage.getData());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

    }

}