package com.changxiang.vod.module.ui.base;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;

import com.changxiang.vod.module.ui.PlayerManager;


/**
 * Created by 15976 on 2017/5/13.
 */

public abstract class BaseMusicActivity extends BaseActivity {

//    private QuickControlsFragment fragment; //底部播放控制栏
    private MediaPlayer player = PlayerManager.getPlayer();//获取音乐播放器

    @Override
    public void handMsg(Message msg) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showQuickControl();
    }

    /**
     *音乐悬浮窗
     */
    protected void showQuickControl() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        if (player!=null&&player.isPlaying()) {
//            if (fragment == null) {
//                fragment = QuickControlsFragment.newInstance();
//                ft.add(R.id.top_choose_floating, fragment).commitAllowingStateLoss();
//            } else {
//                ft.show(fragment).commitAllowingStateLoss();
//            }
//        } else {
//            if (fragment != null)
//                ft.hide(fragment).commitAllowingStateLoss();
//        }
    }
}
