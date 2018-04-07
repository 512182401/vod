package com.changxiang.vod.module.img_select;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.ImageLoader;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.engine.JsonParserFirst;
import com.changxiang.vod.module.img_select.adapter.ImageModel;
import com.changxiang.vod.module.img_select.multi.MatrixImageView;
import com.changxiang.vod.module.ui.base.BaseActivity;
import com.changxiang.vod.module.ui.personal.adapter.AdvAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//import com.quchangkeji.tosingpk.module.ui.diyVedio.net.DiyVedioNet;
//import com.quchangkeji.tosingpk.module.ui.img_select.adapter.ImageModel;
//import com.quchangkeji.tosingpk.module.ui.img_select.multi.MatrixImageView;
//import com.quchangkeji.tosingpk.module.ui.origin.adapter.AdvAdapter;

public class ImageShow extends BaseActivity implements View.OnClickListener {
    String responsemsg = null;//网络请求返回信息
    boolean ismy = false;
    int position = 0;//当前第几张图片
    private ImageModel mImageModel;
    private TextView top_text;//顶部居中显示
    private ImageView bt_right;//右边显示
    private ImageView bt_back;//返回
    private TextView right_text;//顶部右显示
    private List<ImageModel> selectList = new ArrayList<ImageModel>();//选择的照片列表

    //    ViewGroup vGroup;
    private ViewPager advPager = null;
    List<ImageView> advPics = new ArrayList<ImageView>();
    private int ImageOnFail = R.drawable.default_icon;// 下载失败时的图片名称
    private boolean isContinue = true;

    private AtomicInteger what ;


