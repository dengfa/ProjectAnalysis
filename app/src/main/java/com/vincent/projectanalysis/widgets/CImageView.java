package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vincent.projectanalysis.utils.LogUtil;

public class CImageView extends ImageView {

    public CImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.d("vincent", "CImageView onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("vincent", "CImageView onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d("vincent", "CImageView onDraw");
    }
}
