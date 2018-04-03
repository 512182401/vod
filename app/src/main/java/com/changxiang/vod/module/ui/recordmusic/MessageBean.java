package com.changxiang.vod.module.ui.recordmusic;


import com.changxiang.vod.module.entry.SongDetail;

/**
 * Created by 15976 on 2017/10/24.
 */

public class MessageBean {
    private String tag;
    private int position;
    private int playState;
    private SongDetail songBean;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayState() {
        return playState;
    }

    public void setPlayState(int playState) {
        this.playState = playState;
    }

    public SongDetail getSongBean() {
        return songBean;
    }

    public void setSongBean(SongDetail songBean) {
        this.songBean = songBean;
    }
}
