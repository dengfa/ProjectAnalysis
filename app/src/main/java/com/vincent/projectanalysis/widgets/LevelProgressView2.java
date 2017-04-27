package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.utils.UIUtils;

/**
 * Created by dengfa on 17/3/29.
 */
public class LevelProgressView2 extends View {

    protected final static int DEFAULT_WIDTH  = 200;
    protected final static int DEFAULT_HEIGHT = 200;

    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mLevelPaint;

    private int    mColorBg;
    private int    mColorProgress;
    private int    mLevel = 19;
    private float  mMax;
    private float  mProgress;
    private float  mProgressHeight;
    private float  mCircleRadius;
    private float  mProgressWidth;
    private float  mTextSize;
    private float  mTextPaddingTop;
    private float  mProgressCircleRadius;
    private String mText;

    public LevelProgressView2(Context context) {
        this(context, null);
    }

    public LevelProgressView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelProgressView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorBg = 0xff00a0ee;
        mColorProgress = 0xffffffff;
        mCircleRadius = UIUtils.dip2px(context, 5);
        mProgressHeight = UIUtils.dip2px(context, 5);
        mProgressCircleRadius = mProgressHeight * 1f / 2;
        mTextSize = UIUtils.dip2px(context, 13);
        mMax = 100;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xffffffff);
        mTextPaddingTop = UIUtils.dip2px(context,20);

        mLevelPaint = new Paint();
        mLevelPaint.setTextSize(UIUtils.dip2px(context,13));
        mLevelPaint.setColor(0xffffffff);
        mLevelPaint.setTypeface(Typeface.DEFAULT_BOLD);
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
        mProgressWidth = getWidth() - 4 * mCircleRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawProgress(canvas);
        drawText(canvas);
        drawLevel(canvas);
    }

    private void drawLevel(Canvas canvas) {
        if (mLevel > 0) {
            float top = 2 * mCircleRadius + mTextPaddingTop;
            float left = 0;
            canvas.drawText("V" + mLevel, left, top, mLevelPaint);
            String textNextLevel = "V" + (mLevel + 1);
            float textWidth = mLevelPaint.measureText(textNextLevel);
            left = getWidth() - textWidth;
            canvas.drawText(textNextLevel, left, top, mLevelPaint);
        }
    }

    private void drawText(Canvas canvas) {
        if (mText != null) {
            float textWidth = mTextPaint.measureText(mText);
            canvas.drawText(mText, getWidth() / 2 - textWidth / 2, 2 * mCircleRadius + mTextPaddingTop, mTextPaint);
        }
    }

    private void drawProgress(Canvas canvas) {
        if (mProgress > 0) {
            // 绘制progress层
            mPaint.setColor(mColorProgress);
            mPaint.setStrokeWidth(mProgressHeight);
            float stopX = 2 * mCircleRadius - mProgressCircleRadius + mProgress / mMax * (mProgressWidth + 2 * mProgressCircleRadius);
            canvas.drawLine(2 * mCircleRadius - mProgressCircleRadius, mCircleRadius, stopX, mCircleRadius, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(stopX, mCircleRadius, mProgressHeight / 2, mPaint);
        }
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        mPaint.setColor(mColorBg);
        mPaint.setStrokeWidth(mProgressHeight);
        canvas.drawLine(2 * mCircleRadius - mProgressCircleRadius, mCircleRadius,
                2 * mCircleRadius + mProgressWidth + mProgressCircleRadius,
                mCircleRadius, mPaint);
        if (mProgress > 0) {
            mPaint.setColor(mColorProgress);
        } else {
            mPaint.setColor(mColorBg);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCircleRadius, mCircleRadius, mCircleRadius, mPaint);
        if (mProgress < mMax) {
            mPaint.setColor(mColorBg);
        } else {
            mPaint.setColor(mColorProgress);
        }
        mPaint.setStrokeWidth(mProgressCircleRadius);
        canvas.drawCircle(3 * mCircleRadius + mProgressWidth, mCircleRadius, mCircleRadius, mPaint);
    }

    public void setData(int level, float max, float progress) {
        if (level > 0) {
            mLevel = level;
        }
        if (max >= 0) {
            mMax = max;
        }
        if (progress < 0) {
            mProgress = 0;
        } else if (progress > mMax) {
            mProgress = mMax;
        } else {
            mProgress = progress;
        }
        mText = (int) progress + "/" + (int) max;
        invalidate();
    }
}