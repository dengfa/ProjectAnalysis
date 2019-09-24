package com.vincent.projectanalysis.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.vincent.projectanalysis.R;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by dengfa on 17/3/29.
 */
public class VerticalProgressWithIndicatorViewV2 extends View {

    protected final static int DEFAULT_WIDTH = 200;
    protected final static int DEFAULT_HEIGHT = 200;

    private Paint mPaint;

    private int mColorBg;
    private int mColorProgress;
    private float mProgress;
    private PorterDuffXfermode mXfermode;
    private int mTopBottomPadding;
    private int mRadius;
    private Bitmap mBitmapIndicator;
    private Paint mTextPaint;
    private Rect mTextBounds;
    private ValueAnimator mValueAnimator;
    private int mPreScore = 50;
    private int mCurrentScore = 90;
    private RectF mRectF;

    public VerticalProgressWithIndicatorViewV2(Context context) {
        this(context, null);
    }

    public VerticalProgressWithIndicatorViewV2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalProgressWithIndicatorViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorBg = 0xff00a0ee;
        mColorProgress = 0xffffcd6d;
        mTopBottomPadding = com.knowbox.base.utils.UIUtils.dip2px(15);
        mRadius = com.knowbox.base.utils.UIUtils.dip2px(11);
        mProgress = 0.5f;
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_excellent);
        mRectF = new RectF();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(com.knowbox.base.utils.UIUtils.dip2px(14));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextBounds = new Rect();
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw progress bar
        if (mCurrentScore <= mPreScore) {
            mPaint.setColor(mColorProgress);
            mRectF.set(getWidth() / 2 - mRadius,
                    mTopBottomPadding,
                    getWidth() / 2 + mRadius,
                    getHeight() - mTopBottomPadding);
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
            canvas.drawBitmap(mBitmapIndicator, getWidth() / 2 + mRadius + com.knowbox.base.utils.UIUtils.dip2px(7), 0,
                    mPaint);
            String progressStr = mCurrentScore + "";
            mTextPaint.getTextBounds(progressStr, 0, progressStr.length(), mTextBounds);
            canvas.drawText(progressStr,
                    getWidth() / 2 + mRadius + com.knowbox.base.utils.UIUtils.dip2px(8) + mBitmapIndicator.getWidth() / 2,
                    mBitmapIndicator.getHeight() / 2 + mTextBounds.height() / 2,
                    mTextPaint);
        } else {
            mPaint.setColor(mColorBg);
            int saveLayer = canvas.saveLayer(getWidth() / 2 - mRadius,
                    mTopBottomPadding,
                    getWidth() / 2 + mRadius,
                    getHeight() - mTopBottomPadding, mPaint, ALL_SAVE_FLAG);
            float top = mTopBottomPadding + (1 - mProgress) * (getHeight() - 2 * mTopBottomPadding);
            mRectF.set(getWidth() / 2 - mRadius,
                    top,
                    getWidth() / 2 + mRadius,
                    getHeight() - mTopBottomPadding);
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
            mPaint.setXfermode(mXfermode);
            mPaint.setColor(mColorProgress);
            canvas.drawRect(getWidth() / 2 - mRadius,
                    mTopBottomPadding + (getHeight() - 2 * mTopBottomPadding)
                            * (mCurrentScore - mPreScore) * 1f / mCurrentScore,
                    getWidth() / 2 + mRadius,
                    getHeight() - mTopBottomPadding, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saveLayer);
            //draw indicator
            canvas.drawBitmap(mBitmapIndicator, getWidth() / 2 + mRadius + com.knowbox.base.utils.UIUtils.dip2px(7),
                    top - mBitmapIndicator.getHeight() / 2,
                    mPaint);
            String scoreStr = (int) (mCurrentScore * mProgress) + "";
            mTextPaint.getTextBounds(scoreStr, 0, scoreStr.length(), mTextBounds);
            canvas.drawText(scoreStr,
                    getWidth() / 2 + mRadius + com.knowbox.base.utils.UIUtils.dip2px(8) + mBitmapIndicator.getWidth() / 2,
                    top + mTextBounds.height() / 2,
                    mTextPaint);
        }
    }

    public void setData(int level, int preProgress, int currentProgress) {
        switch (level) {
            case 1:
                mColorBg = 0xffcc2f37;
                mColorProgress = 0xffff8667;
                mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_weak);
                break;
            case 2:
                mColorBg = 0xfff39447;
                mColorProgress = 0xffffcd6d;
                mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_mastered);
                break;
            case 3:
                mColorBg = 0xff368cd6;
                mColorProgress = 0xff75c9ff;
                mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_excellent);
                break;
        }
        mPreScore = preProgress;
        mCurrentScore = currentProgress;
        if (mPreScore < mCurrentScore) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(mPreScore * 1f / mCurrentScore, 1);
            valueAnimator.setDuration(1000);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        } else {
            invalidate();
        }
    }
}