package com.changxiang.vod.module.img_select.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.AppUtil;
import com.changxiang.vod.module.entry.ImageFloder;

import java.util.ArrayList;
import java.util.List;

//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.AppUtil;
//import com.quchangkeji.tosing.module.entry.ImageFloder;


public class AlbumDialog extends Dialog {
	
	private Context mCtx;
	private View mBackBtn;
	
	private ListView mListView;
	
	private List<ImageFloder> mList=new ArrayList<ImageFloder>();//相册文件列表
	
	private ImageLoadHelper mImageLoaderHelper;
	
	private MyAdapter mAdapter;

	public AlbumDialog(Context context, int theme) {
		super(context, theme);
		mCtx=context;
	}

	public AlbumDialog(Context context) {
		super(context, R.style.albumDialogStyle);
		mCtx=context;
	}

	protected AlbumDialog(Context context, boolean cancelable,
                          OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mCtx=context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_image_album_dialog);
		LayoutParams params=this.getWindow().getAttributes();
		params.gravity= Gravity.RIGHT;
		params.width=mCtx.getResources().getDisplayMetrics().widthPixels;
		params.height=mCtx.getResources().getDisplayMetrics().heightPixels;
		getWindow().setAttributes(params);
		initView();
	}
	
	private void initView(){
		mBackBtn= findViewById(R.id.rl_bt_main_back);
		mListView=(ListView) findViewById(R.id.album_listview);
		setNavBar();
		mAdapter=new MyAdapter(getContext());
		mAdapter.setData(mList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
				ImageFloder floder=(ImageFloder) parent.getItemAtPosition(position);
				itemclick(floder,position);		
				dismiss();
			}
		});
	}
	
	public void setList(List<ImageFloder> list){
		if(!mList.isEmpty()){
			mList.clear();
		}
		mList.addAll(list);
	}
	
	private void setNavBar(){
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	private class MyAdapter extends BaseAdapter {
		List<ImageFloder> mlist=new ArrayList<ImageFloder>();
		private Context mctx;
		public MyAdapter(Context ctx){
			mctx=ctx;
		}
		class ViewHolder{
			public ImageView imageView;
			public TextView fileName;
			public TextView numTxt;
			public ImageView checkImageView;
			
		}

		@Override
		public int getCount() {
			return mlist.size();
		}

		@Override
		public ImageFloder getItem(int position) {
			return mlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView= LayoutInflater.from(mctx).inflate(R.layout.select_image_album_dialog_list_item, null);
				holder.checkImageView= AppUtil.findViewById(convertView, R.id.checkimg);
				holder.fileName= AppUtil.findViewById(convertView, R.id.filename);
				holder.imageView=AppUtil.findViewById(convertView, R.id.id_dir_item_image);
				holder.numTxt=AppUtil.findViewById(convertView,R.id.numtxt);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			ImageFloder model=getItem(position);
			
			holder.checkImageView.setVisibility(View.GONE);
			holder.fileName.setText(model.getName());
			holder.numTxt.setText(model.getCount()+"张");
			loadImage(holder.imageView, model.getFirstImagePath());
			return convertView;
		}
		
		public void setData(List<ImageFloder> list){
			if(!mlist.isEmpty()){
				mlist.clear();
			}
			mlist.addAll(list);
			notifyDataSetChanged();
		}
	}
	
	private void loadImage(ImageView imageView, String url){
		if(mImageLoaderHelper==null){
			mImageLoaderHelper=new ImageLoadHelper(getContext());
		}
		mImageLoaderHelper.loadImage(url, imageView);
	}
	
	public interface AlbumDialogItemClick{
		void onAlbumItemClick(ImageFloder item, int position);
	}
	AlbumDialogItemClick mAlbumDialogItemClick;

	public AlbumDialogItemClick getAlbumDialogItemClick() {
		return mAlbumDialogItemClick;
	}

	public void setAlbumDialogItemClick(AlbumDialogItemClick mAlbumDialogItemClick) {
		this.mAlbumDialogItemClick = mAlbumDialogItemClick;
	}
	
	private void itemclick(ImageFloder item,int position){
		if(item==null){
			return;
		}
		if(mAlbumDialogItemClick==null){
			return;
		}
		mAlbumDialogItemClick.onAlbumItemClick(item,position);
	}
}
