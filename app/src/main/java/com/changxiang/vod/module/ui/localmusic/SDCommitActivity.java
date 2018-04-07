package com.changxiang.vod.module.ui.localmusic;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.alibaba.sdk.android.oss.ClientConfiguration;
//import com.alibaba.sdk.android.oss.ClientException;
//import com.alibaba.sdk.android.oss.OSS;
//import com.alibaba.sdk.android.oss.OSSClient;
//import com.alibaba.sdk.android.oss.ServiceException;
//import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
//import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
//import com.alibaba.sdk.android.oss.common.OSSLog;
//import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
//import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
//import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
//import com.alibaba.sdk.android.oss.model.PutObjectRequest;
//import com.alibaba.sdk.android.oss.model.PutObjectResult;
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.AppUtil;
//import com.quchangkeji.tosing.common.utils.DensityUtil;
//import com.quchangkeji.tosing.common.utils.FileUtil;
//import com.quchangkeji.tosing.common.utils.ImageLoader;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.utils.RegValUtil;
//import com.quchangkeji.tosing.common.utils.dialog.ActionSheetDialog;
//import com.quchangkeji.tosing.common.utils.dialog.AlertDialog;
//import com.quchangkeji.tosing.common.view.CustomEditText;
//import com.quchangkeji.tosing.common.view.RoundProgressBar;
//import com.quchangkeji.tosing.module.base.BaseApplication;
//import com.quchangkeji.tosing.module.constance.NetInterface;
//import com.quchangkeji.tosing.module.db.ComposeManager;
//import com.quchangkeji.tosing.module.db.LocalCompose;
//import com.quchangkeji.tosing.module.engine.JsonParserFirst;
//import com.quchangkeji.tosing.module.engine.LoginedDialog;
//import com.quchangkeji.tosing.module.entry.UploadWork;
//import com.quchangkeji.tosing.module.entry.UploadZP;
//import com.quchangkeji.tosing.module.entry.User;
//import com.quchangkeji.tosing.module.ui.base.BaseActivity;
//import com.quchangkeji.tosing.module.ui.img_select.crop.Crop;
//import com.quchangkeji.tosing.module.ui.localMusic.aliyun.PutObjectSamples;
//import com.quchangkeji.tosing.module.ui.localMusic.net.LocalMusicNet;
//import com.quchangkeji.tosing.module.ui.music_download.downloadservice.UploadWorkService;
//import com.quchangkeji.tosing.module.util.MyFileUtil;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.common.utils.DensityUtil;
import com.changxiang.vod.common.utils.ImageLoader;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.utils.RegValUtil;
import com.changxiang.vod.common.utils.dialog.ActionSheetDialog;
import com.changxiang.vod.common.utils.dialog.AlertDialog;
import com.changxiang.vod.common.view.CustomEditText;
import com.changxiang.vod.common.view.RoundProgressBar;
import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.constance.NetInterface;
import com.changxiang.vod.module.db.ComposeManager;
import com.changxiang.vod.module.db.LocalCompose;
import com.changxiang.vod.module.engine.JsonParserFirst;
import com.changxiang.vod.module.engine.LoginedDialog;
import com.changxiang.vod.module.entry.User;
import com.changxiang.vod.module.img_select.crop.Crop;
import com.changxiang.vod.module.ui.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SDCommitActivity extends BaseActivity implements View.OnClickListener {
    //    PutObjectSamples engine;
//    private OSS oss;
    private Context context;
    // 运行sample前需要配置以下字段为有效的值
    private static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private static String uploadFilePath = "";
    private static String securityToken = "";
    private static String expirationstr = "";

    private static final String testBucket = "qc-test";
    //    private static final String testBucket = "qc-operation";
    private static String uploadObject = "sampleObject";
    private static final String downloadObject = "sampleObject";
    //  开发文档  https://help.aliyun.com/document_detail/32042.html?spm=5176.doc32055.6.690.o3u6up
    //更改头像，封面
    private Uri photoUri;// 照片uri
    private String songname = "";
    private String imageurl = "";
    private String mediatype = "video";
    private String remark = "";
    private String ycVipId = "";//

    private static Bitmap.CompressFormat iconType = Bitmap.CompressFormat.PNG;// 图片格式
    public final static int USE_CAREMA = 0;
    public final static int USE_IMGS = 1;
    public final static int USE_CROP = 2;
    public final static int GO_CITY = 3;
    public final static int GO_HOMETOWN = 4;
    public final static int CHOOSE_BIG_PICTURE = 5;

    private TextView top_text;//顶部居中显示
    private ImageView bt_right;//右边显示
    private ImageView bt_back;//返回
    private TextView right_text;//y右边显示
    private LinearLayout progressLl;//下载时显示的布局
    private RoundProgressBar roundProgressBar;
    TextView progressHint;//下载进度

    private ImageView song_image; // 封面 hend_song_image
    private CustomEditText content; // 描述 edit_centent
    private TextView show_count;// 数字统计 tv_show_count
    //上传作品
    private Button updata_work; // bt_save
    private CustomEditText name_content; // name_edit
    //    private String work_name; // 作品名称
    private boolean isname;//是否填写了名称
    private boolean isimage;//是否添加了封面

    private LocalCompose mLocalCompose;//实体类
    private LocalCompose dbLocalCompose;//实体类(数据库)
    private int rowheight = 0;
    private int rowWidth = 0;
    private int videoWidth = 0;
    private int videoHeight = 0;
    //    UploadZP mUploadZP;
    private boolean isuploading = false;//是否再上传中

    String responsemsg = null;//网络请求返回信息
    private ComposeManager mComposeManager;//数据库：
    private Bitmap bit;
    private String imagePath = MyFileUtil.DIR_IMAGE.toString() + File.separator;//路径(cache);//mTempMixAudioFile 待合成的audio

    @Override
    public void handMsg(Message msg) {


        Intent intent;
        switch (msg.what) {
            case -1:
                toast("联网登录出现网络异常错误");
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                right_text.setText("上传");
                isuploading = false;
                //roundProgressBar.setVisibility( View.GONE );
                progressLl.setVisibility(View.GONE);
                setViewEnable(true);
                break;
            case 0:
//                startUpload();


////                toast( "获取权限成功，开始上传云端" );
//                //roundProgressBar.setVisibility( View.VISIBLE );
//                progressLl.setVisibility( View.VISIBLE );
//                initAliYun();//获取数据成功，开始上传云端
//
//                handler.sendEmptyMessageDelayed( 1000, 1000 );//开启进度条
                break;

            case 1:
                toast(responsemsg);//
                right_text.setText("上传");
                isuploading = false;
                //roundProgressBar.setVisibility( View.GONE );
                progressLl.setVisibility(View.GONE);
                setViewEnable(true);
//                handler.sendEmptyMessageDelayed( 1, 100 );
                break;
            case 3://修改性别成功
                toast("成功");
//                    handler.sendEmptyMessageDelayed( 0, 1000 );

                initData();
                break;
            case 1000://上传中
                if (isuploading) {
                    LogUtils.sysout("上传中");
                    if (roundProgressBar != null) {
                        double percent = progress * 100.0 / (roundProgressBar.getMax());
                        DecimalFormat decimalFormat = new DecimalFormat(".0");
                        String p = decimalFormat.format(percent);
                        progressHint.setText("作品上传中" + p + "%");
                        roundProgressBar.setProgress((int) progress);
                    }
                    LogUtils.sysout("progress= " + progress);
                    if (progress < 100) {
                        handler.sendEmptyMessageDelayed(1000, 1000);
                    } else {
                        right_text.setText("上传");
                        isuploading = false;
                        //roundProgressBar.setVisibility( View.GONE );
                        progressLl.setVisibility(View.GONE);
                        setViewEnable(true);
                    }

                } else {
                    if (roundProgressBar != null) {
                        //roundProgressBar.setVisibility( View.GONE );
                        progressLl.setVisibility(View.GONE);
                    }
                }

                break;
            case 2000://上传成功1:保存到数据库，2跳转到成功界面

                try {
//                    (String key,String field,String content)
                    mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "isUpload", "1");//标志数据库为已经上传
                    LogUtils.sysout("修改数据库，为已经上传。");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LogUtils.sysout("上传成功之后保存服务器成功");
                right_text.setText("已上传");
                right_text.setEnabled(false);
//                right_text.setText( "上传" );
//                isuploading = false;
                setViewEnable(true);
//                updataimage();
//                handler.sendEmptyMessageDelayed( 2000, 100 );
                //跳转到上传成功界面：
//                intent = new Intent(SDCommitActivity.this, UploadSucceedActivity.class);
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("LocalCompose", mLocalCompose);
//                intent.putExtras(bundle);
//
//                intent.putExtra("remark", remark);
//                intent.putExtra("ycVipId", ycVipId);
//                startActivity(intent);
                break;
            case 3000://上传失败
                LogUtils.sysout("上传失败");
                toast("上传失败");

                right_text.setText("上传");
                isuploading = false;
                setViewEnable(true);
//                updataimage();
//                handler.sendEmptyMessageDelayed( 3000, 100 );
                break;
            case 5000://上传成功,保存服务器
                LogUtils.sysout("上传成功");
                if (roundProgressBar != null) {
                    //roundProgressBar.setVisibility( View.GONE );
                    progressLl.setVisibility(View.GONE);
                }

//                updataSucceed();
//                handler.sendEmptyMessageDelayed( 5000, 100 );
                break;

        }
    }

    public void stopUpload() {//停止上传
//        if (task != null) {
//            task.cancel(); // 可以取消任务
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic_commit_sd);
        mComposeManager = ComposeManager.getComposeManager(this);
        context = this;
        rowWidth = this.getResources().getDisplayMetrics().widthPixels;//宽高比例为2：1
        initView();


//        engine = new PutObjectSamples();
        Intent intent = getIntent();
        mLocalCompose = (LocalCompose) intent.getSerializableExtra("LocalCompose");
        videoWidth = intent.getIntExtra("videoWidth", 3);
        videoHeight = intent.getIntExtra("videoHeight", 4);
//        if (mLocalCompose != null) {
//            //判断是否已经上传：（通过数据库）
//            dbLocalCompose = mComposeManager.query(mLocalCompose.getCreateDate());
//            try {
//                if (dbLocalCompose != null && !dbLocalCompose.getCompose_id().equals("")) {
//                    mLocalCompose = dbLocalCompose;
//                }
//                songname = mLocalCompose.getCompose_name();
//                imageurl = mLocalCompose.getCompose_image();
//                if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("0")) {
//                    mediatype = "audio";
//                } else {
//                    mediatype = "video";
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        initnewView();
    }

    private void initData() {
        //注意歌名有两个地方需要赋值，song_name  name_content
        //修改默认数据


    }

    /**
     * 开始上传：
     */
