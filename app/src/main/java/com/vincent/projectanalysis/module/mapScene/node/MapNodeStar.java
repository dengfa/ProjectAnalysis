package com.vincent.projectanalysis.module.mapScene.node;

import android.text.TextUtils;

/**
 * Created by wutong on 16/11/16.
 */
public class MapNodeStar extends MapNode {

    public String mCount;
    public String mStyle;
    public MapNodeStar(String id, int width, int height) {
        super(id, width, height);
    }
    public int getCount() {
        return TextUtils.isEmpty(mCount) ? 0 : Integer.parseInt(mCount);
    }
}
