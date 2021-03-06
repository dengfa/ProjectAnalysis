package com.vincent.projectanalysis.module.mapScene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.vincent.projectanalysis.module.mapScene.action.MapAction;
import com.vincent.projectanalysis.module.mapScene.action.MapActionAlpha;
import com.vincent.projectanalysis.module.mapScene.action.MapActionFrame;
import com.vincent.projectanalysis.module.mapScene.action.MapActionRotate;
import com.vincent.projectanalysis.module.mapScene.action.MapActionScale;
import com.vincent.projectanalysis.module.mapScene.action.MapActionSequence;
import com.vincent.projectanalysis.module.mapScene.action.MapActionTranslate;
import com.vincent.projectanalysis.module.mapScene.action.MapFrame;
import com.vincent.projectanalysis.module.mapScene.base.CLayer;
import com.vincent.projectanalysis.module.mapScene.base.CMap;
import com.vincent.projectanalysis.module.mapScene.base.CNode;
import com.vincent.projectanalysis.module.mapScene.base.CPoint;
import com.vincent.projectanalysis.module.mapScene.base.CScene;
import com.vincent.projectanalysis.module.mapScene.base.CScrollLayer;
import com.vincent.projectanalysis.module.mapScene.base.CSprite;
import com.vincent.projectanalysis.module.mapScene.base.CTextNode;
import com.vincent.projectanalysis.module.mapScene.base.action.CAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CAlphaToAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CFrameAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CIntervalAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CMethodAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CMoveToAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CRepeatAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CRotateToAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CScaleToAction;
import com.vincent.projectanalysis.module.mapScene.base.action.CSequenceAction;
import com.vincent.projectanalysis.module.mapScene.base.texture.CTexture;
import com.vincent.projectanalysis.module.mapScene.node.MapNode;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeBlock;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeButton;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeLayer;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeLine;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeSprite;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeText;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeTitle;
import com.vincent.projectanalysis.module.mapScene.parser.DefaultMapParser;
import com.vincent.projectanalysis.module.mapScene.parser.MapParser;
import com.vincent.projectanalysis.module.mapScene.render.BlockNode;
import com.vincent.projectanalysis.module.mapScene.render.ButtonNode;
import com.vincent.projectanalysis.module.mapScene.render.LineNode;
import com.vincent.projectanalysis.module.mapScene.render.StateTextNode;
import com.vincent.projectanalysis.module.mapScene.render.StateTexture;
import com.vincent.projectanalysis.module.mapScene.render.TitleNode;
import com.vincent.projectanalysis.module.mapScene.style.MapStyle;
import com.vincent.projectanalysis.utils.FileUtils;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.MathUtils;
import com.vincent.projectanalysis.utils.UIUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/4/22.
 */
public class MapScene extends CScene {

    private MapParser mParser = new DefaultMapParser();
    private CMap      mMap    = null;
    private CLayer mTopLayer;
    private int mScreenWidth  = 0;
    private int mScreenHeight = 0;

    protected MapScene(Director director) {
        super(director);
    }

