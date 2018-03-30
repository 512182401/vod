package com.changxiang.vod.common.utils.Bitmap;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 镜像操作：
 */
public class BitmapConvert {

	public static Bitmap convertBmp(Bitmap bmp) {
		int w = bmp.getWidth();
		int h = bmp.getHeight();

		Matrix matrix = new Matrix();
		matrix.postScale(-1, 1); // 镜像水平翻转
		Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

		return convertBmp;
	}
}