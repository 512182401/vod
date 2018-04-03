//package com.changxiang.vod.module.ui.recordmusic.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.text.format.Formatter;
//import android.util.SparseIntArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.common.view.FlikerProgressBar;
//import com.quchangkeji.tosingpk.common.view.guideview.Guide;
//import com.quchangkeji.tosingpk.common.view.guideview.GuideBuilder;
//import com.quchangkeji.tosingpk.module.entry.CurrentPeriodBean;
//import com.quchangkeji.tosingpk.module.entry.SongDetail;
//import com.quchangkeji.tosingpk.module.music_download.downloadservice.DownloadAllService;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.MessageBean;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.screen.SimpleComponent;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 15976 on 2017/2/27.
// * 伴奏适配器   后期抽取共同部分  ，合并ChooseSongAdapter
// */
//
//public class DownloadSongAdapter extends BaseAdapter {
//    public boolean deleteState;
//    Context context;
//    LayoutInflater inflater;
//    public List<SongDetail> beanList = new ArrayList<>();
//    SparseIntArray isDownloadList = new SparseIntArray();
//    int statue;//下载状态:0，未下载;1，待下载;2,下载中,3,下载完
//    public SparseIntArray progressing = new SparseIntArray();//正在下载的进度
//    IPracticeSongClick iPracticeSongClick;//点歌按钮回调接口
//    String downloadSongId, downloadMusicType;
//    String size;
//    int percent;//下载进度
//    SongDetail hotSong;
//    String songId, musicType, songName, singerName;
//    MessageBean msg;
//    public int playState = 0;//播放状态，0表示未播放，1表示正在播放，2 表示暂停, 3 表示准备中
//
//    List<Boolean> arrCheck = new ArrayList<>();//记录是否删除，true是选中
//    List<Boolean> arrMediaPlay = new ArrayList<>();//播放按钮的状态
//    List<Boolean> arrDownload = new ArrayList<>();//下载状态
//    private int lastSelectPosition = -1;
//
//    public DownloadSongAdapter(List<SongDetail> beanList, Context context, boolean deleteState) {
//        this.beanList = beanList;
//        this.context = context;
//        this.deleteState = deleteState;
//        try {
//            inflater = LayoutInflater.from(context);
//            initArrMediaPlay();
//            initArrDownload();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initArrDownload() {
//        arrDownload.clear();
//        for (int i = 0; i < beanList.size(); i++) {
//            arrDownload.add(false);
//        }
//        LogUtils.sysout("arrDownload.size()=====================" + arrDownload.size() );
//    }
//
//    private void initArrMediaPlay() {
//        arrMediaPlay.clear();
//        for (int i = 0; i < beanList.size(); i++) {
//            arrMediaPlay.add(true);
//        }
//    }
//
//    public boolean isDeleteState() {
//        return deleteState;
//    }
//
//    @Override
//    public int getCount() {
//        return beanList == null ? 0 : beanList.size();
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
//        final int selectedPosition = position;
//        SongHolder holder;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.adapter_recommend_song, null);
//            holder = new SongHolder(convertView);
//            convertView.setTag(holder);
//        }
//        holder = (SongHolder) convertView.getTag();
//        SongDetail item = beanList.get(position);
//        songName = item.getSongName();
//        singerName = item.getSingerName();
//        String imgHead = item.getImgHead();
//        songId = item.getSongId();
//        musicType = item.getType();
//        size = item.getSize();
//        if (size != null && !size.equals("") && !size.equals("0")) {
//            holder.sizeTv.setText("" + Formatter.formatFileSize(context, Integer.parseInt(size) * 1024));//单位换算
//        } else {
//            holder.sizeTv.setText(R.string.fileSize);
//        }
//        if (songName != null && singerName != null) {
//            holder.songName.setText(songName);
//            holder.singerName.setText(singerName);
//        }
//        //开始或暂停图标
//        if (arrMediaPlay.get(position)) {
//            holder.mediaPlayState.setImageResource(R.drawable.accompany_start);
//        } else {
//            holder.mediaPlayState.setImageResource(R.drawable.accompany_pause);
//        }
//        //删除图片显示判断
//        if (deleteState) {
//            holder.deleteCb.setVisibility(View.VISIBLE);
//            if ("0".equals(beanList.get(position).getIsSelect())) {//没有选中
//                holder.deleteCb.setChecked(false);
//            } else {
//                holder.deleteCb.setChecked(true);
//            }
//        } else {
//            holder.deleteCb.setVisibility(View.GONE);
//        }
//        //是否下载图标
////        if (arrDownload.get(selectedPosition)) {
//            holder.downloadState.setVisibility(View.VISIBLE);
////        } else {
////            holder.downloadState.setVisibility(View.INVISIBLE);
////        }
//
//        //下载中
//        statue = isDownloadList.get(position);
//        if (statue == 0) {
//            statue = 0;
////            holder.flikerProgressBar.setVisibility(View.GONE);
////            holder.statueTv.setVisibility(View.VISIBLE);
////            holder.statueTv.setText(R.string.download_song);
////            holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
////            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//        } else if (downloadSongId != null && songId != null && downloadMusicType != null && musicType != null &&
//                songId.equals(downloadSongId) && musicType.equals(downloadMusicType)) {
//            percent = progressing.get(position);
//            if (percent >= 0 && percent <= 100) {
////                holder.flikerProgressBar.reset();
////                holder.flikerProgressBar.setVisibility(View.VISIBLE);
////                holder.statueTv.setVisibility(View.GONE);
////                holder.flikerProgressBar.setProgress(percent);
//                statue = 2;
////                isDownloadList.put(position,2);
//                if (percent == 100) {
//                    isDownloadList.put(position, 3);
////                    holder.flikerProgressBar.setVisibility(View.GONE);
////                    holder.statueTv.setVisibility(View.VISIBLE);
////                    holder.statueTv.setText(R.string.sing);
////                    holder.statueTv.setTextColor(context.getResources().getColor(R.color.white));
////                    holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_bg);
//                    statue = 3;
//                    downloadSongId = null;
//                    downloadMusicType = null;
//                }
//            } else {
////                holder.flikerProgressBar.reset();
////                holder.flikerProgressBar.setVisibility(View.GONE);
////                holder.statueTv.setVisibility(View.VISIBLE);
////                holder.statueTv.setText(R.string.download_song);
////                holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
////                holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//                statue = 0;
//            }
//        } else if (statue == 1) {//待下载
////            holder.flikerProgressBar.setVisibility(View.GONE);
////            holder.statueTv.setVisibility(View.VISIBLE);
////            holder.statueTv.setText(R.string.wait_download);
////            holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
////            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//            statue = 1;
//        } else if (statue == 3) {//下载完
////            holder.flikerProgressBar.setVisibility(View.GONE);
////            holder.statueTv.setVisibility(View.VISIBLE);
////            holder.statueTv.setText(R.string.sing);
////            holder.statueTv.setTextColor(context.getResources().getColor(R.color.white));
////            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_bg);
//            statue = 3;
//        } else {//未下载
//            statue = 0;
////            holder.flikerProgressBar.setVisibility(View.GONE);
////            holder.statueTv.setVisibility(View.VISIBLE);
////            holder.statueTv.setText(R.string.download_song);
////            holder.statueTv.setTextColor(context.getResources().getColor(R.color.app_oher_red));
////            holder.statueTv.setBackgroundResource(R.drawable.shape_sing_bar_red_bg);
//        }
//        isDownloadList.put(position, statue);
//
//        if ("audio".equals(musicType)) {
//            holder.ivTag.setText(R.string.mp3_tag);
//        } else {
//            holder.ivTag.setText(R.string.ktv_tag);
//        }
//
//        if (!deleteState) {
//            holder.recommend_all.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (iPracticeSongClick != null){
//                        iPracticeSongClick.practiceSongClick(selectedPosition, 10);//听歌
//                    }
//                }
//            });
//            if (position == 0) {
////            view = holder.statueTv;
//                guide_show = holder.guide_show;
////                        showGuideView(holder.ivCover);
//
//
//            }
//            holder.statueTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemToSingListener != null){
//                        SongDetail songDetail = beanList.get(selectedPosition);
//                        //构造转递参数
//                        CurrentPeriodBean currentPeriodBean = new CurrentPeriodBean();
//                        currentPeriodBean.setSongName(songDetail.getSongName());
//                        currentPeriodBean.setId(songDetail.getSongId());
//                        currentPeriodBean.setType(songDetail.getType());
//                        currentPeriodBean.setSingerName(songDetail.getSingerName());
//                        currentPeriodBean.setActivityId(songDetail.getActivityId());
//                        onItemToSingListener.onItemToSing(currentPeriodBean,selectedPosition);
//                    }
//                }
//            });
//
//            final SongHolder finalHolder = holder;
//            holder.mediaPlayState.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(context, "后台播放音乐" + selectedPosition, Toast.LENGTH_SHORT).show();
//                    if (lastSelectPosition != selectedPosition) {//开始：lastSelectPosition = -1  selectedPosition= 0
//                        playState = 0;
//                        arrMediaPlay.set(selectedPosition, false);
//                        if (lastSelectPosition != -1) {
//                            arrMediaPlay.set(lastSelectPosition, true);
//                        }
//                        notifyDataSetChanged();
//                        lastSelectPosition = selectedPosition;
//                    }
//                    msg = new MessageBean();
//                    LogUtils.w("playstate", "=1111111===" + playState);
//                    if (playState == 1) {//1表示正在播放
//                        LogUtils.w("playstate", "=1111111===");
//                        finalHolder.mediaPlayState.setImageResource(R.drawable.accompany_start);
//                        msg.setPlayState(1);
//                        playState = 2;
//                    } else if (playState == 2) {//2 表示暂停
//                        LogUtils.w("playstate", "=22222222===");
//                        finalHolder.mediaPlayState.setImageResource(R.drawable.accompany_pause);
//                        msg.setPlayState(2);
//                        playState = 1;
//                    } else if (playState == 0) {//0表示未播放
//                        LogUtils.w("playstate", "=333333333333===");
//                        finalHolder.mediaPlayState.setImageResource(R.drawable.accompany_pause);
//                        msg.setPlayState(0);
//                        playState = 1;
//                    }
//                    msg.setSongBean(beanList.get(selectedPosition));
//                    msg.setTag("StartMusic");
//                    msg.setPosition(selectedPosition);
//                    EventBus.getDefault().post(msg);
//                }
//            });
//        } else {
////            holder.recommentRoot.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//永远自己处理
//            holder.deleteCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        beanList.get(position).setIsSelect("1");
//                    } else {
//                        beanList.get(position).setIsSelect("0");
//                    }
//                }
//            });
//            holder.statueTv.setClickable(false);
//            holder.mediaPlayState.setClickable(false);
//            holder.recommend_all.setClickable(false);
//        }
//
//        return convertView;
//    }
//
//    /**
//     * 发送，播放，暂停广播
//     *
//     * @param isplay true 播放，false 暂停
//     */
//    private void musicPlay(boolean isplay) {
//        Intent intent = new Intent();
//        intent.putExtra("isplay", isplay);
//        if ("audio".equals(musicType)) {
//            intent.setAction("ACTION_ISPLAY");
//        } else if ("video".equals(musicType)) {
//            intent.setAction("ACTION_ISPLAY_VIDEO");
//        }
//        context.sendBroadcast(intent);
//    }
//
//    public void addDataList(List<SongDetail> songList) {
//        if (songList != null) {
//            for (int i = 0; i < songList.size(); i++) {
//                hotSong = beanList.get(i);
//                if (DownloadAllService.isWaiting(hotSong.getSongId(), hotSong.getType())) {
//                    progressing.put(i + beanList.size(), -2);
//                } else {
//                    progressing.put(i + beanList.size(), -1);
//                }
//            }
//            beanList.addAll(songList);
//            initArrMediaPlay();
//            changeArrCheck();
//            notifyDataSetChanged();
//        }
//    }
//
//    private void changeArrCheck() {
//        arrCheck.clear();
//        for (int i = 0; i < beanList.size(); i++) {
//            arrCheck.add(true);
//        }
//    }
//
//    //获取list的大小
//    public int getListSize() {
//        return beanList == null ? 0 : beanList.size();
//    }
//
//    //获取list数据
//    public List<SongDetail> getBeanList() {
//        return beanList;
//    }
//
//    //设置checkbox的状态
//    public void setArrCheck(List<Boolean> arrCheck) {
//        this.arrCheck = arrCheck;
//    }
//
//    //设置MediaPlay的状态
//    public void setArrMediaPlay(List<Boolean> arrMediaPlay) {
//        this.arrMediaPlay = arrMediaPlay;
//    }
//
//    public void changeArrMediaPlay() {
//        lastSelectPosition = -1;
//        for (int i = 0; i < beanList.size(); i++) {
//            arrMediaPlay.set(i, true);
//        }
//    }
//
//    public void setArrDownload(List<Boolean> arrDownload) {
//        this.arrDownload = arrDownload;
//    }
//
//    //等待下载
//    public void setWaitDownload(int position, String downloadSongId, String downloadMusicType) {
//        /*this.downloadSongId=downloadSongId;
//        this.downloadMusicType=downloadMusicType;*/
//        isDownloadList.put(position, 1);//改为等待下载中状态
//        notifyDataSetChanged();
//
//    }
//
//    //下载过程中网络断开或者服务器异常
//    public void setOnError(int position, String downloadSongId, String downloadMusicType) {
//        this.downloadSongId = downloadSongId;
//        this.downloadMusicType = downloadMusicType;
//        if (position != -1) {
//            isDownloadList.put(position, 0);//改为非下载中状态
//            progressing.put(position, -1);//改为非下载中状态
//            notifyDataSetChanged();
//        }
//    }
//
//    public void setIsDownloadList(SparseIntArray isDownloadList) {
//        if (isDownloadList != null && isDownloadList.size() > 0) {
//            this.isDownloadList = isDownloadList;
//            notifyDataSetChanged();
//        }
//    }
//
//    //练歌按钮回调接口,tag,1表示已下载，2表示未下载，3表示下载中
//    public interface IPracticeSongClick {
//        void practiceSongClick(int position, int tag);
//
//    }
//
//    public void setiPracticeSongClick(IPracticeSongClick iPracticeSongClick) {
//        this.iPracticeSongClick = iPracticeSongClick;
//    }
//
//    class SongHolder {
//        ImageView downloadState;
//        RelativeLayout recommentRoot;
//        ImageView mediaPlayState;
//        CheckBox deleteCb;
//        TextView songName, singerName;
//        ImageView icon;//
//        TextView ivTag;
//        FlikerProgressBar flikerProgressBar;
//        TextView statueTv;//点歌按钮标识
//        TextView sizeTv;//歌曲大小
//        RelativeLayout recommend_all;
//        TextView guide_show;//tv_guide_show
//
//        public SongHolder(View convertView) {
//            songName = (TextView) convertView.findViewById(R.id.songName);
//            singerName = (TextView) convertView.findViewById(R.id.singerName);
//            icon = (ImageView) convertView.findViewById(R.id.singer_ivICon);
//            ivTag = (TextView) convertView.findViewById(R.id.mv_tag);
////            flikerProgressBar = (FlikerProgressBar) convertView.findViewById(R.id.flikerProgressBar);
//            statueTv = (TextView) convertView.findViewById(R.id.statue_tv);
//            sizeTv = (TextView) convertView.findViewById(R.id.size_tv);
//            recommend_all = (RelativeLayout) convertView.findViewById(R.id.rl_recommend_all);
//            guide_show = (TextView) convertView.findViewById(R.id.tv_guide_show);
//            deleteCb = (CheckBox) convertView.findViewById(R.id.delete_cb);
//            mediaPlayState = (ImageView) convertView.findViewById(R.id.mediaplay_state);
//            recommentRoot = (RelativeLayout) convertView.findViewById(R.id.recomment_root);
//            downloadState = (ImageView) convertView.findViewById(R.id.download_state);
//        }
//    }
//
//
//    View view;
//    private TextView guide_show;//tv_guide_show
//
//    public void showGuideViewitem() {
//        if (view == null) {
//            return;
//        }
//
//        GuideBuilder builder = new GuideBuilder();
//        builder.setTargetView(view)
//                .setFullingViewId(R.id.ll_user_info)
//                .setAlpha(150)
//                .setHighTargetCorner(20)
//                .setHighTargetPadding(10)
//                .setOverlayTarget(false)
//                .setOutsideTouchable(false);
//        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
//            @Override
//            public void onShown() {
//            }
//
//            @Override
//            public void onDismiss() {
//                try {
////                    showGuideView2();
//                    showGuideViewitem2();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
////        MutiComponent mMutiComponent = new MutiComponent(context,"听听别人唱的怎么样点这里",true);
//        SimpleComponent mMutiComponent = new SimpleComponent(context, context.getString(R.string.tosing_diange), true);
//        builder.addComponent(mMutiComponent);
//        Guide guide = builder.createGuide();
//        guide.setShouldCheckLocInWindow(true);
//        guide.show((Activity) context);
//    }
//
//    public void showGuideViewitem2() {
//        if (guide_show == null) {
//            return;
//        }
//
//        GuideBuilder builder = new GuideBuilder();
//        builder.setTargetView(guide_show)
//                .setFullingViewId(R.id.rl_recommend_all)
//                .setAlpha(150)
//                .setHighTargetCorner(20)
//                .setHighTargetPadding(1)
//                .setOverlayTarget(false)
//                .setOutsideTouchable(false);
//        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
//            @Override
//            public void onShown() {
//            }
//
//            @Override
//            public void onDismiss() {
////                try {
////                    showGuideView2();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }
//        });
////        MutiComponent mMutiComponent = new MutiComponent(context,"听听别人唱的怎么样点这里",true);
//        SimpleComponent mMutiComponent = new SimpleComponent(context, context.getString(R.string.tosing_listtening), false);
//        builder.addComponent(mMutiComponent);
//        Guide guide = builder.createGuide();
//        guide.setShouldCheckLocInWindow(true);
//        guide.show((Activity) context);
//    }
//
//    //    public void showGuideView2() {
////        GuideBuilder builder = new GuideBuilder();
////        builder.setTargetView(guide_show)
////                .setTextContext("想立即唱歌点这里")
////                .setAlpha(150)
////                .setHighTargetCorner(20)
////                .setHighTargetPadding(2)
////                .setOverlayTarget(false)
////                .setOutsideTouchable(false);
////        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
////            @Override
////            public void onShown() {
////            }
////
////            @Override
////            public void onDismiss() {
//////                try {
//////                    mFragmentHotL.mAdapter.showGuideViewitem();
//////                } catch (Exception e) {
//////                    e.printStackTrace();
//////                }
////            }
////        });
////        LogUtils.sysout("56465465");
////        SimpleComponent mMutiComponent = new SimpleComponent(context, context.getString(R.string.tosing_listtening), false);
//////        mSimpleComponent.setContent("想立即唱歌点这里");
////        builder.addComponent(mMutiComponent);
////        Guide guide = builder.createGuide();
////        guide = builder.createGuide();
////        guide.setShouldCheckLocInWindow(false);
////        guide.show((Activity) context);
////    }
//    //接口回调
//    public interface onItemToSingListener {
//        void onItemToSing(CurrentPeriodBean currentPeriodBean, int positon);
//    }
//
//    private onItemToSingListener onItemToSingListener;
//
//    public void setOnToSingListener(onItemToSingListener onItemToSingListener) {
//        this.onItemToSingListener = onItemToSingListener;
//    }
//}
