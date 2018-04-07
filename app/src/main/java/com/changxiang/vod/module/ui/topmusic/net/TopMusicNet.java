package com.changxiang.vod.module.ui.topmusic.net;

import android.content.Context;

import com.changxiang.vod.module.constance.NetInterface;
import com.changxiang.vod.module.engine.NetInterfaceEngine;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Callback;

/**
 * Created by 15976 on 2016/11/14.
 */

public class TopMusicNet {
    public static final String TOPMUSICURL= NetInterface.SERVER_URL+"common/songBDList.do";
    public static final String TOPLISTURL= NetInterface.SERVER_URL+"common/bdSongTop.do";
    //榜单点歌
    public static void api_TopChoose(Context context, Callback callback){

        NetInterfaceEngine.sendhttp("",TOPMUSICURL,callback);
    }
    //榜单点歌列表
    public static void api_TopList(String typeId, Callback callback){
        JSONObject object=null;
        try {
            object=new JSONObject();
            object.put("typeId",typeId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = object.toString();
        NetInterfaceEngine.sendhttp(params,TOPLISTURL,callback);
    }

}
