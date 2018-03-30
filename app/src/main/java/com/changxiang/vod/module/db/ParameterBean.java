package com.changxiang.vod.module.db;

import java.io.Serializable;

/**
 * Created by 15976 on 2017/2/28.
 */

public class ParameterBean implements Serializable {

    String songId;//歌曲Id
    String musciType;//歌曲类型
    String songName;//
    String singerName;//
    String imgCover;//
    int position;//列表中的位置

    public ParameterBean(String songId, String musciType, int position) {
        this.songId = songId;
        this.musciType = musciType;
        this.position = position;
    }

    public ParameterBean(String songId, String musciType, String songName, String singerName, int position) {
        this.songId = songId;
        this.musciType = musciType;
        this.songName = songName;
        this.singerName = singerName;
        this.position = position;
    }

    public ParameterBean(String songId, String musciType, String songName, String singerName, String imgCover, int position) {
        this.songId = songId;
        this.musciType = musciType;
        this.songName = songName;
        this.singerName = singerName;
        this.imgCover = imgCover;
        this.position = position;
    }

    public ParameterBean() {
    }

    public String getMusciType() {
        return musciType;
    }

    public void setMusciType(String musciType) {
        this.musciType = musciType;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getImgCover() {
        return imgCover;
    }

    public void setImgCover(String imgCover) {
        this.imgCover = imgCover;
    }
}
