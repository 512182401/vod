package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15976 on 2016/12/6.
 */

public class DownloadHelper extends SQLiteOpenHelper implements IDownloadTable {

    public DownloadHelper(Context context) {
        super(new DBContext(context), "download.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
                + "start_pos integer, end_pos integer, compelete_size integer,url char)");
        /*db.execSQL("create table Song_detail(_id integer PRIMARY KEY AUTOINCREMENT,songId char,songName char," +
                "singerName char,type char,num integer,krcPath char,lrcPath char,accPath char," +
                "oriPath char,imgAlbumUrl char)");*/
        //创建保存下载记录的表
        StringBuilder builder = new StringBuilder();
        builder.append("create table " + TABLE_NAME + "(")
                .append(_ID + " integer primary key autoincrement,")
                .append(COLUMN_SONGID + " text,")
                .append(COLUMN_SONG_NAME + " text,")
                .append(COLUMN_SINGER + " text,")
                .append(COLUMN_TYPE + " text,")
                .append(COLUMN_NUM + " integer,")
                .append(COLUMN_KRC_URL + " text,")
                .append(COLUMN_LRC_URL + " text,")
                .append(COLUMN_ACC_URL + " text,")
                .append(COLUMN_ORI_URL + " text,")
                /*.append(HistoryTable.COLUMN_BZURL+" text,")
                .append(HistoryTable.COLUMN_YCURL+" text,")
                .append(HistoryTable.COLUMN_GCURL+" text,")
                */
                .append(COLUMN_QZTIME + " text,")
                .append(COLUMN_IMG + " text,")
                .append(COLUMN_FILE_SIZE + " text,")
                .append(COLUMN_IS_ALL_DOWNLOAD + " integer,")
                .append(SONG_DECODE + " text,")
                .append(IS_DECODE + " text,")
                .append(ACTIVITY_ID + " text,")
                .append(COLUMN_IMGHEAD + " text)");

        db.execSQL(builder.toString());//创建保存播放记录的表
        //创建保存下载队列的表
        StringBuilder builder1 = new StringBuilder();
        builder1.append("create table " + TABLE_NAME_DOWNLOAD + "(")
                .append(_ID + " integer primary key autoincrement,")
                .append(COLUMN_SONGID + " text,")
                .append(COLUMN_SONG_NAME + " text,")
                .append(COLUMN_SINGER + " text,")
                .append(COLUMN_TYPE + " text,")
                .append(COLUMN_POSITION + " integer,")
                .append(COLUMN_IS_RECORD + " integer,")
                .append(COLUMN_IS_FINISH + " integer)");
        db.execSQL(builder1.toString());//创建保存播放记录的表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion != oldVersion) {
            String sql = "ALTER TABLE " + TABLE_NAME + " ADD " + COLUMN_FILE_SIZE + " text";
            db.execSQL(sql);
        }
    }
}
