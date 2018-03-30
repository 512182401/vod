package com.changxiang.vod.module.db;

/**
 * Created by 15976 on 2016/12/16.
 */

public interface IDownloadTable {

    public static final String _ID = "_id";
    //表名
    public static final String TABLE_NAME="Song_detail";
    //mp3表名
    public static final String MP3_TABLE_NAME="mp3";
    //播放历史表
    public static final String TABLE_NAME_HISTORY="history";
    //下载列表数据表
    public static final String TABLE_NAME_DOWNLOAD="downloadList";
    //歌曲ID
    public static final String COLUMN_SONGID="songId";
    //歌曲名
    public static final String COLUMN_SONG_NAME="songName";
    //歌手名
    public static final String COLUMN_SINGER="singerName";
    //歌曲类别（mv,mp3）
    public static final String COLUMN_TYPE="type";
    //播放量
    public static final String COLUMN_NUM="num";
    //krc地址
    public static final String COLUMN_KRC_URL="krcPath";
    //歌词地址
    public static final String COLUMN_LRC_URL="lrcPath";
    //伴奏地址
    public static final String COLUMN_ACC_URL="accPath";
    //原唱地址
    public static final String COLUMN_ORI_URL="oriPath";
    //图片
    public static final String COLUMN_IMG="imgAlbumUrl";
    //播放时间
    public static final String COLUMN_DATE="date";
    //是否已经勾选
    public static final String COLUMN_ISCHECKED="isChecked";
    //歌手图像
    public static final String COLUMN_IMGHEAD="imgHead";
    //前奏时间
    public static final String COLUMN_QZTIME="qztime";
    //home_origin，home_accompany，krc，歌词是否都已下载
    public static final String COLUMN_IS_ALL_DOWNLOAD="isAllDownload";
    //文件全部下载完成的值
    public static final int ALL_FILE_DOWNLOAD=2;
    //下载队列中的位置
    public static final String COLUMN_POSITION="position";
    //是否下载完成
    public static final String COLUMN_IS_FINISH="isFinish";
    //数据库是否已经有下载记录
    public static final String COLUMN_IS_RECORD="isRecord";
    //全部文件大小
    public static final String COLUMN_FILE_SIZE="fileSize";
    //解码地址
    public static final String SONG_DECODE="songDecode";
    //解码状态
    public static final String IS_DECODE="isDecode";
    //活动Id
    public static final String ACTIVITY_ID="activityId";


}
