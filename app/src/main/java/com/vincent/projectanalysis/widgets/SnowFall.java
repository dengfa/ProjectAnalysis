package com.vincent.projectanalysis.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.knowbox.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.Vector;

public class SnowFall extends View {
    private static final int MSG_LOOPER_MAKE_SNOW = 1;
    private static final int MSG_LOOPER_REFRESH = 2;
    private static int[] mSnowRes;
    private Bitmap[] mSnowBitmap;
    private Vector<Snow> mSnows = new Vector();
    private int mSnowFall;
    private Handler mLooperHandler;
    private boolean mSnowing = false;

    public SnowFall(Context context) {
        super(context);
        this.init();
    }

    public SnowFall(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public SnowFall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        HandlerThread thread = new HandlerThread("handler_snow");
        thread.start();
        this.mLooperHandler = new Handler(thread.getLooper()) {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SnowFall.this.looper(msg);
            }
        };
    }

    public void snow(int snowFall) {
        this.mSnowing = true;
        this.mSnowFall = snowFall;
        Message makeSnow = this.mLooperHandler.obtainMessage(1);
        makeSnow.sendToTarget();
        Message refresh = this.mLooperHandler.obtainMessage(2);
        refresh.sendToTarget();
    }

    public void stop() {
        this.mSnowing = false;
        this.mLooperHandler.removeMessages(1);
    }

    public void release() {
        this.mSnowing = false;
        this.mSnows.clear();
        this.mLooperHandler.removeMessages(2);
    }

    private void looper(Message msg) {
        int what = msg.what;
        Message next;
        switch (what) {
            case 1:
                this.checkIfCreateSnow();
                next = this.mLooperHandler.obtainMessage(1);
                this.mLooperHandler.sendMessageDelayed(next, 200L);
                break;
            case 2:
                this.postInvalidate();
                if (this.mSnowing) {
                    if (!this.mSnows.isEmpty()) {
                        this.trimSnow();
                    }

                    next = this.mLooperHandler.obtainMessage(2);
                    this.mLooperHandler.sendMessageDelayed(next, 20L);
                }
        }

    }

    private synchronized void trimSnow() {
        ArrayList invalidSnows = new ArrayList();
        Rect container = new Rect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < this.mSnows.size(); ++i) {
            SnowFall.Snow snow = (SnowFall.Snow) this.mSnows.get(i);
            Point point = snow.getPoint();
            if (!container.contains(point.x, point.y) || !snow.isValid()) {
                invalidSnows.add(snow);
            }
        }

        this.mSnows.removeAll(invalidSnows);
    }

    private void checkIfCreateSnow() {
        boolean left = true;

        for (int i = 0; i < this.mSnowFall; ++i) {
            int startX = (int) ((double) this.getWidth() * Math.random());
            int startY = (int) (Math.random() * (double) UIUtils.dip2px(10.0F));
            int endX = (int) ((double) this.getWidth() * Math.random());
            int endY = (int) ((double) (this.getHeight() / 4) * Math.random()) + this.getHeight() * 3 / 4;
            Bitmap bitmap = this.mSnowBitmap[(int) (Math.random() * (double) this.mSnowBitmap.length)];
            this.mSnows.add(new SnowFall.Snow(bitmap, new Point(startX, startY), new Point(endX, endY)));
            left = !left;
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int snowCnt = this.mSnows.size();

        for (int i = 0; i < snowCnt; ++i) {
            try {
                ((SnowFall.Snow) this.mSnows.get(i)).onDraw(canvas);
            } catch (Exception var5) {
            }
        }
    }

    public void setSnowRes(int[] snowRes) {
        mSnowRes = snowRes;
        this.mSnowBitmap = new Bitmap[mSnowRes.length];

        for (int i = 0; i < mSnowRes.length; ++i) {
            this.mSnowBitmap[i] = BitmapFactory.decodeResource(this.getResources(), mSnowRes[i]);
        }
    }

    private class Snow {
        private Path mPath;
        private PathMeasure mPathMeasure;
        private Point mPoint = new Point();
        private Bitmap mSnowBitmap;
        private Paint mPaint;
        private int mAlpha = 255;
        private float mScale = 1.0F;
        private int mRotate = 0;
        private boolean mRunning = true;

        public Snow(Bitmap snowBitmap, Point start, Point end) {
            this.mSnowBitmap = snowBitmap;
            this.mPath = new Path();
            this.mPath.moveTo((float) start.x, (float) start.y);
            this.mPath.lineTo((float) end.x, (float) end.y);
            this.mPathMeasure = new PathMeasure(this.mPath, false);
            this.mPaint = new Paint(1);
            this.mScale = 1.0F - (float) (Math.random() * 0.9D);
            this.start();
        }

        private void start() {
            final float length = this.mPathMeasure.getLength();
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration((long) (Math.random() * 5000.0D));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = length * ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    Snow.this.mAlpha = 255 - (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 100.0F);
                    Snow.this.mRotate = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 300.0F);
                    float[] pos = new float[2];
                    Snow.this.mPathMeasure.getPosTan(value, pos, (float[]) null);
                    Snow.this.mPoint.set((int) pos[0], (int) pos[1]);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    Snow.this.mRunning = true;
                }

                public void onAnimationEnd(Animator animator) {
                    Snow.this.mRunning = false;
                }

                public void onAnimationCancel(Animator animator) {
                    Snow.this.mRunning = false;
                }

                public void onAnimationRepeat(Animator animator) {
                    Snow.this.mRunning = false;
                }
            });
            animator.start();
        }

        public Point getPoint() {
            return this.mPoint;
        }

        public boolean isValid() {
            return this.mRunning;
        }

        public void onDraw(Canvas canvas) {
            this.mPaint.setAlpha(this.mAlpha);
            canvas.save();
            canvas.rotate((float) this.mRotate, (float) this.mPoint.x, (float) this.mPoint.y);
            canvas.drawBitmap(this.mSnowBitmap, (float) this.mPoint.x, (float) this.mPoint.y, this.mPaint);
            canvas.restore();
        }
    }
}
