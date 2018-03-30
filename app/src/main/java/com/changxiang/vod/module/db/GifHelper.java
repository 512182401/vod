package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15976 on 2017/6/29.
 */

public class GifHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="gif.db";
    public static final String _ID = "_id";
    public static final String COLUNM_TID="tid";
    public static final String COLUNM_TURL="turl";
    public static final String COLUNM_GIFBITMAP="gifbitmap";
    public static final String COLUNM_DECODER="decoder";
    public static final String COLUNM_GIFDURATION="gifDuration";
    public static final String COLUNM_ISDECODE="isdecode";
    public static final String COLUNM_GIFFRAMES="gifFrames";
    public static final String COLUNM_OTHER="other";


    public GifHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//创建保存解析记录的表
        StringBuilder builder=new StringBuilder();
        builder.append("create table "+DB_NAME+"(")
                .append(_ID+" integer primary key autoincrement,")
                .append(COLUNM_TID+" text,")
                .append(COLUNM_TURL+" text,")
                .append(COLUNM_GIFBITMAP+" text,")
                .append(COLUNM_DECODER+" text,")
                .append(COLUNM_GIFDURATION+" text,")
                .append(COLUNM_ISDECODE+" text,")
                .append(COLUNM_GIFFRAMES+" text,")
                .append(COLUNM_OTHER+" text)");
        db.execSQL(builder.toString());//
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
