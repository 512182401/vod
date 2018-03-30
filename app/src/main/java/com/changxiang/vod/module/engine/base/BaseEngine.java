package com.changxiang.vod.module.engine.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.changxiang.vod.common.view.BaseProgressDialog;
import com.changxiang.vod.module.base.AppManager;


/**
 * 业务操作的基类
 * @author admin
 *
 */
public abstract class BaseEngine {
	protected MsgHelper engineHelper;
	private BaseProgressDialog mDialog;
	public void setEngineHelper(MsgHelper engineHelper) {
		this.engineHelper = engineHelper;
	}

	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			handMsg(msg);
		};
	};

	protected MsgHelper msgHelper = new MsgHelper() {

		@Override
		public void sendMsg(Message msg) {
			handler.sendMessage(msg);
		}

		@Override
		public void sendEmpteyMsg(int what) {
			handler.sendEmptyMessage(what);
		}
	};

	public abstract void handMsg(Message msg);
	
	 public void toast(String text) {
	        toast( AppManager.getInstance().getCurrentActivity(), text, Toast.LENGTH_SHORT);
	    }
	    
	    private Toast mToast;
	  
	    private void toast(Context context, String msg, int duration)
	    {
	        if(mToast == null)
	        {
	            mToast = Toast.makeText(context, msg, duration);
	        }
	        mToast.setDuration(duration);
	        mToast.setText(msg);
	        mToast.show();
	    }
	
		public void showProgressDialog(Activity ctx, String message, boolean isCancelable){
			if(mDialog!=null){
				mDialog.dismiss();
			}
			mDialog=BaseProgressDialog.getInstance();
			mDialog.show(ctx, message, isCancelable);
		}
		
		public void closeProgressDialog(){
			if(mDialog==null){
				return;
			}
			mDialog.dismiss();
		}

}
