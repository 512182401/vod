package com.changxiang.vod.module.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * 每一行歌词的实体类
 * Created by teana on 2016/10/22.
 */

public class KrcLine {
    KrcLineTime lineTime;//一句歌词对象的开始时间和持续时间
    List<KrcWord> wordTime;//单个歌词对象集合
    String lineStr;//一句歌词

    public KrcLine() {
        super();
        this.lineStr = "";
        this.lineTime = new KrcLineTime();
        this.wordTime = new ArrayList<>();

    }

    public KrcLine(String lineStr, KrcLineTime lineTime, List<KrcWord> wordTime) {
        super();
        this.lineStr = lineStr;
        this.lineTime = lineTime;
        this.wordTime = wordTime;
    }

    public String getLineStr() {
        return lineStr;
    }

    public void setLineStr(String lineStr) {
        this.lineStr = lineStr;
    }

    public KrcLineTime getLineTime() {
        return lineTime;
    }

    public void setLineTime(KrcLineTime lineTime) {
        this.lineTime = lineTime;
    }

    public List<KrcWord> getWordTime() {
        return wordTime;
    }

    public void setWordTime(List<KrcWord> wordTime) {
        this.wordTime = wordTime;
    }
}
