package com.vincent.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LeakActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        sContext = this;
        findViewById(R.id.tv_unleak).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unleak:
                sContext = null;
                break;
        }
    }
}
