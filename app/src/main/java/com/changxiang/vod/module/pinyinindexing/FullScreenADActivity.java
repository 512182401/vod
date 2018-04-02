package com.changxiang.vod.module.pinyinindexing;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.changxiang.vod.R;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYFullScreenAd;

//import com.example.customtoast.R;

public class FullScreenADActivity extends Activity {

	private IFLYFullScreenAd ad;
	private boolean isBack;
	
	private Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ad);
		button1=(Button) findViewById(R.id.button1);
		createAd();
	}

	private void createAd() {
		ad = IFLYFullScreenAd.createFullScreenAd(this,
				"38835A5513D4E17AFBA81E0443B185CB");
		ad.setAdSize(IFLYAdSize.FULLSCREEN);
		ad.setParameter(AdKeys.SHOW_TIME_FULLSCREEN, "6000");
		ad.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
		ad.loadAd(new IFLYAdListener() {

			@Override
			public void onAdReceive() {
				ad.showAd();
			}

			@Override
			public void onAdFailed(AdError arg0) {
				Toast.makeText(
						getApplicationContext(),
						arg0.getErrorCode() + "****"
								+ arg0.getErrorDescription(), Toast.LENGTH_SHORT).show();
				isBack = true;
			}

			@Override
			public void onAdClose() {
				isBack = true;
			}

			@Override
			public void onAdClick() {

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isBack) {
				return super.onKeyDown(keyCode, event);
			} else {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);

	}

}
