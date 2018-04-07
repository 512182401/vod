package com.changxiang.vod.module.ui.oratorio;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.utils.PreferUtil;
import com.changxiang.vod.common.utils.dialog.AlertDialog;
import com.changxiang.vod.common.view.SwitchView;
import com.changxiang.vod.common.view.ThreeButtonAlertDialog;
import com.changxiang.vod.module.db.ComposeManager;
import com.changxiang.vod.module.db.LocalCompose;
import com.changxiang.vod.module.entry.SongDetail;
import com.changxiang.vod.module.musicInfo.TimeUtil;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.localmusic.LocalMusicIndexActivity;
import com.changxiang.vod.module.ui.recordmusic.BeautyFilterView;
import com.changxiang.vod.module.ui.recordmusic.recordutils.CameraManager;
import com.changxiang.vod.module.ui.recordmusic.recordutils.ExtAudioRecorder;
import com.changxiang.vod.module.ui.recordmusic.recordutils.RecorderManager;
import com.changxiang.vod.module.ui.saveutils.SavePracticeActivity;
import com.seu.magicfilter.MagicEngine;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.widget.MagicCameraView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class CameraOratorioActivity extends BaseActivity implements View.OnClickListener, SoundEffecView.MyListener, Thread.UncaughtExceptionHandler, MagicCameraView.MagicViewListener {

    private static final String TAG = "CameraOratorioActivity";
    private final int TIMEPIECE = 0;
    private final int TIMEDOWN = 1;
    private final int ALPHA = 2;
    private MagicCameraView videoSurface;
    private CameraManager cameraManager;
    private RecorderManager recorderManager;
    private SurfaceHolder mMSurfaceHolder;
    private MediaRecorder mRecorder;
    private ImageView mRecordMusic;

    private boolean isMusicRlVisiable = true;
    private ImageView ivStart;
    private SoundEffecView musicRl;
    private int startState = 0;//0 : 开始录歌  1:继续录歌  2:暂停录歌
    private ImageView ivClose;
    private ImageView ivEnd;
    private boolean is_recording;
    private String recordAudioPath;
    private Camera.AutoFocusCallback autoFocusCallback;
    private SurfaceHolder mHolder;
    private MediaRecorder mMediaRecorder;
    private Camera.Parameters mParameters;
    private BeautyFilterView beautyFilter;
    private AnimationDrawable drawable;
    private ImageView countDownIv;
    private LinearLayout showTimeLl;
    private ImageView ivTwinkle;
    private ObjectAnimator alphaAnimation;
    private MyAnimatorUpdateListener alphaUpdateListener;
    private int limitTime = 600;
    private int time = 0;
    private TextView tvTimepiece;
    private CheckBox ivCamera;
    private Intent intent;
    private TextView tvRecordName;
    private int mAddtype;
    private int mDuration;
    private int TotalTime;
    private String recordAudio;
    private LinearLayout llBottom;
    private Button btnStart;
    private LinearLayout llTiempiece;
    private ImageView countCameraDownIv;
    private TextView tvTiempiece;
    private AnimationDrawable drawable2;
    private TextView tvCameraTiempieceText;
    private ImageView timepiecePoint;
    private ProgressBar progressBar;
    private TextView progressNow;
    private TextView progressTotle;
    private int progress = 0;//进度
    private boolean is_repeat = false;
    private int mDurationTemp;

    private LocalCompose diyLocalCompose = new LocalCompose();//DIY作品视频对象
    private String datetime = "888888888";
    private String createDate = "";

    private int composeType = -1;// composeType？ 0://MP3和MP3合成：1://MP4和MP4合成 2://录制MP4和下载MP3合成3://录制MP3和下载MP4合成,4://录制mp3和DIY
    private ComposeManager mComposeManager;//数据库：
    private String composeDir = MyFileUtil.DIR_COMPOSE.toString() + File.separator;//路径(cache)
    private String composeFile = "";//保存到数据库的合成之后的文件
    private String songName = "清唱";//音乐源名字
    private int Compose_finish = 0;//结束录制进度
    private String strMill;
    private String strSecond;
    private CountDownTimer timer;
    private boolean isRun;
    private int value = 255;
    private int timeValue;
    private SwitchView switchBtn;
    private ImageView oratorioListen;
    private View contentView;

    private final MagicFilterType[] types = new MagicFilterType[]{
            MagicFilterType.NONE,//0
            MagicFilterType.LOMO,
            MagicFilterType.FAIRYTALE,
            MagicFilterType.SUNRISE,
            MagicFilterType.SUNSET,
            MagicFilterType.WHITECAT,
            MagicFilterType.BLACKCAT,
            MagicFilterType.SKINWHITEN,
            MagicFilterType.HEALTHY,//8
            MagicFilterType.SWEETS,
            MagicFilterType.ROMANCE,
            MagicFilterType.SAKURA,
            MagicFilterType.WARM,
            MagicFilterType.ANTIQUE,
            MagicFilterType.NOSTALGIA,
            MagicFilterType.CALM,
            MagicFilterType.LATTE,
            MagicFilterType.TENDER,//17
            MagicFilterType.COOL,//18
            MagicFilterType.EMERALD,
            MagicFilterType.EVERGREEN,
            MagicFilterType.CRAYON,
            MagicFilterType.SKETCH,
            MagicFilterType.AMARO,
            MagicFilterType.BRANNAN,
            MagicFilterType.BROOKLYN,
            MagicFilterType.EARLYBIRD,
            MagicFilterType.FREUD,
            MagicFilterType.HEFE,
            MagicFilterType.HUDSON,
            MagicFilterType.INKWELL,//30
            MagicFilterType.KEVIN,
            MagicFilterType.N1977,
            MagicFilterType.NASHVILLE,
            MagicFilterType.PIXAR,
            MagicFilterType.RISE,
            MagicFilterType.SIERRA,
            MagicFilterType.SUTRO,
            MagicFilterType.TOASTER2,
            MagicFilterType.VALENCIA,
            MagicFilterType.WALDEN,
            MagicFilterType.XPROII
    };
    private MagicEngine magicEngine;
    private PopupWindow mPopWnd;

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case TIMEPIECE:
                //计时器
                runTimepiece();
                break;
            case TIMEDOWN:
                //倒计时
                revertTimepiece();
                break;
            case 111:
                if (drawable != null) {
                    drawable.stop();
                }
                countDownIv.setVisibility(View.GONE);
                break;
            case 222:
                if (drawable != null) {
                    drawable.stop();
                }
                countCameraDownIv.setVisibility(View.GONE);
                break;
            case 101://延时跳转到本地歌曲界面
                //跳转到本地歌曲界面，并关闭当前界面,
                Intent intent1 = new Intent(this, LocalMusicIndexActivity.class);
                startActivity(intent1);
                finishActivity();//跳转到本地列表
                break;
            case ALPHA:
                timepiecePoint.setAlpha(1.0f);
                float alpha1 = timepiecePoint.getAlpha();
                LogUtils.w("alpha", "alpha1------------------" + alpha1);
                timepiecePoint.setBackgroundResource(R.drawable.timepiece_red_point_shape);
                break;
            case 110:
                if (isRun) {
                    value++;
                    timeValue++;
                    LogUtils.w("value", "alpha1------------------" + value);
//                    timepiecePoint.setAlpha(value % 255);
                    int temp = value % 255;
                    if (timeValue % 10 > 5) {
                        LogUtils.w("value", "alpha1------------------" + timeValue);
                        timepiecePoint.getBackground().setAlpha(255);
                    } else {
                        timepiecePoint.getBackground().setAlpha(0);
                    }
//                    handler.sendEmptyMessage(110);
                    handler.sendEmptyMessageDelayed(110, 100);
                } else {
                    timepiecePoint.getBackground().setAlpha(255);
                    timeValue = 0;
                }
                break;
        }
    }

    /**
     * 倒计时
     */
    private void revertTimepiece() {
        LogUtils.w("bug", "mDuration========" + mDuration);
        if (mDuration == 0) {
            LogUtils.w("bug", "111111111111");
            handler.removeMessages(110);
            handler.removeMessages(TIMEDOWN);
            timepiecePoint.getBackground().setAlpha(255);
            timeValue = 0;
            LogUtils.w("bug", "222222222222222");
            //停止录制
            magicEngine.stopRecord();
            LogUtils.w("bug", "333333333333333");
            stopAudioRecorder();
            LogUtils.w("bug", "666666666666");
            //跳转
            if (mAddtype == 1) {//录音保存跳过来
                start2CameraServer2();
                LogUtils.w("bug", "55555555555555");
            } else if (mAddtype == 2) {//本地跳过来
                makeMV2Server();
            }
            return;
        }
        if (mDuration > 0) {
            mDuration--;
            progress++;
            LogUtils.w("bug", "444444444444");
            String result = int2String(mDuration);
            String progressResult = int2String(progress);
            tvCameraTiempieceText.setText("录制剩余" + result.substring(0, 2) + "分" + result.substring(3) + "秒");
            progressNow.setText(progressResult);
            progressBar.setProgress(progress);
            handler.sendEmptyMessageDelayed(TIMEDOWN, 1000);
        }
    }

    /**
     * 将一个int数据装换成一个00:00格式的字符串
     */
    public String int2String(int num) {
        String result = "";
        int seconde1 = num / 60;//100
        int seconde2 = 0;
        if (seconde1 == 10) {
            seconde1 = 0;
            seconde2 = 1;
        }
        int m1 = num % 60;
        String m = m1 + "";
        if (m1 <= 9) {
            m = 0 + m;
        }
        result = seconde2 + "" + seconde1 + ":" + m;
        return result;
    }

    /**
     * 计时器
     */
    private void runTimepiece() {
        if (time <= limitTime) {
            time++;
        }
        if (time > limitTime) {
            //停止录制,跳转到保存界面
//            recorderManager.stopRecord();
//            showThreeDialog();
            //停止录像
            if (magicEngine != null) {
                magicEngine.stopRecord();
                stopAudioRecorder();
            }
            start2CameraSaveActivity();
            return;
        }
        //将time转换成String
        int seconde1 = time / 60;
        int seconde2 = 0;
        if (seconde1 == 10) {
            seconde1 = 0;
            seconde2 = 1;
        }
        int m1 = time % 60;
        String m = m1 + "";
        if (m1 <= 9) {
            m = 0 + m;
        }
        String result;
        if (seconde2 == 0) {
            result = "" + seconde1 + ":" + m;
        } else {
            result = seconde2 + "" + seconde1 + ":" + m;
        }
        tvTimepiece.setText(result);
        handler.sendEmptyMessageDelayed(TIMEPIECE, 1000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设定为竖屏
        setContentView(R.layout.activity_camera_oratorio);

        Intent intent = getIntent();
        getIntentDdata(intent);

//        //录制时长100ms
//        mDurationTemp = mDuration = intent.getIntExtra("duration", -1) / 1000;//注意单位统一
//        mLocalCompose = (LocalCompose) intent.getSerializableExtra("LocalCompose");
//        mComposeManager = ComposeManager.getComposeManager(this);
//        String canpic = intent.getStringExtra("canpic");
        //添加跳转方式：1：表示跳转到保存界面合成，2：表示跳转到后台合成，显示本地作品界面。
        initView();
        initDate();
        initEvent();
//        initCameraAndSurfaceViewHolder();

        registerReceive();
        //在此调用下面方法，才能捕获到线程中的异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            "android.permission.READ_EXTERNAL_STORAGE",
//            "android.permission.WRITE_EXTERNAL_STORAGE" };
//
//
//    public static void verifyStoragePermissions(Activity activity) {
//
//        try {
//            //检测是否有写的权限
//            int permission = ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.WRITE_EXTERNAL_STORAGE");
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                // 没有写的权限，去申请写的权限，会弹出对话框
//                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getIntentDdata(intent);
        LogUtils.sysout("**************执行 onNewIntent");
    }

    private void getIntentDdata(Intent intent) {
        //录制时长100ms
        mDurationTemp = mDuration = intent.getIntExtra("duration", -1) / 1000;//注意单位统一
        TotalTime = intent.getIntExtra("duration", -1);//注意单位统一
        recordAudio = intent.getStringExtra("recordAudio");
        //获取合成的相关实体类LocalCompose（合成的时候需要确保已经解码成功）
//        oldsongDetail = (SongDetail) intent.getSerializableExtra("songDetail");
        mLocalCompose = (LocalCompose) intent.getSerializableExtra("LocalCompose");
        mComposeManager = ComposeManager.getComposeManager(this);
        mAddtype = intent.getIntExtra("addtype", -1);
        LogUtils.w("mAddtype", "mDuration==" + mDuration);
        LogUtils.w("mAddtype", "mAddtype==" + mAddtype);
        LogUtils.sysout("9999999999999999999999" + mAddtype);
    }

//    private void initCameraAndSurfaceViewHolder() {
//        mHolder = videoSurface.getHolder();
////        videoSurface.measure(0, 0);
////        videoHeight = videoSurface.getMeasuredHeight(); // 获取高度
//        //顺序不能变
//        if (cameraManager == null) {
//            cameraManager = getCameraManager();
//        }
//        cameraManager.startCamera(videoSurface);
//        mCamera = cameraManager.getCamera();
//        LogUtils.w("mCamera", "mCamera===" + mCamera);
//        if (recorderManager == null) {
//            recorderManager = new RecorderManager(getCameraManager(), videoSurface, this);
//        }
//        mHolder.addCallback(this);
//    }

    private void initView() {
        videoSurface = (MagicCameraView) findViewById(R.id.open_camera_sv);
        mRecordMusic = (ImageView) findViewById(R.id.record_music);
        ivStart = (ImageView) findViewById(R.id.record_start_iv);
        ivClose = (ImageView) findViewById(R.id.record_iv_close);
        ivEnd = (ImageView) findViewById(R.id.record_end_iv);
        musicRl = (SoundEffecView) findViewById(R.id.camera_sound_effect_rl);//音效布局
        beautyFilter = (BeautyFilterView) findViewById(R.id.beauty_filter);//滤镜布局
        countDownIv = (ImageView) findViewById(R.id.record_count_down_tv);
//        countCameraDownIv = (ImageView) findViewById(R.id.camera_count_down_tv);
        showTimeLl = (LinearLayout) findViewById(R.id.record_camera_ll);
        ivTwinkle = (ImageView) findViewById(R.id.oratorio_red_twinkle);
        timepiecePoint = (ImageView) findViewById(R.id.camare_timepiece_point);
        tvTimepiece = (TextView) findViewById(R.id.oratorio_timepiece);
        ivCamera = (CheckBox) findViewById(R.id.open_camera_camera);//相机
        tvRecordName = (TextView) findViewById(R.id.record_song_name_tv);
        llBottom = (LinearLayout) findViewById(R.id.practice_sing_llBotton);
        btnStart = (Button) findViewById(R.id.camare_btn_start);
        llTiempiece = (LinearLayout) findViewById(R.id.camare_timepiece);
        tvCameraTiempieceText = (TextView) findViewById(R.id.camare_timepiece_text);

        //进度条
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressNow = (TextView) findViewById(R.id.progress_now);
        progressTotle = (TextView) findViewById(R.id.progress_totle);
        oratorioListen = (ImageView) findViewById(R.id.camera_ear_back);//耳返

        MagicEngine.Builder builder = new MagicEngine.Builder();
//        builder.setVideoPath(MyFileUtil.DIR_RECORDER.toString());//Environment.getExternalStorageDirectory().getPath() + File.separator
        builder.setVideoPath(Environment.getExternalStorageDirectory().getPath());//Environment.getExternalStorageDirectory().getPath() + File.separator
        builder.setVideoName("MagicCamera_test.mp4");
        magicEngine = builder
                .build((MagicCameraView) findViewById(R.id.open_camera_sv));
        beautyFilter.setFilters(types);
        beautyFilter.setOnClickListener(this);
        beautyFilter.setOnFilterChangeListener(new BeautyFilterView.onFilterChangeListener() {
            @Override
            public void onFilterChanged(MagicFilterType filterType) {
                magicEngine.setFilter(filterType);
//                Toast.makeText(CameraOratorioActivity.this, "" + filterType, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initDate() {
        recordAudioPath = MyFileUtil.DIR_RECORDER.toString() + File.separator + "source.wav";
        if (mAddtype == 1 || mAddtype == 2) {
            endRecordTime = mDuration;
            int mill = mDuration / 60;
            int second = mDuration % 60;
            strMill = "";
            strSecond = "";
            if (mill <= 9) {
                strMill = 0 + "" + mill;
            } else {
                strMill = "" + mill;
            }
            if (second <= 9) {
                strSecond = 0 + "" + second;
            } else {
                strSecond = "" + second;
            }
            tvRecordName.setText("录制视屏");
            tvCameraTiempieceText.setText("录制剩余" + strMill + "分" + strSecond + "秒");
            LogUtils.w("mAddtype", "mAddtype==" + mAddtype);
            musicRl.setVisibility(View.INVISIBLE);
            llBottom.setVisibility(View.INVISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            llTiempiece.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            progressNow.setVisibility(View.VISIBLE);
            progressTotle.setVisibility(View.VISIBLE);
            //进度条
            progressBar.setMax(mDuration);
            progressBar.setProgress(0);
            progressTotle.setText(strMill + ":" + strSecond);
        }
    }

    private void initEvent() {
        mRecordMusic.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        ivEnd.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        musicRl.setOnListener(this);
        btnStart.setOnClickListener(this);
        oratorioListen.setOnClickListener(this);
        videoSurface.setMagicViewListener(this);
    }

    //耳返开关
    private void earListenBack() {
        contentView = LayoutInflater.from(this).inflate(R.layout.pop_listen, null, false);
        contentView.setClickable(true);
        switchBtn = (SwitchView) contentView.findViewById(R.id.pop_listen_switch);
        switchBtn.setOpened(PreferUtil.getInstance().getBoolean("earback", false));
        if (extAudioRecorder != null) {
            extAudioRecorder.setHasHeadSet(PreferUtil.getInstance().getBoolean("earback", false));
        }
        mPopWnd = new PopupWindow(this);
        mPopWnd.setContentView(contentView);
        mPopWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //是否点击外面消失
        mPopWnd.setBackgroundDrawable(new BitmapDrawable());
        mPopWnd.setOutsideTouchable(true);
        //可点击
        mPopWnd.setFocusable(true);
        mPopWnd.setTouchable(true);
        switchBtn.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
//                Toast.makeText(OratorioActivity.this, "111111", Toast.LENGTH_SHORT).show();
                switchBtn.setOpened(true);
                hasHeadset = true;
                if (extAudioRecorder != null) {
                    extAudioRecorder.setHasHeadSet(true);
                }
                //sp保存状态
                PreferUtil.getInstance().putBoolean("earback", true);
            }

            @Override
            public void toggleToOff(View view) {
//                Toast.makeText(OratorioActivity.this, "222222222", Toast.LENGTH_SHORT).show();
                switchBtn.setOpened(false);
                hasHeadset = false;
                if (extAudioRecorder != null) {
                    extAudioRecorder.setHasHeadSet(false);
                }
                PreferUtil.getInstance().putBoolean("earback", false);
            }
        });
        //将PopupWindow显示在oratorioListen的下方
        contentView.measure(0, 0);
        int measuredWidth = contentView.getMeasuredWidth();
        mPopWnd.showAsDropDown(oratorioListen, -measuredWidth + 60, 40);
    }
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        Log.d("surface", "surfaceCreated");
////        mParameters = mCamera.getParameters();
////        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
////        mCamera.setParameters(mParameters);
//        videoHeight = videoSurface.getHeight();
//        mCamera.setDisplayOrientation(90);
//        try {
//            mCamera.setPreviewDisplay(holder);
//            mCamera.startPreview();
//            //下面这个方法能帮我们获取到相机预览帧，我们可以在这里实时地处理每一帧
//            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
//                @Override
//                public void onPreviewFrame(byte[] data, Camera camera) {
//                    Log.i(TAG, "获取预览帧...");
//                    new ProcessFrameAsyncTask().execute(data);
//                    Log.d(TAG, "预览帧大小：" + String.valueOf(data.length));
//                }
//            });
//        } catch (Exception e) {
//            Log.d(TAG, "设置相机预览失败", e);
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        Log.d("surface", "surfaceChanged");
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.d("surface", "surfaceDestroyed");
//    }

    private boolean mStartedFlg = false;
    private Camera mCamera;
    private String path = "";
    private TimerTask recordTask;
    private int recordTime = 0;

    private void registerReceive() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, intentFilter);
    }

    private void unRegister() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        if (extAudioRecorder != null) {
                            hasHeadset = false;
                            extAudioRecorder.setHasHeadSet(false);
                            PreferUtil.getInstance().putBoolean("earback", false);
//                            Toast.makeText(CameraOratorioActivity.this, "没耳机", Toast.LENGTH_SHORT).show();
                        }
                        oratorioListen.setVisibility(View.INVISIBLE);
                        if (mPopWnd != null && mPopWnd.isShowing()) {
                            mPopWnd.dismiss();
                        }

                    } else if (intent.getIntExtra("state", 0) == 1) {
                        if (extAudioRecorder != null) {
                            hasHeadset = true;
                            extAudioRecorder.setHasHeadSet(false);
                            PreferUtil.getInstance().putBoolean("earback", false);
//                            Toast.makeText(CameraOratorioActivity.this, "有耳机", Toast.LENGTH_SHORT).show();
                        }
                        oratorioListen.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };

    private void starRecordVideo() {
        if (!mStartedFlg) {
            // Start
            if (mRecorder == null) {
                mRecorder = new MediaRecorder(); // Create MediaRecorder
            }
            try {
                /**
                 * 解锁camera
                 * 设置输出格式为mpeg_4（mp4），此格式音频编码格式必须为AAC否则网页无法播放
                 */
                mCamera.unlock();
                mRecorder.setCamera(mCamera);
                mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                //音频编码格式对应应为AAC
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                //视频编码格式对应应为H264
                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                mRecorder.setVideoSize(640, 480);
                mRecorder.setVideoEncodingBitRate(600 * 1024);
                mRecorder.setPreviewDisplay(mMSurfaceHolder.getSurface());

                /**
                 * 设置输出地址
                 */
                String sdPath = getSDPath();
                if (sdPath != null) {
                    File dir = new File(sdPath + "/VideoAndAudio");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    path = dir + "/" + getDate() + ".mp4";

                    mRecorder.setOutputFile(path);
                    mRecorder.setOrientationHint(90);
                    mRecorder.prepare();
                    mRecorder.start();   // Recording is now started
                    starRecordTimer();
                    mStartedFlg = true;
//                    updateProgress();
                }
            } catch (Exception e) {
                /**
                 * 当用户拒绝录音权限会执行这里
                 */
                Toast.makeText(CameraOratorioActivity.this, "没有录音权限", Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            if (mStartedFlg) {
                try {
                    mRecorder.stop();
                    if (recordTask != null) {
                        recordTask.cancel();
                    }
                    mRecorder.reset();
                    mStartedFlg = false;
                    Toast.makeText(CameraOratorioActivity.this, "录制完成" + "视频地址:" + path, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(CameraOratorioActivity.this, "录制失败", Toast.LENGTH_SHORT).show();
                }
            }
            mStartedFlg = false; // Set button status flag
        }
    }

    /**
     * 获取SD path
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }

        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.w("test", "onDestroy");
        //清除动画，消息
        handler.removeMessages(TIMEPIECE);
        handler.removeMessages(TIMEDOWN);
        ivTwinkle.clearAnimation();
        timepiecePoint.clearAnimation();
        beautyFilter = null;
        musicRl = null;
//        stopRecording();
//        if (mCamera != null) {
//            cameraManager.closeCamera();
//        }
        unRegister();

        magicEngine.stopRecord();
        magicEngine = null;
        try {
            finishActivityByClz(CameraOratorioActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒
        String date = "" + year + (month + 1) + day + hour + minute + second;

        return date;
    }

    /**
     * 开启计时
     */
    private void starRecordTimer() {
        recordTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recordTime++;
                        int m = recordTime / 60;
                        int s = recordTime % 60;
                        String strm = m + "";
                        String strs = s + "";
                        if (m < 10) {
                            strm = "0" + m;
                        }
                        if (s < 10) {
                            strs = "0" + s;
                        }
                    }
                });
            }
        };
        Timer recordTimer = new Timer(true);
        recordTimer.schedule(recordTask, 0, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_iv_close:
                //关闭界面
                if (startState != 0 || is_recording || is_repeat) {
                    //弹出对话框
                    showExitDialog();
                } else {
                    finish();
                }
                break;
            case R.id.record_start_iv:
                //录制按钮  0 : 开始录歌  1:继续录歌  2:暂停录歌
//                Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
                if (countDownIv.getVisibility() == View.VISIBLE) {//防止重复点击
                    return;
                }
                ivEnd.setImageResource(R.drawable.oratorio_end_red);
                if (startState == 0) {
                    //开始录歌,点击变成暂停录歌；隐藏提示语和手势；显示计时器和光盘动画;音效消失
//                    startRecording();
                    ivStart.setImageResource(R.drawable.oratorio_pause);
                    changeUI();

                } else if (startState == 1) {
                    //继续录歌状态
                    if (mMediaRecorder != null) {
                        mCamera.lock();
                    }
//                    startRecording();
                    startState = 2;
                    ivStart.setImageResource(R.drawable.oratorio_pause);
                    //恢复暂停前的状态
                    if (!(mAddtype == 1 || mAddtype == 2)) {
                        if (alphaUpdateListener != null && alphaUpdateListener.isPause()) {
                            alphaUpdateListener.play();
                            handler.sendEmptyMessage(TIMEPIECE);
                        }
                    }
                } else if (startState == 2) {
                    //暂停录歌状态
                    Toast.makeText(this, "暂不支持暂停", Toast.LENGTH_SHORT).show();
//                    stopRecording();
//                    recorderManager.stopRecord();
//                    magicEngine.startRecord();
//                    stopAudioRecorder();
//                    startState = 1;
//                    ivStart.setImageResource(R.drawable.oratorio_continue);
                    //暂停计时器和动画
//                    handler.removeMessages(TIMEPIECE);
//                    if (!(mAddtype == 1 || mAddtype == 2)) {
//                        alphaUpdateListener.pause();
//                    }
                }
                break;
            case R.id.record_end_iv:
                if (is_recording) {
                    //弹出dialog
                    showThreeDialog();
                }
                break;
            case R.id.record_music:
//                Toast.makeText(this, "音效", Toast.LENGTH_SHORT).show();
                //开始按钮的逻辑，时间和录制红点停止：暂停计时器和动画
//                if (is_recording) {
//                    handler.removeMessages(TIMEPIECE);
//                    if (!(mAddtype == 1 || mAddtype == 2)) {
//                        if (alphaUpdateListener != null) {
//                            alphaUpdateListener.pause();
//                        }
//                    }
//                    ivStart.setImageResource(R.drawable.oratorio_continue);
//                    startState = 1;
//                }
                //音效
                if (isMusicRlVisiable) {
                    mRecordMusic.setImageResource(R.drawable.oratorio_music_normal);
                    musicRl.setVisibility(View.INVISIBLE);
                    isMusicRlVisiable = false;
                } else {
                    mRecordMusic.setImageResource(R.drawable.oratorio_music_press);
                    musicRl.setVisibility(View.VISIBLE);
                    isMusicRlVisiable = true;
                }
                break;

            case R.id.open_camera_camera://前后摄像头切换
                magicEngine.switchCamera();
                break;

            case R.id.camare_btn_start:
//                Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
                if (is_repeat) {//重录
//                    repeatRecord();
                    magicEngine.stopRecord();
                    stopAudioRecorder();
                    finishActivity();
                } else {
                    if (countDownIv != null && countDownIv.getVisibility() == View.VISIBLE) {
                        return;
                    }
                    changeUITwo();
                }
                break;
            case R.id.camera_ear_back:
                //耳返
                earListenBack();
                break;
        }
    }

    //重新录制
    private void repeatRecord() {
        //显示
        beautyFilter.setVisibility(View.VISIBLE);
        btnStart.setText(getString(R.string.camera_oratorio_start_record));//"开始录制"
        btnStart.setBackgroundResource(0);
        btnStart.setBackgroundResource(R.drawable.btn_bg);
        //隐藏
        progressBar.setVisibility(View.VISIBLE);
        progressNow.setVisibility(View.VISIBLE);
        progressTotle.setVisibility(View.VISIBLE);
        llTiempiece.setVisibility(View.VISIBLE);

        progressNow.setText(int2String(0));
        String text = getString(R.string.camera_oratorio_record_remian) + strMill + "分" + strSecond + "秒";
        tvCameraTiempieceText.setText(text);//"录制剩余" + strMill + "分" + strSecond + "秒"
        progressBar.setProgress(0);
        isRun = false;
//        float alpha = timepiecePoint.getAlpha();
//        LogUtils.w("alpha","alpha------------------" + alpha);
        timepiecePoint.clearAnimation();
//        handler.sendEmptyMessageDelayed(ALPHA,1000);
        //数据初始化
        is_repeat = false;
        is_recording = false;
        startState = 0;
        progress = 0;
        mDuration = mDurationTemp;
        handler.removeMessages(TIMEDOWN);
        //关闭录制
        magicEngine.stopRecord();
        stopAudioRecorder();
    }

    private void changeUITwo() {
        //进行3秒倒计时
        showCountDown();
        //滤镜选择弹层
        beautyFilter.setVisibility(View.INVISIBLE);
        //倒计时结束后
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //开始录制
                magicEngine.startRecord();
                llTiempiece.setVisibility(View.VISIBLE);
                musicRl.setVisibility(View.INVISIBLE);
                //开始动画
                isRun = true;
                handler.sendEmptyMessage(110);
                //减时间
                handler.sendEmptyMessage(TIMEDOWN);
//                startAnimation();
                is_repeat = true;
                is_recording = true;
                //按钮背景
                btnStart.setBackgroundResource(0);
                btnStart.setBackgroundResource(R.drawable.btn_bg2);
                btnStart.setText("重录");
//                btnStart.setPadding(btnStart.getPaddingLeft() * 2, 0 , btnStart.getPaddingRight() * 2,0);
                //进度条
                progressTotle.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                progressNow.setVisibility(View.VISIBLE);
                progressNow.setText(int2String(0));
            }
        }, 3000);
    }

    /**
     * 此按钮有三个状态：开始录歌、继续录歌和暂停录歌；0 : 开始录歌  1:继续录歌  2:暂停录歌
     * 1、隐藏音效选择和滤镜选择弹层，再进行3秒倒计时，倒计时结束后开始显示计时器并开始录制计时，同时显示录制红点，结束按钮由“禁用”状态转为“启用”。
     * 2、点击“暂停”，暂停录音录相，暂停计时器。
     * 3、点击“继续”，继续录音录相，继续计时器。
     */
    private void changeUI() {
        //进行3秒倒计时
        showCountDown();
        beautyFilter.setVisibility(View.INVISIBLE);
        musicRl.setVisibility(View.INVISIBLE);
        showTimeLl.setVisibility(View.VISIBLE);
        //倒计时结束后
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ivCamera.setVisibility(View.VISIBLE);
                //开始录制
//                recorderManager.reset();
//                recorderManager.startRecord(true);
                LogUtils.w("test", "5555555555555");
                magicEngine.startRecord();
                getAudioRecorder(recordAudioPath);//录音
                is_recording = true;
                startState = 2;
                //开始动画和计时
                startAnimation();
                handler.sendEmptyMessage(TIMEPIECE);
                //隐藏音效选择和滤镜选择弹层
//                beautyFilter.setVisibility(View.INVISIBLE);
                isMusicRlVisiable = false;
            }
        }, 3000);
    }

    /**
     * 开始动画,属性动画
     */
    private void startAnimation() {
        //小红点的闪烁效果
        alphaAnimation = ObjectAnimator.ofFloat(ivTwinkle, "alpha", 1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatMode(ValueAnimator.RESTART);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        if (alphaUpdateListener == null) {
            alphaUpdateListener = new MyAnimatorUpdateListener();
        }
        alphaAnimation.addUpdateListener(alphaUpdateListener);
        if (alphaUpdateListener != null && alphaUpdateListener.isPause()) {
            alphaUpdateListener.play();
        } else {
            alphaAnimation.start();
        }
    }

    //启动3s倒计时
    private void showCountDown() {
        drawable = (AnimationDrawable) countDownIv.getDrawable();
        drawable.start();
        countDownIv.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(111, 3000);
    }

//    //启动3s倒计时
//    private void showCountDownTwo() {
//        drawable2 = (AnimationDrawable) countCameraDownIv.getDrawable();
//        drawable2.start();
//        countCameraDownIv.setVisibility(View.VISIBLE);
//        handler.sendEmptyMessageDelayed(222, 3000);
//    }

    //弹出dialog
    private void showThreeDialog() {
        final ThreeButtonAlertDialog myDialog = new ThreeButtonAlertDialog(this).builder();
        myDialog.setTitle("温馨提示");
        myDialog.setMsg("确定已经完成当前清唱录歌吗？");
        myDialog.setCancelable(true);
        myDialog.setPositiveButton("重新录制", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束录像和录音
                magicEngine.stopRecord();
                stopAudioRecorder();
                //重新录制:不做数据保存直接使当前页面复位到初始状态；
//                reInit();
//                videoSurface.setVisibility(View.GONE);
//                beautyFilter = null;
                startActivity(new Intent(CameraOratorioActivity.this, OratorioActivity.class));
                finishActivity();
            }
        });
        myDialog.setNeutralButton("保存录制", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止录像
//                if (recorderManager != null) {
//                    recorderManager.stopRecord();
//                    stopAudioRecorder();
//                }
                magicEngine.stopRecord();
                stopAudioRecorder();
                //合成视屏
//                try {
//                    String[] arr = new String[recordPath.size()];
//                    for (int i = 0; i < recordPath.size(); i++) {
//                        arr[i] = recordPath.get(i);
//                    }
//                    VideoUtils.appendVideo(CameraOratorioActivity.this, getSDPath() + "/VideoRecorderTest" + "/diyishouge" + getDate() + ".mp4", arr);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //跳转到保存界面
                start2CameraSaveActivity();
//                startActivity(new Intent(CameraOratorioActivity.this,HomeActivity.class));
            }
        });
        myDialog.setNegativeButton("继续录制", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myDialog.show();

    }

    private void start2CameraSaveActivity() {
        intent = new Intent(CameraOratorioActivity.this, SavePracticeActivity.class);
        intent.putExtra("from", "camera1");//camera1:代表清唱模块的录像,camera2:代表自拍录歌
        if (time > limitTime) {
            endRecordTime = total = limitTime;
        } else {
            endRecordTime = total = time;
        }
        parameterPass(getFinalVideoFileName(), recordAudioPath, startRecordTime, endRecordTime * 1000);
        startActivity(intent);
        if (alphaUpdateListener != null) {
            alphaUpdateListener.play();
            alphaAnimation.end();
            ivTwinkle.clearAnimation();
            alphaUpdateListener = null;
        }
        finish();
    }

    private void start2CameraServer2() {
//        intent = new Intent(CameraOratorioActivity.this, SavePracticeCameraActivity.class);
//        intent.putExtra("from","camera1");//camera1:代表清唱模块的录像,camera2:代表自拍录歌
//        endRecordTime = total = time;
//        parameterPass(getFinalVideoFileName(), recordAudioPath, startRecordTime, endRecordTime * 1000);
//        intent.putExtra("mvurl", getFinalVideoFileName());
//        startActivity(intent);
//        finish();
    }

    //获取合成后的文件地址
    private String getFinalVideoFileName() {
        return MyFileUtil.DIR_VEDIO.toString() + File.separator + "MagicCamera_test.mp4";
    }


    private int startRecordTime = 0, endRecordTime;//录音开始的时间，录音结束的时间
    private int total, current;//总时长，当前进度

    //恢复初始状态
    private void reInit() {
        LogUtils.w("1116", "mAddtype===========" + mAddtype);
        //UI初始化
        if (mAddtype == 1 || mAddtype == 2) {
            handler.removeMessages(TIMEDOWN);
            llTiempiece.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            progressNow.setVisibility(View.VISIBLE);
            progressTotle.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            tvCameraTiempieceText.setText("录制剩余" + strMill + "分" + strSecond + "秒");
            btnStart.setText("开始录制");
            progressNow.setText(int2String(0));
            btnStart.setBackgroundResource(0);
            btnStart.setBackgroundResource(R.drawable.btn_bg);

            is_repeat = false;
            is_recording = false;
            startState = 0;
            progress = 0;
            mDuration = mDurationTemp;
        } else {
            //数据初始化
            LogUtils.w("1116", "111111");
            handler.removeMessages(TIMEPIECE);
            tvTimepiece.setText(TimeUtil.mill2mmss(0));
            time = 0;
            //动画圆点恢复
            if (alphaUpdateListener != null) {
                alphaUpdateListener.pause();
                alphaAnimation.end();
                ivTwinkle.clearAnimation();
                alphaUpdateListener = null;
            }
            if (extAudioRecorder == null) {
                extAudioRecorder = ExtAudioRecorder.getInstanse(false);
            }
            startState = 0;
            is_recording = false;
            is_repeat = false;
            //ui初始化
            musicRl.setVisibility(View.VISIBLE);
            llTiempiece.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            progressNow.setVisibility(View.INVISIBLE);
            progressTotle.setVisibility(View.INVISIBLE);
            //        beautyFilter.setVisibility(View.VISIBLE);
            ivStart.setImageResource(R.drawable.home_start_record);
            ivEnd.setImageResource(R.drawable.home_stop_record_white);
            showTimeLl.setVisibility(View.INVISIBLE);
            beautyFilter.setVisibility(View.VISIBLE);
        }


    }

