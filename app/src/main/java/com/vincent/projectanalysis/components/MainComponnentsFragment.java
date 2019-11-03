package com.vincent.projectanalysis.components;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.vincent.projectanalysis.activity.PhotoviewActivity;
import com.vincent.projectanalysis.activity.RippleAndWaveActivity;
import com.vincent.projectanalysis.activity.ScanActivity;
import com.vincent.projectanalysis.activity.WidgetsCollectionsActivity;
import com.vincent.projectanalysis.module.guideMask.demo.ShowGuideActivity;
import com.vincent.projectanalysis.module.mapScene.MapSceneActivity;

/**
 * Created by dengfa on 2018/12/14.
 */

public class MainComponnentsFragment extends Fragment {

    private RecyclerView mRecyclerView;

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
            "引导遮罩",
            "PhotoviewActivity"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.lv);
        ComponentAdapter adapter = new ComponentAdapter(tabs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder> {

        private final String[] mDatas;

        public ComponentAdapter(String[] datas) {
            mDatas = datas;
        }

        @NonNull
        @Override
        public ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ComponentViewHolder(View.inflate(getContext(), R.layout.item_main_listview, null));
        }

        @Override
        public void onBindViewHolder(@NonNull ComponentViewHolder holder, final int position) {
            holder.mTab.setText(mDatas[position]);
            holder.mTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.length;
        }

        public class ComponentViewHolder extends RecyclerView.ViewHolder {

            public TextView mTab;

            public ComponentViewHolder(View itemView) {
                super(itemView);
                mTab = itemView.findViewById(R.id.tv_item);
            }
        }
    }

    public void onItemClick(int position) {
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
            case 15:
                startActivity(new Intent(getContext(), PhotoviewActivity.class));
                break;
        }
    }
}
