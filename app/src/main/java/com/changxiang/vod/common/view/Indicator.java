package com.changxiang.vod.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Indicator extends View {

    private Paint paint=new Paint();

    private int selctedColor= Color.parseColor("#5F5E5E");

    private int defaultColor= Color.parseColor("#9E9BA0");
    //页面个数,当前页
    private int count=10;
    private int index;
    //两圆之间的距离
    private int padding=20;
    //圆的半径
    private int radius=12;
    //第一个圆的起始坐标
    private int fromX,fromY;

    public Indicator(Context context) {
        super(context);
    }

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置抗锯齿
        paint.setAntiAlias(true);

    }
    //设置圆的间距
    public void setPadding(int padding){
        this.padding=padding;
    }
    //设置圆的半径
    public void setRadius(int radius){
        this.radius=radius;
    }
    //获取页面的个数
    public void setCount(int count){
        this.count=count;
        //invalidate();
    }
    //设置当前页
    public void setIndex(int index){
        this.index=index;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //确定圆的位置，并画圆
        int height=canvas.getHeight();
        int width=canvas.getWidth();
        int leftSpace=(width-(radius*2*count+padding*(count-1)))/2;
        for (int i=0;i<count;i++){
            fromX=radius+i*(padding+2*radius)+leftSpace;
            fromY=canvas.getHeight()/2;
            if (i==index){
                paint.setColor(selctedColor);

            }else {
                paint.setColor(defaultColor);
            }
            canvas.drawCircle(fromX,fromY,radius,paint);
        }



    }
}
