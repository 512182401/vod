package com.changxiang.vod.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.DensityUtil;


/**
 * Created by teana on 2017/10/20.
 */

public class RotateTextView extends TextView {
    private Context context;

    public String getMyText() {
        return myText;
    }

    public void setMyText(String myText) {
        this.myText = myText;
    }

    private String myText;

    public RotateTextView(Context context) {
        super(context);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.rotate_text);
        if (attributes != null) {
            myText = attributes.getString(R.styleable.rotate_text_mytext);//自定义属性
        }
        attributes.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画三角形
        Path p = new Path();//通过此对象绘制一个轨迹
        p.moveTo(0, 0);
//        p.lineTo(100, 0);
//        p.lineTo(0, 100);
        p.lineTo(DensityUtil.dip2px(context, 50), 0);
        p.lineTo(0, DensityUtil.dip2px(context, 50));
        p.close();//形成闭环
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getResources().getColor(R.color.alpa_white_20f));
        canvas.drawPath(p, paint);

        //倾斜度45,上下左右居中
        canvas.rotate(-45, getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        //文本
        paint.setColor(Color.WHITE);
        paint.setTextSize(DensityUtil.sp2px(context, 12));
//        canvas.drawText(myText, 50,40,paint);
        canvas.drawText(myText, DensityUtil.dip2px(context, 28), DensityUtil.dip2px(context, 12), paint);
        super.onDraw(canvas);
    }

}