package com.changxiang.vod.common.utils;

import android.content.Context;

/**
 * 字符串相关类：
 */
public class StringUtil {
    /**
     * 判断字符串包含某个字符段
     */
    public static boolean isInclude(Context context, String desString, String key) {
        boolean isinclude = false;
        if (desString.indexOf(key) != -1) {
//			System.out.println("包含该字符串");
            isinclude = true;
        }
        return isinclude;
    }


}
