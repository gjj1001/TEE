package com.tee686.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.Observer;
import com.tee686.entity.PubContent;
import com.tee686.entity.UserInfoItem;
import com.tee686.https.HttpUtils;
<<<<<<< HEAD
import com.tee686.https.NetWorkHelper;
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.IntentUtil;
import com.tee686.utils.ImageUtil.ImageCallback;

public class UserInfoActivity extends BaseActivity {

	private ImageView userHead;
	private ImageView imgFile;
	private ImageView gohome;
	private TextView txtName;
	private TextView txtRegTime;
	private TextView txtEP;
	private TextView txtEM;
//	private TextView brand;
	private TextView zone;
	private TextView content;
	private TextView title;
	private TextView level;
	private TextView observers;
	private TextView fans;
	private Button observ;
	private Button sendmsg;
	
	private String headimage;
	private String username;
	private PubContent pubContent;
	private List<Observer> listobservers = new ArrayList<Observer>();
	private List<Observer> listfans = new ArrayList<Observer>();
	private SharedPreferences share;
	private int total;
<<<<<<< HEAD
	
	private DataAsyncTask dataTask;
	private PubAsyncTask pubTask;
	private CheckObserverNumTask checkNumTask;
	private CheckObserverTask checkObserverTask;
	private CheckFanTask checkFanTask;
	private AddObserverTask addTask;
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initControl();
		title.setBackgroundResource(0);
		title.setText("个人资料");
		Intent intent = getIntent();
		getStringExtra(intent);
<<<<<<< HEAD
		initSharedPreference();							
=======
		initSharedPreference();			
		String url2 = String.format(Urls.USER_OBSERVER+"?uname=%s&username=%s&check=%s", 
				share.getString(UserLoginActivity.UID, ""),username,true);
		new CheckObserverTask().execute(url2);
		String urlString = String.format(Urls.USER_INFO, username);
		new DataAsyncTask().execute(urlString);
		String urlString2 = String.format(Urls.USER_RECENTLY_PUBLISH, username);
		new PubAsyncTask().execute(urlString2);
		
		observ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(share.contains(UserLoginActivity.KEY)) {
					if(!observ.getText().toString().equals("已关注")) {
						String urlString = String.format(Urls.USER_OBSERVER+
								"?uname=%s&username=%s&headimage=%s&headimg=%s&add=%s",
								share.getString(UserLoginActivity.UID, ""), username, headimage,
								share.getString(UserLoginActivity.PIC, ""), "true");
						new AddObserverTask().execute(urlString);
					} 
				} else {
					showShortToast("登陆后可关注");
				}
			}
		});
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		
		gohome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.umeng_fb_slide_in_from_left, R.anim.umeng_fb_slide_out_from_right);
            }
        });		
		
		sendmsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(share.contains(UserLoginActivity.KEY)) {
					if(observ.getText().toString().equals("已关注")) {
						IntentUtil.startActivity(UserInfoActivity.this, ReplyActivity.class,
								new BasicNameValuePair("uname", username));
					} else {
						showShortToast("关注后可发送消息");
					}					
				} else {
					showShortToast("登陆加关注后可发送消息");
				}
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
<<<<<<< HEAD
		if(NetWorkHelper.checkNetState(this)) {
			String url2 = String.format(Urls.USER_OBSERVER+"?uname=%s&username=%s&check=%s", 
					share.getString(UserLoginActivity.UID, ""),username,true);
			checkObserverTask = new CheckObserverTask();
			checkObserverTask.execute(url2);
			String urlString = String.format(Urls.USER_INFO, username);
			dataTask = new DataAsyncTask();
			dataTask.execute(urlString);
			String urlString2 = String.format(Urls.USER_RECENTLY_PUBLISH, username);
			pubTask = new PubAsyncTask();
			pubTask.execute(urlString2);
			if(!listobservers.isEmpty()) {
				listobservers.clear();
			}
			if(!listfans.isEmpty()) {
				listfans.clear();
			}
			String url1 = String.format(Urls.USER_OBSERVER+"?uname=%s", username);	
			checkNumTask = new CheckObserverNumTask();
			checkNumTask.execute(url1);
			String url3 = String.format(Urls.USER_OBSERVER+"?username=%s", username);	
			checkFanTask = new CheckFanTask();
			checkFanTask.execute(url3);
		} else {
			showShortToast("网络连接问题，请稍后再试");
		}
		
