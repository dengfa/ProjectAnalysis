package com.vincent.projectanalysis.knowbox;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyena.framework.app.adapter.SingleTypeAdapter;
import com.hyena.framework.app.fragment.ListFragment;
import com.hyena.framework.datacache.BaseObject;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.knowbox.base.UIFragmentHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengfa on 2019/1/22.
 */

public class TestListFragment extends ListFragment<UIFragmentHelper, String> {


    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.anakin);
        mSrlPanel.addView(imageView, 0);
    }

    @Override
    protected SingleTypeAdapter<String> buildListAdapter() {
        return new TestAdapter(getContext());
    }

    @Override
    public List<String> convertData2List(BaseObject baseObject) {
        ArrayList<String> strings = new ArrayList<>();
        return strings;
    }

    class TestAdapter extends SingleTypeAdapter<String> {

        public TestAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setText("test test");
            return textView;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
