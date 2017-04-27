package com.vincent.projectanalysis.module.guideMask.base;

import android.app.Activity;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vincent.projectanalysis.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导遮盖builder
 */
public class GuideBuilder implements View.OnClickListener, View.OnTouchListener {

    private final Activity mActivity;
    /**
     * 需要被找的View
     */
    View       mTargetView  = null;
    /**
     * 需要被找的Views
     */
    List<View> mTargetViews = null;
    /**
     * 如果targetView没有传进来，传的是坐标和宽高，就用下面四个代替targetView
     */
    int mTargetX, mTargetY, mTargetWidth, mTargetHeight;

    /**
     * 遮罩覆盖区域控件Id
     * <p/>
     * 该控件的大小既该导航页面的大小
     */
    int mFullingViewId = -1;

    /**
     * 遮罩透明度
     */
    int mAlpha         = 255;
    /**
     * 高亮区域的圆角大小
     */
    int mCorner        = 0;
    /**
     * 高亮区域的padding
     */
    int mPadding       = 0;
    // 一般情况下没用，只有需要单独设置某个方向的padding时才用，默认不用传
    int mPaddingLeft   = 0;
    int mPaddingRight  = 0;
    int mPaddingTop    = 0;
    int mPaddingBottom = 0;

    /**
     * 是否覆盖目标控件
     */
    boolean mOverlayTarget    = false;
    /**
     * 是否忽略Fit
     */
    boolean mIgnoreFit        = false;
    /**
     * 样式
     */
    int     mStyle            = GuideComponent.ROUNDRECT;
    /**
     *
     */
    boolean mOutsideTouchable = true;
    /**
     * 是否是强引导,true:只有target区域可以点击，2.9.2新增加需求，一般情况下用不到这个
     */
    boolean mIsForceGuide     = false;
    /**
     * 遮罩背景颜色id
     */
    int     mFullingColorId   = android.R.color.black;
    GuideMaskView mMaskView;

    /**
     * Builder被创建后，不允许在对它进行更改
     */
    private boolean mBuilt = true;

    private List<GuideComponent> mComponents = new ArrayList<GuideComponent>();
    private OnVisibleChangeListener mOnVisibilityChangedListener;
    // 暂时先没用，后期去掉
    private boolean mShouldCheckLocInWindow = true;
    private String mType;

    /**
     * 构造函数
     */
    public GuideBuilder(Activity activity) {
        mActivity = activity;
    }

    /**
     * 1.
     * 设置目标view
     */
    public GuideBuilder setTargetView(View v) {
        mTargetView = v;
        return this;
    }

    public GuideBuilder setTargetViews(View view) {
        if (mTargetViews == null) {
            mTargetViews = new ArrayList<View>();
        }
        mTargetViews.add(view);
        return this;
    }

    public GuideBuilder setTargetParams(int x, int y, int width, int height) {
        mTargetX = x;
        mTargetY = y;
        mTargetWidth = width;
        mTargetHeight = height;
        return this;
    }

    /**
     * 2.
     * 设置蒙板View的id
     *
     * @param id 蒙板View的id
     * @return GuideBuilder
     */
    public GuideBuilder setFullingViewId(int id) {
        mFullingViewId = id;
        return this;
    }

    /**
     * 3.
     * 设置蒙板透明度
     *
     * @param alpha [0-255] 0 表示完全透明，255表示不透明
     * @return GuideBuilder
     */
    public GuideBuilder setAlpha(int alpha) {
        mAlpha = alpha;
        return this;
    }

    /**
     * 4.
     * 设置高亮区域的圆角大小
     *
     * @return GuideBuilder
     */
    public GuideBuilder setHighTargetCorner(int corner) {
        mCorner = UIUtils.dip2px(corner);
        return this;
    }

    /**
     * 5.
     * 设置高亮区域的padding
     *
     * @return GuideBuilder
     */
    public GuideBuilder setTargetPadding(int padding) {
        if (padding < 0) {
            mPadding = 0;
        } else {
            padding = UIUtils.dip2px(padding);
            mPadding = padding;
            mPaddingLeft = padding;
            mPaddingTop = padding;
            mPaddingRight = padding;
            mPaddingBottom = padding;
        }
        return this;
    }

    /**
     * 设置高亮区域的padding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public GuideBuilder setTargetPadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingTop = top;
        mPaddingRight = right;
        mPaddingBottom = bottom;
        return this;
    }

    /**
     * 6.
     * 是否覆盖目标
     *
     * @param b true 遮罩将会覆盖整个屏幕
     * @return GuideBuilder
     */
    public GuideBuilder setOverlayTarget(boolean b) {
        mOverlayTarget = b;
        return this;
    }

