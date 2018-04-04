package com.changxiang.vod.module.ui.recently.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.changxiang.vod.R;
import com.changxiang.vod.module.ui.recently.db.HistoryDBManager;
import com.changxiang.vod.module.ui.recently.db.PlayedHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 15976 on 2016/10/18.
 */

public class RecentlyEditAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private HashMap<Integer, Boolean> isSelected = new HashMap<>();
    List<PlayedHistory> list;
    public ArrayList<String> ids=new ArrayList<>();
    public ArrayList<String> names=new ArrayList<>();
    public ArrayList<String> singerNames=new ArrayList<>();
    //public ArrayList<String> isChecked=new ArrayList<>();
    EditHolder holder = null;
    public RecentlyEditAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list= HistoryDBManager.getHistoryManager().queryAll();
        //按时间降序
        Collections.sort(list, new Comparator<PlayedHistory>() {
            @Override
            public int compare(PlayedHistory o1, PlayedHistory o2) {
                return (int) (o2.getDate()-o1.getDate());
            }
        });
        for (int i=0;i<list.size();i++){
            String id=list.get(i).getSongId();
            String name=list.get(i).getName();
            String singerName=list.get(i).getSingerName();
            ids.add(id);
            names.add(name);
            singerNames.add(singerName);

        }
        if (ids!=null&&names!=null&&ids.size()==names.size()){
            isSelected.clear();
            for (int i=0;i<ids.size();i++){
                isSelected.put(i,false);
            }
        }
    }

    @Override
    public int getCount() {
        if (ids!=null&&names!=null&&ids.size()==names.size()){
            return ids.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_bulk_edit, null);
            holder = new EditHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (EditHolder) convertView.getTag();
        holder.singer.setText(singerNames.get(position));
        holder.song.setText(names.get(position));
        holder.checkBox.setChecked(isSelected.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                        isSelected.put(position,true);
                    }else {
                        isSelected.put(position,false);
                    }
            }
        });

        return convertView;
    }

    /**
     * 通过该方法可以修改checkbox状态
     * @param isSelected 保存checkbox状态的HashMap
     */
    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }

    public HashMap<Integer, Boolean> getIsSelected(){

        return isSelected;
    }
    /**
     * 获取指定位置checkBox的状态
     * @param postion item的位置
     * @return
     */
    public boolean getIsSelected(int postion){
        return isSelected.get(postion);
    }

    public void setItemIsSelected(int postion,boolean flag){
        isSelected.put(postion,flag);
        //setIsSelected(isSelected);
    }


    private class EditHolder {
        private CheckBox checkBox;
        private TextView song, singer;

        public EditHolder(View convertView) {
            checkBox = (CheckBox) convertView.findViewById(R.id.adapter_recently_item_cb);
            song = (TextView) convertView.findViewById(R.id.adapter_recently_item_tvSong);
            singer = (TextView) convertView.findViewById(R.id.adapter_recently_item_tvSinger);

        }
    }
}
