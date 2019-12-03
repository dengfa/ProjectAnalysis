package com.vincent.projectanalysis.module.mapScene.practicemap;

import com.vincent.projectanalysis.bean.OnlineMapDetailInfo;
import com.vincent.projectanalysis.module.mapScene.base.CNode;

public interface GameMapClickListener {
    /**
     * 关卡点击事件
     *
     * @param levelInfo
     * @param node
     */
    void onNodeClick(OnlineMapDetailInfo.OnlineLevelInfo levelInfo, CNode node);

    /**
     * 背包点击事件
     *
     * @param bagInfo
     * @param node
     */
    void onBagClick(OnlineMapDetailInfo.OnlineBagInfo bagInfo, CNode node);

    /**
     * 勋章点击
     *
     * @param mapDetailIf
     */
    void onMedalClick(OnlineMapDetailInfo mapDetailIf, OnlineMapDetailInfo.OnlineBagInfo bagInfo);
}