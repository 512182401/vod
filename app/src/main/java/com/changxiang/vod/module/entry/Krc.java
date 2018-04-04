package com.changxiang.vod.module.entry;

import java.util.List;

/**
 * Created by teana on 2016/10/22.
 */

public class Krc {

//    [id:$00000000]
//            [ar:信乐团]
//            [ti:北京一夜]
//            [by:韩佯Τé]
//            [hash:766fe295bf2722a9ede2abdd61d580c1]
//            [total:278438]
//            [sign:大家去北京玩一夜吧!!!!]
//    1：[max:4000]  最长一句歌词的时长；建议使用整百毫秒数，如：实际长度为3518，则取进位运算，为3600；
//            2：[lrc:0]  是否可以作为歌词显示（0：不可以，1可以）。
//            3：[piece:3]  采用哪一种频率等级（预留）
    private String id;//$00000000
    private String ar;//信乐团
    private String ti;//北京一夜
    private String by;//韩佯Τé
    private String hash;//766fe295bf2722a9ede2abdd61d580c1
    private String total;//278438
    private String sign;//大家去北京玩一夜吧
    private Long max;//最长一行时长
    private int lrc;//是否可用展示
    private int piece;//
    private List<KrcLine> mKrcLineList;////用来存储一行歌词的类




    public Krc() {
    }

    public Krc(String ar, String by, String hash, String id, int lrc, Long max, List<KrcLine> mKrcLineList, int piece, String sign, String ti, String total) {
        this.ar = ar;
        this.by = by;
        this.hash = hash;
        this.id = id;
        this.lrc = lrc;
        this.max = max;
        this.mKrcLineList = mKrcLineList;
        this.piece = piece;
        this.sign = sign;
        this.ti = ti;
        this.total = total;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLrc() {
        return lrc;
    }

    public void setLrc(int lrc) {
        this.lrc = lrc;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public List<KrcLine> getmKrcLineList() {
        return mKrcLineList;
    }

    public void setmKrcLineList(List<KrcLine> mKrcLineList) {
        this.mKrcLineList = mKrcLineList;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
