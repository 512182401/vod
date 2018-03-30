//package com.changxiang.vod.common.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.LinearGradient;
//import android.graphics.Paint;
//import android.graphics.Shader;
//import android.text.TextPaint;
//import android.util.AttributeSet;
//
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.module.entry.KrcLine;
//import com.quchangkeji.tosingpk.module.entry.KrcWord;
//
//import java.util.List;
//
////import com.quchangkeji.tosing.R;
////import com.quchangkeji.tosing.module.entry.KrcLine;
////import com.quchangkeji.tosing.module.entry.KrcWord;
//
///**
// * Created by 15976 on 2017/5/23.
// */
//
//public class LinearGradientTextView extends android.support.v7.widget.AppCompatTextView {
//    private int strokeColor;
//    private int strokeWidth;
//    private float float1 = 0.0f;//渲染百分比
//    private float float2 = 0.01f;
//    private List<KrcLine> lrcLists;
//    private int index;
//    private int lastIndex;
//    private String text = "geci";
//
//    private TextPaint strokePaint;
//
//    public LinearGradientTextView(Context context) {
//        super(context);
//    }
//
//    public LinearGradientTextView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public LinearGradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView, defStyleAttr, 0);
//
//        strokeColor = a.getColor(R.styleable.StrokeTextView_tv_border_color, 0);
//        strokeWidth = a.getDimensionPixelSize(R.styleable.StrokeTextView_tv_border_width, 0);
//    }
//
//    public void setStrokeColor(int color) {
//        strokeColor = color;
//    }
//
//    public List<KrcLine> getLrcLists() {
//        return lrcLists;
//    }
//
//    public void setLrcLists(List<KrcLine> lrcLists) {
//        this.lrcLists = lrcLists;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//
//        if (strokeWidth > 0) {
//            if (strokePaint == null) {
//                strokePaint = new TextPaint();
//            }
//
//            // 复制原来TextViewg画笔中的一些参数
//            TextPaint paint = getPaint();
//            strokePaint.setTextSize(paint.getTextSize());
//            strokePaint.setFlags(paint.getFlags());
//            strokePaint.setTypeface(paint.getTypeface());
//            strokePaint.setAlpha(paint.getAlpha());
//
//            // 自定义描边效果
//            strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);// 设置不同Style有不同效果哟
//            strokePaint.setColor(strokeColor);
//            strokePaint.setStrokeWidth(strokeWidth);
//            String text = getText().toString();
//
//            float len = strokePaint.getTextSize() * text.length();
//            float currentX = (getWidth() - strokePaint.measureText(text)) / 2;
//            // 参数color数组表示参与渐变的集合
//            // 参数float数组表示对应颜色渐变的位置
//            int[] a = new int[]{getContext().getResources().getColor(R.color.app_oher_red), Color.WHITE};
//            float[] f = new float[]{float1, float2};
//            Shader shader = new LinearGradient(currentX,
//                    getBaseline(),
//                    currentX + len+strokePaint.getTextSize()/3,
//                    getBaseline(),
//                    a, f, Shader.TileMode.CLAMP);
//            strokePaint.setShader(shader);
//
//            //在文本底层画出带描边的文本
//            canvas.drawText(text, (getWidth() - strokePaint.measureText(text)) / 2, getBaseline(), strokePaint);
//        }
//
//        super.onDraw(canvas);
//    }
//
//    /**
//     * 根据播放进度来改变歌词
//     * @param time 播放进度
//     */
//    public void updateLrc(long time) {
//        if (lrcLists == null || lrcLists.size() == 0) {
//            return;
//        }
//        KrcLine kl = null;
//        for (int i = 0; i < lrcLists.size(); i++) {
//            // 遍历歌词列表来获取当前行和当前字
//            index = i;
//            /*if (index!=lastIndex||index==lrcLists.size()-1){
//                this.scrollTo(0, (int) (index * textHeight));
//                lastIndex=index;
//            }*/
//            float1 = 0.0f;
//            float2 = 0.001f;
//            kl = lrcLists.get(i);//一行歌词对象（包含歌词，时间）
//            if (kl.getLineTime().getStartTime() <= time && time < kl.getLineTime().getStartTime() + kl.getLineTime().getSpanTime()) {
//                //当前行
//                text = kl.getLineStr();
//                this.setText(text);
//                int len = kl.getLineStr().length();//当前行的字数
//                int currentWordIndex = 0;
////                KrcLineTime klt = null;
//                KrcWord klt = null;
//                long timeSpan = time - kl.getLineTime().getStartTime();//该句歌词已经播放的时长
//                for (int j = 0; j < kl.getWordTime().size(); j++) {
//                    currentWordIndex = j;
//                    klt = kl.getWordTime().get(j);//一个字对应一个时间，j是第几个字
//                    if (klt.getStartTime() <= timeSpan && timeSpan < klt.getStartTime() + klt.getSpanTime()) {//定位到第几个字
//                        //计算当前歌词所在的百分比
//                        float1 = (j + (timeSpan - klt.getStartTime()) * 1.0f / klt.getSpanTime()) / len;
//                        float2 = float1 > 0.99f ? float1 : (float1 + 0.01f);
//                        break;
//                    }
//                }
//                break;
//            } else if (kl.getLineTime().getStartTime() >= time) {
//                break;
//            }
//        }
//        postInvalidate();
//    }
//}
