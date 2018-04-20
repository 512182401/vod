package com.changxiang.vod.module.ui.saveutils;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.KrcParse;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.utils.RemarkRandom;
import com.changxiang.vod.common.utils.ScreenUtils;
import com.changxiang.vod.common.utils.SharedPrefManager;
import com.changxiang.vod.common.utils.dialog.AlertDialog;
import com.changxiang.vod.common.view.CompesProgressDialog;
import com.changxiang.vod.common.view.MyAlertDialog;
import com.changxiang.vod.common.view.PowerManagerUtil;
import com.changxiang.vod.common.view.StrokeTextView;
import com.changxiang.vod.module.constance.Constant;
import com.changxiang.vod.module.db.ComposeManager;
import com.changxiang.vod.module.db.DownloadManager;
import com.changxiang.vod.module.db.LocalCompose;
import com.changxiang.vod.module.entry.CameraSongDetail;
import com.changxiang.vod.module.entry.KrcLine;
import com.changxiang.vod.module.entry.SongDetail;
import com.changxiang.vod.module.mediaExtractor.Function.AudioFunction;
import com.changxiang.vod.module.mediaExtractor.Function.DecodeFunction;
import com.changxiang.vod.module.mediaExtractor.Interface.ComposeAudioInterface;
import com.changxiang.vod.module.mediaExtractor.Interface.DecodeOperateInterface;
import com.changxiang.vod.module.mediaExtractor.musicplus.VideoMuxerRecord;
import com.changxiang.vod.module.mediaExtractor.musicplus.VideoMuxerRecordvedio;
import com.changxiang.vod.module.mediaExtractor.musicplus.VideoMuxerVod;
import com.changxiang.vod.module.musicInfo.TimeUtil;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.localmusic.LocalMusicIndexActivity;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

/**
 * 保存页面
 * Created by 15976 on 2016/10/25.
 */

public class SavePracticeActivity extends BaseActivity implements View.OnClickListener, DecodeOperateInterface, ComposeAudioInterface, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, SeekBar.OnSeekBarChangeListener {
    private final int PROGRESS = 10;
    //数据库信息
    private ComposeManager mComposeManager;//数据库管理
    private DownloadManager dao;//数据库管理工具
    private LocalCompose mLocalCompose;//合成后歌曲对象
    private String datetime = "888888888";
    private String createDate = "";
    private int composeType = 1;//合成类型 composeType？ 0://录音：1://录像
    private String composeFile;//合成之后的文件

    //歌曲信息：
    private String songName = "测试";//歌曲名称
    private String bzUrl = "";//伴奏
    private String imgAlbumUrl = "";//封面
    private int Compose_finish = 0;//结束录制进度
    private int progressCount = 0;//进度
    private boolean isSkipPreLude;//是否跳过前奏


    private String decodeFileUrl;////解码后的音频文件
    private int actualRecordTime = 240;//录音时长(先固定值) 单位：s

    private boolean isfailure = false;//合成失败：(可以再次合成，)
    private boolean isdecoded = true;//是否已经解码成功：
    private boolean isCompose;//是否已经合成成功：
    private boolean isSave;//已经点击过保存按钮
    private boolean isrelease = false;//是否是_发布到我的主页


    private int offsetNumBase = 0;//人声偏移，毫秒（合成时数值）
    private int offsetNum = 0;//人声偏移，毫秒
    private int Compose_begin = 0;//开始录制进度
    private String composeVoiceUrlwav;////合成后的wav音频文件
    private boolean canencode = true;//是否可以使用meditcode解码；
    private final String pathfirst = MyFileUtil.DIR_RECORDER.toString() + File.separator + "firstcompose.mp4";//录像第一次合成的地址：
    /**
     * 地址信息
     */
//    private final String cacheDir = MyFileUtil.DIR_CACHE.toString() + File.separator;//路径(cache)
    private final String muxerDecode = MyFileUtil.MUXER_DECODE.toString() + File.separator;//路径(cache)
    private final String composeDir = MyFileUtil.DIR_COMPOSE.toString() + File.separator;//路径(cache)
    private ImageView lastBack;

    private Intent intent;
    private CameraSongDetail songDetail;//伴奏数据
    private AlphaAnimation out;
    private TextView songNameText;
    private ImageView ivCover;//封面
    private AlphaAnimation in;
    private int bg[] = {R.mipmap.origin_detail_01, R.mipmap.origin_detail_02, R.mipmap.origin_detail_03, R.mipmap.origin_detail_05, R.mipmap.origin_detail_06,
            R.mipmap.origin_detail_07, R.mipmap.origin_detail_08, R.mipmap.origin_detail_09, R.mipmap.origin_detail_10, R.mipmap.origin_detail_04};
    private int currentBg;
    private TextView saveMore;
    private TextView saveRepeat;//重录
    private TextView savePreview;//预览
    private TextView save;//保存
    private TextView release;//,发布到个人主页
    private String recordAudio;
    private MediaPlayer mp3Player;
    private String recordVideo;
    private FrameLayout fragmentFl;
    private SaveRecordFragment fragment;
    private MediaPlayer mp4Player;
    private RelativeLayout saveRl;
    private String recordType;
    private SeekBar playSb;
    private MediaPlayer accPlayer;
    private ScrollView scrollview;
    private boolean isBottom;
    private TextView tvCurrent;
    private TextView tvTotal;
    private String qzTime;
    private SeekBar voiceSb;
    private SeekBar accompanySb;
    private SeekBar offsetSb;
    private ImageView offset_left;//iv_save_offset_left
    private ImageView offset_right;//iv_save_offset_right
    private TextView voiceNumTv;
    private TextView accompanyNumTv;
    private TextView offsetNumTv;
    private TextView offsetNoticeTv;
    private View dialog;//对话框布局
    private int total;

