/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.vincent.projectanalysis.download;

/**
 * 下载核心
 *
 * @author yangzc on 15/8/27.
 */
public class Downloader {

    //超时时间
    private static final int     TIME_OUT  = 30;
    //开始位置
    private              int     mStartPos;
    //任务进行状态
    private volatile     boolean mCanceled = false;

    //下载任务回调
    private DownloaderListener mDownloadListener;

    /**
     * 设置开始下载位置
     *
     * @param startPos 开始进度
     */
    public void setStartPos(int startPos) {
        this.mStartPos = startPos;
    }

    /**
     * 下载监听器
     *
     * @param listener 监听器
     */
    public void setDownloaderListener(DownloaderListener listener) {
        this.mDownloadListener = listener;
    }

    /**
     * 终止任务
     */
    public void cancel() {
        mCanceled = true;
    }

    /**
     * 开启下载
     *
     * @param remoteUrl 远程文件URL
     * @param destPath  目标路径
     * @return result
     */
    public boolean startTask(final String remoteUrl, final String destPath) {
        mCanceled = false;

        return true;
    }

    /**
     * 通知准备完成
     */
    private void notifyDownloadReady() {
        if (mDownloadListener != null) {
            mDownloadListener.onDownloadReady(this);
        }
    }

    /**
     * 通知开始启动
     *
     * @param startPos      文件下载的起始位置
     * @param contentLength 文件总长度
     */
    private void notifyDownloadStarted(long startPos, long contentLength) {
        if (mDownloadListener != null) {
            mDownloadListener.onDownloadStarted(this, startPos, contentLength);
        }
    }

    /**
     * 通知下载进行中
     *
     * @param downloadLen   文件已经下载的位置
     * @param contentLength 文件总长度
     */
    private void notifyDownloadAdvance(long downloadLen, long contentLength) {
        if (mDownloadListener != null) {
            mDownloadListener.onDownloadAdvance(this, downloadLen, contentLength);
        }
    }

    /**
     * 通知下载完成
     */
    private void notifyDownloadSuccess() {
        if (mDownloadListener != null) {
            mDownloadListener.onDownloadSuccess(this);
        }
    }

    /**
     * 通知下载完成
     *
     * @param result http返回数据
     */
    private void notifyDownlaodError(String result) {
        if (mDownloadListener != null) {
            mDownloadListener.onDownloadError(this, result);
        }
    }

    /**
     * 任务下载回调
     */
    interface DownloaderListener {

        /**
         * 准备完成
         * 文件已经建立
         *
         * @param downloader 下载任务
         */
        void onDownloadReady(Downloader downloader);

        /**
         * 已经开启
         * 连接上服务器 准备开始下载
         *
         * @param downloader    下载任务
         * @param startPos      下载的开始位置
         * @param contentLength 文件的总长度
         */
        void onDownloadStarted(Downloader downloader, long startPos, long contentLength);

        /**
         * 下载进行中
         * 下载进度回调
         *
         * @param downloader    下载任务
         * @param downloadLen   已经下载文件大小
         * @param contentLength 文件的总长度
         */
        void onDownloadAdvance(Downloader downloader, long downloadLen, long contentLength);

        /**
         * 下载成功
         *
         * @param downloader 下载任务
         */
        void onDownloadSuccess(Downloader downloader);

        /**
         * 下载失败
         *
         * @param downloader 下载任务
         * @param result     http返回数据
         */
        void onDownloadError(Downloader downloader, String result);
    }
}
