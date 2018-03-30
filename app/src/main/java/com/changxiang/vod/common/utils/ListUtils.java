package com.changxiang.vod.common.utils;

import java.util.List;

/**
 * Created by 15976 on 2017/10/23.
 * 集合工具类
 */

public class ListUtils {
    /**
     * 打印集合
     */
    public static void print(List list){
        String result = "(";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1){
                result = result + list.get(i);
            }else {
                result = result + list.get(i) + ",";
            }
        }
        result += ")";
        LogUtils.w("ListUtils",result);
    }
}