    MuxerTaskVod mMuxerTaskVod;
    MuxerTask20 mMuxerTask2;
    MuxerTaskNoEncode mMuxerTaskNoEncode;
    private StrokeTextView lastLrcTv;
    private StrokeTextView nextLrcTv;
    private String krcPath;

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {

            case 1:

                break;
            case 2:
                closeProgressDialogC();//首先关闭显示的，
                //显示加载进度框
                showProgressDialogC("", 1, true);
                //暂停预览
                if (accPlayer != null && accPlayer.isPlaying()) {
                    accPlayer.pause();
                }
                if (mp3Player != null && mp3Player.isPlaying()) {
                    mp3Player.pause();
                }
                if (mp4Player != null && mp4Player.isPlaying()) {
                    mp4Player.pause();
                }
                setDrawTop(savePreview, R.drawable.practice_save_preview, getString(R.string.preview));
                break;

            case 990://解码自动加载进度条
                if (isCompose) {
                    return;
                }
                if (progressCount >= 80) {
                    progressCount++;
                    mDialog.setProgress(progressCount);
                    handler.sendEmptyMessageDelayed(990, 2000);
                    return;
                }
                if (progressCount < 80) {
                    progressCount++;
                    mDialog.setProgress(progressCount);
                    handler.sendEmptyMessageDelayed(990, 1000);
                }
                break;
            case 333:
                outAnim();
                break;
            case 1000://（后台解码）判断是否解码成功_进行下一步
                //1：通过查询数据库，获取当前伴奏音乐是否已经解码
                //2：如果没有，则提交给后台合成：

//                handler.sendEmptyMessageDelayed(1000, 100);//判断是否解码成功_进行下一步
                break;
            case 1001://（当前activity）解码成功之后需要合成
                if (isdecoded) {//解码成功
                    //修改伴奏数据库数据： 解码成功
//                    dao.updateSong("lrcPath", lrcFile.toString(),downloadActivityId, downloadSongId, downloadMusicType);
//                    try {
//                        dao.updateSong("songDecode", decodeFileUrl, songDetail.getActivityId(), songDetail.getSongId(), songDetail.getType());
//                        dao.updateSong("isDecode", "1", songDetail.getActivityId(), songDetail.getSongId(), songDetail.getType());
//                        LogUtils.sysout("修改songDetail数据库，");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    if (mLocalCompose != null && mLocalCompose.getCompose_MuxerTask() != null && mLocalCompose.getCompose_MuxerTask().equals("1")) {
                        isCompose = true;
                    } else {
//                        countend = 44;//进度条阀值
                        MuxerCompose();//开始合成
//                        isdecoded = false;//不可再解码
//                        save.setEnabled(false);//不可点击保存按钮
                    }

                } else {//解码没有完成，则不停去检测是否完成，
                    handler.sendEmptyMessageDelayed(1001, 1000);//isdecoded
                }
                break;
            case 101://修改了人声偏移和人声伴奏音量权重之后：
//                if (composeType == 0) {//录音为MP3
//                    if (canencode) {//如果是可以解码
//                    }
//                }else{
//                    finishMuxerDecodeCompose();
//                    isCompose = false;
//                    //是否已经解码
//                    handler.sendEmptyMessageDelayed(1001, 10);//isdecoded
//                }
////                handler.sendEmptyMessageDelayed(101, 10);//isdecoded
                break;
            case 1100://判断是否合成成功_进行下一步
                if (isCompose) {
                    try {
                        mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "Compose_MuxerTask", "1");//标志数据库为已经合成成功
                        mLocalCompose.setCompose_MuxerTask("1");
                        LogUtils.sysout("修改数据库，");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    closeProgressDialogC();
                    if (isrelease) {//是否是_发布到我的主页
                        isLeave = true;
                        send2LocalCommit();
                    } else {//跳转到本地作品
                        isLeave = true;
                        showCommitAlertDialog();
                    }

                } else {
                    handler.sendEmptyMessageDelayed(1100, 1000);//isdecoded
                }


//                handler.sendEmptyMessageDelayed(1100, 100);//判断是否合成成功_进行下一步
                break;

            case 2002://合成成功,修改标志位和数据库。
//                isdecoded = false;
                save.setEnabled(true);//可点击保存事件：

                LogUtils.sysout("***202***合成成功之后跳转到(3, 100)");
//                handler.sendEmptyMessageDelayed( 202, 100 );
                break;

            case 2003://合成失败,修改标志位和数据库。
//                isdecoded = false;
                toast("合成失败,请尝试再次合成。");
                save.setEnabled(true);//可点击保存事件：
                isCompose = false;
                isfailure = true;

//                handler.sendEmptyMessageDelayed( 202, 100 );
                break;

            case PROGRESS:
                if (mp3Player != null && mp3Player.isPlaying()) {
                    int currentPosition = mp3Player.getCurrentPosition();
                    playSb.setProgress(currentPosition);
                    tvCurrent.setText(TimeUtil.mill2mmss(mp3Player.getCurrentPosition()));
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    current = currentPosition;
                    updateLrc();
                }
                break;
            case 77://2s后隐藏偏移量提示框
                offsetNoticeTv.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * handMsg 合成规则：默认合成：1000和1001； 点击合成则为检测合成以及跳转显示 1100
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        PowerManagerUtil.keepScreenOn(getApplicationContext(), true);//保持屏幕不熄灭
        dao = DownloadManager.getDownloadManager(this);
        mComposeManager = ComposeManager.getComposeManager(this);
        //判断手机厂商和Android版本号，(因为部分手机不支持解码)
        if (Build.MANUFACTURER.equals("vivo")) {
//            if (Float.parseFloat(android.os.Build.VERSION.RELEASE) < 6) {
            canencode = false;
//            }
        }
//        recordUrl = MyFileUtil.DIR_RECORDER.toString() + File.separator + "1510828339117source.wav";
        initView();
        initData();
        initEvent();
        //TODO:测试用例：
        if (songDetail == null) {//测试开发模式
//        if (true) {//测试开发模式
            //测试解码：
            decodeFileUrl = muxerDecode + datetime + "source.wav";
            bzUrl = MyFileUtil.DIR_MP3.toString() + File.separator + "赵雷-成都.mp3source.wav";
            recordAudio = MyFileUtil.DIR_RECORDER.toString() + File.separator + "source.wav";
            composeType = 1;// // composeType？ 0://MP3和MP3合成：1://MP4和MP4合成 2://录制MP4和下载MP3合成3://录制MP3和下载MP4合成
            datetime = "20170106145999";//默认一首
            recordType = "mp4";
            composeFile = "清唱20180411205915.mp4";
            recordVideo = "MagicCamera_test.mp4";
//            combineVideo(composeFile, recordVideo, recordAudio);
            songDetail = new CameraSongDetail();
//            songDetail.setType("mp4");
            songDetail.setAccPath(bzUrl);
            songDetail.setDuration(240 * 1000 + "");
            songDetail.setSongDecode(decodeFileUrl);
            songDetail.setIsDecode(0 + "");//解码状态； 0：未开始解码：1：解码成功；2：解码进行中；解码失败，4：该手机不能解码：

            makeDB();//添加到数据库：

            MuxerCompose();//开始合成
            //IsDecode 解码状态； 0：未开始解码：1：解码成功；2：解码进行中；解码失败，4：该手机不能解码：
//            switch (Integer.parseInt(songDetail.getIsDecode())) {
//                case 0:
//                    startMuxerDecode();//开始解码
//                    break;
//                case 1:
//                    MuxerCompose();//开始合成
//                    break;
//                case 2:
//                    startMuxerDecode();//开始解码
//                    break;
//                case 3:
//                    startMuxerDecode();//开始解码
//                    break;
//                case 4:
//                    MuxerCompose();//开始合成
//                    break;
//            }

        } else {
            recordType = "mp4";
            isdecoded = true;
            try {
//                SongDetail songDetailnew = dao.selectSong(songDetail.getActivityId(), songDetail.getSongId(), songDetail.getType());
//                LogUtils.w("songDetail", songDetail.toString());
//                if (songDetailnew != null && !songDetailnew.equals("")) {
//                    songDetail = songDetailnew;
//                    if (!canencode) {
//                        songDetail.setIsDecode("4");
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            bzUrl = songDetail.getAccPath();
            try {
                krcPath = songDetail.getKrcPath();
                skipTime = Integer.parseInt(songDetail.getQzTime());
                if (krcPath != null && !"".equals(krcPath)) {
                    krcLines = KrcParse.setKrcPath(krcPath, false);
                }
            } catch (Exception e) {
                toast("歌词格式不对");
                e.printStackTrace();
            }
//            decodeFileUrl = cacheDir + "mp3" + File.separator + datetime + "444";
//            if (songDetail.getIsDecode() != null && songDetail.getIsDecode().equals("1") && songDetail.getSongDecode() != null && !songDetail.getSongDecode().equals("")) {
//
//                decodeFileUrl = songDetail.getSongDecode();
//                //判断SD卡是否存在：
//                File file = new File(decodeFileUrl);
//                if (file.exists()) {
//                    isdecoded = true;
//                } else {//文件不存在，则修改数据库：
//                    if (canencode) {
//                        isdecoded = false;
////                        songDetail.setIsDecode("0");
////                        try {
////                            dao.updateSong("isDecode", "0", songDetail.getActivityId(), songDetail.getSongId(), songDetail.getType());
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
//
//                    } else {
//                        isdecoded = true;
//                        songDetail.setIsDecode("4");
////                        try {
//////                            dao.updateSong("isDecode", "4", songDetail.getActivityId(), songDetail.getSongId(), songDetail.getType());
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
//                    }
//                }
//            } else {
////                decodeFileUrl = muxerDecode + songDetail.getActivityId() + "_" + songDetail.getSongId() + "_" + songDetail.getType();
//            }
//
            makeDB();//添加到数据库
//            //IsDecode 解码状态； 0：未开始解码：1：解码成功；2：解码进行中；解码失败，4：该手机不能解码：
//            String isDecode = songDetail.getIsDecode();
//            if (isDecode == null) {
//                isDecode = "0";
//            }
//            switch (Integer.parseInt(isDecode)) {
//                case 0:
//                    startMuxerDecode();//开始解码
//                    break;
//                case 1:
            MuxerCompose();//开始合成
//
//                    break;
//                case 2:
//                    startMuxerDecode();//开始解码
//                    break;
//                case 3:
//                    startMuxerDecode();//开始解码
//                    break;
//                case 4:
//                    MuxerCompose();//开始合成
//                    break;
//            }
        }

        mp3Play();
    }

