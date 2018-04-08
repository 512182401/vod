package com.changxiang.vod.module.ui.oratorio;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.utils.dialog.AlertDialog;
//import com.quchangkeji.tosing.common.view.SwitchView;
//import com.quchangkeji.tosing.common.view.ThreeButtonAlertDialog;
//import com.quchangkeji.tosing.module.db.SongDetail;
//import com.quchangkeji.tosing.module.ui.base.BaseActivity;
//import com.quchangkeji.tosing.module.ui.practicesinging.SavePracticeActivity;
//import com.quchangkeji.tosing.module.ui.recordermusic.AudioRecorder;
//import com.quchangkeji.tosing.module.util.MyFileUtil;
//import com.quchangkeji.tosing.module.util.PreferUtil;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.utils.PreferUtil;
import com.changxiang.vod.common.utils.dialog.AlertDialog;
import com.changxiang.vod.common.view.SwitchView;
import com.changxiang.vod.common.view.ThreeButtonAlertDialog;
import com.changxiang.vod.module.entry.SongDetail;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.recordmusic.recordutils.AudioRecorder;
import com.changxiang.vod.module.ui.saveutils.SavePracticeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OratorioActivity extends BaseActivity implements View.OnClickListener, SoundEffecView.MyListener {

    private ImageView ivClose;
    private TextView tvSongName;
    private TextView tvPromptBegin;//提示语句
    private ImageView ivMusic;//音效
    private SoundEffecView musicRl;
    private boolean isMusicRlVisiable = true;
    private ImageView ivStart;

    private int startState = 0;//0 : 开始录歌  1:继续录歌  2:暂停录歌
    private ImageView ivEnd;//结束按钮
    private boolean isEnd = true;//是否结束
    private ImageView ivScreen;

    //    bufferSizeInBytes //缓冲区
    private static final int SAMPLE_RATE = 44100; //采样率(CD音质)
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO; //音频通道(单声道)
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT; //音频格式
    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;  //音频源（麦克风）
    private static boolean is_recording = false;
    private TextView tvPromptEnd;
    private ImageView ivArrow;
    private RelativeLayout showTimeRl;
    //    private ImageView ivSingPic;
    private ImageView ivTwinkle;//闪烁效果
    private final int TIMEPIECE = 0;//计时器
    private TextView tvTimepiece;

    private int limitTime = 600;

    private int time = 0;
    private RelativeLayout showeBeginRl;
    private ImageView countDownIv;
    private AnimationDrawable animationDrawable;
    private AnimationDrawable drawable;
    //    private ObjectAnimator rotateAnimation;
    private ObjectAnimator alphaAnimation;
    //    private MyAnimatorUpdateListener rotateUpdateListener;
    private MyAnimatorUpdateListener alphaUpdateListener;
    private LinearLayout oratorioLl;
    private AnimationDrawable oratorioDrawable;

    private int startReordTime;//开始录制的时间
    private int endRecordTime;//结束录制的时间
    private int total;//总时长
    private Intent intent;
    private ImageView oratorioListen;
    private SwitchView switchBtn;
    private RelativeLayout oratorioRoot;
    private PopupWindow mPopWnd;
    private ObjectAnimator objAnim;
    private Float currentValue;

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case TIMEPIECE:
                //计时器
                runTimepiece();
                break;
            case 111:
                if (drawable != null) {
                    drawable.stop();
                }
                countDownIv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设定为竖屏
        setContentView(R.layout.activity_oratorio);
        initView();
        initEvent();
        initData();
        sentStopBroad();

        //注册广播  是否插入耳机
        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(mReceiver, intentFilter);
    }


    private void initView() {
        ivClose = (ImageView) findViewById(R.id.oratorio_iv_close);
        ivMusic = (ImageView) findViewById(R.id.oratorio_music);
        ivStart = (ImageView) findViewById(R.id.oratorio_start_iv);
        ivEnd = (ImageView) findViewById(R.id.oratorio_end_iv);
        ivArrow = (ImageView) findViewById(R.id.oratorio_arrow);
        ivScreen = (ImageView) findViewById(R.id.oratorio_grade_screen);
        countDownIv = (ImageView) findViewById(R.id.oratorio_count_down_tv);
//        ivSingPic = (ImageView) findViewById(R.id.oratorio_sing_picture);
        ivTwinkle = (ImageView) findViewById(R.id.oratorio_red_twinkle);
        tvSongName = (TextView) findViewById(R.id.oratorio_song_name_tv);
        tvTimepiece = (TextView) findViewById(R.id.oratorio_timepiece);
        tvPromptBegin = (TextView) findViewById(R.id.oratorio_begin_tv);
        tvPromptEnd = (TextView) findViewById(R.id.oratorio_end_tv);
        musicRl = (SoundEffecView) findViewById(R.id.oratorio_sound_effect_rl);//音效布局
        showTimeRl = (RelativeLayout) findViewById(R.id.oratorio_rl_show);
        showeBeginRl = (RelativeLayout) findViewById(R.id.oratorio_begin_rl);
        oratorioLl = (LinearLayout) findViewById(R.id.oratorio_ll);
        oratorioListen = (ImageView) findViewById(R.id.oratorio_listen);//耳返
        oratorioRoot = (RelativeLayout) findViewById(R.id.oratorio);
    }

    private final String cacheDir = MyFileUtil.DIR_RECORDER.toString() + File.separator;//
    private AudioRecorder audioRecorder;
    private File finalFile;//录音路径地址

    private void initData() {
        String times = System.currentTimeMillis() + "";
        finalFile = new File(cacheDir + times + "source.wav");//
        //音效初始化
        short s = 0;
        setSoundEffect(s);
    }


    private void initEvent() {
        ivClose.setOnClickListener(this);
        ivMusic.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        ivEnd.setOnClickListener(this);
        ivScreen.setOnClickListener(this);
        oratorioListen.setOnClickListener(this);

        musicRl.setOnListener(this);
    }

    //发送停止广播
    private void sentStopBroad() {
        Intent stopIntent = new Intent();
        stopIntent.setAction("ACTION_STOP");
        sendBroadcast(stopIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oratorio_iv_close:
                //关闭界面
                if (startState != 0) {
                    //弹出对话框
                    showExitDialog();
                } else {
                    finish();
                }

                break;
            case R.id.oratorio_music:
//                Toast.makeText(this, "音效", Toast.LENGTH_SHORT).show();
                if (is_recording) {
                    ivStart.setImageResource(R.drawable.oratorio_continue);
                    startState = 1;
                    //暂停动画和计时器
                    handler.removeMessages(TIMEPIECE);
                    if (alphaUpdateListener != null) {
                        alphaUpdateListener.pause();
                        alphaUpdateListener.isPaused = false;
                    }
                    //暂停录音
                    audioRecorder.pauseRecord();
                }
                //音效
                if (isMusicRlVisiable) {
                    ivMusic.setImageResource(R.drawable.oratorio_music_normal);
                    musicRl.setVisibility(View.INVISIBLE);
                    isMusicRlVisiable = false;
                } else {
                    ivMusic.setImageResource(R.drawable.oratorio_music_press);
                    musicRl.setVisibility(View.VISIBLE);
                    isMusicRlVisiable = true;
                }

                break;
            case R.id.oratorio_start_iv:
                //录制按钮  0 : 开始录歌  1:继续录歌  2:暂停录歌
//                Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
                ivEnd.setImageResource(R.drawable.oratorio_end_red);
                if (startState == 0) {
                    //申请读写SD卡的权限和录音权限
                    requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 0x0003);
                    //开始录歌,点击变成暂停录歌；隐藏提示语和手势；显示计时器和光盘动画;音效消失
                    ivStart.setImageResource(R.drawable.oratorio_pause);
                    changeUI();
                } else if (startState == 1) {
                    //继续录歌状态
                    audioRecorder.startRecord();
                    startState = 2;
                    ivStart.setImageResource(R.drawable.oratorio_pause);
                    if (alphaUpdateListener.isPause()) {
                        alphaUpdateListener.play();
                        handler.sendEmptyMessage(TIMEPIECE);
                    }

                    //恢复录音
//                    audioRecorder.startRecord();

                } else if (startState == 2) {
                    //暂停录歌状态
                    audioRecorder.pauseRecord();
                    startState = 1;
                    ivStart.setImageResource(R.drawable.oratorio_continue);
                    //暂停计时器和光盘动画
                    handler.removeMessages(TIMEPIECE);
//                    rotateUpdateListener.pause();
                    alphaUpdateListener.pause();
                }
                break;
            case R.id.oratorio_end_iv://结束按钮
//                Toast.makeText(this, "结束", Toast.LENGTH_SHORT).show();
                if (is_recording) {
                    //弹出dialog
                    showThreeDialog();
                }
                break;
            case R.id.oratorio_grade_screen:
                //录制视屏按钮
                if (is_recording) {
                    showRecordDialog();
                } else {
                    //防止暴力点击
                    if (countDownIv.getVisibility() == View.VISIBLE) {
                        if (drawable != null) {
                            drawable.stop();
                        }
                        countDownIv.setVisibility(View.GONE);
                    }
                    startActivity(new Intent(this, CameraOratorioActivity.class));
                }
                break;
            case R.id.oratorio_listen:
                //耳返
                earListenBack();
                break;
        }
    }

    //耳返开关
    private void earListenBack() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_listen, null, false);
        contentView.setClickable(true);
        switchBtn = (SwitchView) contentView.findViewById(R.id.pop_listen_switch);
        switchBtn.setOpened(PreferUtil.getInstance().getBoolean("earback", false));
        audioRecorder.setHasHeadSet(PreferUtil.getInstance().getBoolean("earback", false));
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
                audioRecorder.setHasHeadSet(true);
                //sp保存状态
                PreferUtil.getInstance().putBoolean("earback", true);
            }

            @Override
            public void toggleToOff(View view) {
//                Toast.makeText(OratorioActivity.this, "222222222", Toast.LENGTH_SHORT).show();
                switchBtn.setOpened(false);
                hasHeadset = false;
                audioRecorder.setHasHeadSet(false);
                PreferUtil.getInstance().putBoolean("earback", false);
            }
        });
        //将PopupWindow显示在oratorioListen的下方
        contentView.measure(0, 0);
        int measuredWidth = contentView.getMeasuredWidth();
        mPopWnd.showAsDropDown(oratorioListen, -measuredWidth + 60, 40);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //清除动画，消息
        handler.removeMessages(TIMEPIECE);
