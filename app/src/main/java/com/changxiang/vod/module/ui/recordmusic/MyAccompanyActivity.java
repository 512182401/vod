//package com.changxiang.vod.module.ui.recordmusic;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.os.Message;
//import android.util.SparseIntArray;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
//import com.quchangkeji.tosing.aidl.ServicePlayer;
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.FileUtil;
//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.common.utils.MyFileUtil;
//import com.quchangkeji.tosingpk.common.view.MyAlertDialog;
//import com.quchangkeji.tosingpk.module.base.AppManager;
//import com.quchangkeji.tosingpk.module.db.DownloadManager;
//import com.quchangkeji.tosingpk.module.entry.CurrentPeriodBean;
//import com.quchangkeji.tosingpk.module.entry.SongBean;
//import com.quchangkeji.tosingpk.module.entry.SongDetail;
//import com.quchangkeji.tosingpk.module.ui.base.BaseActivity;
//import com.quchangkeji.tosingpk.module.ui.listening.PlayerManager;
//import com.quchangkeji.tosingpk.module.ui.recently.db.HistoryDBManager;
//import com.quchangkeji.tosingpk.module.ui.recently.db.PlayedHistory;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.adapter.DownloadSongAdapter;
//
//import java.io.File;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Created by 15976 on 2017/10/20.
// * 我的伴奏
// */
//
//public class MyAccompanyActivity extends BaseActivity implements View.OnClickListener, DownloadSongAdapter.onItemToSingListener{
//
//    private ImageView backLast;
//    private TextView centerText;
//    private ImageView deleteImage;
//    private TwinklingRefreshLayout refreshLayout;
//    private ListView listView;
//    private boolean isLast;//是否是最后一页
//    private List<SongDetail> songList;//接口返回的歌曲对象
//    private DownloadSongAdapter mAdapter;
//    private final ArrayList<String> ids = new ArrayList<>();
//    private final ArrayList<String> types = new ArrayList<>();
//    private final ArrayList<String> names = new ArrayList<>();
//    private final ArrayList<String> singerNames = new ArrayList<>();
//    private final ArrayList<String> imgCoverList = new ArrayList<>();
//    private final String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//mp3文件地址
//    private final String mp4Dir = MyFileUtil.DIR_VEDIO.toString() + File.separator;//mp4文件地址
//    private final String accDir = MyFileUtil.DIR_ACCOMPANY.toString() + File.separator;//伴奏文件地址
//    private final String lrcDir = MyFileUtil.DIR_LRC.toString() + File.separator;//lrc文件地址
//    private final String krcDir = MyFileUtil.DIR_KRC.toString() + File.separator;//krc文件地址
//    private DownloadManager dao;//数据库管理工具
//    private int downloadStatue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
//    private final SparseIntArray isDownloadList = new SparseIntArray();//伴奏文件是否下载完成的Map,SparseIntArray key和value都是int，性能比map优化
//    private SongDetail songDetail;//播放歌曲对象
//    private View dialog;//对话框布局
//    private final List<CurrentPeriodBean> allSongList = new ArrayList<>();//接口返回的全部歌曲对象
//    private int page = 1;//第几页数据
//    private List<Boolean> arrCheck = new ArrayList<>();//删除checkbox的状态存放
//    private List<Boolean> arrDownload = new ArrayList<>();//下载状态存放
//    private PopupWindow popWnd;
//    private ServicePlayer servicePlayer;
//    private final ServiceConnection sc = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
////            MyService.MyBinder binder = (MyService.MyBinder) service;
////            myService = binder.getService();
////            Toast.makeText(MyAccompanyActivity.this, "成功绑定服务", Toast.LENGTH_SHORT).show();
//            servicePlayer = ServicePlayer.Stub.asInterface(service);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
////            myService = null;
//        }
//    };
//    public int position;//在歌曲id集合中的位置
//    public String lrcUrl;//歌词地址
//    private String krcUrl;//krc歌词地址
//    public String songName;//歌曲名
//    public String singerName;//歌手名
//    public String musicType = "video";//0表示音频，1表示视频
//    public String songId;//歌曲id
//    public String imgHead;//歌手头像
//    private String size;
//    public String imgAlbumUrl;//封面
//    private int num;//点播量
//    private SongBean songBean;//歌曲对象（内容更全，包括播放地址等）
//    private String path;//歌曲路径
//    private Intent intent;
//    private ProgressDialog processDia;//数据加载对话框
//    public static int isNoticeOnce;//0 还没有提醒过，1 已经提醒过了
//    private boolean isRecord;
//    private boolean isDownload;//当前播放的歌曲是否已经有记录，是否已下载
//    private Intent intent1;
//    //请求参数：
//    int curPage = 1;
//    private TextView defaultView;
//    private List<SongDetail> songDetails;
//    private CurrentPeriodBean currentPeriodBean;
//    private int selectPosition;
//
//    @Override
//    public void handMsg(Message msg) {
//        switch (msg.what) {
//            case 11://初始化数据
//                if (listView != null && songList != null && songList.size() > 0) {
////                    mAdapter = new ChooseSongAdapter(songList, this, false);
//                    listView.setAdapter(mAdapter);
//                    getArrDownload();
//                    allSongList.clear();
////                    allSongList.addAll(songList);
////                    mAdapter.setIsDownloadList(isDownloadList);
////                    setPracticeSongListener();
//                    for (int i = 0; i < allSongList.size(); i++) {
//                        arrCheck.add(i, false);
//                    }
//                } else {
//                    //空视图
//                    refreshLayout.setVisibility(View.GONE);
//                    defaultView.setVisibility(View.VISIBLE);
//                }
//                break;
//            case 12://下拉刷新数据
//                if (listView != null && songList != null && songList.size() > 0) {
//                    int listSize = mAdapter.getListSize();
////                    mAdapter.addDataList(songList);
//                    getArrDownload();
////                    mAdapter.setIsDownloadList(isDownloadList);
////                    allSongList.addAll(songList);
//                }
//                break;
//            case 222://歌曲没有下载
//                if (path != null && path.length() > 6) {
//                    firstPlay(path);//发送播放地址
//                } else {
//                    closeLoadingDialog();
//                    toast(getString(R.string.get_no_data));
//                }
//                break;
//            case 111://歌曲已下载，延时发送广播，不然service接收不到广播
//                firstPlay(path);
//                break;
//            case 123:
//                Intent intent = new Intent();
//                intent.putExtra("songList", (Serializable) allSongList);
//                intent.putExtra("position", position);
//                intent.setAction("FIRST_PLAY");
//                sendBroadcast(intent);
//                break;
//            case -555://12s后，歌曲还没播放出来，关闭对话框，提示歌曲播放失败
//                playState = 0;
////                toast(getString(R.string.play_fail));
//                closeLoadingDialog();
//                break;
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_accompany_activity);
//        bindServiceConnection();
//        initViews();
//        initDatas();
//        initEvents();
//    }
//
//    private void initViews() {
//        backLast = (ImageView) findViewById(R.id.back_last);
//        deleteImage = (ImageView) findViewById(R.id.back_next_imageview);//删除按钮
//        centerText = (TextView) findViewById(R.id.center_text);
//
//        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);//下拉刷新
//        listView = (ListView) findViewById(R.id.fragment_works_lv);
//        defaultView = (TextView) findViewById(R.id.fragment_works_default);
//    }
//
//    private void initDatas() {
//        centerText.setText(getResources().getString(R.string.myaccompany_title));
//        deleteImage.setVisibility(View.VISIBLE);
//        deleteImage.setImageResource(R.drawable.delete);
//        dao = DownloadManager.getDownloadManager(this);
//
//        //查询数据库  (后期优化方向)分页查询，子线程
////        AsyncTask asyncTask = new AsyncTask() {
////            @Override
////            protected Object doInBackground(Object[] params) {
////                return null;
////            }
////
////            @Override
////            protected void onPreExecute() {
////                super.onPreExecute();
////            }
////
////            @Override
////            protected void onPostExecute(Object o) {
////                super.onPostExecute(o);
////            }
////        };
//        songDetails = dao.queryAllDownload();
//        for (int i = 0; i < songDetails.size(); i++) {
//            SongDetail songDetail = songDetails.get(i);
//            CurrentPeriodBean currentPeriodBean = new CurrentPeriodBean();
//            currentPeriodBean.setActivityId(songDetail.getActivityId());
//            currentPeriodBean.setId(songDetail.getSongId());
//            currentPeriodBean.setType(songDetail.getType());
////            currentPeriodBean.setSingerId(songDetail.getSongId());
//            allSongList.add(currentPeriodBean);
//        }
//
////        allSongList.addAll(songDetails);
//        LogUtils.sysout("songDetails.size()=====================" + songDetails.size());
//        if (songDetails != null && songDetails.size() > 0) {
//            mAdapter = new DownloadSongAdapter(songDetails, this, false);
//            mAdapter.setOnToSingListener(this);
//            for (int i = 0; i < songDetails.size(); i++) {
//                arrCheck.add(i, false);
//            }
//            listView.setAdapter(mAdapter);
//        } else {
//            //空视图
//            refreshLayout.setVisibility(View.GONE);
//            defaultView.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    //启动service
//    private void bindServiceConnection() {
//        intent1 = new Intent(this, MyService.class);
//        startService(intent1);
//        bindService(intent1, sc, BIND_AUTO_CREATE);
//    }
//
//    private void initEvents() {
//        backLast.setOnClickListener(this);
//        deleteImage.setOnClickListener(this);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                toast("点击条目" + position);
//                if (mAdapter.isDeleteState()) {
//                    arrCheck.set(position, !arrCheck.get(position));
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
//            //下拉刷新
//            @Override
//            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
//                super.onRefresh(refreshLayout);
//                //查询数据库
//            }
//
//            //上拉加载更多
//            @Override
//            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back_last:
//                if (mAdapter != null && mAdapter.isDeleteState()) {
//                    mAdapter.deleteState = false;
//                    mAdapter.notifyDataSetChanged();
//                    popWnd.dismiss();
//                } else {
//                    finishActivity();
//                }
//                break;
//            case R.id.back_next_imageview:
//                //显示删除checkbox
//                if ((mAdapter != null && mAdapter.deleteState) || (mAdapter != null && mAdapter.getBeanList() != null && mAdapter.getBeanList().size() == 0)){
//                    return;
//                }
//                //需要暂停
//                stopMusic();
//                if (mAdapter != null) {
//                    mAdapter.changeArrMediaPlay();
//                    mAdapter.deleteState = true;
//                    for (int i = 0; i < mAdapter.getBeanList().size(); i++) {
////                        arrCheck.add(i, false);
//                        SongDetail songDetail = mAdapter.getBeanList().get(i);
//                        songDetail.setIsSelect("0");
//                    }
////                    mAdapter.setArrCheck(arrCheck);
//                    mAdapter.notifyDataSetChanged();
//                    //显示底部popup
//                    showDeleteBottom();
//                }
//                break;
//        }
//    }
//
//    //点击删除按钮时，弹出底部的popu
//    private void showDeleteBottom() {
//        View contentView = LayoutInflater.from(MyAccompanyActivity.this).inflate(R.layout.popuplayout, null);
//        popWnd = new PopupWindow(this);
//        popWnd.setContentView(contentView);
//        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        final CheckBox selectAll = (CheckBox) contentView.findViewById(R.id.select_all);
//        ImageView selectDelete = (ImageView) contentView.findViewById(R.id.select_delete);
//        //点击check事件，全选或全不选
//        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    for (int i = 0; i < mAdapter.beanList.size(); i++) {
////                        arrCheck.set(i, false);
//                       mAdapter.beanList.get(i).setIsSelect("0");
//                    }
//                } else {
//                    for (int i = 0; i < mAdapter.beanList.size(); i++) {
////                        arrCheck.set(i, true);
//                        mAdapter.beanList.get(i).setIsSelect("1");
//                    }
//                }
////                mAdapter.setArrCheck(arrCheck);
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//        selectDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                toast("删除");
//                //获取arrCheck和allSongList,处理适配器数据源
//                List<SongDetail> beanList = mAdapter.getBeanList();
//                Iterator<SongDetail> iterator = beanList.iterator();
//                while (iterator.hasNext()){
//                    SongDetail songDetail = iterator.next();
//                    if ("1".equals(songDetail.getIsSelect())) {
//                        //删除sd卡数据  原唱，伴奏，lrc.krc
//                        try {
//                            String accPath = songDetail.getAccPath();
//                            if (accPath != null && !"".equals(accPath)){
//                                FileUtil.delectFile(new File(accPath));
//                            }
//
//                            String file = songDetail.getOriPath();
//                            if (file != null && !"".equals(file)){
//                                FileUtil.delectFile(new File(file));
//                            }
//
//                            String krcPath = songDetail.getKrcPath();
//                            if (file != null && !"".equals(file)){
//                                FileUtil.delectFile(new File(krcPath));
//                            }
//
//                            String lrcPath = songDetail.getLrcPath();
//                            if (file != null && !"".equals(file)){
//                                FileUtil.delectFile(new File(lrcPath));
//                            }
//
//                            dao.deleteLocalSong(songDetail.getSongId(), songDetail.getType());//先处理数据库
//                            iterator.remove();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                //更改界面和数据库
//                if (songDetails.size() == 0) {
//                    refreshLayout.setVisibility(View.GONE);
//                    defaultView.setVisibility(View.VISIBLE);
//                } else {
//                    mAdapter.deleteState = false;
//                    mAdapter.notifyDataSetChanged();
//                }
//                popWnd.dismiss();
//            }
//        });
//        //显示pop
//        View rootview = LayoutInflater.from(this).inflate(R.layout.my_accompany_activity, null);
//        popWnd.setBackgroundDrawable(new BitmapDrawable());
//        popWnd.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//    }
//
//    //播放MP3
//    public void playMp3() {
//        if (PlayerManager.getPlayer().isPlaying()) {//停止正在播放的歌曲
//            PlayerManager.getPlayer().stop();
//        }
//        handler.sendEmptyMessageDelayed(-555, 1000 * 12);
//        handler.sendEmptyMessageDelayed(123, 100);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mAdapter != null) {
//            mAdapter.changeArrMediaPlay();
//            mAdapter.notifyDataSetChanged();
//        }
//        //遍历数据库,找到已经下载的歌曲
//        getArrDownload();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopMusic();
//    }
//
//    private void stopMusic() {
//        Intent intent = new Intent();
//        intent.setAction("ACTION_STOP");
//        sendBroadcast(intent);
//    }
//
//    private void getArrDownload() {
//        if (mAdapter != null) {
//            arrDownload.clear();
//            for (int i = 0; i < mAdapter.beanList.size(); i++) {
////                HotSong hotSong = mAdapter.beanList.get(i);
////                arrDownload.add(i,dao.isAllDownload(hotSong.getId(),hotSong.getType()));
//            }
//            mAdapter.setArrDownload(arrDownload);
//            mAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (mAdapter != null && mAdapter.isDeleteState() && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            mAdapter.deleteState = false;
//            mAdapter.notifyDataSetChanged();
//            popWnd.dismiss();
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            AppManager.getInstance().finishActivity(this);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    //发送，播放新歌的广播
//    private void firstPlay(String ycUrl) {
//        Intent intent = new Intent();
//        LogUtils.sysout("85955555555 ycUrl=" + ycUrl);
//        intent.putExtra("ycUrl", ycUrl);
//        intent.putExtra("songList", (Serializable) allSongList);
//        intent.putExtra("position", position);
//        intent.putExtra("lrcUrl", lrcUrl);
//        intent.putExtra("krcUrl", krcUrl);
//        intent.putExtra("songName", songName);
//        intent.putExtra("singerName", singerName);
//        if ("audio".equals(musicType)) {
//            intent.setAction("FIRST_PLAY");
//        } else if ("video".equals(musicType)) {
//            intent.setAction("FIRST_PLAY_VIDEO");
//        }
//        sendBroadcast(intent);
//        //添加最近播放记录
//        if ("video".equals(musicType)) {
//            long time = System.currentTimeMillis();
//            PlayedHistory history = new PlayedHistory(songId, songName, singerName, musicType, time, imgHead, num, null, imgAlbumUrl, size);
//            HistoryDBManager.getHistoryManager().insert(history);
//        }
//    }
//
//    public int playState;//播放状态，0表示未播放，1表示正在播放，2 表示暂停, 3 表示准备中
//
//    /**
//     * 发送，播放，暂停广播
//     */
//    @Override
//    public void playMusic(MessageBean msg) {
//        super.playMusic(msg);
//        if (msg.getPlayState() == 1) {
//            LogUtils.w("playstate", "=44444444===");
//            msg.setPlayState(2);
//            musicPlay(false);
//        } else if (msg.getPlayState() == 2) {
//            LogUtils.w("playstate", "=5555555555===");
//            msg.setPlayState(1);
//            musicPlay(true);
//        } else if (msg.getPlayState() == 0) {
//            LogUtils.w("playstate", "=66666666===");
//            msg.setPlayState(1);
//            position = msg.getPosition();
//            playMp3();
//        }
//    }
//
//    /**
//     * @param isplay true 播放，false 暂停
//     */
//    private void musicPlay(boolean isplay) {
//        Intent intent = new Intent();
//        intent.putExtra("isplay", isplay);
//        intent.setAction("ACTION_ISPLAY");
//        sendBroadcast(intent);
//    }
//
//    //后台发送过来的网络提醒
//    private void playNetDialog() {
//        handler.removeMessages(-555);
//        FrameLayout parent = (FrameLayout) dialog.getParent();
//        if (parent != null) {
//            parent.removeView(dialog);
//        }
//        new MyAlertDialog(this, dialog).builder()
//                .setMsg(getString(R.string.wifiDisconnected_notice2))
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        handler.sendEmptyMessageDelayed(-555, 1000 * 12);
//                        intent = new Intent();
//                        intent.setAction("ACTION_ENSURE");
//                        sendBroadcast(intent);
//                    }
//                }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeLoadingDialog();
//                finishActivity();
//            }
//        }).show();
//    }
//
//    /**
//     * 关闭加载对话框
//     */
//    public void closeLoadingDialog() {
//        handler.removeMessages(-555);
//        if (processDia != null && this != null && !this.isFinishing()) {
//            if (processDia.isShowing()) {
//                processDia.cancel();
//            }
//            processDia = null;
//        }
//    }
//
//    //文件是否存在
//    private boolean isHasFile(String musicType) {
//        File file;
//        if ("audio".equals(musicType)) {
//            file = new File(mp3Dir + singerName + "-" + songName + ".mp3");
//            isDownload = file.exists();
//        } else if ("video".equals(musicType)) {
//            file = new File(mp4Dir + singerName + "-" + songName + ".mp4");
//            isDownload = file.exists();
//        }
//        return isDownload;
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbindService(sc);
////        stopService(intent1);
//    }
//
//    @Override
//    public void onItemToSing(CurrentPeriodBean currentPeriodBean,int position) {
//        this.currentPeriodBean = currentPeriodBean;
//        this.selectPosition = position;
//        //动态申请相机权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0x0003);
//        }else {
//            openCamera(currentPeriodBean, selectPosition);
//        }
//    }
//
//    @Override
//    public void permissionSuccess(int request_code_permission) {
//        super.permissionSuccess(request_code_permission);
//        if (request_code_permission == 0x0003){
//            openCamera(currentPeriodBean, position);
//        }
//    }
//
//    /**
//     * 跳传到录像界面
//     * @param currentPeriodBean
//     * @param position
//     */
//    private void openCamera(CurrentPeriodBean currentPeriodBean, int position) {
//        Intent intent = new Intent(this, OpenCameraActivity.class);
//        intent.putExtra("position", position);
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("currentPeriod", currentPeriodBean);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//}
