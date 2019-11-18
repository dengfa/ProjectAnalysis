package com.vincent.interview.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.UIUtils;

/**
 * Created by dengfa on 2019-11-18.
 * Des:
 */
public class cLinearLayout extends LinearLayout {


    private Paint mPaint;
    private int   mX;
    private int   mY;
    private int   mDelX;
    private int   mDelY;

    public cLinearLayout(Context context) {
        this(context, null);
    }

    public cLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public cLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0xffff0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(UIUtils.dip2px(2));
        setWillNotDraw(false);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        LogUtil.d("vincent", "heightSize - " + heightSize);
        LogUtil.d("vincent", "widthSize - " + widthSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        LogUtil.d("vincent", "measuredHeight - " + measuredHeight);
        LogUtil.d("vincent", "measuredWidth - " + measuredWidth);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
            int paddingTop = getPaddingTop();
            int paddingLeft = getPaddingLeft();
            child.layout(l + UIUtils.dip2px(150), t + lp.topMargin + paddingTop,
                    r + UIUtils.dip2px(150), b + lp.topMargin + paddingTop);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mDelX = (int) (event.getX() - mX);
                mDelY = (int) (event.getY() - mY);
                LogUtil.d("vincent", "ACTION_MOVE - " + mDelX + " - " + mDelY);
                scrollBy(-mDelX, -mDelY);
                break;
        }
        mX = (int) event.getX();
        mY = (int) event.getY();
        return true;
    }
}
