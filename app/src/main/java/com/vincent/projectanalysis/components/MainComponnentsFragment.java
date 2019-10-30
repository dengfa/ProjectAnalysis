package com.vincent.projectanalysis.components;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.activity.BlinkTagActivity;
import com.vincent.projectanalysis.activity.ChartActivity;
import com.vincent.projectanalysis.activity.ClipRevealActivity;
import com.vincent.projectanalysis.activity.CountDownActivity;
import com.vincent.projectanalysis.activity.GuideActivity;
import com.vincent.projectanalysis.activity.HomeworkCheckActivity;
import com.vincent.projectanalysis.activity.LevelProgressActivity;
import com.vincent.projectanalysis.activity.LottieActivity;
import com.vincent.projectanalysis.activity.OrderHomeworkActivity;
import com.vincent.projectanalysis.activity.RippleAndWaveActivity;
import com.vincent.projectanalysis.activity.ScanActivity;
import com.vincent.projectanalysis.activity.WidgetsCollectionsActivity;
import com.vincent.projectanalysis.module.guideMask.demo.ShowGuideActivity;
import com.vincent.projectanalysis.module.mapScene.MapSceneActivity;

/**
 * Created by dengfa on 2018/12/14.
 */

public class MainComponnentsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;

    private String[] tabs = {
            "KnowBox",
            "地图场景",
            "RippleView",
            "LevelProgress",
            "ClipReveal",
            "CountDown",
            "HomeworkCheckActivity",
            "ScanActivity",
            "控件集合",
            "BlinkTag",
            "Guide",
            "LottieAnimation",
            "Chart",
            "Order Homework",
            "引导遮罩"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.knowbox_main_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //startActivity(new Intent(getActivity(), KnowBoxMainActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), MapSceneActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), RippleAndWaveActivity.class));
                break;
            case 3:
                startActivity(new Intent(getContext(), LevelProgressActivity.class));
                break;
            case 4:
                startActivity(new Intent(getContext(), ClipRevealActivity.class));
                break;
            case 5:
                startActivity(new Intent(getContext(), CountDownActivity.class));
                break;
            case 6:
                startActivity(new Intent(getContext(), HomeworkCheckActivity.class));
                break;
            case 7:
                startActivity(new Intent(getContext(), ScanActivity.class));
                break;
            case 8:
                startActivity(new Intent(getContext(), WidgetsCollectionsActivity.class));
                break;
            case 9:
                startActivity(new Intent(getContext(), BlinkTagActivity.class));
                break;
            case 10:
                startActivity(new Intent(getContext(), GuideActivity.class));
                break;
            case 11:
                startActivity(new Intent(getContext(), LottieActivity.class));
                break;
            case 12:
                startActivity(new Intent(getContext(), ChartActivity.class));
                break;
            case 13:
                startActivity(new Intent(getContext(), OrderHomeworkActivity.class));
                break;
            case 14:
                startActivity(new Intent(getContext(), ShowGuideActivity.class));
                break;
        }
    }
}
