package com.vincent.projectanalysis.mapScene.base;


import com.vincent.projectanalysis.mapScene.node.MapNodeLayer;
import com.vincent.projectanalysis.mapScene.style.MapStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/4/21.
 */
public class CMap {

    public String mMapName;
    public int mLevelCnt = 1;

    public String              mBackGround;
    private List<MapStyle>     mStyles;
    private List<MapNodeLayer> mLayers;

    public void addStyle(MapStyle style) {
        if (mStyles == null)
            mStyles = new ArrayList<MapStyle>();
        mStyles.add(style);
    }

    public List<MapStyle> getStyles() {
        return mStyles;
    }

    public void addLayer(MapNodeLayer layer) {
        if (mLayers == null)
            mLayers = new ArrayList<MapNodeLayer>();
        mLayers.add(layer);
//        sort();
    }

    public List<MapNodeLayer> getLayers() {
        return mLayers;
    }

//    private void sort() {
//        if (mLayers != null) {
//            Collections.sort(mLayers, new Comparator<MapNodeLayer>() {
//                @Override
//                public int compare(MapNodeLayer lhs, MapNodeLayer rhs) {
//                    return lhs.getZIndex() - rhs.getZIndex();
//                }
//            });
//        }
//    }
}
