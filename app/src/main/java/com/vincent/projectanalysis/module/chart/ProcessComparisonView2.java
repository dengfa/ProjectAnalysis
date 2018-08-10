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

import java.util.ArrayList;

public class ProcessComparisonView2 extends View {

    private static final int DEF_MIN_HEIGHT = UIUtils.dip2px(70);
    private static final int DEF_WIDTH = 300;
    private static final int DEF_HEIGHT = DEF_MIN_HEIGHT;

    private Paint mBarPaint;
    private Paint mTextPaint;
    private Paint mValuePaint;

    private int mLeft;
    private int mBarRadius;

    int[] mStepColors = {0xffff706f, 0xffffb800, 0xff7ed321, 0xff17abf2};

    ArrayList<String> mSteps = new ArrayList<>();
    ArrayList<Integer> mStepValues = new ArrayList<>();

    ArrayList<String> mGroup = new ArrayList<>();
    ArrayList<Integer> mGroupValues = new ArrayList<>();

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
    private int mValueTextY;

    /**
     * @param context
     */
    public ProcessComparisonView2(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public ProcessComparisonView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ProcessComparisonView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        mValueTextY = getHeight() - mBarBottomPadding + UIUtils.dip2px(12);
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
        if (mGroup.size() < 2) {
            return;
        }
        Integer localValue = mGroupValues.get(1);
        int localStep = 0;
        for (int i = 0; i < mStepValues.size(); i++) {
            if (localValue <= mStepValues.get(i)) {
                localStep = i;
                break;
            }
        }

        //横轴等分，根据节点值确定位置
        int localStepValue = localStep == 0 ? localValue : localValue - mStepValues.get(localStep - 1);
        int currentStepValue = localStep == 0 ? mStepValues.get(localStep) : mStepValues.get(localStep) - mStepValues.get(localStep - 1);
        int left = mBarLeftPadding
                + mBarWidth / mSteps.size() * localStep
                + mBarWidth / mSteps.size() * localStepValue / currentStepValue
                - mPointYouBitmap.getWidth() / 2;
        //local的值作为横轴的最大值时，绘制指示图标时往右移一点
        if (localValue == mStepValues.get(mStepValues.size() - 1)) {
            left -= UIUtils.dip2px(20);
        }
        int top = getHeight() - mBarBottomPadding;
        int right = left + mPointLocalBitmap.getWidth();
        int bottom = top + mPointLocalBitmap.getHeight();
        int saveLayer = canvas.saveLayer(left, top, right, bottom, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(mStepColors[localStep % mStepColors.length]);
        mBarPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mPointLocalBitmap, left, top, mBarPaint);
        mBarPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        String groupStr = mGroup.get(1);
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
        if (mGroup.size() < 2) {
            return;
        }
        Integer yourValue = mGroupValues.get(0);
        int yourStep = 0;
        for (int i = 0; i < mStepValues.size(); i++) {
            if (yourValue <= mStepValues.get(i)) {
                yourStep = i;
                break;
            }
        }

        //横轴等分，根据节点值确定位置
        int yourStepValue = yourStep == 0 ? yourValue : yourValue - mStepValues.get(yourStep - 1);
        int currentStepValue = yourStep == 0 ? mStepValues.get(yourStep) : mStepValues.get(yourStep) - mStepValues.get(yourStep - 1);
        int left = mBarLeftPadding
                + mBarWidth / mSteps.size() * yourStep
                + mBarWidth / mSteps.size() * yourStepValue / currentStepValue
                - mPointYouBitmap.getWidth() / 2;
        //your的值作为横轴的最大值时，绘制指示图标时往右移一点
        if (yourValue == mStepValues.get(mStepValues.size() - 1)) {
            left -= UIUtils.dip2px(20);
        }
        int top = getHeight() - mBarBottomPadding - mBarHeight - mPointYouBitmap.getHeight();
        int right = left + mPointYouBitmap.getWidth();
        int bottom = top + mPointYouBitmap.getHeight();
        int saveLayer = canvas.saveLayer(left, top, right, bottom, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(mStepColors[yourStep % mStepColors.length]);
        mBarPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mPointYouBitmap, left, top, mBarPaint);
        mBarPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        String groupStr = mGroup.get(0);
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
        if (mSteps.isEmpty()) {
            return;
        }
        mLeft = mBarLeftPadding;
        for (int i = 0; i < mSteps.size(); i++) {
            float[] radii;
            if (i == 0) {
                radii = radiiStart;
            } else if (i == mSteps.size() - 1) {
                radii = radiiEnd;
            } else {
                radii = radiiCentre;
            }
            float stepWidth = mBarWidth / mSteps.size();
            mPath.reset();
            mRectF.left = mLeft;
            mRectF.top = getHeight() - mBarBottomPadding - mBarHeight;
            mRectF.right = mLeft + stepWidth;
            mRectF.bottom = getHeight() - mBarBottomPadding;
            mPath.addRoundRect(mRectF, radii, Path.Direction.CW);
            mBarPaint.setColor(mStepColors[i % mStepColors.length]);
            canvas.drawPath(mPath, mBarPaint);
            mLeft += stepWidth;
            canvas.drawText(mSteps.get(i) + "", mLeft - stepWidth / 2,
                    getHeight() - mBarBottomPadding - mBarHeight - UIUtils.dip2px(2), mValuePaint);
            if (i != mSteps.size() - 1) {
                canvas.drawText(mStepValues.get(i) + "", mLeft, mValueTextY, mValuePaint);
            }
        }
    }

    public void setData(ArrayList<String> steps, ArrayList<Integer> stepValues, ArrayList<String> group,
                        ArrayList<Integer> groupValus) {
        if (steps == null || stepValues == null
                || group == null || groupValus == null
                || group.size() != 2 || groupValus.size() != 2) {
            return;
        }
        mSteps.clear();
        mSteps.addAll(steps);
        mStepValues.clear();
        mStepValues.addAll(stepValues);
        //当地数据和我的数据的较大值，作为x轴的最大值
        mStepValues.add(Math.max(groupValus.get(0), groupValus.get(1)));
        mGroup.clear();
        mGroup.addAll(group);
        mGroupValues.clear();
        mGroupValues.addAll(groupValus);
        invalidate();
    }
}