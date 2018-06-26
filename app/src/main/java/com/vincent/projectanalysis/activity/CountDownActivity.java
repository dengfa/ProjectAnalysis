package com.vincent.projectanalysis.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.TimerCountDownView;

/**
 * Created by dengfa on 17/9/16.
 */

public class CountDownActivity extends AppCompatActivity {

    public static final float SCALE = 10f;
    private TimerCountDownView mCountDownView;
    private AnimatorSet mExitAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        View mainPanel = findViewById(R.id.ll_main_panel);
        mCountDownView = (TimerCountDownView) findViewById(R.id.count_down);
        View ivLogo = findViewById(R.id.iv_logo);

        AnimatorSet entryAnimatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mainPanel, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mainPanel, "scaleY", 0f, 1f);
        ObjectAnimator logoScaleX = ObjectAnimator.ofFloat(ivLogo, "scaleX", 0f, SCALE);
        ObjectAnimator logoScaleY = ObjectAnimator.ofFloat(ivLogo, "scaleY", 0f, SCALE);
        entryAnimatorSet.playTogether(scaleX, scaleY, logoScaleX, logoScaleY);
        entryAnimatorSet.setDuration(1000);
        entryAnimatorSet.setInterpolator(new DecelerateInterpolator());
        entryAnimatorSet.start();
        entryAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mCountDownView.countDown();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mExitAnimatorSet = new AnimatorSet();
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(mainPanel, "scaleX", 1f, 0f);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(mainPanel, "scaleY", 1f, 0f);
        ObjectAnimator logoScaleX2 = ObjectAnimator.ofFloat(ivLogo, "scaleX", SCALE, 0f);
        ObjectAnimator logoScaleY2 = ObjectAnimator.ofFloat(ivLogo, "scaleY", SCALE, 0f);
        mExitAnimatorSet.playTogether(scaleX2, scaleY2, logoScaleX2, logoScaleY2);
        mExitAnimatorSet.setDuration(1000);
        mExitAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mExitAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                CountDownActivity.this.finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mCountDownView.setOnCountDownListener(new TimerCountDownView.OnCountDownListener() {
            @Override
            public void onCountDownFinished() {
                mExitAnimatorSet.start();
            }
        });
    }
}
