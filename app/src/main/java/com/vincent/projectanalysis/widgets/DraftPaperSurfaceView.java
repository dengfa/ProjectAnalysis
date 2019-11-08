package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Stack;

/**
 * 草稿本
 */
public class DraftPaperSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private float mPreviousX, mPreviousY;
    private Paint mPaint;
    private Path  mDrawPath;

    private Stack<Path>   mLayerIfs = new Stack<Path>();
    private SurfaceHolder mHolder;
    private boolean       mIsDrawing;

    public DraftPaperSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DraftPaperSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DraftPaperSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);


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

    public void drawCanvas(Canvas canvas) {
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            Canvas canvas = mHolder.lockCanvas();
            try {
                drawCanvas(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public interface DraftPaperListener {

        void onPaperChange(boolean hasContent);
    }
}