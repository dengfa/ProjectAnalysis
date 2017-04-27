package com.vincent.projectanalysis.module.guideMask.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 绘制引导遮盖的view
 */
class GuideMaskView extends ViewGroup {

    private static final String TAG = "MaskView";

    private final RectF mTargetRect = new RectF();
    private ArrayList<RectF> mTargetRects;
    private final RectF mFullingRect   = new RectF();
    private final RectF mChildTmpRect  = new RectF();
    private final Paint mFullingPaint  = new Paint();
    private       int   mPadding       = 0;
    private       int   mPaddingLeft   = 0;
    private       int   mPaddingTop    = 0;
    private       int   mPaddingRight  = 0;
    private       int   mPaddingBottom = 0;
    private boolean mOverlayTarget;
    // 是否忽略Fit值，忽略后坐标从（0，0）开始
    private       boolean mIgnoreFit   = false;
    private       int     mCorner      = 0;
    private       int     mStyle       = GuideComponent.ROUNDRECT;
    private final int     LEFT_TOP     = 1;
    private final int     RIGHT_TOP    = 2;
    private final int     LEFT_BOTTOM  = 3;
    private final int     RIGHT_BOTTOM = 4;
    private Paint  mEraser;
    private Bitmap mEraserBitmap;
    private Canvas mEraserCanvas;

    public GuideMaskView(Context context) {
        this(context, null, 0);
    }

    public GuideMaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideMaskView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        Point size = new Point();
        size.x = getResources().getDisplayMetrics().widthPixels;
        size.y = getResources().getDisplayMetrics().heightPixels;

        mEraserBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        mEraserCanvas = new Canvas(mEraserBitmap);

