package com.changxiang.vod;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.view.Indicator;
import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.entry.Banner;
import com.changxiang.vod.module.entry.NearPerson;
import com.changxiang.vod.module.musicInfo.DisplayUtil;
import com.changxiang.vod.module.ui.adapter.NearbyPersonAdapter;
import com.changxiang.vod.module.ui.adapter.VideoPagerAdapter;
import com.changxiang.vod.module.ui.addlocal.UpdateLocalVideoActivity;
import com.changxiang.vod.module.ui.agora.VideoChatViewActivity;
import com.changxiang.vod.module.ui.base.FragmentVideo1;
import com.changxiang.vod.module.ui.oratorio.CameraOratorioActivity;
import com.changxiang.vod.module.ui.singermusic.SingerIndexNewActivity;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.localmusic.LocalMusicIndexActivity;
import com.changxiang.vod.module.ui.oratorio.OratorioActivity;
import com.changxiang.vod.module.ui.recently.RecentlyIndexActivity;
import com.changxiang.vod.module.ui.typemusic.TypeIndexActivityold;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private NearbyPersonAdapter madapter;

    private TextView tvSinger, tvType, tvTop, tvLocal, tvDownload, tvRecently, tvUpdata;//歌星点歌，分类点歌，榜单点歌，本地作品，已点歌曲，最近播放,上传视频

    private TextView ivOratorio;
    private Indicator indicator;//页面指示器
    private ViewPager viewPager;//Viewpager
    private VideoPagerAdapter pagerAdapte;//图片轮播的适配器
    private int index;//ViewPager的位置（+2）
    List<Fragment> fragments = new ArrayList<>();//
    private int rowWidth = 0;
    List<Banner> listbanner = null;
    private final LinkedList<FragmentVideo1> fragmentList = new LinkedList<>();//

    private NearPerson nearpersonall;
    private NearPerson nearperson;

    private ListView mListView;//  list_club
    private TwinklingRefreshLayout refreshLayout;
    private LinearLayout ll_no_data;//no_data_show
    private int bg[] = {R.mipmap.origin_detail_01, R.mipmap.origin_detail_02, R.mipmap.origin_detail_03, R.mipmap.origin_detail_05, R.mipmap.origin_detail_06,
            R.mipmap.origin_detail_07, R.mipmap.origin_detail_08, R.mipmap.origin_detail_09, R.mipmap.origin_detail_10, R.mipmap.origin_detail_04};
    private String nearPersonal[] = {"张三", "李四", "王五", "赵六", "七夜", "八哥", "贝贝", "京京", "欢欢", "妮妮"};
    private int recLen = 4;
    Timer timer = new Timer();

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case 1:
                break;
            case 2://刷新成功，刷新界面
                refreshLayout.setVisibility(View.VISIBLE);
                ll_no_data.setVisibility(View.GONE);
                madapter.setDataList(nearperson.getResults());
//                handler.sendEmptyMessageDelayed( 2, 100 );
                break;
            case 3://加载更多成功，刷新界面
                madapter.addDataList(nearperson.getResults());
//                handler.sendEmptyMessageDelayed( 3, 100 );
                break;
            case 4://轮播图数据
                if (listbanner != null) {
                    initViewPagerData();
                    initPage();
                }

