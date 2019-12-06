package com.vincent.db;

import android.content.Context;

import com.vincent.projectanalysis.App;
import com.vincent.projectanalysis.download.DownloadTable;

public class BoxDataBase extends BaseDataBaseHelper {

    public static final String DATABASE_NAME    = "vincent.db";
    public static final int    DATABASE_VERSION = 1;

    public BoxDataBase(Context context) {
        super(App.getAppContext(), DATABASE_NAME, DATABASE_VERSION, 1);
    }

    @Override
    public void initTablesImpl(DataBaseHelper db) {
        addTable(DownloadTable.class, new DownloadTable(db));//下载表
    }

    @Override
    protected void upgradeVersionImpl(int oldVersion, int newVersion) {
        super.upgradeVersionImpl(oldVersion, newVersion);
        //升级下载表
        DownloadTable downloadTable = getTable(DownloadTable.class);
    }
}
