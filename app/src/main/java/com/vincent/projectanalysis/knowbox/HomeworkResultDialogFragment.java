package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.view.View;

import com.hyena.framework.app.fragment.AnimType;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.clientlog.LogUtil;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.knowbox.base.UIFragmentHelper;
import com.vincent.projectanalysis.widgets.SnowFall;
import com.vincent.projectanalysis.widgets.VerticalProgressWithIndicatorView;

/**
 * Created by dengfa on 2018/12/12.
 * 沉浸式适配
 */

public class HomeworkResultDialogFragment extends BaseUIFragment<UIFragmentHelper> implements View.OnClickListener {

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
        view.findViewById(R.id.tv_continue).setOnClickListener(this);
        mProgress.setData(3, 10, 90);
        playWinSnow();
        LogUtil.d("vincent", "HomeworkResultDialogFragment onViewCreatedImpl");
    }

    @Override
    public void setVisibleToUser(boolean visible) {
        super.setVisibleToUser(visible);
        LogUtil.d("vincent", "HomeworkResultDialogFragment setVisibleToUser - "  + visible);
    }

    @Override
    public void onResumeImpl() {
        super.onResumeImpl();
        LogUtil.d("vincent", "HomeworkResultDialogFragment onResumeImpl");
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
