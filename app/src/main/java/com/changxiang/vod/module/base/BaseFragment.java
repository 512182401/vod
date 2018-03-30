package com.changxiang.vod.module.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.view.BaseProgressDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/1.
 */
public abstract class BaseFragment extends Fragment {
    private BaseProgressDialog mDialog;
    protected View root;


    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            handMsg(msg);
        }
    };


    public abstract void handMsg(Message msg);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(getLayoutId(),null);
//        root = View.inflate(getActivity(),getLayoutId(),null );
//        root = LayoutInflater.from( getActivity() ).inflate(getLayoutId(),null );
        initViews();
        initEvents();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 获取布局的id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initViews();

    /**
     * 初始化视频的监听事件
     */
    protected abstract void initEvents();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    public void showProgressDialog(String message, boolean isCancelable){
        if(mDialog!=null){
            mDialog.dismiss();
        }
        if(this.getActivity()==null||this.getActivity().isFinishing()){
            return;
        }
        mDialog= BaseProgressDialog.getInstance();
        mDialog.show(this.getActivity(), message, isCancelable);
    }

    public void closeProgressDialog(){
        if(mDialog==null){
            return;
        }
        mDialog.dismiss();
    }






    public void toast(String text) {
        toast(getActivity(), text, Toast.LENGTH_SHORT);
    }

    private Toast mToast;

    private void toast(Context context, String msg, int duration) {
        if (mToast == null&&context!=null) {
            mToast = Toast.makeText(context, msg, duration);
        }
        if (mToast!=null){
            mToast.setDuration(duration);
            mToast.setText(msg);
            mToast.show();
        }

    }

    private final String TAG="MPermissions";
    private int REQUEST_CODE_PERMISSION=0x00099;

    /**
     *检测所有的权限是否都已授权
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            return true;
        }
        for (String permission:permissions){
            if (ContextCompat.checkSelfPermission(getContext(),permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     *请求权限
     * @param permissions   请求的权限
     * @param requestCode   请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode){
        this.REQUEST_CODE_PERMISSION=requestCode;
        if (checkPermissions(permissions)){
            permissionSuccess(REQUEST_CODE_PERMISSION);
        }else {
            List<String> needPermissions=getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(getActivity(),needPermissions.toArray(new String[needPermissions.size()]),requestCode);
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
        new AlertDialog.Builder(getActivity())
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
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        startActivity(intent);
    }

    /**
     * 权限获取成功
     * @param request_code_permission
     */
    public void permissionSuccess(int request_code_permission) {
        LogUtils.w(TAG, "获取权限成功=" + request_code_permission);
    }
    /**
     * 权限获取失败
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        LogUtils.w(TAG, "获取权限失败=" + requestCode);
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
//        UmengUtils.onResumeToActivity(this.getActivity());
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
//        UmengUtils.onPauseToActivity(this.getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
