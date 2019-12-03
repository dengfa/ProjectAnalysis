package com.vincent.projectanalysis.module.mapScene;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.bean.OnlineMapDetailInfo;
import com.vincent.projectanalysis.module.mapScene.base.CNode;
import com.vincent.projectanalysis.module.mapScene.base.CScene;
import com.vincent.projectanalysis.module.mapScene.base.RenderView;
import com.vincent.projectanalysis.module.mapScene.practicemap.GameMapScene2;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.HashMap;

public class MapSceneActivity extends AppCompatActivity {

    private GameMapScene2 mGameMapScene;
    private static HashMap<String, String> MAPS = new HashMap<String, String>();

    static {
        MAPS.put("1", "map/iceworld/iceworld.xml");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_scene);

        mDirector = new Director(this);
        mDirector.setRefreshDelay(20);

        mDirector.start();

        RenderView renderView = (RenderView) findViewById(R.id.rv_game);
        setRenderView(renderView);

        final int screenWidth = UIUtils.getWindowWidth(MapSceneActivity.this);
        final int screenHeight = UIUtils.getWindowHeight(MapSceneActivity.this);
        mGameMapScene = new  GameMapScene2(MapSceneActivity.this, mDirector);
        showScene(mGameMapScene);
        mGameMapScene.setGameMapClickListener(mMapClickListener);
        String path = MAPS.get("1");
        LogUtil.d("GameMapFragment", "load map, path: " + path);
        mGameMapScene.loadAssetPath(path, screenWidth, screenHeight);
    }

    private GameMapScene.GameMapClickListener mMapClickListener = new GameMapScene.GameMapClickListener() {

        @Override
        public void onNodeClick(OnlineMapDetailInfo.OnlineLevelInfo levelInfo, CNode node) {
            LogUtil.d("GameMapFragment", "onNodeClick: ");
        }

        @Override
        public void onBagClick(OnlineMapDetailInfo.OnlineBagInfo bagInfo, CNode node) {
            LogUtil.d("GameMapFragment", "lonBagClick: ");
        }

        @Override
        public void onMedalClick(OnlineMapDetailInfo mapDetailIf, OnlineMapDetailInfo.OnlineBagInfo bagInfo) {
            LogUtil.d("GameMapFragment", "onMedalClick: ");
        }
    };

    private Director mDirector;

    public Director getDirector() {
        return mDirector;
    }

    public void showScene(CScene scene) {
        if (mDirector != null) {
            mDirector.showScene(scene);
        }
    }

    public void setRenderView(RenderView renderView) {
        if (mDirector != null) {
            mDirector.setRenderView(renderView);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mDirector != null) {
            mDirector.resumeScene();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDirector != null) {
            mDirector.pauseScene();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (mDirector != null) {
            mDirector.stop();
            mDirector.release(); //导致崩溃
        }*/
        /*Caused by: java.lang.ClassCastException: com.vincent.projectanalysis.module.mapScene.base.
                CTextNode cannot be cast to com.vincent.projectanalysis.module.mapScene.base.CLayer
        at com.vincent.projectanalysis.module.mapScene.GameMapScene.onSceneStop(GameMapScene.java:521)*/
    }
}
