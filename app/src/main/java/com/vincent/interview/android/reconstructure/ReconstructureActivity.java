package com.vincent.interview.android.reconstructure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vincent.projectanalysis.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReconstructureActivity extends AppCompatActivity {

    @BindView(R.id.rv_question)
    RecyclerView rvQuestion;

    //private NormalAdapter mAdapter;
    private XQuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconstructure);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        //mAdapter = new NormalAdapter(this);
        mAdapter = new XQuestionAdapter(this);
        mAdapter.setDisPlayStratege(new SDisplayStratege());
        rvQuestion.setLayoutManager(new LinearLayoutManager(this));
        rvQuestion.setAdapter(mAdapter);
    }

    private void initData() {
        ArrayList<QuestionData> questionData = new ArrayList<>();
        questionData.add(new QuestionData(1, "qdjg\nquestion\nData"));
        questionData.add(new QuestionData(2, "qdjg"));
        questionData.add(new QuestionData(3, "qdjg\nquestion\nData\nsdsfsdfsdfsfsd"));
        questionData.add(new QuestionData(2, "qdjg"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nData\nsdsfsdfsdfsfsd"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\n"));
        questionData.add(new QuestionData(1, "qdjg\nquestion\nData\nsdsfsdfsdfsfsd"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nData"));
        questionData.add(new QuestionData(3, "qdjg\nquestion\nData\nsdfsdf"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nData"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd"));
        questionData.add(new QuestionData(1, "qdjg\nquestion"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd\nsdfsdf"));
        questionData.add(new QuestionData(3, "qdjg"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd"));
        questionData.add(new QuestionData(1, "qdjg\nquestion"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd\nsdfsdf"));
        questionData.add(new QuestionData(3, "qdjg"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd"));
        questionData.add(new QuestionData(1, "qdjg\nquestion"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd\nsdfsdf"));
        questionData.add(new QuestionData(3, "qdjg"));
        questionData.add(new QuestionData(2, "qdjg\nquestion\nDatas\nsdd"));
        mAdapter.setDatas(questionData);
    }
}
