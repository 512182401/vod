package com.changxiang.vod.module.ui.recordmusic.recordutils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by 15976 on 2017/9/26.
 * 不能拖动的seekbar
 */

public class NoDragSeekBar extends SeekBar {
    public NoDragSeekBar(Context context) {
        super(context);
    }

    public NoDragSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoDragSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return false;
    }
}
