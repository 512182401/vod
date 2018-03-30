package com.changxiang.vod.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.changxiang.vod.R;


/**
 * Created by 15976 on 2017/1/10.
 */

public class BorderImage extends ImageView {
    private int color= getResources().getColor(R.color.app_oher_red);//图片边框的颜色
    private int borderwidth=1;//图片边框的宽度

    public BorderImage(Context context) {
        super(context);
    }

    public BorderImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setBorderwidth(int borderwidth) {
        this.borderwidth = borderwidth;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (borderwidth<=0){
            return;
        }
        // 画边框
        Rect rec = canvas.getClipBounds();
        rec.bottom--;
        rec.right--;
        Paint paint = new Paint();
        //设置边框颜色
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        //设置边框宽度

        paint.setStrokeWidth(borderwidth);
        canvas.drawRect(rec, paint);
    }
}
