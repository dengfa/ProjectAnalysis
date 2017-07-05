//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.layout;

import android.graphics.Rect;

import com.vincent.projectanalysis.module.coretext.base.TextEnv;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYBreakLineBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYLineBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYPageBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYParagraphEndBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYParagraphStartBlock;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYParagraphStyle;
import com.vincent.projectanalysis.module.coretext.base.blocks.CYPlaceHolderBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CYHorizontalLayout extends CYLayout {
    private int leftWidth = 0;
    private int y = 0;
    private CYLineBlock line = null;
    private List<CYPlaceHolderBlock> placeHolderBlocks = new ArrayList();
    private List<CYPlaceHolderBlock> linePlaceHolderBlocks = new ArrayList();
    private Stack<CYParagraphStyle> styleParagraphStack = new Stack();
    private List<CYLineBlock> lines = new ArrayList();
    private List<CYBlock> mBlocks;
    private List<CYPageBlock> mPageBlocks = new ArrayList();
    private Rect              mTemp1Rect  = new Rect();
    private Rect              mTemp2Rect  = new Rect();

    public CYHorizontalLayout(TextEnv textEnv, List<CYBlock> blocks) {
        super(textEnv);
        this.leftWidth = textEnv.getPageWidth();
        this.mBlocks = blocks;
    }

    private void reset() {
        this.leftWidth = this.getTextEnv().getPageWidth();
        this.y = 0;
        this.line = null;
        if(this.placeHolderBlocks == null) {
            this.placeHolderBlocks = new ArrayList();
        }

        this.placeHolderBlocks.clear();
        if(this.linePlaceHolderBlocks == null) {
            this.linePlaceHolderBlocks = new ArrayList();
        }

        this.linePlaceHolderBlocks.clear();
        if(this.styleParagraphStack == null) {
            this.styleParagraphStack = new Stack();
        }

        this.styleParagraphStack.clear();
        if(this.lines == null) {
            this.lines = new ArrayList();
        }

        this.lines.clear();
    }

    public List<CYPageBlock> parse() {
        this.reset();
        List lines = this.parseLines(this.mBlocks);
        CYPageBlock page = new CYPageBlock(this.getTextEnv());
        int y = 0;
        if(lines != null) {
            for(int i = 0; i < lines.size(); ++i) {
                CYLineBlock line = (CYLineBlock)lines.get(i);
                if(line.getChildren() != null && !line.getChildren().isEmpty() && line.isValid()) {
                    int maxBlockHeight = line.getMaxBlockHeightInLine();
                    if(y + maxBlockHeight > this.getTextEnv().getPageHeight()) {
                        page = new CYPageBlock(this.getTextEnv());
                        y = 0;
                    } else {
                        line.updateLineY(y);
                        y += line.getHeight() + this.getTextEnv().getVerticalSpacing();
                    }

                    page.addChild(line);
                }
            }
        }

        this.mPageBlocks.add(page);
        return this.mPageBlocks;
    }

    public List<CYPageBlock> getPages() {
        return this.mPageBlocks;
    }

    public List<CYBlock> getBlocks() {
        return this.mBlocks;
    }

    private List<CYLineBlock> parseLines(List<CYBlock> blocks) {
        int pageWidth = this.getTextEnv().getPageWidth();
        int blockCount = blocks.size();

        for(int i = 0; i < blockCount; ++i) {
            CYBlock itemBlock = (CYBlock)blocks.get(i);
            if(itemBlock instanceof CYParagraphStartBlock) {
                this.styleParagraphStack.push(((CYParagraphStartBlock)itemBlock).getStyle());
                this.wrapLine();
                if(this.line != null) {
                    this.line.setIsFirstLineInParagraph(true);
                }
            } else if(itemBlock instanceof CYParagraphEndBlock) {
                if(!this.styleParagraphStack.isEmpty()) {
                    this.styleParagraphStack.pop();
                }

                if(this.line == null) {
                    this.line = new CYLineBlock(this.getTextEnv(), this.getParagraphStyle(this.styleParagraphStack));
                    this.lines.add(this.line);
                }

                this.line.setIsFinishingLineInParagraph(true);
                this.wrapLine();
            } else if(itemBlock instanceof CYBreakLineBlock) {
                if(this.line == null) {
                    this.line = new CYLineBlock(this.getTextEnv(), this.getParagraphStyle(this.styleParagraphStack));
                    this.lines.add(this.line);
                }

                this.wrapLine();
            } else {
                if(this.line == null) {
                    this.line = new CYLineBlock(this.getTextEnv(), this.getParagraphStyle(this.styleParagraphStack));
                    this.lines.add(this.line);
                }

                if(itemBlock != null) {
                    itemBlock.setParagraphStyle(this.getParagraphStyle(this.styleParagraphStack));
                }

                if(itemBlock instanceof CYPlaceHolderBlock) {
                    if(((CYPlaceHolderBlock)itemBlock).getAlignStyle() == CYPlaceHolderBlock.AlignStyle.Style_MONOPOLY) {
                        this.wrapLine();
                        itemBlock.setX(0);
                        itemBlock.setLineY(this.y);
                        this.line.addChild(itemBlock);
                        this.wrapLine();
                        if(this.placeHolderBlocks != null) {
                            this.placeHolderBlocks.clear();
                        }
                        continue;
                    }

                    this.placeHolderBlocks.add((CYPlaceHolderBlock)itemBlock);
                }

                int blockWidth = itemBlock.getWidth();
                CYPlaceHolderBlock hitCell;
                if(blockWidth < this.leftWidth) {
                    for(hitCell = this.getHitCell(this.linePlaceHolderBlocks, pageWidth - this.leftWidth, this.y, itemBlock); hitCell != null; hitCell = this.getHitCell(this.linePlaceHolderBlocks, pageWidth - this.leftWidth, this.y, itemBlock)) {
                        this.leftWidth = pageWidth - hitCell.getWidth() - hitCell.getX();
                    }
                }

                while(this.leftWidth != pageWidth && this.leftWidth < blockWidth) {
                    this.wrapLine();

                    for(hitCell = this.getHitCell(this.linePlaceHolderBlocks, pageWidth - this.leftWidth, this.y, itemBlock); hitCell != null; hitCell = this.getHitCell(this.linePlaceHolderBlocks, pageWidth - this.leftWidth, this.y, itemBlock)) {
                        this.leftWidth = pageWidth - hitCell.getWidth() - hitCell.getX();
                    }
                }

                itemBlock.setX(pageWidth - this.leftWidth);
                itemBlock.setLineY(this.y);
                this.leftWidth -= blockWidth;
                this.line.addChild(itemBlock);
            }
        }

        return this.lines;
    }

    private void wrapLine() {
        if(this.line != null) {
            int lineHeight = 0;
            if(this.line.getChildren() != null && !this.line.getChildren().isEmpty()) {
                lineHeight = this.line.getHeight();
            } else if(this.lines != null) {
                this.lines.remove(this.line);
            }

            this.y += lineHeight + this.getTextEnv().getVerticalSpacing();
            this.leftWidth = this.getTextEnv().getPageWidth();
            this.line = new CYLineBlock(this.getTextEnv(), this.getParagraphStyle(this.styleParagraphStack));
            this.lines.add(this.line);
            this.linePlaceHolderBlocks = this.getLinePlaceHolderBlocks(this.y);
        }
    }

    private List<CYPlaceHolderBlock> getLinePlaceHolderBlocks(int y) {
        if(this.placeHolderBlocks != null && !this.placeHolderBlocks.isEmpty()) {
            ArrayList linePlaceHolderBlocks = new ArrayList();
            int count = this.placeHolderBlocks.size();

            for(int i = 0; i < count; ++i) {
                CYPlaceHolderBlock block = (CYPlaceHolderBlock)this.placeHolderBlocks.get(i);
                int top = block.getLineY();
                int bottom = top + block.getHeight();
                if(y >= top && y <= bottom) {
                    linePlaceHolderBlocks.add(block);
                }
            }

            return linePlaceHolderBlocks;
        } else {
            return null;
        }
    }

    private CYPlaceHolderBlock getHitCell(List<CYPlaceHolderBlock> linePlaceHolderBlocks, int x, int y, CYBlock block) {
        if(linePlaceHolderBlocks != null && !linePlaceHolderBlocks.isEmpty()) {
            this.mTemp1Rect.set(x, y, x + block.getWidth(), y + block.getHeight());
            int count = linePlaceHolderBlocks.size();

            for(int i = 0; i < count; ++i) {
                CYPlaceHolderBlock cell = (CYPlaceHolderBlock)linePlaceHolderBlocks.get(i);
                this.mTemp2Rect.set(cell.getX(), cell.getLineY(), cell.getX() + cell.getWidth(), cell.getLineY() + cell.getHeight());
                if(cell != block && this.mTemp2Rect.intersect(this.mTemp1Rect)) {
                    return cell;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    private CYParagraphStyle getParagraphStyle(Stack<CYParagraphStyle> styleStack) {
        return styleStack != null && !styleStack.isEmpty()?(CYParagraphStyle)styleStack.peek():null;
    }
}
