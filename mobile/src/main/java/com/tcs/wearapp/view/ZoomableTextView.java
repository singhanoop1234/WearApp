package com.tcs.wearapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tcs.wearapp.R;
import com.tcs.wearapp.application.WearApplication;

/**
 * Created by apple on 23/03/15.
 */
public class ZoomableTextView extends TextView implements View.OnTouchListener{

    private final static float STEP = 200;
    private float mRatio = 1.0f;
    private int mBaseDist;
    private float mBaseRatio;
    private float fontSize = 13;
    private float maxFontSize = 25;
    private float minFontSize = 13;
    private boolean isZoomable;
    float textsize = 0;

    public ZoomableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public ZoomableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public ZoomableTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZoomableTextView);
            this.isZoomable = a.getBoolean(R.styleable.ZoomableTextView_isZoomable, false);
            if(isZoomable)
                this.setOnTouchListener(this);
            a.recycle();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(isZoomable) {
            if (event.getPointerCount() == 2) {
                int action = event.getAction();
                int pureaction = action & MotionEvent.ACTION_MASK;
                if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                    mBaseDist = getDistance(event);
                    mBaseRatio = mRatio;
                }else if (pureaction == MotionEvent.ACTION_POINTER_UP) {
                    mBaseDist = getDistance(event);
                    mBaseRatio = mRatio;
                    this.setTextSize(textsize);
                    recordChanges(textsize);
                } else {
                    float delta = (getDistance(event) - mBaseDist) / STEP;
                    float multi = (float) Math.pow(2, delta);
                    mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));

                    textsize = (mRatio + fontSize);
                    if(textsize > maxFontSize)
                        textsize = maxFontSize;
                    //this.setTextSize(textsize);
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);

    }

    /**
     * Send data to Analyser
     * Currently, it is saved locally in shared preferences.
     */
    private void recordChanges(float size) {

        String type = "";

        if(size <= 18){
            type = WearApplication.TEXT_SIZE_SMALL;

        }else if(size > 18){
            type = WearApplication.TEXT_SIZE_LARGE;
        }
        Log.i("###########################",type);

        WearApplication.setTextTypeCount(getContext(), type);
       /* int count = WearApplication.getViewStatementCount(UserActionActivity.this);
        count = (count == -1? 1 : count+1);
        WearApplication.incrementViewStatement(UserActionActivity.this, count);*/
    }

    /**
     * @param event MotionEvent
     * @return distance between 2 touch points
     */
    private int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }
}
