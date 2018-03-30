package com.changxiang.vod.module.engine;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.changxiang.vod.common.utils.dialog.AlertDialog;


public class LoginedDialog {
    static AlertDialog mAlertDialog;

    public static void loginedcheck(final Context context) {
        new AlertDialog(context).builder()
                .setTitle("未登录")
                .setMsg("您当前未登录或者登录状态出错，请重新登录！")
                .setPositiveButton("登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
////						Intent it = new Intent(context, LoginActivityOther.class);
//                        Intent it = new Intent(context, LoginIndexActivity.class);
//                        it.putExtra("isfirst", 1);
//                        context.startActivity(it);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    //在其他时候登录了：
    public static void loginedOpen(final Context context) {
        if (mAlertDialog != null && mAlertDialog.isShow()) {
            return;
        }
        mAlertDialog = new AlertDialog(context).builder();
        mAlertDialog.setTitle("验证码出错");
        mAlertDialog.setMsg("您当前账号在其他手机上登录了，请重新登录！");
        mAlertDialog.setPositiveButton("登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////						Intent it = new Intent(context, LoginActivityOther.class);
//                Intent it = new Intent(context, LoginIndexActivity.class);
//                it.putExtra("isfirst", 1);
//                context.startActivity(it);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

}
