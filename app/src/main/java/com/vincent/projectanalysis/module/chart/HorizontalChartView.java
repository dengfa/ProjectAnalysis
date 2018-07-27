package com.vincent.projectanalysis.module.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

public class HorizontalChartView extends View {

    private static final int DEF_WIDTH = 300;
    private static final int DEF_HEIGHT = 300;

    private int mGroupAColor = 0xff17abf2;
    private int mGroupBColor = 0xffbae48c;

    private Paint mBarPaint;
    private Paint mTextPaint;

    private int mLeft;
    private int mRadius;
    private int mBarRadius;

    String[] mSteps = {"问题解决", "阅读理解", "推理分析", "列式运算"};
    String[] mGroup = {"您的", "当地"};
    float[] mDatasA = {0.99f, 0.5f, 0.3f, 0.2f};
    float[] mDatasB = {0.9f, 0.6f, 0.4f, 0.2f};
    private int mRightPadding;
    private int mBarWidth;
    private int mBarHeight;
    int mPerHeight;
    private Bitmap mPassPercentBitmap;
    PorterDuffXfermode mXfermode;

    /**
     * @param context
     */
    public HorizontalChartView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public HorizontalChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public HorizontalChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(UIUtils.dip2px(10));
        mTextPaint.setColor(0xff999999);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mRadius = UIUtils.dip2px(3);
        mBarHeight = UIUtils.dip2px(16);
        mBarRadius = mBarHeight / 2;

        mPassPercentBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pass_percent);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = UIUtils.dip2px(50);
        mRightPadding = UIUtils.dip2px(25);
        mBarWidth = (getWidth() - mLeft - mRightPadding) / 2;
        mPerHeight = getHeight() / mSteps.length;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = DEF_WIDTH;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = DEF_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawValue(canvas);
        drawGroupABar(canvas);
        drawGroupBBar(canvas);
        drawGroupAPassPercent(canvas);
        drawGroupBPassPercent(canvas);
        drawBottomHint(canvas);
    }

    private void drawBottomHint(Canvas canvas) {
        int padding = UIUtils.dip2px(25);
        mTextPaint.setColor(0xff333333);
        float textY = getHeight();
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(mGroup[1], 0, mGroup[1].length(), textBounds);
        canvas.drawText(mGroup[1], getWidth() - textBounds.width() / 2, textY - textBounds.height(), mTextPaint);
        mBarPaint.setColor(mGroupBColor);
        canvas.drawCircle(getWidth() - textBounds.width() - padding / 2, textY - textBounds.height() * 1.5f,
                mRadius, mBarPaint);
        float bRight = getWidth() - textBounds.width() - padding;
        mTextPaint.getTextBounds(mGroup[0], 0, mGroup[0].length(), textBounds);
        canvas.drawText(mGroup[0], bRight - textBounds.width() / 2, textY - textBounds.height(), mTextPaint);
        mBarPaint.setColor(mGroupAColor);
        canvas.drawCircle(bRight - textBounds.width() - padding / 2, textY - textBounds.height() * 1.5f,
                mRadius, mBarPaint);
    }

    private void drawValue(Canvas canvas) {
        mTextPaint.setColor(0xff999999);
        for (int i = 1; i <= mSteps.length; i++) {
            canvas.save();
            Rect textBounds = new Rect();
            mTextPaint.getTextBounds(mSteps[i - 1], 0, mSteps[i - 1].length(), textBounds);
            canvas.translate(0, getHeight() / mSteps.length * (i - 0.5f));
            canvas.drawText(mSteps[i - 1], textBounds.centerX(), textBounds.height() / 2, mTextPaint);
            canvas.restore();
        }
    }

    private void drawGroupAPassPercent(Canvas canvas) {
        for (int i = 1; i < mDatasA.length; i++) {
            int top = mPerHeight * i - mPassPercentBitmap.getHeight() / 2;
            int bottom = mPerHeight * i + mPassPercentBitmap.getHeight() / 2;
            int left = mLeft + mBarWidth / 2 - mPassPercentBitmap.getWidth() / 2;
            int right = mLeft + mBarWidth / 2 + mPassPercentBitmap.getWidth() / 2;
            int saveLayer = canvas.saveLayer(left, top, right, bottom, null);
            canvas.drawColor(mDatasA[i] <= mDatasB[i] ? 0xfff7bcbc : 0xffcaeeff);
            mBarPaint.setXfermode(mXfermode);
            canvas.drawBitmap(mPassPercentBitmap, left, top, mBarPaint);
            mBarPaint.setXfermode(null);
            String percentStr = (int) (mDatasA[i] * 100) + "%";
            Rect textBounds = new Rect();
            mTextPaint.getTextBounds(percentStr, 0, percentStr.length(), textBounds);
            canvas.drawText(percentStr, (left + right) / 2, (top + bottom) / 2, mTextPaint);
            canvas.restoreToCount(saveLayer);
        }
    }

    private void drawGroupBPassPercent(Canvas canvas) {
        for (int i = 1; i < mDatasB.length; i++) {
            int top = mPerHeight * i - mPassPercentBitmap.getHeight() / 2;
            int bottom = mPerHeight * i + mPassPercentBitmap.getHeight() / 2;
            int left = mLeft + mBarWidth * 3 / 2 - mPassPercentBitmap.getWidth() / 2;
            int right = mLeft + mBarWidth * 3 / 2 + mPassPercentBitmap.getWidth() / 2;
            int saveLayer = canvas.saveLayer(left, top, right, bottom, null);
            canvas.drawColor(0xffdef6c4);
            mBarPaint.setXfermode(mXfermode);
            canvas.drawBitmap(mPassPercentBitmap, left, top, mBarPaint);
            mBarPaint.setXfermode(null);
            String percentStr = (int) (mDatasB[i] * 100) + "%";
            Rect textBounds = new Rect();
            mTextPaint.getTextBounds(percentStr, 0, percentStr.length(), textBounds);
            canvas.drawText(percentStr, (left + right) / 2, (top + bottom) / 2, mTextPaint);
            canvas.restoreToCount(saveLayer);
        }
    }

    private void drawGroupABar(Canvas canvas) {
        mBarPaint.setColor(mGroupAColor);
        for (int i = 0; i < mDatasA.length; i++) {
            canvas.save();
            canvas.translate(mLeft + mBarWidth, mPerHeight * (i + 0.5f));
            Path path = new Path();
            float[] radii = {mBarRadius, mBarRadius, 0f, 0f, 0f, 0f, mBarRadius, mBarRadius};
            path.addRoundRect(new RectF(-mBarWidth * mDatasA[i], -mBarHeight / 2, 0, mBarHeight / 2),
                    radii, Path.Direction.CW);
            canvas.drawPath(path, mBarPaint);
            canvas.restore();
        }
    }

    private void drawGroupBBar(Canvas canvas) {
        mBarPaint.setColor(mGroupBColor);
        for (int i = 0; i < mDatasB.length; i++) {
            canvas.save();
            canvas.translate(mLeft + mBarWidth, mPerHeight * (i + 0.5f));
            Path path = new Path();
            float[] radii = {0, 0, mBarRadius, mBarRadius, mBarRadius, mBarRadius, 0, 0};
            path.addRoundRect(new RectF(0, -mBarHeight / 2, mBarWidth * mDatasB[i], mBarHeight / 2),
                    radii, Path.Direction.CW);
            canvas.drawPath(path, mBarPaint);
            canvas.restore();
        }
    }

    public void setData(float[] datasA, float[] datasB) {
        mDatasA = datasA;
        mDatasB = datasB;
        invalidate();
    }
}