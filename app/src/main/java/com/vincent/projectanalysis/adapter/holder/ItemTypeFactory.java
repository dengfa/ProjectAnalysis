package com.vincent.projectanalysis.adapter.holder;

import android.content.Context;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.adapter.holder.base.BaseComponentVH;

/**
 * Created by dengfa on 2019-11-04.
 * Des:
 */
public class ItemTypeFactory {

    public static final int TYPE_NOEMAL = 1;

    public static BaseComponentVH createViewHolder(Context context, int type) {
        switch (type) {
            case TYPE_NOEMAL: {
                return new ComponentViewHolder(context,
                        View.inflate(context, R.layout.item_main_listview, null));
            }
            default:
                return new ComponentViewHolder(context,
                        View.inflate(context, R.layout.item_main_listview, null));
        }
    }
}
