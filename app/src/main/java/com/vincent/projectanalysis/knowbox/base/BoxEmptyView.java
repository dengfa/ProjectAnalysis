/**
 * Copyright (C) 2015 The AndroidRCStudent Project
 */
package com.vincent.projectanalysis.knowbox.base;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyena.framework.app.coretext.Html;
import com.hyena.framework.app.widget.EmptyView;
import com.hyena.framework.utils.UiThreadHandler;
import com.vincent.projectanalysis.R;

/**
 * 通用空页面视图
 */
public class BoxEmptyView extends EmptyView {

    private ImageView mEmptyHintImg;
    private TextView mEmptyHintTxt;
    private TextView mDescText;
    private TextView mEmptyFaqText;
    private TextView mEmptyBtn;
    private View mEmptySpace;

    public BoxEmptyView(Context context) {
        super(context);
        View.inflate(getContext(), R.layout.layout_common_empty, this);
        mEmptyHintImg = (ImageView) findViewById(R.id.emtpy_image);
        mEmptyHintTxt = (TextView) findViewById(R.id.empty_hint);
        mDescText = (TextView) findViewById(R.id.empty_desc);
        mEmptyFaqText = (TextView) findViewById(R.id.empty_faq_text);
        mEmptyBtn = (TextView) findViewById(R.id.empty_btn);
        mEmptySpace = findViewById(R.id.empty_space);
    }

    public void setEmptySpaceHeigh(int h) {
        if (mEmptySpace != null) {
            ViewGroup.LayoutParams params = mEmptySpace.getLayoutParams();
            params.height = h;
            mEmptySpace.setLayoutParams(params);
        }
    }

    @Override
    public void showNoNetwork() {
        showEmpty(R.drawable.icon_empty_default, "哎呀，网络连接失败", "请连接后重试", null, null);
    }

    @Override
    public void showEmpty(String errorCode, String hint) {
        showEmpty(R.drawable.icon_empty_default, hint, null, null, null);
    }

    public void showEmpty(String hint) {
        showEmpty(R.drawable.icon_empty_default, hint);
    }

    public void showEmpty(final int resId, String hint) {
        showEmpty(resId, hint, null, null, null);
    }

    public void showEmpty(final int resId, final String hint, final String desc,
                          final String btnTxt, final OnClickListener btnClickListener) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mEmptyHintImg != null)
                    mEmptyHintImg.setImageResource(resId);

                if (null != mEmptyHintTxt) {
                    if (null != hint && !TextUtils.isEmpty(hint)) {
                        mEmptyHintTxt.setVisibility(VISIBLE);
                        mEmptyHintTxt.setText(hint);
                    } else {
                        mEmptyHintTxt.setVisibility(INVISIBLE);
                    }
                }

                if (null != mDescText) {
                    if (null != desc && !TextUtils.isEmpty(desc)) {
                        mDescText.setVisibility(VISIBLE);
                        mDescText.setText(Html.fromHtml(desc));
                    } else {
                        mDescText.setVisibility(GONE);
                    }
                }

                if (mEmptyBtn != null) {
                    if (btnTxt != null) {
                        mEmptyBtn.setVisibility(VISIBLE);
                        mEmptyBtn.setText(btnTxt);
                    } else {
                        mEmptyBtn.setVisibility(INVISIBLE);
                    }
                    if (btnClickListener != null) {
                        mEmptyBtn.setOnClickListener(btnClickListener);
                    }
                }
                if (getBaseUIFragment() != null && getBaseUIFragment().getLoadingView() != null) {
                    getBaseUIFragment().getLoadingView().clearAnimation();
                    getBaseUIFragment().getLoadingView().setVisibility(INVISIBLE);
                    setVisibility(VISIBLE);
                }
            }
        });
    }

    /**
     * 设置空页面的附加信息
     *
     * @param faqText
     * @param listener
     */
    public void setFaqText(final Spanned faqText, final OnClickListener listener) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                mEmptyFaqText.setVisibility(VISIBLE);
                mEmptyFaqText.setText(faqText);
                mEmptyFaqText.setOnClickListener(listener);
            }
        });
    }
}
