//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

import com.vincent.projectanalysis.module.coretext.base.TextEnv;
import com.vincent.projectanalysis.module.coretext.base.TextEnv.Align;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class CYBlock<T extends CYBlock> implements ICYFocusable, Cloneable {
    private static final String TAG = "CYBlock";
    private int x;
    private int lineY;
    private int lineHeight;
    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;
    private boolean mFocus = false;
    private Rect mContentRect = new Rect();
    private Rect mBlockRect = new Rect();
    private List<T> mChildren = new ArrayList();
    private TextEnv mTextEnv;
    private Paint   mPaint;
    private boolean mIsInMonopolyRow = true;
    private boolean mFocusable = false;
    private CYParagraphStyle mParagraphStyle;
    private static int DP_1 = UIUtils.dip2px(1.0F);

    public CYBlock(TextEnv textEnv, String content) {
        this.mTextEnv = textEnv;
        this.paddingBottom = DP_1;
        if(this.isDebug()) {
            this.mPaint = new Paint(1);
            this.mPaint.setColor(0xff000000);
            this.mPaint.setStyle(Style.STROKE);
        }

    }

    public TextEnv getTextEnv() {
        return this.mTextEnv;
    }

    public void setTextEnv(TextEnv textEnv) {
        this.mTextEnv = textEnv;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setLineY(int lineY) {
        this.lineY = lineY;
    }

    public int getLineY() {
        return this.lineY;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public int getLineHeight() {
        return this.lineHeight;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
    }

    public int getPaddingLeft() {
        return this.paddingLeft;
    }

    public int getPaddingTop() {
        return this.paddingTop;
    }

    public int getPaddingRight() {
        return this.paddingRight;
    }

    public int getPaddingBottom() {
        return this.paddingBottom;
    }

    public abstract int getContentWidth();

    public abstract int getContentHeight();

    public int getHeight() {
        return this.getContentHeight() + this.paddingTop + this.paddingBottom;
    }

    public int getWidth() {
        return this.getContentWidth() + this.paddingLeft + this.paddingRight;
    }

    public void setIsInMonopolyRow(boolean isInMonopolyRow) {
        this.mIsInMonopolyRow = isInMonopolyRow;
    }

    public void draw(Canvas canvas) {
        if(this.isDebug()) {
            canvas.drawRect(this.getContentRect(), this.mPaint);
            canvas.drawRect(this.getBlockRect(), this.mPaint);
        }

    }

    public void onMeasure() {
    }

    public void addChild(T child) {
        if(this.mChildren == null) {
            this.mChildren = new ArrayList();
        }

        this.mChildren.add(child);
    }

    public List<T> getChildren() {
        return this.mChildren;
    }

    public Rect getContentRect() {
        int left = this.x + this.paddingLeft;
        int right = this.x + this.paddingLeft + this.getContentWidth();
        Align align = this.getTextEnv().getTextAlign();
        int contentHeight = this.getContentHeight();
        int top;
        if(align != Align.TOP && this.mIsInMonopolyRow) {
            if(align == Align.CENTER) {
                top = this.lineY + (this.getLineHeight() - contentHeight >> 1);
            } else {
                top = this.lineY + this.getLineHeight() - contentHeight - this.paddingBottom;
            }
        } else {
            top = this.lineY + this.paddingTop;
        }

        this.mContentRect.set(left, top, right, top + contentHeight);
        return this.mContentRect;
    }

    public Rect getBlockRect() {
        int left = this.x;
        int right = this.x + this.getContentWidth() + this.paddingLeft + this.paddingRight;
        Align align = this.getTextEnv().getTextAlign();
        int contentHeight = this.getContentHeight();
        int top;
        if(align != Align.TOP && this.mIsInMonopolyRow) {
            if(align == Align.CENTER) {
                top = this.lineY + (this.getLineHeight() - contentHeight >> 1) - this.paddingTop;
            } else {
                top = this.lineY + this.getLineHeight() - contentHeight - this.paddingTop - this.paddingBottom;
            }
        } else {
            top = this.lineY;
        }

        this.mBlockRect.set(left, top, right, top + contentHeight + this.paddingTop + this.paddingBottom);
        return this.mBlockRect;
    }

    public boolean onTouchEvent(int action, float x, float y) {
        if(this.isDebug()) {
            this.debug("onEvent: " + action);
        }

        return false;
    }

    public void requestLayout() {
        if(this.mTextEnv != null) {
            this.mTextEnv.getEventDispatcher().requestLayout();
        }

    }

    public void postInvalidateThis() {
        if(this.mTextEnv != null) {
            this.mTextEnv.getEventDispatcher().postInvalidate(this.getBlockRect());
        }

    }

    public void postInvalidate() {
        if(this.mTextEnv != null) {
            this.mTextEnv.getEventDispatcher().postInvalidate((Rect)null);
        }

    }

    public boolean isDebug() {
        return this.mTextEnv != null?this.mTextEnv.isDebug():false;
    }

    protected void debug(String msg) {
        Log.v("CYBlock", msg);
    }

    public void setFocus(boolean focus) {
        if(this.isFocusable()) {
            this.mFocus = focus;
            if(this.isDebug()) {
                this.debug("rect: " + this.getBlockRect().toString() + ", focus: " + focus);
            }
        }

    }

    public boolean hasFocus() {
        return this.mFocus;
    }

    public void setFocusable(boolean focusable) {
        this.mFocusable = focusable;
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public ICYEditable findEditableByTabId(int tabId) {
        List children = this.getChildren();
        if(children != null && !children.isEmpty()) {
            for(int i = 0; i < children.size(); ++i) {
                CYBlock block = (CYBlock)children.get(i);
                ICYEditable editable = block.findEditableByTabId(tabId);
                if(editable != null) {
                    return editable;
                }
            }
        } else {
            if(this instanceof CYEditBlock && ((CYEditBlock)this).getTabId() == tabId) {
                return (ICYEditable)this;
            }

            if(this instanceof ICYEditableGroup) {
                return this.findEditableByTabId(tabId);
            }
        }

        return null;
    }

    public void findAllEditable(List<ICYEditable> editables) {
        List children = this.getChildren();
        if(children != null && !children.isEmpty()) {
            for(int var5 = 0; var5 < children.size(); ++var5) {
                CYBlock block = (CYBlock)children.get(var5);
                block.findAllEditable(editables);
            }
        } else if(this instanceof CYEditBlock) {
            editables.add((ICYEditable)this);
        } else if(this instanceof ICYEditableGroup) {
            List edits = ((ICYEditableGroup)this).findAllEditable();
            if(edits != null) {
                editables.addAll(edits);
            }
        }

    }

    public void setParagraphStyle(CYParagraphStyle style) {
        this.mParagraphStyle = style;
    }

    public CYParagraphStyle getParagraphStyle() {
        return this.mParagraphStyle;
    }

    public void release() {
        List children = this.getChildren();
        if(children != null && !children.isEmpty()) {
            for(int i = 0; i < children.size(); ++i) {
                CYBlock block = (CYBlock)children.get(i);
                block.release();
            }
        }

    }

    public int getTextHeight(Paint paint) {
        return (int)(Math.ceil((double)(paint.descent() - paint.ascent())) + 0.5D);
    }

    public boolean isValid() {
        return true;
    }
}
