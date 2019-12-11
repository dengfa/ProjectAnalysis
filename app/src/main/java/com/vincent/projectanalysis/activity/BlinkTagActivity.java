package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.UIUtils;

import java.io.File;
import java.util.Arrays;

public class BlinkTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blink_tag);
        UIUtils.getWindowWidth(this);

        File filesDir = getFilesDir();
        LogUtil.d("vincent", "filesDir - " + filesDir.getAbsolutePath());
        File cacheDir = getCacheDir();
        LogUtil.d("vincent", "cacheDir - " + cacheDir.getAbsolutePath());
        String[] strings = fileList();
        LogUtil.d("vincent", "fileList - " + Arrays.toString(strings));
        //deleteFile("tst");
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        LogUtil.d("vincent", "externalStorageDirectory - " + externalStorageDirectory.getAbsolutePath());
    }
}
