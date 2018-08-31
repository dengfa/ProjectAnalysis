package com.vincent.projectanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vincent.projectanalysis.activity.BlinkTagActivity;
import com.vincent.projectanalysis.activity.ChartActivity;
import com.vincent.projectanalysis.activity.ClipRevealActivity;
import com.vincent.projectanalysis.activity.CountDownActivity;
import com.vincent.projectanalysis.activity.GuideActivity;
import com.vincent.projectanalysis.activity.HomeworkCheckActivity;
import com.vincent.projectanalysis.knowbox.KnowBoxMainActivity;
import com.vincent.projectanalysis.activity.LevelProgressActivity;
import com.vincent.projectanalysis.activity.LottieActivity;
import com.vincent.projectanalysis.activity.OrderHomeworkActivity;
import com.vincent.projectanalysis.activity.RippleAndWaveActivity;
import com.vincent.projectanalysis.activity.ScanActivity;
import com.vincent.projectanalysis.activity.WidgetsCollectionsActivity;
import com.vincent.projectanalysis.java.Json;
import com.vincent.projectanalysis.java.Test;
import com.vincent.projectanalysis.module.guideMask.demo.ShowGuideActivity;
import com.vincent.projectanalysis.module.mapScene.MapSceneActivity;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.MD5Util;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_naivigate);
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(this, R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);
        Json.jsonArrayTest();

        String packageMd5 = MD5Util.encode("com.alibaba.android.rimet" + ".box");
        LogUtil.d("vincent", "packageMd5 - " + packageMd5);

        Test.test();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, KnowBoxMainActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, MapSceneActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, RippleAndWaveActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, LevelProgressActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, ClipRevealActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, CountDownActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, HomeworkCheckActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, WidgetsCollectionsActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, BlinkTagActivity.class));
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, GuideActivity.class));
                break;
            case 11:
                startActivity(new Intent(MainActivity.this, LottieActivity.class));
                break;
            case 12:
                startActivity(new Intent(MainActivity.this, ChartActivity.class));
                break;
            case 13:
                startActivity(new Intent(MainActivity.this, OrderHomeworkActivity.class));
                break;
            case 14:
                startActivity(new Intent(MainActivity.this, ShowGuideActivity.class));
                break;
        }
    }
}
