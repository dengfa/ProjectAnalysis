package com.vincent.projectanalysis.module.mapScene;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.text.TextUtils;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.bean.OnlineMapDetailInfo;
import com.vincent.projectanalysis.bean.OnlineMapListInfo;
import com.vincent.projectanalysis.module.mapScene.base.CLayer;
import com.vincent.projectanalysis.module.mapScene.base.CNode;
import com.vincent.projectanalysis.module.mapScene.base.CScrollLayer;
import com.vincent.projectanalysis.module.mapScene.base.texture.CTexture;
import com.vincent.projectanalysis.module.mapScene.node.MapNode;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeSprite;
import com.vincent.projectanalysis.module.mapScene.render.AnchorSprite;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.MathUtils;

import java.util.List;

/**
 * Created by yangzc on 16/4/19.
 */
public class GameMapScene extends MapScene {

    private static final String TAG = "GameMapScene";
    private Activity mGameFragment;
    private int mCurrentLevel = 1;
    private int mMapKeys      = 0;
    private Bitmap mDefaultUserIcon;

    public GameMapScene(Activity gameFragment, Director director) {
        super(director);
        this.mGameFragment = gameFragment;
        mDefaultUserIcon = BitmapFactory.decodeResource(gameFragment.getResources()
                , R.drawable.default_student);
    }

    @Override
    public void load(String xml, int screenWidth, int screenHeight) {
        super.load(xml, screenWidth, screenHeight);
        for (int i = 0; i < getLevelCount(); i++) {
            setLevelStatus("level_" + (i + 1), STATUS_LEVEL_LOCKED, false);
            setBoxStatus("bag_" + (i + 1), STATUS_BAG_UNABLE);
        }
        //初始化所有关卡
        setLevelStatus("level_1", STATUS_LEVEL_OPEN, false);
        setAnchor("level_1");
        setTry("level_1", false);
        mCurrentLevel = 1;
    }

    /**
     * 下一关卡
     */
    public void nextLevel(boolean isFromOpenBag, boolean lockEnable) {
        if (mMapDetailInfo.mMapInfo.mStatus == OnlineMapListInfo.OnlineMapInfo.STATUS_UNOPEN) {
            if (isFromOpenBag) {
                setBagStatus(mCurrentLevel + "", STATUS_BAG_OPENED);
            }
            setLevelStatus("level_" + (mCurrentLevel + 1), STATUS_LEVEL_LOCKED, lockEnable);
            return;
        }

        if (mCurrentLevel + 1 > getLevelCount()) {
            syncMedalState();
            return;
        }

        if (isFromOpenBag) {
            setBagStatus(mCurrentLevel + "", STATUS_BAG_OPENED);
            setAnchor("level_" + (mCurrentLevel + 1), true);
            setLevelStatus("level_" + (mCurrentLevel + 1), STATUS_LEVEL_OPEN, lockEnable);
            mCurrentLevel = mCurrentLevel + 1;
        } else {

            StateSprite boxSprite = (StateSprite) findNodeById("bag_" + mCurrentLevel);
            LogUtil.d("yangzc", "StateSprite" + (boxSprite == null));
            if (boxSprite != null) {
                //存在宝箱
                setBagStatus(mCurrentLevel + "", STATUS_BAG_ENABLE);
            } else {
                setBagStatus(mCurrentLevel + "", STATUS_BAG_OPENED);
                setAnchor("level_" + (mCurrentLevel + 1), true);
                setLevelStatus("level_" + (mCurrentLevel + 1), STATUS_LEVEL_OPEN, lockEnable);
                mCurrentLevel = mCurrentLevel + 1;
            }
        }
        syncMedalState();
    }

    public OnlineMapDetailInfo.OnlineLevelInfo getCurrentLevelInfo() {
        int curIndex = mCurrentLevel - 1;
        if (mMapDetailInfo.mLevelIfs != null && !mMapDetailInfo.mLevelIfs.isEmpty()
                && curIndex > 0 && curIndex < mMapDetailInfo.mLevelIfs.size()) {
            return mMapDetailInfo.mLevelIfs.get(mCurrentLevel - 1);
        }
        return null;
    }

    private void syncMedalState() {
        if (mMapDetailInfo != null) {
            List<OnlineMapDetailInfo.OnlineLevelInfo> listItems = mMapDetailInfo.mLevelIfs;
            OnlineMapDetailInfo.OnlineLevelInfo endLevelItem = listItems.get(listItems.size() - 1);
            if (endLevelItem.mStatus == OnlineMapDetailInfo.OnlineLevelInfo.STATUS_OPENED
                    && endLevelItem.mFinishNum == endLevelItem.mMissionNum) {
                setBagStatus(getLevelCount() + "", STATUS_BAG_OPENED);
            }
        }
    }

