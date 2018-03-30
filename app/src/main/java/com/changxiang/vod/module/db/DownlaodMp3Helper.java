package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建保存MP3数据的数据库
 * Created by 15976 on 2017/4/13.
 */

public class DownlaodMp3Helper extends SQLiteOpenHelper implements IDownloadTable {

    public DownlaodMp3Helper(Context context) {
        super(context, "mp3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder=new StringBuilder();
        builder.append("create table "+MP3_TABLE_NAME+"(")
                .append(_ID+" integer primary key autoincrement,")
                .append(COLUMN_SONGID+" text,")
                .append(COLUMN_SONG_NAME+" text,")
                .append(COLUMN_SINGER+" text,")
                .append(COLUMN_TYPE+" text,")
                .append(COLUMN_NUM+" integer,")
                .append(COLUMN_KRC_URL+" text,")
                .append(COLUMN_LRC_URL+" text,")
                .append(COLUMN_ACC_URL+" text,")
                .append(COLUMN_ORI_URL+" text,")
                .append(COLUMN_QZTIME+" text,")
                .append(COLUMN_IMG+" text,")
                .append(COLUMN_IS_ALL_DOWNLOAD+" integer,")
                .append(COLUMN_IMGHEAD+" text)");
        db.execSQL(builder.toString());//创建保存播放记录的表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
