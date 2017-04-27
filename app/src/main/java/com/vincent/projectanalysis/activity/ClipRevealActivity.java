package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.module.ClipReveal.BrightSideFragment;

/**
 * Created by dengfa on 17/4/27.
 */

public class ClipRevealActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_clip_reveal);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_upward, 0)
                .add(R.id.container, BrightSideFragment.newInstance(), "bright")
                .commit();
    }
}
