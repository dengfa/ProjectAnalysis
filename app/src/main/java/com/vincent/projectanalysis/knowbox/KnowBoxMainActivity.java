package com.vincent.projectanalysis.knowbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.hyena.framework.app.activity.NavigateActivity;
import com.hyena.framework.utils.BaseApp;
import com.hyena.framework.utils.ToastUtils;
import com.hyena.framework.utils.UiThreadHandler;
import com.vincent.projectanalysis.App;

public class KnowBoxMainActivity extends NavigateActivity {

    private boolean mExitMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
