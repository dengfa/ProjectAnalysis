package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.module.ClipReveal.BrightSideFragment;
import com.vincent.projectanalysis.module.ClipReveal.DarkSideFragment;
import com.vincent.projectanalysis.module.ClipReveal.GreetingsFragment;
import com.vincent.projectanalysis.module.ClipReveal.interfaces.DemoActivityInterface;
import com.vincent.projectanalysis.module.ClipReveal.interfaces.GreetingFragmentInterface;

import java.util.List;

/**
 * Created by dengfa on 17/4/27.
 */

public class ClipRevealActivity extends AppCompatActivity implements DemoActivityInterface,
        GreetingFragmentInterface {

    private DarkSideFragment  mDarkFragment;
    private GreetingsFragment mGreetingsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_reveal);
        showGreetings();
    }


    @Override
    public void goToSide(int cx, int cy, boolean appBarExpanded, String side) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mDarkFragment = DarkSideFragment.newInstance(cx, cy, appBarExpanded);
        Fragment fragment;
        switch (side) {
            case "bright":
                fragment = BrightSideFragment.newInstance(cx, cy, appBarExpanded);
                break;
            case "dark":
                fragment = DarkSideFragment.newInstance(cx, cy, appBarExpanded);
                break;
            default:
                throw new IllegalStateException();
        }
        ft.add(R.id.container, fragment, side).commit();
    }

    @Override
    public void removeAllFragmentExcept(@Nullable String tag) {
        List<Fragment> frags = getSupportFragmentManager().getFragments();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment frag;
        for (int i = 0; i < frags.size(); i++) {
            frag = frags.get(i);
            if (frag == null) {
                continue;
            }
            if (tag == null || !tag.equals(frag.getTag())) {
                ft.remove(frag);
            }
        }
        ft.commit();
    }

    private void showGreetings() {
        mGreetingsFragment = GreetingsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_downward, 0, R.anim.slide_downward, 0)
                .add(R.id.container, mGreetingsFragment, "greetings")
                .commit();
    }

    @Override
    public void onSetupProfileClick() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_upward, 0)
                        .add(R.id.container, BrightSideFragment.newInstance(), "bright")
                        .commit();
            }
        });
    }
}
