package com.changxiang.vod.module.editimage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changxiang.vod.R;
import com.changxiang.vod.common.view.BaseProgressDialog;

//import com.teana.myimageeditdemo.R;

/**
 * @author panyi
 * 
 */
@SuppressLint("InflateParams")
public abstract class BaseActivity extends FragmentActivity
{

	protected LayoutInflater mLayoutInflater;

	private BaseProgressDialog mDialog;
	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			handMsg(msg);
		};
	};

	public abstract void handMsg(Message msg);

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		mLayoutInflater = LayoutInflater.from(this);
	}

	public static Dialog getLoadingDialog(Context context, String title, boolean canCancel)
	{
		try{
		View dialogView = LayoutInflater.from(context).inflate(R.layout.view_loading_dialog, null);// 加载view
		ImageView image = (ImageView) dialogView.findViewById(R.id.img); //
		TextView loadingDialogText = (TextView) dialogView.findViewById(R.id.tipTextView);// 提示文字
		if (!TextUtils.isEmpty(title))
		{
			loadingDialogText.setText(title);
		}
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCancelable(canCancel);//
		AnimationDrawable animationDrawable = (AnimationDrawable) image.getDrawable();
		animationDrawable.start();//
		loadingDialog.setContentView(dialogView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
		}catch (Exception e){
			return null;
		}
	}


	public void toast(String text) {
		toast(this, text, Toast.LENGTH_SHORT);
	}

	private Toast mToast;

	private void toast(Context context, String msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, duration);
		}
		mToast.setDuration(duration);
		mToast.setText(msg);
		mToast.show();
	}
}// end class