    public int getCurrentLevelIndex() {
        return this.mCurrentLevel;
    }

    /**
     * 设置宝箱状态
     *
     * @param index
     * @param status
     */
    public void setBagStatus(String index, int status) {
        setBoxStatus("bag_" + index, status);
    }

    /**
     * 设置关卡好友位置
     *
     * @param levelId
     * @param isOpened
     * @param url
     */
    public void setLevelFriend(String levelId, boolean isOpened, String url) {
        if (!TextUtils.isEmpty(url)) {
            AnchorSprite friendBg = (AnchorSprite) findNodeById("friend_" + levelId);
            if (friendBg != null) {
                friendBg.setVisible(true);
                CTexture texture = friendBg.getTexture();
                texture.setTexture(loadBitmap("", "res:map/bg_friend.png", 0, 0));
                texture.setViewSize(texture.getWidth(), texture.getHeight());
                friendBg.setImageUrl(url);
            }
        }
    }

    @Override
    protected void onAddNode(CScrollLayer layer, CNode node) {
        super.onAddNode(layer, node);
        if (node != null) {
            String nodeId = node.getId();
            if (!TextUtils.isEmpty(nodeId) && nodeId.matches("^level_([0-9]+)$")) {
                AnchorSprite sprite = (AnchorSprite) findNodeById("friend_" + node.getId());
                if (sprite == null) {
                    CTexture texture = CTexture.create(getDirector(), null);
                    texture.setViewSize(getNumber(34), getNumber(42));
                    sprite = AnchorSprite.create(getDirector(), texture, "");

                    Point target = new Point(node.getPosition().x + (node.getWidth() - sprite.getWidth()) / 2
                            , node.getPosition().y - getNumber(37));
                    sprite.setId("friend_" + node.getId());
                    sprite.setPosition(target);

                    layer.addNode(sprite, 9);
                }
                sprite.setBorderColor(0xff44cdfc);
                sprite.setVisible(false);
            }
        }
    }

    @Override
    public void setAnchor(String fromLevelId, String toLevelId) {
        super.setAnchor(fromLevelId, toLevelId);

        if (mFriendAnchorSprite != null) {
            mFriendAnchorSprite.setVisible(true);
        }
        AnchorSprite sprite = (AnchorSprite) findNodeById("friend_" + toLevelId);
        if (sprite != null && sprite.isVisible()) {
            mFriendAnchorSprite = sprite;
            sprite.setVisible(false);
        }
    }

    private AnchorSprite mFriendAnchorSprite = null;

    @Override
    public void setAnchor(String levelId) {
        super.setAnchor(levelId);
        if (mFriendAnchorSprite != null) {
            mFriendAnchorSprite.setVisible(true);
        }
        AnchorSprite sprite = (AnchorSprite) findNodeById("friend_" + levelId);
        if (sprite != null && sprite.isVisible()) {
            mFriendAnchorSprite = sprite;
            sprite.setVisible(false);
        }
    }

    @Override
    protected StateSprite createSprite(MapNodeSprite spriteNode, MapNode attach, CTexture texture) {
        if (spriteNode != null && "anchor".equals(spriteNode.getId())) {
            //UserItem userItem = Utils.getLoginUserItem();
            //AnchorSprite sprite = AnchorSprite.create(getDirector(), texture, userItem.headPhoto);
            AnchorSprite sprite = AnchorSprite.create(getDirector(), texture,
                    "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=9d3833093f292df588c3ab158c305ce2/d788d43f8794a4c274c8110d0bf41bd5ad6e3928.jpg");
            return sprite;
        } else {
            return super.createSprite(spriteNode, attach, texture);
        }
    }

    /**
     * 获得最后的一个宝箱
     *
     * @return
     */
    public OnlineMapDetailInfo.OnlineBagInfo getLastBag() {
        if (mMapDetailInfo.mBagIfs != null) {
            OnlineMapDetailInfo.OnlineBagInfo lastBagInfo = null;
            int index = 0;
            for (int i = 0; i < mMapDetailInfo.mBagIfs.size(); i++) {
                OnlineMapDetailInfo.OnlineBagInfo bagInfo = mMapDetailInfo.mBagIfs.get(i);
                if (index < MathUtils.valueOfInt(bagInfo.mIndex)) {
                    index = MathUtils.valueOfInt(bagInfo.mIndex);
                    lastBagInfo = bagInfo;
                }
            }
            return lastBagInfo;
        }
        return null;
    }

