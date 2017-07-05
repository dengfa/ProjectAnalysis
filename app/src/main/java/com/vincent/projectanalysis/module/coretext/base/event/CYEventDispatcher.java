//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.event;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class CYEventDispatcher {
    private List<CYLayoutEventListener> mLayoutListeners;

    public CYEventDispatcher() {
    }

    public void addLayoutEventListener(CYLayoutEventListener listener) {
        if(this.mLayoutListeners == null) {
            this.mLayoutListeners = new ArrayList();
        }

        if(!this.mLayoutListeners.contains(listener)) {
            this.mLayoutListeners.add(listener);
        }

    }

    public void removeLayoutEventListener(CYLayoutEventListener listener) {
        if(this.mLayoutListeners != null) {
            this.mLayoutListeners.remove(listener);
        }
    }

    public void requestLayout() {
        this.requestLayout(true);
    }

    public void requestLayout(boolean force) {
        if(this.mLayoutListeners != null && !this.mLayoutListeners.isEmpty()) {
            for(int i = 0; i < this.mLayoutListeners.size(); ++i) {
                CYLayoutEventListener listener = (CYLayoutEventListener)this.mLayoutListeners.get(i);
                listener.doLayout(force);
            }

        }
    }

    public void postInvalidate(Rect rect) {
        if(this.mLayoutListeners != null && !this.mLayoutListeners.isEmpty()) {
            for(int i = 0; i < this.mLayoutListeners.size(); ++i) {
                CYLayoutEventListener listener = (CYLayoutEventListener)this.mLayoutListeners.get(i);
                listener.onInvalidate(rect);
            }

        }
    }
}
