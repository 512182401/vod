//package com.changxiang.vod.module.ui.recordmusic;
//
//import android.Manifest;
//import android.animation.ObjectAnimator;
//import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.AnimationDrawable;
//import android.graphics.drawable.BitmapDrawable;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.audiofx.Equalizer;
//import android.media.audiofx.PresetReverb;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.widget.CheckBox;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.coremedia.iso.IsoFile;
//import com.coremedia.iso.boxes.Container;
//import com.coremedia.iso.boxes.TrackBox;
//import com.googlecode.mp4parser.authoring.Movie;
//import com.googlecode.mp4parser.authoring.Track;
//import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
//import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
//import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.highlight.HighLight;
//import com.quchangkeji.tosingpk.common.highlight.position.OnBottomPosCallback;
//import com.quchangkeji.tosingpk.common.highlight.position.OnTopPosCallback;
//import com.quchangkeji.tosingpk.common.highlight.shape.CircleLightShape;
//import com.quchangkeji.tosingpk.common.utils.ImageLoader;
//import com.quchangkeji.tosingpk.common.utils.KrcParse;
//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.common.utils.MyFileUtil;
//import com.quchangkeji.tosingpk.common.utils.PreferUtil;
//import com.quchangkeji.tosingpk.common.utils.ScreenUtils;
//import com.quchangkeji.tosingpk.common.utils.SharedPrefManager;
//import com.quchangkeji.tosingpk.common.utils.dialog.AlertDialog;
//import com.quchangkeji.tosingpk.common.view.AudioVideoToggle;
//import com.quchangkeji.tosingpk.common.view.FlikerProgressBar;
//import com.quchangkeji.tosingpk.common.view.ImageAnimation;
//import com.quchangkeji.tosingpk.common.view.MyAlertDialog;
//import com.quchangkeji.tosingpk.common.view.MySurfaceViewTwo;
//import com.quchangkeji.tosingpk.common.view.PowerManagerUtil;
//import com.quchangkeji.tosingpk.common.view.SKrcViewFour;
//import com.quchangkeji.tosingpk.common.view.SwitchView;
//import com.quchangkeji.tosingpk.common.view.ThreeButtonAlertDialog;
//import com.quchangkeji.tosingpk.common.view.guideview.Guide;
//import com.quchangkeji.tosingpk.module.base.BaseApplication;
//import com.quchangkeji.tosingpk.module.constance.Constant;
//import com.quchangkeji.tosingpk.module.db.DownloadManager;
//import com.quchangkeji.tosingpk.module.db.ParameterBean;
//import com.quchangkeji.tosingpk.module.entry.CurrentPeriodBean;
//import com.quchangkeji.tosingpk.module.entry.GuideItem;
//import com.quchangkeji.tosingpk.module.entry.KrcLine;
//import com.quchangkeji.tosingpk.module.entry.SongDetail;
//import com.quchangkeji.tosingpk.module.musicInfo.TimeUtil;
//import com.quchangkeji.tosingpk.module.music_download.downloadservice.DownloadAllService;
//import com.quchangkeji.tosingpk.module.ui.base.BaseActivity;
//import com.quchangkeji.tosingpk.module.ui.other.HomeListener;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.Blur;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.ExtAudioRecorder;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.NoDragSeekBar;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.RecorderManager;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.screen.FragmentMusicScreen;
//import com.quchangkeji.tosingpk.module.ui.saveutils.SavePracticeActivity;
//import com.seu.magicfilter.MagicEngine;
//import com.seu.magicfilter.filter.helper.MagicFilterType;
//import com.seu.magicfilter.widget.MagicCameraView;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.nio.channels.Channels;
//import java.nio.channels.FileChannel;
//import java.nio.channels.WritableByteChannel;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * 录像
// * Created by 15976 on 2016/12/5.
// */
//
//public class OpenCameraActivity2 extends BaseActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MySurfaceViewTwo.MySufaceViewListener, MagicCameraView.MagicViewListener, SoundEffecView.MyListener, AudioVideoToggle.OnToggleClickListener {
//    private static final int PROGRESS_START = 1;
//    private String TAG = OpenCameraActivity2.class.getName();
//    //高斯模糊背景
//    private ImageView ivBg;
//    //顶部控件
//    private ImageView ivBack;
//    private CheckBox ivCamera;
//    private TextView tvSong;
//    private TextView tvTime;
//    private ImageView redDot;
//    private ImageView countDownIv;//倒计时（起唱符）
//    private ImageView tvCountDown;//点击开始后3s倒计时
//    //    private CameraLrcView lrcView;//歌词
//    private SKrcViewFour lrcView;//歌词
//    private MagicCameraView videoSurface;//录像窗口
//    private ImageView lightAnimIv;//放置灯光动画的控件
//    //跳过前奏框
//    private LinearLayout prelude_ll;
//    private TextView tvPreludeTime;
//    private TextView tvSkip;
//    private TextView tvClose;
//    //底部控制按钮
//    private ImageView ivMode;//模式
//    private ImageView ivAccompany;//伴奏、原唱
//    private ImageView ivEnd;//结束录音
//    private ImageView ivScore;//音乐屏开关
//    //    private View dialog;//对话框布局
//    private ProgressDialog processDia;//数据加载对话框
//
//    //音乐屏布局
//    private FrameLayout musicScreen;
//    //音效，音调控制按钮
//    private ImageView sound_effect_iv;
//    private ImageView modify_tone_iv;
//
//    private final String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//
//    private final String mp4Dir = MyFileUtil.DIR_VEDIO.toString() + File.separator;//
//    private final String accDir = MyFileUtil.DIR_ACCOMPANY.toString() + File.separator;//
//    private final String lrcDir = MyFileUtil.DIR_LRC.toString() + File.separator;//
//    private final String krcDir = MyFileUtil.DIR_KRC.toString() + File.separator;//
//    private File ycFile;//原唱文件地址
//    private File bzFile;//伴奏文件地址
//    private File lrcFile;//lrc文件地址
//    private File krcFile;//krc文件地址
//
//    private HomeListener mHomeWatcher;//home键监控
//    private SongDetail songDetail;//包含地址的歌曲对象
//    private String musicType, songName, singerName, imgcover;//歌曲类型(mv,mp3),歌曲名，歌手名,封面地址,歌手头像
//    private String preludeTime;//前奏时间
//    private Intent intent;
//    private final List<Integer> countDownList = new ArrayList<>();
//    //    public List<LrcContent> lrclists;
//    private List<KrcLine> krcLines;
//    private final int NOT_START = 0;//未播放
//    private final int ISPLAYING = 1;//正在录制
//    private final int PAUSE = 2;//暂停状态
//    private int total, current;//总时长，当前进度
//    private int musicTag = 1;//0表示当前播放的是原唱，1表示当前播放的是伴奏,默认是伴奏
//    private int state = 0;//0表示还未开始播放，1表示正在播放，2表示暂停播放
//    private int startRecordTime, endRecordTime;//录音开始的时间，录音结束的时间
//    private int videoHeight;//视频窗口高度
//    private final int[] frameRes = {R.drawable.light01, R.drawable.light02, R.drawable.light03, R.drawable.light04, R.drawable.light05, R.drawable.light06,
//            R.drawable.light07, R.drawable.light08, R.drawable.light09, R.drawable.light10, R.drawable.light11, R.drawable.light12,
//            R.drawable.light13, R.drawable.light14, R.drawable.light15, R.drawable.light16, R.drawable.light17, R.drawable.light18,
//            R.drawable.light19, R.drawable.light20, R.drawable.light21, R.drawable.light22, R.drawable.light23, R.drawable.light24,
//            R.drawable.light25};//灯光图片
//    private ImageAnimation imageAnimation;//灯光动画
//
//    private MediaPlayer accPlayer;//原唱，伴奏播放器
//    private RecorderManager recorderManager = null;//录音
//    private FragmentTransaction fragmentTransaction;
//    private FragmentMusicScreen ftMusicScreen;//音乐屏
//    private AnimationDrawable animationDrawable;
//    private String recordAudioPath;//录音文件地址
//
//    //    private String finalVideo;//录音和录像合成后的文件地址
////    private int countNum;//倒计时数字
//    private DownloadManager dao;//数据库管理工具
//
//    //获取播放进度，设置当前播放进度和更新歌词进度
//    private final Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            LogUtils.w("tag", "total = " + total);
//            if (total == 0) {
//                return;
//            }
//            updateTime(accPlayer, mRunnable);
//        }
//    };
//    private RelativeLayout topRl;
//    private RelativeLayout topRlAnimal;
//    private NoDragSeekBar cameracSb;
//    private TextView sbStart;
//    private TextView sbEnd;
//    private int progress;//初始进度条
//    private boolean isSkipPreLude;
//    private ImageView pauseIcon;
//    private ImageView pause;
//    private ImageView oratorioListen;
//    private SwitchView switchBtn;
//    private FlikerProgressBar download;
//    private Intent intent1;
//    private AudioVideoToggle fragmentState;
//    private BeautyFilterView beautyFilter;
//    private final MagicFilterType[] types = new MagicFilterType[]{
//            MagicFilterType.NONE,//0
//            MagicFilterType.LOMO,
//            MagicFilterType.FAIRYTALE,
//            MagicFilterType.SUNRISE,
//            MagicFilterType.SUNSET,
//            MagicFilterType.WHITECAT,
//            MagicFilterType.BLACKCAT,
//            MagicFilterType.SKINWHITEN,
//            MagicFilterType.HEALTHY,//8
//            MagicFilterType.SWEETS,
//            MagicFilterType.ROMANCE,
//            MagicFilterType.SAKURA,
//            MagicFilterType.WARM,
//            MagicFilterType.ANTIQUE,
//            MagicFilterType.NOSTALGIA,
//            MagicFilterType.CALM,
//            MagicFilterType.LATTE,
//            MagicFilterType.TENDER,//17
//            MagicFilterType.COOL,//18
//            MagicFilterType.EMERALD,
//            MagicFilterType.EVERGREEN,
//            MagicFilterType.CRAYON,
//            MagicFilterType.SKETCH,
//            MagicFilterType.AMARO,
//            MagicFilterType.BRANNAN,
//            MagicFilterType.BROOKLYN,
//            MagicFilterType.EARLYBIRD,
//            MagicFilterType.FREUD,
//            MagicFilterType.HEFE,
//            MagicFilterType.HUDSON,
//            MagicFilterType.INKWELL,//30
//            MagicFilterType.KEVIN,
//            MagicFilterType.N1977,
//            MagicFilterType.NASHVILLE,
//            MagicFilterType.PIXAR,
//            MagicFilterType.RISE,
//            MagicFilterType.SIERRA,
//            MagicFilterType.SUTRO,
//            MagicFilterType.TOASTER2,
//            MagicFilterType.VALENCIA,
//            MagicFilterType.WALDEN,
//            MagicFilterType.XPROII
//    };
//    private MagicEngine magicEngine;
//    private CurrentPeriodBean hotSong;
//    private SKrcViewFour lrcViewAll;
//    private String mFState;
//    private RelativeLayout openCameaRl;
//    private LinearLayout recordBottom;
//    private TextView fragmentText;
//    private ImageView recordFont;
//    private LinearLayout recordFontLL;
//    private RadioGroup recordRg;
//    private SoundEffecView soundEffect;
//    private boolean isshow;//頂部是否显示
//    private String activityId;
//    private RelativeLayout cameraParent;
//    private String songId;
//    private String type;
//    private int time;
//    private ThreeButtonAlertDialog myDialog;
//    private View dialog;//对话框布局
//    private int position;
//
//    @Override
//    public void handMsg(Message msg) {
//        float coefficient;
//        switch (msg.what) {
//            case 110:
//                checkGuide();//检测是否显示引导界面
//                break;
//            case 10:
//                countDownIv.setVisibility(View.INVISIBLE);//隐藏倒计时
//                animationDrawable.stop();//结束动画
//                break;
//            case 22://3s后开始录像
//                if (lrcFile != null && lrcFile.exists() && ycFile.exists() && bzFile.exists() && krcFile.exists()) {
//                    setData();
//                } else {
//                    toast(getString(R.string.file_not_exist));
//                }
//                break;
//            case 111:
//                if (drawable != null) {
//                    drawable.stop();
//                }
//                tvCountDown.setVisibility(View.GONE);
//                //录音，播放伴奏
//                prelude_ll.setVisibility(View.VISIBLE);
////                play();
//                break;
//            case -222://播放失败
//                toast(getString(R.string.play_fail));
////                recorderManager.stopRecord();
////                stopAudioRecorder();
//                break;
//            case 44:
//                closeProgressDialog();//关闭进度条
//                parameterPass(getFinalVideoFileName(), recordAudioPath, startRecordTime, endRecordTime);
//                startActivity(intent);
//                break;
//            case 555://跳过前奏后录制
////                skipPreludeRecord();
//                startRecord();//开始录制
//                updateViews();
//                break;
//            case 2240:
//                coefficient = (float) msg.obj;
//                updataFontSize(coefficient);
//                break;
//            case 2241:
//                coefficient = (float) msg.obj;
//                updataFontSize(coefficient);
//                break;
//            case 2242:
//                coefficient = (float) msg.obj;
//                updataFontSize(coefficient);
//                break;
//            case PROGRESS_START:
//                progress++;
////                sbEnd.setText("12:00");
//                sbStart.setText(TimeUtil.mill2mmss(progress));
//                break;
//        }
//    }
//
//    /**
//     * 字体刷新
//     *
//     * @param coefficient
//     */
//    private void updataFontSize(float coefficient) {
//        if (lrcView != null) {
//            lrcView.setFontCoefficient(coefficient);
//            lrcView.invalidate();
//        }
//        if (lrcViewAll != null) {
//            lrcViewAll.setFontCoefficient(coefficient);
//            lrcViewAll.invalidate();
//        }
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_open_camera);
//
//        //关闭第三方app播放
//        clossOther(this, true);
//        PowerManagerUtil.keepScreenOn(this, true);//保持屏幕不熄灭
////        registerHomeListener();//监控Home键      可以不用
//        intent1 = getIntent();
//        intent = new Intent();
//        String test = intent1.getStringExtra("test");
//        initView();
//        initData();
//        initEvent();
//        sentStopBroad();
//        handler.sendEmptyMessageDelayed(110, 1000);
//    }
//
//    private Boolean clossOther(Context context, boolean bMute) {
//        if (context == null) {
//            LogUtils.w("ANDROID_LAB", "context is null.");
//            return false;
//        }
////        if(!VersionUtils.isrFroyo()){
////            // 2.1以下的版本不支持下面的API：requestAudioFocus和abandonAudioFocus
////            LogUtils.w("ANDROID_LAB", "Android 2.1 and below can not stop music");
////            return false;
////        }
//        boolean bool = false;
//        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        if (bMute) {
//            int result = am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);//请求系统的音频焦点
//            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
//        } else {
//            int result = am.abandonAudioFocus(null);//播放完毕后取消焦点
//            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
//        }
//        LogUtils.w("ANDROID_LAB", "pauseMusic bMute=" + bMute + " result=" + bool);
//        return bool;
//    }
//
//    private void initView() {
//        ivBg = (ImageView) findViewById(R.id.record_camera_bg);
//        ivBack = (ImageView) findViewById(R.id.open_camera_ivBack);
//        ivCamera = (CheckBox) findViewById(R.id.open_camera_camera);
//        tvSong = (TextView) findViewById(R.id.open_camera_tvSong);
//        tvTime = (TextView) findViewById(R.id.home_duration);
//        redDot = (ImageView) findViewById(R.id.red_dot);
//        countDownIv = (ImageView) findViewById(R.id.camera_count_down);
//        tvCountDown = (ImageView) findViewById(R.id.teach_count_down_tv);
//        lrcView = (SKrcViewFour) findViewById(R.id.open_camera_lrc);
//        lrcViewAll = (SKrcViewFour) findViewById(R.id.open_camera_all_lrc);
//        videoSurface = (MagicCameraView) findViewById(R.id.open_camera_sv);
//        lightAnimIv = (ImageView) findViewById(R.id.camera_light_iv);
//        musicScreen = (FrameLayout) findViewById(R.id.music_screen);
//        ivMode = (ImageView) findViewById(R.id.home_mode);
//        ivAccompany = (ImageView) findViewById(R.id.home_accompany);
//        soundEffect = (SoundEffecView) findViewById(R.id.record_sound_effect);
//        ivEnd = (ImageView) findViewById(R.id.end_iv);
//        ivScore = (ImageView) findViewById(R.id.grade_screen);
//
//        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
//        //跳过前奏
//        prelude_ll = (LinearLayout) findViewById(R.id.prelude_ll);
//        tvPreludeTime = (TextView) findViewById(R.id.prelude_time);
//        tvSkip = (TextView) findViewById(R.id.skip_prelude);
//        tvClose = (TextView) findViewById(R.id.prelude_tvClose);
//
//        topRl = (RelativeLayout) findViewById(R.id.top_rl);
//        topRlAnimal = (RelativeLayout) findViewById(R.id.open_camera_animal);
//        pauseIcon = (ImageView) findViewById(R.id.start_pause_animal);
//        pause = (ImageView) findViewById(R.id.start_pause);
//        recordFont = (ImageView) findViewById(R.id.record_font);
//        recordFontLL = (LinearLayout) findViewById(R.id.record_font_ll);
//        cameraParent = (RelativeLayout) findViewById(R.id.open_camera_sv_parent);
//
//        //滤镜
//        beautyFilter = (BeautyFilterView) findViewById(R.id.beauty_filter);
//        ViewGroup.LayoutParams layoutParams = videoSurface.getLayoutParams();
//        layoutParams.width = ScreenUtils.widthPixels(this);
//        layoutParams.height = ScreenUtils.widthPixels(this) * Constant.previewRateH / Constant.previewRateW;//设置预览视频宽高
//        videoSurface.setLayoutParams(layoutParams);
//
//        MagicEngine.Builder builder = new MagicEngine.Builder();
//        magicEngine = builder
//                .build((MagicCameraView) findViewById(R.id.open_camera_sv));
//        beautyFilter.setFilters(types);
//        beautyFilter.setOnClickListener(this);
//        beautyFilter.setOnFilterChangeListener(new BeautyFilterView.onFilterChangeListener() {
//            @Override
//            public void onFilterChanged(MagicFilterType filterType) {
//                magicEngine.setFilter(filterType);
//            }
//        });
//
//        //进度条相关
//        cameracSb = (NoDragSeekBar) findViewById(R.id.open_camera_sb);
//        sbStart = (TextView) findViewById(R.id.seek_start);
//        sbEnd = (TextView) findViewById(R.id.seek_end);
//        oratorioListen = (ImageView) findViewById(R.id.open_camera_ear_back);//耳返
//
//        //下载
//        download = (FlikerProgressBar) findViewById(R.id.open_camerac_download);
//
//        fragmentState = (AudioVideoToggle) findViewById(R.id.fragment_state);
//        if ("mv".equals(PreferUtil.getInstance().getString("fragmentState", "mv"))) {
////            fragmentState.setImageResource(R.drawable.mv);
//            fragmentState.setSelectAndInvalidate(AudioVideoToggle.SELECT_MV);
//        } else if ("mp3".equals(PreferUtil.getInstance().getString("fragmentState", "mv"))) {
////            fragmentState.setImageResource(R.drawable.mp3);
//            fragmentState.setSelectAndInvalidate(AudioVideoToggle.SELECT_MP3);
//        }
//
//        //mp3进度条时间监听
//        cameracSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // TODO Auto-generated method stub
//                LogUtils.w("fromUser", "fromUser=========" + fromUser);
//                LogUtils.w("fromUser", "fromUser=========" + progress);
//                if (fromUser) {
//                    if (isSkipPreLude) {
//                        //跳过前奏
//                        LogUtils.w("fromUser", "qz=========" + Integer.parseInt(songDetail.getQzTime()));
//                        accPlayer.seekTo(progress + Integer.parseInt(songDetail.getQzTime()));
//                    } else {
//                        //没跳过前奏
//                        accPlayer.seekTo(progress);
//                    }
//                }
//            }
//        });
//
//        openCameaRl = (RelativeLayout) findViewById(R.id.open_camera_rl);
//        recordBottom = (LinearLayout) findViewById(R.id.practice_sing_llBotton);
//        fragmentText = (TextView) findViewById(R.id.fragment_text);
//        //字号设置
//        recordRg = (RadioGroup) findViewById(R.id.record_rg);
////        PreferUtil.getInstance().putString("font","small");
//        String restore = PreferUtil.getInstance().getString("font", "small");
//        if ("small".equals(restore)) {
//            recordRg.check(R.id.record_font_small);
//            sendMsg(2240, 1.0f);
//        } else if ("middle".equals(restore)) {
//            recordRg.check(R.id.record_font_middle);
//            sendMsg(2241, 1.5f);
//        } else if ("big".equals(restore)) {
//            recordRg.check(R.id.record_font_big);
//            sendMsg(2242, 2.0f);
//        }
//    }
//
//    private void initData() {
//        //动态设置SurfaceView的布局
//        ViewGroup.LayoutParams layoutParams = cameraParent.getLayoutParams();
//        layoutParams.width = ScreenUtils.widthPixels(this);
//        layoutParams.height = 4 * ScreenUtils.widthPixels(this) / 3;
//        cameraParent.setLayoutParams(layoutParams);
//        LogUtils.sysout(ScreenUtils.widthPixels(this) + "------------------------" + ScreenUtils.heightPixels(this));
////        extAudioRecorder = ExtAudioRecorder.getInstanse(false);
////        extAudioRecorder.setHasHeadSet(false);//测试先关掉
//        recordAudioPath = MyFileUtil.DIR_RECORDER.toString() + File.separator + "source.wav";//录音地址
//        FragmentManager manager = getSupportFragmentManager();
//        ftMusicScreen = new FragmentMusicScreen(this);
////        recorderManager = new RecorderManager(getCameraManager(), videoSurface, this);
//        //是MV还是MP3
//        mFState = PreferUtil.getInstance().getString("fragmentState", "mv");
//        if ("mv".equals(mFState)) {
//            lrcViewAll.setVisibility(View.INVISIBLE);
//            openCameaRl.setBackgroundResource(0);
//            ivCamera.setVisibility(View.VISIBLE);
//        } else if ("mp3".equals(mFState)) {
//            videoSurface.setVisibility(View.INVISIBLE);
//            lrcView.setVisibility(View.INVISIBLE);
//            beautyFilter.setVisibility(View.INVISIBLE);
//            ivCamera.setVisibility(View.INVISIBLE);
//            openCameaRl.setBackgroundResource(R.drawable.record_mp3_bg);
//        }
//        //播放器
//        if (accPlayer == null) {
//            accPlayer = new MediaPlayer();
//        }
//        accPlayer.setOnCompletionListener(this);
//        fragmentTransaction = manager.beginTransaction();
//        fragmentTransaction.replace(R.id.music_screen, ftMusicScreen);
//        fragmentTransaction.commit();
//        musicScreen.setVisibility(View.INVISIBLE);//音乐屏所在Frame显示
//        if (imgcover != null && imgcover.length() > 6) {
//            ImageLoader.getDiskCache(imgcover);
//            Bitmap bitmap = com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImageSync(imgcover);
//            setBg(bitmap);
//        } else {
//            setBg(null);
//        }
//        setupPresetReverb();//音效
//
//        //下载歌曲,若已经下载就不需要在下载
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x002);
//        } else {
//            dao = DownloadManager.getDownloadManager(this);
//            startDownload();
//        }
//    }
//
//    private void startDownload() {
//        //获取要下载歌曲的信息
//        hotSong = (CurrentPeriodBean) intent1.getSerializableExtra("currentPeriod");
//        String test = intent1.getStringExtra("test");
//        LogUtils.w("1234567", "hotSong====" + hotSong.toString());
//        tvSong.setText(hotSong.getSongName());
//        activityId = hotSong.getActivityId();
//        songId = hotSong.getId();
//        type = hotSong.getType();
//        position = intent1.getIntExtra("position", 0);
//        //查询数据库是否已经下载
//        boolean allDownload = dao.isAllDownload(activityId, songId, type);
//        LogUtils.sysout("activityId----" + activityId + "----songId-----------" + songId + "-----type-----------" + type);
//        LogUtils.sysout("allDownload--------------" + allDownload);
//        if (allDownload) {
//            download.setProgress(100);
//            songDetail = dao.selectSong(activityId, hotSong.getId(), hotSong.getType());
//            LogUtils.sysout(songDetail.toString());
//            bzFile = new File(songDetail.getAccPath());
//            krcFile = new File(songDetail.getKrcPath());
//            lrcFile = new File(songDetail.getLrcPath());
//            //查数据库
//            detialSongInfo();
//            loadLrcView();
//            prepareAccplay();
//            return;
//        }
//        //检查网络
//        switch (BaseApplication.wifiState) {
//            case 0://
//            case 1://每次提醒
//                playNetDialog(0);
//                break;
//            case 2://首次提醒
//                if (BaseApplication.isNoticeOnce != 1) {
//                    playNetDialog(1);
//                } else {
//                    startDownloadService();
//                }
//                break;
//            case 3://不提醒
//            case 4://wifi
//                startDownloadService();
//                break;
//            case 5:
////                closeLoadingDialog();
//                toast(getString(R.string.no_net));
//                break;
//            default:
//                break;
//        }
//    }
//
//    //开启下载服务
//    private void startDownloadService() {
//        Intent intent = new Intent(this, DownloadAllService.class);
//        ParameterBean parameterBean = new ParameterBean(hotSong.getId(), hotSong.getType(), hotSong.getSongName(), hotSong.getSingerName(), position);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("parameterBean", parameterBean);
//        intent.putExtras(bundle);
//        intent.setAction("ACTION_START");
//        startService(intent);
//    }
//
//    /**
//     * 播放流量提醒对话框
//     *
//     * @param isNoticeOnce 针对首次提醒设置，0，没有提醒过，1 已经提醒过了
//     */
//    private void playNetDialog(final int isNoticeOnce) {
//        FrameLayout parent = (FrameLayout) dialog.getParent();
//        if (parent != null) {
//            parent.removeView(dialog);
//        }
//        new MyAlertDialog(this, dialog).builder()
//                .setMsg(getString(R.string.wifiDisconnected_notice))
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        BaseApplication.isNoticeOnce = isNoticeOnce;
//                        startDownloadService();
//                    }
//                }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishActivity();
//            }
//        }).show();
//    }
//
//    private void detialSongInfo() {
//        if (songDetail != null) {
//            String songId = songDetail.getSongId();
//            musicType = songDetail.getType();
//            songName = songDetail.getSongName();
//            singerName = songDetail.getSingerName();
//            imgcover = songDetail.getImgAlbumUrl();
//            preludeTime = songDetail.getQzTime();
//            String activityId = hotSong.getActivityId();
//            if (preludeTime == null) {
//                DownloadManager dao = DownloadManager.getDownloadManager(this);
//                songDetail = dao.selectSong(activityId, songId, musicType);
//                preludeTime = songDetail.getQzTime();
//            }
//        }
//        if (songName != null && singerName != null) {
//            if ("video".equals(musicType)) {
//                ycFile = new File(mp4Dir + singerName + "-" + songName + ".mp4");//
//                bzFile = new File(accDir + singerName + "-" + songName + ".mp4");//
//            } else if ("audio".equals(musicType)) {
//                ycFile = new File(mp3Dir + singerName + "-" + songName + ".mp3");//
//                bzFile = new File(accDir + singerName + "-" + songName + ".mp3");//
//            }
//            lrcFile = new File(lrcDir + singerName + "-" + songName + ".lrc");//
//            krcFile = new File(krcDir + singerName + "-" + songName + ".krc");//
//        }
//        if (songDetail != null && songDetail.getOriPath() == null) {
//            songDetail.setOriPath(ycFile.getPath());
//        }
//    }
//
//
//    private void setBg(Bitmap bitmap) {
//        if (bitmap == null) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tv_mv);
//        }
//        doBlurnew(ivBg, bitmap);
//    }
//
//    private void doBlurnew(final ImageView iv, final Bitmap bitmap) {
//        int scaleRatio = 20;
//        int blurRadius = 8;
//        if (bitmap != null) {
//            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
//                    bitmap.getWidth() / scaleRatio,
//                    bitmap.getHeight() / scaleRatio,
//                    false);
//            final Bitmap blurBitmap = Blur.fastblur(this, scaledBitmap, blurRadius);
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
////            bgImg.setImageBitmap(blurBitmap);
//            try {
//                iv.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        iv.setImageBitmap(blurBitmap);
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void initEvent() {
//        ivBack.setOnClickListener(this);
//        ivCamera.setOnClickListener(this);
//        lrcView.setOnClickListener(this);
//        ivMode.setOnClickListener(this);
//        ivAccompany.setOnClickListener(this);
//        ivEnd.setOnClickListener(this);
//        ivScore.setOnClickListener(this);
//        tvClose.setOnClickListener(this);
//        tvSkip.setOnClickListener(this);
//
//        pauseIcon.setOnClickListener(this);
//        pause.setOnClickListener(this);
//        fragmentState.setOnToggleClickListener(this);
//        fragmentState.setOnToggleTouchListener(new AudioVideoToggle.OnToggleTouchListener() {
//            @Override
//            public void myTouch() {
//                int select = fragmentState.getSelect();
//                LogUtils.w("select", "=========" + select);
//                if (select == AudioVideoToggle.SELECT_MP3) {
//                    PreferUtil.getInstance().putString("fragmentState", "mp3");
//                    lrcViewAll.setVisibility(View.VISIBLE);
//                    ivCamera.setVisibility(View.INVISIBLE);
//                    videoSurface.setVisibility(View.INVISIBLE);
//                    beautyFilter.setVisibility(View.INVISIBLE);
//                    lrcView.setVisibility(View.INVISIBLE);
//                    openCameaRl.setBackgroundResource(R.drawable.record_mp3_bg);
//                } else if (select == AudioVideoToggle.SELECT_MV) {
//                    PreferUtil.getInstance().putString("fragmentState", "mv");
//                    lrcViewAll.setVisibility(View.INVISIBLE);
//                    ivCamera.setVisibility(View.VISIBLE);
//                    videoSurface.setVisibility(View.VISIBLE);
//                    beautyFilter.setVisibility(View.VISIBLE);
//                    lrcView.setVisibility(View.VISIBLE);
//                    openCameaRl.setBackgroundResource(0);
//                }
//            }
//        });
//
//        oratorioListen.setOnClickListener(this);
//        download.setOnClickListener(this);
//        recordFont.setOnClickListener(this);
//
//        //字号RadioGraup监听
//        recordRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.record_font_small) {
//                    //handler发送信息
//                    sendMsg(2240, 1.0f);
//                    PreferUtil.getInstance().putString("font", "small");
//                } else if (checkedId == R.id.record_font_middle) {
//                    //handler发送信息
////                    Message msg = Message.obtain();
////                    msg.what = 2241;
////                    msg.obj = 1.5f;
////                    handler.sendMessage(msg);
//                    sendMsg(2241, 1.5f);
//                    PreferUtil.getInstance().putString("font", "middle");
//                } else if (checkedId == R.id.record_font_big) {
////                    toast("大号");
//                    //handler发送信息
////                    Message msg = Message.obtain();
////                    msg.what = 2242;
////                    msg.obj = 2.0f;
////                    handler.sendMessage(msg);
//                    sendMsg(2242, 2.0f);
//                    PreferUtil.getInstance().putString("font", "big");
//                }
//            }
//        });
//        videoSurface.setMagicViewListener(this);
//        prelude_ll.setOnClickListener(this);
//        soundEffect.setOnListener(this);
//    }
//
//    private void sendMsg(int what, Object obj) {
//        Message msg = Message.obtain();
//        msg.what = what;
//        msg.obj = obj;
//        handler.sendMessage(msg);
//    }
//
//    /**
//     * 初始化预设音场控制器
//     */
//    // 定义系统的预设音场控制器
//    private PresetReverb mPresetReverb1;//伴奏加音效
//    private PresetReverb mPresetReverb2;//原唱加音效
//    private List<Short> reverbNames = new ArrayList<>();
//    Equalizer mEqualizer;
//
//    private void setupPresetReverb() {
//        mPresetReverb1 = new PresetReverb(0,
//                accPlayer.getAudioSessionId());
//        // 设置启用预设音场控制
//        mPresetReverb1.setEnabled(true);
//
//        // 定义系统的均衡器,相当于设置Equalizer负责控制该MediaPlayer
//        mEqualizer = new Equalizer(0, accPlayer.getAudioSessionId());
//        // 启用均衡控制效果
//        mEqualizer.setEnabled(true);
//        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
//            reverbNames.add(i);
//        }
//    }
//
//    private void releaseSoundEffect() {
//        if (mPresetReverb1 != null) {
//            mPresetReverb1.release();
//            mPresetReverb1 = null;
//        }
//        if (mPresetReverb2 != null) {
//            mPresetReverb2.release();
//            mPresetReverb2 = null;
//        }
//        if (mEqualizer != null) {
//            mEqualizer.release();
//            mEqualizer = null;
//        }
//    }
//
//    //设置音效
//    private void setSoundEffect(short num) {
//        try {
//            if (musicTag == 0) {
//                mPresetReverb2.setPreset(num);
//            } else {
//                mPresetReverb1.setPreset(num);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //发送停止广播
//    private void sentStopBroad() {
//        Intent stopIntent = new Intent();
//        stopIntent.setAction("ACTION_STOP");
//        sendBroadcast(stopIntent);
//    }
//
//
//    @Override
//    protected void onResume() {
////        registerHomeListener();//监控Home键和网络状态
//        registerReceive();
//        //显示
//        download.setVisibility(View.VISIBLE);
//        fragmentState.setVisibility(View.VISIBLE);
//        fragmentText.setVisibility(View.VISIBLE);
//        beautyFilter.setVisibility(View.VISIBLE);
//        //隐藏
//        recordBottom.setVisibility(View.INVISIBLE);
//        musicScreen.setVisibility(View.INVISIBLE);
//
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        videoSurface.measure(w, h);
//        videoSurface.getMeasuredWidth(); // 获取宽度
//        videoHeight = videoSurface.getMeasuredHeight(); // 获取高度
//        sbEnd.setText("00:00");
//        sbStart.setText("00:00");
//        cameracSb.setProgress(0);
//        state = 0;
//        try {
//            if (PreferUtil.getInstance().getHomeBack()) showWindow(4);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (lrcView != null) {
//            lrcView.changeCurrent(0);
//        }
//        if (lrcViewAll != null) {
//            lrcViewAll.changeCurrent(0);
//        }
//
//        super.onResume();
//    }
//
//    //EventBus传递的下载进度
//    @Override
//    public void setDownloadProgress(int percent) {
//        super.setDownloadProgress(percent);
//        download.setProgress(percent);
//        if (percent == 100) {
//            //设置歌词
//            LogUtils.sysout("songDetails===" + activityId);
//            LogUtils.sysout("hotSong===" + hotSong);
//            songDetail = dao.selectSong(activityId, hotSong.getId(), hotSong.getType());
//            LogUtils.sysout("songDetails====" + songDetail);
//            detialSongInfo();
//            loadLrcView();
//            //准备播放音乐
//            prepareAccplay();
//        }
//    }
//
//    private void prepareAccplay() {
//        LogUtils.sysout("prepareAccplay");
//        progress = 0;
//        getNeedCountDown();
////        startRecord();
//        startRecordTime = 0;
//        try {
//            accPlayer.reset();
//            LogUtils.w("pk", "bzFile" + bzFile.toString());
//            accPlayer.setDataSource(bzFile.toString());
//            accPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//
//                }
//            });
//            accPlayer.prepare();
//            total = accPlayer.getDuration();//设置总时长和当前播放进度
//            LogUtils.w("pk", "total===" + total);
//            sbEnd.setText(TimeUtil.mill2mmss(total));
//            ftMusicScreen.setExtAudioRecorder(extAudioRecorder, 0);
//            ftMusicScreen.setKrcfile(krcFile.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            handler.sendEmptyMessage(-222);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.open_camera_ivBack://返回
//                if (tvCountDown.isShown()) {
//                    return;
//                }
//                if (state != NOT_START) {
//                    showWindow(0);
//                } else {
//                    finishActivity();
//                }
//                break;
//            case R.id.open_camera_camera://切换摄像头
//                magicEngine.switchCamera();
//                break;
//            case R.id.end_iv://
//                //如果还没点击播放，则返回
//                if (state == NOT_START) {
//                    return;
//                }
//                showWindow(4);
//                break;
//            case R.id.grade_screen://
//                hideOrShowMusicScreen(musicScreen.isShown());
//                break;
//            case R.id.skip_prelude://跳过前奏
//                stop(); //停止录制
////                showCountDown();//3s倒计时
//                startCountDown();
//                skipPreludeRecord();
//                handler.sendEmptyMessageDelayed(555, 3000);//3s开始播放
//                break;
//            case R.id.prelude_tvClose://关闭前奏对话框
//                prelude_ll.setVisibility(View.GONE);
//                break;
//            case R.id.start_pause_animal:
//            case R.id.start_pause:
//                //暂不支持暂停录像
//                showWindow(0);
//                break;
//            case R.id.open_camera_ear_back:
//                //耳返
////                Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
//                earListenBack();
//                break;
//            case R.id.fragment_state://mp3和mp4状态切换   UI上的变化
////                toast("点击");
////                mFState = PreferUtil.getInstance().getString("fragmentState", "mv");
//                int select = fragmentState.getSelect();
//                LogUtils.w("select", "=========" + select);
//                if (select == AudioVideoToggle.SELECT_MV) {
////                    fragmentState.setImageResource(R.drawable.mp3);
////                    fragmentState.setSelect(AudioVideoToggle.SELECT_MP3);
//                    PreferUtil.getInstance().putString("fragmentState", "mp3");
//                    lrcViewAll.setVisibility(View.VISIBLE);
//                    ivCamera.setVisibility(View.INVISIBLE);
//                    videoSurface.setVisibility(View.INVISIBLE);
//                    beautyFilter.setVisibility(View.INVISIBLE);
//                    lrcView.setVisibility(View.INVISIBLE);
//                    openCameaRl.setBackgroundResource(R.drawable.record_mp3_bg);
//                } else if (select == AudioVideoToggle.SELECT_MP3) {
////                    fragmentState.setImageResource(R.drawable.mv);
////                    fragmentState.setSelect(AudioVideoToggle.SELECT_MV);
//                    PreferUtil.getInstance().putString("fragmentState", "mv");
//                    lrcViewAll.setVisibility(View.INVISIBLE);
//                    ivCamera.setVisibility(View.VISIBLE);
//                    videoSurface.setVisibility(View.VISIBLE);
//                    beautyFilter.setVisibility(View.VISIBLE);
//                    lrcView.setVisibility(View.VISIBLE);
//                    openCameaRl.setBackgroundResource(0);
//                }
//                break;
//            case R.id.open_camerac_download://开始按钮
//                //录音权限
//                //申请读写SD卡的权限和录音权限
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 0x0003);
//                } else {
//                    startBtn();
//                }
//
//                break;
//            case R.id.record_font://字号选择
//                if (soundEffect.getVisibility() == View.VISIBLE) {//不然会覆盖字号UI,所以先隐藏音效UI
//                    soundEffect.setVisibility(View.INVISIBLE);
//                    ivAccompany.setImageResource(R.drawable.record_music_before);
//                }
//                if (recordFontLL.getVisibility() == View.VISIBLE) {
//                    recordFontLL.setVisibility(View.INVISIBLE);
//                    recordFont.setImageResource(R.drawable.record_font_before);
//                } else if (recordFontLL.getVisibility() == View.INVISIBLE) {
//                    recordFontLL.setVisibility(View.VISIBLE);
//                    recordFont.setImageResource(R.drawable.record_font_after);
//                }
//                break;
//            case R.id.home_accompany://音效选择
//                if (recordFontLL.getVisibility() == View.VISIBLE) {
//                    recordFontLL.setVisibility(View.INVISIBLE);
//                    recordFont.setImageResource(R.drawable.record_font_before);
//                }
//                if (soundEffect.getVisibility() == View.VISIBLE) {
//                    soundEffect.setVisibility(View.INVISIBLE);
//                    ivAccompany.setImageResource(R.drawable.record_music_before);
//                } else {
//                    soundEffect.setVisibility(View.VISIBLE);
//                    ivAccompany.setImageResource(R.drawable.record_music_after);
//                }
//                break;
//        }
//    }
//
//    //耳返开关
//    private void earListenBack() {
//        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_listen, null, false);
//        contentView.setClickable(true);
//        switchBtn = (SwitchView) contentView.findViewById(R.id.pop_listen_switch);
//        switchBtn.setOpened(PreferUtil.getInstance().getBoolean("earback", true));//默认打开
//        if (extAudioRecorder != null) {
//            extAudioRecorder.setHasHeadSet(PreferUtil.getInstance().getBoolean("earback", true));//默认打开
//        }
//        PopupWindow popWnd = new PopupWindow(this);
//        popWnd.setContentView(contentView);
//        popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        //是否点击外面消失
//        popWnd.setBackgroundDrawable(new BitmapDrawable());
//        popWnd.setOutsideTouchable(true);
//        //可点击
//        popWnd.setFocusable(true);
//        popWnd.setTouchable(true);
//        switchBtn.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
//            @Override
//            public void toggleToOn(View view) {
////                Toast.makeText(OratorioActivity.this, "111111", Toast.LENGTH_SHORT).show();
//                switchBtn.setOpened(true);
//                hasHeadset = true;
//                extAudioRecorder.setHasHeadSet(true);
//                //sp保存状态
//                PreferUtil.getInstance().putBoolean("earback", true);
//            }
//
//            @Override
//            public void toggleToOff(View view) {
////                Toast.makeText(OratorioActivity.this, "222222222", Toast.LENGTH_SHORT).show();
//                switchBtn.setOpened(false);
//                hasHeadset = false;
//                extAudioRecorder.setHasHeadSet(false);
//                PreferUtil.getInstance().putBoolean("earback", false);
//            }
//        });
//        //将PopupWindow显示在oratorioListen的下方
//        contentView.measure(0, 0);
//        int measuredWidth = contentView.getMeasuredWidth();
//        popWnd.showAsDropDown(oratorioListen, -measuredWidth + 60, 40);
//    }
//
//    /**
//     * 显示或者隐藏音乐屏
//     *
//     * @param hide 为true时需要隐藏
//     */
//    private void hideOrShowMusicScreen(boolean hide) {
//        if (hide) {
//            ftMusicScreen.setFireWorksShow(false);
//            musicScreen.setVisibility(View.GONE);
//            if (fragmentTransaction != null) {
//                fragmentTransaction.remove(ftMusicScreen);
//            }
//        } else {
//            ftMusicScreen.setFireWorksShow(true);
//            musicScreen.setVisibility(View.VISIBLE);
//        }
//    }
//
//    //打开灯光效果
//    private void startLight() {
//        lightAnimIv.setVisibility(View.VISIBLE);
//        imageAnimation = new ImageAnimation(lightAnimIv, frameRes, 100);
//    }
//
//    //关闭灯光效果
//    private void stopLight() {
//        if (imageAnimation != null) {
//            imageAnimation.destory();
//            imageAnimation = null;
//            lightAnimIv.setVisibility(View.GONE);
//        }
//    }
//
//    public void muteAll(boolean isMute) {
//        List<Integer> streams = new ArrayList<>();
//        Field[] fields = AudioManager.class.getFields();
//        for (Field field : fields) {
//            if (field.getName().startsWith("STREAM_")
//                    && Modifier.isStatic(field.getModifiers())
//                    && field.getType() == int.class) {
//                try {
//                    Integer stream = (Integer) field.get(null);
//                    streams.add(stream);
//                } catch (IllegalArgumentException e) {
//                    // do nothing
//                } catch (IllegalAccessException e) {
//                    // do nothing
//                }
//            }
//        }
//    }
//
//    //加载歌词  /storage/emulated/0/gewang/krc/王源-淘汰.krc
//    private void loadLrcView() {
//        LogUtils.w("krcFile", "krcFile========" + krcFile);
//        if (krcFile != null) {
//            try {
//                krcLines = KrcParse.setKrcPath(krcFile.toString(), false);
//                lrcView.setLrcLists(krcLines);
//                lrcView.setLrcState(krcLines.size() == 0 ? 0 : 1);
//                lrcViewAll.setLrcLists(krcLines);
//                lrcViewAll.setLrcState(krcLines.size() == 0 ? 0 : 1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    //获取需要显示3s倒计时的时间集合
//    private void getNeedCountDown() {
//        if (krcLines != null && krcLines.size() > 2) {
//            int timeDuration = 0;
//            countDownList.clear();
//            countDownList.add(krcLines.get(0).getLineTime().getStartTime());
//            for (int i = 1; i < krcLines.size(); i++) {
//                timeDuration = (krcLines.get(i).getLineTime().getStartTime() -
//                        (krcLines.get(i - 1).getLineTime().getStartTime() + krcLines.get(i - 1).getLineTime().getSpanTime()));
//                if (timeDuration > 10 * 1000) {
//                    countDownList.add(krcLines.get(i).getLineTime().getStartTime());
//                }
//            }
//        }
//    }
//
//    //开始录像
//    private void setData() {
//        progress = 0;
//        getNeedCountDown();
//        startRecord();
//        startRecordTime = 0;
//        try {
//            accPlayer.reset();
//            accPlayer.setDataSource(bzFile.toString());
//            accPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    accPlayer.start();
//                    state = ISPLAYING;
//                    updateViews();
//                }
//            });
//            accPlayer.prepare();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            handler.sendEmptyMessage(-222);
//        }
//    }
//
//    private void startRecord() {
//        new Thread() {
//            @Override
//            public void run() {
//                magicEngine.startRecord();
//                //进度条
//                handler.sendEmptyMessage(PROGRESS_START);
//            }
//        }.run();
//        getAudioRecorder(recordAudioPath);
//    }
//
//    private void updateViews() {
//        total = accPlayer.getDuration();//设置总时长和当前播放进度
//        current = accPlayer.getCurrentPosition();
//        tvTime.setText(getString(R.string.record) + TimeUtil.mill2mmss(current) + "/" + TimeUtil.mill2mmss(total));
////                      updateLrcView(lrcView, current);
//        //进度条
//        sbEnd.setText(TimeUtil.mill2mmss(total));
//        sbStart.setText(TimeUtil.mill2mmss(current));
//        cameracSb.setMax(total);
//        cameracSb.setProgress(current);
//
//        hideOrShowMusicScreen(false);
//        ivCamera.setVisibility(View.GONE);
//        redDot.setVisibility(View.VISIBLE);
//        tvTime.setVisibility(View.VISIBLE);
//        if (startRecordTime > 0) {//跳过前奏
//            prelude_ll.setVisibility(View.GONE);
//        } else {
//            prelude_ll.setVisibility(View.VISIBLE);
//        }
//        pauseIcon.setVisibility(View.VISIBLE);
//        pause.setVisibility(View.VISIBLE);
//        //开启动画
//        startIconAnimal();
//        handler.postDelayed(mRunnable, 10);
//        ftMusicScreen.setExtAudioRecorder(extAudioRecorder, 0);//获取振幅
//        ftMusicScreen.setKrcfile(krcFile.toString());
//    }
//
//    //一闪一闪动画
//    private void startIconAnimal() {
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
//        alphaAnimation.setDuration(1000);
//        alphaAnimation.setRepeatCount(Animation.INFINITE);
//        pauseIcon.startAnimation(alphaAnimation);
//    }
//
//    //跳过前奏
//    private void skipPreludeRecord() {
//        isSkipPreLude = true;
//        getNeedCountDown();
////        startRecord();//开始录制
//        try {
//            startRecordTime = Integer.parseInt(preludeTime);
//            accPlayer.reset();
//            accPlayer.setDataSource(bzFile.toString());
//            accPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    accPlayer.seekTo(Integer.parseInt(preludeTime) - 3000);
//                    if (musicTag == 0) {
//                        accPlayer.start();
//                        accPlayer.pause();
//                    } else {
//                        accPlayer.start();
//                        state = ISPLAYING;
//                        updateViews();
//                    }
//                }
//            });
//            accPlayer.prepare();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            handler.sendEmptyMessage(-222);
//        }
//    }
//
//
//    private ExtAudioRecorder extAudioRecorder = null;//录音
//    private boolean hasHeadset;//是否插入耳机
//
//    //根据路径录音：
//    private void getAudioRecorder(String recordFile) {
//        LogUtils.w("tag12", "mp3录音开始");
//        if (extAudioRecorder == null)
//            extAudioRecorder = ExtAudioRecorder.getInstanse(false);
//        extAudioRecorder.setHasHeadSet(false);//测试先关掉
//        extAudioRecorder.setOutputFile(recordFile);
//        extAudioRecorder.prepare();
//        extAudioRecorder.start();
//    }
//
//    //停止录音
//    private void stopAudioRecorder() {
//        try {
//            LogUtils.w("tag12", "mp3录音结束");
//            if (extAudioRecorder != null) {
//                extAudioRecorder.stop();
//                extAudioRecorder.release();
//                extAudioRecorder = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //启动3s倒计时
//    private void startCountDown() {
//        countDownIv.setVisibility(View.VISIBLE);
//        animationDrawable = (AnimationDrawable) countDownIv.getDrawable();
////        animationDrawable.setOneShot(false);
//        animationDrawable.start();
//        handler.sendEmptyMessageDelayed(10, 3000);//
//    }
//
//    //播放音频
//    public void play() {
//        LogUtils.w("tag123", "111111111111");
//        //播放伴奏
////        accPlayer.seekTo(current);
//        mFState = PreferUtil.getInstance().getString("fragmentState", "mv");
//        if ("mv".equals(mFState)) {
//            new Thread() {
//                @Override
//                public void run() {
//                    //录像
//                    magicEngine.startRecord();
//                }
//            }.run();
//            //录音
//            LogUtils.w("tag123", "2222222222222");
//            getAudioRecorder(recordAudioPath);
//        } else if ("mp3".equals(mFState)) {
//            //录音
//            LogUtils.w("tag123", "333333333333");
//            getAudioRecorder(recordAudioPath);
//        }
////        LogUtils.w("tag123","extAudioRecorder" + extAudioRecorder.getMaxAmplitude());
//        ftMusicScreen.setExtAudioRecorder(extAudioRecorder, 0);//获取振幅
//        ftMusicScreen.setKrcfile(krcFile.toString());
//        //开始按钮的闪烁动画和进度条开始结束时间
//        startIconAnimal();
//        cameracSb.setProgress(0);
//        handler.postDelayed(mRunnable, 100);
//        accPlayer.start();
//        state = ISPLAYING;
//    }
//
//    //暂停
//    public void pause() {
//
//        if (state == ISPLAYING) {
////            recorderManager.stopRecord();
////            stopAudioRecorder();
//            if (accPlayer.isPlaying()) {
//                accPlayer.pause();
//            }
//            if (mRunnable != null) {
//                handler.removeCallbacks(mRunnable);
//            }
//            state = PAUSE;
//        }
//    }
//
//    //停止
//    private void stop() {
//        try {
//            if ("mv".equals(mFState)) {
//                magicEngine.stopRecord();
//                LogUtils.w("tag12", "-------------1");
//                stopAudioRecorder();
//            } else if ("mp3".equals(mFState)) {
//                LogUtils.w("tag12", "-------------2");
//                stopAudioRecorder();
//            }
//        } finally {
////            muteAll(false);
//        }
//        if (accPlayer != null && accPlayer.isPlaying()) {
//            accPlayer.stop();
//        }
//        handler.removeCallbacks(mRunnable);
//        state = NOT_START;
//        redDot.setVisibility(View.GONE);
//        tvTime.setText("");
//        sbStart.setText("00:00");
//        loadLrcView();
//        ivCamera.setVisibility(View.VISIBLE);
//        prelude_ll.setVisibility(View.GONE);
//        if (ftMusicScreen != null) {
//            ftMusicScreen.setinitMusicScreen();
////            hideOrShowMusicScreen(true);//隐藏音乐屏
//        }
//    }
//
//    private void updateTime(MediaPlayer mediaPlayer, Runnable runnable) {
//        if (mediaPlayer != null && state == ISPLAYING && mediaPlayer.isPlaying()) {
//            current = mediaPlayer.getCurrentPosition();
//            total = mediaPlayer.getDuration();
//            if (countDownList.size() > 0) {
//                int time = countDownList.get(0) - 3 * 1000 - current;
//                LogUtils.w("tag", "倒计时：" + "，时间差" + time);
//                if (countDownList.get(0) - 3 * 1000 > 0 && (time <= 150 && time > 0)) {//显示倒计时
//                    LogUtils.w("tag", "倒计时：" + countDownList.get(0) + ",当前播放进度" + current + "，时间差" + time);
//                    countDownList.remove(0);//移除已经显示了3s倒计时的那一个
//                    startCountDown();
//                }
//            }
//            try {
//                if (startRecordTime > 0 || current > Integer.parseInt(preludeTime)) {
//                    prelude_ll.setVisibility(View.GONE);
//                } else {
//                    tvPreludeTime.setText((Integer.parseInt(preludeTime) - current) / 1000 + "s");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (ftMusicScreen != null) {
//                LogUtils.w("tag", "current========" + current);
//                ftMusicScreen.setKrcProgress(current);
//            }
//            tvTime.setText(getString(R.string.record) + TimeUtil.mill2mmss(current - startRecordTime) + "/" + TimeUtil.mill2mmss(total - startRecordTime));
//            time = current - startRecordTime;
//            if (time < 0) {
//                time = 0;
//            }
//            sbStart.setText(TimeUtil.mill2mmss(time));
//            sbEnd.setText(TimeUtil.mill2mmss(total - startRecordTime));
//            cameracSb.setMax(total - startRecordTime);
//            cameracSb.setProgress(current - startRecordTime);
//            lrcView.changeCurrentTwo(current);
//            lrcViewAll.changeCurrentTwo(current);
//            handler.postDelayed(runnable, 100);
//        }
//
//    }
//
//
//    //拼接视频
//    private void combineFiles() {
//
//        FileChannel fc = null;
//        try {
//            List<Track> videoTracks = new LinkedList<>();
//            List<Track> audioTracks = new LinkedList<>();
//            for (String fileName : recorderManager.getVideoTempFiles()) {
//                try {
//                    Movie movie = MovieCreator.build(fileName);
//                    for (Track t : movie.getTracks()) {
//                        if (t.getHandler().equals("soun")) {
//                            audioTracks.add(t);
//                        }
//                        if (t.getHandler().equals("vide")) {
//                            videoTracks.add(t);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Movie result = new Movie();
//
//            if (audioTracks.size() > 0) {
//                result.addTrack(new AppendTrack(audioTracks
//                        .toArray(new Track[audioTracks.size()])));
//            }
//            if (videoTracks.size() > 0) {
//                result.addTrack(new AppendTrack(videoTracks
//                        .toArray(new Track[videoTracks.size()])));
//            }
//
//            Container out = new DefaultMp4Builder().build(result);
//
//            fc = new RandomAccessFile(
//                    String.format(getFinalVideoFileName()), "rw").getChannel();
//            out.writeContainer(fc);
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (fc != null) {
//                try {
//                    fc.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            closeLoadingDialog();
//        }
//    }
//
//    //数据加载时进度框
//    public void showLoadingDialog(Context context, String message, boolean isCancelable) {
//        if (processDia == null && context != null) {
//            processDia = new ProgressDialog(context, R.style.dialog);
//            //点击提示框外面是否取消提示框
//            processDia.setCanceledOnTouchOutside(false);
//            //点击返回键是否取消提示框
//            processDia.setCancelable(isCancelable);
//            processDia.setIndeterminate(true);
//            processDia.setMessage(message);
//            processDia.show();
//        }
//    }
//
//    /**
//     * 关闭加载对话框
//     */
//    private void closeLoadingDialog() {
//        if (processDia != null) {
//            if (processDia.isShowing()) {
//                processDia.cancel();
//            }
//            processDia = null;
//        }
//    }
//
//    private List<TrackBox> getTrackBoxesOfVideoFileByPath(String sourceFilePath) {
//        IsoFile isoFile = null;
//        List<TrackBox> trackBoxes = null;
//        try {
//            isoFile = new IsoFile(sourceFilePath);
//            trackBoxes = isoFile.getMovieBox().getBoxes(TrackBox.class);
//            isoFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return trackBoxes;
//    }
//
//    /*private Movie getRotatedMovieOfTrackBox(List<TrackBox> trackBoxes) {
//        Movie rotatedMovie = new Movie();
//        // 旋转
//        for (TrackBox trackBox : trackBoxes) {
//            trackBox.getTrackHeaderBox().setMatrix(Matrix.ROTATE_90);
//            rotatedMovie.addTrack(new Mp4TrackImpl(trackBox));
//        }
//        return rotatedMovie;
//    }*/
//
//    private void writeMovieToModifiedFile(Movie movie) {
//        Container container = new DefaultMp4Builder().build(movie);
//        File modifiedVideoFile = new File(getFinalVideoFileName().replace(".mp4", "_MOD.mp4"));
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream(modifiedVideoFile);
//            WritableByteChannel bb = Channels.newChannel(fos);
//            container.writeContainer(bb);
//            // 关闭文件流
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //删除录制的文件
//    private void deleteFiles() {
//        if (recorderManager != null) {
//            for (String fileName : recorderManager.getVideoTempFiles()) {
//                if (fileName != null) {
//                    File file = new File(fileName);
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                }
//            }
//        }
//    }
//
//    //获取合成后的文件地址
//    private String getFinalVideoFileName() {
//        return MyFileUtil.DIR_VEDIO.toString() + File.separator + "MagicCamera_test.mp4";
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        if (myDialog != null && myDialog.isShow()) {
//            myDialog.dismiss();
//        }
//        //跳转到保存练歌页面
//        if (state == NOT_START) {
//            return;
//        }
//        int totalScore = ftMusicScreen.stopExtAudioRecorder(0);
//        //音乐和录音停止
//        stop();
//        //视频拼接
//        intent = new Intent(OpenCameraActivity2.this, SavePracticeActivity.class);
//        endRecordTime = total;
//        parameterPass(getFinalVideoFileName(), recordAudioPath, startRecordTime, endRecordTime);
//
//        int rankPercent;
//        if (krcLines == null || krcLines.size() == 0) {
//            rankPercent = 50;
//        } else {
//            rankPercent = totalScore / (krcLines.size() + 1);
//        }
//        if (rankPercent > 100) {
//            rankPercent = 90;
//        } else if (rankPercent < 30) {
//            rankPercent = 30;
//        }
//        /*if (ftMusicScreen!=null){
//            ftMusicScreen.setResetMusicScreen();
//        }*/
//        //提示总得分和排名
//        new AlertDialog(this).builder().setTitle(getString(R.string.notice))
//                .setMsg(getString(R.string.total_score) + totalScore + "," + getString(R.string.beat_country) + rankPercent + "%" + getString(R.string.de_user))
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mHomeWatcher != null) {
//                            mHomeWatcher.stopWatch();
//                        }
//                        releaseSoundEffect();
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                }).show();
//    }
//
//    private void parameterPass(String videoFile, String audioFile, int startReordTime, int endRecordTime) {
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("songDetail", songDetail);
//        intent.putExtras(bundle);
//        intent.putExtra("recordVideo", videoFile);
//        intent.putExtra("recordAudio", audioFile);
//        intent.putExtra("videoHeight", videoHeight);
//        intent.putExtra("activityId", activityId);
//        mFState = PreferUtil.getInstance().getString("fragmentState", "mv");
//        if ("mv".equals(mFState)) {
//            intent.putExtra("recordType", "mp4");
//        } else if ("mp3".equals(mFState)) {
//            intent.putExtra("recordType", "mp3");
//        }
//        intent.putExtra("startReordTime", startReordTime);//开始时间
//        intent.putExtra("endReordTime", endRecordTime);//结束时间
//        intent.putExtra("isSkipPreLude", isSkipPreLude);//是否跳过前奏
//        intent.putExtra("TotalTime", total);//总时长
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (tvCountDown.isShown()) {
//                return false;
//            } else if (state != NOT_START) {
//                showWindow(0);
//            } else {
//                return super.onKeyDown(keyCode, event);
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 保存对话框
//     *
//     * @param tag
//     */
//    private void showWindow(final int tag) {
//
////        int score = ftMusicScreen.stopExtAudioRecorder(0);
//        int score = ftMusicScreen.getAll_score();
//        try {
//            PreferUtil.getInstance().setHomeBack(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        myDialog = new ThreeButtonAlertDialog(this).builder();
//        myDialog.setTitle(getString(R.string.notice))
//                .setMsg(getString(R.string.total_score) + score + "! " + getString(R.string.stop_record_notice))
//                .setCancelable(true)
//                .setPositiveButton(getString(R.string.abandon_record), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ftMusicScreen.stopExtAudioRecorder(0);
//                        switch (tag) {
//                            case 0://音乐和录音停止,返回
//                                stop();
//                                finishActivity();
//                                break;
//                            case 1://音乐和录音停止,还原
//                                stop();
//                                deleteFiles();
////                                cameracSb.setProgress(0);
//                                break;
//                            case 2://音乐和录音停止,跳转到录MP3界面
//                                stop();
////                                intent = new Intent(OpenCameraActivity.this, RecordSongActivity.class);
//                                Bundle bundle1 = new Bundle();
//                                bundle1.putSerializable("songDetail", songDetail);
//                                intent.putExtras(bundle1);
//                                startActivity(intent);
//                                break;
//                            case 3://音乐和录音停止,跳转到练歌界面
//                                stop();
////                                intent = new Intent(OpenCameraActivity.this, PracticeSongActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("songDetail", songDetail);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                break;
//
//                            case 4:
//                                stop();
//                                deleteFiles();
//                                break;
//
//                        }
//
//                    }
//                }).setNeutralButton(getString(R.string.save_record), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endRecordTime = accPlayer.getCurrentPosition();
//                ftMusicScreen.stopExtAudioRecorder(0);
//                //录制时间不能少于15s
//                if (endRecordTime - startRecordTime < 15000) {
//                    toast(getString(R.string.open_camera_time));
//                    return;
//                }
//
//                //跳转到保存练歌页面
//                intent = new Intent(OpenCameraActivity2.this, SavePracticeActivity.class);
//                if (mHomeWatcher != null) {
//                    mHomeWatcher.stopWatch();
//                }
//                stop();//音乐和录音停止
//                releaseSoundEffect();
//                handler.sendEmptyMessage(44);
//            }
//        }).setNegativeButton(getString(R.string.continue_record), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtils.w("tag1", "11111111111111");
//                ftMusicScreen.setExtAudioRecorder(extAudioRecorder, 0);//获取振幅
//                if (tag == 4) {
//                    LogUtils.w("tag1", "22222222222");
//                    play();
//                    setNewData();
//                }
//            }
//        }).show();
//
//    }
//
//
//    @Override
//    public void permissionSuccess(int request_code_permission) {
//        super.permissionSuccess(request_code_permission);
//        switch (request_code_permission) {
//            case 0x0003:
//                startBtn();
//                break;
//            case 0x002:
//                dao = DownloadManager.getDownloadManager(this);
//                startDownload();
//                break;
//        }
//    }
//
//    private void startBtn() {
//        if (download.getProgress() == 100) {
//            //隐藏
//            download.setVisibility(View.INVISIBLE);
//            fragmentState.setVisibility(View.INVISIBLE);
//            fragmentText.setVisibility(View.INVISIBLE);
//            beautyFilter.setVisibility(View.INVISIBLE);
//            //显示
//            recordBottom.setVisibility(View.VISIBLE);
//            musicScreen.setVisibility(View.VISIBLE);
//            ftMusicScreen.stopExtAudioRecorder(0);
//            ftMusicScreen.setExtAudioRecorder(extAudioRecorder, 0);
//            //3s掉计时
//            showCountDown();
//            handler.sendEmptyMessageDelayed(22, 3000);
//        }
//    }
//
//
//    private AnimationDrawable drawable;
//
//    /**
//     * 开始3s倒计时
//     */
//    private void showCountDown() {
//        drawable = (AnimationDrawable) tvCountDown.getDrawable();
//        drawable.start();
//        tvCountDown.setVisibility(View.VISIBLE);
//        handler.sendEmptyMessageDelayed(111, 3000);
//    }
//
//    @Override
//    protected void onPause() {
//        //停止录音
////        pause();
//        unRegister();
//        //切换后台暂定停
//        if (accPlayer != null) {
//            accPlayer.pause();
//        }
////        stop();
//        pauseIcon.clearAnimation();
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        //释放资源
//        super.onDestroy();
//        if (mHomeWatcher != null) {
//            mHomeWatcher.stopWatch();
//        }
//        if (accPlayer != null) {
//            accPlayer.release();
//            accPlayer = null;
//        }
//        if (recorderManager != null) {
//            recorderManager.reset();
//            recorderManager = null;
//        }
//        releaseSoundEffect();
//        stopLight();
//        deleteFiles();
//        PowerManagerUtil.keepScreenOn(this, false);//保持屏幕不熄灭
//
//        //打开第三方app播放
//        clossOther(this, false);
//    }
//
//
//    /**
//     * 监控Home键
//     */
//    private void registerHomeListener() {
//        mHomeWatcher = new HomeListener(this);
//        mHomeWatcher.setOnHomePressedListener(new HomeListener.OnHomePressedListener() {
//
//            @Override
//            public void onHomePressed() {//短按
//                LogUtils.w("xsl", "0000000000000");
//                if (state != NOT_START) {
//                    pause();
//                    try {
//                        PreferUtil.getInstance().setHomeBack(true);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                //this.stopService(mIntentService);
//                if (tvCountDown.isShown()) {
//                    handler.removeMessages(22);
//                    handler.removeMessages(111);//移除三秒延时消息
//                    handler.sendEmptyMessage(111);
//                }
//            }
//
//            @Override
//            public void onHomeLongPressed() {
//                LogUtils.w("xsl", "0000000000000");
//
//            }
//        });
//        mHomeWatcher.startWatch();
//    }
//
//    private void registerReceive() {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
//        registerReceiver(mReceiver, intentFilter);
//    }
//
//    private void unRegister() {
//        if (mReceiver != null) {
//            unregisterReceiver(mReceiver);
//        }
//    }
//
//    private void setNewData() {
//        startRecordTime = 0;
//        try {
//            accPlayer.reset();
//            accPlayer.setDataSource(bzFile.toString());
//            accPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    if (musicTag == 0) {
//                        accPlayer.start();
//                        accPlayer.pause();
//                    } else {
//                        accPlayer.start();
//                        state = ISPLAYING;
//                        updateViews();
//                    }
//                }
//            });
//            accPlayer.prepare();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            handler.sendEmptyMessage(-222);
//        }
//    }
//
//    @Override
//    public void doSomething() {
//        recordFontLL.setVisibility(View.INVISIBLE);
//        recordFont.setImageResource(R.drawable.record_font_before);
//        if (!isshow) {
//            startTopRlHideAnimal();
//            isshow = true;
//            LogUtils.w("testa", "111111111111");
//        } else {
//            startTopRlShowAnimal();
//            isshow = false;
//            LogUtils.w("testa", "2222222222222");
//        }
//    }
//
//    //顶部显示动画
//    private void startTopRlShowAnimal() {
//        LogUtils.w("getHeight()", "" + -topRl.getHeight());
////        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -topRl.getHeight(), 0);
////        translateAnimation.setDuration(500);
////        translateAnimation.setFillAfter(true);
////        topRlAnimal.startAnimation(translateAnimation);
//
//        //用属性动画
////        topRlAnimal.getTranslationY()
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topRlAnimal, "translationY", -topRl.getHeight(), 0);
//        objectAnimator.setDuration(500);
//        objectAnimator.start();
//
//    }
//
//    private void startTopRlHideAnimal() {
////        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -topRl.getHeight());
////        translateAnimation.setDuration(500);
////        translateAnimation.setFillAfter(true);
////        topRlAnimal.startAnimation(translateAnimation);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topRlAnimal, "translationY", 0, -topRl.getHeight());
//        objectAnimator.setDuration(500);
//        objectAnimator.start();
//    }
//
//    /**
//     * 检测是否显示引导界面
//     */
//    public void checkGuide() {
////        LogUtils.sysout("9999999999999999999999999999999");
////        if (NetInterface.isDevelopment) {
////            SharedPrefManager.getInstance().cacheApiguide(null);//保存数据到本地
////        }
//        GuideItem mGuideItem = SharedPrefManager.getInstance().getCacheApiguide();
//        if (mGuideItem != null && mGuideItem.isLead9()) {//
//        } else {//未打开过：
////            showGuideView();
//            if (mGuideItem == null) {
//                mGuideItem = new GuideItem();
//            }
//            mGuideItem.setLead9(true);
//            SharedPrefManager.getInstance().cacheApiguide(mGuideItem);//保存数据到本地
//        }
//    }
//
//    Guide guide;
//
////    public void showGuideView() {
////        GuideBuilder builder = new GuideBuilder();
////        builder.setTargetView(threePonit)
////                .setTextContext("想立即唱歌点这里")
////                .setAlpha(150)
////                .setHighTargetCorner(20)
////                .setHighTargetPadding(10)
////                .setOverlayTarget(false)
////                .setOutsideTouchable(false);
////        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
////            @Override
////            public void onShown() {
////            }
////
////            @Override
////            public void onDismiss() {
////                try {
////                    showTipView(ivMode);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////
////        SimpleComponent mMutiComponent = new SimpleComponent(this, getString(R.string.tosing_more), true);
//////        mSimpleComponent.setContent("想立即唱歌点这里");
////        builder.addComponent(mMutiComponent);
////        guide = builder.createGuide();
////        guide.setShouldCheckLocInWindow(false);
////        guide.show(this);
////    }
//
//    private HighLight mHightLight;
//
//    public void showTipView(View view) {//ivMode
//        mHightLight = new HighLight(OpenCameraActivity2.this)//
//                .anchor(findViewById(R.id.all_rl))//如果是Activity上增加引导层，不需要设置anchor
////                .addHighLight(R.id.camera_three_point,R.layout.info_gravity_left_down,new OnLeftPosCallback(45),new RectLightShape())
//                .addHighLight(this, R.id.home_mode, R.layout.info_gravity_left_down, new OnTopPosCallback(), new CircleLightShape(), getString(R.string.tosing_mp3_camera))
//                .addHighLight(this, view, R.layout.info_gravity_left_down, new OnBottomPosCallback(60), new CircleLightShape(), "2222222222222222");
//        mHightLight.show();
//    }
////    public  void showTipView(View view){
////        mHightLight = new HighLight(MainActivity.this)//
////                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
////                .addHighLight(R.id.btn_rightLight,R.layout.info_gravity_left_down,new OnLeftPosCallback(45),new RectLightShape())
////                .addHighLight(R.id.btn_light,R.layout.info_gravity_left_down,new OnRightPosCallback(5),new CircleLightShape())
////                .addHighLight(R.id.btn_bottomLight,R.layout.info_gravity_left_down,new OnTopPosCallback(),new CircleLightShape())
////                .addHighLight(view,R.layout.info_gravity_left_down,new OnBottomPosCallback(60),new CircleLightShape());
////        mHightLight.show();
////    }
//
//
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
//                if (intent.hasExtra("state")) {
//                    if (intent.getIntExtra("state", 0) == 0) {
//                        if (extAudioRecorder != null) {
//                            hasHeadset = false;
//                            extAudioRecorder.setHasHeadSet(false);
////                            Toast.makeText(OpenCameraActivity.this, "没耳机", Toast.LENGTH_SHORT).show();
//                        }
//                        oratorioListen.setVisibility(View.INVISIBLE);
//                    } else if (intent.getIntExtra("state", 0) == 1) {
//                        if (extAudioRecorder != null) {
//                            hasHeadset = true;
//                            extAudioRecorder.setHasHeadSet(false);
////                            Toast.makeText(OpenCameraActivity.this, "有耳机", Toast.LENGTH_SHORT).show();
//                        }
//                        oratorioListen.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }
//    };
//
//    @Override
//    public void getData(int index) {
//
//    }
//
//    @Override
//    public void myClick() {
//        int select = fragmentState.getSelect();
//        LogUtils.w("select", "=========" + select);
//        if (select == AudioVideoToggle.SELECT_MV) {
//            fragmentState.setSelectAndInvalidate(AudioVideoToggle.SELECT_MP3);
//            PreferUtil.getInstance().putString("fragmentState", "mp3");
//            lrcViewAll.setVisibility(View.VISIBLE);
//            ivCamera.setVisibility(View.INVISIBLE);
//            videoSurface.setVisibility(View.INVISIBLE);
//            beautyFilter.setVisibility(View.INVISIBLE);
//            lrcView.setVisibility(View.INVISIBLE);
//            openCameaRl.setBackgroundResource(R.drawable.record_mp3_bg);
//        } else if (select == AudioVideoToggle.SELECT_MP3) {
//            fragmentState.setSelectAndInvalidate(AudioVideoToggle.SELECT_MV);
//            PreferUtil.getInstance().putString("fragmentState", "mv");
//            lrcViewAll.setVisibility(View.INVISIBLE);
//            ivCamera.setVisibility(View.VISIBLE);
//            videoSurface.setVisibility(View.VISIBLE);
//            beautyFilter.setVisibility(View.VISIBLE);
//            lrcView.setVisibility(View.VISIBLE);
//            openCameaRl.setBackgroundResource(0);
//        }
//    }
//}
