package com.changxiang.vod.module.ui.recordmusic.recordutils;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.view.SurfaceView;


import com.changxiang.vod.common.utils.MyFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Recorder controller, used to start,stop record, and combine all the videos together
 * @author xiaodong
 *
 */
public class RecorderManager {
	private MediaRecorder mediaRecorder = null;
	private CameraManager cameraManager = null;
	private String parentPath = null;
	private List<String> videoTempFiles = new ArrayList<String>();
	private SurfaceView mySurfaceView = null;
	public static final int MAX_TIME = 60000;
	private boolean isMax = false;
	private long videoStartTime;
	private int totalTime = 0;
	private boolean isStart = false;
	Activity mainActivity = null;
	private final Semaphore semp = new Semaphore(1);

	public RecorderManager(CameraManager cameraManager,
                           SurfaceView mySurfaceView, Activity mainActivity) {
		this.cameraManager = cameraManager;
		this.mySurfaceView = mySurfaceView;
		this.mainActivity = mainActivity;
		//parentPath = generateParentFolder();
		parentPath = MyFileUtil.DIR_RECORDER.toString();
		reset();
	}

	private Camera getCamera() {
		return cameraManager.getCamera();
	}

	public boolean isStart() {
		return isStart;
	}

	public long getVideoStartTime() {
		return videoStartTime;
	}

	public int checkIfMax(long timeNow) {
		int during = 0;
		if (isStart) {
			during = (int) (totalTime + (timeNow - videoStartTime));
			if (during >= MAX_TIME) {
				stopRecord();
				during = MAX_TIME;
				isMax = true;
			}
		} else {
			during = totalTime;
			if (during >= MAX_TIME) {
				during = MAX_TIME;
			}
		}

		return during;
	}

	public void reset() {
		for (String file : videoTempFiles) {
			File tempFile = new File(file);
			if (tempFile.exists()) {
				tempFile.delete();
			}
		}
		videoTempFiles = new ArrayList<String>();
		isStart = false;
		totalTime = 0;
		isMax = false;
	}

	public List<String> getVideoTempFiles() {
		return videoTempFiles;
	}

	public String getVideoParentpath() {
		return parentPath;
	}

	//获取最大振幅
	public int getMaxAmplitude(){
		try {
			if (mediaRecorder!=null){
				return mediaRecorder.getMaxAmplitude();
			}else {
				return 0;
			}
		} catch (IllegalStateException e) {
			return 0;
		}
	}

	public MediaRecorder getMediaRecorder() {
		return mediaRecorder;
	}

	public void startRecord(boolean isFirst) {
		if (isMax) {
			return;
		}
		try {
		semp.acquireUninterruptibly();
//		getCamera().stopPreview();
		Camera camera=cameraManager.getCamera();
		if (camera!=null){
			camera.stopPreview();
		}
		mediaRecorder = new MediaRecorder();//MediaRecorder:控制录像音视频源和输出编码
		cameraManager.getCamera().unlock();
		mediaRecorder.setCamera(cameraManager.getCamera());
		if (cameraManager.isUseBackCamera()) {
			mediaRecorder.setOrientationHint(90);
		} else {
			mediaRecorder.setOrientationHint(90 + 180);
		}
		Size defaultSize = cameraManager.getDefaultSize();
//		String fileName = parentPath + File.separator + videoTempFiles.size() + ".mp4";
		String fileName = parentPath + File.separator + "video" + ".mp4";
			File file=new File(fileName);
			if (file.exists()){
				file.delete();
			}
		/*CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//2st. Initialized state
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

//3st. config
		mediaRecorder.setOutputFormat(mProfile.fileFormat);
		mediaRecorder.setAudioEncoder(mProfile.audioCodec);
		mediaRecorder.setVideoEncoder(mProfile.videoCodec);
		mediaRecorder.setOutputFile(fileName);
		mediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
		mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);
		mediaRecorder.setVideoEncodingBitRate(mProfile.videoBitRate);
		mediaRecorder.setAudioEncodingBitRate(mProfile.audioBitRate);
		mediaRecorder.setAudioChannels(mProfile.audioChannels);
		mediaRecorder.setAudioSamplingRate(mProfile.audioSampleRate);*/


//		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);//设置声源
//		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置声源
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置视频源
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//设置音频输出格式为mp4
//		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//录制音频编码
		mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);//录制视频编码
//		mediaRecorder.setProfile(CamcorderProfile.get(CameraProfile.QUALITY_HIGH));
		mediaRecorder.setOutputFile(fileName);

//		defaultSize=null;
		if (defaultSize != null) {//设置录制视频尺寸
			mediaRecorder.setVideoSize(defaultSize.width, defaultSize.height);
		} else {
			mediaRecorder.setVideoSize(640, 480);//设置视频尺寸大小
//			mediaRecorder.setVideoSize(480, 640);//设置视频尺寸大小
		}
		mediaRecorder.setVideoFrameRate(30);//设置视频帧率
		mediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024);//设置比特率
//		mediaRecorder.setVideoEncodingBitRate(128000);//设置比特率
		mediaRecorder.setAudioChannels(2);
		mediaRecorder.setAudioSamplingRate(48000);

		videoTempFiles.add(fileName);
		mediaRecorder.setPreviewDisplay(mySurfaceView.getHolder().getSurface());

			mediaRecorder.prepare();
		} catch (Exception e) {
			e.printStackTrace();
			stopRecord();
		}

		try {
			mediaRecorder.start();
			isStart = true;
			videoStartTime = new Date().getTime();

		} catch (Exception e) {
			e.printStackTrace();
			if (isFirst) {
				startRecord(false);
			} else {
				stopRecord();
			}
		}
	}

	public void stopRecord() {
		if (!isMax) {
			totalTime += new Date().getTime() - videoStartTime;
			videoStartTime = 0;
		}
		//
		isStart = false;

		//
		if (mediaRecorder == null) {
			return;
		}
		try {
			mediaRecorder.setPreviewDisplay(null);
			mediaRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				mediaRecorder.reset();
				mediaRecorder.release();
				mediaRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				getCamera().reconnect();
			} catch (Exception e) {
				// TODO: handle this exception...
			}
			getCamera().lock();
			semp.release();
		}

	}

	/*public String generateParentFolder() {
		String parentPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mycapture/video/temp";
		File tempFile = new File(parentPath);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		return parentPath;

	}*/
}
