package com.changxiang.vod.module.ui.search;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.utils.ReplaceBlank;
import com.changxiang.vod.common.utils.SharedPrefManager;
import com.changxiang.vod.common.utils.Speech2Text;
import com.changxiang.vod.common.utils.dialog.AlertDialog;
import com.changxiang.vod.common.view.CustomEditText;
import com.changxiang.vod.common.view.MyAlertDialog;
import com.changxiang.vod.common.view.MyViewGroup;
import com.changxiang.vod.module.base.AdapterListListener;
import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.db.LocalMusicManager;
import com.changxiang.vod.module.db.RecordsDao;
import com.changxiang.vod.module.db.VodMedia;
import com.changxiang.vod.module.engine.JsonParserFirst;
import com.changxiang.vod.module.entry.HotCearch;
import com.changxiang.vod.module.entry.HotSong;
import com.changxiang.vod.module.service.receiver.MusicReceiver;
import com.changxiang.vod.module.ui.base.BaseMusicActivity;
import com.changxiang.vod.module.ui.recently.ISetProgress;
import com.changxiang.vod.module.ui.recordmusic.OpenCameraActivity;
import com.changxiang.vod.module.ui.search.adapter.SearchRecordsAdapter;
import com.changxiang.vod.module.ui.singermusic.SingerListActivity;
import com.changxiang.vod.module.ui.singermusic.adapter.SingerSongAdapter;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//http://blog.csdn.net/u013700040/article/details/52046514 历史搜索记录
public class SearchActivity extends BaseMusicActivity implements View.OnClickListener, AdapterListListener<List<VodMedia>>, ISetProgress, TextView.OnEditorActionListener {
    /**
     * 讯飞
     */
    private static final String TAG = SearchActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private final HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private SharedPreferences mSharedPreferences;
    // 引擎类型
//    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private String[] hotSearch;

    private boolean islast = false;

    private CustomEditText content;//  emoji_edit  输入内容

    private MyViewGroup mViewGroup;// ll_list_show  热门搜索标签

    private RelativeLayout will_to_search;//rl_will_to_search 搜索之前的界面
    private ListView mListView;// xl_singer_list //搜索结果界面列表
    private boolean isSearchDetails = true;//表示显示搜索结果
    private SingerSongAdapter madapter;
    private List<VodMedia> songListall;
    private VodMedia songDetail;

    private List<HotCearch> mHotCearchlist;
    private int curPage = 1;//当前值为热门推荐分页计数
    private ImageView bt_back;
    private ImageView voice;
    private ImageView search;

    private final List<HotSong> allSongList = new ArrayList<>();//用于封装动态列表数据（歌曲）
    private final ArrayList<String> ids = new ArrayList<>();
    private final ArrayList<String> types = new ArrayList<>();
    private final ArrayList<String> names = new ArrayList<>();
    private final ArrayList<String> singerNames = new ArrayList<>();
    private final ArrayList<String> imgCoverList = new ArrayList<>();
    private String responsemsg = null;//网络请求返回信息

    private MusicReceiver musicReceiver;//广播接收器
    private String songId, musicType, songName, singerName, imgCover;//歌曲id,音乐类型（mp3,mv）,歌曲名
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
    private LocalMusicManager dao;
    private List<VodMedia> existList;//歌曲列表对象集合
    private View dialog;//对话框布局

    private String SearchContent, searchSongName, searchSingerNameId;

    private int searchType = 0;//搜索方式：0：表示泛搜索；1：表示根据歌手名搜索歌曲列表；2：根据歌曲名搜索歌曲列表

    //搜索历史记录
    private View recordsHistoryView;
    private ListView recordsListLv;
    private LinearLayout searchRecordsLl;
    private TextView clearAllRecordsTv;
    private List<String> searchRecordsList;
    private List<String> tempList;
    private RecordsDao recordsDao;
    private SearchRecordsAdapter recordsAdapter;

    private LocalMusicManager mLocalMusicManager;//数据库：

    private List<VodMedia> mList;//歌曲列表对象集合

