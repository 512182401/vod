//package com.changxiang.vod.common.view;
//
//import android.graphics.Rect;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
///**
// * 重设RecycleView的间距
// * Created by 15976 on 2017/4/25.
// */
//
//public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
//
//    private int space;
//
//    public SpaceItemDecoration(int space) {
//        this.space = space;
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        //不是第一个的格子都设一个左边和底部的间距
//        outRect.left = space/2;
//        outRect.right = space/2;
//        //outRect.right=space;
//            outRect.bottom = space/2;
//        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
//        if (parent.getChildLayoutPosition( view ) % 2 == 0) {
//            outRect.right =space/4;
//        }
//        if (parent.getChildLayoutPosition( view ) % 2 == 1) {
//            outRect.left=space/4;
////            outRect.left=0;
//        }
//    }
//
//}