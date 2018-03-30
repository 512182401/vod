//package com.changxiang.vod.common.utils;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.TextView;
//
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.dialog.AlertDialog;
//import com.quchangkeji.tosingpk.common.view.CommonDialogManager;
//import com.quchangkeji.tosingpk.common.view.CustomDialog;
//import com.quchangkeji.tosingpk.common.view.MiNiProgressBar;
//import com.quchangkeji.tosingpk.module.base.BaseApplication;
//import com.quchangkeji.tosingpk.module.engine.JsonParserFirst;
//import com.quchangkeji.tosingpk.module.engine.NetInterfaceEngine;
//import com.quchangkeji.tosingpk.module.entry.UpdataByParams;
//import com.quchangkeji.tosingpk.module.ui.personal.net.PersonalNet;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
////import com.quchangkeji.tosing.R;
////import com.quchangkeji.tosing.common.utils.dialog.AlertDialog;
////import com.quchangkeji.tosing.common.view.CommonDialogManager;
////import com.quchangkeji.tosing.common.view.CustomDialog;
////import com.quchangkeji.tosing.common.view.MiNiProgressBar;
////import com.quchangkeji.tosing.module.engine.JsonParserFirst;
////import com.quchangkeji.tosing.module.engine.NetInterfaceEngine;
////import com.quchangkeji.tosing.module.entry.UpdataByParams;
////import com.quchangkeji.tosing.module.ui.personal.net.PersonalNet;
//
///**
// * 检查APP是否更新的工具类
// */
//public class CheckAppUpUtils {
//
//    private static final String TAG = CheckAppUpUtils.class.getSimpleName();
//    private static CheckAppUpUtils mUtils;
//    private static Context mContext;
//    private CustomDialog dialog;
//    private String apkUrl;
//    private int prograss = 0;// 显示下载进度的
//    private MiNiProgressBar pg;// 下载进度条的自定义progressBar
//    private String content = "检测到apk需要更新 , 是否更新?";
//    private boolean isToast;// 是否弹出不更新吐司的标志位
//    UpdataByParams mUpdataByParams;
//    String version = "1.2";
//
//    /**
//     * 私有构造函数
//     */
//    private CheckAppUpUtils() {
//    }
//
//    public static CheckAppUpUtils getInstance(Context context) {
//        mContext = context;
//        if (mUtils == null) {
//            mUtils = new CheckAppUpUtils();
//        }
//        return mUtils;
//    }
//
//    /**
//     * 检查app是否需要更新 , 接口100101. 取代原来的39号接口 ,参数boolean是否弹出吐司
//     */
//    public void serviceCheckApp100101(boolean isToast) {
//        this.isToast = isToast;
//        int uid = 0;
//        String openid = "";
//        String tag = "0";
//        String deviceid = AppUtil.getdeviceid(mContext) + "";
////		 version = AppUtil.getPackageInfo(mContext).versionCode + "";// app版本号
//        String versionName = AppUtil.getPackageInfo(mContext).versionName;
//        PersonalNet.api_CheckAppUp(mContext, BaseApplication.getUserId(), BaseApplication.getOpenId(), deviceid, version + "", versionName, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();
//                LogUtils.sysout("登录返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//
//                if (code == 0) {
//                    mUpdataByParams = UpdataByParams.objectFromData(data, "data");
//
//                    if (mUpdataByParams != null && !mUpdataByParams.equals("")) {
//                        if (mUpdataByParams.getVersionNo() != null && !mUpdataByParams.getVersionNo().equals(version)) {//版本号变
//                            handCheckResult(mUpdataByParams.getAppPath());
//                            LogUtils.sysout("00000000000");
//                        } else {
//                            LogUtils.sysout("11111111111");
//                        }
//                    } else {
//                        LogUtils.sysout("222222222222222");
//                    }
//                } else {
//                    LogUtils.sysout("没有更新！");
//                }
//            }
//        });
//    }
//
//    private void handCheckResult(String url) {
//        if (url != null && !url.equals("")) {
//            apkUrl = url;
//        } else {
//            apkUrl = "http://fir.im/tosingbeta";
//        }
//        try {
//            // 需要更新 , 显示dialog
////				toast("需要更新");
//            showUpAppDialog();
//            LogUtils.sysout("99999999999");
//        } catch (Exception e) {
//            LogUtils.info(TAG, "解析返回的json数据出现异常");
//            // engineHelper.sendEmpteyMsg(MsgHandCode.NET_CONNCT_FAIL);
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 第三步 : dialog做更新选择 展示是否需要更新app的提示
//     */
//    private void showUpAppDialog() {
//
//        new AlertDialog(mContext).builder()
//                .setTitle("温馨提示")
//                .setMsg("当前不是最新的版本，是否立即更新！")
//                .setPositiveButton("更新", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////						upApp(mUpdataByParams.getRoute(),  temp, final int what)
//                    }
//                }).setNegativeButton("下次提醒", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
//
////		dialog = EngineUtil.getDialog(mContext, "更新应用", content, "确认", "取消", new CollteDialogHelper() {
////			@Override
////			public void clickLook() {
////				dialog.dismiss();
////				SharedPreferences sp = mContext.getSharedPreferences("appconfig", Context.MODE_PRIVATE);
////				Editor edit = sp.edit();
////				edit.putBoolean("firstuse", true);
////				edit.commit();
////				upApp();// 调用下载的方法
////				showDownPrograss();
////			}
////
////			@Override
////			public void clickKnow() {
////				dialog.dismiss();
////			}
////		});
////		dialog.show();
//    }
//
//    /**
//     * 第四步(1) : 联网更新app 联网更新app
//     */
//
//
//    int allSize = 0;
//    int completeSize = 0;
//    File temp;
//
//    public void upApp(String URL, final File temp, final int what) {
//
//        NetInterfaceEngine.startDownloadClick(URL, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
////					handler.sendEmptyMessage(-1);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if (response != null) {
//
//                    if (response.body().contentLength() < 0) {
////							handler.sendEmptyMessage(-1);
//                        return;
//                    }
////						BaseApplication.isDownload = true;
//                    allSize = (int) response.body().contentLength();//总长度
////							roundProgressBar.setMax(ycSize);
//
//                    InputStream is = null;
//                    FileOutputStream fos = null;
//                    try {
//                        is = response.body().byteStream();
//                        fos = new FileOutputStream(temp);
//                        byte[] buf = new byte[1024 * 1024 * 10];
//                        int len = 0;
//                        long completeSize = 0l;//已下长度
//                        while ((len = is.read(buf)) > 0) {
//                            fos.write(buf, 0, len);
//                            completeSize += len;
////								Message msg = handler.obtainMessage();
////								msg.what = what;
////								msg.arg1 = len;
////								msg.arg2 = completeSize;
////								handler.sendMessage(msg);
//                            prograss = (int) (completeSize * 100 / (float) allSize);
//                            pg.setProgress(prograss);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        //BaseApplication.isDownload = false;
//                    } finally {
//                        if (fos != null) {
//                            fos.close();
//                        }
//                        if (is != null) {
//                            is.close();
//                        }
//                    }
//                } else {
////						BaseApplication.isDownload = false;
//                }
//            }
//        });
//    }
////		String target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hhxcjsb.apk";
////		NetInterfaceEngine.getEngine().downloadFile(apkUrl, target, new NetHelper() {
////
////			@Override
////			public void onSuccess(String result) {
////				apkUrl = result;
////				dialog.dismiss();
////				// 这个finish...
////				((Activity) mContext).finish();
////				AppUtil.install(mContext, apkUrl);
////			}
////
////			@Override
////			public void onFail(HttpException e, String err) {
////				LogUtils.info(TAG, "下载apk失败！" + err);
////			}
////
////			@Override
////			public void onLoadding(long total, long current, boolean isUploading) {
////				super.onLoadding(total, current, isUploading);
////				prograss = (int) (current * 100 / (float) total);
////				pg.setProgress(prograss);
////			}
////		});
//
//
//    /**
//     * 第四部(2) 一个dialog 显示下载用的dialog
//     */
//    private void showDownPrograss() {
//        dialog = CommonDialogManager.getInstance().createDialog(mContext, R.layout.down_file_dialog);
//        dialog.setCdHelper(new CustomDialog.CustomDialogHelper() {
//            @Override
//            public void showDialog(CustomDialog dialog) {
//                ((TextView) dialog.findViewById(R.id.tv_downfile_dialog_desc)).setText("正在为您下载最新版应用，请稍候..");
//                pg = (MiNiProgressBar) dialog.findViewById(R.id.mpg_donwnload_show);
//            }
//        });
//        dialog.setCancelable(false);
//        dialog.show();
//    }
//
//    public void toast(String showmsg) {//showToastShort
////		ToastUtil.showToast(mContext,showmsg);
//    }
//}