    //结束所有合成进程
    private void finishMuxerDecodeCompose() {
        if (mMuxerTask2 != null) {
            mMuxerTask2.cancel(true);
        }
        if (mMuxerTaskNoEncode != null) {
            mMuxerTaskNoEncode.cancel(true);
        }
//        AudioFunction.setIsstop(false);
    }

    private void mp3Play() {
        try {
            mp3Player.reset();
            mp3Player.setDataSource(recordAudio);
            mp3Player.prepareAsync();

            accPlayer.reset();
            accPlayer.setDataSource(songDetail.getAccPath());
            accPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);//对话框布局
        lastBack = (ImageView) findViewById(R.id.activity_save_close);
        ivCover = (ImageView) findViewById(R.id.activity_save_imgCover);
        songNameText = (TextView) findViewById(R.id.activity_save_name);

        save = (TextView) findViewById(R.id.activity_save_save);
        tvCurrent = (TextView) findViewById(R.id.activity_save_tvCurrent);
        tvTotal = (TextView) findViewById(R.id.activity_save_tvTotal);
        release = (TextView) findViewById(R.id.activity_save_release);
        saveRepeat = (TextView) findViewById(R.id.activity_save_repeat);
        savePreview = (TextView) findViewById(R.id.activity_save_preview);
        saveMore = (TextView) findViewById(R.id.activity_save_more);
        fragmentFl = (FrameLayout) findViewById(R.id.activity_play_record_fl);
        saveRl = (RelativeLayout) findViewById(R.id.activity_save_rl);
        playSb = (SeekBar) findViewById(R.id.activity_save_playSb);
        voiceSb = (SeekBar) findViewById(R.id.activity_save_voiceBar);
        accompanySb = (SeekBar) findViewById(R.id.activity_save_accompanyBar);
        offsetSb = (SeekBar) findViewById(R.id.activity_save_offsetBar);
        voiceNumTv = (TextView) findViewById(R.id.activity_save_voiceNum);
        accompanyNumTv = (TextView) findViewById(R.id.activity_save_accompanyNum);
        offsetNumTv = (TextView) findViewById(R.id.activity_save_offsetNum);
        scrollview = (ScrollView) findViewById(R.id.save_sv);
        lastLrcTv = (StrokeTextView) findViewById(R.id.last_lrcTv);
        nextLrcTv = (StrokeTextView) findViewById(R.id.next_lrcTv);

//        private ImageView offset_left;//iv_save_offset_left
//        private ImageView offset_right;//iv_save_offset_right
        offset_left = (ImageView) findViewById(R.id.iv_save_offset_left);
        offset_right = (ImageView) findViewById(R.id.iv_save_offset_right);

        offsetNoticeTv = (TextView) findViewById(R.id.offset_notice_tv);

        ViewGroup.LayoutParams layoutParams = saveRl.getLayoutParams();
        layoutParams.width = ScreenUtils.widthPixels(this);
        layoutParams.height = 9 * ScreenUtils.widthPixels(this) / 16;
        saveRl.setLayoutParams(layoutParams);
    }

    private List<KrcLine> krcLines;
    private int line;//歌词播放到第几行
    private int current;
    private int skipTime;
    private int currentLineSTime;
    private int currentLineETime;

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

