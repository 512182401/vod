package com.changxiang.vod.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.changxiang.vod.common.utils.DensityUtil;
import com.changxiang.vod.common.utils.LogUtils;


/**
 * 滑动顶部变色
 * Created by 15976 on 2017/3/2.
 */

public class ScrollChangeScrollView extends ScrollView {

    private View mTopView;
    private boolean shouldSlowlyChange = true;
    private IOnScrollListener mListener;

    public ScrollChangeScrollView(Context context) {
        super(context);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void scrollTo(int x, int y) {
        //这是为了修复noScrllListView嵌套在srcollview时就自动滑动到noscrolllistview的顶部的bug，不影响使用
        if (x == 0 && y == 0 || y <= 0) {
            super.scrollTo(x, y);
        }
    }

    public void setListener(IOnScrollListener listener){
        this.mListener = listener;
    }

    public void setShouldSlowlyChange(boolean slowlyChange) {
        this.shouldSlowlyChange = slowlyChange;
    }

    public void setupTitleView (View view) {
        this.mTopView = view;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        LogUtils.sysout("height:"+mTopView.getMeasuredHeight()+",getTop:"+mTopView.getTop());
        if (scrollY>mTopView.getMeasuredHeight()- DensityUtil.dip2px(getContext(),50)){
            mListener.onScroll(1,scrollY);
        }else {
            mListener.onScroll(2,scrollY);
        }
        /*if (scrollY >= mByWhichView.getTop() + mByWhichView.getMeasuredHeight()) {
            mTitleView.setBackgroundColor(Color.BLACK);
        } else if (scrollY>=0) {
            if (!shouldSlowlyChange) {
                mTitleView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                float persent = scrollY * 1f / (mByWhichView.getTop() + mByWhichView.getMeasuredHeight());
                int alpha = (int) (255 * persent);
                int color = Color.argb(alpha,0,0,0);
                mTitleView.setBackgroundColor(color);
            }
        }
        if (mListener!=null) {
            mListener.onScroll(scrollX, scrollY);
        }*/
    }

    public interface IOnScrollListener{
        void onScroll(int tag, int scrollY);
    }
}