    @Override
    public void handMsg(Message msg) {
//        Intent intent;
        switch (msg.what) {
            case -1:
//                toast( "联网登录出现网络异常错误" );
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                break;
            case 0:
                //修改成功，更新数据，刷新数据：
                if (mHotCearchlist != null && mHotCearchlist.size() > 0) {
                    hotSearch = new String[mHotCearchlist.size()];
                    for (int i = 0; i < mHotCearchlist.size(); i++) {
                        hotSearch[i] = mHotCearchlist.get(i).getContent();
                    }

//                    hotSearch = new String[]{"中国新歌声", "周杰伦", "薛之谦", "微微一笑很倾城", "Mystery Girl", "放不过自己", "Birdy", "朴树", "王菲"};
                    setHotTag();
                }

//                    handler.sendEmptyMessageDelayed( 0, 1000 );
                break;
            case 1:
//                toast( responsemsg );//返回数据为空
                noDataDialog();
//                handler.sendEmptyMessageDelayed( 1, 100 );
                break;
            case 2://刷新成功，刷新界面
                if (existList != null) {
//                    beanList = songList;
//                    hasFileAndRecord(beanList);//判断刷新的歌曲是否已下载
//                    madapter.setIsDownloadList(isDownloadList);
//                    recordsAdapter.setSearchContent(SearchContent);
                    madapter.setDataList(existList);
//                handler.sendEmptyMessageDelayed( 2, 100 );
                }
                break;
            case 3://加载更多成功，刷新界面
//                addFileAndRecord(songList.getResults());//判断刷新的歌曲是否已下载
//                madapter.setIsDownloadList(isDownloadList);
//                madapter.addDataList(songList.getResults());
////                handler.sendEmptyMessageDelayed( 3, 100 );
                break;

            case 4:  //更新

//                if (songList != null) {
//                    beanList = songList.getResults();
//                    hasFileAndRecord(beanList);//判断刷新的歌曲是否已下载
//                    madapter.setIsDownloadList(isDownloadList);
//                    madapter.setSearchContent(SearchContent);
//                    madapter.setDataList(songList.getResults());
//                }

                break;


            case 10://更新
//                handler.sendEmptyMessageDelayed( 10, 500 );
                break;


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = this.getIntent();
        String data = intent.getStringExtra("search");//是否直接搜索
        String searchContent = intent.getStringExtra("searchContent");//搜索内容
        initXunFei();//初始化讯飞语音：
        initView();
        initData();//历史搜索记录
        watchSearch();//键盘搜索事件：
        ApkInstaller mInstaller = new ApkInstaller(SearchActivity.this);
        mLocalMusicManager = LocalMusicManager.getComposeManager(getApplicationContext());
        mList = new ArrayList<>();
        gethotSearch();//获取热门搜索


        if (data != null) {
            if (data.equals("1")) {//需要输入再搜索
                isSearchDetails = false;
                showSearchDetails();
//                LogUtils.sysout( "需要输入再搜索" );
            } else {//直接搜索
                if (searchContent != null) {
                    content.setText(searchContent);

                    getSearchData();
//                    LogUtils.sysout( "直接搜索" );
                } else {
                    isSearchDetails = false;
                    showSearchDetails();
//                    LogUtils.sysout( "需要输入再搜索" );
                }
            }

        } else {
            isSearchDetails = false;
            showSearchDetails();
            LogUtils.sysout("需要输入再搜索");
        }

        handler.sendEmptyMessageDelayed(10, 500);
    }


    //初始化讯飞语音：
    private void initXunFei() {
        String PREFER_NAME = "com.iflytek.setting";
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(SearchActivity.this, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(SearchActivity.this, mInitListener);
        mSharedPreferences = getSharedPreferences(PREFER_NAME,
                Activity.MODE_PRIVATE);
    }

    private void initView() {
        registerReceiver();
        initRecordsView();//历史搜索UI
        dao = LocalMusicManager.getComposeManager(this);
        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
        mViewGroup = (MyViewGroup) findViewById(R.id.rcv_hot_srearch);// mViewGroup;// ll_list_show  热门搜索标签
        bt_back = (ImageView) findViewById(R.id.back_last);
        voice = (ImageView) findViewById(R.id.imv_voice);
        search = (ImageView) findViewById(R.id.btn_search);
        content = (CustomEditText) findViewById(R.id.emoji_edit);//  emoji_edit  输入内容
        TextView next_left = (TextView) findViewById(R.id.back_next_left);

        searchRecordsLl = (LinearLayout) findViewById(R.id.search_content_show_ll);//历史记录
        //添加搜索view
        searchRecordsLl.addView(recordsHistoryView);
        LinearLayout search_bytype = (LinearLayout) findViewById(R.id.ll_search_bytype);
        LinearLayout search_bysinger = (LinearLayout) findViewById(R.id.ll_search_bysinger);
        LinearLayout search_bytop = (LinearLayout) findViewById(R.id.ll_search_bytop);

        will_to_search = (RelativeLayout) findViewById(R.id.rl_will_to_search);//         will_to_search ;//用于显示和隐藏搜索之前的界面
        mListView = (ListView) findViewById(R.id.xl_singer_list);//mListView;// xl_singer_list //搜索结果界面列表
        //历史记录点击事件
        recordsListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将获取到的字符串传到搜索结果界面
//                toast("将获取到的字符串传到搜索结果界面");
                content.setText(searchRecordsList.get(position));
                searchRecordsLl.setVisibility(View.GONE);
            }
        });

        TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
//        search_bytype;//ll_search_bytype 分类查找
//        search_bysinger;//ll_search_bysinger 歌星查找 LinearLayout
//        search_bytop;//ll_search_bytop 榜单查找
        next_left.setText(R.string.search);
        madapter = new SingerSongAdapter(this);
        //TODO
        madapter.setListener(this);
        mListView.setAdapter(madapter);
//      content.setText( "因为爱情" );

        search_bytype.setOnClickListener(this);//ll_search_bytype 分类查找
        search_bysinger.setOnClickListener(this);//ll_search_bysinger 歌星查找 LinearLayout
        search_bytop.setOnClickListener(this);//ll_search_bytop 榜单查找
        search.setOnClickListener(this);
        voice.setOnClickListener(this);
        bt_back.setOnClickListener(this);
        content.setOnClickListener(this);
        next_left.setOnClickListener(this);
        clearAllRecordsTv.setOnClickListener(this);
        content.setOnEditorActionListener(this);


        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
            //x下拉刷新
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        curPage = 1;
                        //搜索方式：0：表示泛搜索；1：表示根据歌手名搜索歌曲列表；2：根据歌曲名搜索歌曲列表
                        switch (searchType) {
                            case 0:
                                getSearchData();//表示泛搜索
                                break;
                            case 1:
                                getSeachSingerIdData();//根据歌手名搜索歌曲列表
                                break;
                            case 2:
                                getSeachSongNameData();//根据歌曲名搜索歌曲列表
                                break;
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
                        if (islast) {
                            toast(getString(R.string.no_more_data));
                            refreshLayout.finishLoadmore();
                            return;
                        }
                        switch (searchType) {
                            case 0:
                                getSearchData();
                                break;
                            case 1:
                                getSeachSingerIdData();
                                break;
                            case 2:
                                getSeachSongNameData();
                                break;
                        }


                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });


        content.addTextChangedListener(new TextWatcher() {
            @Override
            //s:变化后的所有字符
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    if (existList != null) {
                        existList = null;
                    }
                    searchRecordsLl.setVisibility(View.GONE);
//                    String inputstr = ReplaceBlank.replaceBlankAll(s.toString());
                    String inputstr = s.toString();
                    SearchContent = inputstr;
                    if (inputstr.length() != s.length()) {//去掉句号
                        content.setText(inputstr);
                        content.setSelection(inputstr.length());
                    }
                    getSearchDataItem();
                } else {
                    isSearchDetails = false;
                    showSearchDetails();
                }
            }
        });


        /***
         * mListView item点击事件
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (beanList.get(position).getQueryType() != null) {
//                    if (Integer.valueOf(beanList.get(position).getQueryType()) != 1) {
//                        searchSongName = beanList.get(position).getName();
//                        curPage = 1;
//                        getSeachSongNameData();// 根据歌曲名搜索歌曲列表
//                        searchType = 2;
//                    } else {
//                        curPage = 1;
//                        searchSingerNameId = beanList.get(position).getId();
////                        getSeachSingerIdData();//根据歌手名搜索歌曲列表
//                        searchType = 1;
//
//
//                        //根据不同信息跳转到不同的界面
//                        Intent intent = new Intent(SearchActivity.this, SingerListActivity.class);
//                        intent.putExtra("singerid", searchSingerNameId);
////                        intent.putExtra("imgCover", singer.getImgCover());
//                        intent.putExtra("imgCover", beanList.get(position).getImgHead());
//                        intent.putExtra("singername", beanList.get(position).getName());
//                        intent.putExtra("songcount", "MP3(" + beanList.get(position).getMp3num() + "首)/MV(" + beanList.get(position).getMvnum() + "首)");
////                        intent.putExtra("imgCover", singer.getImgCover());
//                        startActivity(intent);
//                    }
//                }
            }
        });
    }

    private void initData() {
        content.setText("今天");

        recordsDao = new RecordsDao(this);
        searchRecordsList = new ArrayList<>();
        tempList = new ArrayList<>();
        tempList.addAll(recordsDao.getRecordsList());

        reversedList();
        //第一次进入判断数据库中是否有历史记录，没有则不显示
        checkRecordsSize();
    }

    @Override
    public boolean onEditorAction(TextView v,
                                  int actionId, KeyEvent event) {
//        toast("*********搜索事件***********");
//        if (actionId == EditorInfo.IME_ACTION_SEND ||
//                (event != null && event.getKeyCode() ==
//                        KeyEvent.KEYCODE_ENTER))
//        {

        LogUtils.sysout("*************3333333333****************");
        switch (v.getId()) {
            case R.id.emoji_edit://输入框
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (content.getText().toString().length() > 0) {

                        String record = content.getText().toString();

                        //判断数据库中是否存在该记录
                        LogUtils.sysout("*************将搜索记录保存至数据库中****************");
                        if (!recordsDao.isHasRecord(record)) {
                            tempList.add(record);
                        }
                        //将搜索记录保存至数据库中
                        recordsDao.addRecords(record);
                        reversedList();
                        checkRecordsSize();
                        recordsAdapter.notifyDataSetChanged();

                        //根据关键词去搜索

                    } else {
                        toast("搜索内容不能为空");
                    }
                }
                break;
        }


        LogUtils.sysout("*************搜索事件****************");
//        // 先隐藏键盘
//        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
//                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        //处理事件
//        curPage = 1;
//        getSearchData();

        return false;
//        }
//        return false;
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

    private void noDataDialog() {
        new AlertDialog(this).builder().setTitle(getString(R.string.notice))
                .setMsg(getString(R.string.song_not_exist_new))
                .setNegativeButton(getString(R.string.song_not_exist_nex), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    private void showSearchDetails() {
        if (isSearchDetails) {//显示结果
            will_to_search.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            isSearchDetails = false;
        } else {//显示搜索界面
            will_to_search.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            isSearchDetails = true;
        }
    }


    //TODO  取得热门推荐
    private void initData(int curPage) {

//        hotSearch = new String[]{"中国新歌声", "周杰伦", "薛之谦", "微微一笑很倾城", "Mystery Girl", "放不过自己", "Birdy", "朴树", "王菲"};
//        setHotTag();
    }


    private void setHotTag() {
//        mViewGroup = (MyViewGroup) findViewById( R.id.ll_list_show );// mViewGroup;// ll_list_show  评论标签
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int lenght = hotSearch.length;
        if (lenght > 0) {
            for (int i = 0; i < lenght; i++) {
                View view = View.inflate(this, R.layout.view_search_hot_tab, null);
                final TextView newText = (TextView) view.findViewById(R.id.tv_content);
//                TextView newText =(TextView) View.inflate(this, R.layout.view_flower_count_text, null);;
                newText.setText(hotSearch[i]);
                newText.setId(i);
                newText.setTag("" + i);
                newText.setOnClickListener(this);
                mViewGroup.addView(view);
                newText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        toast( newText.getText().toString().trim() );
                        content.setText(newText.getText().toString());
                        content.selectAll();
//                        toast( "直接搜索" );
                        curPage = 1;
                        searchType = 0;
                        getSearchData();
                    }
                });
            }
        }
    }

    private void getSearchVoice() {
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(SearchActivity.this, "iat_recognize");

        content.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        Speech2Text.setParam(mIat, mSharedPreferences);
        boolean isShowDialog = mSharedPreferences.getBoolean(
                getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            toast(getString(R.string.text_begin));
        } else {
            // 不显示听写对话框
            int ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                toast(getString(R.string.listen_fail) + ret);
            } else {
                toast(getString(R.string.text_begin));
            }
        }
    }

    /**
     * 初始化监听器。
     */
    private final InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                toast(getString(R.string.init_fail) + code);
            }
        }
    };
    /**
     * 听写监听器。
     */
    private final RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            toast(getString(R.string.text_begin));
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            toast(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            toast(getString(R.string.end_speek));
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            Speech2Text.printResult(results, mIatResults, content);

//            if (isLast) {
//                // TODO 最后的结果
//            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            toast("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 听写UI监听器
     */
    private final RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            Speech2Text.printResult(results, mIatResults, content);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            toast(error.getPlainDescription(true));
        }

    };


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.emoji_edit://显示历史记录
//                LogUtils.sysout("999999999999999999999999999999");
//                initData();
//                bindAdapter();//历史搜索记录
                break;
            //清空所有历史数据
            case R.id.clear_all_records_tv:
                tempList.clear();
                reversedList();
                recordsDao.deleteAllRecords();
                recordsAdapter.notifyDataSetChanged();
                searchRecordsLl.setVisibility(View.GONE);
                break;
            case R.id.back_next://主页
