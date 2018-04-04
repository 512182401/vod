package com.changxiang.vod.module.service.receiver;

/**
 * 设置上传，合成进度的接口
 * Created by 15976 on 2017/1/5.
 */


public interface ComposeSetProgress {

    //position是歌曲在列表中的位置，percent上传，合成的百分比,歌曲ID，歌曲类型
    public void setUploadProgress(int position, int percent, String songId, String musicType);

    public void setUploadFinishImg(int position, String songId, String musicType);//上传完成后调用

    public void setUploadOnException(int position, String songId, String musicType);//上传过程中网络断开或者服务器异常



    public void setComposeProgress(int position, int percent, String songId, String musicType);//合成过程中

    public void setComposeFinishImg(int position, String songId, String musicType);//合成完成后调用

    public void setComposeOnException(int position, String songId, String musicType);//合成过程中网络断开或者服务器异常

}
