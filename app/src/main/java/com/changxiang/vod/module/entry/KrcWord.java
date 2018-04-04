package com.changxiang.vod.module.entry;

/**
 * 一个歌词中的单个歌词对象
 * Created by 15976 on 2017/7/3.
 */

public class KrcWord {

    String words;
    int startTime;
    int spanTime;
    int reserve;

    public KrcWord() {
    }

    public KrcWord(String words, int startTime, int spanTime, int reserve) {
        this.words = words;
        this.startTime = startTime;
        this.spanTime = spanTime;
        this.reserve = reserve;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getSpanTime() {
        return spanTime;
    }

    public void setSpanTime(int spanTime) {
        this.spanTime = spanTime;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }
}
