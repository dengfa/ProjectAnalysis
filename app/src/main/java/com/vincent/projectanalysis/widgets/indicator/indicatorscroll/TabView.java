package com.vincent.projectanalysis.widgets.indicator.indicatorscroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * Created by guoyong on 18/7/10.
 */
@SuppressLint("AppCompatCustomView")
public class TabView extends TextView {
    public int mIndex;
    private int mMaxTabWidth;
    private TabPageIndicatorScroll.StyleType mStyleType;

    public void setStyleType(TabPageIndicatorScroll.StyleType mStyleType ,int mMaxTabWidth) {
        this.mStyleType = mStyleType;
        setMaxTabWidth(mMaxTabWidth);
    }
    private void setMaxTabWidth(int mMaxTabWidth){
        this.mMaxTabWidth = mMaxTabWidth ;
        switch (mStyleType){
            case home_school:
                setTextColor(isSelected() ? 0xffffffff : 0xffffffff);
//        getPaint().setFakeBoldText(isSelected());
                setTextSize(isSelected() ? 19 : 15);
//        setBackgroundColor(getResources().getColor(R.color.divider_color));
//        setPadding(UIUtils.dip2px(10) , isSelected()? UIUtils.dip2px(8) : UIUtils.dip2px(14), UIUtils.dip2px(10),isSelected()? UIUtils.dip2px(1): UIUtils.dip2px(8));
//        setGravity(TEXT_ALIGNMENT_CENTER);
                break;
            case classes:
                setTextColor(isSelected() ? 0xff01affe : 0xffadadad);
                setTextSize(isSelected() ? 19 : 15);
                break;
        }
    }
    public TabView(Context context , int mMaxTabWidth) {
        super(context, null, 0);
        this.mMaxTabWidth = mMaxTabWidth;
    }public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Re-measure if we went beyond our maximum size.
        if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        switch (mStyleType){
            case home_school:
                setTextColor(isSelected() ? 0xffffffff : 0xffffffff);
//        getPaint().setFakeBoldText(isSelected());
                setTextSize(isSelected() ? 19 : 15);
//        setBackgroundColor(getResources().getColor(R.color.divider_color));
//        setPadding(UIUtils.dip2px(10) , isSelected()? UIUtils.dip2px(8) : UIUtils.dip2px(14), UIUtils.dip2px(10),isSelected()? UIUtils.dip2px(1): UIUtils.dip2px(8));
//        setGravity(TEXT_ALIGNMENT_CENTER);
                break;
            case classes:
                setTextColor(isSelected() ? 0xff01affe : 0xffadadad);
                setTextSize(isSelected() ? 19 : 15);
                break;
        }

    }

    public int getIndex() {
        return mIndex;
    }
}