//                handler.sendEmptyMessageDelayed( 4, 100 );
                break;

            case 666://自动轮播
                if (pagerAdapte != null && viewPager != null && indicator != null) {
                    int count = pagerAdapte.getCount();
                    if (count > 2) {
                        index = viewPager.getCurrentItem();
                        index = index % (count - 2) + 1;
                        viewPager.setCurrentItem(index, true);
                        indicator.setIndex(index - 1);
//                        handler.sendEmptyMessageDelayed(666, 4000);
                    }
                }

                break;

        }

    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            recLen--;
            if (recLen == 0) {
                handler.sendEmptyMessageDelayed(666, 10);
                recLen = 4;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        creatDir();
        getBananaData();
        getnearpersonData();
    }

    public void getnearpersonData() {
//        private NearPerson nearperson;
        nearperson = new NearPerson();
        nearperson.setCurPage(1);
        nearperson.setFirst(true);
        nearperson.setLast(true);
        nearperson.setPageCount(1);
        nearperson.setPageSize(1);
        nearperson.setTotal(10);
        List<NearPerson.ResultsBean> resultsAll = new ArrayList<>();
        resultsAll.clear();

        for (int i = 0; i < 10; i++) {
            NearPerson.ResultsBean mresults = new NearPerson.ResultsBean();
            mresults.setId(bg[i] + "");
            mresults.setName(nearPersonal[i]);//名字
            mresults.setSex("1");
            mresults.setDistance(100 * (i + 1) + "");//距离
            mresults.setLevel((i + 1) + "");
            mresults.setLevelName((i + 1) + "等级");
            resultsAll.add(mresults);
        }
        nearperson.setResults(resultsAll);

        handler.sendEmptyMessageDelayed(2, 100);
    }

    public void getBananaData() {
        listbanner = new ArrayList<>();
        listbanner.clear();
        for (int i = 0; i < 5; i++) {
            Banner mBanner = new Banner();
            mBanner.setImg(bg[i] + "");
            mBanner.setName("轮播图");
            mBanner.setUrl("");
            listbanner.add(mBanner);
        }

        handler.sendEmptyMessageDelayed(4, 100);
    }

    public void initView() {

        mListView = (ListView) findViewById(R.id.list_club);// 我的头像 civ_my_image

        ll_no_data = (LinearLayout) findViewById(R.id.ll_no_data_show);//无数据
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);


        viewPager = (ViewPager) findViewById(R.id.homeactivity_sing_vp);
        indicator = (Indicator) findViewById(R.id.homeactivity_indicator);

        tvSinger = (TextView) findViewById(R.id.choose_tvSinger);
        tvType = (TextView) findViewById(R.id.choose_tvType);
        tvTop = (TextView) findViewById(R.id.choose_tvTop);
        tvLocal = (TextView) findViewById(R.id.choose_tvLocal);
        tvDownload = (TextView) findViewById(R.id.choose_tvDownload);
        tvRecently = (TextView) findViewById(R.id.choose_tvRecently);
        ivOratorio = (TextView) findViewById(R.id.choose_tvOratorio);
        tvUpdata = (TextView) findViewById(R.id.choose_tvUpdata);


        tvSinger.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvTop.setOnClickListener(this);
        tvLocal.setOnClickListener(this);
        tvDownload.setOnClickListener(this);
        tvRecently.setOnClickListener(this);
        ivOratorio.setOnClickListener(this);
        tvUpdata.setOnClickListener(this);


        madapter = new NearbyPersonAdapter(this);
