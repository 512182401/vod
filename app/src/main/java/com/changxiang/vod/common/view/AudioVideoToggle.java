//package com.changxiang.vod.common.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.DensityUtil;
//
//
///**
// * Created by 15976 on 2018/1/18.
// * 录制mv和MP3按钮
// */
//
//public class AudioVideoToggle extends View implements View.OnClickListener {
//
//    private boolean isClickable = true;//是否可以点击
//    private Bitmap mp3_bg;
//    private Bitmap mv_btn;
//    private Paint paint;
//    private int width;
//    private int height;
//    private Bitmap mv_bg;
//    private Bitmap mp3_btn;
//    private int btn_width;
//    private int btn_height;
//    private float startX;
//    private float leftDistance;
//    private int leftDistanceMax;
//    private float lastX;
//    private boolean isChange;
//    private Context context;
//    private boolean open;
//    private int finalSelect ; //0 : 选中MV    1:选中Mp3
//    public static int SELECT_MP3 = 1;
//    public static int SELECT_MV = 0;
//
//    public AudioVideoToggle(Context context) {
//        this(context, null);
//    }
//
//    public AudioVideoToggle(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public AudioVideoToggle(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//        initView();
//    }
//
//    private void initView() {
//        mp3_bg = BitmapFactory.decodeResource(getResources(), R.drawable.mp3_bg);
//        mv_btn = BitmapFactory.decodeResource(getResources(), R.drawable.mv_btn);
//        mv_bg = BitmapFactory.decodeResource(getResources(), R.drawable.mv_bg);
//        mp3_btn = BitmapFactory.decodeResource(getResources(), R.drawable.mp3_btn);
//
//        //View的大小是背景的大小
//        width = mp3_bg.getWidth();
//        height = mp3_bg.getHeight();
//        btn_width = mv_btn.getWidth();
//        btn_height = mv_btn.getHeight();
//
//        leftDistanceMax = width - btn_width;
//
//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.CENTER);
//
//        setOnClickListener(this);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //View的尺寸
//        setMeasuredDimension(width, height);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (isClickable) {
//            if (!open) {
//                canvas.drawBitmap(mp3_bg, 0, 0, paint);
//                canvas.drawBitmap(mv_btn, 0, height / 4 - DensityUtil.dip2px(context, 2), paint);
//                leftDistance = 0;
//                finalSelect = SELECT_MP3;
//            } else {
//                canvas.drawBitmap(mv_bg, 0, 0, paint);
//                canvas.drawBitmap(mp3_btn, leftDistanceMax, height / 4 - DensityUtil.dip2px(context, 2), paint);
//                leftDistance = leftDistanceMax;
//                finalSelect = SELECT_MV;
//            }
//        } else {
//            if (!isChange) {
//                canvas.drawBitmap(mp3_bg, 0, 0, paint);
//                canvas.drawBitmap(mv_btn, leftDistance, height / 4 - DensityUtil.dip2px(context, 2), paint);
//            } else {
//                canvas.drawBitmap(mv_bg, 0, 0, paint);
//                canvas.drawBitmap(mp3_btn, leftDistance, height / 4 - DensityUtil.dip2px(context, 2), paint);
//            }
//        }
//
//    }
//
//    @Override
//    public void onClick(View v) {
////        if (isClickable) {
////            LogUtils.w("toggle","点击事件");
////            open = !open;
////            invalidate();
////        }
//        if (isClickable){
//            if (listener != null){
//                listener.myClick();
//            }
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
////                LogUtils.w("toggle","ACTION_DOWN");
//                lastX = startX = event.getX();
//                isClickable = true;
//                break;
//            case MotionEvent.ACTION_MOVE:
////                LogUtils.w("toggle","ACTION_MOVE");
//                float moveX = event.getX();
//                float distance = moveX - lastX;
//
//                //判断点击还是拖动
////                LogUtils.w("toggle", moveX  + "===" +  startX + "===============" + Math.abs(moveX - startX));
//                if (Math.abs(moveX - startX) > 5) {  //滑动
//                    isClickable = false;
//                    leftDistance = distance + leftDistance;
//                    //越界处理
//                    if (leftDistance > leftDistanceMax) {
//                        leftDistance = leftDistanceMax;
//                    }
//                    if (leftDistance < 0) {
//                        leftDistance = 0;
//                    }
//
//                    if (leftDistance >= leftDistanceMax / 2) {
//                        isChange = true;
//                    } else {
//                        isChange = false;
//                    }
//                    lastX = moveX;
//                    invalidate();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
////                LogUtils.w("toggle","ACTION_UP");
//                if (!isClickable) {
//                    if (leftDistance >= leftDistanceMax / 2) {
//                        leftDistance = leftDistanceMax;
//                        isChange = true;
//                        finalSelect = SELECT_MV;
//                    } else {
//                        leftDistance = 0;
//                        finalSelect = SELECT_MP3;
//                        isChange = false;
//                    }
//                    if (touchlistener != null){
//                        touchlistener.myTouch();
//                    }
//                    invalidate();
//                }
//                break;
//        }
//        return true;
//    }
//
//    //返回选中的状态
//    public int getSelect() {
//        return finalSelect;
//    }
//
//    public boolean getIsClick() {
//        return isClickable;
//    }
//
//    /**
//     * 设置选中的状态
//     * @param finalSelect
//     */
//    public void setSelectAndInvalidate(int finalSelect) {
//        this.finalSelect = finalSelect;
//        isClickable = false;
//        if (finalSelect == SELECT_MV) {
//            leftDistance = leftDistanceMax;
//            isChange = true;
//        } else if (finalSelect == SELECT_MP3) {
//            leftDistance = 0;
//            isChange = false;
//        }
//        invalidate();
//    }
//
//    public void setSelect(int finalSelect) {
//        this.finalSelect = finalSelect;
//    }
//
//    private OnToggleClickListener listener;
//
//    public void setOnToggleClickListener(OnToggleClickListener listener) {
//        this.listener = listener;
//    }
//
//    public interface OnToggleClickListener {
//        void myClick();
//    }
//
//
//    private OnToggleTouchListener touchlistener;
//
//    public void setOnToggleTouchListener(OnToggleTouchListener touchlistener) {
//        this.touchlistener = touchlistener;
//    }
//
//    public interface OnToggleTouchListener {
//        void myTouch();
//    }
//}
