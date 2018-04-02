package com.changxiang.vod.module.pinyinindexing;

import android.content.Context;
import android.widget.Toast;

/**
 * ������˾
 * 
 * @author xiaozhifeng
 * 
 */
public class CustomToast {

	private static String content;
	private static long oneTime;
	private static long twoTime;
	private static Toast toast;

	/**
	 * ����Toast
	 * @param context
	 * @param str
	 */
	public static void showToast(Context context, String str) {

		if (toast == null) {
			content = str;
			toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (str.equals(content) && twoTime - oneTime > Toast.LENGTH_SHORT) {
				toast.show();
			} else {
				content = str;
				toast.setText(content);
				toast.show();
			}
			oneTime = twoTime;
		}

	}

}
