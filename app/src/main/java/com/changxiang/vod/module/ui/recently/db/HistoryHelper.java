package com.changxiang.vod.module.ui.recently.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.changxiang.vod.module.db.IDownloadTable;

import static com.changxiang.vod.module.db.IDownloadTable.COLUMN_FILE_SIZE;
import static com.changxiang.vod.module.db.IDownloadTable.TABLE_NAME_HISTORY;

/**
 * 创建保存播放记录的数据库
 * Created by 15976 on 2016/10/20.
 */

public class HistoryHelper extends SQLiteOpenHelper implements IDownloadTable {

    //数据库版本，数据库名称
    private static final int VERSION_CODE = 2;
    private static final String DB_NAME = "playedHistory.db";

    public HistoryHelper(Context context) {
        super(context, DB_NAME, null, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDb(db,TABLE_NAME_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            String sql="ALTER TABLE "+TABLE_NAME_HISTORY+" ADD "+COLUMN_FILE_SIZE+" text";
            db.execSQL(sql);

//            addColumn(db, new String[]{COLUMN_FILE_SIZE}, TABLE_NAME_HISTORY);
        }

    }

    private void createDb(SQLiteDatabase db, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("create table " + tableName + "(")
                /*.append(HistoryTable._ID+" integer primary key autoincrement,")*/
                .append(COLUMN_SONGID + " text,")
                .append(COLUMN_SONG_NAME + " text,")
                .append(COLUMN_SINGER + " text,")
                .append(COLUMN_TYPE + " text,")
                .append(COLUMN_NUM + " integer,")
                .append(COLUMN_KRC_URL + " text,")
                .append(COLUMN_LRC_URL + " text,")
                .append(COLUMN_ACC_URL + " text,")
                .append(COLUMN_ORI_URL + " text,")
                .append(COLUMN_IMG + " text,")
                .append(COLUMN_DATE + " integer,")
                .append(COLUMN_IS_ALL_DOWNLOAD + " integer,")
                .append(COLUMN_FILE_SIZE + " text,")
                .append(COLUMN_IMGHEAD + " text)");
        db.execSQL(builder.toString());//创建保存播放记录的表
    }

    /**
     * 在表中添加字段方法 操作步骤： 1、先更改表名 2、创建新表,表名为原来的表名 3、复制数据 4、删除旧表
     *
     * @param db           数据库名
     * @param newColumnArr 添加的新字段的表名数组
     * @param oldTableName 旧表名，在方法内部将旧表名修改为 _temp_+oldTableName
     */
    private void addColumn(SQLiteDatabase db, String[] newColumnArr,
                           String oldTableName) {

        if (db == null || newColumnArr == null || newColumnArr.length < 1
                || oldTableName == null) {
            // 数据库为空，新字段个数为0，添加字段后的字段数组个数为0，旧表名为空
            return;
        }

        // 拿到旧表中所有的数据
        Cursor cursor = db.rawQuery("select * from " + oldTableName, null);
        if (cursor == null) {
            // 如果游标为空
            return;
        }
        // 拿到原来的表中所有的字段名
        String[] oldColumnNames = cursor.getColumnNames();

        // 更改原表名为临时表
        String tempTableName = "_temp_" + oldTableName;
        db.execSQL(
                "alter table " + oldTableName + " rename to " + tempTableName);

        // 创建新表
        if (oldColumnNames.length < 1) {
            // 如果原来的表中字段个数为0
            return;
        }

        createDb(db,oldTableName);//创建新表,表名为原来的表名

        // 创建一个线程安全的字符串缓冲对象，防止用conn多线程访问数据库时造成线程安全问题
        /*StringBuffer createNewTableStr = new StringBuffer();
        createNewTableStr
                .append("create table if not exists " + oldTableName + "(");
        for (int i = 0; i < oldColumnNames.length; i++) {
            if (i == 0) {
                createNewTableStr.append(oldColumnNames[i]
                        + " ,");
            } else {
                createNewTableStr.append(oldColumnNames[i] + ",");
            }
        }

        for (int i = 0; i < newColumnArr.length; i++) {
            if (i == newColumnArr.length - 1) {
                // 最后一个
                createNewTableStr.append(newColumnArr[i] + ")");
            } else {
                // 不是最后一个
                createNewTableStr.append(newColumnArr[i] + ",");
            }
        }

        db.execSQL(createNewTableStr.toString());*/

        // 复制旧表数据到新表
        StringBuffer copySQLStr = new StringBuffer();
        copySQLStr.append("insert into " + oldTableName + " select * ");
        // 有多少个新的字段，就要预留多少个' '空值给新字段
        for (int i = 0; i < newColumnArr.length; i++) {
            if (i == newColumnArr.length - 1) {
                // 最后一个
                copySQLStr.append("' ' from " + tempTableName);
            } else {
                // 不是最后一个
                copySQLStr.append("' ',");
            }
        }

        db.execSQL(copySQLStr.toString());


        // 删除旧表
        db.execSQL("drop table " + tempTableName);

        // 关闭游标
        cursor.close();
    }


    /*class HistoryTable implements BaseColumns{

        //表名
        public static final String TABLE_NAME="history";
        //歌曲名
        public static final String COLUMN_SONG="songName";
        //歌手名
        public static final String COLUMN_SINGER="singerName";
        //歌曲类别（mv,mp3）
        public static final String COLUMN_TYPE="type";
        //歌曲id
        public static final String COLUMN_SONGID="songId";
        //播放时间
        public static final String COLUMN_DATE="date";
        //是否已经勾选
        public static final String COLUMN_ISCHECKED="isChecked";
        //歌手图像
        public static final String COLUMN_IMGHEAD="imgHead";
        //图片
        public static final String COLUMN_IMG="imgAlbumUrl";
        //前奏时间
        //public static final String COLUMN_QZTIME="qztime";
        //歌词地址
        public static final String COLUMN_GCURL="gcUrl";
        //播放量
        public static final String COLUMN_NUM="num";
        //伴奏地址
        public static final String COLUMN_BZURL="bzUrl";
        //原唱地址
        public static final String COLUMN_YCURL="ycUrl";

    }*/
}
