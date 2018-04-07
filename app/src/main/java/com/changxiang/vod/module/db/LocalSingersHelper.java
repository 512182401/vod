package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建保存歌星数据的数据库
 * Created by 15976 on 2017/4/13.
 */

public class LocalSingersHelper extends SQLiteOpenHelper implements ISingerTable {

    public LocalSingersHelper(Context context) {

        super(new DBContext(context), "vod.db", null, 2);
//        super(context, "vod.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("create table " + TABLE_NAME_SINGERS + "(")
                .append(_ID + " integer primary key autoincrement,")
                .append(ID + " text,")
                .append(Name + " text,")
                .append(Type + " text,")
                .append(Picture + " text,")
                .append(Nationality + " integer,")
                .append(SongsCount + " text,")
                .append(Visible + " text,")
                .append(PINYIN + " text,")
                .append(Strokes + " text,")
                .append(zs + " text,")
                .append(ordertimes + " text,");
        db.execSQL(builder.toString());//创建保存播放记录的表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
