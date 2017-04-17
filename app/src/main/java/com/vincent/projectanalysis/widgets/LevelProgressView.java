package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by dengfa on 17/3/29.
 */
public class LevelProgressView extends View {

    protected final static int DEFAULT_WIDTH              = 200;
    protected final static int DEFAULT_HEIGHT             = 200;

    private Paint mPaint;
    private Paint mTextPaint;

    private int    mColorBg;
    private int    mColorProgress;
    private int    mLevel;
    private float  mMax;
    private float  mProgress;
    private float  mCircleStrokeWidth;
    private float  mProgressHeight;
    private float  mCircleRadius;
    private float  mProgressWidth;
    private float  mTextSize;
    private float  mTextPaddingTop;
    private String mText;

    private ArrayList<Bitmap> mLevelBitmaps1;
    private ArrayList<Bitmap> mLevelBitmaps2;
    private float             mLevelWidth1;
    private float             mLevelWidth2;
    private Bitmap            mBp_v;

    private int[] mLevelImgId = {R.drawable.my_gold, R.drawable.my_gold, R.drawable.my_gold,
            R.drawable.my_gold, R.drawable.my_gold, R.drawable.my_gold, R.drawable.my_gold,
            R.drawable.my_gold, R.drawable.my_gold, R.drawable.my_gold};

    public LevelProgressView(Context context) {
        this(context, null);
    }

    public LevelProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorBg = 0xff00a0ee;
        mColorProgress = 0xffffffff;
        mCircleRadius = UIUtils.dip2px(context, 13);
        mCircleStrokeWidth = UIUtils.dip2px(context, 6);
        mProgressHeight = UIUtils.dip2px(context, 6);
        mTextSize = UIUtils.dip2px(context, 13);
        mMax = 100;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xffffffff);
        mTextPaddingTop = UIUtils.dip2px(context, 14);
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
            float top = 2 * mCircleRadius;
            float left = 0;
            for (Bitmap bitmap : mLevelBitmaps1) {
                canvas.drawBitmap(bitmap, left, top, null);
                left += bitmap.getWidth();
            }
            left = getWidth() - mLevelWidth2;
            for (Bitmap bitmap : mLevelBitmaps2) {
                canvas.drawBitmap(bitmap, left, top, null);
                left += bitmap.getWidth();
            }
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
            float stopX = 2 * mCircleRadius - mCircleStrokeWidth + mProgress / mMax * (mProgressWidth + 2 * mCircleStrokeWidth);
            canvas.drawLine(2 * mCircleRadius - mCircleStrokeWidth, mCircleRadius, stopX, mCircleRadius, mPaint);
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
        canvas.drawLine(2 * mCircleRadius - mCircleStrokeWidth, mCircleRadius,
                2 * mCircleRadius + mProgressWidth + mCircleStrokeWidth,
                mCircleRadius, mPaint);
        if (mProgress > 0) {
            mPaint.setColor(mColorProgress);
        } else {
            mPaint.setColor(mColorBg);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleStrokeWidth);
        canvas.drawCircle(mCircleRadius, mCircleRadius, mCircleRadius - mCircleStrokeWidth, mPaint);
        if (mProgress < mMax) {
            mPaint.setColor(mColorBg);
        } else {
            mPaint.setColor(mColorProgress);
        }
        mPaint.setStrokeWidth(mCircleStrokeWidth);
        canvas.drawCircle(3 * mCircleRadius + mProgressWidth, mCircleRadius,
                mCircleRadius - mCircleStrokeWidth, mPaint);
    }

    public void setData(int level, float max, float progress) {
        if (level > 0) {
            mLevel = level;
            initLevelBitmap();
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

    private void initLevelBitmap() {
        mBp_v = BitmapFactory.decodeResource(getResources(), R.drawable.my_gold);
        mLevelWidth1 = mBp_v.getWidth();
        mLevelWidth2 = mBp_v.getWidth();
        Integer[] levelArr = ConvertIntToIntArray(mLevel);
        mLevelBitmaps1 = new ArrayList<>();
        mLevelBitmaps1.add(mBp_v);
        for (Integer integer : levelArr) {
            Bitmap bp = BitmapFactory.decodeResource(getResources(), mLevelImgId[integer]);
            mLevelWidth1 += bp.getWidth();
            mLevelBitmaps1.add(bp);
        }
        Integer[] levelArr2 = ConvertIntToIntArray(mLevel + 1);
        mLevelBitmaps2 = new ArrayList<>();
        mLevelBitmaps2.add(mBp_v);
        for (Integer integer : levelArr2) {
            Bitmap bp = BitmapFactory.decodeResource(getResources(), mLevelImgId[integer]);
            mLevelWidth2 += bp.getWidth();
            mLevelBitmaps2.add(bp);
        }
    }

    private static Integer[] ConvertIntToIntArray(int val) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        do {
            result.add(0, val % 10);
            val /= 10;
        }
        while (val > 0);
        return result.toArray(new Integer[]{});
    }
}