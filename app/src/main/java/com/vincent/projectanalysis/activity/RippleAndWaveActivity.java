package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.RippleView;
import com.vincent.projectanalysis.widgets.WaterWaveView;

public class RippleAndWaveActivity extends AppCompatActivity {

    private RippleView    mRippleView;
    private WaterWaveView mWaterWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        mRippleView = (RippleView) findViewById(R.id.ripple_view);
        mRippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRippleView.isRipple()) {
                    mRippleView.stopRipple();
                } else {
                    mRippleView.stratRipple();
                }
            }
        });
        mWaterWaveView = (WaterWaveView) findViewById(R.id.wave_view);
        mWaterWaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWaterWaveView.IsWaving()) {
                    mWaterWaveView.stopWave();
                } else {
                    mWaterWaveView.startWave();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        mWaterWaveView.stopWave();
        mWaterWaveView = null;
        super.onDestroy();
    }
}
