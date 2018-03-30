package com.changxiang.vod.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by 15976 on 2017/4/10.
 */

public class NoMoveSeekbar extends SeekBar {

    boolean noMove=true;
    public NoMoveSeekbar(Context context) {
        super(context);
    }

    public NoMoveSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoMoveSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        //原来是要将TouchEvent传递下去的,我们不让它传递下去就行了
        if (noMove){
            return false;
        }else {
            return super.onTouchEvent(event);
        }
    }

    public void setNoMove(boolean noMove){
        this.noMove=noMove;
    }
}