//                intent = new Intent(SearchActivity.this, HomeActivity.class);
////                intent.putExtra("showsearch", isShowSearch);
//                startActivity(intent);
                break;
            case R.id.back_last://
                finishActivity();
                break;
            case R.id.back_next_left://搜索
//                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
//                finishActivity();
                curPage = 1;
                getSearchData();
                searchRecordsLl.setVisibility(View.GONE);
                break;

            case R.id.imv_voice://录音
//                toast("点击了科大讯飞录音");
//                Speech2Text.getSearchVoice(this,content,mIat,mSharedPreferences,mIatDialog);
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    toast("权限得不到");
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            1);
                } else {
                    toast("权限得到");
                    getSearchVoice();
                }

                break;
            //        search_bytype;//ll_search_bytype 分类查找
//        search_bysinger;//ll_search_bysinger 歌星查找 LinearLayout
//        search_bytop;//ll_search_bytop 榜单查找
            case R.id.ll_search_bytype://ll_search_bytype 分类查找
                toast("分类查找");
//                intent = new Intent(SearchActivity.this, TypeIndexActivityold.class);
////                intent.putExtra("showsearch", isShowSearch);
//                startActivity(intent);
                break;
            case R.id.ll_search_bysinger://ll_search_bysinger 歌星查找
