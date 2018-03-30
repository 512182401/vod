package com.changxiang.vod.common.utils;

import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/9/1.
 */
public class QuchangTask extends AsyncTask {

    private IRequestCallBack callBack;

    private IRequest request;

    /**
     * 请求任务
     * @param callBack 请求结果回调
     * @param request 执行请求的接口
     */
    public QuchangTask(IRequestCallBack callBack,IRequest request) {
        this.callBack = callBack;
        this.request = request;

        if(request == null || callBack == null){
            throw new IllegalArgumentException("request or callback can not be null");
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return request.doRequest();

    }

    @Override
    protected void onPostExecute(Object o) {

        if(o!=null){
            callBack.onSeccess(o);
        }else{
            callBack.onFailed("请求失败！！！");
        }
    }

    /**
     * 执行请求接口
     */
    public interface IRequest{
        /**
         * 请求操作
         * @return 请求结果
         */
        Object doRequest();
    }

    /**
     * 请求结果回调
     */
    public interface IRequestCallBack{
        /**
         * 请求成功的回调
         * @param obj
         */
        void onSeccess(Object obj);

        /**
         * 请求失败的回调
         */
        void onFailed(String msg);
    }
}
