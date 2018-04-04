package com.changxiang.vod.module.mediaExtractor.Decode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;


import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.constance.Constant;
import com.changxiang.vod.module.mediaExtractor.Function.CommonFunction;
import com.changxiang.vod.module.mediaExtractor.Function.FileFunction;
import com.changxiang.vod.module.mediaExtractor.Function.LogFunction;
import com.changxiang.vod.module.mediaExtractor.Global.Variable;
import com.changxiang.vod.module.mediaExtractor.Interface.DecodeOperateInterface;
import com.changxiang.vod.module.mediaExtractor.vavi.sound.pcm.resampling.ssrc.SSRC;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static android.provider.MediaStore.Files.FileColumns.MIME_TYPE;


/**
 *解码mp3
 */
public class DecodeEngine {
    private static DecodeEngine instance;

    private DecodeEngine() {
    }

    public static DecodeEngine getInstance() {
        if (instance == null) {
            synchronized (DecodeEngine.class) {
                if (instance == null) {
                    instance = new DecodeEngine();
                }
            }
        }

        return instance;
    }

    public void beginDecodeMusicFile(String musicFileUrl, String decodeFileUrl, int startSecond,
                                     int endSecond,
                                     final DecodeOperateInterface decodeOperateInterface) {
        LogUtils.sysout("开始解码++1111解码后的文件=" + decodeFileUrl);
        Handler handler = new Handler(Looper.getMainLooper());

        final boolean decodeResult =
                decodeMusicFile(musicFileUrl, decodeFileUrl, startSecond, endSecond, handler,
                        decodeOperateInterface);
LogUtils.sysout("解码解析，判断是否完成 decodeResult = "+decodeResult);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (decodeResult) {
                    try {
                        decodeOperateInterface.decodeSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    decodeOperateInterface.decodeFail();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean decodeMusicFile(String musicFileUrl, String decodeFileUrl, int startSecond,
                                    int endSecond,
                                    Handler handler,
                                    DecodeOperateInterface decodeOperateInterface) {
      LogUtils.sysout("开始解码++2222decodeFileUrl=" + decodeFileUrl);
        int sampleRate = 0;//采样率
        int channelCount = 0;//通道数
        long duration = 0;//时长
        String mime = null;

        MediaExtractor mediaExtractor = new MediaExtractor();//解复用器，将audio和video分离
        MediaFormat mediaFormat = null;//媒体数据的格式,音频或视频。
        MediaCodec mediaCodec = null;//解码器

        try {
            mediaExtractor.setDataSource(musicFileUrl);
        } catch (Exception e) {
            LogUtils.sysout("设置解码音频文件路径错误~~~~~~~~~");
//            LogFunction.error("设置解码音频文件路径错误", e);
            return false;
        }
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            MediaFormat format = mediaExtractor.getTrackFormat(i);
            mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("audio/")) {//获取音频
                mediaExtractor.selectTrack(i);
                mediaFormat = format;
                break;
            }
        }
        if (mediaFormat == null) {
            LogUtils.sysout("not a valid file with audio track..");
//            extractor.release();
            return false;
        }
//        mediaFormat = mediaExtractor.getTrackFormat(0);
        sampleRate = mediaFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE) ?
                mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE) : 48000;
        channelCount = mediaFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT) ?
                mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT) : 2;
        duration = mediaFormat.containsKey(MediaFormat.KEY_DURATION) ? mediaFormat.getLong
                (MediaFormat.KEY_DURATION)
                : 0;
        mime = mediaFormat.containsKey(MediaFormat.KEY_MIME) ? mediaFormat.getString(MediaFormat
                .KEY_MIME) : "";

        LogUtils.sysout("歌曲信息" + "Track info: mime:" + mime + " 采样率sampleRate:" + sampleRate + " 通道数:channels:" +
                channelCount + " 时长 duration:" + duration);

        if (CommonFunction.isEmpty(mime) || !mime.startsWith("audio/")) {
            LogUtils.sysout("解码文件不是音频文件~~~~~~~~~");
            LogFunction.error("解码文件不是音频文件", "mime:" + mime);
            return false;
        }
        LogUtils.sysout("解码文件不是音频文件,继续走下去。");
        if (mime.equals("audio/ffmpeg")) {
            mime = "audio/mpeg";
            mediaFormat.setString(MediaFormat.KEY_MIME, mime);
        }
//        if (mime.equals("audio/mpeg")) {
//            mime = "audio/mp4a-latm";
//            mediaFormat.setString(MediaFormat.KEY_MIME, mime);
//        }
//        try{
//            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
//            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
//            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
//            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        try {
            mediaCodec = MediaCodec.createDecoderByType(mime);
