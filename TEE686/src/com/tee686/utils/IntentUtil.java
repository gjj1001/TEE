package com.tee686.utils;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import com.casit.tee686.R;

public class IntentUtil {
	public static void start_activity(Activity activity,Class<?> cls,BasicNameValuePair...name)
	{
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		for(int i=0;i<name.length;i++)
		{
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	public static void startActivity(Activity activity,Class<?> cls,
			BasicNameValuePair...name) {
		Intent intent= new Intent();		
		intent.setClass(activity,cls);
		for(int i=0;i<name.length;i++)
		{
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.push_left_in, R.anim.hold);
	}
}
