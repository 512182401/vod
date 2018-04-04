package com.changxiang.vod.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;


import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.entry.User;
import com.iflytek.cloud.Setting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;


public class SharedPrefManager {
    private static final String SHAREPRE_FILE_NAME = "user";
    private static final String VOICEVAULE = "voiceVaule";
    private static final String ACCVALUE = "accvalue";
    private static SharedPrefManager instance;
    private static SharedPreferences mShpr;//

    private static final String USER_Object = "user";
    /**
     * 支付相关保存
     */
    private static final String PAY_WXALI = "paywxali";//支付相关保存
    /**
     * 发现模块
     */
    private static final String SINGERCLUB_Object = "SingerClub";//歌友圈
    private static final String NEARBYPRODUCTION_Object = "NearbyProduction";//附近作品
    private static final String NEARBYPERSON_Object = "NearbyPerson";//附近的人

    /**
     * homeactivity_hot（原创）模块
     */
    private static final String ORIGINBANNER_Object = "originBanner";//原创轮播图
    private static final String ORIGININDEX_Object = "originIndex";//原创首页列表
    private static final String ORIGINHOT_Object = "originhot";//原创热听
    private static final String ORIGINTOP_Object = "origintop";//原创排行榜
    private static final String ORIGINFIRST_Object = "originfirst";//原创首发

    /**
     * 首页扇形
     */

    private static final String CHOOSEMUSIC_Object = "indexmusic";//点歌轮播图
    private static final String INDEXMUSIC_Object = "indexmusic";//首页
    private static final String SINGERMUSIC_Object = "singermusic";//歌星点歌
    private static final String TYPEMUSIC_Object = "typemusic";//歌星点歌
    private static final String TOPMUSIC_Object = "topmusic";//榜单点歌
    private static final String SEARCHMUSIC_Object = "searchmusic";//热门搜索
    private static final String CHOOSE_OBJECT = "hotmusic";//热门搜索
    private static final String CHOOSEDAY_OBJECT = "hotmusicdata";//j今日热门搜索

    /**
     * 上传成功热听排行榜：
     */
    private static final String UPLOADORIGINTOP = "uploadorigintop";//
    /*听歌页面热门推荐*/
    private static final String MUSICLISTENING_OBJECT = "musiclistening";

    /**
     * 我的主页
     */
    private static final String MYNOMEWORKS_Object = "myhomeWorks";//我的作品
    private static final String MYNOMECOLLECTION_Object = "myhomeCollection";//收藏作品
    private static final String MYNOMETRANS_Object = "myhomeTrans";//转发作品
    private static final String PersonSet_Object = "personset";//个人设置信息
    private static final String BlackList_Object = "blacklist";//黑名单
    private static final String Language_Object = "language";//黑名单
    private static final String GUIDE_Object = "guide";//新手引导界面集

    private static final String SETTING_Object = "setting";//设置

    private String IS_AutoLogin = "autologin";
    private String lock = "lock";


    private static final String Code_Section1_Cache_Data = "";
    //是否更新app的日期
    public static final String IS_UP_APP = "is_upapp";

    // 保存字段名称 start
    /**
     * 是否第一次启动App
     */
    public static final String IS_EXECUTE_FIRST = "is_execute_first";
    public static final String IS_Device_ID_upload = "device_id_upload";

    // 保存字段名称 end

