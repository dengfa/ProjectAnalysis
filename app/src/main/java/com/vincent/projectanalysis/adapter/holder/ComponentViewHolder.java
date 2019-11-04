package com.vincent.projectanalysis.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.adapter.holder.base.BaseComponentVH;

public class ComponentViewHolder extends BaseComponentVH<String> {

    public TextView mTab;

    public ComponentViewHolder(Context context, View itemView) {
        super(context, itemView);
        mTab = itemView.findViewById(R.id.tv_item);
    }

    @Override
    public void onBindView(String data) {
        mTab.setText(data);
        mTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick();
                }
            }
        });
    }
}