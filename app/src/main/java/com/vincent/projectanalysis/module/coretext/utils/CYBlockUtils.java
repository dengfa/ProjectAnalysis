//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.utils;

import com.vincent.projectanalysis.module.coretext.blocks.CYBlock;
import com.vincent.projectanalysis.module.coretext.blocks.CYPageBlock;
import com.vincent.projectanalysis.module.coretext.blocks.CYTextBlock;

import java.util.List;

public class CYBlockUtils {
    public CYBlockUtils() {
    }

    public static CYBlock findBlockByPosition(CYPageBlock pageBlock, int x, int y) {
        if(pageBlock == null) {
            return null;
        } else {
            List blocks = pageBlock.getBlocks();
            if(blocks != null && !blocks.isEmpty()) {
                for(int i = 0; i < blocks.size(); ++i) {
                    CYBlock block = (CYBlock)blocks.get(i);
                    if(block instanceof CYTextBlock) {
                        List subBlocks = block.getChildren();
                        if(subBlocks != null && !subBlocks.isEmpty()) {
                            for(int j = 0; j < subBlocks.size(); ++j) {
                                CYBlock subBlock = (CYBlock)subBlocks.get(j);
                                if(subBlock.getBlockRect().contains(x, y)) {
                                    return block;
                                }
                            }
                        }
                    } else if(block.getBlockRect().contains(x, y)) {
                        return block;
                    }
                }

                return null;
            } else {
                return null;
            }
        }
    }
}
