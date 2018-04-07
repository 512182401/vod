package com.changxiang.vod.module.ui.addlocal;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.changxiang.vod.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    public List<MediaItem> datas = null;
    public List<Bitmap> arrBitmap = null;
    private OnItemClickListener mOnItemClickListener = null;
    private final int width;

    public MyAdapter(List<MediaItem> datas, List<Bitmap> arrBitmap, Context context) {
        this.datas = datas;
        this.arrBitmap = arrBitmap;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (MediaItem) v.getTag());
        }
    }

    //点击事件接口
    public static interface OnItemClickListener {
        void onItemClick(View view, MediaItem mediaItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MediaItem mediaItem = datas.get(position);
        viewHolder.mVideoFeimian.setImageBitmap(arrBitmap.get(position));
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewHolder.mVideoUp.setLayoutParams(param);
//        viewHolder.mVideoName.setText(datas.get(position).getName());
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(datas.get(position));
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mVideoUp;
        public ImageView mVideoFeimian;
        //        public TextView mVideoName;

        public ViewHolder(View view) {
            super(view);
            mVideoFeimian = (ImageView) view.findViewById(R.id.video_feimian);
            mVideoUp = (ImageView) view.findViewById(R.id.video_up);
//            mVideoName = (TextView) view.findViewById(R.id.video_name);
        }
    }

//    /**
//     * 获取视频的缩略图
//     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
//     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
//     *
//     * @param videoPath 视频的路径
//     * @param width     指定输出视频缩略图的宽度
//     * @param height    指定输出视频缩略图的高度度
//     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
//     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
//     * @return 指定大小的视频缩略图
//     */
//    private Bitmap getVideoThumbnail(String videoPath, int width, int height,
//                                     int kind) {
//        Bitmap bitmap = null;
//        // 获取视频的缩略图
//        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
////        System.out.println("w" + bitmap.getWidth());
////        System.out.println("h" + bitmap.getHeight());
//        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
//                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        return bitmap;
//    }
}