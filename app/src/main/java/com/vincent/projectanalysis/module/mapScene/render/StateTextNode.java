package com.vincent.projectanalysis.module.mapScene.render;


import com.vincent.projectanalysis.module.mapScene.Director;
import com.vincent.projectanalysis.module.mapScene.base.CTextNode;

/**
 * Created by yangzc on 16/5/20.
 */
public class StateTextNode extends CTextNode {

    public static StateTextNode create(Director director) {
        return new StateTextNode(director);
    }

    private int mDisableColor = -1;
    private int mNormalColor;

    protected StateTextNode(Director director) {
        super(director);
    }

    public void setDisableColor(int disableColor) {
        this.mDisableColor = disableColor;
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        this.mNormalColor = color;
        this.mDisableColor = color;
    }

    private boolean mEnable = true;
    public void setEnable(boolean enable){
        this.mEnable = enable;
        if (enable) {
            super.setColor(mNormalColor);
        } else {
            super.setColor(mDisableColor);
        }
    }

    @Override
    protected void onTouchDown() {
        if (!mEnable)
            return;

        super.onTouchDown();
    }

    @Override
    protected void onTouchUp() {
        if (!mEnable)
            return;

        super.onTouchUp();
    }
}
