package com.changxiang.vod;

import android.os.Build;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.module.ui.base.BaseActivity;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void handMsg(Message msg) {
        switch (msg.what){
            case 1:
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creatDir();
    }



    private void creatDir() {
        //创建文件夹  static
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.sysout("------------------------------------requestPermission");
            requestPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_PHONE_STATE}, 0x002);
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

    }
}
