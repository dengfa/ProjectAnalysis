package com.vincent.projectanalysis.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.vincent.projectanalysis.utils.UIUtils;

/**
 * Created by dengfa on 17/9/16.
 */

public class TimerCountDownView extends View {

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private Paint mBgPaint;
    private float mDegrees = -90f;
    private float mDegreesInc;
    private int mCurSecond;
    private int mPreSecond;

    public TimerCountDownView(Context context) {
        this(context, null);
    }

    public TimerCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.GRAY);
        mBgPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xff00ffff);
        mTextPaint.setTextSize(UIUtils.dip2px(20));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = DEFAULT_WIDTH;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = DEFAULT_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, mWidth, mHeight, mBgPaint);
        if (mCurSecond > 0) {
            canvas.save();
            canvas.rotate(mDegrees + mDegreesInc, mWidth / 2, mHeight);
            canvas.drawText(mCurSecond + "", mWidth / 2, mHeight - mWidth / 2 + UIUtils.dip2px(20), mTextPaint);
            canvas.restore();
        }
        if (mPreSecond > 0) {
            canvas.save();
            canvas.rotate(mDegrees + 90 + mDegreesInc, mWidth / 2, mHeight);
            canvas.drawText(mPreSecond + "", mWidth / 2, mHeight - mWidth / 2 + UIUtils.dip2px(20), mTextPaint);
            canvas.restore();
        }
    }

    public void countDown(int curSecond) {
        mCurSecond = curSecond;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100f, 90f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegreesInc = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mPreSecond = mCurSecond + 1;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
}
