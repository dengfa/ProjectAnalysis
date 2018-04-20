package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.vincent.projectanalysis.R;

public class GuideActivity extends AppCompatActivity {

    int[] guides = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3, R.drawable.guide4};
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        final ImageView tvGuide = (ImageView) findViewById(R.id.iv_guide);
        tvGuide.setImageResource(R.drawable.guide1);
        mCurrentIndex = 0;
        tvGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == guides.length - 1) {
                    finish();
                    return;
                }
                mCurrentIndex++;
                tvGuide.setImageResource(guides[mCurrentIndex]);
            }
        });
    }
}
