package com.changxiang.vod.module.mediaExtractor.Interface;

/**
 * Created by for 合成mp3
 */
public interface ComposeAudioInterface {
    public void updateComposeProgress(int composeProgress);

    public void composeSuccess();

    public void composeFail();
}
