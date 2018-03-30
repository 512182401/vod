//package com.changxiang.vod.common.view.editimage;
//
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.graphics.Path;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.graphics.Typeface;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.view.View;
//import android.widget.EditText;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class StickerItem {
//    private static final float MIN_SCALE = 0.15f;
//    private static final int HELP_BOX_PAD = 35;
//    private static final int BUTTON_WIDTH = 35;
//
//    public float rowH = 1.25f;//行间距是字号大小的比例
//    public float lineW = 0.08f;//列间距是字号大小的比例
//    public Bitmap bitmap;
//    public Bitmap textBitmap;
//    public Rect srcRect;// 原始图片坐标
//    public RectF dstRect;// 绘制目标坐标
//    private Rect helpToolsRect;
//    public RectF deleteRect;// 删除按钮位置
//    public RectF rotateRect;// 旋转按钮位置
//    public RectF mirroringRect;// 镜像按钮位置
//
//    public boolean istext = false;//是否是字幕
//    public boolean isorigin = true;//是否是原始的，非镜像
//    public String textstr = "趣";//输入文字内容
//    List<String> textstrlist = new ArrayList<>();//字幕显示文字内容
//
//    public float textSize = 34f;
//    public int textcolor = 0xff9b82fc;//Color.RED
//    float textleft, texttop, textrigth, textbottom;//输入框占底图的差比；
//    int cursorWidth = 0;//默认为2dp
//    RectF helpBox;
//    RectF textBox;//输入框
//    RectF mTextRect;//镜像输入框
//    float mirroringX, mirroringY;//左下角坐标
//    public Matrix matrix;// 变化矩阵
//    private float roatetAngle = 0;
//    boolean isDrawHelpTool = false;
//    private Paint dstPaint = new Paint();
//    private Paint paint = new Paint();
//    private Paint textPaint = new Paint();//字幕颜色
//    private Paint testPaint = new Paint();//字幕颜色
//    private Paint tryPaint = new Paint();//字幕颜色
//
//    private Paint savetextPaint = new Paint();//保存合成字幕颜色
//    private Paint textPaintBg = new Paint();//输入框颜色
//    private Paint cursorPaint = new Paint();//光标
//    private Paint helpBoxPaint = new Paint();
//
//    EditText meditText;
//    private float initWidth;// 加入屏幕时原始宽度
//
//    private static Bitmap deleteBit;//删除
//    private static Bitmap rotateBit;//变形
//    private static Bitmap mirroring;//镜像
//
//    private Paint greenPaint = new Paint();
//    public RectF detectRotateRect;
//    public RectF detectDeleteRect;
//    public RectF detectMirroringRect;
//    Context context;
//
//    // 缩放操作图片
//    private StickerActionIcon zoomIcon;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public StickerItem(Context context) {
//
//        this.context = context;
//        textcolor = 0xff9b82fc;
//        cursorWidth = DensityUtil.dp2px(context, 2f);
////        helpBoxPaint.setColor(Color.BLACK);
//        helpBoxPaint.setColor(0xffff5f5f);
//        helpBoxPaint.setStyle(Style.STROKE);
//        helpBoxPaint.setAntiAlias(true);
//        helpBoxPaint.setStrokeWidth(4);
//
//        textPaintBg.setColor(0xffff5f5f);
//        textPaintBg.setStyle(Style.STROKE);
//        textPaintBg.setAntiAlias(true);
//        textPaintBg.setStrokeWidth(4);
//
//        textPaint.setStrokeWidth(0);
//        textPaint.setColor(textcolor);//textcolor = Color.RED
//        textPaint.setTextSize(textSize);//textSize 34
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
//        textPaint.setLetterSpacing(lineW);
////        textPaint.setLetterSpacin
//
//        savetextPaint.setStrokeWidth(0);
//        savetextPaint.setColor(textcolor);//textcolor = Color.RED
//        savetextPaint.setTextSize(textSize);//textSize 34
//        savetextPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
//
//        dstPaint = new Paint();
//        dstPaint.setColor(Color.RED);
//        dstPaint.setAlpha(120);
//
//        greenPaint = new Paint();
//        greenPaint.setColor(Color.GREEN);
//        greenPaint.setAlpha(120);
//        // 导入工具按钮位图
//        if (deleteBit == null) {
//            deleteBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.sticker_delete);
//        }// end if
//        if (rotateBit == null) {
//            rotateBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.sticker_rotate);
//        }// end if
//        if (mirroring == null) {
//            mirroring = BitmapFactory.decodeResource(context.getResources(), R.drawable.sticker_mirroring);
//        }
//
//        zoomIcon = new StickerActionIcon(context);
//    }
//
//
//    public void init(Bitmap addBit, View parentView, boolean istext) {
//        this.istext = istext;//
////        this.istext = true;//测试
//        this.bitmap = addBit;
//        this.srcRect = new Rect(0, 0, addBit.getWidth(), addBit.getHeight());
////        LogUtils.sysout("88888888888888 addBit.getWidth()"+addBit.getWidth()+","+addBit.getHeight());
//
//        int bitWidth = Math.min(addBit.getWidth(), parentView.getWidth() >> 1);
//        int bitHeight = (int) bitWidth * addBit.getHeight() / addBit.getWidth();
////        if(istext) {
////             bitWidth = Math.min(addBit.getWidth()<<1, parentView.getWidth());
////             bitHeight = (int) (bitWidth * addBit.getHeight() / addBit.getWidth());
////        }
//        int left = (parentView.getWidth() >> 1) - (bitWidth >> 1);
//        int top = (parentView.getHeight() >> 1) - (bitHeight >> 1);
//        this.dstRect = new RectF(left, top, left + bitWidth, top + bitHeight);
//        this.matrix = new Matrix();
//        this.matrix.postTranslate(this.dstRect.left, this.dstRect.top);
//        this.matrix.postScale((float) bitWidth / addBit.getWidth(), (float) bitHeight / addBit.getHeight(), this.dstRect.left, this.dstRect.top);
//        initWidth = this.dstRect.width();// 记录原始宽度
//        // item.matrix.setScale((float)bitWidth/addBit.getWidth(),
//        // (float)bitHeight/addBit.getHeight());
//        this.isDrawHelpTool = true;
//        this.helpBox = new RectF(this.dstRect);
//        this.textBox = new RectF(this.dstRect);
//        this.mTextRect = new RectF(this.dstRect);
//        updateHelpBoxRect();
//        updateTextRect();//输入区域
//        mirroringTextRect();//输入区域镜像
////        onTextSizechang2();//计算字体大小和颜色
//        refitTextNew();//计算字体大小和颜色
//        helpToolsRect = new Rect(0, 0, deleteBit.getWidth(), deleteBit.getHeight());
//
//        deleteRect = new RectF(helpBox.left - BUTTON_WIDTH, helpBox.top - BUTTON_WIDTH, helpBox.left + BUTTON_WIDTH, helpBox.top + BUTTON_WIDTH);//删除按钮位置
//        rotateRect = new RectF(helpBox.right - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH, helpBox.right + BUTTON_WIDTH, helpBox.bottom + BUTTON_WIDTH);//变形按钮位置
//        mirroringRect = new RectF(helpBox.left - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH, helpBox.left + BUTTON_WIDTH, helpBox.bottom + BUTTON_WIDTH);//镜像按钮位置
//
//        detectRotateRect = new RectF(rotateRect);
//        detectDeleteRect = new RectF(deleteRect);
//        detectMirroringRect = new RectF(mirroringRect);
//    }
//
//    /**
//     * 重新计算矩形的左上角和右下角（left，top）（right，bottom）
//     * 现在增加左下角
//     */
//    private void updateHelpBoxRect() {
//        this.helpBox.left -= HELP_BOX_PAD;
//        this.helpBox.right += HELP_BOX_PAD;
//        this.helpBox.top -= HELP_BOX_PAD;
//        this.helpBox.bottom += HELP_BOX_PAD;
//
//    }
//
//    /**
//     * 获取输入框等相关数据
//     */
//    public void initTextValue(float textleft, float texttop, float textrigth, float textbottom) {
//        LogUtils.sysout("float textleft,float texttop,float textrihth,float textbottom" + textleft + "," + texttop + "," + textrigth + "," + textbottom);
//        this.textleft = textleft;
//        this.texttop = texttop;
//        this.textrigth = textrigth;
//        this.textbottom = textbottom;
//
//    }
//
//    /**
//     * 计算文字输入框的Rect
//     */
//    private void updateTextRect() {
//        if (istext) {
//            this.textBox.left = this.helpBox.left + HELP_BOX_PAD + ((this.helpBox.right - this.helpBox.left - HELP_BOX_PAD - HELP_BOX_PAD) * this.textleft);
//            this.textBox.top = this.helpBox.top + HELP_BOX_PAD + ((this.helpBox.bottom - this.helpBox.top - HELP_BOX_PAD - HELP_BOX_PAD) * this.texttop);
//            this.textBox.right = this.helpBox.left + HELP_BOX_PAD + ((this.helpBox.right - this.helpBox.left - HELP_BOX_PAD - HELP_BOX_PAD) * this.textrigth);
//            this.textBox.bottom = this.helpBox.top + HELP_BOX_PAD + ((this.helpBox.bottom - this.helpBox.top - HELP_BOX_PAD - HELP_BOX_PAD) * this.textbottom);
//            //计算出输入区域的宽高
//        }
//    }
//
//    /**
//     * 修改（镜像）计算文字输入框的Rect
//     */
//    private void mirroringTextRect() {
//        if (istext) {
//            this.mTextRect.left = this.helpBox.right - HELP_BOX_PAD - ((this.helpBox.right - this.helpBox.left - HELP_BOX_PAD - HELP_BOX_PAD) * this.textrigth);
//            this.mTextRect.top = this.helpBox.top + HELP_BOX_PAD + ((this.helpBox.bottom - this.helpBox.top - HELP_BOX_PAD - HELP_BOX_PAD) * this.texttop);
//            this.mTextRect.right = this.helpBox.right - HELP_BOX_PAD - ((this.helpBox.right - this.helpBox.left - HELP_BOX_PAD - HELP_BOX_PAD) * this.textleft);
//            this.mTextRect.bottom = this.helpBox.top + HELP_BOX_PAD + ((this.helpBox.bottom - this.helpBox.top - HELP_BOX_PAD - HELP_BOX_PAD) * this.textbottom);
//            //计算出输入区域的宽高
//        }
//    }
//
//    //获取镜像中点坐标
////    public void updataMirroringXY(double cos,float curLen,int flag ) {
//    public void updataMirroringXY(float left, float top, float right, float bottom) {
//
//
//        //        点(x,y)绕坐标原点旋转a度后的坐标(x',y')：
////        x' = x * cosa - y * sina ;
////        y' = x * sina + y * cosa ;
////        sin(π/2-x)=cosx
////        cos(π/2-x)=sinx
////        sin*sin+cos*cos =1
//
//        //this.dstRect.centerX(), this.dstRect.centerY()
////        double sin =(double) Math.sqrt(1-(cos*cos))*flag;//原始长度
//
//        if (top >= bottom) {
//            LogUtils.sysout("111111111111 top>=bottom=");
//            this.mirroringX = (float) (((left * -1) - (top * 1)) * -1) + this.dstRect.centerX();
//            this.mirroringY = (float) (((left * 1) + (top * -1) * -1)) + this.dstRect.centerY();
//        } else {
//            LogUtils.sysout("2222222222222 top>=dstRect=" + this.dstRect.centerX());
//            this.mirroringX = (float) (((left * -1) - (top * 1))) + this.dstRect.centerX();
//            this.mirroringY = (float) (((left * 1) + (top * -1))) + this.dstRect.centerY();
//        }
//
//    }
//
//    /**
//     * 位置更新
//     *
//     * @param dx
//     * @param dy
//     */
//    public void updatePos(final float dx, final float dy) {
//        this.matrix.postTranslate(dx, dy);// 记录到矩阵中
//
//        dstRect.offset(dx, dy);
//
//        // 工具按钮随之移动
//        helpBox.offset(dx, dy);
//        textBox.offset(dx, dy);//输入框
//        mTextRect.offset(dx, dy);//镜像输入框
//        deleteRect.offset(dx, dy);
//        rotateRect.offset(dx, dy);
//        mirroringRect.offset(dx, dy);
//
//        this.detectRotateRect.offset(dx, dy);
//        this.detectDeleteRect.offset(dx, dy);
//        this.detectMirroringRect.offset(dx, dy);
//    }
//
//    /**
//     * 镜像旋转
//     *
//     * @param oldx
//     * @param oldy
//     * @param dx
//     * @param dy
//     */
//    public void updatamirroring(final float oldx, final float oldy, final float dx, final float dy) {
//        this.bitmap = BitmapConvert.convertBmp(this.bitmap);
//        if (isorigin) {//
//            isorigin = false;
//        } else {
//            isorigin = true;
//        }
////		updateRotateAndScale(oldx,oldy,dx,dy);
//    }
//
//
//    /**
//     * 旋转 缩放 更新
//     *
//     * @param dx
//     * @param dy
//     */
//    public void updateRotateAndScale(final float oldx, final float oldy, final float dx, final float dy) {
//        //获取绘制坐标（相对屏幕）
//        float c_x = dstRect.centerX();
//        float c_y = dstRect.centerY();
////获取删除中点坐标（相对屏幕）
//        float x = this.detectRotateRect.centerX();
//        float y = this.detectRotateRect.centerY();
//
//        // float x = oldx;
//        // float y = oldy;
////偏移坐标（相对原点）
//        float n_x = x + dx;
//        float n_y = y + dy;
//
//        //原始坐标（相对原点）
//        float xa = x - c_x;
//        float ya = y - c_y;
//
//        //现有坐标（相对原点）
//        float xb = n_x - c_x;//
//        float yb = n_y - c_y;
//
//        float srcLen = (float) Math.sqrt(xa * xa + ya * ya);//原始长度
//        float curLen = (float) Math.sqrt(xb * xb + yb * yb);//当前长度
//
//        // System.out.println("srcLen--->" + srcLen + "   curLen---->" +
//        // curLen);
//
//        float scale = curLen / srcLen;// 计算缩放比
//
//        float newWidth = dstRect.width() * scale;//当前长度
//        if (newWidth / initWidth < MIN_SCALE) {// 最小缩放值检测
//            return;
//        }
//
//        this.matrix.postScale(scale, scale, this.dstRect.centerX(), this.dstRect.centerY());// 存入scale矩阵
//        // this.matrix.postRotate(5, this.dstRect.centerX(),
//        // this.dstRect.centerY());
//        scaleRect(this.dstRect, scale);// 缩放目标矩形
//
//        // 重新计算工具箱坐标
//        helpBox.set(dstRect);
//        updateHelpBoxRect();// 重新计算
//        updateTextRect();// 重新计算输入框
//        mirroringTextRect();// 重新计算镜像输入框
//        rotateRect.offsetTo(helpBox.right - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH);
//        deleteRect.offsetTo(helpBox.left - BUTTON_WIDTH, helpBox.top - BUTTON_WIDTH);
//        mirroringRect.offsetTo(helpBox.left - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH);
//        float tt = helpBox.centerX();
//
//        detectRotateRect.offsetTo(helpBox.right - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH);
//        detectDeleteRect.offsetTo(helpBox.left - BUTTON_WIDTH, helpBox.top - BUTTON_WIDTH);
//        detectMirroringRect.offsetTo(helpBox.left - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH);
//
//        double cos = (xa * xb + ya * yb) / (srcLen * curLen);
//        if (cos > 1 || cos < -1)
//            return;
//        float angle = (float) Math.toDegrees(Math.acos(cos));
//        // System.out.println("angle--->" + angle);
//
//
//        // 定理
//        float calMatrix = xa * yb - xb * ya;// 行列式计算 确定转动方向
////        float new_x =
//        int flag = calMatrix > 0 ? 1 : -1;
//        angle = flag * angle;
//
////        mirroringRect.offsetTo(helpBox.left - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH);
////        detectMirroringRect.offsetTo(helpBox.left - BUTTON_WIDTH, helpBox.bottom - BUTTON_WIDTH);
//        //TODO: 获取新的镜像坐标
////        updataMirroringXY(this.dstRect.left,this.dstRect.top,this.dstRect.right,this.dstRect.bottom);;//获取镜像中点坐标
////        mirroringRect.offsetTo((float)mirroringX - BUTTON_WIDTH, (float)mirroringY - BUTTON_WIDTH);
////        detectMirroringRect.offsetTo((float)mirroringX - BUTTON_WIDTH, (float)mirroringY - BUTTON_WIDTH);
//
//        // System.out.println("angle--->" + angle);
//        roatetAngle += angle;
//        this.matrix.postRotate(angle, this.dstRect.centerX(), this.dstRect.centerY());
//
////        矩形绕指定点旋转
//        rotateRect(this.detectRotateRect, this.dstRect.centerX(), this.dstRect.centerY(), roatetAngle);
//        rotateRect(this.detectDeleteRect, this.dstRect.centerX(), this.dstRect.centerY(), roatetAngle);
//        rotateRect(this.detectMirroringRect, this.dstRect.centerX(), this.dstRect.centerY(), roatetAngle);
//
//
//
////        onTextSizechang2();//计算字体大小
//        refitTextNew();//计算字体大小
//    }
//
//
//    //    public boolean istext = false;//是否是字幕
////    public String  str = "";//字幕显示文字内容
//    int showCursor = 0;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void draw(Canvas canvas) {
//        canvas.drawBitmap(this.bitmap, this.matrix, null);// 贴图元素绘制
//        // canvas.drawRect(this.dstRect, dstPaint);
//
//
//        if (this.isDrawHelpTool) {// 绘制辅助工具线
//            canvas.save();
//            canvas.rotate(roatetAngle, helpBox.centerX(), helpBox.centerY());
//            canvas.drawRoundRect(helpBox, 10, 10, helpBoxPaint);
//            // 绘制工具按钮
//            canvas.drawBitmap(deleteBit, helpToolsRect, deleteRect, null);
//            canvas.drawBitmap(rotateBit, helpToolsRect, rotateRect, null);
//            canvas.drawBitmap(mirroring, helpToolsRect, mirroringRect, null);
//
//            //画输入框
//            //画光标：
//
//            if (istext) {//字幕
////                canvas.drawRoundRect(textBox, 1, 1, textPaintBg);//绘制输入框
//                if (isorigin) {//
//                    canvas.drawRoundRect(textBox, 1, 1, textPaintBg);//绘制输入框
//                } else {
//                    canvas.drawRoundRect(mTextRect, 1, 1, textPaintBg);//绘制输入框
//                }
//
//                //绘制光标：
////                if(showCursor%2==0) {
////                    showCursor++;
////                    drawCursor(canvas);//绘制光标：
////                }
//            }
//
//            canvas.restore();
//        }// end if
//        if (istext) {//字幕
////            drawText(canvas,"free", 150, 180, paint,-45);
//            textPaint.setTextSize(textSize);
//            textPaint.setLetterSpacing(lineW);
//            drawText(canvas, textstr, textPaint, roatetAngle);
////            canvas.drawText(textstr, textBox.left, textBox.top, textPaint); //画出字幕
//        }
////        textBitmap = canvas.g
//        // detectRotateRect
//    }
//
//    /**
//     * 保存贴纸字幕：
//     *
//     * @param textCanvas1
//     * @return
//     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public Bitmap saveTextBitmap(Canvas textCanvas1) {
//        textBitmap = this.bitmap.copy(Bitmap.Config.ARGB_8888, true);//拷贝原图bitmap        ;
//        Canvas textCanvas = new Canvas(textBitmap);
//        //绘制文字，需要考虑倍数：
////        float end = (srcRect.right-srcRect.left)/ (dstRect.right-dstRect.left);
//        float end = (srcRect.right - srcRect.left) / (helpBox.right - helpBox.left - HELP_BOX_PAD - HELP_BOX_PAD);
//        LogUtils.sysout("保存之后放大缩小倍数：end=" + end);
//        onTextSizeSave(end);
////        获取字符串的宽高：
//        if (textstrlist != null && textstrlist.size() > 0) {
//            if (isorigin) {
//                for (int h = 1; h <= textstrlist.size(); h++) {
//                    //不是按照基准线绘制：
////                float svalue = (textSize * (h-1)) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
//                    float svalue = (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
////                textCanvas.drawText(textstrlist.get(h - 1), (textBox.left - helpBox.left) * end - (textSize * end), (textBox.top - helpBox.top) * end+(svalue*end), savetextPaint);
//
//                    LogUtils.sysout("保存之后放大缩小倍数：svalue=" + svalue);
//                    textCanvas.drawText(textstrlist.get(h - 1),
//                            (textBox.left - helpBox.left - HELP_BOX_PAD) * end,//X轴
//                            (textBox.top - helpBox.top - HELP_BOX_PAD) * end + (svalue * end),////Y轴（基准线），
//                            savetextPaint);
//
//
//                }
//            } else {
//                for (int h = 1; h <= textstrlist.size(); h++) {
//                    //不是按照基准线绘制：
////                float svalue = (textSize * (h-1)) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
//                    float svalue = (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
////                textCanvas.drawText(textstrlist.get(h - 1), (textBox.left - helpBox.left) * end - (textSize * end), (textBox.top - helpBox.top) * end+(svalue*end), savetextPaint);
//
//                    LogUtils.sysout("保存之后放大缩小倍数：svalue=" + svalue);
//                    textCanvas.drawText(textstrlist.get(h - 1),
//                            (mTextRect.left - helpBox.left - HELP_BOX_PAD) * end,//X轴
//                            (mTextRect.top - helpBox.top - HELP_BOX_PAD) * end + (svalue * end),////Y轴（基准线），
//                            savetextPaint);
//
//
//                }
//            }
//        }
//
////        for (int h = 1; h <= textstrlist.size(); h++) {
////            textCanvas.drawText(textstrlist.get(h - 1),
//// textBox.left,
//// textBox.top + (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW)),
//// paint);
////        }
//        return textBitmap;
//    }
//
//    /**
//     * 保存贴纸字幕（先放大10倍在缩小）：
//     *
//     * @param textCanvas1
//     * @return
//     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public Bitmap saveTextBitmapMax(Canvas textCanvas1) {
//        Bitmap base = this.bitmap.copy(Bitmap.Config.ARGB_8888, true);//拷贝原图bitmap
//        textBitmap = big(base, 5f);//拷贝原图bitmap        ;
//        Canvas textCanvas = new Canvas(textBitmap);
//        //绘制文字，需要考虑倍数：
//        float end = (srcRect.right - srcRect.left) / (helpBox.right - helpBox.left - HELP_BOX_PAD - HELP_BOX_PAD) * 5f;
//        onTextSizeSave(end);
//        if (textstrlist != null && textstrlist.size() > 0) {
//            if (isorigin) {
//                for (int h = 1; h <= textstrlist.size(); h++) {
//                    //不是按照基准线绘制：
////                float svalue = (textSize * (h-1)) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
//                    float svalue = (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
////                textCanvas.drawText(textstrlist.get(h - 1), (textBox.left - helpBox.left) * end - (textSize * end), (textBox.top - helpBox.top) * end+(svalue*end), savetextPaint);
//
//                    LogUtils.sysout("保存之后放大缩小倍数：svalue=" + svalue);
//                    textCanvas.drawText(textstrlist.get(h - 1),
//                            (textBox.left - helpBox.left - HELP_BOX_PAD) * end,//X轴
//                            (textBox.top - helpBox.top - HELP_BOX_PAD) * end + (svalue * end),////Y轴（基准线），
//                            savetextPaint);
//
//
//                }
//            } else {
//                for (int h = 1; h <= textstrlist.size(); h++) {
//                    //不是按照基准线绘制：
////                float svalue = (textSize * (h-1)) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
//                    float svalue = (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW));//行间距
////                textCanvas.drawText(textstrlist.get(h - 1), (textBox.left - helpBox.left) * end - (textSize * end), (textBox.top - helpBox.top) * end+(svalue*end), savetextPaint);
//
//                    LogUtils.sysout("保存之后放大缩小倍数：svalue=" + svalue);
//                    textCanvas.drawText(textstrlist.get(h - 1),
//                            (mTextRect.left - helpBox.left - HELP_BOX_PAD) * end,//X轴
//                            (mTextRect.top - helpBox.top - HELP_BOX_PAD) * end + (svalue * end),////Y轴（基准线），
//                            savetextPaint);
//
//
//                }
//            }
//        }
////        return textBitmap;
//        return small(textBitmap, 0.2f);
//    }
//
//    //获取字符串绘制的基准线：
//    private float getBaseSvalueW(float srcsvalue) {
//        float svalue = srcsvalue;
//
////        return svalue;
//        return savetextPaint.measureText(textstr);
//    }
//
//    /**
//     * Bitmap放大的方法
//     */
//    private static Bitmap big(Bitmap bitmap, float value) {
//        Matrix matrix = new Matrix();
//        matrix.postScale(value, value); //长和宽放大缩小的比例
//        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        return resizeBmp;
//    }
//
//    /**
//     * Bitmap缩小的方法
//     */
//    private static Bitmap small(Bitmap bitmap, float value) {
//        Matrix matrix = new Matrix();
//        matrix.postScale(value, value); //长和宽放大缩小的比例
//        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        return resizeBmp;
//    }
//
//
//    class ShowCursorThread extends Thread {
//        @Override
//        public void run() {
//            //自动隐藏显示光标：	invalidate();
//
//        }
//
//    }
//
//    /**
//     * 绘制光标：
//     *
//     * @param canvas
//     */
//    private void drawCursor(Canvas canvas) {
//        cursorPaint.setColor(Color.BLACK);
////        cursorPaint.setStrokeWidth(DensityUtil.dp2px(2f));
//
//        cursorPaint.setStrokeWidth(cursorWidth);
//        cursorPaint.setStyle(Paint.Style.FILL);
//        //光标未显示 && 开启光标 && 输入位数未满 && 获得焦点
////        if (!isCursorShowing && isCursorEnable && !isInputComplete && hasFocus()) {
//        // 起始点x = paddingLeft + 单个密码框大小 / 2 + (单个密码框大小 + 密码框间距) * 光标下标
//        // 起始点y = paddingTop + (单个密码框大小 - 光标大小) / 2
//        // 终止点x = 起始点x
//        // 终止点y = 起始点y + 光标高度
//        canvas.drawLine(textBox.left + cursorWidth,
//                textBox.top,
//                textBox.left + cursorWidth,
//                textBox.top + textSize,
//                cursorPaint);
////        }
//    }
//
//    void drawText(Canvas canvas, String text, Paint paint, float angle) {
//        if (angle != 0) {
////            canvas.rotate(angle, x, y);
//            canvas.rotate(angle, helpBox.centerX(), helpBox.centerY());
//        }
//
////        if (textstrlist != null && textstrlist.size() > 0) {
////            for (int h = 1; h <= textstrlist.size(); h++) {
//////                canvas.drawText(textstrlist.get(h-1), textBox.left, textBox.top + (textSize*rowH*h), paint);
////                canvas.drawText(textstrlist.get(h - 1), textBox.left, textBox.top + (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW)), paint);
////            }
////        }
//
//
//        if (isorigin) {
//            if (textstrlist != null && textstrlist.size() > 0) {
//                for (int h = 1; h <= textstrlist.size(); h++) {
////                canvas.drawText(textstrlist.get(h-1), textBox.left, textBox.top + (textSize*rowH*h), paint);
//                    canvas.drawText(textstrlist.get(h - 1), textBox.left, textBox.top + (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW)), paint);
//                }
//            }
//        } else {
//            if (textstrlist != null && textstrlist.size() > 0) {
//                for (int h = 1; h <= textstrlist.size(); h++) {
////                canvas.drawText(textstrlist.get(h-1), textBox.left, textBox.top + (textSize*rowH*h), paint);
//                    canvas.drawText(textstrlist.get(h - 1), mTextRect.left, mTextRect.top + (textSize * h) + (textSize * (rowH - 1) * (h - 1) * (1 - lineW)), paint);
//                }
//            }
//        }
////        canvas.drawText(text, textBox.left, textBox.top + textSize, paint);
//        if (angle != 0) {
////            canvas.rotate(-angle, x, y);
//            canvas.rotate(-angle, helpBox.centerX(), helpBox.centerY());
//        }
//    }
//
//    public void setTextColor(int color) {
//        if (color != 0) {
//            textcolor = color;
//        }
//        textPaint.setColor(textcolor);//textcolor = Color.RED
//        savetextPaint.setColor(textcolor);//textcolor = Color.RED
//    }
//
//    //计算字体大小
//    public void onTextSizechang2() {
//        //算出可输入区域的宽高：
//        float textW = textBox.right - textBox.left;
//        float textH = textBox.bottom - textBox.top;
//        float textlength = textstr.length();//只对应的是中文字符，应该考虑英文以及特殊符号。
//        float textAllW = testPaint.measureText(textstr);//获取字符串的总长度：
//        float textItemW = testPaint.measureText("趣");//获取一个字符的长度：
//        float s = testPaint.getTextSize();
//        textlength = textAllW / textItemW;//
//        float maxTextSize = textH - 2;
//        float minTextSize = textW / 10;
//        if (textW > 0 && textH > 0) {
//            if (textSize > textW || textSize > textH) {//判断最大值：
//                maxTextSize = textW > textH ? textH : textW;
//                textSize = maxTextSize;
//            }
//        }
//        if (textstrlist != null) {
//            textstrlist.clear();
//        } else {
//            textstrlist = new ArrayList<String>();
//        }
//        //设定每行最多可以放置个数：
//        //以矩形来计算
//
//        if (textlength > 5 * (int) (5 * textW / textH * rowH * (1 - lineW))) {//六行：
//            int row = (int) (6 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            int rowold = (int) (5 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            minTextSize = maxTextSize / (6 * rowH * (1 - lineW));//最小字符字号大小
//            try {
//                subStrText(textlength, 6, row);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else if (textlength > 4 * (int) (4 * textW / textH * rowH * (1 - lineW))) {//五行：
//            int row = (int) (5 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            int rowold = (int) (4 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            minTextSize = maxTextSize / (5 * rowH * (1 - lineW));//最小字符字号大小
//            try {
//                subStrText(textlength, 5, row);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else if (textlength > 3 * (int) (3 * textW / textH * rowH * (1 - lineW))) {//四行：
//            int row = (int) (4 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            int rowold = (int) (3 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            minTextSize = maxTextSize / (4 * rowH * (1 - lineW));//最小字符字号大小
//            try {
//                subStrText(textlength, 4, row);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else if (textlength > 2 * (int) (2 * textW / textH * rowH * (1 - lineW))) {//三行：
//            int row = (int) (3 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            int rowold = (int) (2 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            minTextSize = maxTextSize / (3 * rowH * (1 - lineW));//最小字符字号大小
//            try {
//                subStrText(textlength, 3, row);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (textlength > (1 * textW / textH)) {//可放两行：
//            int row = (int) (2 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//            int rowold = (int) (1 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
//
//            minTextSize = maxTextSize / (2 * rowH * (1 - lineW));
//            try {
//                subStrText(textlength, 2, row);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (textlength <= (1 * textW / textH)) {//可放一行：
//            minTextSize = maxTextSize;
//            textstrlist.add(textstr);
//        } else {//单行：
//            minTextSize = maxTextSize;
//            textstrlist.add(textstr);
//        }
//
//
//        if (textW > 0 && textH > 0) {
//            //根据字数长度自动调整字体大小
//            refitText(textstr, textW, textH, maxTextSize, minTextSize);//根据字数长度自动调整字体大小
//            // TODO 根据字体的大小，切换行：
//        }
////        判断最多最能多少行：
//        float textWidth = paint.measureText(textstr);   //测量字体宽度，我们需要根据字体的宽度设置在可输入区域中间
//
//    }
//
//    /**
//     * 截取字符串，
//     *
//     * @param scale :所有字符串排成一行和高的比例
//     * @param baseH ：当前输入区域行数
//     * @param rows  ：每行最多可放多少个中文字符串长度：
//     */
//    public void subStrText(float scale, int baseH, int rows) {//可放baseH行,每行可放rows个中文：
//        int index = 1;//当前截取到的第index个字符；
//        int length = 0;
//        if (textstr != null) {
//            length = textstr.length();
//        }
//        if (length == 0) {//字符串为空
//            return;
//        }
//
//        float with = testPaint.measureText("趣") * rows;
//        for (int h = 1; h <= baseH; h++) {
////            while (length >= index) {//
//            for (int j = 0; j <= length; j++) {//循环index之后的字符串，成为一行：
//                String textstr1 = textstr.substring(0, index + j);
//                String textstr2 = textstr.substring(index - 1, index + j);
//
//
//                LogUtils.sysout("切割字符串 textstr1 = " + textstr1);
//                LogUtils.sysout("切割字符串 textstr2 = " + textstr2);
//                if (j != length) {//未到达最后一个字符
//                    float with1 = testPaint.measureText(textstr.substring(index - 1, index + j));
//                    float with2 = testPaint.measureText(textstr.substring(index - 1, index + j + 1));
//                    if (with1 <= with) {//
//                        if (with2 > with) {
//                            textstrlist.add(textstr.substring(index - 1, index + j));
//                            index += j;
//                            break;
//                        }
//                    }
//                } else {
//                    textstrlist.add(textstr.substring(index - 1, length));
//                    index = length;
//                    break;
//                }
//
//
//            }
//        }
////        }
//
//    }
//
//    //计算字体大小
////    public void onTextSizechang() {
////        //算出可输入区域的宽高：
////        float textW = textBox.right - textBox.left;
////        float textH = textBox.bottom - textBox.top;
////        float textlength = textstr.length();//只对应的是中文字符，应该考虑英文以及特殊符号。
////        float textAllW = testPaint.measureText(textstr);//获取字符串的总长度：
////        float textItemW = testPaint.measureText(textstr);//获取一个字符的长度：
//////        textlength = textAllW;
////        float maxTextSize = textH-2;
////        float minTextSize = textW / 10;
////        if (textW > 0 && textH > 0) {
////            if (textSize > textW || textSize > textH) {//判断最大值：
////                maxTextSize = textW > textH ? textH : textW;
////                textSize = maxTextSize;
////            }
////        }
////        if (textstrlist != null) {
////            textstrlist.clear();
////        } else {
////            textstrlist = new ArrayList<String>();
////        }
////        //设定每行最多可以放置个数：
////        //以矩形来计算
////        if (textlength > 5 * (int) (5 * textW / textH * rowH * (1 - lineW))) {//六行：
////            int row = (int) (6 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            int rowold = (int) (5 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            minTextSize = maxTextSize / (6 * rowH * (1 - lineW));//最小字符字号大小
//////            minTextSize = maxTextSize / ((2*textW/textH)+1);
//////            if (row - rowold == 1) {
////            for (int h = 1; h <= 6; h++) {
////                if (textlength >= (row * h)) {//每一行的数字
////                    textstrlist.add(textstr.substring(row * (h - 1), row * h));
////                } else {
////                    if (textlength > row * (h - 1)) {
////                        textstrlist.add(textstr.substring(row * (h - 1), (int)textlength));
////                    }
////                }
////            }
////
////        } else if (textlength > 4 * (int) (4 * textW / textH * rowH * (1 - lineW))) {//五行：
////            int row = (int) (5 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            int rowold = (int) (4 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            minTextSize = maxTextSize / (5 * rowH * (1 - lineW));//最小字符字号大小
//////            minTextSize = maxTextSize / ((2*textW/textH)+1);
//////            if (row - rowold == 1) {
////            for (int h = 1; h <= 5; h++) {
////                if (textlength >= (row * h)) {//每一行的数字
////                    textstrlist.add(textstr.substring(row * (h - 1), row * h));
////                } else {
////                    if (textlength > row * (h - 1)) {
////                        textstrlist.add(textstr.substring(row * (h - 1), (int)textlength));
////                    }
////                }
////            }
////
////        } else if (textlength > 3 * (int) (3 * textW / textH * rowH * (1 - lineW))) {//四行：
////            int row = (int) (4 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            int rowold = (int) (3 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            minTextSize = maxTextSize / (4 * rowH * (1 - lineW));//最小字符字号大小
//////            minTextSize = maxTextSize / ((2*textW/textH)+1);
//////            if (row - rowold == 1) {
////            for (int h = 1; h <= 4; h++) {
////                if (textlength >= (row * h)) {//每一行的数字
////                    textstrlist.add(textstr.substring(row * (h - 1), row * h));
////                } else {
////                    if (textlength > row * (h - 1)) {
////                        textstrlist.add(textstr.substring(row * (h - 1), (int)textlength));
////                    }
////                }
////            }
////
////
////        } else if (textlength > 2 * (int) (2 * textW / textH * rowH * (1 - lineW))) {//三行：
////            int row = (int) (3 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            int rowold = (int) (2 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            minTextSize = maxTextSize / (3 * rowH * (1 - lineW));//最小字符字号大小
////            LogUtils.sysout("888888 textlength=" + textlength);
////            LogUtils.sysout("888888 三行row=" + row);
////            LogUtils.sysout("888888 三行 rowold=" + rowold);
////
////            for (int h = 1; h <= 3; h++) {
////                if (textlength >= (row * h)) {//每一行的数字
////                    textstrlist.add(textstr.substring(row * (h - 1), row * h));
////                } else {
////                    if (textlength > row * (h - 1)) {
////                        textstrlist.add(textstr.substring(row * (h - 1), (int)textlength));
////                    }
////                }
////            }
////
////        } else if (textlength > (int) (1 * textW / textH)) {//可放两行：
////            int row = (int) (2 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            int rowold = (int) (1 * textW / textH * rowH * (1 - lineW));//每行最多能放的数字
////            LogUtils.sysout("888888 textlength=" + textlength);
////            LogUtils.sysout("888888 可放两行row=" + row);
////            LogUtils.sysout("888888 可放两行 rowold=" + rowold);
//////            if (row - rowold == 1) {
////            minTextSize = maxTextSize / (2 * rowH * (1 - lineW));
////            for (int h = 1; h <= 2; h++) {
////                if (textlength >= (row * h)) {//每一行的数字
////                    textstrlist.add(textstr.substring(row * (h - 1), row * h));
////                } else {
////                    if (textlength > row * (h - 1)) {
////                        textstrlist.add(textstr.substring(row * (h - 1), (int)textlength));
////                    }
////                }
////            }
////
////        } else if (textlength <= (int) (1 * textW / textH)) {//可放一行：
////            minTextSize = maxTextSize;
////            textstrlist.add(textstr);
////        }else{//单行：
////            minTextSize = maxTextSize;
////            textstrlist.add(textstr);
////        }
////
////
////        if (textW > 0 && textH > 0) {
////            //根据字数长度自动调整字体大小
////            refitText(textstr, textW, textH, maxTextSize, minTextSize);//根据字数长度自动调整字体大小
////            // TODO 根据字体的大小，切换行：
////        }
//////        判断最多最能多少行：
////        float textWidth = paint.measureText(textstr);   //测量字体宽度，我们需要根据字体的宽度设置在可输入区域中间
////
////    }
//
//    /**
//     * 根据字数长度自动调整字体大小
//     *
//     * @param text//字符串
//     * @param textWidth//可输入区域宽
//     * @param textHeight//可输入区域长
//     * @param maxTextSize//最大字号
//     * @param minTextSize//最小字号
//     */
//    private void refitText(String text, float textWidth, float textHeight, float maxTextSize, float minTextSize) {
//
//        int Length = text.length();
//        //直接根据字符长度来调整字体大小  最大长度为20
////        this.setTextSize(DensityUtils.dp2px(this.getContext(), trySize));
////        testPaint.set(this.getPaint());
//        if (textWidth > 0) {
//            float availableWidth = textWidth;  //获取改TextView的画布可用大小
//            float trySize = maxTextSize;
//            float scaled = context.getResources().getDisplayMetrics().scaledDensity;//这是获取手机屏幕参数，后面的density就是屏幕的密度，类似分辨率，但不是。
////            Log.v(TAG, "availableWidth="+availableWidth + ";scaled="+scaled);
//            testPaint.setTextSize(trySize * scaled);   //模拟 注意乘以scaled
//            while ((trySize > minTextSize) && (testPaint.measureText(text) > availableWidth)) {//Paint的measureText()方法取得字符串显示的宽度值
//
//                trySize -= 1;
//                Paint.FontMetrics fm = testPaint.getFontMetrics();
//                double rowFontHeight = (Math.ceil(fm.descent - fm.top) + 1);
//                float scaled1 = (float) (textHeight / rowFontHeight);  //字体的行数   textview的总高度/每行字的高度
//                float scaled2 = (float) ((testPaint.measureText(text) / availableWidth));  //也是行数  所有字的总长度/textview的有效宽度
//
////                Log.v(TAG, "trySize="+trySize + ";testPaint.measureText(text)="+testPaint.measureText(text)+";scaled1="+scaled1+";scaled2="+scaled2+";rowFontHeight="+rowFontHeight);
//                if ((scaled2 * rowFontHeight * 1.2) < textHeight)  //1.9代表是1.9的行高（1个字体本身，0.9的行距 ，大致差不多，没有实际测过）
//                    break;
//                if (trySize <= minTextSize) {
//                    trySize = minTextSize;
//                    break;
//                }
//                testPaint.setTextSize(trySize * scaled);
//            }
//            textSize = trySize;
////            this.setTextSize(trySize);
////            Log.v(TAG, "trySize="+trySize+";maxTextSize="+maxTextSize+";minTextSize="+minTextSize);
//        }
//    }
//
//
//    /**
//     * Re size the font so the specified text fits in the text box * assuming the text box is the specified width.
//     */
////    private void refitTextNew(String text, float maxTextSize, float minTextSize, int textWidth, int textHeight) {
//    public void refitTextNew() {
//
//        String text =textstr;
//        int textWidth = (int)(textBox.right - textBox.left)- DensityUtil.dp2px(context,5);
//        int textHeight = (int)(textBox.bottom - textBox.top)- DensityUtil.dp2px(context,2);
//        float maxTextSize = textHeight - DensityUtil.dp2px(context,2);
//        float minTextSize =1f;
//
//        if (textWidth > 0 && textHeight > 0) {
//            //allow diplay rect
//            int availableWidth = textWidth - 10;//this.getPaddingLeft() - this.getPaddingRight();
//            int availableHeight = textHeight - 2;// this.getPaddingBottom() - this.getPaddingTop();
//            //by the line calculate allow displayWidth
//            int autoWidth = availableWidth;
//            float mult = 1.25f;//行间距倍数
//            float add = DensityUtil.dp2px(context,3);;//行间距
//            if (Build.VERSION.SDK_INT > 16) {
////                mult=getLineSpacingMultiplier();
////                add=getLineSpacingExtra();
//            } else {
//                //the mult default is 1.0f,if you need change ,you can reflect invoke this field;
//            }
//            float trySize = maxTextSize;
//            testPaint.setTextSize(trySize);
//            int oldline = 1, newline = 1;
//            while ((trySize > minTextSize)) {
//                //calculate text singleline width。
//                int displayW = (int) testPaint.measureText(text);
//                //calculate text singleline height。
//                int displaH = round(testPaint.getFontMetricsInt(null) * mult + add);
//                if (displayW < autoWidth) {
//                    break;
//                }
//                //calculate maxLines
//                newline = availableHeight / displaH;
//                //if line change ,calculate new autoWidth
//                if (newline > oldline) {
//                    oldline = newline;
//                    autoWidth = availableWidth * newline;
//                    continue;
//                }
//                //try more small TextSize
//                trySize -= 1;
//                if (trySize <= minTextSize) {
//                    trySize = minTextSize;
//                    break;
//                }
//
//                testPaint.setTextSize(trySize);
//            }
//            //setMultiLine
////            if (newline>=2)
////            {
////                this.setSingleLine(false);//是否为单行显示
////                this.setMaxLines(newline);//新的最多行数
////            }
//            textSize = trySize;
//            testPaint.setTextSize(trySize);
//            subStrTextNew(0,0,availableWidth);
//
//
//        }
//    }
//
//    //FastMath.round()
//    public static int round(float value) {
//        long lx = (long) (value * (65536 * 256f));
//        return (int) ((lx + 0x800000) >> 24);
//    }
//
//    /**
//     * 截取字符串，
//     *
//     * @param scale          :所有字符串排成一行和高的比例
//     * @param baseH          ：当前输入区域行数
//     * @param availableWidth ：可输入宽度：
//     */
//    public void subStrTextNew(float scale, int baseH, int availableWidth) {//可放baseH行
//        if(textstrlist!=null&&textstrlist.size()>0){
//            textstrlist.clear();
////            textstrlist.add(textstr);
//        }else{
//            textstrlist = new ArrayList<>();
////            textstrlist.add(textstr);
//        }
//        int index = 0;//当前截取到的第index个字符；
//        int length = 0;
//        if (textstr != null) {
//            length = textstr.length();
//        }
//
//        if (length == 0) {//字符串为空
//            return;
//        }
//
//
//        for (int j = 0; j <= length; j++) {//循环index之后的字符串，成为一行：
////            String textstr1 = textstr.substring(0, j);
////            LogUtils.sysout("切割字符串 textstr1 = " + textstr1);
//            if(index>length||index<0){
//                return;
//            }
//            String textstr1 = textstr.substring(index, j);
//            float with1 = testPaint.measureText(textstr1);
////            LogUtils.sysout("111111111111111111111111111111111111 textSize ="+textSize);
////            LogUtils.sysout("切割字符串 with1 ="+with1);
////            LogUtils.sysout("切割字符串 availableWidth ="+availableWidth);
////            LogUtils.sysout("当前  index="+index);
////            LogUtils.sysout("当前  j="+j);
//            if (with1 > availableWidth) {
//                if (j == length) {//到达最后一个字符
//                    textstrlist.add(textstr.substring(index, j-1));
//                    textstrlist.add(textstr.substring(j-1, j));
//                    break;
//                }else{
//                    textstrlist.add(textstr.substring(index, j-1));
//                    index = j-1;
//                }
//
//
//            } else {
//                if (j == length) {//到达最后一个字符
//                    if(index==0){
//                        textstrlist.add(textstr.substring(index, length));
//                    }else{
//                        textstrlist.add(textstr.substring(index, length));
//                    }
//                    break;
//                }
//            }
//        }
//    }
//
//    //计算字体大小
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    protected void onTextSizeSave(float end) {
//        savetextPaint.setLetterSpacing(lineW * end);
//        savetextPaint.setTextSize(textSize * end);//textSize 34
//
//    }
//
//
//    //计算光标尺寸：
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
////        super.onSizeChanged(w, h, oldw, oldh);
//        //文本大小 textSize
////        cipherTextSize = passwordSize / 2;
//        //光标宽度
////        cursorWidth = dp2px(2);
//        //光标长度
////        cursorHeight = passwordSize / 2;
//    }
//
//    Path path = new Path();
//
//    /**
//     * 缩放指定矩形
//     *
//     * @param rect
//     * @param scale
//     */
//    private static void scaleRect(RectF rect, float scale) {
//        float w = rect.width();
//        float h = rect.height();
//
//        float newW = scale * w;
//        float newH = scale * h;
//
//        float dx = (newW - w) / 2;
//        float dy = (newH - h) / 2;
//
//        rect.left -= dx;
//        rect.top -= dy;
//        rect.right += dx;
//        rect.bottom += dy;
//    }
//
//    /**
//     * 矩形绕指定点旋转
//     */
//    private static void rotateRect(RectF rect, float center_x, float center_y, float roatetAngle) {
//        float x = rect.centerX();
//        float y = rect.centerY();
//        float sinA = (float) Math.sin(Math.toRadians(roatetAngle));
//        float cosA = (float) Math.cos(Math.toRadians(roatetAngle));
//        float newX = center_x + (x - center_x) * cosA - (y - center_y) * sinA;
//        float newY = center_y + (y - center_y) * cosA + (x - center_x) * sinA;
//
//        float dx = newX - x;
//        float dy = newY - y;
//
//        rect.offset(dx, dy);
//
//        // float w = rect.width();
//        // float h = rect.height();
//        // rect.left = newX;
//        // rect.top = newY;
//        // rect.right = newX + w;
//        // rect.bottom = newY + h;
//    }
//}// end class
