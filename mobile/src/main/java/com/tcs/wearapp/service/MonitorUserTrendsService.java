package com.tcs.wearapp.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.tcs.wearapp.R;
import com.tcs.wearapp.activity.NotificationActivity1;
import com.tcs.wearapp.activity.NotificationLandingActivity;
import com.tcs.wearapp.application.WearApplication;

/**
 * Created by apple on 20/03/15.
 */
public class MonitorUserTrendsService extends Service {



    int mStartMode;
    IBinder mBinder;
    boolean mAllowRebind;

    @Override
    public void onCreate() {

    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startMonitorThread();

        return mStartMode;
    }

    private void startMonitorThread() {

        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(5000);
                        int count = WearApplication.getViewStatementCount(MonitorUserTrendsService.this);
                        if(count >= 5){
                            createNotification1();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }


    //custom
    private void createNotification1() {

        Log.i("#############################","created notification");

        // Create remote view and set bigContentView.
        RemoteViews expandedView = new RemoteViews(this.getPackageName(),
                R.layout.notification);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                                                        .setAutoCancel(true);

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.title_notification))
                .setContentText(getString(R.string.notification_small_view_txt));


        int id = 59;


        /*notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.title_notification))
                .setContentText(getString(R.string.notification_small_view_txt));*/


        PendingIntent printIntent = createPendingIntent(id,0);

        PendingIntent printIntent2 = createPendingIntent(id,1);


        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(0,
                        getString(R.string.txt_register_now), printIntent)
                        .build();

        NotificationCompat.Action action2 =
                new NotificationCompat.Action.Builder(0,
                        getString(R.string.txt_later), printIntent2)
                        .build();

        notificationBuilder.extend(new NotificationCompat.WearableExtender().addAction(action).addAction(action2));

        Notification notification = notificationBuilder.build();
        notification.bigContentView = expandedView;
        notification.bigContentView.setOnClickPendingIntent(R.id.btn_notification_negative,printIntent2);
        notification.bigContentView.setOnClickPendingIntent(R.id.btn_notification_positive,printIntent);

        // Build the notification and issues it with notification manager.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, notification);



    }

    //Default
    private void createNotification() {

        Log.i("#############################","created notification");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setAutoCancel(true);
        int id = 59;

        PendingIntent printIntent = createPendingIntent(id,0);

        PendingIntent printIntent2 = createPendingIntent(id,1);
        notificationBuilder.addAction(0, getString(R.string.txt_register_now), printIntent);

        notificationBuilder.addAction(0, getString(R.string.txt_later), printIntent2);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.title_notification))
                        .setContentText(getString(R.string.notification_small_view_txt))
        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.splash_image))
                                                           .setSummaryText(getString(R.string.notification_big_view_txt)));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(id, notificationBuilder.build());



    }

    private PendingIntent createPendingIntent(int notificationId, int identifier) {

        if(identifier ==0) {
            Intent viewIntent = new Intent(this, NotificationLandingActivity.class);
            viewIntent.putExtra("key", 0);
            Intent notificationIntent = new Intent(this, NotificationActivity1.class);


            //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Pass the file action and notification id to the NotificationActivity.
            notificationIntent.putExtra(Intent.EXTRA_INTENT, viewIntent);
            notificationIntent.putExtra(WearApplication.INTENT_EXTRA_NOTIFICATION_ID, notificationId);

            return PendingIntent.getActivity(this, identifier, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            Intent viewIntent = new Intent(this, NotificationLandingActivity.class);
            viewIntent.putExtra("key", 1);

            Intent notificationIntent = new Intent(this, NotificationActivity1.class);


            //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Pass the file action and notification id to the NotificationActivity.
            notificationIntent.putExtra(Intent.EXTRA_INTENT, viewIntent);
            notificationIntent.putExtra(WearApplication.INTENT_EXTRA_NOTIFICATION_ID, notificationId);

            return PendingIntent.getActivity(this, identifier, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }


    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }

}
