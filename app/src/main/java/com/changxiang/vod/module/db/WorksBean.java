package com.changxiang.vod.module.db;

import java.io.Serializable;

/**
 * Created by 15976 on 2017/2/20.
 */

public class WorksBean implements Serializable {

    private String workId;//作品id
    private String workType;//收藏，转发，原创
    private String musicType;//作品类型 MP4，MP3
    private String imgAlbumUrl;//封面图
    private String ycVipId;//会员名
    private int listenNum;//收听数
    private int flowerNum;//鲜花数
    private int commentNum;//评论数
    private int transNum;//转发数
    private String songName;//作品名
    private String other;//预留字段
    private String songId;//音乐源id
    private String songType;//音乐源类型
    private String skipPrelude;//跳过前奏的毫秒值，0 表示录像时没有跳过前奏

    public WorksBean() {
    }


    public WorksBean(String workId, String ycVipId, String imgAlbumUrl, String musicType, int listenNum,
                     int flowerNum, int commentNum, int transNum, String songName, String workType, String other, String songId, String songType, String skipPrelude) {
        this.workId=workId;

        this.ycVipId = ycVipId;
        this.imgAlbumUrl = imgAlbumUrl;
        this.musicType = musicType;
        this.listenNum = listenNum;
        this.flowerNum = flowerNum;
        this.commentNum = commentNum;
        this.transNum = transNum;
        this.songName = songName;
        this.workType = workType;
        this.other = other;
        this.songId = songId;
        this.songType=songType;
        this.skipPrelude=skipPrelude;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getSongType() {
        return songType;
    }

    public void setSongType(String songType) {
        this.songType = songType;
    }

    public String getSkipPrelude() {
        return skipPrelude;
    }

    public void setSkipPrelude(String skipPrelude) {
        this.skipPrelude = skipPrelude;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public String getImgAlbumUrl() {
        return imgAlbumUrl;
    }

    public void setImgAlbumUrl(String imgAlbumUrl) {
        this.imgAlbumUrl = imgAlbumUrl;
    }

    public String getYcVipId() {
        return ycVipId;
    }

    public void setYcVipId(String ycVipId) {
        this.ycVipId = ycVipId;
    }

    public int getFlowerNum() {
        return flowerNum;
    }

    public void setFlowerNum(int flowerNum) {
        this.flowerNum = flowerNum;
    }

    public int getListenNum() {
        return listenNum;
    }

    public void setListenNum(int listenNum) {
        this.listenNum = listenNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getTransNum() {
        return transNum;
    }

    public void setTransNum(int transNum) {
        this.transNum = transNum;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