    /**
     * 加载asset中的文件
     *
     * @param path         路径
     * @param screenWidth  屏幕dp宽度
     * @param screenHeight 屏幕dp高度
     */
    public void loadAssetPath(String path, int screenWidth, int screenHeight) {
        try {
            InputStream is = getDirector().getContext().getAssets().open(path);
            byte buf[] = FileUtils.getBytes(is);
            load(new String(buf, "UTF-8"), screenWidth, screenHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAssetPath(File file, int screenWidth, int screenHeight) {
        try {
            InputStream is = new FileInputStream(file);
            byte buf[] = FileUtils.getBytes(is);
            load(new String(buf, "UTF-8"), screenWidth, screenHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载地图文件
     *
     * @param xml          文件内容xml格式
     * @param screenWidth  屏幕dp宽度
     * @param screenHeight 屏幕dp高度
     */
    public void load(String xml, int screenWidth, int screenHeight) {
        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;
        //解析地图
        mMap = mParser.parse(xml, screenWidth, screenHeight);
        if (mMap != null) {
            int zIndex = -1;
            CScrollLayer topLayer = null;
            List<MapNodeLayer> layers = mMap.getLayers();
            if (layers != null && !layers.isEmpty()) {
                for (int i = 0; i < layers.size(); i++) {
                    MapNodeLayer nodeLayer = layers.get(i);
                    //创建层
                    CScrollLayer layer = createLayer(nodeLayer);
                    if (nodeLayer.getZIndex() > zIndex && nodeLayer.getDepth() > 0) {
                        topLayer = layer;
                        zIndex = nodeLayer.getZIndex();
                    }
                    if (layer != null) {
                        layer.setViewSize(nodeLayer.getWidth(), nodeLayer.getHeight());
                        layer.setDepth(nodeLayer.getDepth());
                        //加载层
                        addNode(layer, nodeLayer.getZIndex());
                    }
                }
            }
            mTopLayer = topLayer;
            mTopLayer.setTouchable(true);
            //同步滚动位置
            if (topLayer != null) {
                topLayer.setOnScrollerListener(new OnScrollerListener() {

                    @Override
                    public void onScroll(CLayer layer, int scrollX, int scrollY, int width, int height) {
                        List<CNode> nodes = getNodes();
                        for (int i = 0; i < nodes.size(); i++) {
                            if (nodes.get(i) instanceof CScrollLayer
                                    && ((CScrollLayer) nodes.get(i)).getDepth() > 0) {

                                CScrollLayer scrollLayer = (CScrollLayer) nodes.get(i);
                                if (scrollLayer != layer && scrollLayer.getDepth() > 0)
                                    scrollLayer.scrollTo((int) (scrollX * layer.getDepth() * 1.0f),
                                            (int) (scrollY * layer.getDepth() * 1.0f));
                            }
                        }
                    }
                });
            }
        }
    }

    private boolean isInited = false;

    @Override
    public synchronized void update(float dt) {
        //初始化滚动到最底端
        if (!isInited && mTopLayer != null) {
            scrollToBottom();
            isInited = true;
        }
        super.update(dt);
    }

    /**
     * 获取关卡数
     *
     * @return
     */
    public int getLevelCount() {
        if (mMap != null) {
            return mMap.mLevelCnt;
        }
        return 1;
    }

    /**
     * 扩展parser
     *
     * @param parser
     */
    public void setParser(MapParser parser) {
        mParser = parser;
    }

    //=====================地图控制逻辑==============================

    /**
     * 判断本关卡后边是否存在宝箱
     *
     * @param index
     * @return
     */
    public StateSprite getBag(String index) {
        try {
            return (StateSprite) findNodeById("bag_" + index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String mLevelId;

    public String getCurrentLevel() {
        return mLevelId;
    }

    public void setAnchor(String levelId, boolean hasAnim) {
        if (hasAnim) {
            setAnchor(mLevelId, levelId);
        } else {
            setAnchor(levelId);
        }
    }

    private CAction mAnchorAction = null;

    public void setAnchor(String levelId) {
        try {
            if (mAnchorAction != null) {
                mAnchorAction.stop();
            }
            this.mLevelId = levelId;
            StateSprite levelSprite = (StateSprite) findNodeById(levelId);
            StateSprite anchorSprite = (StateSprite) findNodeById("anchor");
            if (levelSprite != null && anchorSprite != null) {
                // @update by wutong 2016/12/5
                anchorSprite.setVisible(true);
                Point target = new Point(levelSprite.getPosition().x + (levelSprite.getWidth() - anchorSprite.getWidth()) / 2
                        , levelSprite.getPosition().y - getNumber(37));
                anchorSprite.setPosition(target);

                int offset = UIUtils.dip2px(5);
                CMoveToAction upAction = CMoveToAction.create(target.x, target.y - offset, 400);
                CMoveToAction downAction = CMoveToAction.create(target.x, target.y + offset, 400);
                mAnchorAction = CRepeatAction.create(CSequenceAction.create(upAction, downAction));
                anchorSprite.runAction(mAnchorAction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAnchor(String fromLevelId, String toLevelId) {
        this.mLevelId = toLevelId;
        if (mAnchorAction != null) {
            mAnchorAction.stop();
        }
        StateSprite fromLevelSprite = (StateSprite) findNodeById(fromLevelId);
        StateSprite toLevelSprite = (StateSprite) findNodeById(toLevelId);
        final StateSprite anchorSprite = (StateSprite) findNodeById("anchor");
        if (fromLevelSprite != null && toLevelSprite != null && anchorSprite != null) {
            CMoveToAction toCenter = CMoveToAction.create(
                    fromLevelSprite.getPosition().x + (fromLevelSprite.getWidth() - anchorSprite.getWidth()) / 2
                    , fromLevelSprite.getPosition().y + fromLevelSprite.getHeight() / 2 - anchorSprite.getHeight(),
                    300, new DecelerateInterpolator());

            CMoveToAction toTarget = CMoveToAction.create(
                    toLevelSprite.getPosition().x + (toLevelSprite.getWidth() - anchorSprite.getWidth()) / 2
                    , toLevelSprite.getPosition().y + toLevelSprite.getHeight() / 2 - anchorSprite.getHeight(), 1600);

            final int finishX = toLevelSprite.getPosition().x + (toLevelSprite.getWidth() - anchorSprite.getWidth()) / 2;
            final int finishY = toLevelSprite.getPosition().y - UIUtils.dip2px(37);
            CMoveToAction toFinal = CMoveToAction.create(finishX, finishY, 300, new AccelerateInterpolator());

            CMethodAction.CMethodCallBack finishCallBack = new CMethodAction.CMethodCallBack() {
                @Override
                public void onCallBack() {
                    int offset = UIUtils.dip2px(5);
                    CMoveToAction upAction = CMoveToAction.create(finishX, finishY - offset, 400);
                    CMoveToAction downAction = CMoveToAction.create(finishX, finishY + offset, 400);
                    mAnchorAction = CRepeatAction.create(CSequenceAction.create(upAction, downAction));
                    anchorSprite.runAction(mAnchorAction);
                }
            };
            anchorSprite.runAction(CSequenceAction.create(toCenter, toTarget, toFinal,
                    CMethodAction.create(finishCallBack)));
        }
    }

    public static final int STATUS_LEVEL_LOCKED = 1;
    public static final int STATUS_LEVEL_UNLOCK = 2;
    public static final int STATUS_LEVEL_OPEN   = 3;

    /**
     * 设置关卡标题以及副标题
     *
     * @param levelId  关卡ID
     * @param title    标题
     * @param subTitle 副标题
     * @param hasStar  是否有星星
     */
    public void setBlockInfo(String levelId, String title, String subTitle, boolean hasStar) {
        try {
            BlockNode blockNode = (BlockNode) findNodeById(levelId + "_block");
            if (blockNode != null) {
                blockNode.setTitle(title);
                blockNode.setSubTitle(subTitle, hasStar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置正在使用的关卡
     *
     * @param levelId
     * @param visible
     */
    public void setTry(String levelId, boolean visible) {
        try {
            CSprite tryNode = (CSprite) findNodeById("try");
            tryNode.setVisible(visible);
            if (!visible) {
                return;
            }
            StateSprite levelSprite = (StateSprite) findNodeById(levelId);
            if (tryNode != null && levelSprite != null) {
                tryNode.setPosition(new Point(levelSprite.getPosition().x - UIUtils.dip2px(44),
                        levelSprite.getPosition().y + UIUtils.dip2px(11)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题
     *
     * @param title
     * @param leftTxt
     * @param rightTxt
     */
    public void setTitle(String title, String leftTxt, String rightTxt) {
        try {
            TitleNode titleNode = (TitleNode) findNodeById("title");
            if (titleNode != null) {
                titleNode.setTitle(title);
                titleNode.setSubTitle(leftTxt, rightTxt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置关卡状态
     *
     * @param levelId    关卡ID
     * @param status     关卡状态
     * @param enableLock 锁的状态
     */
    public void setLevelStatus(String levelId, int status, boolean enableLock) {
        try {
            StateSprite levelSprite = (StateSprite) findNodeById(levelId);
            StateSprite lockSprite = (StateSprite) findNodeById(levelId + "_lock");
            StateTextNode indexText = (StateTextNode) findNodeById(levelId + "_index");
            //            if (levelSprite != null && lockSprite != null && indexText != null) {
            switch (status) {
                case STATUS_LEVEL_LOCKED: {
                    if (lockSprite != null) {
                        lockSprite.setVisible(true);
                        lockSprite.setStatus(enableLock ? StateSprite.STATUS_UNABLE
                                : StateSprite.STATUS_NORMAL);
                    }
                    if (indexText != null) {
                        indexText.setVisible(false);
                    }
                    if (levelSprite != null) {
                        levelSprite.setStatus(StateSprite.STATUS_UNABLE);
                    }
                    break;
                }
                case STATUS_LEVEL_UNLOCK: {
                    if (lockSprite != null) {
                        lockSprite.setVisible(false);
                        lockSprite.setStatus(enableLock ? StateSprite.STATUS_UNABLE
                                : StateSprite.STATUS_NORMAL);
                    }
                    if (indexText != null) {
                        indexText.setVisible(true);
                        indexText.setEnable(false);
                    }
                    if (levelSprite != null) {
                        levelSprite.setStatus(StateSprite.STATUS_UNABLE);
                    }
                    break;
                }
                case STATUS_LEVEL_OPEN: {
                    if (lockSprite != null) {
                        lockSprite.setVisible(false);
                        lockSprite.setStatus(enableLock ? StateSprite.STATUS_UNABLE
                                : StateSprite.STATUS_NORMAL);
                    }
                    if (indexText != null) {
                        indexText.setVisible(true);
                        indexText.setEnable(true);
                    }
                    if (levelSprite != null) {
                        levelSprite.setStatus(StateSprite.STATUS_NORMAL);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int STATUS_BAG_UNABLE = 1;
    public static final int STATUS_BAG_OPENED = 2;
    public static final int STATUS_BAG_ENABLE = 3;

    /**
     * 设置宝箱状态
     *
     * @param bagId     宝箱ID
     * @param bagStatus 宝箱状态
     */
    public void setBoxStatus(String bagId, int bagStatus) {
        try {
            StateSprite boxSprite = (StateSprite) findNodeById(bagId);
            if (boxSprite != null) {
                switch (bagStatus) {
                    case STATUS_BAG_UNABLE: {
                        boxSprite.setStatus(StateSprite.STATUS_UNABLE);
                        break;
                    }
                    case STATUS_BAG_ENABLE: {
                        boxSprite.setStatus(StateSprite.STATUS_NORMAL);
                        break;
                    }
                    case STATUS_BAG_OPENED: {
                        boxSprite.setStatus(StateSprite.STATUS_OPENED);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //=====================地图渲染逻辑==============================

    /**
     * 滚动所有层到最底端
     */
    private void scrollToBottom() {
        if (getNodes() != null) {
            for (int i = 0; i < getNodes().size(); i++) {
                CNode node = getNodes().get(i);
                if (node instanceof CLayer && ((CLayer) node).getDepth() > 0) {
                    CLayer layer = (CLayer) node;
                    layer.scrollTo(0, -layer.getHeight() + getDirector().getViewSize().height());
                }
            }
        }

        StateSprite levelSprite = (StateSprite) findNodeById(mLevelId);
        if (mTopLayer != null && levelSprite != null) {
            int y = levelSprite.getPosition().y;
            int scrollY = y - mScreenHeight / 2;
            if (scrollY < 0) {
                scrollY = 0;
            }
            if (scrollY > mTopLayer.getHeight() - mScreenHeight) {
                scrollY = mTopLayer.getHeight() - mScreenHeight;
            }
            mTopLayer.scrollTo(0, -scrollY);
        }
    }

    /**
     * 加载图片
     *
     * @param tag 节点标签
     * @param url 节点路径
     * @return
     */
    protected Bitmap loadBitmap(String tag, String url, int width, int height) {
        if (url != null && url.startsWith("res:")) {
            try {
                InputStream is = getDirector().getContext().getAssets()
                        .open(url.replace("res:", ""));
                Bitmap bitmap = getDirector().getBitmapManager().getBitmap(url, is);
                LogUtil.i("loadBitmap333 url=" + url);
                return bitmap;
            } catch (FileNotFoundException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 创建层
     *
     * @param mapLayer 层信息
     */
    protected CScrollLayer createLayer(MapNodeLayer mapLayer) {
        if (mapLayer == null)
            return null;

        CScrollLayer layer = CScrollLayer.create(getDirector());
        List<MapNode> nodes = mapLayer.getNodes();
        if (nodes != null && !nodes.isEmpty()) {
            for (int i = 0; i < nodes.size(); i++) {
                MapNode mapNode = nodes.get(i);
                CNode node = null;
                if (mapNode instanceof MapNodeSprite) {
                    MapNodeSprite sprite = (MapNodeSprite) mapNode;
                    //加载精灵
                    node = loadSprite(sprite, null);

                    //加载关卡描述
                    if (sprite.getBlocks() != null && !sprite.getBlocks().isEmpty()) {
                        for (int j = 0; j < sprite.getBlocks().size(); j++) {
                            MapNodeBlock mapBlock = sprite.getBlocks().get(j);
                            BlockNode blockNode = createBlock(mapBlock, sprite);
                            if (blockNode != null) {
                                blockNode.setId(TextUtils.isEmpty(mapBlock.getId())
                                        ? sprite.getId() + "_block" : mapBlock.getId());
                                blockNode.setTag(mapBlock.getTag());
                                layer.addNode(blockNode, mapBlock.getZIndex());
                            }
                        }
                    }

                    //加载关卡索引
                    if (sprite.getTexts() != null && !sprite.getTexts().isEmpty()) {
                        for (int j = 0; j < sprite.getTexts().size(); j++) {
                            MapNodeText textMap = sprite.getTexts().get(j);
                            //style
                            CTextNode textNode = createText(textMap, sprite);
                            if (textNode != null) {
                                textNode.setId(TextUtils.isEmpty(textMap.getId())
                                        ? sprite.getId() + "_index" : textMap.getId());
                                textNode.setTag(textMap.getTag());
                                layer.addNode(textNode, sprite.getZIndex() + 1);
                            }
                        }
                    }

                    //加载覆盖层
                    if (sprite.getSprites() != null && !sprite.getSprites().isEmpty()) {
                        for (int j = 0; j < sprite.getSprites().size(); j++) {
                            MapNodeSprite cover = sprite.getSprites().get(j);
                            if (TextUtils.isEmpty(cover.mSrc)) {
                                cover.mSrc = "res:map/icon_lock.png";
                            }
                            if (TextUtils.isEmpty(cover.mUnableSrc)) {
                                cover.mUnableSrc = "res:map/icon_lock_disable.png";
                            }
                            CSprite coverNode = loadSprite(cover, sprite);
                            if (coverNode != null) {
                                coverNode.setId(TextUtils.isEmpty(cover.getId())
                                        ? sprite.getId() + "_lock" : cover.getId());
                                coverNode.setTag(cover.getTag());
                                layer.addNode(coverNode, cover.getZIndex() + 2);
                            }
                        }
                    }

                } else if (mapNode instanceof MapNodeLine) {
                    MapNodeLine mapNodeLine = (MapNodeLine) mapNode;
                    node = createLine(mapLayer, mapNodeLine);
                } else if (mapNode instanceof MapNodeText) {
                    MapNodeText nodeText = (MapNodeText) mapNode;
                    node = createText(nodeText, null);
                } else if (mapNode instanceof MapNodeTitle) {
                    node = createTitle((MapNodeTitle) mapNode);
                } else if (mapNode instanceof MapNodeButton) {//TODO：暂时没有该节点
                    node = createButton((MapNodeButton) mapNode);
                }
                //添加子节点
                if (node != null) {
                    node.setId(mapNode.getId());
                    node.setTag(mapNode.getTag());
                    layer.addNode(node, mapNode.getZIndex());
                    //添加节点回调
                    onAddNode(layer, node);
                }
            }
        }
        return layer;
    }

    /**
     * 添加新节点
     *
     * @param layer
     * @param node
     */
    protected void onAddNode(CScrollLayer layer, CNode node) {
    }

    private CRepeatAction createAction(MapNodeSprite spriteNode, MapAction mapAction) {
        CIntervalAction action = null;
        if (mapAction instanceof MapActionAlpha) {
            action = createAlphaAction((MapActionAlpha) mapAction);
        } else if (mapAction instanceof MapActionScale) {
            action = createScaleAction(spriteNode, (MapActionScale) mapAction);
        } else if (mapAction instanceof MapActionTranslate) {
            action = createMoveToAction((MapActionTranslate) mapAction);
        } else if (mapAction instanceof MapActionFrame) {
            action = createFrameAction((MapActionFrame) mapAction);
        } else if (mapAction instanceof MapActionSequence) {
            action = createSequenceAction(spriteNode, (MapActionSequence) mapAction);
        } else if (mapAction instanceof MapActionRotate) {
            action = createRotateAction((MapActionRotate) mapAction);
        }
        if (action != null) {
            CRepeatAction result;
            if (mapAction.getRepeat() == -1) {
                result = CRepeatAction.create(action, Integer.MAX_VALUE);
            } else {
                result = CRepeatAction.create(action, mapAction.getRepeat());
            }
            return result;
        }
        return null;
    }

    private CRotateToAction createRotateAction(MapActionRotate mapRotateAction) {
        CRotateToAction action = CRotateToAction.create(mapRotateAction.mFrom,
                mapRotateAction.mDegree, mapRotateAction.getDuration());
        return action;
    }

    private CSequenceAction createSequenceAction(MapNodeSprite spriteNode,
                                                 MapActionSequence mapSequenceAction) {
        List<MapAction> mapActions = mapSequenceAction.getActions();
        if (mapActions == null || mapActions.isEmpty())
            return null;

        List<CAction> actions = new ArrayList<CAction>();
        for (int i = 0; i < mapActions.size(); i++) {
            MapAction mapAction = mapActions.get(i);
            CRepeatAction action = createAction(spriteNode, mapAction);
            if (action != null) {
                actions.add(action);
            }
        }
        CSequenceAction action = CSequenceAction.create(actions
                .toArray(new CAction[actions.size()]));
        return action;
    }

    private CFrameAction createFrameAction(MapActionFrame mapFrameAction) {
        List<MapFrame> frames = mapFrameAction.getFrames();
        if (frames != null && frames.size() > 0) {
            CFrameAction action = CFrameAction.create();
            for (int i = 0; i < frames.size(); i++) {
                MapFrame frame = frames.get(i);
                Bitmap bitmap = loadBitmap("", frame.mSrc, 0, 0);
                if (bitmap == null) {
                    continue;
                }
                action.addFrame(bitmap, frame.getDuration());
            }
            return action;
        }
        return null;
    }

    private CMoveToAction createMoveToAction(MapActionTranslate mapScaleAction) {
        CMoveToAction action = CMoveToAction.create(mapScaleAction.mToX,
                mapScaleAction.mToY, mapScaleAction.getDuration());
        return action;
    }

    private CScaleToAction createScaleAction(MapNodeSprite spriteNode, MapActionScale mapScaleAction) {
        CScaleToAction action = CScaleToAction.create(mapScaleAction.mFrom,
                mapScaleAction.mTo, mapScaleAction.getDuration());
        return action;
    }

    private CAlphaToAction createAlphaAction(MapActionAlpha mapAlphaAction) {
        CAlphaToAction action = CAlphaToAction.create(mapAlphaAction.mFrom,
                mapAlphaAction.mTo, mapAlphaAction.getDuration());
        return action;
    }

    /**
     * 创建精灵节点
     *
     * @param spriteNode 精灵节点信息
     * @param attach     关联的节点
     * @return 精灵节点
     */
    protected CSprite loadSprite(MapNodeSprite spriteNode, MapNode attach) {
        CTexture normal = createTexture(spriteNode.getId(), spriteNode.mSrc,
                spriteNode.getWidth(), spriteNode.getHeight());
        CTexture unable = createTexture(spriteNode.getId(), spriteNode.mUnableSrc,
                spriteNode.getWidth(), spriteNode.getHeight());
        CTexture open = createTexture(spriteNode.getId(), spriteNode.mOpenSrc,
                spriteNode.getWidth(), spriteNode.getHeight());

        StateSprite sprite = createSprite(spriteNode, attach, normal);
        sprite.setNextBagId(spriteNode.mNextBagId);
        sprite.setTexture(normal, unable, open);

        normal.setAnchor(spriteNode.getAnchorX(), spriteNode.getAnchorY());
        unable.setAnchor(spriteNode.getAnchorX(), spriteNode.getAnchorY());
        open.setAnchor(spriteNode.getAnchorX(), spriteNode.getAnchorY());

        if (attach != null) {
            sprite.setPosition(new Point(attach.getX() + spriteNode.getX(),
                    attach.getY() + spriteNode.getY()));
        } else {
            sprite.setPosition(new Point(spriteNode.getX(), spriteNode.getY()));
        }

        if (spriteNode.getActions() != null
                && !spriteNode.getActions().isEmpty()) {
            for (int i = 0; i < spriteNode.getActions().size(); i++) {
                MapAction mapAction = spriteNode.getActions().get(i);
                CAction action = createAction(spriteNode, mapAction);

                if (action != null) {
                    sprite.runAction(action);
                }
            }
        }
        return sprite;
    }

    /**
     * 创建精灵
     *
     * @param spriteNode
     * @param attach
     * @param texture
     * @return
     */
    protected StateSprite createSprite(MapNodeSprite spriteNode, MapNode attach
            , CTexture texture) {
        StateSprite sprite = StateSprite.create(getDirector(), texture);
        return sprite;
    }

    /**
     * 创建节点描述信息
     *
     * @param block      描述信息
     * @param attachNode 关联的节点
     * @return 描述信息节点
     */
    private BlockNode createBlock(MapNodeBlock block, MapNode attachNode) {
        MapStyle style = getStyle(block.mStyle);
        if (style == null)
            return null;

        String path = style.getStyle("subTitleSrc");
        if (TextUtils.isEmpty(path))
            path = "res:map/icon_star.png";
        Bitmap bitmap = loadBitmap(block.getId(), path, block.getWidth(), block.getHeight());

        BlockNode node = BlockNode.create(getDirector());
        node.setTitle(block.mTitle);
        node.setSubTitle(block.mSubTitle, bitmap);

        int titleFontSize = MathUtils.valueOfInt(style.getStyle("titleFontSize"));
        int subTitleFontSize = MathUtils.valueOfInt(style.getStyle("subTitleFontSize"));
        int marginLeft = MathUtils.valueOfInt(style.getStyle("marginLeft"));
        int marginRight = MathUtils.valueOfInt(style.getStyle("marginRight"));

        node.setTitleStyle(UIUtils.dip2px(titleFontSize), style.getStyle("titleColor"));
        node.setSubTitleStyle(UIUtils.dip2px(subTitleFontSize), style.getStyle("subTitleColor"));
        if (attachNode != null) {
            String direction = style.getStyle("attachDirection");
            if (TextUtils.isEmpty(direction)) {
                direction = "left";
            }
            //初始化布局方向
            node.setDirection(direction);
            //初始化边距
            node.setMargin(UIUtils.dip2px(marginLeft), UIUtils.dip2px(marginRight));
            node.setAttachMapNode(attachNode);
            //            if ("left".equals(direction)) {
            //                node.setPosition(new Point(attachNode.getX() - node.getWidth() - UIUtils.dip2px(marginRight), attachNode.getY()));
            //            } else {
            //                node.setPosition(new Point(attachNode.getX() + attachNode.getWidth() + UIUtils.dip2px(marginLeft), attachNode.getY()));
            //            }
        }
        return node;
    }

    /**
     * 创建按钮节点
     *
     * @param buttonNode 节点信息
     * @return
     */
    @Deprecated
    private CNode createButton(MapNodeButton buttonNode) {
        //TODO:暂时没有使用
        ButtonNode button = ButtonNode.create(getDirector());
        button.setPosition(new Point(buttonNode.getX(), buttonNode.getY()));
        button.setViewSize(buttonNode.getWidth(), buttonNode.getHeight());
        button.setTitle(buttonNode.mTitle);
        return button;
    }

    /**
     * 创建标题节点
     *
     * @param titleNode 节点信息
     * @return
     */
    protected TitleNode createTitle(MapNodeTitle titleNode) {
        TitleNode node = TitleNode.create(getDirector());
        Bitmap bitmap = loadBitmap(titleNode.getId(), titleNode.mBackGround, titleNode.getWidth(), titleNode.getHeight());
        node.setBackGround(bitmap);

        node.setStarBitmap(loadBitmap(titleNode.getId(), "res:map/icon_star.png", titleNode.getWidth(), titleNode.getHeight()));

        node.setTitle(titleNode.mTitle);
        node.setSubTitle(titleNode.mSubTitleLeft, titleNode.mSubTitleRight);
        node.setTitleStyle(titleNode.mTitleFontSize, titleNode.mTitleColor);
        node.setSubTitleStyle(titleNode.mSubTitleFontSize, titleNode.mSubTitleLeftColor,
                titleNode.mSubTitleRightColor);

        node.setPosition(new Point(titleNode.getX(), titleNode.getY()));
        node.setViewSize(titleNode.getWidth(), titleNode.getHeight());
        return node;
    }

    /**
     * 创建文本节点
     *
     * @param textNode 节点信息
     * @param attach   关联的节点信息
     * @return
     */
    private CTextNode createText(MapNodeText textNode, MapNode attach) {
        StateTextNode node = StateTextNode.create(getDirector());
        node.setText(textNode.mText);
        Point position;
        if (attach != null) {
            position = new Point(attach.getX() + textNode.getX(), attach.getY() + textNode.getY());
        } else {
            position = new Point(textNode.getX(), textNode.getY());
        }
        int width, height, fontSize;
        String textColor, pressColor, disableColor, textAlign;
        MapStyle style = getStyle(textNode.mStyle);
        if (style != null) {
            width = MathUtils.valueOfInt(style.getStyle("width"));
            width = getNumber(width);
            height = MathUtils.valueOfInt(style.getStyle("height"));
            height = getNumber(height);
            fontSize = MathUtils.valueOfInt(style.getStyle("fontSize"));
            fontSize = UIUtils.dip2px(fontSize);
            textColor = style.getStyle("color");
            pressColor = style.getStyle("pressed");
            textAlign = style.getStyle("textAlign");
            disableColor = style.getStyle("disable");
        } else {
            width = textNode.getWidth();
            height = textNode.getHeight();
            fontSize = textNode.mFontSize;
            textColor = textNode.mColor;
            pressColor = textNode.mPressColor;
            textAlign = textNode.mAlign;
            disableColor = textNode.mDisableColor;
        }
        node.setPosition(position);
        node.setViewSize(width, height);
        node.setFontSize(fontSize);
        node.setColor(Color.parseColor(textColor));
        if (!TextUtils.isEmpty(disableColor)) {
            node.setDisableColor(Color.parseColor(disableColor));
        }
        if (!TextUtils.isEmpty(pressColor)) {
            node.setPressedColor(Color.parseColor(pressColor));
        }

        if (textAlign != null) {
            if ("topLeft".equals(textAlign)) {
                node.setTextAlign(CAlign.TOP_LEFT);
            } else if ("topCenter".equals(textAlign)) {
                node.setTextAlign(CAlign.TOP_CENTER);
            } else if ("topRight".equals(textAlign)) {
                node.setTextAlign(CAlign.TOP_RIGHT);
            } else if ("centerLeft".equals(textAlign)) {
                node.setTextAlign(CAlign.CENTER_LEFT);
            } else if ("center".equals(textAlign)) {
                node.setTextAlign(CAlign.CENTER_CENTER);
            } else if ("centerRight".equals(textAlign)) {
                node.setTextAlign(CAlign.CENTER_RIGHT);
            } else if ("bottomLeft".equals(textAlign)) {
                node.setTextAlign(CAlign.BOTTOM_LEFT);
            } else if ("bottomCenter".equals(textAlign)) {
                node.setTextAlign(CAlign.BOTTOM_CENTER);
            } else if ("bottomRight".equals(textAlign)) {
                node.setTextAlign(CAlign.BOTTOM_RIGHT);
            } else {
                node.setTextAlign(CAlign.CENTER_CENTER);
            }
        } else {
            node.setTextAlign(CAlign.CENTER_CENTER);
        }
        return node;
    }

    /**
     * 创建线节点
     *
     * @param nodeLayer 线所在的层信息
     * @param line      线信息
     * @return
     */
    private LineNode createLine(MapNodeLayer nodeLayer, MapNodeLine line) {
        MapNode fromSprite = getSprite(nodeLayer, line.mFromId);
        MapNode toSprite = getSprite(nodeLayer, line.mToId);

        LineNode node = LineNode.create(getDirector());
        if ("dot".equals(line.mStyle)) {
            node.setStyle(LineNode.STYLE_DOT);
        } else {
            node.setStyle(LineNode.STYLE_NORMAL);
        }
        node.setColor(Color.parseColor(line.mColor));

        node.setStartPoint(new CPoint(fromSprite.getX() + fromSprite.getWidth() / 2,
                fromSprite.getY() + fromSprite.getHeight() / 2));
        node.setEndPoint(new CPoint(toSprite.getX() + toSprite.getWidth() / 2,
                toSprite.getY() + toSprite.getHeight() / 2));
        return node;
    }

    /**
     * 根据ID查找精灵信息
     *
     * @param layer 关联层信息
     * @param id    精灵ID
     * @return 精灵信息
     */
    private MapNode getSprite(MapNodeLayer layer, String id) {
        if (layer != null) {
            List<MapNode> nodes = layer.getNodes();
            if (nodes != null && !nodes.isEmpty()) {
                for (int i = 0; i < nodes.size(); i++) {
                    MapNode mapNode = nodes.get(i);
                    if (mapNode.getId() != null && mapNode.getId().equals(id)) {
                        return mapNode;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据样式ID查找样式
     *
     * @param styleId 样式ID
     * @return 样式信息
     */
    protected MapStyle getStyle(String styleId) {
        if (TextUtils.isEmpty(styleId))
            return null;
        if (mMap != null) {
            List<MapStyle> styles = mMap.getStyles();
            if (styles != null && !styles.isEmpty()) {
                for (int i = 0; i < styles.size(); i++) {
                    MapStyle style = styles.get(i);
                    if (styleId.equals(style.getId())) {
                        return styles.get(i);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 创建纹理
     *
     * @param id
     * @param path
     * @param width
     * @param height
     * @return
     */
    protected CTexture createTexture(String id, String path, int width, int height) {
        Bitmap bitmap = loadBitmap(id, path, width, height);
        Bitmap pressed = null;
        if (!TextUtils.isEmpty(path)) {
            String fileName = path.substring(0, path.indexOf("."));
            String suffix = path.substring(path.indexOf("."));
            pressed = loadBitmap(id, fileName + "_p" + suffix, width, height);
        }

        CTexture texture = StateTexture.create(getDirector(), bitmap, pressed);
        texture.setViewSize(width, height);
        return texture;
    }

    @Override
    public synchronized void render(Canvas canvas) {
        //绘制背景
        if (mMap != null && mMap.mBackGround != null) {
            canvas.drawColor(Color.parseColor(mMap.mBackGround));
        }
        super.render(canvas);
    }

    /**
     * 所有图片均已720切图
     *
     * @param result
     * @return
     */
    protected int getNumber(int result) {
        return (int) (result * 2.0f * mScreenWidth / 640);
    }
}
