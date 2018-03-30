package com.changxiang.vod.common.utils.Bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;


/**
 * Bitmap操作类, 不要轻易修改
 */
public class BitmapBlind {
	private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;
	private static int MB = 1024 * 1024;
	//百叶窗
	public static Bitmap Blind(Bitmap bitmap, int n){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);

		//垂直方向上的百叶窗
		boolean _direct = false;//horizontal: true,  vertical: false

		int _width = w / n; //10个
		int _opacity = 100;
		int _color = 0x00000000;

		int r, g, b, a, color;
		int[] oldPx = new int[w * h];
		int[] newPx = new int[w * h];

		bitmap.getPixels(oldPx, 0, w, 0, 0, w, h);

		for(int x = 0 ; x < (w - 1) ; x++){
			for(int y = 0 ; y < (h - 1) ; y++){
				color = oldPx[x * h + y];
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);

				int  nMod = 0 ;
				if (_direct) // 水平方向
					nMod = y % _width ;
				else if (_direct == false) // 垂直方向
					nMod = x % _width ;

				double fDelta = 255.0 * (_opacity/100.0) / (_width-1.0);
				a = Function.FClamp0255(nMod * fDelta) ;
				int colorR = _color & 0xFF0000 >> 16;
				int colorG = _color & 0x00FF00 >> 8;
				int colorB = _color & 0x0000FF;
				if (_color == 0xFF)
				{
					newPx[x * h + y] = Color.rgb(colorR, colorG, colorB);
					continue ;
				}
				if (a == 0)
					continue ;

				int t = 0xFF - a ;
				newPx[x * h + y] = Color.rgb((colorR * a + r * t) / 0xFF,
						(colorG * a + g * t) / 0xFF, (colorB * a + b * t) / 0xFF);
			}
		}
		result.setPixels(newPx, 0, w, 0, 0, w, h);
		return result;
	}

	/**
	 * 给图片添加纹理
	 * @param bmp  原图
	 * @param overlay  纹理图
	 * @return
	 */
	public static Bitmap Overlay(Bitmap bmp, Bitmap overlay) {

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

		// 对边框图片进行缩放
		int w = overlay.getWidth();
		int h = overlay.getHeight();
		float scaleX = width * 1F / w;
		float scaleY = height * 1F / h;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);

		Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);

		int pixColor = 0;
		int layColor = 0;

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int pixA = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;
		int newA = 0;

		int layR = 0;
		int layG = 0;
		int layB = 0;
		int layA = 0;

		final float alpha = 0.5F;

		int[] srcPixels = new int[width * height];
		int[] layPixels = new int[width * height];
		bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);
		overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);

		int pos = 0;
		for (int i = 0; i < height; i++){
			for (int k = 0; k < width; k++){
				pos = i * width + k;
				pixColor = srcPixels[pos];
				layColor = layPixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				pixA = Color.alpha(pixColor);

				layR = Color.red(layColor);
				layG = Color.green(layColor);
				layB = Color.blue(layColor);
				layA = Color.alpha(layColor);

				newR = (int) (pixR * alpha + layR * (1 - alpha));
				newG = (int) (pixG * alpha + layG * (1 - alpha));
				newB = (int) (pixB * alpha + layB * (1 - alpha));
				layA = (int) (pixA * alpha + layA * (1 - alpha));

				//检查各点新的像素值是否超出范围
				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));
				newA = Math.min(255, Math.max(0, layA));

				srcPixels[pos] = Color.argb(newA, newR, newG, newB);
			}
		}
		bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	//拼图
	public static Bitmap Pintu(Bitmap bitmap){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap resultBitmap = Bitmap.createBitmap(w - 4, h - 4, Bitmap.Config.ARGB_8888);

		List<ImagePiece> mItemBitmaps;
		//切图 3*3    得到9个碎片
		mItemBitmaps = ImageSplitterUtil.splitImage(bitmap, 3);

		List<ImagePiece> imagePieces = new ArrayList<ImagePiece>();

		Canvas cv = new Canvas(resultBitmap);
		int pieceWidth = w / 3;
		int pieceHeight = h / 3;

		int w2 = 0;
		int h2 = 0;

		//对每个碎片随机产生一种效果，并保存到数组中
		for(int s = 0; s < mItemBitmaps.size(); s++){
			int type = (int) (Math.random() * 13);

			Bitmap tempBitmap = mItemBitmaps.get(s).getBitmap();
			ImagePiece imagePiece = new ImagePiece();

//			imagePiece.setBitmap(pintu(tempBitmap, type));//滤镜
			imagePiece.setBitmap(tempBitmap);
			imagePieces.add(imagePiece);
		}

		//在画布上将每个经过处理后的碎片重新绘制到画布上
		for(int i = 0; i < imagePieces.size(); i++){
			if(i == 3 || i == 6){
				w2 = w2 + pieceWidth - 2;
				h2 = 0;
			}else if(i != 0){
				h2 += pieceHeight - 2;
			}
			cv.drawBitmap(imagePieces.get(i).getBitmap(), w2, h2, null);
			cv.save(Canvas.ALL_SAVE_FLAG);
			cv.restore();
		}
		return resultBitmap;
	}

}