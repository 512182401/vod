package com.changxiang.vod.module.ui.localmusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.DensityUtil;
import com.changxiang.vod.common.utils.FileUtil;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.RegValUtil;
import com.changxiang.vod.common.view.FlikerProgressBar;
import com.changxiang.vod.common.view.RotateTextView;
import com.changxiang.vod.module.base.AdapterCommonListener;
import com.changxiang.vod.module.db.LocalCompose;

import java.util.ArrayList;
import java.util.List;

public class MyProAdapter extends BaseAdapter {
    int rowWidth;
    private List<LocalCompose> singerList = new ArrayList<>();
    private List<Boolean> chechList;
    LocalCompose bean;
    private Context context;
    int percent;//下载进度

    private int ImageOnFail = R.drawable.tv_mv;// 下载失败时的图片名称

    private int selectPos = -1;
    private Animation mAnimation;
    public boolean deleteState = false;

    public MyProAdapter(Context context) {
        rowWidth = context.getResources().getDisplayMetrics().widthPixels - DensityUtil.dip2px(context, 20);//宽高比例为2：1
        this.context = context;
    }

    public void setSelectPos(int selectPos) {
        if (this.selectPos == selectPos) {
            return;
        }
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return singerList == null ? 0 : singerList.size();
//        return 6;
    }

    @Override
    public LocalCompose getItem(int position) {
        if (singerList != null && singerList.size() > position) {
            return singerList.get(position);
        }
        return null;
    }

