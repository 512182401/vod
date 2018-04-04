package com.changxiang.vod.module.ui.recently.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.ImageLoader;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;
import com.changxiang.vod.common.utils.SongDataNumb;
import com.changxiang.vod.common.view.FlikerProgressBar;
import com.changxiang.vod.module.base.AdapterListListener;
import com.changxiang.vod.module.db.DownloadManager;
import com.changxiang.vod.module.entry.SongDetail;
import com.changxiang.vod.module.ui.recently.db.HistoryDBManager;
import com.changxiang.vod.module.ui.recently.db.PlayedHistory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecentlyPlayerAdapterUpdate extends BaseAdapter {

    List<PlayedHistory> list;
    String songId, musicType, songName, singerName;//歌曲id,音乐类型（mp3,mv）,歌曲名
    File file;//文件地址
    DownloadManager dao;//数据库工具类
    private Context context;
    private int ImageOnFail = R.drawable.tv_mv;// 下载失败时的图片名称

    public ArrayList<String> ids = new ArrayList<>();
    public ArrayList<String> types = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<String> singerNames = new ArrayList<>();
    //public AdapterListListener<List<PlayedHistory>> listener;
    LayoutInflater inflater;
    Map<Integer, Integer> isDownloadList = new HashMap<>();//歌曲文件是否下载完成的集合
    public HashMap<Integer, Integer> progressing = new HashMap<>();//正在下载的进度
    String mp3Dir = MyFileUtil.DIR_MP3.toString() + File.separator;//
    String mp4Dir = MyFileUtil.DIR_VEDIO.toString() + File.separator;//
    String accDir = MyFileUtil.DIR_ACCOMPANY.toString() + File.separator;//
    String lrcDir = MyFileUtil.DIR_LRC.toString() + File.separator;//
    String krcDir = MyFileUtil.DIR_KRC.toString() + File.separator;//
    File ycFile;//已下载歌曲的文件
    File bzFile;//
    File lrcFile;//
    File krcFile;//
    boolean isAllDownload;
    String downloadSongId, downloadMusicType;
    String size;
    int percent;//下载进度
    int statue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
    PlayedHistory item;

    public RecentlyPlayerAdapterUpdate(Context context) {
        this.context = context;
        dao = DownloadManager.getDownloadManager(context);
        inflater = LayoutInflater.from(context);
        LogUtils.sysout("请求最近播放数据+++++");
        list = HistoryDBManager.getHistoryManager().queryAll();
        //按时间降序
        Collections.sort(list, new Comparator<PlayedHistory>() {
            @Override
            public int compare(PlayedHistory o1, PlayedHistory o2) {
                return (int) (o2.getDate() - o1.getDate());
            }
        });
        for (int i = 0; i < list.size(); i++) {
            songId = list.get(i).getSongId();
            musicType = list.get(i).getType();
            songName = list.get(i).getName();
            singerName = list.get(i).getSingerName();
            ids.add(songId);
            types.add(musicType);
            names.add(songName);
            singerNames.add(singerName);
            if ("video".equals(musicType)) {
                ycFile = new File(mp4Dir + singerName + "-" + songName + ".mp4");//
                bzFile = new File(accDir + singerName + "-" + songName + ".mp4");//
            } else if ("audio".equals(musicType)) {
                ycFile = new File(mp3Dir + singerName + "-" + songName + ".mp3");//
                bzFile = new File(accDir + singerName + "-" + songName + ".mp3");//
            }
            lrcFile = new File(lrcDir + singerName + "-" + songName + ".lrc");//
            krcFile = new File(krcDir + singerName + "-" + songName + ".krc");//
            if (lrcFile.exists() && ycFile.exists() && bzFile.exists() && krcFile.exists()) {
                isAllDownload = dao.isAllDownload("",songId, musicType);
                if (isAllDownload){
                    statue=3;
                }else {
//                    if (DownloadAllService.isWaiting(songId,musicType)){
//                        statue=1;
//                    }else {
//                        statue=0;
//                    }
                }
            } else {
//                if (DownloadAllService.isWaiting(songId,musicType)){
//                    statue=1;
//                }else {
//                    statue=0;
//                }
            }
            isDownloadList.put(i, statue);
        }
    }

    @Override
    public int getCount() {
        //TODO
        return list == null ? 0 : list.size();

    }

    @Override
    public PlayedHistory getItem(int position) {
        if (list != null && list.size() > position) {
            return list.get(position);
        }
        return null;
    }

    public SongDetail getDownloadItem(int position) {
        SongDetail  songDetail = dao.selectSong("",getItem(position).getSongId(), getItem(position).getType());
//        if (songDetail != null && songDetail.size() > position) {
//            return list.get(position);
//        }
        return songDetail;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_singer_song, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String Userpic, name, singerName;//歌曲地址，歌曲名，歌手名
        if (item != null) {
            Userpic = item.getImgAlbumUrl();
            ImageLoader.getImageViewLoad(holder.showimager, Userpic, ImageOnFail);
            name = item.getName();
            songId = item.getSongId();
            musicType = item.getType();
            singerName = item.getSingerName();
            if (name != null) {
                holder.tvSong.setText(name);
            }
            if (singerName != null) {
                holder.tvSinger.setText(singerName);
            }
            size=item.getSize();
            if (size!=null&&!size.equals("")&&!size.equals("0")){
                holder.tvSize.setText("("+size+"M)");
            }else {
                holder.tvSize.setText(R.string.fileSize);
            }
            if ("video".equals(list.get(position).getType())) {
                holder.tag.setText(R.string.ktv_tag);
            }else {
                holder.tag.setText(R.string.mp3_tag);
            }
            int num = item.getNum();
            try {
                SongDataNumb.shownum2view(num, holder.playedNum);//点播量
            } catch (Exception e) {
                e.printStackTrace();
            }
            statue = isDownloadList.get(position);
            //下载中
            if (downloadSongId != null && songId != null && downloadMusicType != null && musicType != null &&
                    songId.equals(downloadSongId) && musicType.equals(downloadMusicType)) {
                percent = progressing.get(position);
                if (percent >= 0 && percent <= 100) {
                    holder.flikerProgressBar.setVisibility(View.VISIBLE);
                    holder.statueTv.setVisibility(View.GONE);
                    holder.flikerProgressBar.setProgress(percent);
                    statue = 2;
                    if (percent == 100) {
                        holder.flikerProgressBar.setVisibility(View.GONE);
                        holder.statueTv.setVisibility(View.VISIBLE);
                        holder.statueTv.setText(R.string.sing);
                        holder.statueTv.setTextColor(context.getResources().getColor(R.color.white));
                        holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_bg);
                        statue = 3;
                        downloadSongId = null;
                        downloadMusicType = null;
                    }
                } else {
                    holder.flikerProgressBar.reset();
                    holder.flikerProgressBar.setVisibility(View.GONE);
                    holder.statueTv.setVisibility(View.VISIBLE);
                    holder.statueTv.setText(R.string.download_song);
                    holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
                    holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
                    statue = 0;
                }
            } else if (statue == 1) {//待下载
                holder.flikerProgressBar.setVisibility(View.GONE);
                holder.statueTv.setVisibility(View.VISIBLE);
                holder.statueTv.setText(R.string.wait_download);
                holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
                holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
                statue = 1;
            } else if (statue == 3) {//下载完
                holder.flikerProgressBar.setVisibility(View.GONE);
                holder.statueTv.setVisibility(View.VISIBLE);
                holder.statueTv.setText(R.string.sing);
                holder.statueTv.setTextColor(context.getResources().getColor(R.color.white));
                holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_bg);
                statue = 3;
            } else {//未下载
                statue = 0;
                holder.flikerProgressBar.setVisibility(View.GONE);
                holder.statueTv.setVisibility(View.VISIBLE);
                holder.statueTv.setText(R.string.download_song);
                holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
                holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
            }
            isDownloadList.put(position, statue);

            holder.showimager.setOnClickListener(new OnClickListener() {// home_choose_song1/听歌

                @Override
                public void onClick(View arg0) {
                    excuterQXRItem(1, position, list);

                }
            });
            holder.statueTv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (isDownloadList.get(position) == 3) {//练歌
                        excuterQXRItem(2, position, list);
                    } else if (isDownloadList.get(position) == 1) {//取消等待
                        excuterQXRItem(5, position, list);
                    } else {//下载
                        excuterQXRItem(3, position, list);
                    }
                }
            });
            holder.flikerProgressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    excuterQXRItem(4, position, list);
                }
            });
        }
        return convertView;
    }

    private AdapterListListener<List<PlayedHistory>> listener;

    public AdapterListListener<List<PlayedHistory>> getListener() {
        return listener;
    }

    public void setListener(AdapterListListener<List<PlayedHistory>> listener) {
        this.listener = listener;
    }

    private void excuterQXRItem(int type, int position, List<PlayedHistory> songList) {
        if (listener != null)
            listener.click(type, position, songList);
    }

    public List<PlayedHistory> getDataList(){
        return list;
    }

    //等待下载
    public void setWaitDownload(int position, String downloadSongId, String downloadMusicType) {
        /*this.downloadSongId=downloadSongId;
        this.downloadMusicType=downloadMusicType;*/
        isDownloadList.put(position, 1);//改为非下载中状态
        notifyDataSetChanged();

    }

    //取消等待下载
    public void cancelWaiting(int position, String downloadSongId, String downloadMusicType){
        isDownloadList.put(position,0);//改为非下载中状态
        notifyDataSetChanged();
    }
    //更新下载进度的方法
    public void setDownloadProgress(int position, int percent, String downloadSongId, String downloadMusicType){
        this.downloadSongId=downloadSongId;
        this.downloadMusicType=downloadMusicType;
        this.percent=percent;
        position=getDownloadPosition(downloadSongId,downloadMusicType);
        if (position!=-1){
            progressing.put(position,percent);
            notifyDataSetChanged();
        }
    }
    /**
     * 获取正在下载的歌曲在当前列表中的位置
     * @param downloadSongId
     * @param downloadMusicType
     * @return
     */
    private int getDownloadPosition(String downloadSongId, String downloadMusicType){
        String id;
        String type;
        for (int i=0;i<list.size();i++){
            item=list.get(i);
            id=item.getSongId();
            type=item.getType();
            if (downloadSongId!=null&&downloadMusicType!=null&&downloadSongId.equals(id)&&downloadMusicType.equals(type)){
                return i;
            }
        }
        return -1;
    }

    public void setFinishImg(int position, String downloadSongId, String downloadMusicType) {
        this.downloadSongId=downloadSongId;
        this.downloadMusicType=downloadMusicType;
        position=getDownloadPosition(downloadSongId,downloadMusicType);
        progressing.put(position,100);//改为非下载中状态
        notifyDataSetChanged();
    }

    //下载过程中网络断开或者服务器异常
    public void setOnError(int position, String downloadSongId, String downloadMusicType){
        this.downloadSongId=downloadSongId;
        this.downloadMusicType=downloadMusicType;
        position=getDownloadPosition(downloadSongId,downloadMusicType);
        isDownloadList.put(position,0);//改为非下载中状态
        progressing.put(position,-1);//改为非下载中状态
        notifyDataSetChanged();
    }

    class ViewHolder {
        public TextView tag, tvSong, tvSinger, playedNum;//编号，mv标志，歌曲名，歌手名，点播量
        private ImageView showimager;
        FlikerProgressBar flikerProgressBar;
        TextView statueTv;//点歌按钮标识
        TextView tvSize;//

        public ViewHolder(View convertView) {
            showimager = (ImageView) convertView.findViewById(R.id.showimager);
            tvSong = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvSong);
            tvSinger = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvName);
            playedNum = (TextView) convertView.findViewById(R.id.adapter_topmusic_playedNum);
            tag = (TextView) convertView.findViewById(R.id.adapter_topmusic_tag);
            flikerProgressBar = (FlikerProgressBar) convertView.findViewById(R.id.flikerProgressBar);
            statueTv = (TextView) convertView.findViewById(R.id.statue_tv);
            tvSize= (TextView) convertView.findViewById(R.id.size_tv);
        }
    }


}
