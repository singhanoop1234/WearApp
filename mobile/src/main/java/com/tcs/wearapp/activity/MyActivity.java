package com.tcs.wearapp.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

import com.tcs.wearapp.R;


public class MyActivity extends Activity {



    static final String INTENT_EXTRA_NOTIFICATION_ID = "data_id";

    private Button btnCreateNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);



        btnCreateNotification = (Button)findViewById(R.id.btn_create_notification);
        btnCreateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
            }
        });
    }







    private void createNotification() {


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setAutoCancel(true);
        int id = 59;

        PendingIntent printIntent = createPendingIntent(id);

        notificationBuilder.addAction(R.drawable.ic_launcher, "Yes", printIntent);

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Test Title")
                        .setContentText("Test Message");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

         // Build the notification and issues it with notification manager.
        notificationManager.notify(id, notificationBuilder.build());



    }

    private PendingIntent createPendingIntent(int notificationId) {

        Intent viewIntent = new Intent(this, NotificationLandingActivity.class);

        Intent notificationIntent = new Intent(this, NotificationActivity1.class);


        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Pass the file action and notification id to the NotificationActivity.
        notificationIntent.putExtra(Intent.EXTRA_INTENT, viewIntent);
        notificationIntent.putExtra(INTENT_EXTRA_NOTIFICATION_ID, notificationId);

        return PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    /**
     * Creates local notification
     */
//    private void createNotification() {
//        int notificationId = new Random().nextInt();
//
//        // Build intent for notification content
//        Intent viewIntent = new Intent(this, NotificationLandingActivity.class);
//
//        PendingIntent viewPendingIntent =
//                PendingIntent.getActivity(this, 0, viewIntent, 0);
//
//        //new NotificationCompat.Action.Builder()
//        // Create the action
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
//                        "View Message", viewPendingIntent)
//                        .build();
//
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("Test Title")
//                        .setContentIntent(viewPendingIntent)
//                        .setContentText("Test Message")
//                        .addAction(action)
//        .setAutoCancel(true);
//                       /*.extend(new NotificationCompat.WearableExtender().addAction(action).setBackground(BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                               R.drawable.splash_image)))*/
//
//
//        // Get an instance of the NotificationManager service
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        Notification notification = notificationBuilder.build();
////        notification.defaults |= Notification.DEFAULT_SOUND;
////        notification.defaults |= Notification.DEFAULT_VIBRATE;
////        notification.defaults |= Notification.DEFAULT_LIGHTS;
//
//         // Build the notification and issues it with notification manager.
//        notificationManager.notify(notificationId, notification);
//    }


}
