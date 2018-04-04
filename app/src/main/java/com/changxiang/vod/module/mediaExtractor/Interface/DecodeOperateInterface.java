package com.changxiang.vod.module.mediaExtractor.Interface;

/**
 * Created by for 解码mp3成
 */
public interface DecodeOperateInterface {
    public void updateDecodeProgress(int decodeProgress);

    public void decodeSuccess();

    public void decodeFail();
}
