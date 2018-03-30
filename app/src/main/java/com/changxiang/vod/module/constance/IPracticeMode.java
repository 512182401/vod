package com.changxiang.vod.module.constance;

/**
 * Created by 15976 on 2016/12/12.
 */

public interface IPracticeMode {

    //播放模式
    public static final int freeMode=0;
    public static final int partMode=1;
    public static final int chorusMode=2;
    public static final int teachMode=3;
    public static final int prePartMode=4;//选择歌词片段
    //消息类型
    public static final int PART_LRC_MESSAGE = 0;
    public static final int HOME_DATA_MESSAGE = 3;
    /*public static final int YC_DOWNLOAD_FINISH = 1;
    public static final int BZ_DOWNLOAD_FINISH = 2;
    public static final int LRC_DOWNLOAD_FINISH = 4;
    public static final int SONG_MESSAGE_DOWNLOAD = 5;
    public static final int KRC_DOWNLOAD_FINISH = 6;*/
    //首页播放状态
    public static final int NOT_START=0;
    public static final int ISPLAYING=1;
    public static final int PAUSE=2;

    public static final int READ_LOC_FAIL = 0;    //本地查找不到歌词或者查询异常
    public static final int READ_LOC_OK = 1;
    /*public static final int QUERY_ONLINE = 2; //正在联网查找
    public static final int QUERY_ONLINE_OK = 3;
    public static final int QUERY_ONLINE_NULL = 4;    //网络无歌词
    public static final int QUERY_ONLINE_FAIL = 5; //联网查找失败*/
}
