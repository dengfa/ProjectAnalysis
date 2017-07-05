//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.vincent.projectanalysis.module.coretext.base.blocks.CYBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYPageBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.ICYEditable;
import com.vincent.projectanalysis.module.coretext.base.blocks.ICYEditableGroup;
import com.vincent.projectanalysis.module.coretext.base.event.CYFocusEventListener;
import com.vincent.projectanalysis.module.coretext.base.event.CYLayoutEventListener;
import com.vincent.projectanalysis.module.coretext.base.utils.CYBlockUtils;

import java.util.ArrayList;
import java.util.List;

public class CYPageView extends View implements CYLayoutEventListener {
    public static int FOCUS_TAB_ID = -1;
    private CYPageBlock mPageBlock;
    private CYBlock     mFocusBlock;
    private ICYEditable mFocusEditable;
    private TextEnv     mTextEnv;
    private CYFocusEventListener mFocusEventListener = null;

    public CYPageView(Context context) {
        super(context);
        this.init();
    }

    public CYPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CYPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.mPageBlock != null) {
            canvas.save();
            canvas.translate((float)this.mPageBlock.getPaddingLeft(), (float)this.mPageBlock.getPaddingTop());
            this.mPageBlock.draw(canvas);
            canvas.restore();
        }

    }

    public void setPageBlock(TextEnv textEnv, CYPageBlock pageBlock) {
        this.mTextEnv = textEnv;
        this.mPageBlock = pageBlock;
    }

    public CYPageBlock getPageBlock() {
        return this.mPageBlock;
    }

    public ICYEditable findEditableByTabId(int tabId) {
        return this.mPageBlock != null?this.mPageBlock.findEditableByTabId(tabId):null;
    }

    public List<ICYEditable> getEditableList() {
        ArrayList editableList = new ArrayList();
        if(this.mPageBlock != null) {
            this.mPageBlock.findAllEditable(editableList);
        }

        return editableList;
    }

    public void clearFocus() {
        FOCUS_TAB_ID = -1;
        if(this.mFocusEditable != null) {
            this.mFocusEditable.setFocus(false);
            this.notifyFocusChange(false, this.mFocusEditable);
        }

        this.mFocusEditable = null;
        this.mFocusBlock = null;
        this.postInvalidate();
    }

    public void setText(int tabId, String text) {
        ICYEditable editable = this.findEditableByTabId(tabId);
        if(editable != null) {
            editable.setText(text);
        }

    }

    public String getText(int tabId) {
        ICYEditable editable = this.findEditableByTabId(tabId);
        return editable != null?editable.getText():null;
    }

    public void setFocus(int tabId) {
        ICYEditable editable = this.findEditableByTabId(tabId);
        if(editable != null) {
            if(this.mFocusEditable != null) {
                this.mFocusEditable.setFocus(false);
            }

            editable.setFocus(true);
            this.mFocusEditable = editable;
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        if(this.mPageBlock != null && this.mTextEnv != null) {
            int action = MotionEventCompat.getActionMasked(event);
            int x = (int)event.getX() - this.mPageBlock.getPaddingLeft();
            int y = (int)event.getY() - this.mPageBlock.getPaddingTop();
            boolean handle = false;
            switch(action) {
            case 0:
                this.onTouchDown(event);
                if(this.mFocusBlock != null) {
                    handle = this.mFocusBlock.onTouchEvent(action, (float)(x - this.mFocusBlock.getX()), (float)(y - this.mFocusBlock.getLineY()));
                } else {
                    this.setPressed(true);
                }
                break;
            case 1:
                if(this.mFocusBlock != null) {
                    handle = this.mFocusBlock.onTouchEvent(action, (float)(x - this.mFocusBlock.getX()), (float)(y - this.mFocusBlock.getLineY()));
                } else {
                    this.setPressed(false);
                    this.performClick();
                }
                break;
            case 2:
                if(this.mFocusBlock != null) {
                    handle = this.mFocusBlock.onTouchEvent(action, (float)(x - this.mFocusBlock.getX()), (float)(y - this.mFocusBlock.getLineY()));
                }
                break;
            case 3:
            case 4:
                this.setPressed(false);
                if(this.mFocusBlock != null) {
                    handle = this.mFocusBlock.onTouchEvent(action, (float)(x - this.mFocusBlock.getX()), (float)(y - this.mFocusBlock.getLineY()));
                }
            }

            return !handle?super.onTouchEvent(event):true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private void onTouchDown(MotionEvent event) {
        this.handleFocusEvent(event);
    }

    private void handleFocusEvent(MotionEvent event) {
        int x = (int)event.getX() - this.mPageBlock.getPaddingLeft();
        int y = (int)event.getY() - this.mPageBlock.getPaddingTop();
        CYBlock focusBlock = CYBlockUtils.findBlockByPosition(this.mPageBlock, x, y);
        this.mFocusBlock = focusBlock;
        ICYEditable focusEditable = null;
        ICYEditable editable;
        if(focusBlock != null) {
            if(focusBlock instanceof ICYEditable) {
                focusEditable = (ICYEditable)focusBlock;
            } else if(focusBlock instanceof ICYEditableGroup) {
                editable = ((ICYEditableGroup)focusBlock).findEditable((float)(x - focusBlock.getX()), (float)(y - focusBlock.getLineY()));
                if(editable != null) {
                    focusEditable = editable;
                }
            }
        }

        if(focusEditable != null) {
            this.notifyEditableClick(focusEditable);
        }

        if(focusEditable != null && focusEditable.isFocusable() && focusEditable != this.mFocusEditable) {
            if(this.mFocusEditable != null) {
                this.mFocusEditable.setFocus(false);
                if(this.mFocusEditable instanceof ICYEditable) {
                    this.notifyFocusChange(false, this.mFocusEditable);
                } else if(this.mFocusEditable instanceof ICYEditableGroup) {
                    editable = ((ICYEditableGroup)this.mFocusEditable).getFocusEditable();
                    if(editable != null) {
                        this.notifyFocusChange(false, editable);
                    }
                }
            }

            this.notifyFocusChange(true, focusEditable);
        }

    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.release();
    }

    protected void release() {
        if(this.mTextEnv != null) {
            this.mTextEnv.getEventDispatcher().removeLayoutEventListener(this);
        }

        if(this.mFocusEditable != null) {
            this.mFocusEditable.setFocus(false);
        }

        if(this.mPageBlock != null) {
            this.mPageBlock.release();
        }

    }

    public void measure() {
        if(this.mPageBlock != null) {
            this.mPageBlock.onMeasure();
        }

    }

    public void doLayout(boolean force) {
        this.measure();
    }

    public void onInvalidate(Rect rect) {
        if(rect != null) {
            this.postInvalidate(rect.left, rect.top, rect.right, rect.bottom);
        } else {
            this.postInvalidate();
        }

    }

    public void setFocusEventListener(CYFocusEventListener listener) {
        this.mFocusEventListener = listener;
    }

    private void notifyEditableClick(ICYEditable editable) {
        if(this.mFocusEventListener != null && editable != null) {
            this.mFocusEventListener.onClick(editable.getTabId());
        }

    }

    private void notifyFocusChange(boolean hasFocus, ICYEditable editable) {
        if(editable != null) {
            if(hasFocus) {
                this.mFocusEditable = editable;
            }

            editable.setFocus(hasFocus);
            if(this.mFocusEventListener != null) {
                this.mFocusEventListener.onFocusChange(hasFocus, editable.getTabId());
            }

        }
    }
}
