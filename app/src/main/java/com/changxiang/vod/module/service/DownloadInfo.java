package com.changxiang.vod.module.service;

/**
 * Created by 15976 on 2016/12/6.
 */

public class DownloadInfo {

    private int threadId;// 下载器id
    private int startPos;// 开始点
    private int endPos;// 结束点
    private int compeleteSize;// 完成度
    private String url;// 下载器网络标识

    public DownloadInfo(int threadId, int startPos, int endPos, int compeleteSize, String url) {
        this.threadId = threadId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.compeleteSize = compeleteSize;
        this.url = url;
    }

    public DownloadInfo() {
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getCompeleteSize() {
        return compeleteSize;
    }

    public void setCompeleteSize(int compeleteSize) {
        this.compeleteSize = compeleteSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "threadId=" + threadId +
                ", startPos=" + startPos +
                ", endPos=" + endPos +
                ", compeleteSize=" + compeleteSize +
                ", url='" + url + '\'' +
                '}';
    }
}
