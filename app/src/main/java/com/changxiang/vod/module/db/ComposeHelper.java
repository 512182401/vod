package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15976 on 2017/1/9.
 */

public class ComposeHelper extends SQLiteOpenHelper implements IComposeTable {
//    public static final String COMPOSE_ID="compose_id";
//    public static final String COMPOSE_NAME="compose_name";
//    public static final String COMPOSE_IMAGE="compose_image";
//    public static final String COMPOSE_REMARK="compose_remark";
//    public static final String CREATE_DATE="createDate";
//    public static final String compose_type="compose_type";
//    public static final String COMPOSE_MUXERDECODE="compose_muxerdecode";
//    public static final String COMPOSE_MUXERTASK="compose_muxertask";
//    public static final String ISUPLOAD="isUpload";
//    public static final String SONGID="songId";
//    public static final String COMPOSE_BEGIN="compose_begin";
//    public static final String COMPOSE_FINISH="compose_finish";
//    public static final String COMPOSE_DELETE="compose_delete";
//    public static final String COMPOSE_ERRCODE="compose_errcode";
//    public static final String COMPOSE_OTHER="compose_other";
//    public static final String RECORDURL="recordUrl";
//    public static final String BZURL="bzUrl";
//    public static final String ISEXPORT="isExport";
//    public static final String COMPOSE_FILE="Compose_file";

    public ComposeHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建保存解析记录的表
        StringBuilder builder = new StringBuilder();
        builder.append("create table " + TABLE_NAME_COMPOSE + "(")
                .append(_ID + " integer primary key autoincrement,")
                .append(COMPOSE_ID + " text,")
                .append(COMPOSE_NAME + " text,")
                .append(COMPOSE_IMAGE + " text,")
                .append(COMPOSE_REMARK + " text,")
                .append(CREATE_DATE + " text,")
                .append(COMPOSE_TYPE + " text,")
                .append(COMPOSE_MUXERDECODE + " text,")
                .append(COMPOSE_MUXERTASK + " text,")
                .append(ISUPLOAD + " text,")
                .append(SONGID + " text,")
                .append(COMPOSE_BEGIN + " text,")
                .append(COMPOSE_FINISH + " text,")
                .append(COMPOSE_DELETE + " text,")
                .append(COMPOSE_DURATION + " text,")
                .append(RECORDURL + " text,")
                .append(BZURL + " text,")
                .append(ISEXPORT + " text,")
                .append(COMPOSE_FILE + " text,")
                .append(COMPOSE_ACTIVITY + " text,")
                .append(COMPOSE_OTHER + " text)");
        db.execSQL(builder.toString());//
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
