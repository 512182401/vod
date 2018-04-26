package com.changxiang.vod.module.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.common.utils.MD5;
import com.changxiang.vod.common.utils.SharedPrefManager;
import com.changxiang.vod.common.view.ProgressWebView;
import com.changxiang.vod.module.base.BaseApplication;
import com.changxiang.vod.module.constance.NetInterface;
import com.changxiang.vod.module.engine.JsonParserFirst;
import com.changxiang.vod.module.entry.User;
import com.changxiang.vod.module.ui.base.BaseActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * 展示web html内容的activity
 * 需要传递url和标题title
 */
public class StoreWebActivity extends BaseActivity {

    private StringBuffer sb;
    private Map<String, String> resultunifiedorder;

    int isSucc = -1;
    private ProgressWebView webView;
    private ImageView rl_back;
    private MyWebViewClient webViewClient;

    User user = null;
    int payvalue = 0;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    /**
     * 记录URL的栈
     */
    private final Stack<String> mUrls = new Stack<>();

    String responsemsg = "请求数据为空";//网络请求返回信息
    //分享参数：
    public String sw_title;
    public String sw_imageurl;
    public String sw_sharecontent;
    public String sw_shareurl;
    public String sw_type;

    @Override
    public void handMsg(Message msg) {
        Intent intent;
        switch (msg.what) {
            case -1:
                toast("联网登录出现网络异常错误");
//                send_all.setEnabled(true);
//                    handler.sendEmptyMessageDelayed( -1, 1000 );
                break;
            case 0:
//                toast( "请求详情成功" );
//                    handler.sendEmptyMessageDelayed( 0, 1000 );

//                initData();
                break;
            case 1:
                toast(responsemsg);//
//                handler.sendEmptyMessageDelayed( 1, 100 );
                break;
            case 2://刷新成功，刷新界面
//                madapter.setDataList( replylist.getResults() );
//                handler.sendEmptyMessageDelayed( 2, 100 );
//                initData();
                break;
            case 3://加载更多成功，刷新界面
//                madapter.addDataList( replylist.getResults() );
//                handler.sendEmptyMessageDelayed( 3, 100 );
                break;
            case 11://兑换成功
                finishActivity();
                toast("充值成功！");
//                recharge_value.setText("");
//                payment_value.setText(getString(R.string.money) + 0.0);
//                updataimage();
//                handler.sendEmptyMessageDelayed( 11, 100 );
                break;
            case 123://权限
//                updataimage();
//                handler.sendEmptyMessageDelayed( 123, 100 );
                break;
            case 101:////微信后台交互成功
//                sb = new StringBuffer();
//                req = new PayReq();
//                tagid = genOutTradNo();
//                BaseApplication.tagid = tagid;
//                genPayReq();//生成签名参数(获取订单)
//                sendPayReq();//确认支付
//                handler.sendEmptyMessageDelayed( 101, 100 );
                break;
            case 102:////支付宝后台交互成功
//                sendPayALi();
//                handler.sendEmptyMessageDelayed( 102, 100 );
                break;
            case 555:////一秒内不能重复点击
//                send_all.setEnabled(true);
//                handler.sendEmptyMessageDelayed(555, 1000);//一秒内不能重复点击
                break;
            case 103://余额后台交互成功
                finishActivity();
//                toast("充值成功！");
//                recharge_value.setText("");
//                payment_value.setText(getString(R.string.money) + 0.0);
//                handler.sendEmptyMessageDelayed( 103, 100 );
                break;
            case 9000://支付成功  参数： 成功失败：code（成功：0 失败：1）  vipId：vipId，订单id：orderId ， 金额：orderAmount  ，
//                toast(responsemsg);
//                try {
//                    webView.loadUrl(payendurl + BaseApplication.getUser().getId() + "&orderId=" + orderId + "&code=0&orderAmount=" + orderAmount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                handler.sendEmptyMessageDelayed( 9000, 100 );
                break;
            case 9001://支付失败
//                toast(responsemsg);
//                try {
//                    webView.loadUrl(payendurl + BaseApplication.getUser().getId() + "&orderId=" + orderId + "&code=" + isSucc + "&orderAmount=" + orderAmount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String tagid = genOutTradNo();//重新获取tagid
//                BaseApplication.tagid = tagid;
//                handler.sendEmptyMessageDelayed( 9001, 100 );
                break;
            case 1001://分享
//                try {
//                    ShareUtil.goToShare(StoreWebActivity.this, sw_title, sw_sharecontent, sw_imageurl, sw_shareurl, "1");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                //                handler.sendEmptyMessageDelayed( 1001, 100 );
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_web);
        rl_back = (ImageView) findViewById(R.id.back_last);
        webView = (ProgressWebView) findViewById(R.id.wv_show);
        webViewClient = new MyWebViewClient();
        // 支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.clearHistory();
        webView.setDownloadListener(new MyWebViewDownLoadListener());
//优先使用缓存：
//		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//不使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        init();
        user = BaseApplication.getUser();
        initImageWebView();//js文件和图片选择配置：
    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        closeWebViewDialog();
    }

    public void closeWebViewDialog() {
        if (webView != null) {
            webView.closeProgressDialog();
        }
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");
        // WebView加载web资源
        webView.loadUrl(url);
//        webView.loadUrl(" file:///android_asset/index.html ");//本地的html
        webView.addJavascriptInterface(getHtmlObject(), "qcandroid");//qcandroid
        webView.setWebViewClient(webViewClient);

    }


