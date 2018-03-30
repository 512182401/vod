package com.changxiang.vod.common.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/*
 * 内部接口：
 *  1、对于文件操作的检查接口
 *  	实现实时获取文件的读写进度
 * 静态方法：
 *  1、是否挂载sd卡（isSdCardMounted）
 * 
 * 非静态方法：
 *  1、将文件写入到指定路径通过is（saveFromIs）
 *  2、检查路径存在（checkFileDir）
 */

public class FileUtil {
	/**
	 * 
	 * 对于文件操作的监察接口
	 * 
	 * @author w
	 * 
	 */
	public interface FileCheck {
		/**
		 * 获取文件写入过程中已经写入的文件长度
		 * 
		 * @param length
		 *            当前写入的文件长度
		 * @return
		 */
		void getFileWriteLength(long length);
	}

	private static final String TAG = "FileUtil2";

	private static FileCheck check;

	public void setCheck(FileCheck check) {
		this.check = check;
	}



	public static boolean fileIsExists(String fileName){
		try{
			File f=new File(fileName);
			if(!f.exists()){
				return false;
			}

		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/**
	 * 将文件写入指定路径当中
	 * 
	 * @param fileDir
	 *            路径名称
	 * @param fileName
	 *            文件名称
	 * @param is
	 *            输入流
	 * @return
	 */
	public static boolean saveFromIs(String fileDir, String fileName, InputStream is) {
		if (!checkFileDir(fileDir)) {
			return false;
		}
		FileOutputStream fos = null;
		try {
			File saveFile = new File(fileDir, fileName);
			fos = new FileOutputStream(saveFile);
			byte[] buffer = new byte[1024];
			int len = 0, length = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				length += len;
				if (check != null) {
					check.getFileWriteLength(length);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 
	 * 拷贝文件
	 *
	 * @param toFileDir
	 * @param toFileName
	 */
	public static boolean copyFile(String filePath, String toFileDir, String toFileName) {
		File file=new File(filePath);
		try {
			FileInputStream fis=new FileInputStream(file);
			return saveFromIs(toFileDir, toFileName, fis);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 删除指定的文件
	 * @param file
	 * @return
     */
	public static boolean delectFile(File file) {
//		File file=new File(filePath);
		boolean suss = false;
		try {
			if (file.isFile() && file.exists()) {
//deleteFile(path);
				if (file.delete()) {
//					Toast.makeText( this, "音乐删除成功！", Toast.LENGTH_LONG ).show();
					suss = true;
				} else {
//					Toast.makeText( this, "音乐删除失败！", Toast.LENGTH_LONG ).show();
					suss = false;
				}
			}
		} catch (Exception e) {
//			Toast.makeText( this, "发生异常，删除文件失败！", Toast.LENGTH_LONG ).show();
			suss = false;
		}
		return suss;
	}

	/**
	 * 检查该路径是否存在，如果不存在则创建
	 * 
	 * @param fileDir
	 */
	private static boolean checkFileDir(String fileDir) {
		File file = new File(fileDir);
		boolean isMkdir = true;
		if (!file.exists()) {
			isMkdir = file.mkdirs();
		}
		return isMkdir;
	}

	/**
	 * 检查SdCard是否挂载
	 * 
	 * @return
	 */
	public static boolean isSdCardMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 解压zip文件
	 * 
	 * @param params
	 *            第一个参数为zip文件路径，第二个参数为解压缩文件保存路径，第二个参数为空则默认为压缩文件所在路径
	 * @throws IOException
	 */
	public static void extractZipFile(String... params) {
		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(params[0]);
			String dir = (String) (params.length > 1 ? params[1] : new File(
					params[0]).getParent());

			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			ZipEntry entry;
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				String name = entry.getName();
				String path = dir + name;
				File file = new File(path);
				if (entry.isDirectory()) {
					if (!file.exists()) {
						file.mkdirs();
					}
					continue;
				}
				// 获取文件名之前的 比如 f:/1/2.txt ,获取f:/1/
				int currindexof = 0;
				int indexof = 0;
				while ((indexof = path.indexOf("/", currindexof)) != -1) {
					currindexof = indexof + 1;
				}
				path = path.substring(0, currindexof);

				File f = new File(path);
				if (!f.exists()) {
					f.mkdirs();
				}

				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				bos = new BufferedOutputStream(new FileOutputStream(file));
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = bis.read(buffer, 0, 1024)) != -1) {
					bos.write(buffer, 0, len);
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 递归删除文件和文件夹
	 * @param file    要删除的根目录
	 */
	public static void RecursionDeleteFile(File file){
		if(file.isFile()){
			file.delete();
			return;
		}
		if(file.isDirectory()){
			File[] childFile = file.listFiles();
			if(childFile == null || childFile.length == 0){
				file.delete();
				return;
			}
			for(File f : childFile){
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//              delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 根据路径获取文件名：
	 * @param pathandname
	 * @return
	 */
	public static String getFileName(String pathandname){

		int start=pathandname.lastIndexOf("/");
		int end=pathandname.lastIndexOf(".");
		if(start!=-1 && end!=-1){
			return pathandname.substring(start+1,end);
		}else{
			return null;
		}

	}


	public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
	/**
	 * 获取文件指定文件的指定单位的大小
	 *
	 * @param filePath
	 *            文件路径
	 * @param sizeType
	 *            获取大小的类型1为B、2为KB、3为MB、4为GB
	 * @return double值的大小
	 */
	public static double getFileOrFilesSize(String filePath, int sizeType) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize, sizeType);
	}
	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 *
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize);
	}
	/**
	 * 获取指定文件大小
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}
	/**
	 * 获取指定文件夹
	 *
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}
	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 * @return
	 */
	private static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}
	/**
	 * 转换文件大小,指定转换的类型
	 *
	 * @param fileS
	 * @param sizeType
	 * @return
	 */
	private static double FormetFileSize(long fileS, int sizeType) {
		DecimalFormat df = new DecimalFormat("#.00");
		double fileSizeLong = 0;
		switch (sizeType) {
			case SIZETYPE_B:
				fileSizeLong = Double.valueOf(df.format((double) fileS));
				break;
			case SIZETYPE_KB:
				fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
				break;
			case SIZETYPE_MB:
				fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
				break;
			case SIZETYPE_GB:
				fileSizeLong = Double.valueOf(df
						.format((double) fileS / 1073741824));
				break;
			default:
				break;
		}
		return fileSizeLong;
	}
}



