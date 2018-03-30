//package com.changxiang.vod.common.utils.krc;
//
//import android.os.Environment;
//
//import com.quchangkeji.tosingpk.module.entry.Krc;
//import com.quchangkeji.tosingpk.module.entry.KrcLine;
//import com.quchangkeji.tosingpk.module.entry.KrcLineTime;
//import com.quchangkeji.tosingpk.module.entry.KrcWord;
//
//import org.apache.http.util.EncodingUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * KRC解析，用于音乐屏
// * Created by ss on 2015/8/7.
// */
//public class Krcparser {
//    //解密的key
//    private static final int[] miarry = {64, 71, 97, 119, 94, 50, 116, 71, 81, 54, 49, 45, 206, 210, 110, 105};
//    //文件根目录，存放图片、音乐和krc歌词
//    private String mDir = Environment.getExternalStorageDirectory()
//            + File.separator + "quchang" + File.separator;
//    // 歌词路径
//    String krcPath = mDir + "一克拉的眼泪.krc";
//    private static List<KrcLine> mKrcLineList = new ArrayList<KrcLine>();// 存放歌词
//
//    /**
//     * 直接取得文件内容
//     *
//     * @param path
//     */
//    public static Krc getPathData(String path) {
//        String lines = "";
//        Krc mKrc = new Krc();
//        mKrcLineList = new ArrayList<>();// 存放歌词
//        //TODO
////        LogUtils.sysout("歌詞解析11111111111");
//        lines = readFileSdcard(path);
//        if (lines == null) {
//            return null;
//        }
////        LogUtils.sysout("歌詞解析22222222222222");
//        String lineArray[] = lines.split( "\r\n" );
//        if (lineArray.length <= 10) {
//            return null;
//        }
//
//        //逐行解析
//        for (int i = 0; i < lineArray.length; i++) {
//            ParseTitle( lineArray[i],mKrc);
////            LogUtils.sysout("歌詞解析3333333");
//            KrcLine krcLine = ParseLine( lineArray[i] );
//            if (null != krcLine) {
//                mKrcLineList.add( krcLine );
//            }
//        }
////        LogUtils.sysout("歌詞解析mKrcLineList==" + mKrcLineList);
//        if(mKrcLineList!=null&&mKrcLineList.size()>0){
//             mKrc.setmKrcLineList( mKrcLineList );
//            return mKrc;
//        }else{
//            return null;
//        }
//
//    }
//
//    //解析头文件
//    private static void ParseTitle(String line, Krc mKrc){
//        try {
//            if(line.matches("\\[.+\\].+")){//  匹配  ？？？？？
//
//            }else{
//                String sbustr = null;
//                String strArray[] = line.split("\\]",2);
//                String timeStr[] = strArray[0].split(":");
//                String titleKey = timeStr[0].substring(1);
//                String titleValue  = timeStr[1];
//                if(titleKey.equals( "max" )){
//                    mKrc.setMax( Long.parseLong(titleValue) );
////                    mMaxrow = 4500;
////                    Toast.makeText(getContext(),"最长一句歌词时间："+mMaxrow,Toast.LENGTH_LONG).show();
//                }else if(titleKey.equals( "piece" )){
//                    mKrc.setPiece( Integer.parseInt( titleValue ) );
////                    Toast.makeText(getContext(),"11111"+titleValue,Toast.LENGTH_LONG).show();
//                } if(titleKey.equals( "ar" )){
//                    mKrc.setAr( titleValue );
////                    Toast.makeText(getContext(),"歌手："+titleValue,Toast.LENGTH_LONG).show();
//                } if(titleKey.equals( "ti" )){
//                    mKrc.setTi( titleValue );
////                    Toast.makeText(getContext(),"歌曲："+titleValue,Toast.LENGTH_LONG).show();
//                }if(titleKey.equals( "by" )){
//                    mKrc.setBy( titleValue );
////                    Toast.makeText(getContext(),"歌曲："+titleValue,Toast.LENGTH_LONG).show();
//                }if(titleKey.equals( "total" )){
//                    mKrc.setTotal( titleValue );
////                    Toast.makeText(getContext(),"歌曲："+titleValue,Toast.LENGTH_LONG).show();
//                }if(titleKey.equals( "lrc" )){
//                    mKrc.setLrc( Integer.parseInt( titleValue )  );
////                    Toast.makeText(getContext(),"歌曲："+titleValue,Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    //根据SD卡获取文件内容
//    public static String readFileSdcard(String fileName) {
//        String res = "";
//        try {
//            FileInputStream fin = new FileInputStream(fileName);
//            int length = fin.available();
//            byte[] buffer = new byte[length];
//            fin.read(buffer);
//            res = EncodingUtils.getString(buffer, "UTF-8");
//            fin.close();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//
//    // 外部提供方法
//    // 设置krc的路径
//    public void setKrcPath1(String path) {
//        String lines = null;
//        try {
//            mKrcLineList.clear();
//            //解密解压
//            lines = getKrcText( path );
//            //
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (lines == null) {
//            return;
//        }
//        String lineArray[] = lines.split( "\r\n" );
//        if (lineArray.length <= 10) {
//            return;
//        }
//
//        //逐行解析
//        for (int i = 0; i < lineArray.length; i++) {
//            KrcLine krcLine = ParseLine( lineArray[i] );
//            if (null != krcLine) {
//                mKrcLineList.add( krcLine );
//            }
//        }
//
//    }
//    //
//
//    //解密krc数据
//    public String getKrcText(String filenm) throws IOException {
//        File krcfile = new File( filenm );
//        byte[] zip_byte = new byte[(int) krcfile.length()];
//        FileInputStream fileinstrm = new FileInputStream( krcfile );
//        byte[] top = new byte[4];
//        fileinstrm.read( top );
//        fileinstrm.read( zip_byte );
//        int j = zip_byte.length;
//        for (int k = 0; k < j; k++) {
//            int l = k % 16;
//            int tmp67_65 = k;
//            byte[] tmp67_64 = zip_byte;
//            tmp67_64[tmp67_65] = (byte) (tmp67_64[tmp67_65] ^ miarry[l]);
//        }
//        String krc_text = new String( ZLibUtils.decompress( zip_byte ), "utf-8" );
//        return krc_text;
//    }
//
//    // 外部提供方法
//    // 设置krc的路径
//    public void setKrcPath(String path) throws Exception {
//        mKrcLineList.clear();
//        //解密解压
//        String lines = getKrcText( path );
//        //
//        String lineArray[] = lines.split( "\r\n" );
//        if (lineArray.length <= 10) {
//            return;
//        }
//
//        //逐行解析
//        for (int i = 0; i < lineArray.length; i++) {
//            KrcLine krcLine = ParseLine( lineArray[i] );
//            if (null != krcLine) {
//                mKrcLineList.add( krcLine );
//            }
//        }
//    }
//
//    //解析一行
//    private static KrcLine ParseLine(String line) {
//        String LineStr = "";//每一行歌词（home_record_tone）
//        KrcLine krcLine = null;
//        KrcLineTime lineTime = null;
//        List<KrcWord> lineTimelist = null;
//        try {
//
//            krcLine = new KrcLine();
//            lineTime = new KrcLineTime();
//            lineTimelist = new ArrayList<>(  );
//            if (line.matches( "\\[.+\\].+" )) {
//
//                line = line.substring( 1 );
//                String strArray[] = line.split( "\\]", 2 );
//                String timeStr[] = strArray[0].split( "," );
////                krcLine.lineTime.startTime = Long.parseLong( timeStr[0] );
//                lineTime.setStartTime( Integer.parseInt( timeStr[0] ) );
////                krcLine.lineTime.spanTime = Long.parseLong( timeStr[1] );
//                lineTime.setSpanTime( Integer.parseInt( timeStr[1] ) );
//                krcLine.setLineTime( lineTime );
//                String lyricsStr[] = strArray[1].split( "[<>]" );
//                for (int i = 1; i < lyricsStr.length; i += 2) {
//                    String wordTime[] = lyricsStr[i].split( "," );
//                    if (wordTime.length < 3) {
//                        continue;
//                    }
//                    int startT = Integer.parseInt( wordTime[0] );
//                    int spanT = Integer.parseInt( wordTime[1] );
//                    int reverse = Integer.parseInt( wordTime[2] );
//                    String word=lyricsStr[1+i];
////                    KrcLineTime krcLineTime = new KrcLineTime( startT, spanT, reverse );
//                    KrcWord krcWord = new KrcWord(word,startT,spanT,reverse);
//
////                    krcLine.wordTime.add( krcLineTime );
////                    krcLine.getWordTime().add( krcLineTime );
//                    lineTimelist.add( krcWord );
////                    krcLine.lineStr += lyricsStr[1 + i];
////                    krcLine.setLineStr( krcLine.getLineStr() + lyricsStr[1 + i] );
//                    LineStr +=reverse;
//
//                }
//            } else {
//                return null;
//            }
//            krcLine.setLineStr(LineStr);
//            krcLine.setWordTime( lineTimelist );
//        } catch (Exception e) {
//            return null;
//        }
//
//        return krcLine;
//    }
//
//
//}
