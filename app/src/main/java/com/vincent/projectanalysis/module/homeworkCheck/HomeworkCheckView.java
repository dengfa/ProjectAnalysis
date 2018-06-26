package com.vincent.projectanalysis.module.homeworkCheck;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 17/10/10.
 */

public class HomeworkCheckView extends RelativeLayout implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    public static final int INT = 100;
    private Context mContext;
    private int scale;
    private GestureDetectorCompat mGestureDetectorCompat;
    private ScaleGestureDetector mScaleGestureDetector;
    private DisplayMetrics mDisplayMetrics;

    public HomeworkCheckView(Context context) {
        this(context, null);
    }

    public HomeworkCheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mGestureDetectorCompat = new GestureDetectorCompat(context, this);
        mGestureDetectorCompat.setOnDoubleTapListener(this);
        setOnTouchListener(this);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);

        mDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    public void setData() {

        for (int i = 1; i < 5; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageResource(R.drawable.ic_launcher);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
            layoutParams.leftMargin = i * 200;
            layoutParams.topMargin = i * 200;
            addView(iv, layoutParams);
        }
    }

    public void setScale() {
        scale++;
        if (scale > 5) {
            scale = 1;
        }
        setScaleX(scale);
        setScaleY(scale);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetectorCompat.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        LogUtil.d("vincent", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        LogUtil.d("vincent", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.d("vincent", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        LogUtil.d("vincent", "onScroll");
        LogUtil.d("vincent", "getTranslationX - " + getTranslationX());
        LogUtil.d("vincent", "getTranslationY - " + getTranslationY());


        LogUtil.d("vincent", "getRight - " + getRight());
        LogUtil.d("vincent", "getLeft - " + getLeft());
        LogUtil.d("vincent", "distanceX - " + distanceX);
        LogUtil.d("vincent", "distanceY - " + distanceY);

        //右左拖动 边界限制
        if ((distanceX < 0 && getRight() > mDisplayMetrics.widthPixels)
                || (distanceX > 0 && getLeft() < 0)) {

        }

        setTranslationX(getTranslationX() - distanceX);
        setTranslationY(getTranslationY() - distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtil.d("vincent", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtil.d("vincent", "onFling");

        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        LogUtil.d("vincent", "onSingleTapConfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        setPivotX(e.getX());
        setPivotY(e.getY());
        setScale();
        LogUtil.d("vincent", "onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        LogUtil.d("vincent", "onDoubleTapEvent");
        return false;
    }


    /**
     * 缩放手势
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        LogUtil.d("vincent", "onScale");

        float scaleFactor = detector.getScaleFactor();
        LogUtil.d("vincent", "scaleFactor: " + scaleFactor);
        float scaleX = getScaleX() * scaleFactor;
        scaleX = scaleX < 0 ? 1 : scaleX;
        float scaleY = getScaleY() * scaleFactor;
        scaleX = scaleX < 0 ? 1 : scaleX;
        setScaleX(scaleX);
        setScaleY(scaleY);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        setPivotX(detector.getFocusX());
        setPivotY(detector.getFocusY());
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
