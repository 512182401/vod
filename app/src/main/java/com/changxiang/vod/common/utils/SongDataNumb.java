package com.changxiang.vod.common.utils;

import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class SongDataNumb {

    public static void shownum2view(int numb, TextView textview) {
//        numb = 1234567;
        if (numb < 10000) {
            textview.setText(numb + "");//收听数  tv_listen_count
        } else {
            DecimalFormat df = new DecimalFormat("###.0");
            textview.setText((df.format(numb / 10000)) + "万");//收听数  tv_listen_count
        }
    }

    public static String shownum2String(String numbStr) {
        String value = numbStr;
        float numb = 0f;

        try {
            numb = Float.parseFloat(numbStr);
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
        if (numb < 10000) {
//            holder.distance.setText(new BigDecimal(item.getFunbean() + "").setScale(0, BigDecimal.ROUND_HALF_UP) + "");//趣豆
//            value = new BigDecimal(3.5 + "").setScale(0, BigDecimal.ROUND_HALF_UP) + "";
            value = new BigDecimal(numb + "").setScale(0, BigDecimal.ROUND_HALF_UP) + "";
//            value = numb + "";
        } else {
            DecimalFormat df = new DecimalFormat("###.0");
//			textview.setText( (df.format( numb / 10000 )) + "万" );//收听数  tv_listen_count
            value = (df.format(numb / 10000)) + "万";
        }
        return value;
    }


}
