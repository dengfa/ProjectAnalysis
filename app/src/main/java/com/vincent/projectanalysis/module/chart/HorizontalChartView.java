package com.vincent.projectanalysis.module.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

public class HorizontalChartView extends View {

    private static final int DEF_COLOR = 0x49617291;
    private static final int DEF_WIDTH = 300;
    private static final int DEF_HEIGHT = 300;

    private int mGroupAColor = 0xff17abf2;
    private int mGroupBColor = 0xffbae48c;

    private Paint mBarPaint;
    private Paint mTextPaint;

    private int mLeft;
    private int mRadius;
    private int mBarRadius;

    String[] steps = {"问题解决", "阅读理解", "推理分析", "列式运算"};
    String[] group = {"您的", "当地"};
    float[] datasA = {0.99f, 0.5f, 0.3f, 0.2f};
    float[] datasB = {0.9f, 0.6f, 0.4f, 0.2f};
    private int mRightPadding;
    private int mBarWidth;
    private int mBarHeight;
    int perHeight;

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

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);

        // TODO: 2018/7/25
        //mColor = typedArray.getColor(R.styleable.RippleView_RvColor, DEF_COLOR);
        typedArray.recycle();
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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = UIUtils.dip2px(50);
        mRightPadding = UIUtils.dip2px(25);
        mBarWidth = (getWidth() - mLeft - mRightPadding) / 2;
        perHeight = getHeight() / steps.length;
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

        //draw value
        mTextPaint.setColor(0xff999999);
        for (int i = 1; i <= steps.length; i++) {
            canvas.save();
            Rect textBounds = new Rect();
            mTextPaint.getTextBounds(steps[i - 1], 0, steps[i - 1].length(), textBounds);
            canvas.translate(0, getHeight() / steps.length * (i - 0.5f));
            canvas.drawText(steps[i - 1], textBounds.centerX(), textBounds.centerY(), mTextPaint);
            canvas.restore();
        }

        //draw bar
        drawGroupABar(canvas);
        drawGroupBBar(canvas);


        //drawtext
        int padding = UIUtils.dip2px(25);
        mTextPaint.setColor(0xff333333);
        float textY = getHeight();
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(group[1], 0, group[1].length(), textBounds);
        canvas.drawText(group[1], getWidth() - textBounds.width() / 2, textY - textBounds.height(), mTextPaint);
        mBarPaint.setColor(mGroupBColor);
        canvas.drawCircle(getWidth() - textBounds.width() - padding / 2, textY - textBounds.height(), mRadius, mBarPaint);

        float bRight = getWidth() - textBounds.width() - padding;
        mTextPaint.getTextBounds(group[0], 0, group[0].length(), textBounds);
        canvas.drawText(group[0], bRight - textBounds.width() / 2, textY - textBounds.height(), mTextPaint);
        mBarPaint.setColor(mGroupAColor);
        canvas.drawCircle(bRight - textBounds.width() - padding / 2, textY - textBounds.height(), mRadius, mBarPaint);
    }

    private void drawGroupABar(Canvas canvas) {
        mBarPaint.setColor(mGroupAColor);
        for (int i = 0; i < datasA.length; i++) {
            canvas.save();
            canvas.translate(mLeft + mBarWidth, perHeight * (i + 0.5f));
            Path path = new Path();
            float[] radii = {mBarRadius, mBarRadius, 0f, 0f, 0f, 0f, mBarRadius, mBarRadius};
            path.addRoundRect(new RectF(-mBarWidth * datasA[i], -mBarHeight, 0, 0), radii, Path.Direction.CW);
            canvas.drawPath(path, mBarPaint);
            canvas.restore();
        }
    }

    private void drawGroupBBar(Canvas canvas) {
        mBarPaint.setColor(mGroupBColor);
        for (int i = 0; i < datasB.length; i++) {
            canvas.save();
            canvas.translate(mLeft + mBarWidth, perHeight * (i + 0.5f));
            Path path = new Path();
            float[] radii = {0, 0, mBarRadius, mBarRadius, mBarRadius, mBarRadius, 0, 0};
            path.addRoundRect(new RectF(0, 0, mBarWidth * datasB[i], -mBarHeight), radii, Path.Direction.CW);
            canvas.drawPath(path, mBarPaint);
            canvas.restore();
        }
    }

    public void setData() {
        invalidate();
    }
}