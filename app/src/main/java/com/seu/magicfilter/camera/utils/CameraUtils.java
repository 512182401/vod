package com.seu.magicfilter.camera.utils;

import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by why8222 on 2016/2/25.
 */
public class CameraUtils {

    public static Camera.Size getLargePictureSize(Camera camera) {
        if (camera != null) {
            List<Camera.Size> sizes = camera.getParameters().getSupportedPictureSizes();
            Camera.Size temp = sizes.get(0);
            for (int i = 1; i < sizes.size(); i++) {
                float scale = (float) (sizes.get(i).height) / sizes.get(i).width;
                if (temp.width < sizes.get(i).width && scale < 0.6f && scale > 0.5f)
                    temp = sizes.get(i);
            }
            return temp;
        }
        return null;
    }

    public static Camera.Size getLargePreviewSize(Camera camera) {
        if (camera != null) {
            List<Camera.Size> sizes = camera.getParameters().getSupportedPreviewSizes();
            //针对不同手机排序
            Collections.sort(sizes, new Comparator<Camera.Size>() {
                @Override
                public int compare(Camera.Size o1, Camera.Size o2) {
                    return o1.width - o2.width;
                }
            });
            //选择合适的尺寸3:4  注意是反的
            Camera.Size temp = sizes.get(0);
            for (int i = 1; i < sizes.size(); i++) {
//                LogUtils.w("123", "--------------" + sizes.get(i).width + "---------------" + sizes.get(i).height);
                if (temp.width < sizes.get(i).width && sizes.get(i).width < 700 && sizes.get(i).width * 3 == sizes.get(i).height * 4) {
                    temp = sizes.get(i);
//                    LogUtils.w("123", "1--------------" + temp.width + "---1---------------" + temp.height);
                }

            }
//            LogUtils.w("123", "12--------------" + temp.width + "---12---------------" + temp.height);
            return temp;
        }
        return null;
    }
}
