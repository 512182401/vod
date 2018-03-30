package com.changxiang.vod.common.utils;//package tosingpk.quchangkeji.com.quchangpk.common.utils;
//
//import android.content.res.Resources;
//import android.support.design.widget.TabLayout;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import java.lang.reflect.Field;
//
///**
// * 自定义Tablayout下划线长度
// * Created by 15976 on 2017/4/6.
// */
//
//public class TabIndicatorDefine {
//
//    /**
//     *
//     * @param tabs
//     * @param leftDip 距离左边的dp
//     * @param rightDip 距离右边的dp
//     */
//    public static void setIndicator (TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//    }
//}
