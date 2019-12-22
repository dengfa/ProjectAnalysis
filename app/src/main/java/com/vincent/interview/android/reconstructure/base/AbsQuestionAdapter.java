package com.vincent.interview.android.reconstructure.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class AbsQuestionAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected IDiasplayStrategy mDisplayStrategy;

    protected Context mContext;

    protected ArrayList<T> mDatas = new ArrayList<>();

    public AbsQuestionAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setDatas(ArrayList<T> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void setDisPlayStratege(IDiasplayStrategy disPlayStratege) {
        mDisplayStrategy = disPlayStratege;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindHolder(holder, position);
    }

    public abstract VH onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }
}