package com.changxiang.vod.common.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.changxiang.vod.R;


/**
 * 带进度条的WebView
 * 

 * 
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

	private BaseProgressDialog progressbarTop;
	public ProgressBar progressbar;
	private Context mCtx;
	private boolean isShowLoadingDialog = true;
	private LinearLayout vProgressBar;

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCtx = context;

		progressbar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleHorizontal);//progressBarStyleHorizontal

		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, 10, 0,
				0);

		progressbar.setLayoutParams(params);
		Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
		progressbar.setProgressDrawable(drawable);
		addView(progressbar);
		//是否支持缩放
		getSettings().setSupportZoom(true);
		getSettings().setBuiltInZoomControls(true);
//		showProgressDialog();

		// setWebViewClient(new WebViewClient(){});
		setWebChromeClient(new WebChromeClient());
	}

	private void showProgressDialog() {
		vProgressBar = (LinearLayout) LayoutInflater.from(getContext()).inflate(
				R.layout.common_progress_layout, null);
		ImageView spaceshipImage = (ImageView) vProgressBar
				.findViewById(R.id.img);
		TextView tipTextView = (TextView) vProgressBar
				.findViewById(R.id.tipTextView);// 提示文字
//		vProgressBar.setLayoutParams();
		vProgressBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closeProgressDialog();
			}
		});
		// // 加载动画
		// AnimationDrawable animationDrawable = (AnimationDrawable)
		// spaceshipImage
		// .getBackground();
		// animationDrawable.start();
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				getContext(), R.anim.loading_progress);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText("加载中...");// 设置加载信息
		int w = getResources().getDisplayMetrics().widthPixels;
		int h = getResources().getDisplayMetrics().heightPixels;
		LayoutParams params = new LayoutParams(w, h, 0,0);
		vProgressBar.setLayoutParams(params);
		addView(vProgressBar);
	}

	public void closeProgressDialog() {
		if (vProgressBar!=null&&vProgressBar.getVisibility() == View.VISIBLE) {
			vProgressBar.setVisibility(View.GONE);
//			removeView(vProgressBar);
		}
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
//				closeProgressDialog();
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public void onPause() {
		super.onPause();
//		closeProgressDialog();
	}

	public boolean isShowLoadingDialog() {
		return isShowLoadingDialog;
	}

	public void setShowLoadingDialog(boolean isShowLoadingDialog) {
		this.isShowLoadingDialog = isShowLoadingDialog;
	}





}