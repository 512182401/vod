package com.changxiang.vod.module.ui.typemusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.musicInfo.TimeUtil;
import com.changxiang.vod.module.ui.PlayerManager;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.singermusic.SingerListActivity;

public class TypeIndexActivityold extends BaseActivity implements View.OnClickListener {

    private TextView top_text;//顶部居中显示
    private ImageView bt_right;//右边显示
    private ImageView bt_back;//返回
    private TextView right_text;//y右边显示

    //综艺
    private RelativeLayout zy_zghsy;// 中国好声音 rl_zy_zghsy
    private RelativeLayout zy_zghgq;// 中国好歌曲 rl_zy_zghgq
    private RelativeLayout zy_wsgs;// 我是歌手 rl_zy_wsgs
    private RelativeLayout zy_xgdd;// 星光大道 rl_zy_xgdd
    private RelativeLayout zy_gstx;// 国色天香 rl_zy_gstx

    //主题
    private RelativeLayout zt_erge;// 儿歌 rl_zt_erge
    private RelativeLayout zt_errz;// 二人转 rl_zt_errz

    private RelativeLayout zt_minge;// 民歌 rl_zt_minge
    private RelativeLayout zt_hongge;// 红歌 rl_zt_hongge
    private RelativeLayout zt_ysrg;// 影视热歌 rl_zt_ysrg
    private RelativeLayout zt_zgf;// 中国风 rl_zt_zgf

    private RelativeLayout zt_qingge;// 情歌 rl_zt_qingge
    private RelativeLayout zt_shenqu;// 神曲 rl_zt_shenqu
    private RelativeLayout zt_wlgq;// 网络歌曲 rl_zt_wlgq

    private RelativeLayout zt_xiaoyuan;// 校园 rl_zt_xiaoyuan
    private RelativeLayout zt_yaogun;// 摇滚 rl_zt_yaogun
    private RelativeLayout zt_jlgq;// 军旅歌曲 rl_zt_jlgq

    //年代
    private RelativeLayout nd_erge;// 儿歌 rl_nd_erge
    private RelativeLayout nd_50nd;// 军旅歌曲 rl_nd_50nd
    private RelativeLayout nd_60nd;// 军旅歌曲 rl_nd_60nd
    private RelativeLayout nd_70nd;// 军旅歌曲 rl_nd_70nd
    private RelativeLayout nd_80nd;// 军旅歌曲 rl_nd_80nd
    private RelativeLayout nd_90nd;// 军旅歌曲 rl_nd_90nd
    private RelativeLayout nd_90hou;// 军旅歌曲 rl_nd_90hou
    //音乐悬浮窗
    private RelativeLayout floatingWindow;
    private TextView tvSong, tvDuration;//音乐悬窗上的歌曲名，歌曲时长
    private ProgressBar pb;//音乐悬窗上进度条
    private ImageView ivRhythm, ivClose;
    //自定义广播接收器
    private TypeIndexActivityold.Mybroad myBD;
    //
    MediaPlayer player = PlayerManager.getPlayer();
    private int maxtime;//音乐时长

    private String typeValue = "";

