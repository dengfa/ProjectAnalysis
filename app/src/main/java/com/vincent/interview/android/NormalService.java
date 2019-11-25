package com.vincent.interview.android;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-11-25.
 * Des:
 */
public class NormalService extends Service {

    public static final String TAG   = "preload";
    private static      int    start = 100;

    public NormalService() {
        super();
    }

    private class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.d("vincent", "handleMessage - " + msg.what);
            switch (msg.what) {
                case 0:
                    onHandleIntent(null);
                    break;
            }
        }
    }

    protected void onHandleIntent(@Nullable final Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = intent.getStringExtra("url");
                start = 100;
                while (start > 0) {
                    LogUtil.d("vincent", "NormalService - " + url + start);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    start--;
                }
            }
        }).start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("vincent", "NormalService - onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtil.d("vincent", "NormalService - onStartCommand");
        onHandleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    Messenger messenger = new Messenger(new MsgHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("vincent", "NormalService - onBind");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("vincent", "NormalService - onUnbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtil.d("vincent", "NormalService - onRebind");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("vincent", "NormalService - onDestroy");
    }
}
