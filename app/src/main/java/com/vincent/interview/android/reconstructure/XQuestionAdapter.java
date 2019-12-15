package com.vincent.interview.android.reconstructure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.vincent.interview.android.reconstructure.base.AbsQuestionAdapter;
import com.vincent.interview.android.reconstructure.base.AbsQuestionHolder;
import com.vincent.interview.android.reconstructure.base.AbsQuestionView;
import com.vincent.projectanalysis.R;


/**
 *
 */
public class XQuestionAdapter
        extends AbsQuestionAdapter<QuestionData, XQuestionAdapter.Holder> {

    public XQuestionAdapter(Context context) {
        super(context);
    }

    @Override
    public Holder onCreateHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_question, null);
        AbsQuestionView questionView = QuestionViewFactory.createQuestionView(mContext, viewType);
        return new Holder(view, questionView);
    }

    @Override
    public void onBindHolder(@NonNull Holder holder, int position) {
        mDisplayStrategy.display(mDatas.get(position), holder);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).type;
    }

    class Holder extends AbsQuestionHolder {
        public Holder(View convertView, AbsQuestionView questionView) {
            super(convertView);
            mTvType = convertView.findViewById(R.id.tv_type);
            ivTag = convertView.findViewById(R.id.iv_tag);
            container = convertView.findViewById(R.id.rl_container);
            mQuestionView = questionView;
            container.addView(questionView.createView());
        }
    }
}
