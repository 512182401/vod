package com.changxiang.vod.module.ui.recordmusic.recordutils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.Toast;

//import com.quchangkeji.tosingpk.common.utils.PreferUtil;
//import com.quchangkeji.tosingpk.module.ui.recordmusic.screen.PcmToWav;

import com.changxiang.vod.common.utils.PreferUtil;
import com.changxiang.vod.module.ui.recordmusic.screen.PcmToWav;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 15976 on 2017/6/6.
 */

public class AudioRecorder {

    //音频输入-麦克风
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    //采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
//    private final static int AUDIO_SAMPLE_RATE = 48000;
    private final static int AUDIO_SAMPLE_RATE = 44100;
    //声道 单声道
    private final static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
//    private final static int AUDIO_CHANNEL1 = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private final static int AUDIO_CHANNEL1 = AudioFormat.CHANNEL_OUT_MONO;
    //编码
    private final static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;

    private int playBufSize = 0;//播放字节

    //录音对象
    private AudioRecord audioRecord;

    //录音状态
    private Status status = Status.STATUS_NO_READY;

    //录音文件名
    private String fileName;

    //拼接后的的录音文件名
    private String finalName;

    //录音文件
    private List<String> filesName = new ArrayList<>();

    //线程池
    private ExecutorService mExecutorService;

    //录音监听
    private RecordStreamListener listener;

    private Context mContext;

    //pcm播放器
    private AudioTrack audioTrack;

    private boolean hasHeadSet;//是否插入耳机

    private int cAmplitude = 0;//最大振幅

    public void setHasHeadSet(boolean hasHeadSet) {
        this.hasHeadSet = hasHeadSet;
    }

    public AudioRecorder() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public AudioTrack getAudioTrack() {
        return audioTrack;
    }

    /**
     * 创建录音对象
     */
    public void createAudio(String fileName, int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, channelConfig);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
        this.fileName = fileName;
    }

    /**
     * 创建默认的录音对象
     *
     * @param fileName 文件名
     */
    public void createDefaultAudio(Context mContext, String fileName, String finalName) {
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,
                AUDIO_CHANNEL, AUDIO_ENCODING);
        playBufSize = AudioTrack.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL1, AUDIO_ENCODING);
        audioRecord = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL1, AUDIO_ENCODING,
                playBufSize, AudioTrack.MODE_STREAM);
        audioTrack.setStereoVolume(0.5f, 0.5f);//设置当前音量大小
        this.fileName = fileName;
        this.finalName=finalName;
        status = Status.STATUS_READY;
        this.mContext=mContext;
        cAmplitude=0;
    }

    public int getMaxAmplitude(){
        int result = cAmplitude;
        cAmplitude = 0;
        return result;
    }

    /**
     * 开始录音
     *
     */
    public void startRecord() {

        if (status == Status.STATUS_NO_READY||audioRecord==null) {
            createDefaultAudio(mContext,fileName,finalName);
//            return;
//            throw new IllegalStateException("录音尚未初始化,请检查是否禁止了录音权限~");
        }
        if (status == Status.STATUS_START) {
            return;
//            throw new IllegalStateException("正在录音");
        }
        Log.d("AudioRecorder", "===startRecord===" + audioRecord.getState());
        setHasHeadSet(PreferUtil.getInstance().getBoolean("earback", true));
        audioRecord.startRecording();
        audioTrack.play();
//        if (listener!=null){
//            listener.startAudio();
//        }

        String currentFileName = fileName;
        if (status == Status.STATUS_PAUSE) {
            //假如是暂停录音 将文件名后面加个数字,防止重名文件内容被覆盖
            currentFileName += filesName.size();

        }
//        filesName.add(currentFileName);

        final String finalFileName=currentFileName;
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;

        //使用线程池管理线程
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                writeDataTOFile(finalFileName);
            }
        });
    }

    /**
     * 暂停录音
     */
    public void pauseRecord() {
        Log.d("AudioRecorder", "===pauseRecord===");
        if (status != Status.STATUS_START) {
//            throw new IllegalStateException("没有在录音");
        } else {
            audioRecord.stop();
            status = Status.STATUS_PAUSE;
        }
    }

    public void stopRecord() {
        Log.d("AudioRecorder", "===stopRecord===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY||status == Status.STATUS_STOP) {
            return;
//            throw new IllegalStateException("录音尚未开始");
        } else {
            audioRecord.stop();
            audioTrack.stop();
//            if (listener!=null){
//                listener.finishAudio();
//            }
            status = Status.STATUS_STOP;
            cAmplitude=0;
            release();
        }
    }


    /**
     * 释放资源
     */
    public void release() {
        Log.d("AudioRecorder", "===release===");
        //假如有暂停录音
        try {
            if (filesName.size() > 1) {
                List<String> filePaths = new ArrayList<>();
                for (String fileName : filesName) {
//                    filePaths.add(FileUtils.getPcmFileAbsolutePath(fileName));
                    filePaths.add(fileName);
                }
                //清除
                filesName.clear();
                //将多个pcm文件转化为wav文件
                mergePCMFilesToWAVFile(filePaths);

            } else {
                makePCMFileToWAVFile();
                //这里由于只要录音过filesName.size都会大于0,没录音时fileName为null
                //会报空指针 NullPointerException
                // 将单个pcm文件转化为wav文件
                //Log.d("AudioRecorder", "=====makePCMFileToWAVFile======");
                //makePCMFileToWAVFile();
            }
        } catch (IllegalStateException e) {
//            throw new IllegalStateException(e.getMessage());
        }

//        if (audioRecord != null) {
//            audioRecord.release();
//            audioRecord = null;
//        }
//
//        status = Status.STATUS_NO_READY;
    }

    /**
     * 取消录音
     */
    public void canel() {
//        filesName.clear();
//        fileName = null;
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        if (audioTrack!=null){
            audioTrack.release();
            audioTrack=null;
        }

        status = Status.STATUS_NO_READY;
    }


    /**
     * 将音频信息写入文件
     *
     */
    private void writeDataTOFile(String currentFileName) {
        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        byte[] audiodata = new byte[bufferSizeInBytes];

        FileOutputStream fos = null;
        int readsize = 0;
        try {
//            File file = new File(FileUtils.getPcmFileAbsolutePath(currentFileName));
            File file = new File(currentFileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);// 建立一个可存取字节的文件
        } catch (IllegalStateException e) {
            Log.e("AudioRecorder", e.getMessage());
//            throw new IllegalStateException(e.getMessage());
        } catch (FileNotFoundException e) {
            Log.e("AudioRecorder", e.getMessage());

        }
        while (status == Status.STATUS_START) {
            readsize = audioRecord.read(audiodata, 0, bufferSizeInBytes);
            if (AudioRecord.ERROR_INVALID_OPERATION != readsize && fos != null) {
                try {
                    fos.write(audiodata);
                    for (int i = 0; i < audiodata.length / 2; i++) { // 获取最大振幅
                        // sample
                        // size
                        short curSample = getShort(audiodata[i * 2],
                                audiodata[i * 2 + 1]);
                        if (curSample > cAmplitude) { // Check amplitude
                            cAmplitude = curSample;
                        }
                    }
                    if (hasHeadSet) {
                        //用于拓展业务
                        byte[] tmpBuf = new byte[readsize];
                        System.arraycopy(audiodata, 0, tmpBuf, 0, readsize);
                        //写入数据即播放
                        audioTrack.write(tmpBuf, 0, tmpBuf.length);

//                        listener.onRecording(audiodata, readsize);
                    }
                } catch (IOException e) {
                    Log.e("AudioRecorder", e.getMessage());
                }
            }
        }
        try {
                if (fos != null) {
                    fos.close();// 关闭写入流
                }
            } catch (IOException e) {
                Log.e("AudioRecorder", e.getMessage());
        }
    }

