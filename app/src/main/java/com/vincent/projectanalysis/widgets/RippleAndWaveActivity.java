package com.vincent.projectanalysis.widgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.R;

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
        mWaterWaveView.setmWaterLevel(0.5F);
        mWaterWaveView.startWave();
    }

    @Override
    protected void onDestroy() {
        mWaterWaveView.stopWave();
        mWaterWaveView = null;
        super.onDestroy();
    }
}
