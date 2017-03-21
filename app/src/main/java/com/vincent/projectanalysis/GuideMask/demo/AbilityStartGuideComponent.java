package com.vincent.projectanalysis.GuideMask.demo;

import android.view.LayoutInflater;
import android.view.View;

import com.vincent.projectanalysis.GuideMask.base.GuideComponent;
import com.vincent.projectanalysis.R;

public class AbilityStartGuideComponent implements GuideComponent {
    @Override
    public View getView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ability_guide_start, null);
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
