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
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

/**
 * Created by dengfa on 17/3/29.
 */
public class VerticalProgressWithIndicatorView extends View {

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
    private int mPreProgress = 50;
    private int mCurrentProgress = 90;

    public VerticalProgressWithIndicatorView(Context context) {
        this(context, null);
    }

    public VerticalProgressWithIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalProgressWithIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorBg = 0xff00a0ee;
        mColorProgress = 0xffffcd6d;
        mTopBottomPadding = UIUtils.dip2px(15);
        mRadius = UIUtils.dip2px(11);
        mProgress = 0.5f;
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_excellent);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(UIUtils.dip2px(14));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextBounds = new Rect();

        mValueAnimator = ValueAnimator.ofInt(mPreProgress, mCurrentProgress);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue() / 100f;
                invalidate();
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw progress bar
        mPaint.setColor(mColorBg);
        int saveLayer = canvas.saveLayer(getWidth() / 2 - mRadius,
                mTopBottomPadding,
                getWidth() / 2 + mRadius,
                getHeight() - mTopBottomPadding, mPaint);
        canvas.drawRoundRect(getWidth() / 2 - mRadius,
                mTopBottomPadding,
                getWidth() / 2 + mRadius,
                getHeight() - mTopBottomPadding, mRadius, mRadius, mPaint);
        mPaint.setXfermode(mXfermode);
        mPaint.setColor(mColorProgress);
        float progressTop = mTopBottomPadding + (getHeight() - 2 * mTopBottomPadding) * (1 - mProgress);
        canvas.drawRect(getWidth() / 2 - mRadius,
                progressTop,
                getWidth() / 2 + mRadius,
                getHeight() - mTopBottomPadding, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);

        //draw indicator
        canvas.drawBitmap(mBitmapIndicator, getWidth() / 2 + mRadius + UIUtils.dip2px(7),
                progressTop - mBitmapIndicator.getHeight() / 2,
                mPaint);
        String progressStr = (int) (100 * mProgress) + "";
        mTextPaint.getTextBounds(progressStr, 0, progressStr.length(), mTextBounds);
        canvas.drawText(progressStr,
                getWidth() / 2 + mRadius + UIUtils.dip2px(8) + mBitmapIndicator.getWidth() / 2,
                progressTop + mTextBounds.height() / 2,
                mTextPaint);
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
        mPreProgress = preProgress;
        mCurrentProgress = currentProgress;
        mValueAnimator.start();
    }
}