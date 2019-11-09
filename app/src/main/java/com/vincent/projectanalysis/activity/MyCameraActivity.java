package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.mycamera.MyCameraView;

public class MyCameraActivity extends AppCompatActivity {

    private MyCameraView mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);
        mCamera = findViewById(R.id.camera_new);
        mCamera.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera != null) {
            mCamera.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            //mCamera.destroy();
        }
    }
}
