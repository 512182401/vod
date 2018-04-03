package com.changxiang.vod.module.ui.recordmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.changxiang.vod.R;


/**
 * Created by 15976 on 2017/9/12.
 * 自定义音效控件
 */

public class SoundEffecView extends RelativeLayout implements View.OnClickListener {
    private LinearLayout origin_voice_ll;
    private LinearLayout record_studio_ll;
    private LinearLayout ktv_ll;
    private LinearLayout concert_ll;
    private LinearLayout valley_ll;
    private LinearLayout boy_ll;
    private LinearLayout girl_ll;
    private LinearLayout phonograph_ll;
    private LinearLayout theatre_ll;
    private ImageView origin_voice_mark;
    private ImageView record_studio_mark;
    private ImageView ktv_mark;
    private ImageView concert_mark;
    private ImageView valley_mark;
    private ImageView boy_mark;
    private ImageView girl_mark;
    private ImageView phonograph_mark;
    private ImageView theatre_mark;
    private int finalIndex;//最终选择的位子

    public int getFinalIndex() {
        return finalIndex;
    }

    public SoundEffecView(Context context) {
        this(context, null);
    }

    public SoundEffecView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoundEffecView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initEvent();
    }

    private void initEvent() {
        origin_voice_ll.setOnClickListener(this);
        record_studio_ll.setOnClickListener(this);
        ktv_ll.setOnClickListener(this);
        concert_ll.setOnClickListener(this);
        valley_ll.setOnClickListener(this);
        boy_ll.setOnClickListener(this);
        girl_ll.setOnClickListener(this);
        phonograph_ll.setOnClickListener(this);
        theatre_ll.setOnClickListener(this);
    }

    //加载布局
    private void initView(Context context) {
        View view = View.inflate(context, R.layout.sound_effect_layout, this);
        origin_voice_ll = (LinearLayout) view.findViewById(R.id.origin_voice_ll);
        record_studio_ll = (LinearLayout) view.findViewById(R.id.record_studio_ll);
        ktv_ll = (LinearLayout) view.findViewById(R.id.ktv_ll);
        concert_ll = (LinearLayout) view.findViewById(R.id.concert_ll);
        valley_ll = (LinearLayout) view.findViewById(R.id.valley_ll);
        boy_ll = (LinearLayout) view.findViewById(R.id.boy_ll);
        girl_ll = (LinearLayout) view.findViewById(R.id.girl_ll);
        phonograph_ll = (LinearLayout) view.findViewById(R.id.phonograph_ll);
        theatre_ll = (LinearLayout) view.findViewById(R.id.theatre_ll);
        //选中标记
        origin_voice_mark = (ImageView) view.findViewById(R.id.origin_voice_mark);
        record_studio_mark = (ImageView) view.findViewById(R.id.record_studio_mark);
        ktv_mark = (ImageView) view.findViewById(R.id.ktv_mark);
        concert_mark = (ImageView) view.findViewById(R.id.concert_mark);
        valley_mark = (ImageView) view.findViewById(R.id.valley_mark);
        boy_mark = (ImageView) view.findViewById(R.id.boy_mark);
        girl_mark = (ImageView) view.findViewById(R.id.girl_mark);
        phonograph_mark = (ImageView) view.findViewById(R.id.phonograph_mark);
        theatre_mark = (ImageView) view.findViewById(R.id.theatre_mark);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.origin_voice_ll://原声
                whichSelect(0);
                break;
            case R.id.record_studio_ll://录音棚
                whichSelect(1);
                break;
            case R.id.ktv_ll://KTV
                whichSelect(2);
                break;
            case R.id.concert_ll://演唱会
                whichSelect(3);
                break;
            case R.id.valley_ll://山谷
                whichSelect(4);
                break;
            case R.id.boy_ll://男声
                whichSelect(5);
                break;
            case R.id.girl_ll://女声
                whichSelect(6);
                break;
            case R.id.phonograph_ll://留声机
                whichSelect(7);
                break;
            case R.id.theatre_ll://剧场
                whichSelect(8);
                break;
        }
    }

    private void whichSelect(int index) {
        finalIndex = index;
        myListener.getData(index);
        switch (index) {
            case 0:
                origin_voice_mark.setVisibility(VISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 1:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(VISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 2:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(VISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 3:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(VISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 4:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(VISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 5:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(VISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 6:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(VISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 7:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(VISIBLE);
                theatre_mark.setVisibility(INVISIBLE);
                break;
            case 8:
                origin_voice_mark.setVisibility(INVISIBLE);
                record_studio_mark.setVisibility(INVISIBLE);
                ktv_mark.setVisibility(INVISIBLE);
                concert_mark.setVisibility(INVISIBLE);
                valley_mark.setVisibility(INVISIBLE);
                boy_mark.setVisibility(INVISIBLE);
                girl_mark.setVisibility(INVISIBLE);
                phonograph_mark.setVisibility(INVISIBLE);
                theatre_mark.setVisibility(VISIBLE);
                break;

        }

    }

    private MyListener myListener;

    public interface MyListener {
        //通过抽象方法的参数传递数据的
        void getData(int index);
    }

    //回调方法
    public void setOnListener(MyListener myListener) {
        this.myListener = myListener;
    }
}