//    public void startUpload() {
//        makeDB();
//        Intent intent = new Intent(this, UploadWorkService.class);
//        UploadWork mUploadWork = new UploadWork(mLocalCompose.getCompose_id(), mLocalCompose, mUploadZP, 0, 0);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("UploadWork", mUploadWork);
//        intent.putExtras(bundle);
//        intent.setAction("UPLOAD_START");
//        startService(intent);
//        Intent intent1 = new Intent(this, LocalMusicIndexActivity.class);
//        startActivity(intent1);
//        finishActivity();//跳转到本地列表
//    }


//    public void initAliYun() {
//        if (mUploadZP != null) {
//            accessKeyId = mUploadZP.getStsAccessKeyId();
//            accessKeySecret = mUploadZP.getStsAccessKeySecret();
//            securityToken = mUploadZP.getStsSecurityToken();
//            expirationstr = mUploadZP.getStsExpiration();
//            uploadObject = mUploadZP.getReserveUrl();
//            uploadFilePath = mLocalCompose.getCompose_file();//合成的上传文件
//            LogUtils.sysout("accessKeyId=" + accessKeyId + "   accessKeySecret= " + accessKeySecret);
//        } else {
//            return;
//        }
//        if (accessKeyId.equals("") && accessKeySecret.equals("")) {
//            toast("获取不到权限");
//            return;
//        }
//
//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken);
//
//
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        OSSLog.enableLog();
//        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                asyncPutObjectFromLocalFile();
//            }
//        }).start();
//
//    }
    private void initnewView() {
        if (imageurl != null && !imageurl.equals("")) {
            if (RegValUtil.containsString(imageurl, mLocalCompose.getCompose_id())) {
                ImageLoader.displayFromSDCard(imageurl, song_image);
            } else {
                ImageLoader.getImageViewLoad(song_image, imageurl, R.mipmap.sdcard_video_image);
            }
        } else {
            song_image.setImageResource(R.mipmap.sdcard_video_image);
        }
        if (songname != null && !songname.equals("")) {

            if (mLocalCompose != null && mLocalCompose.getCompose_remark() != null && !mLocalCompose.getCompose_remark().equals("")) {
                //判断songname的位置和内容是否相同
                if (songname.equals(checkSongName())) {
                    content.setText(mLocalCompose.getCompose_remark());
//                    content.setHint("友友们，快来听我新唱的《" + songname + "》吧！");
                    content.setHint(mLocalCompose.getCompose_remark());
                } else {
                    content.setText(mLocalCompose.getCompose_remark());
                    content.setHint(getString(R.string.commit_content_come2) + songname + getString(R.string.commit_content_come3));
                }
            } else {
                if (Integer.parseInt(mLocalCompose.getCompose_type()) >= 5) {//清唱
                    content.setHint(getString(R.string.commit_content));
                } else {
                    content.setText(getString(R.string.commit_content_come2) + songname + getString(R.string.commit_content_come3));
                    content.setHint(getString(R.string.commit_content_come2) + songname + getString(R.string.commit_content_come3));
                }
            }
        } else {
            if (mLocalCompose != null && mLocalCompose.getCompose_remark() != null && !mLocalCompose.getCompose_remark().equals("")) {
                content.setText(mLocalCompose.getCompose_remark());
                content.setHint(getString(R.string.commit_content_come));
            } else {
                content.setText(getString(R.string.commit_content_come));
                content.setHint(getString(R.string.commit_content_come));
            }
        }
    }
    //判断songname的位置和内容是否相同

    public String checkSongName() {
        String oldname = songname;
        try {
            int remarksize = mLocalCompose.getCompose_remark().length();
            oldname = mLocalCompose.getCompose_remark().substring(12, remarksize - 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldname;
    }

    private void initView() {

        bt_back = (ImageView) findViewById(R.id.back_last);
        top_text = (TextView) findViewById(R.id.center_text);
        right_text = (TextView) findViewById(R.id.back_next_left);
        bt_right = (ImageView) findViewById(R.id.back_next);


        song_image = (ImageView) findViewById(R.id.hend_song_image); // bt_chang_name
        content = (CustomEditText) findViewById(R.id.edit_centent); // edit_centent
        show_count = (TextView) findViewById(R.id.tv_show_count);// 数字统计 tv_show_count
        progressLl = (LinearLayout) findViewById(R.id.progress_ll);
        roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar2);// 数字统计 tv_show_count
        progressHint = (TextView) findViewById(R.id.progress_hint);


        updata_work = (Button) findViewById(R.id.bt_updata_work); // bt_save
        name_content = (CustomEditText) findViewById(R.id.name_edit); // name_edit


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(rowWidth - DensityUtil.dip2px(this, 40), (rowWidth - DensityUtil.dip2px(this, 40)) * 9 / 16);
        song_image.setLayoutParams(params);
        updata_work.setOnClickListener(this); // bt_save


        bt_back.setOnClickListener(this);
        right_text.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        song_image.setOnClickListener(this);

        top_text.setText("上传到我的主页");
        right_text.setVisibility(View.GONE);
        right_text.setText("上传");

        bt_back = (ImageView) findViewById(R.id.back_last);
        top_text = (TextView) findViewById(R.id.center_text);
        right_text = (TextView) findViewById(R.id.back_next_left);
        bt_right = (ImageView) findViewById(R.id.back_next);
        //应该将两个标题保存到

        content.addTextChangedListener(new TextWatcher() {
            @Override
            //s:变化后的所有字符
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int leng = s.length();
                show_count.setText(leng + "/200");
            }
        });

        name_content.addTextChangedListener(new TextWatcher() {
            @Override
            //s:变化后的所有字符
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int leng = s.length();
                if (leng > 0) {

                    isname = true;
                } else {
                    isname = false;
                }
                if (isname && isimage) {//TODO 修改上传按钮图标，可点击上传按钮：
                    updata_work.setBackgroundResource(R.drawable.shape_both_red_red);
                } else {
                    updata_work.setBackgroundResource(R.drawable.shape_both_red_red_light);
                }
            }
        });
    }

    //设置控件是否可点击
    public void setViewEnable(boolean can) {
        if (can) {
            content.setEnabled(true);
        } else {
            content.setEnabled(false);
        }

    }

    public void showAlertDialog() {
        new AlertDialog(this).builder()
                .setTitle("温馨提示")
                .setMsg("您的作品正在上传，是否放弃上传")
                .setPositiveButton("放弃", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishActivity();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.back_last://
                if (isuploading) {//正在上传中
                    showAlertDialog();//您的作品正在上传，是否放弃上传
                } else {
                    stopUpload();//停止上传
                    finishActivity();
                }
                break;
            case R.id.back_next://搜索按钮
                toast("搜索按钮");
                break;
            case R.id.bt_updata_work://上传
                if (isname && isimage) {//TODO 修改上传按钮图标，可点击上传按钮：

                } else {
                    toast(getString(R.string.name_must_need));
                    return;
                }
                songname = name_content.getText().toString().trim();
                try {
                    mLocalCompose.setCompose_name(songname);
                    mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "compose_name", songname);//修改描述
                } catch (Exception e) {
                    e.printStackTrace();
                }
                User user = BaseApplication.getUser();
                if (user == null) {
//            toast( "未登录。" );
                    LoginedDialog.loginedcheck(this);
                    return;
                }
                if (songname == null || songname.equals("")) {
                    toast(getString(R.string.name_must_need));
                    return;
                }
                try {
                    remark = content.getText().toString().trim();
                    mLocalCompose.setCompose_remark(remark);
                    LogUtils.sysout("1111111111remark=" + remark);
                    mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "compose_remark", remark);//修改描述
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isuploading) {//中断上传
                    right_text.setText("上传");
                    isuploading = false;
                    setViewEnable(true);
                    stopUpload();//停止上传
                    //TODO
                } else {//开始上传
                    right_text.setText("停止");
                    setViewEnable(false);
                    isuploading = true;
                    remark = content.getText().toString().trim();
//                    updataimage();//和后台交互：
                }

                break;

            case R.id.hend_song_image://修改封面
