//package com.changxiang.vod.module.ui.typemusic.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.ImageLoader;
//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.common.utils.SuperPlayerUtils;
//import com.quchangkeji.tosing.module.entry.TypeMusic;
//import com.quchangkeji.tosing.module.musicInfo.DisplayUtil;
//import com.quchangkeji.tosing.module.ui.typemusic.TypeListActivity;
//
//import java.util.List;
//
///**
// * Created by 15976 on 2016/11/26.
// */
//
//public class TypeNameAdapter extends RecyclerView.Adapter<TypeNameAdapter.NameHold> {
//    private Context context;
//    private View itemView;
//    private LayoutInflater inflater;
//    private List<TypeMusic.ListBean> typeNameList;
//    private String type;//分类模块名称
//    private int sWidth;//屏幕宽度
//
//    public TypeNameAdapter(Context context, List<TypeMusic.ListBean> typeNameList, String type) {
//        this.context = context;
//        inflater= LayoutInflater.from(context);
//        this.typeNameList=typeNameList;
//        this.type=type;
//        sWidth= SuperPlayerUtils.getScreenWidth((Activity) context);
//
//    }
//
//    @Override
//    public NameHold onCreateViewHolder(ViewGroup parent, int viewType) {
////        itemView=inflater.inflate(R.layout.adapter_recycle_type,null);
//        itemView=inflater.inflate(R.layout.adapter_recycle_type_new,null);
//        return new NameHold(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(NameHold holder, int position) {
//
//        final TypeMusic.ListBean listBean=typeNameList.get(position);
//        String typeName;
//        String typeCover;
//        boolean isHot=false;
//        if (listBean!=null){
//            typeName=listBean.getTypename();
//            typeCover=listBean.getTypeImg();
//            /*if ("1".equals(listBean.getTypestat())){
//                isHot=true;
//                holder.imageView.setVisibility(View.VISIBLE);
//            }else {
//                isHot=false;
//                holder.imageView.setVisibility(View.INVISIBLE);
//            }*/
//            if (typeName!=null){
//                holder.textView.setText(typeName);
//            }
//            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
//            params.height=(sWidth- DisplayUtil.dip2px(context,30))/3;
//            holder.imageView.setLayoutParams(params);
//            ImageLoader.getImageViewLoad(holder.imageView,typeCover,R.drawable.default_icon);
//
//        }
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, TypeListActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                //传递数据
//                String code=listBean.getCode();
//                intent.putExtra("code",code);
//                intent.putExtra("type",type);
//                LogUtils.sysout( "跳转到：TypeListActivity code ="+code+"type = "+type );
//                if (code!=null){
//                    context.startActivity(intent);
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return typeNameList==null?0:typeNameList.size();
//        /*if (typeNameList==null){
//            return 0;
//        }if (typeNameList.size()<6){
//            for (int i=0;i<=6-typeNameList.size();i++){
//                TypeMusic.ListBean listBean=new TypeMusic.ListBean();
//                listBean.setTypestat("0");
//                typeNameList.add(listBean);
//            }
//        }
//        return 6;*/
//    }
//
//    class NameHold extends RecyclerView.ViewHolder{
//        private TextView textView;
//        private ImageView imageView;
//        public NameHold(View itemView) {
//            super(itemView);
//            textView= (TextView) itemView.findViewById(R.id.adapter_type_name);
//            imageView= (ImageView) itemView.findViewById(R.id.adapter_type_cover);
//        }
//    }
//}
