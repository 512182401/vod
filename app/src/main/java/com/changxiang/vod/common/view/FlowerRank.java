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
//public class FlowerRank {
//
//
//    /**
//     * id : 作品id
//     * bfnum : 5
//     * imgAlbumUrl : 作品封面url
//     * xhnum : 13
//     * plnum : 0
//     * imgHead : 作者头像url
//     * name : 作品名
//     * ycVipName : 作者名
//     * ycVipId : 作者id
//     * type : 作品类型
//     * zfnum : 0
//     */
//
//    private String id;
//    private int bfnum;
//    private String imgAlbumUrl;
//    private int xhnum;
//    private int plnum;
//    private String imgHead;
//    private String name;
//    private String ycVipName;
//    private String ycVipId;
//    private String type;
//    private int zfnum;
//
//    public static FlowerRank objectFromData(String str) {
//
//        return new Gson().fromJson(str, FlowerRank.class);
//    }
//
//    public static FlowerRank objectFromData(String str, String key) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//
//            return new Gson().fromJson(jsonObject.getString(key), FlowerRank.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static List<FlowerRank> arrayFlowerRankFromData(String str) {
//
//        Type listType = new TypeToken<ArrayList<FlowerRank>>() {
//        }.getType();
//
//        return new Gson().fromJson(str, listType);
//    }
//
//    public static List<FlowerRank> arrayFlowerRankFromData(String str, String key) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//            Type listType = new TypeToken<ArrayList<FlowerRank>>() {
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
