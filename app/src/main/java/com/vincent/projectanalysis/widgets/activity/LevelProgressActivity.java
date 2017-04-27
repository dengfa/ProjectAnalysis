package com.vincent.projectanalysis.widgets.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.LevelProgressView2;

import static com.vincent.projectanalysis.R.id.levelProgressView;

public class LevelProgressActivity extends AppCompatActivity implements View.OnClickListener {

    private int progress;
    private int max =100;
    private LevelProgressView2 mLevelProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_progress);

        mLevelProgressView = (LevelProgressView2) findViewById(levelProgressView);
        findViewById(R.id.btn_dec).setOnClickListener(this);
        findViewById(R.id.btn_inc).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dec:
                if (progress > 0) {
                    progress--;
                    mLevelProgressView.setData(99, max, progress);
                }
                break;
            case R.id.btn_inc:
                if (progress < max) {
                    progress++;
                    mLevelProgressView.setData(99, max, progress);
                }
                break;
        }

    }
}
