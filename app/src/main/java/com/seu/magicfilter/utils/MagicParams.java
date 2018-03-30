package com.seu.magicfilter.utils;

import android.content.Context;

import com.changxiang.vod.common.utils.MyFileUtil;
import com.seu.magicfilter.widget.base.MagicBaseView;

/**
 * Created by why8222 on 2016/2/26.
 */
public class MagicParams {
    public static Context context;
    public static MagicBaseView magicBaseView;

    public static String videoPath = MyFileUtil.DIR_VEDIO.toString();
    public static String videoName = "MagicCamera_test.mp4";

    public static int beautyLevel = 5;

    public MagicParams() {

    }
}
