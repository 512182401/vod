//package com.changxiang.vod.module.ui.typemusic.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.module.entry.TypeMusic;
//import com.quchangkeji.tosing.module.ui.typemusic.TypeListActivity;
//
//import java.util.List;
//
///**
// * Created by 15976 on 2016/11/26.
// */
//
//public class TypeNameAdapter2 extends RecyclerView.Adapter<TypeNameAdapter2.NameHold> {
//    private Context context;
//    private View itemView;
//    private LayoutInflater inflater;
//    List<TypeMusic.ListBean> typeNameList;
//    String type;//分类模块名称
//    TypeMusic.ListBean listBean;
//
//    public TypeNameAdapter2(Context context, List<TypeMusic.ListBean> typeNameList, String type) {
//        this.context = context;
//        inflater= LayoutInflater.from(context);
//        this.typeNameList=typeNameList;
//        this.type=type;
//
//    }
//
//    @Override
//    public NameHold onCreateViewHolder(ViewGroup parent, int viewType) {
//        itemView=inflater.inflate(R.layout.adapter_recycle_type,null);
//        return new NameHold(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(NameHold holder, final int position) {
//        if (position>=typeNameList.size()){
//            holder.imageView.setVisibility(View.INVISIBLE);
//        }else {
//            listBean=typeNameList.get(position);
//            String typeName="";
//            boolean isHot=false;
//            if (listBean!=null){
//                typeName=listBean.getTypename();
//                if ("1".equals(listBean.getTypestat())){
//                    isHot=true;
//                    holder.imageView.setVisibility(View.VISIBLE);
//                }else {
//                    isHot=false;
//                    holder.imageView.setVisibility(View.INVISIBLE);
//                }
//                if (typeName!=null){
//                    holder.textView.setText(typeName);
//                }
//            }
//        }
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(typeNameList.size()>position) {
//                    Intent intent = new Intent(context, TypeListActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //传递数据
//                    String code = typeNameList.get(position).getCode();
//                    intent.putExtra("code", code);
//                    intent.putExtra("type", type);
//                    if (code != null) {
//                        context.startActivity(intent);
//                    }
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (typeNameList==null){
//            return 0;
//        }if (typeNameList.size()%4==0){
//            return typeNameList.size();
//        }
//        return (typeNameList.size()/4+1)*4;
//
//    }
//
//    class NameHold extends RecyclerView.ViewHolder{
//        private TextView textView;
//        private ImageView imageView;
//        public NameHold(View itemView) {
//            super(itemView);
//            textView= (TextView) itemView.findViewById(R.id.adapter_typeName);
//            imageView= (ImageView) itemView.findViewById(R.id.adapter_ivHot);
//        }
//    }
//}
