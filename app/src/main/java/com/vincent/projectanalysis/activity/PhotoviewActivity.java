package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.photoviewextend.PhotoView;

public class PhotoviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);

        PhotoView pv = findViewById(R.id.pv);
        pv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(R.drawable.anakin).into(pv);
    }
}
