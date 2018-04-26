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

import com.changxiang.vod.module.base.AdapterCommonListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.changxiang.vod.R;
import com.changxiang.vod.common.view.CircleImageView;
import com.changxiang.vod.module.entry.NearPerson;

import java.util.ArrayList;
import java.util.List;


public class NearbyPersonAdapter extends BaseAdapter implements OnClickListener {

    private List<NearPerson.ResultsBean> singerList = new ArrayList<NearPerson.ResultsBean>();
    private Context context;

    private int ImageOnFail = R.drawable.default_icon;// 下载失败时的图片名称

    private int selectPos = -1;
    private Animation mAnimation;

    public NearbyPersonAdapter(Context context) {
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
//        return singerList == null ? 0 : singerList.size();
        return 5;
    }

    @Override
    public NearPerson.ResultsBean getItem(int position) {
        if (singerList != null && singerList.size() > position) {
            return singerList.get(position);
        }
        return null;
    }

    public NearPerson.ResultsBean getItemData(int selectPos) {
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
        TextView user_name;//歌手名 tv_name
        ImageView sex;//性别 tv_sex
        TextView dengji;//等级 tv_content
        TextView distance;//距离 tv_distance
        TextView level_tag;//tv_level_tag 等级名称
        ImageView level_tag_image;//im_level_tag_image 等级图标
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int conn = position;
        final NearPerson.ResultsBean item = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_discover_nearby_person, null);
            holder = new ViewHolder();
            holder.showimager = (CircleImageView) convertView.findViewById(R.id.showimager);//头像
            holder.sex = (ImageView) convertView.findViewById(R.id.iv_sex);////歌手名 tv_name性别 tv_sex
            holder.user_name = (TextView) convertView.findViewById(R.id.tv_name);//歌手名 tv_name
            holder.dengji = (TextView) convertView.findViewById(R.id.tv_dengji);//等级 tv_content
            holder.distance = (TextView) convertView.findViewById(R.id.tv_distance);//距离 tv_distance
            holder.level_tag_image = (ImageView) convertView.findViewById(R.id.im_level_tag_image);
            holder.level_tag = (TextView) convertView.findViewById(R.id.tv_level_tag);//tv_level_tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            if (item.getSex() != null && item.getSex().equals("2")) {//为女性
                holder.sex.setImageResource(R.mipmap.discover_nearby_person_wman);
            } else {
                holder.sex.setImageResource(R.mipmap.discover_nearby_person_man);
            }
            holder.dengji.setText("Lv" + item.getLevel());
            holder.distance.setText(item.getDistance());
//		// 取得网络头像：
//		int uid = 0;
            String Userpic = item.getImgHead();
            if (Userpic == null || Userpic.equals("")) {// 没有图片，图片为空
//			ImageOnFail = ResultAdapterShow.showCircleImageView(holder.showimager, getItem(position).getUserid());
                holder.showimager.setImageResource(ImageOnFail);
            } else {
                getImageViewLoa(holder.showimager, Userpic, ImageOnFail);

            }
            holder.level_tag.setVisibility(View.VISIBLE);
            holder.level_tag_image.setVisibility(View.VISIBLE);
            holder.level_tag.setText(item.getLevelName());
//            com.changxiang.vod.common.utils.ImageLoader.getImageViewLoad(holder.level_tag_image, item.getLevelImg(), R.mipmap.qc_level_tag01);
            String nick;
            if (item.getName() == null || item.getName().equals("")) {
                nick = "趣唱人";
            } else {
                nick = item.getName();
            }
            holder.user_name.setText(nick);
        }


        return convertView;
    }


    private AdapterCommonListener<NearPerson.ResultsBean> listener;

    public AdapterCommonListener<NearPerson.ResultsBean> getListener() {
        return listener;
    }

    public void setListener(AdapterCommonListener<NearPerson.ResultsBean> listener) {
        this.listener = listener;
    }

    private void excuterQXRItem(int type, NearPerson.ResultsBean item) {
        if (listener != null)
            listener.click(type, item);
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

    public void setDataList(List<NearPerson.ResultsBean> list) {
        if (singerList != null && !singerList.isEmpty()) {
            singerList.clear();
        }
        singerList.addAll(list);
        notifyDataSetChanged();
    }

    public void addDataList(List<NearPerson.ResultsBean> list) {
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
