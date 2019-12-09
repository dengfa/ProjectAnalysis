package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

public class BlinkTagActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blink_tag);
        UIUtils.getWindowWidth(this);
    }
}
