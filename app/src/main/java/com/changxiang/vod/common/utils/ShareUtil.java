//package com.changxiang.vod.common.utils;
//
//
//import android.app.Activity;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.widget.Toast;
//
//import com.quchangkeji.tosingpk.R;
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMWeb;
//import com.umeng.socialize.shareboard.SnsPlatform;
//import com.umeng.socialize.utils.ShareBoardlistener;
//
///**
// * 友盟分享集成
// */
//public class ShareUtil {
//    private static SHARE_MEDIA share_media = SHARE_MEDIA.ALIPAY;
//    private static Context context;
//
//    /**
//     * @param context
//     * @param title
//     * @param imageurl
//     * @param shareurl
//     * @param type
//     */
//    public static void shareData(Context context, String sharecontent, String title, String imageurl, String shareurl, String type) {
//        goToShare(context, sharecontent, title, imageurl, shareurl, type);
//    }
//
//
//    public static void goToShare(final Context context, final String sharecontent, final String title, final String imageurl, final String shareurl, String type) {
//        ShareUtil.context = context;
//        final UMWeb web = new UMWeb(shareurl);//连接地址
//        web.setTitle(title);//标题
//        web.setDescription(sharecontent);//描述
//        ShareAction shareAction = new ShareAction((Activity) context);
//        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
////            final UMImage image = new UMImage(OriginDetailsActivity.this, R.drawable.default_icon);//资源文件
//
//        new ShareAction((Activity) context).withText(context.getString(R.string.app_name))
//                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
//                        SHARE_MEDIA.QQ,
//                        SHARE_MEDIA.QZONE,
//                        SHARE_MEDIA.SINA)
//                .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "info_icon_1", "info_icon_1")
//                .addButton("umeng_sharebutton_home_page", "umeng_sharebutton_home_page", "share_home_pager", "share_home_pager")
//                .setShareboardclickCallback(new ShareBoardlistener() {
//                    @Override
//                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_custom")) {
//                            Toast.makeText((Activity) context, context.getString(R.string.copy_link), Toast.LENGTH_LONG).show();
//                            ClipboardManager copy = (ClipboardManager) context
//                                    .getSystemService(context.CLIPBOARD_SERVICE);
////                            copy.setText("复制的内容001");
//                            try {
////                                copy.setText(originDetalt.getDetail().getYcUrl());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else if (snsPlatform.mShowWord.equals("umeng_sharebutton_home_page")) {
////
////                            Toast.makeText( OriginDetailsActivity.this, "分享到我的主页", Toast.LENGTH_LONG ).show();
//                            //初始化一个自定义的Dialog
////                            showShareDialog();
//                        } else {
//                            //TODO
//                            new ShareAction((Activity) context).withText(sharecontent)//原创NetInterface.SHARE_content+  originDetalt.getDetail().getRemark() + ""
//                                    .setPlatform(share_media)
//                                    .setCallback(umShareListener)
////                                        .withMedia( new UMImage( OriginDetailsActivity.this, NetInterface.SHARE_IMAGE ) )//SHARE_IMAGE
//                                    .withMedia(web)//SHARE_IMAGE
//                                    .share();
//
//                        }
//
//                    }
//                })
//                .open();
//
//
//    }
//
//
//    private static UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//
//        }
//
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            LogUtils.sysout("plat" + "platform" + platform);
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                LogUtils.sysout("分享成功啦---------");
////                Toast.makeText( OriginDetailsActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT ).show();
//            } else {
////                Toast.makeText( OriginDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT ).show();
//                LogUtils.sysout("分享成功啦++++++++");
//
////                toast( "分享成功" );
////                tempType = "0";
////                sendShareData("0", "");
//            }
//            //TODO
////            toast( "分享成功啦" );
////            LogUtils.sysout( "分享成功啦" );
////            sendShareData("0","");
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText((Activity) context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                LogUtils.sysout("throw" + "throw:" + t.getMessage());
//            }
//            LogUtils.sysout("分享失败啦++++++++");
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
////            Toast.makeText( OriginDetailsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT ).show();
////            LogUtils.sysout( "分享取消了+++++++++++++++++++++++++++++++++++++" );
//            LogUtils.sysout("分享取消了++++++++");
//        }
//    };
//
//
//}
