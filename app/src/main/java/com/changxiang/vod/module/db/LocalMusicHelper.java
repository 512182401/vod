package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建保存MP3数据的数据库
 * Created by 15976 on 2017/4/13.
 */

public class LocalMusicHelper extends SQLiteOpenHelper implements ILocalTable {

    public LocalMusicHelper(Context context) {

        super(new DBContext(context), "vod.db", null, 2);
//        super(context, "VodMedia.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder=new StringBuilder();
        builder.append("create table "+TABLE_NAME_LOCAL+"(")
                .append(_ID+" integer primary key autoincrement,")
                .append(SONGBM+" text,")
                .append(SONGNAME+" text,")
                .append(ZS+" text,")
                .append(SINGER+" text,")
                .append(SONGTYPE+" integer,")
                .append(LANG+" text,")
                .append(ServerName+" text,")
                .append(SPELL+" text,")


                .append(VOLUME+" text,")
                .append(MAXVOL+" text,")
                .append(MINVOL+" text,")
                .append(MUSICTRACK+" integer,")
                .append(CHOURS+" text,")
                .append(EXIST+" text,")
                .append(NEWSONG+" text,")
                .append(STROKE+" integer,")
                .append(BRIGHTNESS+" text,")
                .append(SATURATION+" text,")
                .append(CONTRAST+" text,")
                .append(ORDERTIMES+" integer,")
                .append(MEDIAINFO+" text,")

                .append(CLOUD+" integer,")
                .append(CLOUDDOWN+" text,")
                .append(CLOUDPLAY+" integer,")
                .append(MOVIE+" text,")
                .append(HD+" integer,")
                .append(MEDIAARRAY+" text)");
        db.execSQL(builder.toString());//创建保存播放记录的表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
