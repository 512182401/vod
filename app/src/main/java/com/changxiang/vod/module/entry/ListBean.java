package com.changxiang.vod.module.entry;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15976 on 2016/11/15.
 */

public class ListBean implements Serializable {
    /**
     * id : 1
     * name : sdfsd
     * num : 32232
     * singerName : 上当
     * imgAlbumUrl : http://192.168.3.6:8083/tsAPI/files/liu2.png
     */
    private static final long serialVersionUID = 1L;
    private String id;//歌曲id
    private String name;//歌曲名
    private int num;//点播量
    private String singerName;//歌手名
    private String imgAlbumUrl;//歌曲封面
    private String imgHead;//歌手头像
    private String type;//歌曲类型



    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getnum() {
        return num;
    }

    public void setnum(int num) {
        this.num = num;
    }

    public String getsingerName() {
        return singerName;
    }

    public void setsingerName(String singerName) {
        this.singerName = singerName;
    }
    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public String getimgAlbumUrl() {
        return imgAlbumUrl;
    }

    public void setimgAlbumUrl(String imgAlbumUrl) {
        this.imgAlbumUrl = imgAlbumUrl;
    }
}
