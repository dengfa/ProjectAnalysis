//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.blocks;


import com.vincent.projectanalysis.module.coretext.TextEnv;

public class CYParagraphStartBlock extends CYBlock {
    private CYParagraphStyle mStyle;

    public CYParagraphStartBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.mStyle = new CYParagraphStyle(textEnv);
    }

    public int getContentWidth() {
        return 0;
    }

    public int getContentHeight() {
        return 0;
    }

    public void setParagraphStyle(CYParagraphStyle style) {
        this.mStyle = style;
    }

    public CYParagraphStyle getStyle() {
        return this.mStyle;
    }
}
