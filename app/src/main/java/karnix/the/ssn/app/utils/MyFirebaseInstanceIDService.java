package karnix.the.ssn.app.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import karnix.the.ssn.app.model.FcmToken;
import karnix.the.ssn.ssnmachan.R;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);

        String[] topics = getResources().getStringArray(R.array.notification_category_keys);

        FirebaseMessaging.getInstance().subscribeToTopic(topics[0]);
        FirebaseMessaging.getInstance().subscribeToTopic(topics[1]);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[2]);
        FirebaseMessaging.getInstance().subscribeToTopic(topics[3]);
        FirebaseMessaging.getInstance().subscribeToTopic(topics[4]);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[5]);

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications_" + topics[0], true);
        editor.putBoolean("notifications_" + topics[1], true);
        editor.putBoolean("notifications_" + topics[2], false);
        editor.putBoolean("notifications_" + topics[3], true);
        editor.putBoolean("notifications_" + topics[4], true);
        editor.putBoolean("notifications_" + topics[5], false);
        editor.apply();
    }

    private void sendRegistrationToServer(String token) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("fcmTokens/" + token);
        FcmToken fcmToken = new FcmToken(token);
        databaseReference.setValue(fcmToken);
        Log.e(TAG, "FCM Token Set");
    }
}
