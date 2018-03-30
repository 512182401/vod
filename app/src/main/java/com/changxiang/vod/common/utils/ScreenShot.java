package com.changxiang.vod.common.utils;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenShot {

	// 获取指定Activity的截屏，保存到png文件
	private static Bitmap takeScreenShot(Activity activity ){


//View是你需要截图的View
		View view = activity.getWindow().getDecorView();
//		View view = view;
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();


//获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

//获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();


//去掉标题栏
//Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}


	//保存到sdcard
	private static void savePic(Bitmap b, String strFileName){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(strFileName);
			if (null != fos)
			{
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成视图的预览
	 * @param activity
	 * @param v
	 * @return  视图生成失败返回null
	 *          视图生成成功返回视图的绝对路径
	 */
	public static String saveImage(Activity activity, View v, String path) {
		Bitmap bitmap;
//		String path =  Environment.getExternalStorageDirectory().getAbsolutePath()  + "preview.png";
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		bitmap = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		try {
			bitmap = Bitmap.createBitmap(bitmap, location[0], location[1], v.getWidth(), v.getHeight());
			FileOutputStream fout = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
			return path;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
//			Logger.e(TAG, "生成预览图片失败：" + e);
			LogUtils.sysout( "生成预览图片失败：" + e );
		} catch (IllegalArgumentException e) {
			LogUtils.sysout("width is <= 0, or height is <= 0");
		} finally {
			// 清理缓存
			view.destroyDrawingCache();
		}
		return null;

	}


	//程序入口  "sdcard/xx.png"
	public static void shoot(Activity a, View view, String path){
		ScreenShot.savePic(ScreenShot.takeScreenShot(a), path);
	}
}