package com.changxiang.vod.module.img_select;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.changxiang.vod.module.img_select.adapter.ImageLoadHelper;

import java.util.ArrayList;
import java.util.List;

//import com.quchangkeji.tosing.module.ui.img_select.adapter.ImageLoadHelper;

//import com.gwkj.haohaoxiuchesf.module.ui.img_select.adapter.ImageLoadHelper;


public abstract class AddViewHolderAdapter<DATA, VIEWHOLDER> extends BaseAdapter {

    protected Context mCtx;
    protected LayoutInflater mInflater;
    protected ImageLoadHelper mImageLoader;

    protected List<DATA> mDatas;

    public AddViewHolderAdapter(Context ctx) {
        mCtx = ctx;
        mInflater = LayoutInflater.from(mCtx);
    }

    public void setData(List<DATA> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<DATA>();
        }
        mDatas.clear();
        mDatas.addAll(datas);
    }

    public void addData(DATA data) {
        if (mDatas == null) {
            mDatas = new ArrayList<DATA>();
        }
        mDatas.add(data);
    }

    public void addData(int idx, DATA data) {
        if (mDatas == null) {
            mDatas = new ArrayList<DATA>();
        }
        mDatas.add(idx, data);
    }

    public void addData(List<DATA> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<DATA>();
        }
        mDatas.addAll(datas);
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size() + 1;
        }
        return 1;
    }

    public void removeItem(DATA model) {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mDatas.remove(model);
    }

    public void removeItem(int position) {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mDatas.remove(position);
    }

    @Override
    public DATA getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        VIEWHOLDER holder = null;
        if (view == null) {
            view = createItemView(mInflater, position);
            holder = createAndBindViewHolder(view, position);
            view.setTag(holder);
        } else {
            holder = (VIEWHOLDER) view.getTag();
        }

//        if(position==0&&getCount()==1){
//            addDataToView(holder, null, position);
//        }else if(position==20&&getCount()==21){
//            addDataToView(holder, null, position);
//        }else{
//            if(position<=mDatas.size()) {
//                DATA data = mDatas.get(position);
//                bindDataToView(holder, data, position);
//            }
//
//        }



        if(mDatas == null||(mDatas != null&&mDatas.isEmpty())){//没有数据的时候

            addDataToView(holder, null, position);

        }else if (mDatas != null && mDatas.size()>0&&mDatas.size()<20) {//20

            if(position<mDatas.size()) {
                DATA data = mDatas.get(position);
                bindDataToView(holder, data, position);
            }else if(position==mDatas.size()){
                addDataToView(holder, null, position);
            }

        }




        return view;
    }

    public void loadImage(String imageUrl, int holdImageViewResId,
                          android.widget.ImageView imageView) {
        imageView.setImageResource(holdImageViewResId);
        if (mImageLoader == null) {
            mImageLoader = new ImageLoadHelper(mCtx);
        }
        mImageLoader.loadImage(imageUrl, imageView);

    }

    public abstract View createItemView(LayoutInflater inflater, int position);

    public abstract VIEWHOLDER createAndBindViewHolder(View view, int position);

    public abstract void bindDataToView(VIEWHOLDER holder, DATA data, int position);

    public abstract void addDataToView(VIEWHOLDER holder, DATA data, int position);

}
