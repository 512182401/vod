//package com.changxiang.vod.module.ui.topmusic;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.util.SparseIntArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.changxiang.vod.common.utils.MyFileUtil;
//import com.changxiang.vod.module.service.receiver.MusicReceiver;
//import com.changxiang.vod.module.ui.recordmusic.OpenCameraActivity;
//import com.jaeger.library.StatusBarUtil;
//import com.changxiang.vod.R;
//import com.changxiang.vod.common.utils.ImageLoader;
//import com.changxiang.vod.common.utils.LogUtils;
//import com.changxiang.vod.common.view.MyAlertDialog;
//import com.changxiang.vod.common.view.ScrollChangeScrollView;
//import com.changxiang.vod.module.base.AdapterListListener;
//import com.changxiang.vod.module.base.BaseApplication;
//import com.changxiang.vod.module.db.DownloadManager;
//import com.changxiang.vod.module.db.ParameterBean;
//import com.changxiang.vod.module.engine.JsonParserFirst;
//import com.changxiang.vod.module.entry.HotSong;
//import com.changxiang.vod.module.ui.base.BaseActivity;
//import com.changxiang.vod.module.ui.search.SearchActivity;
//import com.changxiang.vod.module.ui.topmusic.adapter.PlayListAdapter;
//import com.changxiang.vod.module.ui.topmusic.net.TopMusicNet;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
///**
// * Created by 15976 on 2016/10/13.
// */
//
//public class TopListActivity extends BaseActivity implements View.OnClickListener, ISetProgress, AdapterListListener<String> {
//
//    private ImageView ivBg;//背景，返回键，返回主页
//    private TextView tvPlay;//播放全部按钮，更新日期
//    private ListView listView;//歌曲列表
//    private String imgbd;//封面背景
//    private ScrollChangeScrollView scrollView;
//    private LinearLayout search_top;
//    private ImageView ivBack;//返回键
//    private RelativeLayout searchRl;//搜索点击框
//    private TextView rightIv;//已点歌单按钮
//    private ArrayList<SongBean> songBeanList;
//    private final ArrayList<String> ids = new ArrayList<>();
//    private final ArrayList<String> types = new ArrayList<>();
//    private final ArrayList<String> names = new ArrayList<>();
//    private final ArrayList<String> singerNames = new ArrayList<>();
//    private final ArrayList<String> imgCoverList = new ArrayList<>();
//    private final List<HotSong> allSongList = new ArrayList<>();//用于封装动态列表数据（歌曲）
//    private MusicReceiver musicReceiver;//广播接收器
//    private PlayListAdapter adapter;//
//    private final SparseIntArray isDownloadList = new SparseIntArray();//歌曲文件是否下载完成的集合
//    private int downloadStatue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
//    private final String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//
//    private final String mp4Dir = MyFileUtil.DIR_VEDIO.toString() + File.separator;//
//    private final String accDir = MyFileUtil.DIR_ACCOMPANY.toString() + File.separator;//
//    private final String lrcDir = MyFileUtil.DIR_LRC.toString() + File.separator;//
//    private final String krcDir = MyFileUtil.DIR_KRC.toString() + File.separator;//
//    private File ycFile;//已下载歌曲的文件
//    private File bzFile;//
//    private File lrcFile;//
//    private File krcFile;//
//    private DownloadManager dao;
//    private View dialog;//对话框布局
//    private SongDetail songDetail;//播放歌曲对象
//
//    @Override
//    public void handMsg(Message msg) {
//        switch (msg.what) {
//            case 1:
////                ImageLoader.getImageViewLoad(ivBg,imgbd,R.drawable.tv_mv);
//                if (songBeanList != null && songBeanList.size() > 0) {
//                    SongBean songBean = songBeanList.get(0);
//                    imgbd = songBean.getimgAlbumUrl();
//                }
//                ImageLoader.getImageViewLoad(ivBg, imgbd, R.drawable.tv_mv);
//                ivBg.setScaleType(ImageView.ScaleType.FIT_XY);
//                adapter = new PlayListAdapter(getApplicationContext(), songBeanList);
//                listView.setAdapter(adapter);
//                hasFileAndRecord(songBeanList);
//                adapter.setIsDownloadList(isDownloadList);
//                adapter.setListener(TopListActivity.this);
//                ListAdapter listAdapter = listView.getAdapter();
//                if (listAdapter == null) {
//                    return;
//                }
//                int totalHeight = 0;
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    View item = listAdapter.getView(i, null, listView);
//                    item.measure(0, 0);
//                    totalHeight += item.getMeasuredHeight();
//                }
//                int paddingSum = listView.getDividerHeight() * (listAdapter.getCount() - 1);
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listView.getLayoutParams();
//                params.height = totalHeight + paddingSum;
//                listView.setLayoutParams(params);
//                adapter.notifyDataSetChanged();
//                ivBg.setFocusable(true);
//                ivBg.setFocusableInTouchMode(true);
//                ivBg.requestFocus();
//                break;
//            case -1:
//                toast(getString(R.string.get_no_data));
//                break;
//        }
//
//    }
//
//    //判断歌曲的当前状态
//    private void hasFileAndRecord(ArrayList<SongBean> list) {
//        if (list != null && list.size() > 0) {
//
//            allSongList.clear();
//            ids.clear();
//            types.clear();
//            names.clear();
//            singerNames.clear();
//            imgCoverList.clear();
////            HotSong mHotSong = new HotSong();
//            for (int i = 0; i < list.size(); i++) {
//                HotSong  mHotSong = new HotSong();
//                mHotSong.setId(list.get(i).getid());
//                mHotSong.setType(list.get(i).getType());
//                mHotSong.setName(list.get(i).getname());
//                mHotSong.setSingerName(list.get(i).getsingerName());
//                mHotSong.setImgAlbumUrl(list.get(i).getimgAlbumUrl());
//                mHotSong.setNum(list.get(i).getnum());
//                mHotSong.setSize(list.get(i).getSize());
//                allSongList.add(mHotSong);
//
//                String songId = list.get(i).getid();
//                String musicType = list.get(i).getType();
//                String songName = list.get(i).getname();
//                String singerName = list.get(i).getsingerName();
//                String imgCover = list.get(i).getsingerName();
//                ids.add(songId);
//                types.add(musicType);
//                names.add(songName);
//                singerNames.add(singerName);
//                imgCoverList.add(imgCover);
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
//                    boolean isAllDownload = dao.isAllDownload(songId, musicType);
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
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_top_list);
//        initView();
//        initData();
//        initEvent();
//    }
//
//    private void initView() {
//        dao = DownloadManager.getDownloadManager(this);
//        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
//        int rowWidth = this.getResources().getDisplayMetrics().widthPixels;
//        scrollView = (ScrollChangeScrollView) findViewById(R.id.scrollView);
//        search_top = (LinearLayout) findViewById(R.id.search_top_ll);
//        ivBack = (ImageView) findViewById(R.id.search_back);
//        searchRl = (RelativeLayout) findViewById(R.id.search_top_rl);
//        rightIv = (TextView) findViewById(R.id.has_selected_song);
//        ivBg = (ImageView) findViewById(R.id.top_list_ivbg);
//        tvPlay = (TextView) findViewById(R.id.hot_list_tvPlay);
//        listView = (ListView) findViewById(R.id.hot_list_lv);
//        ivBg.setLayoutParams(new RelativeLayout.LayoutParams(rowWidth, (rowWidth * 9 / 16)));
//        registerReceiver();
//        StatusBarUtil.setTranslucentForImageView(this, 0, findViewById(R.id.search_top));
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            StatusBarUtil.setColor(this, Color.argb(70, 0, 0, 0), 0);
//        }
//    }
//
//    //注册下载进度监听器
//    private void registerReceiver() {
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
//    private void unRegisterReceiver() {
//        if (musicReceiver != null) {
//            unregisterReceiver(musicReceiver);
//        }
//    }
//
//    private void initData() {
//        Intent intent = getIntent();
//        String typeId = intent.getStringExtra("typeId");
//        //请求数据
//        TopMusicNet.api_TopList(typeId, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                handler.sendEmptyMessage(-1);
//                LogUtils.log("TAG", "榜单歌曲列表页面，数据请求失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                LogUtils.w("TAG", "榜单歌曲列表页面数据：" + result);
//                int code = JsonParserFirst.getRetCode(result);
//                if (code == 0) {
//                    try {
//                        JSONObject root = new JSONObject(result);
//                        JSONObject data = root.getJSONObject("data");
//                        JSONObject entity = data.getJSONObject("entity");
//                        imgbd = entity.getString("imgbd");
//                        //final String createDate=entity.getString("createDate");
//                        final JSONArray list = data.getJSONArray("list");
//                        songBeanList = (ArrayList<SongBean>) SongBean.arraySongBeanFromData(list.toString());
//                        handler.sendEmptyMessage(1);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    handler.sendEmptyMessage(-1);
//                }
//            }
//        });
//    }
//
//    private void initEvent() {
//        ivBack.setOnClickListener(this);
//        searchRl.setOnClickListener(this);
//        rightIv.setOnClickListener(this);
//        tvPlay.setOnClickListener(this);
//        scrollView.setupTitleView(ivBg);
//        scrollView.setListener(new ScrollChangeScrollView.IOnScrollListener() {
//            @Override
//            public void onScroll(int tag, int scrollY) {
//                if (scrollY == 0) {
//                    search_top.setBackgroundColor(Color.parseColor("#44000000"));
//                    StatusBarUtil.setColor(TopListActivity.this, Color.argb(70, 0, 0, 0), 0);
//                    return;
//                }
//                float scale = (float) scrollY / ivBg.getMeasuredHeight();
//                float alpha = (255 * scale);
//                if (tag == 2) {
////                    search_top.setBackgroundColor(getResources().getColor(R.color.halfBlack));
//                    search_top.setBackgroundColor(Color.argb((int) alpha, 220, 52, 42));
//                    StatusBarUtil.setColor(TopListActivity.this, Color.argb((int) alpha, 220, 52, 42), 0);
//
//                } else {
//                    search_top.setBackgroundColor(getResources().getColor(R.color.app_red));
//                    StatusBarUtil.setColor(TopListActivity.this, Color.rgb(220, 52, 42), 0);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        int id = v.getId();
//        switch (id) {
//            case R.id.search_back:
//                finish();
//                break;
//            case R.id.search_top_rl:
//                Intent intent = new Intent(this, SearchActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.has_selected_song:
//                intent = new Intent(this, DownloadSongActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.hot_list_tvPlay:
//                //跳转至榜单第1首歌曲播放页面，并且设置播放模式为顺序播放全部；
//                Intent playIntent = new Intent(this, MusicPlayActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("songList", (Serializable) allSongList);
////                bundle.putStringArrayList("ids",ids);
////                bundle.putStringArrayList("types",types);
////                bundle.putStringArrayList("names",names);
////                bundle.putStringArrayList("singerNames",singerNames);
//                playIntent.putExtras(bundle);
//                playIntent.putExtra("position", 0);
//                startActivity(playIntent);
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * @param position 歌曲列表中的位置
//     * @param percent  下载的百分比
//     */
//    @Override
//    public void setDownloadProgress(int position, int percent, String songId, String musicType) {
//        if (adapter != null) {
//            adapter.setDownloadProgress(position, percent, songId, musicType);
//
//        }
//        LogUtils.w("TAG1", "Activity==position:" + position + ",percent" + percent);
//
//    }
//
//    /**
//     * @param position 歌曲列表中的位置
//     */
//    @Override
//    public void setFinishImg(int position, String songId, String musicType) {
//
//        if (adapter != null) {
//            adapter.setFinishImg(position, songId, musicType);
//        }
//
//    }
//
//    //异常处理
//    @Override
//    public void setOnException(int position, String songId, String musicType) {
//        if (adapter != null) {
//            adapter.setOnError(position, songId, musicType);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //数据同步
//        hasFileAndRecord(songBeanList);
//        if (adapter != null) {
//            adapter.setIsDownloadList(isDownloadList);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        unRegisterReceiver();
//
//        super.onDestroy();
//    }
//
//    @Override
//    public void click(int opt, final int position, String item) {
//        switch (opt) {
//            case 1://听歌
//                Intent intent = new Intent(this, MusicPlayActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("songList", (Serializable) allSongList);
////                bundle.putStringArrayList("ids", ids);
////                bundle.putStringArrayList("types", types);
////                bundle.putStringArrayList("names", names);
////                bundle.putStringArrayList("singerNames", singerNames);
//                intent.putExtras(bundle);
//                intent.putExtra("position", position);
//                startActivity(intent);
//                break;
//            case 2://练歌
//                try {
//                    songDetail = dao.selectSong(ids.get(position), types.get(position));
//                    requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0002);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
////                intent = new Intent(this, HomeActivity.class);
////                intent.putExtra("songId", ids.get(position));
////                intent.putExtra("musicType", types.get(position));
////                startActivity(intent);
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
//                break;
//            case 4://取消下载
//                BaseApplication.isStop = true;
//                if (adapter != null) {
//                    adapter.setOnError(position, ids.get(position), types.get(position));
//                }
//                break;
//            case 5://取消等待
//                DownloadAllService.removeWaitingDownload(ids.get(position), types.get(position));
//                if (adapter != null) {
//                    adapter.cancelWaiting(position, ids.get(position), types.get(position));
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void permissionSuccess(int request_code_permission) {
//        super.permissionSuccess(request_code_permission);
//        switch (request_code_permission) {
//            case 0x0002://开启摄像头的权限
//                Intent intent = new Intent(TopListActivity.this, OpenCameraActivity.class);
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
//    private void startDownload(int position) {
//        toast("《" + names.get(position) + "》"+getString(R.string.download_msg));
//        adapter.setWaitDownload(position, ids.get(position), types.get(position));
//        Intent intent = new Intent(this, DownloadAllService.class);
//        ParameterBean parameterBean = new ParameterBean(ids.get(position), types.get(position), names.get(position), singerNames.get(position), position);
//        parameterBean.setImgCover(imgCoverList.get(position));
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
//}
