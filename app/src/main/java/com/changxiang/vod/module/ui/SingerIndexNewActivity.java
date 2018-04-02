package com.changxiang.vod.module.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.module.pinyinindexing.QuickindexBar;
import com.changxiang.vod.module.ui.adapter.SingerIndexAdapter;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SingerIndexNewActivity extends BaseActivity implements View.OnClickListener {
    //TODO
//    item_singer_index.xml
    private SingerIndexAdapter madapter;
    private TextView top_text;//顶部居中显示
    private ImageView bt_right;//右边显示
    private ImageView bt_back;//返回
    private ListView singer_list;//xl_singer_list
    private TwinklingRefreshLayout refreshLayout;

    private LinearLayout add;
    private LinearLayout sex;
    private LinearLayout type;
    private List<View> addviews = new ArrayList<View>();
    private List<View> sexviews = new ArrayList<View>();
    private List<View> typeviews = new ArrayList<View>();
    private String[] addstr;// 地域  1 内地 2 港台 3 日韩 4 欧美
    private String[] sexstr;
    private String[] typestr = {"全部", "流行", "摇滚", "民谣", "电子", "爵士", "嘻哈", "轻音乐", "R&B", "其他"};
    boolean islast = false;
    private int rowWidth = 0;
    private String langValue = "0";
    private String sexValue = "0";
    private String styleValue = "0";
    private int curPage = 1;
    private boolean first = true;

    private LinearLayout ll_no_data;//no_data_show
    private QuickindexBar slideBar;
    private TextView tv_zimu;

    private String responsemsg = "请求数据为空";//网络请求返回信息

    @Override
    public void handMsg(Message msg) {
        Intent intent;
        switch (msg.what) {
            case -1:
                toast(getString(R.string.get_no_data));
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                break;
            case 0:
//                toast( "成功" );
//                    handler.sendEmptyMessageDelayed( 0, 1000 );

                initData();
                break;
            case 1:
                toast(responsemsg);//
                refreshLayout.setVisibility(View.GONE);
                ll_no_data.setVisibility(View.VISIBLE);
//                handler.sendEmptyMessageDelayed( 1, 100 );
                break;
            case 2://刷新成功，刷新界面
                refreshLayout.setVisibility(View.VISIBLE);
                ll_no_data.setVisibility(View.GONE);
//                madapter.setDataList( hotTJ.getResults() );
//                handler.sendEmptyMessageDelayed( 2, 100 );
                break;
            case 3://加载更多成功，刷新界面
//                madapter.addDataList( hotTJ.getResults() );
//                handler.sendEmptyMessageDelayed( 3, 100 );
                break;

            case 123://权限
//                updataimage();//修改图片，以及上传
//                handler.sendEmptyMessageDelayed( 123, 100 );
                break;

            case 10://提交成功（）
//                handler.sendEmptyMessageDelayed( 10, 100 );
//                club_image.setImageBitmap( newbitmap );
                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_index_new);
        rowWidth = this.getResources().getDisplayMetrics().widthPixels / 7;
        initView();
        initData();
        curPage = 1;
        getNewData(curPage);
    }

    private void initView() {

        addstr = getResources().getStringArray(R.array.array_location);
        sexstr = getResources().getStringArray(R.array.array_sex);
        bt_back = (ImageView) findViewById(R.id.back_last);
        top_text = (TextView) findViewById(R.id.center_text);
        bt_right = (ImageView) findViewById(R.id.back_next);
        singer_list = (ListView) findViewById(R.id.xl_singer_list);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);


        ll_no_data = (LinearLayout) findViewById(R.id.ll_no_data_show);//无数据
        add = (LinearLayout) findViewById(R.id.ll_add);
        sex = (LinearLayout) findViewById(R.id.ll_sex);
        type = (LinearLayout) findViewById(R.id.ll_type);

        tv_zimu = (TextView) findViewById(R.id.tv_zimu);
        slideBar = (QuickindexBar) findViewById(R.id.slideBar);
        slideBar.setOnSlideTouchListener(new QuickindexBar.OnSlideTouchListener() {

            @Override
            public void onBack(String str) {

//                showZimu(str);

//                Intent intent = new Intent(SingerIndexNewActivity.this,SingerPinYinActivity.class);
//                intent.putExtra("pinyin",str);
//                startActivity(intent);

            }
        });
        top_text.setText(R.string.choose_singer);
        bt_right.setVisibility(View.VISIBLE);
        bt_right.setImageResource(R.mipmap.search_white);
        setAddHoriListView();
        setSexHoriListView();
//        setTypeHoriListView();

//        hotTJ = new HotTJ();
        bt_back.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        madapter = new SingerIndexAdapter(this);
        //TODO
        singer_list.setAdapter(madapter);
//        singer_list.setPullLoadEnable( true );// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
//        singer_list.setXListViewListener( new XListView.IXListViewListener() {
//
//            @Override
//            public void onRefresh() {//刷新
//                clear();
//                curPage = 1;
//                getNewData( curPage );
//            }
//
//            @Override
//            public void onLoadMore() {//加载更多
//
//                if (mdapter.getCount() < 5) {
//                    stopLoading();
//                } else {
//                    //TODO 加载更多
//                    curPage++;
//                    getNewData( curPage );
//                }
//            }
//        } );

        singer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                //取得歌手信息
//                HotTJ.ResultsBean singer =  madapter.getItemData(position);
//                //根据不同信息跳转到不同的界面
//                Intent intent = new Intent( SingerIndexNewActivity.this, SingerListActivity.class );
//                intent.putExtra("singerid", singer.getId());
//                intent.putExtra("imgCover", singer.getImgCover());
//                intent.putExtra("singername", singer.getName());
////                holder.songerinfo.setText("MP3("+persons.get(arg0).getmSingerPY().getMp3num()+"首)/MV("+persons.get(arg0).getmSingerPY().getMvnum()+"首)");
//                intent.putExtra("songcount", "MP3("+singer.getMp3num()+"首)/MV("+singer.getMvnum()+"首)");
//                startActivity( intent );
            }
        });

        refreshLayout.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
            //x下拉刷新
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        curPage = 1;
                        getNewData(curPage);//首次请求数据
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
                        getNewData(curPage);//首次请求数据
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });


    }

    // 显示在屏幕中间的字母
    private void showZimu(String string) {

        tv_zimu.setVisibility(View.VISIBLE);
        tv_zimu.setText(string);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                tv_zimu.setVisibility(View.GONE);
            }
        }, 1500);
    }

    /**
     * 停止刷新，
     */
