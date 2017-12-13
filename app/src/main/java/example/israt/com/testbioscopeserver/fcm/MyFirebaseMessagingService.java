package example.israt.com.testbioscopeserver.fcm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import example.israt.com.testbioscopeserver.Constants;
import example.israt.com.testbioscopeserver.MainActivity;
import example.israt.com.testbioscopeserver.MainApp;
import example.israt.com.testbioscopeserver.R;
import example.israt.com.testbioscopeserver.dbhelper.DatabaseHelper;
import example.israt.com.testbioscopeserver.model.EventReceived;
import example.israt.com.testbioscopeserver.model.ServerStatus;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private DatabaseHelper databaseHelper;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        databaseHelper=new DatabaseHelper(getApplicationContext());
        long time= remoteMessage.getSentTime();
        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

           String ifUP = remoteMessage.getData().get("IsUp");
            //String message = remoteMessage.getData().get("text");
            //String username = remoteMessage.getData().get("username");
            //String uid = remoteMessage.getData().get("uid");
            //String fcmToken = remoteMessage.getData().get("fcm_token");

            // Don't show notification if chat activity is open.
            ServerStatus serverStatus=new ServerStatus();
            if(ifUP.equals("No"))
            {

                serverStatus.setDate(String.valueOf(time));
                serverStatus.setServerStatus("Down");
                serverStatus.setDownTime(String.valueOf(time));
                databaseHelper.addStatus(serverStatus);

            }
            else {
                serverStatus.setUpTime(String.valueOf(time));
                serverStatus.setServerStatus("Up");
                databaseHelper.UpdateStatus(serverStatus);


            }

            if (!MainApp.isChatActivityOpen()) {
                showNotification(remoteMessage.getData().get("text"), String.valueOf(time),ifUP);
            } else {
                EventBus.getDefault().post(new EventReceived(true,String.valueOf(time),ifUP));
            }
        }

        }





    private void showNotification(String message,String time,String state) {

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(Constants.SERVER_DOWN_TIME, time);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Bioscope Lense")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }





    /**
     * Create and show a simple notification containing the received FCM message.
     */

}