    private void initData() {
        mp4Player = new MediaPlayer();
        mp3Player = new MediaPlayer();
        accPlayer = new MediaPlayer();
        //设置监听
        setMediaPlayListener();
        //获取OpenCameraActivity传递过来的数据
        intent = getIntent();

        songDetail = (CameraSongDetail) intent.getExtras().getSerializable("songDetail");
        songNameText.setText(songDetail.getSongName());
        recordAudio = songDetail.getRecordAudio();
        recordVideo = songDetail.getRecordVideo();
//        total = intent.getIntExtra("TotalTime", 0);//源时长
        try {
//            total = Integer.parseInt(songDetail.getDuration(), 0);//源时长
            total = Integer.parseInt(songDetail.getDuration());//源时长
            qzTime = songDetail.getQzTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Compose_begin = Integer.parseInt(songDetail.getStartReordTime(), 0);//开始时间
            Compose_finish = Integer.parseInt(songDetail.getEndReordTime(), 0);//结束时间
            isSkipPreLude = intent.getBooleanExtra("isSkipPreLude", false);//是否跳过前奏
        } catch (Exception e) {
            e.printStackTrace();
        }

//        LogUtils.sysout("recordType------------" + recordType);
//        LogUtils.sysout("recordAudio------------" + recordAudio);
//        LogUtils.sysout("recordVideo------------" + recordVideo);
//        LogUtils.sysout("Compose_begin------------" + Compose_begin);
//        LogUtils.sysout("Compose_finish------------" + Compose_finish);
//        LogUtils.sysout("isSkipPreLude------------" + isSkipPreLude);
//        if ("mp3".equals(recordType)) {//录音为MP3
//            mp3Play();
//            outAnim();
//            composeType = 0;
//            saveMore.setVisibility(View.GONE);
//        } else if ("mp4".equals(recordType)) {//录像为MP4
        saveMore.setVisibility(View.VISIBLE);
        composeType = 1;
        ivCover.setVisibility(View.INVISIBLE);//
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = new SaveRecordFragment();
        transaction.commit();
        transaction.replace(R.id.activity_play_record_fl, fragment);
        LogUtils.w("recordType", "recordType------------");
//        }

        //seekbar初始值
        String voiceValue = SharedPrefManager.getInstance().getVoiceValue();
        String accValue = SharedPrefManager.getInstance().getAccValue();
        voiceSb.setProgress(Integer.parseInt(voiceValue));
        accompanySb.setProgress(Integer.parseInt(accValue));
        voiceNumTv.setText(voiceValue);
        accompanyNumTv.setText(accValue);

        setDrawTop(savePreview, R.drawable.activity_save_record_pause, getString(R.string.pause));
    }

    private void setMediaPlayListener() {
        mp4Player.setOnPreparedListener(this);
        mp4Player.setOnCompletionListener(this);
        mp4Player.setOnErrorListener(this);
        mp3Player.setOnPreparedListener(this);
        mp3Player.setOnCompletionListener(this);
        mp3Player.setOnErrorListener(this);
        accPlayer.setOnPreparedListener(this);
        accPlayer.setOnCompletionListener(this);
        accPlayer.setOnErrorListener(this);
    }

    private int tag;


    //设置TextView的图片和文字
    private void setDrawTop(TextView textView, int drawId, String text) {
        Drawable drawable = getResources().getDrawable(drawId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        textView.setCompoundDrawables(null, drawable, null, null);
        textView.setText(text);
    }

    //事件初始化
    private void initEvent() {
        offset_left.setOnClickListener(this);
        offset_right.setOnClickListener(this);

        lastBack.setOnClickListener(this);
        saveRepeat.setOnClickListener(this);
        savePreview.setOnClickListener(this);
        save.setOnClickListener(this);
        release.setOnClickListener(this);
        saveMore.setOnClickListener(this);
        playSb.setOnSeekBarChangeListener(this);
        voiceSb.setOnSeekBarChangeListener(this);
        accompanySb.setOnSeekBarChangeListener(this);
        offsetSb.setOnSeekBarChangeListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_save_offset_left://左边人声偏移
//                LogUtils.sysout("55555555555555 offsetNum = " + offsetNum);

                if (offsetNum < -800) {
                    offsetNum = -1000;
                } else {
                    offsetNum = offsetNum - 200;
                }

                try {
                    offsetNum = (int) (offsetNum / 200) * 200;
                    offsetSb.setProgress(50 + offsetNum / 20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                offsetNumTv.setText("" + offsetNum);
                offsetNoticeTv.setVisibility(View.VISIBLE);
                handler.removeMessages(77);
                handler.sendEmptyMessageDelayed(77, 3000);
                if ("mp4".equals(recordType)) {//录MV
                    if (offsetNum > 0) {//唱慢了，伴奏增加偏移量
                        accPlayer.seekTo(mp4Player.getCurrentPosition() + offsetNum);
                    } else if (offsetNum < 0) {//唱快了,录音增加偏移量
                        mp3Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                        mp4Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                    }
                } else if ("mp3".equals(recordType)) {//录MP3
                    if (offsetNum > 0) {//唱慢了，伴奏增加偏移量
                        accPlayer.seekTo(mp3Player.getCurrentPosition() + offsetNum);
                    } else if (offsetNum < 0) {//唱快了,录音增加偏移量
                        mp3Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                    }
                }
//                toast("左边人声偏移");
                break;
            case R.id.iv_save_offset_right://右边人声偏移
//                toast("右边人声偏移");
//                LogUtils.sysout("55555555555555 offsetNum = " + offsetNum);

                if (offsetNum > 800) {
                    offsetNum = 1000;
                } else {
                    offsetNum = offsetNum + 200;
                }

                try {
                    offsetNum = (int) (offsetNum / 200) * 200;
                    offsetSb.setProgress(50 + offsetNum / 20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                offsetNumTv.setText("" + offsetNum);
                offsetNoticeTv.setVisibility(View.VISIBLE);
                handler.removeMessages(77);
                handler.sendEmptyMessageDelayed(77, 3000);
                if ("mp4".equals(recordType)) {//录MV
                    if (offsetNum > 0) {//唱慢了，伴奏增加偏移量
                        accPlayer.seekTo(mp4Player.getCurrentPosition() + offsetNum);
                    } else if (offsetNum < 0) {//唱快了,录音增加偏移量
                        mp3Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                        mp4Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                    }
                } else if ("mp3".equals(recordType)) {//录MP3
                    if (offsetNum > 0) {//唱慢了，伴奏增加偏移量
                        accPlayer.seekTo(mp3Player.getCurrentPosition() + offsetNum);
                    } else if (offsetNum < 0) {//唱快了,录音增加偏移量
                        mp3Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                    }
                }
                break;
            case R.id.activity_save_release://发布到我的主页
////                toast("发布到我的主页");
                isrelease = true;
                if (isfailure) {//合成失败，或者需要再次合成
                    MuxerCompose();//开始合成
                    isfailure = false;
                    return;
                }
                if (isSave) {//已经点击过保存按钮
                    handler.sendEmptyMessageDelayed(2, 100);//打开进度条
                    handler.sendEmptyMessageDelayed(1100, 1000);//检测自动（开始合成）

                    return;
                } else {
                    if (composeType == 1) {//录像为MP4
                        handler.sendEmptyMessageDelayed(990, 1000);
                    }
                    handler.sendEmptyMessageDelayed(2, 100);//打开进度条
                    handler.sendEmptyMessageDelayed(1100, 1000);//检测自动（开始合成）
                    isSave = true;
                }
//                handler.sendEmptyMessageDelayed(1100, 1000);//isdecoded

//                intent = new Intent(this, LocalMusicIndexActivity.class);
//                startActivity(intent);

                break;
            case R.id.activity_save_save://保存按钮
                isrelease = false;
                if (isfailure) {//合成失败，或者需要再次合成
                    MuxerCompose();//开始合成
                    isfailure = false;
                    return;
                }

                if (isSave) {//已经点击过保存按钮
                    handler.sendEmptyMessageDelayed(2, 100);//打开进度条
                    handler.sendEmptyMessageDelayed(1100, 1000);//检测自动（开始合成）

                    return;
                } else {
                    if (composeType == 1) {//录像为MP4
                        handler.sendEmptyMessageDelayed(990, 1000);
                    }
                    handler.sendEmptyMessageDelayed(2, 100);//打开进度条
                    handler.sendEmptyMessageDelayed(1100, 1000);//检测自动（开始合成）
                    isSave = true;
                }

//                handler.sendEmptyMessageDelayed(1100, 1000);//isdecoded
//                toast("保存按钮");
                break;
            case R.id.activity_save_close:
//                toast("测试使用");
                exit();
                break;
            case R.id.activity_save_repeat:
//                toast("重录");
                finishActivity();
                break;
            case R.id.activity_save_preview:
//                toast("预览");
                if ("mp3".equals(recordType)) {//录音为MP3
                    if (mp3Player.isPlaying() && accPlayer.isPlaying()) {
                        handler.removeMessages(PROGRESS);
                        setDrawTop(savePreview, R.drawable.practice_save_preview, getString(R.string.preview));
                        mp3Player.pause();
                        accPlayer.pause();
                    } else {
                        mp3Player.start();
                        accPlayer.start();
                        setDrawTop(savePreview, R.drawable.activity_save_record_pause, getString(R.string.pause));
                        //每秒走一次进度
                        handler.removeMessages(PROGRESS);
                        handler.sendEmptyMessage(PROGRESS);
                    }
                } else if ("mp4".equals(recordType)) {
                    if (mp3Player.isPlaying() && mp4Player.isPlaying() && accPlayer.isPlaying()) {
                        mp3Player.pause();
                        mp4Player.pause();
                        accPlayer.pause();
                        setDrawTop(savePreview, R.drawable.practice_save_preview, getString(R.string.preview));
                        handler.removeMessages(PROGRESS);
                    } else {
                        mp3Player.start();
                        mp4Player.start();
                        accPlayer.start();
                        setDrawTop(savePreview, R.drawable.activity_save_record_pause, getString(R.string.pause));
                        //每秒走一次进度
                        handler.removeMessages(PROGRESS);
                        handler.sendEmptyMessage(PROGRESS);
                    }
                }
                break;
            case R.id.activity_save_more:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);//滑动到底部
                    }
                });

                break;
        }
    }