//            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            LogUtils.sysout("99999999999999999999vivo出错~~~");
            mediaCodec.configure(mediaFormat, null, null, 0);
//            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        } catch (Exception e) {
            LogUtils.sysout("解码器configure出错~~~~~~~~~");
            LogFunction.error("解码器configure出错", e);
            return false;
        }
/**
 * 　getDecodeData方法是此次的进行解码和裁剪的核心，
 * 方法的传入参数中mediaExtractor，mediaCodec用以实际控制处理背景音乐的音频数据，
 * decodeFileUrl用以指明解码和裁剪后的PCM文件的存储地址，
 * sampleRate，channelCount分别用以指明背景音乐的采样率，声道数，
 * startSecond用以指明裁剪背景音乐的开始时间， 目前功能中默认为0，
 * endSecond用以指明裁剪背景音乐的结束时间，数值大小由录音时长直接决定。
 */
        LogUtils.sysout("开始解码++3333");
        getDecodeData(mediaExtractor, mediaCodec, decodeFileUrl, sampleRate, channelCount,
                startSecond,
                endSecond, handler, decodeOperateInterface);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getDecodeData(MediaExtractor mediaExtractor, MediaCodec mediaCodec, String
            decodeFileUrl, int
                                       sampleRate,
                               int channelCount, int startSecond, int endSecond,
                               Handler handler,
                               final DecodeOperateInterface decodeOperateInterface) {
        LogUtils.sysout("开始解码++4444");
        boolean decodeInputEnd = false;
        boolean decodeOutputEnd = false;

        int sampleDataSize;
        int inputBufferIndex;
        int outputBufferIndex;
        int byteNumber;

        long decodeNoticeTime = System.currentTimeMillis();
        long decodeTime;
        long presentationTimeUs = 0;

        final long timeOutUs = 100;
        final long startMicroseconds = startSecond * 1000 * 1000;
        final long endMicroseconds = endSecond * 1000 * 1000;

        ByteBuffer[] inputBuffers;
        ByteBuffer[] outputBuffers;

        ByteBuffer sourceBuffer;
        ByteBuffer targetBuffer;
        MediaFormat outputFormat = null;
//        MediaFormat
//可能出错断点到这里
        try {
            outputFormat = mediaCodec.getOutputFormat();
        } catch (Exception e) {
            outputFormat = MediaFormat.createVideoFormat(MIME_TYPE, 640, 480);
            e.printStackTrace();
        }


        MediaCodec.BufferInfo bufferInfo;

        byteNumber = (outputFormat.containsKey("bit-width") ? outputFormat.getInteger("bit-width") : 0) / 8;

        mediaCodec.start();

        inputBuffers = mediaCodec.getInputBuffers();
        outputBuffers = mediaCodec.getOutputBuffers();

        mediaExtractor.selectTrack(0);

        bufferInfo = new MediaCodec.BufferInfo();
        LogUtils.sysout("开始解码++555");
        BufferedOutputStream bufferedOutputStream = FileFunction
                .GetBufferedOutputStreamFromFile(decodeFileUrl);

        while (!decodeOutputEnd) {
            if (decodeInputEnd) {
                return;
            }

            decodeTime = System.currentTimeMillis();

            if (decodeTime - decodeNoticeTime > Constant.OneSecond) {//每1s获取一次解码进度
                final int decodeProgress =
                        (int) ((presentationTimeUs - startMicroseconds) * Constant
                                .NormalMaxProgress /
                                endMicroseconds);

                LogUtils.sysout("进度：decodeProgress"+decodeProgress);
                if (decodeProgress > 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            decodeOperateInterface.updateDecodeProgress(decodeProgress);
                        }
                    });
                }

                decodeNoticeTime = decodeTime;
            }

            try {
                inputBufferIndex = mediaCodec.dequeueInputBuffer(timeOutUs);

                if (inputBufferIndex >= 0) {
                    sourceBuffer = inputBuffers[inputBufferIndex];

                    sampleDataSize = mediaExtractor.readSampleData(sourceBuffer, 0);

                    if (sampleDataSize < 0) {
                        decodeInputEnd = true;
                        sampleDataSize = 0;
                    } else {
                        presentationTimeUs = mediaExtractor.getSampleTime();
                    }

                    mediaCodec.queueInputBuffer(inputBufferIndex, 0, sampleDataSize,
                            presentationTimeUs,
                            decodeInputEnd ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0);

                    if (!decodeInputEnd) {
                        mediaExtractor.advance();
                    }
                } else {
                    LogFunction.error("inputBufferIndex", "" + inputBufferIndex);
                }

                // decode to PCM and push it to the AudioTrack player
                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, timeOutUs);

                if (outputBufferIndex < 0) {
                    switch (outputBufferIndex) {
                        case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                            outputBuffers = mediaCodec.getOutputBuffers();
                            LogFunction.error("MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED",
                                    "[AudioDecoder]output buffers have changed.");
                            break;
                        case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                            outputFormat = mediaCodec.getOutputFormat();

                            sampleRate = outputFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE) ?
                                    outputFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE) :
                                    sampleRate;
                            channelCount = outputFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT) ?
                                    outputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT) :
                                    channelCount;
                            byteNumber = (outputFormat.containsKey("bit-width") ? outputFormat
                                    .getInteger
                                            ("bit-width") : 0) / 8;

                            LogFunction.error("MediaCodec.INFO_OUTPUT_FORMAT_CHANGED",
                                    "[AudioDecoder]output format has changed to " +
                                            mediaCodec.getOutputFormat());
                            break;
                        default:
                            LogFunction.error("error",
                                    "[AudioDecoder] dequeueOutputBuffer returned " +
                                            outputBufferIndex);
                            break;
                    }
                    continue;
                }

                targetBuffer = outputBuffers[outputBufferIndex];

                byte[] sourceByteArray = new byte[bufferInfo.size];

                targetBuffer.get(sourceByteArray);
                targetBuffer.clear();

                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);

                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    decodeOutputEnd = true;
                }

                if (sourceByteArray.length > 0 && bufferedOutputStream != null) {
                    if (presentationTimeUs < startMicroseconds) {
                        continue;
                    }

                    byte[] convertByteNumberByteArray = ConvertByteNumber(byteNumber, Constant
                                    .RecordByteNumber,
                            sourceByteArray);

                    byte[] resultByteArray =
                            ConvertChannelNumber(channelCount, Constant.RecordChannelNumber,
                                    Constant.RecordByteNumber,
                                    convertByteNumberByteArray);

                    try {
                        bufferedOutputStream.write(resultByteArray);
                    } catch (Exception e) {
                        LogFunction.error("输出解压音频数据异常", e);
                    }
                }

                if (presentationTimeUs > endMicroseconds) {//处理到endMicroseconds时主动退出，不在解析下去：
                    break;
                }
            } catch (Exception e) {
                LogFunction.error("getDecodeData异常", e);
            }
        }
        LogUtils.sysout("开始解码++66666666");
        if (bufferedOutputStream != null) {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                LogFunction.error("关闭bufferedOutputStream异常", e);
            }
        }
        LogUtils.sysout("开始解码++77777777777");
        if (sampleRate != Constant.RecordSampleRate) {
            Resample(sampleRate, decodeFileUrl);
        }
        LogUtils.sysout("开始解码++8888888888");
        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
        }
        LogUtils.sysout("开始解码++999999999999");
        if (mediaExtractor != null) {
            mediaExtractor.release();
        }
        LogUtils.sysout("结束解码++000000000000");
    }

    private static void Resample(int sampleRate, String decodeFileUrl) {
        String newDecodeFileUrl = decodeFileUrl + "new";

        try {
            FileInputStream fileInputStream =
                    new FileInputStream(new File(decodeFileUrl));
            FileOutputStream fileOutputStream =
                    new FileOutputStream(new File(newDecodeFileUrl));

            new SSRC(fileInputStream, fileOutputStream, sampleRate, Constant.RecordSampleRate,
                    Constant.RecordByteNumber, Constant.RecordByteNumber, 1, Integer.MAX_VALUE,
                    0, 0, true);

            fileInputStream.close();
            fileOutputStream.close();

            FileFunction.RenameFile(newDecodeFileUrl, decodeFileUrl);
        } catch (IOException e) {
            LogFunction.error("关闭bufferedOutputStream异常", e);
        }
    }
