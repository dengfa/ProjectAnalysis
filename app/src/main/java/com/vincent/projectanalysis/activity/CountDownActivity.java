package com.vincent.projectanalysis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.TimerCountDownView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dengfa on 17/9/16.
 */

public class CountDownActivity extends Activity implements View.OnClickListener {

    private TimerCountDownView mCountDownView;
    private int mCurSecond = 5;

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
                mCurSecond = 5;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (mCurSecond >= 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCountDownView.countDown(mCurSecond);
                                    mCurSecond --;
                                }
                            });
                        }else {
                            cancel();
                        }
                    }
                }, 0, 1000);
                break;
        }
    }
}
