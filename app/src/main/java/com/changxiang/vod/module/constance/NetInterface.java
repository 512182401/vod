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


    /******
     * 微信支付
     ******/
    //appid 微信分配的appID
    public static final String APP_ID = "wxecb544ddccdd0df5";//wx11f4ad30030a2ea6
    //商户号 微信分配的公众账号ID
    public static final String MCH_ID = "1493773122";//
    //  API密钥，在商户平台设置
    public static final String API_KEY = "8797E4BA5BEE5A425B6A6646B235B76C";//


    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017010904949957";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088521556267001";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCC/Y2T1J3G6RpZfiPiQ3atck4RslDGczhhYVG0a1Q1l9Tn+EO3QJnA0OT+vungmAL1lmat9iPQf2cXYaYgG6M4//9zRQ6y9m5g/PeuZnOTOAzlARR+0T7f9CkZf+VcxE39gBzrcts5b/h4Uxy9KBTIs0MtBeiSsbRNkEVZr6KoyZnZejeU/tKp8kMJKDwHo9M2T3mSPjYZwhsjtxaglF65JDHwV3m6rYUdYd8rmoViI+gBE2ej1s5ly++yWwlkcPjdeh0Czxal2SnHY133Or61aXWTHWktLGQT5+yQ1HB73lO5A9LLLLmEexW2d/bZz9x6ASvG4pkAbbvVb3m7U1x9AgMBAAECggEAMxt+e6k/DfaxAy3GDmfIDBBHCXyLK4KVAtmXqU8fAfIFbaRdt0gLNUHLtuECG1D0BqHx2fnpK1YqAONmj6x42Kp5Hp5ZB9ZCZrEMag0rFsuNrGPpfTli0LpQttRBxxoIW9n6bprXkedwCHJVCjBzO4NFuHFPtryC3qhlhoKSckv3Ba0Y7t8pj5tY0aYhAzZaYFjEVQD9cxA8w8ufEkUfagx46xZlB+OZ5sV3tWE27AwAaXY8E533iFeu7nHVNN1zUjwVLCVOr1DuOVV8jJd5UwoxbMRcLhDxJw7T0uj7xjWzl4lzRtW/neEieP2Xjn9C0qO6EaHGgLLFOY/gqndYwQKBgQDCceyq2AuQ5NB8Uxn7O/z+aEgMCnPgD72OYpqma2YIIh/2RXnY0DaceALwoISoXn1SzP9vx0ANAqqWdW4HftgmLKoeqKW1EDZJioz35tae4/O9hN1JWkpEFM3r5+7/6i0eV1VqLubYJMkQjcxHVYVqTrWOwwpouHfuxW7pOV9BmQKBgQCsdTAN2YXTCFd0cd3FCX2RKJVtbr9wVFP28D4RrZSFab6fMD+469HbgcFh49cWNWt4DfylPxBYrnJFSxhC91TICpVpLNLfGvnxS4SA/quMgBv9OiTiBu7eI6okhj9g1rfl+eOkk+7VdqzAHeCaJpzjruJSp39xeQZhl97SmgWIhQKBgGTnK7KIhI//PXWWxj/KpIH86Y5eCj22zV5LKwe7aepEkyOAcDej7QMLgDMQiWa6Cfzi5pdB5aoavR1w+NUo7M/6k5lvWPZ54EIPhRHMF4nCrySlAxRip3Fm/mkyJaNmCV3aYjfE+QQnKrPNcbl+1soQ6ESaUBeByrL8f47Ft/nZAoGAYWaWS1wtXvD5I8UjsaMYjiFTs8i2yqR1/6PUCnv0xBzjEllXxAM8NTNlmvnpgpuNJPMRW+rwOoXb+BLfkwLJmWmQ1SQBvQwpWgSCw7ASB3pbVHvAVQABbOjLsXE43mKm3nZyAYOjgmBSVPcK4UUpmtYs8O205suptPjnWh6vPVkCgYEAn5yVTMzLi45PB7kpnks6nTw8vXH4saF+6ghiyg/cz4jdRh+NnM0LOYCn8sEs5J1kadPYiECqydtDF458sa9U9h40FE9t8IZ0ZERxgir526+BJlkL4kQMGy3JMT0JaM9IYqdH3GpbVb0R0y7UGJ3NHart3pLkrMVk28f7QSmt5AA=";
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIOdoCVrr/bWnmPE\n" +
            "aDYoPJSH7cfrtGBTMFQjeE2v73GlTEWPjJNs7T6aTPcmXNeIiyLmLzOL8AgArrn2\n" +
            "pjgpw04nIetW3ZaiczrRzK3E+zadfEDf4KighWgGeYVgLRfNYirDBnaqQMk3XcvD\n" +
            "hYoF6hTZWg4q8ABNcvXTI4FLGWtVAgMBAAECgYBdbCp7xWee55KAMK7kGkV+DMo8\n" +
            "iVN8uC/q2U6QnlxxJ6rvCUj4cG4qbK5LFID8QKC6gfdpOGCF3a4otCoiXYqSTxEU\n" +
            "untEqS5rtovxvW/fP3TPLZ3DFFIwt2F7np6dvRQ5sLqRvHWNS4r716XDXfxV/Bsf\n" +
            "lEwaaxAOZ4/6uG1nVQJBAMC28NxyRb/9MdrEufKDjMcuZEnw1TjSJVyuKlS8eSdT\n" +
            "vq12viH+ZWeFHYcq9U8ZVN0vKEWUBmCHV8qZlyKS2RMCQQCu1j8cMG0bkQuB8WHt\n" +
            "Nvs80lRXqIMvfsyDco06JjAdUJfULs187qwSrMN2nhwWQf+8gFSPv7YFfqcfAYHR\n" +
            "F173AkEAqVxcwq9eYwJl3OfErr8zahx4II8ZI61zDkc1hnB4XLp5OULAh2llvps6\n" +
            "vv5exVvyu8tkrfkPvadT3QYrz0OUpwJBAK2kSV+01Ng5EQXId6rCHXoFpxC8YzYL\n" +
            "qBCw94SWItkqjvCEXz/CR5Hwldy8IUcV22kax2FRVPVWGaMYuxawMHcCQGir3STI\n" +
            "2uP/RIcShyA12ZfwzYRbJqtAVt3C991l8piDrf5n0lxtSP1TeFZ/L5zjDjCGEveN\n" +
            "a5HJuj4re8xhyps=";


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
