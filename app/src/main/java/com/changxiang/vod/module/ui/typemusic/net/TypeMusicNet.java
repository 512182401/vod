package com.changxiang.vod.module.ui.typemusic.net;

import android.content.Context;

import com.changxiang.vod.module.constance.NetInterface;
import com.changxiang.vod.module.engine.NetInterfaceEngine;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Callback;

/**
 * Created by 15976 on 2016/11/16.
 */

public class TypeMusicNet {
    //分类点歌接口
    private static final String TYPEMUSIC= NetInterface.SERVER_URL+"common/songTypeList.do";
    //分类歌曲列表接口
    private static final String TYPELIST= NetInterface.SERVER_URL+"common/typeSgList.do";

    public static void api_TypeMusic(Callback callback){
        NetInterfaceEngine.sendhttp("",TYPEMUSIC,callback);
    }

    public static final void api_TypeList(Context context, String type, int curPage, int code, Callback callback){
        JSONObject object=null;
        try {
            object=new JSONObject();
            object.put("type",type);
            object.put("curPage",curPage);
            object.put("code",code);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = object.toString();
        NetInterfaceEngine.sendhttp(params,TYPELIST,callback);
    }
}
