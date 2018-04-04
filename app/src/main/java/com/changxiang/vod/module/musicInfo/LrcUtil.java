package com.changxiang.vod.module.musicInfo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*import com.huwei.sweetmusicplayer.comparator.LrcComparator;
import com.huwei.sweetmusicplayer.contains.ILrcStateContain;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.models.LrcContent;*/
//import com.huwei.sweetmusicplayer.models.MusicInfo;


/**
 * 歌词工具类
 */
public class LrcUtil implements ILrcStateContain {
    public static final String lrcRootPath = android.os.Environment
            .getExternalStorageDirectory().toString()
            + "/SweetMusicPlayer/Lyrics/";

    //从文件中加载歌词
    public static List<LrcContent> loadLrc(File file) {
        List<LrcContent> lrclists = new ArrayList<>();
        if (file==null||!file.exists()){
            return lrclists;
        }
        FileInputStream fin=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        //AssetManager manager=getActivity().getAssets();
        try {
            fin = new FileInputStream( file );
            //InputStream is=manager.open("lyric.txt");
            //InputStreamReader input=new InputStreamReader(is);
            isr = new InputStreamReader( fin, "utf-8" );
            br = new BufferedReader( isr );

            String s;
            while ((s = br.readLine()) != null) {
                //LogUtils.w( "TAG", s );
                handleOneLine( s, lrclists );
            }
            // 按时间排序
            Collections.sort( lrclists, new LrcComparator() );

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                if (br!=null){
                    br.close();
                }
                if (isr!=null){
                    isr.close();
                }if (fin!=null){
                    fin.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return lrclists;
    }

    /**
     * 从本地读取歌词
     *
     * @param
     * @return
     */
    public static List<LrcContent> loadLrc(AbstractMusic imusic) {
        List<LrcContent> lrclists = new ArrayList<LrcContent>();
        if (imusic.getType() == AbstractMusic.MusicType.Local) {
            MusicInfo music = (MusicInfo)imusic;
            String path = music.getPath();

            // 得到歌词文件路径
            String lrcPathString = path.substring(0, path.lastIndexOf("."))
                    + ".lrc";
            int index = lrcPathString.lastIndexOf("/");

            String parentPath;
            String lrcName;
            // if(index!=-1){
            parentPath = lrcPathString.substring(0, index);
            lrcName = lrcPathString.substring(index);
            // }
            File file = new File(lrcPathString);

            // 匹配SweetMusicPlayer/Lyrics
            if (!file.exists()) {
                file = new File(getLrcPath(
                        music.getTitle(), music.getArtist()));
            }
            Log.i("Path", file.getAbsolutePath().toString());

            // 匹配Lyrics
            if (!file.exists()) {
                file = new File(parentPath + "/../" + "Lyrics/" + lrcName);
            }
            Log.i("Path", file.getAbsolutePath().toString());

            // 匹配lyric
            if (!file.exists()) {
                file = new File(parentPath + "/../" + "lyric/" + lrcName);
            }
            Log.i("Path", file.getAbsolutePath().toString());

            // 匹配Lyric
            if (!file.exists()) {
                file = new File(parentPath + "/../" + "Lyric/" + lrcName);
            }

            Log.i("Path", file.getAbsolutePath().toString());

            // 匹配lyrics
            if (!file.exists()) {
                file = new File(parentPath + "/../" + "lyrics/" + lrcName);
            }
            Log.i("Path", file.getAbsolutePath().toString());

            if (file.exists()) {
                try {
                    FileInputStream fin = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fin, "utf-8");
                    BufferedReader br = new BufferedReader(isr);

                    String s;
                    while ((s = br.readLine()) != null) {
                        handleOneLine(s, lrclists);
                    }
                    // 按时间排序
                    Collections.sort(lrclists, new LrcComparator());

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return lrclists;
    }


    public static List<LrcContent> parseLrcStr(String lrcContent) {
        List<LrcContent> lrclists = new ArrayList<>();
        String lines[] = lrcContent.split("\n");
        for (String line : lines) {
            handleOneLine(line, lrclists);
        }
        return lrclists;
    }

    static void handleOneLine(String line, List<LrcContent> lrclists) {
        String s = line.replace("[", ""); // 去掉左边括号
        String lrcData[] = s.split("]");

        // 这句是歌词
        //if (lrcData[0].matches("^\\d{2}:\\d{2}.\\d+$")) {
        if (line.matches( "\\[.+\\].+" )) {
            int len = lrcData.length;
            //int end = lrcData[len - 1].matches("^\\d{2}:\\d{2}.\\d+$") ? len
            int end = lrcData[len - 1].matches("\\[.+\\].+") ? len
                    : len - 1;

            for (int i = 0; i < end; i++) {
                LrcContent lrcContent = new LrcContent();
                String time[]=lrcData[i].split(",");
                if (time.length!=2){
                    return;
                }

                //int lrcTime = TimeUtil.getLrcMillTime(lrcData[i]);
                lrcContent.setLrcTime(Integer.parseInt(time[0]));
                lrcContent.setEndLrcTime(Integer.parseInt(time[1]));
                if (lrcData.length == end)
                    lrcContent.setLrcStr(""); // 空白行
                else
                    lrcContent.setLrcStr(lrcData[len - 1]);

                lrclists.add(lrcContent);
            }

        }
    }

    public static void writeLrcToLoc(String title, String artist, String lrcContext) {
        FileWriter writer = null;
        try {
            File file = new File(getLrcPath(title, artist));
            if (!file.exists()) {
                file.createNewFile();
                writer = new FileWriter(getLrcPath(title, artist));
                writer.write(lrcContext);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据歌名和艺术家生产的系统默认歌词文件位置路径
     *
     * @param title
     * @param artist
     * @return
     */
    public static String getLrcPath(String title, String artist) {
        return lrcRootPath + title + " - " + artist + ".lrc";
    }
}
