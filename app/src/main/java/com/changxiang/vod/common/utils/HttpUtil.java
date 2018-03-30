package com.changxiang.vod.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/9.
 */
public class HttpUtil {
    //download
    public static Object doGet(String httpUrl) {
        Log.w("TAG","httpUrl:"+httpUrl);
        StringBuffer sb=new StringBuffer();
        InputStream is=null;
        BufferedReader br=null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //设置连接超时
            connection.setConnectTimeout(5000);
            //设置读取超时时长
            connection.setReadTimeout(5000);
            //开始连接
            connection.connect();
            //获取返回码
            int code=connection.getResponseCode();
            if(code== HttpURLConnection.HTTP_OK){
                is=connection.getInputStream();
                br=new BufferedReader(new InputStreamReader(is));
                String cache;
                while((cache=br.readLine())!=null){
                    sb.append(cache);
                }
                Log.w("TAG","请求成功，result="+sb.toString());
                return sb;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("TAG","请求失败");
        return null;
    }
    //下载图片，并放入SD卡中
    public static Bitmap downloadBitmap(String httpUrl, File dir, String rename){
        Log.w("TAG","httpUrl:"+httpUrl);
        InputStream is=null;
        FileOutputStream fos=null;
        Bitmap bitmap=null;
        File target=new File(dir,rename+".jpg");
        //先判断文件是否存在，若存在则直接返回
        if(target.exists()){
            bitmap= BitmapFactory.decodeFile(target.getAbsolutePath());
            return bitmap;
        }
        try {
            URL url=new URL( httpUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            int code=conn.getResponseCode();
            int read=-1;
            byte[] buff=new byte[1024];
            if(code== HttpURLConnection.HTTP_OK){
                is=conn.getInputStream();
                fos=new FileOutputStream(target);
                bitmap= BitmapFactory.decodeStream(is);
                //把下载的图片放到文件中
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Log.e("TAG","图片请求失败");
        return null;
    }

    //下载文件
    public static File downloadFile(String httpUrl, File dir, String rename){
        Log.w("TAG","downloadFile");
       File target=new File(dir,rename);
        if(target.exists()){
            Log.w("TAG","文件已下载，不用重复下载");
            return target;
        }
        InputStream is=null;
        FileOutputStream fos=null;
        try {
            URL url=new URL(httpUrl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            int responseCode=connection.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_OK){
                byte[] bytes=new byte[1024*10];
                is=connection.getInputStream();
                fos=new FileOutputStream(target);
                int len;
                //文件字节大小
                long contentLength=connection.getContentLength();
                long download=0;
                while((len=is.read(bytes))!=-1){
                    fos.write(bytes, 0, len);
                    fos.flush();
                    download+=len;
                    float progress=100.0f*download/contentLength;
                    Log.w("TAG","Progress:"+progress+",download:"+download);
                }
                Log.w("TAG","文件下载成功");
                return target;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                    if(fos!=null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("TAG","文件下载失败");
        return null;
    }

}
