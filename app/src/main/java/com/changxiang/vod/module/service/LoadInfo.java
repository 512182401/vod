package com.changxiang.vod.module.service;

/**
 * Created by 15976 on 2016/12/6.
 */

public class LoadInfo {

    public int fileSize;// 文件大小
    private int complete;// 完成度
    private String urlstring;// 下载器标识

    public LoadInfo(int fileSize, int complete, String urlstring) {
        this.fileSize = fileSize;
        this.complete = complete;
        this.urlstring = urlstring;
    }
    public LoadInfo(){

    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getUrlstring() {
        return urlstring;
    }

    public void setUrlstring(String urlstring) {
        this.urlstring = urlstring;
    }
}
