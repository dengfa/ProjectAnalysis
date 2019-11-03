package com.vincent.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class LeakActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        sContext = this;
        findViewById(R.id.tv_unleak).setOnClickListener(this);

        ArrayList<String> arrayList = new ArrayList<>();
        Vector<String> vector = new Vector<>();
        LinkedList<String> linkedList = new LinkedList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unleak:
                sContext = null;
                break;
        }
    }
}