//        madapter.setListener(this);
        mListView.setAdapter(madapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                toast( "1111111111111111" );

                Intent intent;

                intent = new Intent(MainActivity.this, VideoChatViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("nearperson", nearperson.getResults().get(position));
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
            //x下拉刷新
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        curPage = 1;
//                        getNearPersonData();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            //上拉加载
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        curPage++;
//                        if (islast) {
//                            toast(getString(R.string.no_more_data));
//                            refreshLayout.finishLoadmore();
//                            return;
//                        }
//                        getNearPersonData();

                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (listbanner.size() > 1) {//多于1，才会循环跳转
                    if (position < 1) {
                        position = listbanner.size();
                        viewPager.setCurrentItem(position, false);
                        indicator.setIndex(position - 1);
                    } else if (position > listbanner.size()) {
                        viewPager.setCurrentItem(1, false);
                        position = 1;
                        indicator.setIndex(0);
                    } else {
                        indicator.setIndex(position - 1);
                    }
//                    tvName.setText(mp3List.get(position - 1).getName() + "--" + mp3List.get(position - 1).getSingerName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (state==2){
//                    stopTimer();
//                }else if (state==1){
//                    stopTimer();
//                }

            }
        });
        timer.schedule(task, 1000, 1000);       // timeTask
    }

    private void creatDir() {
        //创建文件夹  static
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.sysout("------------------------------------requestPermission");
            requestPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE}, 0x002);
        } else {
            makeDirs();
        }
    }


    @Override
    public void permissionSuccess(int request_code_permission) {
        super.permissionSuccess(request_code_permission);
        if (request_code_permission == 0x002) {
            makeDirs();
        }
    }

    private void makeDirs() {
        File lyric = MyFileUtil.getDir("lyric");
        File image = MyFileUtil.getDir("image");
        File cache = MyFileUtil.getDir("cache");
        File apk = MyFileUtil.getDir("apk");
        File mp3 = MyFileUtil.getDir("mp3");
        File video = MyFileUtil.getDir("video");
        File accompany = MyFileUtil.getDir("accompany");
        File krc = MyFileUtil.getDir("krc");
        File work = MyFileUtil.getDir("work");
        File cacheMp3 = MyFileUtil.getDir("cache/MP3");
        File cacheMp4 = MyFileUtil.getDir("cache/MP4");
        File compose = MyFileUtil.getDir("compose");
        File diycrop = MyFileUtil.getDir("diycrop");
        File diymv = MyFileUtil.getDir("diymv");
        File gifbitmap = MyFileUtil.getDir("gifbitmap");
        File gifcache = MyFileUtil.getDir("gifcache");
        File recorder = MyFileUtil.getDir("recorder");
        File muxerDecode = MyFileUtil.getDir(".muxerDecode");
//            File recorder = MyFileUtil.getDir("recorder");
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
//            case R.id.choose_back://返回
////                finishActivity();
//                break;
//            case R.id.choose_top_rl://搜索歌曲
////                intent = new Intent(this, SearchActivity.class);
////                startActivity(intent);
//                break;
            case R.id.choose_tvSinger://歌星点歌
//                intent = new Intent(this, SingerIndexActivity.class);
                intent = new Intent(this, SingerIndexNewActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_tvType://分类
                intent = new Intent(this, TypeIndexActivityold.class);
                startActivity(intent);
                break;
            case R.id.choose_tvTop://榜单
//                intent = new Intent(this, TopIndexActivity.class);
//                startActivity(intent);
                break;
            case R.id.choose_tvLocal://本地作品
                intent = new Intent(this, LocalMusicIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_tvDownload://已点歌曲
                toast("准放置将要播放的歌曲列表");
//                intent = new Intent(this, DownloadSongActivity.class);
//                startActivity(intent);
                break;
            case R.id.choose_tvRecently://最近播放
                intent = new Intent(this, RecentlyIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_tvOratorio://清唱
//                intent = new Intent(this, OratorioActivity.class);
                intent = new Intent(this, CameraOratorioActivity.class);
                startActivity(intent);
//                musicPlay(false);
                break;
            case R.id.choose_tvUpdata://上传视频
//                toast("上传视频");
                intent = new Intent(this, UpdateLocalVideoActivity.class);
                startActivity(intent);
//                musicPlay(false);
                break;
        }

    }


    //初始化Viewpager的数据源
    private void initViewPagerData() {
        //把视频集合添加进来

        if (listbanner.size() > 0) {
            for (int i = 0; i < listbanner.size(); i++) {

                Banner homeBean = listbanner.get(i);
                if (homeBean != null) {
                    FragmentVideo1 video1 = new FragmentVideo1();
                    video1.setImgUrl(homeBean.getImg());
//                    if (homeBean.getUrl() != null && homeBean.getUrl().length() > 0) {
                    video1.setBannerItem(homeBean);
//                    }
                    fragmentList.add(video1);
                    String url = homeBean.getUrl();
                    String songName = homeBean.getName();
//                    video1.setiOnItemClickListener(new FragmentVideo1.IOnItemClickListener(i,));
                }
            }
        }
    }


    //显示热听推荐，初始化数据
    private void initPage() {
        if (listbanner != null && listbanner.size() > 0) {
            indicator.setCount(listbanner.size());
            indicator.setPadding(DisplayUtil.dip2px(this, 8));
            indicator.setRadius(DisplayUtil.dip2px(this, 4));
            for (int i = 0; i < fragmentList.size(); i++) {
                final int position = i;
                fragmentList.get(i).setiOnItemClickListener(i, new FragmentVideo1.IOnItemClickListener() {
                    @Override
                    public void onItemClick() {

                    }
                });
            }
            FragmentVideo1 fragmentVideo1 = new FragmentVideo1();
            fragmentVideo1.setImgUrl(fragmentList.get(fragmentList.size() - 1).getImgUrl());
            fragmentVideo1.setiOnItemClickListener(fragmentList.size() - 1, fragmentList.get(fragmentList.size() - 1).getiOnItemClickListener());
            FragmentVideo1 fragmentVideoN = new FragmentVideo1();
            fragmentVideoN.setImgUrl(fragmentList.get(0).getImgUrl());
            fragmentVideoN.setiOnItemClickListener(0, fragmentList.get(0).getiOnItemClickListener());
            fragmentList.addFirst(fragmentVideo1);
            fragmentList.addLast(fragmentVideoN);
            pagerAdapte = new VideoPagerAdapter(getSupportFragmentManager(), fragmentList);
            viewPager.setAdapter(pagerAdapte);
            viewPager.getChildCount();
            viewPager.setCurrentItem(1);
//            tvName.setText(mp3List.get(0).getName() + "--" + mp3List.get(0).getSingerName());
//            BaseApplication.state = NOT_START;
        }
    }

}
