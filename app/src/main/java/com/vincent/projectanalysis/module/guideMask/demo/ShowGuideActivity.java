package com.vincent.projectanalysis.module.guideMask.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.vincent.projectanalysis.module.guideMask.base.GuideBuilder;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.AppPreferences;
import com.vincent.projectanalysis.utils.UiThreadHandler;

/**
 * Created by dengfa on 17/3/21.
 */
public class ShowGuideActivity extends Activity {

    private static final String SP_IS_ABILITY_GUIDE_START         = "sp_is_ability_guide_start";
    private static final String SP_IS_ABILITY_GUIDE_END           = "sp_is_ability_guide_end";
    private static final String SP_IS_ABILITY_GUIDE_ABILITY_VALUE = "sp_is_ability_guide_ability_value";
    private static final String SP_IS_ABILITY_GUIDE_CARD          = "sp_is_ability_guide_card";
    private static final String SP_IS_ABILITY_GUIDE_CRYSTAL       = "sp_is_ability_guide_crystal";
    private Activity mActivity;
    private TextView mTvA;
    private TextView mTvB;
    private TextView mTvC;
    private TextView mTvD;
    private TextView mTvE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_guide_activity);
        mTvA = (TextView) findViewById(R.id.tv_A);
        mTvB = (TextView) findViewById(R.id.tv_B);
        mTvC = (TextView) findViewById(R.id.tv_C);
        mTvD = (TextView) findViewById(R.id.tv_D);
        mTvE = (TextView) findViewById(R.id.tv_E);
        mActivity = this;
        AppPreferences.getPreferences().removeAllPreference();
        showGuidePage();
    }

    private void showGuidePage() {
        if (AppPreferences.getBoolean(SP_IS_ABILITY_GUIDE_START, true)) {
            UiThreadHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GuideBuilder builder = new GuideBuilder(mActivity);
                    builder.setTargetView(mTvA)
                            .setAlpha(180)
                            .setHighTargetCorner(1)
                            .addComponent(new AbilityStartGuideComponent())
                            .setOnVisibilityChangedListener(mGuideListener, SP_IS_ABILITY_GUIDE_START)
                            .show(mActivity);
                }
            }, 500);
        }
    }

    private GuideBuilder.OnVisibleChangeListener mGuideListener = new GuideBuilder.OnVisibleChangeListener() {

        @Override
        public void onShown(String type) {
        }

        @Override
        public void onDismiss(String type) {
            switch (type) {
                case SP_IS_ABILITY_GUIDE_START: {
                    AppPreferences.setBoolean(SP_IS_ABILITY_GUIDE_START, false);
                    if (AppPreferences.getBoolean(SP_IS_ABILITY_GUIDE_CRYSTAL, true)) {
                        GuideBuilder builder = new GuideBuilder(mActivity);
                        builder.setTargetView(mTvB)
                                .setAlpha(180)
                                .setHighTargetCorner(10)
                                .addComponent(new AbilityCrystalGuideComponent())
                                .setOnVisibilityChangedListener(mGuideListener, SP_IS_ABILITY_GUIDE_CRYSTAL)
                                .show(mActivity);
                    }
                    break;
                }
                case SP_IS_ABILITY_GUIDE_CRYSTAL: {
                    AppPreferences.setBoolean(SP_IS_ABILITY_GUIDE_CRYSTAL, false);
                    if (AppPreferences.getBoolean(SP_IS_ABILITY_GUIDE_CARD, true)) {
                        GuideBuilder builder = new GuideBuilder(mActivity);
                        builder.setTargetView(mTvC)
                                .setAlpha(180)
                                .setHighTargetCorner(1)
                                .setTargetPadding(-10, 0, -10, 0)
                                .addComponent(new AbilityCardGuideComponent())
                                .setOnVisibilityChangedListener(mGuideListener, SP_IS_ABILITY_GUIDE_CARD)
                                .show(mActivity);
                    }
                    break;
                }
                case SP_IS_ABILITY_GUIDE_CARD: {
                    AppPreferences.setBoolean(SP_IS_ABILITY_GUIDE_CARD, false);
                    if (AppPreferences.getBoolean(SP_IS_ABILITY_GUIDE_ABILITY_VALUE, true)) {
                        GuideBuilder builder = new GuideBuilder(mActivity);
                        builder.setTargetView(mTvD)
                                .setAlpha(180)
                                .setHighTargetCorner(3)
                                .addComponent(new AbilityValueGuideComponent())
                                .setOnVisibilityChangedListener(mGuideListener, SP_IS_ABILITY_GUIDE_ABILITY_VALUE)
                                .show(mActivity);
                    }
                    break;
                }

                case SP_IS_ABILITY_GUIDE_ABILITY_VALUE: {
                    AppPreferences.setBoolean(SP_IS_ABILITY_GUIDE_ABILITY_VALUE, false);
                    GuideBuilder builder = new GuideBuilder(mActivity);
                    builder.setTargetView(mTvE)
                            .setAlpha(180)
                            .setHighTargetCorner(10)
                            .addComponent(new AbilityEndGuideComponent())
                            .setOnVisibilityChangedListener(mGuideListener, SP_IS_ABILITY_GUIDE_END)
                            .show(mActivity);
                    break;
                }

                case SP_IS_ABILITY_GUIDE_END:
                    AppPreferences.setBoolean(SP_IS_ABILITY_GUIDE_END, false);
                    break;
            }
        }
    };
}
