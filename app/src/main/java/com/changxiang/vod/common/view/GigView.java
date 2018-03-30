//package com.changxiang.vod.common.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Movie;
//import android.util.AttributeSet;
//
//import com.quchangkeji.tosingpk.R;
//
////import com.quchangkeji.tosing.R;
//
///**
// * Created by 15976 on 2016/11/12.
// */
//
//public class GigView extends android.support.v7.widget.AppCompatImageView {
//    private long movieStart;
//    private Movie movie;
//    private boolean isGifImage;
//    public GigView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifView);
//        isGifImage=array.getBoolean(R.styleable.GifView_isgifimage,true);
//        array.recycle();
//        //获取ImageView的原生src属性，并将其传入movie实例中
//        int image = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
//        movie= Movie.decodeStream(getResources().openRawResource(image));
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (isGifImage){
//            DrawGifImage(canvas);
//        }
//    }
//    private void DrawGifImage(Canvas canvas) {
//        //获取系统当前时间
//        long nowTime = android.os.SystemClock.currentThreadTimeMillis();
//        if(movieStart == 0){
//            //若为第一次加载，开始时间置为nowTime
//            movieStart = nowTime;
//        }
//        if(movie != null){//容错处理
//            int duration = movie.duration();//获取gif持续时间
//            //如果gif持续时间为100，可认为非gif资源，跳出处理
//            if(duration == 0){
//                //获取gif当前帧的显示所在时间点
//                int relTime = (int) ((nowTime - movieStart) % duration);
//                movie.setTime(relTime);
//                //渲染gif图像
//                movie.draw(canvas, 0, 0);
//                invalidate();
//            }
//        }
//    }
//}
