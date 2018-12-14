package com.vincent.projectanalysis;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vincent.projectanalysis.components.MainComponnentsFragment;
import com.vincent.projectanalysis.knowbox.MainFragment;
import com.vincent.projectanalysis.utils.ContactsUtils;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.MD5Util;
import com.vincent.projectanalysis.utils.StatusBarUtil;
import com.vincent.projectanalysis.widgets.indicator.ColorTransitionPagerTitleView;
import com.vincent.projectanalysis.widgets.indicator.CommonNavigator;
import com.vincent.projectanalysis.widgets.indicator.CommonNavigatorAdapter;
import com.vincent.projectanalysis.widgets.indicator.IPagerIndicator;
import com.vincent.projectanalysis.widgets.indicator.IPagerTitleView;
import com.vincent.projectanalysis.widgets.indicator.LinePagerIndicator;
import com.vincent.projectanalysis.widgets.indicator.MagicIndicator;
import com.vincent.projectanalysis.widgets.indicator.SimplePagerTitleView;
import com.vincent.projectanalysis.widgets.indicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager mVpMain;
    MagicIndicator mIndicator;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mModuleTitles = new String[]{"Components", "Helper", "Demo", "Lab"};
    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        setContentView(R.layout.activity_main_naivigate);
        initView();
        initData();


        String packageMd5 = MD5Util.encode("com.alibaba.android.rimet" + ".box");
        LogUtil.d("vincent", "packageMd5 - " + packageMd5);

        //Test.test();

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1001);
        } else {
            addContacts();
        }
    }

    private void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setOffscreenPageLimit(mModuleTitles.length);
        mMainAdapter = new MainAdapter(getSupportFragmentManager());
        mVpMain.setAdapter(mMainAdapter);
        initIndicator();
    }

    private void initData() {
        mFragments.add(new MainComponnentsFragment());
        mFragments.add(new MainFragment());
        mFragments.add(new MainFragment());
        mFragments.add(new MainFragment());
        mMainAdapter.notifyDataSetChanged();
    }

    private void addContacts() {
        try {
            ArrayList<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add("1111451111");
            phoneNumbers.add("22222dd222");
            phoneNumbers.add("33333df333");
            phoneNumbers.add("555556454");
            ContactsUtils.update(this, phoneNumbers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MainAdapter extends FragmentPagerAdapter {


        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return mFragments.get(pos);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mModuleTitles[position];
        }
    }

    private void initIndicator() {
        mIndicator = findViewById(R.id.indicator_main);
        mIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public float getTitleWeight(Context context, int index) {
                return 1;
            }

            @Override
            public int getCount() {
                return mModuleTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_4f6171));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.default_blue));
                simplePagerTitleView.setText(mModuleTitles[index]);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpMain.setCurrentItem(index);
                    }
                });
                simplePagerTitleView.setPadding(0, 0, 0, 0);
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.default_blue));
                return linePagerIndicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mVpMain);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addContacts();
                }
                break;
        }
    }
}
