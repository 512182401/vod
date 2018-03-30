package com.changxiang.vod.common.utils;

//import com.quchangkeji.tosing.R;

/**
 * 通过随机数获取不同描述
 *
 * @author wdl
 */
public class RemarkRandom {
    private static String remake = "快来听听我新唱的歌曲，一念起，万水千山；一念灭，沧海桑田。";

//1、快来听听我新唱的歌曲，一念起，万水千山；一念灭，沧海桑田。
//
//            2、你们尽管转发，不惊喜不意外算我输！
//
//            3、如果不能美得惊人，那就唱得勾魂！
//
//            4、我的歌声很大，只是现实容不下！
//
//            5、我横溢的不只是才华而已，其实还有引亢高歌。
//
//            6、一首忐忑让多少五音不全的孩子得到了自信。

    public static String getRemakeStr(int value) {
//        LogUtils.sysout("11111111111111 获取不停的描述：value = " + value);
        switch (value) {
            case 0:
                remake = "快来听听我新唱的歌曲，一念起，万水千山；一念灭，沧海桑田。";
                break;
            case 1:
                remake = "你们尽管转发，不惊喜不意外算我输！";
                break;
            case 2:
                remake = "如果不能美得惊人，那就唱得勾魂！";
                break;
            case 3:
                remake = "我的歌声很大，只是现实容不下！";
                break;
            case 4:
                remake = "我横溢的不只是才华而已，其实还有引亢高歌。";
                break;
            case 5:
                remake = "一首忐忑让多少五音不全的孩子得到了自信。";
                break;
            case 6:
                remake = "快来听听我新唱的歌曲，一念起，万水千山；一念灭，沧海桑田。";
                break;
            case 7:
                remake = "你们尽管转发，不惊喜不意外算我输！";
                break;
            case 8:
                remake = "如果不能美得惊人，那就唱得勾魂！";
                break;
            case 9:
                remake = "我的歌声很大，只是现实容不下！";
                break;
            case 10:
                remake = "我横溢的不只是才华而已，其实还有引亢高歌。";
                break;

        }
        return remake;
    }
}
