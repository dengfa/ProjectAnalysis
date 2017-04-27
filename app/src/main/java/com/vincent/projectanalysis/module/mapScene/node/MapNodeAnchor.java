package com.vincent.projectanalysis.module.mapScene.node;

/**
 * Created by wutong on 16/11/16.
 */
public class MapNodeAnchor extends MapNode {


    public String mSrc;
    public int    mOutborder;
    public int    mInborder;
    public String mBordercolor;
    public int    mCorner;

    public MapNodeAnchor(String id, int width, int height, String src, int outborder, int inborder, String
            borderColor, int corner) {
        super(id, width, height);

        mSrc = src;
        mOutborder = outborder;
        mInborder = inborder;
        mBordercolor = borderColor;
        mCorner = corner;
    }
}
