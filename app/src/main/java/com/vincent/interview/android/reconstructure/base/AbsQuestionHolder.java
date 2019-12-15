package com.vincent.interview.android.reconstructure.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class AbsQuestionHolder extends RecyclerView.ViewHolder {

    public TextView        mTvType;//index
    public RelativeLayout  container;
    public View            ivTag;
    public AbsQuestionView mQuestionView;

    public AbsQuestionHolder(View convertView) {
        super(convertView);
    }
}