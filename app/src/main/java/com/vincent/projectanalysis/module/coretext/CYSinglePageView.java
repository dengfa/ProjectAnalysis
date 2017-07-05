//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.vincent.projectanalysis.module.coretext.blocks.CYBlock;
import com.vincent.projectanalysis.module.coretext.blocks.CYPageBlock;
import com.vincent.projectanalysis.module.coretext.blocks.ICYEditable;
import com.vincent.projectanalysis.module.coretext.builder.CYBlockProvider;
import com.vincent.projectanalysis.module.coretext.layout.CYHorizontalLayout;
import com.vincent.projectanalysis.utils.UIUtils;

import java.util.List;

public class CYSinglePageView extends CYPageView {
    private TextEnv           mTextEnv;
    private String            mQuestionTxt;
    private List<ICYEditable> mEditableList;
    private List<CYBlock>     blocks;
    private CYSinglePageView.Builder mBuilder = new CYSinglePageView.Builder();

    public CYSinglePageView(Context context) {
        super(context);
        this.init();
    }

    public CYSinglePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CYSinglePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.mTextEnv = this.buildDefaultTextEnv(this.getContext());
        this.mTextEnv.getEventDispatcher().addLayoutEventListener(this);
    }

    public TextEnv buildDefaultTextEnv(Context context) {
        int width = this.getResources().getDisplayMetrics().widthPixels;
        return (new TextEnv(context)).setPageWidth(width).setTextColor(-13421773).setFontSize(UIUtils.dip2px(20.0F)).setTextAlign(TextEnv.Align.CENTER).setPageHeight(2147483647).setVerticalSpacing(UIUtils.dip2px(this.getContext(), 3.0F));
    }

    private void setText(String questionTxt) {
        this.mQuestionTxt = questionTxt;
    }

    private void build() {
        if(TextUtils.isEmpty(this.mQuestionTxt)) {
            if(this.blocks != null && !this.blocks.isEmpty()) {
                for(int var3 = 0; var3 < this.blocks.size(); ++var3) {
                    ((CYBlock)this.blocks.get(var3)).release();
                }
            }

            this.blocks = null;
        } else {
            String text = this.mQuestionTxt.replaceAll("\\\\#", "labelsharp").replaceAll("\n", "").replaceAll("\r", "");
            if(this.blocks != null && !this.blocks.isEmpty()) {
                for(int i = 0; i < this.blocks.size(); ++i) {
                    ((CYBlock)this.blocks.get(i)).release();
                }
            }

            this.blocks = CYBlockProvider.getBlockProvider().build(this.mTextEnv, text);
            this.mEditableList = this.getEditableList();
            this.doLayout(true);
        }
    }

    public List<ICYEditable> getEditables() {
        return this.mEditableList;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(this.getSuggestedMinimumWidth(), widthMeasureSpec);
        if(width > 0) {
            if(this.getPageBlock() != null && this.mTextEnv.getPageWidth() == width) {
                this.setMeasuredDimension(width, this.getPageBlock().getHeight());
            } else {
                this.mTextEnv.setPageWidth(width);
                CYPageBlock pageBlock = this.parsePageBlock();
                this.setPageBlock(this.mTextEnv, pageBlock);
                this.setMeasuredDimension(width, pageBlock == null?0:pageBlock.getHeight());
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void doLayout(boolean force) {
        super.doLayout(force);
        if(force) {
            CYPageBlock pageBlock = this.parsePageBlock();
            this.setPageBlock(this.mTextEnv, pageBlock);
        }

        this.requestLayout();
    }

    private CYPageBlock parsePageBlock() {
        if(this.blocks != null && !this.blocks.isEmpty()) {
            CYHorizontalLayout layout = new CYHorizontalLayout(this.mTextEnv, this.blocks);
            List pages = layout.parse();
            if(pages != null && pages.size() > 0) {
                CYPageBlock pageBlock = (CYPageBlock)pages.get(0);
                pageBlock.setPadding(0, 0, 0, 0);
                return pageBlock;
            }
        }

        return null;
    }

    public TextEnv getTextEnv() {
        return this.mTextEnv;
    }

    public CYSinglePageView.Builder getBuilder() {
        return this.mBuilder;
    }

    public class Builder {
        private String mText;

        public Builder() {
        }

        public CYSinglePageView.Builder setEditable(boolean editable) {
            CYSinglePageView.this.mTextEnv.setEditable(editable);
            return this;
        }

        public CYSinglePageView.Builder setTextColor(int textColor) {
            CYSinglePageView.this.mTextEnv.setTextColor(textColor);
            return this;
        }

        public CYSinglePageView.Builder setTextSize(int dp) {
            CYSinglePageView.this.mTextEnv.setFontSize(UIUtils.dip2px(CYSinglePageView.this.getContext(), (float)dp));
            return this;
        }

        public CYSinglePageView.Builder setText(String questionTxt) {
            CYSinglePageView.this.setText(questionTxt);
            return this;
        }

        public CYSinglePageView.Builder setDebug(boolean debug) {
            CYSinglePageView.this.mTextEnv.setDebug(debug);
            return this;
        }

        public void build() {
            CYSinglePageView.this.build();
        }
    }
}
