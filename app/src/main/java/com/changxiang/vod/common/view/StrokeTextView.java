package com.changxiang.vod.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.changxiang.vod.R;


/**
 * Created by Maijx on 2015/5/6.
 */
public class StrokeTextView extends TextView {

    private int strokeColor;
    private int strokeWidth;

    private TextPaint strokePaint;

    public StrokeTextView(Context context) {
        super(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView, defStyleAttr, 0);

        strokeColor = a.getColor(R.styleable.StrokeTextView_tv_border_color, 0);
        strokeWidth = a.getDimensionPixelSize(R.styleable.StrokeTextView_tv_border_width, 0);
    }

    public void setStrokeColor(int color){
        strokeColor=color;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (strokeWidth > 0) {
            if (strokePaint == null) {
                strokePaint = new TextPaint();
            }

            // 复制原来TextViewg画笔中的一些参数
            TextPaint paint = getPaint();
            strokePaint.setTextSize(paint.getTextSize());
            strokePaint.setFlags(paint.getFlags());
            strokePaint.setTypeface(paint.getTypeface());
            strokePaint.setAlpha(paint.getAlpha());

            // 自定义描边效果
            strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);// 设置不同Style有不同效果哟
            strokePaint.setColor(strokeColor);
            strokePaint.setStrokeWidth(strokeWidth);

            String text = getText().toString();

            //在文本底层画出带描边的文本
//            canvas.drawText(text, (getWidth() - strokePaint.measureText(text)) / 2, getBaseline(), strokePaint);
        }

        super.onDraw(canvas);
    }
}