    public void setMapKeys(int mapKeys) {
        this.mMapKeys = mapKeys;
    }

    public int getMapKeys() {
        return mMapKeys;
    }

    private OnlineMapDetailInfo mMapDetailInfo;

    public void onGetMapDetailInfo(OnlineMapDetailInfo mapDetailInfo) {
        this.mMapDetailInfo = mapDetailInfo;
        if (mapDetailInfo != null && mapDetailInfo.mLevelIfs != null) {
            String mTryLevelId = "";
            mCurrentLevel = mapDetailInfo.mCurrentIndex;
            //初始化mapKey
            setMapKeys(mapDetailInfo.mMapKeys);
            String anchorLevelIndex = "1";
            for (int i = 0; i < mapDetailInfo.mLevelIfs.size(); i++) {
                OnlineMapDetailInfo.OnlineLevelInfo levelItem = mapDetailInfo.mLevelIfs.get(i);
                String levelId = "level_" + levelItem.mIndex;

                setBlockInfo(levelId, levelItem.mLevelName,
                        levelItem.mFinishNum + "/" + levelItem.mMissionNum, true);

                if (levelItem.mIsDemo) {
                    mTryLevelId = levelId;
                }

                switch (levelItem.mStatus) {
                    case OnlineMapDetailInfo.OnlineLevelInfo.STATUS_LOCK: {
                        setLevelStatus(levelId, STATUS_LEVEL_LOCKED, false);
                        break;
                    }
                    case OnlineMapDetailInfo.OnlineLevelInfo.STATUS_DISABLE: {
                        setLevelStatus(levelId, STATUS_LEVEL_UNLOCK, false);
                        break;
                    }
                    case OnlineMapDetailInfo.OnlineLevelInfo.STATUS_OPENED: {
                        anchorLevelIndex = levelItem.mIndex;
                        setLevelStatus(levelId, STATUS_LEVEL_OPEN, false);
                        break;
                    }
                }

                //update friends
                AnchorSprite anchorSprite = (AnchorSprite) findNodeById("friend_" + levelId);
                if (anchorSprite != null) {
                    anchorSprite.setVisible(false);
                }

                setLevelFriend(levelId, levelItem.mStatus == OnlineMapDetailInfo
                        .OnlineLevelInfo.STATUS_OPENED, levelItem.mFriendHeadPhoto);
            }

            if (mapDetailInfo.mBagIfs != null) {
                for (int i = 0; i < mapDetailInfo.mBagIfs.size(); i++) {
                    OnlineMapDetailInfo.OnlineBagInfo bagInfo = mapDetailInfo.mBagIfs.get(i);
                    switch (bagInfo.mStatus) {
                        case OnlineMapDetailInfo.OnlineBagInfo.STATUS_LOCK: {
                            setBoxStatus("bag_" + bagInfo.mIndex, STATUS_BAG_UNABLE);
                            break;
                        }
                        case OnlineMapDetailInfo.OnlineBagInfo.STATUS_CLOSE: {
                            setBoxStatus("bag_" + bagInfo.mIndex, STATUS_BAG_ENABLE);
                            break;
                        }
                        case OnlineMapDetailInfo.OnlineBagInfo.STATUS_OPENED: {
                            setBoxStatus("bag_" + bagInfo.mIndex, STATUS_BAG_OPENED);
                            break;
                        }
                    }
                }
            }

            boolean lockEnable = getMapKeys() > 0;
            if (lockEnable) {
                int index = getFirstLockLevelIndex();
                StateSprite lockSprite = (StateSprite) findNodeById("level_" + index + "_lock");
                if (lockSprite != null) {
                    lockSprite.setStatus(StateSprite.STATUS_UNABLE);
                }
            }

            if (mapDetailInfo.mMapInfo != null) {
                setTitle(mapDetailInfo.mMapInfo.mMapName, mapDetailInfo.mMapInfo.mFinishMission + ""
                        , "/" + mapDetailInfo.mMapInfo.mMissionNum + "");
            }

            if (!TextUtils.isEmpty(anchorLevelIndex)) {
                setAnchor("level_" + anchorLevelIndex);
            }

            if (!TextUtils.isEmpty(mTryLevelId)) {
                setTry(mTryLevelId, true);
            }
            syncMedalState();
        }
    }

