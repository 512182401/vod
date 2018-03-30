//package com.changxiang.vod.common.view;
//
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.quchangkeji.tosingpk.R;
//
//
//@SuppressWarnings(value = {"all"})
//public class ThreeButtonAlertDialog {
//    private Context context;
//    private Dialog dialog;
//    private LinearLayout lLayout_bg;
//    private TextView txt_title;
//    private TextView txt_msg;
//    private Button btn_neg;
//    private Button btn_pos;
//    private Button btn_Neu;
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
//    public ThreeButtonAlertDialog(Context context) {
//        this.context = context;
//        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_three_button, null);
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    public ThreeButtonAlertDialog builder() {
//
//        // 获取自定义Dialog布局中的控件
//        lLayout_bg = (LinearLayout) view.findViewById(R.id.quchang_lLayout_bg);
//        txt_title = (TextView) view.findViewById(R.id.txt_title);
//        txt_title.setVisibility(View.GONE);
//        txt_msg = (TextView) view.findViewById(R.id.quchang_alert_msg);
//        txt_msg.setVisibility(View.GONE);
//        btn_neg = (Button) view.findViewById(R.id.quchang_alert_postive);
//        btn_neg.setVisibility(View.GONE);
//        btn_Neu = (Button) view.findViewById(R.id.quchang_alert_Neutral);
//        //btn_Neu.setVisibility(View.GONE);
//        btn_pos = (Button) view.findViewById(R.id.quchang_alert_negative);
//        btn_pos.setVisibility(View.GONE);
//        /*img_line = (ImageView) view.findViewById(R.id.img_line);
//		img_line.setVisibility(View.GONE);*/
//
//        // 定义Dialog布局和参数
//        dialog = new Dialog(context, R.style.AlertDialogStyle);
//        dialog.setContentView(view);
//
//        // 调整dialog背景大小
//		/*lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//				.getWidth() * 0.85), FrameLayout.LayoutParams.WRAP_CONTENT));*/
//
//        return this;
//    }
//
//    public ThreeButtonAlertDialog setTitle(String title) {
//        showTitle = true;
//        if ("".equals(title)) {
//            txt_title.setText("提示");
//        } else {
//            txt_title.setText(title);
//        }
//        return this;
//    }
//
//    public ThreeButtonAlertDialog setMsg(String msg) {
//        showMsg = true;
//        if ("".equals(msg)) {
//            txt_msg.setText("内容");
//        } else {
//            txt_msg.setText(msg);
//        }
//        return this;
//    }
//
//    public ThreeButtonAlertDialog setCancelable(boolean cancel) {
//        dialog.setCancelable(cancel);
//        return this;
//    }
//
//    public ThreeButtonAlertDialog setPositiveButton(String text,
//                                                    final OnClickListener listener) {
//        showPosBtn = true;
//        if ("".equals(text)) {
//            btn_pos.setText("确定");
//        } else {
//            btn_pos.setText(text);
//            btn_pos.setTextColor(Color.RED);
//        }
//        btn_pos.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    public ThreeButtonAlertDialog setNegativeButton(String text,
//                                                    final OnClickListener listener) {
//        showNegBtn = true;
//        if ("".equals(text)) {
//            btn_neg.setText("取消");
//        } else {
//            btn_neg.setText(text);
//        }
//        btn_neg.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    public ThreeButtonAlertDialog setNeutralButton(String text,
//                                                   final OnClickListener listener) {
//        showNeuBtn = true;
//        if ("".equals(text)) {
//            btn_Neu.setText("保存");
//        } else {
//            btn_Neu.setText(text);
//        }
//        btn_Neu.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        return this;
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
//        if (!showPosBtn && !showNegBtn) {
//            btn_pos.setText("确定");
//            btn_pos.setVisibility(View.VISIBLE);
//            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
//            btn_pos.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//        }
//
//        if (showPosBtn && showNegBtn) {
//            btn_pos.setVisibility(View.VISIBLE);
//            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
//            btn_neg.setVisibility(View.VISIBLE);
//            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
//            //img_line.setVisibility(View.VISIBLE);
//        }
//
//        if (showPosBtn && !showNegBtn) {
//            btn_pos.setVisibility(View.VISIBLE);
//            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
//        }
//
//        if (!showPosBtn && showNegBtn) {
//            btn_neg.setVisibility(View.VISIBLE);
//            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
//        }
//    }
//
//    public void show() {
//        setLayout();
//        dialog.show();
//    }
//
//    public void dismiss() {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//    }
//
//    public boolean isShow() {
//        if (dialog != null) {
//           return dialog.isShowing();
//        }
//        return false;
//    }
//}
