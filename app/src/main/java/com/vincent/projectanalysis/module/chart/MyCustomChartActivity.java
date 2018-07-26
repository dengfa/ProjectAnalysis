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
        chart.setData(0.12f, 0.18f, 0.7f, 0.5f, 0.2f, 0.3f);

        HorizontalChartView chart2 = findViewById(R.id.chart2);
        float[] datasA = {1f, 0.9f, 0.6f, 0.2f};
        float[] datasB = {1f, 0.8f, 0.5f, 0.3f};
        chart2.setData(datasA, datasB);
    }
}
