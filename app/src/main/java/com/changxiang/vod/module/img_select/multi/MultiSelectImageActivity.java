package com.changxiang.vod.module.img_select.multi;//package tosingpk.quchangkeji.com.quchangpk.module.img_select.multi;
//
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
//import android.widget.TextView;
//
////import com.quchangkeji.tosing.R;
////import com.quchangkeji.tosing.common.utils.AppUtil;
////import com.quchangkeji.tosing.common.utils.LogUtils;
////import com.quchangkeji.tosing.module.entry.ImageFloder;
////import com.quchangkeji.tosing.module.ui.base.BaseActivity;
////import com.quchangkeji.tosing.module.ui.img_select.ShowImageActivity;
////import com.quchangkeji.tosing.module.ui.img_select.adapter.AlbumDialog;
////import com.quchangkeji.tosing.module.ui.img_select.adapter.AlbumDialog.AlbumDialogItemClick;
////import com.quchangkeji.tosing.module.ui.img_select.adapter.ImageGridViewAdapter;
////import com.quchangkeji.tosing.module.ui.img_select.adapter.ImageGridViewAdapter.OnSelectItemTouch;
////import com.quchangkeji.tosing.module.ui.img_select.adapter.ImageModel;
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class MultiSelectImageActivity extends BaseActivity implements OnSelectItemTouch,OnClickListener,AlbumDialogItemClick{
//
////	@ViewInject(R.id.title_bar)
////	private NavigationView mNavigationView;//标题栏控件
//	private View mBackBtn;
//	private View mDoneBtn;
//
//	private TextView mDoneTxt;
//	private TextView mTitleTxt;
//
//	private GridView mGridview;
//
//	private TextView mAlbumTv;//所有图片的数量控件
//
//	private TextView mFileName;//当前文件名称
//
//
//	private List<ImageFloder> mImageDirs=new ArrayList<ImageFloder>();//相册文件列表
//
//	private List<ImageModel> mImageList=new ArrayList<ImageModel>();//图片列表
//
//	private HashSet<String> mParentFolders=new HashSet<String>();//用于存放已经扫描过的相册，避免重复扫描
//
//	private List<ImageModel> centlyList=new ArrayList<ImageModel>();//最近的照片列表
//
//	private List<String> mSelImageList=new ArrayList<String>();//被选中的图片的列表
//
//	private HashSet<String> mSelImageSet=new HashSet<String>();
//
////	private int mTotalCount;//所有文件夹图片文件加起来的总数
//
//	private int mSelectedCount;//被选中的图片的数量
//	private int MaxPicCount;//最多可以选图片数量
//
//
//	private ImageGridViewAdapter mAdapter;
//
//
//	@Override
//	protected void onCreate( Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.select_image_activity);
//		getIntentParams();
//		init();
//	}
//
//	private void getIntentParams(){
//		Intent intent=getIntent();
//		if(intent==null){
//			return;
//		}
//		MaxPicCount = intent.getIntExtra("maxpic", 6);
//		List<String> list=(List<String>) intent.getSerializableExtra("mSelectedImage");
//		if(list==null||list.isEmpty()){
//			return;
//		}
//
//		mSelImageList.addAll(list);
//		for(String str:mSelImageList){
//			mSelImageSet.add(str);
//		}
//	}
//
//	private void init(){
//		mBackBtn= AppUtil.findViewById(this, R.id.rl_bt_main_back);
//		mTitleTxt=AppUtil.findViewById(this, R.id.iv_main_toptext);
//		mAlbumTv=AppUtil.findViewById(this, R.id.pic_album);
//		mDoneBtn=AppUtil.findViewById(this, R.id.rl_bt_main_done);
//		mDoneTxt=AppUtil.findViewById(this, R.id.iv_pic_back);
//		mGridview=AppUtil.findViewById(this, R.id.gridview);
//		mFileName=AppUtil.findViewById(this, R.id.filename);
//		setTopNavigationBar();
//		mAlbumTv.setOnClickListener(this);
//		mAdapter=new ImageGridViewAdapter(this);
//		mAdapter.setOnSelectItemTouch(this);
//		mGridview.setAdapter(mAdapter);
//		mGridview.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//				ImageModel data=(ImageModel) parent.getItemAtPosition(position);
//				if(data==null){
//					return;
//				}
//				Intent intent=new Intent(MultiSelectImageActivity.this,ShowImageActivity.class);
//				intent.putExtra("url", data.imageUrl);
//				startActivity(intent);
//			}
//		});
//		getLoacalImages();
//	}
//
//	private void setTopNavigationBar() {
//
//		mBackBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				MultiSelectImageActivity.this.finishActivity();
//			}
//		});
//		mSelectedCount=mSelImageList.size();
//		mTitleTxt.setText(getString(R.string.select_image_title, mSelectedCount+"",MaxPicCount+""));
//		//设置右上角分享按钮
//		mDoneTxt.setText("完成");
//		mDoneBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				resultPic2qxr();
//			}
//		});
//	}
//
//	public void resultPic2qxr(){
//		Intent data = new Intent();
////		Log.i("测试返回图片地址：***************", mSelectedImage.toString());
//		Bundle bundle;
//		bundle = new Bundle();
//		bundle.putSerializable("mSelectedImage", (Serializable) mSelImageList);
////		canpic = data.getIntExtra("canpic", 0);
//		data.putExtras(bundle);
//		setResult(RESULT_OK, data);
////		mAdapter.detepiclist();
//		finishActivity();
//	}
//
//	private void setPicNum(String filename, int num){
//
//		mFileName.setText(filename+"  共"+num+"张");
//	}
//
//	public void getLoacalImages(){
//		if(!AppUtil.hasSdcard()){
//			toast("检查不到SD卡");
//			return;
//		}
//
//		showProgressDialog("正在扫描本地图片...", true);
//		new AsyncTask<String, Integer, String>() {
//			@Override
//			protected String doInBackground(String... params) {
//
//				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//				ContentResolver mContentResolver = getContentResolver();
//
//				Cursor mCursor = mContentResolver.query(mImageUri, null,
//						null,
//						null,
//						MediaStore.Images.Media.DATE_MODIFIED+" desc");
////				Cursor mCursor = mContentResolver.query(mImageUri, null,
////						MediaStore.Images.Media.MIME_TYPE + "=? or "
////								+ MediaStore.Images.Media.MIME_TYPE + "=?",
////								new String[] { "image/jpeg", "image/png" },
////								MediaStore.Images.Media.DATE_MODIFIED+" desc");
//				if(mCursor==null){
//					new RuntimeException("mCursor is null");
//					return "";
//				}
//				while(mCursor.moveToNext()){
//					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
//					LogUtils.log("scanFile", path);
//					File parentFile=new File(path).getParentFile();
//					if(!isFileExsits(parentFile)){//如果文件不存在则返回继续循环
//						continue;
//					}
//					if(centlyList.size()<99){//最近的照片
//
//						ImageModel model=new ImageModel();
//						if(mSelImageSet.contains(path)){
//							model.isSelected=true;
//						}else{
//							model.isSelected=false;
//						}
//						model.imageUrl=path;
//						centlyList.add(model);
//					}
//					String parentDir=parentFile.getAbsolutePath();
//					if(mParentFolders.contains(parentDir)){//如果文件被扫描过，则跳过；
//						continue;
//					}
//
//					mParentFolders.add(parentDir);
//					int count=getFileCount(parentFile);//获取该文件夹下图片的数量
//					if(count==0){
//						continue;
//					}
////					mTotalCount+=count;
//					ImageFloder imageFloder=new ImageFloder();
//					imageFloder.setDir(parentDir);
//					imageFloder.setFirstImagePath(path);
//					imageFloder.setCount(count);
//					mImageDirs.add(imageFloder);
//				}
//
//				ImageFloder floder=new ImageFloder();
//				floder.setDir("/最近拍摄");
//				floder.setCount(centlyList.size());
//				String firstUrl ="";
//				if(!centlyList.isEmpty()){
//					firstUrl=centlyList.get(0).imageUrl;
//				}
//				floder.setFirstImagePath(firstUrl);
//				mImageDirs.add(0,floder);//确保最近拍摄的放在文件列表第一个位置
//
//				mCursor.close();
//				return "";
//			}
//			@Override
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
//				closeProgressDialog();
//				updateList(mImageDirs.get(0).getName(),centlyList);
//			}
//			private boolean isFileExsits(File file){
//				if(file==null){
//					return false;
//				}
//				return file.exists();
//			}
//
//			private int getFileCount(File file){
//				String[]imageArray=file.list(new FilenameFilter() {
//
//					@Override
//					public boolean accept(File dir, String filename) {
//						if (filename.endsWith(".jpg")||filename.endsWith(".JPG")
//								|| filename.endsWith(".png")|| filename.endsWith(".PNG")
//								|| filename.endsWith(".jpeg")|| filename.endsWith(".JPEG"))
//							return true;
//						return false;
//					}
//				});
//				if(imageArray==null){
//					return 0;
//				}
//				return imageArray.length;
//			}
//		}.execute("");
//
//
//	}
//
//	private void updateList(String fileName, List<ImageModel> list){
//		mAdapter.setData(list);
//		mAdapter.notifyDataSetChanged();
//		setPicNum(fileName,list.size());//显示默认文件夹中图片的数量
//	}
//
//	public void getImageListInDir(final ImageFloder floder){
//
//		new AsyncTask<String, Integer, String>(){
//			@Override
//			protected String doInBackground(String... params) {
//				//selection: 指定查询条件
//				String selection = MediaStore.Images.Media.DATA + " like ?";
////				String selection = MediaStore.Images.Media.MIME_TYPE + "=? or "
////						+ MediaStore.Images.Media.MIME_TYPE + "=?"+" and "+MediaStore.Images.Media.DATA + " like ?";
//
//				//定义selectionArgs：
//				String[] selectionArgs = {"%"+floder.getDir()+"%"};
////				String[] selectionArgs = {"image/jpeg", "image/png","%"+dir+"%"};
//				Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
//				                                selection, selectionArgs, MediaStore.Images.Media.DATE_MODIFIED+" desc");
//				if(cursor==null){
//					new RuntimeException("cursor is null");
//					return "";
//				}
//				mImageList.clear();
//				while(cursor.moveToNext()){
//					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//					ImageModel model=new ImageModel();
//					if(mSelImageSet.contains(path)){
//						model.isSelected=true;
//					}else{
//						model.isSelected=false;
//					}
//					model.imageUrl=path;
//					mImageList.add(model);
////					LogUtil.e("paht",path+"");
//				}
//				cursor.close();
//				return "";
//			}
//			@Override
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
////				setPicNum(floder.getName(),floder.getCount());//显示默认文件夹中图片的数量
////				mAdapter.setData(mImageList);
////				mAdapter.notifyDataSetChanged();
//				updateList(floder.getName(), mImageList);
//			}
//
//		}.execute("");
//
//	}
//
//	@Override
//	public void onItemtTouch(ImageGridViewAdapter.ViewHolder holder,ImageModel data) {
//
//		if(data.isSelected){
//			mSelImageList.remove(data.imageUrl);
//			mSelImageSet.remove(data.imageUrl);
//			mSelectedCount--;
//			holder.isSelected.setImageResource(R.drawable.picture_unselected);
//			holder.touchView.setVisibility(View.GONE);
//			data.isSelected=false;
//			if(mSelImageList.isEmpty()){
//				return;
//			}
//		}else{
//			if(mSelectedCount>=MaxPicCount){
//				toast("最多选择 "+MaxPicCount+" 张图片");
//				return;
//			}
//			mSelImageList.add(data.imageUrl);
//			mSelImageSet.add(data.imageUrl);
//			mSelectedCount++;
//			holder.isSelected.setImageResource(R.drawable.pictures_selected);
//			holder.touchView.setVisibility(View.VISIBLE);
//			data.isSelected=true;
//		}
//		mTitleTxt.setText(getString(R.string.select_image_title, mSelectedCount+"",MaxPicCount+""));
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch(v.getId()){
//		case R.id.pic_album:
//			AlbumDialog dialog=new AlbumDialog(MultiSelectImageActivity.this);
//			dialog.setList(mImageDirs);
//			dialog.setAlbumDialogItemClick(this);
//			dialog.show();
//			break;
//		}
//	}
//
//	@Override
//	public void onAlbumItemClick(ImageFloder item,int position) {
//		if(position==0){
//			updateList(mImageDirs.get(position).getName(), centlyList);
//			return;
//		}
//		getImageListInDir(mImageDirs.get(position));
////		updateList(mImageDirs.get(position).getName(), list)
//	}
//
//	@Override
//	public void handMsg(Message msg) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
