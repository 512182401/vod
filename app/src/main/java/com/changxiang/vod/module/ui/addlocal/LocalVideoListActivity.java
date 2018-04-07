package com.changxiang.vod.module.ui.addlocal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.module.musicInfo.DisplayUtil;
import com.changxiang.vod.module.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by 15976 on 2017/11/1.
 */

public class LocalVideoListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backLast;
    private TextView centerText;
    private RecyclerView videoRecyclerview;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<MediaItem> mediaItems;
    private MyAdapter mAdapter;
    private ArrayList<Bitmap> arrBitmap;
    private LinearLayout emptyLL;

    @Override
    public void handMsg(Message msg) {
        switch (msg.what) {
            case 10:
                closeProgressDialog();
                if (mediaItems != null && mediaItems.size() > 0) {
                    LogUtils.w("mediaItems", "mediaItems====" + mediaItems.size());
                    LogUtils.w("mediaItems", "mediaItems====" + mediaItems.toString());
                    //创建并设置Adapter
                    mAdapter = new MyAdapter(mediaItems, arrBitmap, this);
                    videoRecyclerview.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, MediaItem mediaItem) {
//                            Toast.makeText(LocalVideoListActivity.this, "" + mediaItem.getName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LocalVideoListActivity.this, PreformViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mediaItem", mediaItem);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                } else {
                    //空视图
                    videoRecyclerview.setVisibility(View.GONE);
                    emptyLL.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设定为竖屏
        setContentView(R.layout.local_video_list);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        backLast = (ImageView) findViewById(R.id.back_last);
        centerText = (TextView) findViewById(R.id.center_text);
        emptyLL = (LinearLayout) findViewById(R.id.ll_no_data_show);
        videoRecyclerview = (RecyclerView) findViewById(R.id.video_recyclerview);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new GridLayoutManager(this, 4);
        videoRecyclerview.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        videoRecyclerview.setHasFixedSize(true);
    }

    private void initData() {
        centerText.setText(R.string.local_update_text1);
        showProgressDialog("正在扫描数据..", true);
        getDataFromLocal();
    }

    private void initListener() {
        backLast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_last:
                finishActivity();
                break;
        }
    }

    /**
     * 从本地的sdcard得到数据
     * //1.遍历sdcard,后缀名
     * //2.从内容提供者里面获取视频
     * //3.如果是6.0的系统，动态获取读取sdcard的权限
     * //4.如何获取封面
     */
    private void getDataFromLocal() {

        new Thread() {
            @Override
            public void run() {
                super.run();

                mediaItems = new ArrayList<>();
                arrBitmap = new ArrayList<>();
                ContentResolver resolver = getApplicationContext().getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard的名称
                        MediaStore.Video.Media.DURATION,//视频总时长
                        MediaStore.Video.Media.SIZE,//视频的文件大小
                        MediaStore.Video.Media.DATA,//视频的绝对地址
                        MediaStore.Video.Media.ARTIST,//歌曲的演唱者

                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {

                        MediaItem mediaItem = new MediaItem();

                        String name = cursor.getString(0);//视频的名称
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频的时长

                        if (duration < 600000 && duration > 0){//10min
                            mediaItem.setDuration(duration);
                        }else {
                            continue;
                        }

                        long size = cursor.getLong(2);//视频的文件大小

                        if (size < 100 * 1024 * 1024 && size > 0){//100M
                            mediaItem.setSize(size);
                        }else {
                            continue;
                        }

                        String data = cursor.getString(3);//视频的播放地址
                        //data='/storage/emulated/0/quchang/compose/清唱20171027084715.mp4'
                        // data='/storage/emulated/0/youshixiu/rectools/录屏专家171101181138.mp4'
                        //data='/storage/emulated/0/相机/video_20171101_201648.mp4
                        //排除本身软件录制
                        String file = MyFileUtil.getAppDir().toString();
                        LogUtils.w("mediaItems", "file====" + file);
                        LogUtils.w("mediaItems", "file1====" + data.contains(file));
                        LogUtils.w("mediaItems", "file2====" + data.startsWith(file));
                        LogUtils.w("mediaItems", "file3====" + data);


                        if (!data.contains(file) && !data.contains("MagicCamera_test.mp4")) {
                            mediaItem.setData(data);
                        } else {
                            continue;
                        }

                        String artist = cursor.getString(4);//艺术家
                        mediaItem.setArtist(artist);

                        mediaItems.add(mediaItem);

                        //获取视屏压缩图
                        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth();
                        int height = wm.getDefaultDisplay().getHeight();
                        width = (width - DisplayUtil.dip2px(getApplicationContext(), 10)) / 4;
                        Bitmap bitmap = getVideoThumbnail(mediaItem.getData(), width, width, MediaStore.Images.Thumbnails.MICRO_KIND);
//                        mediaItem.setBitmap(bitmap);
                        arrBitmap.add(bitmap);

                    }

                    cursor.close();


                }


                //Handler发消息
                handler.sendEmptyMessage(10);


            }
        }.start();

    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    private Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
//        System.out.println("w" + bitmap.getWidth());
//        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}
