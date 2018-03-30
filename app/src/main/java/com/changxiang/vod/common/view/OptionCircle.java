package com.changxiang.vod.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

//import com.quchangkeji.tosing.R;


public class OptionCircle extends android.support.v7.widget.AppCompatImageView {

	private final Paint paint;
	private final Context context;
	boolean clicked = false;// 是否被点击
	boolean addBackground = false;
	int radius = -1;      // 半径值初始化为-1
	int centerOffsetX = 0;// 圆圈原点的偏移量x
	int centerOffsetY = 0;// 偏移量y
	int colorCircle;      // 圆圈颜色
	int colorBackground;  // 背景填充颜色
	int colorText;        // 文字颜色
	String textCircle = "";

	public OptionCircle(Context context) {
		this(context, null);
	}

	public OptionCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setStyle(Paint.Style.STROKE);
		colorCircle = Color.argb(205, 245, 2, 51);// 默认颜色
		colorText = colorCircle;      // 字体颜色默认与圈圈颜色保持一致
		colorBackground = colorCircle;// 设定默认参数
	}
	// 属性设置方法
	public void setRadius(int r) {
		this.radius = r;
	}

	public void setCenterOffset(int x, int y) {
		this.centerOffsetX = x;
		this.centerOffsetY = y;
	}

	public void setColorCircle(int c) {
		this.colorCircle = c;
	}

	public void setColorText(int c) {
		this.colorText = c;
	}

	public void setColorBackground(int c) {
		this.colorBackground = c;
	}

	public void setText(String s) {
		this.textCircle = s;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void setAddBackground(boolean add) {
		this.addBackground = add;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int center = getWidth() / 2;// 当前宽度的一半
		int innerCircle = getWidth() / 3;       // 默认半径为86
		if (radius > 0) {
			innerCircle = dip2px(context, radius); // 如果没有另外设置半径，取半径86
		}

		Drawable drawable = getDrawable();
		if (addBackground) {
		} else {
			// 画圈圈；被点击后会变成实心的圈圈，默认是空心的
//			this.paint.setStyle(clicked ? Paint.Style.FILL : Paint.Style.STROKE);
			this.paint.setStyle(Paint.Style.FILL);
			this.paint.setColor(clicked ? colorBackground : colorCircle);
			this.paint.setStrokeWidth(1.5f);
			canvas.drawCircle(center + centerOffsetX, center + centerOffsetY,
					innerCircle, this.paint);// 画圆圈时带上偏移量
		}

		// 绘制文字
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setStrokeWidth(1);
		this.paint.setTextSize(22);
		this.paint.setTypeface(Typeface.MONOSPACE);// 设置一系列文字属性
		this.paint.setColor(clicked ? Color.WHITE : colorText);
		this.paint.setTextAlign(Paint.Align.CENTER);// 文字水平居中
		Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		canvas.drawText(textCircle, center + centerOffsetX,
				center + centerOffsetY - (fontMetrics.top + fontMetrics.bottom) / 2, this.paint);// 设置文字竖直方向居中
		super.onDraw(canvas);
	}

	/**
	 * convert dp to px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}