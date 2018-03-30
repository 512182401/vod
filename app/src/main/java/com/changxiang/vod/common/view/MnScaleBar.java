package com.changxiang.vod.common.view;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 带进度条的WebView
 */
@SuppressWarnings("deprecation")
public class MnScaleBar extends View {

    private Context mContext;

    private Rect mRect;

    private int max = 100; //最大刻度
    private int mCountScale; //滑动的总刻度

    private int screenWidth = 720; //默认屏幕分辨率

    private int mScaleMargin = 10; //刻度间距
    private int mScaleHeight = 20; //刻度线的高度
    private int mScaleMaxHeight = mScaleHeight * 2; //整刻度线高度

    private int mRectWidth = max * mScaleMargin; //总宽度
    private int mRectHeight = 150; //高度

    //    private Scroller mScroller;
    private int mScrollLastX;

    private int mTempScale = screenWidth / mScaleMargin / 2; //判断滑动方向
    private int mScreenMidCountScale = screenWidth / mScaleMargin / 2; //中间刻度


    private String tag = MnScaleBar.class.getSimpleName();

    public MnScaleBar(Context context) {
        this(context, null);
    }

    public MnScaleBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MnScaleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        screenWidth = getPhoneW(mContext);//获取手机分辨率

        mTempScale = screenWidth / mScaleMargin / 2; //判断滑动方向
        mScreenMidCountScale = screenWidth / mScaleMargin / 2; //中间刻度

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 200;
        int width = 200;
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSpecSize = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {


        max = canvas.getWidth(); //最大刻度  600
        mScaleMargin = canvas.getWidth() / 10; //刻度间距
        mScaleHeight = 20; //刻度线的高度
        mScaleMaxHeight = mScaleHeight * 2; //整刻度线高度

        mRectWidth = canvas.getWidth(); //总宽度
        mRectHeight = canvas.getHeight(); //高度


        //设置LayoutParams
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mRectWidth, mRectHeight);
        this.setLayoutParams(lp);
        //相对位置坐标
        mRect = new Rect(0, 0, mRectWidth, mRectHeight);
        //画笔
        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);


//        canvas.drawRect(mRect, mPaint);

        onDrawScale(canvas); //画刻度
//        onDrawPointer(canvas); //画指针

        super.onDraw(canvas);
    }

    /**
     * 画刻度
     */
    private void onDrawScale(Canvas canvas) {
        if (canvas == null) return;
        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER); //文字居中
        mPaint.setTextSize(20);
        for (int i = 0; i < max; i++) {
            if (i != 0 && i != max) {
                canvas.drawLine(i * mScaleMargin, mRectHeight, i * mScaleMargin, mRectHeight - mScaleMaxHeight, mPaint);
            }
        }
    }


    /**
     * 获取手机分辨率--W
     */
    public static int getPhoneW(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int disW = dm.widthPixels;
        return disW;
    }

}