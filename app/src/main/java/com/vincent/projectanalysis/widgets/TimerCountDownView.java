package com.vincent.projectanalysis.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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

    public static final int DEFAULT_WIDTH = UIUtils.dip2px(130);
    public static final int DST_WIDTH = UIUtils.dip2px(105 / 3);
    public static final int DST_HEIGHT = UIUtils.dip2px(150 / 3);
    public static final int ANGLE = 140;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float mDegrees = -ANGLE;
    private float mDegreesInc;
    private int mCurSecond = 3;
    private int mPreSecond;
    private ArrayList<Bitmap> mBitmaps;
    private ValueAnimator mValueAnimator;
    private Rect mSrc;
    private Rect mDst;

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

        mBitmaps = new ArrayList<>();
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.count_down_1));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.count_down_2));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.count_down_3));

        mValueAnimator = ValueAnimator.ofFloat(0, ANGLE + 20, ANGLE);
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
                if (mOnCountDownListener != null){
                    mOnCountDownListener.onCountDownFinished();
                }
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

        mSrc = new Rect();
        mDst = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = DEFAULT_WIDTH;
        }
        setMeasuredDimension(width, width / 2);
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

            mSrc.left = 0;
            mSrc.top = 0;
            mSrc.right = bpCur.getWidth();
            mSrc.bottom = bpCur.getHeight();

            mDst.left = mWidth / 2 - DST_WIDTH / 2;
            mDst.top = 0;
            mDst.right = mWidth / 2 + DST_WIDTH / 2;
            mDst.bottom = DST_HEIGHT;

            canvas.drawBitmap(bpCur, mSrc, mDst, mPaint);
            canvas.restore();
        }
        if (mPreSecond > 0) {
            canvas.save();
            canvas.rotate(mDegrees + ANGLE + mDegreesInc, mWidth / 2, mHeight);
            Bitmap bpPre = mBitmaps.get(mPreSecond - 1);

            mSrc.left = 0;
            mSrc.top = 0;
            mSrc.right = bpPre.getWidth();
            mSrc.bottom = bpPre.getHeight();

            mDst.left = mWidth / 2 - DST_WIDTH / 2;
            mDst.top = 0;
            mDst.right = mWidth / 2 + DST_WIDTH / 2;
            mDst.bottom = DST_HEIGHT;

            canvas.drawBitmap(bpPre, mSrc, mDst, mPaint);
            canvas.restore();
        }
    }

    public void countDown() {
        mCurSecond = mBitmaps.size();
        mPreSecond = 0;
        mValueAnimator.start();
    }

    private OnCountDownListener mOnCountDownListener;

    public interface OnCountDownListener{
        void onCountDownFinished();
    }

    public void setOnCountDownListener(OnCountDownListener onCountDownListener){
        mOnCountDownListener = onCountDownListener;
    }

    public void cancle(){
        mValueAnimator.cancel();
    }
}