    private void exit() {
        if (!isLeave) {
            FrameLayout parent = (FrameLayout) dialog.getParent();
            if (parent != null) {
                parent.removeView(dialog);
            }
            new MyAlertDialog(this, dialog).builder()
                    .setMsg(getString(R.string.sure_to_abandon_record))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //数据库操作
                            finishMuxerDecodeCompose();
                            //删除数据库
                            if (mComposeManager != null) {
                                try {
//                                        mComposeManager.deleteCompose( mLocalCompose.getCompose_id() );//不能立马删除，否则会出错，应该标志删除
                                    mComposeManager.updateCompose(mLocalCompose.getCompose_id(), "Compose_delete", "1");//标志数据库为删除
                                } catch (EmptyStackException e) {
                                    e.printStackTrace();
                                }
                            }

                            //关闭界面
                            finishActivity();
                        }
                    }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }).show();
        } else {
            finishActivity();
        }
    }


    //背景图退出动画
    private void outAnim() {
        if (out == null) {
            out = new AlphaAnimation(1.0f, 0.5f);
        }
        out.setDuration(100);
        ivCover.startAnimation(out);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                System.out.println("动画开始...");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                System.out.println("动画重复...");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("动画结束...");
                currentBg = (++currentBg) % 10;
                ivCover.setImageResource(bg[currentBg]);
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
        ivCover.startAnimation(in);
        handler.removeMessages(333);
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                System.out.println("动画开始...");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                System.out.println("动画重复...");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("动画结束...");
                handler.sendEmptyMessageDelayed(333, 3000);
            }
        });
    }

    @Override
    protected void onResume() {
        isLeave = false;
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ("mp4".equals(recordType)) {
            fragment.holder.addCallback(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp3Player != null) {
            mp3Player.release();
        }
        if (mp4Player != null) {
            mp4Player.release();
        }
        if (accPlayer != null) {
            accPlayer.release();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;

    }

    /**
     * //开始解码
     */
    private void startMuxerDecode() {
//        sendBroad("ACTION_DIYCOMPOSEING", 0, decodeAccompaniment);//开始合成中：
        if (canencode) {//如果是可以解码
            DecodeFunction.DecodeMusicFile(bzUrl,//解码源文件
                    decodeFileUrl,//目标文件（解出之后的文件0）
                    0,//伴奏解码开始时间
                    actualRecordTime + Constant.MusicCutEndOffset,//伴奏解码结束时间
                    this);//
        } else {//不可以解码
            isdecoded = true;
            handler.sendEmptyMessageDelayed(1001, 10);//isdecoded
//        toast("解码成功");
            LogUtils.sysout("解码成功");
        }


    }

    /**
     * 开始合成
     */
    public void MuxerCompose() {
        //解码测试：
//        DecodeFunction.DecodeMusicFile(bzUrl, decodeFileUrl, 0,
//                actualRecordTime + Constant.MusicCutEndOffset, this);
        //合成测试：
        composeVoiceUrlwav = muxerDecode + "composeVoice11.wav";//解码后的音频文件（未转码）
//        composeFile = composeDir + songName + datetime + ".mp3";
        offsetNumBase = offsetNum;
//        if (composeType == 0) {//录音为MP3录音为MP3
        if (false) {//录音为MP3录音为MP3

            if (canencode) {//如果是可以解码
                if (offsetNum >= 0) {
                    AudioFunction.BeginComposeAudio(recordAudio, decodeFileUrl, composeFile, composeVoiceUrlwav, false,
                            Constant.VoiceWeight, Constant.VoiceBackgroundWeight,
                            offsetNum / (2 * 1000) * Constant.RecordDataNumberInOneSecond, Compose_begin / (1000) * Constant.RecordDataNumberInOneSecond, this);

                } else {
                    AudioFunction.BeginComposeAudio(recordAudio, decodeFileUrl, composeFile, composeVoiceUrlwav, false,
                            Constant.VoiceWeight, Constant.VoiceBackgroundWeight,
                            -1 * offsetNum / (2 * 1000) * Constant.RecordDataNumberInOneSecond, -1 * Compose_begin / (1000) * Constant.RecordDataNumberInOneSecond, this);

                }
            } else {
//                    toast("你使用的手机不支持解码，请升级版本或者更换手机。");
                AudioFunction.BeginComposeAudio(recordAudio, recordAudio, composeFile, composeVoiceUrlwav, false,
                        Constant.VoiceWeight, Constant.VoiceBackgroundWeight,
                        -1 * Constant.MusicCutEndOffset / 2 * Constant.RecordDataNumberInOneSecond, 0, this);
            }

        } else {//录像为MP4
//            if (canencode) {//如果是可以解码
//                mMuxerTask2 = new MuxerTask20();
//                mMuxerTask2.execute();//已经解码的
//            } else {
//                mMuxerTaskNoEncode = new MuxerTaskNoEncode();
//                mMuxerTaskNoEncode.execute();
//            }

            mMuxerTaskVod = new MuxerTaskVod();
            mMuxerTaskVod.execute();//已经解码的
        }


    }

    ////录制MP4和下载MP3合成(解码不成功的时候，将)
    private class MuxerTaskNoEncode extends AsyncTask<Void, Long, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
//            finalMixVideo = composeFile;//FINAL_MIX_VIDEO_FILE 合成的video
            VideoMuxerRecord videoMuxer = VideoMuxerRecord.createVideoMuxer(pathfirst);//传入第一次合成后文件地址，
//            videoMuxer.mixRawAudio(new File(recordVideo),//视频
//                    new File(recordAudio),///下载的“mp3”解码后的文件
//                    new File(recordAudio), 0, true, true, Compose_finish);////录制的录音文件
            try {
//                combineFiles(composeFile, recordVideo, pathfirst);//第二次合成
                combineFiles(composeFile, recordVideo, recordAudio);//第二次合成
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            LogUtils.sysout("mVideoMuxer3合成成功。");

            isCompose = true;
            handler.sendEmptyMessageDelayed(2002, 10);//isdecoded
            closeProgressDialogC();
        }
    }


    ////录制MP4和下载MP3合成
    private class MuxerTask20 extends AsyncTask<Void, Long, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
//            finalMixVideo = composeFile;//FINAL_MIX_VIDEO_FILE 合成的video
            VideoMuxerRecordvedio videoMuxer = VideoMuxerRecordvedio.createVideoMuxer(pathfirst);//传入第一次合成后文件地址，

            videoMuxer.mixRawAudio(new File(recordVideo),//视频
                    new File(decodeFileUrl),///下载的“mp3”解码后的文件
                    new File(recordAudio), offsetNum / 1000, Compose_begin / 1000, true, true, Compose_finish);////录制的录音文件
            try {
                combineFiles(composeFile, recordVideo, pathfirst);//第二次合成
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            LogUtils.sysout("mVideoMuxer3合成成功。");
            isCompose = true;
            handler.sendEmptyMessageDelayed(2002, 10);//isdecoded
            closeProgressDialogC();
        }
    }

    ////录制MP4和下载MP3合成
    private class MuxerTaskVod extends AsyncTask<Void, Long, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
