package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.changxiang.vod.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

//import com.quchangkeji.tosing.common.utils.LogUtils;
/**
 *
 */

/**
 * Created by 15976 on 2017/1/9.
 */

public class ComposeManager implements IComposeTable {

    private ComposeHelper dbHelper;

    private static ComposeManager manager;

    private ComposeManager(Context context) {
        dbHelper = new ComposeHelper(context);
    }

    public static ComposeManager getComposeManager(Context context) {
        //防止太多线程同时操作该方法引起的空指针
        if (manager == null) {
            synchronized (ComposeManager.class) {
                manager = new ComposeManager(context);
            }
        }
        return manager;
    }

    //增加
    public void insertCompose(LocalCompose localCompose) {
        if (localCompose == null) {
            return;
        }
        LocalCompose compose1 = manager.query(localCompose.getCreateDate());
        if (compose1.getSongId() != null) {
            return;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();

//        public static final String ISEXPORT="isExport";
//        public static final String COMPOSE_FILE="Compose_file";
        try {
            String sql = "insert into " + TABLE_NAME_COMPOSE + "(" + COMPOSE_ID + "," + COMPOSE_NAME + "," + COMPOSE_IMAGE + "," + COMPOSE_REMARK + "," +
                    CREATE_DATE + "," + COMPOSE_TYPE + "," + COMPOSE_MUXERTASK + "," + ISUPLOAD + "," +
                    SONGID + "," + COMPOSE_BEGIN + "," + COMPOSE_FINISH + "," + COMPOSE_DELETE + "," +
                    COMPOSE_DURATION + "," + COMPOSE_OTHER + "," + COMPOSE_MUXERDECODE + "," +
                    RECORDURL + "," + BZURL + "," +
                    ISEXPORT + "," + COMPOSE_FILE + "," + COMPOSE_ACTIVITY + ")" +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            Object[] bindArgs = {localCompose.getCompose_id(), localCompose.getCompose_name(), localCompose.getCompose_image(), localCompose.getCompose_remark(),
                    localCompose.getCreateDate(), localCompose.getCompose_type(), localCompose.getCompose_MuxerTask(), localCompose.getIsUpload(),
                    localCompose.getSongId(), localCompose.getCompose_begin(), localCompose.getCompose_finish(), localCompose.getCompose_delete(),
                    localCompose.getAllDuration(), localCompose.getCompose_other(), localCompose.getCompose_MuxerDecode()
                    , localCompose.getRecordUrl(), localCompose.getBzUrl()
                    , localCompose.getIsExport(), localCompose.getCompose_file(), localCompose.getActivityId()};

            LogUtils.sysout("database.execSQL");
            database.execSQL(sql, bindArgs);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }

    }

    /**
     * 删除
     *
     * @param key COMPOSE_ID
     */

    public void deleteCompose(String key) {

        SQLiteDatabase database = null;
        try {
            database = dbHelper.getWritableDatabase();
            String sql = "delete from " + TABLE_NAME_COMPOSE + " where " + COMPOSE_ID + "=?";
            Object[] bindArgs = {key};
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 修改
     * key:时间作为key
     *
     * @param field   需要修改的字段
     * @param content 修改后的值
     */
    public void updateCompose(String key, String field, String content) {
        if (field == null || content == null) {
            return;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String sql = "update " + TABLE_NAME_COMPOSE + " set " + field + "=? where " + COMPOSE_ID + "=?";
            Object[] bindArgs = {content, key};
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            LogUtils.sysout("修改数据库失败");
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
                LogUtils.sysout("修改数据库");
            }
        }
    }
    /*public static final String COMPOSE_ID="compose_id";
    public static final String COMPOSE_NAME="compose_name";
    public static final String COMPOSE_IMAGE="compose_image";
    public static final String COMPOSE_REMARK="compose_remark";
    public static final String CREATE_DATE="createDate";
    public static final String COMPOSE_TYPE="compose_type";
    public static final String COMPOSE_MUXERDECODE="compose_muxerdecode";
    public static final String COMPOSE_MUXERTASK="compose_muxertask";
    public static final String ISUPLOAD="isUpload";
    public static final String SONGID="songId";
    public static final String COMPOSE_BEGIN="compose_begin";
    public static final String COMPOSE_FINISH="compose_finish";
    public static final String COMPOSE_DELETE="compose_delete";
    public static final String COMPOSE_ERRCODE="compose_errcode";
    public static final String COMPOSE_OTHER="compose_other";
    public static final String RECORDURL="recordUrl";
    public static final String BZURL="bzUrl";

    public static final String ISEXPORT="isExport";
    public static final String COMPOSE_FILE="Compose_file";*/

    //查询单条记录
    public LocalCompose query(String createTime) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME_COMPOSE + " where " + CREATE_DATE + "=?";
        //Object[] bindArgs = {field, content};
        Cursor cursor = database.rawQuery(sql, new String[]{createTime});
        LocalCompose listBean = new LocalCompose();
        try {
            while (cursor.moveToNext()) {
                //String songName1 = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME));
                String compose_id = cursor.getString(cursor.getColumnIndex(COMPOSE_ID));
                String compose_name = cursor.getString(cursor.getColumnIndex(COMPOSE_NAME));
                String compose_image = cursor.getString(cursor.getColumnIndex(COMPOSE_IMAGE));
                String compose_remark = cursor.getString(cursor.getColumnIndex(COMPOSE_REMARK));
                String createDate = cursor.getString(cursor.getColumnIndex(CREATE_DATE));
                String compose_type = cursor.getString(cursor.getColumnIndex(COMPOSE_TYPE));
                String compose_muxerdecode = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERDECODE));
                String compose_muxertask = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERTASK));
                String isUpload = cursor.getString(cursor.getColumnIndex(ISUPLOAD));
                String songId = cursor.getString(cursor.getColumnIndex(SONGID));
                String compose_begin = cursor.getString(cursor.getColumnIndex(COMPOSE_BEGIN));
                String compose_finish = cursor.getString(cursor.getColumnIndex(COMPOSE_FINISH));
                String compose_delete = cursor.getString(cursor.getColumnIndex(COMPOSE_DELETE));
                String allDuration = cursor.getString(cursor.getColumnIndex(COMPOSE_DURATION));
                String compose_other = cursor.getString(cursor.getColumnIndex(COMPOSE_OTHER));

