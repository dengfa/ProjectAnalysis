package com.vincent.interview.android;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vincent.interview.android.reflect.Son;
import com.vincent.interview.android.retrofit.IpModel;
import com.vincent.interview.android.retrofit.IpService;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.AppPreferences;
import com.vincent.projectanalysis.utils.LogUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterviewActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_DOWNLOAD = 1;

    private Messenger   mMsg;
    private Messenger   mMsg2;
    private MyAsynctask mAsynctask;
    private ImageView   mIvImage;
    private String[]    urls = {"https://static.runoob.com/images/demo/demo4.jpg",
            "https://image.xiaowd.com/photo/20190505/5cce6b50d40f005886ebbb56.jpeg!cover"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
        ButterKnife.bind(this);
        mIvImage = findViewById(R.id.iv_image);

        AssetManager assetManager = new AssetManager();
        assetManager.addAssetPath("");

        String url = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpService ipService = retrofit.create(IpService.class);
        Call<IpModel> call = ipService.getIp("ip");
        call.enqueue(new Callback<IpModel>() {
            @Override
            public void onResponse(Call<IpModel> call, Response<IpModel> response) {

            }

            @Override
            public void onFailure(Call<IpModel> call, Throwable t) {

            }
        });

        String path = AppPreferences.getStringValue("path");
        File file = new File(path);
        mIvImage.setImageURI(Uri.fromFile(file));

        testReflect();
    }

    public void testReflect() {
        Field[] fields = Son.class.getFields();
        LogUtil.d("Reflect", "fields:");
        for (Field field : fields) {
            LogUtil.d("Reflect", field.toString());
        }
        Field[] declaredFields = Son.class.getDeclaredFields();
        LogUtil.d("Reflect", "declaredFields:");
        for (Field field : declaredFields) {
            LogUtil.d("Reflect", field.toString());
        }

        Method[] methods = Son.class.getMethods();
        LogUtil.d("Reflect", "methods:");
        for (Method method : methods) {
            LogUtil.d("Reflect", method.toString());
        }
        Method[] declaredMethods = Son.class.getDeclaredMethods();
        LogUtil.d("Reflect", "declaredMethods:");
        for (Method method : declaredMethods) {
            LogUtil.d("Reflect", method.toString());
        }

        try {
            Son son = Son.class.newInstance();
            son.getStr();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        SparseArray<String> sparseArray = new SparseArray<>();
        sparseArray.put(1, "1");
    }

    public void startIntentService(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_DOWNLOAD);
        } else {
            predownload();
        }
    }

    private void predownload() {
        for (String url : urls) {
            Intent intent = new Intent();
            intent.putExtra("url", url);
            intent.setClass(this, PreDownLoadService.class);
            startService(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_DOWNLOAD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                predownload();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        for (String url : urls) {
            Intent intent = new Intent();
            intent.putExtra("url", url);
            intent.setClass(this, NormalService.class);
            startService(intent);
        }
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
