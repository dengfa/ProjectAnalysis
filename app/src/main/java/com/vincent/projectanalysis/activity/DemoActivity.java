package com.vincent.projectanalysis.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Glide.with(this).pauseRequests();
        Glide.with(this).resumeRequests();
        String url = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getMemoryInfo();
        String[] strings = new String[1000000];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "sadfasdfadfgdfgdfxxcxcvxcvxcvxcvgsdf" + i;
        }
        getMemoryInfo();

        android.app.FragmentManager manager = getFragmentManager();

        Fruit[] fruits = new Apple[3];
        //fruits[0] = new Orange();
        fruits[1] = new Apple();
        //fruits[2] = new Fruit();
        for (Fruit fruit : fruits) {
            LogUtil.d("vincent", fruit.getType());
        }

        List<? super Apple> apples = new ArrayList<>();
        apples.add(new RedApple());
    }

    private void getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        LogUtil.d("Memory", "系统总内存:" + (info.totalMem / (1024 * 1024)) + "M");
        LogUtil.d("Memory", "系统剩余内存:" + (info.availMem / (1024 * 1024)) + "M");
        LogUtil.d("Memory", "系统是否处于低内存运行：" + info.lowMemory);
        LogUtil.d("Memory", "系统剩余内存低于" + (info.threshold / (1024 * 1024)) + "M时为低内存运行");


        //最大分配内存
        int memory = activityManager.getMemoryClass();
        LogUtil.d("Memory", "memory: " + memory);
        //最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
        //当前分配的总内存
        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
        //剩余内存
        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
        LogUtil.d("Memory", "maxMemory: " + maxMemory);
        LogUtil.d("Memory", "totalMemory: " + totalMemory);
        LogUtil.d("Memory", "freeMemory: " + freeMemory);
    }

    class vpAdpter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }


    }

    class FPagerAdapter extends FragmentPagerAdapter {

        public FPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }

    class Fruit {
        public String getType() {
            return "fruit";
        }
    }

    class Apple extends Fruit {
        public String getType() {
            return "Apple";
        }
    }

    class RedApple extends Apple{
        public String getType() {
            return "RedApple";
        }
    }

    class Orange extends Fruit {
        public String getType() {
            return "Orange";
        }
    }
}
