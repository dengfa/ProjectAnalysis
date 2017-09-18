package com.vincent.projectanalysis.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by dengfa on 17/9/16.
 */

public class TimerCountDownView extends View {

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float mDegrees = -90f;
    private float mDegreesInc;
    private int mCurSecond = 3;
    private int mPreSecond;
    private ArrayList<Bitmap> mBitmaps;
    private ValueAnimator mValueAnimator;
    private int mInitSecond;

    public TimerCountDownView(Context context) {
        this(context, null);
    }

    public TimerCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xff00ffff);
        mTextPaint.setTextSize(UIUtils.dip2px(20));

        mBitmaps = new ArrayList<>();
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.count_down_1));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.count_down_2));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.count_down_3));

        mValueAnimator = ValueAnimator.ofFloat(0, 100f, 90f);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.setRepeatCount(3);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegreesInc = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mPreSecond = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mPreSecond = mCurSecond;
                mCurSecond--;
            }
        });
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
        if (mCurSecond > 0) {
            canvas.save();
            canvas.rotate(mDegrees + mDegreesInc, mWidth / 2, mHeight);
            Bitmap bpCur = mBitmaps.get(mCurSecond - 1);
            canvas.drawBitmap(bpCur, mWidth / 2 - bpCur.getWidth() / 2, mHeight - mWidth / 2, mPaint);
            canvas.restore();
        }
        if (mPreSecond > 0) {
            canvas.save();
            canvas.rotate(mDegrees + 90 + 20 + mDegreesInc, mWidth / 2, mHeight);
            Bitmap bpPre = mBitmaps.get(mPreSecond - 1);
            canvas.drawBitmap(bpPre, mWidth / 2 - bpPre.getWidth() / 2, mHeight - mWidth / 2, mPaint);
            canvas.restore();
        }
    }

    public void countDown(int curSecond) {
        mInitSecond = curSecond;
        mCurSecond = curSecond;
        mValueAnimator.start();
    }
}
