package com.changxiang.vod.module.entry;


/**
 * Created by teana on 2016/10/22.
 */

public class KrcLineTime {

    int startTime;
    int spanTime;//持续时间
    int reserve;
    public KrcLineTime(){
    }
    public KrcLineTime(int startTime, int spanTime, int reserve) {
        super();
        this.startTime = startTime;
        this.spanTime = spanTime;
        this.reserve = reserve;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public int getSpanTime() {
        return spanTime;
    }

    public void setSpanTime(int spanTime) {
        this.spanTime = spanTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
}
