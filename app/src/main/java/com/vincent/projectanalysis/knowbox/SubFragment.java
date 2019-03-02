package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.hyena.framework.app.fragment.BaseUIFragment;
import com.vincent.projectanalysis.R;

/**
 * Created by dengfa on 2019/1/23.
 */

public class SubFragment extends BaseUIFragment {

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_sub, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        HomeworkResultDialogFragment fragment = (HomeworkResultDialogFragment) newFragment(getContext(),
                HomeworkResultDialogFragment.class);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
