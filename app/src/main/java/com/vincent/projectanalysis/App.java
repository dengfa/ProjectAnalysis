package com.vincent.projectanalysis;

import com.hyena.framework.config.FrameworkConfig;
import com.hyena.framework.network.executor.UrlConnectionHttpExecutor;
import com.hyena.framework.servcie.ServiceProvider;
import com.hyena.framework.utils.BaseApp;
import com.hyena.framework.utils.CrashHelper;
import com.vincent.projectanalysis.knowbox.service.BoxServiceManager;
import com.vincent.projectanalysis.utils.DirContext;

public class App extends BaseApp {

    public static String CITY_VERSION = "119";

    @Override
    public void initApp() {
        super.initApp();
        // 初始化崩溃统计
        CrashHelper.init();

        // 初始化底层框架
        FrameworkConfig.init(this).setAppRootDir(DirContext.getRootDir()).setDebug(false)
                .setHttpExecutor(new UrlConnectionHttpExecutor());

        // 注册应用系统服务
        ServiceProvider.getServiceProvider().registServiceManager(new BoxServiceManager());
    }

    @Override
    public void exitApp() {
        super.exitApp();
        // 释放所有资源
        ServiceProvider.getServiceProvider().getServiceManager().releaseAll();
    }

    @Override
    public String[] getValidProcessNames() {
        return new String[]{"com.vincent.projectanalysis"};
    }
}