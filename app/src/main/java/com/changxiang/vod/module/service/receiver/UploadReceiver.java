package com.changxiang.vod.module.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.changxiang.vod.common.utils.LogUtils;


/**
 * 用于接收上传阿里云服务器进度的广播
 * Created by 15976 on 2016/11/26.
 */

public class UploadReceiver extends BroadcastReceiver {
    ComposeSetProgress iSetProgress;
   int position;//歌曲在列表中的位置
    int percent;//上传进度
    String songId,musicType;//
    @Override
    public void onReceive(Context context, Intent intent) {
        position=intent.getIntExtra("position",-1);
        percent=intent.getIntExtra("percent",0);
        songId=intent.getStringExtra("songId");
        musicType=intent.getStringExtra("musicType");
        LogUtils.sysout("9999999999");
        if ("ACTION_UPLOADING".equals(intent.getAction())){

            if (iSetProgress!=null){
                iSetProgress.setUploadProgress(position,percent,songId,"");
                LogUtils.sysout("9999999999999999999999BroadcastReceiver==position:"+position+",completeSize"+percent);
            }

        }else if ("ACTION_UPLOAD_FINISH".equals(intent.getAction())){
            if (iSetProgress!=null){
                iSetProgress.setUploadFinishImg(position,songId,"");
            }
        }else if ("ACTION_UPLAOD_EXCEPTION".equals(intent.getAction())){
            if (iSetProgress!=null){
                iSetProgress.setUploadOnException(position,songId,"");
            }
        }
    }

    public void setiSetProgress(ComposeSetProgress iSetProgress){

        this.iSetProgress=iSetProgress;
    }

}
