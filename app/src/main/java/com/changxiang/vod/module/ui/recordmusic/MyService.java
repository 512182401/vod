//package com.changxiang.vod.module.ui.recordmusic;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.database.Cursor;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.os.Binder;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.os.RemoteException;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//
//import com.danikula.videocache.CacheListener;
//import com.danikula.videocache.HttpProxyCacheServer;
//import com.quchangkeji.tosing.aidl.ServicePlayer;
//import com.quchangkeji.tosingpk.common.utils.AppUtil;
//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.common.utils.MyFileUtil;
//import com.quchangkeji.tosingpk.module.base.BaseApplication;
//import com.quchangkeji.tosingpk.module.db.DownloadManager;
//import com.quchangkeji.tosingpk.module.engine.JsonParserFirst;
//import com.quchangkeji.tosingpk.module.entry.CurrentPeriodBean;
//import com.quchangkeji.tosingpk.module.entry.SongBean;
//import com.quchangkeji.tosingpk.module.entry.SongDetail;
//import com.quchangkeji.tosingpk.module.music_download.net.SongUrl2;
//import com.quchangkeji.tosingpk.module.ui.listening.PlayerManager;
//import com.quchangkeji.tosingpk.module.ui.recently.db.HistoryDBManager;
//import com.quchangkeji.tosingpk.module.ui.recently.db.PlayedHistory;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
////mp3后台播放
//public class MyService extends Service implements MediaPlayer.OnBufferingUpdateListener,
//        OnCompletionListener, CacheListener, MediaPlayer.OnErrorListener {
//
//    private MediaPlayer media;
//    private Mybroadmusic myBM;
//    private int num = 0;//
//    private static int check = 0;//播放模式，0代表顺序播放，1代表单曲循环
//    private static List<CurrentPeriodBean> allSongList;//歌曲对象集合
//    private static int position;//当前播放的第几首歌
//    private static int total;//总时长
//    private int progress;//音乐播放进度
//    private Context mContext;
//    private OnCacheListener onCacheListener;
//    public final IBinder binder = new MyBinder();
//
//    private DownloadManager dao;//数据库工具
//    private CurrentPeriodBean hotSong;//不包含歌曲地址的歌曲对象
//    private SongBean songBean;//包含歌曲地址的后台歌曲对象
//    //    private SongDetail songDetail;//数据库歌曲对象
//    private static int playState;//0 表示未播放，1 准备好，2 表示播放, 3 表示暂停
//    private String songId;//歌曲id
//    private String singId;//歌手id
//    private String musicType = "audio";
//    private String path;//歌曲路径
//    public static String lrcUrl;//歌词地址
//    public static String krcUrl;//krc歌词地址
//    public String imgAlbumUrl;//封面
//    private int playedNum;//点播量
//    public static String songName;//歌曲名
//    public String singerName;//歌手名
//    public String imgHead;//歌手头像
//    public String size;//文件总大小
//    private boolean isRecord, isDownload;//当前播放的歌曲是否已经有记录，是否已下载
//    private File file;//歌曲下载文件的地址
//    private String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//
//    private boolean isCompleted;//是否刚好播放完
//    private boolean isNeedStop;//是否是其他页面需要播放歌曲(避免出现跳转到其他页面，后台播放下一首的问题。只有在歌曲刚好播完，准备放一下首时才可能出现)
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 11://播放未下载的歌曲，发送更新UI的广播
//                    playNewSong(false);
//                    break;
//                case 12://播放已下载的歌曲，发送更新UI的广播
//                    playNewSong(false);
//                    break;
//                case -11://歌曲播放失败
//                    playFailed(0);
//                    break;
//                case -12://网络未连接
//                    playFailed(1);
//                    break;
//                case -13://歌曲信息请求失败
//                    playFailed(2);
//                    break;
//                case -14://发送流量使用广播
//                    playFailed(3);
//                    break;
//            }
//        }
//    };
//    private String activityId;
//
//    @Override
//    public boolean onError(MediaPlayer mp, int what, int extra) {
//        sendError();
//        return false;
//    }
//
//    private void sendError() {
//        Intent intent = new Intent();
//        intent.setAction("ACTION_ERROR");
//        intent.putExtra("alltime",media.getDuration());
//        sendBroadcast(intent);
//    }
//
//
//    //缓存进度回调接口
//    public interface OnCacheListener {
//        void getCacheProgress(int progress);
//    }
//
//    public MyService(Context mContext, OnCacheListener onCacheListener) {
//        this.mContext = mContext;
//        this.onCacheListener = onCacheListener;
//    }
//
//
//    ServicePlayer.Stub servicePlayer = new ServicePlayer.Stub() {
//        @Override
//        public int getCurrentTime() throws RemoteException {
//            int cur = 0;
//            if (media != null) {
//                cur = media.getCurrentPosition();
//            }
//            return cur;
//        }
//
//        @Override
//        public boolean isCompletion() throws RemoteException {
//
//            return isCompleted;
//        }
//    };
//
//
//    public MyService() {
//    }
//
//    public class MyBinder extends Binder {
//        public MyService getService() {
//            return MyService.this;
//        }
//
//        public void outPause() {
//            pause();
//        }
//    }
//
//    public void setOnCacheListener(OnCacheListener onCacheListener) {
//        this.onCacheListener = onCacheListener;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        LogUtils.sysout("绑定服务----onBind()");
//        return servicePlayer;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        LogUtils.sysout("创建服务----onCreate()");
//        dao = DownloadManager.getDownloadManager(this);
//        media = PlayerManager.getPlayer();
//        media.setOnCompletionListener(this);
//        media.setOnBufferingUpdateListener(this);
//        media.setOnErrorListener(this);
//
//        //注册广播接收者
//        myBM = new Mybroadmusic();
//        zhuce();
//        // 对电话的来电状态进行监听
//        TelephonyManager telManager = (TelephonyManager) this
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        // 注册一个监听器对电话状态进行监听
//        telManager.listen(new MyPhoneStateListener(),
//                PhoneStateListener.LISTEN_CALL_STATE);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        LogUtils.sysout("创建服务----onStartCommand()");
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    public class CameraReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Cursor cursor = context.getContentResolver().query(intent.getData(),
//                    null, null, null, null);
//            cursor.moveToFirst();
//            String path = cursor.getString(cursor.getColumnIndex("_data"));
//        }
//    }
//
//
//    private class MyPhoneStateListener extends PhoneStateListener {
//        File audioFile;
//        String phoneNumber;
//
//        public void onCallStateChanged(int state, String incomingNumber) {
//            switch (state) {
//                case TelephonyManager.CALL_STATE_IDLE: /* 无任何状态时 */
////                    LogUtils.sysout("11111111111 无任何状态时");
//                    pause();
//                    break;
//                case TelephonyManager.CALL_STATE_OFFHOOK: /* 接起电话时 */
////                    LogUtils.sysout("11111111111 接起电话时");
//                    pause();
//                    break;
//
//                case TelephonyManager.CALL_STATE_RINGING: /* 电话进来时 */
////                    LogUtils.sysout("11111111111 电话进来时");
//                    pause();
//                    break;
//                default:
//                    break;
//            }
//            super.onCallStateChanged(state, incomingNumber);
//        }
//    }
//
//    //注册广播接收器
//    public void zhuce() {
//        IntentFilter mFilter = new IntentFilter();
//        mFilter.addAction("FIRST_PLAY");
//        mFilter.addAction("ACTION_STOP");
//        mFilter.addAction("ACTION_ISPLAY");
//        mFilter.addAction("ACTION_NEXT");
//        mFilter.addAction("ACTION_STYLE");
//        mFilter.addAction("ACTION_SEEKBAR");
//        mFilter.addAction("ACTION_ENSURE");
//        //照相和录像
//        mFilter.addAction(android.hardware.Camera.ACTION_NEW_PICTURE);
//        mFilter.addAction(android.hardware.Camera.ACTION_NEW_VIDEO);
////        mFilter.addAction("android.intent.action.CAMERA_BUTTON");
////        mFilter.addAction("android.intent.action.CAMERA_BUTTON");//外设键
////        mFilter.addAction(android.hardware.camera.ACTION_CAMERA_BUTTON);
//
////        try {
////            mFilter.addDataType("image/*");
////            mFilter.addDataType("video/*");
////        } catch (IntentFilter.MalformedMimeTypeException e) {
////            e.printStackTrace();
////        }
////        mFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        registerReceiver(myBM, mFilter);
//    }
//
//    //悬浮窗需要用到的信息
//    public static List<CurrentPeriodBean> getAllSongList() {
//        return allSongList;
//    }
//
//    public static int getPosition() {
//        return position;
//    }
//
//    public static int getTotal() {
//        return total;
//    }
//
//    public static String getLrcUrl() {
//        return lrcUrl;
//    }
//
//    public static String getKrcUrl() {
//        return krcUrl;
//    }
//
//    public static String getSongName() {
//        return songName;
//    }
//
//    public static int getPlayState() {
//        return playState;
//    }
//
//    public static int getCheck() {
//        return check;
//    }
//
//    public class Mybroadmusic extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            LogUtils.sysout("444444444444 intent.getAction()" + intent.getAction());
//            // TODO Auto-generated method stub
//            if (intent.getAction().equals("ACTION_ALLSONG")) {//获取歌曲对象集合
//
//                allSongList = (List<CurrentPeriodBean>) intent.getSerializableExtra("songList");
//            } else if (intent.getAction().equals("FIRST_PLAY")) {//播放新歌曲
//
//                isNeedStop = false;
//                playState = 0;
//                try {
//                    allSongList = (List<CurrentPeriodBean>) intent.getSerializableExtra("songList");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                position = intent.getIntExtra("position", 0);
//                hotSong = allSongList.get(position);
//                songId = hotSong.getId();
//                singId = hotSong.getSingerId();
//                activityId = hotSong.getActivityId();
//                musicType = hotSong.getType();
//                playNewSong(activityId, songId, musicType);
//            } else if (intent.getAction().equals("ACTION_ISPLAY")) {//播放或者暂停
//
//                boolean isplay = intent.getBooleanExtra("isplay", false);
//                isNeedStop = false;
//                if (!isplay) {
//                    pause();
//                } else {
//                    play(false);
//                }
//            } else if (intent.getAction().equals("ACTION_NEXT")) {//播放上一首或者下一首
//
//
//            } else if (intent.getAction().equals("ACTION_STYLE")) {//改变播放模式
//
//                check = intent.getIntExtra("check", 0);
//            } else if (intent.getAction().equals("ACTION_SEEKBAR")) {//拖动进度条
//                LogUtils.w("test2", "======================");
//                progress = intent.getIntExtra("seekbar", 0);
//                media.seekTo(progress);
//            } else if (intent.getAction().equals("ACTION_ENSURE")) {
//
//                getSongDetail(songId, musicType);
//            } else if (intent.getAction().equals("ACTION_STOP")) {//停止播放(其他页面播放歌曲时)
//                isNeedStop = true;
//                if (media != null && media.isPlaying()) {
//                    media.pause();
//                    playState = 3;
//                    mHandler.removeCallbacks(mRunnable);
//                }
//            } else if (intent.getAction().equals(android.hardware.Camera.ACTION_NEW_VIDEO)) {
//                pause();
//                LogUtils.sysout("46546545646调用了系统的照相功能 android.hardware.action.NEW_VIDEO");
//
//            } else if (intent.getAction().equals(android.hardware.Camera.ACTION_NEW_PICTURE)) {
//                pause();
//                LogUtils.sysout("46546545646调用了系统的录像功能 android.hardware.action.NEW_VIDEO");
//            } else if (intent.getAction().equals("android.intent.action.CAMERA_BUTTON")) {
//                pause();
//                LogUtils.sysout("46546545646调用了系统的录像功能 android.hardware.action.NEW_VIDEO");
//            }
//        }
//    }
//
//    /**
//     * 播放新歌
//     *
//     * @param songId    歌曲id
//     * @param musicType 歌曲类型
//     */
//    private void playNewSong(String activityId, String songId, String musicType) {
//        if (PlayerManager.getPlayer().isPlaying()) {//停止正在播放的歌曲
//            PlayerManager.getPlayer().stop();
//        }
//        isRecord = false;//
//        isDownload = false;
//        if (isHasRecord(activityId, songId, musicType)) {
//            if (isHasFile(musicType)) {
//                mHandler.sendEmptyMessage(12);
//            } else {
//                dao.setFieldNull("oriPath", null, songId, musicType);
//                requestSong(songId, musicType);
//            }
//        } else {
//            requestSong(songId, musicType);
//        }
//    }
//
//    //是否有下载记录
//    private boolean isHasRecord(String activityId, String songId, String musicType) {
//        SongDetail songDetail = dao.selectSong(activityId, songId, musicType);
//        String url = songDetail.getOriPath();
//        if (url != null && !url.equals("")) {
//            isRecord = true;
//            path = songDetail.getOriPath();
//            imgAlbumUrl = songDetail.getImgAlbumUrl();
//            songName = songDetail.getSongName();
////                singerName = songDetail.getSingerName();//当切换简体和繁体的时候，使用已下载的，所以不能用。
//            lrcUrl = songDetail.getLrcPath();
//            krcUrl = songDetail.getKrcPath();
//            imgHead = songDetail.getImgHead();
//            size = songDetail.getSize();
//            playedNum = songDetail.getNum();
//        } else {
//            isRecord = false;
//        }
//        return isRecord;
//    }
//
//    //文件是否存在
//    private boolean isHasFile(String musicType) {
//        if ("audio".equals(musicType)) {
//            file = new File(mp3Dir + singerName + "-" + songName + ".mp3");
//            if (file.exists()) {
//                isDownload = true;
//            } else {
//                isDownload = false;
//            }
//        } else {
//            isDownload = false;
//        }
//        return isDownload;
//    }
//
//    /**
//     * 获取未下载歌曲对应的信息
//     *
//     * @param songId1    歌曲id
//     * @param musicType1 歌曲类型
//     */
//    private void requestSong(final String songId1, final String musicType1) {
//        LogUtils.sysout("---------------------" + BaseApplication.wifiState);
//        switch (BaseApplication.wifiState) {
//            case 0://
//            case 1://每次提醒
//                //发送广播
//                mHandler.sendEmptyMessage(-14);
//                break;
//            case 2://首次提醒
//                if (BaseApplication.isNoticeOnce != 1) {
//                    //发送广播
//                    mHandler.sendEmptyMessage(-14);
//                } else {
//                    getSongDetail(songId1, musicType1);
//                }
//                break;
//            case 3://不提醒
//            case 4://wifi
//                getSongDetail(songId1, musicType1);
//                break;
//            case 5:
//                mHandler.sendEmptyMessage(-12);
//                break;
//        }
//    }
//
//    //获取歌曲的信息
//    private void getSongDetail(String songId1, String musicType1) {
//        SongUrl2.api_SongUrl_Mp3(BaseApplication.getOpenId(), AppUtil.getdeviceid(this), BaseApplication.getUserId(), songId1, "1", "a", musicType1, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.sysout("在线听歌数据,请求失败");
//                mHandler.sendEmptyMessage(-13);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String allData = response.body().string();
//                int code = JsonParserFirst.getRetCode(allData);
//                if (code == 0) {
//                    LogUtils.sysout("在线听歌数据：" + allData);
//                    try {
//                        JSONObject result = new JSONObject(allData);
//                        JSONObject data = result.getJSONObject("data");
//                        songBean = SongBean.objectFromData(data.toString());
//                        musicType = songBean.getType();
//                        songName = songBean.getname();
//                        singerName = songBean.getsingerName();
//                        imgHead = songBean.getimgHead();
//                        path = songBean.getYcUrl();
//                        lrcUrl = songBean.getGcUrl();//获取歌词地址
//                        krcUrl = songBean.getToneUrl();//获取歌词地址
//                        imgAlbumUrl = songBean.getimgAlbumUrl();
//                        playedNum = songBean.getnum();
//                        size = songBean.getSize();
//                        mHandler.sendEmptyMessage(11);//发送数据获取完成的消息
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    mHandler.sendEmptyMessage(-13);
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 获取播放地址后，播放歌曲
//     *
//     * @param isRepeat 是否是循环播放
//     */
//    private void playNewSong(boolean isRepeat) {
//        if (isRepeat) {
//            sendRepeat();
//            media.setLooping(true);
//            if (!isNeedStop) {
//                media.start();
//                isSuccess();
//            }
//        } else {
//            sendNewSong();
//            if (path != null) {
//                if (path.contains("http")) {
//                    setdata(path, 0);
//                } else {
//                    setdata(path, 1);
//                }
//                if (!isNeedStop) {
//                    play(true);
//                }
//            }
//        }
//    }
//
//    //发送歌曲信息
//    private void sendNewSong() {
//        isCompleted = false;
//        Intent intent = new Intent();
//        intent.setAction("NEW_SONG");
//        intent.putExtra("imgAlbumUrl", imgAlbumUrl);
//        intent.putExtra("songName", songName);
//        intent.putExtra("singerName", singerName);
//        intent.putExtra("songId", songId);
//        intent.putExtra("singId", singId);
//        intent.putExtra("musicType", musicType);
//        intent.putExtra("lrcUrl", lrcUrl);
//        intent.putExtra("krcUrl", krcUrl);
//        intent.putExtra("imgHead", imgHead);
//        intent.putExtra("position", position);
//        sendBroadcast(intent);
//    }
//
//    /**
//     * 播放歌曲
//     *
//     * @param path 歌曲路径
//     * @param tag  0 在线，1 本地
//     */
//    public void setdata(String path, int tag) {
//        LogUtils.sysout("3333333333333333333");
//        media.reset();
//        try {
//            if (tag == 0) {
//                LogUtils.sysout("444444444444444444");
//                checkCachedState(path, getApplicationContext());
//                HttpProxyCacheServer proxy = BaseApplication.getProxy(getApplicationContext());
//                proxy.registerCacheListener(this, path);
//                String proxyUrl = proxy.getProxyUrl(path);
//                media.setDataSource(proxyUrl);
//            } else {
//                media.setDataSource(path);
//            }
//            media.prepare();
//            playState = 1;
//            LogUtils.w("TAG", "当前播放歌曲地址：" + path);
//            total = media.getDuration();
//            maxtime();
//            LogUtils.w("TAG", "总时长：" + total);
//        } catch (Exception e) {
//            e.printStackTrace();
//            playState = 0;
//            LogUtils.sysout("5555555555555555");
//            mHandler.sendEmptyMessage(-11);
//        }
//    }
//
//    /**
//     * 获取缓存进度
//     *
//     * @param cacheFile         缓存文件
//     * @param url               地址
//     * @param percentsAvailable 缓存
//     */
//    @Override
//    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
//        if (onCacheListener != null) {
//            onCacheListener.getCacheProgress(percentsAvailable);
//        }
//    }
//
//    /**
//     * 检查缓存的状态
//     */
//    private void checkCachedState(String url, Context mContext) {
//        HttpProxyCacheServer proxy = BaseApplication.getProxy(mContext);
//        boolean fullyCached = proxy.isCached(url);
//        if (fullyCached && onCacheListener != null) {
//            onCacheListener.getCacheProgress(100);
//        }
//    }
//
//    /**
//     * 播放
//     *
//     * @param isFirst 首次播放为true，暂停后播放未false
//     */
//    private void play(boolean isFirst) {
//        if (playState == 1 || playState == 3) {
//            media.start();
//            playState = 2;
//            mHandler.postDelayed(mRunnable, 100);
//            if (isFirst) {
//                isSuccess();
//            }
//        }
//    }
//
//    //暂停播放
//    private void pause() {
//        if (playState == 2 && media.isPlaying()) {
//            media.pause();
////            media.stop();
//            playState = 3;
//            mHandler.removeCallbacks(mRunnable);
//        } else if (media != null) {
//            media.pause();
//            playState = 3;
//            mHandler.removeCallbacks(mRunnable);
//        }
//    }
//
//    //播放成功后发送的广播
//    private void isSuccess() {
//        Intent intent = new Intent();
//        intent.setAction("ACTION_SUCCESS");
//        sendBroadcast(intent);
//        addHistory();
//    }
//
//    //添加播放记录
//    private void addHistory() {
//        long time = System.currentTimeMillis();
//        PlayedHistory history = new PlayedHistory(songId, songName, singerName, musicType, time, imgHead, playedNum, null, imgAlbumUrl, size);
//        HistoryDBManager.getHistoryManager().insert(history);
//    }
//
//    //缓冲
//    @Override
//    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        LogUtils.w("TAG", "缓冲：" + percent);
//        Intent intent = new Intent();
//        intent.setAction("ACTION_BUFFER");
//        intent.putExtra("buffer", percent);
//        sendBroadcast(intent);
//    }
//
//    //播放完成后
//    @Override
//    public void onCompletion(MediaPlayer mp) {
////        playState = 0;
////        isCompleted = true;
//        sendCompletion();
////        if (check == 0) {//顺序播放
////            playNext();
////        } else {//单曲循环
////            playNewSong(true);
////        }
//    }
//
//    private void sendCompletion() {
//        Intent intent = new Intent();
//        intent.setAction("ACTION_COMPLETE");
//        intent.putExtra("isMp3", true);
//        intent.putExtra("alltime",media.getDuration());
//        intent.putExtra("isShow", !isNeedStop);//是否显示加载对话框
//        sendBroadcast(intent);
//    }
//
//    private void sendRepeat() {
//        Intent intent = new Intent();
//        intent.setAction("REPEAT_SONG");
//        sendBroadcast(intent);
//    }
//
//    //播放下一首
//    private void playNext() {
//        if (allSongList == null) {
//            return;
//        }
//        do {
//            position++;
//            if (position > allSongList.size() - 1) {
//                position = 0;
//            }
//            hotSong = allSongList.get(position);
//            songId = hotSong.getId();
//            singId = hotSong.getSingerId();
//            activityId = hotSong.getActivityId();
//            musicType = hotSong.getType();
//        } while (!"audio".equals(musicType));
//        playNewSong(activityId, songId, musicType);
//    }
//
//
//    /**
//     * 发送播放失败广播
//     *
//     * @param tag 0 播放失败，1 网络未连接，2 歌曲信息获取失败，3 流量使用提醒
//     */
//    private void playFailed(int tag) {
//        isCompleted = false;
//        playState = 0;
//        Intent intent = new Intent();
//        intent.setAction("ACTION_ERROR");
//        intent.putExtra("tag", tag);
//        sendBroadcast(intent);
//    }
//
//    //播放上一首
//    public void up() {
//        num--;
//        /*if (num < 0) {
//            num = alist.size() - 1;
//        }
//        String path = alist.get(num).getYcUrl();*/
//        setdata(path, 0);
//        play(true);
//    }
//
//    //播放下一首
//    public void down() {
//        num++;
//        /*if (num > alist.size() - 1) {
//            num = 0;
//        }
//        String path = alist.get(num).getYcUrl();*/
//        setdata(path, 0);
//        play(true);
//    }
//
//    //传递当前播放歌曲的时长
//    public void maxtime() {
//        Intent intent = new Intent();
//        intent.setAction("ACTION_MAXTIME");
//        if (media != null){
//            intent.putExtra("maxtime", media.getDuration());
//            sendBroadcast(intent);
//        }
//    }
//
//    //传递当前播放的进度
//    public void nowtime() {
//        Intent intent = new Intent();
//        intent.setAction("ACTION_NOWTIME");
//        if (media != null && media.isPlaying()) {
//            intent.putExtra("nowtime", media.getCurrentPosition());
//            sendBroadcast(intent);
//        }
//    }
//
//    Runnable mRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            nowtime();
//            mHandler.postDelayed(mRunnable, 1000);
//        }
//    };
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        LogUtils.sysout("解绑服务----onUnbind()");
//        return super.onUnbind(intent);
//    }
//
//    @Override
//    public void onDestroy() {
//        LogUtils.sysout("销毁服务----onDestroy()");
//        //反注册
//        if (myBM != null) {
//            unregisterReceiver(myBM);
//        }
//        if (media != null) {
//            media.release();
//        }
//        super.onDestroy();
//    }
//}
//
