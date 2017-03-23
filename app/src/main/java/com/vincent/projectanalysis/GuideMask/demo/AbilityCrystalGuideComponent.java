package com.vincent.projectanalysis.guideMask.demo;

import android.view.LayoutInflater;
import android.view.View;

import com.vincent.projectanalysis.guideMask.base.GuideComponent;
import com.vincent.projectanalysis.R;

public class AbilityCrystalGuideComponent implements GuideComponent {
    @Override
    public View getView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ability_guide_crystal, null);
    }

    @Override
    public int getAnchor() {
        return GuideComponent.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return GuideComponent.FIT_END;
    }

    @Override
    public int getXOffset() {
        return -70;
    }

    @Override
    public int getYOffset() {
        return 20;
    }

    @Override
    public boolean getTargetIsCover() {
        return false;
    }
}
