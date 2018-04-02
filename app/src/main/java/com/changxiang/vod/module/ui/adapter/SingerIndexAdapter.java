package com.changxiang.vod.module.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.view.CircleImageView;
import com.changxiang.vod.module.db.Singers;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class SingerIndexAdapter extends BaseAdapter implements OnClickListener {

    private List<Singers> singerList = new ArrayList<>();
    private Context context;

    private int ImageOnFail = R.drawable.default_icon;// 下载失败时的图片名称

    private int selectPos = -1;
    private Animation mAnimation;

    public SingerIndexAdapter(Context context) {
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
//		return 10;
    }

    @Override
    public Singers getItem(int position) {
        if (singerList != null && singerList.size() > position) {
            return singerList.get(position);
        }
        return null;
    }

    public Singers getItemData(int selectPos) {
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
        CircleImageView showimager;//头像
        TextView hostinfo;//昵称
        TextView songerinfo;//歌曲数量
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int conn = position;
        final Singers item = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_singer_index, null);
            holder = new ViewHolder();
            holder.showimager = (CircleImageView) convertView.findViewById(R.id.showimager);
            holder.hostinfo = (TextView) convertView.findViewById(R.id.tv_name);
            holder.songerinfo = (TextView) convertView.findViewById(R.id.tv_songer_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {

//		// 取得网络头像：
//		int uid = 0;
            String Userpic = item.getPicture();
            if (Userpic == null || Userpic.equals("")) {// 没有图片，图片为空
//			ImageOnFail = ResultAdapterShow.showCircleImageView(holder.showimager, getItem(position).getUserid());

            } else {
//			String iconUrl = NetInterface.SERVER_IMG_PRE + item.getUserpic();
                getImageViewLoa(holder.showimager, Userpic, ImageOnFail);

            }

            String nick;
            if (item.getName() == null || item.getName().equals("")) {
                nick = "趣唱人";
            } else {
                nick = item.getName();
            }
            holder.hostinfo.setText(nick);
            try {
//				holder.songerinfo.setText("MP3("+item.getMp3num()+"首)/MV("+item.getMvnum()+"首)");
                holder.songerinfo.setText(item.getSongsCount() + "首)");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return convertView;
    }


    public void getImageViewLoa(ImageView mImageView, String imageUrl, int ImageOnFail) {
        // 显示图片的配置
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(ImageOnFail)
                // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(ImageOnFail)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(ImageOnFail)
                // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)
                // 是否緩存都內存中
                .cacheOnDisc(true)
                // 是否緩存到sd卡上
                .cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);
        // mImageView.setImageBitmap(ViewUtil.getCircleBitmap(mImageView.getDrawingCache()));
    }

    public void setDataList(List<Singers> list) {
        if (singerList != null && !singerList.isEmpty()) {
            singerList.clear();
        }
        singerList.addAll(list);
        notifyDataSetChanged();
    }

    public void addDataList(List<Singers> list) {
        if (singerList != null) {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.showimager:// 点击了头像

                break;
            default:
                break;
        }

    }

}
