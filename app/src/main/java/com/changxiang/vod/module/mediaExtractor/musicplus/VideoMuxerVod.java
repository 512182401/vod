package com.changxiang.vod.module.mediaExtractor.musicplus;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;

import com.changxiang.vod.common.utils.FileUtil;
import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.mediaExtractor.Function.CommonFunction;
import com.changxiang.vod.module.mediaExtractor.Global.Variable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 视频混合音频（）
 *
 * @author Darcy
 */
public abstract class VideoMuxerVod {

    private static final String TAG = "VideoMuxer";

    String mOutputVideo;

    private VideoMuxerVod(String outputVideo) {
        this.mOutputVideo = outputVideo;
    }

    public final static VideoMuxerVod createVideoMuxer(String outputVideo) {//合成的文件路i经
        return new Mp4Muxer(outputVideo);
    }

    /**
     * mix raw audio into video
     * offset:偏移，当为1000毫秒的时候，表示一秒之后再播放录音，所以录音前面应该填充1秒的
     *
     * @param videoFile
     * @param rawAudioFile
     * @param includeAudioInVideo
     */
    public abstract void mixRawAudio(File videoFile, File audioFile, File rawAudioFile, int offset, int PreludeValue, boolean includeAudioInVideo, boolean isRecorderMp3, long allDuration);
    //videoFile 录制的源文件
    //rawAudioFile 未加工的音频文件
    //includeAudioInVideo 是否包含视频音轨
//    isRecorderMp3 表示录制的为MP3
//

    /**
     * use android sdk MediaMuxer
     *
     * @author Darcy
     * @version API >= 18
     */
    private static class Mp4Muxer extends VideoMuxerVod {

        private final static String AUDIO_MIME = "audio/mp4a-latm";
        private final static long audioBytesPerSample = 44100 * 16 / 8;//用44.10khz的采样频率,16位的精度存储,则录制1秒钟的单声道所占的空间大小。

        private long rawAudioSize;
        private long duration;
        private int offsetnum = 0;//以伴奏为基准，当大于0的时候，表示录音需要加长，小于0，则表示录音需要裁剪掉开头部分。
        private int PreludeValuenum = 0;//跳过前奏所占字节长度。
        private boolean getbase = true;//是否获取原本的录音,主要结合录音偏移参数来判断