//    噪音消除算法:
    void calc1(short[] lin,int off,int len) {
        int i,j;
        for (i = 0; i < len; i++) {
            j = lin[i+off];
            lin[i+off] = (short)(j>>2);
        }
    }

    /*
	 *
	 * Converts a byte[2] to a short, in LITTLE_ENDIAN format
	 */
    private short getShort(byte argB1, byte argB2) {
        return (short) (argB1 | (argB2 << 8));
    }

    /**
     * 将pcm合并成wav
     *
     * @param filePaths
     */
    private void mergePCMFilesToWAVFile(final List<String> filePaths) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
//                if (PcmToWav.mergePCMFilesToWAVFile(filePaths, FileUtils.getWavFileAbsolutePath(fileName))) {
                if (PcmToWav.mergePCMFilesToWAVFile(filePaths, finalName)){

                    //操作成功
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,"拼接成功", Toast.LENGTH_SHORT).show();
                        }
                    });
//
                } else {
                    //操作失败
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,"拼接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("AudioRecorder", "mergePCMFilesToWAVFile fail");
//                    throw new IllegalStateException("mergePCMFilesToWAVFile fail");
                }
            }
        });
    }

    /**
     * 将单个pcm文件转化为wav文件
     */
    private void makePCMFileToWAVFile() {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
//                if (PcmToWav.makePCMFileToWAVFile(FileUtils.getPcmFileAbsolutePath(fileName), FileUtils.getWavFileAbsolutePath(fileName), true)) {
                if (PcmToWav.makePCMFileToWAVFile(fileName, finalName, true)) {
                    //操作成功
                } else {
                    //操作失败
                    Log.e("AudioRecorder", "makePCMFileToWAVFile fail");
//                    throw new IllegalStateException("makePCMFileToWAVFile fail");
                }
            }
        });
    }


    /**
     * 录音对象的状态
     */
    public enum Status {
        //未开始
        STATUS_NO_READY,
        //预备
        STATUS_READY,
        //录音
        STATUS_START,
        //暂停
        STATUS_PAUSE,
        //停止
        STATUS_STOP
    }

    /**
     * 获取录音对象的状态
     *
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 获取本次录音文件的个数
     *
     * @return
     */
    public int getPcmFilesCount() {
        return filesName.size();
    }


    public RecordStreamListener getListener() {
        return listener;
    }

    public void setListener(RecordStreamListener listener) {
        this.listener = listener;
    }

}
