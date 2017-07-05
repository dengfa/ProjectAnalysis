//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.vincent.projectanalysis.module.coretext.base.TextEnv;
import com.vincent.projectanalysis.module.coretext.base.utils.EditableValue;
import com.vincent.projectanalysis.utils.UIUtils;


public class CYEditFace {
    private static final int ACTION_FLASH = 1;
    private Handler mHandler;
    private boolean mInputFlash = false;
    private boolean mIsEditable = true;
    private TextEnv mTextEnv;
    private ICYEditable mEditable;
    protected Paint mTextPaint;
    protected Paint mFlashPaint;
    protected Paint mBorderPaint;
    protected Paint mBackGroundPaint;
    protected Paint mDefaultTxtPaint;
    protected FontMetrics mTextPaintMetrics;
    protected FontMetrics mDefaultTextPaintMetrics;
    private String mDefaultText;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private CYParagraphStyle mParagraphStyle;

    public CYEditFace(TextEnv textEnv, ICYEditable editable) {
        this.mTextEnv = textEnv;
        this.mEditable = editable;
        this.init();
    }

    protected void init() {
        this.mTextPaint = new Paint(1);
        this.mTextPaint.set(this.mTextEnv.getPaint());
        this.mTextPaintMetrics = this.mTextPaint.getFontMetrics();
        this.mDefaultTxtPaint = new Paint(this.mTextPaint);
        this.mDefaultTextPaintMetrics = this.mDefaultTxtPaint.getFontMetrics();
        this.mFlashPaint = new Paint(1);
        this.mFlashPaint.setStrokeWidth((float) UIUtils.dip2px(this.mTextEnv.getContext(), 2.0F));
        this.mBorderPaint = new Paint(1);
        this.mBorderPaint.setColor(-16777216);
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setStrokeWidth(2.0F);
        this.mBackGroundPaint = new Paint(1);
        this.mBackGroundPaint.setColor(-7829368);
        this.mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                CYEditFace.this.handleMessageImpl(msg);
            }
        };
    }

    public void postInit() {
        EditableValue value = this.mTextEnv.getEditableValue(this.mEditable.getTabId());
        if(value != null && value.getColor() != -1) {
            this.mTextPaint.setColor(value.getColor());
        }

    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
    }

    public void setDefaultText(String defaultText) {
        this.mDefaultText = defaultText;
    }

    public Paint getTextPaint() {
        return this.mTextPaint;
    }

    public Paint getDefaultTextPaint() {
        return this.mDefaultTxtPaint;
    }

    public Paint getBackGroundPaint() {
        return this.mBackGroundPaint;
    }

    public void onDraw(Canvas canvas, Rect blockRect, Rect contentRect) {
        this.drawBackGround(canvas, blockRect, contentRect);
        this.drawBorder(canvas, blockRect, contentRect);
        this.drawFlash(canvas, contentRect);
        String text = this.getText();
        if(TextUtils.isEmpty(text)) {
            this.drawDefaultText(canvas, contentRect);
        } else {
            this.drawText(canvas, this.getText(), contentRect);
        }

    }

    protected void drawBorder(Canvas canvas, Rect blockRect, Rect contentRect) {
        canvas.drawRect(blockRect, this.mBorderPaint);
    }

    protected void drawBackGround(Canvas canvas, Rect blockRect, Rect contentRect) {
        if(!this.hasFocus()) {
            canvas.drawRect(blockRect, this.mBackGroundPaint);
        }

    }

    protected void drawFlash(Canvas canvas, Rect contentRect) {
        if(this.mIsEditable && this.hasFocus() && this.mInputFlash) {
            String text = this.getText();
            float left;
            if(!TextUtils.isEmpty(text)) {
                float textWidth = this.mTextPaint.measureText(text);
                if(textWidth > (float)contentRect.width()) {
                    left = (float)contentRect.right;
                } else {
                    left = (float)contentRect.left + ((float)contentRect.width() + textWidth) / 2.0F;
                }
            } else {
                left = (float)(contentRect.left + contentRect.width() / 2);
            }

            canvas.drawLine(left, (float)contentRect.top, left, (float)contentRect.bottom, this.mFlashPaint);
        }

    }

    protected void drawText(Canvas canvas, String text, Rect contentRect) {
        if(!TextUtils.isEmpty(text)) {
            float textWidth = this.mTextPaint.measureText(text);
            float contentWidth = (float)contentRect.width();
            float x;
            if(textWidth > contentWidth) {
                x = (float)contentRect.right - textWidth;
            } else {
                x = (float)contentRect.left + ((float)contentRect.width() - textWidth) / 2.0F;
            }

            canvas.save();
            canvas.clipRect(contentRect);
            Align align = this.getTextEnv().getTextAlign();
            float y;
            if(align == Align.TOP) {
                y = (float)(contentRect.top + this.getTextHeight(this.mTextPaint)) - this.mTextPaintMetrics.bottom;
            } else if(align == Align.CENTER) {
                y = (float)(contentRect.top + (contentRect.height() + this.getTextHeight(this.mTextPaint)) / 2) - this.mTextPaintMetrics.bottom;
            } else {
                y = (float)contentRect.bottom - this.mTextPaintMetrics.bottom;
            }

            canvas.drawText(text, x, y, this.mTextPaint);
            canvas.restore();
        }

    }

    protected void drawDefaultText(Canvas canvas, Rect contentRect) {
        if(!TextUtils.isEmpty(this.mDefaultText)) {
            float textWidth = this.mDefaultTxtPaint.measureText(this.mDefaultText);
            float contentWidth = (float)contentRect.width();
            float x;
            if(textWidth > contentWidth) {
                x = (float)contentRect.right - textWidth;
            } else {
                x = (float)contentRect.left + ((float)contentRect.width() - textWidth) / 2.0F;
            }

            canvas.save();
            canvas.clipRect(contentRect);
            canvas.drawText(this.mDefaultText, x, (float)contentRect.bottom - this.mDefaultTextPaintMetrics.bottom, this.mTextPaint);
            canvas.restore();
        }

    }

    private void handleMessageImpl(Message msg) {
        int what = msg.what;
        switch(what) {
        case 1:
            this.mInputFlash = !this.mInputFlash;
            Message next = this.mHandler.obtainMessage(1);
            this.mHandler.sendMessageDelayed(next, 500L);
            if(this.mTextEnv != null) {
                this.mTextEnv.getEventDispatcher().postInvalidate((Rect)null);
            }
        default:
        }
    }

    public String getText() {
        return this.mEditable != null?this.mEditable.getText():null;
    }

    public void setText(String text) {
    }

    public void setTextColor(int color) {
        if(this.mTextPaint != null) {
            this.mTextPaint.setColor(color);
        }

    }

    public void setEditable(boolean isEditable) {
        this.mIsEditable = isEditable;
        if(this.mTextEnv != null) {
            this.mTextEnv.getEventDispatcher().postInvalidate((Rect)null);
        }

    }

    public void setFocus(boolean hasFocus) {
        if(hasFocus && this.mEditable != null) {
            CYPageView.FOCUS_TAB_ID = this.mEditable.getTabId();
        }

        if(this.hasFocus()) {
            this.mHandler.removeMessages(1);
            Message next = this.mHandler.obtainMessage(1);
            this.mHandler.sendMessageDelayed(next, 500L);
        } else {
            this.mHandler.removeMessages(1);
        }

        if(this.mTextEnv != null) {
            this.mTextEnv.getEventDispatcher().postInvalidate((Rect)null);
        }

    }

    public boolean hasFocus() {
        return this.mIsEditable && CYPageView.FOCUS_TAB_ID == this.mEditable.getTabId();
    }

    public TextEnv getTextEnv() {
        return this.mTextEnv;
    }

    public void setParagraphStyle(CYParagraphStyle style) {
        this.mParagraphStyle = style;
    }

    public void release() {
        this.mHandler.removeMessages(1);
    }

    public int getTextHeight(Paint paint) {
        return (int)(Math.ceil((double)(paint.descent() - paint.ascent())) + 0.5D);
    }
}
