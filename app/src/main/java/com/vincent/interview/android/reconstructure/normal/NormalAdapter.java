package com.vincent.interview.android.reconstructure.normal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vincent.interview.android.reconstructure.QuestionData;
import com.vincent.projectanalysis.R;

import java.util.ArrayList;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public class NormalAdapter extends RecyclerView.Adapter<NormalHolder> {

    protected Context mContext;

    protected ArrayList<QuestionData> mDatas = new ArrayList<>();

    public NormalAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setDatas(ArrayList<QuestionData> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NormalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_question_a, null);
        return new NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NormalHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
