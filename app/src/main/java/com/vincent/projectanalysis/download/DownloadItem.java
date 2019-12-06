/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.vincent.projectanalysis.download;

import com.vincent.db.BaseItem;

import java.sql.Date;

/**
 * @author yangzc on 15/12/10.
 */
public class DownloadItem extends BaseItem {

    public String mTaskId;
    public String mSrcPath;
    public String mDestPath;
    public long mDownloaded;
    public long mTotalLen;
    public int mStatus = Task.STATUS_UNINITED;
    //下载类型
    public String mSourceType;
    //扩展字段
    public String mExt;
    public Date mAddDate;
}