//    ConvertByteNumber方法是通过处理音频数据以保证解码后音频文件和录音文件采样点字节数相同。

    //    ConvertByteNumber方法的参数中
// sourceByteNumber 代表背景音乐文件采样点字节数，
// outputByteNumber 代表录音文件采样点字节数，
// 两者如果相同就不处理，
// 不相同则根据背景音乐文件采样点字节数进行不同的处理，
// 本方法只对单字节存储和双字节存储进行了处理，
// 欢迎在各位Github上填充其他采样点字节数的处理方法，
    private static byte[] ConvertByteNumber(int sourceByteNumber, int outputByteNumber, byte[]
            sourceByteArray) {
        if (sourceByteNumber == outputByteNumber) {// 两者如果相同就不处理，
            return sourceByteArray;
        }

        int sourceByteArrayLength = sourceByteArray.length;

        byte[] byteArray;

        switch (sourceByteNumber) {//sourceByteNumber（背景音乐）不相同则根据背景音乐文件采样点字节数进行不同的处理，
            case 1:
                switch (outputByteNumber) {
                    case 2:
                        byteArray = new byte[sourceByteArrayLength * 2];

                        byte resultByte[];

                        for (int index = 0; index < sourceByteArrayLength; index += 1) {
                            resultByte = CommonFunction.GetBytes((short) (sourceByteArray[index]
                                    * 256), Variable
                                    .isBigEnding);

                            byteArray[2 * index] = resultByte[0];
                            byteArray[2 * index + 1] = resultByte[1];
                        }

                        return byteArray;
                }
                break;
            case 2:
                switch (outputByteNumber) {
                    case 1:
                        int outputByteArrayLength = sourceByteArrayLength / 2;

                        byteArray = new byte[outputByteArrayLength];

                        for (int index = 0; index < outputByteArrayLength; index += 1) {
                            byteArray[index] = (byte) (CommonFunction.GetShort(sourceByteArray[2
                                            * index],
                                    sourceByteArray[2 * index + 1], Variable.isBigEnding) / 256);
                        }

                        return byteArray;
                }
                break;
        }

        return sourceByteArray;
    }

    //ConvertChannelNumber方法是通过处理音频数据以保证解码后音频文件和录音文件声道数相同。
    private static byte[] ConvertChannelNumber(int sourceChannelCount, int outputChannelCount,
                                               int byteNumber,//采样率
                                               byte[] sourceByteArray) {
        if (sourceChannelCount == outputChannelCount) {
            return sourceByteArray;
        }

        switch (byteNumber) {
            case 1:
            case 2:
                break;
            default:
                return sourceByteArray;
        }

        int sourceByteArrayLength = sourceByteArray.length;//长度

        byte[] byteArray;

        switch (sourceChannelCount) {//来源通道数
            case 1://来源通道数为1
                switch (outputChannelCount) {//
                    case 2://需要转化成通道数为2
                        byteArray = new byte[sourceByteArrayLength * 2];

                        byte firstByte;
                        byte secondByte;

                        switch (byteNumber) {
                            case 1:
                                for (int index = 0; index < sourceByteArrayLength; index += 1) {
                                    firstByte = sourceByteArray[index];

                                    byteArray[2 * index] = firstByte;
                                    byteArray[2 * index + 1] = firstByte;
                                }
                                break;
                            case 2:
                                for (int index = 0; index < sourceByteArrayLength; index += 2) {
                                    firstByte = sourceByteArray[index];
                                    secondByte = sourceByteArray[index + 1];

                                    byteArray[2 * index] = firstByte;
                                    byteArray[2 * index + 1] = secondByte;
                                    byteArray[2 * index + 2] = firstByte;
                                    byteArray[2 * index + 3] = secondByte;
                                }
                                break;
                        }

                        return byteArray;
                }
                break;
            case 2:
                switch (outputChannelCount) {
                    case 1:
                        int outputByteArrayLength = sourceByteArrayLength / 2;

                        byteArray = new byte[outputByteArrayLength];

                        switch (byteNumber) {
                            case 1:
                                for (int index = 0; index < outputByteArrayLength; index += 2) {
                                    short averageNumber =
                                            (short) ((short) sourceByteArray[2 * index] + (short)
                                                    sourceByteArray[2 *
                                                            index + 1]);
                                    byteArray[index] = (byte) (averageNumber >> 1);
                                }
                                break;
                            case 2:
                                for (int index = 0; index < outputByteArrayLength; index += 2) {
                                    byte resultByte[] = CommonFunction.AverageShortByteArray
                                            (sourceByteArray[2 * index],
                                                    sourceByteArray[2 * index + 1],
                                                    sourceByteArray[2 *
                                                            index + 2],
                                                    sourceByteArray[2 * index + 3], Variable
                                                            .isBigEnding);

                                    byteArray[index] = resultByte[0];
                                    byteArray[index + 1] = resultByte[1];
                                }
                                break;
                        }

                        return byteArray;
                }
                break;
        }

        return sourceByteArray;
    }
}
