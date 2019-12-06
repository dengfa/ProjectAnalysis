/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.vincent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.projectanalysis.utils.AppPreferences;
import com.vincent.projectanalysis.utils.LogUtil;

/**
 * 数据库帮助类基类
 * @author yangzc on 15/8/21.
 */
public abstract class BaseDataBaseHelper extends DataBaseHelper {

    public BaseDataBaseHelper(Context context, String name, int version, int baseVersion) {
        super(context, name, version);
        int oldVersion = AppPreferences.getInt("BASE_TABLE_VERSION", 0);
        if (oldVersion != baseVersion) {
            //升级
            upgradeVersion(oldVersion, baseVersion);
            AppPreferences.setInt("BASE_TABLE_VERSION", baseVersion);
        }
    }

    @Override
    public final void initTables(DataBaseHelper db) {
        initTablesImpl(db);
    }

    /*
     * 初始化所有业务表
     */
    public abstract void initTablesImpl(DataBaseHelper db);

    private void upgradeVersion(int oldVersion, int newVersion) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            if(db == null)
                return;
            db.beginTransaction();
            upgradeVersionImpl(oldVersion, newVersion);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.e("", e);
        } finally {
            try {
                db.endTransaction();
            } catch (Exception e2) {
            }
        }
    }

    protected void upgradeVersionImpl(int oldVersion, int newVersion) {}
}
