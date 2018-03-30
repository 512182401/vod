package com.changxiang.vod.common.utils;//package tosingpk.quchangkeji.com.quchangpk.common.utils;
//
////import com.coremedia.iso.boxes.Container;
////import com.googlecode.mp4parser.authoring.Movie;
////import com.googlecode.mp4parser.authoring.Track;
////import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
////import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
////import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
//
//import android.graphics.Movie;
//
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.channels.FileChannel;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * 对于log的打印输出封装类
// *
// * @author wdl
// *
// */
//public class SounAndVideo {
//
//
//	/**
//	 * @param composeFile：最終合成的地址
//	 * @param recordVideoUrl：录制的视频
//	 * @param recordAudioUrl：录制的音频
//	 */
//	private static void combineFiles(String composeFile, String recordVideoUrl, String recordAudioUrl) {
//		FileChannel fc = null;
//		try {
//			List<Track> videoTracks = new LinkedList<Track>();
//			List<Track> audioTracks = new LinkedList<Track>();
//
//			try {
//				Movie movie = MovieCreator.build(recordAudioUrl);//获取音轨（）
//				for (Track t : movie.getTracks()) {
//					if (t.getHandler().equals("soun")) {
//						audioTracks.add(t);
//					}
////                        if (t.getHandler().equals("vide")) {
////                            videoTracks.add(t);
////                        }
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			try {
//				Movie movie = MovieCreator.build(recordVideoUrl);//获取视轨
//				for (Track t : movie.getTracks()) {
//					if (t.getHandler().equals("vide")) {
//						videoTracks.add(t);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//
//			Movie result = new Movie();
//
//			if (audioTracks.size() > 0) {
//				result.addTrack(new AppendTrack(audioTracks
//						.toArray(new Track[audioTracks.size()])));
//			}
//			if (videoTracks.size() > 0) {
//				result.addTrack(new AppendTrack(videoTracks
//						.toArray(new Track[videoTracks.size()])));
//			}
//
//			Container out = new DefaultMp4Builder().build(result);
////            String outUrl = MyFileUtil.DIY_MV.toString() + File.separator  + "two.mp4";
//			fc = new RandomAccessFile(
//					String.format(composeFile), "rw").getChannel();
//			out.writeContainer(fc);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (fc != null) {
//				try {
//					fc.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//
//}
