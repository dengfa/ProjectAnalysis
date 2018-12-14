package com.vincent.projectanalysis.widgets.indicator;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.knowbox.base.utils.UIUtils;


/**
 * 带文本的指示器标题
 */
public class TwoPagerTitleView extends LinearLayout implements IMeasurablePagerTitleView {
    private TextView mTvOne;
    private TextView mTvTwo;
    protected int mSelectedColor;
    protected int mNormalColor;

    public TwoPagerTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        int padding = UIUtils.dip2px(context, 25);
        setPadding(padding, 0, padding, 0);
        mTvOne = new TextView(getContext());
        mTvOne.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mTvOne.setSingleLine();
        mTvOne.setGravity(Gravity.CENTER);
        mTvOne.setEllipsize(TextUtils.TruncateAt.END);
        mTvOne.setTextSize(10);

        mTvTwo = new TextView(getContext());
        mTvTwo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mTvTwo.setSingleLine();
        mTvTwo.setGravity(Gravity.CENTER);
        mTvTwo.setEllipsize(TextUtils.TruncateAt.END);
        mTvOne.setTextSize(15);

        addView(mTvOne);
        addView(mTvTwo);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        mTvOne.setTextColor(mSelectedColor);
        mTvTwo.setTextColor(mSelectedColor);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        mTvOne.setTextColor(mNormalColor);
        mTvTwo.setTextColor(mNormalColor);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        mTvOne.setTextColor(mNormalColor);
        mTvTwo.setTextColor(mNormalColor);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        mTvOne.setTextColor(mSelectedColor);
        mTvTwo.setTextColor(mSelectedColor);
    }

    @Override
    public int getContentLeft() {
        Rect boundOne = new Rect();
        Rect boundTwo = new Rect();
        mTvOne.getPaint().getTextBounds(mTvOne.getText().toString(), 0, mTvOne.getText().length(), boundOne);
        mTvTwo.getPaint().getTextBounds(mTvTwo.getText().toString(), 0, mTvTwo.getText().length(), boundTwo);
        int contentWidth = Math.max(boundOne.width(), boundTwo.width());
        return getLeft() + getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        Paint.FontMetrics metricOne = mTvOne.getPaint().getFontMetrics();
        Paint.FontMetrics metricTwo = mTvTwo.getPaint().getFontMetrics();
        float contentHeight = Math.max(metricOne.bottom - metricOne.top, metricTwo.bottom - metricTwo.top);
        return (int) (getHeight() / 2 - contentHeight / 2);
    }

    @Override
    public int getContentRight() {
        Rect boundOne = new Rect();
        Rect boundTwo = new Rect();
        mTvOne.getPaint().getTextBounds(mTvOne.getText().toString(), 0, mTvOne.getText().length(), boundOne);
        mTvTwo.getPaint().getTextBounds(mTvTwo.getText().toString(), 0, mTvTwo.getText().length(), boundTwo);
        int contentWidth = Math.max(boundOne.width(), boundTwo.width());
        return getLeft() + getWidth() / 2 + contentWidth / 2;
    }

    @Override
    public int getContentBottom() {
        Paint.FontMetrics metricOne = mTvOne.getPaint().getFontMetrics();
        Paint.FontMetrics metricTwo = mTvTwo.getPaint().getFontMetrics();
        float contentHeight = Math.max(metricOne.bottom - metricOne.top, metricTwo.bottom - metricTwo.top);
        return (int) (getHeight() / 2 + contentHeight / 2);
    }

    public void setTitle(String titleOne, String titleTwo) {
        mTvOne.setText(titleOne);
        mTvTwo.setText(titleTwo);
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }


    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }
}
