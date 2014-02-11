package com.tee686.receiver;

import org.json.JSONException;
import org.json.JSONObject;

import com.tee686.activity.DisplayActivity;
import com.tee686.activity.ReplyActivity;
import com.tee686.activity.UserCenterActivity;

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
		} else if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			/*Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)+
					"给您发来一条消息", Toast.LENGTH_SHORT).show();*/
		} else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			openNotification(context, bundle);
		}

	}

	private void openNotification(Context context, Bundle bundle) {
		String notify = null;
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
 		try {
 			JSONObject json = new JSONObject(extras);
 			notify = json.optString("notify");
 		} catch (JSONException e) {			
 			e.printStackTrace();
 		}
		if(!"".equals(notify)) {
			Intent intent = new Intent();
			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, UserCenterActivity.class);
			context.startActivity(intent);
		} else {
			Intent intent = new Intent();
			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, ReplyActivity.class);
			context.startActivity(intent);
		}
		
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
