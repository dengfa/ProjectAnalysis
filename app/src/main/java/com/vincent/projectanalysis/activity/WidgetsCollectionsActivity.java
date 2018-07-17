package com.vincent.projectanalysis.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.widgets.TagView;
import com.vincent.projectanalysis.widgets.VerifyCode2View;

import java.util.ArrayList;

public class WidgetsCollectionsActivity extends AppCompatActivity {

    private VerifyCode2View mCodeView;

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

        mCodeView = (VerifyCode2View) findViewById(R.id.code_view);
        mCodeView.setInputCompleteListener(new VerifyCode2View.InputCompleteListener() {
            @Override
            public void inputComplete() {
                String content = mCodeView.getTextContent();
                if (content.length() > 3) {
                    mCodeView.clearAllText();
                }
            }

            @Override
            public void deleteContent() {

            }
        });
    }
}