//    public void stopRecording() {
//        if (mMediaRecorder != null) {
//            mMediaRecorder.stop();
//        }
//        releaseMediaRecorder();
//    }

    /**
     * 退出录制对话框
     */
    private void showExitDialog() {
        final AlertDialog alertDialog = new AlertDialog(this).builder();
        alertDialog.setMsg("您录制的歌曲还没有保存，确定要放弃吗？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清唱界面数据置为初始值
                if (alphaUpdateListener != null) {
                    alphaUpdateListener.play();
                    alphaAnimation.end();
                    alphaUpdateListener = null;
                    ivTwinkle.clearAnimation();
                }
                magicEngine.stopRecord();
                stopAudioRecorder();
                finishActivity();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        alertDialog.show();
    }

    private LocalCompose mLocalCompose;//歌曲对象
    private SongDetail songDetail;//歌曲对象
    //    private SongDetail oldsongDetail;//歌曲对象
    private int videoHeight;//视频窗口高度

    /**
     * songId = songDetail.getSongId();
     * musicType = songDetail.getType();
     * String singerName = songDetail.getSingerName();
     * songName = songDetail.getSongName();
     * imgAlbumUrl = songDetail.getImgAlbumUrl();
     * bzUrl = songDetail.getAccPath();
     *
     * @param videoFile
     * @param audioFile
     * @param startReordTime
     * @param endRecordTime
     */
    private void parameterPass(String videoFile, String audioFile, int startReordTime, int endRecordTime) {
        //假数据
        songDetail = new SongDetail();
        songDetail.setSongId("acappella");
        songDetail.setType("audio");
        songDetail.setSingerName("清唱");
        songDetail.setSongName("清唱");
        songDetail.setImgAlbumUrl(null);
        if (recordAudio != null) {
            LogUtils.w("bug", "99999999999");
            songDetail.setAccPath(recordAudio);
        } else {
            LogUtils.w("bug", "999999999992222222");
//            songDetail.setAccPath(videoFile);
            songDetail.setAccPath(audioFile);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("songDetail", songDetail);
        intent.putExtras(bundle);
        intent.putExtra("recordVideo", videoFile);
        LogUtils.w("qwe", "videoFile----" + videoFile);
        if (recordAudio != null) {//表示添加录像
            intent.putExtra("startReordTime", 0);//
            intent.putExtra("endReordTime", TotalTime);//
            intent.putExtra("recordAudio", audioFile);
            intent.putExtra("TotalTime", TotalTime);//
            LogUtils.w("bug", "TotalTime" + TotalTime);
        } else {
            intent.putExtra("startReordTime", startReordTime);//
            intent.putExtra("endReordTime", endRecordTime);//
            intent.putExtra("recordAudio", audioFile);
            intent.putExtra("TotalTime", total * 1000);//
        }
        intent.putExtra("videoHeight", 800);
//        intent.putExtra("videoHeight", videoSurface.getVedioHeight());
        intent.putExtra("recordType", "video");
        intent.putExtra("musicType", "video");
        intent.putExtra("oratorio", 2);//音频  0:音频  1：视屏 2:合成
    }

    private ExtAudioRecorder extAudioRecorder;//录音
    private boolean hasHeadset;//是否插入耳机

    //根据路径录音：
    private void getAudioRecorder(String recordFile) {
        if (extAudioRecorder == null)
            extAudioRecorder = ExtAudioRecorder.getInstanse(false);
        extAudioRecorder.setOutputFile(recordFile);
        extAudioRecorder.prepare();
        extAudioRecorder.start();
    }

    //停止录音
    private void stopAudioRecorder() {
        try {
            if (extAudioRecorder != null) {
                extAudioRecorder.stop();
                extAudioRecorder.release();
                extAudioRecorder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取音效
    @Override
    public void getData(int index) {
        LogUtils.w(TAG, "index==========" + index);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }

    @Override
    public void doSomething() {

    }

    private class ProcessFrameAsyncTask extends AsyncTask<byte[], Void, String> {

        @Override
        protected String doInBackground(byte[]... params) {

//            // TODO Auto-generated method stub
//            Camera.Size size = mCamera.getParameters().getPreviewSize(); //获取预览大小
//            final int w = size.width;  //宽度
//            final int h = size.height;
//            final YuvImage image = new YuvImage(params[0], ImageFormat.NV21, w, h, null);
//            ByteArrayOutputStream os = new ByteArrayOutputStream(params[0].length);
//            if(!image.compressToJpeg(new Rect(0, 0, w, h), 100, os)){
//                return null;
//            }
//            byte[] tmp = os.toByteArray();
//            Bitmap bmp = BitmapFactory.decodeByteArray(tmp, 0,tmp.length);
////            Bitmap bitmap = ImageHelper.handleImagePixelsOldPhoto(bmp);//自己定义的实时分析预览帧视频的算法
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            params[0] = baos.toByteArray();
//            //处理图片数据
//            Log.i(TAG, "====================...");
//            Bitmap bitmap = BitmapFactory.decodeByteArray(params[0], 0, params[0].length);//bitmap为空
//            Log.i(TAG, "====================..." + bitmap);
//            Bitmap bitmap1 = ImageHelper.handleImagePixelsOldPhoto(bitmap);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            params[0] = baos.toByteArray();
//            try {
//                baos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            processFrame(params[0]);
            return null;
        }

        private void processFrame(byte[] frameData) {

            Log.i(TAG, "正在处理预览帧...");
            Log.i(TAG, "预览帧大小" + String.valueOf(frameData.length));
            Log.i(TAG, "预览帧处理完毕...");
            //下面这段注释掉的代码是把预览帧数据输出到sd卡中，以.yuv格式保存
            String path = getSDPath();
            File dir = new File(path + "/FrameTest");
            if (!dir.exists()) {
                dir.mkdir();
            }
            path = dir + "/" + "testFrame" + ".yuv";
            File file = new File(path);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(frameData);
                Log.i(TAG, "预览帧处理完毕...");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新初始化数据
        reInit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!(mAddtype == 1 || mAddtype == 2)) {
            if (alphaUpdateListener != null) {
                alphaUpdateListener.play();
                alphaAnimation.end();
                ivTwinkle.clearAnimation();
                alphaUpdateListener = null;
            }
            handler.removeMessages(TIMEPIECE);
        }
        if (mAddtype == 1 || mAddtype == 2) {
            handler.removeMessages(TIMEDOWN);
            if (timepiecePoint != null) {
                timepiecePoint.clearAnimation();
            }
        }
        isRun = false;
//        recorderManager.stopRecord();
        stopAudioRecorder();
    }

    class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        /**
         * 暂停状态
         */
        private boolean isPause = false;
        /**
         * 是否已经暂停，如果一已经暂停，那么就不需要再次设置停止的一些事件和监听器了
         */
        public boolean isPaused = false;
        /**
         * 当前的动画的播放位置
         */
        private float fraction = 0.0f;
        /**
         * 当前动画的播放运行时间
         */
        private long mCurrentPlayTime = 0l;

        /**
         * 是否是暂停状态
         *
         * @return
         */
        public boolean isPause() {
            return isPause;
        }

        /**
         * 停止方法，只是设置标志位，剩余的工作会根据状态位置在onAnimationUpdate进行操作
         */
        public void pause() {
            isPause = true;
        }

        public void play() {
            isPause = false;
            isPaused = false;
        }


        @Override
        public void onAnimationUpdate(final ValueAnimator animation) {
            /**
             * 如果是暂停则将状态保持下来，并每个刷新动画的时间了；来设置当前时间，让动画
             * 在时间上处于暂停状态，同时要设置一个静止的时间加速器，来保证动画不会抖动
             */
            if (isPause) {
                if (!isPaused) {
                    mCurrentPlayTime = animation.getCurrentPlayTime();
                    fraction = animation.getAnimatedFraction();
                    animation.setInterpolator(new TimeInterpolator() {
                        @Override
                        public float getInterpolation(float input) {
                            if (mAddtype == 1 || mAddtype == 2) {
                                return 0;
                            } else {
                                return fraction;
                            }
//                            return fraction;
//                            return 0;
                        }
                    });
                    isPaused = true;
                }
                //每隔动画播放的时间，我们都会将播放时间往回调整，以便重新播放的时候接着使用这个时间,同时也为了让整个动画不结束
                timer = new CountDownTimer(ValueAnimator.getFrameDelay(), ValueAnimator.getFrameDelay()) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        LogUtils.w("timer", "zzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                    }

                    @Override
                    public void onFinish() {
                        LogUtils.w("timer", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
                        animation.setCurrentPlayTime(mCurrentPlayTime);
                    }
                }.start();
            } else {
                //将时间拦截器恢复成线性的，如果您有自己的，也可以在这里进行恢复
                animation.setInterpolator(null);
            }
        }
    }


    /**
     * 录制完成之后，需要跳转到后台
     */
    public void makeMV2Server() {
//        recordAudioPath = diyLocalCompose.getRecordUrl();//录音文件
//        intent = new Intent(CameraOratorioActivity.this, SavePracticeCameraActivity.class);
//        endRecordTime = total = time;
//        LogUtils.w("endRecordTime","endRecordTime==" + endRecordTime * 1000);
//        LogUtils.w("endRecordTime","mDurationTemp==" + mDurationTemp);
//        LogUtils.w("endRecordTime","mDurationTemp12==" + Integer.parseInt(mLocalCompose.getCompose_finish()));
//        LogUtils.w("endRecordTime","mDurationTemp12==" +  mLocalCompose.getRecordUrl());
////        mLocalCompose.setCompose_finish(mDurationTemp * 1000 + "");
////        parameterPass(getFinalVideoFileName(), recordAudioPath, startRecordTime, endRecordTime * 1000);
//        parameterPass(getFinalVideoFileName(), mLocalCompose.getRecordUrl(), startRecordTime, Integer.parseInt(mLocalCompose.getCompose_finish()));
//        intent.putExtra("mvurl", getFinalVideoFileName());
//        intent.putExtra("from", "camera");
//        startActivity(intent);
//        finish();


//        //获取当前时间，以便用做解码文件命名：
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        SimpleDateFormat dDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        datetime = sDateFormat.format(new java.util.Date());
//        createDate = dDateFormat.format(new java.util.Date());
//        makeDB(datetime);//添加到数据库
////        AddMVCompose mAddMVCompose = new AddMVCompose(diyLocalCompose.getCompose_id(), mLocalCompose,diyLocalCompose, selectList,outUrl, 0, 0);
////        public AddMVCompose(String id, LocalCompose diyLocalCompose, String mvpath, String outUrl, int position, int state) {
//        AddMVCompose mAddMVCompose = new AddMVCompose(diyLocalCompose.getCompose_id(), diyLocalCompose, mLocalCompose, getFinalVideoFileName(), diyLocalCompose.getCompose_file(), 0, 0);
////        toast("《"+names.get(position)+"》已准备下载，请稍后");
//
//        //启动后台，并传递相关参数：
////        List< LinkedHashMap<Integer, GifItem>> GifItemlist = new ArrayList<>();
////        GifItemlist.add(addItems);
//        Intent intent = new Intent(this, MVComposeService.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("AddMVCompose", mAddMVCompose);
//        intent.putExtras(bundle);
//        intent.setAction("DIYCOMPOSE_START");
//        startService(intent);
//        handler.sendEmptyMessageDelayed(101, 1500);//延时跳转到本地歌曲界面

    }


    /**
     * 新建记录：
     *
     * @param datetime
     */
    public void makeDB(String datetime) {//新建类，并添加到数据库记录
        try {
            songName = mLocalCompose.getCompose_name();
            composeType = Integer.parseInt(mLocalCompose.getCompose_type());
        } catch (Exception e) {
            e.printStackTrace();
        }

        composeFile = composeDir + mLocalCompose.getCompose_name() + datetime + ".mp4";

        diyLocalCompose = new LocalCompose();
//        diyLocalCompose = mLocalCompose;
        diyLocalCompose.setCreateDate(createDate);//设置时间：
        LogUtils.sysout("保存数据库datetime=" + datetime);
        diyLocalCompose.setCompose_id(datetime);//设置时间为唯一标识：
        diyLocalCompose.setCompose_name(mLocalCompose.getCompose_name());//名字

        if (mLocalCompose != null && mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("0")) {
            diyLocalCompose.setCompose_remark("友友们，快来听我新唱的《" + songName + "》吧！");//名字
        } else if (mLocalCompose != null && mLocalCompose.getCompose_type() != null && mLocalCompose.getCompose_type().equals("5")) {
            diyLocalCompose.setCompose_remark("");//名字
        } else {
            diyLocalCompose.setCompose_remark("友友们，快来听我新唱的《" + songName + "》吧！");//名字
        }
        diyLocalCompose.setSongId(mLocalCompose.getSongId());//歌曲id
//        diyLocalCompose.setCompose_type(mLocalCompose.getCompose_type());//合成类型
        diyLocalCompose.setCompose_type("7");//合成类型
        diyLocalCompose.setRecordUrl(mLocalCompose.getRecordUrl());//录制资源路径
        diyLocalCompose.setBzUrl(mLocalCompose.getBzUrl());//伴奏资源路径
        diyLocalCompose.setCompose_image(mLocalCompose.getCompose_image());//封面
        diyLocalCompose.setCompose_begin(mLocalCompose.getCompose_begin());//开始录制进度
        diyLocalCompose.setCompose_finish(mLocalCompose.getCompose_finish() + "");//结束录制进度
        diyLocalCompose.setCompose_file(composeFile);//合成之后的文件//
        diyLocalCompose.setCompose_delete("0");
        diyLocalCompose.setCompose_MuxerTask("1");
        mComposeManager.insertCompose(diyLocalCompose);//插入记录
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (startState != 0 || is_recording || is_repeat) {
                //弹出对话框
                showExitDialog();
            } else {
                finishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
