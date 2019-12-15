package com.vincent.interview.android.reconstructure;

import android.view.View;

import com.vincent.interview.android.reconstructure.base.AbsQuestionHolder;
import com.vincent.interview.android.reconstructure.base.IDiasplayStrategy;
import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public class SDisplayStratege implements IDiasplayStrategy<QuestionData, AbsQuestionHolder> {

    @Override
    public void display(final QuestionData data, AbsQuestionHolder holder) {
        holder.mTvType.setText("S -");
        holder.mQuestionView.displayT(data);
        holder.ivTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("vincent", data.desc);
            }
        });
    }
}
