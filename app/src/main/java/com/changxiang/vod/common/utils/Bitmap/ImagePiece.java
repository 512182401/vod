package com.changxiang.vod.common.utils.Bitmap;

import android.graphics.Bitmap;

/**
 * Bitmap操作类, 不要轻易修改
 */
public class ImagePiece {

		private Bitmap bitmap ;

		public ImagePiece(){
	}

		public ImagePiece(Bitmap bitmap){
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap(){
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
}