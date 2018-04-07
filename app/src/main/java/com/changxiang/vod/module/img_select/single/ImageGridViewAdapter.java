package com.changxiang.vod.module.img_select.single;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.module.img_select.BaseViewHolderAdapter;
import com.changxiang.vod.module.img_select.adapter.ImageModel;



public class ImageGridViewAdapter extends BaseViewHolderAdapter<ImageModel, ImageGridViewAdapter.ViewHolder> {
//	private void 
	int width=0;
	public ImageGridViewAdapter(Context ctx) {
		super(ctx);
		width=(ctx.getResources().getDisplayMetrics().widthPixels-15)/3;
	}

	public static class ViewHolder{
		public ImageView image;
		public View touchView;
		public ImageView isSelected;
	}

	@Override
	public View createItemView(LayoutInflater inflater, int position) {
		return inflater.inflate(R.layout.select_single_image_gridview_item, null);
	}

	@Override
	public ViewHolder createAndBindViewHolder(View view, int position) {
		ViewHolder holder=new ViewHolder();
		view.setLayoutParams(new AbsListView.LayoutParams(width, width));
		holder.image= AppUtil.findViewById(view, R.id.image);
		holder.touchView= AppUtil.findViewById(view, R.id.touchview);
		holder.isSelected=AppUtil.findViewById(view, R.id.selected_flag);
		return holder;
	}

	@Override
	public void bindDataToView(final ViewHolder holder, final ImageModel data, int position) {
		loadImage(data.imageUrl, R.drawable.default_icon, holder.image);
//		if(data.isSelected){
//			holder.touchView.setVisibility(View.VISIBLE);
//			holder.isSelected.setImageResource(R.drawable.pictures_selected);
//		}else{
//			holder.isSelected.setImageResource(R.drawable.picture_unselected);
//			holder.touchView.setVisibility(View.GONE);
//		}
//		holder.isSelected.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(mOnSelectItemTouch==null){
//					return;
//				}
////				togleTouchView(holder, data);
//				mOnSelectItemTouch.onItemtTouch(holder,data);
//			}
//		});
	}
	
	private void togleTouchView( ViewHolder holder, ImageModel data){
		if(data.isSelected){
			holder.isSelected.setImageResource(R.drawable.picture_unselected);
			holder.touchView.setVisibility(View.GONE);
			data.isSelected=false;
		}else{
			holder.isSelected.setImageResource(R.drawable.pictures_selected);
			holder.touchView.setVisibility(View.VISIBLE);
			data.isSelected=true;
		}
	}
	
	public interface OnSelectItemTouch{
		void onItemtTouch(ViewHolder holder, ImageModel data);
	}
	OnSelectItemTouch mOnSelectItemTouch;
	public OnSelectItemTouch getOnSelectItemTouch() {
		return mOnSelectItemTouch;
	}

	public void setOnSelectItemTouch(OnSelectItemTouch selectItemTouch) {
		this.mOnSelectItemTouch = selectItemTouch;
	}
	
}
