package com.vincent.projectanalysis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;

public class DraftPaperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_paper);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("vincent", "DraftPaperActivity onDestroy");
    }

    public void bv_click(View view) {
        startActivity(new Intent(this, GuideActivity.class));
    }
}
