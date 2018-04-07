package com.changxiang.vod.module.ui.addlocal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.module.ui.base.BaseActivity;

/**
 * Created by 15976 on 2017/11/1.
 */

public class UpdateLocalVideoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backLast;
    private TextView centerText;
    private RelativeLayout updateVideo;

    @Override
    public void handMsg(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_local_video);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        backLast = (ImageView) findViewById(R.id.back_last);
        centerText = (TextView) findViewById(R.id.center_text);
        updateVideo = (RelativeLayout) findViewById(R.id.update_video);
    }

    private void initData() {
        centerText.setText(getString(R.string.local_text));
    }

    private void initListener() {
        backLast.setOnClickListener(this);
        updateVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_last:
                finishActivity();
                break;
            case R.id.update_video:
                startActivityForResult(new Intent(this,LocalVideoListActivity.class),1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
