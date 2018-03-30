package com.changxiang.vod.module.entry;

import java.io.Serializable;

/**
 * 下载后
 * Created by 15976 on 2016/12/12.
 */

public class SongDetail implements Serializable {

    String songId;//歌曲id
    String songName;//歌曲名
    String singerName;//歌手名
    String type;//类型（mv,mp3// ）
    int num;//点播量
    String imgAlbumUrl;//封面
    String imgHead;//歌手头像
    String qzTime;//前奏时间
    int isAllDownload;//home_origin，home_accompany，krc，歌词是否都已下载,1表示未下载，2表示已下载
    String oriPath;//原唱地址
    String accPath;//伴奏地址
    String lrcPath;//歌词地址
    String krcPath;//krc地址
    String size;//文件总大小

    String songDecode;//mp3解码之后地址
    String isDecode = "0";//解码状态； 0：未开始解码：1：解码成功；2：解码进行中；解码失败，4：该手机不能解码：
    String duration;//总时长（毫秒）
    String activityId;//活动id
    String isSelect = "0";//1:代表选中   0：没选择

    public SongDetail(String songId, String songName, String singerName, String type, int num,
                      String krcPath, String lrcPath, String accPath, String oriPath, String imgAlbumUrl, String imgHead, String qzTime, String size, String activityId) {
        this.songId = songId;
        this.songName = songName;
        this.singerName = singerName;
        this.type = type;
        this.num = num;
        this.krcPath = krcPath;
        this.lrcPath = lrcPath;
        this.accPath = accPath;
        this.oriPath = oriPath;
        this.imgAlbumUrl = imgAlbumUrl;
        this.imgHead = imgHead;
        this.qzTime = qzTime;
        this.size = size;
        this.activityId = activityId;
    }

    public SongDetail(String songId, String songName, String singerName, String type, int num,
                      String krcPath, String lrcPath, String accPath, String oriPath, String imgAlbumUrl, String imgHead, String qzTime) {
        this.songId = songId;
        this.songName = songName;
        this.singerName = singerName;
        this.type = type;
        this.num = num;
        this.krcPath = krcPath;
        this.lrcPath = lrcPath;
        this.accPath = accPath;
        this.oriPath = oriPath;
        this.imgAlbumUrl = imgAlbumUrl;
        this.imgHead = imgHead;
        this.qzTime = qzTime;
    }

    public SongDetail(String songId, String songName, String singerName, String type, int num,
                      String oriPath, String imgAlbumUrl, String imgHead) {
        this.songId = songId;
        this.songName = songName;
        this.singerName = singerName;
        this.type = type;
        this.num = num;
        this.oriPath = oriPath;
        this.imgAlbumUrl = imgAlbumUrl;
        this.imgHead = imgHead;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSongDecode() {
        return songDecode;
    }

    public void setSongDecode(String songDecode) {
        this.songDecode = songDecode;
    }

    public String getIsDecode() {
        if (isDecode != null) {
            return isDecode;
        } else {
            return "0";
        }
    }

    public void setIsDecode(String isDecode) {
        this.isDecode = isDecode;
    }

    public SongDetail() {
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImgAlbumUrl() {
        return imgAlbumUrl;
    }

    public void setImgAlbumUrl(String imgAlbumUrl) {
        this.imgAlbumUrl = imgAlbumUrl;
    }

    public String getOriPath() {
        return oriPath;
    }

    public void setOriPath(String oriPath) {
        this.oriPath = oriPath;
    }

    public String getAccPath() {
        return accPath;
    }

    public void setAccPath(String accPath) {
        this.accPath = accPath;
    }

    public String getLrcPath() {
        return lrcPath;
    }

    public void setLrcPath(String lrcPath) {
        this.lrcPath = lrcPath;
    }

    public String getKrcPath() {
        return krcPath;
    }

    public void setKrcPath(String krcPath) {
        this.krcPath = krcPath;
    }

    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public int isAllDownload() {
        return isAllDownload;
    }

    public void setAllDownload(int allDownload) {
        isAllDownload = allDownload;
    }

    public void setQzTime(String qzTime) {
        this.qzTime = qzTime;
    }

    public int getIsAllDownload() {
        return isAllDownload;
    }

    public void setIsAllDownload(int isAllDownload) {
        this.isAllDownload = isAllDownload;
    }

    public String getQzTime() {
        return qzTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "SongDetail{" +
                "songId='" + songId + '\'' +
                ", songName='" + songName + '\'' +
                ", singerName='" + singerName + '\'' +
                ", type='" + type + '\'' +
                ", num=" + num +
                ", imgAlbumUrl='" + imgAlbumUrl + '\'' +
                ", imgHead='" + imgHead + '\'' +
                ", qzTime='" + qzTime + '\'' +
                ", isAllDownload=" + isAllDownload +
                ", oriPath='" + oriPath + '\'' +
                ", accPath='" + accPath + '\'' +
                ", lrcPath='" + lrcPath + '\'' +
                ", krcPath='" + krcPath + '\'' +
                ", size='" + size + '\'' +
                ", songDecode='" + songDecode + '\'' +
                ", isDecode='" + isDecode + '\'' +
                ", duration='" + duration + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
