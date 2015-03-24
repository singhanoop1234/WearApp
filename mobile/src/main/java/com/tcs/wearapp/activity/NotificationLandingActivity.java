package com.tcs.wearapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.tcs.wearapp.R;

/**
 * Created by apple on 19/03/15.
 */
public class NotificationLandingActivity extends Activity {


    TextView txt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        txt_message = (TextView)findViewById(R.id.txt_message);
    }
}
