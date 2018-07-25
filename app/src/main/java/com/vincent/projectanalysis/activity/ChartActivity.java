package com.vincent.projectanalysis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.module.chart.HorizontalBarChartActivity;
import com.vincent.projectanalysis.module.chart.MyCustomChartActivity;
import com.vincent.projectanalysis.module.chart.PieChartActivity;

public class ChartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private String[] tabs = {
            "pie chart", "HorizontalBarChartActivity","MyCustomChart"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(this, R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(ChartActivity.this, PieChartActivity.class));
                break;
            case 1:
                startActivity(new Intent(ChartActivity.this, HorizontalBarChartActivity.class));
                break;
            case 2:
                startActivity(new Intent(ChartActivity.this, MyCustomChartActivity.class));
                break;
        }
    }
}
