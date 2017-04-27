package com.vincent.projectanalysis.module.mapScene.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/4/21.
 */
public class MapNodeSprite extends MapNode {

    public String              mSrc;
    public String              mUnableSrc;
    public String              mOpenSrc;
    public String              mNextBagId;
    public List<MapNodeBlock>  mBlocks;
    public List<MapNodeText>   mTexts;
    public List<MapNodeSprite> mSprites;
    public List<MapNodeStar>   mStars;

    public MapNodeSprite(String id, int width, int height) {
        super(id, width, height);
    }

    public void addMapBlock(MapNodeBlock block) {
        if (mBlocks == null)
            mBlocks = new ArrayList<MapNodeBlock>();
        mBlocks.add(block);
    }

    public List<MapNodeBlock> getBlocks() {
        return mBlocks;
    }

    public void addMapText(MapNodeText text) {
        if (mTexts == null)
            mTexts = new ArrayList<MapNodeText>();
        mTexts.add(text);
    }

    public List<MapNodeText> getTexts() {
        return mTexts;
    }

    public void addSprite(MapNodeSprite sprite) {
        if (mSprites == null)
            mSprites = new ArrayList<MapNodeSprite>();
        mSprites.add(sprite);
    }

    public List<MapNodeSprite> getSprites() {
        return mSprites;
    }

    public void addStar(MapNodeStar star) {
        if (mStars == null)
            mStars = new ArrayList<MapNodeStar>();
        mStars.add(star);
    }

    public List<MapNodeStar> getStars() {
        return mStars;
    }
}
