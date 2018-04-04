package com.changxiang.vod.module.mediaExtractor.Function;



import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.mediaExtractor.Decode.DecodeEngine;
import com.changxiang.vod.module.mediaExtractor.Interface.DecodeOperateInterface;
import com.changxiang.vod.module.mediaExtractor.schedulers.AndroidSchedulers;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * MP3的解码
 */
public class DecodeFunction {
    /**
     * decodeMusicFile方法的代码主要功能是获取背景音乐信息，初始化解码器，最后调用getDecodeData方法正式开始对背景音乐进行处理。
     * 解码伴奏mp3文件
     *
     * @param decodeFileUrl 解码后文件
     * @param startSecond 偏移时间
     * @param endSecond 录音时长
     * @param decodeOperateInterface
     */
    public static void DecodeMusicFile(final String musicFileUrl, final String decodeFileUrl, final int startSecond,
                                       final int endSecond,
                                       final DecodeOperateInterface decodeOperateInterface) {

        LogUtils.sysout( "开始解码+++++解码后的保存文件" + decodeFileUrl );
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                DecodeEngine.getInstance().beginDecodeMusicFile( musicFileUrl, decodeFileUrl, startSecond, endSecond,
                        decodeOperateInterface );
            }
        } ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogFunction.error( "异常观察", e.toString() );
                    }

                    @Override
                    public void onNext(String string) {
                    }
                } );
    }


}
