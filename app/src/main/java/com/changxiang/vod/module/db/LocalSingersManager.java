package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.changxiang.vod.module.db.IComposeTable.CREATE_DATE;
import static com.changxiang.vod.module.db.ISingerTable.ID;
import static com.changxiang.vod.module.db.ISingerTable.Name;
import static com.changxiang.vod.module.db.ISingerTable.Nationality;
import static com.changxiang.vod.module.db.ISingerTable.PINYIN;
import static com.changxiang.vod.module.db.ISingerTable.Picture;
import static com.changxiang.vod.module.db.ISingerTable.SongsCount;
import static com.changxiang.vod.module.db.ISingerTable.Strokes;
import static com.changxiang.vod.module.db.ISingerTable.TABLE_NAME_SINGERS;
import static com.changxiang.vod.module.db.ISingerTable.Type;
import static com.changxiang.vod.module.db.ISingerTable.Visible;
import static com.changxiang.vod.module.db.ISingerTable.ordertimes;
import static com.changxiang.vod.module.db.ISingerTable.zs;


//import com.quchangkeji.tosing.common.utils.LogUtils;
/**
 *
 */

/**
 * Created by 15976 on 2017/1/9.
 */

public class LocalSingersManager implements ILocalTable {


    private LocalSingersHelper dbHelper;

    private static LocalSingersManager manager;

    private LocalSingersManager(Context context) {
        dbHelper = new LocalSingersHelper(context);
    }

    public static LocalSingersManager getComposeManager(Context context) {
        //防止太多线程同时操作该方法引起的空指针
        if (manager == null) {
            synchronized (ComposeManager.class) {
                manager = new LocalSingersManager(context);
            }
        }
        return manager;
    }


    //查询单条记录
    public Singers query(String singername) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME_SINGERS + " where " + Name + "=?";
        //Object[] bindArgs = {field, content};
        Cursor cursor = database.rawQuery(sql, new String[]{singername});
        Singers listBean = new Singers();
        try {
            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();//如果不关闭，容易造成内存泄漏
        }
        return listBean;
    }

    //查询全部记录
    //查询全部记录
    public List<Singers> queryAll() {
        List<Singers> SingersList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("select * from " + TABLE_NAME_LOCAL, null);
            while (cursor.moveToNext()) {

                Singers listBean = new Singers();

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

                SingersList.add(0, listBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return SingersList;

    }


    //模糊查询(歌名)
    public List<Singers> fuzzyQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<Singers> SingersList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SONGNAME + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {

                Singers listBean = new Singers();

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

                SingersList.add(0, listBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();//如果不关闭，容易造成内存泄漏
            }
        }
        return SingersList;
    }

    //模糊查询(拼写)
    public List<Singers> SpellQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<Singers> SingersList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SONGNAME + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {

                Singers listBean = new Singers();

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

                SingersList.add(0, listBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();//如果不关闭，容易造成内存泄漏
            }
        }
        return SingersList;
    }

    //模糊查询(语言种类)
    public List<Singers> LangQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<Singers> SingersList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + LANG + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {

                Singers listBean = new Singers();

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

                SingersList.add(0, listBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();//如果不关闭，容易造成内存泄漏
            }
        }
        return SingersList;
    }

    //模糊查询(歌星)
    public List<Singers> SingerQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<Singers> SingersList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SINGER + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {

                Singers listBean = new Singers();

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

                SingersList.add(0, listBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();//如果不关闭，容易造成内存泄漏
            }
        }
        return SingersList;
    }

    //模糊查询(歌星)
    public List<Singers> SongTypeQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<Singers> SingersList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SONGTYPE + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {

                Singers listBean = new Singers();

                String id = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                String type = cursor.getString(cursor.getColumnIndex(Type));
                String picture = cursor.getString(cursor.getColumnIndex(Picture));
                String nationality = cursor.getString(cursor.getColumnIndex(Nationality));

                String songsCount = cursor.getString(cursor.getColumnIndex(SongsCount));
                String visible = cursor.getString(cursor.getColumnIndex(Visible));
                String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                String strokes = cursor.getString(cursor.getColumnIndex(Strokes));
                String Zs = cursor.getString(cursor.getColumnIndex(zs));
                String orderTimes = cursor.getString(cursor.getColumnIndex(ordertimes));
                listBean.setID(id);
                listBean.setName(name);
                listBean.setType(type);
                listBean.setPicture(picture);
                listBean.setNationality(nationality);

                listBean.setSongsCount(songsCount);
                listBean.setVisible(visible);
                listBean.setPINYIN(Pinyin);
                listBean.setStrokes(strokes);
                listBean.setZs(Zs);
                listBean.setOrdertimes(orderTimes);

                SingersList.add(0, listBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();//如果不关闭，容易造成内存泄漏
            }
        }
        return SingersList;
    }
}
