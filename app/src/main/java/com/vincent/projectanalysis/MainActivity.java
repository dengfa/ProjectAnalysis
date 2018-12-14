package com.vincent.projectanalysis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vincent.projectanalysis.activity.BlinkTagActivity;
import com.vincent.projectanalysis.activity.ChartActivity;
import com.vincent.projectanalysis.activity.ClipRevealActivity;
import com.vincent.projectanalysis.activity.CountDownActivity;
import com.vincent.projectanalysis.activity.GuideActivity;
import com.vincent.projectanalysis.activity.HomeworkCheckActivity;
import com.vincent.projectanalysis.activity.LevelProgressActivity;
import com.vincent.projectanalysis.activity.LottieActivity;
import com.vincent.projectanalysis.activity.OrderHomeworkActivity;
import com.vincent.projectanalysis.activity.RippleAndWaveActivity;
import com.vincent.projectanalysis.activity.ScanActivity;
import com.vincent.projectanalysis.activity.WidgetsCollectionsActivity;
import com.vincent.projectanalysis.knowbox.KnowBoxMainActivity;
import com.vincent.projectanalysis.module.guideMask.demo.ShowGuideActivity;
import com.vincent.projectanalysis.module.mapScene.MapSceneActivity;
import com.vincent.projectanalysis.utils.ContactsUtils;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.MD5Util;
import com.vincent.projectanalysis.utils.StatusBarUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private String[] tabs = {
            "KnowBox",
            "地图场景",
            "RippleView",
            "LevelProgress",
            "ClipReveal",
            "CountDown",
            "HomeworkCheckActivity",
            "ScanActivity",
            "控件集合",
            "BlinkTag",
            "Guide",
            "LottieAnimation",
            "Chart",
            "Order Homework",
            "引导遮罩"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        setContentView(R.layout.activity_main_naivigate);
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(this, R.layout.item_main_listview, R.id.tv_item, tabs));
        mListView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, KnowBoxMainActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, MapSceneActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, RippleAndWaveActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, LevelProgressActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, ClipRevealActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, CountDownActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, HomeworkCheckActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, WidgetsCollectionsActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, BlinkTagActivity.class));
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, GuideActivity.class));
                break;
            case 11:
                startActivity(new Intent(MainActivity.this, LottieActivity.class));
                break;
            case 12:
                startActivity(new Intent(MainActivity.this, ChartActivity.class));
                break;
            case 13:
                startActivity(new Intent(MainActivity.this, OrderHomeworkActivity.class));
                break;
            case 14:
                startActivity(new Intent(MainActivity.this, ShowGuideActivity.class));
                break;
        }
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
