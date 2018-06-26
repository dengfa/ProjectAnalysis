package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.vincent.projectanalysis.R;

public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_splash_ani);
        lottieAnimationView.playAnimation();
    }
}
