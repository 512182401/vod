//package com.changxiang.vod.module.ui.typemusic;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.util.SparseIntArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.iflytek.cloud.ErrorCode;
//import com.iflytek.cloud.InitListener;
//import com.iflytek.cloud.SpeechConstant;
//import com.iflytek.cloud.SpeechRecognizer;
//import com.iflytek.cloud.ui.RecognizerDialog;
//import com.iflytek.sunflower.FlowerCollector;
//import com.jaeger.library.StatusBarUtil;
//import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.ImageLoader;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.view.MyAlertDialog;
//import com.quchangkeji.tosing.common.view.ScrollChangeScrollView;
//import com.quchangkeji.tosing.common.view.WrapListView;
//import com.quchangkeji.tosing.module.base.AdapterListListener;
//import com.quchangkeji.tosing.module.base.BaseApplication;
//import com.quchangkeji.tosing.module.db.DownloadManager;
//import com.quchangkeji.tosing.module.db.ParameterBean;
//import com.quchangkeji.tosing.module.db.SongDetail;
//import com.quchangkeji.tosing.module.engine.JsonParserFirst;
//import com.quchangkeji.tosing.module.entry.HotSong;
//import com.quchangkeji.tosing.module.entry.SongList;
//import com.quchangkeji.tosing.module.ui.base.BaseActivity;
//import com.quchangkeji.tosing.module.ui.listening.MusicPlayActivity;
//import com.quchangkeji.tosing.module.ui.music_download.downloadservice.DownloadAllService;
//import com.quchangkeji.tosing.module.ui.music_download.receiver.MusicReceiver;
//import com.quchangkeji.tosing.module.ui.search.SearchActivity;
//import com.quchangkeji.tosing.module.ui.sing.DownloadSongActivity;
//import com.quchangkeji.tosing.module.ui.sing.OpenCameraActivity;
//import com.quchangkeji.tosing.module.ui.singermusic.ApkInstaller;
//import com.quchangkeji.tosing.module.ui.topmusic.ISetProgress;
//import com.quchangkeji.tosing.module.ui.typemusic.adapter.TypeSongAdapter;
//import com.quchangkeji.tosing.module.util.MyFileUtil;
//import com.quchangkeji.tosing.module.util.SingUtil;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//public class TypeListActivity extends BaseActivity implements View.OnClickListener, AdapterListListener<List<SongList.ResultsBean>>, ISetProgress {
//    int width, height;
//    /**
//     * 讯飞
//     */
//    private static String TAG = TypeListActivity.class.getSimpleName();
//    // 语音听写对象
//    private SpeechRecognizer mIat;
//    // 语音听写UI
//    private RecognizerDialog mIatDialog;
//    // 用HashMap存储听写结果
//    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
//    private SharedPreferences mSharedPreferences;
//    // 引擎类型
//    private String mEngineType = SpeechConstant.TYPE_CLOUD;
//    // 语记安装助手类
//    private ApkInstaller mInstaller;
//
//    //    private String imgCover;//封面地址
//    private SongList songList;
//    private SongList songListall;
//
//    boolean islast = false;
//    boolean islastsearch = false;
//
//    //TODO
////    item_singer_index.xml
//    private TypeSongAdapter madapter;
//    /*private TextView top_text;//顶部居中显示
//    private ImageView bt_right;//右边显示
//    private ImageView bt_back;//返回*/
//    private ScrollChangeScrollView scrollView;//
//    private ImageView im_imgCover;//歌手封面图片
//    private WrapListView singer_list;//xl_singer_list
//
//    //搜索控件
//    /*private ImageView voice;// btn_voice  ；录音
//    private ImageView search;  //  btn_seach  搜索按钮
//    private EditText content;//  emoji_edit  输入内容*/
//    private LinearLayout search_top;
//    private ImageView ivBack;//返回键
//    private RelativeLayout searchRl;//搜索点击框
//    private TextView rightIv;//已点歌单按钮
//    private TextView nodata_show;//没有数据
//    private int curPage = 1;//当前值为热门推荐分页计数
//    private int singerPage = 0;//当前值为歌手分页计数
//    private boolean first = true;
//    private boolean last = false;//当前页是否是最后一页
//    private int rowWidth = 0;
//    private int rowheight = 0;
//    // intent.putExtra("type", "2");//1：表示此歌手，2表示此类型；
////    List<ListBean> allList = new ArrayList<>();//用于封装动态列表数据（歌曲）
//    private List<HotSong> allSongList = new ArrayList<>();//用于封装动态列表数据（歌曲）
//    private String typeName, code;//板块名称，类型编码
//    private TwinklingRefreshLayout refreshLayout;
//    private String responsemsg = "请求数据为空";//网络请求返回信息
//    private boolean isSearch = false;
//    private MusicReceiver musicReceiver;//广播接收器
//
//    private String songId, musicType, songName, singerName;//歌曲id,音乐类型（mp3,mv）,歌曲名
//    private SparseIntArray isDownloadList = new SparseIntArray();//歌曲文件是否下载完成的集合
//    private int downloadStatue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
//    private String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//
//    private String mp4Dir = MyFileUtil.DIR_VEDIO.toString() + File.separator;//
//    private String accDir = MyFileUtil.DIR_ACCOMPANY.toString() + File.separator;//
//    private String lrcDir = MyFileUtil.DIR_LRC.toString() + File.separator;//
//    private String krcDir = MyFileUtil.DIR_KRC.toString() + File.separator;//
//    private File ycFile;//已下载歌曲的文件
//    private File bzFile;//
//    private File lrcFile;//
//    private File krcFile;//
//    private boolean isAllDownload;
//    private DownloadManager dao;
//    private List<SongList.ResultsBean> beanList;//歌曲列表对象集合
//    private View dialog;//对话框布局
//    private SongDetail songDetail;//播放歌曲对象
//
//    @Override
//    public void handMsg(Message msg) {
//        switch (msg.what) {
//            case -1://请求失败
//                toast(getString(R.string.get_no_data));
////                    handler.sendEmptyMessageDelayed( -1, 1000 );
//                break;
//            case 0://服务器查询失败（code）
//                toast(responsemsg);
//                break;
//            case 1://返回数据为空
////                toast(responsemsg);//
//                nodata_show.setVisibility(View.VISIBLE);
//                singer_list.setVisibility(View.GONE);
//                ImageLoader.getImageViewLoad(im_imgCover, "", R.drawable.tv_mv);
////                handler.sendEmptyMessageDelayed( 1, 100 );
//                break;
//            case 2://刷新成功，刷新界面
//                nodata_show.setVisibility(View.GONE);
//                singer_list.setVisibility(View.VISIBLE);
//                beanList = songList.getResults();
//                hasFileAndRecord(beanList);
//                madapter.setIsDownloadList(isDownloadList);
//                madapter.setDataList(songList.getResults());
//                ImageLoader.getImageViewLoad(im_imgCover, songList.getResults().get(0).getImgAlbumUrl(), R.drawable.tv_mv);
////                handler.sendEmptyMessageDelayed( 2, 100 );
//                break;
//            case 3://加载更多成功，刷新界面
//                nodata_show.setVisibility(View.GONE);
//                singer_list.setVisibility(View.VISIBLE);
//                addFileAndRecord(songList.getResults());
//                madapter.setIsDownloadList(isDownloadList);
//                madapter.addDataList(songList.getResults());
////                handler.sendEmptyMessageDelayed( 3, 100 );
//                break;
//            case 123://权限
////                updataimage();
////                handler.sendEmptyMessageDelayed( 123, 100 );
//                break;
//
//        }
//        refreshLayout.finishLoadmore();
//
//    }
//
//    //判断歌曲是否已下载
//    public void hasFileAndRecord(List<SongList.ResultsBean> list) {
//        if (list != null && list.size() > 0) {
//            allSongList.clear();
////            ids.clear();
////            types.clear();
////            names.clear();
////            singerNames.clear();
//            HotSong mHotSong = new HotSong();
//            for (int i = 0; i < list.size(); i++) {
//                mHotSong = new HotSong();
//                mHotSong.setId(list.get(i).getId());
//                mHotSong.setType(list.get(i).getType());
//                mHotSong.setName(list.get(i).getName());
//                mHotSong.setSingerName(list.get(i).getSingerName());
//                mHotSong.setImgAlbumUrl(list.get(i).getImgAlbumUrl());
//                mHotSong.setNum(list.get(i).getNum());
//                mHotSong.setSize(list.get(i).getSize());
//                allSongList.add(mHotSong);
//
//                songId = list.get(i).getId();
//                musicType = list.get(i).getType();
//                songName = list.get(i).getName();
//                singerName = list.get(i).getSingerName();
//                String imgCover = list.get(i).getImgAlbumUrl();
////                ids.add(songId);
////                types.add(musicType);
////                names.add(songName);
////                singerNames.add(singerName);
////                imgCoverList.add(imgCover);
//                if ("video".equals(musicType)) {
//                    ycFile = new File(mp4Dir + singerName + "-" + songName + ".mp4");//
//                    bzFile = new File(accDir + singerName + "-" + songName + ".mp4");//
//                } else if ("audio".equals(musicType)) {
//                    ycFile = new File(mp3Dir + singerName + "-" + songName + ".mp3");//
//                    bzFile = new File(accDir + singerName + "-" + songName + ".mp3");//
//                }
//                lrcFile = new File(lrcDir + singerName + "-" + songName + ".lrc");//
//                krcFile = new File(krcDir + singerName + "-" + songName + ".krc");//
//                if (lrcFile.exists() && ycFile.exists() && bzFile.exists() && krcFile.exists()) {
//                    isAllDownload = dao.isAllDownload(songId, musicType);
//                    if (isAllDownload) {
//                        downloadStatue = 3;
//                    } else {
//                        if (DownloadAllService.isWaiting(songId, musicType)) {
//                            downloadStatue = 1;
//                        } else {
//                            downloadStatue = 0;
//                        }
//                    }
//                } else {
//                    if (DownloadAllService.isWaiting(songId, musicType)) {
//                        downloadStatue = 1;
//                    } else {
//                        downloadStatue = 0;
//                    }
//                }
//                isDownloadList.put(i, downloadStatue);
//            }
//        }
//    }
//
//    //判断刷新的歌曲是否已下载
//    public void addFileAndRecord(List<SongList.ResultsBean> list) {
//        if (list != null && list.size() > 0) {
//            int num = beanList.size();
//            beanList.addAll(list);
//
////            allSongList.clear();
//            HotSong mHotSong = new HotSong();
//            for (int i = 0; i < list.size(); i++) {
//                mHotSong = new HotSong();
//                mHotSong.setId(list.get(i).getId());
//                mHotSong.setType(list.get(i).getType());
//                mHotSong.setName(list.get(i).getName());
//                mHotSong.setSingerName(list.get(i).getSingerName());
//                mHotSong.setImgAlbumUrl(list.get(i).getImgAlbumUrl());
//                mHotSong.setNum(list.get(i).getNum());
//                mHotSong.setSize(list.get(i).getSize());
//                allSongList.add(mHotSong);
//
//
//                songId = list.get(i).getId();
//                musicType = list.get(i).getType();
//                songName = list.get(i).getName();
//                singerName = list.get(i).getSingerName();
//                String imgCover = list.get(i).getImgAlbumUrl();
////                ids.add(songId);
////                types.add(musicType);
////                names.add(songName);
////                singerNames.add(singerName);
////                imgCoverList.add(imgCover);
//                if ("video".equals(musicType)) {
//                    ycFile = new File(mp4Dir + singerName + "-" + songName + ".mp4");//
//                    bzFile = new File(accDir + singerName + "-" + songName + ".mp4");//
//                } else if ("audio".equals(musicType)) {
//                    ycFile = new File(mp3Dir + singerName + "-" + songName + ".mp3");//
//                    bzFile = new File(accDir + singerName + "-" + songName + ".mp3");//
//                }
//                lrcFile = new File(lrcDir + singerName + "-" + songName + ".lrc");//
//                krcFile = new File(krcDir + singerName + "-" + songName + ".krc");//
//                /*if (lrcFile.exists() && ycFile.exists() && bzFile.exists() && krcFile.exists()) {
//                    isAllDownload = dao.isAllDownload(songId, musicType);
//                    isDownloadList.put(i+num, isAllDownload);
//                } else {
//                    isDownloadList.put(i+num, false);
//                }*/
//                if (lrcFile.exists() && ycFile.exists() && bzFile.exists() && krcFile.exists()) {
//                    isAllDownload = dao.isAllDownload(songId, musicType);
//                    if (isAllDownload) {
//                        downloadStatue = 3;
//                    } else {
//                        if (DownloadAllService.isWaiting(songId, musicType)) {
//                            downloadStatue = 1;
//                        } else {
//                            downloadStatue = 0;
//                        }
//                    }
//                } else {
//                    if (DownloadAllService.isWaiting(songId, musicType)) {
//                        downloadStatue = 1;
//                    } else {
//                        downloadStatue = 0;
//                    }
//                }
//                isDownloadList.put(i + num, downloadStatue);
//            }
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_type_song_list);
//        dao = DownloadManager.getDownloadManager(this);
//        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
//        songList = new SongList();
//        songListall = new SongList();
//        Intent intent = this.getIntent();
//
////        Intent intent = getIntent();
//        typeName = intent.getStringExtra("type");
//        code = intent.getStringExtra("code");
//        rowWidth = this.getResources().getDisplayMetrics().widthPixels;
//        rowheight = this.getResources().getDisplayMetrics().widthPixels * 9 / 16;//宽高比例为16：9
//        initXunFei();//初始化讯飞语音：
//        initView();
//        initData();
//        initEvent();
//        curPage = 1;
//        getSearchBysinsgerData();//根据类型获取歌曲
//        mInstaller = new ApkInstaller(TypeListActivity.this);
//    }
//
//
//    //初始化讯飞语音：
//    private void initXunFei() {
//        String PREFER_NAME = "com.iflytek.setting";
//        // 初始化识别无UI识别对象
//        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
//        mIat = SpeechRecognizer.createRecognizer(TypeListActivity.this, mInitListener);
//        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
//        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
//        mIatDialog = new RecognizerDialog(TypeListActivity.this, mInitListener);
//        mSharedPreferences = getSharedPreferences(PREFER_NAME,
//                Activity.MODE_PRIVATE);
//    }
//
//    private void initView() {
//
//        im_imgCover = (ImageView) findViewById(R.id.iv_singer_icom);
//        scrollView = (ScrollChangeScrollView) findViewById(R.id.scrollView);
//        search_top = (LinearLayout) findViewById(R.id.search_top_ll);
//        ivBack = (ImageView) findViewById(R.id.search_back);
//        searchRl = (RelativeLayout) findViewById(R.id.search_top_rl);
//        rightIv = (TextView) findViewById(R.id.has_selected_song);
//        nodata_show = (TextView) findViewById(R.id.fragment_works_default);
//        /*bt_back = (ImageView) findViewById( R.id.back_last );
//        top_text = (TextView) findViewById( R.id.center_text );
//        bt_right = (ImageView) findViewById( R.id.back_next );
//        voice = (ImageView) findViewById( R.id.imv_voice );// btn_voice  ；录音
//        search = (ImageView) findViewById( R.id.btn_search );  //  btn_seach  搜索按钮
//        content = (EditText) findViewById( R.id.emoji_edit );//  emoji_edit  输入内容*/
//        singer_list = (WrapListView) findViewById(R.id.xl_singer_list);
//        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
//
//        width = this.getResources().getDisplayMetrics().widthPixels;//store.getWidth()-origin_bottom.getWidth();
//        height =  (height * 9 / 16);
//        RelativeLayout.LayoutParams paramstt = new RelativeLayout.LayoutParams(this.getResources().getDisplayMetrics().widthPixels, height);
//        paramstt.setMargins(1, 100, 1, 1);
//        im_imgCover.setLayoutParams(paramstt);
//        madapter = new TypeSongAdapter(this);
//        madapter.setListener(this);
//        singer_list.setAdapter(madapter);
//        /*if(typeName!=null&&!typeName.equals( "" )){
//            top_text.setText( typeName );
//        }else{
//            top_text.setText( "分类点歌" );
//        }
//
//        top_text.setVisibility( View.GONE );
//        bt_right.setVisibility( View.VISIBLE );
//        bt_right.setImageResource( R.mipmap.back_home );
//        switch (type) {
//            case -1:
//                break;
//            case 1:
//                content.setHint( "搜索此歌手的歌曲" );//
//                break;
//            case 2:
////                content.setHint( "搜索此类型的歌曲" );//
//                content.setHint( "歌手/歌曲/拼音缩写" );//
//                break;
//        }*/
//
//
//        //TODO  设置歌手封面宽高
//        im_imgCover.setLayoutParams(new RelativeLayout.LayoutParams(rowWidth,(rowWidth * 9 / 16)));
////        im_imgCover.setw
//
//        /*search.setOnClickListener( this );
//        voice.setOnClickListener( this );
//        bt_back.setOnClickListener( this );
//        bt_right.setOnClickListener( this );*/
//        ivBack.setOnClickListener(this);
//        searchRl.setOnClickListener(this);
//        rightIv.setOnClickListener(this);
//
//        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
//            //x下拉刷新
//            @Override
//            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        curPage = 1;
////
//                        if (isSearch) {
////                            toast( "1111111111111" );
//                            //getSearchData();//首次请求数据
//                        } else {
////                            toast( "2223333333333" );
//                            getSearchBysinsgerData();//根据歌手获取歌曲
//                        }
//
//                        refreshLayout.finishRefreshing();
//                    }
//                }, 2000);
//            }
//
//            //上拉加载
//            @Override
//            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        curPage++;
////                        getClubData();//首次请求数据
//                        if (isSearch) {
//                            if (islastsearch) {
//                                toast(getString(R.string.no_more_data));
//                                refreshLayout.finishLoadmore();
//                                return;
//                            }
////                            toast( "2222222222" );
//                            //getSearchData();//首次请求数据
//                        } else {
////                            toast( "21111111111" );
//                            if (islast) {
//                                toast(getString(R.string.no_more_data));
//                                refreshLayout.finishLoadmore();
//                                return;
//                            }
//                            getSearchBysinsgerData();//根据歌手获取歌曲
//                        }
//                        refreshLayout.finishLoadmore();
//                    }
//                }, 2000);
//            }
//        });
//        /*musicReceiver=new TypeMusicReceiver();
//        musicReceiver.setiSetProgress(this);
//        mFilter = new IntentFilter();
//        mFilter.addAction("TYPE_ACTION_DOWNLOADING");
//        mFilter.addAction("TYPE_ACTION_FINISH");
//        mFilter.addAction("TYPE_ACTION_EXCEPTION");
//        registerReceiver(musicReceiver, mFilter);*/
//        registerReceiver();
//        StatusBarUtil.setTranslucentForImageView(this, 0, findViewById(R.id.search_top));
//        StatusBarUtil.setColor(this, Color.argb(70, 0, 0, 0), 0);
//
//    }
//
//    //注册下载进度监听器
//    public void registerReceiver() {
//        musicReceiver = new MusicReceiver();
//        musicReceiver.setiSetProgress(this);
//        IntentFilter mFilter = new IntentFilter();
//        mFilter.addAction("ACTION_DOWNLOADING");
//        mFilter.addAction("ACTION_FINISH");
//        mFilter.addAction("ACTION_EXCEPTION");
//        registerReceiver(musicReceiver, mFilter);
//    }
//
//    //反注册网络监听器
//    public void unRegisterReceiver() {
//        if (musicReceiver != null) {
//            unregisterReceiver(musicReceiver);
//        }
//    }
//
//    //清空数据
//    private void clear() {
//        //TODO
//        if (madapter != null) {
//            madapter.clear();
//        }
//    }
//
//    //TODO  取得热门推荐
//    private void initData() {
//
//    }
//
//    private void initEvent() {
//
//        scrollView.setupTitleView(im_imgCover);
//        scrollView.setListener(new ScrollChangeScrollView.IOnScrollListener() {
//            @Override
//            public void onScroll(int tag, int scrollY) {
//                if (scrollY == 0) {
//                    search_top.setBackgroundColor(Color.parseColor("#44000000"));
//                    StatusBarUtil.setColor(TypeListActivity.this, Color.argb(70, 0, 0, 0), 0);
//                    return;
//                }
//                float scale = (float) scrollY / im_imgCover.getMeasuredHeight();
//                float alpha = (255 * scale);
//                if (tag == 2) {
////                    search_top.setBackgroundColor(getResources().getColor(R.color.halfBlack));
//                    search_top.setBackgroundColor(Color.argb((int) alpha, 220, 52, 42));
//                    StatusBarUtil.setColor(TypeListActivity.this, Color.argb((int) alpha, 220, 52, 42), 0);
//                } else {
//                    search_top.setBackgroundColor(getResources().getColor(R.color.app_red));
//                    StatusBarUtil.setColor(TypeListActivity.this, Color.rgb(220, 52, 42), 0);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent;
//        switch (v.getId()) {
//
//            case R.id.search_back:
//                finishActivity();
//                break;
//            case R.id.search_top_rl:
//                intent = new Intent(this, SearchActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.has_selected_song:
//                intent = new Intent(this, DownloadSongActivity.class);
//                startActivity(intent);
//                break;
//            /*case R.id.back_next://主页
//                intent = new Intent( TypeListActivity.this, HomeActivity.class );
////                intent.putExtra("showsearch", isShowSearch);
//                startActivity( intent );
//                break;
//            case R.id.back_last://
//                finishActivity();
//                break;
//
//            case R.id.imv_voice://录音
//                toast( "点击了科大讯飞录音" );
////                Speech2Text.getSearchVoice(this,content,mIat,mSharedPreferences,mIatDialog);
//                if (ContextCompat.checkSelfPermission( this,
//                        Manifest.permission.RECORD_AUDIO )
//                        != PackageManager.PERMISSION_GRANTED) {
//                    toast( "权限得不到" );
//                    ActivityCompat.requestPermissions( this,
//                            new String[]{Manifest.permission.RECORD_AUDIO},
//                            1 );
//                } else {
//                    toast( "权限得到" );
//                    getSearchVoice();
//                }
//
//                break;
//
//            case R.id.btn_search://搜索按钮
//                isSearch = true;
//                curPage = 1;
//                  songList = new SongList();
//                  songListall = new SongList();
//                getSearchData();
//                break;*/
//        }
//    }
//
//    /**
//     * //根据歌手获取歌曲：
//     */
//    private void getSearchBysinsgerData() {
//
//        SingUtil.api_typesongList(this, "" + typeName, code + "", curPage, new Callback() {
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
//                    songList = SongList.objectFromData(data, "data");
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
//                                songListall.getResults().add((SongList.ResultsBean) songList.getResults());
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
//                    responsemsg = JsonParserFirst.getRetMsg(data);
//                    handler.sendEmptyMessageDelayed(0, 100);
//                }
//            }
//        });
//
//
//    }
//
//    /**
//     * 点击搜索事件：
//     */
//    /*private void getSearchData() {
//        String searchcontent = content.getText().toString().trim();
//
//        String inputstr = ReplaceBlank.replaceBlankAll(searchcontent);
////        toast( "输入的搜索内容为：" + searchcontent );
//        if (inputstr.length() < 1) {
//            toast( "请输入你需要搜索的歌曲名称" );
//            return;
//        } else {
//            //TODO
////if(singerPage==0){//判断是否应该清空所有的列表数据：
////    songList.
////}
//            SingUtil.api_songSearchName( this, "" + singerid, "" + inputstr,"" + curPage, new Callback() {
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    closeProgressDialog();
//                    LogUtils.sysout( "联网登录出现网络异常错误！" );
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
////                    stopLoadData();
////                toast( "请求失败" );
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    closeProgressDialog();
////                    stopLoadData();
//                    String data = response.body().string();
//                    LogUtils.sysout("当前歌星搜索歌曲返回结果:"+data);
//                    int code = JsonParserFirst.getRetCode( data );
//                    if (code == 0) {
//                        *//*if (songList == null) {
//                            songList = new SongList();
//                        }
//                        songList = SingerJsonParser.parser_SongList( data );*//*
//                        JSONObject root = null;
//                        try {
//                            root = new JSONObject( data );
////                        JSONObject data1 = root.getJSONObject( "data" );
////                        songList = SongList.objectFromData( data1.toString() );
//                            songList = SongList.objectFromData(  data, "data"  );
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        if (songList != null && !songList.equals( "" )) {
//                            if(songList.isLast()){
//                                islastsearch = true;
//                            }else{
//                                islastsearch = false;
//                            }
//
//                            if (curPage < 2) {
//                                if (songList.getTotal() == 0) {//数据为空
//                                    handler.sendEmptyMessageDelayed( 1, 100 );
//                                } else {
//                                    handler.sendEmptyMessageDelayed( 2, 100 );//刷新
//                                    songListall = songList;
//                                }
//
//                                //TODO  保存数据到本地
//                            } else {
//                                try {
//                                    curPage = songList.getCurPage();
//                                    songListall.getResults().add( (SongList.ResultsBean) songList.getResults() );
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
////
//                                handler.sendEmptyMessageDelayed( 3, 100 );//加载更多
//                            }
//
//                        } else {
//                            if (curPage < 2) {//数据为空
//                                handler.sendEmptyMessageDelayed( 1, 100 );
//                            }
//                        }
//                    } else {
////                    closeProgressDialog();
////                    stopLoadData();
//                        responsemsg = JsonParserFirst.getRetMsg( response.body().toString() );
////                    toast( meg );
//                        handler.sendEmptyMessageDelayed( 1, 100 );
////                    toast( meg );
//
//                    }
//
//                }
//
//            } );
//        }
//
//    }*/
//
//    /*private void getSearchVoice() {
//        // 移动数据分析，收集开始听写事件
//        FlowerCollector.onEvent( TypeListActivity.this, "iat_recognize" );
//
//        content.setText( null );// 清空显示内容
//        mIatResults.clear();
//        // 设置参数
//        Speech2Text.setParam( mIat, mSharedPreferences );
//        boolean isShowDialog = mSharedPreferences.getBoolean(
//                getString( R.string.pref_key_iat_show ), true );
//        if (isShowDialog) {
//            // 显示听写对话框
//            mIatDialog.setListener( mRecognizerDialogListener );
//            mIatDialog.show();
//            toast( getString( R.string.text_begin ) );
//        } else {
//            // 不显示听写对话框
//            ret = mIat.startListening( mRecognizerListener );
//            if (ret != ErrorCode.SUCCESS) {
//                toast( "听写失败,错误码：" + ret );
//            } else {
//                toast( getString( R.string.text_begin ) );
//            }
//        }
//    }*/
//
//    /**
//     * 初始化监听器。
//     */
//    private InitListener mInitListener = new InitListener() {
//
//        @Override
//        public void onInit(int code) {
//            Log.d(TAG, "SpeechRecognizer init() code = " + code);
//            if (code != ErrorCode.SUCCESS) {
//                toast("初始化失败，错误码：" + code);
//            }
//        }
//    };
//    /**
//     * 听写监听器。
//     */
//    /*private RecognizerListener mRecognizerListener = new RecognizerListener() {
//
//        @Override
//        public void onBeginOfSpeech() {
//            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            toast( "开始说话" );
//        }
//
//        @Override
//        public void onError(SpeechError error) {
//            // Tips：
//            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
//            toast( error.getPlainDescription( true ) );
//        }
//
//        @Override
//        public void onEndOfSpeech() {
//            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            toast( "结束说话" );
//        }
//
//        @Override
//        public void onResult(RecognizerResult results, boolean isLast) {
//            Log.d( TAG, results.getResultString() );
//            Speech2Text.printResult( results, mIatResults, content );
//
//            if (isLast) {
//                // TODO 最后的结果
//            }
//        }
//
//        @Override
//        public void onVolumeChanged(int volume, byte[] data) {
//            toast( "当前正在说话，音量大小：" + volume );
//            Log.d( TAG, "返回音频数据：" + data.length );
//        }
//
//        @Override
//        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
//            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
//            // 若使用本地能力，会话id为null
//            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
//            //		Log.d(TAG, "session id =" + sid);
//            //	}
//        }
//    };*/
//
//    /**
//     * 听写UI监听器
//     */
//    /*private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
//        public void onResult(RecognizerResult results, boolean isLast) {
//            Speech2Text.printResult( results, mIatResults, content );
//        }
//
//        */
//
//    /**
//     * 识别回调错误.
//     *//*
//        public void onError(SpeechError error) {
//            toast( error.getPlainDescription( true ) );
//        }
//
//    };*/
//    @Override
//    public void click(int opt, final int position, List<SongList.ResultsBean> item) {
//        Intent intent;
//        Bundle bundle = new Bundle();
//        switch (opt) {
//            case 1:
//                intent = new Intent(TypeListActivity.this, MusicPlayActivity.class);
//                bundle.putSerializable("songList", (Serializable) allSongList);
////                bundle.putStringArrayList( "ids", ids );
////                bundle.putStringArrayList( "types", types );
////                bundle.putStringArrayList( "names", names );
////                bundle.putStringArrayList( "singerNames", singerNames );
//                intent.putExtras(bundle);
//                intent.putExtra("position", position);
//                startActivity(intent);
//                break;
//            case 2://练歌
//
//                try {
//                    songDetail = dao.selectSong(item.get(position).getId(), item.get(position).getType());
//                    requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0002);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                //toast( "musci_list_practice" );
////                intent = new Intent( TypeListActivity.this, HomeActivity.class );
////                intent.putExtra( "songId", allSongList.get(position).getId());
////                intent.putExtra("musicType", allSongList.get(position).getType());
////                startActivity( intent );
//                break;
//            case 3://下歌
//                switch (BaseApplication.wifiState) {
//                    case 0://
//                    case 1://每次提醒
//                        netNotice(position, 0);
//                        break;
//                    case 2://首次提醒
//                        if (BaseApplication.isNoticeOnce != 1) {
//                            netNotice(position, 1);
//                        } else {
//                            startDownload(position);
//                        }
//                        break;
//                    case 3://不提醒
//                    case 4://wifi
//                        startDownload(position);
//                        break;
//                    case 5:
//                        toast(getString(R.string.no_net));
//                        break;
//                    default:
//                        break;
//                }
//
//                break;
//            case 4://取消下载
//                BaseApplication.isStop = true;
//                if (madapter != null) {
//                    madapter.setOnError(position, allSongList.get(position).getId(), allSongList.get(position).getType());
//                }
//
//                break;
//            case 5://取消等待
//                DownloadAllService.removeWaitingDownload(allSongList.get(position).getId(), allSongList.get(position).getType());
//                if (madapter != null) {
//                    madapter.cancelWaiting(position, allSongList.get(position).getId(), allSongList.get(position).getType());
//                }
//                break;
//        }
//    }
//
//
//    @Override
//    public void permissionSuccess(int request_code_permission) {
//        super.permissionSuccess(request_code_permission);
//        switch (request_code_permission) {
//            case 0x0002://开启摄像头的权限
//                Intent intent = new Intent(TypeListActivity.this, OpenCameraActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("songDetail", songDetail);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case 0x0003://开始按钮
////                freePractice();
//                break;
//            case 0x0004://录歌按钮
////                startRecord();
//                break;
//        }
//    }
//
//    /**
//     * 开始下载：
//     *
//     * @param position
//     */
//    public void startDownload(int position) {
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
//    }
//
//    /**
//     * 流量提醒对话框
//     *
//     * @param position
//     * @param isNoticeOnce 是否已经提醒过了（首次提醒）
//     */
//    private void netNotice(final int position, final int isNoticeOnce) {
//        FrameLayout parent = (FrameLayout) dialog.getParent();
//        if (parent != null) {
//            parent.removeView(dialog);
//        }
//        new MyAlertDialog(this, dialog).builder()
//                .setMsg(getString(R.string.wifiDisconnected_notice))
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        BaseApplication.isNoticeOnce = isNoticeOnce;
//                        startDownload(position);
//                    }
//                }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        }).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 退出时释放连接
//        mIat.cancel();
//        mIat.destroy();
//        unRegisterReceiver();
//    }
//
//    @Override
//    protected void onResume() {
//        // 开放统计 移动数据统计分析
//        FlowerCollector.onResume(TypeListActivity.this);
//        FlowerCollector.onPageStart(TAG);
//        //数据同步
//        hasFileAndRecord(beanList);
//        if (madapter != null) {
//            madapter.setIsDownloadList(isDownloadList);
//        }
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        // 开放统计 移动数据统计分析
//        FlowerCollector.onPageEnd(TAG);
//        FlowerCollector.onPause(TypeListActivity.this);
//        super.onPause();
//    }
//
//
//    @Override
//    public void setDownloadProgress(int position, int percent, String songId, String musicType) {
//        if (madapter != null) {
//            madapter.setDownloadProgress(position, percent, songId, musicType);
//
//        }
//        LogUtils.w("TAG1", "Activity==position:" + position + ",percent" + percent);
//    }
//
//    @Override
//    public void setFinishImg(int position, String songId, String musicType) {
//        if (madapter != null) {
//            madapter.setFinishImg(position, songId, musicType);
//        }
//    }
//
//    @Override
//    public void setOnException(int position, String songId, String musicType) {
//        if (madapter != null) {
//            madapter.setOnError(position, songId, musicType);
//        }
//    }
//}