    /**
     * 7.
     * 设置遮罩系统是否可点击并处理点击事件
     *
     * @param touchable true 遮罩可点击，处于不可点击状态 false 不可点击，遮罩自己可以处理自身点击事件
     */
    public GuideBuilder setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
        return this;
    }

    /**
     * 8.
     * 添加一个控件
     *
     * @param component 被添加的控件
     * @return GuideBuilder
     */
    public GuideBuilder addComponent(GuideComponent component) {
        mComponents.add(component);
        return this;
    }

    /**
     * 9.
     * 设置遮罩可见状态变化时的监听回调
     *
     * @param type 展现类型
     */
    public GuideBuilder setOnVisibilityChangedListener(OnVisibleChangeListener listener, String type) {
        mOnVisibilityChangedListener = listener;
        mType = type;
        return this;
    }

    /**
     * 设置忽略Fit
     *
     * @param b
     * @return
     */
    public GuideBuilder setIgnoreFit(boolean b) {
        mIgnoreFit = b;
        return this;
    }

    /**
     * 创建Guide，非Fragment版本
     *
     * @return Guide
     */
    public GuideMaskView createGuide() {
        ViewGroup content = (ViewGroup) mActivity.findViewById(android.R.id.content);
        // ViewGroup content = (ViewGroup) activity.getWindow().getDecorView();
        GuideMaskView maskView = new GuideMaskView(mActivity);
        maskView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        maskView.setOnClickListener(this);
        if (mIsForceGuide) {
            maskView.setOnTouchListener(this);
        }
        maskView.setFullingColor(mActivity.getResources().getColor(mFullingColorId));
        maskView.setFullingAlpha(mAlpha);
        maskView.setHighTargetCorner(mCorner);
        maskView.setPadding(mPadding);
        // 一般情况下没用
        maskView.setPaddingLeft(mPaddingLeft);
        maskView.setPaddingTop(mPaddingTop);
        maskView.setPaddingRight(mPaddingRight);
        maskView.setPaddingBottom(mPaddingBottom);
        maskView.setOverlayTarget(mOverlayTarget);
        maskView.setIgnoreFit(mIgnoreFit);
        maskView.setStyle(mStyle);
        //        maskView.setOnKeyListener(this);

        int parentX = 0;
        int parentY = 0;
        final int[] loc = new int[2];
        content.getLocationInWindow(loc);
        parentY = loc[1];//通知栏的高度
        if (mShouldCheckLocInWindow && parentY == 0) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 =
                        Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                parentY = mActivity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mTargetView != null) {
            maskView.setTargetRect(getViewAbsRect(mTargetView, parentX, parentY));
        }
        // 支持一个引导页有多个高亮区域
        else if (mTargetViews != null) {
            for (int i = 0; i < mTargetViews.size(); i++) {
                maskView.setTargetRects(getViewAbsRect(mTargetViews.get(i), parentX, parentY));
            }
        }
        // 支持高亮区域通过坐标传入方式绘制
        else {
            maskView.setTargetRect(getViewAbsRect(mTargetX, mTargetY, mTargetWidth, mTargetHeight, parentX, parentY));
        }

        for (GuideComponent c : mComponents) {
            maskView.addView(componentToView(mActivity.getLayoutInflater(), c));
        }
        return maskView;
    }

    /**
     * @param activity
     */
    public void show(Activity activity) {
        if (mMaskView == null) {
            mMaskView = createGuide();
        }
        ViewGroup content =
                (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (mMaskView.getParent() == null) {
            content.addView(mMaskView);
            if (mOnVisibilityChangedListener != null) {
                mOnVisibilityChangedListener.onShown(mType);
            }
        }
    }

    /**
     * 隐藏该遮罩并回收资源相关
     */
    public void dismiss() {
        if (mMaskView == null) {
            return;
        }
        final ViewGroup vp = (ViewGroup) mMaskView.getParent();
        if (vp == null) {
            return;
        }
        vp.removeView(mMaskView);
        if (mOnVisibilityChangedListener != null) {
            mOnVisibilityChangedListener.onDismiss(mType);
        }
        onDestroy();
    }

    /**
     * 设置Component
     */
    private View componentToView(LayoutInflater inflater, GuideComponent c) {
        View view = c.getView(inflater);
        GuideMaskView.LayoutParams lp;
        if (view.getLayoutParams() == null) {
            lp = new GuideMaskView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            lp = new GuideMaskView.LayoutParams(view.getLayoutParams());
        }

        lp.offsetX = c.getXOffset();
        lp.offsetY = c.getYOffset();
        lp.targetAnchor = c.getAnchor();
        lp.targetParentPosition = c.getFitPosition();
        lp.targetIsCover = c.getTargetIsCover();
        view.setLayoutParams(lp);
        return view;
    }

    public GuideBuilder setmIsForceGuide(boolean isForceGuide) {
        mIsForceGuide = isForceGuide;
        return this;
    }

    /**
     * Rect在屏幕上去掉状态栏高度的绝对位置
     */
    private RectF getViewAbsRect(View view, int parentX, int parentY) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        //       view.getLocationOnScreen(loc);
        RectF rect = new RectF();
        rect.set(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
        rect.offset(-parentX, -parentY);
        return rect;
    }

    private RectF getViewAbsRect(int x, int y, int width, int height, int parentX, int parentY) {
        RectF rect = new RectF();
        rect.set(x, y, x + width, y + height);
        rect.offset(-parentX, -parentY);
        return rect;
    }

    public GuideBuilder setStyle(int style) {
        mStyle = style;
        return this;
    }

    private void onDestroy() {
        mComponents = null;
        mOnVisibilityChangedListener = null;
        mMaskView.removeAllViews();
        mMaskView = null;
    }

    @Override
    public void onClick(View view) {
        if (mOutsideTouchable) {
            dismiss();
        }
    }

    // 产品更改需求，新增加onTouch事件，仅处理target区域的touch事件，由此完成强引导的功能
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // 计算点击事件的区域
        if (mMaskView != null && mMaskView.isTargetRect(motionEvent.getRawX(), motionEvent.getRawY())) {
            dismiss();
        }
        return false;
    }

    /**
     * 遮罩可见发生变化时的事件监听
     */
    public interface OnVisibleChangeListener {

        void onShown(String type);

        void onDismiss(String type);
    }
}
