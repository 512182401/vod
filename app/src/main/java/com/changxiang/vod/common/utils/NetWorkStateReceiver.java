//package com.changxiang.vod.common.utils;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//
//import com.quchangkeji.tosingpk.module.entry.Setting;
//
//
///**
// * Created by 15976 on 2016/12/29.
// */
//
//public class NetWorkStateReceiver extends BroadcastReceiver {
//
//    private NetChangeListener netChangeListener;
//    Setting mSetting;
//    private int wifiNotice;//用户设置的网络提醒 0, 1：非wifi环境下每次都提醒； 2：非wifi环境下首次都提醒；3：不做任何提醒
//
//    //网络变化回调接口
//    public interface NetChangeListener {
//
////        public void wifiDisconnect();//wifi断开连接
//
//        public void dataDisconnect();//数据断开连接
//
//        /**
//         * 网络提醒 0, 1：非wifi环境下每次都提醒； 2：非wifi环境下首次都提醒；3：不做任何提醒
//         * @param wifiNotice
//         */
//        public void dataConnect(int wifiNotice);//数据已连接
//
//        public void wifiConnect();//wifi已连接连接
//    }
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        mSetting = SharedPrefManager.getInstance().getSettingFromLocal();
//
//        System.out.println("网络状态发生变化");
//        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//
//            //获得ConnectivityManager对象
//            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            //获取ConnectivityManager对象对应的NetworkInfo对象
//            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//            //获取移动数据连接的信息
//            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                //Toast.makeText( context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT ).show();
//                if (netChangeListener != null) {
//                    netChangeListener.wifiConnect();
//                }
//
//            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//                //Toast.makeText( context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT ).show();
//                if (netChangeListener != null) {
//                    netChangeListener.wifiConnect();
//                }
//
//            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {//移动数据
//
//                if (mSetting != null) {//使用默认值,每次都提醒
//                    wifiNotice=mSetting.getWifi();
//                }
//                if (netChangeListener != null) {
//                    netChangeListener.dataConnect(wifiNotice);
//                }
//            } else {//网络未连接
//                if (netChangeListener != null) {
//                    netChangeListener.dataDisconnect();
//                }
//
//            }
//        } else {
//            //这里的就不写了，前面有写，大同小异
//            System.out.println("API level 大于21");
//            //获得ConnectivityManager对象
//            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            //获取所有网络连接的信息
//            /*Network[] networks = connMgr.getAllNetworks();
//            //用于存放网络连接信息
//            StringBuilder sb = new StringBuilder();
//            //通过循环将网络信息逐个取出来
//            for (int i = 0; i < networks.length; i++) {*/
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//                if (networkInfo != null && networkInfo.isAvailable()) {
//                    String name = networkInfo.getTypeName();
//                    if ("WIFI".equals(name)) {//WIFI已连接,移动数据已连接
//                        if (netChangeListener != null) {
//                            netChangeListener.wifiConnect();
//                        }
//
//                    } else {//WIFI已断开,移动数据已连接
//
//                        if (mSetting != null) {//使用默认值,每次都提醒
//                            wifiNotice=mSetting.getWifi();
//                        }
//                        if (netChangeListener != null) {
//                            netChangeListener.dataConnect(wifiNotice);
//                        }
//                    }
//                } else {//网络断开
//                    if (netChangeListener != null) {
//                        netChangeListener.dataDisconnect();
//                    }
//
//                }
//                //sb.append( networkInfo.getTypeName() + " connect is " + networkInfo.isConnected() );
//            }
//    }
//
//    public void setNetChangeListener(NetChangeListener netChangeListener) {
//        this.netChangeListener = netChangeListener;
//    }
//}
