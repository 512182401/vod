package com.changxiang.vod.module.mediaExtractor.Function;

import android.os.Handler;
import android.os.Looper;


import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.constance.Constant;
import com.changxiang.vod.module.mediaExtractor.Global.Variable;
import com.changxiang.vod.module.mediaExtractor.Interface.ComposeAudioInterface;
import com.changxiang.vod.module.mediaExtractor.libmp3lame.LameUtil;
import com.changxiang.vod.module.mediaExtractor.schedulers.AndroidSchedulers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * MP3的合成
 * Created by zhengtongyu on 16/5/29.
 */
public class AudioFunction {

    private static boolean isstop = false;
    //composeFilePath 合成文件路径
    //composewavFilePath 合成为转码的


    public static boolean isstop() {
        return isstop;
    }

    public static void setIsstop(boolean isstop) {
        AudioFunction.isstop = isstop;
    }

    /**
     * 将录音wav和伴奏MP3，合成MP3文件
     *
     * @param firstAudioPath        伴奏解码后的wav文件地址
     * @param secondAudioPath       录制的wav文件地址
     * @param composeFilePath       合成文件路径
     * @param composewavFilePath    合成为转码的
     * @param deleteSource
     * @param firstAudioWeight
     * @param secondAudioWeight
     * @param audioOffset
     * @param composeAudioInterface
     */
    public static void BeginComposeAudio(final String firstAudioPath, final String secondAudioPath,
                                         final String composeFilePath, final String composewavFilePath, final boolean deleteSource,
                                         final float firstAudioWeight,
                                         final float secondAudioWeight, final int audioOffset,
                                         final int PreludeValue, final ComposeAudioInterface composeAudioInterface) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ComposeAudio(firstAudioPath, secondAudioPath, composeFilePath, composewavFilePath, deleteSource,
                        firstAudioWeight, secondAudioWeight, audioOffset, PreludeValue, composeAudioInterface);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String string) {
                    }
                });
    }

    //composeFilePath 合成文件路径
    //firstAudioWeight  合成过程中的音量权重
    //secondAudioWeight  合成过程中的音量权重
//    audioOffset 用以指明第一个音频文件相对于第二个音频文件合成过程中的数据偏移，
// 如为负数，则合成过程中先输出audioOffset个字节长度的第二个音频文件数据，
// 如为正数，则合成过程中先输出audioOffset个字节长度的第一个音频文件数据，
//    PreludeValue 跳过前奏时长（字节长度）

