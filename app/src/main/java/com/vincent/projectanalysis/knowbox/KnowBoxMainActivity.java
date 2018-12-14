package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.hyena.framework.app.activity.NavigateActivity;
import com.hyena.framework.utils.BaseApp;
import com.hyena.framework.utils.ToastUtils;
import com.hyena.framework.utils.UiThreadHandler;
import com.vincent.projectanalysis.App;
import com.vincent.projectanalysis.utils.StatusBarUtil;

public class KnowBoxMainActivity extends NavigateActivity {

    private boolean mExitMode;

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

        MainFragment fragment = (MainFragment) Fragment.instantiate(this, MainFragment.class.getName());
        fragment.setParent(this, null);
        showFragment(fragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mExitMode) {
                ((App) BaseApp.getAppContext()).exitApp();
                finish();
            } else {
                ToastUtils.showShortToast(this, "再按一次退出程序");
                mExitMode = true;
                UiThreadHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExitMode = false;
                    }
                }, 2000);
            }
            return true;
        }
        return false;
    }
}
