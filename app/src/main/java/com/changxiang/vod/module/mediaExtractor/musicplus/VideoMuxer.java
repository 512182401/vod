package com.changxiang.vod.module.mediaExtractor.musicplus;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;



import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.mediaExtractor.MainApplication;
import com.changxiang.vod.module.mediaExtractor.utils.DLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

//import com.teana.musicplus.app.MainApplication;

/**
 * 视频混合音频
 *
 * @author Darcy
 */
public abstract class VideoMuxer {

    private static final String TAG = "VideoMuxer";

    String mOutputVideo;

    private VideoMuxer(String outputVideo) {
        this.mOutputVideo = outputVideo;
    }

    public final static VideoMuxer createVideoMuxer(String outputVideo) {//合成的文件路i经
        return new Mp4Muxer( outputVideo );
    }

    /**
     * mix raw audio into video
     *
     * @param videoFile
     * @param rawAudioFile
     * @param includeAudioInVideo
     */
    public abstract void mixRawAudio(File videoFile, File rawAudioFile, boolean includeAudioInVideo, boolean isRecorderMp3);
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
    private static class Mp4Muxer extends VideoMuxer {

        private final static String AUDIO_MIME = "audio/mp4a-latm";
        private final static long audioBytesPerSample = 44100 * 16 / 8;//用44.10khz的采样频率,16位的精度存储,则录制1秒钟的单声道所占的空间大小。

        private long rawAudioSize;

