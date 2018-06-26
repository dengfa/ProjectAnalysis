package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.module.homeworkCheck.HomeworkCheckView;

public class HomeworkCheckActivity extends AppCompatActivity {

    private HomeworkCheckView mHomeworkCheckView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_check);

        mHomeworkCheckView = (HomeworkCheckView) findViewById(R.id.hc_view);
        mHomeworkCheckView.setData();
    }
}
