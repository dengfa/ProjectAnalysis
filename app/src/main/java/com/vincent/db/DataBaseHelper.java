/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.vincent.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.vincent.projectanalysis.utils.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * 数据库帮助类
 * @author yangzc on 15/8/21.
 */
public abstract class DataBaseHelper extends SQLiteOpenHelper {

    private String password;

    private final Context mContext;
    private final String mName;

    //数据库维护的所有表
    private Hashtable<Class<? extends BaseTable<?>>, BaseTable<?>> mDbTables;

    public DataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.mContext = context;
        this.mName = name;
        //password = FrameworkConfig.getConfig().getDBPassword();
        //SQLiteDatabase.loadLibs(context);
        mDbTables = new Hashtable<Class<? extends BaseTable<?>>, BaseTable<?>>();
        //初始化所有表
        initTables(this);
    }


    /*
     * 初始化所有表
     * 随应用启动时创建的表
     */
    public abstract void initTables(DataBaseHelper db);

    @Override
    public final void onCreate(SQLiteDatabase db) {
        //创建表
        createTables(db);
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgradeImpl(db, oldVersion, newVersion);
    }

    /*
     * 添加表
     */
    public void addTable(Class<? extends BaseTable<?>> clazz, BaseTable<?> table) {
        mDbTables.put(clazz, table);
    }

    /*
     * 获得所有表
     */
    public Hashtable<Class<? extends BaseTable<?>>, BaseTable<?>> getTables() {
        return mDbTables;
    }

    /*
     * 通过表类型获得表实例
     */
    @SuppressWarnings("unchecked")
	public <T extends BaseTable<?>> T getTable(Class<T> table) {
        return (T) getTables().get(table);
    }

    /*
     * 创建所有表
     */
    private void createTables(SQLiteDatabase db){
        if(mDbTables != null){
            Iterator<Class<? extends BaseTable<?>>> iterator = mDbTables.keySet().iterator();
            while (iterator.hasNext()) {
                Class<? extends BaseTable<?>> clazz = iterator.next();
                BaseTable<?> table = mDbTables.get(clazz);
                String sql = table.getCreateSql();
                if(!TextUtils.isEmpty(sql)){
                    db.execSQL(sql);
                }
            }
        }
    }

    /*
     * 数据库升级
     */
    public void onUpgradeImpl(SQLiteDatabase db, int oldVersion, int newVersion){
        if(mDbTables != null){
            Iterator<Class<? extends BaseTable<?>>> iterator = mDbTables.keySet().iterator();
            while (iterator.hasNext()) {
                Class<? extends BaseTable<?>> clazz = iterator.next();
                BaseTable<?> table = mDbTables.get(clazz);
                table.onUpgrade(db, oldVersion, newVersion);
            }
        }
    }

    /**
     * 清空默认数据库
     */
    public void clearDataBase() {
        if (mDbTables != null) {
            Iterator<Class<? extends BaseTable<?>>> iterator = mDbTables
                    .keySet().iterator();
            while (iterator.hasNext()) {
                Class<? extends BaseTable<?>> key = iterator.next();
                BaseTable<?> table = mDbTables.get(key);
                table.deleteByCase(null, null);
            }
        }
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        try {
            return super.getReadableDatabase();
        } catch (Exception e) {
            try {
                encrypt(mContext, mName, password);
                return super.getReadableDatabase();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        try {
            return super.getWritableDatabase();
        } catch (Exception e) {
            try {
                encrypt(mContext, mName, password);
                return super.getWritableDatabase();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public List<String> getTableNames() {
        List<String> tables = new ArrayList<String>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            if(db == null)
                return null;
            cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
            String name = cursor.getString(0);
            LogUtil.v("DataBaseHelper", "table: " + name);
            tables.add(name);
        } catch (Exception e) {
            LogUtil.e("DataBaseHelper", e);
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return tables;
    }

    public static void encrypt(Context context, String dbName,
                               String passphrase) throws IOException {
       /* File originalFile = context.getDatabasePath(dbName);
        if (originalFile.exists()) {
            File newFile = File.createTempFile("sqlcipherutils", "tmp", context.getCacheDir());
            SQLiteDatabase db = SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);
            db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';", newFile.getAbsolutePath(), passphrase));
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.rawExecSQL("DETACH DATABASE encrypted;");
            int version = db.getVersion();
            db.close();

            db = SQLiteDatabase.openDatabase(newFile.getAbsolutePath(), passphrase, null, SQLiteDatabase.OPEN_READWRITE);
            db.setVersion(version);
            db.close();
            originalFile.delete();
            newFile.renameTo(originalFile);
        }*/
    }
}