//            finalMixVideo = composeFile;//FINAL_MIX_VIDEO_FILE 合成的video
//            VideoMuxerVod videoMuxer = VideoMuxerVod.createVideoMuxer(pathfirst);//传入第一次合成后文件地址，
//
//            videoMuxer.mixRawAudio(new File(recordVideo),//视频
//                    new File(decodeFileUrl),///下载的“mp3”解码后的文件
//                    new File(recordAudio), offsetNum / 1000, Compose_begin / 1000, true, true, Compose_finish);////录制的录音文件
            try {
                combineFiles(composeFile, recordVideo, recordAudio);//第二次合成
//                combineVideo(composeFile, recordVideo, recordAudio);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            LogUtils.sysout("mVideoMuxer3合成成功。");
            isCompose = true;
            handler.sendEmptyMessageDelayed(2002, 10);//isdecoded
            closeProgressDialogC();
        }
    }

    /**
     * @param composeFile：最終合成的地址
     * @param recordUrl：录制的视频
     * @param pathfirst：第一次合成的音视频
     */

    private void combineFiles(String composeFile, String recordUrl, String pathfirst) {
//        countend = 95;
        FileChannel fc = null;
        try {
            List<Track> videoTracks = new LinkedList<>();
            List<Track> audioTracks = new LinkedList<>();

            try {
                Movie movie = MovieCreator.build(pathfirst);//获取音轨（）
                for (Track t : movie.getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Movie movie = MovieCreator.build(recordUrl);//获取视轨
                for (Track t : movie.getTracks()) {
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            Movie result = new Movie();

            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks
                        .toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks
                        .toArray(new Track[videoTracks.size()])));
            }

            Container out = new DefaultMp4Builder().build(result);
            fc = new RandomAccessFile(
                    String.format(composeFile), "rw").getChannel();
            out.writeContainer(fc);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //解码监控开始：
    @Override
    public void updateDecodeProgress(int decodeProgress) {//解码进度
        LogUtils.sysout("解码进度 decodeProgress = " + decodeProgress);
        if (mDialog != null) {
//            mDialog.setProgress(decodeProgress);
        } else {
//            mDialog = CompesProgressDialog.getInstance();
//            mDialog.show(this, decodeProgress, message, isCancelable);
////            handler.sendEmptyMessageDelayed(990, 1000);
////            progressCount = 0;
        }
    }

    @Override
    public void decodeSuccess() {//解码成功
//        closeProgressDialogC();
        isdecoded = true;
        handler.sendEmptyMessageDelayed(1001, 10);//isdecoded
//        toast("解码成功");
        LogUtils.sysout("解码成功");
    }

    @Override
    public void decodeFail() {//解码失败
//        toast("解码失败");
        LogUtils.sysout("解码失败");
        closeProgressDialogC();
    }

    //解码监控结束：
//合成监控开始：
    @Override
    public void updateComposeProgress(int composeProgress) {
        LogUtils.sysout("合成进度 composeProgress = " + composeProgress);
        if (mDialog != null) {
            mDialog.setProgress(composeProgress);
        }
    }

    @Override
    public void composeSuccess() {//合成成功
//        toast("合成成功");
        LogUtils.sysout("合成成功");
        isCompose = true;
        handler.sendEmptyMessageDelayed(2002, 10);//isdecoded
        closeProgressDialogC();

    }

    @Override
    public void composeFail() {//合成失败
//        toast("合成失败");
        LogUtils.sysout("合成失败");
//        toast("合成失败。");
        handler.sendEmptyMessageDelayed(2003, 10);//isdecoded
        closeProgressDialogC();
    }
//合成监控结束：


    private void makeDB() {//新建类，并添加到数据库记录
        songName = songDetail.getSongName();
        //获取当前时间，以便用做解码文件命名：
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetime = sDateFormat.format(new java.util.Date());
        createDate = dDateFormat.format(new java.util.Date());

        if (composeType == 0) {//0://录音：1://录像
            composeFile = composeDir + songName + datetime + ".mp3";
        } else {
            composeFile = composeDir + songName + datetime + ".mp4";
        }
        String remark = "快来听听我新唱的歌曲，一念起，万水千山；一念灭，沧海桑田。";
        //TODO 判断是否已经解码成功，如果成功解码，则直接获取解码文件，如果尚未解码，则启动mp3解码后台，优先解码该歌曲
        try {
            remark = RemarkRandom.getRemakeStr((int) (Double.parseDouble(datetime) % 10));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLocalCompose = new LocalCompose();
        mLocalCompose.setCreateDate(createDate);//设置时间：
        mLocalCompose.setCompose_id(datetime);//设置时间为唯一标识：
        mLocalCompose.setCompose_name(songName);//名字
//        mLocalCompose.setCompose_remark(getString(R.string.come_to_listen_new_work) + songName + getString(R.string.come_to_listen_new_work2));//名字
        mLocalCompose.setCompose_remark(remark);//描述
        try {
            mLocalCompose.setSongId(songDetail.getmVodMedia().getSongbm() + "");//歌曲id
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLocalCompose.setCompose_type(composeType + "");//合成类型
        mLocalCompose.setRecordUrl(recordAudio + "");//录制资源路径
        mLocalCompose.setBzUrl(bzUrl + "");//伴奏资源路径
        mLocalCompose.setCompose_image(imgAlbumUrl + "");//封面
        mLocalCompose.setAllDuration(total + "");//总时长
        mLocalCompose.setCompose_begin(Compose_begin + "");//开始录制进度
        mLocalCompose.setCompose_finish(Compose_finish + "");//结束录制进度
        mLocalCompose.setCompose_file(composeFile);//合成之后的文件//
        mLocalCompose.setCompose_delete("0");
        mLocalCompose.setCompose_other(songDetail.getKrcPath());//歌词
        mComposeManager.insertCompose(mLocalCompose);//插入记录
        LogUtils.sysout("添加数据库记录");
    }

    /**
     * 管理Fragment block stop
     */
    private CompesProgressDialog mDialog;

    public void showProgressDialogC(String message, int progress, boolean isCancelable) {
//        if(progress>95){
//            closeProgressDialogC();
//            return;
//        }
        if (mDialog != null) {
//            mDialog.dismiss();
            mDialog.show(this, progress, message, isCancelable);
        } else {
            mDialog = CompesProgressDialog.getInstance();
            mDialog.show(this, progress, message, isCancelable);
//            handler.sendEmptyMessageDelayed(990, 1000);
            progressCount = 0;
        }
    }

    public void closeProgressDialogC() {
        progressCount = 99;
        if (this != null && !this.isFinishing()) {
            if (mDialog == null) {
                return;
            }
            mDialog.dismiss();
        }
    }

    /*----------------------------SurfaceView生命周期-----------------------------*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.w("recordType", "--------------surfaceCreated");
        try {
            //这里要播放一个录像视频，两个音频：录制的音频和伴奏音频，因为带耳机录不进伴奏
            mp4Player.reset();
            mp4Player.setDataSource(recordVideo);
            mp4Player.setDisplay(holder);
            mp4Player.prepareAsync();


            mp3Player.reset();
            mp3Player.setDataSource(recordAudio);
            mp3Player.prepareAsync();

            accPlayer.reset();
            accPlayer.setDataSource(songDetail.getAccPath());
            accPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.w("recordType", "--------------surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.w("recordType", "--------------surfaceDestroyed");
    }


    /*----------------------------MediaPlay播放完监听-----------------------------*/
    @Override
    public void onCompletion(MediaPlayer mp) {
        //播放完成再次播放
        try {
            if ("mp4".equals(recordType)) {//录像为MP4
                mp4Player.reset();
                mp4Player.setDataSource(recordVideo);
                mp4Player.prepareAsync();
            }
            mp3Player.reset();
            mp3Player.setDataSource(recordAudio);
            mp3Player.prepareAsync();

            accPlayer.reset();
            accPlayer.setDataSource(songDetail.getAccPath());
            accPlayer.prepareAsync();

            lastLrcTv.setText("");
            nextLrcTv.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*----------------------------MediaPlay错误监听-----------------------------*/
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /*----------------------------MediaPlay准备完成监听-----------------------------*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        LogUtils.w("recordType", "--------------onPrepared");
        if (mp == mp4Player) {
            int videoHeight = mp4Player.getVideoHeight();
            int videoWidth = mp4Player.getVideoWidth();
            if (videoHeight != 0 && videoWidth != 0) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) fragmentFl.getLayoutParams();
                params.width = ScreenUtils.widthPixels(this);
                params.height = videoHeight * ScreenUtils.widthPixels(this) / videoWidth;
                LogUtils.w("recordType", mp4Player.getVideoHeight() + "----------" + mp4Player.getVideoWidth());
                fragmentFl.setLayoutParams(params);
                mp4Player.start();
//                mp4Player.setLooping(true);//循环播放
            }
        }
        if (mp3Player == mp) {
            mp3Player.start();
//            mp3Player.setLooping(true);
            int duration = mp3Player.getDuration();
            playSb.setMax(duration);
            tvCurrent.setText(TimeUtil.mill2mmss(0));
            tvTotal.setText(TimeUtil.mill2mmss(mp3Player.getDuration()));
            //每秒走一次进度
            handler.sendEmptyMessage(PROGRESS);
        }
        if (mp == accPlayer) {
            if (isSkipPreLude) {
                accPlayer.seekTo(Integer.parseInt(qzTime));
            } else {  //不跳过
                accPlayer.start();
            }
//            accPlayer.setLooping(true);
        }

    }

    AlertDialog mAlertDialog;
    private boolean isLeave;//是否离开保存了页面,或者已经保存完成。

    /**
     * 跳转到上传界面
     */
    private void send2LocalCommit() {

        Intent intent;
//        if (total > 0) {
//            playtime = total;
//        }
        if (Compose_finish != 0 && Compose_finish >= 60000) {

//            intent = new Intent(this, LocalCommitActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("LocalCompose", mLocalCompose);
//            intent.putExtras(bundle);
//            startActivity(intent);
//            finishActivity();
        } else {
            toast(getString(R.string.time_less_1_min));

            send2LocalMusicIndex();//不到一分钟，跳转到本地作品界面：
        }
    }


    /**
     * 跳转到本地作品提示框
     */
    private void showCommitAlertDialog() {


        if (mAlertDialog != null && mAlertDialog.isShow()) {

        } else {
            mAlertDialog = new AlertDialog(this).builder();
            mAlertDialog.setTitle(getString(R.string.save_success))
                    .setMsg(getString(R.string.save_to_local))
                    .setPositiveButton(getString(R.string.see_now), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isLeave = true;
                            send2LocalMusicIndex();
                        }
                    }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();

        }
    }

    /**
     * 跳转到本地歌曲界面
     */
    private void send2LocalMusicIndex() {
//        stop();
        Intent intent;
        intent = new Intent(this, LocalMusicIndexActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LocalCompose", mLocalCompose);
        intent.putExtras(bundle);
//        if (oratorio != -1) {
//            if ("audio".equals(recordType)) {
//                intent.putExtra("save", "mp3");
//            } else {
//                intent.putExtra("save", "mp4");
//            }
//        }
        startActivity(intent);
        finishActivity();
    }

    /*----------------------------进度条监听-----------------------------*/

    /**
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            switch (seekBar.getId()) {
                case R.id.activity_save_playSb:
                    //视频和音频一起调整
                    if ("mp3".equals(recordType)) {//录音为MP3
                        mp3Player.seekTo(progress);
                        accPlayer.seekTo(progress);
                        tvCurrent.setText(TimeUtil.mill2mmss(progress));
                    } else if ("mp4".equals(recordType)) {//录像为MP4
                        mp4Player.seekTo(progress);
                        mp3Player.seekTo(progress);
                        accPlayer.seekTo(progress);
                        tvCurrent.setText(TimeUtil.mill2mmss(progress));
                    }
                    break;
                case R.id.activity_save_voiceBar://人声音量
                    handler.sendEmptyMessageDelayed(101, 10);//重新合成

                    voiceNumTv.setText("" + progress);
                    float voiceValue = progress * 1.0f / 100;
                    mp3Player.setVolume(voiceValue, voiceValue);
                    //保存数据
                    SharedPrefManager.getInstance().saveVoiceValue(progress + "");
                    break;
                case R.id.activity_save_accompanyBar://伴奏音量
                    handler.sendEmptyMessageDelayed(101, 10);//重新合成

                    accompanyNumTv.setText("" + progress);
                    float accValue = progress * 1.0f / 100;
                    accPlayer.setVolume(accValue, accValue);
                    //保存数据
                    SharedPrefManager.getInstance().saveAccValue(progress + "");
                    break;
                case R.id.activity_save_offsetBar://偏移：录音和录像与伴奏的偏移

                    handler.sendEmptyMessageDelayed(101, 10);//重新合成

//                    if(progress){//判断是左移动还是右移动
//
//                    }else{
//
//                    }


                    offsetNum = (progress - 50) * 20 * 1;
                    try {
                        offsetNum = (int) (offsetNum / 200) * 200;
                        offsetSb.setProgress(50 + offsetNum / 20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    offsetNumTv.setText("" + offsetNum);
                    offsetNoticeTv.setVisibility(View.VISIBLE);
                    handler.removeMessages(77);
                    handler.sendEmptyMessageDelayed(77, 3000);
                    if ("mp4".equals(recordType)) {//录MV
                        if (offsetNum > 0) {//唱慢了，伴奏增加偏移量
                            accPlayer.seekTo(mp4Player.getCurrentPosition() + offsetNum);
                        } else if (offsetNum < 0) {//唱快了,录音增加偏移量
                            mp3Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                            mp4Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                        }
                    } else if ("mp3".equals(recordType)) {//录MP3
                        if (offsetNum > 0) {//唱慢了，伴奏增加偏移量
                            accPlayer.seekTo(mp3Player.getCurrentPosition() + offsetNum);
                        } else if (offsetNum < 0) {//唱快了,录音增加偏移量
                            mp3Player.seekTo(accPlayer.getCurrentPosition() - offsetNum);
                        }
                    }
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

    /**
     * 视频合成：
     *
     * @param output       合成之后的路径
     * @param output_video 视频轨道
     * @param output_audio 音频轨道
     */
    private void combineVideo(String output, String output_video, String output_audio) {
        try {
            MediaExtractor videoExtractor = new MediaExtractor();
            videoExtractor.setDataSource(output_video);
            MediaFormat videoFormat = null;
            int videoTrackIndex = -1;
            int videoTrackCount = videoExtractor.getTrackCount();
            for (int i = 0; i < videoTrackCount; i++) {
                videoFormat = videoExtractor.getTrackFormat(i);
                String mimeType = videoFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("video/")) {
                    videoTrackIndex = i;
                    break;
                }
            }

            MediaExtractor audioExtractor = new MediaExtractor();
            audioExtractor.setDataSource(output_audio);
            MediaFormat audioFormat = null;
            int audioTrackIndex = -1;
            int audioTrackCount = audioExtractor.getTrackCount();
            for (int i = 0; i < audioTrackCount; i++) {
                audioFormat = audioExtractor.getTrackFormat(i);
                String mimeType = audioFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("audio/")) {
                    audioTrackIndex = i;
                    break;
                }
            }

            videoExtractor.selectTrack(videoTrackIndex);
            audioExtractor.selectTrack(audioTrackIndex);

            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();

            MediaMuxer mediaMuxer = new MediaMuxer(output, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeVideoTrackIndex = mediaMuxer.addTrack(videoFormat);
            int writeAudioTrackIndex = mediaMuxer.addTrack(audioFormat);
            mediaMuxer.start();

            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            long sampleTime = 0;
            {
                videoExtractor.readSampleData(byteBuffer, 0);
                if (videoExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    videoExtractor.advance();
                }
                videoExtractor.readSampleData(byteBuffer, 0);
                long secondTime = videoExtractor.getSampleTime();
                videoExtractor.advance();
                long thirdTime = videoExtractor.getSampleTime();
                sampleTime = Math.abs(thirdTime - secondTime);
            }
            videoExtractor.unselectTrack(videoTrackIndex);
            videoExtractor.selectTrack(videoTrackIndex);

            while (true) {
                int readVideoSampleSize = videoExtractor.readSampleData(byteBuffer, 0);
                if (readVideoSampleSize < 0) {
                    break;
                }
                videoBufferInfo.size = readVideoSampleSize;
                videoBufferInfo.presentationTimeUs += sampleTime;
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = videoExtractor.getSampleFlags();
                mediaMuxer.writeSampleData(writeVideoTrackIndex, byteBuffer, videoBufferInfo);
                videoExtractor.advance();
            }

            while (true) {
                int readAudioSampleSize = audioExtractor.readSampleData(byteBuffer, 0);
                if (readAudioSampleSize < 0) {
                    break;
                }


                audioBufferInfo.size = readAudioSampleSize;
                audioBufferInfo.presentationTimeUs += sampleTime;
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = videoExtractor.getSampleFlags();
                mediaMuxer.writeSampleData(writeAudioTrackIndex, byteBuffer, audioBufferInfo);
                audioExtractor.advance();
            }

            mediaMuxer.stop();
            mediaMuxer.release();
            videoExtractor.release();
            audioExtractor.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
