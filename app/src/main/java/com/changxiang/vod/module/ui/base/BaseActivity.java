package com.changxiang.vod.module.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.view.BaseProgressDialog;
import com.changxiang.vod.module.base.AppManager;
import com.changxiang.vod.module.engine.base.BaseEngine;
import com.changxiang.vod.module.engine.base.MsgHelper;
import com.changxiang.vod.module.entry.MessageBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class BaseActivity extends FragmentActivity {


    //    public HomeActivity mHomeActivity = null;
    private BaseProgressDialog mDialog;
    private FragmentStack fragStack = null;
    private LinearLayout vProgressBar;
    private FrameLayout contentView;
    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            handMsg(msg);
        }
    };

    public abstract void handMsg(Message msg);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//需要打开，和趣唱一样
        AppManager.getInstance().addActivity(this);
        fragStack = new FragmentStack(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(getApplicationContext());

        //Toast.makeText(this, "类的名字: " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEvent(String str) {
        switch (str) {
            case "language":
                changeAppLanguage();
                recreate();//刷新界面
                break;
            case "StartMusic":
                break;
        }
    }
    @Subscribe
    public  void onEvent(MessageBean msg){
        playMusic(msg);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void getPercent(Integer percent) {
        LogUtils.w("percent", "--percent33333333333333333333333------" + percent);
        percent = percent.intValue();
        setDownloadProgress(percent);
    }

    //下载进度
    public void setDownloadProgress(int percent) {

    }

    //播放音乐
    public void playMusic(MessageBean msg) {

    }

    //    public void setmHomeActivity(HomeActivity mHomeActivity){
//        this.mHomeActivity = mHomeActivity;
//    }
//    public HomeActivity getmHomeActivity() {
//
//            return mHomeActivity;
//
//
//    }
    private void changeAppLanguage() {
//        String sta = SharedPrefManager.getInstance().getCacheApiLanguage();//getLanuageIsChinese() ? "zh" : "en";//这是SharedPreferences工具类，用于保存设置，代码很简单，自己实现吧
//        // 本地语言设置
//        String[] str = sta.split("_");
//        if (str.length == 2) {
//            Locale myLocale = new Locale(str[0], str[1]);
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = myLocale;
//            res.updateConfiguration(conf, dm);
//        }
    }


    private final String TAG = "MPermissions";
    private int REQUEST_CODE_PERMISSION = 0x00099;

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), requestCode);
        }
    }

    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 权限获取成功
     *
     * @param request_code_permission
     */
    public void permissionSuccess(int request_code_permission) {
        Log.d(TAG, "获取权限成功=" + request_code_permission);
    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    private void permissionFail(int requestCode) {
        Log.d(TAG, "获取权限失败=" + requestCode);
    }

    @Deprecated
    private void showProgressDialogView() {
        if (vProgressBar != null) {
            vProgressBar.setVisibility(View.VISIBLE);
            return;
        }
        vProgressBar = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.common_progress_layout, null);
        ImageView spaceshipImage = (ImageView) vProgressBar.findViewById(R.id.img);
        TextView tipTextView = (TextView) vProgressBar.findViewById(R.id.tipTextView);// 提示文字
        vProgressBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeProgressDialogView();
            }
        });
        // // 加载动画
        // AnimationDrawable animationDrawable = (AnimationDrawable)
        // spaceshipImage
        // .getBackground();
        // animationDrawable.start();
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_progress);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(R.string.loading);// 设置加载信息
        int w = getResources().getDisplayMetrics().widthPixels;
        int h = getResources().getDisplayMetrics().heightPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h, 0);
        vProgressBar.setLayoutParams(params);
        contentView.addView(vProgressBar, params);
    }

    @Deprecated
    private void closeProgressDialogView() {
        if (vProgressBar.getVisibility() == View.VISIBLE) {
            vProgressBar.setVisibility(View.GONE);
        }
    }

    private BaseEngine engine;

    protected void setEngine(BaseEngine eg) {
        this.engine = eg;
        engine.setEngineHelper(new MsgHelper() {


            @Override
            public void sendEmpteyMsg(int what) {
                handler.sendEmptyMessage(what);
            }

            @Override
            public void sendMsg(Message msg) {
                handler.sendMessage(msg);
            }


        });
    }

    protected void finishActivity() {
        AppManager.getInstance().finishActivity(this);

    }

    protected void finishActivityByClz(Class<?> clazz) {
        AppManager.getInstance().finishActivityByClz(clazz);

    }

    protected void finishAllActivity() {
        AppManager.getInstance().finishAllActivity();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            AppManager.getInstance().finishActivity(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void toast(String text) {
        toast(this, text, Toast.LENGTH_SHORT);
    }

    private Toast mToast;

    private void toast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        }
        mToast.setDuration(duration);
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 管理Fragment block start
     */

    public void pushFragment(Fragment frg) {
        fragStack.pushFragment(frg);
    }

    public void pushFragmentWithAnim(Fragment frg) {
        fragStack.pushFragmentWithAnim(frg);
    }

    public boolean popFragment() {
        return fragStack.popFragment();
    }

    public void pushRootFragment(Fragment frg) {
        fragStack.pushRootFragment(frg);
    }

    /**
     * 管理Fragment block stop
     */
    public void showProgressDialog(String message, boolean isCancelable) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = BaseProgressDialog.getInstance();
        mDialog.show(this, message, isCancelable);
    }

    public void closeProgressDialog() {
        if (this != null && !this.isFinishing()) {
            if (mDialog == null) {
                return;
            }
            mDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
//        MobclickAgent.onPageStart(this.getClass().getSimpleName()); // 统计页面
//        UmengUtils.onResumeToActivity(this);

        super.onResume();
        // regiserNetReceiver();
    }

    @Override
    protected void onPause() {
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName()); // 统计页面

        super.onPause();
        // unregiserNetReceiver();
    }


    /**
     * wifi是否可用
     */
    private boolean isWifiEnabled;
    /**
     * 网络是否可用
     */
    private boolean isNerworkEnabled;
    /**
     * GPRS是否可用(2G/3G/4G)
     */
    private boolean isMobileEnabled;
    public int MobileType = -1;// GPRS网络类型 1,2,3,4 分别对应1G，2G，3G，4G

    public class ConnectionChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.log("net_status", "网络状态改变");

            boolean success = false;

            // 获得网络连接服务
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // State state = connManager.getActiveNetworkInfo().getState();
            // 获取WIFI网络连接状态

