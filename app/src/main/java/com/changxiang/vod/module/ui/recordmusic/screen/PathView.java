package com.changxiang.vod.module.ui.recordmusic.screen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.LogUtils;
//import com.quchangkeji.tosingpk.module.entry.Krc;
//import com.quchangkeji.tosingpk.module.entry.KrcLine;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.recordutils.RecordHelper;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.entry.Krc;
import com.changxiang.vod.module.entry.KrcLine;
import com.changxiang.vod.module.ui.recordmusic.recordutils.RecordHelper;

import java.util.ArrayList;
import java.util.Timer;

import static com.changxiang.vod.module.ui.recordmusic.screen.SoundMeter.ANIMATION_INTERVAL;

//import static com.quchangkeji.tosingpk.module.ui.recordmusic.screen.SoundMeter.ANIMATION_INTERVAL;

/**
 * 全程通过播放进度来控制
 * 1：进度对比画整句歌词的音调
 * 2：通过进度画当前的播放进度
 * 3：
 */

/**
 * 根据数组，暂定每一句的时长：alltimes（ms毫秒），每一个频段的时间 itemtimes （ms毫秒），每一个频段的值（int）
 * 对应的手机绘图：总的宽度：getWidth(),分成 alltimes 等份，每一等份所占时间：getWidth()/alltimes；每一个频段的屏宽为getWidth()/alltimes*itemtimes；
 */
public class PathView extends View {

    private int rowhight = 30;//音调显示间隔
    Paint mPaint;//最初的画笔
    Paint mShadow;//唱完之后的画笔
    Paint mtrigon;//三角形画笔
    float mCurrentAngle;
    private Krc mKrc;
    private int mCurrentLine = 0; // 当前行
    private int baseCurrentLine = -1; // 当前行(已经画上的一行)
    private int nowCurrentLine = 0; // 当前行（由进度条觉得的一行）
    private float float1 = 0.0f;//渲染百分比
    private float float2 = 0.01f;


    private int alltimes = 4000;//总时长
    private int linetimes = 4000;//每一句的时长
    private int[] dataTimes = {400, 50, 200, 50, 300, 600, 800, 50, 600, 50, 400, 500};//时间
    private int[] dataValue = {5, 0, 8, 0, 3, 0, 4, 2, 6, 0, 2, 10};//值
    private double allWidth = getWidth();//屏宽

    private Timer sfvtimer;
    int countTimes = 0;//计时器（每一句歌词的进度时长）
    int rotateValue = 0;//旋转角度值

    //Rotate动画 - 画面旋转
    private Animation rotateAnimation = null;
    private Rect mSrcRect, mDestRect;
    private Bitmap mBitmap;
    Context ct;
    private ArrayList<Integer> x = new ArrayList<Integer>();// 保存音量数据

    public PathView(Context context) {
        super(context);
        ct = context;
        init(context);
        getRotateView();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;
        init(context);
        getRotateView();
    }

    public void setKrcData(Krc mKrc) {
        this.mKrc = mKrc;
        if (mKrc == null) {//重置或者拿不到数据
            dataTimes = null;
            dataValue = null;
            return;
        }
//        KrcLine kl = null;
        LogUtils.sysout("获取到的mKrc = " + mKrc);
        String meg = "";
        KrcLine kl = mKrc.getmKrcLineList().get(0);
        try {
            alltimes = Integer.parseInt(kl.getLineTime().getSpanTime() + "");
            dataTimes = new int[kl.getWordTime().size()];
            dataValue = new int[kl.getWordTime().size()];
            for (int h = 0; h < kl.getWordTime().size(); h++) {
                meg += kl.getWordTime().get(h).getReserve() + ",";
                dataTimes[h] = Integer.parseInt(kl.getWordTime().get(h).getSpanTime() + "");
                dataValue[h] = Integer.parseInt(kl.getWordTime().get(h).getReserve() + "");
            }
        } catch (Exception e) {
            alltimes = 4000;
            e.printStackTrace();
        }

        postInvalidateDelayed(ANIMATION_INTERVAL);
//        LogUtils.sysout( "第一行的音调"+meg);
//        LogUtils.sysout( "9999999999999外部传入MKRC999999999999");
        //TODO
//        alltimes = Integer.parseInt( mKrc.getMax() + "" );
    }

    void init(Context context) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.WHITE);
        mPaint.setColor(0xFFcccccc);
        mShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mShadow.setColor(Color.argb(60, 0, 0, 0));
        mShadow.setColor(0xFF78FD88);
        mtrigon = new Paint(Paint.ANTI_ALIAS_FLAG);
        mtrigon.setColor(0xFFFFFC00);


        mCurrentAngle = 0;
//        timeToDo();//计时器

    }

    public void setRecorder(RecordHelper recorder) {
        invalidate();
    }

    private int BASE = 1;

    //    private int BASE = 32768;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawold(canvas);//


    }

