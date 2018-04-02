package com.changxiang.vod.module.db;

import java.io.Serializable;

/**
 * Created by 15976 on 2017/1/9.
 */

public class VodMedia implements Serializable {



    //    SONGBM 歌曲编码
   String Songbm= "SONGBM";
    //    SONGNAME  歌曲名称
     String SongName="SONGNAME";
    //    ZS
    String Zs="ZS";
    //    SINGER 歌手
    String Singer="SINGER";
    //    SONGTYPE 歌曲类型
     String SongType="SONGTYPE";
    //    LANG 歌曲语言
     String Lang="LANG";
    //服务器名称
     String Servername="ServerName";
    //            PATH 路径
   String Path="PATH";
    //文件名
    String FileName="FILENAME";
    //            拼写
     String Spell="SPELL";
//



    //体积(大小)
    String Volume="VOLUME";
    //最大音量
     String MaxVol="MAXVOL";
    //最低音量
     String MinVol="MINVOL";
    //音乐声带
     String MusicTrack="MUSICTRACK";
    //同唱
     String Chours="CHOURS";
    //保存
     String Exist="EXIST";
    //新歌
     String NewSong="NEWSONG";
    //跳动
     String Stroke="STROKE";
    //亮度
    String Brightness="BRIGHTNESS";
    //饱和度
     String Saturation ="SATURATION";




    //对比度
     String Contrast="CONTRAST";
    //顺序时间
     String Ordertimes="ORDERTIMES";
    //媒体信息
    String MediaInfo="MEDIAINFO";
    //云
     String Cloud="CLOUD";
    //云下载
     String CloudDown="CLOUDDOWN";
    //网络播放量
     String CloudPlay="CLOUDPLAY";
    //电影
     String Movie="MOVIE";
    //高清
     String Hd="HD";
    //媒体数组
     String MediAarray="MEDIAARRAY";


    public VodMedia() {
    }

    public String getSongbm() {
        return Songbm;
    }

    public void setSongbm(String songbm) {
        Songbm = songbm;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getZs() {
        return Zs;
    }

    public void setZs(String zs) {
        Zs = zs;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

    public String getSongType() {
        return SongType;
    }

    public void setSongType(String songType) {
        SongType = songType;
    }

    public String getLang() {
        return Lang;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    public String getServername() {
        return Servername;
    }

    public void setServername(String servername) {
        Servername = servername;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSpell() {
        return Spell;
    }

    public void setSpell(String spell) {
        Spell = spell;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getMaxVol() {
        return MaxVol;
    }

    public void setMaxVol(String maxVol) {
        MaxVol = maxVol;
    }

    public String getMinVol() {
        return MinVol;
    }

    public void setMinVol(String minVol) {
        MinVol = minVol;
    }

    public String getMusicTrack() {
        return MusicTrack;
    }

    public void setMusicTrack(String musicTrack) {
        MusicTrack = musicTrack;
    }

    public String getChours() {
        return Chours;
    }

    public void setChours(String chours) {
        Chours = chours;
    }

    public String getExist() {
        return Exist;
    }

    public void setExist(String exist) {
        Exist = exist;
    }

    public String getNewSong() {
        return NewSong;
    }

    public void setNewSong(String newSong) {
        NewSong = newSong;
    }

    public String getStroke() {
        return Stroke;
    }

    public void setStroke(String stroke) {
        Stroke = stroke;
    }

    public String getBrightness() {
        return Brightness;
    }

    public void setBrightness(String brightness) {
        Brightness = brightness;
    }

    public String getSaturation() {
        return Saturation;
    }

    public void setSaturation(String saturation) {
        Saturation = saturation;
    }

    public String getContrast() {
        return Contrast;
    }

    public void setContrast(String contrast) {
        Contrast = contrast;
    }

    public String getOrdertimes() {
        return Ordertimes;
    }

    public void setOrdertimes(String ordertimes) {
        Ordertimes = ordertimes;
    }

    public String getMediaInfo() {
        return MediaInfo;
    }

    public void setMediaInfo(String mediaInfo) {
        MediaInfo = mediaInfo;
    }

    public String getCloud() {
        return Cloud;
    }

    public void setCloud(String cloud) {
        Cloud = cloud;
    }

    public String getCloudDown() {
        return CloudDown;
    }

    public void setCloudDown(String cloudDown) {
        CloudDown = cloudDown;
    }

    public String getCloudPlay() {
        return CloudPlay;
    }

    public void setCloudPlay(String cloudPlay) {
        CloudPlay = cloudPlay;
    }

    public String getMovie() {
        return Movie;
    }

    public void setMovie(String movie) {
        Movie = movie;
    }

    public String getHd() {
        return Hd;
    }

    public void setHd(String hd) {
        Hd = hd;
    }

    public String getMediAarray() {
        return MediAarray;
    }

    public void setMediAarray(String mediAarray) {
        MediAarray = mediAarray;
    }
}