    public LocalCompose getItemData(int selectPos) {
        if (singerList != null) {
            return singerList.get(selectPos);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView showimager;//头像
        TextView name;//歌名 tv_name
        TextView times;//时间 tv_times
        TextView tv_mv;//MV
        TextView file_size;//tv_file_size
        Button get_compose;//合成 bt_get_commit_share
        Button commit_share;//上传 bt_get_commit_share
        Button get_upload;//上传 bt_get_upload
        FlikerProgressBar flikerProgressBar;//进度条
        RelativeLayout deleteRl;//取消上传
        RelativeLayout local_music_info;//rl_local_music_info
        CheckBox deleteCb;//删除图标
        RotateTextView overImage;//删除图标
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int conn = position;
        if (singerList != null && singerList.size() > 0 && singerList.size() > position) {

        } else {
            return convertView;
        }
        final LocalCompose item = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_local_music_index, null);
            holder = new ViewHolder();
            holder.showimager = (ImageView) convertView.findViewById(R.id.showimager);
            holder.name = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvSong);
            holder.times = (TextView) convertView.findViewById(R.id.adapter_topmusic_tvtime);
            holder.tv_mv = (TextView) convertView.findViewById(R.id.adapter_topmusic_tag);//mv
            holder.file_size = (TextView) convertView.findViewById(R.id.tv_file_size);//
            holder.commit_share = (Button) convertView.findViewById(R.id.bt_get_commit_share);
            holder.get_compose = (Button) convertView.findViewById(R.id.bt_get_compose);
            holder.get_upload = (Button) convertView.findViewById(R.id.bt_get_upload);
            holder.flikerProgressBar = (FlikerProgressBar) convertView.findViewById(R.id.flikerProgressBar);
            holder.deleteRl = (RelativeLayout) convertView.findViewById(R.id.delete_rl);
            holder.local_music_info = (RelativeLayout) convertView.findViewById(R.id.rl_local_music_info);
            holder.deleteCb = (CheckBox) convertView.findViewById(R.id.delete_cb);
            holder.overImage = (RotateTextView) convertView.findViewById(R.id.iv_home_over);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//		RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(rowWidth/2,rowWidth*9/32);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.showimager.getLayoutParams();
        params.width = rowWidth / 2;
        params.height = rowWidth * 9 / 32;
        if (item != null) {
            //覆盖物
            try {
                LogUtils.sysout("aaaaaaaaaaaaa111"  + item.getAllDuration());
                LogUtils.sysout("aaaaaaaaaaaaa111==="  + item.getCompose_finish());
                int songDuration = Integer.parseInt(item.getAllDuration());
                int finishDuration = Integer.parseInt(item.getCompose_finish());
                LogUtils.sysout("aaaaaaaaaaaaa222222");
                if (songDuration - finishDuration < 3000){
                    holder.overImage.setVisibility(View.GONE);
                }else {
                    holder.overImage.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //删除图片显示判断
            if (deleteState) {
                holder.deleteCb.setVisibility(View.VISIBLE);
                //删除相关
                if ("1".equals(item.getIsExport())) {//没有选中
                    holder.deleteCb.setChecked(false);
                } else {//选中
                    holder.deleteCb.setChecked(true);
                }
            }else {
                holder.deleteCb.setVisibility(View.GONE);
            }

//		// 取得网络头像：
//		int uid = 0;
            String Userpic = item.getCompose_image();
            if (Userpic == null || Userpic.equals("")) {// 没有图片，图片为空
//			ImageOnFail = ResultAdapterShow.showCircleImageView(holder.showimager, getItem(position).getUserid());
                holder.showimager.setImageResource(ImageOnFail);
            } else {
                if (RegValUtil.containsString(Userpic, item.getCompose_id())) {
                    com.changxiang.vod.common.utils.ImageLoader.displayFromSDCard(Userpic, holder.showimager);
                } else {
                    com.changxiang.vod.common.utils.ImageLoader.getImageViewLoad(holder.showimager, Userpic, ImageOnFail);
                }
            }
            holder.showimager.setLayoutParams(params);
            holder.name.setText(item.getCompose_name());
            holder.times.setText(item.getCreateDate());
            // composeType？ 0://MP3和MP3合成：1://MP4和MP4合成 2://录制MP4和下载MP3合成3://录制MP3和下载MP4合成,4://录制mp3和DIY，5：录制清唱录音；6：清唱录像；7：清唱录音和录像合成
            int mvType = 1;
            if (item.getCompose_type() != null && item.getCompose_type().equals("7")) {
                mvType = 7;
            } else if (item.getCompose_type() != null && item.getCompose_type().equals("0")) {
                mvType = 4;
            } else if (item.getCompose_type() != null && item.getCompose_type().equals("5")) {
                mvType = 4;
            } else if (item.getCompose_type() != null && item.getCompose_type().equals("3")) {
                mvType = 1;
            } else if (item.getCompose_type() != null && item.getCompose_type().equals("4")) {
                mvType = 3;
            } else {
                mvType = 2;
            }
            String recordUrl = FileUtil.getFileName(item.getRecordUrl());
            int allDuration = Integer.parseInt(item.getCompose_finish());
            int isupload = 0;
            int muxerTask = 0;
            try {
                isupload = Integer.parseInt(item.getIsUpload());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                muxerTask = Integer.parseInt(item.getCompose_MuxerTask());
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.tv_mv.setVisibility(View.VISIBLE);
            switch (mvType) {// 1 KTV  2  MV  3  DIY MV   4 mp3
                case 0:
                    holder.tv_mv.setText("KTV");
                    break;
                case 1:
                    holder.tv_mv.setText("KTV");
                    break;
                case 2:
                    holder.tv_mv.setText("M V");
                    break;
                case 3:
                    holder.tv_mv.setText("DIY");
                    break;
                case 4:
                    holder.tv_mv.setText("MP3");
//
                    break;
                case 7:
                    holder.tv_mv.setText("M V");
                    break;
            }

            //isUpload 是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
            //Compose_MuxerTask 是否合成成功：0：初始状态（刚刚入库）；1：已经合成完成；2：等待合成状态；:3：合成进行中；4：合成失败（文件不存在）
//            item
//            LogUtils.sysout("45465464635 item.getCreateDate()" + item.getCreateDate());
//            LogUtils.sysout("45465464635 mvType" + mvType);
//            LogUtils.sysout("45465464635 muxerTask" + muxerTask);
//            LogUtils.sysout("45465464635 isupload" + isupload);
{
                holder.get_compose.setVisibility(View.GONE);
                switch (isupload) {
                    case 0:
                        holder.commit_share.setVisibility(View.VISIBLE);
                        holder.get_upload.setVisibility(View.GONE);
                        holder.flikerProgressBar.setVisibility(View.GONE);
                        holder.deleteRl.setVisibility(View.GONE);
                        break;
                    case 1:
                        holder.commit_share.setVisibility(View.GONE);
                        holder.get_upload.setVisibility(View.VISIBLE);
                        holder.flikerProgressBar.setVisibility(View.GONE);
                        holder.deleteRl.setVisibility(View.GONE);
                        break;
                    case 2:
                        holder.flikerProgressBar.reset();
                        holder.commit_share.setVisibility(View.GONE);
                        holder.get_upload.setVisibility(View.GONE);
                        holder.flikerProgressBar.setVisibility(View.VISIBLE);
                        holder.deleteRl.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        holder.flikerProgressBar.reset();
                        holder.commit_share.setVisibility(View.GONE);
                        holder.get_upload.setVisibility(View.GONE);
                        holder.flikerProgressBar.setVisibility(View.VISIBLE);
                        if (percent >= 0 && percent <= 100) {
                            holder.flikerProgressBar.setProgress(percent);
                        }
                        holder.deleteRl.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        holder.commit_share.setVisibility(View.VISIBLE);
                        holder.get_upload.setVisibility(View.GONE);
                        holder.flikerProgressBar.setVisibility(View.GONE);
                        holder.deleteRl.setVisibility(View.GONE);
                        break;
                }

            }


            double filesize = FileUtil.getFileOrFilesSize(singerList.get(position).getCompose_file(), 3);
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
            holder.file_size.setText("(" + df.format(filesize) + "M)");

            holder.commit_share.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    excuterQXRItem(1, getItem(position));//上传分享
                }
            });
            holder.get_upload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.sysout("跳转到我的主页");
                    excuterQXRItem(2, getItem(position));//我的主页
                }
            });
        /*holder.local_music_info.setOnClickListener( new OnClickListener() {
            @Override
			public void onClick(View v) {
				LogUtils.sysout("跳转到本地播放界面。");
				excuterQXRItem( 8, getItem( position ) );//我的主页
			}
		} );*/
            final int finalMvType = mvType;
            holder.flikerProgressBar.setOnClickListener(new View.OnClickListener() {//停止、取消上传。
                @Override
                public void onClick(View v) {
                    if (finalMvType != 3) {//不是是DIY
                        if (singerList.get(position).getIsUpload().equals("3")) {
                            excuterQXRItem(5, getItem(position));
                        } else {
                            excuterQXRItem(4, getItem(position));
                        }
                    } else {
                        if (singerList.get(position).getCompose_MuxerTask().equals("3")) {
                            excuterQXRItem(7, getItem(position));
                        } else {
                            excuterQXRItem(7, getItem(position));
                        }

                    }
//				singerList.get(position).setIsUpload("0");
                    notifyDataSetChanged();
                }
            });

            holder.deleteRl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalMvType != 3) {//不是是DIY
                        if (singerList.get(position).getIsUpload().equals("3")) {
                            excuterQXRItem(5, getItem(position));
                        } else {
                            excuterQXRItem(4, getItem(position));
                        }
                    } else {
                        if (singerList.get(position).getCompose_MuxerTask().equals("3")) {
                            excuterQXRItem(7, getItem(position));
                            singerList.remove(getItem(position));
                        } else {
                            excuterQXRItem(7, getItem(position));
                            singerList.remove(getItem(position));
                        }

                    }
