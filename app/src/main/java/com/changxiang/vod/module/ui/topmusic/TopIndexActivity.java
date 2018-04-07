//package com.changxiang.vod.module.ui.topmusic;
//
//import android.content.Intent;
//import android.graphics.Rect;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.ImageLoader;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.utils.SharedPrefManager;
//import com.quchangkeji.tosing.common.view.WrapListView;
//import com.quchangkeji.tosing.module.engine.JsonParserFirst;
//import com.quchangkeji.tosing.module.entry.HotSong;
//import com.quchangkeji.tosing.module.entry.TopMusic;
//import com.quchangkeji.tosing.module.entry.TypeTop;
//import com.quchangkeji.tosing.module.musicInfo.DisplayUtil;
//import com.quchangkeji.tosing.module.ui.HomeActivity;
//import com.quchangkeji.tosing.module.ui.base.BaseMusicActivity;
//import com.quchangkeji.tosing.module.ui.topmusic.adapter.MainListAdapter;
//import com.quchangkeji.tosing.module.ui.topmusic.adapter.TypeAdapter;
//import com.quchangkeji.tosing.module.ui.topmusic.net.TopMusicNet;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
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
//public class TopIndexActivity extends BaseMusicActivity implements View.OnClickListener, MyItemClickListener {
//    private LinearLayout relativeLayout;//总布局
//    //返回，主页,榜单封面和关闭音乐悬窗
//    private ImageView ivBack, ivHome, ivCover;
//    //主打榜单
//    private RecyclerView recyclerView;
//    //榜单封面对应的文字,页面顶部标题文字
//    private TextView tvText, tvTop;
//    //分类排行
//    private WrapListView listView;
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;
//    private String typeCode;//音乐分类类型
//    private List<TopMusic> topMusics = new ArrayList<>();//主打榜单集合
//    private List<TypeTop> typeTops = new ArrayList<>();//分类榜单集合
//    private MainListAdapter adapter;//主打榜单适配器
//
//    @Override
//    public void handMsg(Message msg) {
//        switch (msg.what) {
//            case 1:
//                adapter = new MainListAdapter(topMusics, getApplicationContext());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                adapter.setOnItemClickListener(TopIndexActivity.this);
//                //华语金曲榜
//                TypeTop hyjq = typeTops.get(0);
//                String typeName = hyjq.getTypename();
//                typeCode = hyjq.getTypecode();
//                List<HotSong> lisBean = hyjq.getList();
//                HotSong bean = lisBean.get(0);
//                String songName = bean.getName();
//                String singerName = bean.getSingerName();
//                String imgCover = bean.getImgAlbumUrl();
//                tvText.setText(typeName + "TOP1|" + songName + "-" + singerName);
//                WindowManager wm = TopIndexActivity.this.getWindowManager();
//                int width = wm.getDefaultDisplay().getWidth();
//                RelativeLayout.LayoutParams ivCoverLayoutParamsparams = (RelativeLayout.LayoutParams) ivCover.getLayoutParams();
//                ivCoverLayoutParamsparams.height = width * 9 / 16;
//                ivCover.setLayoutParams(ivCoverLayoutParamsparams);
//                ImageLoader.getImageViewLoad(ivCover, imgCover, R.drawable.default_icon);
//                //ListView自适应高度
//                TypeAdapter typeAdapter = new TypeAdapter(typeTops, getApplicationContext());
//                listView.setAdapter(typeAdapter);
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
//                break;
//        }
//    }
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_top_music);
//        initView();
//        initData();
//        initEvent();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }
//
//
//    private void initView() {
//        relativeLayout = (LinearLayout) findViewById(R.id.activity_type);
//        ivBack = (ImageView) findViewById(R.id.back_last);
//        ivHome = (ImageView) findViewById(R.id.back_next);
//        tvTop = (TextView) findViewById(R.id.center_text);
//        tvTop.setText(R.string.choose_top);
//        ivHome.setVisibility(View.VISIBLE);
//        ivHome.setImageResource(R.mipmap.back_home);
//
//        ivCover = (ImageView) findViewById(R.id.top_choose_ivCover);
//        /*floatingWindow = (RelativeLayout) findViewById( R.id.top_choose_floating );
//        tvSong = (TextView) findViewById( R.id.top_choose_tvSong );
//        pb = (ProgressBar) findViewById( R.id.top_choose_pb );
//        tvDuration = (TextView) findViewById( R.id.top_choose_tvDuration );
//        ivClose = (ImageView) findViewById( R.id.top_choose_ivClose );
//        ivRhythm = (ImageView) findViewById( R.id.top_choose_ivRhythm );*/
//        recyclerView = (RecyclerView) findViewById(R.id.top_choose_rv);
//        tvText = (TextView) findViewById(R.id.top_choose_tvText);
//        listView = (WrapListView) findViewById(R.id.top_choose_lv);
//    }
//
//    private void initData() {
//        /*myBD = new Mybroad();
//        //注册广播接收器
//        zhuce();*/
//        //请求数据
//        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this, 10)));//添加分隔线
//
//        TopMusicNet.api_TopChoose(this, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                LogUtils.sysout("数据请求失败");
//                String data = SharedPrefManager.getInstance().getCacheApitopmusic();
//                if (data != null && !data.equals("")) {
////TODO
//                    try {
//                        JSONObject root = new JSONObject(data);
//                        JSONObject data1 = root.getJSONObject("data");
//                        JSONArray zdbd = data1.getJSONArray("zdbd");
//                        JSONArray flbd = data1.getJSONArray("flbd");
//                        LogUtils.w("TAG", "zdbd.size()" + zdbd.length() + ",==" + zdbd.getString(0));
//                        LogUtils.w("TAG", "flbd.size()" + flbd.length() + ",==" + flbd.getString(0));
//                        topMusics = TopMusic.arrayTopMusicFromData(zdbd.toString());
//                        typeTops = TypeTop.arrayTypeTopFromData(flbd.toString());
//                        //SharedPrefManager.getInstance().cacheApitopmusic( data );
//                        handler.sendEmptyMessage(1);
//
//                    } catch (JSONException en) {
//                        en.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();
//                LogUtils.w("TAG", "榜单点歌数据：" + data);
//                int code = JsonParserFirst.getRetCode(data);
//                if (code == 0) {
//                    try {
//                        JSONObject root = new JSONObject(data);
//                        JSONObject data1 = root.getJSONObject("data");
//                        JSONArray zdbd = data1.getJSONArray("zdbd");
//                        JSONArray flbd = data1.getJSONArray("flbd");
//                        LogUtils.w("TAG", "zdbd.size()" + zdbd.length() + ",==" + zdbd.getString(0));
//                        LogUtils.w("TAG", "flbd.size()" + flbd.length() + ",==" + flbd.getString(0));
//                        topMusics = TopMusic.arrayTopMusicFromData(zdbd.toString());
//                        typeTops = TypeTop.arrayTypeTopFromData(flbd.toString());
//
//                        //TODO kai
//                        SharedPrefManager.getInstance().cacheApitopmusic(data);
//                        handler.sendEmptyMessage(1);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } else {
//
//                    String dataT = SharedPrefManager.getInstance().getCacheApitopmusic();
//                    if (dataT != null && !dataT.equals("")) {
////TODO
//                        try {
//                            JSONObject root = new JSONObject(dataT);
//                            JSONObject data1 = root.getJSONObject("data");
//                            JSONArray zdbd = data1.getJSONArray("zdbd");
//                            JSONArray flbd = data1.getJSONArray("flbd");
//                            LogUtils.w("TAG", "zdbd.size()" + zdbd.length() + ",==" + zdbd.getString(0));
//                            LogUtils.w("TAG", "flbd.size()" + flbd.length() + ",==" + flbd.getString(0));
//                            topMusics = TopMusic.arrayTopMusicFromData(zdbd.toString());
//                            typeTops = TypeTop.arrayTypeTopFromData(flbd.toString());
//                            //SharedPrefManager.getInstance().cacheApitopmusic( data );
//                            handler.sendEmptyMessage(1);
//
//                        } catch (JSONException en) {
//                            en.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    private void initEvent() {
//        ivBack.setOnClickListener(this);
//        ivHome.setOnClickListener(this);
//        ivCover.setOnClickListener(this);
//        tvText.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        //Intent intent=null;
//        switch (id) {
//            case R.id.back_last:
//                finishActivity();
//                break;
//            case R.id.back_next:
//                //返回主页
//                Intent home = new Intent(TopIndexActivity.this, HomeActivity.class);
//                startActivity(home);
//                break;
//            case R.id.top_choose_ivCover:
//            case R.id.top_choose_tvText:
//                //1.21榜单歌曲列表
//                Intent intent = new Intent(TopIndexActivity.this, TopListActivity.class);
//                intent.putExtra("typeId", typeCode);
//                startActivity(intent);
//                break;
//        }
//    }
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("TopIndex Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }
//
//    //RecycleView item点击事件
//    @Override
//    public void onItemClick(View view, int postion, String typeCode) {
//        Intent intent = new Intent(this, TopListActivity.class);
//        //传递数据
//        //String typeid=topMusic.getTypecode();
//        intent.putExtra("typeId", typeCode);
//        startActivity(intent);
//    }
//
//    //RecyclerView的间距
//    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int space;
//
//        public SpaceItemDecoration(int space) {
//            this.space = space;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            //不是第一个的格子都设一个左边和底部的间距
//            outRect.left = space;
//            outRect.right = space;
//            //outRect.right=space;
//            outRect.bottom = space;
//            //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
//            if (parent.getChildLayoutPosition(view) % 2 == 0) {
//                outRect.right = 0;
//            }
//            if (parent.getChildLayoutPosition(view) % 2 == 1) {
//
//            }
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //unregisterReceiver(myBD);
//    }
//}
