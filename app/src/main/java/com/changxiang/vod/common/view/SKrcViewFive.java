package com.changxiang.vod.common.view;//package tosingpk.quchangkeji.com.quchangpk.common.view;
//
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.LinearGradient;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.Shader;
//import android.graphics.Typeface;
//import android.os.Handler;
//import android.os.Message;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
////import com.quchangkeji.tosing.R;
////import com.quchangkeji.tosing.common.utils.LogUtils;
////import com.quchangkeji.tosing.module.entry.KrcLine;
////import com.quchangkeji.tosing.module.entry.KrcLineTime;
////import com.quchangkeji.tosing.module.entry.KrcWord;
////import com.quchangkeji.tosing.module.musicInfo.DisplayUtil;
////import com.quchangkeji.tosing.module.musicInfo.ILrcStateContain;
//
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class SKrcViewFive extends ScrollView implements OnTouchListener, ILrcStateContain {
//    private float width;    //歌词视图宽度
//    private float height;    //歌词视图高度
//    private Paint currentPaint;        //当前画笔对象
//    private Paint notCurrentPaint;        //非当前画笔对象
//    private Paint tipsPaint;  //提示信息画笔
//
//    private float lightTextSize;        //高亮文本大小,当前句
//    private float norTextSize;        //非高亮文本大小
//    private float tipsTextSize;    //提示文本大小
//    private float textHeight;    //文本高度
//    private int index;    //歌词list集合下标
//    private int lastIndex;//上一句歌词位置，只能换行后才滑动
//
//    private int lrcState = -1;
//    private LrcTextView lrcTextView;
//    private List<KrcLine> lrcLists;//歌词集合
//
//    private int scrollY;
//    private int lastY;
//    //    private int endY;//滑动停止时，滑动到的位置
//    private boolean canDrawLine = false;
//    private boolean isScroll = false;//是否是手动滑动（因为可能是自动滑动）
//    private boolean isHandScroll = false;//是否是手动滑动
//    private int pos = -1; //手指按下后歌词要到的位置
//    private Paint linePaint;
//
//    private boolean canTouchLrc = true;//是否可以触摸并调整歌词
//
//    private int count = 0;  //绘制加载点的次数
//    private Context mContext;
//
//    private float float1 = 0.0f;//渲染百分比
//    private float float2 = 0.01f;
//    private boolean isChanging = false;//视图是否正在更新，用处不大
//    private float mCoefficient = 1.0f;
//    private boolean isEn;
//
//    private int currentWord = 0;
//    private int mTemp = 0;
//    private int stopWord = 0;
//    private boolean isWordChange = false;//字变化
//    private KrcLine Kl;
//    private int mLen;
//    private KrcWord mKlt;
//    private long mTimeSpan;
//    private int temp = -1;
//    private boolean isLine;
//    private float len;
//
//    //private OnLrcSearchClickListener onLrcSearchClickListener;
//
//    public SKrcViewFive(Context context) {
//        this(context, null);
//        // TODO Auto-generated constructor stub
//    }
//
//    public SKrcViewFive(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//        // TODO Auto-generated constructor stub
//    }
//
//    public SKrcViewFive(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        // TODO Auto-generated constructor stub
//
//        mContext = context;
//
//        this.setOnTouchListener(this);
//
//        init();
//    }
//
//    /*public void setOnLrcSearchClickListener(OnLrcSearchClickListener onLrcSearchClickListener) {
//        this.onLrcSearchClickListener = onLrcSearchClickListener;
//    }*/
//
//    //获取歌词的内容和对应时间
//    public List<KrcLine> getLrcLists() {
//        return lrcLists;
//    }
//
//    public void setLrcLists(List<KrcLine> lrcLists) {
//        if (lrcLists != null) {
//            this.lrcLists = lrcLists;
//        }
//        //判断歌词界面是否可以触摸
//        if (lrcLists == null || lrcLists.size() == 0) canTouchLrc = false;
//        else canTouchLrc = true;
//        //设置index=-1
//        this.index = -1;
//        //设置lrcTextView的宽高
//        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        lrcTextView = new LrcTextView(this.getContext());
//        lrcTextView.setLayoutParams(params1);
//
//        this.removeAllViews();
//        this.addView(lrcTextView);
//        this.scrollTo(0, 0);
//    }
//
//    public void setLightTextSize(int size) {
//        lightTextSize = DisplayUtil.sp2px(mContext, size);
//    }
//
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        //歌曲位置发生变化,而且手指不是调整歌词位置的状态
//        if (this.index != index && pos == -1) {
//            this.scrollTo(0, (int) (index * textHeight));
//        }
//        this.index = index;
//    }
//
//    // 外部提供方法
//    // 传入当前播放时间
//    public void changeCurrent(long time) {
//        if (isChanging || lrcLists == null || lrcLists.size() == 0) {
//            return;
//        }
//        isChanging = true;
//        // 每次进来都遍历存放的时间
//        KrcLine kl = null;
//        for (int i = 0; i < lrcLists.size(); i++) {
//            // 遍历歌词列表来获取当前行和当前字
//            index = i;
////            if (!canDrawLine&&index!=lastIndex||index==lrcLists.size()-1){//canDrawLine 3s后才会自动滑到中间，因为有定位框
//            if (!canDrawLine && index != lastIndex) {//canDrawLine 3s后才会自动滑到中间，因为有定位框
////                isHandScroll=false;
//                this.scrollTo(0, (int) (index * textHeight));
//                lastIndex = index;
//            }
//            float1 = 0.0f;
//            float2 = 0.001f;
//            kl = lrcLists.get(i);//一行歌词对象（包含歌词，时间）
//            if (kl.getLineTime().getStartTime() <= time && time < kl.getLineTime().getStartTime() + kl.getLineTime().getSpanTime()) {
//                //当前行
//                int len = kl.getLineStr().length();//当前行的字数
////                int currentWordIndex=0;
//                KrcWord klt = null;//一句歌词中，单个字或者单词对象
//                long timeSpan = time - kl.getLineTime().getStartTime();//该句歌词已经播放的时长
//                for (int j = 0; j < kl.getWordTime().size(); j++) {
////                    currentWordIndex=j;
//                    klt = kl.getWordTime().get(j);//一个字对应一个时间，j是第几个字
//                    if (klt.getStartTime() <= timeSpan && timeSpan < klt.getStartTime() + klt.getSpanTime()) {//定位到第几个字
//                        //计算当前歌词所在的百分比
//                        float pLen = 0;
//                        for (int k = 0; k <= j; k++) {
//                            pLen += kl.getWordTime().get(k).getWords().trim().length();
////                            Log.d("TAG22", "plen:"+pLen+",klt.getWords():"+klt.getWords().length());
//                        }
////                        pLen+=(timeSpan-klt.getStartTime())*1.0f/klt.getSpanTime()*klt.getWords().trim().length();
////                        float1 = (j + (timeSpan - klt.getStartTime()) * 1.0f / klt.getSpanTime()) / len;
//                        float1 = (pLen + (timeSpan - klt.getStartTime()) * 1.0f / klt.getSpanTime() * klt.getWords().trim().length()) / len;
//                        float2 = float1 > 0.99f ? float1 : (float1 + 0.01f);
////                        Log.d("TAG22", "pLen:"+pLen+",len:"+len+",float1: "+float1);
//                        break;
//                    }
//                }
//                break;
//            } else if (kl.getLineTime().getStartTime() >= time) {
//                break;
//            }
//        }
//        //                        postInvalidate();
//        invalidate();//更新视图
//
//    }
//
//    // 跑马灯：传入当前播放时间
//    public void changeCurrentTwo(long time) {
//        if (isChanging || lrcLists == null) {
//            return;
//        }
//        isChanging = true;
//        // 每次进来都遍历存放的时间
//        Kl = null;
//        for (int i = 0; i < lrcLists.size(); i++) {
//            // 遍历歌词列表来获取当前行和当前字
//            index = i;
//            if (!canDrawLine && index != lastIndex) {
////            if ( index != lastIndex || index == lrcLists.size() - 1) {
////                isHandScroll=false;
//                this.scrollTo(0, (int) (index * textHeight));
//                lastIndex = index;
//            }
//            float1 = 0.0f;
//            float2 = 0.001f;
//            Kl = lrcLists.get(i);//一行歌词对象（包含歌词，时间）
//            if (Kl.getLineTime().getStartTime() <= time && time < Kl.getLineTime().getStartTime() + Kl.getLineTime().getSpanTime()) {
//                //找到当前行
//                //当前行的字数
//                mLen = Kl.getLineStr().length();
//                //判断当前行中空格数
//                String[] split = Kl.getLineStr().replaceAll("\\s", " ").trim().split(" ");
//                int spaceNum = split.length - 1;
//                //测量一个汉字的宽和当前行的宽
//                float wordSize = currentPaint.measureText("好");
//                float currentLen = currentPaint.measureText(Kl.getLineStr());
//                //固定可显示区域
//                float showWidthStart = DisplayUtil.dip2px(mContext, 100);
//                float showWidthEnd = getWidth();
//                float block = showWidthEnd - showWidthStart;
//                //固定区域显示字的个数
//                int num = (int) (block / wordSize);
////                KrcLineTime klt = null;
//                mKlt = null;
//                //该句歌词已经播放的时长
//                mTimeSpan = time - Kl.getLineTime().getStartTime();
//                for (int j = 0; j < Kl.getWordTime().size(); j++) {
//                    isWordChange = false;
//                    mKlt = Kl.getWordTime().get(j);//一个字对应一个时间，j是第几个字
//                    if (mKlt.getStartTime() <= mTimeSpan && mTimeSpan < mKlt.getStartTime() + mKlt.getSpanTime()) {//定位到第几个字
//                        if (temp != j) {
//                            isWordChange = true;
//                            temp = j;
//                        }
//                        //计算当前歌词所在的百分比
//                        LogUtils.w("test", "第" + i + "行有空格" + spaceNum);
//                        LogUtils.w("test", "第" + i + "行的歌词" + Kl.getLineStr());
//                        LogUtils.w("test", "当前字" + currentWord);
//                        LogUtils.w("test", "block" + block);
//                        LogUtils.w("test", "currentLen" + currentLen);
//                        float pLen = 0;
//                        currentWord = j;
//                        if (block > currentLen) {//不需要跑马
//                            LogUtils.w("test", "不要跑马--当前字" + currentWord);
//                            for (int k = 0; k <= j -1 ; k++) {
//                                pLen += Kl.getWordTime().get(k).getWords().trim().length();
//                            }
//                        } else {//需要跑马
//                            if (num <= (Kl.getWordTime().size() - j)) {//正在跑马状态
//                                //记录跑马灯的字
//                                if (spaceNum != 0) {
//                                    stopWord = j + spaceNum;
//                                } else {
//                                    stopWord = j;
//                                }
//                                for (int k = mTemp; k <= j - 1; k++) {
//                                    pLen += Kl.getWordTime().get(k).getWords().trim().length();
//                                }
//                            } else {
//                                for (int k = mTemp; k <= j - 1; k++) {
//                                    pLen += Kl.getWordTime().get(k).getWords().trim().length();
//                                }
//                            }
//                        }
//                        float1 = (pLen + (mTimeSpan - mKlt.getStartTime()) * 1.0f / mKlt.getSpanTime() * mKlt.getWords().trim().length()) / mLen;
//                        float2 = float1 > 0.99f ? float1 : (float1 + 0.01f);
//                        break;
//                    }
//                }
//                break;
//            } else if (Kl.getLineTime().getStartTime() >= time) {
//                mTemp = 0;
//                stopWord = 0;
//                break;
//            }
//        }
//        //                        postInvalidate();
//        invalidate();//更新视图
//
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        // TODO Auto-generated method stub
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        this.width = w;
//        this.height = h;
//    }
//
//    public int getIndexByLrcTime(int currentTime) {
//        if (lrcLists == null) {
//            return 0;
//        }
//        for (int i = 0; i < lrcLists.size(); i++) {
//            KrcLineTime krcLineTime = lrcLists.get(i).getLineTime();
//            if (currentTime < krcLineTime.getStartTime()) {
//                return i - 1;
//            }
//        }
//        return lrcLists.size() - 1;
//    }
//
//    public void clear() {
//        lrcLists = null;
//    }
//
//
//    public void setLrcState(int lrcState) {
//        this.lrcState = lrcState;
//        invalidate();
//    }
//
//
//    class LrcTextView extends TextView {
//
//        public LrcTextView(Context context) {
//            this(context, null);
//            // TODO Auto-generated constructor stub
//        }
//
//        public LrcTextView(Context context, AttributeSet attrs) {
//            this(context, attrs, 0);
//            // TODO Auto-generated constructor stub
//        }
//
//        public LrcTextView(Context context, AttributeSet attrs, int defStyle) {
//            super(context, attrs, defStyle);
//            // TODO Auto-generated constructor stub
//            this.setWillNotDraw(false);
//        }
//
//        int tempY;
//
//        //绘制歌词
//        @Override
//        protected void onDraw(Canvas canvas) {
//            // TODO Auto-generated method stub
//            super.onDraw(canvas);
//            if (canvas == null) return;
////            tempY = (int) height / 2;
//            tempY = (int) norTextSize - DisplayUtil.dip2px(mContext,3);
////            tempY = (int) textHeight;
//            switch (lrcState) {
//                case READ_LOC_FAIL:
//                    tipsPaint.setUnderlineText(true);
////                    canvas.drawText(mContext.getString(R.string.quchang_music), width / 2, tempY, tipsPaint);
////                    canvas.drawText(mContext.getString(R.string.quchang_music_nokrc), width / 2, tempY, tipsPaint);
//                    canvas.drawText(mContext.getString(R.string.quchang_music_nokrc), width / 2, tempY, tipsPaint);
//                    break;
//                case QUERY_ONLINE:
//                    tipsPaint.setUnderlineText(false);
//                    String drawContentStr = "在线匹配歌词";
//                    for (int i = 0; i < count; i++) {
//                        drawContentStr += ".";
//                    }
//                    count++;
//                    if (count >= 6) count = 0;
//                    canvas.drawText(drawContentStr.trim(), width / 2, tempY, tipsPaint);
//                    break;
//                case QUERY_ONLINE_OK:
//                case READ_LOC_OK:
//
//                    /*
//                    canvas.save();
//                    // 圈出可视区域
//                    canvas.clipRect(0, 0, width, height);
//                    // 将画布上移
//                    canvas.translate(0, -((index - (lrcLists.size()+1)/2) * textHeight));
//
//                    // 画当前行上面的
//                    for (int i = index - 1; i >= 0; i--) {
//                        String lrc =  lrcLists.get(i).getLineStr();
//                        float x = (width - notCurrentPaint.measureText(lrc)) / 2;
//                        canvas.drawText(lrc, x, (textHeight) * i,notCurrentPaint);
//                    }
//
//                    String currentLrc = lrcLists.get(index).getLineStr();
//                    float currentX = (width - notCurrentPaint.measureText(currentLrc)) / 2;
//
//                    // 获得字符串的"长度"
//                    float len = notCurrentPaint.getTextSize() * currentLrc.length();
//                    // 参数color数组表示参与渐变的集合
//                    // 参数float数组表示对应颜色渐变的位置
//                    int[] a=new int[]{currentTextColor, normalTextColor};
//                    float[] f=new float[]{float1, float2};
//                    Shader shader = new LinearGradient(currentX,
//                            (textHeight)* index,
//                            len+currentX,
//                            textHeight* index,
//                            a,f, Shader.TileMode.CLAMP);
//                    currentPaint.setShader(shader);
//                    // 画当前行
//                    canvas.drawText(currentLrc, currentX, (textHeight)* index, currentPaint);
//
//                    // 画当前行下面的
//                    for (int i = index + 1; i < lrcLists.size(); i++) {
//                        String lrc =  lrcLists.get(i).getLineStr();
//                        float x = (width - notCurrentPaint.measureText(lrc)) / 2;
//                        canvas.drawText(lrc, x, (textHeight) * i, notCurrentPaint);
//                    }
//                    canvas.restore();*/
//
//                    //绘制歌词
//                    for (int i = 0; i < lrcLists.size(); i++, tempY += textHeight) {
////                        float currentX = DisplayUtil.dip2px(mContext, 100);
//                        if (i == index) {
//                            // 获得字符串的"长度"
//                            String currentLrc = lrcLists.get(i).getLineStr().replaceAll("\\s", " ").trim();
//                            Rect rect = new Rect();
//                            currentPaint.getTextBounds(currentLrc, 0, currentLrc.length(), rect);
//                            len = rect.width();
////                            tempY = rect.height();
//                            Pattern p = Pattern.compile("[a-zA-Z]");
//                            isEn = p.matcher(currentLrc).find();
////                            float len = currentPaint.getTextSize() * currentLrc.length();
//                            float currentX = (width - currentPaint.measureText(currentLrc)) / 2;
////                            currentX = DisplayUtil.dip2px(mContext, 100);
//                            // 参数color数组表示参与渐变的集合
//                            // 参数float数组表示对应颜色渐变的位置
//                            int[] a = new int[]{getContext().getResources().getColor(R.color.app_oher_red), getResources().getColor(R.color.skr_three_color)};
//                            float[] f = new float[]{float1, float2};
//                            Shader shader = new LinearGradient(currentX,
//                                    tempY,
//                                    currentX + len + currentPaint.getTextSize() / 3,
//                                    tempY,
//                                    a, f, Shader.TileMode.CLAMP);
//                            currentPaint.setShader(shader);
////                            currentPaint.setShadowLayer(10,5,5,Color.GRAY);
////                            canvas.drawText(lrcLists.get(i).getLineStr().replaceAll("\\s"," ").trim(), width / 2, tempY, currentPaint);
////                            float showWidthStart = DisplayUtil.dip2px(mContext, 100);
//                            //歌词跑马灯
//                            if (lrcLists.size() != 0){
//                                startCurrentMarquee(canvas, currentX , lrcLists.get(i).getLineStr().replaceAll("\\s", " ").trim(), currentPaint);
//                            }
//
//                        } else if (i == pos) {
////                            currentX = DisplayUtil.dip2px(mContext, 100);
//                            canvas.drawText(lrcLists.get(i).getLineStr().replaceAll("\\s", " ").trim(), width / 2, tempY, linePaint);
////                            canvas.drawText(lrcLists.get(i).getLineStr().replaceAll("\\s", " ").trim(),currentX * 2, tempY, linePaint);
//                        } else {
//                            //跑马灯
////                            currentX = DisplayUtil.dip2px(mContext, 100);
////                            performStartMarquee(canvas, currentX * 2, lrcLists.get(i).getLineStr().replaceAll("\\s", " ").trim(), notCurrentPaint);
//                            canvas.drawText(lrcLists.get(i).getLineStr().replaceAll("\\s"," ").trim(), width / 2, tempY, notCurrentPaint);
//                        }
//                    }
//                    break;
//                case QUERY_ONLINE_FAIL:
//                    tipsPaint.setUnderlineText(true);
//                    canvas.drawText("搜索失败，请重试", width / 2, tempY, tipsPaint);
//                    break;
//                case QUERY_ONLINE_NULL:
//                    tipsPaint.setUnderlineText(false);
//                    canvas.drawText("网络无匹配歌词", width / 2, tempY, tipsPaint);
//                    break;
//            }
//            isChanging = false;
////            canvas.restore();
//
////		  	if(MusicManager.OperateState.READLRC_LISTNULL.equals(lrcState)){
////		    	canvas.drawText("歌词内容为空", width/2, tempY, notCurrentPaint);
////		    	return;
////		    }else if(MusicManager.OperateState.READLRCFILE_FAIL.equals(lrcState)){
////		    	canvas.drawText("暂无歌词", width/2, tempY, notCurrentPaint);
////		    	return;
////		    }
////		    else if(MusicManager.OperateState.READLRC_SUCCESS.equals(lrcState)){
////
////			    //绘制歌词
////			    for(int i=0;i<lrcLists.size();i++,tempY+=textHeight){
////			    	if(i==index){
////			    		canvas.drawText(lrcLists.get(i).getLrcStr(), width/2, tempY, currentPaint);
////			    	}else if(i==pos){
////			    		canvas.drawText(lrcLists.get(i).getLrcStr(), width/2, tempY, linePaint);
////			    	}else{
////			    		canvas.drawText(lrcLists.get(i).getLrcStr(), width/2, tempY, notCurrentPaint);
////			    	}
////			    }
////
////		    	return;
////		    }else if(MusicManager.OperateState.READLRC_ONLINE.equals(lrcState)){
////		    	String drawContentStr="在线匹配歌词";
////
////		    	for(int i=0;i<count;i++){
////		    		drawContentStr+=".";
////		    	}
////
////		    	count++;
////		    	if(count>=6) count=0;
////
////		    	canvas.drawText(drawContentStr, width/2, tempY, notCurrentPaint);
////
////
////		    	handler.sendEmptyMessageDelayed(1, 500);
////		    	return;
////		    }else if(MusicManager.OperateState.READLRCONLINE_FAIL.equals(lrcState)){
////		    	canvas.drawText("从网络加载歌词失败", width/2, tempY, notCurrentPaint);
////		    	return;
////		    }
//        }
//
//        //处理当前行歌词跑马灯
//        private void startCurrentMarquee(Canvas canvas, float currentX, String currentLrc, Paint paint) {
//            currentLrc = currentLrc + " ";
//            float wordSize = paint.measureText("好");//非汉字宽用汉字的一半计算
//            float englishWordSize = paint.measureText("a");
////            float englishWordSize1 = paint.measureText(".");
////            float englishWordSize2 = paint.measureText("0");
////            LogUtils.w("ha","englishWordSize==" + englishWordSize);
////            LogUtils.w("ha","englishWordSize1==" + englishWordSize1);
////            LogUtils.w("ha","englishWordSize2==" + englishWordSize2);
//            //固定显示区域
//            float showWidthStart = DisplayUtil.dip2px(mContext, 100);
//            float showWidthEnd = getWidth();
////            float block = showWidthEnd - showWidthStart;
//            float block = showWidthEnd;
//            int k ;
//            isEn = false;
//            if (isEn) {
//                k = (int) (block / englishWordSize);
//            } else {
//                //固定区域显示汉字的个数
//              k = (int) (block / wordSize);
//            }
//            //计算歌词中汉字和其他字符的个数:
////            int[] result = findCount(currentLrc);
//            //当前行的实际宽
////            float totalLen = paint.measureText(currentLrc.replaceAll("[0-9]","")) + (result[0]+result[1])/2;
//            float totalLen = paint.measureText(currentLrc);
//            if (totalLen > block ) {//要处理
//                //currentWord：记录当前位子到达当前行的第几个字
//                // canvas.drawText(lrcLists.get(i).getLineStr().replaceAll("\\s"," ").trim(), width / 2, tempY, currentPaint);
//                if (currentWord < 4) {
//                    canvas.drawText(currentLrc.substring(0, k), currentX, tempY, currentPaint);
//                    mTemp = 0;
//                } else if (currentWord >= 4) {
//                    LogUtils.w("test", "currentWord===" + currentWord);
////                    if (mTemp < stopWord && lastCurrentWord != currentWord) {
//                    if (mTemp < stopWord && isWordChange) {
//                        mTemp++;
//                    }
////                    canvas.drawText(currentLrc.substring(currentWord-1,k + currentWord-1), currentX, tempY, currentPaint);
//                    float remianBlock;
//                    float remianLen;
//                    if (isEn) {
//                        //可显示剩余长度
//                        remianBlock = (k - currentWord) * englishWordSize;
//                        //实际剩余长度
//                        remianLen = (currentLrc.length() - currentWord) * englishWordSize;
//                    } else {
//                        //可显示剩余长度
//                        remianBlock = (k - currentWord) * wordSize;
//                        //实际剩余长度
//                        remianLen = (currentLrc.length() - currentWord) * wordSize;
//                    }
//                    if (remianBlock < remianLen) {
//                        LogUtils.w("test", "currentWord===" + currentWord);
//                        LogUtils.w("test", "k===" + k);
//                        LogUtils.w("test", "mTemp===" + mTemp);
//                        LogUtils.w("test", "stopWord===" + stopWord);
//                        if ((k + mTemp - 1) <= currentLrc.length() - 1) {
//                            canvas.drawText(currentLrc.substring(mTemp, k + mTemp - 1) + currentLrc.charAt(k + mTemp - 1), currentX, tempY, currentPaint);
//                        } else {
//                            canvas.drawText(currentLrc.substring(stopWord, currentLrc.length() - 1), currentX, tempY, currentPaint);
//                        }
//                    } else {
//                        canvas.drawText(currentLrc.substring(mTemp), currentX, tempY, currentPaint);
//                    }
//                }
//
//            } else {
//                currentPaint.setShader(null);
//                float len = currentPaint.getTextSize() * currentLrc.length();
//                currentX = (width - currentPaint.measureText(currentLrc)) / 2;
//                int[] a = new int[]{getContext().getResources().getColor(R.color.app_oher_red), getResources().getColor(R.color.skr_three_color)};
//                float[] f = new float[]{float1, float2};
//                Shader shader = new LinearGradient(currentX,
//                        tempY,
//                        currentX + len + currentPaint.getTextSize() / 3,
//                        tempY,
//                        a, f, Shader.TileMode.CLAMP);
//                currentPaint.setShader(shader);
////                currentPaint.setShadowLayer(10,5,5,Color.GRAY);
//                canvas.drawText(currentLrc, width/2, tempY, currentPaint);
//            }
//        }
//
//        private int[] findCount(String currentLrc) {
//            int[] arr = new int[4];
//            int numCount = 0;
//            int charCount = 0;
//            int hanZiCount = 0;
//            for (int i = 0; i < currentLrc.length(); i++) {
//                String c = currentLrc.charAt(i) + "";
//                if (c.matches("[0-9]")) {
//                    numCount++;
//                } else if (c.matches("[a-zA-Z]")) {
//                    charCount++;
//                }
//            }
//            //计算汉字的个数
//            hanZiCount = currentLrc.length() - currentLrc.replaceAll("[\u4e00-\u9fa5]", "").length();
//            //其他字符
//            int otherCount = currentLrc.length() - numCount - charCount - hanZiCount;
//            arr[0] = numCount;
//            arr[1] = charCount;
//            arr[2] = hanZiCount;
//            arr[3] = otherCount;
//            return arr;
//        }
//
//        private void performStartMarquee(Canvas canvas, float currentX, String currentLrc, Paint paint) {
//            float wordSize = paint.measureText("好");
////            float englishWordSize = paint.measureText("a");
//            //固定显示区域
//            float showWidthStart = DisplayUtil.dip2px(mContext, 100);
//            float showWidthEnd = getWidth();
//            float block = showWidthEnd - showWidthStart;
//            int k;
////            if (isEn){
////                k = (int) (block / englishWordSize);
////            }else {
//            //固定区域显示汉字的个数
//            k = (int) (block / wordSize);
////            }
//            //当前行的实际宽
//            float totalLen = paint.measureText(currentLrc);
//            if (block < totalLen) {
//                //要做处理
//                currentLrc = currentLrc.substring(0, k);
//            }
//            canvas.drawText(currentLrc, currentX, tempY, paint);
//        }
//
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            // TODO Auto-generated method stub
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//
//            if (lrcLists != null) {
//                heightMeasureSpec = (int) (height + textHeight * (lrcLists.size() - 1));
//                setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//            }
//
//        }
//
//    }
//
//    ;
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        // TODO Auto-generated method stub
//        super.onDraw(canvas);
//        /*canDrawLine=false;
//        if (canDrawLine) {
//            canvas.drawLine(0, scrollY + height / 2, width, scrollY + height / 2, linePaint);
//            canvas.drawText(TimeUtil.mill2mmss(lrcLists.get(pos).getLineTime().getStartTime()), 42, scrollY + height / 2 - 2, linePaint);
//        }*/
//    }
//
//    private void init() {
//        setFocusable(true);    //设置该控件可以有焦点
//        this.setWillNotDraw(false);
//
//        norTextSize = DisplayUtil.sp2px(mContext, 16);
//        lightTextSize = DisplayUtil.sp2px(mContext, 24);
//        tipsTextSize = DisplayUtil.sp2px(mContext, 20);
////        tipsTextSize = DisplayUtil.sp2px(mContext, 16);
//        textHeight = norTextSize + DisplayUtil.dip2px(mContext, 18);
//
//        //高亮歌词部分
//        currentPaint = new Paint();
//        currentPaint.setAntiAlias(true);    //设置抗锯齿
//        currentPaint.setTextAlign(Paint.Align.CENTER);    //设置文本居中
//
//        //非高亮歌词部分
//        notCurrentPaint = new Paint();
//        notCurrentPaint.setAntiAlias(true);
//        notCurrentPaint.setTextAlign(Paint.Align.CENTER);
//
//        //提示信息画笔
//        tipsPaint = new Paint();
//        tipsPaint.setAntiAlias(true);
//        tipsPaint.setTextAlign(Paint.Align.CENTER);
//
//        //
//        linePaint = new Paint();
//        linePaint.setAntiAlias(true);
//        linePaint.setTextAlign(Paint.Align.CENTER);
//
//        //设置画笔颜色
//        currentPaint.setColor(getResources().getColor(R.color.skr_three_color));
////        notCurrentPaint.setColor(Color.argb(140, 255, 255, 255));
//        notCurrentPaint.setColor(getResources().getColor(R.color.skr_three_color));
////        notCurrentPaint.setColor(getResources().getColor(R.color.skr_three_color));
//        tipsPaint.setColor(getResources().getColor(R.color.skr_three_color));
////        tipsPaint.setColor(getResources().getColor(R.color.skr_three_color));
//        linePaint.setColor(getResources().getColor(R.color.skr_three_color));
////        linePaint.setColor(getResources().getColor(R.color.skr_three_color));
//
//
//        //设置字体
//        currentPaint.setTextSize(lightTextSize);
//        currentPaint.setTypeface(Typeface.SERIF);
//
//        notCurrentPaint.setTextSize(norTextSize);
//        notCurrentPaint.setTypeface(Typeface.DEFAULT);
//
//        tipsPaint.setTextSize(tipsTextSize);
//        tipsPaint.setTypeface(Typeface.DEFAULT);
//
////        linePaint.setTextSize(lightTextSize);
//        linePaint.setTextSize(norTextSize);
//        linePaint.setTypeface(Typeface.SERIF);
//
//    }
//
//    @Override
//    public void invalidate() {
//        // TODO Auto-generated method stub
//        super.invalidate();
//
//        //更新画笔
//        norTextSize = DisplayUtil.sp2px(mContext, 16 * mCoefficient);
//        lightTextSize = DisplayUtil.sp2px(mContext, 16 * mCoefficient);
//        tipsTextSize = DisplayUtil.sp2px(mContext, 20 * mCoefficient);
//        //设置字体
//        currentPaint.setTextSize(lightTextSize);
//        notCurrentPaint.setTextSize(norTextSize);
//        tipsPaint.setTextSize(tipsTextSize);
//        linePaint.setTextSize(norTextSize);
//
//        if (lrcTextView != null) {
//            lrcTextView.invalidate();
//        }
//
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        /*lastY=0;
//        if (lastY==getScrollY()){
//            handler.sendEmptyMessageDelayed(11,3000);
//        }else {
//            lastY=getScrollY();
//            if (isHandScroll){
//                isScroll=true;
//            }
//        }*/
//
//        if (itouchListener != null) {
//            int y = this.getScrollY() + DisplayUtil.dip2px(mContext, 9);//加上一半的行间距
//            pos = (int) (y / textHeight);
//            itouchListener.scrollPosition(pos);
//            pos = -1;
//        }
//    }
//
//    /* @Override
//    public void onScrollChanged() {
//        // TODO Auto-generated method stub
//
//
//    }*/
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // TODO Auto-generated method stub
//
//        //界面不能被触摸
//        if (!canTouchLrc) return true;
//
//        switch (lrcState) {
//            case READ_LOC_FAIL:
//            case QUERY_ONLINE_FAIL:
//                //return handleTouchLrcFail(event.getAction());
//            case READ_LOC_OK:
//            case QUERY_ONLINE_OK:
//                return handleTouchLrcOK(event.getAction());
//
//        }
//
//        return false;
//    }
//
//    boolean handleTouchLrcOK(int action) {
//
//        switch (action) {
//
//            case MotionEvent.ACTION_DOWN:
////                isScroll=false;
//                LogUtils.w("touch","touch ======  down");
//                handler.removeMessages(11);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                LogUtils.w("touch","touch ======  move");
//                isHandScroll = true;
//                scrollY = this.getScrollY();
//                pos = (int) (this.getScrollY() / textHeight);
//                canDrawLine = true;
//                this.invalidate();
//                if (itouchListener != null) {
//                    itouchListener.touchDown(pos);
//                }
//                LogUtils.w("Position:", "" + pos);
//                break;
//            case MotionEvent.ACTION_UP:
//                LogUtils.w("touch","touch ======  up");
//                /*if(pos!=-1){
//                    //MusicManager.getInstance().seekTo(lrcLists.get(pos).getLrcTime());
//                    //通过滑动歌词来改变播放进度
//                    if (PlayerType==0){
//                        PlayerManager.getPlayer().seekTo(lrcLists.get(pos).getLrcTime());
//                    }else if (PlayerType==1){
//                        mediaPlayer.seekTo(lrcLists.get(pos).getLrcTime());
//                    }
//                }*/
//                upAction();
//                break;
//        }
//
//        return false;
//    }
//
//    public void upAction() {
//        if (itouchListener != null) {
//            itouchListener.touchUp();
//        }
//        handler.sendEmptyMessageDelayed(11, 3000);//3s后才会自动滑到中间位置
////        canDrawLine = false;
//        pos = -1;
////        this.invalidate();
//    }
//    /*boolean handleTouchLrcFail(int action) {
//        switch (action) {
//            case MotionEvent.ACTION_UP:
//                if (onLrcSearchClickListener != null) {
//                    onLrcSearchClickListener.onLrcSearchClicked(this);
//                }
//                break;
//        }
//        return true;
//    }*/
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 11:
//                    canDrawLine = false;
//                    break;
//            }
//        }
//    };
//
//    public interface ItouchListener {
//        void touchDown(int index);
//
//        void touchUp();
//
//        void scrollPosition(int position);
//    }
//
//    ItouchListener itouchListener;
//
//    public void setItouchListener(ItouchListener itouchListener) {
//        this.itouchListener = itouchListener;
//    }
//
//    //设置乘积系数因子
//    public void setFontCoefficient(float coefficient) {
//        mCoefficient = coefficient;
//    }
//
//}
