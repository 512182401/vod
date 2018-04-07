package com.changxiang.vod.module.base;

import android.app.Service;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Config;
import android.util.DisplayMetrics;


import com.changxiang.vod.common.utils.PreferUtil;
import com.changxiang.vod.common.utils.SharedPrefManager;
import com.changxiang.vod.module.constance.IPracticeMode;
import com.changxiang.vod.module.entry.User;
import com.changxiang.vod.module.ui.recently.db.HistoryDBManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Locale;


public class BaseApplication extends MultiDexApplication implements IPracticeMode {//MultiD
    private static User user;

    //    public static HomeActivity mHomeActivity = null;
    public static boolean isMakeMv = true;
    public static BaseApplication app;
    public String locCity = "";//城市
    public String mLongitude = "";//返回经度
    public String mLatitude = "";//返回纬度
    //	private static Context ct;
    private static boolean islogined = false;//用于判断是否登录

    //后台下载是否要停止
    public static boolean isStop = true;
    public static String downingname = "";//当前下载歌曲
    //后台上传是否要停止
    public static boolean isUploadStop = false;
    //后台合成是否要停止
    public static boolean isComposeDiyStop = true;//DIY后台合成，当为false的时候，有DIY在合成，
    //是否正在录音
    public static boolean isRecord = false;
    //	IntentFilter mFilter;//过滤器
//    private NetWorkStateReceiver netWorkStateReceiver;//网络状态监听器
    //网络状态,0, 1：非wifi环境下每次都提醒； 2：非wifi环境下首次都提醒；3：不做任何提醒,4:wifi，5：表示没有网络
    public static int wifiState;//网络状态

    public static int isNoticeOnce;//0 还没有提醒过，1 已经提醒过了
    //    public static LinkedHashMap<Integer, GifItem> addItems = null;
    public static String composeid = "88888888";//
    //    public LocationService locationService;
    public Vibrator mVibrator;

    public static String orderId = "";//支付订单id
    public static String tagid = "";//支付订单id

    public void setWifiState(int wifi) {
        wifiState = wifi;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        // 第三方图片下载下载Android-Universal-Image-Loader getApplicationContext()
        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(configuration);
        ImageLoader.getInstance().init(configuration);
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush
//		/**
//		 * 初始化BQMMSDK,通过官网获取AppId以及AppSecert
//		 */


        //TODO
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
//		Config.REDIRECT_URL = "quchang的新浪后台的回掉地址";
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = true;
//        HistoryDBManager是单例模式
        HistoryDBManager.init(this);
        setLanguage();
//        LeakCanary.install(this);//内存泄漏检测
//        CanaryLog.Logger.init("LogTAG");
        try {
            PreferUtil.getInstance().init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***
         * 初始化定位sdk，建议在Application中创建
         */
//        SDKInitializer.initialize(getApplicationContext());
//        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static boolean islogined() {
        return islogined;
    }

    public static void setIslogined(boolean islogined) {
        BaseApplication.islogined = islogined;
    }

    public static User getUser() {
        if (user != null) {
            return user;
        } else {
            user = SharedPrefManager.getInstance().getUserFromLocal();
            if (user != null) {
                setIslogined(true);
            }
            return user;
        }
//        return null;
    }


    public static void setUser(User user) {
        BaseApplication.user = user;
    }

    //设置app语言
    private void setLanguage() {
//        String language = SharedPrefManager.getInstance().getCacheApiLanguage();
//        if (language.equals("language")) {//跟随系统
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = Locale.getDefault();
//            res.updateConfiguration(conf, dm);
//        } else {//设置的语言
//            String[] str = language.split("_");
//            if (str.length == 2) {
//                Locale myLocale = new Locale(str[0], str[1]);
//                Resources res = getResources();
//                DisplayMetrics dm = res.getDisplayMetrics();
//                Configuration conf = res.getConfiguration();
//                conf.locale = myLocale;
//                res.updateConfiguration(conf, dm);
//            }
//        }
    }

    /**
     * 退出登录
     */
    public void exitLogin() {
        user = null;
    }

    public static String getOpenId() {
        User user = getUser();
        if (user == null) {
            return "0";
        }
        return user.getOpenId();//user.getOpenid();
    }

    public static String getUserId() {
        User user = getUser();
        if (user == null) {
            return "0";
        }
        return user.getUserId();//user.getOpenid();
    }


    public static boolean isLogin() {
        User user = getUser();
        return user != null;
    }


}
