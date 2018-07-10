package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.vincent.projectanalysis.R;

public class ClearableEditext extends AppCompatEditText {
    private Drawable mCloseDrawable;
    private boolean mCloseEnabled;

    Paint mPaint;
    Paint mPaint2;

    public ClearableEditext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = getPaint();
        mPaint2 = getPaint();
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.inputAttrs);
            mCloseDrawable = ta.getDrawable(R.styleable.inputAttrs_closeTogDrawable);
            if (mCloseDrawable != null) {
                mCloseDrawable.setBounds(0, 0, mCloseDrawable.getIntrinsicWidth(), mCloseDrawable.getIntrinsicHeight());
            }
            mCloseEnabled = ta.getBoolean(R.styleable.inputAttrs_closeTogEnabled, false);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCloseEnabled) {
            if (getText().toString().length() > 0)
                closeDraw(canvas);
        }
    }

    private void closeDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() - 3 * mCloseDrawable.getIntrinsicWidth(), getHeight() / 2 - mCloseDrawable.getIntrinsicHeight() / 2);
        if (mCloseDrawable != null) {
            mCloseDrawable.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mCloseEnabled && event.getX() > getWidth() - 3 * mCloseDrawable.getIntrinsicWidth()) {
                    setText("");
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}