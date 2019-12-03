package com.vincent.projectanalysis.module.mapScene.practicemap;

import android.graphics.Canvas;
import android.graphics.Color;

import com.vincent.projectanalysis.module.mapScene.Director;
import com.vincent.projectanalysis.module.mapScene.base.CLayer;
import com.vincent.projectanalysis.module.mapScene.base.CMap;
import com.vincent.projectanalysis.module.mapScene.base.CNode;
import com.vincent.projectanalysis.module.mapScene.base.CScene;
import com.vincent.projectanalysis.module.mapScene.base.CScrollLayer;
import com.vincent.projectanalysis.module.mapScene.node.MapNode;
import com.vincent.projectanalysis.module.mapScene.node.MapNodeLayer;
import com.vincent.projectanalysis.module.mapScene.parser.DefaultMapParser;
import com.vincent.projectanalysis.module.mapScene.parser.MapParser;
import com.vincent.projectanalysis.utils.FileUtils;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yangzc on 16/4/22.
 */
public class MapScene extends CScene {

    private MapParser mParser       = new DefaultMapParser();
    private CMap      mMap          = null;
    private CLayer    mTopLayer;
    private int       mScreenWidth  = 0;
    private int       mScreenHeight = 0;

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
                /*MapNode mapNode = nodes.get(i);
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
                    //onAddNode(layer, node);
                }*/
            }
        }
        return layer;
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
