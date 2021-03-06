package com.vincent.projectanalysis;

import android.app.Application;
import android.content.Context;

import com.vincent.db.BoxDataBase;
import com.vincent.db.DataBaseManager;
import com.vincent.projectanalysis.utils.CrashHandler;

public class App extends Application {

    //全局应用上下文
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        CrashHandler.getInstance().init(this);
        // 初始化数据库
        DataBaseManager.getDataBaseManager().registDataBase(new BoxDataBase(this));
    }

    /*
     * 获得全局上下文
     */
    public static Context getAppContext() {
        return mAppContext;
    }

    public static void exit() {
        System.exit(0);
    }
}
