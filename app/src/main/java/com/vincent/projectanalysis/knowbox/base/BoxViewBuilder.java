/**
 * Copyright (C) 2015 The AndroidRCStudent Project
 */
package com.vincent.projectanalysis.knowbox.base;

import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.app.fragment.ViewBuilder;
import com.hyena.framework.app.widget.EmptyView;
import com.hyena.framework.app.widget.LoadingView;
import com.hyena.framework.app.widget.TitleBar;

/**
 * @author Fanjb
 * @name 通用页面视图构造器
 * @date 2015年9月1日
 */
public class BoxViewBuilder implements ViewBuilder {

    public static final String ARGS_TITLE_BAR_STYLE = "args_title_bar_style";
    public static final String ARGS_LOADING_VIEW_STYLE = "args_loading_view_style";

    public static final int TITLE_BAR_STYLE_DEFAULT = 0;
    public static final int TITLE_BAR_STYLE_HOME_SCHOOL = 1;

    public static final int LOADING_VIEW_STYLE_DEFAULT = 0;
    public static final int LOADING_VIEW_STYLE_PROGRESS = 1;

    @Override
    public TitleBar buildTitleBar(BaseUIFragment<?> fragment) {
        int style = TITLE_BAR_STYLE_DEFAULT;
        if (fragment != null && fragment.getArguments() != null) {
            style = fragment.getArguments().getInt(ARGS_TITLE_BAR_STYLE, TITLE_BAR_STYLE_DEFAULT);
        }
        TitleBar titleBar;
        switch (style) {
            case TITLE_BAR_STYLE_DEFAULT: {
                titleBar = new BoxTitleBar(fragment.getActivity());
                return titleBar;
            }
            default: {
                titleBar = new BoxTitleBar(fragment.getActivity());
            }
        }
        titleBar.setBaseUIFragment(fragment);
        return titleBar;
    }

    @Override
    public EmptyView buildEmptyView(BaseUIFragment<?> fragment) {
        BoxEmptyView emptyView = new BoxEmptyView(fragment.getActivity());
        emptyView.setBaseUIFragment(fragment);
        return emptyView;
    }

    @Override
    public LoadingView buildLoadingView(BaseUIFragment<?> fragment) {
        LoadingView loadingView;
        int style = LOADING_VIEW_STYLE_DEFAULT;
        if (fragment != null && fragment.getArguments() != null) {
            style = fragment.getArguments().getInt(ARGS_LOADING_VIEW_STYLE, LOADING_VIEW_STYLE_DEFAULT);
        }
        switch (style) {
            case LOADING_VIEW_STYLE_DEFAULT:
                loadingView = new BoxLoadingView(fragment.getActivity());
                break;
            default:
                loadingView = new BoxLoadingView(fragment.getActivity());
                break;
        }
        loadingView.setBaseUIFragment(fragment);
        return loadingView;
    }
}
