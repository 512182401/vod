package com.changxiang.vod.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MyFileUtil {
    //public static final File dir_app_search=getDir("AppSearch");
    public static final File DIR_DB_DATA=getDir("db");
    public static final File DIR_IMAGE = getDir("image");
    public static final File DIR_CACHE = getDir("cache");
    public static final File DIR_APK = getDir("apk");
    public static final File DIR_MP3 = getDir("mp3");
    public static final File DIR_VEDIO = getDir("video");
    //    public static final File DIR_ACCOMPANY=getDir(".accompany");
    public static final File DIR_ACCOMPANY = getDir("accompany");
    public static final File DIR_LRC = getDir("lyric");
    public static final File DIR_KRC = getDir("krc");
    public static final File DIR_WORK = getDir("work");//我的作品（合成之后可以上传）
    public static final File DIR_CODE_MP3 = getDir("cache/MP3");//解码音频文件
    public static final File DIR_CODE_MP4 = getDir("cache/MP4");//解码视频文件
    public static final File DIR_COMPOSE = getDir("compose");
    public static final File DIY_CROP = getDir("diycrop");//剪切时候保存的图片
    public static final File DIY_MV = getDir("diymv");//剪切时候保存的图片
    public static final File GIF_BITMAP = getDir("gifbitmap");//.gif
    public static final File GIF_CACHE = getDir("gifcache");//解析图
    //录音
    public static final File DIR_RECORDER = getDir("recorder");
    public static final File MUXER_DECODE = getDir(".muxerDecode");//解码之后的数据。


    //获取SD卡根目录
    public static File getSDcardDir() {
        String state = Environment.getExternalStorageState();
        //判断内存卡是否挂载
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //获取SDcard根目录
            File storageDirection = Environment.getExternalStorageDirectory();
            //LogUtils.w("TAG",""+storageDirection.getAbsolutePath());
            return storageDirection;
        }
        //没有挂载，则抛出异常
        throw new RuntimeException("没有找到内存卡");
    }

    //获取应用目录
    public static File getAppDir() {
        File dir = new File(getSDcardDir(), "vod");
        if (!dir.exists()) {
            dir.mkdir();
        }
        //LogUtils.w("TAG","getAppDir:===="+dir.getAbsolutePath());
        return dir;
    }

    //获取该应用下的文件目录
    public static File getDir(String dir) {
        File file = new File(getAppDir(), dir);
        if (!file.exists()) {
            file.mkdir();
        }
        //LogUtils.w("TAG","getDir:===="+file.getAbsolutePath());
        return file;
    }

    //    //获取该应用下的文件目录
//    public static File getCode(Sng dir){
//        File file=new File(getAppDir(),dir);
//        if(!file.exists()){
//            file.mkdir();
//        }
//        //LogUtils.w("TAG","getDir:===="+file.getAbsolutePath());
//        return file;
//    }
    //安装apk
    public static void installApk(Context context, File apk) {
        Uri uri = Uri.fromFile(apk);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


}
