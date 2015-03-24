package com.tcs.wearapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tcs.wearapp.R;
import com.tcs.wearapp.application.WearApplication;
import com.tcs.wearapp.view.ZoomableTextView;

public class TnCActivity extends Activity {

    private ZoomableTextView scrollView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnc);

        init();
    }

    private void init(){
        scrollView1 = (ZoomableTextView) findViewById(R.id.txt_tnc);

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
    }



}
