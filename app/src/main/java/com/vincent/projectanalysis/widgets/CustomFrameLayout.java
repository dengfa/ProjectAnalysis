package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-12-04.
 * Des:
 */
public class CustomFrameLayout extends FrameLayout {
    public CustomFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
        LogUtil.d("vincent", "CustomFrameLayout dispatchTouchEvent - " + dispatchTouchEvent);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouchEvent = super.onTouchEvent(event);
        LogUtil.d("vincent", "CustomFrameLayout onTouchEvent - " + onTouchEvent);
        return onTouchEvent;
    }
}
