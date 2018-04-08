package com.changxiang.vod;

import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.module.ui.addlocal.UpdateLocalVideoActivity;
import com.changxiang.vod.module.ui.oratorio.CameraOratorioActivity;
import com.changxiang.vod.module.ui.singermusic.SingerIndexNewActivity;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.localmusic.LocalMusicIndexActivity;
import com.changxiang.vod.module.ui.oratorio.OratorioActivity;
import com.changxiang.vod.module.ui.recently.RecentlyIndexActivity;
import com.changxiang.vod.module.ui.typemusic.TypeIndexActivityold;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvSinger, tvType, tvTop, tvLocal, tvDownload, tvRecently, tvUpdata;//歌星点歌，分类点歌，榜单点歌，本地作品，已点歌曲，最近播放,上传视频

    private TextView ivOratorio;

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case 1:
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        creatDir();
    }

    public void initView() {
        tvSinger = (TextView) findViewById(R.id.choose_tvSinger);
        tvType = (TextView) findViewById(R.id.choose_tvType);
        tvTop = (TextView) findViewById(R.id.choose_tvTop);
        tvLocal = (TextView) findViewById(R.id.choose_tvLocal);
        tvDownload = (TextView) findViewById(R.id.choose_tvDownload);
        tvRecently = (TextView) findViewById(R.id.choose_tvRecently);
        ivOratorio = (TextView) findViewById(R.id.choose_tvOratorio);
        tvUpdata = (TextView) findViewById(R.id.choose_tvUpdata);


        tvSinger.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvTop.setOnClickListener(this);
        tvLocal.setOnClickListener(this);
        tvDownload.setOnClickListener(this);
        tvRecently.setOnClickListener(this);
        ivOratorio.setOnClickListener(this);
        tvUpdata.setOnClickListener(this);
    }

    private void creatDir() {
        //创建文件夹  static
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.sysout("------------------------------------requestPermission");
            requestPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE}, 0x002);
        } else {
            makeDirs();
        }
    }


    @Override
    public void permissionSuccess(int request_code_permission) {
        super.permissionSuccess(request_code_permission);
        if (request_code_permission == 0x002) {
            makeDirs();
        }
    }

    private void makeDirs() {
        File lyric = MyFileUtil.getDir("lyric");
        File image = MyFileUtil.getDir("image");
        File cache = MyFileUtil.getDir("cache");
        File apk = MyFileUtil.getDir("apk");
        File mp3 = MyFileUtil.getDir("mp3");
        File video = MyFileUtil.getDir("video");
        File accompany = MyFileUtil.getDir("accompany");
        File krc = MyFileUtil.getDir("krc");
        File work = MyFileUtil.getDir("work");
        File cacheMp3 = MyFileUtil.getDir("cache/MP3");
        File cacheMp4 = MyFileUtil.getDir("cache/MP4");
        File compose = MyFileUtil.getDir("compose");
        File diycrop = MyFileUtil.getDir("diycrop");
        File diymv = MyFileUtil.getDir("diymv");
        File gifbitmap = MyFileUtil.getDir("gifbitmap");
        File gifcache = MyFileUtil.getDir("gifcache");
        File recorder = MyFileUtil.getDir("recorder");
        File muxerDecode = MyFileUtil.getDir(".muxerDecode");
//            File recorder = MyFileUtil.getDir("recorder");
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
//            case R.id.choose_back://返回
////                finishActivity();
//                break;
//            case R.id.choose_top_rl://搜索歌曲
////                intent = new Intent(this, SearchActivity.class);
////                startActivity(intent);
//                break;
            case R.id.choose_tvSinger://歌星点歌
//                intent = new Intent(this, SingerIndexActivity.class);
                intent = new Intent(this, SingerIndexNewActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_tvType://分类
                intent = new Intent(this, TypeIndexActivityold.class);
                startActivity(intent);
                break;
            case R.id.choose_tvTop://榜单
//                intent = new Intent(this, TopIndexActivity.class);
//                startActivity(intent);
                break;
            case R.id.choose_tvLocal://本地作品
                intent = new Intent(this, LocalMusicIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_tvDownload://已点歌曲
                toast("准放置将要播放的歌曲列表");
//                intent = new Intent(this, DownloadSongActivity.class);
//                startActivity(intent);
                break;
            case R.id.choose_tvRecently://最近播放
                intent = new Intent(this, RecentlyIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_tvOratorio://清唱
//                intent = new Intent(this, OratorioActivity.class);
                intent = new Intent(this, CameraOratorioActivity.class);
                startActivity(intent);
//                musicPlay(false);
                break;
            case R.id.choose_tvUpdata://上传视频
//                toast("上传视频");
                intent = new Intent(this, UpdateLocalVideoActivity.class);
                startActivity(intent);
//                musicPlay(false);
                break;
        }

    }
}
