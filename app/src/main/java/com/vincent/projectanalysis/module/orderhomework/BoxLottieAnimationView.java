package com.vincent.projectanalysis.module.orderhomework;

import android.content.Context;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

public class BoxLottieAnimationView extends LottieAnimationView {

    private boolean mAutoPlay;

    public BoxLottieAnimationView(Context context) {
        super(context);
    }

    public BoxLottieAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoxLottieAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isAnimating() && mAutoPlay) {
            playAnimation();
        }
    }
}
