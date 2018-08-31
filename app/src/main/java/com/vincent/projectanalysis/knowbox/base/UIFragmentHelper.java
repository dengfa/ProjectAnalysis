package com.vincent.projectanalysis.knowbox.base;

import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.app.fragment.BaseUIFragmentHelper;
import com.hyena.framework.app.fragment.ViewBuilder;
import com.hyena.framework.servcie.audio.PlayerBusService;
import com.knowbox.base.coretext.AudioBlock;

/**
 * 通用视图帮助类
 */
public class UIFragmentHelper extends BaseUIFragmentHelper {

    public UIFragmentHelper(BaseUIFragment<?> fragment) {
        super(fragment);
    }

    @Override
    public ViewBuilder getViewBuilder() {
        return new BoxViewBuilder();
    }

    @Override
    public void setVisibleToUser(boolean visible) {
        super.setVisibleToUser(visible);
    }

    public BoxTitleBar getTitleBar() {
        return (BoxTitleBar) getBaseUIFragment().getTitleBar();
    }

    public BoxEmptyView getEmptyView() {
        return (BoxEmptyView) getBaseUIFragment().getEmptyView();
    }

    public BoxLoadingView getLoadingView() {
        return (BoxLoadingView) getBaseUIFragment().getLoadingView();
    }

    public void stopPlay() {
        PlayerBusService playerBusService = (PlayerBusService) getBaseUIFragment()
                .getSystemService(PlayerBusService.BUS_SERVICE_NAME);
        if (playerBusService != null) {
            try {
                playerBusService.pause();
                AudioBlock.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
