package com.vincent.projectanalysis.adapter.holder.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dengfa on 2019-11-04.
 * Des:
 */
public abstract class BaseComponentVH<T> extends RecyclerView.ViewHolder {

    protected Context             mContext;
    protected OnItemClickListener mOnItemClickListener;

    public BaseComponentVH(Context context, View itemView) {
        super(itemView);
        mContext = context;
    }

    public abstract void onBindView(T data);

    public interface OnItemClickListener {
        void onItemClick( );
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
