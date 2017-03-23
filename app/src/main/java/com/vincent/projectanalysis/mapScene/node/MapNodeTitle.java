package com.vincent.projectanalysis.mapScene.node;

/**
 * Created by yangzc on 16/4/27.
 */
public class MapNodeTitle extends MapNode {


    public String mBackGround = "";
    public String mTitle;
    public int    mTitleFontSize;
    public String mTitleColor;

    public int mSubTitleFontSize;

    public String mSubTitleLeft;
    public String mSubTitleLeftColor;
    public String mSubTitleRight;
    public String mSubTitleRightColor;

    // 新增
    public int mXoffset;
    public int mYCenter;


    public MapNodeTitle(String id, int width, int height) {
        super(id, width, height);
    }
}
