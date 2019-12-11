package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.fragment.Main3Fragment;
import com.vincent.projectanalysis.fragment.Main4Fragment;
import com.vincent.projectanalysis.fragment.Main5Fragment;

public class FragmentTestActivity extends AppCompatActivity {

    private FragmentManager mManager;
    private Main5Fragment mFragment5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        mManager = getSupportFragmentManager();
        Main3Fragment main3Fragment = new Main3Fragment();
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.rl_container, main3Fragment);
        transaction.addToBackStack("2");
        transaction.commit();
        mFragment5 = new Main5Fragment();
    }

    public void next(View view) {
        mManager.beginTransaction()
                .replace(R.id.rl_container, new Main4Fragment())
                .attach(mFragment5)
                .show(mFragment5)
                .addToBackStack("1")
                .commit();
    }

    public void pop(View view) {
        mManager.popBackStack("2", 0);
    }
}
