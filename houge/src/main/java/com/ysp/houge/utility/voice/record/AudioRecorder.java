package com.ysp.houge.utility.voice.record;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ysp.houge.utility.FileUtil;
import com.ysp.houge.utility.LogUtil;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;

@SuppressLint("SimpleDateFormat")
public class AudioRecorder implements IRecordStrategy {

	private MediaRecorder recorder;
	private String fileName;
	private String fileFolder = FileUtil.getAmrFolder();

	private boolean isRecording = false;

	@SuppressWarnings("deprecation")
	@Override
	public void ready() {
		fileName = getCurrentDate();
		recorder = new MediaRecorder();
		recorder.setOutputFile(fileFolder + "/" + fileName + ".amr");// 路径为
																		// 录音路径名+时间+格式
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置MediaRecorder的音频源为麦克风
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);// 设置MediaRecorder录制的音频格式
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置MediaRecorder录制音频的编码为amr
	}

	/** 以当前时间作为文件名 */
	private String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	@Override
	public void start() {
		if (!isRecording) {
			try {
				recorder.prepare();
				recorder.start();
			} catch (Exception e) {
				LogUtil.setLogWithE("AudioRecorder", e.getMessage());
			}
			isRecording = true;
		}
	}

	@Override
	public void stop() {
		if (isRecording) {
			try {
				recorder.stop();
				recorder.release();
				isRecording = false;
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void deleteOldFile() {
		File file = new File(fileFolder + "/" + fileName + ".amr");
		file.deleteOnExit();
	}

	@Override
	public double getAmplitude() {
		if (!isRecording) {
			LogUtil.setLogWithE("AudioRecorder", "Did you start recorded?");
			return 0;
		}
		return recorder.getMaxAmplitude();
	}

	@Override
	public String getFilePath() {
		return fileFolder + "/" + fileName + ".amr";
	}

}