package com.ysp.houge.utility.voice.media;

import com.ysp.houge.utility.FileUtil;
import com.ysp.houge.utility.LogUtil;

import android.media.MediaPlayer;

public class MyMediaPlayer implements IMediaPlayer, MediaPlayer.OnCompletionListener {
	private boolean isPlaying = false;
	private onMediaPlayListener listener;
	private MediaPlayer playle;

	@Override
	public void start() {
		if (playle == null) {
			LogUtil.setLogWithE("MyMediaPlayer", "please call playMethed at the first");
			return;
		}
		if (!isPlaying) {
			playle.start();
			playle.setOnCompletionListener(this);
			isPlaying = true;
		}

	}

	public void setListener(onMediaPlayListener listener) {
		this.listener = listener;
	}

	@Override
	public void stop() {
		if (playle == null) {
			LogUtil.setLogWithE("MyMediaPlayer", "please call playMethed at the first");
			return;
		}
		if (isPlaying) {
			playle.stop();
			isPlaying = false;
		}
	}

	@Override
	public void playFromFile(String sdFile) {
		if (FileUtil.isFileExist(sdFile)) {
			playle = new MediaPlayer();
			try {
				playle.setDataSource(sdFile);
				playle.prepare();
			} catch (Exception e) {
				LogUtil.setLogWithE("MyMediaPlayer", "play dataSource Error");
				LogUtil.setLogWithE("MyMediaPlayer", e.getMessage());
			}
		} else {
			LogUtil.setLogWithE("MyMediaPlayer", "File is not exit");
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		listener.onFinishPlay();
	}

	@Override
	public boolean playFromNet(String netPat) {
		playle = new MediaPlayer();
		try {
			playle.setDataSource(netPat);
			playle.prepare();
		} catch (Exception e) {
			LogUtil.setLogWithE("MyMediaPlayer", "play dataSource Error");
			LogUtil.setLogWithE("MyMediaPlayer", e.getMessage());
			return false;
		}
		return true;
	}

	public interface onMediaPlayListener {
		void onFinishPlay();
	}

}
