/**
 * Copyright (C) 2015 The AndroidRCStudent Project
 */
package com.vincent.projectanalysis.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * 自适应缩放button
 */
public class ScaleViewGroupButton extends RelativeLayout {

    private static final float SCALE_VALUE = 0.8f;

    public ScaleViewGroupButton(Context context) {
        super(context);
    }

    public ScaleViewGroupButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleViewGroupButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                playScaleIn();
                break;

            }
            case MotionEvent.ACTION_UP: {
                playScaleOut(true);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                playScaleOut(false);
                break;
            }
        }
        return true;
    }

    private void playScaleIn() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", 1, SCALE_VALUE);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", 1, SCALE_VALUE);

        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(100);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewCompat.setScaleX(ScaleViewGroupButton.this, SCALE_VALUE);
                ViewCompat.setScaleY(ScaleViewGroupButton.this, SCALE_VALUE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                playScaleOut(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }

    private void playScaleOut(final boolean performClick) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", SCALE_VALUE, 1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", SCALE_VALUE, 1);

        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(100);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            boolean isClicked = false;

            @Override
            public void onAnimationStart(Animator animation) {
                isClicked = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewCompat.setScaleX(ScaleViewGroupButton.this, 1);
                ViewCompat.setScaleY(ScaleViewGroupButton.this, 1);
                if (performClick && !isClicked) {
                    isClicked = true;
                    performClick();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }
}