                String recordUrl = cursor.getString(cursor.getColumnIndex(RECORDURL));
                String bzUrl = cursor.getString(cursor.getColumnIndex(BZURL));

                String isExport = cursor.getString(cursor.getColumnIndex(ISEXPORT));
                String Compose_file = cursor.getString(cursor.getColumnIndex(COMPOSE_FILE));
                String Compose_activity = cursor.getString(cursor.getColumnIndex(COMPOSE_ACTIVITY));

                listBean.setCompose_id(compose_id);
                listBean.setCompose_name(compose_name);
                listBean.setCompose_image(compose_image);
                listBean.setCompose_remark(compose_remark);
                listBean.setCreateDate(createDate);
                listBean.setCompose_type(compose_type);
                listBean.setCompose_MuxerDecode(compose_muxerdecode);
                listBean.setCompose_MuxerTask(compose_muxertask);
                listBean.setIsUpload(isUpload);
                listBean.setSongId(songId);
                listBean.setCompose_begin(compose_begin);
                listBean.setCompose_finish(compose_finish);
                listBean.setCompose_delete(compose_delete);
                listBean.setAllDuration(allDuration);
                listBean.setCompose_other(compose_other);

                listBean.setRecordUrl(recordUrl);
                listBean.setBzUrl(bzUrl);

