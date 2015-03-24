package com.tcs.wearapp.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tcs.wearapp.R;
import com.tcs.wearapp.adapter.StatementListAdapter;
import com.tcs.wearapp.application.WearApplication;
import com.tcs.wearapp.service.MonitorUserTrendsService;
import com.tcs.wearapp.view.ZoomableTextView;

/**
 * Created by apple on 20/03/15.
 */
public class UserActionActivity extends Activity /*implements View.OnTouchListener*/{

    Button btnViewStatements;
    TextView txtReponse;
    ZoomableTextView scrollView1;
    ListView listStatements;
    ScrollView scrollView;

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

        initList();

        btnViewStatements = (Button)findViewById(R.id.btn_view_statements);
        btnViewStatements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = WearApplication.getViewStatementCount(UserActionActivity.this);
                count = (count == -1? 1 : count+1);
                WearApplication.incrementViewStatement(UserActionActivity.this, count);

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.setVisibility(View.VISIBLE);
                        scrollView.scrollTo(0,0);
                    }
                });
                /*txtReponse.setText("You have viewed statements " + count + "times");*/

            }
        });

        txtReponse = (TextView)findViewById(R.id.txt_message);

        scrollView = (ScrollView)findViewById(R.id.scrollview);

        if(!isMyServiceRunning())
            startService(new Intent(this, MonitorUserTrendsService.class));


        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollview);
        /*scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,0);
            }
        });*/

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

    private void initList() {

        listStatements = (ListView)findViewById(R.id.list_statement);

        int size = 10;
        Object[] arrObject = new Object[size];
        for(int i=0; i<size;i++){
            arrObject[i] = i;
        }

        listStatements.setAdapter(new StatementListAdapter(this, arrObject));
        setListViewHeightBasedOnChildren(listStatements);
    }

    /**
     * Set height for listview on runtime
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
