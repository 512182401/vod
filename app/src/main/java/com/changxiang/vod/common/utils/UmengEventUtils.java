//package com.changxiang.vod.common.utils;
//
///*
// *友盟统计工具
// */
//
//import android.content.Context;
//
//import com.umeng.analytics.MobclickAgent;
//
//import java.util.HashMap;
//
//public class UmengEventUtils {
//
//    private static final String DEVICEID = "deviceid";
//    private static final String IMSI = "imsi";
//    private static final String MAC = "mac";
//    private static final String PHONEMODEL = "phone_model";
//    private static final String USERID = "userid";
//    private static final String PHONE = "phone";
//    private static final String GOODSID = "goodsid";
//    private static final String GOODSNAME = "goodsname";
//    private static final String SHOPID = "shopid";
//    private static final String SHOPNAME = "shopname";
//    private static final String PRICE = "price";
//    private static final String PAYWAY = "payway";
//
//    /**
//     * 首次安装
//     *
//     * @param ctx
//     */
//    public static void toInstallClick(Context ctx) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        MobclickAgent.onEvent(ctx, "install", map);
//    }
//
//    /**
//     * 登录
//     *
//     * @param ctx
//     * @param userId
//     * @param phone
//     */
//    public static void toLoginClick(Context ctx, String userId, String phone) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        map.put(PHONE, phone);
//        MobclickAgent.onEvent(ctx, "login", map);
//    }
//
//    /**
//     * 1
//     * PK歌王-进行中活动
//     * <p>
//     * 封面图
//     * <p>
//     * 点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void activityIconClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "activityicon", map);
////        LogUtils.sysout("55555555555555555555555555555555555");
//    }
//
//    /**
//     * 2
//     * <p>
//     * PK歌王-进行中活动
//     * <p>
//     * 试听进度条
//     * <p>
//     * 点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void auditionClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "audition", map);
//    }
//
//    /**
//     *3
//
//     PK歌王-进行中活动
//
//     欣赏参赛作品按钮
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void appreciateClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "appreciate", map);
//    }
//
//    /**
//     4
//
//     PK歌王-进行中活动
//
//     我要PK按钮
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void gotoPKClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "hometopk", map);
////        LogUtils.sysout("9999999999999999999");
//    }
//    /**5
//
//     赛事欣赏
//
//     banner图
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void bannerClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "banner", map);
//    }
//    /**6
//
//     赛事欣赏
//
//     本期最新作品
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void newworksClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "newworks", map);
//    }
//    /**     7
//
//     赛事欣赏
//
//     本期作品排行
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void topworksClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "topworks", map);
//    }
//    /**   8
//
//     赛事欣赏
//
//     历届歌王
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void historyworksClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "historyworks", map);
//    }
//    /** 9
//
//     赛事欣赏-本期最新作品
//
//     头像+昵称
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void currentNewHeadClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "currentnewhead", map);
//    }
//    /** 10
//
//     赛事欣赏-本期最新作品
//
//     作品封面图
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void currentNewCoverClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "currentnewcover", map);
//    }
//    /**  11
//     * 赛事欣赏-本期作品排行
//
//     头像+昵称
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void currentTopHeadClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "currenttophead", map);
//    }
//
//
//    /** 12
//
//     赛事欣赏-本期最新作品
//
//     作品封面图
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void currentTopCoverClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "currenttopcover", map);
//    }
//
//    /** 13
//     * 赛事欣赏-历届歌王
//
//     查看本期参赛作品
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void historyAllClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "historyall", map);
//    }
//    /**14
//
//     参赛作品集合
//
//     搜索
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void historySearchClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "historysearch", map);
//    }
//    /**15
//
//     作品详情页面
//
//     翻唱排名
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void TopRankingClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "TopRankingEvent", map);
//    }
//    /**16
//
//     作品详情页面
//
//     弹幕开关
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void DanmaEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "DanmaEvent", map);
//    }
//    /**17
//
//     作品详情页面
//
//     评论回复按钮
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void sendReplyEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "sendReplyEvent", map);
//    }
//    /**18
//
//     作品详情页面
//
//     送花按钮
//
//     点击量
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void SendFlowerEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "SendFlowerEvent", map);
//    }
//
//    /**19
//
//     作品详情页面
//
//     点赞按钮
//
//     点击量
//     * @param ctx
//     * @param userId
//     */
//    public static void GreatEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "GreatEvent", map);
//    }
//
//    /**20
//
//     作品详情页面
//
//     评论按钮
//
//     点击量
//     * @param ctx
//     * @param userId
//     */
//    public static void CommentEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "CommentEvent", map);
//    }
//    /**
//     21
//
//     作品详情页面
//
//     分享按钮
//
//     点击量
//     * @param ctx
//     * @param userId
//     */
//    public static void ShareEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "ShareEvent", map);
//    }
//    /**
//     21
//
//     作品详情页面
//
//     分享按钮
//
//     点击量
//     * @param ctx
//     * @param userId
//     */
//    public static void MyPKEventClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "MyPKEvent", map);
//    }
//
////                                UmengEventUtils.MyPKEventClick(this,BaseApplication.getUserId());//友盟统计
//
////        UmengEventUtils.TopRankingClick(getActivity(),BaseApplication.getUserId());//友盟统计
////        UmengEventUtils.historyAllClick(context, BaseApplication.getUserId());//友盟统计
//
//
//    /**
//     * 退出
//     *
//     * @param ctx
//     * @param userId
//     */
//    public static void toLogoutClick(Context ctx, String userId) {
//        HashMap<String, String> map = getInstallMap(ctx);
//        map.put(USERID, userId);
//        MobclickAgent.onEvent(ctx, "logout", map);
//    }
//
//    /**
//     * 充值
//     *
//     * @param ctx
//     * @param userId
//     * @param goodsId
//     * @param goodsName
//     * @param price
//     * @param payway
//     */
//    public static void toRechargeClick(Context ctx, String userId, String phone,
//                                       String goodsId, String goodsName, int price, String payway) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(USERID, userId);
//        map.put(PHONE, phone);
//        map.put(GOODSID, goodsId);
//        map.put(GOODSNAME, goodsName);
//        map.put(PRICE, String.valueOf(price));
//        map.put(PAYWAY, payway);
//        MobclickAgent.onEvent(ctx, "recharge", map);
//    }
//
//    /**
//     * 扫码app支付
//     *
//     * @param ctx
//     * @param userId
//     * @param phone
//     * @param shopId
//     * @param shopName
//     * @param price
//     * @param payway
//     */
//    public static void toSweepPaymentClick(Context ctx, String userId, String phone,
//                                           String shopId, String shopName, int price, String payway) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(USERID, userId);
//        map.put(PHONE, phone);
//        map.put(SHOPID, shopId);
//        map.put(SHOPNAME, shopName);
//        map.put(PRICE, String.valueOf(price));
//        map.put(PAYWAY, payway);
//        MobclickAgent.onEvent(ctx, "sweeppayment", map);
//    }
//
//    private static HashMap<String, String> getInstallMap(Context ctx) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(PHONEMODEL, CommonUtils.getPhoneModel());
//        map.put(DEVICEID, CommonUtils.getDeviceId(ctx));
//        map.put(IMSI, CommonUtils.getIMSI(ctx));
//        map.put(MAC, CommonUtils.getLocalMacAddressFromWifiInfo(ctx));
//        return map;
//    }
//}
