package com.vincent.db;

import android.net.Uri;

import java.util.List;
import java.util.Vector;

/**
 * 数据库管理器
 *
 * @author yangzc
 */
public class DataBaseManager {

    // 应用主数据库
    private DataBaseHelper mDefaultDbHelper;

    public static DataBaseManager        _instance = null;
    private       List<DataBaseListener> mDbListeners;

    private DataBaseManager() {
    }

    public static DataBaseManager getDataBaseManager() {
        if (_instance == null) {
            _instance = new DataBaseManager();
        }
        return _instance;
    }

    /*
     * 获得表实例
     */
    public <T extends BaseTable<?>> T getTable(Class<T> table) {
        return mDefaultDbHelper.getTable(table);
    }

    /*
     * 初始化数据库
     */
    public void registDataBase(DataBaseHelper database) {
        if (database != null)
            database.close();
        mDefaultDbHelper = database;
    }

    /*
     * 获得默认数据库
     */
    public DataBaseHelper getDefaultDB() {
        return mDefaultDbHelper;
    }

    /**
     * 清空默认数据库
     */
    public void clearDataBase() {
        if (mDefaultDbHelper != null) {
            mDefaultDbHelper.clearDataBase();
        }
    }

    public void notifyDataBaseChange(Uri uri) {
        if (mDbListeners == null)
            return;
        for (int i = 0; i < mDbListeners.size(); i++) {
            mDbListeners.get(i).onDataChange(uri);
        }
    }

    public void addDataBaseListener(DataBaseListener listener) {
        if (mDbListeners == null)
            mDbListeners = new Vector<>();
        mDbListeners.add(listener);
    }

    public void removeDataBaseListener(DataBaseListener listener) {
        if (mDbListeners != null) {
            mDbListeners.remove(listener);
        }
    }

    /**
     * 释放数据库
     */
    public void releaseDB() {
        if (mDefaultDbHelper != null) {
            try {
                mDefaultDbHelper.close();
            } catch (Exception e) {
            }
            mDefaultDbHelper = null;
        }
    }

}
