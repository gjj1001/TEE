package com.tee686.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.Observer;
import com.tee686.https.HttpUtils;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.IntentUtil;
import com.tee686.utils.ImageUtil.ImageCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UserFanActivity extends BaseActivity {
	
	private ListView lv;
	private MyAdapter mAdapter;
	private Button refresh;
	private LinearLayout listContent;
	private LinearLayout loadFailed;
	private TextView title;
	private ImageView goback;
	
	private String url1;
	private String result;
	
	SharedPreferences share;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);
		initControl();
		initSharedPreference();
		title.setBackgroundResource(0);
		title.setText("全部粉丝");
		intent = getIntent();
		if(savedInstanceState!=null) {
			result = savedInstanceState.getString("result");
			List<Observer> observers = new ArrayList<Observer>();
			try{
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Observer observer = new ObjectMapper().readValue(json, Observer.class);
					observers.add(observer);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			if(observers!=null) {
				mAdapter = new MyAdapter(observers);
	//			mAdapter.appendToList(result);		
				lv.setAdapter(mAdapter);
				lv.setOnItemClickListener(new OnItemClickListener() {
	
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Observer observer = (Observer) mAdapter.getItem(position);
						IntentUtil.startActivity(UserFanActivity.this, UserInfoActivity.class,
								new BasicNameValuePair("userhead", observer.getHeadimg()),
								new BasicNameValuePair("username", observer.getUname()),
								new BasicNameValuePair("position", String.valueOf(position)));
					}
				});			 
				
				listContent.setVisibility(View.VISIBLE);
				loadFailed.setVisibility(View.GONE);
	//			result.clear();
			} else {
				showShortToast("获取数据失败");
				listContent.setVisibility(View.GONE);
				loadFailed.setVisibility(View.VISIBLE);
			}
		} else {
			url1 = String.format(Urls.USER_OBSERVER+"?username=%s", intent.getStringExtra("uname"));
			new DataAsyncTask().execute(url1);
		}		
		
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.umeng_fb_slide_in_from_left,
						R.anim.umeng_fb_slide_out_from_right);
			}
		});
		
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DataAsyncTask().execute(url1);
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("result", result);
	}

	private void initSharedPreference() {
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);
	}

	private void initControl() {
		lv = (ListView) findViewById(R.id.lv_community_content);
		refresh = (Button) findViewById(R.id.bn_refresh);
		listContent = (LinearLayout) findViewById(R.id.list_content);
		loadFailed = (LinearLayout) findViewById(R.id.view_load_fail);
		goback = (ImageView) findViewById(R.id.details_imageview_gohome);
		title = (TextView) findViewById(R.id.tv_detail_title);
	}
	
	/**
	 * @author Jason
	 *获得列表数据
	 */
	class DataAsyncTask extends AsyncTask<String, Void, List<Observer>> {
		private List<Observer> observers = new ArrayList<Observer>();
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showAlertDialog("温馨提示", "正在加载列表信息请稍后");
		}

		@Override
		protected List<Observer> doInBackground(String... params) {			
			
			try {				
				result = HttpUtils.getByHttpClient(UserFanActivity.this, params[0]);					
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Observer observer = new ObjectMapper().readValue(json, Observer.class);
					observers.add(observer);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*for(PubContent pubContent : mPubContents) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userhead", pubContent.getHeadimage());
				map.put("bulletincontent", pubContent.getContent());
				map.put("username", pubContent.getUsername());
				map.put("image", pubContent.getImageFile());
				map.put("sendtime", pubContent.getSendtime());
				mlist.add(map);
			}*/ 
			
			
			return observers;
		}

		@Override
		protected void onPostExecute(List<Observer> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAlertDialog.dismiss();
			if(result!=null) {
				mAdapter = new MyAdapter(result);
//				mAdapter.appendToList(result);		
				lv.setAdapter(mAdapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Observer observer = (Observer) mAdapter.getItem(position);
						IntentUtil.startActivity(UserFanActivity.this, UserInfoActivity.class,
								new BasicNameValuePair("userhead", observer.getHeadimg()),
								new BasicNameValuePair("username", observer.getUname()),
								new BasicNameValuePair("position", String.valueOf(position)));
					}
				});
								
				listContent.setVisibility(View.VISIBLE);
				loadFailed.setVisibility(View.GONE);
//				result.clear();
			} else {
				showShortToast("获取数据失败");
				listContent.setVisibility(View.GONE);
				loadFailed.setVisibility(View.VISIBLE);
			}			
		}		
	}
	
	class MyAdapter extends BaseAdapter {
		private List<Observer> observers = new ArrayList<Observer>();

		public MyAdapter() {
		}

		public MyAdapter(List<Observer> observers) {
			this.observers = observers;
			
		}

		/*
		 * public void appendToList(List<PubContent> lists) { if (lists == null)
		 * { return; } pubContents.addAll(lists); notifyDataSetChanged(); }
		 */

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return observers.size();
		}

		@Override
		public Object getItem(int position) {

			return observers.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Observer observer = observers.get(position);
			final ViewHolder viewHolder;
			convertView = null;
			convertView = getLayoutInflater().inflate(
					R.layout.userlist_adapter, null);
			viewHolder = new ViewHolder();
			viewHolder.ivHeadimage = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tv_pub_username);
			convertView.setTag(viewHolder);
			/*
			 * else { viewHolder = (ViewHolder) convertView.getTag(); }
			 */
			viewHolder.tvUsername.setText(observer.getUname());
			// viewHolder.ivHeadimage.setTag(pubContent.getHeadimage());
			// viewHolder.ivImagefile.setTag(pubContent.getImageFile());
			synchronized (viewHolder) {
				if (!"".equals(observer.getHeadimg())) {
					// new
					// HeadImgAsyncTask().execute(pubContent.getHeadimage());
					ImageUtil.setThumbnailView(observer.getHeadimg(),
							viewHolder.ivHeadimage, UserFanActivity.this,
							callback1, true);
				}
			}
			/*
			 * viewHolder.ivImagefile.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub viewHolder.ivImagefile.setImageResource(R.drawable.
			 * umeng_update_close_bg_normal); } });
			 */

			return convertView;
		}

		private class ViewHolder {
			public ImageView ivHeadimage;
			public TextView tvUsername;
		}
		
		ImageCallback callback1 = new ImageCallback() {
			
			@Override
			public void loadImage(Bitmap bitmap, String imagePath) {
				// TODO Auto-generated method stub
				try {
					ImageView img = (ImageView) lv.findViewWithTag(imagePath);
					img.setImageBitmap(bitmap);
				} catch (NullPointerException ex) {
					Log.e("error", "ImageView = null");
				}
			}
		};
	}
}
