package com.vincent.projectanalysis.module.chart;

import android.content.Context;
import android.content.res.TypedArray;
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

public class ProcessComparisonView extends View {

    private static final int DEF_COLOR = 0x49617291;
    private static final int DEF_MIN_HEIGHT = UIUtils.dip2px(110);
    private static final int DEF_WIDTH = 300;
    private static final int DEF_HEIGHT = DEF_MIN_HEIGHT;

    private Paint mBarPaint;
    private Paint mTextPaint;
    private Paint mValuePaint;

    private int mLeft;
    private int mBarRadius;

    String[] mSteps = {"不足", "正常", "良好"};
    int[] mStepColors = {0xffff706f, 0xffffb800, 0xff7ed321};
    float[] mStepValues = {10, 20, 30};

    String[] mGroup = {"您5%", "北京市平均 22%"};
    float[] mGroupValus = {5, 2};

    private int mBarWidth;
    private int mBarHeight;
    PorterDuffXfermode mXfermode;
    private Bitmap mPointYouBitmap;
    private Bitmap mPointLocalBitmap;

    float[] radiiStart;
    float[] radiiCentre;
    float[] radiiEnd;
    private Path mPath;
    private RectF mRectF;
    private int mBarBottomPadding;
    private int mBarLeftPadding;
    private int mBarRightPadding;
    private Rect mTextBounds;

    /**
     * @param context
     */
    public ProcessComparisonView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public ProcessComparisonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ProcessComparisonView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mTextPaint.setTextSize(UIUtils.dip2px(12));
        mTextPaint.setColor(0xff333333);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setTextSize(UIUtils.dip2px(10));
        mValuePaint.setColor(0xff999999);
        mValuePaint.setTextAlign(Paint.Align.CENTER);

        mBarHeight = UIUtils.dip2px(5);
        mBarRadius = mBarHeight / 2;

        mPointYouBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point_you);
        mPointLocalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point_local);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        mPath = new Path();
        mRectF = new RectF();
        mTextBounds = new Rect();

        mBarBottomPadding = UIUtils.dip2px(35);
        mBarLeftPadding = UIUtils.dip2px(10);
        mBarRightPadding = UIUtils.dip2px(10);
        radiiStart = new float[]{mBarRadius, mBarRadius, 0f, 0f, 0f, 0f, mBarRadius, mBarRadius};
        radiiCentre = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
        radiiEnd = new float[]{0f, 0f, mBarRadius, mBarRadius, mBarRadius, mBarRadius, 0f, 0f};
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBarWidth = getWidth() - mBarLeftPadding - mBarRightPadding;
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
            height = heightSize >= DEF_MIN_HEIGHT ? heightSize : DEF_MIN_HEIGHT;
        } else {
            height = DEF_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBar(canvas);
        drawLocalPoint(canvas);
        drawYourPoint(canvas);
    }

    private void drawLocalPoint(Canvas canvas) {
        int left = (int) (mBarLeftPadding + mBarWidth * mGroupValus[1] / mStepValues[mStepValues.length - 1]
                - mPointLocalBitmap.getWidth() / 2);
        int top = getHeight() - mBarBottomPadding;
        int right = left + mPointLocalBitmap.getWidth();
        int bottom = top + mPointLocalBitmap.getHeight();
        int saveLayer = canvas.saveLayer(left, top, right, bottom, null);
        int localStep = 0;
        for (int i = 0; i < mStepValues.length; i++) {
            if (mGroupValus[1] <= mStepValues[i]) {
                localStep = i;
                break;
            }
        }
        canvas.drawColor(mStepColors[localStep]);
        mBarPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mPointLocalBitmap, left, top, mBarPaint);
        mBarPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        String groupStr = mGroup[1];
        mTextPaint.getTextBounds(groupStr, 0, groupStr.length(), mTextBounds);
        int textX;
        int pointX = (left + right) / 2;
        if (pointX < mTextBounds.width() / 2) {
            textX = mBarLeftPadding + mTextBounds.width() / 2;
        } else if (getWidth() - pointX < mTextBounds.width() / 2) {
            textX = getWidth() - mTextBounds.width() / 2;
        } else {
            textX = pointX;
        }
        canvas.drawText(groupStr, textX, bottom + UIUtils.dip2px(5) + mTextBounds.height(), mTextPaint);
    }

    private void drawYourPoint(Canvas canvas) {
        int left = (int) (mBarLeftPadding + mBarWidth * mGroupValus[0] / mStepValues[mStepValues.length - 1]
                - mPointYouBitmap.getWidth() / 2);
        int top = getHeight() - mBarBottomPadding - mBarHeight - mPointYouBitmap.getHeight();
        int right = left + mPointYouBitmap.getWidth();
        int bottom = top + mPointYouBitmap.getHeight();
        int saveLayer = canvas.saveLayer(left, top, right, bottom, null);
        int localStep = 0;
        for (int i = 0; i < mStepValues.length; i++) {
            if (mGroupValus[0] <= mStepValues[i]) {
                localStep = i;
                break;
            }
        }
        canvas.drawColor(mStepColors[localStep]);
        mBarPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mPointYouBitmap, left, top, mBarPaint);
        mBarPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        String groupStr = mGroup[0];
        mTextPaint.getTextBounds(groupStr, 0, groupStr.length(), mTextBounds);
        int textX;
        int pointX = (left + right) / 2;
        if (pointX < mTextBounds.width() / 2) {
            textX = mBarLeftPadding + mTextBounds.width() / 2;
        } else if (getWidth() - pointX < mTextBounds.width() / 2) {
            textX = getWidth() - mTextBounds.width() / 2;
        } else {
            textX = pointX;
        }
        canvas.drawText(groupStr, textX, top - UIUtils.dip2px(5), mTextPaint);
    }

    private void drawBar(Canvas canvas) {
        mLeft = mBarLeftPadding;
        canvas.drawText("0", mBarLeftPadding, getHeight() - mBarBottomPadding / 2, mValuePaint);
        for (int i = 0; i < mSteps.length; i++) {
            float[] radii;
            if (i == 0) {
                radii = radiiStart;
            } else if (i == mSteps.length - 1) {
                radii = radiiEnd;
            } else {
                radii = radiiCentre;
            }
            float stepWidth = mBarWidth * (mStepValues[i] - (i == 0 ? 0 : mStepValues[i - 1]))
                    / mStepValues[mStepValues.length - 1];
            mPath.reset();
            mRectF.left = mLeft;
            mRectF.top = getHeight() - mBarBottomPadding - mBarHeight;
            mRectF.right = mLeft + stepWidth;
            mRectF.bottom = getHeight() - mBarBottomPadding;
            mPath.addRoundRect(mRectF, radii, Path.Direction.CW);
            mBarPaint.setColor(mStepColors[i]);
            canvas.drawPath(mPath, mBarPaint);
            mLeft += stepWidth;
            canvas.drawText(mSteps[i] + "", mLeft - stepWidth / 2,
                    getHeight() - mBarBottomPadding - mBarHeight - UIUtils.dip2px(2), mValuePaint);
            canvas.drawText((int) mStepValues[i] + "", mLeft,
                    getHeight() - mBarBottomPadding + UIUtils.dip2px(12), mValuePaint);
        }
    }

    public void setStepConfig(String[] steps, int[] stepColors, float[] stepValues) {
        if (steps == null || stepColors == null || stepValues == null
                || stepColors.length != steps.length
                || stepColors.length != stepValues.length) {
            return;
        }
        mSteps = steps;
        mStepValues = stepValues;
        mStepColors = stepColors;
        invalidate();
    }

    public void setData(String[] group, float[] groupValus) {
        if (group == null || groupValus == null || group.length != groupValus.length) {
            return;
        }
        mGroup = group;
        mGroupValus = groupValus;
        invalidate();
    }
}