                listBean.setIsExport(isExport);
                listBean.setCompose_file(Compose_file);
                listBean.setActivityId(Compose_activity);
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
    public List<LocalCompose> queryAll() {
        List<LocalCompose> downloadBeanList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("select * from " + TABLE_NAME_COMPOSE, null);
            while (cursor.moveToNext()) {
                String compose_id = cursor.getString(cursor.getColumnIndex(COMPOSE_ID));
                String compose_name = cursor.getString(cursor.getColumnIndex(COMPOSE_NAME));
                String compose_image = cursor.getString(cursor.getColumnIndex(COMPOSE_IMAGE));
                String compose_remark = cursor.getString(cursor.getColumnIndex(COMPOSE_REMARK));
                String createDate = cursor.getString(cursor.getColumnIndex(CREATE_DATE));
                String compose_type = cursor.getString(cursor.getColumnIndex(COMPOSE_TYPE));
                String compose_muxerdecode = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERDECODE));
                String compose_muxertask = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERTASK));
                String isUpload = cursor.getString(cursor.getColumnIndex(ISUPLOAD));
                String songId = cursor.getString(cursor.getColumnIndex(SONGID));
                String compose_begin = cursor.getString(cursor.getColumnIndex(COMPOSE_BEGIN));
                String compose_finish = cursor.getString(cursor.getColumnIndex(COMPOSE_FINISH));
                String compose_delete = cursor.getString(cursor.getColumnIndex(COMPOSE_DELETE));
                String allDuration = cursor.getString(cursor.getColumnIndex(COMPOSE_DURATION));
                String compose_other = cursor.getString(cursor.getColumnIndex(COMPOSE_OTHER));
                String recordUrl = cursor.getString(cursor.getColumnIndex(RECORDURL));
                String bzUrl = cursor.getString(cursor.getColumnIndex(BZURL));

                String isExport = cursor.getString(cursor.getColumnIndex(ISEXPORT));
                String Compose_file = cursor.getString(cursor.getColumnIndex(COMPOSE_FILE));
                String Compose_activity = cursor.getString(cursor.getColumnIndex(COMPOSE_ACTIVITY));
                if (compose_id != null) {
                    LocalCompose temp = new LocalCompose();
                    temp.setCompose_id(compose_id);
                    temp.setCompose_name(compose_name);
                    temp.setCompose_image(compose_image);
                    temp.setCompose_remark(compose_remark);
                    temp.setCreateDate(createDate);
                    temp.setCompose_type(compose_type);
                    temp.setCompose_MuxerDecode(compose_muxerdecode);
                    temp.setCompose_MuxerTask(compose_muxertask);
                    temp.setIsUpload(isUpload);
                    temp.setSongId(songId);
                    temp.setCompose_begin(compose_begin);
                    temp.setCompose_finish(compose_finish);
                    temp.setCompose_delete(compose_delete);
                    temp.setAllDuration(allDuration);
                    temp.setCompose_other(compose_other);

                    temp.setRecordUrl(recordUrl);
                    temp.setBzUrl(bzUrl);

                    temp.setIsExport(isExport);
                    temp.setCompose_file(Compose_file);
                    temp.setActivityId(Compose_activity);
//               //测试使用：用于
//                    updateCompose( compose_id, "isUpload", "0" );//标志数据库为已经上传
                    downloadBeanList.add(0, temp);
                }

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
        return downloadBeanList;

    }

    //没有删除的全部记录(主要用于本地，我的作品)
    public List<LocalCompose> queryNoDelect() {
        List<LocalCompose> downloadBeanList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            String sql = "select * from " + TABLE_NAME_COMPOSE + " where " + COMPOSE_MUXERTASK + "=? AND " + COMPOSE_DELETE + "=?";//,"+COMPOSE_DELETE+"=?
            //Object[] bindArgs = {field, content};
            cursor = database.rawQuery(sql, new String[]{"1", "0"});
            while (cursor.moveToNext()) {
                String compose_id = cursor.getString(cursor.getColumnIndex(COMPOSE_ID));
                String compose_name = cursor.getString(cursor.getColumnIndex(COMPOSE_NAME));
                String compose_image = cursor.getString(cursor.getColumnIndex(COMPOSE_IMAGE));
                String compose_remark = cursor.getString(cursor.getColumnIndex(COMPOSE_REMARK));
                String createDate = cursor.getString(cursor.getColumnIndex(CREATE_DATE));
                String compose_type = cursor.getString(cursor.getColumnIndex(COMPOSE_TYPE));
                String compose_muxerdecode = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERDECODE));
                String compose_muxertask = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERTASK));
                String isUpload = cursor.getString(cursor.getColumnIndex(ISUPLOAD));
                String songId = cursor.getString(cursor.getColumnIndex(SONGID));
                String compose_begin = cursor.getString(cursor.getColumnIndex(COMPOSE_BEGIN));
                String compose_finish = cursor.getString(cursor.getColumnIndex(COMPOSE_FINISH));
                String compose_delete = cursor.getString(cursor.getColumnIndex(COMPOSE_DELETE));
                String allDuration = cursor.getString(cursor.getColumnIndex(COMPOSE_DURATION));
                String compose_other = cursor.getString(cursor.getColumnIndex(COMPOSE_OTHER));
                String recordUrl = cursor.getString(cursor.getColumnIndex(RECORDURL));
                String bzUrl = cursor.getString(cursor.getColumnIndex(BZURL));

                String isExport = cursor.getString(cursor.getColumnIndex(ISEXPORT));
                String Compose_file = cursor.getString(cursor.getColumnIndex(COMPOSE_FILE));
                String Compose_activity = cursor.getString(cursor.getColumnIndex(COMPOSE_ACTIVITY));
                if (compose_id != null) {
                    LocalCompose temp = new LocalCompose();
                    temp.setCompose_id(compose_id);
                    temp.setCompose_name(compose_name);
                    temp.setCompose_image(compose_image);
                    temp.setCompose_remark(compose_remark);
                    temp.setCreateDate(createDate);
                    temp.setCompose_type(compose_type);
                    temp.setCompose_MuxerDecode(compose_muxerdecode);
                    temp.setCompose_MuxerTask(compose_muxertask);
                    temp.setIsUpload(isUpload);
                    temp.setSongId(songId);
                    temp.setCompose_begin(compose_begin);
                    temp.setCompose_finish(compose_finish);
                    temp.setCompose_delete(compose_delete);
                    temp.setAllDuration(allDuration);
                    temp.setCompose_other(compose_other);

                    temp.setRecordUrl(recordUrl);
                    temp.setBzUrl(bzUrl);

                    temp.setIsExport(isExport);
                    temp.setCompose_file(Compose_file);
                    temp.setActivityId(Compose_activity);
//                       //测试使用：用于
//                    updateCompose( compose_id, "isUpload", "" );//标志数据库为已经上传
                    downloadBeanList.add(0, temp);
                }

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
        return downloadBeanList;
    }

    //删除的全部记录(主要用于清除缓存)
    public List<LocalCompose> queryDelect() {
        List<LocalCompose> downloadBeanList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            String sql = "select * from " + TABLE_NAME_COMPOSE + " where " + COMPOSE_DELETE + "=?";//,"+COMPOSE_DELETE+"=?
            //Object[] bindArgs = {field, content};
            cursor = database.rawQuery(sql, new String[]{"1"});
            while (cursor.moveToNext()) {
                String compose_id = cursor.getString(cursor.getColumnIndex(COMPOSE_ID));
                String compose_name = cursor.getString(cursor.getColumnIndex(COMPOSE_NAME));
                String compose_image = cursor.getString(cursor.getColumnIndex(COMPOSE_IMAGE));
                String compose_remark = cursor.getString(cursor.getColumnIndex(COMPOSE_REMARK));
                String createDate = cursor.getString(cursor.getColumnIndex(CREATE_DATE));
                String compose_type = cursor.getString(cursor.getColumnIndex(COMPOSE_TYPE));
                String compose_muxerdecode = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERDECODE));
                String compose_muxertask = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERTASK));
                String isUpload = cursor.getString(cursor.getColumnIndex(ISUPLOAD));
                String songId = cursor.getString(cursor.getColumnIndex(SONGID));
                String compose_begin = cursor.getString(cursor.getColumnIndex(COMPOSE_BEGIN));
                String compose_finish = cursor.getString(cursor.getColumnIndex(COMPOSE_FINISH));
                String compose_delete = cursor.getString(cursor.getColumnIndex(COMPOSE_DELETE));
                String allDuration = cursor.getString(cursor.getColumnIndex(COMPOSE_DURATION));
                String compose_other = cursor.getString(cursor.getColumnIndex(COMPOSE_OTHER));
                String recordUrl = cursor.getString(cursor.getColumnIndex(RECORDURL));
                String bzUrl = cursor.getString(cursor.getColumnIndex(BZURL));
                String isExport = cursor.getString(cursor.getColumnIndex(ISEXPORT));
                String Compose_file = cursor.getString(cursor.getColumnIndex(COMPOSE_FILE));
                String Compose_activity = cursor.getString(cursor.getColumnIndex(COMPOSE_ACTIVITY));
                if (compose_id != null) {
                    LocalCompose temp = new LocalCompose();
                    temp.setCompose_id(compose_id);
                    temp.setCompose_name(compose_name);
                    temp.setCompose_image(compose_image);
                    temp.setCompose_remark(compose_remark);
                    temp.setCreateDate(createDate);
                    temp.setCompose_type(compose_type);
                    temp.setCompose_MuxerDecode(compose_muxerdecode);
                    temp.setCompose_MuxerTask(compose_muxertask);
                    temp.setIsUpload(isUpload);
                    temp.setSongId(songId);
                    temp.setCompose_begin(compose_begin);
                    temp.setCompose_finish(compose_finish);
                    temp.setCompose_delete(compose_delete);
                    temp.setAllDuration(allDuration);
                    temp.setCompose_other(compose_other);

                    temp.setRecordUrl(recordUrl);
                    temp.setBzUrl(bzUrl);

                    temp.setIsExport(isExport);
                    temp.setCompose_file(Compose_file);
                    temp.setActivityId(Compose_activity);

                    downloadBeanList.add(0, temp);
                }

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
        return downloadBeanList;
    }

    //模糊查询
    public List<LocalCompose> fuzzyQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<LocalCompose> downloadBeanList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_COMPOSE + " where " + COMPOSE_NAME + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                String compose_id = cursor.getString(cursor.getColumnIndex(COMPOSE_ID));
                String compose_name = cursor.getString(cursor.getColumnIndex(COMPOSE_NAME));
                String compose_image = cursor.getString(cursor.getColumnIndex(COMPOSE_IMAGE));
                String compose_remark = cursor.getString(cursor.getColumnIndex(COMPOSE_REMARK));
                String createDate = cursor.getString(cursor.getColumnIndex(CREATE_DATE));
                String compose_type = cursor.getString(cursor.getColumnIndex(COMPOSE_TYPE));
                String compose_muxerdecode = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERDECODE));
                String compose_muxertask = cursor.getString(cursor.getColumnIndex(COMPOSE_MUXERTASK));
                String isUpload = cursor.getString(cursor.getColumnIndex(ISUPLOAD));
                String songId = cursor.getString(cursor.getColumnIndex(SONGID));
                String compose_begin = cursor.getString(cursor.getColumnIndex(COMPOSE_BEGIN));
                String compose_finish = cursor.getString(cursor.getColumnIndex(COMPOSE_FINISH));
                String compose_delete = cursor.getString(cursor.getColumnIndex(COMPOSE_DELETE));
                String allDuration = cursor.getString(cursor.getColumnIndex(COMPOSE_DURATION));
                String compose_other = cursor.getString(cursor.getColumnIndex(COMPOSE_OTHER));
                String recordUrl = cursor.getString(cursor.getColumnIndex(RECORDURL));
                String bzUrl = cursor.getString(cursor.getColumnIndex(BZURL));
                String isExport = cursor.getString(cursor.getColumnIndex(ISEXPORT));
                String Compose_file = cursor.getString(cursor.getColumnIndex(COMPOSE_FILE));
                String Compose_activity = cursor.getString(cursor.getColumnIndex(COMPOSE_ACTIVITY));

                if (compose_id != null) {
                    LocalCompose temp = new LocalCompose();
                    temp.setCompose_id(compose_id);
                    temp.setCompose_name(compose_name);
                    temp.setCompose_image(compose_image);
                    temp.setCompose_remark(compose_remark);
                    temp.setCreateDate(createDate);
                    temp.setCompose_type(compose_type);
                    temp.setCompose_MuxerDecode(compose_muxerdecode);
                    temp.setCompose_MuxerTask(compose_muxertask);
                    temp.setIsUpload(isUpload);
                    temp.setSongId(songId);
                    temp.setCompose_begin(compose_begin);
                    temp.setCompose_finish(compose_finish);
                    temp.setCompose_delete(compose_delete);
                    temp.setAllDuration(allDuration);
                    temp.setCompose_other(compose_other);
                    temp.setRecordUrl(recordUrl);
                    temp.setBzUrl(bzUrl);
                    temp.setIsExport(isExport);
                    temp.setCompose_file(Compose_file);
                    temp.setActivityId(Compose_activity);
                    downloadBeanList.add(0, temp);
                }
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
        return downloadBeanList;
    }
}
