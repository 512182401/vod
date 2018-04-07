//package com.changxiang.vod.module.ui.typemusic.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.module.entry.Singer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class TypeZYAdapter extends BaseAdapter implements OnClickListener {
//
//    private List<Singer> singerList = new ArrayList<Singer>();
//    private Context context;
//
//    private int ImageOnFail = R.mipmap.imagehead;// 下载失败时的图片名称
//
//    private int selectPos = -1;
//    private Animation mAnimation;
//
//    public TypeZYAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void setSelectPos(int selectPos) {
//        if (this.selectPos == selectPos) {
//            return;
//        }
//        this.selectPos = selectPos;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
////		return singerList == null ? 0 : singerList.size();
//        return 5;
//    }
//
//    @Override
//    public Singer getItem(int position) {
//        if (singerList != null && singerList.size() > position) {
//            return singerList.get( position );
//        }
//        return null;
//    }
//
//    public Singer getItemData(int selectPos) {
//        if (singerList != null) {
//            return singerList.get( selectPos );
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
//        RelativeLayout image_text;
//        TextView showimager;//用于高度为两倍的图文
//        TextView content;//用于高度为40dp的文
//        RelativeLayout text;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final int conn = position;
//        final Singer item = getItem( position );
//
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = View.inflate( context, R.layout.item_typemusic, null );
//            holder = new ViewHolder();
//            holder.showimager = (TextView) convertView.findViewById( R.id.tv_zongyi );
//            holder.content = (TextView) convertView.findViewById( R.id.tv_type_name );
//            holder.image_text = (RelativeLayout) convertView.findViewById( R.id.rl_zongyi );
//            holder.text = (RelativeLayout) convertView.findViewById( R.id.rl_zy_zghsy );
//            convertView.setTag( holder );
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
////		if(item!=null) {
//
////		// 判断是否为第一个：
//        if (position == 0) {//第一个，则使用图文
//            holder.image_text.setVisibility( View.VISIBLE );
//            holder.text.setVisibility( View.GONE );
//            convertView.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 200 ) );
//
//        } else {//使用文字
//            convertView.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 100 ) );
//            holder.image_text.setVisibility( View.GONE );
//            holder.text.setVisibility( View.VISIBLE );
//            holder.content.setText( "类型" + position );
//
//        }
////		}
//
//
//        return convertView;
//    }
//
//
//    public void getImageViewLoa(ImageView mImageView, String imageUrl, int ImageOnFail) {
//        // 显示图片的配置
//        @SuppressWarnings("deprecation")
//        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage( ImageOnFail )
//                // 设置图片在下载期间显示的图片
//                .showImageForEmptyUri( ImageOnFail )
//                // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail( ImageOnFail )
//                // 设置图片加载/解码过程中错误时候显示的图片
//                .cacheInMemory( true )
//                // 是否緩存都內存中
//                .cacheOnDisc( true )
//                // 是否緩存到sd卡上
//                .cacheInMemory( true ).cacheOnDisk( true ).bitmapConfig( Bitmap.Config.RGB_565 ).build();
//
//        ImageLoader.getInstance().displayImage( imageUrl, mImageView, options );
//        // mImageView.setImageBitmap(ViewUtil.getCircleBitmap(mImageView.getDrawingCache()));
//    }
//
//    public void setDataList(List<Singer> list) {
//        if (singerList != null && !singerList.isEmpty()) {
//            singerList.clear();
//        }
//        singerList.addAll( list );
////		notifyDataSetChanged();
//    }
//
//    public void addDataList(List<Singer> list) {
//        if (singerList != null) {
//            singerList.addAll( list );
////			notifyDataSetChanged();
//        }
//    }
//
//    public void clear() {
//        if (singerList != null) {
//            singerList.clear();
//            notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.showimager:// 点击了头像
//
//                break;
//            default:
//                break;
//        }
//
//    }
//
//}
