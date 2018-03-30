//package com.changxiang.vod.module.engine;
//
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.os.Environment;
//
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.FormBody;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okio.Buffer;
//import okio.BufferedSink;
//import okio.ForwardingSink;
//import okio.Okio;
//import okio.Sink;
//
////import com.quchangkeji.tosingpk.common.utils.LanguageUtil;
////import com.quchangkeji.tosing.common.utils.LogUtils;
//
///**
// * 对于网络操作的业务逻辑 采用单例模式
// *
// * @author admin
// */
//public class NetInterfaceEngine {
//    private static OkHttpClient okHttpClient;
//    private static final String URL_SING = "";
//    private RequestBody params;
//    private File tempFile;
//
//    private static NetInterfaceEngine engine;
//
//    private NetInterfaceEngine() {
//        okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(30, TimeUnit.SECONDS)
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//    }
//
//    public static NetInterfaceEngine getEngine() {
//        if (engine == null) {
//            engine = new NetInterfaceEngine();
//        }
//        return engine;
//    }
//
//    private static String buildParamStr(JSONObject ClientKey) {
//        String content;
//        content = String.valueOf(ClientKey);
////        LogUtils.log("params", content);//打开用于查看测试数据
//        return content;
//    }
//
//
//    public static void sendhttpBase(FormBody requestBody, String url, Callback callback) {
//        LogUtils.sysout("请求接口url:" + url);
////        LogUtils.sysout( "请求接口参数:" + requestBody. );
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < requestBody.size(); i++) {
//            try {
//                sb.append(requestBody.name(i) + "=" + requestBody.value(i) + "&");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        LogUtils.sysout("请求接口参数:" + sb.toString());
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
////                .addHeader( "params", requestBody.toString() )
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//
//
//    }
//
//
//
//    public static void sendhttp(String params, String url, Callback callback) {
//        LogUtils.sysout("请求接口url:" + url);
//
//        JSONObject rootObj = null;
//        try {
//            if (params != null && !params.equals("")) {
//                rootObj = new JSONObject(params);
//                rootObj.put("font", LanguageUtil.getLanguage());
//            } else {
//                rootObj = new JSONObject();
//                rootObj.put("font", LanguageUtil.getLanguage());
//            }
//            params = rootObj.toString();
//            LogUtils.sysout("请求接口字段:" + params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        if (rootObj!=null){
////            params=rootObj.toString();
////        }
//        OkHttpClient okHttpClient = new OkHttpClient();
//        FormBody requestBody = new FormBody.Builder()
//                .add("params", params)
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .addHeader("params", requestBody.toString())
//                .build();
//        LogUtils.sysout("请求接口字段request:" + request);
//        okHttpClient.newCall(request).enqueue(callback);
//
//
//    }
//
//    //带文本参数和文件参数的post请求
//    public void filePostClick(Context context, String params, String url, Callback callback) {
//        tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.txt");
//        try {
//            AssetManager assetManager = context.getAssets();
//            InputStream inputStream = assetManager.open("info.txt");
//            FileWriter fileWriter = new FileWriter(tempFile);
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                fileWriter.write(line);
//            }
//            br.close();
//            fileWriter.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        RequestBody fileBody = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), tempFile);
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "wangwu")
//                .addFormDataPart("password", "hello12345")
//                .addFormDataPart("gender", "female")
//                .addFormDataPart("file", "info.txt", fileBody)
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
////                .post("params",requestBody)
//                .post(requestBody)
//                .addHeader("token", "info.txt")
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//    }
//
//
//    //带文本参数和图片参数的post请求
//    public static void startUploadImagelist(Context context, String params, String filename, List<File> tempFileList, String url, Callback callback) {
////        tempFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.txt" );
//        LogUtils.sysout("请求接口url:" + url);
//        LogUtils.sysout("请求接口字段:" + params);
//        LogUtils.sysout("请求接口:filename" + filename);
//        LogUtils.sysout("请求接口:tempFileList的图片个数" + tempFileList.size());
////        LogUtils.sysout( "请求接口:tempFile" + tempFile.toString() );
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        if (tempFileList != null && !tempFileList.isEmpty()) {
//
//            MultipartBody.Builder mBuilder = new MultipartBody.Builder();
//            mBuilder.setType(MultipartBody.FORM);
//            mBuilder.addFormDataPart("params", params);
//            for (int i = 0; i < tempFileList.size(); i++) {
//                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png; charset=utf-8"), tempFileList.get(i));
//                mBuilder.addFormDataPart("fileNames" + i, filename, fileBody);
//            }
//            RequestBody requestBody = mBuilder.build();
//
//            Request request = new Request.Builder()
//                    .url(url)
////                .post("params",requestBody)
//                    .post(requestBody)
//                    .addHeader("token", "image.png")
//                    .build();
//            okHttpClient.newCall(request).enqueue(callback);
//        }
//    }
//
//    //带文本参数和图片参数的post请求
//    public static void startUploadImageBase(RequestBody requestBody, String url, Callback callback) {
////        tempFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.txt" );
//        LogUtils.sysout("请求接口url:" + url);
////        LogUtils.sysout("请求接口字段:" + params);
////        LogUtils.sysout("请求接口:filename" + filename);
////        LogUtils.sysout("请求接口:tempFile" + tempFile.toString());
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
////                .post("params",requestBody)
//                .post(requestBody)
//                .addHeader("token", "image.png")
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//    }
//
//    //带文本参数和图片参数的post请求
//    public static void startUploadImage(Context context, String params, String filename, File tempFile, String url, Callback callback) {
////        tempFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.txt" );
//        LogUtils.sysout("请求接口url:" + url);
//        LogUtils.sysout("请求接口字段:" + params);
//        LogUtils.sysout("请求接口:filename" + filename);
//        LogUtils.sysout("请求接口:tempFile" + tempFile.toString());
//        OkHttpClient okHttpClient = new OkHttpClient();
//        try {
//            AssetManager assetManager = context.getAssets();
//            InputStream inputStream = assetManager.open(filename);
////            InputStream inputStream = assetManager.open( "info.txt" );
//            FileWriter fileWriter = new FileWriter(tempFile);
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                fileWriter.write(line);
//            }
//            br.close();
//            fileWriter.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png; charset=utf-8"), tempFile);
////        RequestBody fileBody = RequestBody.create( MediaType.parse( "image/png; charset=utf-8" ), tempFile );
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("params", params)
////                .addFormDataPart( "file", "info.txt", fileBody )
//                .addFormDataPart("fileNames", filename, fileBody)
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
////                .post("params",requestBody)
//                .post(requestBody)
//                .addHeader("token", "image.png")
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//    }
//
//    /**
//     * 传递多张图片：
//     *
//     * @param context
//     * @param params
//     * @param filename
//     * @param tempFile
//     * @param url
//     * @param callback
//     */
//    public static void upFiles(Context context, String params, String filename, File tempFile, String url, Callback callback) {
//    /* 第一个要上传的file */
//        RequestBody fileBody1 = RequestBody.create(MediaType.parse("image/png; charset=utf-8"), tempFile);
//        String file1Name = "testFile1.png";
//
//    /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("image/png; charset=utf-8"), tempFile);
//        String file2Name = "testFile2.png";
//
//
//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";
//
//        MultipartBody mBody = new MultipartBody.Builder(boundary).setType(MultipartBody.FORM)
//                .addFormDataPart("params", params)
//            /* 底下是上传了两个文件 */
//                .addFormDataPart("fileNames", file1Name, fileBody1)
//                .addFormDataPart("fileNames", file2Name, fileBody2)
//                .build();
////        mBody.addFormDataPart("fileNames" , file1Name , fileBody1);
////    /* 下边的就和post一样了 */
//        Request request = new Request.Builder().url(url).post(mBody).build();
//        okHttpClient.newCall(request).enqueue(callback);
//
//    }
//
////    //上传头像（图片）文件
////    public static void startUploadImage(Context context, String filename, File tempFile, String url, Callback callback, ProgressListener progressListenerl) {
//////        tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.pdf");
////        FormBody requestBody1 = new FormBody.Builder()
////                .decrease( "params", "" )
////                .build();
////        RequestBody requestBody = new MultipartBody.Builder()
////                .setType( MultipartBody.FORM )
//////                .addFormDataPart( "files", null, new MultipartBody.Builder( "BbC04y" )
//////                .addPart( Headers.of("Content-Disposition", "form-data; filename=\"img.png\"")
//////                .addFormDataPart("files", filename, RequestBody.create(MediaType.parse("image/png; charset=utf-8"), tempFile))
////                        .addFormDataPart( "image", "image.png", RequestBody.create( MediaType.parse( "image/png; charset=utf-8" ), tempFile ) )
//////                        .build() )
////                .build();
////        ProgressRequestBody progressRequestBody = new ProgressRequestBody( requestBody, new ProgressListener() {
////            @Override
////            public void update(long bytesRead, long contentLength, boolean done) {
////
////            }
////        } );
////        Request request = new Request.Builder()
////                .url( url )
////                .post( progressRequestBody )
////                .post( requestBody1 )
////                .build();
////        okHttpClient.newCall( request ).enqueue( callback );
////    }
//
//    //点击按钮开始上传文件
//    public void startUploadFile(Context context, String params, String url, Callback callback, final ProgressListener progressListener) {
//        tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.pdf");
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", "test.pdf", RequestBody.create(MediaType.parse("application/pdf; charset=utf-8"), tempFile))
//                .build();
//        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, progressListener);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(progressRequestBody)
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//    }
//
//    //下载文件
//    public static void startDownloadClick(String url, Callback callback) {
//        OkHttpClient okHttpClient = new OkHttpClient();
////        url = "http://test.sm.srv.quchangkeji.com:8660/av/xiangfengshishouge/audio/ac/mp3/mp3.mp3";//测试地址
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//    }
//
//
//    //通过实现进度回调接口中的方法，来显示进度
//    private ProgressListener progressListener = new ProgressListener() {
//        @Override
//        public void update(long bytesRead, long contentLength, boolean done) {
//            int progress = (int) (100.0 * bytesRead / contentLength);
//
////            progressBar.setProgress(progress);
//        }
//    };
//
//    //自定义的RequestBody，能够显示进度
//    public static class ProgressRequestBody extends RequestBody {
//        //实际的待包装请求体
//        private final RequestBody requestBody;
//        //进度回调接口
//        private final ProgressListener progressListener;
//        //包装完成的BufferedSink
//        private BufferedSink bufferedSink;
//
//        /**
//         * 构造函数，赋值
//         *
//         * @param requestBody      待包装的请求体
//         * @param progressListener 回调接口
//         */
//        public ProgressRequestBody(RequestBody requestBody, ProgressListener progressListener) {
//            this.requestBody = requestBody;
//            this.progressListener = progressListener;
//        }
//
//        /**
//         * 重写调用实际的响应体的contentType
//         *
//         * @return MediaType
//         */
//        @Override
//        public MediaType contentType() {
//            return requestBody.contentType();
//        }
//
//        /**
//         * 重写调用实际的响应体的contentLength
//         *
//         * @return contentLength
//         * @throws IOException 异常
//         */
//        @Override
//        public long contentLength() throws IOException {
//            return requestBody.contentLength();
//        }
//
//        /**
//         * 重写进行写入
//         *
//         * @param sink BufferedSink
//         * @throws IOException 异常
//         */
//        @Override
//        public void writeTo(BufferedSink sink) throws IOException {
//            if (bufferedSink == null) {
//                //包装
//                bufferedSink = Okio.buffer(sink(sink));
//            }
//            //写入
//            requestBody.writeTo(bufferedSink);
//            //必须调用flush，否则最后一部分数据可能不会被写入
//            bufferedSink.flush();
//
//        }
//
//        /**
//         * 写入，回调进度接口
//         *
//         * @param sink Sink
//         * @return Sink
//         */
//        private Sink sink(Sink sink) {
//            return new ForwardingSink(sink) {
//                //当前写入字节数
//                long bytesWritten = 0L;
//                //总字节长度，避免多次调用contentLength()方法
//                long contentLength = 0L;
//
//                @Override
//                public void write(Buffer source, long byteCount) throws IOException {
//                    super.write(source, byteCount);
//                    if (contentLength == 0) {
//                        //获得contentLength的值，后续不再调用
//                        contentLength = contentLength();
//                    }
//                    //增加当前写入的字节数
//                    bytesWritten += byteCount;
//                    //回调
//                    progressListener.update(bytesWritten, contentLength, bytesWritten == contentLength);
//                }
//            };
//        }
//    }
//
//    //进度回调接口
//    public interface ProgressListener {
//        void update(long bytesRead, long contentLength, boolean done);
//    }
//
//
//}
