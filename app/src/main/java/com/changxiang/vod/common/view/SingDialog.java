//package com.changxiang.vod.common.view;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.Display;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import com.changxiang.VodMedia.R;
//
//
//public class SingDialog extends Dialog implements View.OnClickListener {
//
//	// private Context context;
//	private String titleStr;
//	private String contentStr;
//	private String cancelStr;
//	private String goStr;
//
//	public interface ConfirmDialogHelper {
//		void go();
//	}
//
//	private ConfirmDialogHelper mConfirmDialogHelper;
//
//	/** 新建时 , 用这个构造函数 */
//	public SingDialog(Context context, String titleStr, String contentStr, String goStr, ConfirmDialogHelper helper) {
//		super(context, R.style.CustomDialog);
//		this.titleStr = titleStr;
//		this.contentStr = contentStr;
//		this.goStr = goStr;
//		this.mConfirmDialogHelper = helper;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.common_sing_dialog);
//		setCanceledOnTouchOutside(true);
//		Window window = getWindow();// 获得窗口
//		WindowManager manager = window.getWindowManager();// 获得管理器
//		Display display = manager.getDefaultDisplay();
//		WindowManager.LayoutParams attributes = window.getAttributes();
//		attributes.width = (int) (display.getWidth() * 0.8);
//		window.setAttributes(attributes);
//		init();
//	}
//
//	private void init() {
//		TextView tv_title = (TextView) findViewById(R.id.confirm_dialog_title);
//		TextView tv_content = (TextView) findViewById(R.id.confirm_dialog_content);
//		TextView tv_go = (TextView) findViewById(R.id.confirm_dialog_go);
//
//		tv_title.setText(titleStr);
//		tv_content.setText(contentStr);
//		tv_go.setText(goStr);
//		tv_go.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.confirm_dialog_go:
//			// 确定
//			if (mConfirmDialogHelper != null) {
//				mConfirmDialogHelper.go();
//			}
//			dismiss();
//			break;
//		default:
//			break;
//		}
//	}
//}