    private SharedPrefManager() {
        mShpr = BaseApplication.app.getSharedPreferences(SHAREPRE_FILE_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，返回SharedPrefManager的静态实例；
     *
     * @return
     */
    public static SharedPrefManager getInstance() {
        if (instance == null) {
            instance = new SharedPrefManager();
        }
        return instance;
    }

    /**
     * 清除缓存
     */
    public void clearAllData() {
        synchronized (lock) {
            mShpr.edit().clear().commit();
        }
    }


    /**
     * 保存用户信息到本地
     *
     * @param user
     */
    public void saveUserToLocal(User user) {
        setObject(USER_Object, user);

        LogUtils.sysout("保存user数据，为了自动登录");
    }

    //
    public User getUserFromLocal() {

        LogUtils.sysout("请求登录数据");
        return getObject(USER_Object, User.class);
    }

//    private static final String MYNOMEWORKS_Object = "myhomeWorks";//我的作品

    /**
     * 保存黑名单
     */
    public void cacheApiBlackList(String json) {
        synchronized (lock) {
            mShpr.edit().putString(BlackList_Object, json).commit();
        }
    }

    public String getCacheApiBlackList() {
        synchronized (lock) {
            return mShpr.getString(BlackList_Object, "");
        }
    }

    /**
     * 保存语言设置
     */
    public void cacheApiLanguage(String json) {
        synchronized (lock) {
            mShpr.edit().putString(Language_Object, json).commit();
        }
    }

    public String getCacheApiLanguage() {
        synchronized (lock) {
            return mShpr.getString(Language_Object, "language");
//            return mShpr.getString( Language_Object, "zh_TW" );
        }
    }


    /**
     * 保存app检测的时间
     */
    public void cacheApiupapp(String json) {
        synchronized (lock) {
            mShpr.edit().putString(IS_UP_APP, json).commit();
        }
    }

    public String getCacheApiupapp() {
        synchronized (lock) {
            return mShpr.getString(IS_UP_APP, "");
        }
    }

    /**
     * 听歌页面热门推荐
     */
    public void cacheApimyhomeWorks(String json) {
        synchronized (lock) {
            mShpr.edit().putString(MYNOMEWORKS_Object, json).commit();
        }
    }

    public String getCacheApimyhomeWorks() {
        synchronized (lock) {
            return mShpr.getString(MYNOMEWORKS_Object, "");
        }
    }

//    private static final String MYNOMECOLLECTION_Object = "myhomeCollection";//收藏作品

    public void cacheApimyhomeCollection(String json) {
        synchronized (lock) {
            mShpr.edit().putString(MYNOMECOLLECTION_Object, json).commit();
        }
    }

    public String getCacheApimyhomeCollection() {
        synchronized (lock) {
            return mShpr.getString(MYNOMECOLLECTION_Object, "");
        }
    }
//    private static final String MYNOMETRANS_Object = "myhomeTrans";//转发作品

    public void cacheApimyhomeTrans(String json) {
        synchronized (lock) {
            mShpr.edit().putString(MYNOMETRANS_Object, json).commit();
        }
    }

    public String getCacheApimyhomeTrans() {
        synchronized (lock) {
            return mShpr.getString(MYNOMETRANS_Object, "");
        }
    }

    /**
     * 听歌页面热门推荐
     */
    public void cacheApimusiclistening(String json) {
        synchronized (lock) {
            mShpr.edit().putString(MUSICLISTENING_OBJECT, json).commit();
        }
    }

    public String getCacheApimusiclistening() {
        synchronized (lock) {
            return mShpr.getString(MUSICLISTENING_OBJECT, "");
        }
    }

    /**
     * 热听首发
     */
    public void cacheApiuploadorigintop(String json) {
        synchronized (lock) {
            mShpr.edit().putString(UPLOADORIGINTOP, json).commit();
        }
    }

    public String getCacheApiuploadorigintop() {
        synchronized (lock) {
            return mShpr.getString(UPLOADORIGINTOP, "");
        }
    }


    /**
     * 热门搜索
     */
    public void cacheApisearchmusic(String json) {
        synchronized (lock) {
            mShpr.edit().putString(SEARCHMUSIC_Object, json).commit();
        }
    }

    public String getCacheApisearchmusic() {
        synchronized (lock) {
            return mShpr.getString(SEARCHMUSIC_Object, "");
        }
    }


    /**
     * 点歌轮播图
     */
    public void cacheApichooseMusicB(String json) {
        synchronized (lock) {
            mShpr.edit().putString(CHOOSEMUSIC_Object, json).commit();
        }
    }

    public String getCacheApichooseMusicB() {
        synchronized (lock) {
            return mShpr.getString(CHOOSEMUSIC_Object, "");
        }
    }

    /**
     * 首页
     */
    public void cacheApiindexmusic(String json) {
        synchronized (lock) {
            mShpr.edit().putString(INDEXMUSIC_Object, json).commit();
        }
    }

    public String getCacheApiindexmusic() {
        synchronized (lock) {
            return mShpr.getString(INDEXMUSIC_Object, "");
        }
    }

    /*点歌页面，热听推荐*/

    public void CacheApiHotmusic(String json) {
        synchronized (lock) {
            mShpr.edit().putString(CHOOSE_OBJECT, json).commit();
        }
    }

    public String getCacheApiHotmusic() {
        synchronized (lock) {
            return mShpr.getString(CHOOSE_OBJECT, "");
        }
    }


    /*点歌页面，今日热听推荐*/

    public void CacheApiHotmusicday(String json) {
        synchronized (lock) {
            mShpr.edit().putString(CHOOSEDAY_OBJECT, json).commit();
        }
    }

    public String getCacheApiHotmusicday() {
        synchronized (lock) {
            return mShpr.getString(CHOOSEDAY_OBJECT, "");
        }
    }

    /**
     * 榜单点歌
     */
    public void cacheApitopmusic(String json) {
        synchronized (lock) {
            mShpr.edit().putString(TOPMUSIC_Object, json).commit();
        }
    }

    public String getCacheApitopmusic() {
        synchronized (lock) {
            return mShpr.getString(TOPMUSIC_Object, "");
        }
    }

    /**
     * 分类点歌
     */
    public void cacheApitypemusic(String json) {
        synchronized (lock) {
            mShpr.edit().putString(TYPEMUSIC_Object, json).commit();
        }
    }

    public String getCacheApitypemusic() {
        synchronized (lock) {
            return mShpr.getString(TYPEMUSIC_Object, "");
        }
    }

    /**
     * 歌星点歌
     */
    public void cacheApisingermusic(String json) {
        synchronized (lock) {
            mShpr.edit().putString(SINGERMUSIC_Object, json).commit();
        }
    }

    public String getCacheApisingermusic() {
        synchronized (lock) {
            return mShpr.getString(SINGERMUSIC_Object, "");
        }
    }

    /**
     * 热听首发
     */
    public void cacheApioriginfirst(String json) {
        synchronized (lock) {
            mShpr.edit().putString(ORIGINFIRST_Object, json).commit();
        }
    }

    public String getCacheApioriginfirst() {
        synchronized (lock) {
            return mShpr.getString(ORIGINFIRST_Object, "");
        }
    }

    /**
     * 热听排行榜
     */
    public void cacheApiorigintop(String json) {
        synchronized (lock) {
            mShpr.edit().putString(ORIGINTOP_Object, json).commit();
        }
    }

    public String getCacheApiorigintop() {
        synchronized (lock) {
            return mShpr.getString(ORIGINTOP_Object, "");
        }
    }

    /**
     * 热听作品
     */
    public void cacheApioriginhot(String json) {
        synchronized (lock) {
            mShpr.edit().putString(ORIGINHOT_Object, json).commit();
        }
    }

    public String getCacheApioriginhot() {
        synchronized (lock) {
            return mShpr.getString(ORIGINHOT_Object, "");
        }
    }


    /**
     * 热听首页
     */
    public void cacheApioriginIndex(String json) {
        synchronized (lock) {
            mShpr.edit().putString(ORIGININDEX_Object, json).commit();
        }
    }

    public String getCacheApioriginIndex() {
        synchronized (lock) {
            return mShpr.getString(ORIGININDEX_Object, "");
        }
    }


    /**
     * 热听轮播图
     */
    public void cacheApioriginBanner(String json) {
        synchronized (lock) {
            mShpr.edit().putString(ORIGINBANNER_Object, json).commit();
        }
    }

    public String getCacheApioriginBanner() {
        synchronized (lock) {
            return mShpr.getString(ORIGINBANNER_Object, "");
        }
    }


    /**
     * 附近的人
     */
    public void cacheApiNearbyPerson(String json) {
        synchronized (lock) {
            mShpr.edit().putString(NEARBYPERSON_Object, json).commit();
        }
    }

    public String getCacheApiNearbyPerson() {
        synchronized (lock) {
            return mShpr.getString(NEARBYPERSON_Object, "");
        }
    }


    /**
     * 附近作品
     */
    public void cacheApiNearbyProduction(String json) {
        synchronized (lock) {
            mShpr.edit().putString(NEARBYPRODUCTION_Object, json).commit();
        }
    }

    public String getCacheApiNearbyProduction() {
        synchronized (lock) {
            return mShpr.getString(NEARBYPRODUCTION_Object, "");
        }
    }

    /**
     * 保存歌友圈信息到本地
     */
    public void cacheApiClubData(String json) {
        synchronized (lock) {
            mShpr.edit().putString(SINGERCLUB_Object, json).commit();
        }
    }

    public String getCacheApiClubData() {
        synchronized (lock) {
            return mShpr.getString(SINGERCLUB_Object, "");
        }
    }


    /**
     * @return true标识App第一次启动；需要显示引导图界面；false则相反
     */
    public boolean isExecuteFirst() {
        return mShpr.getBoolean(IS_EXECUTE_FIRST, true);
    }

    public void setExecuteFirst(boolean value) {
        mShpr.edit().putBoolean(IS_EXECUTE_FIRST, value).commit();
    }

    /**
     * 缓存汽修人的第一页数据
     *
     * @param key
     * @param json
     */
    public void cacheQXR_firstPage(String key, String json) {
        mShpr.edit().putString(key, json).apply();
    }

    /**
     * 获取汽修人的第一页数据
     *
     * @param key
     * @return
     */
    public String getQXR_firstPage(String key) {
        return mShpr.getString(key, "");
    }


    /**
     * 针对复杂类型存储<对象>
     */
    public void setObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            Editor editor = mShpr.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        if (mShpr.contains(key)) {
            String objectVal = mShpr.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


//保存设置数据：

    public void saveSettingToLocal(Setting setting) {
        setObject(SETTING_Object, setting);

        LogUtils.sysout("保存setting数据，为了");
    }

    public Setting getSettingFromLocal() {

        LogUtils.sysout("请求设置数据");
        return getObject(SETTING_Object, Setting.class);
    }

    //录像保存界面人声音量SeekBar 和 伴奏音量SeekBar数据保存

    public void saveVoiceValue(String voiceVaule) {
        mShpr.edit().putString(VOICEVAULE, voiceVaule).commit();
    }

    public String getVoiceValue() {
        return mShpr.getString(VOICEVAULE, "50");
    }

    public void saveAccValue(String accvalue) {
        mShpr.edit().putString(ACCVALUE, accvalue).commit();
    }

    public String getAccValue() {
        return mShpr.getString(ACCVALUE, "50");
    }
}
