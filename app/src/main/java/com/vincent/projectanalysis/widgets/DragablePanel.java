/**
 * Copyright (C) 2015 The AndroidPhoneTeacher Project
 */
package com.vincent.projectanalysis.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.vincent.projectanalysis.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.vincent.projectanalysis.utils.UIUtils;

@SuppressLint("ClickableViewAccessibility")
public class DragablePanel extends RelativeLayout {

    private              boolean mDraging = false;
    private              View    mDragView;
    private static final int     PADDING  = 10;

    public DragablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = findViewById(R.id.drag_handler);
    }

    private float mDownX, mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mMoving = false;
                mDownX = ev.getX();
                mDownY = ev.getY();
                int padding = UIUtils.dip2px(PADDING);
                Rect outRect = new Rect(mDragView.getLeft() - padding,
                        mDragView.getTop() - padding, mDragView.getLeft()
                        + mDragView.getWidth() + padding,
                        mDragView.getTop() + mDragView.getHeight() + padding);
                mDraging = outRect.contains((int) mDownX, (int) mDownY);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                mDraging = false;
                mMoving = false;
                break;
            }
            default:
                break;
        }
        return mDraging;
    }

    private float mLastX, mLastY;
    private boolean mMoving = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mMoving = false;
                mLastX = x;
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float disX = x - mLastX;
                float disY = y - mLastY;
                getParent().requestDisallowInterceptTouchEvent(true);
                if (Math.abs(disX) > com.vincent.projectanalysis.utils.UIUtils.dip2px(2)
                        && Math.abs(disY) > UIUtils.dip2px(2)) {
                    mMoving = true;
                    LayoutParams params = (LayoutParams) mDragView
                            .getLayoutParams();
                    params.rightMargin = (int) (params.rightMargin - disX);
                    params.bottomMargin = (int) (params.bottomMargin - disY);

                    if (params.rightMargin > getWidth() - mDragView.getWidth()) {
                        params.rightMargin = getWidth() - mDragView.getWidth();
                    }
                    if (params.rightMargin < 0) {
                        params.rightMargin = 0;
                    }
                    if (params.bottomMargin > getHeight() - mDragView.getHeight()) {
                        params.bottomMargin = getHeight() - mDragView.getHeight();
                    }
                    if (params.bottomMargin < 0) {
                        params.bottomMargin = 0;
                    }
                    requestLayout();
                    mLastX = x;
                    mLastY = y;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                getParent().requestDisallowInterceptTouchEvent(false);
                if (!mMoving) {
                    onClick();
                } else {
                    stickBorder();
                }
                mDraging = false;
                mMoving = false;
                break;
            }
            default:
                break;
        }
        return mDraging;
    }

    private void onClick() {
        mDragView.performClick();
    }

    private boolean toLeft = true;

    private void stickBorder() {
        int left = mDragView.getLeft();
        if (left < (getWidth() - mDragView.getWidth()) / 2) {
            toLeft = true;
        } else {
            toLeft = false;
        }
        ViewPropertyAnimator
                .animate(mDragView)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(200)
                .translationX(
                        toLeft ? -mDragView.getLeft() + UIUtils.dip2px(6)
                                : getWidth() - mDragView.getLeft()
                                - mDragView.getWidth()
                                - UIUtils.dip2px(6))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        LayoutParams params = (LayoutParams) mDragView
                                .getLayoutParams();
                        ViewHelper.setTranslationX(mDragView, 0);
                        if (toLeft) {
                            params.rightMargin = getWidth()
                                    - mDragView.getWidth() - UIUtils.dip2px(6);
                        } else {
                            params.rightMargin = UIUtils.dip2px(6);
                        }
                        requestLayout();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                }).start();
    }
}
