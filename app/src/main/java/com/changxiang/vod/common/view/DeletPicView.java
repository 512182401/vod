//package com.changxiang.vod.common.view;
//
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//
////import com.gwkj.haohaoxiuchesf.R;
//
//public class DeletPicView extends LinearLayout {
//
//
//	private LinearLayout mLinearLayout;
//	private ImageView image;
//	private ImageButton button;
//	private int number=-1;
//	private ImageModel mImageModel;
//	public DeletPicView(Context context) {
//		super(context);
//		 LayoutInflater.from(context).inflate(R.layout.delet_pic_item, this, true);
//	        image = (ImageView) findViewById(R.id.id_item_image);
//	        button = (ImageButton) findViewById(R.id.id_item_delet);
//		mLinearLayout = (LinearLayout) findViewById(R.id.ll_had_edit);
//	        button.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					excuterQXRItem(number, thisone,mImageModel);
////					Toast.makeText(getContext(), "点击事件，删除当前图片，应该写成接口方式，给调用者使用。", Toast.LENGTH_LONG).show();
//				}
//			});
//		// TODO Auto-generated constructor stub
//	}
//	public DeletPicView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//		 // 导入布局
//        LayoutInflater.from(context).inflate(R.layout.delet_pic_item, this, true);
//        image = (ImageView) findViewById(R.id.id_item_image);
//        button = (ImageButton) findViewById(R.id.id_item_delet);
//		mLinearLayout = (LinearLayout) findViewById(R.id.ll_had_edit);
//	}
//	/**
//     * 设置图片资源
//     */
//    public void setImageResource(int resId) {
//    	image.setImageResource(resId);
//    }
//    public void setImageUri(String uri) {
////    	image.setImageURI(uri);
//    	image.setImageBitmap(BitmapFactory.decodeFile(uri.toString()));
//    }
//    /**
//     * 设置右上角图片
//     */
//    public void setbuttonResource(int resId) {
//		if(resId==0){
//			button.setVisibility(GONE);
//		}else{
//			button.setVisibility(VISIBLE);
//			button.setImageResource(resId);
//		}
//
//    }
//    /**
//     * 设置右上角图片
//     */
//    public void setWidthHeight(int width ,int height){
//    	image.setMaxWidth(width);
//    	image.setMaxHeight(height);
//
//    }
//	public ImageView getImageviewid(int num, ImageModel mImageModel) {
//		// TODO Auto-generated method stub
//		number=num;
//		this.mImageModel=mImageModel;
//		return image;
//	}
//	public LinearLayout getLinearLayoutid(int num, ImageModel mImageModel) {
//		// TODO Auto-generated method stub
//		number=num;
//		this.mImageModel=mImageModel;
//		return mLinearLayout;
//	}
//
//	private DeletPic thisone;
//
//
//	public DeletPic getThisone() {
//		return thisone;
//	}
//	public void setThisone(DeletPic thisone) {
//		this.thisone = thisone;
//	}
//
//	public interface DeletPic{
//		void deletPic(int number, ImageModel path);
//	}
//
//	private void excuterQXRItem(int nn,DeletPic deletpic,ImageModel path){
//		if(number>=0)
//			deletpic.deletPic(number,path);
//	}
//
//
////    public
//
//}
