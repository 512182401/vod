package com.changxiang.vod.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.ui.recordmusic.recordutils.CameraManager;

//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.CameraManager;


/**
 * class extends SurfaceView, main purpose is to refresh the preview
 *
 * @author xiaodong
 */
public class MySurfaceViewTwo extends SurfaceView implements
        SurfaceHolder.Callback {

    private CameraManager cameraManager = null;
    private int height, width;
    private MySufaceViewListener listener;

    public MySurfaceViewTwo(Context paramContext) {
        super(paramContext);
        initCameraManager(paramContext);
    }

    public MySurfaceViewTwo(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initCameraManager(paramContext);
    }

    public MySurfaceViewTwo(Context paramContext, AttributeSet paramAttributeSet,
                            int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initCameraManager(paramContext);
    }

    public void initCameraManager(Context paramContext) {
//        cameraManager = ((OpenCameraActivity) paramContext).getCameraManager();
//		cameraManager = ((OpenCameraActivityNew) paramContext).getCameraManager();
        getHolder().addCallback(this);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        cameraManager.rePreview();
        this.height = height;
        this.width = width;
        LogUtils.sysout("录像窗口宽高：" + width + "," + height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cameraManager.startCamera(this);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraManager.closeCamera();

    }

    public int getViedeoHeight() {

        return height;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.w("action", "===============ACTION_DOWN");
                listener.doSomething();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.w("action", "===============ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.w("action", "===============ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    public interface MySufaceViewListener {
        public void doSomething();
    }

    public void setMySufaceViewListener(MySufaceViewListener listener){
        this.listener = listener;
    }
}
