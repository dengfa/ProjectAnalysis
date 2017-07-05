//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.blocks;

import android.graphics.Canvas;

import com.vincent.projectanalysis.module.coretext.TextEnv;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.List;

public class CYLineBlock extends CYBlock<CYBlock> {
    private int mWidth;
    private int mHeight;
    private boolean isInMonopolyRow;
    private CYParagraphStyle mParagraphStyle;
    private int mMaxHeightInLine = 0;
    private boolean isValid = false;
    private static final int DP_20 = UIUtils.dip2px(20.0F);

    public CYLineBlock(TextEnv textEnv, CYParagraphStyle style) {
        super(textEnv, "");
        this.mParagraphStyle = style;
    }

    public int getContentWidth() {
        return this.mWidth;
    }

    public int getContentHeight() {
        if(this.mHeight <= 0) {
            this.mHeight = DP_20;
        }

        return this.mHeight;
    }

    public int getLineHeight() {
        return this.getContentHeight();
    }

    public void draw(Canvas canvas) {
        List children = this.getChildren();
        if(children != null) {
            int count = children.size();

            for(int i = 0; i < count; ++i) {
                CYBlock block = (CYBlock)children.get(i);
                block.draw(canvas);
            }
        }

    }

    public boolean isValid() {
        return this.isValid;
    }

    public void addChild(CYBlock child) {
        super.addChild(child);
        if(child.isValid()) {
            int width = child.getWidth();
            int height = child.getHeight();
            boolean isInMonoMode = false;
            if(child instanceof CYPlaceHolderBlock && (((CYPlaceHolderBlock)child).getAlignStyle() == AlignStyle.Style_Normal || ((CYPlaceHolderBlock)child).getAlignStyle() == AlignStyle.Style_MONOPOLY)) {
                this.isInMonopolyRow = true;
                isInMonoMode = true;
            }

            if((child instanceof CYTextBlock || isInMonoMode) && height > this.mHeight) {
                this.mHeight = height;
            }

            if(height > this.mMaxHeightInLine) {
                this.mMaxHeightInLine = height;
            }

            this.mWidth += width;
            this.isValid = true;
        }
    }

    public void onMeasure() {
        List blocks = this.getChildren();
        if(blocks != null && !blocks.isEmpty()) {
            int count = blocks.size();

            for(int i = 0; i < count; ++i) {
                CYBlock block = (CYBlock)blocks.get(i);
                block.onMeasure();
            }
        }

        super.onMeasure();
    }

    public int getMaxBlockHeightInLine() {
        return this.mMaxHeightInLine;
    }

    public void updateLineY(int lineY) {
        this.setLineY(lineY);
        List children = this.getChildren();
        if(children != null) {
            int appendX = 0;
            if(this.mParagraphStyle != null) {
                if(this.mParagraphStyle.getHorizontalAlign() == CYHorizontalAlign.CENTER) {
                    appendX = this.getTextEnv().getPageWidth() - this.getWidth() >> 1;
                } else if(this.mParagraphStyle.getHorizontalAlign() == CYHorizontalAlign.RIGHT) {
                    appendX = this.getTextEnv().getPageWidth() - this.getWidth();
                }
            }

            int lineHeight = this.getLineHeight();
            int childCount = children.size();

            for(int i = 0; i < childCount; ++i) {
                CYBlock child = (CYBlock)children.get(i);
                child.setIsInMonopolyRow(this.isInMonopolyRow);
                child.setX(child.getX() + appendX);
                child.setLineY(lineY);
                child.setLineHeight(lineHeight);
            }
        }

    }

    public void setIsFinishingLineInParagraph(boolean isFinishingLine) {
        if(isFinishingLine && this.mParagraphStyle != null) {
            this.setPadding(0, this.getPaddingTop(), 0, this.mParagraphStyle.getMarginBottom());
        }

    }

    public void setIsFirstLineInParagraph(boolean isFirstLine) {
        if(isFirstLine && this.mParagraphStyle != null) {
            this.setPadding(0, this.mParagraphStyle.getMarginTop(), 0, this.getPaddingBottom());
        }

    }
}
