package com.vincent.projectanalysis.module.guideMask.demo;

import android.view.LayoutInflater;
import android.view.View;

import com.vincent.projectanalysis.module.guideMask.base.GuideComponent;
import com.vincent.projectanalysis.R;

public class AbilityEndGuideComponent implements GuideComponent {
    @Override
    public View getView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ability_guide_end, null);
    }

    @Override
    public int getAnchor() {
        return GuideComponent.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return GuideComponent.FIT_START;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return -1;
    }

    @Override
    public boolean getTargetIsCover() {
        return false;
    }
}
