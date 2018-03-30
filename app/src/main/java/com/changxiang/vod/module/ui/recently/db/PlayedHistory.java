package com.changxiang.vod.module.ui.recently.db;

import java.io.Serializable;

/**
 * Created by 15976 on 2016/10/20.
 */

public class PlayedHistory implements Serializable {
    private String songId;//歌曲id
    private String name;//歌曲名
    private String singerName;//歌手名
    private String type;//歌曲类别（mv,mp3）
    private long date;//播放时间
    private int isChecked;//是否勾选
    private String imgHead;//歌手图像
    private int num;//播放量
    private String accPath;//伴奏地址
    private String oriPath;//原唱地址
    private String LrcPath;//歌词地址
    private String krcPath;//krc地址
    private String imgAlbumUrl;//背景图片
    private String size;//文件总大小

    //private String qztime;//前奏时间

    public PlayedHistory(String songId, String name, String singerName, String type, long date,
                         String imgHead, int num, String oriPath, String imgAlbumUrl, String size) {
        this.songId = songId;
        this.name = name;
        this.singerName = singerName;
        this.type = type;
        this.date = date;
        this.imgHead = imgHead;
        this.num = num;
        this.oriPath = oriPath;
        this.imgAlbumUrl = imgAlbumUrl;
        this.size=size;
    }

    public PlayedHistory(String songId, String name, String singerName, String type, long date,
                         String imgHead, int num, String oriPath, String imgAlbumUrl) {
        this.songId = songId;
        this.name = name;
        this.singerName = singerName;
        this.type = type;
        this.date = date;
        this.imgHead = imgHead;
        this.num = num;
        this.oriPath = oriPath;
        this.imgAlbumUrl = imgAlbumUrl;
    }

    public PlayedHistory(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAccPath() {
        return accPath;
    }

    public void setAccPath(String accPath) {
        this.accPath = accPath;
    }

    public String getOriPath() {
        return oriPath;
    }

    public void setOriPath(String oriPath) {
        this.oriPath = oriPath;
    }

    public String getLrcPath() {
        return LrcPath;
    }

    public void setLrcPath(String lrcPath) {
        LrcPath = lrcPath;
    }

    public String getKrcPath() {
        return krcPath;
    }

    public void setKrcPath(String krcPath) {
        this.krcPath = krcPath;
    }

    public String getImgAlbumUrl() {
        return imgAlbumUrl;
    }

    public void setImgAlbumUrl(String imgAlbumUrl) {
        this.imgAlbumUrl = imgAlbumUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public static final class IsChecked{
        //已经勾选
        public static final int HAS_CHECKED=1;
        //没有勾选
        public static final int NOT_CHECKED=0;
    }
}
