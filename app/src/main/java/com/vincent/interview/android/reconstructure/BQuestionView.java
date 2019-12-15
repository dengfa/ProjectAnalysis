package com.vincent.interview.android.reconstructure;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vincent.interview.android.reconstructure.base.AbsQuestionView;
import com.vincent.projectanalysis.R;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public class BQuestionView extends AbsQuestionView<QuestionData> {

    protected TextView  mTvDesc;//question
    protected ImageView mIv;

    public BQuestionView(Context context) {
        super(context);
    }

    @Override
    public View createView() {
        View view = View.inflate(mContext, R.layout.item_question_b, null);
        mTvDesc = view.findViewById(R.id.tv_desc);
        mIv = view.findViewById(R.id.iv_image);
        return view;
    }

    public void displayT(QuestionData data) {
        mTvDesc.setText(data.desc + "- T");
        mIv.setImageResource(R.drawable.ic_launcher);
    }

    public void displayS(QuestionData data) {
        mTvDesc.setText(data.desc + "- S");
        mIv.setImageResource(R.drawable.darth);
    }
}
