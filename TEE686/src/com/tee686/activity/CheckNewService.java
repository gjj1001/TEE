package com.tee686.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import com.tee686.config.Constants;
import com.tee686.config.Urls;
import com.tee686.entity.Observer;
import com.tee686.https.HttpUtils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class CheckNewService extends Service {

	private int diff;
	Timer timer = new Timer();
	SharedPreferences share;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
		timer.purge();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {		
		timer.schedule(new TimerTask() {
			String result;
			@Override
			public void run() {
				try {
					result = HttpUtils.getByHttpClient(CheckNewService.this, Urls.CHECK_NEW_PUBCONTENT);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result!=null) {
					diff = share.getInt("pubtotal", 0);
					share.edit().putInt("pubtotal", Integer.parseInt(result)).commit();
					diff = Integer.parseInt(result) - diff;
					if(diff>0) {
						Intent intent = new Intent();
						intent.setAction(Constants.ReceiverAction.CHECK_NEW_PUB);
						intent.putExtra("num", diff);
						sendBroadcast(intent);
					}
				}
			}
				
		}, 0);
		
		timer.schedule(new TimerTask() {
			String result;
			private List<Observer> listfans = new ArrayList<Observer>();
			@Override
			public void run() {
				try {
					result = HttpUtils.getByHttpClient(CheckNewService.this, Urls.USER_OBSERVER+"?username="
							+share.getString(UserLoginActivity.UID, ""));
					StringBuilder sb = new StringBuilder(result);
					result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//					share.edit().putString("pubContents", result).commit();		
					JSONArray jsonArray = new JSONArray(result);
					for(int i=0; i<jsonArray.length(); i++) {
						String json = jsonArray.getString(i);
						Observer observer = new ObjectMapper().readValue(json, Observer.class);
						listfans.add(observer);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result!=null) {
					diff = share.getInt("fantotal", 0);
					share.edit().putInt("fantotal", listfans.size()).commit();
					diff = listfans.size() - diff;
					if(diff>0) {
						Intent intent = new Intent();
						intent.setAction(Constants.ReceiverAction.CHECK_NEW_FAN);
						intent.putExtra("num", diff);
						sendBroadcast(intent);
					}
				}
			}
				
		}, 0);
		return super.onStartCommand(intent, flags, startId);
	}

}