//            NetworkInfo infoGPRS = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//            State state;
//            if (infoGPRS == null) {// 如果无GPRS模块
//                isMobileEnabled = false;
//            } else {
//                state = infoGPRS.getState();
//                // 判断是否正在使用GPRS网络
//                if (State.CONNECTED == state) {
//                    isMobileEnabled = true;
//                    success = true;
//                } else {
//                    isMobileEnabled = false;
//                }
//            }
//            NetworkInfo infoWIFI = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            if (infoWIFI == null) {// 如果无GPRS模块，则检测wifi模块
//                isWifiEnabled = false;
//            } else {
//                state = infoWIFI.getState();
//                // 判断是否正在使用GPRS网络
//                if (State.CONNECTED == state) {
//                    isWifiEnabled = true;
//                    success = true;
//                } else {
//                    isWifiEnabled = false;
//                }
//            }
            if (!success) {
                Toast.makeText(context, "网络断开", Toast.LENGTH_LONG).show();
                isNerworkEnabled = false;
            } else {
                isNerworkEnabled = true;
                networkCallback();
            }
        }
    }


    private ConnectionChangeReceiver mNetReciever;

    public void regiserNetReceiver() {
        mNetReciever = new ConnectionChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetReciever, filter);
    }

    public void unregiserNetReceiver() {
        if (mNetReciever != null) {
            unregisterReceiver(mNetReciever);
        }
    }

    /**
     * 要实现网络恢复时自动刷新界面的可以从写该函数
     */
    private void networkCallback() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
    }
}
