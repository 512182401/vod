package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 15976 on 2017/2/20.
 */

public class WorksManager {

    private WorksHelper dbHelper;

    private static WorksManager manager;

    private WorksManager(Context context) {
        dbHelper = new WorksHelper(context);
    }

    public static WorksManager getWorksManager(Context context){
        //防止太多线程同时操作该方法引起的空指针
        if (manager == null) {
            synchronized (WorksManager.class) {
                manager = new WorksManager(context);
            }
        }
        return manager;
    }

    /**
     * 判断作品是否存在
     * @param workId 作品id
     * @param musicType 作品类型
     * @return
     */
    public boolean isExit(String workId, String musicType){
        Cursor cursor=null;
        SQLiteDatabase database=null;
        int count=0;
        try {
            database = dbHelper.getReadableDatabase();
            String sql = "select count(*)  from "+WorksHelper.WorksTable.TABLE_NAME+" where "+WorksHelper.WorksTable.COLUMN_WORK_ID+
                    "=? and "+WorksHelper.WorksTable.COLUMN_MUSIC_TYPE+"=?";
            cursor = database.rawQuery(sql, new String[]{workId, musicType});
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }if (database!=null){
                database.close();
            }
        }
        return count != 0;
    }

    public void insertWork(WorksBean worksBean){
        if (worksBean == null) {
            return;
        }
        WorksBean bean=queryWork(worksBean.getWorkId(),worksBean.getMusicType());
        if (bean!=null&&bean.getSongName()!=null){
            return;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            if (worksBean.getWorkId()!=null&&worksBean.getMusicType()!=null){
                String sql = "insert into "+ WorksHelper.WorksTable.TABLE_NAME+"(workId,musicType," +
                        "imgAlbumUrl,ycVipId,listenNum,flowerNum,commentNum,transNum,Songname,workType,other,songId,songType,skipPrelude) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                Object[] bindArgs = {worksBean.getWorkId(), worksBean.getMusicType(), worksBean.getImgAlbumUrl(), worksBean.getYcVipId(),
                        worksBean.getListenNum(), worksBean.getFlowerNum(), worksBean.getCommentNum(),worksBean.getTransNum(),worksBean.getSongName(),
                        worksBean.getWorkType(),worksBean.getOther(),worksBean.getSongId(),worksBean.getSongType(),worksBean.getSkipPrelude()};
                database.execSQL(sql, bindArgs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.close();
            }
        }
    }

    /**
     * 删除本地作品
     * @param workId 作品id
     * @param musicType 作品类型
     */
    public void deleteWorks(String workId, String musicType){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            if (workId!=null&&musicType!=null){
                database.delete(WorksHelper.WorksTable.TABLE_NAME, WorksHelper.WorksTable.COLUMN_WORK_ID+"=? and "+
                        WorksHelper.WorksTable.COLUMN_MUSIC_TYPE+"= ?", new String[]{workId,musicType});
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.close();
            }
        }
    }

    public void updateWorks(String field, String path, String workId, String musicType){
        if (field == null || path == null || workId == null ||musicType==null) {
            return;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String sql = "update "+WorksHelper.WorksTable.TABLE_NAME+" set " + field +
                    "=? where "+WorksHelper.WorksTable.COLUMN_WORK_ID+"=? and "+WorksHelper.WorksTable.COLUMN_MUSIC_TYPE+"=?";
            Object[] bindArgs = {path, workId, musicType};
            database.execSQL(sql, bindArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.close();
            }
        }
    }

    /**
     * 查询作品
     * @param workId1 作品id
     * @param musicType1 作品id
     * @return
     */
    public WorksBean queryWork(String workId1, String musicType1){
        if (workId1!=null&&musicType1!=null){
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String sql = "select * from "+WorksHelper.WorksTable.TABLE_NAME+" where "+WorksHelper.WorksTable.COLUMN_WORK_ID+"=? and "+
                    WorksHelper.WorksTable.COLUMN_MUSIC_TYPE+"=?";
            Cursor cursor = null;
            WorksBean worksBean = new WorksBean();
            try {
                cursor = database.rawQuery(sql, new String[]{workId1, musicType1});
                while (cursor.moveToNext()) {
                    String workId = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_WORK_ID));
                    String musicType = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_MUSIC_TYPE));
                    String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_IMGALBUMURL));
                    String ycVipId = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_YCVIPID));
                    int listenNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_LISTEN_NUM));
                    int flowerNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_FLOWER_NUM));
                    int commentNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_COMMENT_NUM));
                    int transNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_TRANS_NUM));
                    String songName = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_NAME));
                    String workType = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_WORK_TYPE));
                    String other = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_OTHER));
                    String songId=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_ID));
                    String songType=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_TYPE));
                    String skip=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SKIP_PRELUDE));

                    worksBean.setWorkId(workId);
                    worksBean.setMusicType(musicType);
                    worksBean.setImgAlbumUrl(imgAlbumUrl);
                    worksBean.setYcVipId(ycVipId);
                    worksBean.setListenNum(listenNum);
                    worksBean.setFlowerNum(flowerNum);
                    worksBean.setCommentNum(commentNum);
                    worksBean.setTransNum(transNum);
                    worksBean.setSongName(songName);
                    worksBean.setWorkType(workType);
                    worksBean.setOther(other);
                    worksBean.setSongId(songId);
                    worksBean.setSongType(songType);
                    worksBean.setSkipPrelude(skip);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor!=null){
                    cursor.close();
                }
                database.close();//如果不关闭，容易造成内存泄漏
            }
            return worksBean;
        }else {
            return null;
        }
    }

    public List<WorksBean> queryAllWorks(){
        List<WorksBean> worksBeanList = new ArrayList<>();
        SQLiteDatabase database =null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("select * from " + WorksHelper.WorksTable.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                String workId = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_WORK_ID));
                String musicType = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_MUSIC_TYPE));
                String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_IMGALBUMURL));
                String ycVipId = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_YCVIPID));
                int listenNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_LISTEN_NUM));
                int flowerNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_FLOWER_NUM));
                int commentNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_COMMENT_NUM));
                int transNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_TRANS_NUM));
                String songName = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_NAME));
                String workType = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_WORK_TYPE));
                String other = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_OTHER));
                String songId=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_ID));
                String songType=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_TYPE));
                String skip=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SKIP_PRELUDE));

                if (workId != null && songName != null && workType != null && musicType != null) {
                    WorksBean worksBean = new WorksBean();
                    worksBean.setWorkId(workId);
                    worksBean.setMusicType(musicType);
                    worksBean.setImgAlbumUrl(imgAlbumUrl);
                    worksBean.setYcVipId(ycVipId);
                    worksBean.setListenNum(listenNum);
                    worksBean.setFlowerNum(flowerNum);
                    worksBean.setCommentNum(commentNum);
                    worksBean.setTransNum(transNum);
                    worksBean.setSongName(songName);
                    worksBean.setWorkType(workType);
                    worksBean.setOther(other);
                    worksBean.setSongId(songId);
                    worksBean.setSongType(songType);
                    worksBean.setSkipPrelude(skip);
                    worksBeanList.add(0,worksBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor!=null){
                cursor.close();
            }if (database!=null){
                database.close();
            }
        }
        return worksBeanList;
    }

    //模糊查询
    public List<WorksBean> fuzzyQuery(String search_context){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<WorksBean> worksBeanList=new ArrayList<>();
        String sql = "select * from "+WorksHelper.WorksTable.TABLE_NAME+" where "+WorksHelper.WorksTable.COLUMN_SONG_NAME+" like ? ";
        String[] selectionArgs  = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                String workId = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_WORK_ID));
                String musicType = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_MUSIC_TYPE));
                String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_IMGALBUMURL));
                String ycVipId = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_YCVIPID));
                int listenNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_LISTEN_NUM));
                int flowerNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_FLOWER_NUM));
                int commentNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_COMMENT_NUM));
                int transNum = cursor.getInt(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_TRANS_NUM));
                String songName = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_NAME));
                String workType = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_WORK_TYPE));
                String other = cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_OTHER));
                String songId=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_ID));
                String songType=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SONG_TYPE));
                String skip=cursor.getString(cursor.getColumnIndex(WorksHelper.WorksTable.COLUMN_SKIP_PRELUDE));

                if (workId != null && songName != null && workType != null && musicType != null) {
                    WorksBean worksBean = new WorksBean();
                    worksBean.setWorkId(workId);
                    worksBean.setMusicType(musicType);
                    worksBean.setImgAlbumUrl(imgAlbumUrl);
                    worksBean.setYcVipId(ycVipId);
                    worksBean.setListenNum(listenNum);
                    worksBean.setFlowerNum(flowerNum);
                    worksBean.setCommentNum(commentNum);
                    worksBean.setTransNum(transNum);
                    worksBean.setSongName(songName);
                    worksBean.setWorkType(workType);
                    worksBean.setOther(other);
                    worksBean.setSongId(songId);
                    worksBean.setSongType(songType);
                    worksBean.setSkipPrelude(skip);
                    worksBeanList.add(worksBean);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            if (database!=null){
                database.close();//如果不关闭，容易造成内存泄漏
            }
        }
        return worksBeanList;
    }
}
