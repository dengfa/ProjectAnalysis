/**
 *
 */
package com.vincent.projectanalysis.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.vincent.projectanalysis.R;

public class WaterWaveView extends View {
    public static final String TAG = "WaterWaveView";
    public static final int    T   = 2;//周期

    private Context mContext;

    private int     mWidth;
    private int     mHeight;
    private float[] mYPositions;
    private static final int   WAVE_PAINT_COLOR = 0x880000aa;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 20;
    private              int   offY             = 100;
    private Handler mHandler;
    private double mCycleFactorW = 2 * Math.PI / 400;
    private boolean mStarted;
    private int     mXOneOffset;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    private int mXTwoOffset = 100;
    private Paint                mWavePaint;
    private int                  mXOffsetSpeedOne;
    private int                  mXOffsetSpeedTwo;
    private PaintFlagsDrawFilter mDrawFilter;
    private float                c;
    private Bitmap               mMaskBitmap;
    private Rect                 mMaskSrcRect;
    private Rect                 mMaskDestRect;

    /**
     * @param context
     */
    public WaterWaveView(Context context) {
        super(context);
        mContext = context;
        init(mContext);
    }

    /**
     * @param context
     * @param attrs
     */
    public WaterWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(mContext);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(mContext);
    }


    private void init(Context context) {
        mXOffsetSpeedOne = 5;
        mXOffsetSpeedTwo = 10;

        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
        // 去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        // 设置风格为实线
        mWavePaint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
        mWavePaint.setColor(WAVE_PAINT_COLOR);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {
                    resetPositonY();
                    // 改变两条波纹的移动点
                    mXOneOffset += mXOffsetSpeedOne;
                    mXTwoOffset += mXOffsetSpeedTwo;

                    // 如果已经移动到结尾处，则重头记录
                    if (mXOneOffset >= mWidth) {
                        mXOneOffset = 0;
                    }
                    if (mXTwoOffset > mWidth) {
                        mXTwoOffset = 0;
                    }
                    invalidate();
                    mHandler.sendEmptyMessageDelayed(0, 20);
                }
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measure(widthMeasureSpec, true);
        int height = measure(heightMeasureSpec, false);
        if (width < height) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(height, height);
        }
    }

    /**
     * @param measureSpec
     * @param isWidth
     * @return
     * @category 测量
     */
    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight()
                : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth()
                    : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCycleFactorW = 2 * Math.PI / mWidth * T;
        mYPositions = new float[mWidth];
        // 根据view总宽度得出所有对应的y值
        for (int i = 0; i < mWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i));
        }

        // 用于保存波纹一的y值
        mResetOneYPositions = new float[mWidth];
        // 用于保存波纹二的y值
        mResetTwoYPositions = new float[mWidth];
        resetPositonY();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(mDrawFilter);
        for (int i = 0; i < mWidth; i++) {
            // 绘制第一条水波纹
            canvas.drawLine(i, mHeight - mResetOneYPositions[i] - offY, i, mHeight, mWavePaint);
            // 绘制第二条水波纹
            canvas.drawLine(i, mHeight - mResetTwoYPositions[i] - offY, i, mHeight, mWavePaint);
        }


        //添加遮罩
        int sc = canvas.saveLayer(0, 0, 200, 200, mWavePaint, Canvas.ALL_SAVE_FLAG);
        for (int i = 0; i < mWidth; i++) {
            // 绘制第一条水波纹
            canvas.drawLine(i, 0, i, mResetOneYPositions[i] + offY, mWavePaint);

            // 绘制第二条水波纹
            canvas.drawLine(i, 0, i, mResetTwoYPositions[i] + offY, mWavePaint);
        }
        mWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mMaskBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.circle_500)).getBitmap();
        int maskWidth = mMaskBitmap.getWidth();
        int maskHeight = mMaskBitmap.getHeight();
        mMaskSrcRect = new Rect(0, 0, maskWidth, maskHeight);
        mMaskDestRect = new Rect(0, 0, 200, 200);
        canvas.drawBitmap(mMaskBitmap, mMaskSrcRect, mMaskDestRect, mWavePaint);
        mWavePaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    private void resetPositonY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
                yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = (int) c;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        c = ss.progress;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 关闭硬件加速，防止异常unsupported operation exception ？？？
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * @category 开始波动
     */
    public void startWave() {
        if (!mStarted) {
            mStarted = true;
            this.mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * @category 停止波动
     */
    public void stopWave() {
        if (mStarted) {
            mStarted = false;
            this.mHandler.removeMessages(0);
        }
    }

    public boolean IsWaving() {
        return mStarted;
    }

    /**
     * @category 保存状态
     */
    static class SavedState extends BaseSavedState {
        int progress;

        /**
         * Constructor called from {@link ProgressBar#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            progress = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
