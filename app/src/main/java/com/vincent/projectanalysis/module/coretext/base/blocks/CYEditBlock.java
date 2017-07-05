//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.vincent.projectanalysis.module.coretext.base.TextEnv;
import com.vincent.projectanalysis.module.coretext.base.utils.EditableValue;
import com.vincent.projectanalysis.utils.UIUtils;

public class CYEditBlock extends CYPlaceHolderBlock implements ICYEditable {
    private int mTabId = 0;
    private CYEditFace mEditFace;

    public CYEditBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.init();
    }

    private void init() {
        Paint paint = this.getTextEnv().getPaint();
        int height = (int)(Math.ceil((double)(paint.descent() - paint.ascent())) + 0.5D);
        this.setWidth(UIUtils.dip2px(80.0F));
        this.setHeight(height);
        this.setFocusable(true);
        this.mEditFace = this.createEditFace(this.getTextEnv(), this);
        this.mEditFace.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
    }

    protected CYEditFace createEditFace(TextEnv textEnv, ICYEditable editable) {
        return new CYEditFace(textEnv, editable);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.mEditFace.onDraw(canvas, this.getBlockRect(), this.getContentRect());
    }

    public CYEditFace getEditFace() {
        return this.mEditFace;
    }

    public int getTabId() {
        return this.mTabId;
    }

    public void setTabId(int tabId) {
        this.mTabId = tabId;
    }

    public String getText() {
        EditableValue value = this.getTextEnv().getEditableValue(this.getTabId());
        return value == null?null:value.getValue();
    }

    public void setText(String text) {
        this.getTextEnv().setEditableValue(this.getTabId(), text);
        this.mEditFace.setText(text);
        this.requestLayout();
    }

    public void setTextColor(int color) {
        EditableValue value = this.getTextEnv().getEditableValue(this.getTabId());
        if(value == null) {
            value = new EditableValue();
            this.getTextEnv().setEditableValue(this.getTabId(), value);
        }

        value.setColor(color);
        this.mEditFace.setTextColor(color);
        this.postInvalidateThis();
    }

    public void setDefaultText(String defaultText) {
        this.mEditFace.setDefaultText(defaultText);
    }

    public void setFocus(boolean focus) {
        super.setFocus(focus);
        if(this.isFocusable()) {
            this.mEditFace.setFocus(focus);
        }

    }

    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        if(this.mEditFace != null) {
            this.mEditFace.setPadding(left, top, right, bottom);
        }

    }

    public boolean hasFocus() {
        return this.mEditFace.hasFocus();
    }

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        if(this.mEditFace != null) {
            this.mEditFace.setEditable(focusable);
        }

    }

    public void setParagraphStyle(CYParagraphStyle style) {
        super.setParagraphStyle(style);
        if(this.mEditFace != null) {
            this.mEditFace.setParagraphStyle(style);
        }

    }

    public int getContentWidth() {
        return super.getContentWidth();
    }

    public int getContentHeight() {
        return super.getContentHeight();
    }

    public void release() {
        super.release();
        if(this.mEditFace != null) {
            this.mEditFace.release();
        }

    }
}
