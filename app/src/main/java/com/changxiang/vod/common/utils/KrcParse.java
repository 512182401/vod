package com.changxiang.vod.common.utils;

//import com.quchangkeji.tosing.common.utils.krc.ZLibUtils;
//import com.quchangkeji.tosing.module.entry.KrcLine;
//import com.quchangkeji.tosing.module.entry.KrcLineTime;
//import com.quchangkeji.tosing.module.entry.KrcWord;


import com.changxiang.vod.common.utils.krc.ZLibUtils;
import com.changxiang.vod.module.entry.KrcLine;
import com.changxiang.vod.module.entry.KrcLineTime;
import com.changxiang.vod.module.entry.KrcWord;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
/**
 * KRC解析，用于歌词
 * Created by 15976 on 2017/5/22.
 */

public class KrcParse {

    //解密的key
    private static final int[] miarry = {64, 71, 97, 119, 94, 50, 116, 71, 81, 54, 49, 45, 206, 210, 110, 105};
    private static List<KrcLine> mKrcLineList = new ArrayList<KrcLine>();// 存放歌词


    //解密krc数据
    public static String getKrcText(String filenm) throws IOException
    {
        File krcfile = new File(filenm);
        byte[] zip_byte = new byte[(int) krcfile.length()];
        FileInputStream fileinstrm = new FileInputStream(krcfile);
        byte[] top = new byte[4];
        fileinstrm.read(top);
        fileinstrm.read(zip_byte);
        int j = zip_byte.length;
        for (int k = 0; k < j; k++)
        {
            int l = k % 16;
            int tmp67_65 = k;
            byte[] tmp67_64 = zip_byte;
            tmp67_64[tmp67_65] = (byte) (tmp67_64[tmp67_65] ^ miarry[l]);
        }
        String krc_text = new String( ZLibUtils.decompress(zip_byte), "utf-8" );
        return krc_text;
    }
    // 外部提供方法
    // 设置krc的路径
    public static List<KrcLine> setKrcPath(String path, boolean isSecret) throws Exception {
        mKrcLineList.clear();
        String lines="";
        if (isSecret){
             lines = getKrcText(path);//解密解压
        }else {
             lines = readFileSdcard(path);
        }

        String lineArray[] = lines.split("\r\n");
        if(lineArray.length<=10){
            return mKrcLineList;
        }

        //逐行解析
        for(int i=0;i<lineArray.length;i++){
            KrcLine krcLine = ParseLine(lineArray[i]);
            if(null!=krcLine){
                mKrcLineList.add(krcLine);
            }
        }
        return mKrcLineList;
    }

    //根据SD卡获取文件内容
    public static String readFileSdcard(String fileName) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //解析头文件
    /*private void ParseTitle(String line){
        try {
            if(line.matches("\\[.+\\].+")){

            }else{
                String sbustr = null;
                String strArray[] = line.split("\\]",2);
                String timeStr[] = strArray[0].split(":");
                String titleKey = timeStr[0].substring(1);
                String titleValue  = timeStr[1];
                if(titleKey.equals( "max" )){
                    mMaxrow = Long.parseLong(titleValue);
//                    mMaxrow = 4500;
                    Toast.makeText(getContext(),"最长一句歌词时间："+mMaxrow,Toast.LENGTH_LONG).show();
                }else if(titleKey.equals( "piece" )){
                    Toast.makeText(getContext(),"11111"+titleValue,Toast.LENGTH_LONG).show();
                } if(titleKey.equals( "ar" )){
                    Toast.makeText(getContext(),"歌手："+titleValue,Toast.LENGTH_LONG).show();
                } if(titleKey.equals( "ti" )){
                    Toast.makeText(getContext(),"歌曲："+titleValue,Toast.LENGTH_LONG).show();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    //解析一行
    private static KrcLine ParseLine(String line){
        KrcLine krcLine=null;
        try {
            krcLine = new KrcLine();
            if(line.matches("\\[.+\\].+")){
                line = line.substring(1);
                String strArray[] = line.split("\\]",2);
                String timeStr[] = strArray[0].split(",");
                KrcLineTime krcLineTime=new KrcLineTime();
                krcLineTime.setStartTime(Integer.parseInt(timeStr[0]));
                krcLineTime.setSpanTime(Integer.parseInt(timeStr[1]));
                krcLine.setLineTime(krcLineTime);
                String lyricsStr[] = strArray[1].split("[<>]");
                StringBuffer lycStr=new StringBuffer();
                for(int i=1;i<lyricsStr.length;i+=2){
                    String wordTime[] = lyricsStr[i].split(",");
                    if(wordTime.length<3){
                        continue;
                    }
                    int startT = Integer.parseInt(wordTime[0]);
                    int spanT = Integer.parseInt(wordTime[1]);
                    int reverse = Integer.parseInt(wordTime[2]);
                    String word=lyricsStr[1+i];
//                    KrcLineTime krcLineTime1 = new KrcLineTime(startT,spanT,reverse);
                    KrcWord krcWord = new KrcWord(word,startT,spanT,reverse);
                    krcLine.getWordTime().add(krcWord);
                    lycStr.append(lyricsStr[1+i]);
                }
//                krcLine.setLineStr(lycStr.toString().replace(" ",""));
                krcLine.setLineStr(lycStr.toString());
            }else{
                return  null;
            }

        }catch (Exception e){
            return null;
        }
        return krcLine;
    }
}
