package com.sunyard.sundemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by MengJie on 2017/8/23.
 */

public class MyApplication extends Application {

    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
