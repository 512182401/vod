package com.changxiang.vod.common.utils;

import android.content.Context;
import android.content.SharedPreferences;


public final class PreferUtil {

    public static PreferUtil INSTANCE;
    private static SharedPreferences mPrefer;
    private static final String APP_NAME = "com.quchangkeji.tosing";

    public  static  final  String HOME_BACK ="home_back";
    public  static  final  String CURRENT ="current";



    private PreferUtil() {
    }

    public static PreferUtil getInstance() {
        if (INSTANCE == null) {
            return new PreferUtil();
        }
        return INSTANCE;
    }

    public void init(Context ctx) {
//        mPrefer = ctx.getSharedPreferences(APP_NAME, Context.MODE_WORLD_READABLE
//                | Context.MODE_WORLD_WRITEABLE);
        mPrefer =ctx.getSharedPreferences( APP_NAME,Context.MODE_PRIVATE );
        mPrefer.edit().commit();
    }


    public String getString(String key, String defValue) {
        return mPrefer.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPrefer.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPrefer != null ? mPrefer.getBoolean(key, defValue) : defValue;
    }

    public void putString(String key, String value) {
        mPrefer.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        mPrefer.edit().putInt(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        LogUtils.w("PreferUtil", "mPrefer.edit()==" + mPrefer);
        mPrefer.edit().putBoolean(key, value).commit();
    }

    public void putLong(String key, long value) {
        mPrefer.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return mPrefer.getLong(key, defValue);
    }

    public void removeKey(String key) {
        mPrefer.edit().remove(key).commit();
    }


    /***
     *   设置是否有点击过Home健状态
     */
    public void setHomeBack(boolean flag) {
        putBoolean(HOME_BACK, flag);
    }

    public boolean getHomeBack() {
        return getBoolean(HOME_BACK, false);
    }


    /***
     *   保存录歌状态下按Home退出时录歌时长
     */
    public void setCurrent(int flag) {
        putInt(CURRENT, flag);
    }

    public int getCurrent() {
        return getInt(CURRENT, 0);
    }


}