        public Mp4Muxer(String outputVideo) {
            super( outputVideo );
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void mixRawAudio(File videoFile, File rawAudioFile, boolean includeAudioInVideo, boolean isRecorderMp3) {
            LogUtils.sysout("视频数据videoFile="+videoFile+ "音频数据：rawAudioFile="+rawAudioFile );
//			rawAudioFile 解码之后的音频文件
//			videoFile 录制的视频文件
//			includeAudioInVideo 是否包含视频音轨
            final String videoFilePath = videoFile.getAbsolutePath();

            MediaMuxer videoMuxer = null;
            try {

                final String outputVideo = mOutputVideo;//outputVideo 合成之后的文件
                videoMuxer = new MediaMuxer( outputVideo, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4 );

                MediaFormat videoFormat = null;
                MediaExtractor videoExtractor = new MediaExtractor();
                videoExtractor.setDataSource( videoFilePath );//设置video路径（分离出来的video）
                //判断是否为视频文件
                for (int i = 0; i < videoExtractor.getTrackCount(); i++) {
                    MediaFormat format = videoExtractor.getTrackFormat( i );
                    String mime = format.getString( MediaFormat.KEY_MIME );
                    if (mime.startsWith( "video/" )) {
                        videoExtractor.selectTrack( i );
                        videoFormat = format;
                        break;
                    }
                }
                LogUtils.sysout( "判断是否为视频文件" );
                int videoTrackIndex = videoMuxer.addTrack( videoFormat );
                int audioTrackIndex = 0;

                //extract and decode audio
                FileInputStream fisExtractAudio = null;
                //获取视频音轨
                if (includeAudioInVideo) {//需要包含视频音轨，故，应该分离保存视频音轨

                    AndroidAudioDecoder audioDecoder = new AndroidAudioDecoder( videoFilePath );
                    //设置解码保存之后的文件
                    String extractAudioFilePath = MainApplication.RECORD_AUDIO_PATH + "/" + System.currentTimeMillis();
                    audioDecoder.decodeToFile( extractAudioFilePath );

                    File extractAudioFile = new File( extractAudioFilePath );
                    fisExtractAudio = new FileInputStream( extractAudioFile );
                }
                LogUtils.sysout( "解码video文件" );
                //获取MP3解码文件流,用于
//				fisMixAudio 音频流
                FileInputStream fisMixAudio = new FileInputStream( rawAudioFile );

                boolean readExtractAudioEOS = includeAudioInVideo ? false : true;
                boolean readMixAudioEOS = false;
                byte[] extractAudioBuffer = new byte[4096*4];//视频音轨的区间
                byte[] mixAudioBuffer = new byte[4096*4];//音频的区间
                byte[] RecorderAudioBuffer = new byte[2048*4];//为了通道数准备
//                isRecorderMp3
                int extractAudioReadCount = 0;
                int mixAudioReadCount = 0;//mixAudioReadCount 音频流数据长度
                int RecorderAudioReadCount = 0;//mixAudioReadCount 音频流数据长度

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
                 * queueInputBuffer和dequeueInputBuffer是一对方法，两个要在一起使用
                 * 首先，这一对函数的应用场合是对输入的数据流进行编码或者解码处理的时候，你会通过各种方法获得一个ByteBuffer的数组，这些数据就是准备处理的数据。
                 你要通过自己的方法找到你要处理的部分，然后调用dequeueInputBuffer方法提取出要处理的部分（也就是一个ByteBuffer数据流），把这一部分放到缓存区。
                 接下来就是你自己对于这个数据流的处理了。
                 然后在处理完毕之后，一定要调用queueInputBuffer把这个ByteBuffer放回到队列中，这样才能正确释放缓存区。
                 对于输出的数据流，同样也有一对这样的函数，叫做queueOutputBuffer和dequeueOutputBuffer，作用类似哦。
                 */
                while (!sawOutputEOS) {
                    if (!sawInputEOS) {
                        //判断相关文件是否存在
                        inputBufIndex = audioEncoder.dequeueInputBuffer( 4096*4 );//准备处理的数据（每10000个长度处理）
                        if (inputBufIndex >= 0) {
                            ByteBuffer inputBuffer = audioInputBuffers[inputBufIndex];
                            inputBuffer.clear();

                            int bufferSize = inputBuffer.remaining();
                            if (bufferSize != extractAudioBuffer.length) {
                                extractAudioBuffer = new byte[bufferSize];
                                mixAudioBuffer = new byte[bufferSize];
                                RecorderAudioBuffer = new byte[bufferSize / 2];
                            }

                            if (!readExtractAudioEOS) {//需要添加视频音轨，判断视频音轨是否存在
                                extractAudioReadCount = fisExtractAudio.read( extractAudioBuffer );
                                if (extractAudioReadCount == -1) {
                                    readExtractAudioEOS = true;
                                }
                            }

                            if (!readMixAudioEOS) {//判断合成音频是否存在
                                //				fisMixAudio 音频流
                                //mixAudioReadCount每一次获取到的数据长度
                                if (isRecorderMp3) {//录制的为mp3
                                    RecorderAudioReadCount = fisMixAudio.read( RecorderAudioBuffer );//只需要获取一半的长度，所以需要*2；
                                    byte firstByte;
                                    byte secondByte;
                                    if (RecorderAudioReadCount != -1) {
                                        mixAudioReadCount = RecorderAudioReadCount * 2;
//                                        mixAudioBuffer = new byte[mixAudioReadCount];
                                        for (int index = 0; index < RecorderAudioReadCount; index += 2) {
//                                            mixAudioBuffer[2 * t] = RecorderAudioBuffer[t];
//                                            mixAudioBuffer[2 * t + 1] = RecorderAudioBuffer[t];

                                                firstByte = RecorderAudioBuffer[index];
                                                secondByte = RecorderAudioBuffer[index + 1];

                                            mixAudioBuffer[2 * index] = firstByte;
                                            mixAudioBuffer[2 * index + 1] = secondByte;
                                            mixAudioBuffer[2 * index + 2] = firstByte;
                                            mixAudioBuffer[2 * index + 3] = secondByte;

                                        }
                                    }else{
                                        mixAudioReadCount = RecorderAudioReadCount;
                                        readMixAudioEOS = true;
                                    }
                                } else {
                                    mixAudioReadCount = fisMixAudio.read( mixAudioBuffer );
                                    LogUtils.sysout( "读取数据==" + RecorderAudioReadCount );
                                }
//                                mixAudioReadCount = fisMixAudio.read( mixAudioBuffer );
                                if (mixAudioReadCount == -1) {
                                    readMixAudioEOS = true;
                                }
                            }

                            if (readExtractAudioEOS && readMixAudioEOS) {//不需要添加视频音轨，且不存在合成音频，则直接合成
                                //调用queueInputBuffer把这个ByteBuffer放回到队列中，这样才能正确释放缓存区
                                //第一个参数表示
                                audioEncoder.queueInputBuffer( inputBufIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM );
                                sawInputEOS = true;
                            } else {

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
                                        Arrays.fill( mixAudioBuffer, mixAudioReadCount - 1, bufferSize, (byte) 0 );
                                    } else {
                                        //当视频音轨时长比音频时长短
//										   Arrays.fill 填充数据
                                        Arrays.fill( extractAudioBuffer, extractAudioReadCount - 1, bufferSize, (byte) 0 );
                                    }
                                    //混合两个以上的音频
                                    mixAudioBytes = audioMixer.mixRawAudioBytes( twoAudioBytes );
                                    if (mixAudioBytes == null) {//混合失败
                                        DLog.e( TAG, "mix audio : null" );
                                    }
                                    inputBuffer.put( mixAudioBytes );
                                    rawAudioSize += mixAudioBytes.length;
                                    //  //需要提交视频音轨，并且有需要合成的音频
                                    audioEncoder.queueInputBuffer( inputBufIndex, 0, mixAudioBytes.length, audioTimeUs, 0 );
                                } else if (!readExtractAudioEOS && readMixAudioEOS) {
                                    //需要提交视频音轨，但没有需要合成的音频
                                    inputBuffer.put( extractAudioBuffer, 0, extractAudioReadCount );
                                    rawAudioSize += extractAudioReadCount;
                                    audioEncoder.queueInputBuffer( inputBufIndex, 0, extractAudioReadCount, audioTimeUs, 0 );
                                } else {  //不需要提交视频音轨，也没有需要合成的音频
                                    inputBuffer.put( mixAudioBuffer, 0, mixAudioReadCount );
                                    rawAudioSize += mixAudioReadCount;
                                    audioEncoder.queueInputBuffer( inputBufIndex, 0, mixAudioReadCount, audioTimeUs, 0 );
                                }

                                //获取微妙进度条：audioTimeUs
                                // 一秒==1000000微妙
                                //rawAudioSize / 2.0 表示双声道转单声道
                                //audioBytesPerSample：一秒钟所占的数据空间。
                                audioTimeUs = (long) (1000000 * (rawAudioSize / 2.0) / audioBytesPerSample);

                            }
                        }
                    }

                    outputBufIndex = audioEncoder.dequeueOutputBuffer( outBufferInfo, 4096*4 );
                    if (outputBufIndex >= 0) {

                        // Simply ignore codec config buffers.
                        if ((outBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                            DLog.i( TAG, "audio encoder: codec config buffer" );
                            audioEncoder.releaseOutputBuffer( outputBufIndex, false );
                            continue;
                        }

                        if (outBufferInfo.size != 0) {
                            ByteBuffer outBuffer = audioOutputBuffers[outputBufIndex];
                            outBuffer.position( outBufferInfo.offset );
                            outBuffer.limit( outBufferInfo.offset + outBufferInfo.size );
                            DLog.i( TAG, String.format( " writing audio sample : size=%s , presentationTimeUs=%s", outBufferInfo.size, outBufferInfo.presentationTimeUs ) );
                            if (lastAudioPresentationTimeUs < outBufferInfo.presentationTimeUs) {
                                videoMuxer.writeSampleData( audioTrackIndex, outBuffer, outBufferInfo );
                                lastAudioPresentationTimeUs = outBufferInfo.presentationTimeUs;
                            } else {
                                DLog.e( TAG, "error sample! its presentationTimeUs should not lower than before." );
                            }
                        }

                        audioEncoder.releaseOutputBuffer( outputBufIndex, false );

                        if ((outBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                            sawOutputEOS = true;
                        }
                    } else if (outputBufIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                        audioOutputBuffers = audioEncoder.getOutputBuffers();
                    } else if (outputBufIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat audioFormat = audioEncoder.getOutputFormat();
                        audioTrackIndex = videoMuxer.addTrack( audioFormat );
                        videoMuxer.start(); //start muxer
                    }
                }

                if (fisExtractAudio != null) {
                    fisExtractAudio.close();
                }

                fisMixAudio.close();//fisMixAudio 音频流
                audioEncoder.stop();
                audioEncoder.release();

                //mix video
                boolean videoMuxDone = false;
                // 压缩帧大小 < 原始图片大小
                int videoWidth = videoFormat.getInteger( MediaFormat.KEY_WIDTH );
                int videoHeight = videoFormat.getInteger( MediaFormat.KEY_HEIGHT );
                ByteBuffer videoSampleBuffer = ByteBuffer.allocateDirect( videoWidth * videoHeight );
                BufferInfo videoBufferInfo = new BufferInfo();
                int sampleSize;
                while (!videoMuxDone) {
                    videoSampleBuffer.clear();
                    sampleSize = videoExtractor.readSampleData( videoSampleBuffer, 0 );
                    if (sampleSize < 0) {
                        videoMuxDone = true;
                    } else {
                        videoBufferInfo.presentationTimeUs = videoExtractor.getSampleTime();
                        videoBufferInfo.flags = videoExtractor.getSampleFlags();
                        videoBufferInfo.size = sampleSize;
                        videoSampleBuffer.limit( sampleSize );
                        videoMuxer.writeSampleData( videoTrackIndex, videoSampleBuffer, videoBufferInfo );
                        videoExtractor.advance();
                    }
                }

                videoExtractor.release();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.sysout( "99999999999999999" );
            } finally {
                if (videoMuxer != null) {
                    videoMuxer.stop();
                    videoMuxer.release();
                    DLog.i( TAG, "video mix complete." );
                    LogUtils.sysout( "**********合成成功***********" );
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private MediaCodec createACCAudioDecoder() throws IOException {
            MediaCodec codec = MediaCodec.createEncoderByType( AUDIO_MIME );
            MediaFormat format = new MediaFormat();
            format.setString( MediaFormat.KEY_MIME, AUDIO_MIME );
            format.setInteger( MediaFormat.KEY_BIT_RATE, 125000 );
            format.setInteger( MediaFormat.KEY_CHANNEL_COUNT, 2 );
            format.setInteger( MediaFormat.KEY_SAMPLE_RATE, 44100 );
            format.setInteger( MediaFormat.KEY_AAC_PROFILE,
                    MediaCodecInfo.CodecProfileLevel.AACObjectLC );
            codec.configure( format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE );
            return codec;
        }

        private long lastAudioPresentationTimeUs = -1;
    }
}
