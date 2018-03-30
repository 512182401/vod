package com.changxiang.vod.common.utils.Bitmap;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片切块工具
 * @author KaipingZhou
 *
 */

public class ImageSplitterUtil
{
	/**
	 * @param bitmap
	 * @param piece
	 *            切成piece*piece块
	 * @return List<ImagePiece>
	 */
	public static List<ImagePiece> splitImage(Bitmap bitmap, int piece)
	{
		List<ImagePiece> imagePieces = new ArrayList<ImagePiece>();

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int pieceWidth = width / piece;
		int pieceHeight = height / piece;

		for (int i = 0; i < piece; i++){
			for (int j = 0; j < piece; j++){
				ImagePiece imagePiece = new ImagePiece();

				int x = i * pieceWidth;
				int y = j * pieceHeight;

				imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y,
						pieceWidth, pieceHeight));
				imagePieces.add(imagePiece);
			}
		}
		return imagePieces;
	}
}