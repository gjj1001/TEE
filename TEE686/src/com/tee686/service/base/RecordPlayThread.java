package com.tee686.service.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.tee686.config.ContentFlag;
import com.tee686.utils.SystemConstant;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class RecordPlayThread extends Thread{
	private int bufferSize;
	private AudioTrack track;
	private String path;
	private boolean runFlag = false;
	public RecordPlayThread() {
		bufferSize = AudioTrack.getMinBufferSize(
				SystemConstant.SAMPLE_RATE_IN_HZ,
				SystemConstant.CHANNEL_CONFIG_IN,
				SystemConstant.AUDIO_FORMAT);
		track = new AudioTrack(AudioManager.STREAM_MUSIC,
				SystemConstant.SAMPLE_RATE_IN_HZ,
				SystemConstant.CHANNEL_CONFIG_IN,
				SystemConstant.AUDIO_FORMAT, bufferSize*2,
				AudioTrack.MODE_STREAM);
	}
	public void run() {
		try {
			InputStream in;
			if(path.startsWith("http")) {
				URL url = new URL(path);
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.connect();
				in = conn.getInputStream();				
			} else {
				in = new FileInputStream(path);
			}
			sleep(1000);
			BufferedInputStream dis = new BufferedInputStream(in);
			byte[] buffer = new byte[bufferSize];
			// 由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
			int length = 0;
			// 开始播放
			track.play();
			while ((length = dis.read(buffer)) != -1 && runFlag) {
				// 然后将数据写入到AudioTrack中
				track.write(buffer, 0, length);
			}
			
			Log.i(ContentFlag.TAG, "play is over");
			sleep(1000);
			runFlag = false;
			Log.i(ContentFlag.TAG, "thread is over");
			dis.close();
		} catch (Exception e) {
			Log.i(ContentFlag.TAG, "thread is closed");
			e.printStackTrace();
		} finally {
			track.stop();
			track.release();
			track = null;
		}
	
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRunFlag() {
		return runFlag;
	}
	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}
}
