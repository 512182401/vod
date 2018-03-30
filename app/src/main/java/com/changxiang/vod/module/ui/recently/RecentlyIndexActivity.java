package com.changxiang.vod.module.ui.recently;//package tosingpk.quchangkeji.com.quchangpk.module.ui.recently;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.view.MyAlertDialog;
//import com.quchangkeji.tosing.module.base.AdapterListListener;
//import com.quchangkeji.tosing.module.base.BaseApplication;
//import com.quchangkeji.tosing.module.db.ParameterBean;
//import com.quchangkeji.tosing.module.db.SongDetail;
//import com.quchangkeji.tosing.module.entry.HotSong;
//import com.quchangkeji.tosing.module.ui.base.BaseMusicActivity;
//import com.quchangkeji.tosing.module.ui.listening.MusicPlayActivity;
//import com.quchangkeji.tosing.module.ui.music_download.downloadservice.DownloadAllService;
//import com.quchangkeji.tosing.module.ui.music_download.receiver.MusicReceiver;
//import com.quchangkeji.tosing.module.ui.recently.adapter.RecentlyPlayerAdapterUpdate;
//import com.quchangkeji.tosing.module.ui.recently.db.PlayedHistory;
//import com.quchangkeji.tosing.module.ui.sing.OpenCameraActivity;
//import com.quchangkeji.tosing.module.ui.topmusic.ISetProgress;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
///**最近播放：
// * Created by 15976 on 2016/10/17.
// */
//
//public class RecentlyIndexActivity extends BaseMusicActivity implements View.OnClickListener,AdapterListListener<List<PlayedHistory>>,ISetProgress {
//    private TextView top_text;//顶部居中显示
//    private ImageView bt_right;//顶部编辑
//    private ImageView bt_back;//返回
//    private ListView listView;
//    RecentlyPlayerAdapterUpdate adapter;
//    MusicReceiver musicReceiver;//广播接收器
//    IntentFilter mFilter;
//    private View dialog;//对话框布局
//    private TextView tvDefault;//默认图像
//
//    private SongDetail songDetail;//播放歌曲对象
//
//    @Override
//    public void handMsg(Message msg) {
//
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recently);
//        initView();
//        initData();
//        initEvent();
//    }
//
//    private void initView() {
//        registerReceiver();
//        dialog = LayoutInflater.from(this).inflate(R.layout.quchang_alertdialog, null);
//        bt_back = (ImageView) findViewById( R.id.back_last );
//        top_text = (TextView) findViewById( R.id.center_text );
//        bt_right = (ImageView) findViewById( R.id.back_next );
//        top_text.setText( getString(R.string.play_recently));
//        bt_right.setVisibility( View.VISIBLE );
//        bt_right.setImageResource( R.drawable.recnetly_edit );
//        listView= (ListView) findViewById(R.id.activity_recently_lv);
//        tvDefault= (TextView) findViewById(R.id.tv_default);
//    }
//
//    private void initData() {
//        adapter=new RecentlyPlayerAdapterUpdate(this);
//        listView.setAdapter(adapter);
//        adapter.setListener(this);//设置回调监听
//
//    }
//
//    private void initEvent() {
//        bt_back.setOnClickListener(this);
//        bt_right.setOnClickListener(this);
//    }
//    //注册下载进度监听器
//    public void registerReceiver(){
//        musicReceiver=new MusicReceiver();
//        musicReceiver.setiSetProgress(this);
//        mFilter = new IntentFilter();
//        mFilter.addAction("ACTION_DOWNLOADING");
//        mFilter.addAction("ACTION_FINISH");
//        mFilter.addAction("ACTION_EXCEPTION");
//        registerReceiver(musicReceiver, mFilter);
//    }
//    //反注册网络监听器
//    public void unRegisterReceiver(){
//        if (musicReceiver!=null){
//            unregisterReceiver(musicReceiver);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.back_last:
//                finishActivity();
//                /*Intent home=new Intent(this, HomeActivity.class);
//                startActivity(home);*/
//                break;
//            case R.id.back_next:
//                //跳转编辑页面
//                Intent intent=new Intent(RecentlyIndexActivity.this,EditPlayedActivity.class);
//                intent.putStringArrayListExtra("ids",adapter.ids);
//                intent.putStringArrayListExtra("names",adapter.names);
//                intent.putStringArrayListExtra("singerNames",adapter.singerNames);
//                startActivity(intent);
//                break;
//
//        }
//    }
//
//    /**
//     * AdapterListListener的点击事件
//     * @param opt 标志位
//     * @param position 点击的位置
//     * @param item 传递的数据
//     */
//
//    List<HotSong> allSongList = new ArrayList<>();//用于封装动态列表数据（歌曲）
//    ArrayList<String> ids = new ArrayList<>();
//    ArrayList<String> types = new ArrayList<>();
//    ArrayList<String> names = new ArrayList<>();
//    ArrayList<String> singerNames = new ArrayList<>();
//    PlayedHistory playedHistory;
//    @Override
//    public void click(int opt, final int position, List<PlayedHistory> item) {
//        Intent intent;
//        Bundle bundle = new Bundle();
//        allSongList.clear();
//        HotSong mHotSong = new HotSong();
//        for (int i = 0; i < item.size(); i++) {
//            mHotSong = new HotSong();
//            mHotSong.setId(item.get(i).getSongId());
//            mHotSong.setType(item.get(i).getType());
//            mHotSong.setName(item.get(i).getName());
//            mHotSong.setSingerName(item.get(i).getSingerName());
//            mHotSong.setImgAlbumUrl(item.get(i).getImgAlbumUrl());
//            mHotSong.setNum(item.get(i).getNum());
//            mHotSong.setSize(item.get(i).getSize());
//            mHotSong.setImgHead(item.get(i).getImgHead());
//            allSongList.add(mHotSong);
//
//
//            String id = item.get(i).getSongId();
//            String type = item.get(i).getType();
//            String name = item.get(i).getName();
//            String singerName = item.get(i).getSingerName();
//            ids.add(id);
//            types.add(type);
//            names.add(name);
//            singerNames.add(singerName);
//        }
//        switch (opt) {
//            case 1:
//                intent = new Intent( RecentlyIndexActivity.this, MusicPlayActivity.class );
//                bundle.putSerializable("songList", (Serializable) allSongList);
////                bundle.putStringArrayList( "ids", ids );
////                bundle.putStringArrayList( "types", types );
////                bundle.putStringArrayList( "names", names );
////                bundle.putStringArrayList( "singerNames", singerNames );
//                intent.putExtras( bundle );
//                intent.putExtra( "position", position );
//                startActivity( intent );
//                break;
//            case 2://练歌
//                try{
//                    songDetail = adapter.getDownloadItem(position);
//                    requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0002);
//                }catch (Exception e){
//                    e.printStackTrace();}
//
////                intent = new Intent( RecentlyIndexActivity.this, HomeActivity.class );
////                intent.putExtra( "songId", item.get( position).getSongId());
////                intent.putExtra("musicType", item.get( position).getType());
////                startActivity( intent );
//                break;
//            case 3:
//                playedHistory=item.get(position);
//                switch (BaseApplication.wifiState){
//                    case 0://
//                    case 1://每次提醒
//                        netNotice(position,0,playedHistory);
//                        break;
//                    case 2://首次提醒
//                        if (BaseApplication.isNoticeOnce!=1){
//                            netNotice(position,1,playedHistory);
//                        }else {
//                            startDownload(position,playedHistory);
//                        }
//                        break;
//                    case 3://不提醒
//                    case 4://wifi
//                        startDownload(position,playedHistory);
//                        break;
//                    case 5:
//                        toast(getString(R.string.no_net));
//                        break;
//                    default:
//                        break;
//                }
//
//
//                break;
//            case 4://取消下载
//                BaseApplication.isStop=true;
//                if (ids!=null&&ids.size()>position){
//                    if (adapter!=null){
//                        adapter.setOnError(position,ids.get(position),types.get(position));
//                    }
//                }
//                break;
//            case 5://取消等待
//                if (ids!=null&&ids.size()>position){
//                    DownloadAllService.removeWaitingDownload(ids.get(position),types.get(position));
//                    if (adapter!=null){
//                        adapter.cancelWaiting(position,ids.get(position),types.get(position));
//                    }
//                }
//                break;
//        }
//    }
//    @Override
//    public void permissionSuccess(int request_code_permission) {
//        super.permissionSuccess(request_code_permission);
//        switch (request_code_permission) {
//            case 0x0002://开启摄像头的权限
//                Intent intent = new Intent(RecentlyIndexActivity.this, OpenCameraActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("songDetail",songDetail);
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
//    public void startDownload(int position,PlayedHistory playedHistory){
//        String name=playedHistory.getName();
//        String singerName=playedHistory.getSingerName();
//        String id=playedHistory.getSongId();
//        String type=playedHistory.getType();
//        String imgCover=playedHistory.getImgAlbumUrl();
//        toast("《"+name+"》"+getString(R.string.download_msg));
//        adapter.setWaitDownload(position,id,type);
//        Intent intent = new Intent(this, DownloadAllService.class);
//        ParameterBean parameterBean=new ParameterBean(id,type,name,singerName,imgCover,position);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("parameterBean",parameterBean);
//        intent.putExtras(bundle);
//        intent.setAction("ACTION_START");
//        startService(intent);
//    }
//
//    /**
//     * 流量提醒对话框
//     * @param position
//     * @param isNoticeOnce 是否已经提醒过了（首次提醒）
//     */
//    private void netNotice(final int position, final int isNoticeOnce, final PlayedHistory playedHistory){
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
//                        BaseApplication.isNoticeOnce=isNoticeOnce;
//                        startDownload(position,playedHistory);
//                    }
//                }).setNegativeButton(getString(R.string.cancel2), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        }).show();
//    }
//
//    @Override
//    protected void onResume() {
//        adapter=new RecentlyPlayerAdapterUpdate(this);
//        listView.setAdapter(adapter);
//        adapter.setListener(this);//设置回调监听
//        if (adapter.getDataList()==null||adapter.getDataList().size()==0){
//            tvDefault.setVisibility(View.VISIBLE);
//        }else {
//            tvDefault.setVisibility(View.GONE);
//        }
//
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unRegisterReceiver();
//    }
//
//    @Override
//    public void setDownloadProgress(int position, int percent, String songId, String musicType) {
//        if (adapter!=null){
//            adapter.setDownloadProgress(position,percent,songId,musicType);
//
//        }
//        LogUtils.w("TAG1","Activity==position:"+position+",percent"+percent);
//    }
//
//    @Override
//    public void setFinishImg(int position, String songId, String musicType) {
//        if (adapter!=null){
//            adapter.setFinishImg(position,songId,musicType);
//        }
//    }
//
//    @Override
//    public void setOnException(int position, String songId, String musicType) {
//        if (adapter!=null){
//            adapter.setOnError(position,songId,musicType);
//        }
//    }
//}
