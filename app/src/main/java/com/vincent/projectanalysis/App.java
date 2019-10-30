package com.vincent.projectanalysis;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    //全局应用上下文
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    /*
     * 获得全局上下文
     */
    public static Context getAppContext() {
        return mAppContext;
    }
}
