package com.vincent.interview.android;

import android.app.IntentService;
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
public class PreDownLoadService extends IntentService {

    public static final String TAG   = "preload";
    private static      int    start = 5;

    public PreDownLoadService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PreDownLoadService(String name) {
        super(name);
    }

    private static class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.d("vincent", "handleMessage - " + msg.what);
            switch (msg.what) {
                case 0:
                    start = 5;
                    break;
            }
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra("url");
        start = 5;
        while (start > 0) {
            LogUtil.d("vincent", "PreDownLoad - " + url + start);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            start--;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("vincent", "PreDownLoad - onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.d("vincent", "PreDownLoad - onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtil.d("vincent", "PreDownLoad - onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    Messenger messenger = new Messenger(new MsgHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("vincent", "PreDownLoad - onBind");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("vincent", "PreDownLoad - onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtil.d("vincent", "PreDownLoad - onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("vincent", "PreDownLoad - onDestroy");
    }
}