// audioOffset在另一程度上也代表着时间的偏移，
// 目前我们合成的两个音频文件参数为16位单通道44.1khz采样率，
// 那么audioOffset如果为116/81*44100=88200字节，
// 那么最终合成出的MP3文件中会先播放1s的第一个音频文件的音频接着再播放两个音频文件加和的音频。

    public static void ComposeAudio(String firstAudioFilePath, String secondAudioFilePath,
                                    String composeAudioFilePath, String composewavFilePath, boolean deleteSource,
                                    float firstAudioWeight, float secondAudioWeight,
                                    int audioOffset, int PreludeValue,
                                    final ComposeAudioInterface composeAudioInterface) {
        boolean firstAudioFinish = false;
        boolean secondAudioFinish = false;
        isstop = false;
        byte[] firstAudioByteBuffer;
        byte[] secondAudioByteBuffer;
        byte[] mp3Buffer;
        byte[] wavBuffer;

        short resultShort;
        short[] outputShortArray;

        int index;
        int outbyte;
        int firstAudioReadNumber;
        int secondAudioReadNumber;
        int outputShortArrayLength;
        final int byteBufferSize = 1024;

        firstAudioByteBuffer = new byte[byteBufferSize];
        secondAudioByteBuffer = new byte[byteBufferSize];
        mp3Buffer = new byte[(int) (7200 + (byteBufferSize * 1.25))];
        wavBuffer = new byte[(int) (7200 + (byteBufferSize * 1.25))];

        outputShortArray = new short[byteBufferSize / 2];

        Handler handler = new Handler(Looper.getMainLooper());

        FileInputStream firstAudioInputStream = FileFunction.GetFileInputStreamFromFile
                (firstAudioFilePath);
        FileInputStream secondAudioInputStream = FileFunction.GetFileInputStreamFromFile
                (secondAudioFilePath);
        //composewavOutputStream 合成文件路径(未合成)
        FileOutputStream composewavOutputStream = FileFunction.GetFileOutputStreamFromFile
                (composewavFilePath);//composeFilePath 合成文件路径
        //composeAudioOutputStream 合成文件路径
        FileOutputStream composeAudioOutputStream = FileFunction.GetFileOutputStreamFromFile
                (composeAudioFilePath);//composeFilePath 合成文件路径

        try {
            LameUtil.init(Constant.RecordSampleRate, Constant.LameBehaviorChannelNumber,
                    Constant.BehaviorSampleRate, Constant.LameBehaviorBitRate, Constant.LameMp3Quality);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            while (!firstAudioFinish && !secondAudioFinish) {
                index = 0;
                outbyte = 0;
                LogUtils.sysout("555555555555 PreludeValue = " + PreludeValue);
                if (PreludeValue > 0) {//第一个为伴奏
                    while (PreludeValue > 0) {//有跳过前奏时长
                        firstAudioReadNumber = firstAudioInputStream.read(firstAudioByteBuffer);
                        PreludeValue -= firstAudioReadNumber;
                    }
                } else if (PreludeValue < 0) {//第二个为伴奏
                    while (PreludeValue < 0) {//有跳过前奏时长
                        secondAudioReadNumber = secondAudioInputStream.read(secondAudioByteBuffer);
                        PreludeValue += secondAudioReadNumber;
                    }

                }

                if (audioOffset < 0) {// 如为负数，则合成过程中先输出audioOffset个字节长度的第二个音频文件数据，
                    secondAudioReadNumber = secondAudioInputStream.read(secondAudioByteBuffer);

                    outputShortArrayLength = secondAudioReadNumber / 2;

                    for (; index < outputShortArrayLength; index++) {
                        resultShort = CommonFunction.GetShort(secondAudioByteBuffer[index * 2],
                                secondAudioByteBuffer[index * 2 + 1], Variable.isBigEnding);

                        outputShortArray[index] = (short) (resultShort * secondAudioWeight);
                    }

                    audioOffset += secondAudioReadNumber;//

                    if (secondAudioReadNumber < 0) {//获取不到数据
                        secondAudioFinish = true;
                        break;
                    }

                    if (audioOffset >= 0) {//当变成正数的时候，退出
                        break;
                    }
                } else {
                    // 如为正数，则合成过程中先输出audioOffset个字节长度的第一个音频文件数据，
                    firstAudioReadNumber = firstAudioInputStream.read(firstAudioByteBuffer);

                    outputShortArrayLength = firstAudioReadNumber / 2;

                    for (; index < outputShortArrayLength; index++) {//重新设计音量权重
                        resultShort = CommonFunction.GetShort(firstAudioByteBuffer[index * 2],
                                firstAudioByteBuffer[index * 2 + 1], Variable.isBigEnding);

                        outputShortArray[index] = (short) (resultShort * firstAudioWeight);//firstAudioWeight  合成过程中的音量权重
                        LogUtils.w("TAG", "outputShortArray:" + outputShortArray[index]);
                    }

                    audioOffset -= firstAudioReadNumber;

                    if (firstAudioReadNumber < 0) {//获取不到数据
                        firstAudioFinish = true;
                        break;
                    }

                    if (audioOffset <= 0) {//当取完audioOffset个的时候，退出
                        break;
                    }
                }
//转码并保存到 composeAudioOutputStream 对应的文件里
                if (outputShortArrayLength > 0) {
//                    for(;outbyte<outputShortArrayLength;outbyte++){
//                        wavBuffer = CommonFunction.shortToByte(outputShortArray[outbyte]);
//                        composewavOutputStream.write( wavBuffer, 0, wavBuffer.length );
//
//                    }
//                    composewavOutputStream.write( outputShortArray, 0, outputShortArrayLength );


                    int encodedSize = LameUtil.encode(outputShortArray, outputShortArray,
                            outputShortArrayLength, mp3Buffer);

                    if (encodedSize > 0) {
                        composeAudioOutputStream.write(mp3Buffer, 0, encodedSize);
                    }
                }
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (composeAudioInterface != null) {
                        composeAudioInterface.updateComposeProgress(20);
                    }
                }
            });


