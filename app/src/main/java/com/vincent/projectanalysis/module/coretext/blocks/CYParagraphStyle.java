//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.blocks;

import com.vincent.projectanalysis.module.coretext.TextEnv;

public class CYParagraphStyle {
    private String            mStyleId;
    private CYHorizontalAlign mHorizontalAlign;
    private int               mTextColor;
    private int               mTextSize;
    private int               mMarginTop;
    private int               mMarginBottom;
    private String            mStyle;
    private TextEnv           mTextEnv;

    public CYParagraphStyle(TextEnv textEnv) {
        this.mHorizontalAlign = CYHorizontalAlign.LEFT;
        this.mTextEnv = textEnv;
    }

    public void setStyleId(String styleId) {
        this.mStyleId = styleId;
    }

    public String getStyleId() {
        return this.mStyleId;
    }

    public void setStyle(String style) {
        this.mStyle = style;
    }

    public String getStyle() {
        return this.mStyle;
    }

    public void setHorizontalAlign(CYHorizontalAlign align) {
        this.mHorizontalAlign = align;
    }

    public CYHorizontalAlign getHorizontalAlign() {
        return this.mHorizontalAlign;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public void setMarginTop(int marginTop) {
        this.mMarginTop = marginTop;
    }

    public int getMarginTop() {
        return this.mMarginTop;
    }

    public void setMarginBottom(int marginBottom) {
        this.mMarginBottom = marginBottom;
    }

    public int getMarginBottom() {
        return this.mMarginBottom;
    }
}
