package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

/**
 * 草稿本
 */
public class DraftPaperView extends View {

    private float mPreviousX, mPreviousY;
    private Paint mPaint;
    private Path  mDrawPath;

    private Stack<Path> mLayerIfs = new Stack<Path>();

    public DraftPaperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DraftPaperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DraftPaperView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);// 空心
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setPathEffect(new CornerPathEffect(80));
        mDrawPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mPreviousX = event.getX();
                mPreviousY = event.getY();
                mDrawPath = new Path();
                mLayerIfs.add(mDrawPath);
                mDrawPath.moveTo(mPreviousX, mPreviousY);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float x = event.getX();
                final float y = event.getY();
                final float dx = Math.abs(x - mPreviousX);
                final float dy = Math.abs(y - mPreviousY);
                if (dx >= 3 || dy >= 3) {
                    mDrawPath.lineTo(x, y);
                    mPreviousX = x;
                    mPreviousY = y;
                }
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (mDraftPaperListener != null) {
                    mDraftPaperListener.onPaperChange(!mLayerIfs.isEmpty());
                }
                break;
            }
            default:
                break;
        }
        return true;
    }

    public void clear() {
        mLayerIfs.clear();
        invalidate();

        if (mDraftPaperListener != null) {
            mDraftPaperListener.onPaperChange(!mLayerIfs.isEmpty());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLayerIfs != null) {
            for (int i = 0; i < mLayerIfs.size(); i++) {
                Path pathInfo = mLayerIfs.get(i);
                if (!pathInfo.isEmpty()) {
                    canvas.drawPath(pathInfo, mPaint);
                }
            }
        }
    }

    private DraftPaperListener mDraftPaperListener;

    public void setDraftPaperListener(DraftPaperListener listener) {
        this.mDraftPaperListener = listener;
    }

    public interface DraftPaperListener {

        void onPaperChange(boolean hasContent);
    }
}