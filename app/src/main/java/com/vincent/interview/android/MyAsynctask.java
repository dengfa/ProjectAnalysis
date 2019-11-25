package com.vincent.interview.android;

import android.os.AsyncTask;

import com.vincent.projectanalysis.utils.LogUtil;

/**
 * Created by dengfa on 2019-11-25.
 * Des:
 */
public class MyAsynctask extends AsyncTask {

    private int start;

    public MyAsynctask() {

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        start = 1000000;
        while (!isCancelled() && start > 0) {
            LogUtil.d("vincent", "doInBackground - " + start);
            start--;
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        LogUtil.d("vincent", "onCancelled - " + start);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        LogUtil.d("vincent", "onPostExecute - " + start);
    }
}