//        ivSingPic.setAnimation(null);
        ivTwinkle.setAnimation(null);
        releaseSoundEffect();

        if (audioRecorder != null) {
            audioRecorder.stopRecord();
            audioRecorder = null;
        }

        //隐藏计时器
        showTimeRl.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        File recordFile = new File(cacheDir + "source.pcm");
        if (audioRecorder == null) {
            audioRecorder = new AudioRecorder();
        }
        audioRecorder.createDefaultAudio(this, recordFile.toString(), finalFile.toString());
        resetUI();
    }

    private void resetUI() {
        musicRl.setVisibility(View.VISIBLE);
        tvPromptBegin.setVisibility(View.VISIBLE);
        tvPromptEnd.setVisibility(View.VISIBLE);
        ivArrow.setVisibility(View.VISIBLE);
        ivStart.setImageResource(R.drawable.home_start_record);
        ivEnd.setImageResource(R.drawable.oratorio_end_white);
        is_recording = false;
        startState = 0;
        time = 0;
    }


    /**
     * 录制按钮的dialog
     */
    private void showRecordDialog() {
        final AlertDialog alertDialog = new AlertDialog(this).builder();
        alertDialog.setMsg("切换到MV录制模式，已录制的音频部分将不会保留，确认切换？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至视频清唱界面
                if (alphaUpdateListener != null) {//结束动画
                    alphaUpdateListener.play();
                    alphaAnimation.end();
                    alphaUpdateListener = null;
                    ivTwinkle.clearAnimation();
                }
                Intent intent = new Intent(OratorioActivity.this, CameraOratorioActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏弹层继续录歌
            }
        });
        alertDialog.show();
    }

    //弹出dialog
    private void showThreeDialog() {
        final ThreeButtonAlertDialog myDialog = new ThreeButtonAlertDialog(this).builder();
        myDialog.setTitle("温馨提示");
        myDialog.setMsg("确定已经完成当前清唱录歌吗？");
        myDialog.setCancelable(true);
        myDialog.setPositiveButton("重新录制", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新录制:不做数据保存直接使当前页面复位到初始状态；
                resetUI();
                reInit();
                //重启录音
                audioRecorder.stopRecord();
                audioRecorder.startRecord();
            }
        });
        myDialog.setNeutralButton("保存录制", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecorder.stopRecord();
                releaseSoundEffect();
                if (alphaUpdateListener != null) {
                    alphaUpdateListener.play();
                    alphaAnimation.end();
                    ivTwinkle.clearAnimation();
                    alphaUpdateListener = null;
                }
                //跳转到保存界面
                intent = new Intent(OratorioActivity.this, SavePracticeActivity.class);
                endRecordTime = total = time;
                parameterPass(finalFile.toString(), startReordTime, endRecordTime * 1000);//传递参数deoFileName(), recordAudioPath, startRecordTime, endRecordTime);
                startActivity(intent);
                finishActivity();
            }
        });
        myDialog.setNegativeButton("继续录制", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myDialog.show();

    }

    private SongDetail songDetail;//歌曲对象

    private void parameterPass(String file, int startReordTime, int endRecordTime) {
        //假数据
        songDetail = new SongDetail();
        songDetail.setSongId("acappella");
        songDetail.setType("audio");
        songDetail.setSingerName("清唱");
        songDetail.setSongName("清唱");
        songDetail.setImgAlbumUrl(null);
        songDetail.setAccPath(file);

        Bundle bundle = new Bundle();
        bundle.putSerializable("songDetail", songDetail);
        intent.putExtras(bundle);
        intent.putExtra("recordUrl", file);
        intent.putExtra("videoHeight", 0);
        intent.putExtra("recordType", "audio");//录制的类型
        intent.putExtra("musicType", "audio");//录制的类型
        intent.putExtra("startReordTime", startReordTime);//
        intent.putExtra("endReordTime", endRecordTime);//
        intent.putExtra("TotalTime", total * 1000);//
        intent.putExtra("oratorio", 0);//音频  0:音频  1：视屏
    }

    //恢复初始状态
    private void reInit() {
        handler.removeMessages(TIMEPIECE);
        time = 0;
        showTimeRl.setVisibility(View.INVISIBLE);
    }


    /**
     * 隐藏提示语和手势；显示计时器和光盘动画；音效消失
     */
    private void changeUI() {
        tvPromptBegin.setVisibility(View.INVISIBLE);
        tvPromptEnd.setVisibility(View.INVISIBLE);
        ivArrow.setVisibility(View.INVISIBLE);

        // 3秒倒计时
        showCountDown();
        //倒计时结束后
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showTimeRl.setVisibility(View.VISIBLE);
                //开始动画和计时
                startAnimation();
                showOratorioAnimal();
                handler.sendEmptyMessage(TIMEPIECE);
                //开始录音
                if (audioRecorder != null) {
                    audioRecorder.startRecord();
                }
                is_recording = true;
                startState = 2;
                //音效消失
                musicRl.setVisibility(View.INVISIBLE);
                isMusicRlVisiable = false;
            }
        }, 3000);

    }

    /**
     * 计时器
     */
    private void runTimepiece() {
        if (time <= limitTime) {
            time++;
        }
        if (time > limitTime) {
            audioRecorder.stopRecord();
            //录制完成，跳到保存界面
//            showThreeDialog();
            releaseSoundEffect();
            intent = new Intent(OratorioActivity.this, SavePracticeActivity.class);
            endRecordTime = total = time - 1;
            parameterPass(finalFile.toString(), startReordTime, endRecordTime * 1000);//传递参数deoFileName(), recordAudioPath, startRecordTime, endRecordTime);
            startActivity(intent);
            finishActivity();
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

    /**
     * 开始动画,属性动画
     */
    private void startAnimation() {
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

    /**
     * 退出录制对话框
     */
    private void showExitDialog() {
        final AlertDialog alertDialog = new AlertDialog(this).builder();
        alertDialog.setMsg("您录制的歌曲还没有保存，确定要放弃吗？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭当前页,关闭动画
                if (alphaUpdateListener != null) {
                    alphaUpdateListener.play();
                    alphaAnimation.end();
                    ivTwinkle.clearAnimation();
                    alphaUpdateListener = null;
                }
                finish();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //继续录制清唱
            }
        });
        alertDialog.show();
    }

    //启动3s倒计时
    private void showCountDown() {
        drawable = (AnimationDrawable) countDownIv.getDrawable();
        drawable.start();
        countDownIv.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(111, 3000);
    }


    /**
     * 初始化预设音场控制器
     */
    // 定义系统的预设音场控制器
    private PresetReverb mPresetReverb;
    private List<Short> reverbNames = new ArrayList<>();
    Equalizer mEqualizer;

    private void setupPresetReverb() {

        int audioSessionId = audioRecorder.getAudioTrack().getAudioSessionId();
        mPresetReverb = new PresetReverb(0, audioSessionId);
        // 设置启用预设音场控制
        mPresetReverb.setEnabled(true);

        // 定义系统的均衡器,相当于设置Equalizer负责控制该MediaPlayer
        mEqualizer = new Equalizer(0, audioSessionId);
        // 启用均衡控制效果
        mEqualizer.setEnabled(true);
        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
            reverbNames.add(i);
//            reverbVals.add(mEqualizer.getPresetName(i));
        }

    }

    private void releaseSoundEffect() {
        if (mPresetReverb != null) {
            mPresetReverb.release();
            mPresetReverb = null;
        }
        if (mEqualizer != null) {
            mEqualizer.release();
            mEqualizer = null;
        }
    }

    //设置音效
    private void setSoundEffect(short num) {
        try {
            mPresetReverb.setPreset(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //启动3s倒计时
    private void showOratorioAnimal() {
        oratorioDrawable = (AnimationDrawable) oratorioLl.getBackground();
        oratorioDrawable.start();
//        countDownIv.setVisibility(View.VISIBLE);
//        handler.sendEmptyMessageDelayed(111, 3000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除动画，消息
        handler.removeMessages(TIMEPIECE);
//        ivSingPic.setAnimation(null);
        ivTwinkle.setAnimation(null);
        releaseSoundEffect();
        musicRl = null;
        unregisterReceiver(mReceiver);
        try {
            finishActivityByClz(OratorioActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取点击SoundEffecView返回的位子结果
     *
     * @param index
     */
    @Override
    public void getData(int index) {
        LogUtils.w("index", "index====" + index);
        setSoundEffect((short) index);
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
                            return fraction;
                        }
                    });
                    isPaused = true;
                }
                //每隔动画播放的时间，我们都会将播放时间往回调整，以便重新播放的时候接着使用这个时间,同时也为了让整个动画不结束
                new CountDownTimer(ValueAnimator.getFrameDelay(), ValueAnimator.getFrameDelay()) {

                    @Override
                    public void onTick(long millisUntilFinished) {
//                        LogUtils.w("timer", "zzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                    }

                    @Override
                    public void onFinish() {
//                        LogUtils.w("timer", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
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
     * 开始动画
     */
    @SuppressLint("NewApi")
    public void startAnimation1() {
        // 设置动画，从上次停止位置开始,这里是顺时针旋转360度
        objAnim = ObjectAnimator.ofFloat(ivTwinkle, "alpha", 1.0f, 0.0f);
        // 设置持续时间
        objAnim.setDuration(1000);
        // 设置循环播放
        objAnim.setRepeatCount(ObjectAnimator.INFINITE);
        // 设置动画监听
        objAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                // 监听动画执行的位置，以便下次开始时，从当前位置开始
                currentValue = (Float) animation.getAnimatedValue();
            }
        });
        objAnim.start();
//        ivTwinkle.startAnimation(animation2);
    }

    private boolean hasHeadset = false;//是否插入耳机

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        if (audioRecorder != null) {
                            hasHeadset = false;
                            audioRecorder.setHasHeadSet(false);
                            LogUtils.w("PreferUtil", "PreferUtil.getInstance()==" + PreferUtil.getInstance());
                            PreferUtil.getInstance().putBoolean("earback", false);
//                            Toast.makeText(OratorioActivity.this, "没耳机", Toast.LENGTH_SHORT).show();
                        }
                        oratorioListen.setVisibility(View.INVISIBLE);
                        if (mPopWnd != null && mPopWnd.isShowing()) {
                            mPopWnd.dismiss();
                        }
                    } else if (intent.getIntExtra("state", 0) == 1) {
                        if (audioRecorder != null) {
                            hasHeadset = true;
                            audioRecorder.setHasHeadSet(false);
                            if (PreferUtil.getInstance() != null) {
                                PreferUtil.getInstance().putBoolean("earback", false);
                            }
//                            Toast.makeText(OratorioActivity.this, "有耳机", Toast.LENGTH_SHORT).show();
                        }
                        oratorioListen.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //关闭界面
            if (startState != 0) {
                //弹出对话框
                showExitDialog();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
