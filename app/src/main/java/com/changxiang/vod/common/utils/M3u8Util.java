package com.changxiang.vod.common.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15976 on 2016/11/23.
 */

public class M3u8Util {
    /**
     * 解析第一步
     */
    public static String parseStep1(File file){
        FileReader fileReader=null;
        BufferedReader br=null;
        try {
            fileReader=new FileReader(file);
            br=new BufferedReader(fileReader);
            String read;
            while((read=br.readLine())!=null){
                if(read.startsWith("http")){
                    //这一行就是我们想要的地址
                    return read;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileReader==null){
                try {
                    fileReader.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("TAG","解析失败");
        return null;
    }

    /**
     * 截取第二个m3u8文件的url，包含最后一个“/”前面的部分
     * @param url
     * @return
     */
    public static String parseStep2(String url){
        int index=url.lastIndexOf("/");
        return url.substring(0,index+1);

    }

    /**
     * 拼接.ts
     * @param urlStart
     * @param file
     * @return
     */
    public static List<String> parseStep3(String urlStart, File file){
        List<String> list=new ArrayList<>();
        FileReader fileReader=null;
        BufferedReader br=null;
        try {
            fileReader=new FileReader(file);
            br=new BufferedReader(fileReader);
            String read;
            while((read=br.readLine())!=null){
                if(read.endsWith(".ts")){
                    //拼接地址
                    read=read+urlStart;
                    list.add(read);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileReader==null){
                try {
                    fileReader.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;

    }
}
