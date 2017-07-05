//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.blocks;

import android.graphics.Canvas;

import com.vincent.projectanalysis.module.coretext.base.TextEnv;

public class CYPlaceHolderBlock extends CYBlock {
    private CYPlaceHolderBlock.AlignStyle mAlignStyle;
    private int mWidth;
    private int mHeight;

    public CYPlaceHolderBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.mAlignStyle = CYPlaceHolderBlock.AlignStyle.Style_Normal;
    }

    public CYPlaceHolderBlock.AlignStyle getAlignStyle() {
        return this.mAlignStyle;
    }

    public CYPlaceHolderBlock setAlignStyle(CYPlaceHolderBlock.AlignStyle style) {
        this.mAlignStyle = style;
        return this;
    }

    public CYPlaceHolderBlock setWidth(int width) {
        this.mWidth = width;
        return this;
    }

    public CYPlaceHolderBlock setHeight(int height) {
        this.mHeight = height;
        return this;
    }

    public int getContentWidth() {
        return this.mWidth;
    }

    public int getContentHeight() {
        return this.mHeight;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public static enum AlignStyle {
        Style_Normal,
        Style_Round,
        Style_MONOPOLY;

        private AlignStyle() {
        }
    }
}
