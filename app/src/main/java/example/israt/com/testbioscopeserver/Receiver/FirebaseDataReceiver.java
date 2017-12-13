package example.israt.com.testbioscopeserver.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by HP on 12/11/2017.
 */
public class FirebaseDataReceiver extends BroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "I'm in!!!");

        Bundle dataBundle = intent.getBundleExtra("data");
        Log.d(TAG, dataBundle.toString());

    }
}