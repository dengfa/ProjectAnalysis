//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.blocks;

import android.graphics.Canvas;

import com.vincent.projectanalysis.module.coretext.TextEnv;

import java.util.ArrayList;
import java.util.List;

public class CYPageBlock extends CYBlock<CYLineBlock> {
    private int mWidth;
    private int mHeight;

    public CYPageBlock(TextEnv textEnv) {
        super(textEnv, "");
    }

    public int getContentWidth() {
        return this.mWidth;
    }

    public int getContentHeight() {
        return this.mHeight;
    }

    public void addChild(CYLineBlock child) {
        super.addChild(child);
        int width = child.getWidth();
        int height = child.getHeight();
        int lineY = child.getLineY();
        if(width > this.mWidth) {
            this.mWidth = width;
        }

        if(lineY + height > this.mHeight) {
            this.mHeight = lineY + height;
        }

    }

    public void draw(Canvas canvas) {
        List children = this.getChildren();
        if(children != null) {
            int count = children.size();

            for(int i = 0; i < count; ++i) {
                CYLineBlock lineBlock = (CYLineBlock)children.get(i);
                lineBlock.draw(canvas);
            }
        }

    }

    public List<CYBlock> getBlocks() {
        ArrayList blocks = new ArrayList();
        List lines = this.getChildren();
        if(lines != null) {
            int lineCount = lines.size();

            for(int i = 0; i < lineCount; ++i) {
                CYLineBlock line = (CYLineBlock)lines.get(i);
                blocks.addAll(line.getChildren());
            }
        }

        return blocks;
    }

    public void onMeasure() {
        List lines = this.getChildren();
        if(lines != null) {
            int lineCount = lines.size();

            for(int i = 0; i < lineCount; ++i) {
                CYLineBlock line = (CYLineBlock)lines.get(i);
                line.onMeasure();
            }
        }

        super.onMeasure();
    }
}
