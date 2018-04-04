package com.changxiang.vod.module.ui.recently.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.db.IDownloadTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15976 on 2016/10/20.
 */

public class HistoryDBManager implements IDownloadTable {
    private static SQLiteDatabase db;
    private static HistoryHelper historyHelper;
    private static HistoryDBManager manager;
    private static Context mContext;

    //构造方法私有化，形成单例模式
    private HistoryDBManager(Context context) {
        historyHelper = new HistoryHelper(context);
    }

    //获取单例
    public static HistoryDBManager getHistoryManager() {
        //防止太多线程同时操作该方法引起的空指针
        if (manager == null) {
            synchronized (HistoryDBManager.class) {
                manager = new HistoryDBManager(mContext);
            }
        }
        return manager;
    }

    //在Application中初始化
    public static void init(Context context) {
        mContext = context;
    }

    private static ContentValues getContentValues(PlayedHistory history) {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(HistoryHelper.COLUMN_IS_ALL_DOWNLOAD, history.getis());
        contentValues.put(HistoryHelper.COLUMN_SONG_NAME, history.getName());
        contentValues.put(HistoryHelper.COLUMN_SINGER, history.getSingerName());
        contentValues.put(HistoryHelper.COLUMN_DATE, history.getDate());
        contentValues.put(HistoryHelper.COLUMN_TYPE, history.getType());
        contentValues.put(HistoryHelper.COLUMN_SONGID, history.getSongId());
        contentValues.put(HistoryHelper.COLUMN_IMGHEAD, history.getImgHead());
        contentValues.put(HistoryHelper.COLUMN_IMG, history.getImgAlbumUrl());
        contentValues.put(HistoryHelper.COLUMN_NUM, history.getNum());
        contentValues.put(HistoryHelper.COLUMN_ACC_URL, history.getAccPath());
        contentValues.put(HistoryHelper.COLUMN_ORI_URL, history.getOriPath());
        contentValues.put(HistoryHelper.COLUMN_LRC_URL, history.getLrcPath());
        contentValues.put(HistoryHelper.COLUMN_KRC_URL, history.getKrcPath());
        contentValues.put(HistoryHelper.COLUMN_FILE_SIZE, history.getSize());
        return contentValues;
    }

    public void insert(PlayedHistory history) {
        //插入之前需查询是否已存在
        PlayedHistory mPlayedHistory = query(history);
        if (mPlayedHistory!= null&&mPlayedHistory.getSongId()!=null) {
            LogUtils.sysout( "改歌曲已经播放过,需要删除++++++++++++++" );
            delete(mPlayedHistory);
        }
        Cursor cursor=null;
        try {
            String sql= "select count(*)  from "+HistoryHelper.TABLE_NAME_HISTORY+";";
            db = historyHelper.getWritableDatabase();
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            //大于50条，则删除最后一条，然后添加
            if (count>=50){
                String deleteSql="delete from "+HistoryHelper.TABLE_NAME_HISTORY+
                        " where date = (select date from "+HistoryHelper.TABLE_NAME_HISTORY+" order by date asc limit 1 );";
                db.execSQL(deleteSql);
            }
            db.insert(HistoryHelper.TABLE_NAME_HISTORY, null, getContentValues(history));
            LogUtils.w("TAG", "数据插入成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            if (db!=null){
                db.close();//如果不关闭，容易造成内存泄漏
            }
        }
    }

    public void delete(PlayedHistory history) {
        try {
            db = historyHelper.getWritableDatabase();
            db.delete(HistoryHelper.TABLE_NAME_HISTORY, HistoryHelper.COLUMN_SONGID + "=?", new String[]{history.getSongId()});
            LogUtils.w("TAG", "数据删除成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db!=null){
                db.close();//如果不关闭，容易造成内存泄漏
            }
        }
    }

    public void delete(String songId) {
        db = historyHelper.getWritableDatabase();
        db.delete(HistoryHelper.TABLE_NAME_HISTORY, HistoryHelper.COLUMN_SONGID + "=?", new String[]{songId});
        LogUtils.w("TAG", "数据删除成功");
        db.close();//如果不关闭，容易造成内存泄漏
    }

