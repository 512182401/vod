//package com.changxiang.vod.common.view;
//
//
//import android.app.Dialog;
//import android.content.Context;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.CheckBox;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.changxiang.vod.R;
//
//
////import tosingpk.quchangkeji.com.quchangpk.R;
//
////import com.quchangkeji.tosing.R;
////import com.quchangkeji.tosing.common.utils.LogUtils;
//
//@SuppressWarnings(value = {"all"})
//public class SendKrcErrorAlertDialog implements OnClickListener {
//    private Context context;
//    private Dialog dialog;
//    private LinearLayout lLayout_bg;
//    private TextView txt_title;
//    private TextView txt_msg;
//    private LinearLayout ll_one, ll_two, ll_thr, ll_four;
//    private CheckBox cb_check1, cb_check2, cb_check3, cb_check4;
//    private CustomEditText editContent;
//    private TextView cancel;//tv_cancel
//    private TextView determine;//tv_determine
//    //private ImageView img_line;
//    private Display display;
//    private boolean showTitle = false;
//    private boolean showMsg = false;
//    private boolean showPosBtn = false;
//    private boolean showNegBtn = false;
//    private boolean showNeuBtn = false;
//
//    private TextView txt_1;
//    private TextView txt_2;
//    private TextView txt_3;
//    private TextView content_count;//tv_content_count
//    //自定义Dialog布局
//    private View view;
//
//    public SendKrcErrorAlertDialog(Context context) {
//        this.context = context;
//        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_krc_error, null);
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    public SendKrcErrorAlertDialog builder() {
//
//        // 获取自定义Dialog布局中的控件
////		LinearLayout ll_one,ll_two;
////        ll_one, ll_two, ll_thr, ll_four;
//        ll_one = (LinearLayout) view.findViewById(R.id.ll_krc_one);
//        ll_two = (LinearLayout) view.findViewById(R.id.ll_krc_two);
//        ll_thr = (LinearLayout) view.findViewById(R.id.ll_krc_thr);
//        ll_four = (LinearLayout) view.findViewById(R.id.ll_krc_four);
////		CheckBox cb_check1,cb_check2;
//        cb_check1 = (CheckBox) view.findViewById(R.id.cb_check1);
//        cb_check2 = (CheckBox) view.findViewById(R.id.cb_check2);
//        cb_check3 = (CheckBox) view.findViewById(R.id.cb_check3);
//        cb_check4 = (CheckBox) view.findViewById(R.id.cb_check4);
//
//        editContent = (CustomEditText) view.findViewById(R.id.edit_centent);
////        private TextView cancel;//tv_cancel
////        private TextView determine;//tv_determine
//        cancel = (TextView) view.findViewById(R.id.tv_cancel);
//        determine = (TextView) view.findViewById(R.id.tv_determine);
//
//        txt_1 = (TextView) view.findViewById(R.id.tv_krc_text1);
//        txt_2 = (TextView) view.findViewById(R.id.tv_krc_text2);
//        txt_3 = (TextView) view.findViewById(R.id.tv_krc_text3);
//
//        content_count = (TextView) view.findViewById(R.id.tv_content_count);
//
//        lLayout_bg = (LinearLayout) view.findViewById(R.id.quchang_lLayout_bg);
//        txt_title = (TextView) view.findViewById(R.id.txt_title);
//        txt_title.setVisibility(View.GONE);
//        txt_msg = (TextView) view.findViewById(R.id.quchang_alert_msg);
//        txt_msg.setVisibility(View.GONE);
//
//
//        // 定义Dialog布局和参数
//        dialog = new Dialog(context, R.style.AlertDialogStyle);
//        dialog.setContentView(view);
//
//        // 调整dialog背景大小
//        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                .getWidth() * 0.85), FrameLayout.LayoutParams.WRAP_CONTENT));
////        ll_one.setOnClickListener(this);
////        ll_two.setOnClickListener(this);
////        ll_one.setOnClickListener(this);
//        editContent.addTextChangedListener( new TextWatcher() {
//        @Override
//        //s:变化后的所有字符
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            int leng = s.length();
//            content_count.setText( leng + "/200" );
//        }
//    } );
//        return this;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    public SendKrcErrorAlertDialog setTitle(String title) {
//        showTitle = true;
//        if ("".equals(title)) {
//            txt_title.setText("提示");
//        } else {
//            txt_title.setText(title);
//        }
//        return this;
//    }
//
//    public SendKrcErrorAlertDialog setMsg(String msg) {
//        showMsg = true;
//        if ("".equals(msg)) {
//            txt_msg.setText("内容");
//        } else {
//            txt_msg.setText(msg);
//        }
//        return this;
//    }
//
//    public SendKrcErrorAlertDialog setCancelable(boolean cancel) {
//        dialog.setCancelable(cancel);
//        return this;
//    }
//
//    //第一个
//    public SendKrcErrorAlertDialog setOneButton(String text,
//                                                final OnClickListener listener) {
//
//        ll_one.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cb_check1.setChecked(true);
//                cb_check2.setChecked(false);
//                cb_check3.setChecked(false);
//                cb_check4.setChecked(false);
//                //关闭编辑框
//                editContent.setVisibility(View.GONE);
//                content_count.setVisibility(View.GONE);
//                listener.onClick(v);
////				dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    //第二个
//    public SendKrcErrorAlertDialog setTwoButton(String text,
//                                                final OnClickListener listener) {
//
//        ll_two.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cb_check1.setChecked(false);
//                cb_check2.setChecked(true);
//                cb_check3.setChecked(false);
//                cb_check4.setChecked(false);
//                //关闭编辑框
//                editContent.setVisibility(View.GONE);
//                content_count.setVisibility(View.GONE);
//                listener.onClick(v);
////                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    //第三个
//    public SendKrcErrorAlertDialog setThrButton(String text,
//                                                final OnClickListener listener) {
//
//        ll_thr.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cb_check1.setChecked(false);
//                cb_check2.setChecked(false);
//                cb_check3.setChecked(true);
//                cb_check4.setChecked(false);
//                //关闭编辑框
//                editContent.setVisibility(View.GONE);
//                content_count.setVisibility(View.GONE);
//                listener.onClick(v);
////                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    //第四个
//    public SendKrcErrorAlertDialog setFourButton(String text,
//                                                 final OnClickListener listener) {
//
//        ll_four.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cb_check1.setChecked(false);
//                cb_check2.setChecked(false);
//                cb_check3.setChecked(false);
//                cb_check4.setChecked(true);
//                //关闭编辑框
//                editContent.setVisibility(View.VISIBLE);
//                content_count.setVisibility(View.VISIBLE);
//                listener.onClick(v);
//                dialog.show();
////                dialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    //	取消
//    //        private TextView cancel;//tv_cancel
////        private TextView determine;//tv_determine
//    public SendKrcErrorAlertDialog setCancelButton(String text,
//                                                   final OnClickListener listener) {
//
//        cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                listener.onClick(v);
//
//                dialog.dismiss();
//
//            }
//        });
//        return this;
//    }
//
//    //	确认
//    public SendKrcErrorAlertDialog setDetermineButton(String text,
//                                                      final OnClickListener listener) {
//
//        determine.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                if (cb_check1.isChecked() || cb_check2.isChecked() || cb_check3.isChecked() || cb_check4.isChecked()) {
//                    if (cb_check4.isChecked()) {
//                        if (editContent.getText().toString().trim().length() > 2) {
//                            dialog.dismiss();
//                        } else {
//                            LogUtils.sysout("提示用户未描述错误原因~~");
//                        }
//                    } else {
//                        dialog.dismiss();
//                    }
//                }
//            }
//        });
//        return this;
//    }
//
//    public String getContent() {
//        String content = editContent.getText().toString().trim();
////         cb_check1.setChecked(false);
////        cb_check2.setChecked(false);
////        cb_check3.setChecked(false);
////        cb_check4.setChecked(true);
//        if (cb_check1.isChecked()) {
//            content =txt_1.getText().toString().trim();
//            return content;
//        } else if (cb_check2.isChecked()) {
//            content =txt_2.getText().toString().trim();
//            return content;
//        } else if (cb_check3.isChecked()) {
//            content =txt_3.getText().toString().trim();
//            return content;
//        } else if (cb_check4.isChecked()) {
//            return content;
//        }else{
//            return "";
//        }
//
//    }
//
//    public boolean getTag() {
//        if (cb_check1.isChecked() || cb_check2.isChecked()) {
//            return true;
//        } else {
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
