package com.changxiang.vod.module.entry;

import com.changxiang.vod.module.db.VodMedia;

import java.io.Serializable;

/**
 * 下载后
 * Created by 15976 on 2016/12/12.
 */

public class CameraSongDetail implements Serializable {

    String songName;//歌曲名
    String qzTime;//前奏时间
    String oriPath;//原唱地址
    String accPath;//伴奏地址
    String lrcPath;//歌词地址
    String krcPath;//krc地址
    String size;//文件总大小
    String recordAudio;//音轨录制地址
    String recordVideo;//视频录制地址
    String endReordTime;//录制时长
    String startReordTime;//相对于伴奏录制时长
    VodMedia mVodMedia;//音乐源

    String songDecode;//mp3解码之后地址
    String isDecode = "0";//解码状态； 0：未开始解码：1：解码成功；2：解码进行中；解码失败，4：该手机不能解码：
    String duration;//总时长（毫秒）
    String isSelect = "0";//1:代表选中   0：没选择

    public CameraSongDetail() {
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getQzTime() {
        return qzTime;
    }

    public void setQzTime(String qzTime) {
        this.qzTime = qzTime;
    }

    public String getOriPath() {
        return oriPath;
    }

    public void setOriPath(String oriPath) {
        this.oriPath = oriPath;
    }

    public String getAccPath() {
        return accPath;
    }

    public void setAccPath(String accPath) {
        this.accPath = accPath;
    }

    public String getLrcPath() {
        return lrcPath;
    }

    public void setLrcPath(String lrcPath) {
        this.lrcPath = lrcPath;
    }

    public String getKrcPath() {
        return krcPath;
    }

    public void setKrcPath(String krcPath) {
        this.krcPath = krcPath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRecordAudio() {
        return recordAudio;
    }

    public void setRecordAudio(String recordAudio) {
        this.recordAudio = recordAudio;
    }

    public String getRecordVideo() {
        return recordVideo;
    }

    public void setRecordVideo(String recordVideo) {
        this.recordVideo = recordVideo;
    }

    public String getEndReordTime() {
        return endReordTime;
    }

    public void setEndReordTime(String endReordTime) {
        this.endReordTime = endReordTime;
    }

    public String getStartReordTime() {
        return startReordTime;
    }

    public void setStartReordTime(String startReordTime) {
        this.startReordTime = startReordTime;
    }

    public VodMedia getmVodMedia() {
        return mVodMedia;
    }

    public void setmVodMedia(VodMedia mVodMedia) {
        this.mVodMedia = mVodMedia;
    }

    public String getSongDecode() {
        return songDecode;
    }

    public void setSongDecode(String songDecode) {
        this.songDecode = songDecode;
    }

    public String getIsDecode() {
        return isDecode;
    }

    public void setIsDecode(String isDecode) {
        this.isDecode = isDecode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }
}
