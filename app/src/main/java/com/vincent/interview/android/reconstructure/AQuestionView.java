package com.vincent.interview.android.reconstructure;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vincent.interview.android.reconstructure.base.AbsQuestionView;
import com.vincent.projectanalysis.R;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public class AQuestionView extends AbsQuestionView<QuestionData> {

    protected TextView mTvDesc;//question

    public AQuestionView(Context context) {
        super(context);
    }

    @Override
    public View createView() {
        View view = View.inflate(mContext, R.layout.item_question_a, null);
        mTvDesc = view.findViewById(R.id.tv_desc);
        return view;
    }

    public void displayT(QuestionData data) {
        mTvDesc.setText(data.desc + "- T");
    }

    public void displayS(QuestionData data) {
        mTvDesc.setText(data.desc + "- S");
    }
}
