package com.changxiang.vod.module.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.view.BaseProgressDialog;
import com.changxiang.vod.module.base.AppManager;


public abstract class BasePermissionsActivity extends FragmentActivity {

	private BaseProgressDialog mDialog;
	FragmentStack fragStack = null;
	LinearLayout vProgressBar;
	private FrameLayout contentView;
	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			handMsg(msg);
		};
	};

	public abstract void handMsg(Message msg);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppManager.getInstance().addActivity(this);
		fragStack = new FragmentStack(this);

		//Toast.makeText(this, "类的名字: " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
	}

	/**第一步，先判断是否有权限
	 * 判断是否拥有权限
	 *
	 * @param permissions
	 * @return
	 */
	public boolean hasPermission(String... permissions) {
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
				return false;
		}
		return true;
	}
//说明：
//String... permissions
//形参String...的效果其实就和数组一样，这里的实参可以写多个String,也就是权限(下面讲到友盟分享的权限申请时就会理解)


	/**
	 * 第二步：如果没有权限，就进行请求
	 * 请求权限
	 */
	protected void requestPermission(int code, String... permissions) {
		ActivityCompat.requestPermissions(this, permissions, code);
		toast( "如果拒绝授权,会导致应用无法正常使用" );
//		ToastUtil.showMessage(this, "如果拒绝授权,会导致应用无法正常使用", Toast.Length_SHORT);
	}

	/**
	 * 请求权限的回调
	 *
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//		switch (requestCode) {
//			case CODE_CAMERA:
//				//例子：请求相机的回调
//				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////					ToastUtil.showMessage(this, "现在您拥有了权限");
//					toast( "现在您拥有了权限" );
//					// 这里写你需要的业务逻辑
//					doYourNeedDo();
//				} else {
////					ToastUtil.showMessage(this, "您拒绝授权,会导致应用无法正常使用，可以在系统设置中重新开启权限", Toast.Length_SHORT);
//					toast( "您拒绝授权,会导致应用无法正常使用，可以在系统设置中重新开启权限" );
//				}
//				break;
//			case SyncStateContract.Constants.CODE_READ_EXTERNAL_STORAGE:
//				//另一个权限的回调
//				break;
//		}
	}
//说明：
//Constants.CODE_CAMERA
//这是在外部封装的一个常量类，里面有许多静态的URL以及权限的CODE，可以自定义
//但是在调用的时候，记得这个CODE要和你自己定义的CODE一一对应
//	最后，第四步,留一个方法，给子类重写，实现你所需要的业务逻辑(比如 拍照)

	//子类重写后实现具体调用相机的业务逻辑
	public void doYourNeedDo() {
		//留给子类重写，这里空白就好
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		contentView = AppUtil.findViewById(this, android.R.id.content);
		// showProgressDialogView();
	}

	@Deprecated
	private void showProgressDialogView() {
		if (vProgressBar != null) {
			vProgressBar.setVisibility(View.VISIBLE);
			return;
		}
		vProgressBar = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.common_progress_layout, null);
		ImageView spaceshipImage = (ImageView) vProgressBar.findViewById(R.id.img);
		TextView tipTextView = (TextView) vProgressBar.findViewById(R.id.tipTextView);// 提示文字
		vProgressBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeProgressDialogView();
			}
		});
		// // 加载动画
		// AnimationDrawable animationDrawable = (AnimationDrawable)
		// spaceshipImage
		// .getBackground();
		// animationDrawable.start();
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_progress);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText("加载中...");// 设置加载信息
		int w = getResources().getDisplayMetrics().widthPixels;
		int h = getResources().getDisplayMetrics().heightPixels;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h, 0);
		vProgressBar.setLayoutParams(params);
		contentView.addView(vProgressBar, params);
	}

	@Deprecated
	private void closeProgressDialogView() {
		if (vProgressBar.getVisibility() == View.VISIBLE) {
			vProgressBar.setVisibility(View.GONE);
		}
	}

//	protected void setEngine(BaseEngine eg) {
//		this.engine = eg;
//		engine.setEngineHelper(new MsgHelper() {
//
//			@Override
//			public void sendMsg(BqMessage msg) {
//				handler.sendMessage(msg);
//			}
//
//			@Override
//			public void sendEmpteyMsg(int what) {
//				handler.sendEmptyMessage(what);
//			}
//		});
//	}

	protected void finishActivity() {
		AppManager.getInstance().finishActivity(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			AppManager.getInstance().finishActivity(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
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

	/**
	 * 管理Fragment block start
	 */

	public void pushFragment(Fragment frg) {
		fragStack.pushFragment(frg);
	}

	public void pushFragmentWithAnim(Fragment frg) {
		fragStack.pushFragmentWithAnim(frg);
	}

	public boolean popFragment() {
		return fragStack.popFragment();
	}

	public void pushRootFragment(Fragment frg) {
		fragStack.pushRootFragment(frg);
	}

	/**
	 * 管理Fragment block stop
	 */
	public void showProgressDialog(String message, boolean isCancelable) {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		mDialog = BaseProgressDialog.getInstance();
		mDialog.show(this, message, isCancelable);
	}

	public void closeProgressDialog() {
		if (this != null && !this.isFinishing()) {
			if (mDialog == null) {
				return;
			}
			mDialog.dismiss();
		}
	}

