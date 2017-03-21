package com.vincent.projectanalysis.GuideMask.demo;

import android.view.LayoutInflater;
import android.view.View;

import com.vincent.projectanalysis.GuideMask.base.GuideComponent;
import com.vincent.projectanalysis.R;

public class AbilityCardGuideComponent implements GuideComponent {
    @Override
    public View getView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ability_guide_card, null);
    }

    @Override
    public int getAnchor() {
        return GuideComponent.ANCHOR_RIGHT;
    }

    @Override
    public int getFitPosition() {
        return GuideComponent.FIT_END;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 0;
    }

    @Override
    public boolean getTargetIsCover() {
        return false;
    }
}
