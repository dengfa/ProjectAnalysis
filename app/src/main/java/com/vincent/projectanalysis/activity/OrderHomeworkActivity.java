package com.vincent.projectanalysis.activity;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.module.orderhomework.BoxLottieAnimationView;

/**
 * Created by chenyan on 2018/7/23.
 */

public class OrderHomeworkActivity extends AppCompatActivity {
    private BoxLottieAnimationView lottieAnimationView;
    private LinearLayout mContainer;
    private RelativeLayout mContainerChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ocr_preview_question_layout);
        mContainerChecking = (RelativeLayout) findViewById(R.id.ll_container_checking);
        mContainer = (LinearLayout) findViewById(R.id.ll_container);
        mContainer.getLayoutTransition().addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {

            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                ImageView icon = (ImageView) view.findViewById(R.id.img_result);
                ViewCompat.animate(icon).scaleX(1);
                ViewCompat.animate(icon).scaleY(1);
            }
        });


        for (int i = 0; i < 5; i++) {
            View view = View.inflate(OrderHomeworkActivity.this, R.layout.layout_analysis_item, null);
            TextView text = (TextView) view.findViewById(R.id.txt_result);
            text.setText("正在分析本次口算批改结果" + i);
            mContainerChecking.addView(view);
        }


        lottieAnimationView = (BoxLottieAnimationView) findViewById(R.id.lottie_splash_ani);
        lottieAnimationView.setMaxProgress(0.5f);
        lottieAnimationView.playAnimation();
        lottieAnimationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setMaxProgress(1);
                lottieAnimationView.setMinProgress(0.5f);
                lottieAnimationView.playAnimation();
                lottieAnimationView.loop(true);
            }
        }, 3000);

        mContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                animatorMoveIn();
            }
        }, 1000);
    }

    private void animatorMoveIn() {
        if (mContainerChecking.getChildCount() == 0) {
            return;
        }
        final View view = mContainerChecking.getChildAt(mContainerChecking.getChildCount() - 1);
        mContainerChecking.removeView(view);
        mContainer.addView(view, 0);
        mContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mContainerChecking.getChildCount() > 0) {
                    animatorMoveIn();
                }
            }
        }, 1000);
    }


    /*private void animatorResult() {
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(mTxtAnimatorResult, "textColor", 0xff666666, 0xffc1c4c8);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(500);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mLayoutAnimatorResult, "translationY",
                UIUtils.dip2px(41), 2 * UIUtils.dip2px(41));
        objectAnimator1.setDuration(500);
        objectAnimator1.setStartDelay(300);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mImgAnimatorResult.setScaleX(value);
                mImgAnimatorResult.setScaleY(value);
                mTxtAnimatorResult.setTextSize(22 * (1.0f - value / 3));
                mLayoutAnimatorResult.setTranslationY(UIUtils.dip2px(41) * value);
            }
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mLayoutAnimatorPoint, "alpha", 0, 1);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(colorAnimator).with(valueAnimator).with(alphaAnimator).before(objectAnimator1);
        bouncer.start();
    }*/

    /*private void animatorPoint() {
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(mTxtAnimatorPoint, "textColor", 0xff666666, 0xffc1c4c8);

        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(500);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mImgAnimatorPoint.setScaleX(value);
                mImgAnimatorPoint.setScaleY(value);
                mTxtAnimatorPoint.setTextSize(22 * (1.0f - value / 3));
                mLayoutAnimatorPoint.setTranslationY(UIUtils.dip2px(41) * 41 * value);
            }
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mTxtAnimatorHomeWork, "alpha", 0, 1);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
        AnimatorSet bouncer = new AnimatorSet();

        bouncer.play(colorAnimator).with(alphaAnimator).with(valueAnimator);
        bouncer.start();
    }*/
}
