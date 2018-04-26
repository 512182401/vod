package com.changxiang.vod.module.entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 歌曲实体类
 *
 * @author admin
 *         方式一：Code-->Generate
 *         <p>
 *         方式二：通过快捷键Alt+Insert
 */
public class Banner implements Serializable {


    /**
     * titleSts : 1
     * name :
     * img : http://test.app.srv.quchangkeji.com:8083/tsAPI/files/banner01@2x.jpg
     * url :
     */

    private String titleSts;
    private String name;
    private String img;
    private String url;

    public static Banner objectFromData(String str) {

        return new Gson().fromJson(str, Banner.class);
    }

    public static Banner objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), Banner.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Banner> arrayBannerFromData(String str) {

        Type listType = new TypeToken<ArrayList<Banner>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Banner> arrayBannerFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Banner>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getTitleSts() {
        return titleSts;
    }

    public void setTitleSts(String titleSts) {
        this.titleSts = titleSts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
