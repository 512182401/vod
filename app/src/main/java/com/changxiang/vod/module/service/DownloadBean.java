package com.changxiang.vod.module.service;

/**
 * 下载队列中保存的对象
 * Created by 15976 on 2017/1/6.
 */

public class DownloadBean {

    String songId;//歌曲id
    String songName;//歌曲名
    String singerName;//歌手名
    String type;//类型（mv,mp3// ）
    int position;//下载队列中的位置
    int isFinish;//是否下载完成,0表示未下载，2表示已下载
    int isRecord;//数据库是否已经有下载记录,0表示没有记录，2表示有记录

    public DownloadBean(String songId, String songName, String singerName, String type, int position, int isFinish, int isRecord) {
        this.songId = songId;
        this.songName = songName;
        this.singerName = singerName;
        this.type = type;
        this.position = position;
        this.isFinish = isFinish;
        this.isRecord = isRecord;
    }

    public DownloadBean() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public int getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(int isRecord) {
        this.isRecord = isRecord;
    }
}
