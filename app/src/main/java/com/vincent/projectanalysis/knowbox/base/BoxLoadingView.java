/**
 * Copyright (C) 2015 The AndroidRCStudent Project
 */
package com.vincent.projectanalysis.knowbox.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyena.framework.app.widget.LoadingView;
import com.hyena.framework.utils.UiThreadHandler;
import com.vincent.projectanalysis.R;

/**
 * 通用页面加载动画视图
 */
public class BoxLoadingView extends LoadingView {

    private ImageView mLoadingImg;
    private TextView mLoadingHintTxt;

    public BoxLoadingView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_common_loading, this);
        mLoadingHintTxt = (TextView) findViewById(R.id.loading_hint);
        mLoadingImg = (ImageView) findViewById(R.id.loading_anim);
    }

    @Override
    public void showLoading(final String hint) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingHintTxt != null && !TextUtils.isEmpty(hint))
                    mLoadingHintTxt.setText(hint);
                setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_comm_page_loading);
                anim.setInterpolator(new LinearInterpolator());
                mLoadingImg.startAnimation(anim);
                getBaseUIFragment().getEmptyView().setVisibility(View.GONE);
            }
        });
    }
}
