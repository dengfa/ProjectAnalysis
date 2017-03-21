package com.vincent.projectanalysis.GuideMask.base;

import android.view.LayoutInflater;
import android.view.View;

public interface GuideComponent {

    public final static int FIT_START = GuideMaskView.LayoutParams.PARENT_START;

    public final static int FIT_END = GuideMaskView.LayoutParams.PARENT_END;

    public final static int FIT_CENTER = GuideMaskView.LayoutParams.PARENT_CENTER;

    public final static int ANCHOR_LEFT = GuideMaskView.LayoutParams.ANCHOR_LEFT;

    public final static int ANCHOR_RIGHT = GuideMaskView.LayoutParams.ANCHOR_RIGHT;

    public final static int ANCHOR_BOTTOM = GuideMaskView.LayoutParams.ANCHOR_BOTTOM;

    public final static int ANCHOR_TOP = GuideMaskView.LayoutParams.ANCHOR_TOP;

    public final static int ANCHOR_OVER = GuideMaskView.LayoutParams.ANCHOR_OVER;

    /**
     * 圆角矩形&矩形
     */
    public final static int ROUNDRECT = 0;

    /**
     * 圆形
     */
    public final static int CIRCLE  = 1;
    /**
     * 自定义
     */
    public final static int SPECIAL = 2;

    /**
     * 自定义右上
     */
    public final static int SPECIAL_TOP_RIGHT = 3;

    /**
     * 需要显示的view
     *
     * @param inflater use to inflate xml resource file
     * @return the component view
     */
    View getView(LayoutInflater inflater);

    /**
     * 相对目标View的锚点
     *
     * @return could be {@link #ANCHOR_LEFT}, {@link #ANCHOR_RIGHT},
     * {@link #ANCHOR_TOP}, {@link #ANCHOR_BOTTOM}, {@link #ANCHOR_OVER}
     */
    int getAnchor();

    /**
     * 相对目标View的对齐
     *
     * @return could be {@link #FIT_START}, {@link #FIT_END},
     * {@link #FIT_CENTER}
     */
    int getFitPosition();

    /**
     * 相对目标View的X轴位移，在计算锚点和对齐之后。
     *
     * @return X轴偏移量, 单位 dp
     */
    int getXOffset();

    /**
     * 相对目标View的Y轴位移，在计算锚点和对齐之后。
     *
     * @return Y轴偏移量，单位 dp
     */
    int getYOffset();

    boolean getTargetIsCover();
}
