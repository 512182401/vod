package com.changxiang.vod.module.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.changxiang.vod.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static com.changxiang.vod.module.db.IComposeTable.COMPOSE_IMAGE;
import static com.changxiang.vod.module.db.IComposeTable.COMPOSE_REMARK;
import static com.changxiang.vod.module.db.IComposeTable.CREATE_DATE;


//import com.quchangkeji.tosing.common.utils.LogUtils;
/**
 *
 */

/**
 * Created by 15976 on 2017/1/9.
 */

public class LocalMusicManager implements ILocalTable {


    private LocalMusicHelper dbHelper;

    private static LocalMusicManager manager;

    private LocalMusicManager(Context context) {
        dbHelper = new LocalMusicHelper(context);
    }

    public static LocalMusicManager getComposeManager(Context context) {
        //防止太多线程同时操作该方法引起的空指针
        if (manager == null) {
            synchronized (ComposeManager.class) {
                manager = new LocalMusicManager(context);
            }
        }
        return manager;
    }


    //查询单条记录
    public VodMedia query(String createTime) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + CREATE_DATE + "=?";
        //Object[] bindArgs = {field, content};
        Cursor cursor = database.rawQuery(sql, new String[]{createTime});
        VodMedia listBean = new VodMedia();
        try {
            while (cursor.moveToNext()) {
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
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
    public List<VodMedia> queryAll() {
        List<VodMedia> VodMediaList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("select * from " + TABLE_NAME_LOCAL, null);
            while (cursor.moveToNext()) {
                VodMedia listBean = new VodMedia();
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
                VodMediaList.add(0, listBean);
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
        return VodMediaList;

    }


    //模糊查询(歌名)
    public List<VodMedia> fuzzyQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<VodMedia> VodMediaList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SONGNAME + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                VodMedia listBean = new VodMedia();
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
                VodMediaList.add(0, listBean);
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
        return VodMediaList;
    }

    //模糊查询(拼写)
    public List<VodMedia> SpellQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<VodMedia> VodMediaList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SONGNAME + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                VodMedia listBean = new VodMedia();
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
                VodMediaList.add(0, listBean);
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
        return VodMediaList;
    }

    //模糊查询(语言种类)
    public List<VodMedia> LangQuery(String search_context, int start, int end) {
        int selectstart = start;
        int selectend = end;
        int count = 0;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<VodMedia> VodMediaList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + LANG + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                count++;
                if (count < selectstart) {
                    continue;

                } else if (count > selectend) {
                    break;

                }
                VodMedia listBean = new VodMedia();
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
                VodMediaList.add(0, listBean);
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
        return VodMediaList;
    }

    //模糊查询(歌星)
    public List<VodMedia> SingerQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<VodMedia> VodMediaList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SINGER + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                VodMedia listBean = new VodMedia();
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
                VodMediaList.add(0, listBean);
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
        return VodMediaList;
    }

    //模糊查询(歌星)
    public List<VodMedia> SongTypeQuery(String search_context) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //List<WorksBean> worksBeanList=new ArrayList<>();
        List<VodMedia> VodMediaList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME_LOCAL + " where " + SONGTYPE + " like ? ";
        String[] selectionArgs = new String[]{"%" + search_context + "%"};
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                VodMedia listBean = new VodMedia();
                String Songbm = cursor.getString(cursor.getColumnIndex(SONGBM));
                String SongName = cursor.getString(cursor.getColumnIndex(SONGNAME));
                String Zs = cursor.getString(cursor.getColumnIndex(ZS));
                String Singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String SongType = cursor.getString(cursor.getColumnIndex(SONGTYPE));

                String Lang = cursor.getString(cursor.getColumnIndex(LANG));
                String Servername = cursor.getString(cursor.getColumnIndex(ServerName));
                String Path = cursor.getString(cursor.getColumnIndex(PATH));
                String FileName = cursor.getString(cursor.getColumnIndex(FILENAME));
                String Spell = cursor.getString(cursor.getColumnIndex(SPELL));


                String Volume = cursor.getString(cursor.getColumnIndex(VOLUME));
                String MaxVol = cursor.getString(cursor.getColumnIndex(MAXVOL));
                String MinVol = cursor.getString(cursor.getColumnIndex(MINVOL));
                String MusicTrack = cursor.getString(cursor.getColumnIndex(MUSICTRACK));
                String Chours = cursor.getString(cursor.getColumnIndex(CHOURS));

                String Exist = cursor.getString(cursor.getColumnIndex(EXIST));
                String NewSong = cursor.getString(cursor.getColumnIndex(NEWSONG));
                String Stroke = cursor.getString(cursor.getColumnIndex(STROKE));
                String Brightness = cursor.getString(cursor.getColumnIndex(BRIGHTNESS));
                String Saturation = cursor.getString(cursor.getColumnIndex(SATURATION));


                String Contrast = cursor.getString(cursor.getColumnIndex(CONTRAST));
                String Ordertimes = cursor.getString(cursor.getColumnIndex(ORDERTIMES));
                String MediaInfo = cursor.getString(cursor.getColumnIndex(MEDIAINFO));
                String Cloud = cursor.getString(cursor.getColumnIndex(CLOUD));
                String CloudDown = cursor.getString(cursor.getColumnIndex(CLOUDDOWN));

                String CloudPlay = cursor.getString(cursor.getColumnIndex(CLOUDPLAY));
                String Movie = cursor.getString(cursor.getColumnIndex(MOVIE));
                String Hd = cursor.getString(cursor.getColumnIndex(HD));
                String MediAarray = cursor.getString(cursor.getColumnIndex(MEDIAARRAY));

                listBean.setSongbm(Songbm);
                listBean.setSongName(SongName);
                listBean.setZs(Zs);
                listBean.setSinger(Singer);
                listBean.setSongType(SongType);
                listBean.setLang(Lang);
                listBean.setServername(Servername);
                listBean.setPath(Path);
                listBean.setFileName(FileName);
                listBean.setSpell(Spell);


                listBean.setVolume(Volume);
                listBean.setMaxVol(MaxVol);
                listBean.setMinVol(MinVol);
                listBean.setMusicTrack(MusicTrack);
                listBean.setChours(Chours);

                listBean.setExist(Exist);
                listBean.setNewSong(NewSong);
                listBean.setStroke(Stroke);
                listBean.setBrightness(Brightness);
                listBean.setSaturation(Saturation);

                listBean.setContrast(Contrast);
                listBean.setOrdertimes(Ordertimes);
                listBean.setMediaInfo(MediaInfo);
                listBean.setCloud(Cloud);
                listBean.setCloudDown(CloudDown);

                listBean.setCloudPlay(CloudPlay);
                listBean.setMovie(Movie);
                listBean.setHd(Hd);
                listBean.setMediAarray(MediAarray);
                VodMediaList.add(0, listBean);
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
        return VodMediaList;
    }
}
