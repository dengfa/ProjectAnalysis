package com.vincent.interview.android.reconstructure;

import android.content.Context;

import com.vincent.interview.android.reconstructure.base.AbsQuestionView;

/**
 * Created by dengfa on 2019-12-15.
 * Des:
 */
public class QuestionViewFactory {

    public static AbsQuestionView createQuestionView(Context context, int type) {
        switch (type) {
            case 1:
                return new AQuestionView(context);
            case 2:
                return new BQuestionView(context);
            default:
                return new AQuestionView(context);
        }
    }
}
