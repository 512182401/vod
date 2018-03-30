package com.changxiang.vod.module.constance;

import android.Manifest;


import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.MyFileUtil;

import java.io.File;


public class NetInterface {
    public static boolean isDevelopment = true;// 是否测试版(主要用于数据是否本地缓存true表示需要缓存)
    //    public static String ALL_URL = "http://app.srv.quchangkeji.com:8082/tosing";//换商城接口
    public static String ALL_URL = " http://srv.ifunsing.com:8080/kingpk-service";//歌王外网地址

    public static String SERVER_URL = ALL_URL + "/";// 测试接口地址（1.0版本）
    public static String SERVER_XIEYI = "https://www.baidu.com/";//注册协议
    public static String SHARE_URL = "https://dl.quchangkeji.com/app/gewang.apk";//分享url
    public static int SHARE_IMAGE = R.mipmap.ic_launcher;//分享图标
    public static String SHARE_apk = "http://fir.im/tosingbeta";//分享图标 https://dl.quchangkeji.com/app/gewang.apk   https://dl.quchangkeji.com/app/android.apk
    public static String yaoqing_apk = "用了趣唱才知道，原来我唱歌也不错！你快来试试吧！";//分享图标 https://dl.quchangkeji.com/app/android.apk
    //    public static String SHARE_apk = "http://app.srv.quchangkeji.com:8083/tsAPI/pro/share.do";//分享图标
    //	云存储的临时外网域名:qc-test.oss-cn-hangzhou.aliyuncs.com
    public static String UPDATA_URL = "qc-test.oss-cn-hangzhou.aliyuncs.com";
    //    public static String ExportProPath = "趣唱作品";
    public static int can_send_pic_diy = 20;//DIY 我的MV和
    public static int can_send_pic_album = 20;//我的趣唱相册
    public static int DIY_PLAY_TIMES = 4;//DIY动态贴纸，每张图片播放时长
    public static int DIY_PLAY_TIMES_timescale = 20;//DIY动态贴纸，每张图片播放时长
    public static int DIY_GIF_FRAMES = 100;//帧率frames（每n毫秒取一个关键帧）
    public static int DIY_IMAGE_Width = 480;//diy合成剪切图片宽
    public static int DIY_IMAGE_Height = 270;//diy合成剪切图片高
    public static int DIY_MAKE_END_TIMES = 4;//DIY的时候，最后图片使用的参数

    public static boolean isTest = false;//表示测试开发模式
    public static int GET_COUNTRY = 86;//国家区域号：
    public static int pageSize = 20;//
    //官网地址：http://www.quchangkeji.com/
    //	public final static String FileTarget = Environment.getExternalStorageDirectory().getAbsolutePath() + "/quchang/image/";// 本地图片保存地址。(二维码)
    public final static String FileTarget = MyFileUtil.DIR_IMAGE.toString() + File.separator;// 本地图片保存地址。(二维码)

    /****** 可添加，上传图片个数 ******/




    /**
     * 权限申请
     * SD卡的读取和写入
     */
    String[] PERMS_EXTERNAL_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String[] PERMS_EXTERNAL_STORAGE_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

}
