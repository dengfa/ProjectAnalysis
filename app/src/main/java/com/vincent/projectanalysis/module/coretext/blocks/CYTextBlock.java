//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.vincent.projectanalysis.module.coretext.TextEnv;

import java.util.List;

public class CYTextBlock extends CYBlock {
    private int start;
    private int count;
    private char[] chs;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private boolean mIsWord = false;
    FontMetrics mFontMetrics;

    public CYTextBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        if(TextUtils.isEmpty(content)) {
            content = "";
        }

        this.chs = content.toCharArray();
        this.start = 0;
        this.count = this.chs.length;
        this.mPaint = new Paint(1);
        this.mPaint.set(textEnv.getPaint());
        this.mIsWord = false;
        this.parseSubBlocks();
    }

    private CYTextBlock buildChildBlock(TextEnv textEnv, Paint paint, int width, int height, char[] chs, int start, int count) {
        try {
            CYTextBlock e = (CYTextBlock)this.clone();
            e.setTextEnv(textEnv);
            e.mPaint = paint;
            e.mWidth = width;
            e.mHeight = height;
            e.chs = chs;
            e.mIsWord = true;
            e.start = start;
            e.count = count;
            return e;
        } catch (CloneNotSupportedException var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public void setParagraphStyle(CYParagraphStyle style) {
        super.setParagraphStyle(style);
        if(style != null) {
            this.mPaint.setTextSize((float)style.getTextSize());
            this.mPaint.setColor(style.getTextColor());
            this.mWidth = (int)this.mPaint.measureText(this.chs, this.start, this.count);
            this.mHeight = this.getTextHeight(this.mPaint);
        }

    }

    public CYTextBlock setTextColor(int color) {
        if(this.mPaint != null && color > 0) {
            this.mPaint.setColor(color);
        }

        return this;
    }

    public CYTextBlock setTypeFace(Typeface typeface) {
        if(this.mPaint != null && typeface != null) {
            this.mPaint.setTypeface(typeface);
        }

        return this;
    }

    public CYTextBlock setTextSize(int fontSize) {
        if(this.mPaint != null && fontSize > 0) {
            this.mPaint.setTextSize((float)fontSize);
            this.mWidth = (int)this.mPaint.measureText(this.chs, this.start, this.count);
        }

        return this;
    }

    public List<CYBlock> getChildren() {
        return this.mIsWord?null:super.getChildren();
    }

    private void parseSubBlocks() {
        if(this.chs.length > 0) {
            int blockHeight = this.getTextHeight(this.mPaint);
            FontMetrics fontMetrics = this.mPaint.getFontMetrics();
            TextEnv textEnv = this.getTextEnv();

            for(int i = 0; i < this.chs.length; ++i) {
                int wordStart = i;

                int count;
                for(count = 1; i + 1 < this.chs.length && isLetter(this.chs[i + 1]) && !Character.isSpace(this.chs[i + 1]); ++i) {
                    ++count;
                }

                int blockWidth = (int)this.mPaint.measureText(this.chs, wordStart, count);
                CYTextBlock block = this.buildChildBlock(textEnv, this.mPaint, blockWidth, blockHeight, this.chs, wordStart, count);
                if(block != null) {
                    block.mFontMetrics = fontMetrics;
                    this.addChild(block);
                }
            }
        }

    }

    public int getContentWidth() {
        return this.mWidth;
    }

    public int getContentHeight() {
        return this.mHeight;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(this.mFontMetrics != null && this.mIsWord) {
            Rect rect = this.getContentRect();
            float x = (float)rect.left;
            float y = (float)rect.bottom - this.mFontMetrics.bottom;
            canvas.drawText(this.chs, this.start, this.count, x, y, this.mPaint);
            CYParagraphStyle paragraphStyle = this.getParagraphStyle();
            if(paragraphStyle != null) {
                String style = paragraphStyle.getStyle();
                if("under_line".equals(style)) {
                    canvas.drawLine(x, (float)rect.bottom, x + (float)rect.width(), (float)rect.bottom, this.mPaint);
                }
            }
        }

    }

    public static boolean isLetter(char ch) {
        return 65 <= ch && ch <= 90 || 97 <= ch && ch <= 122 || ch == 45;
    }
}
