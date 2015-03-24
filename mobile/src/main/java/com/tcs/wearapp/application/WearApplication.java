package com.tcs.wearapp.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by apple on 20/03/15.
 */
public class WearApplication extends Application{

    public static final String INTENT_EXTRA_NOTIFICATION_ID = "data_id";
    private static final String PREF_NAME = "com.tcs.wearapp";
    private static final String STATEMENT_VIEW = "statement_viewed";
    public static final String TEXT_SIZE_SMALL = "size_small";
    public static final String TEXT_SIZE_LARGE = "size_large";

    private static int TEXT_SIZE = 13;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static int getViewStatementCount(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getInt(STATEMENT_VIEW, -1);
    }

    public static void incrementViewStatement(Context context, int views) {

       context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putInt(STATEMENT_VIEW, views).apply();
    }

    public static void setTextTypeCount(Context context, String textTypeCount) {
        int count = 0;

        count = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getInt(textTypeCount, 0);
        count++;

        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putInt(textTypeCount, count).apply();
    }

    public static int getTextType(Context context){
        int smallCount = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getInt(TEXT_SIZE_SMALL, 0);

        int largeCount = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getInt(TEXT_SIZE_LARGE, 0);

        int type = 0;
        Log.i("#######################Small count", ""+smallCount);
        Log.i("#######################Large count", ""+largeCount);
        if ((smallCount < 2 && largeCount <2) ){
            //do nothing
        } else if (smallCount > largeCount ){
            type = 1;
        } else if (smallCount <= largeCount ){
            type = 2;
        }

        return type;
    }


    public static int getTEXT_SIZE() {
        return TEXT_SIZE;
    }

    public static void setTEXT_SIZE(int size) {
        WearApplication.TEXT_SIZE = size;
    }
}
