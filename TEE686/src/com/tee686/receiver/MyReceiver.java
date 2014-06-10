package com.tee686.receiver;

<<<<<<< HEAD
import java.text.SimpleDateFormat;
import java.util.Date;

=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
import org.json.JSONException;
import org.json.JSONObject;

import com.tee686.activity.DisplayActivity;
import com.tee686.activity.ReplyActivity;
<<<<<<< HEAD
import com.tee686.activity.UserPubContentsActivity;
import com.tee686.db.DBHelper;
import com.tee686.db.PushCacheColumn;
=======
import com.tee686.activity.UserCenterActivity;
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12

import cn.jpush.android.api.JPushInterface;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyReceiver extends BroadcastReceiver {

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			processCustomMessage(context, bundle);
		} else if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			/*Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)+
					"给您发来一条消息", Toast.LENGTH_SHORT).show();*/
<<<<<<< HEAD
			if(null!=bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH)) {
				DBHelper dbHelper = DBHelper.getInstance(context);
				ContentValues values = new ContentValues();
				values.put(DisplayActivity.NOTIFICATION_TITLE, bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
				values.put(DisplayActivity.TITLE, bundle.getString(JPushInterface.EXTRA_TITLE));
				values.put(DisplayActivity.ALERT, bundle.getString(JPushInterface.EXTRA_ALERT));
				values.put(DisplayActivity.MESSAGE, bundle.getString(JPushInterface.EXTRA_MESSAGE));
				values.put(DisplayActivity.RICHPUSH_HTML_PATH, bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH));
				values.put(DisplayActivity.Timestamp, format.format(new Date()));
				dbHelper.insert(PushCacheColumn.TABLE_NAME, values);
			}			
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		} else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			openNotification(context, bundle);
		}

	}

	private void openNotification(Context context, Bundle bundle) {
		String notify = null;
<<<<<<< HEAD
		String uname = null;
//		String media = null;
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
 		try {
 			JSONObject json = new JSONObject(extras);
 			notify = json.optString("notify");
<<<<<<< HEAD
 			uname = json.optString("uname");
// 			media = json.optString("media");
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
 		} catch (JSONException e) {			
 			e.printStackTrace();
 		}
		if(!"".equals(notify)) {
			Intent intent = new Intent();
			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
<<<<<<< HEAD
			intent.setClass(context, DisplayActivity.class);
			context.startActivity(intent);		
		} else if(!"".equals(uname)) {
			Intent intent = new Intent();
			intent.putExtra("uname", uname);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, UserPubContentsActivity.class);
			context.startActivity(intent);
		}
		else {
=======
			intent.setClass(context, UserCenterActivity.class);
			context.startActivity(intent);
		} else {
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
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