    private final Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            advPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }
    };

    @Override
    public void handMsg(Message msg) {
        Intent intent;
        switch (msg.what) {
            case -1:
//                toast( "联网登录出现网络异常错误" );
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                break;
            case 0:


//                    handler.sendEmptyMessageDelayed( 0, 1000 );
                break;
            case 1:
                toast(responsemsg);//
//                handler.sendEmptyMessageDelayed( 1, 100 );
                break;
            case 4://更新
                if (selectList != null && !selectList.equals("")) {                    // 保存缓存
//                BaseCacheUtil.setString(getActivity(), SP_KEY, data);
                    try {
                        selectList.remove(selectList.get(position-1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(selectList!=null&&!selectList.isEmpty()){
                    initViewPagerFun(selectList);
                }else{
                    finishActivity();
                }
                //

//                handler.sendEmptyMessageDelayed( 10, 100 );
                break;
            case 10://更新
                toast(responsemsg);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        getIntentParams();

        initViews();
        if (selectList != null && !selectList.equals("")) {
            initViewPagerFun(selectList);// 没有缓存,只能展示一张轮播图
        }
    }

    private void getIntentParams() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        position = intent.getIntExtra("position", 1);
        ismy = intent.getBooleanExtra("ismy", false);
        List<ImageModel> list = (List<ImageModel>) intent.getSerializableExtra("selectList");
        if (list == null || list.isEmpty()) {
            return;
        }
        what = new AtomicInteger(position-1);
        selectList.addAll(list);
    }

    protected void initViews() {

        bt_back = (ImageView) findViewById(R.id.back_last);
        top_text = (TextView) findViewById(R.id.center_text);
        bt_right = (ImageView) findViewById(R.id.back_next);
        right_text = (TextView) findViewById(R.id.back_next_left);

        advPager = (ViewPager) findViewById(R.id.vp_banner);
        advPager.setAdapter(new AdvAdapter(advPics));

        if (ismy) {
            right_text.setVisibility(View.VISIBLE);
        } else {
            right_text.setVisibility(View.GONE);
        }

        right_text.setText("删除");
//        top_text.setText("趣唱相册");
//        if (selectList != null && selectList.size() > 0) {
//            top_text.setText((position -1) + "/" + selectList.size());
//        }
//        try {
//            advPager.setCurrentItem(position);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        bt_right.setVisibility(View.GONE);
        bt_back.setOnClickListener(this);
        right_text.setOnClickListener(this);

    }


    private void initViewPagerFun(List<ImageModel> list) {
//        vGroup.removeAllViews();// 清楚轮播图标记位
        advPics.clear();// 清空list中的View
        if (list == null || list.isEmpty()) {
//            ImageView defaultImage = (ImageView) LayoutInflater.from(this).inflate(R.layout.playimagelayout, null);
            LinearLayout mLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.albumimagelayout, null);
            MatrixImageView defaultImage = (MatrixImageView) mLinearLayout.findViewById(R.id.image);
            defaultImage.setBackgroundResource(R.drawable.tv_mv);
            advPics.add(defaultImage);
            advPager.removeAllViews();
            advPager.setAdapter(new AdvAdapter(advPics));
            return;
        }
        int size = list.size();

        for (int i = 0; i < size; i++) {
            ImageView img = (ImageView) LayoutInflater.from(this).inflate(R.layout.playimagelayout, null);
            img.setTag(list.get(i));
            setBannerClick(img);
            advPics.add(img);

//            LinearLayout mLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.albumimagelayout, null);
//            MatrixImageView defaultImage = (MatrixImageView)mLinearLayout.findViewById(R.id.image);
////            defaultImage.setBackgroundResource(R.mipmap.origin_banner);
//            defaultImage.setTag(list.get(i));
//            setBannerClick(defaultImage);
//            advPics.add(defaultImage);
            //TODO
//            advPics.get( i ).setImageResource( R.mipmap.origin_banner );
            ImageLoader.getImageViewLoad(advPics.get(i), list.get(i).getImageUrl(), ImageOnFail);

        }
        advPager.removeAllViews();
        advPager.setAdapter(new AdvAdapter(advPics));

        advPager.setOnPageChangeListener(new GuidePageChangeListener());

        advPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }
        });
        if (selectList != null && selectList.size() > 0) {
//            position = what.get();
//            if(position<=1) {
//                top_text.setText( 1 + "/" + selectList.size());
//            }else{
            if(position<=selectList.size()){

            }else{
                position = selectList.size();
            }
                top_text.setText((position) + "/" + selectList.size());
//            }
        }
//        what.set(position);
//        what.incrementAndGet();
        viewHandler.sendEmptyMessage(what.get());


    }

    private void setBannerClick(ImageView img) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//点击图片时间操作：
            }
        });
    }


    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            what.getAndSet(arg0);
            position = arg0+1;
            if (selectList != null && selectList.size() > 0) {
                if(position<=selectList.size()){

                }else{
                    position = selectList.size();
                }
                top_text.setText((position) + "/" + selectList.size());
            }
        }
    }

    /**
     * 删除数据
     */
    public void delectAlbumData() {
        String delectableid = null;
        if (position <= selectList.size()) {
            try{
                if(position>=0) {
                    delectableid = selectList.get(position-1).getId();
                }else{
                    delectableid = selectList.get(0).getId();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            position = selectList.size()-1;
            delectableid = selectList.get(position).getId();
        }

        if (delectableid != null && !delectableid.equals("")) {

        } else {
            toast("获取不到相关数据");
            return;
        }
//        showProgressDialog("正在删除数据..", true);
//        DiyVedioNet.api_delectAlbum(this, delectableid, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
////                toast( "请求失败" );
//                LogUtils.sysout("联网登录出现网络异常错误！");
//                handler.sendEmptyMessageDelayed(-1, 1000);
//                closeProgressDialog();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                closeProgressDialog();
////TODO
//                String data = response.body().string();
//                LogUtils.sysout("登录返回结果:" + data);
//                int code = JsonParserFirst.getRetCode(data);
//
//                if (code == 0) {
//                    handler.sendEmptyMessageDelayed(4, 100);//删除成功，刷新数据
////                    albumList = Album.objectFromData(data, "data");
//                } else {
//                    responsemsg = JsonParserFirst.getRetMsg(data);
////                    toast( meg );
//                    handler.sendEmptyMessageDelayed(10, 100);
//
//                }
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_next_left://删除
                delectAlbumData();
                break;
            case R.id.back_last://
                finishActivity();
                break;
        }

    }

}
