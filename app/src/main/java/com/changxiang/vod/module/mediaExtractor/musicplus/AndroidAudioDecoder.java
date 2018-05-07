package com.changxiang.vod.module.mediaExtractor.musicplus;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;

//import com.quchangkeji.tosing.common.utils.LogUtils;
//import com.quchangkeji.tosing.module.mediaExtractor.com.Tool.Function.FileFunction;
//import com.quchangkeji.tosing.module.mediaExtractor.com.Tool.Function.LogFunction;
//import com.quchangkeji.tosing.module.mediaExtractor.com.Tool.Global.Constant;
//import com.quchangkeji.tosing.module.mediaExtractor.vavi.sound.pcm.resampling.ssrc.SSRC;
//import com.quchangkeji.tosing.module.musicplus.utils.DLog;

import com.changxiang.vod.common.utils.LogUtils;
import com.changxiang.vod.module.constance.Constant;
import com.changxiang.vod.module.mediaExtractor.Function.FileFunction;
import com.changxiang.vod.module.mediaExtractor.Function.LogFunction;
import com.changxiang.vod.module.mediaExtractor.utils.DLog;
import com.changxiang.vod.module.mediaExtractor.vavi.sound.pcm.resampling.ssrc.SSRC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Android SDK 支持的编码器
 *
 * @author Darcy
 * @version android.os.Build.VERSION.SDK_INT >= 16
 */
public class AndroidAudioDecoder extends AudioDecoder {

    private static final String TAG = "AndroidAudioDecoder";

    AndroidAudioDecoder(String encodefile) {
        super(encodefile);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RawAudioInfo decodeToFile(String outFile) throws IOException {

        long beginTime = System.currentTimeMillis();
        int sampleRate = 0;//采样率
        final String encodeFile = mEncodeFile;
        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(encodeFile);

        MediaFormat mediaFormat = null;
        for (int i = 0; i < extractor.getTrackCount(); i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("audio/")) {
                LogUtils.sysout("3333解码文件 format = " + format);
                extractor.selectTrack(i);
                mediaFormat = format;
                break;
            }
        }

        if (mediaFormat == null) {
            DLog.e("not a valid file with audio track..");
            extractor.release();
            return null;
        }

        RawAudioInfo rawAudioInfo = new RawAudioInfo();
        rawAudioInfo.channel = mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        rawAudioInfo.sampleRate = mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        rawAudioInfo.tempRawFile = outFile;
        FileOutputStream fosDecoder = new FileOutputStream(rawAudioInfo.tempRawFile);
        LogUtils.sysout("地址" + mEncodeFile +
                "\n获取采样的数据：" + mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT) +
                "\n******" + mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE));
        String mediaMime = mediaFormat.getString(MediaFormat.KEY_MIME);
        sampleRate = mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);//采样率
        MediaCodec codec = MediaCodec.createDecoderByType(mediaMime);
//        MediaCodec codec = MediaCodec.createByCodecName("OMX.SEC.aac.enc");

        codec.configure(mediaFormat, null, null, 0);
        codec.start();

        ByteBuffer[] codecInputBuffers = codec.getInputBuffers();
        ByteBuffer[] codecOutputBuffers = codec.getOutputBuffers();

        final double audioDurationUs = mediaFormat.getLong(MediaFormat.KEY_DURATION);
        final long kTimeOutUs = 5000;
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        boolean sawInputEOS = false;
        boolean sawOutputEOS = false;
        int totalRawSize = 0;
        try {
            while (!sawOutputEOS) {
                if (!sawInputEOS) {
                    int inputBufIndex = codec.dequeueInputBuffer(kTimeOutUs);
                    if (inputBufIndex >= 0) {
                        ByteBuffer dstBuf = codecInputBuffers[inputBufIndex];
                        int sampleSize = extractor.readSampleData(dstBuf, 0);
                        if (sampleSize < 0) {
                            DLog.i(TAG, "saw input EOS.");
                            sawInputEOS = true;
                            codec.queueInputBuffer(inputBufIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        } else {
                            long presentationTimeUs = extractor.getSampleTime();
                            codec.queueInputBuffer(inputBufIndex, 0, sampleSize, presentationTimeUs, 0);
                            extractor.advance();
                        }
                    }
                }
                int res = codec.dequeueOutputBuffer(info, kTimeOutUs);
                if (res >= 0) {

                    int outputBufIndex = res;
                    // Simply ignore codec config buffers.
                    if ((info.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        DLog.i(TAG, "audio encoder: codec config buffer");
                        codec.releaseOutputBuffer(outputBufIndex, false);
                        continue;
                    }

                    if (info.size != 0) {

                        ByteBuffer outBuf = codecOutputBuffers[outputBufIndex];

                        outBuf.position(info.offset);
                        outBuf.limit(info.offset + info.size);
                        byte[] data = new byte[info.size];
                        outBuf.get(data);
                        totalRawSize += data.length;
                        fosDecoder.write(data);
                        if (mOnAudioDecoderListener != null)
                            mOnAudioDecoderListener.onDecode(data, info.presentationTimeUs / audioDurationUs);
//				        DLog.i(TAG, mEncodeFile + " presentationTimeUs : " + info.presentationTimeUs);
                    }

                    codec.releaseOutputBuffer(outputBufIndex, false);

                    if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        DLog.i(TAG, "saw output EOS.");
                        sawOutputEOS = true;
                    }

                } else if (res == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    codecOutputBuffers = codec.getOutputBuffers();
                    DLog.i(TAG, "output buffers have changed.");
                } else if (res == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat oformat = codec.getOutputFormat();
                    DLog.i(TAG, "output format has changed to " + oformat);
                }
            }
            rawAudioInfo.size = totalRawSize;

            if (mOnAudioDecoderListener != null)
                mOnAudioDecoderListener.onDecode(null, 1);

//            DLog.i(TAG, "decode "+outFile+" cost " + (System.currentTimeMillis() - beginTime) +" milliseconds !");
//			LogUtils.sysout( TAG+"decode "+outFile+" cost " + (System.currentTimeMillis() - beginTime) +" milliseconds !" );
            return rawAudioInfo;
        } finally {
//            try {
//                if (sampleRate != Constant.RecordSampleRate) {
//                    Resample(sampleRate, outFile);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            fosDecoder.close();
            codec.stop();
            codec.release();
            extractor.release();

        }


    }


    private static void Resample(int sampleRate, String decodeFileUrl) {
        String newDecodeFileUrl = decodeFileUrl + "new";
        LogUtils.sysout("16545564564564 sampleRate = " + sampleRate);
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
}
