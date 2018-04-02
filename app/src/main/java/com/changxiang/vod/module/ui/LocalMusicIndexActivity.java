//package com.changxiang.vod.module.ui;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.changxiang.vod.R;
//import com.changxiang.vod.common.utils.AppUtil;
//import com.changxiang.vod.module.entry.ListBean;
//import com.changxiang.vod.module.ui.adapter.FragmentViewPagerAdapter;
//import com.changxiang.vod.module.ui.base.BaseActivity;
//
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//public class LocalMusicIndexActivity extends BaseActivity implements View.OnClickListener {
//
//    private TextView top_text;//顶部居中显示
//    private ImageView bt_right;//右边显示
//    private ImageView bt_back;//返回
//    //private TextView right_text;//y右边显示
//
//    private TabLayout tab;//
//    private ViewPager mViewpager;
//    private int mCurrentPage;
//    private final List<TextView> views = new ArrayList<>();
////    private MyOnPageChangeListener mOnPageChangeListener;
//    private FragmentViewPagerAdapter mPageAdapter;
//
//    private final List<Fragment> frgList = new ArrayList<>();
//    private final List<String> list = new ArrayList<>();//tab导航
//
//    //    private FragmentTransaction transaction;
//
//    private TextView mypro;//
//    private TextView otherpro;//
//
//
//    public List<ListBean> getSongList() {
//        return songList;
//    }
//
//    public void setSongList(List<ListBean> songList) {
//        this.songList = songList;
//    }
//
//    private List<ListBean> songList;
//
//    @Override
//    public void handMsg(Message msg) {
//
//        switch (msg.what) {
//            case -1:
//                toast(getString(R.string.get_no_data));
////                    handler.sendEmptyMessageDelayed( -1, 1000 );
//                break;
//            case 0:
////                toast( "成功" );
////                    handler.sendEmptyMessageDelayed( 0, 1000 );
//
////                initData();
//                break;
//            case 1:
//                toast(getString(R.string.get_no_data));//
//
////                handler.sendEmptyMessageDelayed( 1, 100 );
//                break;
//
//            case 123://权限
////                updataimage();
////                handler.sendEmptyMessageDelayed( 123, 100 );
//                break;
//
//            case 11://
////                handler.sendEmptyMessageDelayed( 10, 100 );
////                club_image.setImageBitmap( newbitmap );
//
//                bt_right.setEnabled(true);
////                                handler.sendEmptyMessageDelayed( 11, 100 );
//                break;
//
//        }
//
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_localmusic);
//        Intent intent = getIntent();
//        String intentType = intent.getStringExtra("type");
//
//        initView();
//        initData();
//        if (intentType != null && !intentType.equals("")) {//&&intentType.equals( "1" ) 主要为下载自动跳转
//            try {
//                mViewpager.setCurrentItem(1);
//                setFocus(views.get(1));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    private void initData() {
//        //读取本地文件夹的歌曲（分我的作品和已下载的作品）
//
//
//    }
//
//    private void initView() {
//
//        bt_back = (ImageView) findViewById(R.id.back_last);
//        top_text = (TextView) findViewById(R.id.center_text);
//        //right_text = (TextView) findViewById( R.id.back_next_left );
//        bt_right = (ImageView) findViewById(R.id.back_next);
//
//        tab = (TabLayout) findViewById(R.id.local_music_tl);
//        mViewpager = AppUtil.findViewById(this, R.id.vp_data_chang);
//        mypro = AppUtil.findViewById(this, R.id.tv_my_production);
//        otherpro = AppUtil.findViewById(this, R.id.tv_down_production);
//
//        mypro.setOnClickListener(this);
//        otherpro.setOnClickListener(this);
//
//        bt_back.setOnClickListener(this);
//        //right_text.setOnClickListener( this );
//        bt_right.setOnClickListener(this);
//
//        top_text.setText(R.string.local_work);
//        //right_text.setVisibility( View.VISIBLE );
//        bt_right.setVisibility(View.VISIBLE);
//        bt_right.setImageResource(R.mipmap.top_whistle_blowing);
//
//        //应该将两个标题保存到
//        views.add(mypro);
//        views.add(otherpro);
//        frgList.add(new FragmentMyPro());
//        //frgList.add( new FragmentOtherPro() );
//        frgList.add(new FragmentFriends());
//        list.add(getString(R.string.mine_work));
//        list.add(getString(R.string.friend_work));
////        FragmentManager manager = getSupportFragmentManager();
////        manager.beginTransaction().commit();
//
//        tab.post(new Runnable() {
//            @Override
//            public void run() {
//                TabIndicatorDefine.setIndicator(tab, 50, 50);
//            }
//        });
////        mOnPageChangeListener = new MyOnPageChangeListener();
//        mPageAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), frgList, list);
//        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mCurrentPage = position;
//                setFocus(views.get(position));
//                LogUtils.info("mCurrentPage", mCurrentPage + "");
//                mViewpager.setCurrentItem(mCurrentPage, true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        mViewpager.setAdapter(mPageAdapter);
//        tab.setupWithViewPager(mViewpager);
//        mViewpager.setCurrentItem(0);//默认为第一个
//        setFocus(views.get(0));//默认选择第一个
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//    }
//
////    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
////        // private int one = offset *2 +bmpW;//两个相邻页面的偏移量
////
////        @Override
////        public void onPageScrolled(int arg0, float arg1, int arg2) {
////
////        }
////
////        @Override
////        public void onPageScrollStateChanged(int arg0) {
////
////        }
////
////        @Override
////        public void onPageSelected(int index) {
////            mCurrentPage = index;
////            setFocus(views.get(index));
////
//////			((RadioButton) (mRadioGroup.findViewById(checkedId)))
//////					.setChecked(true);// 修改选项卡状态
////            LogUtils.info("mCurrentPage", mCurrentPage + "");
////            mViewpager.setCurrentItem(mCurrentPage, true);
////        }
////    }
//
//    private void setFocus(TextView item) {
//        for (TextView view : views) {
////            TextView txt = (TextView) view.findViewById(R.id.txt);
//            if (view == item) {
//                view.setTextColor(Color.parseColor("#dc342a"));
//
//            } else {
//                view.setTextColor(Color.parseColor("#2a2a2a"));
//            }
//        }
//        if (item == otherpro) {
//            //right_text.setVisibility( View.INVISIBLE );
//            bt_right.setVisibility(View.VISIBLE);
//        } else {
//            //right_text.setVisibility( View.VISIBLE );
//            bt_right.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_my_production:
//                mViewpager.setCurrentItem(0);
//                setFocus(views.get(0));
//                break;
//            case R.id.tv_down_production:
//                mViewpager.setCurrentItem(1);
//                setFocus(views.get(1));
//                break;
//            case R.id.back_last://
//                String save = getIntent().getStringExtra("save");
//                LogUtils.w("save","save=====" + save);
////                if ("mp3".equals(save)){
////                    finishActivityByClz(CameraOratorioActivity.class);
//////                    finishActivityByClz(SavePracticeActivity.class);
////                    finishActivityByClz(OratorioActivity.class);
////                }else if ("mp4".equals(save)){
////                    finishActivityByClz(OratorioActivity.class);
////                }
//                Intent intent = new Intent(this, HomeActivity.class);
//                intent.putExtra("fragmentitem","sing");
//                startActivity(intent);
//                finishActivity();
//                break;
//            case R.id.back_next://搜索按钮
//                bt_right.setEnabled(false);
//                if (mCurrentPage == 0) {
//                    openChooseMy();
//                } else {
//                    openChooseOther();
//                }
//
//                /*toast( "搜索按钮" );
//                intent = new Intent( LocalMusicIndexActivity.this, SearchActivity.class );
////                intent.putExtra( "songid", "" );
//////                intent.putExtra("songid", item.getId());
//                startActivity( intent );*/
//                break;
//            case R.id.back_next_left://导出 ExportProActivity
////                toast( "导出" );
//                Intent intent1;
//                intent1 = new Intent(LocalMusicIndexActivity.this, ExportProActivity.class);
////                intent.putExtra( "songid", "" );
//////                intent.putExtra("songid", item.getId());
//                startActivity(intent1);
//                break;
//
//        }
//
//    }
//
//    private boolean changopenChoose = false;
//    String type = "0";
//
//    private void openChooseMy() {
//        if (changopenChoose) {
//            changopenChoose = false;
//            return;
//        }
//        changopenChoose = true;
//        // 点击帅选按钮,打开帅选item
//        PopUpChooseMyUtils.getEntrance(this, null).showPopupWindow(bt_right, new PopUpChooseMyUtils.GoSearchForHistory() {
//            @Override
//            public void goSearchForHistory(String searchStr) {
//                Intent intent;
//                handler.sendEmptyMessageDelayed(11, 500);
//                switch (Integer.parseInt(searchStr)) {
//
//                    case 0:
////                        toast( "批量删除" );
////                        type = "0";
//                        intent = new Intent(LocalMusicIndexActivity.this, DelectMyProActivity.class);//DelectOtherProActivity
////                intent.putExtra( "songid", "" );
//////                intent.putExtra("songid", item.getId());
//                        startActivity(intent);
//                        break;
//                    case 1:
////                        toast( "导出" );
//                        intent = new Intent(LocalMusicIndexActivity.this, ExportProActivity.class);
////                intent.putExtra( "songid", "" );
//////                intent.putExtra("songid", item.getId());
//                        startActivity(intent);
//                        break;
//                    case 2:
////                        toast( "搜索" );
////                        intent = new Intent( LocalMusicIndexActivity.this, SearchActivity.class );
//                        intent = new Intent(LocalMusicIndexActivity.this, SearchLocalMySong.class);
//                        intent.putExtra("search", "1");
//////                intent.putExtra("songid", item.getId());
//                        startActivity(intent);
//                        break;
//                    case 3:
////                        toast( "全部" );
//
//                        break;
//                    case 11://监控窗口关闭
//                        break;
//
//                }
//                changopenChoose = false;
////                    flower_count.selectAll();
////                    isOtherflower = true;
////                    list_count.setBackgroundResource( R.mipmap.origin_flower_down );
//
//            }
//        });
//    }
//
//
//    private void openChooseOther() {
//        if (changopenChoose) {
//            changopenChoose = false;
//            return;
//        }
//        changopenChoose = true;
//        // 点击帅选按钮,打开帅选item
//        PopUpChooseOtherUtils.getEntrance(this, null).showPopupWindow(bt_right, new PopUpChooseOtherUtils.GoSearchForHistory() {
//            @Override
//            public void goSearchForHistory(String searchStr) {
//                Intent intent;
//                handler.sendEmptyMessageDelayed(11, 500);
//                switch (Integer.parseInt(searchStr)) {
//                    case 0:
////                        toast( "批量删除" );
//                        intent = new Intent(LocalMusicIndexActivity.this, DelectOtherProActivity.class);//
////                        Bundle bundle = new Bundle();
////                        bundle.putSparseParcelableArray( "songList", (SparseArray<? extends Parcelable>) songList );
//                        intent.putExtra("songList", (Serializable) songList);
//                        startActivity(intent);
//                        break;
//                    case 1:
////
////                        toast( "搜索" );
////                        intent = new Intent( LocalMusicIndexActivity.this, SearchActivity.class );
//                        intent = new Intent(LocalMusicIndexActivity.this, SearchLocalOtherSong.class);
//                        intent.putExtra("search", "1");
//
//                        startActivity(intent);
//                        break;
//                    case 2:
////
//                        break;
//                    case 3:
////               }
//                        break;
//                    case 11://监控窗口关闭
//                        break;
//
//                }
//                changopenChoose = false;
////                    flower_count.selectAll();
////                    isOtherflower = true;
////                    list_count.setBackgroundResource( R.mipmap.origin_flower_down );
//
//            }
//        });
//    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("fragmentitem","sing");
//        startActivity(intent);
//        finishActivity();
//        return super.onKeyDown(keyCode, event);
//    }
//
//}
