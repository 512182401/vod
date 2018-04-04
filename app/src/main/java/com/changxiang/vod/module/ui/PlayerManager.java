package com.changxiang.vod.module.ui;

import android.media.MediaPlayer;

/**
 * Created by 15976 on 2016/10/20.
 */

public class PlayerManager {

    private static MediaPlayer player=null;

    public static MediaPlayer getPlayer(){
        if (player==null){
            synchronized (MediaPlayer.class){
                player=new MediaPlayer();
            }
        }

        return player;
    }
}
