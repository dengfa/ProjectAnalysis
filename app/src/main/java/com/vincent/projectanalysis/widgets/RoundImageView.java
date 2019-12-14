package com.vincent.projectanalysis.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.UIUtils;

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {
    private Path  mPath;
    private RectF mRectF;
    private int   mCorner = UIUtils.dip2px(3);
    private Paint mPaint;

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRectF = new RectF();
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(UIUtils.dip2px(1));
        mPaint.setColor(0xffE6EEF6);
    }

    @Override
    public void draw(Canvas canvas) {
        LogUtil.d("vincent", "RoundImageView draw");
        mRectF.set(0, 0, getWidth(), getHeight());
        mPath.addRoundRect(mRectF, mCorner, mCorner, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.draw(canvas);
        canvas.drawRoundRect(mRectF, mCorner, mCorner, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.d("vincent", "RoundImageView onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("vincent", "RoundImageView onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d("vincent", "RoundImageView onDraw");
    }
}
