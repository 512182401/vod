package com.changxiang.vod.module.ui;//package tosingpk.quchangkeji.com.quchangpk.module.ui;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.location.LocationManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.content.ContextCompat;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.AppUtil;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.utils.SharedPrefManager;
//import com.quchangkeji.tosing.common.utils.dialog.AlertDialog;
//import com.quchangkeji.tosing.common.view.CommonDialogManager;
//import com.quchangkeji.tosing.common.view.CustomDialog;
//import com.quchangkeji.tosing.common.view.MiNiProgressBar;
//import com.quchangkeji.tosing.common.view.ThreeButtonAlertDialog;
//import com.quchangkeji.tosing.module.base.BaseApplication;
//import com.quchangkeji.tosing.module.engine.JsonParserFirst;
//import com.quchangkeji.tosing.module.engine.NetInterfaceEngine;
//import com.quchangkeji.tosing.module.entry.PersonSet;
//import com.quchangkeji.tosing.module.entry.UpdataByParams;
//import com.quchangkeji.tosing.module.entry.User;
//import com.quchangkeji.tosing.module.jpush.JPushManager;
//import com.quchangkeji.tosing.module.ui.base.BaseActivity;
//import com.quchangkeji.tosing.module.ui.choose.FragmentChoose;
//import com.quchangkeji.tosing.module.ui.discover.FragmentDiscover;
//import com.quchangkeji.tosing.module.ui.login.db.SharedPrefLogin;
//import com.quchangkeji.tosing.module.ui.login.net.LoginNet;
//import com.quchangkeji.tosing.module.ui.origin.FragmentOriginNew;
//import com.quchangkeji.tosing.module.ui.personal.fragment.FragmentPersonal;
//import com.quchangkeji.tosing.module.ui.personal.net.PersonalNet;
//import com.quchangkeji.tosing.module.ui.sing.ChooseActivity;
//import com.quchangkeji.tosing.module.ui.sing.fragment.FragmentSing;
//import com.quchangkeji.tosing.module.util.MyFileUtil;
//import com.umeng.analytics.MobclickAgent;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//public class HomeActivity extends BaseActivity {
//
////    private final int SDK_PERMISSION_REQUEST = 127;
////    private String permissionInfo;
//
//    //主界面的四个单选按钮
//    private RadioButton button_sing, button_origin, button_discover, button_person;
//    private Fragment[] fragments;
//    private FragmentPersonal mFragmentPersonal;
//    private FragmentSing mFragmentSing;
//    private FragmentChoose mFragmentChoose;
//    //    FragmentOrigin mFragmentOrigin;
//    private FragmentOriginNew mNewFragmentOrigin;
//    private FragmentDiscover mFragmentDiscover;
//    //记录上一次显示的Fragment，默认显示第0个
//    private int lastIndex = 0;
//    private int index;//准备到达的fragment
//    private User user;
//
//    private int getlocsucc = 0;// 用于计算定位次数，如果定位一分钟还没成功，怎照样关闭。
//
//    private LocationClient mLocationClient = null;
//    //    public BDLocationListener myListener = new MyLocationListener();
//    private MiNiProgressBar pg;// 下载进度条的自定义progressBar
//    private int allSize = 0;
//    //    int completeSize = 0;
//    private final String appDir = MyFileUtil.DIR_APK.toString() + File.separator + "quchang.apk";//路径(cache)
//    private File temp;
//    private int prograss = 0;// 显示下载进度的
//    private RadioGroup radioGroup;
//    private ImageView chooseIv;
//    private FrameLayout buttomFl;//底部按钮布局
//    private String nowDate = "";
//    private final Object objLock = new Object();
//    private PersonSet personset = null;//设置数据请求：
//
//    @Override
//    public void handMsg(Message msg) {
////        Intent intent;
//        switch (msg.what) {
//            case -1:
//                toast(getString(R.string.get_no_data));
////                    handler.sendEmptyMessageDelayed( -1, 1000 );
//                break;
//            case 0:
////                toast( "成功" );
////                    handler.sendEmptyMessageDelayed( 0, 1000 );
//
////                initData();
//                break;
//            case 1:
////                toast( responsemsg );//
////                handler.sendEmptyMessageDelayed( 1, 100 );
//                break;
//            case 2://刷新成功，刷新界面
////                madapter.setDataList( singersclub.getResults() );
////                handler.sendEmptyMessageDelayed( 2, 100 );
//                break;
//            case 3://加载更多成功，刷新界面
////                madapter.addDataList( singersclub.getResults() );
////                handler.sendEmptyMessageDelayed( 3, 100 );
//                break;
//            case 4://版本跟新提示框
//                showUpAppDialog();
//                SharedPrefManager.getInstance().cacheApiupapp(nowDate);
////                handler.sendEmptyMessageDelayed( 4, 100 );
//                break;
//
//            case 123://权限
//
////                handler.sendEmptyMessageDelayed( 123, 100 );
//                break;
//
//            case 9://自动登录成功（）
//                getPersonSetData();//获取设置数据
////                handler.sendEmptyMessageDelayed( 9, 100 );
////                club_image.setImageBitmap( newbitmap );
//                break;
//            case 10://提交成功（）
////                handler.sendEmptyMessageDelayed( 10, 100 );
////                club_image.setImageBitmap( newbitmap );
//                break;
//            case 22://定位成功（）
//                mLocationClient.stop();// 关闭定位
////                synchronized (objLock) {
////                    if (mLocationClient != null && mLocationClient.isStarted()) {
////                        mLocationClient.stop();// 关闭定位
////                    }
////                }
//
//
////                LogUtils.sysout("关闭定位++++++++++++++++++++++++++++");
////                handler.sendEmptyMessageDelayed( 22, 100 );
////                club_image.setImageBitmap( newbitmap );
//                break;
//            case 1111://下载成功：
//                dialogupapp.dismiss();
////                toast("提示安装apk");
//                LogUtils.sysout("提示安装apk");
////               finish();
//                AppUtil.install(this, appDir);
////                handler.sendEmptyMessageDelayed( 10, 100 );
////                club_image.setImageBitmap( newbitmap );
//                break;
//        }
//    }
//
//    public void getfragment(){
//        LogUtils.sysout("+++++++++++++添加fragment++++++++++++++");
//        mFragmentSing = new FragmentSing();
//        mFragmentChoose = new FragmentChoose();
//        mNewFragmentOrigin = new FragmentOriginNew();
//        mFragmentDiscover = new FragmentDiscover();
//        mFragmentPersonal = new FragmentPersonal();
////        if(fragments!=null){
////            mFragmentChoose.onBackPressed();
////        }
//        fragments = new Fragment[]{
////                mFragmentSing,
//                mNewFragmentOrigin,
//                mFragmentChoose,
//                mFragmentDiscover,
//                mFragmentPersonal,
//        };
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        for (int i = 0; i < fragments.length; i++) {
//            Fragment fragment = fragments[i];
//            transaction.add(R.id.homeactivity_fragment, fragments[i]);
//            transaction.hide(fragment);//先隐藏起来
//        }
//        transaction.show(fragments[0]);//默认显示第0个
//        transaction.commit();//提交事务
//        button_origin.setChecked(true);
//        showFragment(getIntent());
//        radioGroup = (RadioGroup) findViewById(R.id.homeactivity_rg);
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                switch (i) {
//
//                    case R.id.homeactivity_origin:
//                        //判断首页是否在录歌
//                        if (BaseApplication.isRecord) {
//                            showWindow(1);
//                        } else {
//                            if (BaseApplication.state == 1) {//如果正在播放，暂停
//                                mFragmentSing.pause();
//                                mFragmentSing.setOrientationDisable(false);
//                            }
//                            index = 0;
//                            /*mFragmentOrigin.toSingAnimation();
//                            mFragmentOrigin.getinitData();*/
//                            mNewFragmentOrigin.toSingAnimation();
//                            mNewFragmentOrigin.getinitData();
//                        }
//                        /*if (BaseApplication.state==0){
//                            index = 1;
//                            mFragmentOrigin.toSingAnimation();
//                            mFragmentOrigin.getinitData();
//                        }else {
//                            showWindow(1);
//                        }*/
//                        break;
//                    case R.id.homeactivity_sing:
//                        index = 1;
////                        if (BaseApplication.state != 0) {
////                            mFragmentSing.setOrientationDisable(false);
////                        }
//                        if(mFragmentChoose!=null) {
//                            mFragmentChoose.getinitData();
//                        }
//                        break;
//                    case R.id.homeactivity_discover:
//                        LogUtils.sysout("homeactivity_discover+++++++++定位");
//                        //判断是否可以定位：并且经纬度没有值
//                        if (isOPenGPS(HomeActivity.this)) {
//                            if (BaseApplication.app.mLongitude.equals("") || BaseApplication.app.mLatitude.equals("")) {
//                                getlocaton();// 取得定位城市
//                            }
//                        } else {
////            chenckisPositioningsys();
////            return;
//                        }
//
//                        //判断首页是否在录歌
//                        if (BaseApplication.isRecord) {
//                            showWindow(2);
//                        } else {
//                            if (BaseApplication.state == 1) {//如果正在播放，暂停
//                                mFragmentSing.pause();
//                                mFragmentSing.setOrientationDisable(false);
//                            }
//                            index = 2;
//                            if (user != null && !user.equals("")) {//判断是否是登录状态
//                                mFragmentDiscover.getData();//取得发现数据
//                            }
//                        }
//                        /*if (BaseApplication.state==0) {
//                            index = 2;
//                            if (user != null && !user.equals("")) {//判断是否是登录状态
//                                mFragmentDiscover.getData();//取得发现数据
//                            }
//                        }else {
//                            showWindow(2);
//                        }*/
//                        break;
//                    case R.id.homeactivity_person:
//
//                        //判断首页是否在录歌
//                        if (BaseApplication.isRecord) {
//                            showWindow(3);
//                        } else {
//                            if (BaseApplication.state == 1) {//如果正在播放，暂停
//                                mFragmentSing.pause();
//                                mFragmentSing.setOrientationDisable(false);
//                            }
//                            mFragmentPersonal.loginedIs();
//                            if (user != null && !user.equals("")) {//判断是否是登录状态
//                                mFragmentPersonal.gettheAllData();//取得发现数据
//                            }
//                            index = 3;
//                        }
//                        if(mFragmentPersonal!=null) {
//                            mFragmentPersonal.getinitData();
//                        }
//                        /*if (BaseApplication.state==0) {
//                            mFragmentPersonal.loginedIs();
//                            if (user != null && !user.equals("")) {//判断是否是登录状态
//                                mFragmentPersonal.gettheAllData();//取得发现数据
//                            }
//                            index = 3;
//                        }else {
//                            showWindow(3);
//                        }*/
//                        break;
//                    default:
//                        index = 0;
//                }
//                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                //隐藏上一次显示的fragment
//                transaction1.hide(fragments[lastIndex]);
//                //显示选中的fragment
//                transaction1.show(fragments[index]);
//                transaction1.commit();
//                //记录lastIndex
//                lastIndex = index;
//
//                //showRadioGroup();//设置底部菜单显示效果；
//            }
//        });
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setmHomeActivity(this);
//        BaseApplication.mHomeActivity = this;
//        setContentView(R.layout.activity_home);
////        String appDir = MyFileUtil.DIR_APK.toString() + File.separator;//路径(cache)
//        temp = new File(appDir);
////        View dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
//        button_sing = (RadioButton) findViewById(R.id.homeactivity_sing);
//        button_origin = (RadioButton) findViewById(R.id.homeactivity_origin);
//        button_discover = (RadioButton) findViewById(R.id.homeactivity_discover);
//        button_person = (RadioButton) findViewById(R.id.homeactivity_person);
//        chooseIv = (ImageView) findViewById(R.id.home_choose_iv);
//        buttomFl = (FrameLayout) findViewById(R.id.fl);
//        getfragment();
//
//
//        String appupdatadate = SharedPrefManager.getInstance().getCacheApiupapp();
//
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
//        nowDate = sDateFormat.format(new java.util.Date());
//        if (!appupdatadate.equals(nowDate)) {//通过日期判断是否检测版本更新：
////        if (true) {//通过日期判断是否检测版本更新：
////            AppUtil.install(this, apkUrl);
//            serviceCheckApp100101(true);
////            SharedPrefManager.getInstance().cacheApiupapp(nowDate);
//        }
//        login();//自动登录
//        getPersimmionsLocaton();//定位
//
//        chooseIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (BaseApplication.isRecord) {
//                    recordDialog();
//                } else {
//                    mFragmentSing.isFromOtherPage = false;
//                    Intent intent1 = new Intent(HomeActivity.this, ChooseActivity.class);
//                    startActivity(intent1);
//                }
//            }
//        });
//    }
//
//    //点歌按钮事件
//    private void recordDialog() {
//        int score = mFragmentSing.getScore();
//        ThreeButtonAlertDialog myDialog = new ThreeButtonAlertDialog(HomeActivity.this).builder();
//        myDialog.setTitle("提示")
//                .setMsg("歌曲总得分" + score + "! 这首歌还没有录完，确定要停止录歌吗？")
//                .setCancelable(true)
//                .setPositiveButton("放弃录歌", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mFragmentSing.reset();
//                        Intent intent1 = new Intent(HomeActivity.this, ChooseActivity.class);
//                        startActivity(intent1);
//                    }
//                }).setNeutralButton("保存录歌", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFragmentSing.saveRecord();
//            }
//        }).setNegativeButton("继续录歌", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
//    }
//
//
//    private void showWindow(final int tag) {
//        int score = mFragmentSing.getScore();
//        ThreeButtonAlertDialog myDialog = new ThreeButtonAlertDialog(HomeActivity.this).builder();
//        myDialog.setTitle("提示")
//                .setMsg("歌曲总得分" + score + "! 这首歌还没有录完，确定要停止录歌吗？")
//                .setCancelable(false)
//                .setPositiveButton("放弃录歌", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (tag) {
//                            case 0:
//                                //mFragmentSing.stop();
//                                break;
//                            case 1:
//                                //mFragmentSing.stop();
//                                mFragmentSing.reset();
//                                index = 1;
////                                mFragmentOrigin.toSingAnimation();
////                                mFragmentOrigin.getinitData();
//                                mNewFragmentOrigin.toSingAnimation();
//                                mNewFragmentOrigin.getinitData();
//                                break;
//                            case 2:
//                                mFragmentSing.reset();
//                                index = 2;
//                                if (user != null && !user.equals("")) {//判断是否是登录状态
//                                    mFragmentDiscover.getData();//取得发现数据
//
//                                }
//                                break;
//                            case 3:
//                                mFragmentSing.reset();
//                                mFragmentPersonal.loginedIs();
//                                if (user != null && !user.equals("")) {//判断是否是登录状态
//                                    mFragmentPersonal.gettheAllData();//取得发现数据
//                                }
//                                index = 3;
//                                break;
//                        }
//                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                        //隐藏上一次显示的fragment
//                        transaction1.hide(fragments[lastIndex]);
//                        //显示选中的fragment
//                        transaction1.show(fragments[index]);
//                        transaction1.commit();
//                        //记录lastIndex
//                        lastIndex = index;
//                    }
//                }).setNeutralButton("保存录歌", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                button_sing.setChecked(true);
//                button_origin.setChecked(false);
//                button_discover.setChecked(false);
//                button_person.setChecked(false);
//                mFragmentSing.saveRecord();
//            }
//        }).setNegativeButton("继续录歌", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                button_sing.setChecked(true);
//                button_origin.setChecked(false);
//                button_discover.setChecked(false);
//                button_person.setChecked(false);
//            }
//        }).show();
//    }
//
//    public void getlocaton() {
//        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
////        mLocationClient.registerLocationListener( myListener ); // 注册监听函数
//        mLocationClient.registerLocationListener(mListener); // 注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true);// 打开GPS
//        option.setAddrType("all");// 返回的定位结果包含地址信息
//        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
//        option.setScanSpan(3000);// 设置发起定位请求的间隔时间为3000ms
//        option.disableCache(false);// 禁止启用缓存定位
//        option.setPriority(LocationClientOption.NetWorkFirst);// 网络定位优先
//        mLocationClient.setLocOption(option);// 使用设置
//        mLocationClient.start();// 开启定位SDK
//        mLocationClient.requestLocation();// 开始请求位置
//    }
//
//
//    /*****
//     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
//     */
//    private BDLocationListener mListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // TODO Auto-generated method stub
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                StringBuffer sb = new StringBuffer(256);
//                sb.append(location.getCity());// 获得城市
//
////                LogUtils.sysout( "返回结果:" + "定位数据++++++++++++++" );
//                String cityString = sb.toString().trim();
//
//                String mLongitude = location.getLongitude() + "";
//                String mLatitude = location.getLatitude() + "";
//                LogUtils.sysout("返回经度:" + mLongitude);
//                LogUtils.sysout("返回纬度:" + mLatitude);
//
//                String loc = null;
//                if (cityString == null || cityString.equals("null")) {
//                    // loc = "深圳" ;
//                    loc = "定位中";
//                    getlocsucc++;
//                    if (getlocsucc > 20) {// 关闭定位
//                        synchronized (objLock) {
//                            if (mLocationClient != null && mLocationClient.isStarted()) {
//                                mLocationClient.stop();// 关闭定位
//                            }
//                        }
//
//                        loc = "定位失败";
//                        BaseApplication.app.locCity = loc;
//                        handler.sendEmptyMessageDelayed(22, 100);
//                    }
//                } else {
//                    loc = cityString.substring(0, 2);
//                    BaseApplication.app.locCity = loc;
//                    BaseApplication.app.mLongitude = mLongitude;
//                    BaseApplication.app.mLatitude = mLatitude;
//                    LogUtils.sysout("返回城市:" + loc);
//                    // 关闭定位
//                    synchronized (objLock) {
//                        if (mLocationClient != null && mLocationClient.isStarted()) {
//                            mLocationClient.stop();// 关闭定位
//                        }
//                    }
//                    LogUtils.sysout("关闭定位:" + loc);
//                    handler.sendEmptyMessageDelayed(22, 100);
//
//                }
//            } else {
//                boolean isloction = true;
//                if (isloction)
//                    BaseApplication.app.locCity = "获取失败";
//                LogUtils.sysout("获取定位失败:");
////                SearchActivity.this.cityButton.setText("无法定位");
////                return;
//            }
//        }
//
//        public void onConnectHotSpotMessage(String s, int i) {
//        }
//    };
//
//    //设置底部菜单显示效果；
//    private void showRadioGroup(int lastIndex) {
//        switch (lastIndex) {
//            case 0:
//                button_sing.setChecked(false);
//                button_origin.setChecked(true);
//                button_discover.setChecked(false);
//                button_person.setChecked(false);
//                break;
//            case 1:
//                button_sing.setChecked(true);
//                button_origin.setChecked(false);
//                button_discover.setChecked(false);
//                button_person.setChecked(false);
//                break;
//            case 2:
//                button_sing.setChecked(false);
//                button_origin.setChecked(false);
//                button_discover.setChecked(true);
//                button_person.setChecked(false);
//                break;
//            case 3:
//                button_sing.setChecked(false);
//                button_origin.setChecked(false);
//                button_discover.setChecked(false);
//                button_person.setChecked(true);
//                break;
//        }
//
//    }
//
//
//    //横竖屏时，显示或者隐藏底部按钮
//    private void hideOrShowButtom(boolean isPortrait) {
//        if (isPortrait) {//竖屏
//            buttomFl.setVisibility(View.VISIBLE);
//        } else {
//            buttomFl.setVisibility(View.GONE);
//        }
//    }
//
//    private void login() {
//        user = new User();
//        String uid = null;
//        try {
//            uid = SharedPrefLogin.getInstance().getUserFromLocal().getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        final String pwd = SharedPrefManager.getInstance().getUserPwd();
//
//        LogUtils.sysout("请求数据uid=" + uid);
////        user.setPhone( phone );
//        if (uid == null || "".equals(uid)) {
//            //设置极光推送
//            JPushManager.getInstance().resumeJPush();
//            JPushManager.getInstance().setAlias("");
//            return;
//        }
//        handApi_autoLogin(uid);
//    }
//
////    String uid = null;
//
//    //TODO
//    private void handApi_autoLogin(String uid) {
////        String uid = user.getId();
//        //网络交互实现：
////        showProgressDialog( "请求数据", true );
//        LoginNet.api_autoLogin(this, uid, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.sysout("联网登录出现网络异常错误！");
////                toast( "请求失败" );
//                closeProgressDialog();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                closeProgressDialog();
//
//                String data = response.body().string();
//                LogUtils.sysout("返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//
//                if (code == 0) {
//                    user = User.objectFromData(data, "data");
//
//                    LogUtils.sysout("登录返回结果:" + user.toString());
//                    //user = LoginJsonParser.parser_Login(data);
//                    if (user != null && !user.equals("")) {
//                        ((BaseApplication) getApplicationContext()).setUser(user);
//                        SharedPrefLogin.getInstance().saveUserToLocal( user );
//                        BaseApplication.setIslogined(true);
//                        JPushManager.getInstance().resumeJPush();
//                        JPushManager.getInstance().setAlias(user.getId());
//                        handler.sendEmptyMessageDelayed(9, 100);
//                    }
//                }
//            }
//        });
//
//
//    }
//
//
//    //TODO
//    private void getPersonSetData() {
//        //网络交互实现：
////        showProgressDialog( "正在个人设置信息获取", true );
//        String uid = null;
//        try {
//            uid = user.getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (uid != null && !uid.equals("")) {
//
//        } else {
//            return;
//        }
//        PersonalNet.api_personSet(this, uid, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.sysout("联网登录出现网络异常错误！");
////                toast( "请求失败" );
//                closeProgressDialog();
////                handler.sendEmptyMessageDelayed( 2, 10 );
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                closeProgressDialog();
//
//                String data = response.body().string();
//                LogUtils.sysout("个人设置信息获取返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//
//                if (code == 0) {
//                    personset = PersonSet.objectFromData(data, "data");
//
//                    LogUtils.sysout("个人设置信息获取:" + user.toString());
//                    //user = LoginJsonParser.parser_Login(data);
//                    if (personset != null && !personset.equals("")) {
////保存到本地：
//                        SharedPrefManager.getInstance().cacheApiPersonSet(personset);
//                    }
////                    handler.sendEmptyMessageDelayed( 2, 100 );
//                } else {
////                    String meg = JsonParserFirst.getRetMsg( response.body().toString() );
////                    toast(meg);
////                    responsemsg = JsonParserFirst.getRetMsg(data);
////                    handler.sendEmptyMessageDelayed( 2, 100 );
//
//                }
//            }
//        });
//    }
//
//
//    private long firstTime = 0;//记录用户首次点击返回键的时间
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else {
//                if (BaseApplication.isRecord) {
//                    showBackWindow();
//                } else {
//                    //判断是的是趣唱界面，
//                    if (index != 0) {
//                        //隐藏上一次显示的fragment
//                        index = 0;
//                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                        //隐藏上一次显示的fragment
//                        transaction1.hide(fragments[lastIndex]);
//                        //显示选中的fragment
//                        transaction1.show(fragments[index]);
//                        transaction1.commit();
//                        //记录lastIndex
//                        lastIndex = index;
//                        showRadioGroup(lastIndex);
//                    } else {
//                        if (System.currentTimeMillis() - firstTime > 2000) {
//                            //Toast.makeText(MainActivityTest.this,"再按一次退出程序--->onKeyDown",Toast.LENGTH_SHORT).show();
//                            toast(getString(R.string.exit_notice));
//                            firstTime = System.currentTimeMillis();
//                        } else {
////                            finish();
//                            finishAllActivity();
//                            System.exit(0);
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    //返回按钮事件
//    private void showBackWindow() {
//        int score = mFragmentSing.getScore();
//        ThreeButtonAlertDialog myDialog = new ThreeButtonAlertDialog(HomeActivity.this).builder();
//        myDialog.setTitle("提示")
//                .setMsg("歌曲总得分" + score + "! 这首歌还没有录完，确定要停止录歌吗？")
//                .setCancelable(true)
//                .setPositiveButton("放弃录歌", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mFragmentSing.reset();
//                    }
//                }).setNeutralButton("保存录歌", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFragmentSing.saveRecord();
//            }
//        }).setNegativeButton("继续录歌", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        LogUtils.w("TAG", "onStart");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        LogUtils.w("TAG", "onRestart");
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        MobclickAgent.onResume(this);
//        LogUtils.w("TAG", "onResume");
//    }
//
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        showFragment(intent);
//
////        LogUtils.sysout("************onNewIntent**********");
//    }
//
//    private void showFragment(Intent intent) {
//        if (intent != null) {
////            String songId = intent.getStringExtra("songId");
////            String musicType = intent.getStringExtra("musicType");
////            String repeat = intent.getStringExtra("repeat");
//            String fragmentitem  = "sing";
//            fragmentitem = intent.getStringExtra("fragmentitem");
//            if (fragmentitem != null && fragmentitem.equals("origin")) {//跳转到热听（原创）界面
//                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                transaction1.hide(fragments[lastIndex]);//隐藏上一次显示的fragment
//                index = 0;
//                transaction1.show(fragments[index]);//显示选中的fragment
//                transaction1.commit();
//                lastIndex = index;//记录lastIndex
//                showRadioGroup(lastIndex);//趣唱显示勾选状态
//            } else  if (fragmentitem != null && fragmentitem.equals("sing")) {//跳转到热听（原创）界面{
////                if (songId != null && musicType != null) {
////                    mFragmentSing.setIntentData(songId, musicType, repeat);
////                }
//                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                transaction1.hide(fragments[lastIndex]);//隐藏上一次显示的fragment
//                index = 1;
//                transaction1.show(fragments[index]);//显示选中的fragment
//                transaction1.commit();
//                lastIndex = index;//记录lastIndex
//                showRadioGroup(lastIndex);//趣唱显示勾选状态
//            }else  if (fragmentitem != null && fragmentitem.equals("language")) {//卻換了語言
////                getfragment();
////                LogUtils.sysout("TTTTTTTTTTTTTTTTTTTTTTTTTTTT");
//                mFragmentChoose = new FragmentChoose();
//            }else{
//                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                transaction1.hide(fragments[lastIndex]);//隐藏上一次显示的fragment
//                index = 0;
//                transaction1.show(fragments[index]);//显示选中的fragment
//                transaction1.commit();
//                lastIndex = index;//记录lastIndex
//                showRadioGroup(lastIndex);//趣唱显示勾选状态
//            }
//        }
//    }
//
//
//    private UpdataByParams mUpdataByParams;
//    private String version = "1.0";
//    private String apkUrl;
//
//    /**
//     * 检查app是否需要更新 ,
//     */
//    private void serviceCheckApp100101(boolean isToast) {
//
//        version = AppUtil.getPackageInfo(this).versionCode + "";// app版本号
//
//        PersonalNet.api_CheckAppUp(this, version, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();
//                LogUtils.sysout("版本更新返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//
//                if (code == 0) {
//                    mUpdataByParams = UpdataByParams.objectFromData(data, "data");
//
//                    if (mUpdataByParams != null && !mUpdataByParams.equals("")) {
//                        if (mUpdataByParams.getClientVersion() != null && !mUpdataByParams.getClientVersion().equals(version)) {//版本号变
//                            handCheckResult(mUpdataByParams.getRoute());
////                            LogUtils.sysout("00000000000");
//                        } else {
////                            LogUtils.sysout("11111111111");
//                        }
//                    } else {
////                        LogUtils.sysout("222222222222222");
//                    }
//                } else {
//                    LogUtils.sysout("没有更新！");
//                }
//            }
//        });
//    }
//
//    private void handCheckResult(String url) {
//
//        try {
//            // 需要更新 , 显示dialog
////				toast("需要更新");
////            showUpAppDialog();
//
//            handler.sendEmptyMessageDelayed(4, 100);
////            LogUtils.sysout("99999999999");
//        } catch (Exception e) {
//            LogUtils.sysout("版本解析返回的json数据出现异常");
//            // engineHelper.sendEmpteyMsg(MsgHandCode.NET_CONNCT_FAIL);
//            e.printStackTrace();
//        }
//    }
//
//    private void showUpAppDialog() {
//        String meg = mUpdataByParams.getRemark();
//        if (meg != null && !meg.equals("")) {
//
//        } else {
//            meg = "您需要更新app";
//        }
//        String url = "";
//        try {
//            url = mUpdataByParams.getRoute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (url != null && !url.equals("")) {
//            apkUrl = url;
//            new AlertDialog(this).builder()
//                    .setTitle(getString(R.string.up_app))
//                    .setMsg(meg)
//                    .setPositiveButton(getString(R.string.up_now), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            upApp();// 调用下载的方法
//                        }
//                    }).setNegativeButton(getString(R.string.up_next), new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            }).show();
//        } else {
//            apkUrl = "http://fir.im/tosingbeta";
//            new AlertDialog(this).builder()
//                    .setTitle(getString(R.string.up_app))
//                    .setMsg(meg)
//                    .setPositiveButton(getString(R.string.up_now), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent newIntent01 = new Intent(HomeActivity.this, ShowWebContActivity.class);
//                            if (apkUrl != null && !apkUrl.equals("")) {
//                                newIntent01.putExtra("url", apkUrl);
//                                newIntent01.putExtra("title", "趣唱更新");
//                                startActivity(newIntent01);
//                            }
//                        }
//                    }).setNegativeButton(getString(R.string.up_next), new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            }).show();
//        }
//
//    }
//
//
//    private void upApp() {//
//        showDownPrograss();
////        downloadApk(appDir);
//        new AsyncTask<Void, Void, Void>() {
//
//
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                downloadApk(appDir);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//
//                LogUtils.sysout("DDDDDDDDDDDDDDDDDDD");
//
//            }
//        }.execute();
//
//
//    }
//
//    private void downloadApk(String downloadApk) {
//        NetInterfaceEngine.startDownloadClick(apkUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.sysout("+++++++++++++++下载失败！");
////					handler.sendEmptyMessage(-1);
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if (response != null) {
//                    allSize = (int) response.body().contentLength();
//                    InputStream is = null;
//                    FileOutputStream fos = null;
//                    try {
//                        is = response.body().byteStream();
//                        fos = new FileOutputStream(temp);
//                        byte[] buf = new byte[1024 * 10];
//                        int len = 0;
//                        int completeSize = 0;
//                        long time = System.currentTimeMillis();
//                        while ((len = is.read(buf)) > 0) {
//                            fos.write(buf, 0, len);
//                            completeSize += len;
//                            prograss = (int) ((completeSize / (float) allSize) * 100);
//                            pg.setProgress(prograss);
//
//                            if (completeSize >= allSize) {
//                                handler.sendEmptyMessage(1111);
//                            }
//                            /*if (BaseApplication.isStop){
//                                isHandStop=true;
//                                downloadThread.interrupt();
//                                downloadThread=null;
//                                //handler.sendEmptyMessageDelayed(5,100);
//                            }else {
//                                fos.write(buf, 0, len);
//                                completeSize += len;
//                                if (completeSize == allSize) {
//                                    handler.sendEmptyMessage(tag);
//                                }
//                                //通过广播把下载进度发布出去
//                                if (System.currentTimeMillis() - time > 1000) {
//                                    time = System.currentTimeMillis();
//                                    if (tag==1){
//                                        percent=(int) (completeSize * 5.0 / fileSize);
//                                    }else if (tag==2){
//                                        percent=(int) (completeSize * 5.0 / fileSize)+5;
//                                    }else if (tag==3){
//                                        percent=(int) (completeSize * 45.0 / fileSize)+10;
//                                    }else {
//                                        percent=(int) (completeSize * 45.0 / fileSize)+55;
//                                    }
//                                    sendBroad("ACTION_DOWNLOADING",percent);
//                                    LogUtils.w("TAG","downloadThread"+downloadThread.getName()+",fileSize:"+fileSize+",completeSize:"+completeSize);
//                                }
//                            }*/
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        handler.sendEmptyMessageDelayed(-5, 0);
//                    } finally {
//                        try {
//                            if (fos != null) {
//                                fos.close();
//                            }
//                            if (is != null) {
//                                is.close();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 第四部(2) 一个dialog 显示下载用的dialog
//     */
//    private CustomDialog dialogupapp;
//
//    private void showDownPrograss() {
//        dialogupapp = CommonDialogManager.getInstance().createDialog(this, R.layout.down_file_dialog);
//        dialogupapp.setCdHelper(new CustomDialog.CustomDialogHelper() {
//            @Override
//            public void showDialog(CustomDialog dialog) {
//                ((TextView) dialog.findViewById(R.id.tv_downfile_dialog_desc)).setText("正在为您下载最新版应用，请稍候..");
//                pg = (MiNiProgressBar) dialog.findViewById(R.id.mpg_donwnload_show);
//            }
//        });
//        dialogupapp.setCancelable(false);
//        dialogupapp.show();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            //radioGroup.setVisibility(View.GONE);
//            hideOrShowButtom(false);
//        } else {
//            //radioGroup.setVisibility(View.VISIBLE);
//            hideOrShowButtom(true);
//        }
//    }
//
//
//    private static boolean isOPenGPS(final Context context) {
//        LocationManager locationManager
//                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
//        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        return gps || network;
//
//    }
//
//
//    @TargetApi(23)
//    private void getPersimmionsLocaton() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, mPermissionList, 1234);
//                //权限还没有授予，需要在这里写申请权限的代码
//            } else {
//                //权限已经被授予，在这里直接写要执行的相应方法即可
////                changImage();
//                getlocaton();// 取得定位城市
//            }
//
//        } else {
//            getlocaton();// 取得定位城市
////            changImage();
//        }
//
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            ArrayList<String> permissions = new ArrayList<String>();
////            /***
////             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
////             */
////            // 定位精确位置
////            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
////                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
////            }
////            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
////                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
////            }
////			/*
////			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
////			 */
////            // 读写权限
////            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
////                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
////            }
////            // 读取电话状态权限
////            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
////                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
////            }
////
////            if (permissions.size() > 0) {
////                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
////            }
////        }
//    }
//
//    @TargetApi(23)
//    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
//            if (shouldShowRequestPermissionRationale(permission)) {
//                return true;
//            } else {
//                permissionsList.add(permission);
//                return false;
//            }
//
//        } else {
//            return true;
//        }
//    }
//
//    @TargetApi(23)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        // TODO Auto-generated method stub
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 123://分享的
////                goToShare();
//                break;
//            case 1234://更换图片
//                LogUtils.sysout("获取到定位权限。。。。。");
//                if (isOPenGPS(this)) {
//                    getlocsucc = 0;
//                    getlocaton();// 取得定位城市
//                } else {
////            chenckisPositioningsys();
////            return;
//                }
//
//                break;
//        }
//
//    }
//}
