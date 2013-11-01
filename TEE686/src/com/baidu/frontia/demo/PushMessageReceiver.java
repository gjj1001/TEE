package com.baidu.frontia.demo;

import org.json.JSONObject;

import android.util.Log;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;

public class PushMessageReceiver extends FrontiaPushMessageReceiver {

	private final static String TAG = "PushMessageReceiver";

	@Override
	public void onHandleMessage(String message) {
		Log.d(TAG, "receive message:" + message);
	}

	@Override
	public void onHandleNotification(String title, String content,
			JSONObject custom) {
		Log.d(TAG, "title:" + title + ", content:" + content);
		
	}

}
