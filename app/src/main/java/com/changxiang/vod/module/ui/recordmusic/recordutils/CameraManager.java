package com.changxiang.vod.module.ui.recordmusic.recordutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;


import com.changxiang.vod.common.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Camera manager that control the start,stop and refresh the camera
 *
 * @author xiaodong
 */
public class CameraManager implements PreviewCallback {
    private Camera camera = null;
    private Size defaultSize = null;
    private int cameraFacingType = CameraInfo.CAMERA_FACING_FRONT;
    Parameters parameters;

    public void startCamera(SurfaceView surfaceView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            for (int i = 0; i < camera.getNumberOfCameras(); i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == cameraFacingType) {
                    camera = Camera.open(i);
                    break;
                }
            }
        } else {
            camera = Camera.open();
        }
        parameters = camera.getParameters();
//		parameters.setRecordingHint(false);
//		parameters.setRotation(90);
        try {
            List<Size> sizeList = parameters.getSupportedPreviewSizes();//获取camera支持的预览尺寸
            int width = 0;
            for (Size s : sizeList) {
                LogUtils.w("parameters:", s.width + "," + s.height);
                if (s.width > width && s.width <= 700 && s.width * 3 == s.height * 4) {
                    width = s.width;
                    defaultSize = s;
                }
            }
//			defaultSize=getOptimalPreviewSize(sizeList,surfaceView.getWidth(),surfaceView.getHeight());
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(surfaceView.getHolder());
        } catch (IOException e) {
            camera.release();
            camera = null;
        }
        try {
//			parameters.setPreviewSize(640, 480);
//			parameters.setPreviewSize(480, 640);
            parameters.setPreviewSize(defaultSize.width, defaultSize.height);
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }

            if (isSupported(parameters.getSupportedWhiteBalance(), "auto"))
                parameters.setWhiteBalance("auto");

            camera.setParameters(parameters);
            setPreviewCallback();
//			defaultSize = null;
        } catch (Exception e) {
            e.printStackTrace();
            parameters.setPreviewSize(defaultSize.width, defaultSize.height);
            LogUtils.sysout("异常：" + defaultSize.width + "," + defaultSize.height);
            camera.setParameters(parameters);
        }

    }

    /**
     * 检测是否支持指定特性
     */
    private boolean isSupported(List<String> list, String key) {
        return list != null && list.contains(key);
    }

    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public Size getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(Size defaultSize) {
        this.defaultSize = defaultSize;
    }

    public void closeCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public void rePreview() {
        // Parameters parameters = camera.getParameters();
        // parameters.setPictureSize(300, 300);
        // camera.setParameters(parameters);
        camera.stopPreview();
        camera.startPreview();
    }

    public boolean isUseBackCamera() {
        return cameraFacingType == CameraInfo.CAMERA_FACING_BACK;
    }

    public void useBackCamera() {
        cameraFacingType = CameraInfo.CAMERA_FACING_BACK;
    }

    public void useFrontCamera() {
        cameraFacingType = CameraInfo.CAMERA_FACING_FRONT;
    }

    public void changeCamera(SurfaceView surfaceView) {
        if (cameraFacingType == CameraInfo.CAMERA_FACING_BACK) {
            useFrontCamera();
        } else if (cameraFacingType == CameraInfo.CAMERA_FACING_FRONT) {
            useBackCamera();
        }
        closeCamera();
        startCamera(surfaceView);
        rePreview();
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void onPreviewFrame(final byte[] data, final Camera camera) {
        LogUtils.w("q123","1232141242111111111111111111");
        byte[] dst = new byte[data.length];
        //图片旋转
        YV12RotateNegative90(dst, data, 640, 480);
//        //处理图片数据
        new AsyncTask<Void,Void,byte[]>(){
            @Override
            protected void onPostExecute(byte[] aVoid) {
                super.onPostExecute(aVoid);
                camera.addCallbackBuffer(aVoid);
            }

            @Override
            protected  byte[] doInBackground(Void... params) {
                byte[] afterHandle = addFilter(data);
                return afterHandle;
            }
        }.execute();
    }

    private byte[] addFilter(byte[] data) {
        //把数据转换成bitmap,处理bitmap,再将bitmap转换成数据
        Camera.Size size = camera.getParameters().getPreviewSize(); //获取预览大小
        final int w = size.width;  //宽度
        final int h = size.height;
        final YuvImage image = new YuvImage(data, ImageFormat.NV21, w, h, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
        if (!image.compressToJpeg(new Rect(0, 0, w, h), 100, os)) {
            return null;
        }
        byte[] tmp = os.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(tmp, 0, tmp.length);

//        Bitmap bitmap = ImageHelper.handleImageNegative(bmp);//自己定义的实时分析预览帧视频的算法

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataTemp = baos.toByteArray();
        return dataTemp;
    }

    /**
     * 设置回调
     */
    public void setPreviewCallback() {
        Camera.Size size = parameters.getPreviewSize();
        if (size != null) {
            PixelFormat pf = new PixelFormat();
            PixelFormat.getPixelFormatInfo(parameters.getPreviewFormat(), pf);
            int buffSize = size.width * size.height * pf.bitsPerPixel / 8;
            try {
                camera.addCallbackBuffer(new byte[buffSize]);
                camera.addCallbackBuffer(new byte[buffSize]);
                camera.addCallbackBuffer(new byte[buffSize]);
                camera.setPreviewCallbackWithBuffer(this);
//				camera.setOneShotPreviewCallback(this);
                camera.setPreviewCallback(this);
            } catch (OutOfMemoryError e) {
                Log.e("Yixia", "startPreview...setPreviewCallback...", e);
            }
            Log.e("Yixia", "startPreview...setPreviewCallbackWithBuffer...width:" + size.width + " height:" + size.height);
        } else {
            camera.setPreviewCallback(this);
        }
    }

    /**
     * 旋转数据
     *
     * @param dst       目标数据
     * @param src       源数据
     * @param srcWidth  源数据宽
     * @param srcHeight 源数据高
     */
    private void YV12RotateNegative90(byte[] dst, byte[] src, int srcWidth,
                                      int srcHeight) {
        int t = 0;
        int i, j;

        int wh = srcWidth * srcHeight;

        for (i = srcWidth - 1; i >= 0; i--) {
            for (j = srcHeight - 1; j >= 0; j--) {
                dst[t++] = src[j * srcWidth + i];
            }
        }

        for (i = srcWidth / 2 - 1; i >= 0; i--) {
            for (j = srcHeight / 2 - 1; j >= 0; j--) {
                dst[t++] = src[wh + j * srcWidth / 2 + i];
            }
        }

        for (i = srcWidth / 2 - 1; i >= 0; i--) {
            for (j = srcHeight / 2 - 1; j >= 0; j--) {
                dst[t++] = src[wh * 5 / 4 + j * srcWidth / 2 + i];
            }
        }

    }
}
