package com.tcs.wearapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.tcs.wearapp.R;
import com.tcs.wearapp.application.WearApplication;

import java.util.Calendar;

/**
 * Created by apple on 20/03/15.
 */
public class NotificationActivity1 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        // Cancel the notification that initiated this activity.
        // This is required when using the action buttons in expanded notifications.
        // While the default action automatically closes the notification, the
        // actions initiated by buttons do not.
        int notificationId = intent.getIntExtra(WearApplication.INTENT_EXTRA_NOTIFICATION_ID, -1);
        if (notificationId != -1) {
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.cancel(notificationId);
        }

        // If there is an activity to handle the action, start the file action.
        Intent pendingIntent = (Intent) intent.getExtras().get(Intent.EXTRA_INTENT);
        int key = pendingIntent.getIntExtra("key",-1);

        if (pendingIntent != null)
        {
            switch (key) {
                case 0:
                    int date = Calendar.getInstance().get(Calendar.DATE);
                    String toastMessage = String.format(getString(R.string.txt_email_registered),getFormattedDate(date));
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    //do nothing
                    break;
                default:
                    startActivity(pendingIntent);
            }

        }


        // Finish activity.
        finish();
    }

    private String getFormattedDate(final int date){

        String strDate = "";
        switch (date){
            case 01 :
            case 21 :
            case 31 :
                strDate = date+"st";
                break;
            case 02 :
            case 22 :
                strDate = date+"nd";
                break;
            case 03 :
            case 23 :
                strDate = date+"rd";
                break;
            default :
                strDate = date+"th";


        }

        return strDate;
    }
}
