package com.changxiang.vod.module.ui.saveutils;

import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.changxiang.vod.R;
import com.changxiang.vod.module.base.BaseFragment;


/**
 * 未开启摄像头页面播放音视频的Fragment
 * Created by 15976 on 2016/10/12.
 */

public class SaveRecordFragment extends BaseFragment {
    //mp3背景图片
    public ImageView imageView;
    public SurfaceView surfaceView;
    public SurfaceHolder holder;

    @Override
    public void handMsg(Message msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_save_record;
    }

    @Override
    protected void initViews() {

        imageView= (ImageView) root.findViewById(R.id.fragment_save_record_iv);
        surfaceView= (SurfaceView) root.findViewById(R.id.fragment_save_record_sv);
        holder=surfaceView.getHolder();
        /*imageView.setImageResource(R.drawable.tv_mv);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);*/

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }



    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    //显示mv
    public void setMV(){
        surfaceView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
    }
    //显示MP3
    public void setMp3(){
        surfaceView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

}
