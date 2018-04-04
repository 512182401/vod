package com.changxiang.vod.module.ui.recordmusic.screen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.ui.recordmusic.recordutils.ExtAudioRecorder;

import java.util.ArrayList;

/**
 * 通过获取mic的振幅和分贝，A
 * 通过获取播放器的振幅和分贝 B
 * 通过获取Krc的当前值D
 * <p>
 * 通过对比AB是否接近一个阀值C的区域（C-n：C+n）；
 * 可以得到一个以D为基准线的值E
 * <p>
 * 通过E画出录音起伏线，并打分
 */
public class SoundMeter extends View {
    static final float PIVOT_RADIUS = 3.5f;
    static final float PIVOT_Y_OFFSET = 10f;
    static final float SHADOW_OFFSET = 2.0f;
    static final float DROPOFF_STEP = 0.18f;
    static final float SURGE_STEP = 0.35f;
    static final long ANIMATION_INTERVAL = 70;
    private int rowhight = 30;//音调显示间隔
    Paint mPaint, mShadow;
    Paint mPaint1, mPaint2, mPaint3, mPaint4, mPaint5, mPaint6, mPaint7, mPaint8, mPaint9, mPaint10;
    float mCurrentAngle;

    ExtAudioRecorder mRecorder;
    Context ct;

    float scoreX = 0;
    float scoreY = 0;
    String scoreText = "0";
    private ArrayList<Integer> x = new ArrayList<Integer>();// 保存音量数据

    public SoundMeter(Context context) {
        super( context );
        ct = context;
        init( context );
    }

    public SoundMeter(Context context, AttributeSet attrs) {
        super( context, attrs );
        ct = context;
        init( context );
//        width=getWidth()*2/3;
    }

    void init(Context context) {
//        Drawable background = context.getResources().getDrawable(R.drawable.vumeter);
//        setBackgroundDrawable(background);

        mPaint = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint.setColor( Color.WHITE );
        mShadow = new Paint( Paint.ANTI_ALIAS_FLAG );
        mShadow.setColor( Color.argb( 60, 0, 0, 0 ) );
        allColorShow();
        mRecorder = null;
        mCurrentAngle = 0;
    }


    public void allColorShow(){
//        80fff5   80ffe6   80ffce   80ffb9    80ff9b    75ff85    e7f47b   f4c87b    f4847b   fd5656
//          80ffce       80ff9b    75ff85    e7f47b   f4c87b    f4847b   fd5656

        mPaint1 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint1.setColor( Color.argb( 100, 128,255,245 ));//80fff5
        mPaint2 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint2.setColor(Color.argb( 100, 128,255,230 ) );//80ffe6
        mPaint3 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint3.setColor( Color.argb( 100, 128,255,206 ) );//80ffce
        mPaint4 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint4.setColor( Color.argb( 100, 128,255,185 ) );//80ffb9
        mPaint5 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint5.setColor( Color.argb( 100, 128,255,155 ) );//80ff9b
        mPaint6 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint6.setColor( Color.argb( 100, 117,255,133 ) );//75ff85
        mPaint7 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint7.setColor( Color.argb( 100, 231,244,123 ) );//e7f47b
        mPaint8 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint8.setColor( Color.argb( 100, 244,200,123 ) );//f4c87b
        mPaint9 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint9.setColor( Color.argb( 100, 244,132,123 ) );//f4847b
        mPaint10 = new Paint( Paint.ANTI_ALIAS_FLAG );
        mPaint10.setColor( Color.argb( 100, 253,86,86 ) );//fd5656

    }


