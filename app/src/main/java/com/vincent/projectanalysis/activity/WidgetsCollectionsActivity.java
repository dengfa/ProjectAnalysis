package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.TagView;

import java.util.ArrayList;

public class WidgetsCollectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_view);

        TagView tagView = (TagView) findViewById(R.id.tag_view);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("dl");
        tags.add("点击防守");
        tags.add("电风扇");
        tags.add("斯蒂芬斯蒂");
        tags.add("电风扇");
        tags.add("斯蒂芬斯蒂");
        tagView.setTags(tags);
    }
}
