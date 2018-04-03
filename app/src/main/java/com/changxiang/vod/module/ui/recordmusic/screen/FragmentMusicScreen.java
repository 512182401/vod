//package com.changxiang.vod.module.ui.recordmusic.screen;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.AccelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.animation.TranslateAnimation;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.plattysoft.leonids.ParticleSystem;
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.common.utils.krc.Krcparser;
//import com.quchangkeji.tosingpk.module.base.BaseFragment;
//import com.quchangkeji.tosingpk.module.entry.Krc;
//import com.quchangkeji.tosingpk.module.entry.KrcLine;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.AudioRecorder;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.ExtAudioRecorder;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.RecorderManager;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * 1:传递krc文件 setKrcfile
// * 2:传递播放进度 setKrcProgress
// * 3:传递录音 ExtAudioRecorder
// * 4:传递播放的fft
// * Created by 15976 on 2016/10/6.
// */
//@SuppressLint("ValidFragment")
//public class FragmentMusicScreen extends BaseFragment implements View.OnClickListener {
//    private ExtAudioRecorder extAudioRecorder = null;//录音
//    private AudioRecorder audioRecorder = null;//录音
//    private RecorderManager mMediaRecorder = null;//录音
//    private Krc mKrc;//krc实体
//    private RelativeLayout activity_main;
//    //    private LinearLayout allcontentview;
//    private SoundMeter mSoundMeter;
//    private PathView all_two;
//    //    PowerManager.WakeLock mWakeLock;
//    private TextView score;//总得分
//    private TextView score_numb;//得分上升 tv_score_numb
//    private Animation scale_animation;
//    private int all_score = 0;//总的得分
//    private int all_scoreold = 0;//总的得分
//    private int item_score = 0;//每次得分
//
//    private int countTimes = 0;//计时器
//    private int progress = 0;//计时器
//
//    //    int progressold = -1;
//    //定义相关开关
//    boolean recordswitch = false;//是否录音开关：ExtAudioRecorder是否为null，以及录音数据是否能取到；
//    private boolean startswitch = false;//是否开始唱，即开始计算平均值；
//    private boolean stopswitch = false;//是否结束录音，即开始计算总分；
//    private int startprogress = -1;//开始录音进度
//    private int stopprogress = -1;//结束录音进度
//    private float tempValue = 0;//krc的平均值
//    private float recordValue = 0;//录音的平均值
//
////    private Context context;
////    boolean isOpenRecorder = true;
//
//
//    public FragmentMusicScreen(Context context) {
////        this.context = context;
//    }
//
//    public FragmentMusicScreen() {
//    }
//
////    private final Handler mHandler = new Handler() {
////        @Override
////        public void handleMessage(Message msg) {
////            // TODO Auto-generated method stub
//////            updateMicStatus();
////            switch (msg.what) {
////                case 0:
////                    break;
////                case 1:
////                    toGetScore();//自动触发分数增加和烟花；
////                    fireWorksShow();//火花展示
////                    break;
////                case 2:
////
////                    break;
////            }
////
////        }
////    };
//
//    @Override
//    public void handMsg(Message msg) {
//        switch (msg.what) {
//            case 0:
//                break;
//            case 1:
////                LogUtils.sysout("000000000000000000000");
//                toGetScore();//自动触发分数增加和烟花；
//                fireWorksShow();//火花展示
//                break;
//            case 2:
//
//                break;
//        }
//    }
//
//    private boolean isfireWorksShow = true;
//
//    public void setFireWorksShow(boolean isfireWorksShow) {
//        this.isfireWorksShow = isfireWorksShow;
//
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_music_screen;
//    }
//
//    @Override
//    protected void initViews() {
//        initView();
//        initAnimation();
//        timeToDo();//定时器
//    }
//
//    @Override
//    protected void initEvents() {
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    /**
//     * 外部调用说明：
//     * 1：点击评分打开音乐屏的时候，调用 setKrcfile ，设置KRC(下载完成之后，)
//     * 2:
//     */
//
//    //结束
//    public void setinitMusicScreen() {
//        baseCurrentLine = -1;
//        valueCurrentLine = 0;
//        setKrcProgress(0);
//        //清除掉音调
//        if (mKrc != null) {
//            all_two.setKrcData(mKrc);
//        }
//        this.extAudioRecorder = null;
//        this.mMediaRecorder = null;
//
//        all_score = 0;
//        item_score = 0;
//        all_scoreold = 0;
//        score.setText(getString(R.string.total_score1) + all_score);
//        setKrcProgress(0);
//    }
//
//    //重置
//    public void setResetMusicScreen() {
//        baseCurrentLine = -1;
//        valueCurrentLine = 0;
//        setKrcProgress(0);
//        //清除掉音调
//        all_two.setKrcData(null);
//        this.extAudioRecorder = null;
//        this.mMediaRecorder = null;
//
//        item_score = 0;
//        all_score = 0;
//        all_scoreold = 0;
//        try {
//            score.setText(getString(R.string.total_score1) + all_score);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        setKrcProgress(0);
//    }
//
//    /**
//     * 歌曲暂停
//     */
//    public void stopRecorder() {
//        stopswitch = true;//未停止录音
//    }
//
//    /**
//     * 歌曲继续播放
//     */
//    public void restartRecorder() {
//        stopswitch = false;//未停止录音
//    }
//
//    /**
//     * 传递录音,(开始录音)
//     * ExtAudioRecorder
//     */
//    public void setExtAudioRecorder(ExtAudioRecorder extAudioRecorder, int progress) {//,int progress
//
//        this.extAudioRecorder = extAudioRecorder;
//        startprogress = progress;
//        startswitch = true;//开始录音
//        stopswitch = false;//未停止录音
//    }
//
//    /**
//     *
//     * @param audioRecorder
//     * @param progress
//     */
//    public void setExtAudioRecorder1(AudioRecorder audioRecorder, int progress) {//,int progress
//
//        this.audioRecorder = audioRecorder;
//        startprogress = progress;
//        startswitch = true;//开始录音
//        stopswitch = false;//未停止录音
//    }
//
//    public void setExtAudioRecorder2(RecorderManager mMediaRecorder, int progress) {//,int progress
//        this.mMediaRecorder = mMediaRecorder;
//        startprogress = progress;
//        startswitch = true;//开始录音
//        stopswitch = false;//未停止录音
//    }
//
//    /**
//     * 停止录音(录完一首歌)
//     * ExtAudioRecorder
//     */
//    public int stopExtAudioRecorder(int progress) {
//        this.extAudioRecorder = null;
//        this.mMediaRecorder = null;
//        stopprogress = progress;//添加提前半秒
//        startswitch = false;
//        stopswitch = true;
//
////        统计分数
//        return all_score == 0 ? all_scoreold : all_score;//返回总分
//
//    }
//
//    /**
//     * 单纯获取分数：
//     *
//     * @return
//     */
//    public int getAll_score() {
//
////        统计分数
//        return all_score == 0 ? all_scoreold : all_score;//返回总分
//
//    }
//
//
//    //获取当前行的分数
//    public int getTheScore() {
//
//        return item_score;
//    }
//
//    /**
//     * 外部调用，通过播放进度，将进度传递给 PathView
//     */
//    int times = 0;
//
//    //进度
//    public void setKrcProgress(int progress) {
//        int maxAmplitude = 0;
//        if (!stopswitch) {//未停止录音
//            if (extAudioRecorder != null) {
//                try {
//                    maxAmplitude = extAudioRecorder.getMaxAmplitude();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (maxAmplitude == 0) {
//                    return;
//                } else {
//                    drawDB(maxAmplitude);//分贝
//                }
//
////        drawold(maxAmplitude);
//            } else if (mMediaRecorder != null) {
//                try {
//                    maxAmplitude = mMediaRecorder.getMaxAmplitude();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                drawDB(maxAmplitude);//分贝
//            }else if (audioRecorder!=null){
//                try {
//                    maxAmplitude = audioRecorder.getMaxAmplitude();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
////                LogUtils.sysout("9999999999999999:maxAmplitude:" + maxAmplitude);
//                drawDB(maxAmplitude);//分贝
//            }
////            LogUtils.w("Taa","maxAmplitude:"+maxAmplitude);
//        }
//        setCountTimesNow(progress);//当前进度，用于烟花和打分：
//        try {
//            if (all_two == null) {
////                toast( "all_two为null" );
//                LogUtils.sysout("PathView控件all_two：" + all_two);
//                all_two = (PathView) root.findViewById(R.id.rl_all_two);//音调控件
//            }
//            all_two.setCountTimesNow(progress);
//            this.progress = progress;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 外部调用，设置取得krc文件，并解析传递给 PathView
//     *
//     * @param
//     */
//    public void setKrcfile(String krcpath) {//KRC文件
////        String krcpath1 = krcpath;
////        this.krcpath = krcPath_tosing;
//        LogUtils.sysout("取得的KRC文件：" + krcpath);
//        if (krcpath != null && !krcpath.equals("")) {//判断krc是否存在
//            //解析KRC文件
//            mKrc = Krcparser.getPathData(krcpath);
//            if (mKrc != null) {
//                //传递krc到 PathView
//                try {
//                    if (all_two == null) {
//                        LogUtils.sysout("准备传递KRC资源到控件：all_two：" + all_two);
//                        initView();
//                    }
//                    all_two.setKrcData(mKrc);
//                    LogUtils.sysout("KRC解析正确，并传递");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                LogUtils.sysout("KRC解析不正确！");
//            }
//
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_score:
////                getMediaRecorderData();
//                break;
//        }
//    }
//
//    private void initView() {
//        activity_main = (RelativeLayout) root.findViewById(R.id.rl_fg_musicscreen);
////        allcontentview = (LinearLayout)root. findViewById(R.id.rl_all);
//        all_two = (PathView) root.findViewById(R.id.rl_all_two);//音调控件
////        show_firework = (Button) root.findViewById(R.id.bt_show_firework);//show_firework;//bt_show_firework
//        score = (TextView) root.findViewById(R.id.tv_score);    //获取要应用动画效果的View  缩放
//        score_numb = (TextView) root.findViewById(R.id.tv_score_numb);    //获取要应用动画效果的View  score_numb;//得分上升 tv_score_numb
//        score_numb.setText("+" + item_score);
////        show_firework.setOnClickListener(this);
//        score.setText(getString(R.string.total_score1) + all_score);
//        score.setOnClickListener(this);
//
////        PowerManager pm = (PowerManager) getActivity().getSystemService(getActivity().POWER_SERVICE);
////        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
////                "SoundRecorder");
//        mSoundMeter = (SoundMeter) root.findViewById(R.id.rl_all_one);//录音控件
//
//    }
//
//    //定时执行任务：
//    private void timeToDo() {
//        //开启画笔线程每0.2绘制一次
//        Timer sfvtimer = new Timer();
//
//        sfvtimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                countTimes += 50;
//                if (countTimes > 1000000) {
//                    countTimes = 0;
//                }
//                if (countTimes % 800 == 0) {//每800毫秒自动增加一个分数；
//                    Message message = new Message();
//                    message.what = 2;
//                    handler.sendMessage(message);
//
//
//                }
////                Log.e("countTimes99999999999", "countTimes=" + countTimes);
//            }
//        }, 0, 50);
//    }
//
//    private void initAnimation() {
//        scale_animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_scale);    //获取“缩放”动画资源
//
////分数上升动画：
//        TranslateAnimation animation_fly = new TranslateAnimation(0, 0, 300, 0);
//        animation_fly.setDuration(1000);//设置动画持续时间
////        animation_fly.setInterpolator(new OvershootInterpolator(1.5f));
//
//        //上升动画监听：
//        animation_fly.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                score.startAnimation(scale_animation);//缩放
//                score_numb.setVisibility(View.INVISIBLE);
//                score_numb.setVisibility(View.GONE);
//                try {
//                    all_score += item_score;
//                    LogUtils.sysout("一次累计加分：" + all_score);
//                } catch (Exception e) {
//                    Log.e("Exception:", "分数增加不成功");
//                }
////                if (all_score > 100) {
////                    all_score -= item_score;
////                }
//                score.setText(getString(R.string.total_score1) + all_score);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//    }
//
//    private int scorenow = 0;//上升动画的分数
//
//    //触发打分和烟花效果：
//    private void toGetScore() {
//
//        if (stopswitch) {
//            return;
//        }
//        if (getActivity() == null) {
//            return;
//        }
////        fireworkView.showFireWork( fireworkView.getWidth() / 2, fireworkView.getHeight() / 2 );//烟花位置
//
//        final TextView itemTextView = new TextView(getActivity());///新建一个TextView控件，水平显示
//        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
////        Params.setMargins(px2dip(context,50),px2dip(context,10),0,0);
//        Params.setMargins(120, 20, 0, 0);
//        itemTextView.setLayoutParams(Params);
//        itemTextView.setTextColor(0xffff0000);
//        itemTextView.setText("+" + item_score);
//        itemTextView.invalidate();
//        if (item_score <= 0) {
//            return;
//        }
////addview
//        activity_main.addView(itemTextView);
//        itemTextView.setVisibility(View.VISIBLE);
//        //分数上升动画：
//        LogUtils.sysout("上升空间加分=" + itemTextView.getText().toString());
//        scorenow = Integer.parseInt(itemTextView.getText().toString().substring(1));
//        LogUtils.sysout("*******" + scorenow);
//        all_scoreold += scorenow;
//        TranslateAnimation fly = new TranslateAnimation(0, 0, 300, 0);
//        fly.setDuration(1000);//设置动画持续时间
////        fly.setInterpolator(new OvershootInterpolator(1.5f));
//
//        itemTextView.startAnimation(fly);
//        fly.startNow();
//
//        //上升动画监听：
//        fly.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                score.startAnimation(scale_animation);//缩放
//                itemTextView.setVisibility(View.INVISIBLE);
////                score_numb.startAnimation(alpha);
//
////                score_numb.setVisibility(View.GONE);
//                try {
////                    all_score += item_score;
//                    all_score += scorenow;
//
//                    LogUtils.sysout("二次累计加分：" + all_score);
//                } catch (Exception e) {
//                    Log.e("Exception:", "分数增加不成功");
//                }
////                if (all_score > 100) {
////                    all_score -= item_score;
////                }
//                score.setText(getString(R.string.total_score1) + all_score);
//                LogUtils.sysout("总分：" + all_score);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
//
//    /**
//     * 隐藏或者显示音乐屏
//     * @param hide 为TRUE表示隐藏，FALSE表示显示
//     */
//    public void hideRootView(boolean hide){
//        if (hide){
//            activity_main.setVisibility(View.GONE);
//        }else {
//            activity_main.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//    /**
//     * 火花效果：
//     */
//    private void fireWorksShow() {
//        if (isfireWorksShow) {
//            try {
//                //第一个参数是activity，第二个是最多的粒子数，第三个是粒子的图片资源，第四个是持续时间，毫秒制，默认持续时间结束后会从开始重复。
//                ParticleSystem ps = new ParticleSystem(getActivity(), 1000, R.mipmap.fireworks, 800);
//                ps.setAcceleration(0.00013f, 240);
//                ps.setScaleRange(0.7f, 1.3f);//缩放：
//                ps.setSpeedRange(0.05f, 0.25f);//setSpeedRange 发散，最小发散范围，最大发散范围：
//                ps.setRotationSpeedRange(90, 180);//setRotationSpeedRange(float minRotationSpeed, float maxRotationSpeed)：粒子运动的最小和最大的旋转角度
//                ps.setFadeOut(200, new AccelerateInterpolator());//setFadeOut(long duration)：淡出的区间，默认从不透明到完全透明
////            ps.addModifier(new ScaleModifier(0.5f, 1.0f, 0, 400));//大小改变 第一个表示初始的大小，第二个参数表示，最终的大小（倍数），持续时间
//                ps.oneShot(all_two, 300);
//
////        ParticleSystem ps2 = new ParticleSystem(getActivity(), 100, R.drawable.star_pink, 800);
////        ps2.setScaleRange(0.7f, 1.3f);
////        ps2.setSpeedRange(0.1f, 0.25f);
////        ps.setRotationSpeedRange(90, 180);
////        ps2.setFadeOut(200, new AccelerateInterpolator());
////        ps2.oneShot(all_two, 70);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private int baseCurrentLine = -1; // 当前行(已经画上的一行)
//    private int valueCurrentLine = 0; // 当前行（以及开始计算音调的一行）
//
//    public void setCountTimes(int count) {
//        countTimes += count;
//    }
//
//    //播放进度 countTimes：每一句歌词的时长，count 进度
//    private void setCountTimesNow(int time) {
//        // 每次进来都遍历存放的时间
//        KrcLine kl = null;
//        if (mKrc == null) {
////            LogUtils.sysout( "mKrc音调文件:=null");
//            return;
//        }
//        if (mKrc.getmKrcLineList() == null) {
//            LogUtils.sysout("mKrc.getmKrcLineList()==null");
//            return;
//        }
////        LogUtils.sysout("2222222222222222222222");
////        LogUtils.sysout( "++++++++++++++krc文件：遍历歌词列表来获取当前行和当前字");
//        for (int i = 0; i < mKrc.getmKrcLineList().size(); i++) {
//            // 遍历歌词列表来获取当前行和当前字
////            int mCurrentLine = i;
////            float float1 = 0.0f;
////            float float2 = 0.001f;
//            kl = mKrc.getmKrcLineList().get(i);
////            LogUtils.sysout( "当前行时长"+kl.getLineTime().getSpanTime()+"");
//            if (kl.getLineTime().getStartTime() <= time && time < kl.getLineTime().getStartTime() + kl.getLineTime().getSpanTime()) {
//                //当前行
//                countTimes = Integer.parseInt((time - kl.getLineTime().getStartTime()) + "");//当前行的进度
//                //在此添加火花效果：
//
//                int nowCurrentLine = Integer.parseInt(kl.getLineTime().getStartTime() + "");
////                LogUtils.sysout("当前时间："+nowCurrentLine+"上一句时间："+baseCurrentLine);
//
//                if (nowCurrentLine != baseCurrentLine && nowCurrentLine > baseCurrentLine) {
//
//                    //烟花效果：
//                    Message message = new Message();
//                    message.what = 1;
//                    handler.sendMessage(message);
//                    startswitch = true;//开始唱
//
//                    int value = 0;
////                    LogUtils.sysout( "***********行该改变了**********" );
//                    try {
//                        for (int h = 0; h < kl.getWordTime().size(); h++) {
//                            value += kl.getWordTime().get(h).getReserve();
//                        }
//                        tempValue = value / kl.getWordTime().size();//得到一句歌词的平均值
//                        baseCurrentLine = nowCurrentLine;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        int alltimes = Integer.parseInt(kl.getLineTime().getSpanTime() + "");
////                        LogUtils.sysout( "当前行时长" + alltimes );
//                    } catch (Exception e) {
////                        alltimes  = 4000;
//                        e.printStackTrace();
//                    }
//                }
////                LogUtils.sysout( "-----------------" );
//
//            }
//        }
//    }
//
//    private int valueall = 0;
//    private int i = 0;
//    private final int rank = 1;//等级，表示打分等级，如：2：表示10/2个等级
//    float allscore = 0;
//
//    private void drawDB(int maxAmplitude) {//分析声音分贝
//        Integer v = 0;
//        float hight = 0;
//        int BASE = 1;
//        double ratio = (double) maxAmplitude / BASE;
//        int db = 0;// 分贝
//        if (ratio > 1)
//            db = (int) (20 * Math.log10(ratio));
//        if (db < 0)
//            db = 0;
////            x.decrease(-db);//保存音频分贝大小，根据X里的值绘制一段完整的波浪线
//        v = db;
//
//        int dataValue = 0;
//
//        if (startswitch) {//开始唱
//            i++;
//            valueall += db / 9;
//            //TODO//
//            mSoundMeter.setDataValue(db / 9);
//            if (valueCurrentLine != baseCurrentLine) {//行该改变了：
//                recordValue = valueall / i;//得到录音的平均值；
//                i = 0;
//                valueall = 0;
//                valueCurrentLine = baseCurrentLine;
//
//                //开始计算所得分数：
////                allscore += (10 - Math.abs( recordValue - tempValue ) * rank) * 10;
//                item_score = (int) ((10 - Math.abs(recordValue - tempValue) * rank) * 10);
////                LogUtils.sysout( "当前得分: " + item_score);
////                LogUtils.sysout( "总分："+allscore );
////                toast( "累计得分=" + allscore );
////                    toast( "总分是" );
//            }
//        } else {
//            i = 0;
//            valueall = 0;
//        }
//    }
//
//    private float mCurrentAngle;
//    private static final float DROPOFF_STEP = 0.18f;
//
//    public void drawold(int maxAmplitude) {//分析声音频率
//        final float minAngle = (float) Math.PI / 8;
//        final float maxAngle = (float) Math.PI * 7 / 8;
//
//        float angle = minAngle;
//        if (extAudioRecorder != null)
//            angle += (maxAngle - minAngle) * maxAmplitude / 32768;
////        LogUtils.sysout( "--------angle=" + angle );
//        if (angle > mCurrentAngle)
//            mCurrentAngle = angle;
//        else
//            mCurrentAngle = Math.max(angle, mCurrentAngle - DROPOFF_STEP);
//
//        mCurrentAngle = Math.min(maxAngle, mCurrentAngle);
//        LogUtils.sysout("11111111111mCurrentAngle=" + mCurrentAngle);
////        LogUtils.sysout( "11111111111 mRecorder=" + extAudioRecorder );
//
//        //判断是否采集到数据
//        float hight = 0;
//        if (extAudioRecorder != null && mCurrentAngle != 0) {
//
//        } else {
////            canvas.drawRect( getWidth() / 2, getHeight() - 10, getWidth(), getHeight(), mPaint );// 长方形  if (mRecorder != null)
////            postInvalidateDelayed( ANIMATION_INTERVAL );
//        }
////
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//}
