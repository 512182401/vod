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
///**
// * Created by 15976 on 2016/12/6.
// */
//
//public class DownloadManager implements IDownloadTable {
//
//    private DownloadHelper dbHelper;
//
//    private static DownloadManager manager;
//
//    private DownloadManager(Context context) {
//        dbHelper = new DownloadHelper(context);
//    }
//
//    public static DownloadManager getDownloadManager(Context context) {
//        //防止太多线程同时操作该方法引起的空指针
//        if (manager == null) {
//            synchronized (DownloadManager.class) {
//                if (manager == null) {
//                    manager = new DownloadManager(context);
//                }
//            }
//        }
//        return manager;
//    }
//
//
//    /**
//     * 查看数据库中是否有数据
//     */
//    public boolean isHasInfors(String urlstr) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select count(*)  from download_info where url=?";
//        Cursor cursor = database.rawQuery(sql, new String[]{urlstr});
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
//        cursor.close();
//        return count == 0;
//    }
//
//    /**
//     * 36 * 保存 下载的具体信息 37
//     */
//    public void saveInfos(List<DownloadInfo> infos) {
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        for (DownloadInfo info : infos) {
//            String sql = "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url) values (?,?,?,?,?)";
//            Object[] bindArgs = {info.getThreadId(), info.getStartPos(),
//                    info.getEndPos(), info.getCompeleteSize(), info.getUrl()};
//            database.execSQL(sql, bindArgs);
//        }
//    }
//
//    /**
//     * 得到下载具体信息
//     */
//    public List<DownloadInfo> getInfos(String urlstr) {
//        List<DownloadInfo> list = new ArrayList<DownloadInfo>();
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select thread_id, start_pos, end_pos,compelete_size,url from download_info where url=?";
//        Cursor cursor = database.rawQuery(sql, new String[]{urlstr});
//        while (cursor.moveToNext()) {
//            DownloadInfo info = new DownloadInfo(cursor.getInt(0),
//                    cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
//                    cursor.getString(4));
//            list.add(info);
//        }
//        cursor.close();
//        return list;
//    }
//
//    /**
//     * 更新数据库中的下载信息
//     */
//    public void updataInfos(int threadId, int compeleteSize, String urlstr) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "update download_info set compelete_size=? where thread_id=? and url=?";
//        Object[] bindArgs = {compeleteSize, threadId, urlstr};
//        database.execSQL(sql, bindArgs);
//    }
//
//    /**
//     * 关闭数据库
//     */
//    public void closeDb() {
//        dbHelper.close();
//    }
//
//    /**
//     * 下载完成后删除数据库中的数据
//     */
//    public void delete(String url) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        database.delete("download_info", "url=?", new String[]{url});
//        database.close();
//    }
//
//
//    /**
//     * 判断是否添加到数据库
//     *
//     * @param songId    歌曲名
//     * @param musicType 歌手名
//     * @return
//     */
//    public boolean isExist(String songId, String musicType) {
//        Cursor cursor = null;
//        SQLiteDatabase database = null;
//        int count = 0;
//        try {
//            database = dbHelper.getReadableDatabase();
//            String sql = "select count(*)  from Song_detail where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//            cursor = database.rawQuery(sql, new String[]{songId, musicType});
//            cursor.moveToFirst();
//            count = cursor.getInt(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database != null) {
//                database.close();
//            }
//        }
//        return count != 0;
//    }
//
//    //根据歌曲id和类型判断文件是否都已下载,COLUMN_IS_ALL_DOWNLOAD字段值为2表文件全部下载
//    public boolean isAllDownload(String activityId, String songId1, String musicType1) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select * from Song_detail where " + ACTIVITY_ID + "=? and " +  COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//        Cursor cursor = null;
//        int isAllDownload = 0;
//        try {
//            cursor = database.rawQuery(sql, new String[]{activityId,songId1, musicType1});
//            while (cursor.moveToNext()) {
//
//                isAllDownload = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ALL_DOWNLOAD));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            database.close();
//        }
//
//        return isAllDownload == ALL_FILE_DOWNLOAD;
//    }
//
//    //查询所有伴奏文件全部下载的数据记录
//    public List<SongDetail> queryAllDownload() {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        List<SongDetail> listBeanList = new ArrayList<>();
//        String sql = "select * from " + TABLE_NAME + " where " + COLUMN_IS_ALL_DOWNLOAD + " = 2";
//        Cursor cursor = null;
//        try {
//            cursor = database.rawQuery(sql, null);
//            while (cursor.moveToNext()) {
//                String songName = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME));
//                String singerName = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER));
//                String songId = cursor.getString(cursor.getColumnIndex(COLUMN_SONGID));
//                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
//                int num = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM));
//                String krcPath = cursor.getString(cursor.getColumnIndex(COLUMN_KRC_URL));
//                String lrcPath = cursor.getString(cursor.getColumnIndex(COLUMN_LRC_URL));
//                String accPath = cursor.getString(cursor.getColumnIndex(COLUMN_ACC_URL));
//                String oriPath = cursor.getString(cursor.getColumnIndex(COLUMN_ORI_URL));
//                String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMG));
//                String imgHead = cursor.getString(cursor.getColumnIndex(COLUMN_IMGHEAD));
//                int isAllDownload = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ALL_DOWNLOAD));
//                String qzTime = cursor.getString(cursor.getColumnIndex(COLUMN_QZTIME));
//                String size = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_SIZE));
//                String activityId = cursor.getString(cursor.getColumnIndex(ACTIVITY_ID));
//                if (singerName != null && songName != null && songId != null && type != null) {
//                    SongDetail songDetail = new SongDetail();
//                    songDetail.setSongId(songId);
//                    songDetail.setSongName(songName);
//                    songDetail.setSingerName(singerName);
//                    songDetail.setType(type);
//                    songDetail.setNum(num);
//                    songDetail.setKrcPath(krcPath);
//                    songDetail.setLrcPath(lrcPath);
//                    songDetail.setAccPath(accPath);
//                    songDetail.setOriPath(oriPath);
//                    songDetail.setImgAlbumUrl(imgAlbumUrl);
//                    songDetail.setImgHead(imgHead);
//                    songDetail.setQzTime(qzTime);
//                    songDetail.setAllDownload(isAllDownload);
//                    songDetail.setSize(size);
//                    songDetail.setActivityId(activityId);
//                    listBeanList.add(0, songDetail);//按照记录添加的先后顺序
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database != null) {
//                database.close();//如果不关闭，容易造成内存泄漏
//            }
//        }
//        return listBeanList;
//
//    }
//
//    //模糊查询
//    public List<SongDetail> fuzzyQuery(String search_context) {
//        List<SongDetail> listBeanList = new ArrayList<>();
//        String sql = "select * from " + TABLE_NAME + " where " + COLUMN_SONG_NAME + " like ? or " + COLUMN_SINGER + " like ?";
//        String[] selectionArgs = new String[]{"%" + search_context + "%",
//                "%" + search_context + "%"};
//        SQLiteDatabase database = null;
//        Cursor cursor = null;
//        try {
//            database = dbHelper.getReadableDatabase();
//            cursor = database.rawQuery(sql, selectionArgs);
//            while (cursor.moveToNext()) {
//                String songName = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME));
//                String singerName = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER));
//                String songId = cursor.getString(cursor.getColumnIndex(COLUMN_SONGID));
//                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
//                int num = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM));
//                String krcPath = cursor.getString(cursor.getColumnIndex(COLUMN_KRC_URL));
//                String lrcPath = cursor.getString(cursor.getColumnIndex(COLUMN_LRC_URL));
//                String accPath = cursor.getString(cursor.getColumnIndex(COLUMN_ACC_URL));
//                String oriPath = cursor.getString(cursor.getColumnIndex(COLUMN_ORI_URL));
//                String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMG));
//                String imgHead = cursor.getString(cursor.getColumnIndex(COLUMN_IMGHEAD));
//                int isAllDownload = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ALL_DOWNLOAD));
//                String qzTime = cursor.getString(cursor.getColumnIndex(COLUMN_QZTIME));
//                String size = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_SIZE));
//                if (singerName != null && songName != null && songId != null && type != null) {
//                    SongDetail songDetail = new SongDetail();
//                    songDetail.setSongId(songId);
//                    songDetail.setSongName(songName);
//                    songDetail.setSingerName(singerName);
//                    songDetail.setType(type);
//                    songDetail.setNum(num);
//                    songDetail.setKrcPath(krcPath);
//                    songDetail.setLrcPath(lrcPath);
//                    songDetail.setAccPath(accPath);
//                    songDetail.setOriPath(oriPath);
//                    songDetail.setImgAlbumUrl(imgAlbumUrl);
//                    songDetail.setImgHead(imgHead);
//                    songDetail.setQzTime(qzTime);
//                    songDetail.setAllDownload(isAllDownload);
//                    songDetail.setSize(size);
//                    listBeanList.add(songDetail);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database != null) {
//                database.close();//如果不关闭，容易造成内存泄漏
//            }
//        }
//
//        return listBeanList;
//    }
//
//    /**
//     * @param field    数据库字段名
//     * @param filePath 要查询的文件
//     * @return
//     */
//    public boolean isDownload(String field, String filePath) {
//        Cursor cursor = null;
//        int count = 0;
//        SQLiteDatabase database = null;
//        try {
//            database = dbHelper.getReadableDatabase();
//            String sql = "select count(*)  from Song_detail where " + field + "=?";
//            cursor = database.rawQuery(sql, new String[]{filePath});
//            cursor.moveToFirst();
//            count = cursor.getInt(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database != null) {
//                database.close();//如果不关闭，容易造成内存泄漏
//            }
//        }
//        return count != 0;
//    }
//
//    /**
//     * 添加已下载文件的信息
//     */
//    public void insertSong(SongDetail detail) {
//        if (detail == null) {
//            return;
//        }
//        if (isExist(detail.getSongId(), detail.getType())) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            if (detail.getSongId() != null && detail.getType() != null) {
//                String sql = "insert into Song_detail(songId,songName,singerName,type,num,krcPath,lrcPath,accPath,oriPath,imgAlbumUrl,imgHead,qztime,fileSize,activityId) " +
//                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                Object[] bindArgs = {detail.getSongId(), detail.getSongName(), detail.getSingerName(), detail.getType(), detail.getNum(), detail.getKrcPath(),
//                        detail.getLrcPath(), detail.getAccPath(), detail.getOriPath(), detail.getImgAlbumUrl(), detail.getImgHead(), detail.getQzTime(), detail.getSize(), detail.getActivityId()};
//                database.execSQL(sql, bindArgs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    /**
//     * 更新一个字段
//     *
//     * @param field     数据库字段
//     * @param path      需要修改的信息
//     * @param songId    歌曲id
//     * @param musicType 歌曲类型
//     */
//    public void updateSong(String field, String path, String activityId, String songId, String musicType) {
//        if (field == null || path == null || songId == null || musicType == null || activityId == null) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            String sql = "update Song_detail set " + field + "=? where " + ACTIVITY_ID + "=? and " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//            Object[] bindArgs = {path, activityId,songId, musicType};
//            database.execSQL(sql, bindArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    /**
//     * 更新一个字段,把一个字段设置为空（记录存在，文件不存在时使用）
//     *
//     * @param field     数据库字段
//     * @param path      需要修改的信息
//     * @param songId    歌曲id
//     * @param musicType 歌曲类型
//     */
//    public void setFieldNull(String field, String path, String songId, String musicType) {
//        if (field == null || songId == null || musicType == null) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            String sql = "update Song_detail set " + field + "=? where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//            Object[] bindArgs = {path, songId, musicType};
//            database.execSQL(sql, bindArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    /**
//     * 更新除了地址以外的全部字段
//     *
//     * @param songDetail
//     */
//    public void updateSong(SongDetail songDetail) {
//
//        if (songDetail != null) {
//            String songId = songDetail.getSongId();
//            String musicType = songDetail.getType();
//            String songName = songDetail.getSongName();
//            String singerName = songDetail.getSingerName();
//            int num = songDetail.getNum();//点播量
//            String imgAlbumUrl = songDetail.getImgAlbumUrl();//封面
//            String imgHead = songDetail.getImgHead();//歌手头像
//            if (songId != null && musicType != null && songName != null && singerName != null && imgAlbumUrl != null && imgHead != null && num > 0) {
//                SQLiteDatabase database = dbHelper.getWritableDatabase();
//                try {
//                    String sql = "update Song_detail set " + COLUMN_IMG + "=? where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//                    Object[] bindArgs = {imgAlbumUrl, songId, musicType};
//                    database.execSQL(sql, bindArgs);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (database != null) {
//                        database.close();
//                    }
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 更新全部下载完成的标识
//     *
//     * @param field         全部下载完成的标识
//     * @param isDownloadTag ALL_FILE_DOWNLOAD（2）标识文件全部下载完成
//     * @param songId
//     * @param musicType
//     */
//    public void updateSong(String field, int isDownloadTag, String songId, String musicType) {
//        if (field == null || songId == null || musicType == null) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            String sql = "update Song_detail set " + field + "=? where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//            Object[] bindArgs = {isDownloadTag, songId, musicType};
//            database.execSQL(sql, bindArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//
//    /**
//     * 删除歌曲的下载记录
//     *
//     * @param songId
//     * @param musicType
//     */
//    public void deleteLocalSong(String songId, String musicType) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        try {
//            if (songId != null && musicType != null) {
//                database.delete(TABLE_NAME, COLUMN_SONGID + "=? and " + COLUMN_TYPE + "= ?", new String[]{songId, musicType});
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    /**
//     * 查询歌曲(全部字段)
//     *
//     * @param songId1
//     * @param musicType1
//     * @return
//     */
//    public SongDetail selectSong(String activityId1, String songId1, String musicType1) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select * from " + TABLE_NAME + " where " + ACTIVITY_ID + "=? and " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//        Cursor cursor = null;
//        SongDetail songDetail = new SongDetail();
//        try {
//            cursor = database.rawQuery(sql, new String[]{activityId1, songId1, musicType1});
//            while (cursor.moveToNext()) {
//                String songName = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME));
//                String singerName = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER));
//                String songId = cursor.getString(cursor.getColumnIndex(COLUMN_SONGID));
//                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
//                int num = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM));
//                String krcPath = cursor.getString(cursor.getColumnIndex(COLUMN_KRC_URL));
//                String lrcPath = cursor.getString(cursor.getColumnIndex(COLUMN_LRC_URL));
//                String accPath = cursor.getString(cursor.getColumnIndex(COLUMN_ACC_URL));
//                String oriPath = cursor.getString(cursor.getColumnIndex(COLUMN_ORI_URL));
//                String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMG));
//                String imgHead = cursor.getString(cursor.getColumnIndex(COLUMN_IMGHEAD));
//                String qzTime = cursor.getString(cursor.getColumnIndex(COLUMN_QZTIME));
//                String size = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_SIZE));
//                String activityId = cursor.getString(cursor.getColumnIndex(ACTIVITY_ID));
//                String isDecode = cursor.getString(cursor.getColumnIndex(IS_DECODE));
//                String songDecode = cursor.getString(cursor.getColumnIndex(SONG_DECODE));
//                int isAllDownload = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ALL_DOWNLOAD));
//
//                songDetail.setSongId(songId);
//                songDetail.setSongName(songName);
//                songDetail.setSingerName(singerName);
//                songDetail.setType(type);
//                songDetail.setNum(num);
//                songDetail.setKrcPath(krcPath);
//                songDetail.setLrcPath(lrcPath);
//                songDetail.setAccPath(accPath);
//                songDetail.setOriPath(oriPath);
//                songDetail.setImgAlbumUrl(imgAlbumUrl);
//                songDetail.setImgHead(imgHead);
//                songDetail.setQzTime(qzTime);
//                songDetail.setSize(size);
//                songDetail.setActivityId(activityId);
//                songDetail.setIsDecode(isDecode);
//                songDetail.setSongDecode(songDecode);
//                songDetail.setAllDownload(isAllDownload);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            database.close();//如果不关闭，容易造成内存泄漏
//        }
//        return songDetail;
//    }
//
//    /**
//     * 根据歌曲id获取歌词地址
//     *
//     * @param songId1    歌曲id
//     * @param musicType1 歌曲类型
//     * @return 本地歌词地址
//     */
//    public String getLrcUrl(String songId1, String musicType1) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select * from " + TABLE_NAME + " where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//        Cursor cursor = null;
//        String lrcPath = "";
//        try {
//            cursor = database.rawQuery(sql, new String[]{songId1, musicType1});
//            while (cursor.moveToNext()) {
//                lrcPath = cursor.getString(cursor.getColumnIndex(COLUMN_LRC_URL));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            database.close();//如果不关闭，容易造成内存泄漏
//        }
//        return lrcPath;
//    }
//
//    //查询本地歌曲(部分字段)
//    public ListBean selectLocalSong(String songName, String singerName, String musicType) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select * from " + TABLE_NAME + " where " + COLUMN_SONG_NAME + "=? and " + COLUMN_SINGER + "= ? and " + COLUMN_TYPE + "=?";
//        Cursor cursor = database.rawQuery(sql, new String[]{songName, singerName, musicType});
//
//        ListBean listBean = new ListBean();
//        //SongDetail songDetail = new SongDetail();
//        try {
//            while (cursor.moveToNext()) {
//
//                String originPath = cursor.getString(cursor.getColumnIndex(COLUMN_ORI_URL));
//                if (originPath != null && !originPath.equals("")) {//本地歌曲要保证原唱地址不能为空
//                    String songId = cursor.getString(cursor.getColumnIndex(COLUMN_SONGID));
//                    String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
//                    int num = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM));
//                    String imgAlbumUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMG));
//                    String imgHead = cursor.getString(cursor.getColumnIndex(COLUMN_IMGHEAD));
//                    listBean.setid(songId);
//                    listBean.setname(songName);
//                    listBean.setsingerName(singerName);
//                    listBean.setType(type);
//                    listBean.setnum(num);
//                    listBean.setimgAlbumUrl(imgAlbumUrl);
//                    listBean.setImgHead(imgHead);
//                }
//            }
//        } catch (Exception e) {
//
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            database.close();//如果不关闭，容易造成内存泄漏
//        }
//        return listBean;
//    }
//
//
//    //下载队列的数据库操作
//    //插入
//    public void insertNeedDownload(DownloadBean downloadBean) {
//        if (downloadBean == null) {
//            return;
//        }
//        if (isHasRecord(downloadBean.getSongId(), downloadBean.getType())) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            String sql = "insert into " + TABLE_NAME_DOWNLOAD + "(" + COLUMN_SONGID + "," + COLUMN_SONG_NAME + "," + COLUMN_SINGER + "," + COLUMN_TYPE + "," +
//                    COLUMN_POSITION + "," + COLUMN_IS_RECORD + "," + COLUMN_IS_FINISH + ")" +
//                    "values (?,?,?,?,?,?,?)";
//            Object[] bindArgs = {downloadBean.getSongId(), downloadBean.getSongName(), downloadBean.getSingerName(), downloadBean.getType(),
//                    downloadBean.getPosition(), downloadBean.getIsRecord(), downloadBean.getIsFinish()};
//            database.execSQL(sql, bindArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    //删除
//    public void deleteDownloadBean(String songId, String musicType) {
//        if (songId == null && musicType == "") {
//            return;
//        }
//        SQLiteDatabase database = null;
//        try {
//            database = dbHelper.getWritableDatabase();
//            database.delete(TABLE_NAME_DOWNLOAD, COLUMN_SONGID + "=? and " + COLUMN_TYPE + "= ?", new String[]{songId, musicType});
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    //删除全部的记录
//    public void deleteAllDownload() {
//
//        SQLiteDatabase database = null;
//        try {
//            database = dbHelper.getWritableDatabase();
//            database.delete(TABLE_NAME_DOWNLOAD, null, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (database != null) {
//                database.close();
//            }
//        }
//    }
//
//    //查询
//    public boolean isHasRecord(String songId, String musicType) {
//        Cursor cursor = null;
//        SQLiteDatabase database = null;
//        int count = 0;
//        try {
//            database = dbHelper.getReadableDatabase();
//            String sql = "select * from " + TABLE_NAME_DOWNLOAD + " where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//            cursor = database.rawQuery(sql, new String[]{songId, musicType});
//            cursor.moveToFirst();
//            count = cursor.getInt(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database != null) {
//                database.close();
//            }
//        }
//        return count != 0;
//    }
//
//    //查询所有
//    public List<DownloadBean> queryAll() {
//        List<DownloadBean> downloadBeanList = new ArrayList<>();
//        SQLiteDatabase database = null;
//        Cursor cursor = null;
//        try {
//            database = dbHelper.getReadableDatabase();
//            cursor = database.rawQuery("select * from " + TABLE_NAME_DOWNLOAD, null);
//            while (cursor.moveToNext()) {
//                String singer = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER));
//                int isRecord = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_RECORD));
//                int isFinish = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FINISH));
//                int position = cursor.getInt(cursor.getColumnIndex(COLUMN_POSITION));
//                String songName = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME));
//                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
//                String songId = cursor.getString(cursor.getColumnIndex(COLUMN_SONGID));
//
//                if (singer != null && songName != null && songId != null && type != null) {
//                    DownloadBean temp = new DownloadBean();
//                    temp.setIsFinish(isFinish);
//                    temp.setSingerName(singer);
//                    temp.setSongName(songName);
//                    temp.setType(type);
//                    temp.setSongId(songId);
//                    temp.setIsRecord(isRecord);
//                    temp.setPosition(position);
//                    downloadBeanList.add(temp);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (database != null) {
//                database.close();
//            }
//        }
//        return downloadBeanList;
//    }
//}
