package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class TagView extends View {

    public static final String TAG = "TagView";

    protected final static int DEFAULT_WIDTH = 200;
    protected final static int DEFAULT_HEIGHT = 200;

    private Context mContext;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private int mWidth;
    private int mHeight;

    private int mTextPaddingHorizontal;
    private int mTextPaddingVertical;
    private int mTextMargin;
    private int mBgRadius;

    private List<String> mTags = new ArrayList<>();

    public TagView(Context context) {
        super(context);
        mContext = context;
        init(mContext);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(mContext);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(getResources().getColor(R.color.color_black_333333));
        mTextPaint.setTextSize(UIUtils.dip2px(context, 14));

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(getResources().getColor(R.color.color_f4f4f4));

        mTextPaddingHorizontal = UIUtils.dip2px(context, 10);
        mTextPaddingVertical = UIUtils.dip2px(context, 5);
        mTextMargin = UIUtils.dip2px(context, 5);
        mBgRadius = UIUtils.dip2px(context, 20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = DEFAULT_WIDTH;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = DEFAULT_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.d(TAG, "mWidth: " + mWidth);
        Log.d(TAG, "mHeight: " + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentWidth;
        int tempHorizontalPosition = 0;
        int maxIndex = 0;
        int length = (int) mTextPaint.measureText("...");
        for (int i = 0; i < mTags.size(); i++) {
            String str = mTags.get(i);
            Rect rect = new Rect();
            mTextPaint.getTextBounds(str, 0, str.length(), rect);
            int width = rect.width();
            currentWidth = width + 2 * mTextPaddingHorizontal + mTextMargin;
            if (mWidth - tempHorizontalPosition - currentWidth - length >= 0) {
                maxIndex = i;
                tempHorizontalPosition += currentWidth;
            } else {
                break;
            }
        }

        Log.d(TAG, "maxIndex: " + maxIndex);
        int emptyLength = mWidth - tempHorizontalPosition - length;
        currentWidth = 0;
        tempHorizontalPosition = maxIndex == mTags.size() - 1 ? emptyLength + length : emptyLength;
        for (int i = 0; i <= maxIndex; i++) {
            String str = mTags.get(i);
            Rect rect = new Rect();
            mTextPaint.getTextBounds(str, 0, str.length(), rect);
            int width = rect.width();
            int height = rect.height();
            Log.d(TAG, "textWidth: " + width);
            Log.d(TAG, "currentHorizontalPosition: " + currentWidth);
            currentWidth = width + 2 * mTextPaddingHorizontal + mTextMargin;
            canvas.drawRoundRect(tempHorizontalPosition,
                    mHeight / 2 - height / 2 - mTextPaddingVertical,
                    tempHorizontalPosition + mTextPaddingHorizontal * 2 + width
                    , mHeight / 2 + height / 2 + mTextPaddingVertical,
                    mBgRadius, mBgRadius, mBackgroundPaint);
            canvas.drawText(str, tempHorizontalPosition + mTextPaddingHorizontal,
                    mHeight / 2 + height / 2, mTextPaint);
            tempHorizontalPosition += currentWidth;
            if (i == maxIndex && maxIndex < mTags.size() - 1) {
                canvas.drawText("...", tempHorizontalPosition, mHeight / 2 + height / 2, mTextPaint);
            }
        }
    }

    public void setTags(List<String> tags) {
        mTags.clear();
        mTags.addAll(tags);
        invalidate();
    }
}
