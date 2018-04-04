package com.changxiang.vod.common.utils.krc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


import com.changxiang.vod.R;
import com.changxiang.vod.module.entry.Krc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.module.entry.Krc;

/**
 * Created by ss on 2015/8/7.
 */
public class KrcView extends View {
    private Krc mKrc ;

    //解密的key
    private static final int[] miarry = {64, 71, 97, 119, 94, 50, 116, 71, 81, 54, 49, 45, 206, 210, 110, 105};

    private List<KrcLine> mKrcLineList = new ArrayList<KrcLine>();// 存放歌词
    private Long mMaxrow=4000l;//最长一句歌词的时长；建议使用整百毫秒数，如：实际长度为3518，则取进位运算，为3600；
    private int pieceType=4;//采用哪一种频率等级（预留）
    private int mViewWidth; // view的宽度
    private int mLrcHeight; // lrc界面的高度
    private int mRows;      // 多少行
    private int mCurrentLine = 0; // 当前行

    private float mTextSize; // 字体
    private float mDividerHeight; // 行间距

    private Paint mNormalPaint; // 常规的字体
    private Paint mCurrentPaint; // 当前歌词的大小

    private Bitmap mBackground = null;

    private float float1 = 0.0f;//渲染百分比
    private float float2 = 0.01f;
    private boolean isChanging=false;//视图是否正在更新，用处不大
    private int normalTextColor;//歌词颜色
    private int currentTextColor;//当前歌词颜色

