package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.view.View;

import com.hyena.framework.app.fragment.AnimType;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.knowbox.base.UIFragmentHelper;
import com.vincent.projectanalysis.widgets.SnowFall;
import com.vincent.projectanalysis.widgets.VerticalProgressWithIndicatorViewV2;

/**
 * Created by dengfa on 2018/12/12.
 * 沉浸式适配
 */

public class HomeworkResultDialogFragmentV2 extends BaseUIFragment<UIFragmentHelper> implements View.OnClickListener {

    public VerticalProgressWithIndicatorViewV2 mProgress;
    public SnowFall mSnowView;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(false);
        setAnimationType(AnimType.ANIM_NONE);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_reault_dialog_v2, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        mSnowView = view.findViewById(R.id.snow_view);
        mProgress = view.findViewById(R.id.progress);
        mProgress.setData(3, 90, 80);
        playWinSnow();
        view.findViewById(R.id.tv_continue).setOnClickListener(this);
    }

    private void playWinSnow() {
        mSnowView.setSnowRes(new int[]{R.drawable.exercise_pk_result_particle_0,
                R.drawable.exercise_pk_result_particle_1,
                R.drawable.exercise_pk_result_particle_2,
                R.drawable.exercise_pk_result_particle_3,
                R.drawable.exercise_pk_result_particle_4});
        mSnowView.snow(2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_continue:
                mProgress.setData(3, 10, 90);
                break;
        }
    }
}
