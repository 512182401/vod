package com.changxiang.vod.module.ui.singermusic.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.ImageLoader;
//import com.quchangkeji.tosing.common.utils.SongDataNumb;
//import com.quchangkeji.tosing.common.view.FlikerProgressBar;
//import com.quchangkeji.tosing.module.base.AdapterListListener;
//import com.quchangkeji.tosing.module.entry.SongList;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.FileUtil;
import com.changxiang.vod.common.utils.ImageLoader;
import com.changxiang.vod.common.utils.SongDataNumb;
import com.changxiang.vod.common.view.FlikerProgressBar;
import com.changxiang.vod.module.base.AdapterListListener;
import com.changxiang.vod.module.db.VodMedia;

import java.util.ArrayList;
import java.util.List;


public class SingerSongAdapter extends BaseAdapter {

    private final List<VodMedia> songList = new ArrayList<>();
    private Context context;
    private String musicType;
    private String songId;//原唱地址，音乐类型，歌曲id
    private final SparseIntArray progressing = new SparseIntArray();//正在下载的进度
    private SparseIntArray isDownloadList;//歌曲文件是否下载完成的集合
    private String downloadSongId;
    private String downloadMusicType;
    private int percent;//下载进度

    public SingerSongAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        //TODO
        return songList == null ? 0 : songList.size();
    }

    public VodMedia getItem(int position) {
        if (songList != null && songList.size() > position) {
            return songList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        final int conn = position;
        final VodMedia item = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_singer_song, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String Userpic, name, singerName;//歌曲地址，歌曲名，歌手名
        if (item != null) {
//            Userpic = item.getImgAlbumUrl();
            Userpic = null;
            ImageLoader.getImageViewLoad(holder.showimager, Userpic, R.drawable.tv_mv);
            name = item.getSongName();
            singerName = item.getSinger();
            songId = item.getSongbm();
//            musicType = item.getType();
            musicType = "video";

            try {
                double filesize = FileUtil.getFileOrFilesSize(item.getPath(), 3);
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
//            holder.file_size.setText("(" + df.format(filesize) + "M)");
                String size = df.format(filesize);
                if (size != null && !size.equals("") && !size.equals("0")) {
                    holder.sizeTv.setText("(" + size + "M)");
                } else {
                    holder.sizeTv.setText(R.string.fileSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (name != null) {
                holder.tvSong.setText(name);
            }
            if (singerName != null) {
                holder.tvSinger.setText(singerName);
            }
//            if ("video".equals(songList.get(position).getType())) {
            holder.tag.setText(R.string.ktv_tag);
//            }else {
//                holder.tag.setText(R.string.mp3_tag);
//            }
            try {
                int num = Integer.parseInt(item.getCloudPlay(), 0);
                SongDataNumb.shownum2view(num, holder.playedNum);//点播量
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        int statue = isDownloadList.get(position);
//
//        isDownloadList.put(position, statue);
        holder.showimager.setOnClickListener(new OnClickListener() {// home_choose_song1/听歌

            @Override
            public void onClick(View arg0) {
                excuterQXRItem(1, position, songList);

            }
        });
//        holder.playedNum.setText(item.getName());//收听量
//        try {
//            SongDataNumb.shownum2view(Integer.parseInt(item.getName()), holder.playedNum);//收听数  tv_listen_count
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        holder.statueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloadList.get(position) == 3) {//练歌
                    excuterQXRItem(2, position, songList);
                } else if (isDownloadList.get(position) == 1) {//取消等待
                    excuterQXRItem(5, position, songList);
                } else {//下载
                    excuterQXRItem(3, position, songList);
                }
            }
        });
        holder.flikerProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excuterQXRItem(4, position, null);
            }
        });
        return convertView;
    }

    private AdapterListListener<List<VodMedia>> listener;

    public AdapterListListener<List<VodMedia>> getListener() {
        return listener;
    }

    public void setListener(AdapterListListener<List<VodMedia>> listener) {
        this.listener = listener;
    }

    private void excuterQXRItem(int type, int position, List<VodMedia> songList) {
        if (listener != null)
            listener.click(type, position, songList);
    }

    class ViewHolder {
        private ImageView showimager;
        public TextView tag, tvSong, tvSinger, playedNum;//编号，mv标志，歌曲名，歌手名，点播量
        FlikerProgressBar flikerProgressBar;
        TextView statueTv;//点歌按钮标识
        TextView sizeTv;//歌曲大小

        public ViewHolder(View convertView) {
            showimager = (ImageView) convertView.findViewById(R.id.showimager);
            tvSong = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvSong);
            tvSinger = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvName);
            playedNum = (TextView) convertView.findViewById(R.id.adapter_topmusic_playedNum);
            tag = (TextView) convertView.findViewById(R.id.adapter_topmusic_tag);
            flikerProgressBar = (FlikerProgressBar) convertView.findViewById(R.id.flikerProgressBar);
            statueTv = (TextView) convertView.findViewById(R.id.statue_tv);
            sizeTv = (TextView) convertView.findViewById(R.id.size_tv);
        }
    }


    public void setDataList(List<VodMedia> list) {
        if (list != null && list.size() > 0) {
            songList.clear();
            songList.addAll(list);
            notifyDataSetChanged();
        }
    }


    public void addDataList(List<VodMedia> list) {
        if (list != null && list.size() > 0) {
            songList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setIsDownloadList(SparseIntArray isDownloadList) {
        if (isDownloadList != null && isDownloadList.size() > 0) {
            this.isDownloadList = isDownloadList;
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (songList != null) {
            songList.clear();
            notifyDataSetChanged();
        }
    }

    //等待下载
    public void setWaitDownload(int position, String downloadSongId, String downloadMusicType) {
        /*this.downloadSongId=downloadSongId;
        this.downloadMusicType=downloadMusicType;*/
        isDownloadList.put(position, 1);//改为等待下载中状态
        notifyDataSetChanged();

    }

    //取消等待下载
    public void cancelWaiting(int position, String downloadSongId, String downloadMusicType) {
        isDownloadList.put(position, 0);//改为非下载中状态
        notifyDataSetChanged();
    }

    //更新下载进度的方法
    public void setDownloadProgress(int position, int percent, String downloadSongId, String downloadMusicType) {
        this.downloadSongId = downloadSongId;
        this.downloadMusicType = downloadMusicType;
        this.percent = percent;
        position = getDownloadPosition(downloadSongId, downloadMusicType);
        if (position != -1) {
            progressing.put(position, percent);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取正在下载的歌曲在当前列表中的位置
     *
     * @param downloadSongId
     * @param downloadMusicType
     * @return
     */
    private int getDownloadPosition(String downloadSongId, String downloadMusicType) {
        String id;
        String type;
//        for (int i=0;i<songList.size();i++){
//            SongList.ResultsBeanVodMedia bean = songList.get(i);
//            id= bean.getId();
//            type= bean.getType();
//            if (downloadSongId!=null&&downloadMusicType!=null&&downloadSongId.equals(id)&&downloadMusicType.equals(type)){
//                return i;
//            }
//        }
        return -1;
    }

    public void setFinishImg(int position, String downloadSongId, String downloadMusicType) {
        this.downloadSongId = downloadSongId;
        this.downloadMusicType = downloadMusicType;
        position = getDownloadPosition(downloadSongId, downloadMusicType);
        if (position != -1) {
            progressing.put(position, 100);//改为非下载中状态
            notifyDataSetChanged();
        }
    }

    //下载过程中网络断开或者服务器异常
    public void setOnError(int position, String downloadSongId, String downloadMusicType) {
        this.downloadSongId = downloadSongId;
        this.downloadMusicType = downloadMusicType;
        position = getDownloadPosition(downloadSongId, downloadMusicType);
        if (position != -1) {
            isDownloadList.put(position, 0);//改为非下载中状态
            progressing.put(position, -1);//改为非下载中状态
            notifyDataSetChanged();
        }
    }

}
