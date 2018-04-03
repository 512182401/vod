package com.changxiang.vod.module.ui.recordmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.seu.magicfilter.filter.helper.MagicFilterType;


/**
 * Created by 15976 on 2017/9/12.
 */

public class BeautyFilterView extends LinearLayout implements View.OnClickListener {

    private TextView originConfirm;
    private TextView lomoConfirm;
    private TextView coolConfirm;
    private TextView blueConfirm;
    private TextView pinkConfirm;
    private TextView cuteConfirm;
    private TextView whiteBlackConfirm;
    private TextView danseConfirm;

    private MagicFilterType[] filters;

    public void setFilters(MagicFilterType[] filters) {
        this.filters = filters;
    }

    public BeautyFilterView(Context context) {
        this(context, null);
    }

    public BeautyFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeautyFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initEvent();
    }

    public TextView getLomoConfirm() {
        return lomoConfirm;
    }

    public TextView getOriginConfirm() {
        return originConfirm;
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.beautyfilter, this);
        originConfirm = (TextView) view.findViewById(R.id.origin_confirm);
        lomoConfirm = (TextView) view.findViewById(R.id.lomo_confirm);
        coolConfirm = (TextView) view.findViewById(R.id.cool_confirm);
        blueConfirm = (TextView) view.findViewById(R.id.blue_confirm);
        pinkConfirm = (TextView) view.findViewById(R.id.pink_confirm);
        cuteConfirm = (TextView) view.findViewById(R.id.cute_confirm);
        whiteBlackConfirm = (TextView) view.findViewById(R.id.white_black_confirm);
        danseConfirm = (TextView) view.findViewById(R.id.danse_confirm);
    }

    private void initEvent() {
        originConfirm.setOnClickListener(this);
        lomoConfirm.setOnClickListener(this);
        coolConfirm.setOnClickListener(this);
        blueConfirm.setOnClickListener(this);
        pinkConfirm.setOnClickListener(this);
        cuteConfirm.setOnClickListener(this);
        whiteBlackConfirm.setOnClickListener(this);
        danseConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.origin_confirm:
                //原图
                onFilterChangeListener.onFilterChanged(filters[0]);
                setFilterTextColor(originConfirm);
                break;
            case R.id.lomo_confirm:
                //LOMO
                onFilterChangeListener.onFilterChanged(filters[8]);//LOMO转递的是HEALTH
                setFilterTextColor(lomoConfirm);
                break;
            case R.id.cool_confirm:
                onFilterChangeListener.onFilterChanged(filters[2]);
                setFilterTextColor(coolConfirm);
                break;
            case R.id.blue_confirm:
                onFilterChangeListener.onFilterChanged(filters[18]);
                setFilterTextColor(blueConfirm);
                break;
            case R.id.pink_confirm://粉嫩
                onFilterChangeListener.onFilterChanged(filters[17]);
                setFilterTextColor(pinkConfirm);
                break;
            case R.id.cute_confirm://可人
                onFilterChangeListener.onFilterChanged(filters[5]);
                setFilterTextColor(cuteConfirm);
                break;
            case R.id.white_black_confirm:
                onFilterChangeListener.onFilterChanged(filters[30]);
                setFilterTextColor(whiteBlackConfirm);
                break;
            case R.id.danse_confirm:
                onFilterChangeListener.onFilterChanged(filters[32]);
                setFilterTextColor(danseConfirm);
                break;
        }
    }

    private void setFilterTextColor(TextView text) {
        originConfirm.setTextColor(getResources().getColor(R.color.white));
        lomoConfirm.setTextColor(getResources().getColor(R.color.white));
        coolConfirm.setTextColor(getResources().getColor(R.color.white));
        blueConfirm.setTextColor(getResources().getColor(R.color.white));
        pinkConfirm.setTextColor(getResources().getColor(R.color.white));
        cuteConfirm.setTextColor(getResources().getColor(R.color.white));
        whiteBlackConfirm.setTextColor(getResources().getColor(R.color.white));
        danseConfirm.setTextColor(getResources().getColor(R.color.white));

        text.setTextColor(getResources().getColor(R.color.app_oher_red));
    }

    public interface onFilterChangeListener {
        void onFilterChanged(MagicFilterType filterType);
    }

    private onFilterChangeListener onFilterChangeListener;

    public void setOnFilterChangeListener(onFilterChangeListener onFilterChangeListener) {
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
