package com.vincent.interview.android.reconstructure;

import android.view.View;

import com.vincent.interview.android.reconstructure.base.AbsQuestionHolder;
import com.vincent.interview.android.reconstructure.base.IDiasplayStrategy;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public class TDisplayStratege implements IDiasplayStrategy<QuestionData, AbsQuestionHolder> {

    @Override
    public void display(QuestionData data, AbsQuestionHolder holder) {
        holder.mTvType.setText("T -");
        holder.ivTag.setVisibility(View.GONE);
        holder.mQuestionView.displayT(data);
    }
}
