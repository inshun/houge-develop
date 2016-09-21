package com.ysp.houge.utility.voice.media;

public interface IMediaPlayer {
	void start();

	void stop();

	void playFromFile(String sdFile);
	
	boolean playFromNet(String netPat);
}
