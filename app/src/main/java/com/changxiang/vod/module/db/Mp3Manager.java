//package com.changxiang.vod.module.db;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.quchangkeji.tosingpk.module.entry.SongDetail;
//
///**
// * Created by 15976 on 2017/4/14.
// */
//
//public class Mp3Manager implements IDownloadTable {
//    private DownlaodMp3Helper dbHelper;
//    private static Mp3Manager manager;
//
//    private Mp3Manager(Context context) {
//        dbHelper = new DownlaodMp3Helper(context);
//    }
//
//    public static Mp3Manager getManager(Context context) {
//        //防止太多线程同时操作该方法引起的空指针
//        if (manager == null) {
//            synchronized (Mp3Manager.class) {
//                manager = new Mp3Manager(context);
//            }
//        }
//        return manager;
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
//                String sql = "insert into mp3(songId,songName,singerName,type,num,krcPath,lrcPath,accPath,oriPath,imgAlbumUrl,imgHead,qztime) " +
//                        "values (?,?,?,?,?,?,?,?,?,?,?,?)";
//                Object[] bindArgs = {detail.getSongId(), detail.getSongName(), detail.getSingerName(), detail.getType(), detail.getNum(), detail.getKrcPath(),
//                        detail.getLrcPath(), detail.getAccPath(), detail.getOriPath(), detail.getImgAlbumUrl(), detail.getImgHead(), detail.getQzTime()};
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
//     * 判断是否添加到数据库
//     *
//     * @param songId    歌曲名
//     * @param musicType 歌曲类型
//     * @return
//     */
//    public boolean isExist(String songId, String musicType) {
//        Cursor cursor = null;
//        SQLiteDatabase database = null;
//        int count = 0;
//        try {
//            database = dbHelper.getReadableDatabase();
//            String sql = "select count(*)  from " + MP3_TABLE_NAME + " where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
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
//    /**
//     * 删除歌曲的下载记录
//     *
//     * @param songId
//     * @param musicType
//     */
//    public void deleteMp3Song(String songId, String musicType) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        try {
//            if (songId != null && musicType != null) {
//                database.delete(MP3_TABLE_NAME, COLUMN_SONGID + "=? and " + COLUMN_TYPE + "= ?", new String[]{songId, musicType});
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
//     * 更新全部下载完成的标识
//     *
//     * @param field         全部下载完成的标识
//     * @param isDownloadTag ALL_FILE_DOWNLOAD（2）标识文件全部下载完成
//     * @param songId
//     * @param musicType
//     */
//    public void updateSong(String field, int isDownloadTag, String songId, String musicType) {
//        if (field == null || isDownloadTag == 0 || songId == null || musicType == null) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            String sql = "update " + MP3_TABLE_NAME + " set " + field + "=? where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
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
//    /**
//     * 更新一个字段
//     *
//     * @param field     数据库字段
//     * @param path      需要修改的信息
//     * @param songId    歌曲id
//     * @param musicType 歌曲类型
//     */
//    public void updateSong(String field, String path, String songId, String musicType) {
//        if (field == null || path == null || songId == null || musicType == null) {
//            return;
//        }
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        try {
//            String sql = "update " + MP3_TABLE_NAME + " set " + field + "=? where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
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
//                    String sql = "update " + MP3_TABLE_NAME + " set " + COLUMN_IMG + "=? where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
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
//    }
//
//    /**
//     * 查询歌曲(全部字段)
//     *
//     * @param songId1
//     * @param musicType1
//     * @return
//     */
//    public SongDetail selectMp3Song(String songId1, String musicType1) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select * from " + MP3_TABLE_NAME + " where " + COLUMN_SONGID + "=? and " + COLUMN_TYPE + "=?";
//        Cursor cursor = null;
//        SongDetail songDetail = new SongDetail();
//        try {
//            cursor = database.rawQuery(sql, new String[]{songId1, musicType1});
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
//    //根据歌曲id和类型判断文件是否都已下载,COLUMN_IS_ALL_DOWNLOAD字段值为2表文件全部下载
//    public boolean isAllDownload(String songId1, String musicType1) {
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        String sql = "select * from "+MP3_TABLE_NAME+" where "+COLUMN_SONGID+"=? and "+COLUMN_TYPE+"=?";
//        Cursor cursor = null;
//        int isAllDownload=0;
//        try {
//            cursor = database.rawQuery(sql, new String[]{songId1, musicType1});
//            while (cursor.moveToNext()) {
////                String songName = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME));
//                isAllDownload = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ALL_DOWNLOAD));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (cursor!=null){
//                cursor.close();
//            }
//            database.close();
//        }
//
//        return isAllDownload == ALL_FILE_DOWNLOAD;
//    }
//}
