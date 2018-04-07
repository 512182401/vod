//package com.changxiang.vod.module.ui.typemusic.adapter;
//
//import android.content.Context;
//import android.util.SparseIntArray;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.ImageLoader;
//import com.quchangkeji.tosing.common.utils.SongDataNumb;
//import com.quchangkeji.tosing.common.view.FlikerProgressBar;
//import com.quchangkeji.tosing.module.base.AdapterListListener;
//import com.quchangkeji.tosing.module.entry.SongList;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class TypeSongAdapter extends BaseAdapter {
//
//    private final List<SongList.ResultsBean> songList = new ArrayList<>();
//    private Context context;
//    private String musicType;
//    private String songId;//原唱地址，音乐类型,歌曲id
//    private final Map<Integer,Integer> progressing = new HashMap<>();//正在下载的进度
//    private SparseIntArray isDownloadList;//歌曲文件是否下载完成的集合
//    private String downloadSongId;
//    private String downloadMusicType;
//    private String size;
//    private int percent;//下载进度
//    private int statue;//下载状态标识符
//    //Map<Integer,Integer> statueList=new HashMap<>();//下载状态集合
//    private SongList.ResultsBean bean;
//
//    public TypeSongAdapter(Context context) {
//        this.context = context;
//    }
//
//
//    @Override
//    public int getCount() {
//        //TODO
//        return songList == null ? 0 : songList.size();
//    }
//
//
//    public SongList.ResultsBean getItem(int position) {
//        if (songList != null && songList.size() > position) {
//            return songList.get(position);
//        }
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    class ViewHolder {
//        private ImageView showimager;
//        public TextView tag, tvSong, tvSinger, playedNum;//编号，mv标志，歌曲名，歌手名，点播量
//        FlikerProgressBar flikerProgressBar;
//        TextView statueTv;//点歌按钮标识
//        TextView sizeTv;//歌曲大小
//
//        public ViewHolder(View convertView) {
//            showimager = (ImageView) convertView.findViewById( R.id.showimager );
//            tvSong = (TextView) convertView.findViewById( R.id.adapter_topmusic_tvSong );
//            tvSinger = (TextView) convertView.findViewById( R.id.adapter_topmusic_tvName );
//            playedNum = (TextView) convertView.findViewById( R.id.adapter_topmusic_playedNum );
//            tag = (TextView) convertView.findViewById( R.id.adapter_topmusic_tag );
//            flikerProgressBar= (FlikerProgressBar) convertView.findViewById(R.id.flikerProgressBar);
//            statueTv= (TextView) convertView.findViewById(R.id.statue_tv);
//            sizeTv = (TextView) convertView.findViewById(R.id.size_tv);
//        }
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
////        final int conn = position;
//        final SongList.ResultsBean item = getItem(position);
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.item_singer_song, null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        String Userpic, name, singerName;//歌曲地址，歌曲名，歌手名
//        if (item != null) {
//            Userpic = item.getImgAlbumUrl();
//            int imageOnFail = R.drawable.tv_mv;
//            ImageLoader.getImageViewLoad( holder.showimager, Userpic, imageOnFail);
//            name = item.getName();
//            singerName = item.getSingerName();
//            songId=item.getId();
//            musicType=item.getType();
//            size=item.getSize();
//            if (size!=null&&!size.equals("")&&!size.equals("0")){
//                holder.sizeTv.setText("("+size+"M)");
//            }else {
//                holder.sizeTv.setText(R.string.fileSize);
//            }
//            if (name != null) {
//                holder.tvSong.setText( name );
//            }
//            if (singerName != null) {
//                holder.tvSinger.setText( singerName );
//            }
//            if ("video".equals( songList.get( position ).getType() )) {
//                holder.tag.setText( R.string.ktv_tag );
//            }else {
//                holder.tag.setText( R.string.mp3_tag );
//            }
//            int num = item.getNum();
//            try {
//                SongDataNumb.shownum2view( num, holder.playedNum );//点播量
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        statue=isDownloadList.get(position);
//        //下载中
//        if (downloadSongId!=null&&songId!=null&&downloadMusicType!=null&&musicType!=null&&
//                songId.equals(downloadSongId)&&musicType.equals(downloadMusicType)){
//            if (progressing.containsKey(position)){
//                percent=progressing.get(position);
//            }else {
//                percent=-1;
//            }
//
//            if (percent>=0&&percent<=100){
//                holder.flikerProgressBar.setVisibility(View.VISIBLE);
//                holder.statueTv.setVisibility(View.GONE);
//                holder.flikerProgressBar.setProgress(percent);
//                statue=2;
//                if (percent==100){
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
//                /*downloadSongId=null;
//                downloadMusicType=null;*/
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
//        holder.showimager.setOnClickListener( new OnClickListener() {// home_choose_song1/听歌
//
//            @Override
//            public void onClick(View arg0) {
//                excuterQXRItem( 1, position, songList );
//
//            }
//        } );
////        //        holder.playedNum.setText(item.getName());//收听量
////        try {
////            SongDataNumb.shownum2view(Integer.parseInt(item.getName()), holder.playedNum);//收听数  tv_listen_count
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//        holder.statueTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isDownloadList.get(position)==3){//练歌
//                    excuterQXRItem(2,position,songList);
//                }else if (isDownloadList.get(position)==1){//取消等待
//                    excuterQXRItem(5,position,songList);
//                }else {//下载
//                    excuterQXRItem(3,position,songList);
//                }
//            }
//        });
//        holder.flikerProgressBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                excuterQXRItem(4,position,songList);
//            }
//        });
//        return convertView;
//    }
//
//    private AdapterListListener<List<SongList.ResultsBean>> listener;
//
//    public AdapterListListener<List<SongList.ResultsBean>> getListener() {
//        return listener;
//    }
//
//    public void setListener(AdapterListListener<List<SongList.ResultsBean>> listener) {
//        this.listener = listener;
//    }
//
//    private void excuterQXRItem(int type, int position, List<SongList.ResultsBean> songList) {
//        if (listener != null)
//            listener.click(type, position, songList);
//    }
//
//
//
//    public void setDataList(List<SongList.ResultsBean> list) {
//        if (list != null && list.size()>0) {
//            songList.clear();
//            songList.addAll( list );
//            notifyDataSetChanged();
//        }
//
//    }
//
//
//
//    public void addDataList(List<SongList.ResultsBean> list) {
//        if (list != null&&list.size()>0) {
//            songList.addAll( list );
//            notifyDataSetChanged();
//        }
//    }
//
//    public void setIsDownloadList(SparseIntArray isDownloadList){
//        if (isDownloadList!=null&&isDownloadList.size()>0){
//            this.isDownloadList=isDownloadList;
//            notifyDataSetChanged();
//        }
//    }
//
//    public void clear() {
//        if (songList != null) {
//            songList.clear();
//            notifyDataSetChanged();
//        }
//    }
//
//    //等待下载
//    public void setWaitDownload(int position, String downloadSongId, String downloadMusicType){
//        /*this.downloadSongId=downloadSongId;
//        this.downloadMusicType=downloadMusicType;*/
//        isDownloadList.put(position,1);//改为等待下载中状态
//        notifyDataSetChanged();
//
//    }
//
//    //取消等待下载
//    public void cancelWaiting(int position, String downloadSongId, String downloadMusicType){
//        isDownloadList.put(position,0);//改为非下载中状态
//        notifyDataSetChanged();
//    }
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
//    /**
//     * 获取正在下载的歌曲在当前列表中的位置
//     * @param downloadSongId
//     * @param downloadMusicType
//     * @return
//     */
//    private int getDownloadPosition(String downloadSongId, String downloadMusicType){
//        String id;
//        String type;
//        for (int i=0;i<songList.size();i++){
//            bean=songList.get(i);
//            id=bean.getId();
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
//}
