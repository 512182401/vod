package com.changxiang.vod.module.db;

import java.io.Serializable;

/**
 * 歌星
 */

public class Singers implements Serializable {



    String ID ;//编号
    String Name;// 名字
    String  Type;// 类型（大陆男歌手）
    String Picture;// 头像
    String Nationality;// 国籍
    String SongsCount;// 歌曲统计
    String Visible;//  可见
    String  PINYIN;// 拼音首字母
    String Strokes;// 中风
    String zs;// 年代？
    String ordertimes;// 时间

    public Singers() {
    }

    public Singers(String ID, String name, String type, String picture, String nationality, String songsCount, String visible, String PINYIN, String strokes, String zs, String ordertimes) {
        this.ID = ID;
        Name = name;
        Type = type;
        Picture = picture;
        Nationality = nationality;
        SongsCount = songsCount;
        Visible = visible;
        this.PINYIN = PINYIN;
        Strokes = strokes;
        this.zs = zs;
        this.ordertimes = ordertimes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getSongsCount() {
        return SongsCount;
    }

    public void setSongsCount(String songsCount) {
        SongsCount = songsCount;
    }

    public String getVisible() {
        return Visible;
    }

    public void setVisible(String visible) {
        Visible = visible;
    }

    public String getPINYIN() {
        return PINYIN;
    }

    public void setPINYIN(String PINYIN) {
        this.PINYIN = PINYIN;
    }

    public String getStrokes() {
        return Strokes;
    }

    public void setStrokes(String strokes) {
        Strokes = strokes;
    }

    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }

    public String getOrdertimes() {
        return ordertimes;
    }

    public void setOrdertimes(String ordertimes) {
        this.ordertimes = ordertimes;
    }
}
