package com.changxiang.vod.module.ui.recordmusic.net;

import android.content.Context;

import com.changxiang.vod.module.constance.NetInterface;
import com.changxiang.vod.module.engine.NetInterfaceEngine;

import org.json.JSONObject;

import okhttp3.Callback;

/**
 * Created by 15976 on 2016/12/3.
 */

public class ChooseNet {

    public static final String HOMESINGURL= NetInterface.SERVER_URL+"common/app3Song.do";

    public static final String HOME_HOT_SINGURL= NetInterface.SERVER_URL+"common/rtSongList.do";

    public static void api_HomeSing(Callback callback){

        NetInterfaceEngine.sendhttp("",HOMESINGURL,callback);
    }

    /**
     * 首页请banan求接口
     */
//  params={"id":"402883845818708f0158189c99bc0005","type":"0","curPage":"1","lng":"120.20000","lat":"30.26667"}
//    参数说明：
//    id：会员id， curPage：页数 ，type： 0 全部  1 男生 2 女
//    lng：经度 lat：纬度
    public static void api_choosebanan(Context ct, Callback callback) {
        JSONObject rootObj = null;
        try {
            rootObj = new JSONObject();
            rootObj.put( "id", "" );

        } catch (Exception e) {
            e.printStackTrace();
        }

        String params = rootObj.toString();
        String url = NetInterface.SERVER_URL + "yc/dgBanner.do";
        NetInterfaceEngine.sendhttp( params, url, callback );
    }



    /**
     * 今日热听推荐的接口
     * @param curPage 页码
     * @param callback
     */
    public static void api_Home_Hot_Day(String curPage, Callback callback){

        JSONObject rootObj = null;
        try {
            rootObj = new JSONObject();
            rootObj.put( "curPage",  curPage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String params = rootObj.toString();
        String url = NetInterface.SERVER_URL + "common/daySongList.do";
        NetInterfaceEngine.sendhttp(params,url,callback);
    }


}