//				singerList.get(position).setIsUpload("0");
                    notifyDataSetChanged();
                }
            });

            holder.get_compose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalMvType != 3) {//不是是DIY
//                        if (singerList.get(position).getIsUpload().equals("3")) {
//                            excuterQXRItem(5, getItem(position));
//                        } else {
//                            excuterQXRItem(4, getItem(position));
//                        }
                    } else {
                        if (singerList.get(position).getCompose_MuxerTask().equals("4")) {
//                            excuterQXRItem(7, getItem(position));
//                            singerList.remove(getItem(position));
                        } else {
                            excuterQXRItem(7, getItem(position));
                            singerList.remove(getItem(position));
                        }

                    }
//				singerList.get(position).setIsUpload("0");
                    notifyDataSetChanged();
                }
            });
            holder.deleteCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        item.setIsExport("2");//非1
                    } else {
                        item.setIsExport("1");
                    }
                }
            });

        }
        return convertView;
    }

    //等待上传
    public void setWaitUpload(int position, String downloadSongId, String downloadMusicType) {
        /*this.downloadSongId=downloadSongId;
        this.downloadMusicType=downloadMusicType;*/
//		isDownloadList.put(position,1);//改为等待下载中状态
        notifyDataSetChanged();

    }

    //取消等待上传
    public void cancelWaiting(int position, String downloadSongId, String downloadMusicType) {
//		isDownloadList.put(position,0);//改为非下载中状态
        notifyDataSetChanged();
    }

    //更新上传进度的方法
    String compose_id;

    public void setUploadProgress(int position, int percent, String downloadSongId, String downloadMusicType) {
        this.compose_id = downloadSongId;
        this.percent = percent;
        position = getDownloadPosition(downloadSongId);
//		notifyDataSetChanged();
        if (position != -1) {
//			progressing.put(position,percent);
            notifyDataSetChanged();
        }
    }

    //更新合成进度的方法
    public void setComposeProgress(int position, int percent, String ComposeId, String downloadMusicType) {
        this.compose_id = ComposeId;
        this.percent = percent;
        position = getComposePosition(ComposeId);//获取正在上传的歌曲在当前列表中的位置,并将当期记录修改成正在合成中：
        if (position != -1) {
//			progressing.put(position,percent);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取正在合成的歌曲在当前列表中的位置
     * 是否合成成功：0：初始状态（刚刚入库）；1：已经合成完成；2：等待合成状态；:3：合成进行中；4：合成失败（文件不存在）Compose_MuxerTask
     *
     * @return
     */
    private int getComposePosition(String downloadSongId) {
        String id;
        String type;
        for (int i = 0; i < singerList.size(); i++) {
            bean = singerList.get(i);
            id = bean.getCompose_id();
            if (downloadSongId != null && downloadSongId.equals(id)) {
                //是否合成成功：0：初始状态（刚刚入库）；1：已经合成完成；2：等待合成状态；:3：合成进行中；4：合成失败（文件不存在）Compose_MuxerTask
//				if(!bean.getIsUpload().equals("3")) {//判断是否是下载进行中
                if (percent < 98) {//查看是否已经完成
//                    singerList.get(i).setIsUpload("3");
                    singerList.get(i).setCompose_MuxerTask("3");
                }
//				}
                return i;
            }
        }
        return -1;
    }


    /**
     * 获取正在上传的歌曲在当前列表中的位置
     *
     * @return
     */
    private int getDownloadPosition(String downloadSongId) {
        String id;
        String type;
        for (int i = 0; i < singerList.size(); i++) {
            bean = singerList.get(i);
            id = bean.getCompose_id();
            if (downloadSongId != null && downloadSongId.equals(id)) {
                //是否上传：0：初始状态（刚刚入库）；1：已经上传完成；2：等待上传状态；:3：上传进行中；4：上传失败（文件不存在）
//				if(!bean.getIsUpload().equals("3")) {//判断是否是下载进行中
                singerList.get(i).setIsUpload("3");
//				}
                return i;
            }
        }
        return -1;
    }

    public void setFinishImg(int position, String downloadSongId, String downloadMusicType) {
        this.percent = 0;
        String id;
        for (int i = 0; i < singerList.size(); i++) {
            bean = singerList.get(i);
            id = bean.getCompose_id();
            if (downloadSongId != null && downloadSongId.equals(id)) {
//				if(!bean.getIsUpload().equals("1")) {//判断是否是下载进行中
                singerList.get(i).setIsUpload("1");
//				}
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 合成DIY完成
     *
     * @param position
     * @param downloadSongId
     * @param downloadMusicType
     */
    public void setComposeFinishImg(int position, String downloadSongId, String downloadMusicType) {
        this.percent = 0;
        String id;
        for (int i = 0; i < singerList.size(); i++) {
            bean = singerList.get(i);
            id = bean.getCompose_id();
            if (downloadSongId != null && downloadSongId.equals(id)) {
//				if(!bean.getIsUpload().equals("1")) {//判断是否是下载进行中
                singerList.get(i).setIsUpload("0");
                singerList.get(i).setCompose_MuxerTask("1");
//                LogUtils.sysout("555555555555555 position" + position);
//                LogUtils.sysout("555555555555555 singerList.get(i)." + singerList.get(i).getCreateDate());
//				}
            }
        }
        notifyDataSetChanged();
    }

    //下载过程中网络断开或者服务器异常
    public void setOnError(int position, String downloadSongId, String downloadMusicType) {
        this.percent = 0;
        String id;
        for (int i = 0; i < singerList.size(); i++) {
            bean = singerList.get(i);
            id = bean.getCompose_id();
            if (downloadSongId != null && downloadSongId.equals(id)) {
//				if(!bean.getIsUpload().equals("0")) {//判断是否是下载进行中
                singerList.get(i).setIsUpload("0");
//				}
            }
        }
        notifyDataSetChanged();
    }

    //合成失败
    public void setComposeException(int position, String downloadSongId, String downloadMusicType) {
        this.percent = 0;
        String id;
        for (int i = 0; i < singerList.size(); i++) {
            bean = singerList.get(i);
            id = bean.getCompose_id();
            if (downloadSongId != null && downloadSongId.equals(id)) {
//				if(!bean.getIsUpload().equals("0")) {//判断是否是下载进行中
                singerList.get(i).setIsUpload("0");
//				}
            }
        }
        notifyDataSetChanged();
    }


    private AdapterCommonListener<LocalCompose> listener;

    public AdapterCommonListener<LocalCompose> getListener() {
        return listener;
    }

    public void setListener(AdapterCommonListener<LocalCompose> listener) {
        this.listener = listener;
    }

    private void excuterQXRItem(int type, LocalCompose item) {
        if (listener != null)
            listener.click(type, item);
    }

    public void setDataList(List<LocalCompose> list) {
        if (singerList != null && singerList.size() > 0) {
            singerList.clear();
        }
        if (list != null) {
            singerList.addAll(list);
            chechList = new ArrayList<>();
            for (int i = 0; i < singerList.size(); i++) {
                chechList.add(false);
            }
        }
        notifyDataSetChanged();
    }

    public void addDataList(List<LocalCompose> list) {
        if (singerList != null && list != null) {
            singerList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (singerList != null) {
            singerList.clear();
            notifyDataSetChanged();
        }
    }


}
