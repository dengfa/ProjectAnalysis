package com.vincent.interview.android.reconstructure.normal;

import android.view.View;
import android.widget.TextView;

import com.vincent.interview.android.reconstructure.base.AbsQuestionHolder;
import com.vincent.projectanalysis.R;

public class NormalHolder extends AbsQuestionHolder {

    private final TextView mTvType;
    private final TextView mTvDesc;

    public NormalHolder(View convertView) {
        super(convertView);
        mTvType = convertView.findViewById(R.id.tv_type);
        mTvDesc = convertView.findViewById(R.id.tv_desc);
    }
}