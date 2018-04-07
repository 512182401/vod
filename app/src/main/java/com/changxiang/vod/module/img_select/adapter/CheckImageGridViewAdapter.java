package com.changxiang.vod.module.img_select.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.img_select.AddViewHolderAdapter;



public class CheckImageGridViewAdapter extends AddViewHolderAdapter<ImageModel, CheckImageGridViewAdapter.ViewHolder> {
    //	private void
    int width = 0;
    boolean isedit = true;

    public CheckImageGridViewAdapter(Context ctx) {
        super(ctx);
        width = (ctx.getResources().getDisplayMetrics().widthPixels - 10) / 2;
    }

    public static class ViewHolder {
        public ImageView image;
        public View touchView;
        public ImageView isSelected;
        public ImageView isEdit;
    }

    public void setImageEdit(boolean isedit) {
        this.isedit = isedit;
        notifyDataSetChanged();
    }

    @Override
    public View createItemView(LayoutInflater inflater, int position) {
        return inflater.inflate(R.layout.check_image_gridview_item, null);
    }

    @Override
    public ViewHolder createAndBindViewHolder(View view, int position) {
        ViewHolder holder = new ViewHolder();
        view.setLayoutParams(new AbsListView.LayoutParams(width, width * 9 / 16));
//		view.setLayoutParams(new AbsListView.LayoutParams(width, width));
        holder.image = AppUtil.findViewById(view, R.id.image);
        holder.touchView = AppUtil.findViewById(view, R.id.touchview);
        holder.isSelected = AppUtil.findViewById(view, R.id.selected_flag);
        holder.isEdit = AppUtil.findViewById(view, R.id.edit_image_flag);
        return holder;
    }

    @Override
    public void bindDataToView(final ViewHolder holder, final ImageModel data, final int position) {
//		LogUtils.sysout("bindDataToView添加旋选择的图片，当前为第"+position+"张图片");
        if (data.isedit()) {
//            LogUtils.sysout(position+" 99999999999999999999999999999"+data.getEdpath());
//            loadImage(data.getEdpath(), R.drawable.default_icon, holder.image);
            com.changxiang.vod.common.utils.ImageLoader.getDiskCacheAll("file://"+data.getEdpath());
            com.changxiang.vod.common.utils.ImageLoader.getImageViewLoad(holder.image,"file://"+data.getEdpath(), R.drawable.tv_mv);
        } else {
//            LogUtils.sysout(position+" 8888888888888888888888888888");
//            loadImage(data.getImageUrl(), R.drawable.default_icon, holder.image);
            com.changxiang.vod.common.utils.ImageLoader.getDiskCacheAll("file://"+data.getImageUrl());
            com.changxiang.vod.common.utils.ImageLoader.getImageViewLoad(holder.image,"file://"+data.getImageUrl(), R.drawable.tv_mv);
        }
        holder.image.setBackgroundResource(R.drawable.mv_bg);
        holder.isSelected.setVisibility(View.VISIBLE);
        holder.isEdit.setVisibility(View.VISIBLE);
        holder.image.setEnabled(false);
        holder.isSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSelectItemTouch == null) {
                    return;
                }
//				togleTouchView(holder, data);
                mOnSelectItemTouch.onItemtTouch(1, holder, data, position);
            }
        });

        holder.isEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSelectItemTouch == null) {
                    return;
                }
//				togleTouchView(holder, data);
                mOnSelectItemTouch.onItemtTouch(2, holder, data, position);
            }
        });


    }

    @Override
    public void addDataToView(final ViewHolder holder, ImageModel imageModel, final int position) {
//		loadImage(data.getEdpath(), R.drawable.default_icon, holder.image);
        LogUtils.sysout("addDataToView 添加增加图片按钮，当前为第" + position + "张图片");
        holder.image.setEnabled(true);
        holder.image.setBackgroundResource(R.color.white);
        holder.image.setImageResource(R.drawable.addbtn_normal);
        holder.isSelected.setVisibility(View.GONE);
        holder.isEdit.setVisibility(View.GONE);
        if (imageModel == null) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.sysout("点击了添加图片按钮");
                    mOnSelectItemTouch.onItemtTouch(3, holder, null, position);
                }
            });
        }


    }

    private void togleTouchView(ViewHolder holder, ImageModel data) {
        if (data.isSelected) {
            holder.isSelected.setImageResource(R.mipmap.diy_delect_pic);
            holder.touchView.setVisibility(View.GONE);
            data.isSelected = false;
        } else {
            holder.isSelected.setImageResource(R.mipmap.diy_delect_pic);
            holder.touchView.setVisibility(View.VISIBLE);
            data.isSelected = true;
        }
    }

    public interface OnSelectItemTouch {
        void onItemtTouch(int opt, ViewHolder holder, ImageModel data, int position);//opt:1:删除 2：编辑
    }

    OnSelectItemTouch mOnSelectItemTouch;

    public OnSelectItemTouch getOnSelectItemTouch() {
        return mOnSelectItemTouch;
    }

    public void setOnSelectItemTouch(OnSelectItemTouch selectItemTouch) {
        this.mOnSelectItemTouch = selectItemTouch;
    }

}
