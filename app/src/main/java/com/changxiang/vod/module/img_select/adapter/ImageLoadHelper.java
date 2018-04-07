package com.changxiang.vod.module.img_select.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.AppUtil;

public class ImageLoadHelper {

	protected ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mImageOptions;
	private DisplayImageOptions mImageOptionsARGB888;
	
	OnImageLoadingDelegate mOnImageLoadingDelegate;
	
	Options mOption;

	public ImageLoadHelper(Context ctx) {
		if(mImageLoader.isInited()){
			return;
		};
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				ctx).threadPoolSize(3).build();
		mImageLoader.init(config);
		mOption=new Options();
		mOption.inSampleSize=2;
		mImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true)
				.cacheOnDisk(true)
				.decodingOptions(mOption)
				
//				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		
		mImageOptionsARGB888 = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.ARGB_8888).build();
	}
	
	public ImageLoader getImageLoader(){
		return mImageLoader;
	}

	// String imageUri = "http://site.com/image.png"; // from Web
	// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
	// String imageUri = "content://media/external/audio/albumart/13"; // from
	// content provider
	// String imageUri = "assets://image.png"; // from assets
	// String imageUri = "drawable://" + R.drawable.image; // from drawables
	// (only images, non-9patch)
	public void loadImage(String url, ImageView imageView) {
		String newUrl = url;
		if(!url.toLowerCase().startsWith("http") && !url.toLowerCase().startsWith("file://")){
			newUrl = "file://"+newUrl;
		}
		if(!AppUtil.isEmpty(newUrl)){
			com.changxiang.vod.common.utils.ImageLoader.getDiskCacheAll(newUrl);
//			getImageLoader().displayImage(newUrl, imageView, mImageOptions);//不用这个
			com.changxiang.vod.common.utils.ImageLoader.getImageViewLoad(imageView,newUrl, R.drawable.tv_mv);
		}
	}
	
	public void loadBigImage(String url, ImageView imageview){
		String newUrl = url;
		if(!url.toLowerCase().startsWith("http") && !url.toLowerCase().startsWith("file://")){
			newUrl = "file://"+newUrl;
		}
		if(!AppUtil.isEmpty(newUrl)){
			getImageLoader().displayImage(newUrl, imageview, mImageOptionsARGB888);
		}
	}
	
	public void loadDrawableImage(int res,ImageView imageView){
		getImageLoader().displayImage("drawable://" + res, imageView);
	}
	
	public void loadImage(String url, int holdImageViewResId,
                          ImageView imageView) {
//		imageView.setImageResource(holdImageViewResId);
		
		if(!AppUtil.isEmpty(url)){
			loadImage(url, imageView);
		}
	}
	
	public void loadImage(String url, ImageView imageView, final OnImageLoadingDelegate delegate){
		getImageLoader().displayImage(url, imageView, mImageOptions,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if(null!=delegate){
					delegate.onLoading(arg0, arg1, arg2);
				}
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				
			}
		});
	}
	
	public interface OnImageLoadingDelegate{
		void onLoading(String arg0, View arg1, Bitmap arg2);
	}

	public OnImageLoadingDelegate getOnImageLoadingDelegate() {
		return mOnImageLoadingDelegate;
	}

	public void setOnImageLoadingDelegate(
			OnImageLoadingDelegate mOnImageLoadingDelegate) {
		this.mOnImageLoadingDelegate = mOnImageLoadingDelegate;
	}
	
}
