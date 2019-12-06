/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.vincent.projectanalysis.download.task;

import com.vincent.projectanalysis.download.DownloadItem;
import com.vincent.projectanalysis.download.Task;

/**
 * 文件下载器
 *
 * @author yangzc on 15/8/27.
 */
public class UrlTask extends Task {

    public static final String SOURCE_TYPE = "urltask";

    private String remoteUrl;
    private String destFilePath;

    public static UrlTask createUrlTask(DownloadItem item) {
        UrlTask task = new UrlTask(item);
        task.remoteUrl = item.mSrcPath;
        task.destFilePath = item.mDestPath;
        return task;
    }

    public UrlTask(DownloadItem item) {
        super(item);
    }

    @Override
    public String getRemoteUrl() {
        return remoteUrl;
    }

    @Override
    public String getDestFilePath() {
        return destFilePath;
    }

    @Override
    public String getTaskType() {
        return SOURCE_TYPE;
    }

    @Override
    public int getStartPos() {
        return super.getStartPos();
    }

    @Override
    public int getPriority() {
        return PRIORITY_MIDDLE;
    }
}
