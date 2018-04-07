package com.changxiang.vod.module.img_select;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.module.img_select.adapter.ImageLoadHelper;
import com.changxiang.vod.module.img_select.multi.MatrixImageView;
import com.changxiang.vod.module.ui.base.BaseActivity;


public class ShowImageActivity extends BaseActivity implements View.OnClickListener{
	
	private MatrixImageView mImageView;

	private TextView top_text;//顶部居中显示
	private ImageView bt_right;//右边显示
	private ImageView bt_back;//返回
	private TextView right_text;//顶部右显示
	
	private String mUrl;
	@Override
	protected void onCreate( Bundle arg0) {
		super.onCreate(arg0);
		setContentView( R.layout.select_image_showimage_activity);
		init();
	}
	
	private void getIntentParems(){
		Intent intent=getIntent();
		if(intent==null){
			return;
		}
		mUrl=intent.getStringExtra("url");
	}
	
	private void init(){
		mImageView= AppUtil.findViewById(this, R.id.image);

		bt_back = (ImageView) findViewById(R.id.back_last);
		top_text = (TextView) findViewById(R.id.center_text);
		bt_right = (ImageView) findViewById(R.id.back_next);
		right_text = (TextView) findViewById(R.id.back_next_left);

		top_text.setText(getString(R.string.image_show));
		bt_right.setVisibility(View.GONE);
		right_text.setVisibility(View.GONE);

		bt_back.setOnClickListener(this);
		getIntentParems();
		new ImageLoadHelper(this).loadBigImage(mUrl,mImageView);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode== KeyEvent.KEYCODE_BACK){
			this.finishActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void handMsg(Message msg) {
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.back_last://
				finishActivity();
				break;

		}

	}
}
