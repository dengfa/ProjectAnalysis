//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.layout;


import com.vincent.projectanalysis.module.coretext.TextEnv;
import com.vincent.projectanalysis.module.coretext.blocks.CYBlock;
import com.vincent.projectanalysis.module.coretext.blocks.CYPageBlock;

import java.util.List;

public abstract class CYLayout {
    private TextEnv mTextEnv;

    public CYLayout(TextEnv textEnv) {
        this.mTextEnv = textEnv;
    }

    public TextEnv getTextEnv() {
        return this.mTextEnv;
    }

    public abstract List<CYPageBlock> parse();

    public abstract List<CYPageBlock> getPages();

    public abstract List<CYBlock> getBlocks();
}