    public void setRecorder(ExtAudioRecorder recorder) {
        mRecorder = recorder;
        try {
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int BASE = 1;

    //    private int BASE = 32768;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
//        drawold( canvas );//分析声音频率
//        drawDB(canvas);////分析声音分贝
        onChangView( canvas );

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDataValue(int dataValue) {
        this.dataValue = dataValue;
//        postInvalidate();
    }

    int dataValue = 0;

    public void onChangView(Canvas canvas) {
//        dataValue = valueall / 10;
//        valueall = 0;
        //db:从0到40分贝：音准线为getHeight();
        int baseline = getHeight();
        rowhight = getHeight() / 11;//满屏显示
        width=getWidth()*2/3;
        i = 0;
//        LogUtils.sysout( "分数平均值：" + dataValue );
//        if (dataValue > 0) {
////               canvas.drawRect(getWidth()/2, getHeight()/2-10, getWidth()-10,getHeight()/2, mPaint);// 长方形
//            canvas.drawRect( width, baseline - 10 - rowhight * dataValue, getWidth() - 10, baseline - rowhight * dataValue, mPaint );
//            postInvalidateDelayed( ANIMATION_INTERVAL );
//            dataValue--;
//        } else {
//            canvas.drawRect( width, getHeight() - 20, getWidth() - 10, getHeight() - 10, mPaint );// 长方形
//            postInvalidateDelayed( ANIMATION_INTERVAL );
//        }

        if (dataValue > 0) {
//               canvas.drawRect(getWidth()/2, getHeight()/2-10, getWidth()-10,getHeight()/2, mPaint);// 长方形
//            canvas.drawRect( width, baseline - 10 - rowhight * dataValue, getWidth() - 10, baseline - rowhight * dataValue, mPaint );
//            postInvalidateDelayed( ANIMATION_INTERVAL );
            drawRectAllColor(canvas,dataValue);
            dataValue--;
        } else {
            canvas.drawRect( width, getHeight() - 20, getWidth() - 10, getHeight() - 10, mPaint1 );// 长方形
            postInvalidateDelayed( ANIMATION_INTERVAL );
        }
    }

    private int width;
    //彩虹条绘制
    public void drawRectAllColor(Canvas canvas, int dataValue){

        int baseline = getHeight();
        rowhight = getHeight() / 11;//满屏显示

        switch (dataValue){
            case -1:
                break;
            case 10:
                canvas.drawRect( width, baseline - 10 - rowhight * 10, getWidth() - 10, baseline - rowhight * 10, mPaint10 );
            case 9:
                canvas.drawRect( width, baseline - 10 - rowhight * 9, getWidth() - 10, baseline - rowhight * 9, mPaint9 );
            case 8:
                canvas.drawRect( width, baseline - 10 - rowhight * 8, getWidth() - 10, baseline - rowhight * 8, mPaint8 );
            case 7:
                canvas.drawRect( width, baseline - 10 - rowhight * 7, getWidth() - 10, baseline - rowhight * 7, mPaint7 );
            case 6:
                canvas.drawRect( width, baseline - 10 - rowhight * 6, getWidth() - 10, baseline - rowhight * 6, mPaint6 );
            case 5:
                canvas.drawRect( width, baseline - 10 - rowhight * 5, getWidth() - 10, baseline - rowhight * 5, mPaint5 );
            case 4:
                canvas.drawRect( width, baseline - 10 - rowhight * 4, getWidth() - 10, baseline - rowhight * 4, mPaint4 );
            case 3:
                canvas.drawRect( width, baseline - 10 - rowhight * 3, getWidth() - 10, baseline - rowhight * 3, mPaint3 );
            case 2:
                canvas.drawRect( width, baseline - 10 - rowhight * 2, getWidth() - 10, baseline - rowhight * 2, mPaint2 );
            case 1:
                canvas.drawRect( width, baseline - 10 - rowhight * 1, getWidth() - 10, baseline - rowhight * 1, mPaint1 );
            case 0:
//                canvas.drawRect( width, baseline - 10 - rowhight * 1, getWidth() - 10, baseline - rowhight * 1, mPaint1 );
                canvas.drawRect( width, getHeight() - 20, getWidth() - 10, getHeight() - 10, mPaint1 );// 长方形
                break;
        }
        postInvalidateDelayed( ANIMATION_INTERVAL );
    }

    int valueall = 0;
    int i = 0;

    public void drawDB(Canvas canvas) {//分析声音分贝
        Integer v = 0;
        float hight = 0;
        if (mRecorder != null) {
//            double ratio = 5;
            double ratio = (double) mRecorder.getMaxAmplitude() / BASE;
            LogUtils.sysout( "9999999999999999:ratio:" + ratio );
//            toast("888888888="+(double) mRecorder.getMaxAmplitude() / BASE);
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (20 * Math.log10( ratio ));
            if (db < 0)
                db = 0;
//            Log.e("dbdbdbdbdbdbdbdb","mCurrentAngle="+db);
//            x.decrease(-db);//保存音频分贝大小，根据X里的值绘制一段完整的波浪线
            v = db;

            //db:从0到40分贝：音准线为getHeight();
            int baseline = getHeight();
            rowhight = getHeight() / 11;//满屏显示

            dataValue = 0;
            i++;
//            for (int i = 0; i < 10; i++) {
            valueall += db / 9;
            if (i == 9) {
                dataValue = valueall / 10;
                valueall = 0;
                i = 0;
                LogUtils.sysout( "分数平均值：" + dataValue );
                if (dataValue > 0) {
//               canvas.drawRect(getWidth()/2, getHeight()/2-10, getWidth()-10,getHeight()/2, mPaint);// 长方形
                    canvas.drawRect( width, baseline - 10 - rowhight * dataValue, getWidth() - 10, baseline - rowhight * dataValue, mPaint );
                    postInvalidateDelayed( ANIMATION_INTERVAL );
                }
            } else {
                canvas.drawRect( width, getHeight() - 20, getWidth() - 10, getHeight() - 10, mPaint );// 长方形
                postInvalidateDelayed( ANIMATION_INTERVAL );
            }
//            }

            /**
             * 划图设计思路：
             * 将整个控件分成15份，分贝取值范围分成10个等级，对应控件的10个等份
             */

        } else {
            canvas.drawRect( width, getHeight() - 20, getWidth() - 10, getHeight() - 10, mPaint );// 长方形
            postInvalidateDelayed( ANIMATION_INTERVAL );
        }
    }


    public void drawold(Canvas canvas) {//分析声音频率
        final float minAngle = (float) Math.PI / 8;
        final float maxAngle = (float) Math.PI * 7 / 8;

        float angle = minAngle;
        if (mRecorder != null)
            angle += (float) (maxAngle - minAngle) * mRecorder.getMaxAmplitude() / 32768;

        if (angle > mCurrentAngle)
            mCurrentAngle = angle;
        else
            mCurrentAngle = Math.max( angle, mCurrentAngle - DROPOFF_STEP );

        mCurrentAngle = Math.min( maxAngle, mCurrentAngle );
        LogUtils.sysout( "11111111111mCurrentAngle=" + mCurrentAngle );
        LogUtils.sysout( "11111111111 mRecorder=" + mRecorder );

        //判断是否采集到数据
        float hight = 0;
        if (mRecorder != null && mCurrentAngle != 0) {

            hight = mRecorder.getMaxAmplitude();
//            Toast.makeText(ct,"hight="+hight,Toast.LENGTH_SHORT).show();
            toast( "33333333=" + mRecorder.getMaxAmplitude() );
            ///画图
            if ((hight * 10 % 2) == 1) {
                mCurrentAngle = getHeight();
                canvas.drawRect( width, mCurrentAngle - 10, getWidth(), mCurrentAngle, mPaint );
            } else {
                mCurrentAngle = getHeight() / 2;
                canvas.drawRect( width, mCurrentAngle - 10, getWidth(), mCurrentAngle, mPaint );
            }
            postInvalidateDelayed( ANIMATION_INTERVAL );
        } else {
            canvas.drawRect( width, getHeight() - 10, getWidth(), getHeight(), mPaint );// 长方形  if (mRecorder != null)
            postInvalidateDelayed( ANIMATION_INTERVAL );
        }
//

    }

    private Toast mToast;

    public void toast(String text) {
        toast( ct, text, Toast.LENGTH_SHORT );
    }

    private void toast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText( context, msg, duration );
        }
        mToast.setDuration( duration );
        mToast.setText( msg );
        mToast.show();
    }

}
