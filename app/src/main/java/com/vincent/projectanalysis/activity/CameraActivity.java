package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.camera.CameraListener;
import com.vincent.projectanalysis.camera.CameraOptions;
import com.vincent.projectanalysis.camera.CameraView;
import com.vincent.projectanalysis.camera.options.Gesture;
import com.vincent.projectanalysis.camera.options.GestureAction;

public class CameraActivity extends AppCompatActivity {

    private CameraView mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //权限管理


        mCamera = findViewById(R.id.camera_new);
        mCamera.mapGesture(Gesture.SCROLL_HORIZONTAL, GestureAction.NONE);
        mCamera.addCameraListener(new CameraListener() {
            public void onPictureTaken(byte[] jpeg) {

            }

            @Override
            public void onCameraOpened(CameraOptions options) {
                super.onCameraOpened(options);
            }
        });
        mCamera.setPlaySounds(false);
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
            mCamera.destroy();
        }
    }
}
