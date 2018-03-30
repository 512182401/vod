package com.changxiang.vod.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    //可编辑输入框
    private Drawable dRight;
    private Rect rBounds;
    private boolean flag = true;
    private boolean isTouchable = true;
    private Context context;


    public boolean isTouchable() {
        return isTouchable;
    }

    public void setTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    public CustomEditText(Context paramContext) {
        super(paramContext);
        context = paramContext;
        initEditText();
    }

    public CustomEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        context = paramContext;
        initEditText();
    }

    public CustomEditText(Context paramContext, AttributeSet paramAttributeSet,
                          int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        context = paramContext;
        initEditText();
    }

    private void initEditText() {
        setEditTextDrawable();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable paramEditable) {
            }

            @Override
            public void beforeTextChanged(CharSequence paramCharSequence,
                                          int paramInt1, int paramInt2, int paramInt3) {
            }

            @Override
            public void onTextChanged(CharSequence paramCharSequence,
                                      int paramInt1, int paramInt2, int paramInt3) {
                CustomEditText.this.setEditTextDrawable();
            }
        });
        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                if (hasFocus) {
                    flag = false;
                } else {
                    flag = true;
                }
                CustomEditText.this.setEditTextDrawable();
            }
        });
    }

    private void setEditTextDrawable() {
        if (getText().toString().length() == 0 || flag) {
            setCompoundDrawables(null, null, null, null);
        } else {
            setCompoundDrawables(null, null, this.dRight, null);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.dRight = null;
        this.rBounds = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if (!isTouchable) {
            return false;
        }
        if ((this.dRight != null)
                && (paramMotionEvent.getAction() == MotionEvent.ACTION_UP)) {
            this.rBounds = this.dRight.getBounds();
            int i = (int) paramMotionEvent.getX();
            if (i > getRight() - getLeft() - 3 * this.rBounds.width()) {
                setText("");
//				setFocusable(true);
//				setFocusableInTouchMode(true);
//				requestFocus();
//				findFocus();
//				requestFocusFromTouch();
                try {//当点击清除按钮的时候，键盘显示出来
                    InputMethodManager imm = (InputMethodManager) context
                            .getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(this, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {//当点击清除按钮的时候，键盘隐藏
//                    InputMethodManager imm = (InputMethodManager) context
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(paramMotionEvent);
    }

    @Override
    public void setCompoundDrawables(Drawable paramDrawable1,
                                     Drawable paramDrawable2, Drawable paramDrawable3,
                                     Drawable paramDrawable4) {
        if (paramDrawable3 != null)
            this.dRight = paramDrawable3;
        super.setCompoundDrawables(paramDrawable1, paramDrawable2,
                paramDrawable3, paramDrawable4);
    }
}
