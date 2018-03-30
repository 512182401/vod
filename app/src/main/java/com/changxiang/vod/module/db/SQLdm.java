package com.changxiang.vod.module.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MyFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLdm {
    //数据库存储路径
    String filePath = "data/data/com.mapbox.mapboxsdk.android.testapp/databases/c.db";
    //数据库存放的文件夹
    String pathStr = "data/data/com.mapbox.mapboxsdk.android.testapp/databases";

    SQLiteDatabase database;

    public SQLiteDatabase openDatabase(Context context) {
        System.out.println("filePath:" + filePath);
        File jhPath = new File(filePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            LogUtils.sysout("test" + "存在数据库");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            //不存在先创建文件夹
            File path = new File(pathStr);
            LogUtils.sysout("test" + "pathStr=" + path);
            if (path.mkdir()) {
                LogUtils.sysout("test" + "创建成功");
            } else {
                LogUtils.sysout("test" + "创建失败");
            }
            ;
            try {
                //得到资源
                AssetManager am = context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open("c.db");
                LogUtils.sysout("test" + is + "");
                //用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                LogUtils.sysout("test" + "fos=" + fos);
                LogUtils.sysout("test" + "jhPath=" + jhPath);
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    LogUtils.sysout("test" + "得到");
                    fos.write(buffer, 0, count);
                }
                //最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    }

    public void initSJBMarker(Context context) {
        SQLdm s = new SQLdm();
        SQLiteDatabase database = s.openDatabase(context.getApplicationContext());

        List<Map<String, String>> marker = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        Cursor cursor = database.rawQuery("select * from city_place", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("zh_name"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String short_desc = cursor.getString(cursor.getColumnIndex("short_desc"));

                map = new HashMap<String, String>();
                map.put("name", name);
                map.put("longitude", longitude);
                map.put("latitude", latitude);
                map.put("short_desc", short_desc);
                marker.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

//        for (int i = 0;i<marker.size();i++){
//            String name = marker.get(i).get("name");
//            String longitude = marker.get(i).get("longitude");
//            String latitude = marker.get(i).get("latitude");
//            String short_desc = marker.get(i).get("short_desc");
//
//            double longitudeD = Double.parseDouble(longitude);
//            double latitudeD = Double.parseDouble(latitude);
//            LatLng latLng = new LatLng(latitudeD,longitudeD);
//            Marker sjbmarker = new Marker(mv,name,short_desc,latLng);
//            sjbmarker.setToolTip(new CustomSJBMarker(mv, sjbmarker));
//            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), R.drawable.map_pin_normal);
//            sjbmarker.setIcon(new Icon(new BitmapDrawable(bitmap)));
//
//            //sjbmarker.setMarker(getResources().getDrawable(R.drawable.map_pin_favourite));
//            mv.addMarker(sjbmarker);
//        }
    }
}