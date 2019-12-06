package com.vincent.projectanalysis.widgets.mapScene;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.UIUtils;

/**
 * Created by dengfa on 2019-12-06.
 * Des:
 */
public class MapScene extends View {

    private float mTouchSlop       = 0;
    private float mMinimumVelocity = 0;
    private float mMaximumVelocity = 0;

    private boolean mDragging = false;
    private float   mLastX, mLastY;

    private Scroller        mScroller;
    private VelocityTracker mVelocityTracker;

    private int      mapHeight = UIUtils.dip2px(1500);
    private Bitmap[] mBitmaps  = new Bitmap[5];
    private Paint    mPaint;
    private int      mWidth;
    private Matrix   mMatrix;

    public MapScene(Context context) {
        super(context);
        LogUtil.d("vincent", "MapScene(Context context)");
        init();
    }

    public MapScene(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LogUtil.d("vincent", "MapScene(Context context, AttributeSet attrs)");
        init();
    }

    public MapScene(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.d("vincent", "MapScene(Context context, AttributeSet attrs, int defStyleAttr)");
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();//手势滑动距离
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();//fling动作最小速度
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();//fling动作最大速度
        mBitmaps[0] = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_0_156);
        mBitmaps[1] = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_0_1742);
        mBitmaps[2] = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_392_1285);
        mBitmaps[3] = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_431_2449);
        mBitmaps[4] = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_0_2632);
        mMatrix = new Matrix();
        mPaint = new Paint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mMatrix.postScale(mWidth * 1.0f / mBitmaps[4].getWidth(), mWidth * 1.0f / mBitmaps[4].getWidth());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.gray));

        canvas.save();
        canvas.translate(0, UIUtils.dip2px(50));
        canvas.drawBitmap(mBitmaps[0], 0, UIUtils.dip2px(100), mPaint);

        canvas.translate(0, mBitmaps[0].getHeight() + UIUtils.dip2px(150));
        canvas.drawBitmap(mBitmaps[1], 0, 0, mPaint);

        canvas.save();
        canvas.translate(mWidth - mBitmaps[2].getWidth(),
                mBitmaps[1].getHeight() + UIUtils.dip2px(100));
        canvas.drawBitmap(mBitmaps[2], 0, 0, mPaint);
        canvas.restore();
        canvas.restore();


        canvas.save();
        //mMatrix.postTranslate(0,mapHeight - mBitmaps[4].getHeight());
        float[] values = new float[9];
        mMatrix.getValues(values);
        float scaleX = values[Matrix.MSCALE_X];
        canvas.translate(0, (mapHeight - mBitmaps[4].getHeight() * scaleX));
        canvas.drawBitmap(mBitmaps[4], mMatrix, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth - mBitmaps[3].getWidth(), mapHeight - mBitmaps[3].getHeight());
        canvas.drawBitmap(mBitmaps[3], 0, 0, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = x;
                mLastY = y;
                abortScroller();
                initOrResetVelocityTracker();
                addTrackerMovement(event);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                addTrackerMovement(event);
                int minY = mapHeight - getHeight();
                int dy = -(int) (y - mLastY);
                if (getScrollY() + dy >= minY) {
                    scrollTo(0, minY);
                } else if (getScrollY() + dy < 0) {
                    scrollTo(0, 0);
                } else {
                    scrollBy(0, dy);
                    mLastY = y;
                }
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                mDragging = false;
                addTrackerMovement(event);
                abortScroller();
                if (mVelocityTracker != null) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    float yVelocity = mVelocityTracker.getYVelocity();
                    if (Math.abs(yVelocity) > mMinimumVelocity) {
                        int minY = mapHeight - getHeight();
                        mScroller.fling(0, getScrollY(), 0, -(int) yVelocity
                                , 0, 0, 0, minY);
                        invalidate();
                    }
                }
                recycleTracker();
                break;
            }
        }
        return true;
    }

    private void abortScroller() {
        if (mScroller != null && !mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
    }

    private void addTrackerMovement(MotionEvent event) {
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void recycleTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
