package com.vincent.projectanalysis.widgets.mapScene;

import android.graphics.Point;

/**
 * Created by dengfa on 2019-12-06.
 * Des:
 */
public class NodeInfo {
    public Point position;
    public int   resId;
    public int   width;
    public int   height;

    public NodeInfo(Point position, int resId) {
        this.position = position;
        this.resId = resId;
    }
}
