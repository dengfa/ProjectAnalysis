/**
 * Copyright (C) 2014 The plugin_music Project
 */
package com.vincent.projectanalysis.utils;

import com.hyena.framework.utils.BaseApp;

import java.io.File;

/**
 * @author yangzc
 * @version 1.0
 * @createTime 2014年11月12日 下午7:07:34
 * 
 */
public class DirContext {

	enum DirName {
		Image("images"),
		Preload("preload"),
		Audio("audio");

		private String mDir;
		DirName(String dir) {
			this.mDir = dir;
		}
		public String getValue() {
			return mDir;
		}
	}

	public static void initDirs() {
		getDir(DirName.Image);
		getDir(DirName.Preload);
		getDir(DirName.Audio);
	}

	/**
	 * 获得文件夹
	 * @param dir
	 * @return
	 */
	public static File getDir(DirName dir) {
		return getDir(getRootDir(), dir.getValue());
	}

	/**
	 * 获得特定目录下的文件夹
	 * @param parent
	 * @param dirName
	 * @return
	 */
	public static File getDir(File parent, String dirName){
		File file = new File(parent, dirName);
		if(!file.exists() || !file.isDirectory())
			file.mkdirs();
		return file;
	}

	/**
	 * 获得引用根路径
	 * @return
	 */
	public static File getRootDir(){
		File rootDir = new File(android.os.Environment
				.getExternalStorageDirectory(), "as_teacher");
		return rootDir;
	}

	/**
	 * 获得图片缓存路径
	 * @return
	 */
	public static File getImageCacheDir(){
		return getDir(DirName.Image);
	}

	/**
	 * 获得音频缓存路径
	 * @return
	 */
	public static File getAudioCacheDir(){
		return getDir(DirName.Audio);
	}

	public static File getPreLoadDir(){
		return getDir(DirName.Preload);
	}
	
	/**
	 * 城市数据文件
	 * @return
	 */
	public static File getCityJsonFile(){
		return new File(BaseApp.getAppContext().getCacheDir(), "city.json");
	}

	/**
	 * 模板缓存路径
	 * @return
     */
	public static File getTemplateDir() {
		return new File(BaseApp.getAppContext().getCacheDir(), "template");
	}

	/**
	 * 模板缓存路径
	 * @return
	 */
	public static File getTempTemplateDir() {
		return new File(BaseApp.getAppContext().getCacheDir(), "tempTemplate");
	}

	/**
	 * 模板缓存路径
	 * @return
	 */
	public static File getTemplateFile() {
		return new File(BaseApp.getAppContext().getCacheDir(), "webView.zip");
	}

}
