package com.vincent.projectanalysis.module.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;

import java.util.ArrayList;

public class MyCustomChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_chart);

        VerticalChartView chart = findViewById(R.id.chart);
        chart.setData(0.02f, 0.01f, 0.96f, 0.5f, 0.2f, 0.29f);

        HorizontalChartView chart2 = findViewById(R.id.chart2);
        float[] datasA = {1f, 1f, 0.00f, 0.02f};
        float[] datasB = {1f, 0.9f, 0.01f, 0.05f};
        chart2.setData(datasA, datasB);

        ProcessComparisonView chart3 = findViewById(R.id.chart3);
        String[] steps = {"不足", "正常", "良好", "优秀"};
        int[] stepColors = {0xffff706f, 0xffffb800, 0xff7ed321, 0xff7ed399};
        float[] stepValues = {10, 12, 30, 32};
        String[] group = {"您20%", "天津市平均 50%"};
        float[] groupValus = {20, 50};
        chart3.setStepConfig(steps, stepColors, stepValues);
        chart3.setData(group, groupValus);

        ProcessComparisonView2 chart4 = findViewById(R.id.chart4);
        ArrayList<String> steps1 = new ArrayList<>();
        steps1.add("不足");
        steps1.add("正常");
        steps1.add("良好");
        ArrayList<Integer> stepValues1 = new ArrayList<>();
        stepValues1.add(10);
        stepValues1.add(30);
        ArrayList<String> group1 = new ArrayList<>();
        group1.add("您20%");
        group1.add("天津市平均 99%");
        ArrayList<Integer> groupValus1 = new ArrayList<>();
        groupValus1.add(20);
        groupValus1.add(90);
        chart4.setData(steps1, stepValues1, group1, groupValus1);
    }
}
