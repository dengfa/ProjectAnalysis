package com.vincent.projectanalysis.widgets.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


/**
 * 两种颜色过渡的指示器标题
 */
public class ColorTransitionPagerTitleView extends SimplePagerTitleView {

    private OnSelectedListener mOnSelectedListener;

    public ColorTransitionPagerTitleView(Context context) {
        super(context);
    }

    public ColorTransitionPagerTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
        setTextColor(color);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        if (mOnSelectedListener != null) {
            mOnSelectedListener.onSelected(true);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        if (mOnSelectedListener != null) {
            mOnSelectedListener.onSelected(false);
        }
    }

    public interface OnSelectedListener {
        void onSelected(boolean isSelected);
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        mOnSelectedListener = onSelectedListener;
    }
}