    @JavascriptInterface
    private Object getHtmlObject() {
        Object insertObj = new Object() {

            @JavascriptInterface
            public void jsFunction(final String url) {
                LogUtils.sysout("444444444444 string=" + url);
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> extraHeaders = new HashMap<String, String>();
                        extraHeaders.put("Referer", "http://app.srv.quchangkeji.com:8083/tsAPI/pages/homePage/homePage.html");
                        webView.loadUrl(url, extraHeaders);
                    }
                });


                Map<String, String> extraHeaders = new HashMap<String, String>();
                extraHeaders.put("Referer", "http://app.srv.quchangkeji.com:8083/tsAPI/pages/homePage/homePage.html");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                System.out.println("js调用了这个方法:" + url);
//		toast("js调用了这个方法:" + string);
            }

            @JavascriptInterface
            public void jsShare(String title, String imageurl, String sharecontent, String shareurl, String type) {//调用app分享：
                LogUtils.sysout("分享传递数据：444444444 string" + title);
//                jsShase(final Context context, final String sharecontent, final String title, final String imageurl, final String shareurl, String type)
                sw_title = title;
                sw_imageurl = imageurl;
                sw_sharecontent = sharecontent;
                sw_shareurl = shareurl;
                sw_type = type;
//                try {
//                    ShareUtil.goToShare(StoreWebActivity.this, "测试分享", "分享", BaseApplication.getUser().getImgHeadUrl(), NetInterface.SHARE_URL, "1");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                handler.sendEmptyMessageDelayed(1001, 100);
            }


            @JavascriptInterface
            public String finishStore() {//@JavascriptInterface 因为版本兼容问题，所以需要添加这句话。
                finishActivity();
                LogUtils.sysout("Html call Java");
                return "Html call Java";
            }

            @JavascriptInterface
            public boolean JScallPhone(String phone) {//拨打电话：
                LogUtils.sysout("444444拨打电话 " + phone);
                if (phone.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));//提示，再拨打电话
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
//                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse(phone));//直接拨打电话
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @JavascriptInterface
            public void RemoveAllUrl() {//清除所有历史记录
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mUrls.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @JavascriptInterface
            public void RemoveItemUrl(final int position) {//清除栈顶一下的数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mUrls != null && position > mUrls.size()) {
                            try {
                                mUrls.clear();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        for (int i = 0; i < position; i++) {
                            try {
                                mUrls.remove(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }

            public void JavacallHtml2() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//						mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
//						Toast.makeText(JSAndroidActivity.this, "clickBtn2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        return insertObj;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return pageGoBack(webView, webViewClient);
    }

    public boolean pageGoBack(WebView web, MyWebViewClient client) {
        final String url = client.popLastPageUrl();
        if (url != null) {
            web.loadUrl(url);
            return true;
        }
        finish();
        return false;
    }

    class MyWebViewClient extends WebViewClient {
        /**
         * 记录URL的栈
         */
        private final Stack<String> mUrls = new Stack<>();
        /**
         * 判断页面是否加载完成
         */
        private boolean mIsLoading;
        private String mUrlBeforeRedirect;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mIsLoading && mUrls.size() > 0) {
                mUrlBeforeRedirect = mUrls.pop();
            }
            recordUrl(url);
            this.mIsLoading = true;
        }

        /**
         * 记录非重定向链接, 避免刷新页面造成的重复入栈
         *
         * @param url 链接
         */
        private void recordUrl(String url) {
            //这里还可以根据自身业务来屏蔽一些链接被放入URL栈
            if (!TextUtils.isEmpty(url) && !url.equalsIgnoreCase(getLastPageUrl())) {
                mUrls.push(url);
            } else if (!TextUtils.isEmpty(mUrlBeforeRedirect)) {
                mUrls.push(mUrlBeforeRedirect);
                mUrlBeforeRedirect = null;
            }
        }

        /**
         * 获取上一页的链接
         **/
        private synchronized String getLastPageUrl() {
            return mUrls.size() > 0 ? mUrls.peek() : null;
        }

        /**
         * 推出上一页链接
         */
        public String popLastPageUrl() {
            if (mUrls.size() >= 2) {
                mUrls.pop(); //当前url
                return mUrls.pop();
            }
            return null;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (this.mIsLoading) {
                this.mIsLoading = false;
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
//            return false;
        }
    }


    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    //生成订单号,测试用，在客户端生成
    private String genOutTradNo() {
        Random random = new Random();
//		return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


//    WebChromeClient();

    /**
     * js文件和图片选择配置：
     */
    private void initImageWebView() {

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    webView.progressbar.setVisibility(GONE);
//				closeProgressDialog();
                } else {
                    if (webView.progressbar.getVisibility() == GONE)
                        webView.progressbar.setVisibility(VISIBLE);
                    webView.progressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("xxx提示").setMessage(message).setPositiveButton("确定", null);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }

            //扩展浏览器上传文件
            //3.0++版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);
            }

            //3.0--版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                onenFileChooseImpleForAndroid(filePathCallback);
                return true;
            }
        });
    }


    public ValueCallback<Uri> mUploadMessage;

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
    }

    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    private void onenFileChooseImpleForAndroid(ValueCallback<Uri[]> filePathCallback) {
        mUploadMessageForAndroid5 = filePathCallback;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }
}

