//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.SparseArray;

import com.vincent.projectanalysis.module.coretext.base.event.CYEventDispatcher;
import com.vincent.projectanalysis.module.coretext.base.utils.EditableValue;

public class TextEnv {
    private Context context;
    private int fontSize = 50;
    private int textColor = -16777216;
    private Typeface typeface;
    private int verticalSpacing = 0;
    private int pageWidth = 0;
    private int pageHeight = 0;
    private boolean editable = true;
    private TextEnv.Align textAlign;
    private float mFontScale;
    private Paint mPaint;
    private CYEventDispatcher mEventDispatcher;
    private SparseArray<EditableValue> mEditableValues;
    private String mTag;
    private boolean mDebug;

    public TextEnv(Context context) {
        this.textAlign = TextEnv.Align.BOTTOM;
        this.mFontScale = 1.0F;
        this.mPaint = new Paint(1);
        this.mEventDispatcher = new CYEventDispatcher();
        this.mEditableValues = new SparseArray();
        this.mTag = "";
        this.mDebug = false;
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public TextEnv setFontSize(int fontSize) {
        this.fontSize = fontSize;
        this.mPaint.setTextSize((float)fontSize);
        return this;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return this.mTag;
    }

    public TextEnv setFontScale(float scale) {
        this.mFontScale = scale;
        return this;
    }

    public float getFontScale() {
        return this.mFontScale;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public TextEnv setTextColor(int textColor) {
        this.textColor = textColor;
        this.mPaint.setColor(textColor);
        return this;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public TextEnv setTypeface(Typeface typeface) {
        this.typeface = typeface;
        if(typeface != null) {
            this.mPaint.setTypeface(typeface);
        }

        return this;
    }

    public int getVerticalSpacing() {
        return this.verticalSpacing;
    }

    public TextEnv setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        return this;
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public TextEnv setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
        return this;
    }

    public int getPageHeight() {
        return this.pageHeight;
    }

    public TextEnv setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
        return this;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public TextEnv setEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public TextEnv.Align getTextAlign() {
        return this.textAlign;
    }

    public TextEnv setTextAlign(TextEnv.Align textAlign) {
        this.textAlign = textAlign;
        return this;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public CYEventDispatcher getEventDispatcher() {
        return this.mEventDispatcher;
    }

    public void setEditableValue(int tabId, String value) {
        EditableValue editableValue = this.getEditableValue(tabId);
        if(editableValue == null) {
            editableValue = new EditableValue();
            this.setEditableValue(tabId, editableValue);
        }

        editableValue.setValue(value);
    }

    public void setEditableValue(int tabId, EditableValue value) {
        if(this.mEditableValues != null) {
            this.mEditableValues.put(tabId, value);
        }
    }

    public EditableValue getEditableValue(int tabId) {
        return this.mEditableValues != null?(EditableValue)this.mEditableValues.get(tabId):null;
    }

    public SparseArray<EditableValue> getEditableValues() {
        return this.mEditableValues;
    }

    public void clearEditableValues() {
        if(this.mEditableValues != null) {
            this.mEditableValues.clear();
        }

    }

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    public boolean isDebug() {
        return this.mDebug;
    }

    public static enum Align {
        TOP,
        CENTER,
        BOTTOM;

        private Align() {
        }
    }
}
