package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.view.View;

import com.hyena.framework.app.fragment.AnimType;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.knowbox.base.UIFragmentHelper;
import com.vincent.projectanalysis.widgets.SnowFall;
import com.vincent.projectanalysis.widgets.VerticalProgressWithIndicatorView;

/**
 * Created by dengfa on 2018/12/12.
 * 沉浸式适配
 */

public class HomeworkResultDialogFragment extends BaseUIFragment<UIFragmentHelper> {

    public VerticalProgressWithIndicatorView mProgress;
    public SnowFall mSnowView;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(false);
        setAnimationType(AnimType.ANIM_NONE);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_reault_dialog, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        mSnowView = view.findViewById(R.id.snow_view);
        mProgress = view.findViewById(R.id.progress);
        mProgress.setData(3, 10, 90);
        playWinSnow();
    }

    private void playWinSnow() {
        mSnowView.setSnowRes(new int[]{R.drawable.exercise_pk_result_particle_0,
                R.drawable.exercise_pk_result_particle_1,
                R.drawable.exercise_pk_result_particle_2,
                R.drawable.exercise_pk_result_particle_3,
                R.drawable.exercise_pk_result_particle_4});
        mSnowView.snow(2);
    }
}
