package com.changxiang.vod.module.entry;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15976 on 2017/3/1.
 */

public class HotSong implements Serializable {


    /**
     * id : 1
     * num : 32232
     * type : video
     * singerName : 刘德华
     * imgAlbumUrl : http://192.168.3.6:8083/tsAPI/files/liu2.png
     * name : sdfsd
     * clarity : 1
     * imgHead :
     * singerId :
     */

    private String id;
    private int num;
    private String type;
    private String singerName;
    private String imgAlbumUrl;
    private String name;
    private String clarity;
    private String imgHead;
    private String singerId;
    private String size;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getImgAlbumUrl() {
        return imgAlbumUrl;
    }

    public void setImgAlbumUrl(String imgAlbumUrl) {
        this.imgAlbumUrl = imgAlbumUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
