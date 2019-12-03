package com.vincent.projectanalysis.module.mapScene.practicemap;

import android.app.Activity;

import com.vincent.projectanalysis.module.mapScene.Director;
import com.vincent.projectanalysis.module.mapScene.GameMapScene;
import com.vincent.projectanalysis.module.mapScene.base.CLayer;
import com.vincent.projectanalysis.module.mapScene.base.CNode;
import com.vincent.projectanalysis.module.mapScene.render.AnchorSprite;

import java.util.List;

/**
 * Created by yangzc on 16/4/19.
 */
public class GameMapScene2 extends MapScene {

    private static final String TAG = "GameMapScene";

    public GameMapScene2(Activity gameFragment, Director director) {
        super(director);
    }


    private GameMapScene.GameMapClickListener mMapClickListener;

    public void setGameMapClickListener(GameMapScene.GameMapClickListener listener) {
        this.mMapClickListener = listener;
    }


    @Override
    public void onSceneStop() {
        super.onSceneStop();
        List<CNode> layers = getNodes();
        if (layers != null && layers.size() > 0) {
            for (int i = 0; i < layers.size(); i++) {
                CLayer layer = (CLayer) layers.get(i);
                if (layer != null && layer.getNodes() != null && layer.getNodes().size() > 0) {
                    for (int j = 0; j < layer.getNodes().size(); j++) {
                        CNode node = layer.getNodes().get(j);
                        if (node instanceof AnchorSprite) {
                            ((AnchorSprite) node).release();
                        }
                    }
                }
            }
        }
    }
}
