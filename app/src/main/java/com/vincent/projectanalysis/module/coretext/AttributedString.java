//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext;

import android.text.TextUtils;

import com.vincent.projectanalysis.module.coretext.blocks.CYBlock;
import com.vincent.projectanalysis.module.coretext.blocks.CYTextBlock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AttributedString {
    private String                              mText;
    private TextEnv                             mTextEnv;
    private List<AttributedString.BlockSection> mBlockSections;

    public AttributedString(TextEnv textEnv, String text) {
        this.mTextEnv = textEnv;
        this.mText = text;
    }

    public <T extends CYBlock> T replaceBlock(int start, int end, Class<T> blockClz) throws IndexOutOfBoundsException {
        if (TextUtils.isEmpty(this.mText)) {
            return null;
        } else {
            if (this.mBlockSections == null) {
                this.mBlockSections = new ArrayList();
            }

            if (start >= 0 && end >= 0 && end <= this.mText.length() && start <= this.mText.length() && end >= start) {
                AttributedString.BlockSection section = new AttributedString.BlockSection(start, end, blockClz);
                CYBlock block = section.getOrNewBlock();
                this.mBlockSections.add(section);
                // return block;  报错？？？
                return (T) block;
            } else {
                throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
            }
        }
    }

    public void replaceBlock(int start, int end, CYBlock block) {
        if (!TextUtils.isEmpty(this.mText) && block != null) {
            if (this.mBlockSections == null) {
                this.mBlockSections = new ArrayList();
            }

            if (start >= 0 && end >= 0 && end <= this.mText.length() && start <= this.mText.length() && end >= start) {
                AttributedString.BlockSection section = new AttributedString.BlockSection(start, end, block);
                this.mBlockSections.add(section);
            } else {
                throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
            }
        }
    }

    public List<CYBlock> buildBlocks() {
        ArrayList blocks = new ArrayList();
        if (this.mBlockSections == null) {
            AttributedString.BlockSection endIndex = new AttributedString.BlockSection(0, this.mText.length(), CYTextBlock.class);
            blocks.add(endIndex.getOrNewBlock());
        } else {
            Collections.sort(this.mBlockSections, new Comparator<AttributedString.BlockSection>() {
                public int compare(AttributedString.BlockSection t1, AttributedString.BlockSection t2) {
                    return t1.startIndex - t2.startIndex;
                }
            });
            int var6 = 0;

            for (int ghostSection = 0; ghostSection < this.mBlockSections.size(); ++ghostSection) {
                AttributedString.BlockSection blockSection = this.mBlockSections.get(ghostSection);
                if (blockSection.startIndex != var6) {
                    AttributedString.BlockSection ghostSection1 = new AttributedString.BlockSection(var6, blockSection.startIndex, CYTextBlock.class);
                    blocks.add(ghostSection1.getOrNewBlock());
                }

                blocks.add(blockSection.getOrNewBlock());
                var6 = blockSection.endIndex;
            }

            if (var6 < this.mText.length()) {
                AttributedString.BlockSection var7 = new AttributedString.BlockSection(var6, this.mText.length(), CYTextBlock.class);
                blocks.add(var7.getOrNewBlock());
            }
        }

        return this.resetBlocks(blocks);
    }

    private List<CYBlock> resetBlocks(List<CYBlock> rawBlocks) {
        ArrayList result = new ArrayList();
        if (rawBlocks != null) {
            int blockCount = rawBlocks.size();

            for (int i = 0; i < blockCount; ++i) {
                CYBlock block = (CYBlock) rawBlocks.get(i);
                if (block instanceof CYTextBlock && block.getChildren() != null) {
                    result.addAll(block.getChildren());
                } else {
                    result.add(block);
                }
            }
        }

        return result;
    }

    private class BlockSection {
        public  int                      startIndex;
        public  int                      endIndex;
        public  Class<? extends CYBlock> blockClz;
        private CYBlock                  mBlock;

       /* public BlockSection(int var1, int startIndex, Class<? extends CYBlock> endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;     //???  ???
            this.blockClz = blockClz;
        }*/

        public BlockSection(int startIndex, int endIndex, Class<? extends CYBlock> blockClz) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.blockClz = blockClz;
        }

        public BlockSection(int startIndex, int endIndex, CYBlock block) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.mBlock = block;
        }

        public CYBlock getOrNewBlock() {
            if (this.mBlock != null) {
                return this.mBlock;
            } else {
                if (this.blockClz != null) {
                    try {
                        Constructor e = this.blockClz.getConstructor(new Class[]{TextEnv.class, String.class});
                        String content = AttributedString.this.mText.substring(this.startIndex, this.endIndex);
                        content = content.replaceAll("labelsharp", "#");
                        this.mBlock = (CYBlock) e.newInstance(new Object[]{AttributedString.this.mTextEnv, content});
                        return this.mBlock;
                    } catch (NoSuchMethodException var3) {
                        var3.printStackTrace();
                    } catch (InvocationTargetException var4) {
                        var4.printStackTrace();
                    } catch (InstantiationException var5) {
                        var5.printStackTrace();
                    } catch (IllegalAccessException var6) {
                        var6.printStackTrace();
                    }
                }
                return null;
            }
        }
    }
}
