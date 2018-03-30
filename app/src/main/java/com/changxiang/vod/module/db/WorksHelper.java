package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15976 on 2017/2/20.
 */

public class WorksHelper extends SQLiteOpenHelper {



    public WorksHelper(Context context) {
        //super(context, name, factory, version);
        super(context, WorksTable.DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder builder=new StringBuilder();
        builder.append("create table "+ WorksTable.TABLE_NAME+"(")
                .append(WorksTable._ID+" integer primary key autoincrement,")
                .append(WorksTable.COLUMN_WORK_ID+" text,")
                .append(WorksTable.COLUMN_MUSIC_TYPE+" text,")
                .append(WorksTable.COLUMN_IMGALBUMURL+" text,")
                .append(WorksTable.COLUMN_YCVIPID+" text,")
                .append(WorksTable.COLUMN_LISTEN_NUM+" integer,")
                .append(WorksTable.COLUMN_FLOWER_NUM+" integer,")
                .append(WorksTable.COLUMN_COMMENT_NUM+" integer,")
                .append(WorksTable.COLUMN_TRANS_NUM+" integer,")
                .append(WorksTable.COLUMN_SONG_NAME+" text,")
                .append(WorksTable.COLUMN_WORK_TYPE+" text,")
                .append(WorksTable.COLUMN_OTHER+" text,")
                .append(WorksTable.COLUMN_SONG_ID+" text,")
                .append(WorksTable.COLUMN_SONG_TYPE+" text,")
                .append(WorksTable.COLUMN_SKIP_PRELUDE+" text)")
                ;
        db.execSQL(builder.toString()); //创建保存下载作品的表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<newVersion){
//            db.delete(WorksTable.DB_NAME,null,null);
        }
    }



    public static class WorksTable{
        public static final String DB_NAME = "workNew.db";
        public static final String _ID = "_id";
        public static final String TABLE_NAME="works";//表名
        public static final String COLUMN_WORK_ID="workId";//作品id
        public static final String COLUMN_MUSIC_TYPE="musicType";//MP4，MP3
        public static final String COLUMN_IMGALBUMURL="imgAlbumUrl";//封面图
        public static final String COLUMN_YCVIPID="ycVipId";//会员名
        public static final String COLUMN_LISTEN_NUM="listenNum";//收听数
        public static final String COLUMN_FLOWER_NUM="flowerNum";//鲜花数
        public static final String COLUMN_COMMENT_NUM="commentNum";//评论数
        public static final String COLUMN_TRANS_NUM="transNum";//转发数
        public static final String COLUMN_SONG_NAME="Songname";//作品名
        public static final String COLUMN_WORK_TYPE="workType";//收藏，转发，原创
        public static final String COLUMN_OTHER="other";//预留字段
        public static final String COLUMN_SONG_ID="songId";//音乐源id
        public static final String COLUMN_SONG_TYPE="songType";//音乐源类型
        public static final String COLUMN_SKIP_PRELUDE="skipPrelude";//跳过前奏的毫秒值，0 表示录像时没有跳过前奏

    }
}
