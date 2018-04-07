package com.changxiang.vod.module.img_select.single;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.changxiang.vod.R;

//import tosingpk.quchangkeji.com.quchangpk.R;

//import com.quchangkeji.tosing.R;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * @author zhy
 *
 */
public class ClipImageLayout extends RelativeLayout
{

	private ClipZoomImageView mZoomImageView;
	private ClipImageBorderView mClipImageView;

	/**
	 * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
	 */
	private int mHorizontalPadding = 20;

	public void setImageBitmap(Bitmap bitmap){
		if(bitmap==null){
			return;
		}
		mZoomImageView.setImageBitmap(bitmap);
	}

	public ClipImageLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mZoomImageView = new ClipZoomImageView(context);
		mClipImageView = new ClipImageBorderView(context);

		LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		lp.addRule(CENTER_IN_PARENT);
		/**
		 * 这里测试，直接写死了图片，真正使用过程中，可以提取为自定义属性
		 */
		mZoomImageView.setImageDrawable(getResources().getDrawable(
				R.drawable.default_icon));

		this.addView(mZoomImageView, lp);
		this.addView(mClipImageView, lp);


		// 计算padding的px
		mHorizontalPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
						.getDisplayMetrics());
		mZoomImageView.setHorizontalPadding(mHorizontalPadding);
		mClipImageView.setHorizontalPadding(mHorizontalPadding);
	}

	/**
	 * 对外公布设置边距的方法,单位为dp
	 *
	 * @param mHorizontalPadding
	 */
	public void setHorizontalPadding(int mHorizontalPadding)
	{
		this.mHorizontalPadding = mHorizontalPadding;
	}

	/**
	 * 裁切图片
	 *
	 * @return
	 */
	public Bitmap clip()
	{
		return mZoomImageView.clip();
	}

}