    /*public void update(PlayedHistory history){
        db=historyHelper.getWritableDatabase();
        db.update(HistoryHelper..TABLE_NAME, getContentValues(history), HistoryHelper..COLUMN_RNAME + "=?", new String[]{history.getRname()});
        LogUtils.w("TAG", "数据更新成功");
        db.close();//如果不关闭，容易造成内存泄漏
    }*/
    //查询单行记录
    public PlayedHistory query(PlayedHistory history) {
        //List<PlayedHistory> list = new ArrayList<>();
        db = historyHelper.getWritableDatabase();
        String sql = "select * from " + HistoryHelper.TABLE_NAME_HISTORY + " where " + HistoryHelper.COLUMN_SONGID + "='" + history.getSongId() + "'";
        Cursor cursor = db.rawQuery(sql, null);
        PlayedHistory temp = new PlayedHistory();
        try {
            while (cursor.moveToNext()) {
                String singer = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_SINGER));
                //int isChecked = cursor.getInt(cursor.getColumnIndex(HistoryHelper.COLUMN_ISCHECKED));
                String songName = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_SONG_NAME));
                String imgHead = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_IMGHEAD));
                int num = cursor.getInt(cursor.getColumnIndex(HistoryHelper.COLUMN_NUM));
                String bzUrl = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_ACC_URL));
                String ycUrl = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_ORI_URL));
                String gcUrl = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_LRC_URL));
                String image = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_IMG));
                String krcPath = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_KRC_URL));
                String type = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_TYPE));
                String songId = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_SONGID));
                String size = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_FILE_SIZE));
                long date = cursor.getLong(cursor.getColumnIndex(HistoryHelper.COLUMN_DATE));
                if (singer != null && songName != null && songId != null && type != null) {
                    //temp.setIsChecked(isChecked);
                    temp.setSingerName(singer);
                    temp.setName(songName);
                    temp.setType(type);
                    temp.setDate(date);
                    temp.setSongId(songId);
                    temp.setImgHead(imgHead);
                    temp.setImgAlbumUrl(image);
                    temp.setNum(num);
                    temp.setAccPath(bzUrl);
                    temp.setOriPath(ycUrl);
                    temp.setLrcPath(gcUrl);
                    temp.setKrcPath(krcPath);
                    temp.setDate(date);
                    temp.setSize(size);
                }
            }
        } catch (Exception e) {

        } finally {
            cursor.close();
            db.close();//如果不关闭，容易造成内存泄漏

        }

        return temp;
    }

    public List<PlayedHistory> queryAll() {
        LogUtils.sysout( "1111111111111111111" );
        int count = 0;
        List<PlayedHistory> histories = new ArrayList<>();
        db = historyHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_HISTORY, null);
        try {
            while (cursor.moveToNext()) {
                count++;
                String singer = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_SINGER));
                //int isChecked = cursor.getInt(cursor.getColumnIndex(HistoryHelper.COLUMN_ISCHECKED));
                String songName = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_SONG_NAME));
                String imgHead = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_IMGHEAD));
                int num = cursor.getInt(cursor.getColumnIndex(HistoryHelper.COLUMN_NUM));
                String bzUrl = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_ACC_URL));
                String ycUrl = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_ORI_URL));
                String gcUrl = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_LRC_URL));
                String image = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_IMG));
                String krcPath = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_KRC_URL));
                String type = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_TYPE));
                String songId = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_SONGID));
                String size = cursor.getString(cursor.getColumnIndex(HistoryHelper.COLUMN_FILE_SIZE));
                long date = cursor.getLong(cursor.getColumnIndex(HistoryHelper.COLUMN_DATE));
                if (singer != null && songName != null && songId != null &&"audio".equals(type)) {
                    PlayedHistory temp = new PlayedHistory();
                    //temp.setIsChecked(isChecked);
                    temp.setSingerName(singer);
                    temp.setName(songName);
                    temp.setType(type);
                    temp.setDate(date);
                    temp.setSongId(songId);
                    temp.setImgHead(imgHead);
                    temp.setImgAlbumUrl(image);
                    temp.setNum(num);
                    temp.setAccPath(bzUrl);
                    temp.setOriPath(ycUrl);
                    temp.setLrcPath(gcUrl);
                    temp.setKrcPath(krcPath);
                    temp.setDate(date);
                    histories.add(temp);
                    temp.setSize(size);
                }
            }
        } catch (Exception e) {

        } finally {
            cursor.close();
            db.close();
        }
        LogUtils.sysout( "查询数据库+++++++++++"+count );
        return histories;
    }
}
