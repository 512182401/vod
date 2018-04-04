package com.changxiang.vod.module.ui.localmusic;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.FileUtil;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.base.AdapterCommonListener;
import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.db.ComposeManager;
import com.changxiang.vod.module.db.LocalCompose;
import com.changxiang.vod.module.service.receiver.ComposeSetProgress;
import com.changxiang.vod.module.service.receiver.UploadReceiver;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.localmusic.adapter.MyProAdapter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LocalMusicIndexActivity extends BaseActivity implements View.OnClickListener,
        AdapterCommonListener<LocalCompose>, ComposeSetProgress {

    private MyProAdapter mAdapter;

    private ListView myprolist;//  lv_localmusic_mypro

    private TextView imageView;//没有数据时显示的图片
    private ComposeManager mComposeManager;//数据库：
    private List<LocalCompose> mList;//数据库集合
    private List<LocalCompose> existList = new ArrayList<>();//文件和记录都存在的集合
    private UploadReceiver mUploadReceiver;//上传广播接收器
    //    private ComposeReceiver mDIYComposeReceiver;//diy合成广播接收器
    private IntentFilter mFilter;//过滤器
    static Context context = null;

    private TextView top_text;//顶部居中显示
    private ImageView bt_right;//右边显示
    private ImageView bt_back;//返回
    private PopupWindow popWnd;
    //private TextView right_text;//y右边显示

    CheckBox selectAll;
    ImageView selectDelete;
    LinearLayout edit_bottom;


    @Override
    public void handMsg(Message msg) {

        Intent intent;
        switch (msg.what) {
            case -1:
                toast("联网登录出现网络异常错误");
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                break;
            case 0:
//                toast( "成功" );
//                    handler.sendEmptyMessageDelayed( 0, 1000 );

//                initData();
                break;
            case 1:
                imageView.setVisibility(View.VISIBLE);
                myprolist.setVisibility(View.GONE);
//                toast( "返回数据为空" );//
//                handler.sendEmptyMessageDelayed( 1, 100 );
                break;
            case 2://刷新成功，刷新界面
                imageView.setVisibility(View.GONE);
                myprolist.setVisibility(View.VISIBLE);
//                try {
////                    (String key,String field,String content)
//                    mComposeManager.updateCompose(mList.get( 0 ).getCompose_id(),"isUpload","1"  );//标志数据库为已经上传
//                    LogUtils.sysout( "修改数据库，为已经上传。" );
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                mAdapter.setDataList( mList );
                mAdapter.setDataList(existList);

//                handler.sendEmptyMessageDelayed( 2, 100 );
                break;
            case 3://加载更多成功，刷新界面
//                mAdapter.addDataList( mList );
//                handler.sendEmptyMessageDelayed( 3, 100 );
                break;
            case 123://权限
//                updataimage();
//                handler.sendEmptyMessageDelayed( 123, 100 );
                break;

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic);
        Intent intent = getIntent();
        String intentType = intent.getStringExtra("type");

        initView();
        initData();
        if (intentType != null && !intentType.equals("")) {//&&intentType.equals( "1" ) 主要为下载自动跳转
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void initData() {
        getDataFormBD();//获取本地数据库数据

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {

        selectAll = (CheckBox) findViewById(R.id.select_all);
        selectDelete = (ImageView) findViewById(R.id.select_delete);
        edit_bottom = (LinearLayout) findViewById(R.id.ll_edit_bottom);

        bt_back = (ImageView) findViewById(R.id.back_last);
        top_text = (TextView) findViewById(R.id.center_text);
        //right_text = (TextView) findViewById( R.id.back_next_left );
        bt_right = (ImageView) findViewById(R.id.back_next);

        imageView = (TextView) findViewById(R.id.fragment_works_default);
        mComposeManager = ComposeManager.getComposeManager(getApplicationContext());
        mList = new ArrayList<>();
        myprolist = (ListView) findViewById(R.id.lv_localmusic_mypro);
        mAdapter = new MyProAdapter(this);
        //TODO
        mAdapter.setListener(this);
        myprolist.setAdapter(mAdapter);

        registerReceiver();

//        selectAll.setOnClickListener(this);
        selectDelete.setOnClickListener(this);

        bt_back.setOnClickListener(this);
        bt_right.setOnClickListener(this);

        top_text.setText(R.string.local_work);
        bt_right.setVisibility(View.VISIBLE);
        bt_right.setImageResource(R.drawable.local_delete);


//        mOnPageChangeListener = new MyOnPageChangeListener();
        myprolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocalMusicIndexActivity.this, GeWangPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("LocalCompose", (Serializable) existList);//序列化,要注意转化
                intent.putExtra("position", position);
                intent.putExtra("tag", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //点击check事件，全选或全不选
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (int i = 0; i < existList.size(); i++) {
                        existList.get(i).setIsExport("1");
                    }
                } else {
                    for (int i = 0; i < existList.size(); i++) {
                        existList.get(i).setIsExport("2");
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_last://回退  ： 退出删除和退出Activity
                if (mAdapter != null && mAdapter.deleteState) {
                    mAdapter.deleteState = false;
                    mAdapter.notifyDataSetChanged();
//                    popWnd.dismiss();

                    edit_bottom.setVisibility(View.GONE);
                } else {
//                    String save = getIntent().getStringExtra("save");
//                    LogUtils.w("save", "save=====" + save);
//                    Intent intent = new Intent(this, HomeActivity.class);
//                    intent.putExtra("fragmentitem", "sing");
//                    startActivity(intent);
//                    finishActivity();
                }
                break;
            case R.id.back_next:
//                toast("删除");
                if ((mAdapter != null && mAdapter.deleteState) || (existList != null && existList.size() == 0)) {//防止重复点击
                    return;
                }
                //刷新adapter
                if (mAdapter != null) {
                    for (int i = 0; i < existList.size(); i++) {
                        existList.get(i).setIsExport("1");//1：没选择  !1:选中
                    }
                    mAdapter.deleteState = true;
                    mAdapter.notifyDataSetChanged();
                    //显示底部popup
//                    showDeleteBottom();

                    edit_bottom.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.select_all://选择全部

                break;
            case R.id.select_delete://全部删除

                //获取arrCheck和allSongList,处理适配器数据源
                try {
                    Iterator<LocalCompose> iterator = existList.iterator();
                    while (iterator.hasNext()) {
                        LocalCompose localCompose = iterator.next();
                        if (!"1".equals(localCompose.getIsExport())) {
                            //删除SD
                            FileUtil.delectFile(new File(localCompose.getCompose_file()));
                            //处理数据库
                            mComposeManager.deleteCompose(localCompose.getCompose_id());
                            //删除集合
                            iterator.remove();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //更改界面和数据库
                mAdapter.deleteState = false;
                if (existList.size() == 0) {
                    myprolist.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.setDataList(existList);
                }

                edit_bottom.setVisibility(View.GONE);
                break;


        }

    }

    //点击删除按钮时，弹出底部的popu
//    private void showDeleteBottom3() {
//        edit_bottom.setVisibility(View.VISIBLE);
//
//    }

    //点击删除按钮时，弹出底部的popu
    private void showDeleteBottom() {
        View contentView = LayoutInflater.from(LocalMusicIndexActivity.this).inflate(R.layout.popuplayout, null);
        popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        final CheckBox selectAll = (CheckBox) contentView.findViewById(R.id.select_all);
        ImageView selectDelete = (ImageView) contentView.findViewById(R.id.select_delete);
        //点击check事件，全选或全不选
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (int i = 0; i < existList.size(); i++) {
                        existList.get(i).setIsExport("1");
                    }
                } else {
                    for (int i = 0; i < existList.size(); i++) {
                        existList.get(i).setIsExport("2");
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        selectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("删除");
                //获取arrCheck和allSongList,处理适配器数据源
                try {
                    Iterator<LocalCompose> iterator = existList.iterator();
                    while (iterator.hasNext()) {
                        LocalCompose localCompose = iterator.next();
                        if (!"1".equals(localCompose.getIsExport())) {
                            //删除SD
                            FileUtil.delectFile(new File(localCompose.getCompose_file()));
                            //处理数据库
                            mComposeManager.deleteCompose(localCompose.getCompose_id());
                            //删除集合
                            iterator.remove();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //更改界面和数据库
                mAdapter.deleteState = false;
                if (existList.size() == 0) {
                    myprolist.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.setDataList(existList);
                }
//                popWnd.dismiss();
                edit_bottom.setVisibility(View.GONE);
            }
        });
        //显示pop
        View rootview = LayoutInflater.from(this).inflate(R.layout.my_accompany_activity, null);
        popWnd.setBackgroundDrawable(new BitmapDrawable());
        popWnd.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void click(int opt, LocalCompose item) {
        Intent intent;
        switch (opt) {
            case 1:
//                Toast.makeText( getActivity(),"上传/分享",Toast.LENGTH_LONG ).show();
//                getContext().toast( "上传/分享" );
                String times = item.getCompose_finish();
                Long iten = 0L;
                LogUtils.sysout("录制时间：" + times);
                Long longtime = 0L;
                if (times != null && !times.equals("")) {
                    try {
                        iten = Long.parseLong(times);
                        if (iten != 0) {
                            longtime = iten / 1000;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogUtils.sysout("您的作品时长   =" + longtime);
                    if (longtime < 60 && longtime != 0) {
                        toast("您的作品时长没有超过60秒，请重新录制长一点。");
                        return;
                    }
                }
                //TODO
                int isUpload = 0;//是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
                String isUploadStr = item.getIsUpload();


                if (isUploadStr != null && !isUploadStr.equals("")) {
                    isUpload = Integer.parseInt(isUploadStr);
                } else {
                    isUpload = 0;
                }


                Bundle bundle;
                switch (isUpload) {
                    case 0://可以上传跳转
//                        intent = new Intent(this, LocalCommitActivity.class);
//                        bundle = new Bundle();
//                        bundle.putSerializable("LocalCompose", item);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
                        break;
                    case 1://可以停止上传
                        LogUtils.sysout("99999999999调到我的主页");
                        break;
                    case 2://可以停止上传
                        break;
                    case 3://可以上传跳转
//                        intent = new Intent(this, LocalCommitActivity.class);
//                        bundle = new Bundle();
//                        bundle.putSerializable("LocalCompose", item);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
                        break;
                }
                break;
            case 2://已经上传了，调到我的主页
//                LogUtils.sysout("已经上传了，调到我的主页");
//                intent = new Intent(this, PersonalActivity.class);
////                intent = new Intent( getActivity(), LocalCommitActivity.class );
//                startActivity(intent);
                break;
            case 4://取消上传等待
////                toast("停止上传");
////                BaseApplication.isUploadStop=true;
//                UploadWorkService.removeWaitingDownload(item.getCompose_id());
//                //是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
//                mComposeManager.updateCompose(item.getCompose_id(), "isUpload", "0");//标志数据库为已经上传
                break;

            case 5://  取消上传
//                toast("停止上传");
                BaseApplication.isUploadStop = true;
//                UploadWorkService.removeWaitingDownload(item.getCompose_id());
                //是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
                mComposeManager.updateCompose(item.getCompose_id(), "isUpload", "0");//标志数据库为已经上传
                break;
            case 6://取消合成等待
//                toast("停止上传");
//                BaseApplication.isUploadStop=true;
//                UploadWorkService.removeWaitingDownload(item.getCompose_id());
//                //Compose_MuxerTask 是否合成成功：0：初始状态（刚刚入库）；1：已经合成完成；2：等待合成状态；:3：合成进行中；4：合成失败（文件不存在）
//                mComposeManager.updateCompose(item.getCompose_id(), "isUpload", "0");//标志数据库为已经上传
                break;

            case 7://  取消合成
//                toast("停止上传");
                BaseApplication.isComposeDiyStop = true;
                //Compose_MuxerTask 是否合成成功：0：初始状态（刚刚入库）；1：已经合成完成；2：等待合成状态；:3：合成进行中；4：合成失败（文件不存在）
                mComposeManager.updateCompose(item.getCompose_id(), "Compose_MuxerTask", "0");//标志数据库为合成失败
                mComposeManager.deleteCompose(item.getCompose_id());//删除该条数据库记录
//                mAdapter.notifyDataSetChanged();
                break;
            case 8://  本地播放：
                int position = 0;
                for (int n = 0; n < existList.size(); n++) {
                    if (existList.get(n).getCompose_id().equals(item.getCompose_id())) {
                        position = n;
                    }
                }
//                intent = new Intent(this, LocalMusicPlayer.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                bundle = new Bundle();
//                bundle.putSerializable("LocalCompose", (Serializable) existList);//序列化,要注意转化(
//                intent.putExtra("position", position);
//                intent.putExtras(bundle);
//                startActivity(intent);
                break;


        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAdapter != null && mAdapter.deleteState) {
            mAdapter.deleteState = false;
            mAdapter.notifyDataSetChanged();
//            popWnd.dismiss();
            edit_bottom.setVisibility(View.GONE);
            return true;
        } else {
//            Intent intent = new Intent(this, HomeActivity.class);
//            intent.putExtra("fragmentitem", "sing");
//            startActivity(intent);
//            finishActivity();
        }
        return super.onKeyDown(keyCode, event);
    }


    //注册下载，合成进度监听器
    private void registerReceiver() {

        mUploadReceiver = new UploadReceiver();
        mUploadReceiver.setiSetProgress(this);
        mFilter = new IntentFilter();
        mFilter.addAction("ACTION_UPLOADING");
        mFilter.addAction("ACTION_UPLOAD_FINISH");
        mFilter.addAction("ACTION_UPLAOD_EXCEPTION");
        registerReceiver(mUploadReceiver, mFilter);


    }

    //反注册网络监听器
    private void unRegisterReceiver() {
        if (mUploadReceiver != null) {
            unregisterReceiver(mUploadReceiver);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        unRegisterReceiver();
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
        mList = mComposeManager.queryAll();
//        mList = mComposeManager.queryNoDelect();
//        if (mList.size() > 0) {
//            for(int i = 0;i<mList.size();i++ ){
//                mComposeManager.updateCompose(mList.get( i ).getCompose_id(),"Compose_delete","0"  );//标志数据库为已经上传
//            }
//            mComposeManager.updateCompose(mList.get( 0 ).getCompose_id(),"Compose_delete","1"  );//标志数据库为已经上传
//        }
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                LocalCompose localCompose = mList.get(i);

                if (!localCompose.getCompose_type().equals("4") && !localCompose.getCompose_type().equals("7")) {//非DIY合成和非视频合成
                    String songUrl = localCompose.getCompose_file();
                    File file = new File(songUrl);
                    if (file.exists()) {
                        existList.add(localCompose);
                        String isUpload = localCompose.getIsUpload();
                        //每次查询，都添加到后台上传
                        if (isUpload != null && !isUpload.equals("") && Integer.parseInt(isUpload) >= 2) {//
                            startUpload(localCompose);
                            LogUtils.sysout("--------自动上传");
                        }
                    } else {//文件不存在，则删除数据库：
//                        mComposeManager.deleteCompose(localCompose.getCompose_id());
                    }
                } else if (localCompose.getCompose_type().equals("4")) {//DIY合成：
                    //判断是否合成失败：1:判断是否有后台合成
                    if (isWorked()) {//有后台在合成DIY，获取合成的ID号
                        if (BaseApplication.composeid != null && !BaseApplication.composeid.equals(localCompose.getCompose_id())) {
                            if (!localCompose.getCompose_MuxerTask().equals("1") && !localCompose.getCompose_MuxerTask().equals("4")) {//合成不成功
                                localCompose.setCompose_MuxerTask("4");
                                mComposeManager.updateCompose(localCompose.getCompose_id(), "Compose_MuxerTask", "4");//标志数据库为合成失败
                            }
                        }

                    } else {//没有后台合成DIY
                        if (!localCompose.getCompose_MuxerTask().equals("1") && !localCompose.getCompose_MuxerTask().equals("4")) {//合成不成功
                            localCompose.setCompose_MuxerTask("4");
                            mComposeManager.updateCompose(localCompose.getCompose_id(), "Compose_MuxerTask", "4");//标志数据库为合成失败
                        }

                    }

                    existList.add(localCompose);
                } else {//视频合成：
                    //判断是否合成失败：1:判断是否有后台合成
                    if (isWorked()) {//有后台在合成DIY，获取合成的ID号
                        if (BaseApplication.composeid != null && !BaseApplication.composeid.equals(localCompose.getCompose_id())) {
                            if (localCompose != null && localCompose.getCompose_MuxerTask() != null && !localCompose.getCompose_MuxerTask().equals("1") && !localCompose.getCompose_MuxerTask().equals("4")) {//合成不成功
                                localCompose.setCompose_MuxerTask("4");
                                mComposeManager.updateCompose(localCompose.getCompose_id(), "Compose_MuxerTask", "4");//标志数据库为合成失败
                            }
                        }

                    } else {//没有后台合成DIY
                        if (localCompose.getCompose_MuxerTask() != null && !localCompose.getCompose_MuxerTask().equals("1") && !localCompose.getCompose_MuxerTask().equals("4")) {//合成不成功
                            localCompose.setCompose_MuxerTask("4");
                            mComposeManager.updateCompose(localCompose.getCompose_id(), "Compose_MuxerTask", "4");//标志数据库为合成失败
                        }

                    }

                    existList.add(localCompose);
                }
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

//    在开启一个服务之前应该判断该服务知否已经在运行

    //本方法判断自己些的一个Service-->com.android.controlAddFunctions.PhoneService是否已经运行
    public static boolean isWorked() {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals("com.quchangkeji.tosing.module.ui.diyVedio.service.GIFComposeService") ||
                    runningService.get(i).service.getClassName().toString().equals("com.quchangkeji.tosing.module.ui.diyVedio.service.DIYComposeService")) {
//                LogUtils.sysout("8888888888888");
                return true;
            }
        }
        return false;
    }


    /**
     * 开始上传：
     */
    private void startUpload(LocalCompose mLocalCompose) {
//        Intent intent = new Intent(this, UploadWorkService.class);
//        UploadWork mUploadWork = new UploadWork(mLocalCompose.getCompose_id(), mLocalCompose, null, 0, 0);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("UploadWork", mUploadWork);
//        intent.putExtras(bundle);
//        intent.setAction("UPLOAD_START");
//        startService(intent);

    }

    //上传监听开始

    @Override
    public void setUploadProgress(int position, int percent, String songId, String musicType) {//进度条
        if (mAdapter != null) {
            mAdapter.setUploadProgress(position, percent, songId, musicType);

        }
        LogUtils.sysout("************Activity==position:" + position + ",percent" + percent);
    }

    @Override
    public void setUploadFinishImg(int position, String songId, String musicType) {
        if (mAdapter != null) {
            mAdapter.setFinishImg(position, songId, musicType);
            LogUtils.sysout("***************上传完成！");
        }
    }

    @Override
    public void setUploadOnException(int position, String songId, String musicType) {
        if (mAdapter != null) {
            mAdapter.setOnError(position, songId, musicType);
            LogUtils.sysout("***************上传失败！");
        }
    }


    @Override
    public void setComposeProgress(int position, int percent, String songId, String musicType) {
        if (mAdapter != null) {
            mAdapter.setComposeProgress(position, percent, songId, musicType);

        }
        LogUtils.sysout("************ 合成中 Activity==position:" + position + ",percent" + percent);
    }

    @Override
    public void setComposeFinishImg(int position, String songId, String musicType) {
        if (mAdapter != null) {
            mAdapter.setComposeFinishImg(position, songId, musicType);
//            mAdapter.notifyDataSetChanged();
        }
        //因为position=-1；所以，只能从新请求本地数据库信息
//        getDataFormBD();
        LogUtils.sysout("***************333合成完成！");
    }

    @Override
    public void setComposeOnException(int position, String songId, String musicType) {
        if (mAdapter != null) {
            mAdapter.setComposeException(position, songId, musicType);
        }
        LogUtils.sysout("***************合成失败！");
    }

    //上传监听结束
}
