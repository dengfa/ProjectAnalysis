package com.vincent.projectanalysis.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

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


        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);

        SpannableString spannableString = new SpannableString("魔法学院涵盖数学十大核心素养，分领域逐层次，细化数学知识，全面提升数学核心能力。");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 5, 14, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 5, 14, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 29, 33, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 29, 33, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv1.setText(spannableString);

        SpannableString spannableString1 = new SpannableString("（1）数学与运算能力\n（2）代数表达与抽象能力\n（3）实践与综合应用能力\n（4）几何直观与想象能力\n（5）数据分析能力 ");
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 3, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 3, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 14, 24, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 14, 24, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 27, 37, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 27, 37, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 40, 50, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 40, 50, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 53, 59, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 53, 59, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv2.setText(spannableString1);

        SpannableString spannableString2 = new SpannableString("这五大能力都是小学数学必须具备的能力，每个必考，每个必会。只要掌握了这5个能力，数学的学习能力极强，数学必然取得好成绩。");
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 7, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString2.setSpan(new StyleSpan(Typeface.BOLD), 7, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#fca93a")), 50, 59, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString2.setSpan(new StyleSpan(Typeface.BOLD), 50, 59, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv3.setText(spannableString2);
    }
}
