package com.vincent.projectanalysis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.TimerCountDownView;

/**
 * Created by dengfa on 17/9/16.
 */

public class CountDownActivity extends Activity implements View.OnClickListener {

    private TimerCountDownView mCountDownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        mCountDownView = (TimerCountDownView) findViewById(R.id.count_down);
        mCountDownView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_down:
                mCountDownView.countDown(3);
                break;
        }
    }
}
