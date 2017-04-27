package com.vincent.projectanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vincent.projectanalysis.activity.ClipRevealActivity;
import com.vincent.projectanalysis.activity.TestActivity;
import com.vincent.projectanalysis.module.guideMask.demo.ShowGuideActivity;
import com.vincent.projectanalysis.java.Json;
import com.vincent.projectanalysis.module.mapScene.MapSceneActivity;
import com.vincent.projectanalysis.activity.LevelProgressActivity;
import com.vincent.projectanalysis.activity.RippleAndWaveActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private String[] tabs = {"引导遮罩","地图场景","RippleView","LevelProgress","ClipReveal","other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);

        Json.jsonArrayTest();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, ShowGuideActivity.class));
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
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
        }
    }
}
