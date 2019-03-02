package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hyena.framework.annotation.AttachViewId;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.vincent.projectanalysis.R;

import java.util.ArrayList;

/**
 * Created by dengfa on 2019/1/23.
 */

public class TestSubFragment extends BaseUIFragment implements View.OnClickListener {

    @AttachViewId(R.id.vp_main_study)
    ViewPager mViewPager;
    @AttachViewId(R.id.tab_study_center)
    TextView mTabStudyCenter;
    @AttachViewId(R.id.tab_student_homework)
    TextView mTabStudentHomework;
    @AttachViewId(R.id.indicator_student_homework)
    View mIndicatorStudentHomework;
    @AttachViewId(R.id.indicator_study_center)
    View mIndicatorStudyCenter;

    private ArrayList<BaseUIFragment> mSubFragments = new ArrayList<>(2);
    private MainStudyFragmentAdapter mFragmentAdapter;
    private int mCurrentTab = -1;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_test_sub, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        mTabStudyCenter.setOnClickListener(this);
        mTabStudentHomework.setOnClickListener(this);
        mSubFragments.add(newFragment(getContext(), HomeworkResultDialogFragmentV2.class));
        mSubFragments.add(newFragment(getContext(), SubFragment.class));
        mFragmentAdapter = new MainStudyFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabSelected(position);
            }
        });
        tabSelected(0);
        mViewPager.setCurrentItem(0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_study_center:
                tabSelected(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_student_homework:
                tabSelected(1);
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    private void tabSelected(int tab) {
        if (mCurrentTab == tab) {
            return;
        }
        switch (tab) {
            case 0:
                mTabStudyCenter.setSelected(true);
                mTabStudyCenter.setTextSize(24);
                mIndicatorStudyCenter.setVisibility(View.VISIBLE);
                mTabStudentHomework.setSelected(false);
                mTabStudentHomework.setTextSize(16);
                mIndicatorStudentHomework.setVisibility(View.GONE);
                break;
            default:
                mTabStudyCenter.setSelected(false);
                mTabStudyCenter.setTextSize(16);
                mIndicatorStudyCenter.setVisibility(View.GONE);
                mTabStudentHomework.setSelected(true);
                mTabStudentHomework.setTextSize(24);
                mIndicatorStudentHomework.setVisibility(View.VISIBLE);
        }
        mCurrentTab = tab;
    }

    private class MainStudyFragmentAdapter extends FragmentPagerAdapter {

        public MainStudyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public String[] titles = new String[]{"学习", "学生练习"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mSubFragments == null ? null : mSubFragments.get(position);
        }

        @Override
        public int getCount() {
            return mSubFragments == null ? 0 : mSubFragments.size();
        }
    }
}
