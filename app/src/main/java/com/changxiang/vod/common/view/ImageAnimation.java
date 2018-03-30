package com.changxiang.vod.common.view;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by 15976 on 2017/2/5.
 */

public class ImageAnimation {
    private Handler handler;//线程处理
    private MovieAction action;//播放器


    //固定循环时间
    public ImageAnimation(ImageView view, int[] frameRes, int duration){
        int len = frameRes.length;
        int [] frameDuration = new int[len];
        for(int i=0;i<len;i++){
            frameDuration[i]=duration;
        }
        this.Init(view,frameRes,frameDuration);
    }

    //非固定循环时间
    public ImageAnimation(ImageView view, int [] frameRes, int[] frameDuration){
        this.Init(view,frameRes,frameDuration);

    }

    private void Init(ImageView view, int [] frameRes, int[] frameDuration){

        if(null==view) {
            Log.e("ImageAnimation", "创建动画失败。");
        }else if(null == frameRes || null == frameDuration ||0 == frameRes.length ||0 == frameDuration.length) {
            Log.e("ImageAnimation", "帧数或资源为空.");
        }else if(frameRes.length != frameDuration.length){
            Log.e("ImageAnimation","帧数与间隔时间不匹配");
        }else {
            handler = new Handler();
            action = new MovieAction(handler, view, frameRes, frameDuration);

        }
    }

    public void destory(){
        if (action!=null){
            action.destory();
        }

    }

}


//动画播放类
class MovieAction implements Runnable {

    private ImageView mView;//画布
    private int [] mFrameRes;//资源
    private int[] mFrameDuration;//间隔
    private int currentFrame;//当前帧
    private int totalFrame;//总帧数

    private Handler mHandler;//线程

    public MovieAction(Handler handler, ImageView view, int [] frameRes, int[] frameDuration){
        this.mView = view;
        this.mFrameRes = frameRes;
        this.mFrameDuration = frameDuration;
        this.mHandler = handler;

        totalFrame = this.mFrameRes.length;
        currentFrame = 0;
        mHandler.postDelayed(this, mFrameDuration[currentFrame]);
    }

    public void destory(){
        this.mHandler.removeCallbacks(this);
    }


    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        mView.setBackgroundResource(mFrameRes[currentFrame]);

        currentFrame++;
        currentFrame=currentFrame%mFrameRes.length;
        mHandler.postDelayed(this, mFrameDuration[currentFrame]);
        /*if(++currentFrame<totalFrame) {
            mHandler.postDelayed(this, mFrameDuration[currentFrame]);
        }else{
            System.out.println("destory、、、、、");
            destory();
            EventManager.getInstance().DispatchEvent(new BaseEvent(EventCode.ShowBtn));
        }*/
    }
}
