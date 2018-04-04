package com.changxiang.vod.module.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.ui.recently.ISetProgress;


/**
 * 用于接收下载进度的广播
 * Created by 15976 on 2016/11/26.
 */

public class MusicReceiver extends BroadcastReceiver {
    ISetProgress iSetProgress;
    int position;//歌曲在列表中的位置
    int percent;//下载进度
    String songId,musicType;//
    @Override
    public void onReceive(Context context, Intent intent) {
        position=intent.getIntExtra("position",-1);
        percent=intent.getIntExtra("percent",0);
        songId=intent.getStringExtra("songId");
        musicType=intent.getStringExtra("musicType");
        if ("ACTION_DOWNLOADING".equals(intent.getAction())){
            if (iSetProgress!=null){
                iSetProgress.setDownloadProgress(position,percent,songId,musicType);
                LogUtils.w("TAG","BroadcastReceiver==position:"+position+",completeSize"+percent);
            }

        }else if ("ACTION_FINISH".equals(intent.getAction())){
            if (iSetProgress!=null){
                iSetProgress.setFinishImg(position,songId,musicType);
            }
        }else if ("ACTION_EXCEPTION".equals(intent.getAction())){
            if (iSetProgress!=null){
                iSetProgress.setOnException(position,songId,musicType);
            }
        }
    }

    public void setiSetProgress(ISetProgress iSetProgress){

        this.iSetProgress=iSetProgress;
    }

}
