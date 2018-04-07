//package com.changxiang.vod.module.ui.topmusic.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.changxiang.vod.R;
//import com.changxiang.vod.common.utils.ImageLoader;
//import com.changxiang.vod.common.utils.LogUtils;
//import com.changxiang.vod.module.entry.ListBean;
//import com.changxiang.vod.module.entry.TopMusic;
//import com.changxiang.vod.module.musicInfo.DisplayUtil;
//import com.changxiang.vod.module.ui.topmusic.MyItemClickListener;
//
//import java.util.List;
//
///**
// * Created by 15976 on 2016/10/17.
// */
//
//public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListHolder> {
//
//    //private
//    private Context context;
//    private View itemView;
//    private LayoutInflater inflater;
//    private List<TopMusic> list;
//    private MyItemClickListener mListener;
//
//    public MainListAdapter(List<TopMusic> list, Context context) {
//        this.context = context;
//        inflater= LayoutInflater.from(context);
//        this.list=list;
//        LogUtils.w("TAG","List长度："+list.size());
//    }
//
//    @Override
//    public MainListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        itemView=inflater.inflate(R.layout.adapter_top_choose_rv,null);
//
//        return new MainListHolder(itemView,mListener);
//    }
//
//    @Override
//    public void onBindViewHolder(MainListHolder holder, int position) {
//        final TopMusic topMusic=list.get(position);
//        String typename="";
//        String imgcover="";
//        String songname="";
//        String singername="";
//        if (topMusic!=null){
//            typename=topMusic.getTypename();
//            ListBean listBean=topMusic.getList();
//            if (listBean!=null){
//                imgcover=listBean.getimgAlbumUrl();
//                songname=listBean.getname();
//                singername=listBean.getsingerName();
//                holder.textView.setText(typename+"TOP1|"+songname+"--"+singername);
//                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                int width = wm.getDefaultDisplay().getWidth();
//                int imgWidth=(width- DisplayUtil.dip2px(context,30))/2;
//                int imgHeight=(imgWidth)*9/16;
//                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
//                params.height=imgHeight;
//                holder.imageView.setLayoutParams(params);
//                ImageLoader.getImageViewLoad(holder.imageView,imgcover,R.drawable.default_icon);
//            }
//
//        }
//
//        //设置文字，图片，事件
//        /*itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, TopListActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                //传递数据
//                String typeid=topMusic.getTypecode();
//                intent.putExtra("typeId",typeid);
//                context.startActivity(intent);
//            }
//        });*/
//
//    }
//
//    public void setOnItemClickListener(MyItemClickListener listener){
//        this.mListener = listener;
//    }
//
//    @Override
//    public int getItemCount() {
//        return list==null?0:list.size();
//    }
//
//    class MainListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private MyItemClickListener myItemClickListener;
//        private ImageView imageView;
//        private TextView textView;
//        public MainListHolder(View itemView, MyItemClickListener myItemClickListener) {
//            super(itemView);
//            imageView= (ImageView) itemView.findViewById(R.id.adapter_topChoose_iv);
//            textView= (TextView) itemView.findViewById(R.id.adapter_topChoose_tv);
//            this.myItemClickListener=myItemClickListener;
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (myItemClickListener!=null){
//                myItemClickListener.onItemClick(v,getPosition(),list.get(getPosition()).getTypecode());
//            }
//        }
//    }
//}