=======
		if(!listobservers.isEmpty()) {
			listobservers.clear();
		}
		if(!listfans.isEmpty()) {
			listfans.clear();
		}
		String url1 = String.format(Urls.USER_OBSERVER+"?uname=%s", username);	
		new CheckObserverNumTask().execute(url1);
		String url3 = String.format(Urls.USER_OBSERVER+"?username=%s", username);	
		new CheckFanTask().execute(url3);
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
	}

	private void initSharedPreference() {
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);
		if(share.getString(UserLoginActivity.UID, "").equals(username)) {
			observ.setVisibility(View.GONE);
			sendmsg.setVisibility(View.GONE);
		}
	}

	private void initControl() {
		// TODO Auto-generated method stub
		userHead = (ImageView) findViewById(R.id.user_imageview_icon);
		imgFile = (ImageView) findViewById(R.id.iv_user_info_img);
		txtName = (TextView) findViewById(R.id.user_textview_name);
		txtRegTime = (TextView) findViewById(R.id.user_textview_reg_time);
		txtEP = (TextView) findViewById(R.id.user_textview_e_p);
		txtEM = (TextView) findViewById(R.id.user_textview_e_m);
		level = (TextView) findViewById(R.id.user_textview_level);
//		brand = (TextView) findViewById(R.id.user_textview_img);
		zone = (TextView) findViewById(R.id.user_textview_zone);
		content = (TextView) findViewById(R.id.user_textView_add);
		observers = (TextView) findViewById(R.id.user_textview_observers);
<<<<<<< HEAD
		fans = (TextView) findViewById(R.id.user_textview_fans); 
=======
		fans = (TextView) findViewById(R.id.user_textview_fans);
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		observ = (Button) findViewById(R.id.button_add_user);
		sendmsg = (Button) findViewById(R.id.button_send_message);
		gohome = (ImageView) findViewById(R.id.details_imageview_gohome);
		title = (TextView) findViewById(R.id.tv_detail_title);
	}

	private void getStringExtra(Intent intent) {
		headimage = intent.getStringExtra("userhead");
		username = intent.getStringExtra("username");
		
		txtName.setText(username);
		if(!headimage.equals("")) {
			ImageUtil.setThumbnailView(headimage, this.userHead, this, callback2, true);
		}		
	}
	
	ImageCallback callback2 = new ImageCallback() {
		
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) userHead.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};
	
	ImageCallback callback3 = new ImageCallback() {

		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) imgFile.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};
	
	class DataAsyncTask extends AsyncTask<String, Void, UserInfoItem> {

		private String result;
		private UserInfoItem userinfo;
<<<<<<< HEAD
		private volatile boolean running = true;
		@Override
		protected UserInfoItem doInBackground(String... params) {
			if(isCancelled()) {
				return null;
			}
=======
		@Override
		protected UserInfoItem doInBackground(String... params) {
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			try {
				result = HttpUtils.getByHttpClient(UserInfoActivity.this, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				userinfo = new ObjectMapper().readValue(result, UserInfoItem.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return userinfo;
		}
<<<<<<< HEAD
		
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			showShortToast("获取数据失败,请稍后再试");
		}


=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		@Override
		protected void onPostExecute(UserInfoItem result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				txtRegTime.setText(result.getRegtime());
				txtEM.setText("v币："+result.getTm());
				txtEP.setText("v望："+result.getTp());
				zone.setText("地区: "+result.getProvince()+result.getCity());
				if(result.getTp()>=100) {
					level.setText(R.string.user_center_lecturer);
				} else if(result.getTp()>=50) {
					level.setText(R.string.user_center_assistant);
				}
<<<<<<< HEAD
			} else {
				showShortToast("获取数据失败,请稍后再试");
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			}
		}
		
	}
	
	class PubAsyncTask extends AsyncTask<String, Void, PubContent> {

		private String result;
		@Override
		protected PubContent doInBackground(String... params) {
<<<<<<< HEAD
			if(isCancelled()) {
				return null;
			}
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			try {
				result = HttpUtils.getByHttpClient(UserInfoActivity.this, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pubContent = new ObjectMapper().readValue(result, PubContent.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pubContent;
		}
		
		@Override
<<<<<<< HEAD
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		protected void onPostExecute(PubContent result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				content.setText(result.getContent());
				if(result.getImageFile()!=null) {
					ImageUtil.setThumbnailView(result.getImageFile(), UserInfoActivity.this.imgFile,
							UserInfoActivity.this, callback3, true);
				}
				
				content.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						IntentUtil.start_activity(UserInfoActivity.this, UserPubContentsActivity.class, 
								new BasicNameValuePair("uname", pubContent.getUsername()));
					}
				});
				
			}
		}		
	}
	
	/**
	 * @author Jason
	 *查询关注者数量
	 */
	class CheckObserverNumTask extends AsyncTask<String, Void, Integer> {

		private String result;
		@Override
		protected Integer doInBackground(String... params) {
<<<<<<< HEAD
			if(isCancelled()) {
				return null;
			}
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			try {
				result = HttpUtils.getByHttpClient(UserInfoActivity.this, params[0]);
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Observer observer = new ObjectMapper().readValue(json, Observer.class);
					listobservers.add(observer);
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listobservers.size();
		}
<<<<<<< HEAD
		
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			observers.setText("关注："+listobservers.size());
			observers.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					IntentUtil.start_activity(
							UserInfoActivity.this, UserObserverActivity.class,
							new BasicNameValuePair("uname", username));
				}
			});
		}


