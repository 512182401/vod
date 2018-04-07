package com.changxiang.vod.module.ui.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.changxiang.vod.R;

import java.util.List;


public class SearchRecordsAdapter extends BaseAdapter {
    private Context context;
    private List<String> searchRecordsList;
    private LayoutInflater inflater;

    public SearchRecordsAdapter(Context context, List<String> searchRecordsList) {
        this.context = context;
        this.searchRecordsList = searchRecordsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchRecordsList.size() == 0 ? 0 : searchRecordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchRecordsList.size() == 0 ? null : searchRecordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_saerch_records_list,null);
            viewHolder.recordTv = (TextView) convertView.findViewById(R.id.search_content_tv);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String content = searchRecordsList.get(position);
        viewHolder.recordTv.setText(content);

        return convertView;
    }

    private class ViewHolder {
        TextView recordTv;
    }
}