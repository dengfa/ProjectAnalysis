package com.vincent.interview.android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;

public class InterviewActivity extends AppCompatActivity {

    private Messenger mMsg;
    private Messenger mMsg2;
    private MyAsynctask mAsynctask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        mAsynctask = new MyAsynctask();
    }

    public void startIntentService(View view) {
        Intent intent = new Intent();
        intent.putExtra("url", "url1");
        intent.setClass(this, PreDownLoadService.class);
        startService(intent);
    }

    public void stopIntentService(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PreDownLoadService.class);
        stopService(intent);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("vincent", "PreDownLoad - onServiceConnected");
            mMsg = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("vincent", "PreDownLoad - onServiceDisconnected");
        }
    };

    public void bindIntentService(View view) {
        Intent intent = new Intent();
        intent.putExtra("url", "url2");
        intent.setClass(this, PreDownLoadService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public void unbindIntentService(View view) {
        unbindService(conn);
    }

    public void stop(View view) {
        Message message = Message.obtain();
        message.what = 0;
        try {
            mMsg.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("vincent", "PreDownLoad - onServiceConnected");
            mMsg2 = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("vincent", "PreDownLoad - onServiceDisconnected");
        }
    };

    public void startnormalService(View view) {
        Intent intent = new Intent();
        intent.putExtra("url", "url2");
        intent.setClass(this, NormalService.class);
        startService(intent);
    }

    public void stopnormalService(View view) {
        Intent intent = new Intent();
        intent.setClass(this, NormalService.class);
        stopService(intent);
    }

    public void bindnormalService(View view) {
        Intent intent = new Intent();
        intent.putExtra("url", "url2");
        intent.setClass(this, NormalService.class);
        bindService(intent, conn2, BIND_AUTO_CREATE);
    }

    public void unbindnormalService(View view) {
        Intent intent = new Intent();
        intent.setClass(this, NormalService.class);
        unbindService(conn2);
    }

    public void startAsynctask(View view) {
        mAsynctask.execute("exe");
    }

    public void cancleAsynctask(View view) {
        mAsynctask.cancel(false);
    }
}
