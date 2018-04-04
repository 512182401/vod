package com.changxiang.vod.module.ui.localmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.KrcParse;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.ScreenUtils;
import com.changxiang.vod.common.view.PowerManagerUtil;
import com.changxiang.vod.common.view.StrokeTextView;
import com.changxiang.vod.module.db.LocalCompose;
import com.changxiang.vod.module.entry.KrcLine;
import com.changxiang.vod.module.musicInfo.TimeUtil;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.saveutils.SaveRecordFragment;

import java.io.IOException;
import java.util.List;

/**
 * Created by 15976 on 2018/1/9.
 * 本地视屏播放器
 */
public class GeWangPlayerActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, SurfaceHolder.Callback, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private final int BEGINANIMAL = 2;//开始动画
    private final int PROGRESS = 1;//开始进度
    private List<LocalCompose> playList;
    LocalCompose localCompose;
    private int position;
    private MediaPlayer videoPlayer;
    private RelativeLayout mediaParent;
    private SaveRecordFragment fragment;
    private ImageView pre;
    private ImageView playState;
    private ImageView next;
    private ImageView upload;
    private ImageView back;
    private TextView name;
    private SeekBar sb;
    private int size;
    private FrameLayout videoFL;
    private ImageView audioImg;
    private MediaPlayer audioPlayer;
    private AlphaAnimation out;
    private int bg[] = {R.mipmap.origin_detail_01, R.mipmap.origin_detail_02, R.mipmap.origin_detail_03, R.mipmap.origin_detail_05, R.mipmap.origin_detail_06,
            R.mipmap.origin_detail_07, R.mipmap.origin_detail_08, R.mipmap.origin_detail_09, R.mipmap.origin_detail_10, R.mipmap.origin_detail_04};
    private int currentBg;
    private AlphaAnimation in;
    private TextView currentTime;
    private TextView duration;
    private String krcPath;
    private List<KrcLine> krcLines;
    private StrokeTextView lastLrcTv;
    private StrokeTextView nextLrcTv;
    private int skipTime;
    private int currentLineSTime;
    private int currentLineETime;


    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case PROGRESS:
                if (videoPlayer != null && videoPlayer.isPlaying()) {
                    int currentPosition = videoPlayer.getCurrentPosition();
                    sb.setProgress(currentPosition);
                    currentTime.setText(TimeUtil.mill2mmss(currentPosition));

                    current = currentPosition;
                } else if (audioPlayer != null && audioPlayer.isPlaying()) {
                    int currentPosition = audioPlayer.getCurrentPosition();
                    sb.setProgress(currentPosition);
                    currentTime.setText(TimeUtil.mill2mmss(currentPosition));

                    current = currentPosition;
                }
                handler.removeMessages(PROGRESS);
                handler.sendEmptyMessageDelayed(PROGRESS, 100);
                //设置歌词
                updateLrc();
                break;
            case BEGINANIMAL:
                handler.removeMessages(BEGINANIMAL);
                outAnim();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gewang_player);
        PowerManagerUtil.keepScreenOn(this, true);//保持屏幕不熄灭
        initViews();
        initEvents();

        initPlayer();
        getDatas();

    }

    private void initViews() {
        mediaParent = (RelativeLayout) findViewById(R.id.media_parent);
        videoFL = (FrameLayout) findViewById(R.id.play_video);
        audioImg = (ImageView) findViewById(R.id.audio_imgCover);
        pre = (ImageView) findViewById(R.id.play_pre_iv);
        playState = (ImageView) findViewById(R.id.play_play_iv);
        next = (ImageView) findViewById(R.id.play_next_iv);
        upload = (ImageView) findViewById(R.id.play_upload_iv);
        back = (ImageView) findViewById(R.id.local_back);
        name = (TextView) findViewById(R.id.local_work_name);
        sb = (SeekBar) findViewById(R.id.play_sb);
        currentTime = (TextView) findViewById(R.id.media_current_time);
        duration = (TextView) findViewById(R.id.media_duration);
        lastLrcTv = (StrokeTextView) findViewById(R.id.last_lrcTv);
        nextLrcTv = (StrokeTextView) findViewById(R.id.next_lrcTv);
    }

    private void initEvents() {
        pre.setOnClickListener(this);
        playState.setOnClickListener(this);
        next.setOnClickListener(this);
        upload.setOnClickListener(this);
        back.setOnClickListener(this);
//        sb.setOnSeekBarChangeListener(this);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    LogUtils.sysout("9999999999999 88进度条监听 progress =" + progress + " fromUser=" + fromUser);
                    LogUtils.sysout("9999999999999 88进度条监听 audioPlayer =" + audioPlayer);
                    if (videoPlayer != null) {
                        videoPlayer.seekTo(progress);
                    }
                    if (audioPlayer != null) {
                        audioPlayer.seekTo(progress);
                    }
                    currentTime.setText(TimeUtil.mill2mmss(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 外界转递进来的播放列表
     */
    private void getDatas() {
        playList = (List<LocalCompose>) getIntent().getSerializableExtra("LocalCompose");//播放列表
        position = getIntent().getIntExtra("position", 0);//播放第几个
        size = playList.size();
        localCompose = playList.get(position);
        name.setText(localCompose.getCompose_name());
        total = Integer.parseInt(localCompose.getCompose_finish());//总时长
        krcPath = localCompose.getCompose_other();//歌词地址
        skipTime = Integer.parseInt(localCompose.getCompose_begin());//前奏时间，为0表示没有跳过前奏
        LogUtils.sysout("krcPath==========" + krcPath);
        try {
            if (krcPath != null && !"".equals(krcPath)) {
                krcLines = KrcParse.setKrcPath(krcPath, false);
            }
        } catch (Exception e) {
            toast("歌词格式不对");
            e.printStackTrace();
        }
        //添加SurfaceView  先准备
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = new SaveRecordFragment();
        transaction.commit();
        transaction.replace(R.id.play_video, fragment);
        videoFL.setVisibility(View.GONE);
        if ("0".equals(localCompose.getCompose_type())) {     //mp3
            //audio布局
            setAudioLayout();
            try {
                if (localCompose != null) {
                    audioPlayer.reset();
                    audioPlayer.setDataSource(localCompose.getCompose_file());
                    audioPlayer.prepareAsync();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ("1".equals(localCompose.getCompose_type())) {     //mp4
            videoFL.setVisibility(View.VISIBLE);
        }
    }

    private void initPlayer() {
        videoPlayer = new MediaPlayer();
        videoPlayer.setOnPreparedListener(this);
        videoPlayer.setOnCompletionListener(this);
        videoPlayer.setOnErrorListener(this);

        audioPlayer = new MediaPlayer();
        audioPlayer.setOnPreparedListener(this);
        audioPlayer.setOnCompletionListener(this);
        audioPlayer.setOnErrorListener(this);
    }

    /*----------------------------Audio布局-----------------------------*/
    private void setAudioLayout() {
        videoFL.setVisibility(View.GONE);
        audioImg.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = mediaParent.getLayoutParams();
        layoutParams.width = ScreenUtils.widthPixels(this);
        layoutParams.height = 9 * ScreenUtils.widthPixels(this) / 16;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioPlayer != null && audioPlayer.isPlaying()) {
            audioPlayer.pause();
            playState.setBackgroundResource(R.drawable.local_play);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fragment != null) {
            fragment.holder.addCallback(this);
        }
    }

    /*----------------------------SurfaceView生命周期-----------------------------*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.sysout("surfaceCreated");
        setDatas();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.sysout("surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.sysout("surfaceDestroyed");
        if (videoPlayer != null && videoPlayer.isPlaying()) {
            videoPlayer.pause();
        }
    }

    private void setDatas() {
        videoPlayer.setDisplay(fragment.holder);
        if (position < size) {
            LocalCompose localCompose = playList.get(position);
            String videoPath = localCompose.getCompose_file();
            try {
                videoPlayer.reset();
                videoPlayer.setDataSource(videoPath);
                videoPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /*----------------------------MediaPlayer的3个监听:准备，完成，错误-----------------------------*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp == videoPlayer) {
            int videoWidth = mp.getVideoWidth();
            int videoHeight = mp.getVideoHeight();
            ViewGroup.LayoutParams layoutParams = mediaParent.getLayoutParams();
            layoutParams.width = ScreenUtils.widthPixels(this);
            layoutParams.height = ScreenUtils.widthPixels(this) * videoHeight / videoWidth;
            mediaParent.setLayoutParams(layoutParams);
            videoPlayer.setLooping(true);
            if (audioPlayer.isPlaying()) {
                audioPlayer.stop();
            }
            //清除动画
            handler.removeMessages(BEGINANIMAL);
            audioImg.clearAnimation();
            currentBg = 0;

            videoPlayer.start();
            sb.setMax(videoPlayer.getDuration());
            duration.setText(TimeUtil.mill2mmss(videoPlayer.getDuration()));
            handler.sendEmptyMessage(PROGRESS);


        } else if (mp == audioPlayer) {
            audioPlayer.setLooping(true);
            if (videoPlayer.isPlaying()) {
                videoPlayer.stop();
            }
            audioPlayer.start();
            //开启动画
            inAnim();
            sb.setMax(audioPlayer.getDuration());
            duration.setText(TimeUtil.mill2mmss(audioPlayer.getDuration()));
            handler.sendEmptyMessage(PROGRESS);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LogUtils.sysout("onCompletion----");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /*----------------------------按钮监听-----------------------------*/
    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle;
        switch (v.getId()) {
            case R.id.play_pre_iv:
//                toast("上一首");
                pre();
                break;
            case R.id.play_play_iv:
//                toast("播放或暂停");
                if ((videoPlayer != null && videoPlayer.isPlaying()) || (audioPlayer != null && audioPlayer.isPlaying())) {
                    pause();
                } else {
                    play();
                }
                break;
            case R.id.play_next_iv:
//                toast("下一首");
                next();
                break;
            case R.id.play_upload_iv:
//                toast("上传主页");
                String times = localCompose.getCompose_finish();
                Long iten = 0L;
                LogUtils.sysout("录制时间：" + times);
                Long longtime = 0L;
                if (times != null && !times.equals("")) {
                    try {
                        iten = Long.parseLong(times);
                        if (iten != 0) {
                            longtime = iten / 1000;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogUtils.sysout(getString(R.string.gewang_text3) + longtime);
                    if (longtime < 60 && longtime != 0) {
                        toast(getString(R.string.gewang_text2));
                        return;
                    }
                }
                //TODO
                int isUpload = 0;//是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
                String isUploadStr = localCompose.getIsUpload();
                if (isUploadStr != null && !isUploadStr.equals("")) {
                    isUpload = Integer.parseInt(isUploadStr);
                } else {
                    isUpload = 0;
                }
                if (isUploadStr != null && !isUploadStr.equals("0")) {
                    toast(getString(R.string.gewang_text1));
                    return;
                }
//                intent = new Intent(this, LocalCommitActivity.class);
//                bundle = new Bundle();
//                bundle.putSerializable("LocalCompose", localCompose);
//                intent.putExtras(bundle);
//                startActivity(intent);
                break;
            case R.id.local_back:
                finishActivity();
                break;
        }
    }

    /*----------------------------播放器基本操作-----------------------------*/
    //播放
    private void play() {
        LogUtils.sysout("---------------play----------------");
        if (videoPlayer != null) {
            videoPlayer.start();
        } else if (audioPlayer != null) {
            audioPlayer.start();
        }
        audioPlayer.start();
        playState.setBackgroundResource(R.drawable.local_play_pause);
    }

    //暂停
    private void pause() {
        if (videoPlayer != null && videoPlayer.isPlaying()) {
            videoPlayer.pause();
        } else if (audioPlayer != null && audioPlayer.isPlaying()) {
            audioPlayer.pause();
        }
        playState.setBackgroundResource(R.drawable.local_play);
    }

    //上一首
    private void pre() {
        position--;
        if (position < 0) {
            position = size - 1;
        }
        //MP3还是MP4
        LocalCompose preCompose = null;
        String compose_type = "";
        String compose_file = "";
        if (position < size && position >= 0) {
            preCompose = playList.get(position);
            localCompose = preCompose;
            compose_type = preCompose.getCompose_type();
            compose_file = preCompose.getCompose_file();
            name.setText(preCompose.getCompose_name());
            krcPath = preCompose.getCompose_other();
            skipTime = Integer.parseInt(localCompose.getCompose_begin());
            try {
                handler.removeMessages(PROGRESS);
                nextLrcTv.setText("");
                lastLrcTv.setText("");
                krcLines.clear();
                if (krcPath != null && !"".equals(krcPath)) {
                    krcLines = KrcParse.setKrcPath(krcPath, false);
                }
            } catch (Exception e) {
                toast("歌词格式不对");
                e.printStackTrace();
            }
        }
        if ("0".equals(compose_type)) {//mp3
            try {
                setAudioLayout();
                audioPlayer.reset();
                audioPlayer.setDataSource(compose_file);
                audioPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("1".equals(compose_type)) {//mp4
            try {
                videoFL.setVisibility(View.VISIBLE);
                audioImg.setVisibility(View.GONE);
                videoPlayer.reset();
                videoPlayer.setDataSource(compose_file);
                videoPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playState.setBackgroundResource(R.drawable.local_play_pause);
    }

    //下一首
    private void next() {
        position++;
        if (position >= size) {
            position = position % size;
        }
        //MP3还是MP4
        LocalCompose nextCompose = null;
        String compose_type = "";
        String compose_file = "";
        if (position < size && position >= 0) {
            nextCompose = playList.get(position);
            localCompose = nextCompose;
        }
        if (nextCompose != null) {
            name.setText(nextCompose.getCompose_name());
            compose_type = nextCompose.getCompose_type();
            compose_file = nextCompose.getCompose_file();
            krcPath = nextCompose.getCompose_other();
            skipTime = Integer.parseInt(localCompose.getCompose_begin());
            try {
                handler.removeMessages(PROGRESS);
                nextLrcTv.setText("");
                lastLrcTv.setText("");
                krcLines.clear();
                if (krcPath != null && !"".equals(krcPath)) {
                    krcLines = KrcParse.setKrcPath(krcPath, false);
                }
            } catch (Exception e) {
                toast("歌词格式不对");
                e.printStackTrace();
            }
        }
        if ("0".equals(compose_type)) {    //mp3
            try {
                setAudioLayout();
                audioPlayer.reset();
                audioPlayer.setDataSource(compose_file);
                audioPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("1".equals(compose_type)) {   //mp4
            try {
                videoFL.setVisibility(View.VISIBLE);
                audioImg.setVisibility(View.GONE);
                videoPlayer.reset();
                videoPlayer.setDataSource(compose_file);
                videoPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playState.setBackgroundResource(R.drawable.local_play_pause);
    }

    /*----------------------------进度条监听-----------------------------*/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (fromUser) {
            LogUtils.sysout("9999999999999 进度条监听 progress =" + progress + " fromUser=" + fromUser);
            switch (seekBar.getId()) {
                case R.id.play_sb:
                    sb.setProgress(progress);
                    if (videoPlayer != null) {
                        videoPlayer.seekTo(progress);
                    } else if (audioPlayer != null) {
                        audioPlayer.seekTo(progress);
                    }
                    currentTime.setText(TimeUtil.mill2mmss(progress));
                    break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /*----------------------------audio动画-----------------------------*/
    //背景图退出动画
    private void outAnim() {
        if (out == null) {
            out = new AlphaAnimation(1.0f, 0.5f);
        }
        out.setDuration(100);
        audioImg.startAnimation(out);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                LogUtils.w("gewang","out动画开始...");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                LogUtils.w("gewang","out动画重复...");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.w("gewang", "out动画结束...");
                currentBg = (++currentBg) % 10;
                audioImg.setImageResource(bg[currentBg]);
                inAnim();
            }
        });
    }

    //背景图进入动画
    private void inAnim() {
        if (in == null) {
            in = new AlphaAnimation(0.5f, 1.0f);
        }
        in.setDuration(100);
        audioImg.startAnimation(in);
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                LogUtils.w("gewang","in动画开始...");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                LogUtils.w("gewang","in动画重复...");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.w("gewang", "in动画结束...");
                handler.sendEmptyMessageDelayed(BEGINANIMAL, 3000);
            }
        });
    }


    private int line;//歌词播放到第几行
    private int current, total;

    //改变歌词和颜色
    private void updateLrc() {
        if (krcLines != null && krcLines.size() > line) {
            for (int i = 0; i < krcLines.size(); i++) {
                int startTime = krcLines.get(i).getLineTime().getStartTime();//一句歌词的开始时刻
                int spanTime = krcLines.get(i).getLineTime().getSpanTime();//一个歌词的跨度时间
                //根据播放进度获取歌词位置
                if (current + skipTime > startTime && current + skipTime < startTime + spanTime) {
                    line = i;
                    String updateLrc1 = krcLines.get(line).getLineStr();
                    LogUtils.sysout("updateLrc1======" + updateLrc1);
                    String updateLrc2 = "";
                    nextLrcTv.setTextColor(getResources().getColor(R.color.white));
                    lastLrcTv.setTextColor(getResources().getColor(R.color.white));
                    if (line + 1 < krcLines.size()) {
                        updateLrc2 = krcLines.get(line + 1).getLineStr();
                        LogUtils.sysout("updateLrc2======" + updateLrc2);
                    }
                    if (line % 2 == 1) { //第二行
                        nextLrcTv.setText(updateLrc1);
                        lastLrcTv.setText(updateLrc2);
                    } else {
                        lastLrcTv.setText(updateLrc1);
                        nextLrcTv.setText(updateLrc2);
                    }
                    break;
                }
            }

            currentLineSTime = krcLines.get(line).getLineTime().getStartTime();
            currentLineETime = krcLines.get(line).getLineTime().getSpanTime() + currentLineSTime;
            LogUtils.w("TAG", "字体变红:" + (currentLineSTime - current));
            if (currentLineSTime - current - skipTime < 150) {//字体变红
                if (line == 0) {//显示两句歌词
                    lastLrcTv.setTextColor(getResources().getColor(R.color.app_oher_red));
                    lastLrcTv.setText(krcLines.get(line).getLineStr());
                    nextLrcTv.setText(krcLines.get(line + 1).getLineStr());
                } else {
                    if (line % 2 == 1) {//第二行歌词
                        nextLrcTv.setTextColor(getResources().getColor(R.color.app_oher_red));
                    } else {//第一行歌词
                        lastLrcTv.setTextColor(getResources().getColor(R.color.app_oher_red));
                    }
                }
            }
            LogUtils.w("TAG", "显示下一句歌词:" + (currentLineETime - current));
            if (currentLineETime - current - skipTime < 0) {//该句唱完，显示下一句歌词
                if (line + 2 < krcLines.size()) {//倒数第二句播放完时，值相等
                    String updateLrc = krcLines.get(line + 2).getLineStr();
                    if (line % 2 == 1) {//第二行
                        nextLrcTv.setText(updateLrc);
                        nextLrcTv.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        lastLrcTv.setText(updateLrc);
                        lastLrcTv.setTextColor(getResources().getColor(R.color.white));
                    }
                    line++;
                } else if (line + 1 < krcLines.size()) {//最后一句
                    if (line % 2 == 1) {//第二行
                        nextLrcTv.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        lastLrcTv.setTextColor(getResources().getColor(R.color.white));
                    }
                    line++;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (videoPlayer != null) {
            videoPlayer.release();
        }
        if (audioPlayer != null) {
            audioPlayer.release();
        }
//        handler.removeCallbacksAndMessages(null);//移除所有 BaseActivity中已近有了
        audioImg.clearAnimation();
        super.onDestroy();
    }
}
