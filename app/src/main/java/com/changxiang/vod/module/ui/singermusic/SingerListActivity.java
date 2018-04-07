package com.changxiang.vod.module.ui.singermusic;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.ImageLoader;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.view.MyAlertDialog;
import com.changxiang.vod.common.view.ScrollChangeScrollView;
import com.changxiang.vod.common.view.WrapListView;
import com.changxiang.vod.module.base.AdapterListListener;
import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.db.DownloadManager;
import com.changxiang.vod.module.db.LocalMusicManager;
import com.changxiang.vod.module.db.LocalSingersManager;
import com.changxiang.vod.module.db.ParameterBean;
import com.changxiang.vod.module.db.Singers;
import com.changxiang.vod.module.db.VodMedia;
import com.changxiang.vod.module.engine.JsonParserFirst;
import com.changxiang.vod.module.entry.HotSong;
import com.changxiang.vod.module.entry.SongDetail;
import com.changxiang.vod.module.service.receiver.MusicReceiver;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.localmusic.GeWangPlayerActivity;
import com.changxiang.vod.module.ui.localmusic.MusicPlayerActivity;
import com.changxiang.vod.module.ui.recently.ISetProgress;
import com.changxiang.vod.module.ui.recordmusic.OpenCameraActivity;
import com.changxiang.vod.module.ui.search.SearchActivity;
import com.changxiang.vod.module.ui.singermusic.adapter.SingerSongAdapter;
import com.jaeger.library.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SingerListActivity extends BaseActivity implements View.OnClickListener,
        AdapterListListener<List<VodMedia>>, ISetProgress {


    private String singerid = "-1";//封面地址
    private String imgCover;//封面地址
    private String imgCoverold;//封面地址
    private String singername;//封面地址
    private String songcount;//封面地址
    private List<VodMedia> existList;
    private List<VodMedia> songListall;
    private final List<HotSong> allSongList = new ArrayList<>();//用于封装动态列表数据（歌曲）
    private boolean islast = false;
    private boolean islastsearch = false;
    private SingerSongAdapter madapter;

    private ImageView im_imgCover;//歌手封面图片
    private WrapListView singer_list;//xl_singer_list
    private ScrollChangeScrollView scrollView;//
    private RelativeLayout rl_singer_info;//rl_singer_info 歌手名称
    private TextView singer_info;//tv_singer_info 歌手名称
    private TextView song_count;//tv_singer_info 歌曲统计

    /*//搜索控件
    private ImageView voice;// btn_voice  ；录音
    private ImageView search;  //  btn_seach  搜索按钮
    private EditText content;//  emoji_edit  输入内容*/
    private LinearLayout search_top;
    private ImageView ivBack;//返回键
    private RelativeLayout searchRl;//搜索点击框
    private TextView rightIv;//已点歌单按钮
    private TextView errorTv;//数据为空时显示的图片

    private int curPage = 1;//当前值为热门推荐分页计数
    private int rowWidth = 0;
    //    private int rowheight = 0;
    private TwinklingRefreshLayout refreshLayout;
    private String responsemsg = "请求数据为空";//网络请求返回信息
    private boolean isSearch = false;
    private MusicReceiver musicReceiver;//广播接收器
    private String songId, musicType, songName, singerName;//歌曲id,音乐类型（mp3,mv）,歌曲名
    private final SparseIntArray isDownloadList = new SparseIntArray();//歌曲文件是否下载完成的集合
    private int downloadStatue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
    private final String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//
    private final String mp4Dir = MyFileUtil.DIR_VEDIO.toString() + File.separator;//
    private final String accDir = MyFileUtil.DIR_ACCOMPANY.toString() + File.separator;//
    private final String lrcDir = MyFileUtil.DIR_LRC.toString() + File.separator;//
    private final String krcDir = MyFileUtil.DIR_KRC.toString() + File.separator;//
    private File ycFile;//已下载歌曲的文件
    private File bzFile;//
    private File lrcFile;//
    private File krcFile;//
    private boolean isAllDownload;
    private DownloadManager dao;
    private List<VodMedia> mList;//歌曲列表对象集合
    private View dialog;//对话框布局
    private SongDetail songDetail;//播放歌曲对象

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case -1://请求失败
                toast(getString(R.string.get_no_data));
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                break;
            case 0://服务器查询失败（code）
                toast(responsemsg);
                break;
            case 1://返回数据为空
                errorTv.setVisibility(View.VISIBLE);
                ImageLoader.getImageViewLoad(im_imgCover, "", R.drawable.tv_mv);
                break;
            case 2://刷新成功，刷新界面
                errorTv.setVisibility(View.GONE);
//                beanList = songList;
//                madapter.setIsDownloadList(isDownloadList);
                madapter.setDataList(mList);
//                ImageLoader.getImageViewLoad(im_imgCover, songList.getResults().get(0).getImgAlbumUrl(), R.drawable.tv_mv);//第一张封面图：
                if (imgCoverold != null && imgCoverold.length() > 0) {
                    ImageLoader.getImageViewLoad(im_imgCover, imgCoverold, R.drawable.tv_mv);//传递过来的封面图：
                } else {
//                    ImageLoader.getImageViewLoad(im_imgCover, songList.getResults().get(0).getImgAlbumUrl(), R.drawable.tv_mv);//第一张封面图：
                }
//                handler.sendEmptyMessageDelayed( 2, 100 );
                break;
            case 3://加载更多成功，刷新界面
                errorTv.setVisibility(View.GONE);
                madapter.setIsDownloadList(isDownloadList);
                madapter.addDataList(existList);
//                handler.sendEmptyMessageDelayed( 3, 100 );
                break;
            case 123://权限
//                updataimage();
//                handler.sendEmptyMessageDelayed( 123, 100 );
                break;

        }
        refreshLayout.finishLoadmore();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_song_list);
        dao = DownloadManager.getDownloadManager(this);
        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