    public KrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mKrc = new Krc(  );
        initViews(attrs);
    }

    public KrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mKrc = new Krc(  );
        initViews(attrs);
    }
    // 初始化操作
    private void initViews(AttributeSet attrs) {
        // <begin>
        // 解析自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.Lrc);
        mTextSize = ta.getDimension( R.styleable.Lrc_textSize2, 50.0f);
        mRows = ta.getInteger(R.styleable.Lrc_rows, 5);
        mDividerHeight = ta.getDimension(R.styleable.Lrc_dividerHeight, 0.0f);

        normalTextColor = ta.getColor(R.styleable.Lrc_normalTextColor,
                Color.WHITE);
        currentTextColor = ta.getColor(R.styleable.Lrc_currentTextColor,
                Color.YELLOW);
        ta.recycle();
        // </end>
        // 计算krc面板的高度
        mLrcHeight = (int) (mTextSize + mDividerHeight) * mRows + 5;
        mNormalPaint = new Paint();
        mCurrentPaint = new Paint();
        // 初始化paint
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setColor(normalTextColor);
        mCurrentPaint.setTextSize(mTextSize);
        //mCurrentPaint.setColor(currentTextColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取view宽度
        mViewWidth = getMeasuredWidth();
        mLrcHeight = getMeasuredHeight();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 重新设置view的高度
        int measuredHeight = MeasureSpec.makeMeasureSpec(mLrcHeight, MeasureSpec.AT_MOST);
        setMeasuredDimension(widthMeasureSpec, measuredHeight);
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    //关键代码
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mKrcLineList.isEmpty() ) {
            canvas.drawText("暂无歌词,点击开始搜索", mViewWidth / 2-mTextSize*5, mLrcHeight/2, mNormalPaint);
            return;
        }

        canvas.save();
        // 圈出可视区域
        canvas.clipRect(0, 0, mViewWidth, mLrcHeight);

        if (null != mBackground) {
            canvas.drawBitmap( Bitmap.createScaledBitmap(mBackground, mViewWidth, mLrcHeight, true),
                    new android.graphics.Matrix(), null);
        }

        // 将画布上移
        canvas.translate(0, -((mCurrentLine - (mRows+1)/2) * (mTextSize + mDividerHeight)));

        // 画当前行上面的
        for (int i = mCurrentLine - 1; i >= 0; i--) {
            String lrc =  mKrcLineList.get(i).lineStr;
            float x = (mViewWidth - mNormalPaint.measureText(lrc)) / 2;
            canvas.drawText(lrc, x, (mTextSize + mDividerHeight) * i,mNormalPaint);
        }

        String currentLrc = mKrcLineList.get(mCurrentLine).lineStr;
        float currentX = (mViewWidth - mCurrentPaint.measureText(currentLrc)) / 2;

        // 获得字符串的"长度"
        float len = mCurrentPaint.getTextSize() * currentLrc.length();
        // 参数color数组表示参与渐变的集合
        // 参数float数组表示对应颜色渐变的位置
        int[] a=new int[]{currentTextColor, normalTextColor};
        float[] f=new float[]{float1, float2};
        Shader shader = new LinearGradient(currentX,
                (mTextSize + mDividerHeight)* mCurrentLine,
                len+currentX,
                (mTextSize + mDividerHeight)* mCurrentLine,
                a,f, Shader.TileMode.CLAMP);
        mCurrentPaint.setShader(shader);
        // 画当前行
        canvas.drawText(currentLrc, currentX, (mTextSize + mDividerHeight)* mCurrentLine, mCurrentPaint);

        // 画当前行下面的
        for (int i = mCurrentLine + 1; i < mKrcLineList.size(); i++) {
            String lrc =  mKrcLineList.get(i).lineStr;
            float x = (mViewWidth - mNormalPaint.measureText(lrc)) / 2;
            canvas.drawText(lrc, x, (mTextSize + mDividerHeight) * i, mNormalPaint);
        }

        canvas.restore();
    }
    //解密krc数据
    public String getKrcText(String filenm) throws IOException
    {
        File krcfile = new File(filenm);
        byte[] zip_byte = new byte[(int) krcfile.length()];
        FileInputStream fileinstrm = new FileInputStream(krcfile);
        byte[] top = new byte[4];
        fileinstrm.read(top);
        fileinstrm.read(zip_byte);
        int j = zip_byte.length;
        for (int k = 0; k < j; k++)
        {
            int l = k % 16;
            int tmp67_65 = k;
            byte[] tmp67_64 = zip_byte;
            tmp67_64[tmp67_65] = (byte) (tmp67_64[tmp67_65] ^ miarry[l]);
        }
        String krc_text = new String( ZLibUtils.decompress(zip_byte), "utf-8" );
        return krc_text;
    }
    // 外部提供方法
    // 设置krc的路径
    public void setKrcPath(String path) throws Exception {
        mKrcLineList.clear();
        //解密解压
        String lines = getKrcText(path);
        //
        String lineArray[] = lines.split("\r\n");
        if(lineArray.length<=10){
            return;
        }

        //逐行解析
        for(int i=0;i<lineArray.length;i++){
            KrcLine krcLine = ParseLine(lineArray[i]);
            if(null!=krcLine){
                mKrcLineList.add(krcLine);
            }
        }
    }
    //用来存储歌词的类
    class KrcLineTime{
        long startTime;
        long spanTime;
        long reserve;
        public KrcLineTime(){
        }
        public KrcLineTime(long startTime, long spanTime, long reserve) {
            super();
            this.startTime = startTime;
            this.spanTime = spanTime;
            this.reserve = reserve;
        }

    }
    //用来存储一行歌词的类
    class KrcLine{
        KrcLineTime lineTime;
        List<KrcLineTime> wordTime;
        String lineStr;
        public KrcLine(){
            lineTime = new KrcLineTime();
            wordTime = new ArrayList<KrcLineTime>();
            lineStr="";
        }
    }
    //解析头文件
    private void ParseTitle(String line){
        try {
            if(line.matches("\\[.+\\].+")){

            }else{
                String sbustr = null;
                String strArray[] = line.split("\\]",2);
                String timeStr[] = strArray[0].split(":");
                String titleKey = timeStr[0].substring(1);
                String titleValue  = timeStr[1];
                if(titleKey.equals( "max" )){
                    mMaxrow = Long.parseLong(titleValue);
//                    mMaxrow = 4500;
                    Toast.makeText(getContext(),"最长一句歌词时间："+mMaxrow, Toast.LENGTH_LONG).show();
                }else if(titleKey.equals( "piece" )){
                    Toast.makeText(getContext(),"11111"+titleValue, Toast.LENGTH_LONG).show();
                } if(titleKey.equals( "ar" )){
                    Toast.makeText(getContext(),"歌手："+titleValue, Toast.LENGTH_LONG).show();
                } if(titleKey.equals( "ti" )){
                    Toast.makeText(getContext(),"歌曲："+titleValue, Toast.LENGTH_LONG).show();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //解析一行
    private KrcLine ParseLine(String line){
        KrcLine krcLine=null;
        try {
            krcLine = new KrcLine();
            if(line.matches("\\[.+\\].+")){
                line = line.substring(1);
                String strArray[] = line.split("\\]",2);
                String timeStr[] = strArray[0].split(",");
                krcLine.lineTime.startTime = Long.parseLong(timeStr[0]);
                krcLine.lineTime.spanTime = Long.parseLong(timeStr[1]);
                String lyricsStr[] = strArray[1].split("[<>]");
                for(int i=1;i<lyricsStr.length;i+=2){
                    String wordTime[] = lyricsStr[i].split(",");
                    if(wordTime.length<3){
                        continue;
                    }
                    long startT = Long.parseLong(wordTime[0]);
                    long spanT = Long.parseLong(wordTime[1]);
                    long reverse = Long.parseLong(wordTime[2]);
                    KrcLineTime krcLineTime = new KrcLineTime(startT,spanT,reverse);

                    krcLine.wordTime.add(krcLineTime);
                    krcLine.lineStr+=lyricsStr[1+i];
                }
            }else{
                return  null;
            }

        }catch (Exception e){
            return null;
        }

        return krcLine;
    }

    //
    // 背景图片
    public void setBackground(Bitmap bmp) {
        mBackground = bmp;    //更新视图
        postInvalidate();
    }

    // 外部提供方法
    // 传入当前播放时间
    public  void changeCurrent(long time) {
        if (isChanging) {
            return;
        }
        isChanging=true;
        // 每次进来都遍历存放的时间
        KrcLine kl=null;
        for (int  i = 0; i < mKrcLineList.size(); i++) {
            // 遍历歌词列表来获取当前行和当前字
            mCurrentLine = i;
            float1=0.0f;
            float2=0.001f;
            kl = mKrcLineList.get(i);//一行歌词对象（包含歌词，时间）
            if (kl.lineTime.startTime<=time && time<kl.lineTime.startTime+kl.lineTime.spanTime) {
                //当前行
                int len = kl.lineStr.length();//当前行的字数
                int currentWordIndex=0;
                KrcLineTime klt = null;
                long timeSpan = time-kl.lineTime.startTime;//该句歌词已经播放的时长
                for(int j=0;j<kl.wordTime.size();j++){
                    currentWordIndex=j;
                    klt = kl.wordTime.get(j);//一个字对应一个时间，j是第几个字
                    if(klt.startTime<=timeSpan && timeSpan<klt.startTime+klt.spanTime){//定位到第几个字
                        //计算当前歌词所在的百分比
                        float1 = (j+(timeSpan-klt.startTime)*1.0f/klt.spanTime)/len;
                        float2 = float1>0.99f ? float1:(float1+0.01f);
                        break;
                    }
                }
                break;
            }else if(kl.lineTime.startTime>=time){
                break;
            }
        }
        //更新视图
        postInvalidate();
        isChanging=false;
    }

}
