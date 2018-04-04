package com.changxiang.vod.module.entry;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15976 on 2017/12/14.
 * 当期PK活动所有比赛曲目列表  bean
 */

public class CurrentPeriodBean implements Serializable {

    /**
     * activityId : 1
     * clarity : 1
     * createDate : 2017-12-13 15:19:22
     * fullName : hanzhonglian
     * id : 243015
     * isHot : 0
     * isOriginal : 1
     * isTran : 0
     * modifyDate : 2017-12-13 15:19:22
     * playQuantity : 0
     * preludeTime : 40967
     * reserveUrl : hanzhonglian
     * shortName : hzl
     * singerId : 14471
     * singerName : 鲁文嘉措
     * size : 8192
     * songName : 汉中恋
     * type : audio
     */

    private String activityId;
    private String clarity;
    private String createDate;
    private String fullName;
    private String id;
    private String isHot;
    private int isOriginal;
    private String isTran;
    private String modifyDate;
    private int playQuantity;
    private String preludeTime;
    private String reserveUrl;
    private String shortName;
    private String singerId;
    private String singerName;
    private String size;
    private String songName;
    private String type;




    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public int getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(int isOriginal) {
        this.isOriginal = isOriginal;
    }

    public String getIsTran() {
        return isTran;
    }

    public void setIsTran(String isTran) {
        this.isTran = isTran;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getPlayQuantity() {
        return playQuantity;
    }

    public void setPlayQuantity(int playQuantity) {
        this.playQuantity = playQuantity;
    }

    public String getPreludeTime() {
        return preludeTime;
    }

    public void setPreludeTime(String preludeTime) {
        this.preludeTime = preludeTime;
    }

    public String getReserveUrl() {
        return reserveUrl;
    }

    public void setReserveUrl(String reserveUrl) {
        this.reserveUrl = reserveUrl;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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


    @Override
    public String toString() {
        return "CurrentPeriodBean{" +
                "activityId='" + activityId + '\'' +
                ", clarity='" + clarity + '\'' +
                ", createDate='" + createDate + '\'' +
                ", fullName='" + fullName + '\'' +
                ", id='" + id + '\'' +
                ", isHot='" + isHot + '\'' +
                ", isOriginal=" + isOriginal +
                ", isTran='" + isTran + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", playQuantity=" + playQuantity +
                ", preludeTime='" + preludeTime + '\'' +
                ", reserveUrl='" + reserveUrl + '\'' +
                ", shortName='" + shortName + '\'' +
                ", singerId='" + singerId + '\'' +
                ", singerName='" + singerName + '\'' +
                ", size='" + size + '\'' +
                ", songName='" + songName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