//	@Override
//	public void onResume() {
//		MobclickAgent.onPageStart(this.getClass().getSimpleName()); // 统计页面
//		super.onResume();
//		// regiserNetReceiver();
//	}
//
//	@Override
//	public void onPause() {
//		MobclickAgent.onPageEnd(this.getClass().getSimpleName()); // 统计页面
//		super.onPause();
//		// unregiserNetReceiver();
//	}

	/**
	 * wifi是否可用
	 */
	public boolean isWifiEnabled;
	/**
	 * 网络是否可用
	 */
	public boolean isNerworkEnabled;
	/**
	 * GPRS是否可用(2G/3G/4G)
	 */
	public boolean isMobileEnabled;
	public int MobileType = -1;// GPRS网络类型 1,2,3,4 分别对应1G，2G，3G，4G

	public class ConnectionChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			LogUtils.log("net_status", "网络状态改变");

			boolean success = false;

			// 获得网络连接服务
			ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			// State state = connManager.getActiveNetworkInfo().getState();
			// 获取WIFI网络连接状态

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
			if (!success) {
				Toast.makeText(context, "网络断开", Toast.LENGTH_LONG).show();
				isNerworkEnabled = false;
			} else {
				isNerworkEnabled = true;
				networkCallback();
			}
		}
	}

//	public void imageLoad(ImageView mImageView, String imageUrl, int ImageOnFail) {
//		// 显示图片的配置
//		@SuppressWarnings("deprecation")
//		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(ImageOnFail)
//		// 设置图片在下载期间显示的图片
//				.showImageForEmptyUri(ImageOnFail)
//				// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(ImageOnFail)
//				// 设置图片加载/解码过程中错误时候显示的图片
//				.cacheInMemory(true)
//				// 是否緩存都內存中
//				.cacheOnDisc(true)
//				// 是否緩存到sd卡上
//				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
//
//		ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);
//		// mImageView.setImageBitmap(ViewUtil.getCircleBitmap(mImageView.getDrawingCache()));
//	}

	private ConnectionChangeReceiver mNetReciever;

	public void regiserNetReceiver() {
		mNetReciever = new ConnectionChangeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mNetReciever, filter);
	}

	public void unregiserNetReceiver() {
		if (mNetReciever != null) {
			unregisterReceiver(mNetReciever);
		}
	}

	/**
	 * 要实现网络恢复时自动刷新界面的可以从写该函数
	 */
	protected void networkCallback() {
	};

}
