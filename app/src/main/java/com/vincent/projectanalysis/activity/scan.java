package com.vincent.projectanalysis.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.vincent.projectanalysis.R;

public class scan extends AppCompatActivity {

    private View mScan;
    public boolean mPalyAnimation = true;
    private EditText mEt_1;
    private EditText mEt_2;
    private EditText mEt_3;
    private EditText mEt_4;
    private EditText mEt_5;
    private EditText mEt_6;
    private EditText mEt_7;
    private EditText mEt_8;
    private EditText mEt_9;
    private EditText mEt_10;
    private EditText mEt_11;
    private View mBottom;
    private AnimatorSet mSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mScan = findViewById(R.id.iv_scan);

        mEt_1 = (EditText) findViewById(R.id.et_1);
        mEt_2 = (EditText) findViewById(R.id.et_2);
        mEt_3 = (EditText) findViewById(R.id.et_3);
        mEt_4 = (EditText) findViewById(R.id.et_4);
        mEt_5 = (EditText) findViewById(R.id.et_5);
        mEt_6 = (EditText) findViewById(R.id.et_6);
        mEt_7 = (EditText) findViewById(R.id.et_7);
        mEt_8 = (EditText) findViewById(R.id.et_8);
        mEt_9 = (EditText) findViewById(R.id.et_9);
        mEt_10 = (EditText) findViewById(R.id.et_10);
        mEt_11 = (EditText) findViewById(R.id.et_11);

        final Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPalyAnimation = !mPalyAnimation;
                if (mPalyAnimation) {
                    startAnimator();
                    btn.setText("stop");
                    mSet.cancel();
                } else {
                    btn.setText("start");
                }
            }
        });
        mBottom = findViewById(R.id.bottom);
    }

    private void startAnimator() {
        mSet = new AnimatorSet();

        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mScan, "alpha", 0, 1);
        alpha1.setDuration(1000);

        float startY = -mScan.getHeight();
        float endY = getResources().getDisplayMetrics().heightPixels - mScan.getHeight() - mBottom.getHeight() - 90;
        float deY = endY - startY;
        /*ObjectAnimator translationY = ObjectAnimator.ofFloat(mScan, "translationY",
                startY,
                startY + deY * 0.01f * Integer.parseInt(mEt_1.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_2.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_3.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_4.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_5.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_6.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_7.getText().toString()),
                startY + deY * 0.01f * Integer.parseInt(mEt_8.getText().toString()),
                endY);*/

        ObjectAnimator translationY = ObjectAnimator.ofFloat(mScan, "translationY",
                startY,
                endY);

        translationY.setDuration(Integer.parseInt(mEt_9.getText().toString()));
        translationY.setInterpolator(new LinearInterpolator());

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mScan, "alpha", 1, 0);
        alpha.setStartDelay(Integer.parseInt(mEt_10.getText().toString()));
        alpha.setDuration(Integer.parseInt(mEt_11.getText().toString()));

        mSet.playTogether(alpha1, translationY, alpha);
        mSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mPalyAnimation) {
                    startAnimator();
                }
            }
        });
        mSet.start();
    }
}