//                toast( "修改封面" );
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, mPermissionList, 1234);
                        //权限还没有授予，需要在这里写申请权限的代码
                    } else {
                        //权限已经被授予，在这里直接写要执行的相应方法即可
                        changImage();
                    }

                } else {
                    changImage();
                }

                break;
        }

    }

    private void changImage() {//更改头像
        new ActionSheetDialog(SDCommitActivity.this).builder().setCancelable(false).setCanceledOnTouchOutside(false)
                .addSheetItem(getString(R.string.mobile_album), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Crop.pickImage(SDCommitActivity.this);
                    }
                }).addSheetItem(getString(R.string.take_photo), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /***
                 /***
                 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
                 * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
                 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
                 */
                ContentValues values = new ContentValues();
                photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, USE_CAREMA);
            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Bitmap bitmap;
//
//        switch (resultCode) {
//            case Activity.RESULT_OK:
//                switch (requestCode) {
//                    case USE_CAREMA:
//                        beginCrop(photoUri);
//                        break;
//                    case Crop.REQUEST_PICK:
//                        Uri uri = data.getData();
//                        if (uri == null || uri.equals("")) {
//                            return;
//                        }
//                        photoUri = uri;
//                        beginCrop(photoUri);
//                        break;
//                    case Crop.REQUEST_CROP:
//                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                123);
//                        handleCrop(resultCode, data);
//                        break;
//                    case USE_CROP:
//                        Bundle bundle = data.getExtras();
//                        bitmap = (Bitmap) bundle.get("data");
//                        song_image.setImageBitmap(bitmap);
//                        break;
//                }
//        }
    }

