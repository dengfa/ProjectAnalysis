package com.vincent.projectanalysis.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vincent.interview.android.reconstructure.ReconstructureActivity;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.activity.DemoActivity;
import com.vincent.projectanalysis.activity.FragmentTestActivity;
import com.vincent.projectanalysis.activity.MyMapSceneActivity;
import com.vincent.projectanalysis.utils.LogUtil;

public class Main2Fragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;

    private String[] tabs = {
            "MyMapSceneActivity",
            "DemoActivity",
            "FragmentTestActivity",
            "ReconstructureActivity"
    };
    private String   mTest;

    public Main2Fragment() {
        super();
    }

    public void setArgs(String test) {
        mTest = test;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onAttach");
        FragmentActivity activity = getActivity();
        LogUtil.d("vincent", "test = " + mTest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onActivityCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onDetach");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onCreateView");
        return inflater.inflate(R.layout.knowbox_main_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d("vincent", this.getClass().getSimpleName() + " onViewCreated");
        mListView = (ListView) view.findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), MyMapSceneActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), DemoActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), FragmentTestActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ReconstructureActivity.class));
                break;
        }
    }
}