//package com.changxiang.vod.common.view;
//
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.changxiang.VodMedia.R;
//
//
//@SuppressWarnings(value = {"all"})
//public class TwoButtonAlertDialog implements OnClickListener {
//    private Context context;
//    private Dialog dialog;
//    private LinearLayout lLayout_bg;
//    private TextView txt_title;
//    private TextView txt_msg;
//    private LinearLayout ll_png, ll_gif;
//    private CheckBox cb_check1, cb_check2;
//    private TextView btn_png;
//    private TextView btn_gif;
//    private Button btn_next;
//    //private ImageView img_line;
//    private Display display;
//    private boolean showTitle = false;
//    private boolean showMsg = false;
//    private boolean showPosBtn = false;
//    private boolean showNegBtn = false;
//    private boolean showNeuBtn = false;
//    //自定义Dialog布局
//    private View view;
//
//    public TwoButtonAlertDialog(Context context) {
//        this.context = context;
//        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_two_button, null);
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    public TwoButtonAlertDialog builder() {
//
//        // 获取自定义Dialog布局中的控件
////		LinearLayout ll_one,ll_two;
//        ll_png = (LinearLayout) view.findViewById(R.id.ll_zhuanc_one);
//        ll_gif = (LinearLayout) view.findViewById(R.id.ll_gid_two);
////		CheckBox cb_check1,cb_check2;
//        cb_check1 = (CheckBox) view.findViewById(R.id.cb_check1);
//        cb_check2 = (CheckBox) view.findViewById(R.id.cb_check2);
//
//        lLayout_bg = (LinearLayout) view.findViewById(R.id.quchang_lLayout_bg);
//        txt_title = (TextView) view.findViewById(R.id.txt_title);
//        txt_title.setVisibility(View.GONE);
//        txt_msg = (TextView) view.findViewById(R.id.quchang_alert_msg);
//        txt_msg.setVisibility(View.GONE);
//
//        btn_png = (TextView) view.findViewById(R.id.quchang_alert_zhuanchang);//转场
////        btn_png.setVisibility(View.GONE);
//
//        btn_gif = (TextView) view.findViewById(R.id.quchang_alert_gif);
//        //btn_Neu.setVisibility(View.GONE);
//        btn_next = (Button) view.findViewById(R.id.quchang_alert_negative);
////        btn_next.setVisibility(View.GONE);
//        /*img_line = (ImageView) view.findViewById(R.id.img_line);
//		img_line.setVisibility(View.GONE);*/
//
//        // 定义Dialog布局和参数
//        dialog = new Dialog(context, R.style.AlertDialogStyle);
//        dialog.setContentView(view);
//
//        // 调整dialog背景大小
//		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//				.getWidth() * 0.85), FrameLayout.LayoutParams.WRAP_CONTENT));
////        ll_one.setOnClickListener(this);
////        ll_two.setOnClickListener(this);
////        ll_one.setOnClickListener(this);
//
//        return this;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    public TwoButtonAlertDialog setTitle(String title) {
//        showTitle = true;
//        if ("".equals(title)) {
//            txt_title.setText("提示");
//        } else {
//            txt_title.setText(title);
//        }
//        return this;
//    }
//
//    public TwoButtonAlertDialog setMsg(String msg) {
//        showMsg = true;
//        if ("".equals(msg)) {
//            txt_msg.setText("内容");
//        } else {
//            txt_msg.setText(msg);
//        }
//        return this;
//    }
//
//    public TwoButtonAlertDialog setCancelable(boolean cancel) {
//        dialog.setCancelable(cancel);
//        return this;
//    }
//
//    //转场
//    public TwoButtonAlertDialog setPNGButton(String text,
//                                             final OnClickListener listener) {
////		showPosBtn = true;
////		if ("".equals(text)) {
////			btn_pos.setText("确定");
////		} else {
////			btn_pos.setText(text);
////		}
//
//
////        LogUtils.sysout("11111111 点击了转场合成");
//        ll_png.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//                cb_check1.setChecked(true);
//                cb_check2.setChecked(false);
//				listener.onClick(v);
////				dialog.dismiss();
//			}
//		});
//        return this;
//    }
//
//    //动态
//    public TwoButtonAlertDialog setGIFButton(String text,
//                                             final OnClickListener listener) {
////		showNeuBtn = true;
////		if ("".equals(text)) {
////			btn_Neu.setText("保存");
////		} else {
////			btn_Neu.setText(text);
////		}
////        LogUtils.sysout("11111111 点击了动态合成");
//
//        ll_gif.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cb_check1.setChecked(false);
//                cb_check2.setChecked(true);
//                listener.onClick(v);
////                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    //	确认
//    public TwoButtonAlertDialog setNextButton(String text,
//                                              final OnClickListener listener) {
//
//        btn_next.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                if (cb_check1.isChecked()||cb_check2.isChecked()) {
//                dialog.dismiss();
//            }
//            }
//        });
//        return this;
//    }
//
//    public boolean getTag(){
//        if (cb_check1.isChecked()||cb_check2.isChecked()) {
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    private void setLayout() {
//        if (!showTitle && !showMsg) {
//            txt_title.setText("提示");
//            txt_title.setVisibility(View.VISIBLE);
//        }
//
//        if (showTitle) {
//            txt_title.setVisibility(View.VISIBLE);
//        }
//
//        if (showMsg) {
//            txt_msg.setVisibility(View.VISIBLE);
//        }
//
////		if (!showPosBtn && !showNegBtn) {
////			btn_pos.setText("确定");
////			btn_pos.setVisibility(View.VISIBLE);
////			btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
////			btn_pos.setOnClickListener(new OnClickListener() {
////				@Override
////				public void onClick(View v) {
////					dialog.dismiss();
////				}
////			});
////		}
//
////		if (showPosBtn && showNegBtn) {
////			btn_pos.setVisibility(View.VISIBLE);
////			btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
////			btn_neg.setVisibility(View.VISIBLE);
////			btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
////			//img_line.setVisibility(View.VISIBLE);
////		}
//
////		if (showPosBtn && !showNegBtn) {
////			btn_pos.setVisibility(View.VISIBLE);
////			btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
////		}
//
////		if (!showPosBtn && showNegBtn) {
////			btn_neg.setVisibility(View.VISIBLE);
////			btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
////		}
//    }
//
//    public void show() {
//        setLayout();
//        dialog.show();
//    }
//
//
//}
