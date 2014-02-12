package com.tee686.utils;

import android.media.AudioFormat;

public class SystemConstant {
	//表情的数量
	public final static int express_counts = 107;
	//录制频率
	public final static int SAMPLE_RATE_IN_HZ = 8000;
	//录制通道
	public final static int CHANNEL_CONFIG_IN = AudioFormat.CHANNEL_IN_MONO;
	public final static int CHANNEL_CONFIG_OUT = AudioFormat.CHANNEL_OUT_MONO;
	//编码格式
	public final static int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
}
