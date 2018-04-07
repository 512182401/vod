//package com.changxiang.vod.module.ui.topmusic.adapter;
//
//import android.content.Context;
//import android.util.SparseIntArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.changxiang.vod.R;
//import com.changxiang.vod.common.utils.ImageLoader;
//import com.changxiang.vod.common.utils.SongDataNumb;
//import com.changxiang.vod.common.view.FlikerProgressBar;
//import com.changxiang.vod.module.base.AdapterListListener;
//import com.changxiang.vod.module.entry.SongBean;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * 榜单列表适配器
// * Created by 15976 on 2016/10/17.
// */
//
//public class PlayListAdapter extends BaseAdapter {
//
//    private Context context;
//    private LayoutInflater inflater;
//    private final ArrayList<SongBean> list;
//    //Map<Integer,Integer> statueList=new HashMap<>();//下载状态集合
//    private final HashMap<Integer, Integer> progressing = new HashMap<>();//正在下载的进度
//    private String musicType;//原唱地址，音乐类型
//    private SparseIntArray isDownloadList;//歌曲文件是否下载完成的集合
//    private String downloadSongId;
//    private String downloadMusicType;
//    private String size;
//    private int percent;//下载进度
//    private int statue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
//    private SongBean bean;
//
//
//    public PlayListAdapter(Context context, ArrayList<SongBean> list) {
//
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        if (list == null) {
//            return 0;
//        } else if (list.size() > 10) {
//            return 10;
//        } else return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        PlayListHolder holder;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.adapter_topmusic_list, null);
//            holder = new PlayListHolder(convertView);
//            convertView.setTag(holder);
//        }
//        holder = (PlayListHolder) convertView.getTag();
//        SongBean bean = list.get(position);
//        String songname = bean.getname();
//        String singername = bean.getsingerName();
//        final String type = bean.getType();
//        final int num = bean.getnum();
//        final String songId = bean.getid();
//        musicType = bean.getType();
//        size=bean.getSize();
//        if (size!=null&&!size.equals("")&&!size.equals("0")){
//            holder.sizeTv.setText("("+size+"M)");
//        }else {
//            holder.sizeTv.setText(R.string.fileSize);
//        }
//        try {
//            SongDataNumb.shownum2view( num, holder.playedNum );//点播量
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String imgurl = bean.getimgAlbumUrl();
//        holder.tvSong.setText(songname);
//        holder.tvSinger.setText(singername);
//        ImageLoader.getImageViewLoad(holder.imageView, imgurl, R.drawable.default_icon);
//        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        //设置文字，封面,事件
//        final int pos = position + 1;
//        if (pos < 10) {
//            holder.tvNum.setText("0" + pos);
//        } else {
//            holder.tvNum.setText("" + pos);
//        }
//        if ("video".equals(type)) {
//            holder.tag.setText(R.string.ktv_tag);
//        }else {
//            holder.tag.setText(R.string.mp3_tag);
//        }
//        statue=isDownloadList.get(position);
//        //下载中
//        if (downloadSongId!=null&&songId!=null&&downloadMusicType!=null&&musicType!=null&&
//                songId.equals(downloadSongId)&&musicType.equals(downloadMusicType)){
//            percent=progressing.get(position);
//            if (percent>=0&&percent<=100){
//                holder.flikerProgressBar.setVisibility(View.VISIBLE);
//                holder.statueTv.setVisibility(View.GONE);
//                holder.flikerProgressBar.setProgress(percent);
//                statue=2;
//                isDownloadList.put(position,2);
//                if (percent==100){
//                    isDownloadList.put(position,3);
//                    holder.flikerProgressBar.setVisibility(View.GONE);
//                    holder.statueTv.setVisibility(View.VISIBLE);
//                    holder.statueTv.setText(R.string.sing);
//                    holder.statueTv.setTextColor(context.getResources().getColor(R.color.white));
//                    holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_bg);
//                    statue=3;
//                    downloadSongId=null;
//                    downloadMusicType=null;
//                }
//            }else {
//                holder.flikerProgressBar.reset();
//                holder.flikerProgressBar.setVisibility(View.GONE);
//                holder.statueTv.setVisibility(View.VISIBLE);
//                holder.statueTv.setText(R.string.download_song);
//                holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
//                holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//                statue=0;
//            }
//        }else if (statue==1){//待下载
//            holder.flikerProgressBar.setVisibility(View.GONE);
//            holder.statueTv.setVisibility(View.VISIBLE);
//            holder.statueTv.setText(R.string.wait_download);
//            holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
//            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//            statue=1;
//        }else if (statue==3){//下载完
//            holder.flikerProgressBar.setVisibility(View.GONE);
//            holder.statueTv.setVisibility(View.VISIBLE);
//            holder.statueTv.setText(R.string.sing);
//            holder.statueTv.setTextColor(context.getResources().getColor(R.color.white));
//            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_bg);
//            statue=3;
//        }else {//未下载
//            statue=0;
//            holder.flikerProgressBar.setVisibility(View.GONE);
//            holder.statueTv.setVisibility(View.VISIBLE);
//            holder.statueTv.setText(R.string.download_song);
//            holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
//            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//        }
//        isDownloadList.put(position,statue);
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                excuterQXRItem(1,position,null);
//            }
//        });
//
//        holder.statueTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isDownloadList.get(position)==3){//练歌
//                    excuterQXRItem(2,position,null);
//                }else if (isDownloadList.get(position)==1){//取消等待
//                    excuterQXRItem(5,position,null);
//                }else {//下载
//                    excuterQXRItem(3,position,null);
//                }
//            }
//        });
//        holder.flikerProgressBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                excuterQXRItem(4,position,null);
//            }
//        });
//        return convertView;
//    }
//
//    private AdapterListListener<String> listener;
//
//    public AdapterListListener<String> getListener() {
//        return listener;
//    }
//
//    public void setListener(AdapterListListener<String> listener) {
//        this.listener = listener;
//    }
//
//    private void excuterQXRItem(int type, int position, String songList) {
//        if (listener != null)
//            listener.click( type, position, songList );
//    }
//
//    private class PlayListHolder {
//        //
//        private ImageView imageView;
//        public TextView tvNum, tag, tvSong, tvSinger, playedNum;//编号，mv标志，歌曲名，歌手名，点播量
//        TextView statueTv;//点歌按钮
//        FlikerProgressBar flikerProgressBar;
//        TextView sizeTv;//歌曲大小
//
//        public PlayListHolder(View convertView) {
//
//            imageView = (ImageView) convertView.findViewById(R.id.adapter_topmusic_ivIcon);
//            tvNum = (TextView) convertView.findViewById(R.id.adapter_topmusic_order);
//            tvSong = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvSong);
//            tvSinger = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvName);
//            playedNum = (TextView) convertView.findViewById(R.id.adapter_topmusic_playedNum);
//            tag = (TextView) convertView.findViewById(R.id.adapter_topmusic_tag);
//            statueTv= (TextView) convertView.findViewById(R.id.statue_tv);
//            flikerProgressBar= (FlikerProgressBar) convertView.findViewById(R.id.flikerProgressBar);
//            sizeTv = (TextView) convertView.findViewById(R.id.size_tv);
//        }
//    }
//    //设置歌曲下载状态
//    public void setIsDownloadList(SparseIntArray isDownloadList){
//        if (isDownloadList!=null&&isDownloadList.size()>0){
//            this.isDownloadList=isDownloadList;
//            notifyDataSetChanged();
//        }
//    }
//
//    //等待下载
//    public void setWaitDownload(int position, String downloadSongId, String downloadMusicType){
//        /*this.downloadSongId=downloadSongId;
//        this.downloadMusicType=downloadMusicType;*/
//        isDownloadList.put(position,1);//改为非下载中状态
//        notifyDataSetChanged();
//
//    }
//
//    //取消等待下载
//    public void cancelWaiting(int position, String downloadSongId, String downloadMusicType){
//        isDownloadList.put(position,0);//改为非下载中状态
//        notifyDataSetChanged();
//    }
//
//    //更新下载进度的方法
//    public void setDownloadProgress(int position, int percent, String downloadSongId, String downloadMusicType){
//        this.downloadSongId=downloadSongId;
//        this.downloadMusicType=downloadMusicType;
//        this.percent=percent;
//        position=getDownloadPosition(downloadSongId,downloadMusicType);
//        if (position!=-1){
//            progressing.put(position,percent);
//            notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * 获取正在下载的歌曲在当前列表中的位置
//     * @param downloadSongId
//     * @param downloadMusicType
//     * @return
//     */
//    private int getDownloadPosition(String downloadSongId, String downloadMusicType){
//        String id;
//        String type;
//        for (int i=0;i<list.size();i++){
//            bean=list.get(i);
//            id=bean.getid();
//            type=bean.getType();
//            if (downloadSongId!=null&&downloadMusicType!=null&&downloadSongId.equals(id)&&downloadMusicType.equals(type)){
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public void setFinishImg(int position, String downloadSongId, String downloadMusicType) {
//        this.downloadSongId=downloadSongId;
//        this.downloadMusicType=downloadMusicType;
//        position=getDownloadPosition(downloadSongId,downloadMusicType);
//        progressing.put(position,100);//改为非下载中状态
//        notifyDataSetChanged();
//    }
//
//    //下载过程中网络断开或者服务器异常
//    public void setOnError(int position, String downloadSongId, String downloadMusicType){
//        this.downloadSongId=downloadSongId;
//        this.downloadMusicType=downloadMusicType;
//        position=getDownloadPosition(downloadSongId,downloadMusicType);
//        isDownloadList.put(position,0);//改为非下载中状态
//        progressing.put(position,-1);//改为非下载中状态
//        notifyDataSetChanged();
//    }
//
//}