        public Mp4Muxer(String outputVideo) {
            super(outputVideo);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void mixRawAudio(File videoFile, File audioFile, File rawAudioFile, int offset, int PreludeValue, boolean includeAudioInVideo, boolean isRecorderMp3, long allDuration) {
            LogUtils.sysout("视频数据videoFile=" + videoFile + "音频数据：rawAudioFile=" + rawAudioFile);
//			rawAudioFile 解码之后的音频文件
//			videoFile 录制的视频文件
//			includeAudioInVideo 是否包含视频音轨
            final String videoFilePath = videoFile.getAbsolutePath();
            duration = allDuration;
            MediaMuxer videoMuxer = null;
            float audioWeight = 0.2f;//伴奏的音量权重0.3f
            float rawAudioWeight = 0.0f;//录制的音量权重1.8f
            short resultShort;//用于音量权重处理
            short[] outputShortArray;
            LogUtils.sysout("++++++++++++++++录制时长" + duration);
            long longall = (duration / 1000 + 1) * 8 * audioBytesPerSample;//录制时长
            int count = 0;
            this.offsetnum = 96000 * offset * 2;//1秒的长度为：96000
            this.PreludeValuenum = 96000 * PreludeValue * 2;//1秒的长度为：96000
            if (offset != 0) {
                getbase = false;
            }
            try {

                final String outputVideo = mOutputVideo;//outputVideo 合成之后的文件
                videoMuxer = new MediaMuxer(outputVideo, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

                MediaFormat videoFormat = null;
                MediaExtractor videoExtractor = new MediaExtractor();
                videoExtractor.setDataSource(videoFilePath);//设置video路径（分离出来的video）
                //判断是否为视频文件
                for (int i = 0; i < videoExtractor.getTrackCount(); i++) {
                    MediaFormat format = videoExtractor.getTrackFormat(i);
                    String mime = format.getString(MediaFormat.KEY_MIME);
                    if (mime.startsWith("video/")) {
                        videoExtractor.selectTrack(i);
                        videoFormat = format;
                        break;
                    }
                }
                LogUtils.sysout("判断是否为视频文件");
                int videoTrackIndex = videoMuxer.addTrack(videoFormat);
                int audioTrackIndex = 0;

                //extract and decode audio
                FileInputStream fisExtractAudio = null;
                //获取视频音轨
                if (includeAudioInVideo) {//需要包含视频音轨，故，应该分离保存视频音轨

//                    AndroidAudioDecoder audioDecoder = new AndroidAudioDecoder( videoFilePath );
                    //设置解码保存之后的文件
//                    String extractAudioFilePath = MainApplication.RECORD_AUDIO_PATH + "/" + System.currentTimeMillis();
//                    audioDecoder.decodeToFile( extractAudioFilePath );
//                    File extractAudioFile = new File( extractAudioFilePath );
                    File extractAudioFile = new File(audioFile.getAbsolutePath());
                    fisExtractAudio = new FileInputStream(extractAudioFile);
                }
                LogUtils.sysout("解码video文件");
                //获取MP3解码文件流,用于
//				fisMixAudio 音频流
                //TODO 需要判断是否存在该文件：
                if (FileUtil.fileIsExists(rawAudioFile.getAbsolutePath())) {

                } else {
                    videoMuxer = null;
                    LogUtils.sysout("3333333333333 rawAudioFile.getAbsolutePath()" + rawAudioFile.getAbsolutePath());
                    LogUtils.sysout("rawAudioFile录音文件不存在！");
                    return;
                }
                FileInputStream fisMixAudio = new FileInputStream(rawAudioFile);

                boolean readExtractAudioEOS = includeAudioInVideo ? false : true;
                boolean readMixAudioEOS = false;
                byte[] extractAudioBuffer = new byte[4096 * 4];//视频音轨的区间
                byte[] mixAudioBuffer = new byte[4096 * 4];//音频的区间
                byte[] RecorderAudioBuffer = new byte[2048 * 4];//为了通道数准备
                byte[] videoAudioBuffer = new byte[2048 * 4];//为了通道数准备
                byte[] changdata = new byte[2];//为了byte和short转换使用
                outputShortArray = new short[2048 * 4];
//                isRecorderMp3
                int extractAudioReadCount = 0;//extractAudioReadCount 音频流数据长度(mp4的音轨)
                int mixAudioReadCount = 0;//mixAudioReadCount 音频流数据长度（将要合成的，即录制的两倍）
                int RecorderAudioReadCount = 0;//RecorderAudioReadCount 音频流数据长度（录制的）
                int videoAudioReadCount = 0;//RecorderAudioReadCount 音频流数据长度（录制的）

                final MultiAudioMixer audioMixer = MultiAudioMixer.createAudioMixer();
                final byte[][] twoAudioBytes = new byte[2][];

                final MediaCodec audioEncoder = createACCAudioDecoder();//
                audioEncoder.start();

                ByteBuffer[] audioInputBuffers = audioEncoder.getInputBuffers();
                ByteBuffer[] audioOutputBuffers = audioEncoder.getOutputBuffers();
                boolean sawInputEOS = false;
                boolean sawOutputEOS = false;
                long audioTimeUs = 0;
                BufferInfo outBufferInfo = new BufferInfo();

                int inputBufIndex, outputBufIndex;
                /**
                 * 首次必须走的程序：
                 */
                /**
                 * queueInputBuffer 和 dequeueInputBuffer 是一对方法，两个要在一起使用
                 * 首先，这一对函数的应用场合是对输入的数据流进行编码或者解码处理的时候，你会通过各种方法获得一个ByteBuffer的数组，这些数据就是准备处理的数据。
                 你要通过自己的方法找到你要处理的部分，然后调用dequeueInputBuffer方法提取出要处理的部分（也就是一个ByteBuffer数据流），把这一部分放到缓存区。
                 接下来就是你自己对于这个数据流的处理了。
                 然后在处理完毕之后，一定要调用queueInputBuffer把这个ByteBuffer放回到队列中，这样才能正确释放缓存区。
                 对于输出的数据流，同样也有一对这样的函数，叫做queueOutputBuffer和 dequeueOutputBuffer ，作用类似哦。
                 */
                while (!sawOutputEOS) {
                    if (!sawInputEOS) {  //将音轨放入
                        //判断相关文件是否存在
                        inputBufIndex = audioEncoder.dequeueInputBuffer(4096 * 4);//准备处理的数据（每10000个长度处理）
                        count++;//用于测试，测试录音文件
//                        if (count % 10 == 0) {
//                            LogUtils.sysout("准备处理的数据长度 count*inputBufIndex=" + count);
//                        }
                        if (inputBufIndex >= 0) {
                            ByteBuffer inputBuffer = audioInputBuffers[inputBufIndex];
                            inputBuffer.clear();

                            int bufferSize = inputBuffer.remaining();
                            if (bufferSize != extractAudioBuffer.length) {
                                extractAudioBuffer = new byte[bufferSize];
                                mixAudioBuffer = new byte[bufferSize];
                                RecorderAudioBuffer = new byte[bufferSize / 2];
                                videoAudioBuffer = new byte[bufferSize / 2];
                            }


                            if (!readExtractAudioEOS) {//需要添加视频音轨，判断视频音轨是否存在
                                while (PreludeValuenum > 0) {//有跳过前奏时长,只需要砍掉伴奏的前段数据
                                    extractAudioReadCount = fisExtractAudio.read(extractAudioBuffer);//extractAudioReadCount 音频流数据长度(mp4的音轨)
                                    PreludeValuenum -= extractAudioReadCount;
                                }
                                extractAudioReadCount = fisExtractAudio.read(extractAudioBuffer);//extractAudioReadCount 音频流数据长度(mp4的音轨)


                                //TODO 添加音量权重（伴奏的）
                                if (extractAudioReadCount != -1 && audioWeight > 0) {//有数据
//                                if (false) {//有数据
                                    for (int index = 0; index < extractAudioReadCount / 2; index++) {//
                                        try {
                                            resultShort = CommonFunction.GetShort(extractAudioBuffer[index * 2],
                                                    extractAudioBuffer[index * 2 + 1], Variable.isBigEnding);
                                            //2：权重处理
                                            outputShortArray[index] = (short) (resultShort * audioWeight);
                                            //3：将 short 转换成 Byte
                                            changdata = CommonFunction.shortToByte(outputShortArray[index]);
                                            extractAudioBuffer[2 * index] = changdata[0];
                                            extractAudioBuffer[2 * index + 1] = changdata[1];

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                if (extractAudioReadCount == -1) {
                                    readExtractAudioEOS = true;
                                }
                                longall -= extractAudioReadCount;
                                if (longall <= 0) {
                                    readExtractAudioEOS = true;
                                }
                            }

                            if (!readMixAudioEOS) {//判断合成音频是否存在
                                //				fisMixAudio 音频流
                                //mixAudioReadCount每一次获取到的数据长度
                                if (isRecorderMp3) {//录制的为mp3
                                    if (!getbase) {//判断是否使用录音数据
//                                    if(false) {//判断是否使用录音数据 f否：
                                        if (this.offsetnum > 0) {////以伴奏为基准，当大于0的时候，表示录音需要加长，小于0，则表示录音需要裁剪掉开头部分。
                                            offsetnum -= extractAudioReadCount;
                                            if (offsetnum <= 0) {
                                                getbase = true;
                                            }
                                            mixAudioReadCount = extractAudioReadCount;
                                            mixAudioBuffer = extractAudioBuffer;
//                                            mixAudioReadCount
                                        } else {
                                            while (offsetnum < 0) {
                                                RecorderAudioReadCount = fisMixAudio.read(RecorderAudioBuffer);
                                                offsetnum += extractAudioReadCount;
                                            }

                                            if (offsetnum >= 0) {
                                                getbase = true;
                                            }
                                            mixAudioReadCount = extractAudioReadCount;
                                            mixAudioBuffer = extractAudioBuffer;
                                        }
                                    } else {//使用原始录音数据
//                                        RecorderAudioReadCount = fisMixAudio.read(RecorderAudioBuffer);//只需要获取一半的长度，所以需要*2；//RecorderAudioReadCount 音频流数据长度（录制）

                                        RecorderAudioReadCount = fisMixAudio.read(RecorderAudioBuffer);//只需要获取一半的长度，所以需要*2；//RecorderAudioReadCount 音频流数据长度（录制）

//                                    LogUtils.sysout( "读取数据==" + RecorderAudioReadCount );
                                        byte firstByte;
                                        byte secondByte;

                                        if (RecorderAudioReadCount != -1) {
                                            mixAudioReadCount = RecorderAudioReadCount * 2;
//                                        mixAudioBuffer = new byte[mixAudioReadCount];
                                            for (int index = 0; index < RecorderAudioReadCount; index += 2) {//单通道转双通道
//                                            mixAudioBuffer[2 * t] = RecorderAudioBuffer[t];
//                                            mixAudioBuffer[2 * t + 1] = RecorderAudioBuffer[t];

                                                firstByte = RecorderAudioBuffer[index];
                                                secondByte = RecorderAudioBuffer[index + 1];

                                                mixAudioBuffer[2 * index] = firstByte;
                                                mixAudioBuffer[2 * index + 1] = secondByte;
                                                mixAudioBuffer[2 * index + 2] = firstByte;
                                                mixAudioBuffer[2 * index + 3] = secondByte;
                                                //修改音量权重：1：Byte 转换成 short
                                                if (rawAudioWeight > 0) {
                                                    short num = 5000;
                                                    for (int o = 0; o < 2; o++) {
                                                        if (o == 0) {
                                                            resultShort = CommonFunction.GetShort(mixAudioBuffer[index * 2],
                                                                    mixAudioBuffer[index * 2 + 1], Variable.isBigEnding);
                                                            //2：权重处理
                                                            outputShortArray[index] = (short) (resultShort * rawAudioWeight);
                                                            short ss = outputShortArray[index];

                                                            if (outputShortArray[index] >= num) {
                                                                outputShortArray[index] = num;
                                                            } else if (outputShortArray[index] <= -num) {
                                                                outputShortArray[index] = (short) -num;
                                                            }
                                                            changdata = CommonFunction.shortToByte(outputShortArray[index]);
                                                            //3：将 short 转换成 Byte
                                                            mixAudioBuffer[2 * index] = changdata[0];
                                                            mixAudioBuffer[2 * index + 1] = changdata[1];
                                                        } else {
                                                            resultShort = CommonFunction.GetShort(mixAudioBuffer[index * 2 + 2],
                                                                    mixAudioBuffer[index * 2 + 3], Variable.isBigEnding);
                                                            //2：权重处理
                                                            outputShortArray[index] = (short) (resultShort * rawAudioWeight);
//                                                        LogUtils.w("test","outputShortArray[index]："+outputShortArray[index]);
                                                            if (outputShortArray[index] >= num) {
                                                                outputShortArray[index] = num;
                                                            } else if (outputShortArray[index] <= -num) {
                                                                outputShortArray[index] = (short) -num;
                                                            }

                                                            changdata = CommonFunction.shortToByte(outputShortArray[index]);
                                                            //3：将 short 转换成 Byte
                                                        /*if (changdata[0]>=30){
                                                            changdata[0]=30;
//                                                            LogUtils.w("test","changdata0："+changdata[0]+",changdata1:"+changdata[1]);
                                                        }else if (changdata[0]<=-30){
                                                            changdata[0]=-30;
//                                                            LogUtils.w("test","changdata0："+changdata[0]+",changdata1:"+changdata[1]);
                                                        }
                                                        if (changdata[1]>=30){
                                                            changdata[1]=30;
                                                            LogUtils.w("test","changdata0："+changdata[0]+",changdata1:"+changdata[1]);
                                                        }else if (changdata[1]<=-30){
                                                            changdata[1]=-30;
                                                            LogUtils.w("test","changdata0："+changdata[0]+",changdata1:"+changdata[1]);
                                                        }
*/
                                                            mixAudioBuffer[2 * index + 2] = changdata[0];
                                                            mixAudioBuffer[2 * index + 3] = changdata[1];
//                                                        LogUtils.w("test","changdata0："+changdata[0]+",changdata1:"+changdata[1]);
                                                        }
                                                    }
                                                }
                                            }


                                        } else {
                                            mixAudioReadCount = RecorderAudioReadCount;
                                            readMixAudioEOS = true;
                                        }
                                    }
                                } else {
                                    mixAudioReadCount = fisMixAudio.read(mixAudioBuffer);
                                    LogUtils.sysout("读取数据==" + RecorderAudioReadCount);
                                }
//                                mixAudioReadCount = fisMixAudio.read( mixAudioBuffer );
                                if (mixAudioReadCount == -1) {
                                    readMixAudioEOS = true;

                                    LogUtils.sysout("准备处理的数据长度 2222222222count*inputBufIndex=" + count);
                                    //TODO 2017-03-10添加：
//                                        LogUtils.sysout("++++++录音数据已经结束++++++++");
//                                        sawInputEOS = true;
                                }
                            }

                            if (readExtractAudioEOS && readMixAudioEOS) {//不需要添加视频音轨，且不存在合成音频，则直接合成
                                //调用queueInputBuffer把这个ByteBuffer放回到队列中，这样才能正确释放缓存区
                                //第一个参数表示
                                audioEncoder.queueInputBuffer(inputBufIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                                sawInputEOS = true;
                            } else {
//                                if (count % 10 == 0) {
//                                    LogUtils.sysout("混合两个以上的音频 count*inputBufIndex=" + count);
//                                }

                                byte[] mixAudioBytes;
                                if (!readExtractAudioEOS && !readMixAudioEOS) {
                                    //需要提交视频音轨，并且有需要合成的音频
                                    if (extractAudioReadCount == mixAudioReadCount) {
                                        //当视频音轨和音频时常相同
                                        twoAudioBytes[0] = extractAudioBuffer;
                                        twoAudioBytes[1] = mixAudioBuffer;
                                    } else if (extractAudioReadCount > mixAudioReadCount) {
                                        //当视频音轨时长比音频时长长
                                        twoAudioBytes[0] = extractAudioBuffer;
//										   Arrays.fill 填充数据
                                        Arrays.fill(mixAudioBuffer, mixAudioReadCount - 1, bufferSize, (byte) 0);
                                    } else {
                                        //当视频音轨时长比音频时长短
//										   Arrays.fill 填充数据
                                        Arrays.fill(extractAudioBuffer, extractAudioReadCount - 1, bufferSize, (byte) 0);
                                    }
                                    //混合两个以上的音频
                                    mixAudioBytes = audioMixer.mixRawAudioBytes(twoAudioBytes);
                                    if (mixAudioBytes == null) {//混合失败
//                                        DLog.e(TAG, "mix audio : null");
                                        LogUtils.sysout(TAG + "mix audio : null");
                                    }
                                    inputBuffer.put(mixAudioBytes);
                                    rawAudioSize += mixAudioBytes.length;
                                    //  //需要提交视频音轨，并且有需要合成的音频
                                    audioEncoder.queueInputBuffer(inputBufIndex, 0, mixAudioBytes.length, audioTimeUs, 0);
                                } else if (!readExtractAudioEOS && readMixAudioEOS) {
                                    //需要提交视频音轨，但没有需要合成的音频
                                    inputBuffer.put(extractAudioBuffer, 0, extractAudioReadCount);
                                    rawAudioSize += extractAudioReadCount;
                                    audioEncoder.queueInputBuffer(inputBufIndex, 0, extractAudioReadCount, audioTimeUs, 0);
                                } else {  //不需要提交视频音轨，也没有需要合成的音频
                                    inputBuffer.put(mixAudioBuffer, 0, mixAudioReadCount);
                                    rawAudioSize += mixAudioReadCount;
                                    audioEncoder.queueInputBuffer(inputBufIndex, 0, mixAudioReadCount, audioTimeUs, 0);
                                }
//                                if(audioTimeUs>=duration){//
//                                    sawInputEOS = true;
//                                }

                                //获取微妙进度条：audioTimeUs
                                // 一秒==1000000微妙
                                //rawAudioSize / 2.0 表示双声道转单声道
                                //audioBytesPerSample：一秒钟所占的数据空间。
                                audioTimeUs = (long) (1000000 * (rawAudioSize / 2.0) / audioBytesPerSample);

                            }
                        }
                    }
//                    LogUtils.sysout( "outputBufIndex==" + "1123" );
                    outputBufIndex = audioEncoder.dequeueOutputBuffer(outBufferInfo, 4096 * 4);
                    //获得录制时间的数据长度：

                    longall -= 4096 * 4;
                    if (longall <= 0) {
//                        outputBufIndex = 0;
                    }

                    if (outputBufIndex >= 0) {

                        // Simply ignore codec config buffers.
                        if ((outBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
//                            DLog.i(TAG, "audio encoder: codec config buffer");
                            LogUtils.sysout(TAG + "audio encoder: codec config buffer");
                            audioEncoder.releaseOutputBuffer(outputBufIndex, false);
                            continue;
                        }

                        if (outBufferInfo.size != 0) {
                            ByteBuffer outBuffer = audioOutputBuffers[outputBufIndex];
                            outBuffer.position(outBufferInfo.offset);
                            outBuffer.limit(outBufferInfo.offset + outBufferInfo.size);
//                            LogUtils.sysout(TAG + String.format(" writing audio sample : size=%s , presentationTimeUs=%s", outBufferInfo.size, outBufferInfo.presentationTimeUs));
                            if (lastAudioPresentationTimeUs < outBufferInfo.presentationTimeUs) {
                                videoMuxer.writeSampleData(audioTrackIndex, outBuffer, outBufferInfo);
                                lastAudioPresentationTimeUs = outBufferInfo.presentationTimeUs;
                            } else {
//                                DLog.e(TAG, "error sample! its presentationTimeUs should not lower than before.");
//                                LogUtils.sysout(TAG + "error sample! its presentationTimeUs should not lower than before.");
                            }
                        }

                        audioEncoder.releaseOutputBuffer(outputBufIndex, false);

                        if ((outBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                            sawOutputEOS = true;
                        }
                    } else if (outputBufIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
//                        LogUtils.sysout( "5555555555555==" + "1123" );
                        audioOutputBuffers = audioEncoder.getOutputBuffers();
                    } else if (outputBufIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
//                        LogUtils.sysout( "6666666666666666==" + "1123" );
                        MediaFormat audioFormat = audioEncoder.getOutputFormat();
                        audioTrackIndex = videoMuxer.addTrack(audioFormat);
                        videoMuxer.start(); //start muxer
                    }
                }

//                LogUtils.sysout( "7777777777777777==" + "1123" );
                if (fisExtractAudio != null) {
                    fisExtractAudio.close();
                }
//                LogUtils.sysout( "88888888888888888888888==" + "1123" );
                fisMixAudio.close();//fisMixAudio 音频流
                audioEncoder.stop();
                audioEncoder.release();

                //mix video
                boolean videoMuxDone = false;
                // 压缩帧大小 < 原始图片大小
                int videoWidth = videoFormat.getInteger(MediaFormat.KEY_WIDTH);
                int videoHeight = videoFormat.getInteger(MediaFormat.KEY_HEIGHT);
                ByteBuffer videoSampleBuffer = ByteBuffer.allocateDirect(videoWidth * videoHeight * 4);
//                ByteBuffer videoSampleBuffer = ByteBuffer.allocateDirect( 500 * 1024);
                BufferInfo videoBufferInfo = new BufferInfo();
                int sampleSize;
                long sampleTime = 0;
                {
                    long firstTime = 0;
                    videoExtractor.readSampleData(videoSampleBuffer, 0);
                    if (videoExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                        firstTime = videoExtractor.getSampleTime();
                        LogUtils.sysout("++++++++++++++firstTime =" + firstTime);
                        videoExtractor.advance();
                    }
                    videoExtractor.readSampleData(videoSampleBuffer, 0);
                    long secondTime = videoExtractor.getSampleTime();
                    LogUtils.sysout("++++++++++++++secondTime =" + secondTime);
                    videoExtractor.advance();
                    long thirdTime = videoExtractor.getSampleTime();
                    LogUtils.sysout("++++++++++++++thirdTime =" + thirdTime);
                    videoExtractor.advance();
                    long fourTime = videoExtractor.getSampleTime();
                    LogUtils.sysout("++++++++++++++fourTime =" + fourTime);
                    if (firstTime != 0) {
                        sampleTime = firstTime / 2;
                    } else if (secondTime != 0 || secondTime != 0) {
                        sampleTime = Math.abs(thirdTime - secondTime);
                    }
                    if (sampleTime == 0) {
                        sampleTime = 40000;
                    }

                }
                videoExtractor.unselectTrack(videoTrackIndex);
                videoExtractor.selectTrack(videoTrackIndex);
                LogUtils.sysout("++++++++++++++presentationTimeUs =" + sampleTime);
                int conut = 0;
                while (!videoMuxDone) {
                    videoSampleBuffer.clear();
                    sampleSize = videoExtractor.readSampleData(videoSampleBuffer, 0);
                    if (sampleSize < 0) {
                        videoMuxDone = true;
                    } else {
//                        videoBufferInfo.presentationTimeUs = videoExtractor.getSampleTime();
                        videoBufferInfo.presentationTimeUs += sampleTime;
                        if (conut < 20) {
//                            LogUtils.sysout("*******************presentationTimeUs =" + videoExtractor.getSampleTime());
                            conut++;
                        }
                        videoBufferInfo.flags = videoExtractor.getSampleFlags();
                        videoBufferInfo.size = sampleSize;
                        videoSampleBuffer.limit(sampleSize);
                        if (sampleTime > duration * 1000) {
                            videoMuxer.writeSampleData(videoTrackIndex, videoSampleBuffer, videoBufferInfo);
                        } else if (videoBufferInfo.presentationTimeUs <= duration * 1000) {
                            videoMuxer.writeSampleData(videoTrackIndex, videoSampleBuffer, videoBufferInfo);
                        } else {


                            videoMuxDone = true;
                        }
                        videoExtractor.advance();
                    }
                }

                videoExtractor.release();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.sysout("99999999999999999");
            } finally {
                if (videoMuxer != null) {
                    videoMuxer.stop();
                    videoMuxer.release();
//                    DLog.i(TAG, "video mix complete.");
                    LogUtils.sysout(TAG + "video mix complete.");
                    LogUtils.sysout("**********合成成功***********");
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private MediaCodec createACCAudioDecoder() throws IOException {
            MediaCodec codec = MediaCodec.createEncoderByType(AUDIO_MIME);
            MediaFormat format = new MediaFormat();
            format.setString(MediaFormat.KEY_MIME, AUDIO_MIME);
            format.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
            format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 2);
            format.setInteger(MediaFormat.KEY_SAMPLE_RATE, 44100);
            format.setInteger(MediaFormat.KEY_AAC_PROFILE,
                    MediaCodecInfo.CodecProfileLevel.AACObjectLC);
            codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            return codec;
        }

        private long lastAudioPresentationTimeUs = -1;
    }

}
