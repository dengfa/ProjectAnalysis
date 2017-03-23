package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.projectanalysis.R;

public class RippleView extends View {
    public static final int DEF_COLOR = 0x49617291;
    private int    mScreenWidth;
    private int    mScreenHeight;
    private Bitmap mRippleBitmap;
    private Paint mRipplePaint = new Paint();
    private int     mBitmapWidth;
    private int     mBitmapHeight;
    private boolean isStartRipple;
    private int     heightPaddingTop;
    private int     heightPaddingBottom;
    private int     widthPaddingLeft;
    private int     widthPaddingRight;
    private RectF  mRect              = new RectF();
    private int    rippleFirstRadius  = 0;
    private int    rippleSecendRadius = -33;
    private int    rippleThirdRadius  = -66;
    private Paint  textPaint          = new Paint();
    private String mText              = "点击我吧";
    private int    mDefaultWidth      = 300;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
            if (isStartRipple) {
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
        }
    };
    private int mColor;
    private static int DEF_WIDTH = 300;
    private float mRadius;

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
        mRipplePaint.setColor(mColor);
        mRipplePaint.setAntiAlias(true);
        mRipplePaint.setStyle(Paint.Style.FILL);
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
            width = DEF_WIDTH;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = DEF_WIDTH / 2;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStartRipple) {
            float f1 = 3 * mRadius / 10;
            mRipplePaint.setAlpha(255);
            canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
                    7 * mRadius / 10, mRipplePaint);
            int i1 = (int) (220.0F - (220.0F - 0.0F) / 100.0F * rippleFirstRadius);
            mRipplePaint.setAlpha(i1);
            canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
                    7 * mRadius / 10 + f1 * rippleFirstRadius / 100.0F,
                    mRipplePaint);

            if (rippleSecendRadius >= 0) {
                int i3 = (int) (220.0F - (220.0F - 0.0F) / 100.0F
                        * rippleSecendRadius);
                mRipplePaint.setAlpha(i3);
                canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight,
                        7 * mRadius / 10 + f1 * rippleSecendRadius
                                / 100.0F, mRipplePaint);
            }
            if (rippleThirdRadius >= 0) {
                int i2 = (int) (220.0F - (220.0F - 0.0F) / 100.0F
                        * rippleThirdRadius);
                mRipplePaint.setAlpha(i2);
                canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight, 7
                                * mRadius / 10 + f1 * rippleThirdRadius / 100.0F,
                        mRipplePaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;
        confirmSize();
        invalidate();
    }

    private void confirmSize() {
        int minScreenSize = Math.min(mScreenWidth, mScreenHeight);
        int widthOverSize = mScreenWidth - minScreenSize;
        int heightOverSize = mScreenHeight - minScreenSize;
        heightPaddingTop = (getPaddingTop() + heightOverSize / 2);
        heightPaddingBottom = (getPaddingBottom() + heightOverSize / 2);
        widthPaddingLeft = (getPaddingLeft() + widthOverSize / 2);
        widthPaddingRight = (getPaddingRight() + widthOverSize / 2);
        int width = getWidth();
        int height = getHeight();
        mRect = new RectF(widthPaddingLeft, heightPaddingTop, width
                - widthPaddingRight, height * 2 - heightPaddingBottom);
    }

    public void stratRipple() {
        isStartRipple = true;
        handler.sendEmptyMessage(0);
    }
}