//    private void stopLoading() {
//        singer_list.stopLoadMore();
//    }
//
//    private void stopRefreshing() {
//        singer_list.stopRefresh();
//    }

    //清空数据
    private void clear() {
        //TODO
        if (madapter != null) {
            madapter.clear();
        }
    }

    //TODO  取得热门推荐
    private void initData() {

    }


    private void setAddHoriListView() {
        int size = addstr.length;
        addviews.clear();
        add.removeAllViews();
        for (int i = 0; i < size; i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_singer_hrzlistview_layout, null);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(rowWidth, RadioGroup.LayoutParams.MATCH_PARENT);
            item.setLayoutParams(params);
            item.setTag(i);
            Bundle b = new Bundle();
            b.putString("aid", "" + i);
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAddFocus(v);
                    langValue = finalI + "";
                    curPage = 1;
                    getNewData(curPage);
                }
            });
            RadioButton txt = AppUtil.findViewById(item, R.id.txt);
            txt.setText(addstr[i]);
            if (i == 0) {
                txt.setTextColor(Color.parseColor("#ff5f5f"));
            }
            addviews.add(item);
            add.addView(item);
        }
    }


    private void setSexHoriListView() {
        int size = sexstr.length;
        sexviews.clear();
        sex.removeAllViews();
        for (int i = 0; i < size; i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_singer_hrzlistview_layout, null);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(rowWidth, RadioGroup.LayoutParams.MATCH_PARENT);
            item.setLayoutParams(params);
            item.setTag(i);
            Bundle b = new Bundle();
            b.putString("sid", "" + i);
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSexFocus(v);
                    sexValue = finalI + "";
                    curPage = 1;
                    getNewData(curPage);
                }
            });
            RadioButton txt = AppUtil.findViewById(item, R.id.txt);
            txt.setText(sexstr[i]);
            if (i == 0) {
                txt.setTextColor(Color.parseColor("#ff5f5f"));
            }
            sexviews.add(item);
            sex.addView(item);
        }
    }


    private void setTypeHoriListView() {
        int size = typestr.length;
        typeviews.clear();
        type.removeAllViews();
        for (int i = 0; i < size; i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_singer_hrzlistview_layout, null);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(rowWidth, RadioGroup.LayoutParams.MATCH_PARENT);
            item.setLayoutParams(params);
            item.setTag(i);
            Bundle b = new Bundle();
            b.putString("tid", "" + i);
//            ask.setArguments(b);
//            frgList.decrease(ask);
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTypeFocus(v);
                    styleValue = finalI + "";
                    curPage = 1;
                    getNewData(curPage);
                }
            });
            RadioButton txt = AppUtil.findViewById(item, R.id.txt);
            txt.setText(typestr[i]);
            if (i == 0) {
                txt.setTextColor(Color.parseColor("#ff5f5f"));
            }
            typeviews.add(item);
            type.addView(item);
        }
    }


    private void setAddFocus(View item) {
        for (View view : addviews) {
            TextView txt = (TextView) view.findViewById(R.id.txt);
            if (view == item) {
                txt.setTextColor(Color.parseColor("#ff5f5f"));
            } else {
                txt.setTextColor(Color.parseColor("#2e2e2e"));
            }
        }
    }

    private void setSexFocus(View item) {
        for (View view : sexviews) {
            TextView txt = (TextView) view.findViewById(R.id.txt);
            if (view == item) {
                txt.setTextColor(Color.parseColor("#ff5f5f"));
            } else {
                txt.setTextColor(Color.parseColor("#2e2e2e"));
            }
        }
    }

    private void setTypeFocus(View item) {
        for (View view : typeviews) {
            TextView txt = (TextView) view.findViewById(R.id.txt);
            if (view == item) {
                txt.setTextColor(Color.parseColor("#ff5f5f"));
            } else {
                txt.setTextColor(Color.parseColor("#2e2e2e"));
            }
        }
    }

    //TODO 取得数据
    private void getNewData(final int curPage1) {
//
////        SingUtil.api_hotTJ2( this, "" + langValue, "" + sexValue, "" + curPage, new Callback() {
//        SingUtil.api_hotTJ2( this, "" + langValue, "" + sexValue, "" + styleValue, "" + curPage, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                closeProgressDialog();
//                LogUtils.sysout( "联网登录出现网络异常错误！" );
//                handler.sendEmptyMessageDelayed( -1, 1000 );
////                toast( "请求失败" );
//
//
//                if (curPage < 2) {
//                    hotTJ = new HotTJ();
//                    String data = SharedPrefManager.getInstance().getCacheApisingermusic();
//                    if (data != null && !data.equals( "" )) {
//                        hotTJ = hotTJ.objectFromData( data, "data" );
//                        if (hotTJ != null) {
////                            LogUtils.sysout( "nearperson=" + nearperson );
//                            handler.sendEmptyMessageDelayed( 2, 100 );//刷新
//                        } else {
//                            LogUtils.sysout( "没有保存数据" );
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                closeProgressDialog();
//
//                String data = response.body().string();
////                toast( show );
////                int code = 0;
//                LogUtils.sysout("歌星点歌返回结果:"+data);
////                showProgressDialog("歌星点歌:",true);
////                int code = SingerJsonParser.getRetCode(data);
//                int code = JsonParserFirst.getRetCode( data );
//                if (code == 0) {
//                    hotTJ = new HotTJ();
////                    hotTJ = SingerJsonParser.parser_HotTJ(data);
//                    hotTJ = HotTJ.objectFromData( data, "data" );
//                    if (hotTJ != null && !hotTJ.equals( "" )) {
////                        List<Singer> list = hotTJ.getResults();
////                        if (list == null || list.isEmpty()) {
////                            return;
////                        }
//                        if(hotTJ.isLast()){
//                            islast = true;
//                        }else{
//                            islast = false;
//                        }
//                        if (curPage < 2) {
//                            if (hotTJ.getTotal() == 0) {//数据为空
//                                handler.sendEmptyMessageDelayed( 1, 100 );
//                            } else {
//                                handler.sendEmptyMessageDelayed( 2, 100 );//刷新
//                                SharedPrefManager.getInstance().cacheApisingermusic( data );//保存数据
//                                hotTJall = hotTJ;
//                            }
//
//                            //TODO  保存数据到本地
//                        } else {
//                            try {
//                               curPage = hotTJ.getCurPage();
//                                //TODO
////                               hotTJall.getResults().add( (HotTJ.ResultsBean) hotTJ.getResults() );
////                                singerscluball.getResults().add( (SingerClub.ResultsBean) singersclub.getResults() );
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
////
//                            handler.sendEmptyMessageDelayed( 3, 100 );//加载更多
//                        }
////                        if (hotTJ.getFirst().equals( "true" )) {//判断是否为首页
////                        cacheFirstPageAll(data);//缓存第一页数据
////                            mdapter.setDataList( list );
////                            runOnUiThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    mdapter.notifyDataSetChanged();
////                                }
////                            });
////                        } else {
////                            mdapter.addDataList( list );
////                            runOnUiThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    mdapter.notifyDataSetChanged();
////                                }
////                            });
////                        }
//                    }
//                } else {
//                    closeProgressDialog();
////                    String meg = SingerJsonParser.getRetMsg( response.body().toString() );
//                    responsemsg = JsonParserFirst.getRetMsg( response.body().toString() );
////                    toast(meg);
//                }
//
//            }
//
//        } );
//
    }


    private void cacheFirstPageAll(String result) {
//        if((mKeyword==null||mKeyword.equals(""))&&tid==0){//只缓存第一页数据;
//            SharedPrefManager.getInstance().cacheQXR_firstPage(mCachekey, result);
//        }
    }

//    private void stopLoadData() {
////        TODO
//
//        if (first) {
//            stopRefreshing();
//        } else {
//            stopLoading();
//        }
//        stopLoading();
//    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_next://主页
////                toast( "跳转到搜索界面" );
////                intent = new Intent( SingerIndexActivity.this, SingerListActivity.class );
//                intent = new Intent(SingerIndexNewActivity.this, SearchActivity.class);
//                intent.putExtra("search", "1");
//                startActivity(intent);
                break;
            case R.id.back_last://
                finishActivity();
                break;
        }
    }
}
