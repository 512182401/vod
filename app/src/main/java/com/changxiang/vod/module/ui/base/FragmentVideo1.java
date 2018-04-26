package com.changxiang.vod.module.ui.base;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.ImageLoader;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.view.LrcView;
import com.changxiang.vod.module.base.BaseFragment;
import com.changxiang.vod.module.entry.Banner;
import com.changxiang.vod.module.ui.StoreWebActivity;


/**
 * 首页轮播图中的Fragment
 * Created by 15976 on 2016/11/25.
 */

public class FragmentVideo1 extends BaseFragment {
    Banner homeBean;
    ImageView imageView;
    String imgUrl;
    LrcView lrcView;
    IOnItemClickListener iOnItemClickListener;
    int position;
    private int bg[] = {R.mipmap.origin_detail_01, R.mipmap.origin_detail_02, R.mipmap.origin_detail_03, R.mipmap.origin_detail_05, R.mipmap.origin_detail_06,
            R.mipmap.origin_detail_07, R.mipmap.origin_detail_08, R.mipmap.origin_detail_09, R.mipmap.origin_detail_10, R.mipmap.origin_detail_04};

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public LrcView getLrcView() {
        return lrcView;
    }

    public void setLrcView(LrcView lrcView) {
        this.lrcView = lrcView;
    }

    @Override
    public void handMsg(Message msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video1;
    }

    @Override
    protected void initViews() {
        lrcView = (LrcView) root.findViewById(R.id.fragment_lrcView);
        imageView = (ImageView) root.findViewById(R.id.fragment_video_iv);

        imageView.setBackgroundResource(Integer.parseInt(imgUrl));
//        if (imgUrl != null && imgUrl.equals("")) {
////            ImageLoader.getImageViewLoad(imageView, imgUrl, R.drawable.tv_mv);
//            imageView.setBackgroundResource(R.mipmap.origin_detail_01);
//        } else if (imgUrl != null && !imgUrl.equals("")) {
//            ImageLoader.getImageViewLoad(imageView, imgUrl, R.drawable.tv_mv);
//
//        }
    }

    @Override
    protected void initEvents() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClick();
                }

                LogUtils.sysout("777777777777777777777");
                Intent newIntent01;
                if (homeBean == null) {
//                    newIntent01 = new Intent(getActivity(), InviteFriendActivity.class);
//                    startActivity(newIntent01);
                    return;
                } else {
                    String url = homeBean.getUrl();

                    LogUtils.sysout("9999999999999 url = " + url);
                    if (url != null && url.length() > 0) {
//                        newIntent01 = new Intent(getActivity(), ShowWebContActivity.class);
                        if (homeBean.getTitleSts() != null && homeBean.getTitleSts().equals("1")) {
                            newIntent01 = new Intent(getActivity(), StoreWebActivity.class);
                        } else {
                            newIntent01 = new Intent(getActivity(), StoreWebActivity.class);
                        }
                        if (url != null && !url.equals("")) {
                            newIntent01.putExtra("url", url);
                            newIntent01.putExtra("title", homeBean.getName());
                            startActivity(newIntent01);
                        }
                    } else {
//                        newIntent01 = new Intent(getActivity(), InviteFriendActivity.class);
//                        startActivity(newIntent01);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;

    }

    public void setBannerItem(Banner homeBean) {
        this.homeBean = homeBean;
    }

    public interface IOnItemClickListener {
        void onItemClick();
    }

    public void setiOnItemClickListener(int position, IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
        this.position = position;
    }

    public IOnItemClickListener getiOnItemClickListener() {
        return iOnItemClickListener;
    }


}