    /**
     * 获得第一个锁的索引
     *
     * @return
     */
    public int getFirstLockLevelIndex() {
        int levelIndex = Integer.MAX_VALUE;
        if (mMapDetailInfo != null && mMapDetailInfo.mLevelIfs != null) {
            for (int i = 0; i < mMapDetailInfo.mLevelIfs.size(); i++) {
                OnlineMapDetailInfo.OnlineLevelInfo levelItem = mMapDetailInfo.mLevelIfs.get(i);
                if (levelItem.mStatus == OnlineMapDetailInfo.OnlineBagInfo.STATUS_LOCK) {
                    int index = MathUtils.valueOfInt(levelItem.mIndex);
                    if (index < levelIndex) {
                        levelIndex = index;
                    }
                }
            }
        }
        return levelIndex;
    }

    private CNode.OnNodeClickListener mNodeClickListener = new CNode.OnNodeClickListener() {

        @Override
        public void onClick(CNode node) {
            String id = node.getId();
            LogUtil.d("GameMapScene", "OnNodeClick - NodeId: " + id);
            if (mMapDetailInfo == null || TextUtils.isEmpty(id)
                    || mMapDetailInfo.mLevelIfs == null)
                return;

            //关卡
            if (id.startsWith("level")) {
                for (int i = 0; i < mMapDetailInfo.mLevelIfs.size(); i++) {
                    OnlineMapDetailInfo.OnlineLevelInfo levelItem
                            = mMapDetailInfo.mLevelIfs.get(i);

                    if (levelItem.mIndex == null)
                        continue;

                    if (levelItem.mIndex.equals(id.replace("level_", ""))) {
                        onLevelClick(levelItem, node);
                        break;
                    }
                }
            } else if (id.startsWith("bag")) {//宝箱
                OnlineMapDetailInfo.OnlineBagInfo lastBagInfo = mMapDetailInfo.mBagIfs.get(mMapDetailInfo.mBagIfs.size() - 1);
                if (id.equalsIgnoreCase("bag_" + getLevelCount())) {
                    onMedalClick(lastBagInfo);
                    return;
                }

                if (mMapDetailInfo.mBagIfs != null) {
                    for (int i = 0; i < mMapDetailInfo.mBagIfs.size(); i++) {
                        OnlineMapDetailInfo.OnlineBagInfo bagInfo
                                = mMapDetailInfo.mBagIfs.get(i);

                        if (bagInfo.mIndex == null)
                            continue;

                        if (bagInfo.mIndex.equals(id.replace("bag_", ""))) {
                            onBagClick(bagInfo, node);
                            break;
                        }
                    }
                }
            }
        }
    };

    @Override
    public void setBoxStatus(String bagId, int bagStatus) {
        super.setBoxStatus(bagId, bagStatus);
        StateSprite boxSprite = (StateSprite) findNodeById(bagId);
        if (boxSprite != null) {
            boxSprite.setOnNodeClickListener(mNodeClickListener);
        }
    }

    /**
     * 关卡点击
     *
     * @param levelInfo
     * @param node
     */
    private void onLevelClick(OnlineMapDetailInfo.OnlineLevelInfo levelInfo, CNode node) {
        if (mMapClickListener != null) {
            mMapClickListener.onNodeClick(levelInfo, node);
        }
    }

    /**
     * 背包点击
     *
     * @param bagInfo
     * @param node
     */
    private void onBagClick(OnlineMapDetailInfo.OnlineBagInfo bagInfo, CNode node) {
        if (mMapClickListener != null) {
            mMapClickListener.onBagClick(bagInfo, node);
        }
    }

    /**
     * 奖牌点击
     */
    private void onMedalClick(OnlineMapDetailInfo.OnlineBagInfo bagInfo) {
        if (mMapClickListener != null) {
            mMapClickListener.onMedalClick(mMapDetailInfo, bagInfo);
        }
    }

    /**
     * 打开新关卡
     *
     * @param levelId
     */
    public void openLevel(String levelId) {
        setAnchor(levelId, true);
        setLevelStatus(levelId, STATUS_LEVEL_OPEN, false);
    }

    @Override
    public void setLevelStatus(String levelId, int status, boolean enableLock) {
        super.setLevelStatus(levelId, status, enableLock);
        try {
            StateSprite levelSprite = (StateSprite) findNodeById(levelId);
            if (levelSprite != null) {
                switch (status) {
                    case STATUS_LEVEL_LOCKED:
                    case STATUS_LEVEL_UNLOCK:
                    case STATUS_LEVEL_OPEN: {
                        levelSprite.setOnNodeClickListener(mNodeClickListener);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GameMapClickListener mMapClickListener;

    public void setGameMapClickListener(GameMapClickListener listener) {
        this.mMapClickListener = listener;
    }

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