//    //定时执行任务：
//    public void timeToDo() {
//        //开启画笔线程每0.2绘制一次
//        sfvtimer = new Timer();
//
//        sfvtimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                countTimes += 50;
////                Log.e("countTimes99999999999", "countTimes=" + countTimes);
//            }
//        }, 0, 50);
//    }

    public void setCountTimes(int count) {
        countTimes += count;
    }

    //播放进度 countTimes：每一句歌词的时长，count 进度
    public void setCountTimesNow(int time) {
        // 每次进来都遍历存放的时间
        KrcLine kl = null;
        if (mKrc == null) {
//            LogUtils.sysout( "mKrc音调文件:=null");
            return;
        }
        if (mKrc.getmKrcLineList() == null) {
            LogUtils.sysout("mKrc.getmKrcLineList()==null");
            return;
        }

//        LogUtils.sysout( "++++++++++++++krc文件：遍历歌词列表来获取当前行和当前字");
        for (int i = 0; i < mKrc.getmKrcLineList().size(); i++) {
            // 遍历歌词列表来获取当前行和当前字
            mCurrentLine = i; // 当前行
            float1 = 0.0f;
            float2 = 0.001f;
            kl = mKrc.getmKrcLineList().get(i);
//            LogUtils.sysout( "当前行时长"+kl.getLineTime().getSpanTime()+"");
            if (kl.getLineTime().getStartTime() <= time && time < kl.getLineTime().getStartTime() + kl.getLineTime().getSpanTime()) {
                //当前行
                countTimes = Integer.parseInt((time - kl.getLineTime().getStartTime()) + "");//当前行的进度
                //在此添加火花效果：

//                private int baseCurrentLine = 0; // 当前行(已经画上的一行)
//                private int nowCurrentLine = 0; // 当前行（由进度条觉得的一行）
//                private int[] dataTimes = {400, 50, 200, 50, 300, 600, 800, 50, 600, 50, 400, 500};//时间
//                private int[] dataValue = {5, 0, 8, 0, 3, 0, 4, 2, 6, 0, 2, 10};//值
                nowCurrentLine = Integer.parseInt(kl.getLineTime().getStartTime() + "");//记录当前行时间
//                LogUtils.sysout( "当前行时长"+kl.getLineTime().getSpanTime()+"");
//                LogUtils.sysout( "nowCurrentLine"+nowCurrentLine);
//                LogUtils.sysout( "baseCurrentLine"+baseCurrentLine);
                if (nowCurrentLine != baseCurrentLine && nowCurrentLine > baseCurrentLine) {//行该改变了：
//                    LogUtils.sysout("***********行该改变了**********"  );
                    try {
                        dataTimes = new int[kl.getWordTime().size()];
                        dataValue = new int[kl.getWordTime().size()];
                        for (int h = 0; h < kl.getWordTime().size(); h++) {
                            dataTimes[h] = Integer.parseInt(kl.getWordTime().get(h).getSpanTime() + "");
                            dataValue[h] = Integer.parseInt(kl.getWordTime().get(h).getReserve() + "");
                        }

                        baseCurrentLine = nowCurrentLine;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        alltimes = Integer.parseInt(kl.getLineTime().getSpanTime() + "");
//                       LogUtils.sysout( "当前行时长"+alltimes );
                    } catch (Exception e) {
                        alltimes = 4000;
                        e.printStackTrace();
                    }
                }
//                LogUtils.sysout("-----------------"  );

            }
        }
    }

    public void drawold(Canvas canvas) {//根据数组绘图，

//            toast("11111111111="+"");
        ///画图

        if (dataTimes == null || dataValue == null) {
            return;

        }
        double itemWidth = getWidth() / alltimes;//
        int startwidth = 0;
        int baseline = getHeight();
        rowhight = getHeight() / 11;//满屏显示
        if (dataTimes.length == dataValue.length) {
            for (int i = 0; i < dataTimes.length; i++) {
                if (dataValue[i] != 0)
                    canvas.drawRect(startwidth, baseline - 10 - rowhight * dataValue[i], (int) (startwidth + (dataTimes[i] * getWidth() / alltimes)), baseline - rowhight * dataValue[i], mPaint);
                startwidth += (dataTimes[i] * getWidth() / alltimes);
//                Log.e("11111111111111","startwidth="+startwidth);
            }
        }

        //绘制已经唱完的数组
        int toSing = 0;
        int addtimestart = 0;
        int addtimeend = 0;

        for (int i = 0; i < dataTimes.length; i++) {
            addtimeend += dataTimes[i];///当前频率的结束时间；
            if (i > 0 && countTimes >= addtimestart) {
                if (dataValue[i - 1] != 0) {
//                    canvas.drawRect(toSing, baseline - 10 - 10 * dataValue[i], (int) ((countTimes * getWidth() / alltimes)), baseline - 10 * dataValue[i], mShadow);
                    canvas.drawRect(toSing - (dataTimes[i - 1] * getWidth() / alltimes), baseline - 10 - rowhight * dataValue[i - 1], (int) (toSing), baseline - rowhight * dataValue[i - 1], mShadow);
                }
            }
            if (countTimes > addtimestart && countTimes <= addtimeend) {
                if (dataValue[i] != 0) {
                    canvas.drawRect(toSing, baseline - 10 - rowhight * dataValue[i], (int) ((countTimes * getWidth() / alltimes)), baseline - rowhight * dataValue[i], mShadow);


                    //绘制闪闪发光 rotate
//                    rotateValue = (int)((countTimes/alltimes)*360*2);
                    rotateValue += 36;
                    if (rotateValue == 360) {
                        rotateValue = 0;
                    }
//                    Log.e("countTimes99999999999", "rotateValue=" + rotateValue);
                    mSrcRect = new Rect(0, 0, getWidth(), getHeight());
                    mDestRect = new Rect((countTimes * getWidth() / alltimes) - 65,
                            baseline - 10 - rowhight * dataValue[i] - 35,
                            (countTimes * getWidth() / alltimes + 15),
                            baseline - 10 - rowhight * dataValue[i] + 45);//
                    canvas.drawBitmap(rotate(BitmapFactory.decodeResource(ct.getResources(), R.mipmap.rotate), rotateValue),
                            mSrcRect, mDestRect, mShadow);

                    // 绘制这个三角形,你可以绘制任意多边形
                    try {
                        Path path = new Path();
                        path.moveTo((countTimes * getWidth() / alltimes) - 36, baseline - 10 - rowhight * dataValue[i] + 25);// 此点为多边形的起点 下边点
                        path.lineTo((countTimes * getWidth() / alltimes) - 36, baseline - 10 - rowhight * dataValue[i] - 20);//上边点
                        path.lineTo((countTimes * getWidth() / alltimes), baseline - 10 - rowhight * dataValue[i] + 5);//顶点
                        path.close(); // 使这些点构成封闭的多边形
                        canvas.drawPath(path, mShadow);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    // 绘制这个三角形,你可以绘制任意多边形
                    try {
                        Path path = new Path();
                        path.moveTo((countTimes * getWidth() / alltimes) - 36, baseline - 10 - rowhight * dataValue[i - 1] + 25);// 此点为多边形的起点，下边点
                        path.lineTo((countTimes * getWidth() / alltimes) - 36, baseline - 10 - rowhight * dataValue[i - 1] - 20);//上边点
                        path.lineTo((countTimes * getWidth() / alltimes), baseline - 10 - rowhight * dataValue[i - 1] + 5);//顶点
                        path.close(); // 使这些点构成封闭的多边形
                        canvas.drawPath(path, mShadow);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
            addtimestart = addtimeend;
            toSing += (dataTimes[i] * getWidth() / alltimes);
        }

        postInvalidateDelayed(ANIMATION_INTERVAL);
//        if (countTimes > alltimes) {//重置计时器并刷新界面（当前应该使用当前行的总时间替换alltimes）
        if (countTimes > linetimes) {//重置计时器并刷新界面（当前应该使用当前行的总时间替换alltimes）
            countTimes = 0;
            //TODO
            //重置所有的数据
            toSing = 0;
            startwidth = 0;
        }
//

    }

//    @Override
//    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {}

    private ImageView rotate;
    private Resources mResources;

    private void getRotateView() {
        mBitmap = BitmapFactory.decodeResource(ct.getResources(), R.mipmap.rotate);
//        mBitmap = rotate( BitmapFactory.decodeResource(ct.getResources(), R.mipmap.rotate),60);
//        mBitmap = adjustPhotoRotation( BitmapFactory.decodeResource(ct.getResources(), R.mipmap.rotate),360);

    }

    public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree)//该方法相对占用内存小
    {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        return bm1;
    }

    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees,
                    (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(
                        b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();  //Android开发网再次提示Bitmap操作完应该显示的释放
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                // Android123建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
            }
        }
        return b;
    }

    /**
     * 旋转的动画
     */
    private LinearInterpolator interpolator = new LinearInterpolator();

    private void playLeft() {
        AnimationSet animationSet = new AnimationSet(false);
        RotateAnimation rotateSelf = new RotateAnimation(0, -359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateSelf.setDuration(10 * 1000);
        rotateSelf.setRepeatCount(-1);
        rotateSelf.setInterpolator(interpolator);
        animationSet.addAnimation(rotateSelf);
        rotate.startAnimation(animationSet);
    }

    private Toast mToast;

    public void toast(String text) {
        toast(ct, text, Toast.LENGTH_SHORT);
    }

    private void toast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        }
        mToast.setDuration(duration);
        mToast.setText(msg);
        mToast.show();
    }

}
