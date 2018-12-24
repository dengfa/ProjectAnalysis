package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.view.View;

import com.hyena.framework.app.fragment.BaseUIFragment;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.knowbox.base.UIFragmentHelper;
import com.vincent.projectanalysis.widgets.VerticalProgressWithIndicatorView;

/**
 * Created by dengfa on 2018/12/12.
 * 沉浸式适配
 */

public class HomeworkResultDialogFragment extends BaseUIFragment<UIFragmentHelper> {

    VerticalProgressWithIndicatorView mProgress;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(true);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_reault_dialog, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        mProgress = view.findViewById(R.id.progress);
        mProgress.setData(3, 10, 90);
    }
}
