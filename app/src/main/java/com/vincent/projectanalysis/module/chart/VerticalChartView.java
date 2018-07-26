package com.vincent.projectanalysis.module.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

public class VerticalChartView extends View {

    private static final int DEF_COLOR = 0x49617291;
    private static final int DEF_WIDTH = 300;
    private static final int DEF_HEIGHT = 300;

    private int mUnasignedColor = 0xffff706f;
    private int mAsignedColor = 0xffffb800;
    private int mDelayAsignedColor = 0xff17abf2;
    private int mLineColor = 0xffd7d7d7;

    private float mAUnasignedPercent = 0.2f;
    private float mAAsignedpercent = 0.4f;
    private float mADelayAsignedPercent = 0.4f;

    private float mBUnasignedPercent = 0.15f;
    private float mBAsignedpercent = 0.25f;
    private float mBDelayAsignedPercent = 0.6f;

    private String strC = "未提交";
    private String strA = "按时提交";
    private String strB = "补交";

    String strGroupA = "您";
    String strGroupB = "当地";

    private Paint mBarPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;

    private int mLeft;
    private float mPerWidth;
    private float mPerHeight;
    private float mRecHeight;
    private int mRadius;

    /**
     * @param context
     */
    public VerticalChartView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public VerticalChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VerticalChartView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(UIUtils.dip2px(1));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(UIUtils.dip2px(10));
        mTextPaint.setColor(0xff999999);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mRadius = UIUtils.dip2px(3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = UIUtils.dip2px(25);
        mPerWidth = (getWidth() - mLeft) / 5f;
        mPerHeight = getHeight() / 7f;
        mRecHeight = getHeight() / 7f * 5;
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

        //draw line
        float[] pts = {
                mLeft, getHeight() / 7f, getWidth(), getHeight() / 7f,
                mLeft, getHeight() / 7f * 2, getWidth(), getHeight() / 7f * 2,
                mLeft, getHeight() / 7f * 3, getWidth(), getHeight() / 7f * 3,
                mLeft, getHeight() / 7f * 4, getWidth(), getHeight() / 7f * 4,
                mLeft, getHeight() / 7f * 5, getWidth(), getHeight() / 7f * 5,
                mLeft, getHeight() / 7f * 6, getWidth(), getHeight() / 7f * 6};
        canvas.drawLines(pts, mLinePaint);

        //draw value
        mTextPaint.setColor(0xff999999);
        for (int i = 1; i < 7; i++) {
            canvas.save();
            canvas.translate(UIUtils.dip2px(10), getHeight() / 7f * i);
            canvas.drawText(100 - (i - 1) * 20 + "", 0, UIUtils.dip2px(4), mTextPaint);
            canvas.restore();
        }

        //draw bar
        mTextPaint.setColor(0xffffffff);
        mBarPaint.setColor(mUnasignedColor);
        canvas.save();
        canvas.translate(mLeft + mPerWidth, mPerHeight);
        drawBar(canvas, mAUnasignedPercent);
        canvas.restore();

        canvas.save();
        canvas.translate(mLeft + mPerWidth * 3, mPerHeight);
        drawBar(canvas, mBUnasignedPercent);
        canvas.restore();

        mBarPaint.setColor(mDelayAsignedColor);
        canvas.save();
        canvas.translate(mLeft + mPerWidth, mPerHeight + mRecHeight * mAUnasignedPercent);
        drawBar(canvas, mADelayAsignedPercent);
        canvas.restore();

        canvas.save();
        canvas.translate(mLeft + mPerWidth * 3, mPerHeight + mRecHeight * mBUnasignedPercent);
        drawBar(canvas, mBDelayAsignedPercent);
        canvas.restore();

        mBarPaint.setColor(mAsignedColor);
        canvas.save();
        canvas.translate(mLeft + mPerWidth, mPerHeight + mRecHeight * (mAUnasignedPercent + mADelayAsignedPercent));
        drawBar(canvas, mAAsignedpercent);
        canvas.restore();

        canvas.save();
        canvas.translate(mLeft + mPerWidth * 3, mPerHeight + mRecHeight * (mBUnasignedPercent + mBDelayAsignedPercent));
        drawBar(canvas, mBAsignedpercent);
        canvas.restore();

        //drawtext
        int padding = UIUtils.dip2px(25);
        mTextPaint.setColor(0xff333333);
        canvas.drawText(strGroupA, mLeft + mPerWidth * 1.5f, mPerHeight / 2, mTextPaint);
        canvas.drawText(strGroupB, mLeft + mPerWidth * 3.5f, mPerHeight / 2, mTextPaint);
        float textY = getHeight() - mPerHeight * 0.3f;
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(strA, 0, strA.length(), textBounds);
        canvas.drawText(strA, getWidth() - textBounds.width() / 2, textY, mTextPaint);
        mBarPaint.setColor(mAsignedColor);
        canvas.drawCircle(getWidth() - textBounds.width() - padding / 2, textY - mRadius, mRadius, mBarPaint);

        float bRight = getWidth() - textBounds.width() - padding;
        mTextPaint.getTextBounds(strB, 0, strB.length(), textBounds);
        canvas.drawText(strB, bRight - textBounds.width() / 2, textY, mTextPaint);
        mBarPaint.setColor(mDelayAsignedColor);
        canvas.drawCircle(bRight - textBounds.width() - padding / 2, textY - mRadius, mRadius, mBarPaint);

        float cRight = bRight - textBounds.width() - padding;
        mTextPaint.getTextBounds(strC, 0, strC.length(), textBounds);
        canvas.drawText(strC, cRight - textBounds.width() / 2, textY, mTextPaint);
        mBarPaint.setColor(mUnasignedColor);
        canvas.drawCircle(cRight - textBounds.width() - padding / 2, textY - mRadius, mRadius, mBarPaint);
    }

    private void drawBar(Canvas canvas, float percent) {
        canvas.drawRect(0, 0, mPerWidth, mRecHeight * percent, mBarPaint);
        String valueText = (int) (percent * 100) + "%";
        Rect textBounds = new Rect();
        //防止空间太小无法绘制文本
        if (mRecHeight * percent >= textBounds.height()) {
            mTextPaint.getTextBounds(valueText, 0, valueText.length(), textBounds);
            canvas.drawText(valueText, mPerWidth / 2,
                    mRecHeight * percent / 2 + textBounds.height() / 2f, mTextPaint);
        }
    }

    public void setData(float aAnasignedPercent, float aAsignedpercent, float aDelayAsignedPercent,
                        float bUnasignedPercent, float bAsignedpercent, float bDelayAsignedPercent) {
        mAUnasignedPercent = aAnasignedPercent;
        mAAsignedpercent = aAsignedpercent;
        mADelayAsignedPercent = aDelayAsignedPercent;
        mBUnasignedPercent = bUnasignedPercent;
        mBAsignedpercent = bAsignedpercent;
        mBDelayAsignedPercent = bDelayAsignedPercent;
        invalidate();
    }
}