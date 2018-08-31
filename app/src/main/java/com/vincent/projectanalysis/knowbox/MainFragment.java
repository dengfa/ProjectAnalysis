package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.app.fragment.BaseUIFragmentHelper;
import com.hyena.framework.utils.ToastUtils;
import com.vincent.projectanalysis.R;

/**
 * Created by dengfa on 2018/8/31.
 */

public class MainFragment extends BaseUIFragment<BaseUIFragmentHelper> implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private String[] tabs = {
            "pie chart", "HorizontalBarChartActivity", "MyCustomChart"};

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(true);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.knowbox_main_fragment, null);
    }

    @Override
    public void onViewCreatedImpl(final View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtils.showShortToast(getContext(), tabs[position]);
        switch (position) {
            case 0:
                break;
        }
    }
}
