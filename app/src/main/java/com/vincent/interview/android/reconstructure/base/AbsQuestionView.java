package com.vincent.interview.android.reconstructure.base;

import android.content.Context;
import android.view.View;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public abstract class AbsQuestionView<T> {

    protected Context mContext;

    public AbsQuestionView(Context context) {
        mContext = context;
    }

    public abstract View createView();

    public abstract void displayT(T data);

    public abstract void displayS(T data);
}
