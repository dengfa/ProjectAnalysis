package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

/**
 * 自定义可清除内容和隐藏显示密码的Edittext
 */
public class ClearAndPswEditText extends AppCompatEditText {
    private boolean mCloseEnabled;
    private Drawable mCloseDrawable;

    private boolean mPasswordEnabled = false;
    private boolean mPasswordVisible = false;
    private Drawable mPasswordTogDrawableOpen;
    private Drawable passwordTogDrawableClose;

    int clearDrawableLeft;
    int pswDrawableLeft;

    Paint mPaint;
    Paint mPaint2;

    public ClearAndPswEditText(Context context, AttributeSet attrs) {
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
            mPasswordTogDrawableOpen = ta.getDrawable(R.styleable.inputAttrs_pswTogDrawableOpen);
            passwordTogDrawableClose = ta.getDrawable(R.styleable.inputAttrs_pswTogDrawableClose);
            if (mPasswordTogDrawableOpen != null) {
                mPasswordTogDrawableOpen.setBounds(0, 0, mPasswordTogDrawableOpen.getIntrinsicWidth(), mPasswordTogDrawableOpen.getIntrinsicHeight());
            }
            if (passwordTogDrawableClose != null) {
                passwordTogDrawableClose.setBounds(0, 0, passwordTogDrawableClose.getIntrinsicWidth(), passwordTogDrawableClose.getIntrinsicHeight());
            }
            mPasswordEnabled = ta.getBoolean(R.styleable.inputAttrs_pswTogEnabled, false);
            ta.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mPasswordTogDrawableOpen != null) {
            pswDrawableLeft = getWidth() - mPasswordTogDrawableOpen.getIntrinsicWidth();
        }
        if (mCloseDrawable != null) {
            clearDrawableLeft = getWidth() - mCloseDrawable.getIntrinsicWidth()
                    - (mPasswordEnabled && mPasswordTogDrawableOpen != null ?
                    mPasswordTogDrawableOpen.getIntrinsicWidth() + UIUtils.dip2px(15) : 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCloseEnabled) {
            if (getText().toString().length() > 0)
                closeDraw(canvas);
        }

        if (mPasswordEnabled && getText().toString().length() > 0) {
            if (mPasswordVisible) {
                passwrodDrawOpen(canvas);
            } else {
                passwrodDrawClose(canvas);
            }
        }
    }

    private void closeDraw(Canvas canvas) {
        if (mCloseDrawable != null) {
            canvas.save();
            canvas.translate(clearDrawableLeft, getHeight() / 2 - mCloseDrawable.getIntrinsicHeight() / 2);
            mCloseDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void passwrodDrawOpen(Canvas canvas) {
        if (mPasswordTogDrawableOpen != null) {
            canvas.save();
            canvas.translate(pswDrawableLeft,
                    getHeight() / 2 - mPasswordTogDrawableOpen.getIntrinsicHeight() / 2);
            mPasswordTogDrawableOpen.draw(canvas);
            canvas.restore();
        }
    }

    private void passwrodDrawClose(Canvas canvas) {
        if (passwordTogDrawableClose != null) {
            canvas.save();
            canvas.translate(pswDrawableLeft,
                    getHeight() / 2 - passwordTogDrawableClose.getIntrinsicHeight() / 2);
            passwordTogDrawableClose.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mPasswordEnabled
                        && event.getX() > pswDrawableLeft
                        && event.getY() > 0
                        && event.getY() < getHeight()) {
                    if (mPasswordVisible) {
                        setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mPasswordVisible = false;
                    } else {
                        setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mPasswordVisible = true;
                    }
                } else if (mCloseEnabled
                        && event.getX() > clearDrawableLeft) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}