//    onRequestPermissionsResult


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtils.sysout("-----------权限回调成功----------");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123://分享的
//                goToShare();
                break;
            case 1234://更换图片

                changImage();
                break;
        }

    }

    private void beginCrop(Uri source) {
        if (source == null || source.equals("")) {
            return;
        }
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
        new Crop(source).output(outputUri).outputSize(500).asSquare(16, 9).start(this);
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
//            Uri uri = photoUri;
            if (uri == null || uri.equals("")) {
                return;
            }
            String path = AppUtil.getImagePath(SDCommitActivity.this, uri);
//            Bitmap tagimage = ImageUtil.getBitmapfromResource(getResources(), R.mipmap.sdcard_video);
//            bit = mergeBitmap(BitmapFactory.decodeFile(path), tagimage);
            bit = BitmapFactory.decodeFile(path);
            if (bit == null) {
                isimage = false;
                return;
            }
            isimage = true;
            if (isname && isimage) {//TODO 修改上传按钮图标，可点击上传按钮：
                updata_work.setBackgroundResource(R.drawable.shape_both_red_red);
            } else {
                updata_work.setBackgroundResource(R.drawable.shape_both_red_red_light);
            }
//            bit =(bit, tagimage);
            song_image.setImageBitmap(bit);
            new Thread() {
                public void run() {
                    try {
                        //TODO: 需要合成两张图片

                        saveIcon(bit);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 保存用户头像到本地
     */
    public void saveIcon(Bitmap bitMap) throws FileNotFoundException {
//        FileOutputStream fos = new FileOutputStream( getIconName() );
        FileOutputStream fos = null;
        try {
            FileOutputStream fout = new FileOutputStream(getIconName());
            bitMap.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    mLocalCompose.setCompose_image(getIconName());
                    mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "compose_image", getIconName());//标志数据库为已经上传
//                    handler.sendEmptyMessageDelayed( 333, 100 );//保存成功之后，去上传。
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //TODO
    }


    //    获取阿里云权限：
//    private void updataimage() {
//        File file = null;
//        // 判断头像 是否更改了
//        try {
////            songname = mLocalCompose.getCompose_name();
//            imageurl = mLocalCompose.getCompose_image();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        User user = BaseApplication.getUser();
//        if (user == null) {
////            toast( "未登录。" );
//            LoginedDialog.loginedcheck(this);
//            return;
//        }
//        showProgressDialog("初始化数据", true);
//        String uid = user.getId();
//        String url = NetInterface.SERVER_URL + "vip/vipPhoto.do";
////   params={"name":"唱歌","remark":"说点什么","vipId":" 402883ec57bced620157bcf15d270000","type":"audio","songId":"36"}
////        private String songname = "";
////        private String imageurl = "";
//        LocalMusicNet.api_UploadParams(this, songname, uid + "", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                closeProgressDialog();
//                LogUtils.sysout("联网登录出现网络异常错误！");
//                responsemsg = getString(R.string.get_no_data);
//                handler.sendEmptyMessageDelayed(-1, 100);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                closeProgressDialog();
//                String data = response.body().string();
//                LogUtils.sysout("返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//
//                if (code == 0) {
//                    mUploadZP = UploadZP.objectFromData(data, "data");
//                    //user = LoginJsonParser.parser_Login(data);
//                    if (mUploadZP != null && !mUploadZP.equals("")) {
//                        handler.sendEmptyMessageDelayed(0, 1000);//获取数据成功，开始上传云端
//                    }
//                } else {
//                    responsemsg = JsonParserFirst.getRetMsg(data);
//                    handler.sendEmptyMessageDelayed(1, 100);//获取不到权限
//
//                }
//            }
//        });
//
//    }

    //    上传成功之后保存到服务器：
//    private void updataSucceed() {
//
//        String imagename = "";
//        File file = null;
//        String uid = null, reserveUrl = null;
////        showProgressDialog( "上传完成", true );
//        User user = BaseApplication.getUser();
//        if (user == null) {
////            toast( "未登录。" );
//
//            LoginedDialog.loginedcheck(this);
//            return;
//        }
//        uid = user.getId();
//        try {
//            reserveUrl = mUploadZP.getReserveUrl();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (reserveUrl == null) {
//            toast("获取标志位出错");
//            return;
//        }
//        try {
//
//            if (RegValUtil.containsString(imageurl, mLocalCompose.getCompose_id())) {
////                file = getFileStreamPath( mLocalCompose.getCompose_image() );
//                file = new File(mLocalCompose.getCompose_image());
//                imagename = "iamge.png";
//            } else {
//
//            }
//            LogUtils.sysout("step" + file.getAbsolutePath());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (file == null) {
//            LogUtils.sysout("**************图片***********:" + file);
////            return;
//        }
//
//
//        String mediatype = "video";//合成后的；类型：
//        // composeType？ 0://MP3和MP3合成：1://MP4和MP4合成 2://录制MP4和下载MP3合成3://录制MP3和下载MP4合成
//        String songType = "audio";//合成前的；类型：（音乐源的类型）
//        String kgb = "3*2";
//        String mvType = "1";//mvType：mv标签类型    1 KTV  2  MV  3  DIY MV   4 mp3
//        String imageurl = mLocalCompose.getCompose_image();
//        if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("0")) {
//            songType = "audio";
//            mediatype = "audio";
//            kgb = "3*2";
//            mvType = "4";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("3")) {
//            songType = "audio";
//            mediatype = "video";
//            kgb = "3*2";
//            mvType = "1";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("4")) {
//            songType = "audio";
//            mediatype = "video";
//            kgb = "3*2";
//            mvType = "3";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("1")) {
//            songType = "video";
//            mediatype = "video";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "2";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("2")) {
//            songType = "audio";
//            mediatype = "video";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "2";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("5")) {
//            songType = "audio";
//            mediatype = "audio";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "4";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("6")) {
//            songType = "audio";
//            mediatype = "video";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "2";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("7")) {
//            songType = "audio";
//            mediatype = "video";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "2";
//        } else if (mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("8")) {
//            songType = "audio";
//            mediatype = "video";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "5";
//        } else {
//            songType = "audio";
//            mediatype = "video";
////            kgb = "25*32";
//            kgb = "3*4";
//            mvType = "2";
//        }
//
//        double filesize = FileUtil.getFileOrFilesSize(mLocalCompose.getCompose_file(), 3);
//        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
//        String size = df.format(filesize);
//        String remark = getString(R.string.commit_content_come);
//        if (mLocalCompose.getCompose_remark() != null && !mLocalCompose.getCompose_remark().equals("")) {
//            remark = mLocalCompose.getCompose_remark();
//        }
//
//
////(Context ct, String vipId, String name, String remark,String type,String songId,String reserveUrl, Callback callback)
////        LocalMusicNet.api_UploadSucceed( this, uid, songname,remark,"audio",mLocalCompose.getSongId(),reserveUrl, new Callback() {
//        LocalMusicNet.api_UploadSucceed(this, uid, songname, remark, mediatype, mLocalCompose.getSongId(), songType, kgb, reserveUrl,
//                imagename, file, mLocalCompose.getCompose_begin(), mvType, size, new Callback() {
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        closeProgressDialog();
//                        LogUtils.sysout("联网出现网络异常错误！");
//                        handler.sendEmptyMessageDelayed(-1, 100);
//                        handler.sendEmptyMessageDelayed(3000, 100);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        closeProgressDialog();
//                        String data = response.body().string();
//                        LogUtils.sysout("返回结果:" + data);
//                        int code = JsonParserFirst.getRetCode(data);
//
//                        if (code == 0) {//ycVipId
//                            try {
//                                JSONObject obj = new JSONObject(data);
//                                JSONObject status = new JSONObject(obj.getString("data"));
//                                ycVipId = status.getString("id");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } finally {
//
//                            }
//
//                            handler.sendEmptyMessageDelayed(2000, 100);//上传成功之后保存服务器成功
//                        } else {
//                            responsemsg = JsonParserFirst.getRetMsg(data);
//                            handler.sendEmptyMessageDelayed(3000, 100);
//
//                        }
//                    }
//                });
//
//    }

    long progress = 0;
//    OSSAsyncTask task = null;

    // 从本地文件上传，使用非阻塞的异步接口
//    public void asyncPutObjectFromLocalFile() {
//        if (uploadFilePath != null && !uploadFilePath.equals("")) {
//
//        } else {
//            LogUtils.sysout("上传文件不存在");
//            return;
//        }
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest(testBucket, uploadObject, uploadFilePath);
//
//        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
////                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//                progress = (currentSize / (totalSize / 100));
////                if(roundProgressBar!=null){
////                    roundProgressBar.setProgress(progress);
////
////                }
//                LogUtils.sysout("进度：PutObject: " + "currentSize: " + currentSize + " totalSize: " + totalSize);
////                engineHelper.sendEmpteyMsg(1000);
//            }
//        });
//
//        task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//
//                LogUtils.sysout("上传成功。");
//                handler.sendEmptyMessageDelayed(5000, 100);
//
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常
////                if(roundProgressBar!=null){//隐藏进度条
////                    roundProgressBar.setVisibility( View.GONE );
////                }
//
//                LogUtils.sysout("请求异常。");
//                handler.sendEmptyMessageDelayed(3000, 100);
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    LogUtils.sysout("本地异常如网络异常等");
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    LogUtils.sysout("服务异常");
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });
//    }


    /**
     * 两张图片合成
     * 需要考虑到底图的扩大和缩小和图标的合成
     *
     * @param firstBitmap
     * @param secondBitmap
     * @return
     */
    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        int video_width = this.getResources().getDisplayMetrics().widthPixels - DensityUtil.dip2px(this, 20);//显示区域宽度,手机屏幕宽度-两边空白：
        int image_width = firstBitmap.getWidth();//底图原始宽度
        double ratio = image_width / video_width;//缩放比例
//        LogUtils.sysout("111111111111111 video_width ="+video_width);
//        LogUtils.sysout("2222222222222 image_width ="+image_width);
//        LogUtils.sysout("2222222222222 ratio ="+ratio);
        int desX = secondBitmap.getWidth();
        int desY = secondBitmap.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
        Paint paint = new Paint();
        paint.setAlpha(20);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        Matrix matrix = new Matrix();
        matrix.postScale(0f, (float) DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 30), DensityUtil.dip2px(this, 80));
//        canvas.drawBitmap(secondBitmap, x, DensityUtil.dip2px(this, 10), null);
        Rect SrcRect = new Rect(0, 0, 0, DensityUtil.dip2px(this, 10));
        Rect DestRect = new Rect(0, DensityUtil.dip2px(this, 10) * image_width / video_width, (int) ((desX * image_width + DensityUtil.dip2px(this, 10)) / video_width), (int) (desY * image_width / video_width));
//        Rect DestRect = new Rect(0, 0, (int) desX, (int) desY);
        canvas.drawBitmap(secondBitmap, null, DestRect, null);
        return bitmap;
    }


    /**
     * 获取头像的地址
     */
    private String getIconName() {

        String imagepath = "image.png";

        try {
//                    (String key,String field,String content)
            if (mComposeManager != null) {
                imagepath = imagePath + mLocalCompose.getCompose_id() + ".png";//imagePath + datetime + ".png";
                mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "compose_image", imagepath);//标志数据库为已为修改了图片
            }
            if (mLocalCompose != null) {
                mLocalCompose.setCompose_image(imagepath);
            }
            LogUtils.sysout("修改数据库，为修改了图片。");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagepath;
        // ".png"
    }


    private void makeDB() {//新建类，并添加到数据库记录
        //获取当前时间，以便用做解码文件命名：
        mLocalCompose.setCompose_begin("0");
        mLocalCompose.setCompose_delete("0");

//        private int videoWidth = 0;
//        private int videoHeight = 0;
        mLocalCompose.setCompose_other(videoWidth + "*" + videoHeight);
        mComposeManager.insertCompose(mLocalCompose);//插入记录
        LogUtils.sysout("添加数据库记录");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isuploading) {//正在上传中
                showAlertDialog();//您的作品正在上传，是否放弃上传
            } else {
                finishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
