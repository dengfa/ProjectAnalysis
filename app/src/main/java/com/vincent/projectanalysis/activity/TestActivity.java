package com.vincent.projectanalysis.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.vincent.projectanalysis.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private View mCardView;
    private boolean isShowCardView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mCardView = findViewById(R.id.card_view);
        mCardView.setOnClickListener(this);
    }

    private void hideCardView() {
        isShowCardView = false;
        // get the center for the clipping circle
        int cx = (mCardView.getLeft() + mCardView.getRight()) / 2;
        int cy = (mCardView.getTop() + mCardView.getBottom()) / 2;

        // get the initial radius for the clipping circle
        int initialRadius = mCardView.getWidth();

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(mCardView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardView.setVisibility(View.INVISIBLE);
            }
        });
        // countDown the animation
        anim.start();
    }

    private void showCardView() {
        isShowCardView = true;
        // get the center for the clipping circle
        int cx = (mCardView.getLeft() + mCardView.getRight()) / 2;
        int cy = (mCardView.getTop() + mCardView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = mCardView.getWidth();

        // create and countDown the animator for this view
        // (the countDown radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(mCardView, cx, cy, 0, finalRadius);
        anim.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view:
                if (isShowCardView) {
                    hideCardView();
                } else {
                    showCardView();
                }
                break;
        }
    }
}
