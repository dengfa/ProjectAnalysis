package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.R;

public class RippleView extends View {

    private static final int MSG_LOOPER_RIPPLE = 1;
    public static final  int DEF_COLOR         = 0x49617291;

    private Paint mRipplePaint;

    private int rippleFirstRadius  = 0;
    private int rippleSecendRadius = -33;
    private int rippleThirdRadius  = -66;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
            rippleFirstRadius++;
            if (rippleFirstRadius > 100) {
                rippleFirstRadius = 0;
            }
            rippleSecendRadius++;
            if (rippleSecendRadius > 100) {
                rippleSecendRadius = 0;
            }
            rippleThirdRadius++;
            if (rippleThirdRadius > 100) {
                rippleThirdRadius = 0;
            }
            sendEmptyMessageDelayed(0, 20);

        }
    };
    private int mColor;
    private static int DEF_WIDTH = 300;
    private float   mRadius;
    private Handler mLooperHandler;
    private boolean isRipple;

    /**
     * @param context
     */
    public RippleView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        mColor = typedArray.getColor(R.styleable.RippleView_RvColor, DEF_COLOR);
        mRadius = typedArray.getDimension(R.styleable.RippleView_RvRadius, DEF_WIDTH / 2);
        typedArray.recycle();
        init();
    }

    private void init() {
        mRipplePaint = new Paint();
        mRipplePaint.setColor(mColor);
        mRipplePaint.setAntiAlias(true);
        mRipplePaint.setStyle(Paint.Style.FILL);

        HandlerThread thread = new HandlerThread("handler_ripple");
        thread.start();
        mLooperHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                postInvalidate();
                rippleFirstRadius++;
                if (rippleFirstRadius > 100) {
                    rippleFirstRadius = 0;
                }
                rippleSecendRadius++;
                if (rippleSecendRadius > 100) {
                    rippleSecendRadius = 0;
                }
                rippleThirdRadius++;
                if (rippleThirdRadius > 100) {
                    rippleThirdRadius = 0;
                }
                sendEmptyMessageDelayed(MSG_LOOPER_RIPPLE, 20);
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) (2 * mRadius);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) mRadius;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f1 = 3 * mRadius / 10;
        mRipplePaint.setAlpha(255);
        canvas.drawCircle(getWidth() / 2, getHeight(), 7 * mRadius / 10, mRipplePaint);
        int i1 = (int) (220.0F - (220.0F - 0.0F) / 100.0F * rippleFirstRadius);
        mRipplePaint.setAlpha(i1);
        canvas.drawCircle(getWidth() / 2, getHeight(), 7 * mRadius / 10 + f1 * rippleFirstRadius / 100.0F, mRipplePaint);
        if (rippleSecendRadius >= 0) {
            int i3 = (int) (220.0F - (220.0F - 0.0F) / 100.0F * rippleSecendRadius);
            mRipplePaint.setAlpha(i3);
            canvas.drawCircle(getWidth() / 2, getHeight(), 7 * mRadius / 10 + f1 * rippleSecendRadius / 100.0F, mRipplePaint);
        }
        if (rippleThirdRadius >= 0) {
            int i2 = (int) (220.0F - (220.0F - 0.0F) / 100.0F * rippleThirdRadius);
            mRipplePaint.setAlpha(i2);
            canvas.drawCircle(getWidth() / 2, getHeight(), 7 * mRadius / 10 + f1 * rippleThirdRadius / 100.0F, mRipplePaint);
        }
    }

    public void stratRipple() {
        isRipple = true;
        Message msg = mLooperHandler.obtainMessage(MSG_LOOPER_RIPPLE);
        msg.sendToTarget();
    }

    public void stopRipple() {
        isRipple = false;
        mLooperHandler.removeMessages(MSG_LOOPER_RIPPLE);
    }

    public boolean isRipple() {
        return isRipple;
    }
}