//两个合成文件其中一个还有数据。
            while (!firstAudioFinish || !secondAudioFinish) {
                index = 0;

                firstAudioReadNumber = firstAudioInputStream.read(firstAudioByteBuffer);
                secondAudioReadNumber = secondAudioInputStream.read(secondAudioByteBuffer);

                int minAudioReadNumber = Math.min(firstAudioReadNumber, secondAudioReadNumber);//获取最小的长度
                int maxAudioReadNumber = Math.max(firstAudioReadNumber, secondAudioReadNumber);//获取最大的长度

                if (firstAudioReadNumber < 0) {//获取不到数据
                    firstAudioFinish = true;
                    secondAudioFinish = true;//默认取第一个录制的时间长度
                }

                if (secondAudioReadNumber < 0) {//获取不到数据
                    secondAudioFinish = true;
                }

                int halfMinAudioReadNumber = minAudioReadNumber / 2;//用最小的文件来合成。

                outputShortArrayLength = maxAudioReadNumber / 2;

                //用最小的文件为标准来合成
                for (; index < halfMinAudioReadNumber; index++) {//根据音量权重，获取数据再求平均值。
                    resultShort = CommonFunction.WeightShort(firstAudioByteBuffer[index * 2],
                            firstAudioByteBuffer[index * 2 + 1], secondAudioByteBuffer[index * 2],
                            secondAudioByteBuffer[index * 2 + 1], firstAudioWeight,
                            secondAudioWeight, Variable.isBigEnding);

                    outputShortArray[index] = resultShort;
                }

                //当两个合成文件不同长度的时候。分开处理。
                if (firstAudioReadNumber != secondAudioReadNumber) {
                    if (firstAudioReadNumber > secondAudioReadNumber) {//第一个较长，只能获取剩下的数据转换
                        for (; index < outputShortArrayLength; index++) {
                            resultShort = CommonFunction.GetShort(firstAudioByteBuffer[index * 2],
                                    firstAudioByteBuffer[index * 2 + 1], Variable.isBigEnding);

                            outputShortArray[index] = (short) (resultShort * firstAudioWeight);
                        }
                    } else {//第二个较长，只能获取剩下的数据转换
                        for (; index < outputShortArrayLength; index++) {
                            resultShort = CommonFunction.GetShort(secondAudioByteBuffer[index * 2],
                                    secondAudioByteBuffer[index * 2 + 1], Variable.isBigEnding);

                            outputShortArray[index] = (short) (resultShort * secondAudioWeight);
                        }
                    }
                }

                //转码并保存到 composeAudioOutputStream 对应的文件里
                if (outputShortArrayLength > 0) {
//                    for(outbyte = 0;outbyte<outputShortArrayLength;outbyte++){
//                        wavBuffer = CommonFunction.shortToByte(outputShortArray[outbyte]);
//                        composewavOutputStream.write( wavBuffer, 0, wavBuffer.length );
//
//                    }

                    int encodedSize = LameUtil.encode(outputShortArray, outputShortArray,
                            outputShortArrayLength, mp3Buffer);

                    if (encodedSize > 0) {
                        composeAudioOutputStream.write(mp3Buffer, 0, encodedSize);
                    }
                }
            }
        } catch (Exception e) {
            LogFunction.error("ComposeAudio异常", e);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (composeAudioInterface != null) {
                        composeAudioInterface.composeFail();
                    }
                }
            });

            return;
        }
        //混音完成，开始转码：
//        AudioEncoder accEncoder =  AudioEncoder.createAccEncoder(composewavFilePath);//转码成acc文件
////        String finalMixPath = MainApplication.RECORD_AUDIO_PATH + "/MixAudioTest.acc";//finalMixPath 得到混音成功后的文件
//        accEncoder.encodeToFile(composeAudioFilePath);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (composeAudioInterface != null) {
                    composeAudioInterface.updateComposeProgress(50);
                }
            }
        });


        try {
            final int flushResult = LameUtil.flush(mp3Buffer);

            if (flushResult > 0) {
                composeAudioOutputStream.write(mp3Buffer, 0, flushResult);
            }
        } catch (Exception e) {
            LogFunction.error("释放ComposeAudio LameUtil异常", e);
        } finally {
            try {
                composeAudioOutputStream.close();
            } catch (Exception e) {
                LogFunction.error("关闭合成输出音频流异常", e);
            }

            LameUtil.close();
        }

        if (deleteSource) {
            FileFunction.DeleteFile(firstAudioFilePath);
            FileFunction.DeleteFile(secondAudioFilePath);
        }

        try {
            firstAudioInputStream.close();
            secondAudioInputStream.close();
            composewavOutputStream.close();//关闭
        } catch (IOException e) {
            LogFunction.error("关闭合成输入音频流异常", e);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (composeAudioInterface != null) {
                    composeAudioInterface.composeSuccess();
                }
            }
        });
    }
}
