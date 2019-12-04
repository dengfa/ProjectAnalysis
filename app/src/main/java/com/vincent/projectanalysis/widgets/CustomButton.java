package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-12-04.
 * Des:
 */
public class CustomButton extends android.support.v7.widget.AppCompatTextView {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouchEvent = super.onTouchEvent(event);
        LogUtil.d("vincent", "CustomButton onTouchEvent - " + onTouchEvent);
        return onTouchEvent;
    }
}
