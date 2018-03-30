//package com.changxiang.vod.common.view;
//
//
//import android.app.Dialog;
//import android.content.Context;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextWatcher;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.utils.RegValUtil;
//
////import com.quchangkeji.tosing.R;
////import com.quchangkeji.tosing.common.utils.LogUtils;
//
//@SuppressWarnings(value = {"all"})
//public class OneButtonAlertDialog implements OnClickListener {
//    private Context context;
//    private Dialog dialog;
//    private LinearLayout lLayout_bg;
//    private TextView txt_title;
//    private TextView txt_msg;
//    private Button btn_show;//灰色不可点击控件
//    private Button btn_next;
//    //private ImageView img_line;
//    private Display display;
//    private boolean showTitle = false;
//    private boolean showMsg = false;
//    private boolean showPosBtn = false;
//    private boolean showNegBtn = false;
//    private boolean showNeuBtn = false;
//    CustomEditText input_phone;//et_input_phone
//    //自定义Dialog布局
//    private View view;
//    private static String phone;
//
//    public OneButtonAlertDialog(Context context) {
//        this.context = context;
//        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null);
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    public OneButtonAlertDialog builder() {
//
//        // 获取自定义Dialog布局中的控件
//
//        lLayout_bg = (LinearLayout) view.findViewById(R.id.quchang_lLayout_bg);
//        txt_title = (TextView) view.findViewById(R.id.txt_title);
//        txt_title.setVisibility(View.GONE);
//        txt_msg = (TextView) view.findViewById(R.id.quchang_alert_msg);
//        txt_msg.setVisibility(View.GONE);
//
//        input_phone = (CustomEditText) view.findViewById(R.id.et_input_phone);
//        //btn_Neu.setVisibility(View.GONE);
//        btn_show = (Button) view.findViewById(R.id.quchang_alert_negative);
//        btn_next = (Button) view.findViewById(R.id.quchang_alert_negative1);
////        btn_next.setVisibility(View.GONE);
//        /*img_line = (ImageView) view.findViewById(R.id.img_line);
//        img_line.setVisibility(View.GONE);*/
//
//        // 定义Dialog布局和参数
//        dialog = new Dialog(context, R.style.AlertDialogStyle);
//        dialog.setContentView(view);
//
//        input_phone.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//        // 调整dialog背景大小
//        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                .getWidth() * 0.85), FrameLayout.LayoutParams.WRAP_CONTENT));
//        input_phone.addTextChangedListener(new TextWatcher() {
//            @Override
//            //s:变化后的所有字符
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().trim().length() == 11) {
//                    phone = s.toString();
//                    btn_show.setVisibility(View.GONE);
//                    btn_next.setVisibility(View.VISIBLE);
//                } else {
//                    btn_show.setVisibility(View.VISIBLE);
//                    btn_next.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        return this;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    public OneButtonAlertDialog setTitle(String title) {
//        showTitle = true;
//        if ("".equals(title)) {
//            txt_title.setText("提示");
//        } else {
//            txt_title.setText(title);
//        }
//        return this;
//    }
//
//    public OneButtonAlertDialog setMsg(String msg) {
//        showMsg = true;
//        if ("".equals(msg)) {
//            txt_msg.setText("内容");
//        } else {
//            txt_msg.setText(msg);
//        }
//        return this;
//    }
//
//    public OneButtonAlertDialog setPhone(String phone) {
//        showMsg = true;
//        if ("".equals(phone)) {
//            input_phone.setText("");
////            txt_msg.setText("手机号");
//            btn_show.setText(context.getString(R.string.one_dialog_text));
//            btn_next.setText(context.getString(R.string.one_dialog_text));
//        } else {
//            input_phone.setText(phone);
//            btn_show.setText(context.getString(R.string.one_dialog_text1));
//            btn_next.setText(context.getString(R.string.one_dialog_text1));
////            txt_msg.setText(phone);
//        }
//        return this;
//    }
//
//    public String getPhone() {
//        return phone;
//
//    }
//
//    public OneButtonAlertDialog setCancelable(boolean cancel) {
//        dialog.setCancelable(cancel);
//        return this;
//    }
//
//    //转场
//    public OneButtonAlertDialog setPNGButton(String text,
//                                             final OnClickListener listener) {
//
//        return this;
//    }
//
//    //动态
//    public OneButtonAlertDialog setGIFButton(String text,
//                                             final OnClickListener listener) {
////		showNeuBtn = true;
////		if ("".equals(text)) {
////			btn_Neu.setText("保存");
////		} else {
////			btn_Neu.setText(text);
////		}
////        LogUtils.sysout("11111111 点击了动态合成");
//
////        ll_gif.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                cb_check1.setChecked(false);
////                cb_check2.setChecked(true);
////                listener.onClick(v);
//////                dialog.dismiss();
////            }
////        });
//        return this;
//    }
//
//    //	确认
//    public OneButtonAlertDialog setNextButton(String text,
//                                              final OnClickListener listener) {
//
//        btn_next.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                phone = input_phone.getText().toString().trim();
//                if (!RegValUtil.isPhoneNumberValid(phone)) {
//                    Toast.makeText(context, "请输入正确的手机号码！", Toast.LENGTH_LONG).show();
//                    ////打开软键盘
//                    InputMethodManager imm1 = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm1.toggleSoftInput(R.id.et_input_phone, InputMethodManager.HIDE_NOT_ALWAYS);
//                } else {
//                    dialog.dismiss();
//                }
//
//            }
//        });
//        return this;
//    }
//    public void dialogDismiss(){
//        dialog.dismiss();
//    }
//
//    public boolean getTag() {
////        if (cb_check1.isChecked()||cb_check2.isChecked()) {
////            return true;
////        }else {
//        return false;
////        }
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
