package com.changxiang.vod;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.musicInfo.TimeUtil;
import com.changxiang.vod.module.ui.addlocal.MediaItem;
import com.changxiang.vod.module.ui.base.BaseActivity;

import java.io.IOException;

/**
 * Created by 15976 on 2017/11/1.
 */

public class MusicPlayActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private ImageView backLast;
    private TextView centerText;
    private Intent intent;
    private MediaItem mediaItem;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceview;
    private ImageView playImage;
    private ImageView updateImage;
    private Bundle bundle;
    private ImageView localRepeat;
    private ProgressBar progressBar;
    private SeekBar sb;
    private TextView sbEnd;
    private TextView sbStart;
    private FrameLayout bottomFL;
    private TextView playText;
    private ImageView localBack;
    private int videoWidth;
    private int videoHeight;

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case 10:
                if (mediaPlayer != null) {
                    sb.setProgress(mediaPlayer.getCurrentPosition());
                    LogUtils.w("time", "mediaPlayer.getCurrentPosition()===" + mediaPlayer.getCurrentPosition());
                    sbStart.setText(TimeUtil.mill2mmss(mediaPlayer.getCurrentPosition()));
                    handler.sendEmptyMessageDelayed(10, 10);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设定为竖屏
        setContentView(R.layout.activity_music_play);//activity_music_play

        intent = getIntent();
        mediaItem = (MediaItem) intent.getExtras().getSerializable("mediaItem");
        LogUtils.w("mediaItem", "mediaItem==" + mediaItem);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        backLast = (ImageView) findViewById(R.id.back_last);
        localBack = (ImageView) findViewById(R.id.local_back);
        centerText = (TextView) findViewById(R.id.center_text);
        surfaceview = (SurfaceView) findViewById(R.id.preform_surface);
        playImage = (ImageView) findViewById(R.id.local_play_state_image);
        updateImage = (ImageView) findViewById(R.id.local_update_image);
        localRepeat = (ImageView) findViewById(R.id.local_repeat_image);
        sb = (SeekBar) findViewById(R.id.musicPlaying_sb);
        sbEnd = (TextView) findViewById(R.id.seek_end);
        sbStart = (TextView) findViewById(R.id.seek_start);
        bottomFL = (FrameLayout) findViewById(R.id.frame_layout);
        playText = (TextView) findViewById(R.id.local_play_state_text);
    }

    private void initData() {
        centerText.setText("预览视屏");
        //设置播放时打开屏幕
        surfaceview.getHolder().setKeepScreenOn(true);
        surfaceview.getHolder().addCallback(this);
    }

    private void initListener() {
        backLast.setOnClickListener(this);
        playImage.setOnClickListener(this);
        updateImage.setOnClickListener(this);
        localRepeat.setOnClickListener(this);
        localBack.setOnClickListener(this);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        playImage.setImageResource(R.drawable.local_pause);
                        playText.setText(R.string.local_preform_text2);
                    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_last:
                finishActivity();
                break;
            case R.id.local_back:
                finishActivity();
                break;
            case R.id.local_play_state_image:
                if (mediaPlayer.isPlaying()) {
                    playImage.setImageResource(R.drawable.local_start);
                    playText.setText(R.string.local_preform_text4);
                    mediaPlayer.pause();
                    handler.removeMessages(10);
                } else {
                    playImage.setImageResource(R.drawable.local_pause);
                    mediaPlayer.start();
                    playText.setText(R.string.local_preform_text2);
                    handler.sendEmptyMessage(10);
                }
                break;
            case R.id.local_update_image:
////                toast("点击");
//                intent = new Intent(this, SDCommitActivity.class);
//                LocalCompose localCompose = new LocalCompose();
//                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                SimpleDateFormat dDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String datetime = sDateFormat.format(new java.util.Date());
//                String createDate = dDateFormat.format(new java.util.Date());
//                localCompose.setCreateDate(createDate);//设置时间：
//                localCompose.setCompose_id(datetime);//设置时间为唯一标识：
////                localCompose.setCompose_name(mediaItem.getName());//名称
//                localCompose.setCompose_file(mediaItem.getData());//地址
//                localCompose.setCompose_finish(mediaItem.getDuration() + "");//时间
//                LogUtils.sysout("9999999视频时长mediaItem.getDuration() = " + mediaItem.getDuration());
//                localCompose.setSongId("localmv");//songId
//                localCompose.setCompose_type(8 + "");//8:本地非趣唱视频
//                bundle = new Bundle();
//                bundle.putSerializable("LocalCompose", localCompose);
//                intent.putExtras(bundle);
//                intent.putExtra("videoWidth", videoWidth);
//                intent.putExtra("videoHeight", videoHeight);
//                LogUtils.w("test", "videoWidth=" + videoWidth + ";videoHeight=" + videoHeight);
//                startActivity(intent);
////                finishActivity();
                break;
            case R.id.local_repeat_image:
                finishActivity();
                break;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.w("surface123", "surfaceCreated===");
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(mediaItem.getData());
            // 把视频输出到SurfaceView上
            mediaPlayer.setDisplay(surfaceview.getHolder());
            mediaPlayer.prepare();
            mediaPlayer.start();
            //播放时屏幕保持唤醒
            mediaPlayer.setScreenOnWhilePlaying(true);
            sb.setMax(mediaPlayer.getDuration());
            sbEnd.setText(TimeUtil.mill2mmss(mediaPlayer.getDuration()));
            handler.sendEmptyMessage(10);

            playImage.setImageResource(R.drawable.local_pause);
            playText.setText(R.string.local_preform_text2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.w("surface123", "surfaceChanged===");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.w("surface123", "surfaceDestroyed===");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //播放完后
    @Override
    public void onCompletion(MediaPlayer mp) {
        playImage.setImageResource(R.drawable.local_start);
        playText.setText(R.string.local_preform_text4);
        mediaPlayer.pause();
        sb.setProgress(0);
        sbStart.setText(TimeUtil.mill2mmss(0));
        handler.removeMessages(10);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoWidth = mp.getVideoWidth();
        videoHeight = mp.getVideoHeight();
        int surfaceviewHeight = surfaceview.getHeight();
        int surfaceviewWith = surfaceview.getWidth();
        LogUtils.w("mp", "videoWidth==" + videoWidth);//1280
        LogUtils.w("mp", "videoHeight==" + videoHeight);//720
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int realHeight;
        int realWith;
        if (videoWidth > videoHeight) {//横
            realHeight = videoHeight * width / videoWidth;
            LogUtils.w("mp", "realHeight==" + realHeight);//720
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) surfaceview.getLayoutParams();
            params.height = realHeight;
            surfaceview.setLayoutParams(params);
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) bottomFL.getLayoutParams();
            params1.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            bottomFL.setLayoutParams(params1);
        } else {//竖
            realWith = videoWidth * surfaceviewHeight / videoHeight;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) surfaceview.getLayoutParams();
            params.width = realWith;
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            surfaceview.setLayoutParams(params);
        }
    }
}