//                toast("歌星查找");
//                intent = new Intent(SearchActivity.this, SingerIndexActivity.class);
////                intent.putExtra("showsearch", isShowSearch);
//                startActivity(intent);
                break;
            case R.id.ll_search_bytop://ll_search_bytop 榜单查找
//                toast("榜单查找");
//                intent = new Intent(SearchActivity.this, TopIndexActivity.class);
////                intent.putExtra("showsearch", isShowSearch);
//                startActivity(intent);
                break;
            case R.id.btn_search://搜索按钮
                curPage = 1;
                getSearchData();
                break;
        }
    }

    /**
     * @方法说明:监控软键盘的的搜索按钮
     * @方法名称:watchSearch
     * @返回值:void
     */
    public void watchSearch() {
        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己要的操作...
                    curPage = 1;
                    getSearchData();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
        unRegisterReceiver();
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(SearchActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(SearchActivity.this);
        super.onPause();
    }

    @Override
    public void click(int opt, final int position, List<VodMedia> item) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (opt) {
            case 1://听歌
//                intent = new Intent(SearchActivity.this, MusicPlayActivity.class);
//                bundle.putSerializable("songList", (Serializable) allSongList);
////                bundle.putStringArrayList( "ids", ids );
////                bundle.putStringArrayList( "types", types );
////                bundle.putStringArrayList( "names", names );
////                bundle.putStringArrayList( "singerNames", singerNames );
//                intent.putExtras(bundle);
//                intent.putExtra("position", position);
//                startActivity(intent);
                break;
            case 2://练歌
//                SongList.ResultsBean resultsBean = item.get(position);
//                songDetail = new SongDetail(resultsBean.getId(), resultsBean.getName(), resultsBean.getSingerName(), resultsBean.getType(),
//                        0, null, resultsBean.getImgAlbumUrl(), null);
////                songDetail.setQzTime(resultsBean.get);
////                songDetail.setImgHead(resultsBean.get);
//                requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0002);
////                intent = new Intent( SearchActivity.this, HomeActivity.class );
////                intent.putExtra( "songId", ids.get(position));
////                intent.putExtra("musicType", types.get(position));
////                startActivity( intent );
                break;
            case 3://下歌
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
                break;
            case 4://取消下载
//                BaseApplication.isStop = true;
//                if (madapter != null) {
//                    madapter.setOnError(position, ids.get(position), types.get(position));
//                }
                break;
            case 5://取消等待
//                DownloadAllService.removeWaitingDownload(ids.get(position), types.get(position));
//                if (madapter != null) {
//                    madapter.cancelWaiting(position, ids.get(position), types.get(position));
//                }
                break;
        }
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
//                        startDownload(position);
                    }
                }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }


    private void gethotSearch() {
    }

    /**
     * 点击搜索事件：
     */

    private void getSearchData() {
        isSearchDetails = true;
        showSearchDetails();
        String searchcontent = content.getText().toString().trim();
        String inputstr = ReplaceBlank.replaceBlankAll(searchcontent);

        mList = mLocalMusicManager.fuzzyQuery(inputstr);
//        mList = mLocalMusicManager.fuzzyQuery("刘德华");
        if (existList != null && existList.size() > 0) {
            existList.clear();
        } else {
            existList = new ArrayList<>();
        }
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

    /**
     * 根据歌曲名搜索歌曲列表
     */

    private void getSeachSongNameData() {
//        SingUtil.api_getSongListByName(SearchActivity.this, searchSongName, "" + curPage, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                closeProgressDialog();
//                LogUtils.sysout("联网登录出现网络异常错误！");
//                handler.sendEmptyMessageDelayed(-1, 1000);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();
//                LogUtils.sysout("当前歌星搜索歌曲返回结果:" + data);
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
//                        }
//                    }
//                }
//
//            }
//        });
    }

    /**
     * 根据歌手名搜索歌曲列表
     */
    private void getSeachSingerIdData() {
//        SingUtil.api_songSearchbysinger(SearchActivity.this, searchSingerNameId, "" + curPage, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                closeProgressDialog();
//                LogUtils.sysout("联网登录出现网络异常错误！");
//                handler.sendEmptyMessageDelayed(-1, 1000);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();
//                LogUtils.sysout("当前歌星搜索歌曲返回结果:" + data);
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
//
//                        }
//                    }
//                }
//            }
//        });
    }


    /**
     * 自动搜索事件：
     */
    private void getSearchDataItem() {
        isSearchDetails = true;
        showSearchDetails();
        String searchcontent = content.getText().toString().trim();
        String inputstr = ReplaceBlank.replaceBlankAll(searchcontent);
//        toast( "输入的搜索内容为：" + searchcontent );
        if (inputstr.length() < 1) {
            toast(getString(R.string.input_searchContent));
        } else {
            //TODO
//if(singerPage==0){//判断是否应该清空所有的列表数据：
//    songList.
//}
            curPage = 1;
//            String singerid = "";


//            SingUtil.api_song(this, "" + inputstr, "" + curPage, new Callback() {
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    closeProgressDialog();
//                    LogUtils.sysout("联网登录出现网络异常错误！");
//                    handler.sendEmptyMessageDelayed(-1, 1000);
////                    stopLoadData();
////                toast( "请求失败" );
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    closeProgressDialog();
////                    stopLoadData();
//                    String data = response.body().string();
//                    Log.e(TAG, data);
//                    LogUtils.sysout("当前歌星搜索歌曲返回结果:" + data);
//                    int code = JsonParserFirst.getRetCode(data);
//                    if (code == 0) {
//                        try {
//                            songList = SongList.objectFromData(data, "data");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (songList != null && !songList.equals("")) {
//                            islast = songList.isLast();
//                            if (curPage < 2) {
//                                if (songList.getTotal() == 0) {//数据为空
//                                    handler.sendEmptyMessageDelayed(2, 100);
//                                } else {
//                                    handler.sendEmptyMessageDelayed(2, 100);//刷新
//                                    songListall = songList;
//                                }
//
//                                //TODO  保存数据到本地
//                            } else {
//                                try {
//                                    curPage = songList.getCurPage();
//                                    songListall.getResults().add((SongList.ResultsBean) songList.getResults());
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
////
//                                handler.sendEmptyMessageDelayed(3, 100);//加载更多
//                            }
//
//                        } else {
//                            if (curPage < 2) {//数据为空
////                                handler.sendEmptyMessageDelayed( 1, 100 );
//                            }
//                        }
//                    } else {
////                    closeProgressDialog();
////                    stopLoadData();
//                        responsemsg = JsonParserFirst.getRetMsg(response.body().toString());
////                    toast( meg );
////                        handler.sendEmptyMessageDelayed( 1, 100 );
////                    toast( meg );
//
//                    }
//                }
//
//            });
        }

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

    @Override
    public void permissionSuccess(int request_code_permission) {
        super.permissionSuccess(request_code_permission);
        switch (request_code_permission) {
            case 0x0002://开启摄像头的权限
//                Intent intent = new Intent(getActivity(), CameraRecorderActivity.class);
                Intent intent = new Intent(this, OpenCameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("songDetail", songDetail);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }


    //初始化搜索历史记录View
    private void initRecordsView() {
        recordsHistoryView = LayoutInflater.from(this).inflate(R.layout.search_records_list_layout, null);
        //显示历史记录lv
        recordsListLv = (ListView) recordsHistoryView.findViewById(R.id.search_records_lv);
        //清除搜索历史记录
        clearAllRecordsTv = (TextView) recordsHistoryView.findViewById(R.id.clear_all_records_tv);
    }

    //颠倒list顺序，用户输入的信息会从上依次往下显示
    private void reversedList() {
        searchRecordsList.clear();
        for (int i = tempList.size() - 1; i >= 0; i--) {
            searchRecordsList.add(tempList.get(i));
        }
    }

    //当没有匹配的搜索数据的时候不显示历史记录栏
    private void checkRecordsSize() {
        searchRecordsLl.setVisibility(View.GONE);
//        if (searchRecordsList.size() == 0) {
//            searchRecordsLl.setVisibility(View.GONE);
//        } else {
//            searchRecordsLl.setVisibility(View.VISIBLE);
//        }
    }

    private void bindAdapter() {
        recordsAdapter = new SearchRecordsAdapter(this, searchRecordsList);
        recordsListLv.setAdapter(recordsAdapter);
    }
}
