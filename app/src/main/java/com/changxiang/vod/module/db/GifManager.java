//package com.changxiang.vod.module.db;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
////import com.quchangkeji.tosing.module.entry.GIF;
//
///**
// * 管理gif下载信息
// * Created by 15976 on 2017/6/29.
// */
//
//public class GifManager {
//
//    static GifManager gifManager;
//    GifHelper gifHelper;
//
//    private GifManager(Context context) {
//        gifHelper = new GifHelper(context);
//    }
//
//    public static GifManager getGifManager(Context context) {
//        if (gifManager == null) {
//            synchronized (GifManager.class) {
//                if (gifManager == null) {
//                    gifManager = new GifManager(context);
//                }
//            }
//        }
//        return gifManager;
//    }
//
//    //记录是否存在
//    public boolean isExist(String id) {
//        Cursor cursor = null;
//        SQLiteDatabase database = null;
//        int count = 0;
//        if (id != null) {
//
//            try {
//                database = gifHelper.getReadableDatabase();
//                String sql = "select count(*)  from " + GifHelper.DB_NAME + " where " + GifHelper.COLUNM_TID + "=? ";
//                cursor = database.rawQuery(sql, new String[]{id});
//                cursor.moveToFirst();
//                count = cursor.getInt(0);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//                if (database != null) {
//                    database.close();
//                }
//            }
//        }
//
//        return count != 0;
//    }
//
//    //插入
//    public void insertGif(GIF gif) {
//        if (gif != null) {
//            String id = gif.getTid();
//            String url = gif.getTurl();
//            if (url == null && id == null) {
//                if (!isExist(id)) {
//                    SQLiteDatabase database = null;
//                    try {
//                        String sql = "insert into gif.db(tid,turl,gifbitmap,decoder,gifDuration,isdecode,gifFrames,other) " +
//                                "values (?,?,?,?,?,?,?,?)";
//                        Object[] bindArgs = {id, url, gif.getGifbitmap(), gif.getDecoder(),
//                                gif.getGifDuration(), gif.getIsdecode(), gif.getGifFrames(), gif.getOther()};
//                        database.execSQL(sql, bindArgs);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (database != null) {
//                            database.close();
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    //删除
//    public void deleteGif(String id){
//        if (id==null) {
//            return;
//        }
//        SQLiteDatabase database = null;
//        try {
//            database = gifHelper.getWritableDatabase();
//            database.delete(GifHelper.DB_NAME, GifHelper.COLUNM_TID+"=? ", new String[]{id});
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (database!=null){
//                database.close();
//            }
//        }
//    }
//
//    /**
//     * 修改
//     * @param field 需要修改的字段
//     * @param value 修改后的值
//     * @param id 唯一标识符
//     */
//    public void modifyGif(String field, String value, String id){
//        if (field!=null&&value!=null&&id!=null){
//            SQLiteDatabase database = gifHelper.getWritableDatabase();
//            try {
//                String sql = "update "+GifHelper.DB_NAME+" set " + field + "=? where "+GifHelper.COLUNM_TID+"=?";
//                Object[] bindArgs = {value, id};
//                database.execSQL(sql, bindArgs);
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                if (database!=null){
//                    database.close();
//                }
//            }
//        }
//    }
//
//    //查询
//    public GIF selectGif(String id){
//        Cursor cursor=null;
//        SQLiteDatabase database=null;
//        GIF gif=new GIF();
//        try {
//            database=gifHelper.getReadableDatabase();
//            String sql="select * from "+GifHelper.DB_NAME+" where "+GifHelper.COLUNM_TID+" = ?";
//            cursor=database.rawQuery(sql,new String[]{id});
//            while (cursor.moveToNext()){
//                String tid=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_TID));
//                String turl=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_TURL));
//                String gifbitmap=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_GIFBITMAP));
//                String decoder=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_DECODER));
//                String gifDuration=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_GIFDURATION));
//                String isdecode=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_ISDECODE));
//                String gifFrames=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_GIFFRAMES));
//                String other=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_OTHER));
//                gif.setTid(tid);
//                gif.setTurl(turl);
//                gif.setGifbitmap(gifbitmap);
//                gif.setDecoder(decoder);
//                gif.setGifDuration(gifDuration);
//                gif.setIsdecode(isdecode);
//                gif.setGifFrames(gifFrames);
//                gif.setOther(other);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database!=null){
//                database.close();//如果不关闭，容易造成内存泄漏
//            }
//        }
//        return gif;
//    }
//
//    //查询全部
//    public List<GIF> selectAll(){
//        List<GIF> downloadBeanList = new ArrayList<>();
//        SQLiteDatabase database =null;
//        Cursor cursor = null;
//        try {
//            database = gifHelper.getReadableDatabase();
//            cursor = database.rawQuery("select * from " + GifHelper.DB_NAME, null);
//            while (cursor.moveToNext()) {
//                String tid=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_TID));
//                String turl=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_TURL));
//                String gifbitmap=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_GIFBITMAP));
//                String decoder=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_DECODER));
//                String gifDuration=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_GIFDURATION));
//                String isdecode=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_ISDECODE));
//                String gifFrames=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_GIFFRAMES));
//                String other=cursor.getString(cursor.getColumnIndex(GifHelper.COLUNM_OTHER));
//
//                if (tid != null && turl != null) {
//                    GIF gif = new GIF();
//                    gif.setTid(tid);
//                    gif.setTurl(turl);
//                    gif.setGifbitmap(gifbitmap);
//                    gif.setDecoder(decoder);
//                    gif.setGifDuration(gifDuration);
//                    gif.setIsdecode(isdecode);
//                    gif.setGifFrames(gifFrames);
//                    gif.setOther(other);
//                    downloadBeanList.add(gif);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor!=null){
//                cursor.close();
//            }if (database!=null){
//                database.close();
//            }
//        }
//        return downloadBeanList;
//    }
//
//}
