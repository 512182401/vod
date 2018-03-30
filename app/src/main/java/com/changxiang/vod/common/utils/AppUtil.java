package com.changxiang.vod.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtil {
	/**
	 * 调用安装apk界面
	 * 
	 * @param context
	 * @param paramFile
	 */
	public static void install(Context context, File paramFile) {
		Intent localIntent = new Intent("android.intent.action.VIEW");
		localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
		context.startActivity(localIntent);
	}

	public static void install(Context context, String paramFile) {
		Intent localIntent = new Intent("android.intent.action.VIEW");
		localIntent.setDataAndType(Uri.fromFile(new File(paramFile)), "application/vnd.android.package-archive");
		context.startActivity(localIntent);
	}

	public static boolean hasSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static String getImagePath(Activity ctx, Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
        Cursor actualimagecursor = ctx.managedQuery(uri, proj, null, null, null);
		String img_path;
		if (actualimagecursor != null) {
			int actual_image_column_index1 = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			img_path = actualimagecursor.getString(actual_image_column_index1);
		} else {
			img_path = uri.getPath();
		}
		return img_path;
	}

	/**
	 * 根据Id查找控件
	 * @param view
	 * @param resId
	 * @return
	 */
	public static <T extends View> T findViewById(View view, int resId) {
		return (T) view.findViewById(resId);
	}

	/**
	 * 根据Id查找控件
	 * 
	 *
	 * @param resId
	 * @return
	 */
	public static <T extends View> T findViewById(Activity ctx, int resId) {
		return (T) ctx.findViewById(resId);
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static void hideSoftInput(Context ctx, View view) {
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 判断字符串是否为null或者""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !str.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static String checkDate(String dateStr) {
		SimpleDateFormat sfToday = new SimpleDateFormat("HH:mm");
		Date todayDate = new Date();
		sfToday.format(todayDate);
		if (dateStr == null || dateStr.equals("")) {
			return sfToday.format(todayDate);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			Calendar today = Calendar.getInstance();
			d = sf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			long intervalMilli = today.getTimeInMillis() - cal.getTimeInMillis();
			int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
			if (xcts == 0) {
				return sfToday.format(d);
			} else if (xcts == 1) {
				return "昨天";
				// }else if (xcts == 2) {
				// return "前天";
			} else {
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
				return sf1.format(cal.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sfToday.format(todayDate);
	}



	/**
	 * 获取手机支持的存储卡路径,利用java反射,Android3.0以上
	 * 
	 * @category Environment.getExternalStorageDirectory()获取存储卡的路径，
	 *           但是现在有很多手机内置有一个存储空间， 同时还支持外置sd卡插入，
	 *           这样通过Environment.getExternalStorageDirectory()方法获取到的就是内置存储卡的位置
	 * @param ctx
	 * @return
	 */
	public static List<String> getSdCardPath(Context ctx) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		StorageManager storageManager = (StorageManager) ctx.getSystemService(Context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
			getVolumePathsMethod.setAccessible(true);
			Object[] params = {};
			Object invoke = getVolumePathsMethod.invoke(storageManager, params);
			for (int i = 0; i < ((String[]) invoke).length; i++) {
				localArrayList.add(((String[]) invoke)[i]);
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return localArrayList;
	}



	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 获取设备号信息
	 * 
	 * @return
	 */
	public static String getdeviceid(Context context) {
		String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
		return ANDROID_ID;
	}

	/**
	 * 获取sd卡路径
	 * 
	 * @return
	 */
	public static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	/**
	 * 获取手机的IMEI
	 * 
	 * @param context
	 * @return
	 */
	public static String IMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String IMEI = telephonyManager.getDeviceId();
		return IMEI;
	}



	public static String getUser_Agent() {
		String ua = "Android;" + getOSVersion() + ";" + getVendor() + "-" + getDevice() + ";" + getPro();
		LogUtils.log("info", ua);
		return ua;
	}

	/**
	 * device model name, e.g: GT-I9100
	 * 
	 * @return the user_Agent
	 */
	public static String getDevice() {
		return Build.MODEL;
	}

	public static String getPro() {
		return Build.MANUFACTURER;
	}

	public static boolean isHuaWei() {
		final String HUAWEI = "HUAWEI";
		return HUAWEI.equals(getPro());
	}

	/**
	 * device factory name, e.g: Samsung
	 * 
	 * @return the vENDOR
	 */
	public static String getVendor() {
		return Build.BRAND;
	}

	/**
	 * @return the SDK version
	 */
	public static int getSDKVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * @return the OS version
	 */
	public static String getOSVersion() {
		return Build.VERSION.RELEASE;
	}

	public static void callPhone(Context ctx, String phoneNo) {
		if (phoneNo == null || phoneNo.isEmpty()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
		ctx.startActivity(intent);
	}

}
