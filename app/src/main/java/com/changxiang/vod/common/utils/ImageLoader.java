package com.changxiang.vod.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

/**
 * TODO<对图片进行管理的工具类>
 *
 * @author ZhuZiQiang
 * @data: 2014-4-4 下午3:07:12
 * @version: V1.0
 */

public class ImageLoader {

	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private static LruCache<String, Bitmap> mMemoryCache;

	/**
	 * ImageLoader的实例。
	 */
	public static ImageLoader mImageLoader;

	private ImageLoader() {
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// 设置图片缓存大小为程序最大可用内存的1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/**
	 * 清除内存缓存（不清楚本地）
	 * @param url
	 */
	public static void getDiskCache(String url){
//		DiskCacheUtils.removeFromCache(url, com.nostra13.universalimageloader.core.ImageLoader.getInstance().getDiskCache());
		MemoryCacheUtils.removeFromCache(url, com.nostra13.universalimageloader.core.ImageLoader.getInstance().getMemoryCache());
	}

	/**
	 * 清除所有数据缓存
	 * @param url
	 */
	public static void getDiskCacheAll(String url){
		DiskCacheUtils.removeFromCache(url, com.nostra13.universalimageloader.core.ImageLoader.getInstance().getDiskCache());
		MemoryCacheUtils.removeFromCache(url, com.nostra13.universalimageloader.core.ImageLoader.getInstance().getMemoryCache());
	}

	/**
	 * 获取ImageLoader的实例。
	 *
	 * @return ImageLoader的实例。
	 */
	public static ImageLoader getInstance() {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader();
		}
		return mImageLoader;
	}

	/**
	 * 将一张图片存储到LruCache中。
	 *
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}


	/**
	 * 从内存卡中异步加载本地图片
	 *
	 * @param uri
	 * @param imageView
	 */
	public static void displayFromSDCard(String uri, ImageView imageView) {
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("file://" + uri, imageView);
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 *
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public static Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth) {
		// 源图片的宽度
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width > reqWidth) {
			// 计算出实际宽度和目标宽度的比率
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}
//	String imageUri = "http://site.com/image.png"; // 网络图片
//	String imageUri = "file:///mnt/sdcard/image.png"; // sd卡图片
//	String imageUri = "content://media/external/audio/albumart/13"; //  content provider
//	String imageUri = "assets://image.png"; // assets文件夹图片
//	String imageUri = "drawable://" + R.drawable.image; // drawable图片
	public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName, options);
	}

	//加载网络图片
	public static void getImageViewLoad(ImageView mImageView, String imageUrl, int ImageOnFail) {
		// 显示图片的配置
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage( ImageOnFail )
				// 设置图片在下载期间显示的图片
				.showImageForEmptyUri( ImageOnFail )
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail( ImageOnFail )
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory( true )
				// 是否緩存都內存中
				.cacheOnDisc( true )
				// 是否緩存到sd卡上
				.bitmapConfig( Bitmap.Config.RGB_565 ).build();

		com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage( imageUrl, mImageView, options );
		// mImageView.setImageBitmap(ViewUtil.getCircleBitmap(mImageView.getDrawingCache()));
	}

	public static Bitmap loadImageSync(String uri){
		return com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImageSync(uri);
	}
}