//        songList = new SongList();
        songListall = new ArrayList<>();
        Intent intent = this.getIntent();
        singerid = intent.getStringExtra("singerid");
        imgCover = intent.getStringExtra("imgCover");
        imgCoverold = intent.getStringExtra("imgCover");
        singername = intent.getStringExtra("singername");
        songcount = intent.getStringExtra("songcount");

        int type = intent.getIntExtra("type", 1);
//        Intent intent = getIntent();
        String typeName = intent.getStringExtra("type");
        String code = intent.getStringExtra("code");
        rowWidth = this.getResources().getDisplayMetrics().widthPixels;
//        rowheight = this.getResources().getDisplayMetrics().widthPixels * 9 / 16;//宽高比例为16：9
        initView();
        initData();
        initEvent();
        curPage = 1;
        getdbData();
//        getSearchBysinsgerData();//根据歌手获取歌曲
//        mInstaller = new ApkInstaller(SingerListActivity.this);
    }

    private LocalMusicManager mLocalMusicManager;//数据库：

    public void getdbData() {

        mLocalMusicManager = LocalMusicManager.getComposeManager(getApplicationContext());
        mList = new ArrayList<>();
        getDataFormBD();//获取本地数据库数据

    }

    private void getDataFormBD() {

        if (existList != null && existList.size() > 0) {
            existList.clear();
        } else {
            existList = new ArrayList<>();
        }
        if (mList != null && mList.size() > 0) {
            mList.clear();
        } else {
            mList = new ArrayList<>();
        }
//        mList = mLocalMusicManager.queryAll();//全部搜索
//        mList = mLocalMusicManager.SingerQuery("刘德华");
        mList = mLocalMusicManager.fuzzyQuery("刘德华");
//        mList = mLocalMusicManager.fuzzyQuery(singername);
//        mList = mLocalMusicManager.fuzzyQuery(singername);

//        mList = mComposeManager.queryNoDelect();
//        if (mList.size() > 0) {
//            for(int i = 0;i<mList.size();i++ ){
//                mComposeManager.updateCompose(mList.get( i ).getCompose_id(),"Compose_delete","0"  );//标志数据库为已经上传
//            }
//            mComposeManager.updateCompose(mList.get( 0 ).getCompose_id(),"Compose_delete","1"  );//标志数据库为已经上传
//        }
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                VodMedia mVodMedia = mList.get(i);
                existList.add(mVodMedia);
            }

            if (existList.size() > 0) {

                handler.sendEmptyMessageDelayed(2, 100);
            } else {
                handler.sendEmptyMessageDelayed(1, 100);
            }
        } else {
            handler.sendEmptyMessageDelayed(1, 100);
        }
    }

    private void initView() {

        rl_singer_info = (RelativeLayout) findViewById(R.id.rl_singer_info);
        singer_info = (TextView) findViewById(R.id.tv_singer_name);
        song_count = (TextView) findViewById(R.id.tv_song_count);

        im_imgCover = (ImageView) findViewById(R.id.iv_singer_icom);
        scrollView = (ScrollChangeScrollView) findViewById(R.id.scrollView);
        search_top = (LinearLayout) findViewById(R.id.search_top_ll);
        ivBack = (ImageView) findViewById(R.id.search_back);
        searchRl = (RelativeLayout) findViewById(R.id.search_top_rl);
        rightIv = (TextView) findViewById(R.id.has_selected_song);
        /*bt_back = (ImageView) findViewById( R.id.back_last );
        top_text = (TextView) findViewById( R.id.center_text );
        bt_right = (ImageView) findViewById( R.id.back_next );*/
        /*voice = (ImageView) findViewById( R.id.imv_voice );// btn_voice  ；录音
        search = (ImageView) findViewById( R.id.btn_search );  //  btn_seach  搜索按钮
        content = (EditText) findViewById( R.id.emoji_edit );//  emoji_edit  输入内容*/
        singer_list = (WrapListView) findViewById(R.id.xl_singer_list);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        errorTv = (TextView) findViewById(R.id.fragment_works_default);
        madapter = new SingerSongAdapter(this);
        madapter.setListener(this);
        singer_list.setAdapter(madapter);

        //TODO  设置歌手封面宽高
        im_imgCover.setLayoutParams(new RelativeLayout.LayoutParams(rowWidth, rowWidth * 9 / 16));
        ivBack.setOnClickListener(this);
        searchRl.setOnClickListener(this);
        rightIv.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
            //x下拉刷新
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        curPage = 1;
//
                        if (isSearch) {
//                            toast( "1111111111111" );

                            //getSearchData();//首次请求数据
                        } else {
//                            toast( "2223333333333" );

//                            getSearchBysinsgerData();//根据歌手获取歌曲
                        }

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
                        curPage++;
//                        getClubData();//首次请求数据
                        if (isSearch) {
//                            toast( "2222222222" );
                            if (islastsearch) {
                                toast(getString(R.string.no_more_data));
                                refreshLayout.finishLoadmore();
                                return;
                            }
                            //getSearchData();//首次请求数据
                        } else {
//                            toast( "21111111111" );
                            if (islast) {
                                toast(getString(R.string.no_more_data));
                                refreshLayout.finishLoadmore();
                                return;
                            }
//                            getSearchBysinsgerData();//根据歌手获取歌曲
                        }
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

        registerReceiver();

        StatusBarUtil.setTranslucentForImageView(this, 0, findViewById(R.id.search_top));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setColor(SingerListActivity.this, Color.argb(70, 0, 0, 0), 0);
        }
    }

    //注册下载进度监听器
    private void registerReceiver() {
        musicReceiver = new MusicReceiver();
        musicReceiver.setiSetProgress(this);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("ACTION_DOWNLOADING");
        mFilter.addAction("ACTION_FINISH");
        mFilter.addAction("ACTION_EXCEPTION");
        registerReceiver(musicReceiver, mFilter);
    }

    //反注册网络监听器
    private void unRegisterReceiver() {
        if (musicReceiver != null) {
            unregisterReceiver(musicReceiver);
        }
    }

    //清空数据
    private void clear() {
        //TODO
        if (madapter != null) {
            madapter.clear();
        }
    }

    //TODO  取得热门推荐
    private void initData() {
        if (singername != null && singername.length() > 0) {
            singer_info.setText(singername);
            singer_info.setVisibility(View.VISIBLE);
            rl_singer_info.setVisibility(View.VISIBLE);

        } else {
            singer_info.setVisibility(View.INVISIBLE);
            rl_singer_info.setVisibility(View.INVISIBLE);
        }
        if (songcount != null && songcount.length() > 3) {
            song_count.setText(songcount);
            song_count.setVisibility(View.VISIBLE);
            rl_singer_info.setVisibility(View.VISIBLE);
        } else {
            song_count.setVisibility(View.INVISIBLE);
            rl_singer_info.setVisibility(View.INVISIBLE);
        }

    }

    private void initEvent() {
        scrollView.setupTitleView(im_imgCover);
        scrollView.setListener(new ScrollChangeScrollView.IOnScrollListener() {
            @Override
            public void onScroll(int tag, int scrollY) {
                if (scrollY == 0) {
                    search_top.setBackgroundColor(Color.parseColor("#44000000"));
                    StatusBarUtil.setColor(SingerListActivity.this, Color.argb(70, 0, 0, 0), 0);
                    return;
                }
                float scale = (float) scrollY / im_imgCover.getMeasuredHeight();
                float alpha = (255 * scale);
                if (tag == 2) {
                    StatusBarUtil.setColor(SingerListActivity.this, Color.argb((int) alpha, 220, 52, 42), 0);
//                    search_top.setBackgroundColor(getResources().getColor(R.color.halfBlack));
                    search_top.setBackgroundColor(Color.argb((int) alpha, 220, 52, 42));
                } else {
                    search_top.setBackgroundColor(getResources().getColor(R.color.app_red));
                    StatusBarUtil.setColor(SingerListActivity.this, Color.rgb(220, 52, 42), 0);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.search_back:
                finishActivity();
                break;
            case R.id.search_top_rl:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.has_selected_song:
//                intent = new Intent(this, DownloadSongActivity.class);
//                startActivity(intent);
                break;
            /*case R.id.back_next://主页
                intent = new Intent( SingerListActivity.this, HomeActivity.class );
//                intent.putExtra("showsearch", isShowSearch);
                startActivity( intent );
                break;
            case R.id.back_last://
                finishActivity();
                break;*/

            /*case R.id.imv_voice://录音
                toast( "点击了科大讯飞录音" );
//                Speech2Text.getSearchVoice(this,content,mIat,mSharedPreferences,mIatDialog);
                if (ContextCompat.checkSelfPermission( this,
                        Manifest.permission.RECORD_AUDIO )
                        != PackageManager.PERMISSION_GRANTED) {
                    toast( "权限得不到" );
                    ActivityCompat.requestPermissions( this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            1 );
                } else {
                    toast( "权限得到" );
                    getSearchVoice();
                }

                break;

            case R.id.btn_search://搜索按钮
                isSearch = true;
                curPage = 1;
                  songList = new SongList();
                  songListall = new SongList();
                getSearchData();
                break;*/
        }
    }

    /**
     * //根据歌手获取歌曲：
     */
//    private void getSearchBysinsgerData() {
//
////if(singerPage==0){//判断是否应该清空所有的列表数据：
////    songList.
//        if (singerid != null && !singerid.equals("")) {
//
//        } else {
//            toast("获取不到当前歌手");
//            return;
//        }
////}
//        SingUtil.api_songSearchbysinger(this, "" + singerid, "" + curPage, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                closeProgressDialog();
//                LogUtils.sysout("联网登录出现网络异常错误！");
//                handler.sendEmptyMessageDelayed(-1, 1000);
////                    stopLoadData();
////                toast( "请求失败" );
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                closeProgressDialog();
////                    stopLoadData();
//                String data = response.body().string();
//                LogUtils.sysout("当前歌星歌曲返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//                if (code == 0) {
//                    try {
//                        songList = SongList.objectFromData(data, "data");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (songList != null && !songList.equals("")) {
//                        islast = songList.isLast();
//                        if (curPage < 2) {
//                            if (songList.getTotal() == 0) {//数据为空
//                                handler.sendEmptyMessageDelayed(1, 100);
//                            } else {
//                                handler.sendEmptyMessageDelayed(2, 100);//刷新
//                                songListall = songList;
//                            }
//                            //TODO  保存数据到本地
//                        } else {
//                            try {
//                                curPage = songList.getCurPage();
//                                songListall.getResults().addAll(songList.getResults());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            handler.sendEmptyMessageDelayed(3, 100);//加载更多
//                        }
//                    } else {
//                        if (curPage < 2) {//数据为空
//                            handler.sendEmptyMessageDelayed(1, 100);
//                        }
//                    }
//                } else {
////                    closeProgressDialog();
////                    stopLoadData();
//                    //使用第一页数据，本地数据也没有，则提示无数据
//                    responsemsg = JsonParserFirst.getRetMsg(response.body().toString());
////                    toast( meg );
//                    handler.sendEmptyMessageDelayed(0, 100);
////                    toast( meg );
//                }
//            }
//        });
//    }
    @Override
    public void click(int opt, final int position, List<VodMedia> item) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (opt) {

            case 1://听歌 songListall
//                intent = new Intent(SingerListActivity.this, MusicPlayActivity.class);
                intent = new Intent(SingerListActivity.this, MusicPlayerActivity.class);
                bundle.putSerializable("songList", (Serializable) existList);
                intent.putExtras(bundle);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
            case 2://练歌
//                try {
//                    songDetail = dao.selectSong(item.get(position).getId(), item.get(position).getType());
//                    requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0002);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            case 3://下歌

                break;
            case 4://取消下载

                break;
            case 5://取消等待

                break;
        }
    }


    @Override
    public void permissionSuccess(int request_code_permission) {
        super.permissionSuccess(request_code_permission);
        switch (request_code_permission) {
            case 0x0002://开启摄像头的权限
                Intent intent = new Intent(SingerListActivity.this, OpenCameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("songDetail", songDetail);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 0x0003://开始按钮
//                freePractice();
                break;
            case 0x0004://录歌按钮
//                startRecord();
                break;
        }
    }

    private void startDownload(int position) {
//        toast("《" + allSongList.get(position).getName() + "》"+getString(R.string.download_msg));
//        madapter.setWaitDownload(position, allSongList.get(position).getId(), allSongList.get(position).getType());
//        Intent intent = new Intent(this, DownloadAllService.class);
//        ParameterBean parameterBean = new ParameterBean(allSongList.get(position).getId(), allSongList.get(position).getType(), allSongList.get(position).getName(), allSongList.get(position).getSingerName(), position);
//        parameterBean.setImgCover(allSongList.get(position).getImgAlbumUrl());
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("parameterBean", parameterBean);
//        intent.putExtras(bundle);
//        intent.setAction("ACTION_START");
//        startService(intent);
    }

    /**
     * 流量提醒对话框
     *
     * @param position
     * @param isNoticeOnce 是否已经提醒过了（首次提醒）
     */
    private void netNotice(final int position, final int isNoticeOnce) {
        FrameLayout parent = (FrameLayout) dialog.getParent();
        if (parent != null) {
            parent.removeView(dialog);
        }
        new MyAlertDialog(this, dialog).builder()
                .setMsg(getString(R.string.wifiDisconnected_notice))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.isNoticeOnce = isNoticeOnce;
                        startDownload(position);
                    }
                }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setDownloadProgress(int position, int percent, String songId, String musicType) {

        if (madapter != null) {
            madapter.setDownloadProgress(position, percent, songId, musicType);

        }
        LogUtils.w("TAG1", "Activity==position:" + position + ",percent" + percent);
    }

    @Override
    public void setFinishImg(int position, String songId, String musicType) {

        if (madapter != null) {
            madapter.setFinishImg(position, songId, musicType);
        }
    }

    @Override
    public void setOnException(int position, String songId, String musicType) {
        if (madapter != null) {
            madapter.setOnError(position, songId, musicType);
        }
    }

}