=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				observers.setText("关注："+result);
				observers.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						IntentUtil.start_activity(
								UserInfoActivity.this, UserObserverActivity.class,
								new BasicNameValuePair("uname", username));
					}
				});
				
			}
		}		
	}
	
	class AddObserverTask extends AsyncTask<String, Void, String> {
<<<<<<< HEAD
		
		private String result;
		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()) {
				return null;
			}
=======

		private String result;
		@Override
		protected String doInBackground(String... params) {
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			try {
				result = HttpUtils.getByHttpClient(UserInfoActivity.this, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
<<<<<<< HEAD
		
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}


=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				showShortToast(result);
				observ.setText("已关注");
				fans.setText("粉丝： "+(total+1));
			} else {
				showShortToast("网络问题，请稍后再试");
			}
		}		
	}
	
	/**
	 * @author Jason
	 *检查是否已关注
	 */
	class CheckObserverTask extends AsyncTask<String, Void, Integer> {

		private String result;
		@Override
		protected Integer doInBackground(String... params) {
<<<<<<< HEAD
			if(isCancelled()) {
				return null;
			}
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			try {
				result = HttpUtils.getByHttpClient(UserInfoActivity.this, params[0]);
					
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Integer.parseInt(result);
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==1) {
<<<<<<< HEAD
				observ.setText("已关注");	
				
			} else {
				observ.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(share.contains(UserLoginActivity.KEY)) {
							if(!observ.getText().toString().equals("已关注")) {
								String urlString = String.format(Urls.USER_OBSERVER+
										"?uname=%s&username=%s&headimage=%s&headimg=%s&add=%s",
										share.getString(UserLoginActivity.UID, ""), username, headimage,
										share.getString(UserLoginActivity.PIC, ""), "true");
								addTask = new AddObserverTask();
								addTask.execute(urlString);
							} 
						} else {
							showShortToast("登陆后可关注");
						}
					}
				});
=======
				observ.setText("已关注");				
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			}
		}		
	}
	
	class CheckFanTask extends AsyncTask<String, Void, Integer> {

		private String result;
		@Override
		protected Integer doInBackground(String... params) {
<<<<<<< HEAD
			if(isCancelled()) {
				return null;
			}
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			try {
				result = HttpUtils.getByHttpClient(UserInfoActivity.this, params[0]);
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
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
			total = listfans.size();
			return total;
		}
<<<<<<< HEAD
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			fans.setText("粉丝："+total);
			fans.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					IntentUtil.start_activity(
							UserInfoActivity.this, UserFanActivity.class,
							new BasicNameValuePair("uname", username));
				}
			});
		}

=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				fans.setText("粉丝："+result);
				fans.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						IntentUtil.start_activity(
								UserInfoActivity.this, UserFanActivity.class,
								new BasicNameValuePair("uname", username));
					}
				});
				
			}
		}		
	}
<<<<<<< HEAD

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dataTask != null && dataTask.getStatus() == AsyncTask.Status.RUNNING) {
			dataTask.cancel(true);
		}
		if (pubTask != null && pubTask.getStatus() == AsyncTask.Status.RUNNING) {
			pubTask.cancel(true);
		}
		if (checkNumTask != null && checkNumTask.getStatus() == AsyncTask.Status.RUNNING) {
			checkNumTask.cancel(true);
		}
		if (checkObserverTask != null && checkObserverTask.getStatus() == AsyncTask.Status.RUNNING) {
			checkObserverTask.cancel(true);
		}
		if (checkFanTask != null && checkFanTask.getStatus() == AsyncTask.Status.RUNNING) {
			checkFanTask.cancel(true);
		}
		if (addTask != null && addTask.getStatus() == AsyncTask.Status.RUNNING) {
			addTask.cancel(true);
		}
	}
	
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
}
