package karnix.the.ssn.app.utils;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import karnix.the.ssn.app.model.FcmToken;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("fcmTokens/" + token);
        FcmToken fcmToken = new FcmToken(token);
        databaseReference.setValue(fcmToken);
        Log.e(TAG, "FCM Token Set");
    }
}