    @Override
    public void handMsg(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typemusic_index);
        initView();
        initData();


    }

    private void initData() {
//        zy_zghsy;// 中国好声音 rl_zy_zghsy
//        zy_zghgq;// 中国好歌曲 rl_zy_zghgq
//        zy_wsgs;// 我是歌手 rl_zy_wsgs
//        zy_xgdd;// 星光大道 rl_zy_xgdd
//        zy_gstx;// 国色天香 rl_zy_gstx
//
//        //主题
//        zt_erge;// 儿歌 rl_zt_erge
//        zt_errz;// 二人转 rl_zt_errz
//
//        zt_minge;// 民歌 rl_zt_minge
//        zt_hongge;// 红歌 rl_zt_hongge
//        zt_ysrg;// 影视热歌 rl_zt_ysrg
//        zt_zgf;// 中国风 rl_zt_zgf
//
//        zt_qingge;// 情歌 rl_zt_qingge
//        zt_shenqu;// 神曲 rl_zt_shenqu
//        zt_wlgq;// 网络歌曲 rl_zt_wlgq
//
//        zt_xiaoyuan;// 校园 rl_zt_xiaoyuan
//        zt_yaogun;// 摇滚 rl_zt_yaogun
//        zt_jlgq;// 军旅歌曲 rl_zt_jlgq
//
//        //年代
//        nd_erge;// 儿歌 rl_nd_erge
//        nd_50nd;// 军旅歌曲 rl_nd_50nd
//        nd_60nd;// 军旅歌曲 rl_nd_60nd
//        nd_70nd;// 军旅歌曲 rl_nd_70nd
//        nd_80nd;// 军旅歌曲 rl_nd_80nd
//        nd_90nd;// 军旅歌曲 rl_nd_90nd
//        nd_90hou;// 军旅歌曲 rl_nd_90hou}}
        myBD = new Mybroad();
        //注册广播接收器
        zhuce();
    }

    private void initView() { //综艺   decrease = (LinearLayout) findViewById( R.id.ll_add );
        //音乐悬浮窗
        floatingWindow = (RelativeLayout) findViewById(R.id.top_choose_floating);
        tvSong = (TextView) findViewById(R.id.top_choose_tvSong);
        pb = (ProgressBar) findViewById(R.id.top_choose_pb);
        tvDuration = (TextView) findViewById(R.id.top_choose_tvDuration);
        ivClose = (ImageView) findViewById(R.id.top_choose_ivClose);
        ivRhythm = (ImageView) findViewById(R.id.top_choose_ivRhythm);

        bt_back = (ImageView) findViewById(R.id.back_last);
        top_text = (TextView) findViewById(R.id.center_text);
        right_text = (TextView) findViewById(R.id.back_next_left);
        bt_right = (ImageView) findViewById(R.id.back_next);


        top_text.setText("分类点歌");
//        right_text.setVisibility( View.VISIBLE );
//        right_text.setText( "上传" );
        bt_right.setVisibility(View.VISIBLE);
        bt_right.setImageResource(R.mipmap.back_home);
        bt_back.setOnClickListener(this);
        bt_right.setOnClickListener(this);

        zy_zghsy = (RelativeLayout) findViewById(R.id.rl_zy_zghsy);// 中国好声音 rl_zy_zghsy
        zy_zghgq = (RelativeLayout) findViewById(R.id.rl_zy_zghsy);// 中国好歌曲 rl_zy_zghsy
        zy_wsgs = (RelativeLayout) findViewById(R.id.rl_zy_wsgs);// 我是歌手 rl_zy_wsgs
        zy_xgdd = (RelativeLayout) findViewById(R.id.rl_zy_xgdd);// 星光大道 rl_zy_xgdd
        zy_gstx = (RelativeLayout) findViewById(R.id.rl_zy_gstx);// 国色天香 rl_zy_gstx
        //主题
        zt_erge = (RelativeLayout) findViewById(R.id.rl_zt_erge);// 儿歌 rl_zt_erge
        zt_errz = (RelativeLayout) findViewById(R.id.rl_zt_errz);// 二人转 rl_zt_errz

        zt_minge = (RelativeLayout) findViewById(R.id.rl_zt_minge);// 民歌 rl_zt_minge
        zt_hongge = (RelativeLayout) findViewById(R.id.rl_zt_hongge);// 红歌 rl_zt_hongge
        zt_ysrg = (RelativeLayout) findViewById(R.id.rl_zt_ysrg);// 影视热歌 rl_zt_ysrg
        zt_zgf = (RelativeLayout) findViewById(R.id.rl_zt_zgf);// 中国风 rl_zt_zgf

        zt_qingge = (RelativeLayout) findViewById(R.id.rl_zt_qingge);// 情歌 rl_zt_qingge
        zt_shenqu = (RelativeLayout) findViewById(R.id.rl_zt_shenqu);// 神曲 rl_zt_shenqu
        zt_wlgq = (RelativeLayout) findViewById(R.id.rl_zt_wlgq);// 网络歌曲 rl_zt_wlgq

        zt_xiaoyuan = (RelativeLayout) findViewById(R.id.rl_zt_xiaoyuan);// 校园 rl_zt_xiaoyuan
        zt_yaogun = (RelativeLayout) findViewById(R.id.rl_zt_yaogun);// 摇滚 rl_zt_yaogun
        zt_jlgq = (RelativeLayout) findViewById(R.id.rl_zt_jlgq);// 军旅歌曲 rl_zt_jlgq

        //年代
        nd_erge = (RelativeLayout) findViewById(R.id.rl_nd_erge);// 儿歌 rl_nd_erge
        nd_50nd = (RelativeLayout) findViewById(R.id.rl_nd_50nd);// 军旅歌曲 rl_nd_50nd
        nd_60nd = (RelativeLayout) findViewById(R.id.rl_nd_60nd);// 军旅歌曲 rl_nd_60nd
        nd_70nd = (RelativeLayout) findViewById(R.id.rl_nd_70nd);// 军旅歌曲 rl_nd_70nd
        nd_80nd = (RelativeLayout) findViewById(R.id.rl_nd_80nd);// 军旅歌曲 rl_nd_80nd
        nd_90nd = (RelativeLayout) findViewById(R.id.rl_nd_90nd);// 军旅歌曲 rl_nd_90nd
        nd_90hou = (RelativeLayout) findViewById(R.id.rl_nd_90hou);// 军旅歌曲 rl_nd_90hou


        zy_zghsy.setOnClickListener(this);// 中国好声音 rl_zy_zghsy
        zy_zghgq.setOnClickListener(this);// 中国好歌曲 rl_zy_zghgq
        zy_wsgs.setOnClickListener(this);// 我是歌手 rl_zy_wsgs
        zy_xgdd.setOnClickListener(this);// 星光大道 rl_zy_xgdd
        zy_gstx.setOnClickListener(this);// 国色天香 rl_zy_gstx

        //主题
        zt_erge.setOnClickListener(this);// 儿歌 rl_zt_erge
        zt_errz.setOnClickListener(this);// 二人转 rl_zt_errz

        zt_minge.setOnClickListener(this);// 民歌 rl_zt_minge
        zt_hongge.setOnClickListener(this);// 红歌 rl_zt_hongge
        zt_ysrg.setOnClickListener(this);// 影视热歌 rl_zt_ysrg
        zt_zgf.setOnClickListener(this);// 中国风 rl_zt_zgf

        zt_qingge.setOnClickListener(this);// 情歌 rl_zt_qingge
        zt_shenqu.setOnClickListener(this);// 神曲 rl_zt_shenqu
        zt_wlgq.setOnClickListener(this);// 网络歌曲 rl_zt_wlgq

        zt_xiaoyuan.setOnClickListener(this);// 校园 rl_zt_xiaoyuan
        zt_yaogun.setOnClickListener(this);// 摇滚 rl_zt_yaogun
        zt_jlgq.setOnClickListener(this);// 军旅歌曲 rl_zt_jlgq

        //年代
        nd_erge.setOnClickListener(this);// 儿歌 rl_nd_erge
        nd_50nd.setOnClickListener(this);// 军旅歌曲 rl_nd_50nd
        nd_60nd.setOnClickListener(this);// 军旅歌曲 rl_nd_60nd
        nd_70nd.setOnClickListener(this);// 军旅歌曲 rl_nd_70nd
        nd_80nd.setOnClickListener(this);// 军旅歌曲 rl_nd_80nd
        nd_90nd.setOnClickListener(this);// 军旅歌曲 rl_nd_90nd
        nd_90hou.setOnClickListener(this);// 军旅歌曲 rl_nd_90hou}}
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.rl_zy_zghsy://        zy_zghsy;// 中国好声音 rl_zy_zghsy
                typeValue = "国语";
                break;
            case R.id.rl_zy_zghgq://        zy_zghgq;// 中国好歌曲
                typeValue = "日语";
                break;
            case R.id.rl_zy_wsgs://        zy_wsgs;// 我是歌手 rl_zy_wsgs
                typeValue = "粤语";
                break;
            case R.id.rl_zy_xgdd://        zy_xgdd;// 星光大道 rl_zy_xgdd
                typeValue = "韩语";
                break;
            case R.id.rl_zy_gstx://        zy_gstx;// 国色天香 rl_zy_gstx
                typeValue = "英语";
                break;

            case R.id.rl_zt_erge://        zt_erge;// 儿歌
                typeValue = "201";
                break;
            case R.id.rl_zt_errz://        zt_errz;// 二人转 rl_zt_errz
                typeValue = "202";
                break;
            case R.id.rl_zt_minge://        zt_minge;// 民歌 rl_zt_minge
                typeValue = "203";
                break;
            case R.id.rl_zt_hongge://        zt_hongge;// 红歌 rl_zt_hongge
                typeValue = "204";
                break;
            case R.id.rl_zt_ysrg://        zt_ysrg;// 影视热歌 rl_zt_ysrg
                typeValue = "205";
                break;
            case R.id.rl_zt_zgf://        zt_zgf;// 中国风 rl_zt_zgf
                typeValue = "206";
                break;
            case R.id.rl_zt_qingge://        zt_qingge;// 情歌 rl_zt_qingge
                typeValue = "207";
                break;
            case R.id.rl_zt_shenqu://        zt_shenqu;// 神曲 rl_zt_shenqu
                typeValue = "208";
                break;
            case R.id.rl_zt_wlgq://        zt_wlgq;// 网络歌曲
                typeValue = "209";
                break;
            case R.id.rl_zt_xiaoyuan://        zt_xiaoyuan;// 校园 rl_zt_xiaoyuan
                typeValue = "210";
                break;
            case R.id.rl_zt_yaogun://        zt_yaogun;// 摇滚 rl_zt_yaogun
                typeValue = "211";
                break;
            case R.id.rl_zt_jlgq://        zt_jlgq;// 军旅歌曲 rl_zt_jlgq
                typeValue = "212";
                break;


            case R.id.rl_nd_erge://        nd_erge;// 儿歌
                typeValue = "301";
                break;
            case R.id.rl_nd_50nd://        nd_50nd;// 军旅歌曲 rl_nd_50nd
                typeValue = "302";
                break;
            case R.id.rl_nd_60nd://        nd_60nd;// 军旅歌曲 rl_nd_60nd
                typeValue = "303";
                break;
            case R.id.rl_nd_70nd://        nd_70nd;// 军旅歌曲 rl_nd_70nd
                typeValue = "304";
                break;
            case R.id.rl_nd_80nd://        nd_80nd;// 军旅歌曲
                typeValue = "305";
                break;
            case R.id.rl_nd_90nd://        nd_90nd;// 军旅歌曲
                typeValue = "306";
                break;
            case R.id.rl_nd_90hou://        nd_90hou;// 军旅歌曲 rl_nd_90hou}}
                typeValue = "307";
                break;
            case R.id.back_next://主页
//                typeValue = "";
//                toast("跳转到主界面");
//                intent = new Intent(TypeIndexActivityold.this, HomeActivity.class);
////                intent.putExtra("type", "2");//1：表示此歌手，2表示此类型；
//                startActivity(intent);
                break;
            case R.id.back_last://
                typeValue = "";
                finishActivity();
                break;
            case R.id.top_choose_ivClose:
                //隐藏悬浮窗，音乐停止
                floatingWindow.setVisibility(View.GONE);
                if (player != null && player.isPlaying()) {
                    player.pause();
                }
                break;
            case R.id.top_choose_floating:
//                //跳转到相应的音乐播放页面
//                Intent musicPlay = new Intent(TypeIndexActivityold.this, MusicPlayActivity.class);
//                startActivity(musicPlay);
                break;
        }
        if (typeValue != null && !typeValue.equals("")) {
            toast("跳转到歌曲列表界面：typeValue=" + typeValue);
//            intent = new Intent(TypeIndexActivityold.this, SingerListActivity.class);
            intent = new Intent(TypeIndexActivityold.this, SingerTypeListActivity.class);
            intent.putExtra("typeValue", typeValue);
            startActivity(intent);

        }
    }

    public class Mybroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //如果不是从榜单点歌进入，则无法获取音乐时长，因为时长的广播已经发出去了，但是榜单点歌广播接收器还没注册，所以收不到
            if (intent.getAction().equals("ACTION_MAXTIME")) {
                maxtime = intent.getIntExtra("maxtime", 0);
                pb.setMax(maxtime);
                //总时长
                //tvDuration.setText(""+setTime(maxtime));
            }
            if (intent.getAction().equals("ACTION_NOWTIME")) {
                int nowtime = intent.getIntExtra("nowtime", 0);
                //设置总时长
                tvDuration.setText("" + TimeUtil.mill2mmss(player.getDuration()));
                //设置当前播放进度
                pb.setProgress(nowtime);
                ivRhythm.setBackgroundResource(R.drawable.music_fram);
                AnimationDrawable anim = (AnimationDrawable) ivRhythm.getBackground();
                if (player.isPlaying()) {
                    anim.start();
                    floatingWindow.setVisibility(View.VISIBLE);
                } else {
                    if (anim.isRunning()) {
                        anim.stop();
                    }
                    floatingWindow.setVisibility(View.GONE);
                }
            }
            //设置缓冲进度条
            if (intent.getAction().equals("ACTION_BUFFER")) {
                int buffer = intent.getIntExtra("buffer", 0);
                LogUtils.w("TAG1", "缓冲进度>>>>：" + buffer + ",*max:" + buffer * pb.getMax());
                pb.setSecondaryProgress(buffer);
            }
        }
    }

    //注册广播接收器
    public void zhuce() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("ACTION_MAXTIME");
        mFilter.addAction("ACTION_NOWTIME");
        registerReceiver(myBD, mFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBD);
    }
}
