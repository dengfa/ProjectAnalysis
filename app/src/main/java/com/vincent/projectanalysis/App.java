package com.vincent.projectanalysis;

import android.app.Application;
import android.content.Context;

/**
 * Created by dengfa on 17/3/21.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
