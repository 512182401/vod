package com.changxiang.vod.module.ui.recently;

/**
 * 设置下载进度的接口
 * Created by 15976 on 2017/1/5.
 */


public interface ISetProgress {

    //position是歌曲在列表中的位置，percent下载的百分比,歌曲ID，歌曲类型
    public void setDownloadProgress(int position, int percent, String songId, String musicType);

    public void setFinishImg(int position, String songId, String musicType);//下载完成后调用

    public void setOnException(int position, String songId, String musicType);//下载过程中网络断开或者服务器异常


}
