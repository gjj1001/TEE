package com.tee686.receiver;

import com.tee686.activity.DisplayActivity;

import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			processCustomMessage(context, bundle);
		} else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			openNotification(context, bundle);
		}

	}

	private void openNotification(Context context, Bundle bundle) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, DisplayActivity.class);
		context.startActivity(intent);
	}

	private void processCustomMessage(Context context, Bundle bundle) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, DisplayActivity.class);
		context.startActivity(intent);
	}

}
