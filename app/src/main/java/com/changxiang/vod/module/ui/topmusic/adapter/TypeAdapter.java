//package com.changxiang.vod.module.ui.topmusic.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.changxiang.vod.R;
//import com.changxiang.vod.common.utils.ImageLoader;
//import com.changxiang.vod.module.entry.HotSong;
//import com.changxiang.vod.module.entry.TypeTop;
//import com.changxiang.vod.module.ui.listening.MusicPlayActivity;
//import com.changxiang.vod.module.ui.topmusic.TopListActivity;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 15976 on 2016/10/17.
// */
//
//public class TypeAdapter extends BaseAdapter {
//
//    private Context context;
//
//    private LayoutInflater inflater;
//
//    private List<TypeTop> list=new ArrayList();
//
//    HotSong bean;
//
//    public TypeAdapter(List<TypeTop> list, Context context) {
//
//        this.context = context;
//        inflater= LayoutInflater.from(context);
//        this.list=list.subList(1,list.size());
//    }
//
//    @Override
//    public int getCount() {
//        return list==null?0:list.size();
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
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TypeHolder holder=null;
//        if (convertView==null){
//            convertView=inflater.inflate(R.layout.adapter_top_choose_lv,null);
//            holder=new TypeHolder(convertView);
//            convertView.setTag(holder);
//        }
//        holder= (TypeHolder) convertView.getTag();
//        TypeTop typeTop=list.get(position);
//        String typeName=typeTop.getTypename();
//        //typeid=typeTop.getTypecode();
//        List<HotSong> listBean= (ArrayList<HotSong>) typeTop.getList();
//        int sizevalue = listBean.size();
//
//        if(sizevalue>0){
//           try{
//               //设置文字，封面,事件
//               holder.tvTop.setText(typeName+"TOP10");
//               bean=listBean.get(0);
//               holder.top1.setText("1."+bean.getName()+"-"+bean.getSingerName());
//               ImageLoader.getImageViewLoad(holder.imageView,bean.getImgAlbumUrl(),R.drawable.default_icon);
//               bean=listBean.get(1);
//               holder.top2.setText("2."+bean.getName()+"-"+bean.getSingerName());
//               //ImageLoader.getImageViewLoad(holder.imageView,bean.getimgAlbumUrl(),R.drawable.default_icon);
//               bean=listBean.get(2);
//               holder.top3.setText("3."+bean.getName()+"-"+bean.getSingerName());
//               //ImageLoader.getImageViewLoad(holder.imageView,bean.getimgAlbumUrl(),R.drawable.default_icon);
//           }catch (Exception e){
//               e.printStackTrace();
//           }
//        }
//        holder.imageView.setOnClickListener(new MyClickListener(typeTop));
//        holder.top1.setOnClickListener(new MyClickListener(typeTop));
//        holder.top2.setOnClickListener(new MyClickListener(typeTop));
//        holder.top3.setOnClickListener(new MyClickListener(typeTop));
//
//        return convertView;
//    }
//    class MyClickListener implements View.OnClickListener{
//        int postion;//在id集合中的位置
//        TypeTop typeTop;
//        ArrayList<HotSong> listBeen;
//
//        List<HotSong> allSongList = new ArrayList<>();//用于封装动态列表数据（歌曲）
//        private ArrayList<String> ids=new ArrayList<>();
//        private ArrayList<String> types=new ArrayList<>();
//        private ArrayList<String> names=new ArrayList<>();
//        private ArrayList<String> singerNames=new ArrayList<>();
//        String id,type,name,singerName;//歌曲id,音乐类型（mp3,mv）,歌曲名
//        private String typeid;//歌曲类型
//
//        public MyClickListener(TypeTop typeTop){
//            this.typeTop=typeTop;
//            typeid=typeTop.getTypecode();
//            listBeen= (ArrayList<HotSong>) typeTop.getList();
//            ids.clear();
//            allSongList.clear();
//            HotSong mHotSong = new HotSong();
//            for (int i=0;i<listBeen.size();i++){
////                mHotSong = new HotSong();
////                mHotSong.setId(listBeen.get(i).getid());
////                mHotSong.setType(listBeen.get(i).getType());
////                mHotSong.setName(listBeen.get(i).getname());
////                mHotSong.setSingerName(listBeen.get(i).getsingerName());
////                mHotSong.setImgAlbumUrl(listBeen.get(i).getimgAlbumUrl());
////                mHotSong.setNum(listBeen.get(i).getnum());
////                mHotSong.setSize(listBeen.get(i).getsize());
//                allSongList.add(listBeen.get(i));
//
//                id=listBeen.get(i).getId();
//                type=listBeen.get(i).getType();
//                name=listBeen.get(i).getName();
//                singerName=listBeen.get(i).getSingerName();
//                ids.add(id);
//                types.add(type);
//                names.add(name);
//                singerNames.add(singerName);
//            }
//        }
//        @Override
//        public void onClick(View v) {
//            Intent intent=null;
//            switch (v.getId()){
//                case R.id.adapter_top_type_ivCover:
//                    intent=new Intent(context, TopListActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //传递数据
//                    intent.putExtra("typeId",typeid);
//                    context.startActivity(intent);
//                    break;
//                case R.id.adapter_top_type_tv1:
//                    postion=0;
//                    startMusic(intent,postion);
//                    break;
//                case R.id.adapter_top_type_tv2:
//                    postion=1;
//                    startMusic(intent,postion);
//                    break;
//                case R.id.adapter_top_type_tv3:
//                    postion=2;
//                    startMusic(intent,postion);
//                    break;
//            }
//        }
//        //启动音乐播放界面，并传递歌曲id集合和这首歌在集合中的位置
//        public void startMusic(Intent intent, int postion){
//            intent=new Intent(context, MusicPlayActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//
//            bundle.putSerializable("songList", (Serializable) allSongList);
//            bundle.putStringArrayList("ids",ids);
//            bundle.putStringArrayList("types",types);
//            bundle.putStringArrayList("names",names);
//            bundle.putStringArrayList("singerNames",singerNames);
//            intent.putExtras(bundle);
//            intent.putExtra("position",postion);
//            context.startActivity(intent);
//        }
//    }
//    private class TypeHolder{
//        //
//        private ImageView imageView;
//        private TextView tvTop;
//        private TextView top1,top2,top3;
//
//        public TypeHolder(View convertView) {
//
//            imageView= (ImageView) convertView.findViewById(R.id.adapter_top_type_ivCover);
//            tvTop= (TextView) convertView.findViewById(R.id.adapter_top_type_tvTop);
//            top1= (TextView) convertView.findViewById(R.id.adapter_top_type_tv1);
//            top2= (TextView) convertView.findViewById(R.id.adapter_top_type_tv2);
//            top3= (TextView) convertView.findViewById(R.id.adapter_top_type_tv3);
//        }
//    }
//}
