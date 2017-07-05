//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.builder;


import com.vincent.projectanalysis.module.coretext.TextEnv;
import com.vincent.projectanalysis.module.coretext.blocks.CYBlock;

import java.util.List;

public class CYBlockProvider {
    private static CYBlockProvider mBlockProvider;
    private CYBlockProvider.CYBlockBuilder mBlockBuilder;

    private CYBlockProvider() {
    }

    public static CYBlockProvider getBlockProvider() {
        if(mBlockProvider == null) {
            mBlockProvider = new CYBlockProvider();
        }

        return mBlockProvider;
    }

    public List<CYBlock> build(TextEnv textEnv, String content) {
        return this.mBlockBuilder != null?this.mBlockBuilder.build(textEnv, content):null;
    }

    public void registerBlockBuilder(CYBlockProvider.CYBlockBuilder builder) {
        this.mBlockBuilder = builder;
    }

    public interface CYBlockBuilder {
        List<CYBlock> build(TextEnv var1, String var2);
    }
}
