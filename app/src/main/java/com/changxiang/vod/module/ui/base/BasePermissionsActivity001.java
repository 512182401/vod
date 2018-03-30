package com.changxiang.vod.module.ui.base;//package tosingpk.quchangkeji.com.quchangpk.module.ui.base;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.NetworkInfo.State;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.content.ContextCompat;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.view.BaseProgressDialog;
//import com.quchangkeji.tosing.module.base.AppManager;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public abstract class BasePermissionsActivity001 extends FragmentActivity {
//	private Map<Integer, Runnable> allowablePermissionRunnables = new HashMap<>();
//	private Map<Integer, Runnable> disallowblePermissionRunnables = new HashMap<>();
//	private BaseProgressDialog mDialog;
//	FragmentStack fragStack = null;
//	LinearLayout vProgressBar;
//	private FrameLayout contentView;
//	protected Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			handMsg(msg);
//		};
//	};
//
//	public abstract void handMsg(Message msg);
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		AppManager.getInstance().addActivity(this);
//		fragStack = new FragmentStack(this);
//
//		//Toast.makeText(this, "类的名字: " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
//	}
//
//	/* @param requestId            请求授权的Id，唯一即可
//	* @param permission           请求的授权
//	* @param allowableRunnable    同意授权后的操作
//	* @param disallowableRunnable 禁止授权后的操作
//	**/
//
//	protected void requestPermission(int requestId, String permission,
//                                     Runnable allowableRunnable, Runnable disallowableRunnable) {
//		if (allowableRunnable == null) {
//			throw new IllegalArgumentException("allowableRunnable == null");
//		}
//		allowablePermissionRunnables.put(requestId, allowableRunnable);
//
//		if (disallowableRunnable != null) {
//			disallowblePermissionRunnables.put(requestId, disallowableRunnable);
//
//		}
//
//		//版本判断
//		if (Build.VERSION.SDK_INT >= 23) {
//			//检查是否拥有权限
//			int checkPermission = ContextCompat.checkSelfPermission( this, permission);
//			if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//				//弹出对话框请求授权
//				ActivityCompat.requestPermissions(BasePermissionsActivity001.this, new String[]{permission}, requestId);
//				return;
//			} else {
//				allowableRunnable.run();
//			}
//		} else {
//			allowableRunnable.run();
//		}
//	}
//
//	@Override
//	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//		if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//			Runnable allowRun=allowablePermissionRunnables.get(requestCode);
//			allowRun.run();
//		}else {
//			Runnable disallowRun = disallowblePermissionRunnables.get(requestCode);
//			disallowRun.run();
//		}
//	}
//	//http://blog.csdn.net/u011068702/article/details/52040926
//
//	@Deprecated
//	private void showProgressDialogView() {
//		if (vProgressBar != null) {
//			vProgressBar.setVisibility(View.VISIBLE);
//			return;
//		}
//		vProgressBar = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.common_progress_layout, null);
//		ImageView spaceshipImage = (ImageView) vProgressBar.findViewById(R.id.img);
//		TextView tipTextView = (TextView) vProgressBar.findViewById(R.id.tipTextView);// 提示文字
//		vProgressBar.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				closeProgressDialogView();
//			}
//		});
//		// // 加载动画
//		// AnimationDrawable animationDrawable = (AnimationDrawable)
//		// spaceshipImage
//		// .getBackground();
//		// animationDrawable.start();
//		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_progress);
//		// 使用ImageView显示动画
//		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
//		tipTextView.setText("加载中...");// 设置加载信息
//		int w = getResources().getDisplayMetrics().widthPixels;
//		int h = getResources().getDisplayMetrics().heightPixels;
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h, 0);
//		vProgressBar.setLayoutParams(params);
//		contentView.addView(vProgressBar, params);
//	}
//
//	@Deprecated
//	private void closeProgressDialogView() {
//		if (vProgressBar.getVisibility() == View.VISIBLE) {
//			vProgressBar.setVisibility(View.GONE);
//		}
//	}
//
////	protected void setEngine(BaseEngine eg) {
////		this.engine = eg;
////		engine.setEngineHelper(new MsgHelper() {
////
////			@Override
////			public void sendMsg(BqMessage msg) {
////				handler.sendMessage(msg);
////			}
////
////			@Override
////			public void sendEmpteyMsg(int what) {
////				handler.sendEmptyMessage(what);
////			}
////		});
////	}
//
//	protected void finishActivity() {
//		AppManager.getInstance().finishActivity(this);
//
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//			AppManager.getInstance().finishActivity(this);
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	public void toast(String text) {
//		toast(this, text, Toast.LENGTH_SHORT);
//	}
//
//	private Toast mToast;
//
//	private void toast(Context context, String msg, int duration) {
//		if (mToast == null) {
//			mToast = Toast.makeText(context, msg, duration);
//		}
//		mToast.setDuration(duration);
//		mToast.setText(msg);
//		mToast.show();
//	}
//
//	/**
//	 * 管理Fragment block start
//	 */
//
//	public void pushFragment(Fragment frg) {
//		fragStack.pushFragment(frg);
//	}
//
//	public void pushFragmentWithAnim(Fragment frg) {
//		fragStack.pushFragmentWithAnim(frg);
//	}
//
//	public boolean popFragment() {
//		return fragStack.popFragment();
//	}
//
//	public void pushRootFragment(Fragment frg) {
//		fragStack.pushRootFragment(frg);
//	}
//
//	/**
//	 * 管理Fragment block stop
//	 */
//	public void showProgressDialog(String message, boolean isCancelable) {
//		if (mDialog != null) {
//			mDialog.dismiss();
//		}
//		mDialog = BaseProgressDialog.getInstance();
//		mDialog.show(this, message, isCancelable);
//	}
//
//	public void closeProgressDialog() {
//		if (this != null && !this.isFinishing()) {
//			if (mDialog == null) {
//				return;
//			}
//			mDialog.dismiss();
//		}
//	}
//
////	@Override
////	public void onResume() {
////		MobclickAgent.onPageStart(this.getClass().getSimpleName()); // 统计页面
////		super.onResume();
////		// regiserNetReceiver();
////	}
////
////	@Override
////	public void onPause() {
////		MobclickAgent.onPageEnd(this.getClass().getSimpleName()); // 统计页面
////		super.onPause();
////		// unregiserNetReceiver();
////	}
//
//	/**
//	 * wifi是否可用
//	 */
//	public boolean isWifiEnabled;
//	/**
//	 * 网络是否可用
//	 */
//	public boolean isNerworkEnabled;
//	/**
//	 * GPRS是否可用(2G/3G/4G)
//	 */
//	public boolean isMobileEnabled;
//	public int MobileType = -1;// GPRS网络类型 1,2,3,4 分别对应1G，2G，3G，4G
//
//	public class ConnectionChangeReceiver extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			LogUtils.log("net_status", "网络状态改变");
//
//			boolean success = false;
//
//			// 获得网络连接服务
//			ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//			// State state = connManager.getActiveNetworkInfo().getState();
//			// 获取WIFI网络连接状态
//
//			NetworkInfo infoGPRS = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//			State state;
//			if (infoGPRS == null) {// 如果无GPRS模块
//				isMobileEnabled = false;
//			} else {
//				state = infoGPRS.getState();
//				// 判断是否正在使用GPRS网络
//				if (State.CONNECTED == state) {
//					isMobileEnabled = true;
//					success = true;
//				} else {
//					isMobileEnabled = false;
//				}
//			}
//			NetworkInfo infoWIFI = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//			if (infoWIFI == null) {// 如果无GPRS模块，则检测wifi模块
//				isWifiEnabled = false;
//			} else {
//				state = infoWIFI.getState();
//				// 判断是否正在使用GPRS网络
//				if (State.CONNECTED == state) {
//					isWifiEnabled = true;
//					success = true;
//				} else {
//					isWifiEnabled = false;
//				}
//			}
//			if (!success) {
//				Toast.makeText(context, "网络断开", Toast.LENGTH_LONG).show();
//				isNerworkEnabled = false;
//			} else {
//				isNerworkEnabled = true;
//				networkCallback();
//			}
//		}
//	}
//
////	public void imageLoad(ImageView mImageView, String imageUrl, int ImageOnFail) {
////		// 显示图片的配置
////		@SuppressWarnings("deprecation")
////		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(ImageOnFail)
////		// 设置图片在下载期间显示的图片
////				.showImageForEmptyUri(ImageOnFail)
////				// 设置图片Uri为空或是错误的时候显示的图片
////				.showImageOnFail(ImageOnFail)
////				// 设置图片加载/解码过程中错误时候显示的图片
////				.cacheInMemory(true)
////				// 是否緩存都內存中
////				.cacheOnDisc(true)
////				// 是否緩存到sd卡上
////				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
////
////		ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);
////		// mImageView.setImageBitmap(ViewUtil.getCircleBitmap(mImageView.getDrawingCache()));
////	}
//
//	private ConnectionChangeReceiver mNetReciever;
//
//	public void regiserNetReceiver() {
//		mNetReciever = new ConnectionChangeReceiver();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//		registerReceiver(mNetReciever, filter);
//	}
//
//	public void unregiserNetReceiver() {
//		if (mNetReciever != null) {
//			unregisterReceiver(mNetReciever);
//		}
//	}
//
//	/**
//	 * 要实现网络恢复时自动刷新界面的可以从写该函数
//	 */
//	protected void networkCallback() {
//	};
//
//}
