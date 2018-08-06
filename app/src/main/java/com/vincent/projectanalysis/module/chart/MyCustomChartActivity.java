package com.vincent.projectanalysis.module.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;

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
        float[] stepValues = {10, 20, 30, 60};
        String[] group = {"您20%", "天津市平均 50%"};
        float[] groupValus = {20, 50};
        chart3.setStepConfig(steps, stepColors, stepValues);
        chart3.setData(group, groupValus);
    }
}