        mEraser = new Paint();
        mEraser.setColor(0xFFFFFFFF);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            clearFocus();
            mEraserCanvas.setBitmap(null);
            mEraserBitmap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int w = MeasureSpec.getSize(widthMeasureSpec);
        final int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w, h);
        final int count = getChildCount();
        View child;
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            if (child != null) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp == null) {
                    child.setLayoutParams(lp);
                }
                measureChild(child, w + MeasureSpec.EXACTLY, h + MeasureSpec.EXACTLY);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        View child;
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            if (child == null) {
                continue;
            }
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp == null) {
                continue;
            }

            if (mIgnoreFit) {
                mChildTmpRect.left = 0;
                mChildTmpRect.top = 0;
                mChildTmpRect.right = child.getMeasuredWidth();
                mChildTmpRect.bottom = child.getMeasuredHeight();
            } else if (lp.targetIsCover) {
                switch (lp.targetAnchor) {
                    case LayoutParams.ANCHOR_LEFT://左
                        mChildTmpRect.right = mTargetRect.right;
                        mChildTmpRect.left = mChildTmpRect.right - child.getMeasuredWidth();
                        verticalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_TOP://上
                        mChildTmpRect.bottom = mTargetRect.bottom;
                        mChildTmpRect.top = mChildTmpRect.bottom - child.getMeasuredHeight();
                        horizontalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_RIGHT://右
                        mChildTmpRect.left = mTargetRect.left;
                        mChildTmpRect.right = mChildTmpRect.left + child.getMeasuredWidth();
                        verticalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_BOTTOM://下
                        mChildTmpRect.top = mTargetRect.top;
                        mChildTmpRect.bottom = mChildTmpRect.top + child.getMeasuredHeight();
                        horizontalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_OVER://中心
                        mChildTmpRect.left = ((int) mTargetRect.width() - child.getMeasuredWidth()) >> 1;
                        mChildTmpRect.top = ((int) mTargetRect.height() - child.getMeasuredHeight()) >> 1;
                        mChildTmpRect.right = ((int) mTargetRect.width() + child.getMeasuredWidth()) >> 1;
                        mChildTmpRect.bottom = ((int) mTargetRect.height() + child.getMeasuredHeight()) >> 1;
                        mChildTmpRect.offset(mTargetRect.left, mTargetRect.top);
                        break;
                }
            } else {
                switch (lp.targetAnchor) {
                    case LayoutParams.ANCHOR_LEFT://左
                        mChildTmpRect.right = mTargetRect.left;
                        mChildTmpRect.left = mChildTmpRect.right - child.getMeasuredWidth();
                        verticalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_TOP://上
                        mChildTmpRect.bottom = mTargetRect.top;
                        mChildTmpRect.top = mChildTmpRect.bottom - child.getMeasuredHeight();
                        horizontalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_RIGHT://右
                        mChildTmpRect.left = mTargetRect.right;
                        mChildTmpRect.right = mChildTmpRect.left + child.getMeasuredWidth();
                        verticalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_BOTTOM://下
                        mChildTmpRect.top = mTargetRect.bottom;
                        mChildTmpRect.bottom = mChildTmpRect.top + child.getMeasuredHeight();
                        horizontalChildPositionLayout(child, mChildTmpRect, lp.targetParentPosition);
                        break;
                    case LayoutParams.ANCHOR_OVER://中心
                        mChildTmpRect.left = ((int) mTargetRect.width() - child.getMeasuredWidth()) >> 1;
                        mChildTmpRect.top = ((int) mTargetRect.height() - child.getMeasuredHeight()) >> 1;
                        mChildTmpRect.right = ((int) mTargetRect.width() + child.getMeasuredWidth()) >> 1;
                        mChildTmpRect.bottom = ((int) mTargetRect.height() + child.getMeasuredHeight()) >> 1;
                        mChildTmpRect.offset(mTargetRect.left, mTargetRect.top);
                        break;
                }
            }
            //额外的xy偏移
            mChildTmpRect.offset(lp.offsetX, lp.offsetY);
            child.layout((int) mChildTmpRect.left, (int) mChildTmpRect.top, (int) mChildTmpRect.right,
                    (int) mChildTmpRect.bottom);
        }
    }

    private void horizontalChildPositionLayout(View child, RectF rect, int targetParentPosition) {
        switch (targetParentPosition) {
            case LayoutParams.PARENT_START:
                rect.left = mTargetRect.left;
                rect.right = rect.left + child.getMeasuredWidth();
                break;
            case LayoutParams.PARENT_CENTER:
                rect.left = (mTargetRect.width() - child.getMeasuredWidth()) / 2;
                rect.right = (mTargetRect.width() + child.getMeasuredWidth()) / 2;
                rect.offset(mTargetRect.left, 0);
                break;
            case LayoutParams.PARENT_END:
                rect.right = mTargetRect.right;
                rect.left = rect.right - child.getMeasuredWidth();
                break;
        }
    }

    private void verticalChildPositionLayout(View child, RectF rect, int targetParentPosition) {
        switch (targetParentPosition) {
            case LayoutParams.PARENT_START:
                rect.top = mTargetRect.top;
                rect.bottom = rect.top + child.getMeasuredHeight();
                break;
            case LayoutParams.PARENT_CENTER:
                rect.top = (mTargetRect.width() - child.getMeasuredHeight()) / 2;
                rect.bottom = (mTargetRect.width() + child.getMeasuredHeight()) / 2;
                rect.offset(0, mTargetRect.top);
                break;
            case LayoutParams.PARENT_END:
                rect.bottom = mTargetRect.bottom;
                rect.top = mTargetRect.bottom - child.getMeasuredHeight();
                break;
        }
    }

    /**
     * 设置padding
     */
    private void resetPadding(RectF rectF) {
        if (mPaddingLeft != 0) {
            rectF.left -= mPaddingLeft;
        }
        if (mPaddingTop != 0) {
            rectF.top -= mPaddingTop;
        }
        if (mPaddingRight != 0) {
            rectF.right += mPaddingRight;
        }
        if (mPaddingBottom != 0) {
            rectF.bottom += mPaddingBottom;
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final long drawingTime = getDrawingTime();
        try {
            View child;
            for (int i = 0; i < getChildCount(); i++) {
                child = getChildAt(i);
                drawChild(canvas, child, drawingTime);
            }
        } catch (NullPointerException e) {

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEraserBitmap.eraseColor(Color.TRANSPARENT);
        mEraserCanvas.drawColor(mFullingPaint.getColor());
        if (!mOverlayTarget) {
            if (mTargetRects == null) { // 绘制一个引导页只有一个高亮区域
                switch (mStyle) {
                    case GuideComponent.ROUNDRECT:
                        mEraserCanvas.drawRoundRect(mTargetRect, mCorner, mCorner, mEraser);
                        break;
                    case GuideComponent.CIRCLE:
                        mEraserCanvas.drawCircle(mTargetRect.centerX(), mTargetRect.centerY(),
                                mTargetRect.width() / 2, mEraser);
                        break;
                    case GuideComponent.SPECIAL:
                        //把不需要的圆角去掉
                        mEraserCanvas.drawRoundRect(mTargetRect, mCorner, mCorner, mEraser);
                        clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRect, RIGHT_TOP);
                        clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRect, LEFT_BOTTOM);
                        clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRect, RIGHT_BOTTOM);
                        break;
                    case GuideComponent.SPECIAL_TOP_RIGHT:
                        mEraserCanvas.drawRoundRect(mTargetRect, mCorner, mCorner, mEraser);
                        clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRect, LEFT_TOP);
                        clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRect, LEFT_BOTTOM);
                        clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRect, RIGHT_BOTTOM);
                        break;
                    default:
                        mEraserCanvas.drawRoundRect(mTargetRect, mCorner, mCorner, mEraser);
                        break;
                }
            } else { // 绘制一个引导页有多个高亮区域
                for (int i = 0; i < mTargetRects.size(); i++) {
                    switch (mStyle) {
                        case GuideComponent.ROUNDRECT:
                            mEraserCanvas.drawRoundRect(mTargetRects.get(i), mCorner, mCorner, mEraser);
                            break;
                        case GuideComponent.CIRCLE:
                            mEraserCanvas.drawCircle(mTargetRects.get(i).centerX(), mTargetRects.get(i).centerY(),
                                    mTargetRects.get(i).width() / 2, mEraser);
                            break;
                        case GuideComponent.SPECIAL:
                            //把不需要的圆角去掉
                            mEraserCanvas.drawRoundRect(mTargetRects.get(i), mCorner, mCorner, mEraser);
                            clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRects.get(i), RIGHT_TOP);
                            clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRects.get(i), LEFT_BOTTOM);
                            clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRects.get(i), RIGHT_BOTTOM);
                            break;
                        case GuideComponent.SPECIAL_TOP_RIGHT:
                            mEraserCanvas.drawRoundRect(mTargetRects.get(i), mCorner, mCorner, mEraser);
                            clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRects.get(i), LEFT_TOP);
                            clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRects.get(i), LEFT_BOTTOM);
                            clipCorner(mEraserCanvas, mEraser, mCorner, mTargetRects.get(i), RIGHT_BOTTOM);
                            break;
                        default:
                            mEraserCanvas.drawRoundRect(mTargetRects.get(i), mCorner, mCorner, mEraser);
                            break;
                    }
                }
            }
            canvas.drawBitmap(mEraserBitmap, 0, 0, null);
        }
    }

    /**
     * 切直角（为适应产品需求做的改变，本没有路，走的人多了就形成了路）
     * 思路：在RoundRect的特定角补上一个小的正方形，将原来的圆角覆盖
     *
     * @param canvas
     * @param paint
     * @param cornerWidth: 正方形的边长
     * @param rect:        原始的RectF
     * @param direction:   目标角的方位
     */
    private void clipCorner(final Canvas canvas, final Paint paint, int cornerWidth, RectF rect, int direction) {

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;
        switch (direction) {
            case LEFT_TOP:
                left = rect.left;
                top = rect.top + cornerWidth;
                right = rect.left + cornerWidth;
                bottom = rect.top;
                break;
            case LEFT_BOTTOM:
                left = rect.left;
                top = rect.bottom;
                right = rect.left + cornerWidth;
                bottom = rect.bottom - cornerWidth;
                break;
            case RIGHT_TOP:
                left = rect.right - cornerWidth;
                top = rect.top + cornerWidth;
                right = rect.right;
                bottom = rect.top;
                break;
            case RIGHT_BOTTOM:
                left = rect.right - cornerWidth;
                top = rect.bottom;
                right = rect.right;
                bottom = rect.bottom - cornerWidth;
                break;
        }
        final RectF block = new RectF(left, top, right, bottom);
        canvas.drawRect(block, paint);
    }

    /**
     * 返回点击区域是否是target区域
     *
     * @return
     */
    public boolean isTargetRect(float x, float y) {
        if (x <= mTargetRect.right && x >= mTargetRect.left && y >= mTargetRect.top && y <= mTargetRect.bottom) {
            return true;
        }
        return false;
    }

    public void setTargetRect(RectF rect) {
        mTargetRect.set(rect);
        resetPadding(rect);
        invalidate();
    }

    public void setTargetRects(RectF rect) {
        //        mTargetRect.set(rect);
        if (mTargetRects == null) {
            mTargetRects = new ArrayList<RectF>();
        }
        mTargetRects.add(rect);
        resetPadding(rect);
        invalidate();
    }

    public void setFullingAlpha(int alpha) {
        mFullingPaint.setAlpha(alpha);
        invalidate();
    }

    public void setFullingColor(int color) {
        mFullingPaint.setColor(color);
        invalidate();
    }

    public void setStyle(int style) {
        mStyle = style;
    }

    public void setHighTargetCorner(int corner) {
        this.mCorner = corner;
    }

    public void setOverlayTarget(boolean b) {
        mOverlayTarget = b;
    }

    public void setIgnoreFit(boolean b) {
        mIgnoreFit = b;
    }

    public void setPadding(int padding) {
        this.mPadding = padding;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.mPaddingLeft = paddingLeft;
    }

    public void setPaddingTop(int paddingTop) {
        this.mPaddingTop = paddingTop;
    }

    public void setPaddingRight(int paddingRight) {
        this.mPaddingRight = paddingRight;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.mPaddingBottom = paddingBottom;
    }

    static class LayoutParams extends ViewGroup.LayoutParams {

        public static final int ANCHOR_LEFT   = 0x01;
        public static final int ANCHOR_TOP    = 0x02;
        public static final int ANCHOR_RIGHT  = 0x03;
        public static final int ANCHOR_BOTTOM = 0x04;
        public static final int ANCHOR_OVER   = 0x05;

        public static final int PARENT_START  = 0x10;
        public static final int PARENT_CENTER = 0x20;
        public static final int PARENT_END    = 0x30;

        public int     targetAnchor         = ANCHOR_BOTTOM;
        public int     targetParentPosition = PARENT_CENTER;
        public boolean targetIsCover        = false;
        public int     offsetX              = 0;
        public int     offsetY              = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
