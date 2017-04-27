package com.vincent.projectanalysis.module.guideMask.demo;

import android.view.LayoutInflater;
import android.view.View;

import com.vincent.projectanalysis.module.guideMask.base.GuideComponent;
import com.vincent.projectanalysis.R;

public class AbilityValueGuideComponent implements GuideComponent {
    @Override
    public View getView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ability_guide_value, null);
    }

    @Override
    public int getAnchor() {
        return GuideComponent.ANCHOR_TOP;
    }

    @Override
    public int getFitPosition() {
        return GuideComponent.FIT_END;
    }

    @Override
    public int getXOffset() {
        return 50;
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
