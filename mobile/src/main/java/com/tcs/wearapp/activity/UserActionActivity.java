package com.tcs.wearapp.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tcs.wearapp.R;
import com.tcs.wearapp.application.WearApplication;
import com.tcs.wearapp.service.MonitorUserTrendsService;
import com.tcs.wearapp.view.ZoomableTextView;

/**
 * Created by apple on 20/03/15.
 */
public class UserActionActivity extends Activity /*implements View.OnTouchListener*/{

    Button btnViewStatements, btnViewTnC;
    TextView txtReponse;
    ZoomableTextView scrollView1;

    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_action);

        initTypeface();

        btnViewStatements = (Button)findViewById(R.id.btn_view_statements);
        btnViewStatements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = WearApplication.getViewStatementCount(UserActionActivity.this);
                count = (count == -1? 1 : count+1);
                WearApplication.incrementViewStatement(UserActionActivity.this, count);

                txtReponse.setText("You have viewed statements " + count + "times");

            }
        });


        btnViewTnC = (Button)findViewById(R.id.btn_view_terms);
        btnViewTnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(UserActionActivity.this,TnCActivity.class));

            }
        });

        txtReponse = (TextView)findViewById(R.id.txt_message);


        if(!isMyServiceRunning())
            startService(new Intent(this, MonitorUserTrendsService.class));

    }


    /**
     *Checks if service is running
     * @return true if service is running
     */
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.tcs.wearapp.service.MonitorUserTrendsService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void initTypeface() {

        scrollView1 = (ZoomableTextView) findViewById(R.id.txt_zoomable_message);

        int textType = WearApplication.getTextType(this);
        Log.i("#######################TextType", "" + textType);
        switch (textType){
            case 0 :
                //do nothing

                break;
            case 1 :
                scrollView1.setTextSize(15);
                break;
            case 2 :
                scrollView1.setTextSize(25);
                break;
            default :
                break;
        }

        //scrollView1.setTextSize(mRatio + 13);
        //scrollView1.setOnTouchListener(mTouchListener);
    }

    final View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getPointerCount() == 2) {
                int action = event.getAction();
                int pureaction = action & MotionEvent.ACTION_MASK;
                if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                    mBaseDist = getDistance(event);
                    mBaseRatio = mRatio;
                } else {
                    float delta = (getDistance(event) - mBaseDist) / STEP;
                    float multi = (float) Math.pow(2, delta);
                    mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                    scrollView1.setTextSize(mRatio + 13);
                }
            }
            return true;
        }
    };

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

}
