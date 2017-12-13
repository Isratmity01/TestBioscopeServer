package example.israt.com.testbioscopeserver.fcm;

import android.app.Service;
import android.content.Intent;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import example.israt.com.testbioscopeserver.Constants;
import example.israt.com.testbioscopeserver.SharedPrefUtil;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private static final String FRIENDLY_ENGAGE_TOPIC = "friendly_engage";
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]



        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        @Override
        public void onTokenRefresh() {
            // If you need to handle the generation of a token, initially or after a refresh this is
            // where you should do that.
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "FCM Token: " + token);

            // Once a token is generated, we subscribe to topic.
            FirebaseMessaging.getInstance().subscribeToTopic(FRIENDLY_ENGAGE_TOPIC);
        }

    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        new SharedPrefUtil(getApplicationContext()).saveString(Constants.ARG_FIREBASE_TOKEN, token);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.ARG_USERS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(Constants.ARG_FIREBASE_TOKEN)
                    .setValue(token);
        }
    }
}