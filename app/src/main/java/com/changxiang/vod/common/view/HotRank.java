//package com.changxiang.vod.common.view;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 15976 on 2017/3/9.
// */
//
//public class HotRank {
//
//
//    /**
//     * id : ff8081815a59a8b8015a5a516fd60004
//     * bfnum : 7
//     * imgAlbumUrl :
//     * xhnum : 11
//     * plnum : 0
//     * imgHead :
//     * name : 北京测试版
//     * ycVipName : 大大兀立
//     * ycVipId : 402883ec57bced620157bcf15d270000
//     * type : video
//     * zfnum : 0
//     */
//
//    private String id;
//    private int bfnum;
//    private String imgAlbumUrl;
//    private int xhnum;
//    private int plnum;
//    private int dznum;
//    private String imgHead;
//    private String name;
//    private String ycVipName;
//    private String ycVipId;
//    private String type;
//    private int zfnum;
//
//    public static HotRank objectFromData(String str) {
//
//        return new Gson().fromJson(str, HotRank.class);
//    }
//
//    public static HotRank objectFromData(String str, String key) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//
//            return new Gson().fromJson(jsonObject.getString(key), HotRank.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static List<HotRank> arrayHotRankFromData(String str) {
//
//        Type listType = new TypeToken<ArrayList<HotRank>>() {
//        }.getType();
//
//        return new Gson().fromJson(str, listType);
//    }
//
//    public static List<HotRank> arrayHotRankFromData(String str, String key) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//            Type listType = new TypeToken<ArrayList<HotRank>>() {
//            }.getType();
//
//            return new Gson().fromJson(jsonObject.getString(key), listType);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList();
//
//
//    }
//
//    public int getDznum() {
//        return dznum;
//    }
//
//    public void setDznum(int dznum) {
//        this.dznum = dznum;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public int getBfnum() {
//        return bfnum;
//    }
//
//    public void setBfnum(int bfnum) {
//        this.bfnum = bfnum;
//    }
//
//    public String getImgAlbumUrl() {
//        return imgAlbumUrl;
//    }
//
//    public void setImgAlbumUrl(String imgAlbumUrl) {
//        this.imgAlbumUrl = imgAlbumUrl;
//    }
//
//    public int getXhnum() {
//        return xhnum;
//    }
//
//    public void setXhnum(int xhnum) {
//        this.xhnum = xhnum;
//    }
//
//    public int getPlnum() {
//        return plnum;
//    }
//
//    public void setPlnum(int plnum) {
//        this.plnum = plnum;
//    }
//
//    public String getImgHead() {
//        return imgHead;
//    }
//
//    public void setImgHead(String imgHead) {
//        this.imgHead = imgHead;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getYcVipName() {
//        return ycVipName;
//    }
//
//    public void setYcVipName(String ycVipName) {
//        this.ycVipName = ycVipName;
//    }
//
//    public String getYcVipId() {
//        return ycVipId;
//    }
//
//    public void setYcVipId(String ycVipId) {
//        this.ycVipId = ycVipId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public int getZfnum() {
//        return zfnum;
//    }
//
//    public void setZfnum(int zfnum) {
//        this.zfnum = zfnum;
//    }
//}
