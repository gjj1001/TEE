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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemLongClickListener;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.Collection;
import com.tee686.entity.PubContent;
import com.tee686.https.HttpUtils;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.IntentUtil;
import com.tee686.utils.ImageUtil.ImageCallback;

public class UserPubContentsActivity extends BaseActivity {

	private ListView lv;
	private ImageView gohome;
	private TextView title;
	private Button refresh;
	private LinearLayout listContent;
	private LinearLayout loadFailed;
	private MyAdapter mAdapter;
	private List<PubContent> mPubContents = new ArrayList<PubContent>();
	
	private String result;
	private String uname;
	SharedPreferences share;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_pub_contents);
		initControl();
		title.setText("个人动态");
<<<<<<< HEAD
		title.setBackgroundResource(0);
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		initSharePreferences();	
		Intent intent = getIntent();
		uname = intent.getStringExtra("uname");
		new CheckCacheTask().execute();
		new DataAsyncTask().execute(Urls.USER_DOAWLOAD_IMAGE+"?username="+uname);
		
		gohome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.umeng_fb_slide_in_from_left, R.anim.umeng_fb_slide_out_from_right);
            }
        });
		
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DataAsyncTask().execute(Urls.USER_DOAWLOAD_IMAGE+"?username="+uname);
			}
		});
	}

	private void initSharePreferences() {
		// TODO Auto-generated method stub
		share = getSharedPreferences(UserLoginActivity.SharedName, Context.MODE_PRIVATE);
	}

	private void initControl() {
		lv = (ListView) findViewById(R.id.lv_pub_contents);
		gohome = (ImageView) findViewById(R.id.details_imageview_gohome);
<<<<<<< HEAD
		title = (TextView) findViewById(R.id.tv_detail_title);
=======
		title = (TextView) findViewById(R.id.details_textview_title);
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		listContent = (LinearLayout) findViewById(R.id.list_content);
		loadFailed = (LinearLayout) findViewById(R.id.view_load_fail);
		refresh = (Button) findViewById(R.id.bn_refresh);
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

	class MyAdapter extends BaseAdapter {

		private List<PubContent> pubContents = new ArrayList<PubContent>();

		public MyAdapter() {
		}

		public MyAdapter(List<PubContent> pubContents) {
			this.pubContents = pubContents;
		}

		/*
		 * public void appendToList(List<PubContent> lists) { if (lists == null)
		 * { return; } pubContents.addAll(lists); notifyDataSetChanged(); }
		 */

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pubContents.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pubContents.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PubContent pubContent = pubContents.get(position);
			ViewHolder viewHolder;
			convertView = null;
			convertView = getLayoutInflater().inflate(R.layout.bulletin_board,
					null);
			viewHolder = new ViewHolder();
			viewHolder.ivHeadimage = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_bulletincontent);
			viewHolder.ivImagefile = (ImageView) convertView
					.findViewById(R.id.iv_imagefile);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tv_pub_username);
			viewHolder.tvSendtime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			convertView.setTag(viewHolder);
			/*
			 * else { viewHolder = (ViewHolder) convertView.getTag(); }
			 */
			viewHolder.tvContent.setText(pubContent.getContent());
			viewHolder.tvSendtime.setText(pubContent.getSendtime());
			viewHolder.tvUsername.setText(pubContent.getUsername());
			// viewHolder.ivHeadimage.setTag(pubContent.getHeadimage());
			// viewHolder.ivImagefile.setTag(pubContent.getImageFile());
			synchronized (viewHolder) {
				if (!"".equals(pubContent.getHeadimage())) {
					// new
					// HeadImgAsyncTask().execute(pubContent.getHeadimage());
					ImageUtil.setThumbnailView(pubContent.getHeadimage(),
							viewHolder.ivHeadimage,
							UserPubContentsActivity.this, callback1, true);
				}
				if (pubContent.getImageFile() != null) {
					ImageUtil.setThumbnailView(pubContent.getImageFile(),
							viewHolder.ivImagefile,
							UserPubContentsActivity.this, callback1, true);

					/*
					 * if(headImage!=null) {
					 * viewHolder.ivHeadimage.setImageBitmap(headImage); }
					 */

				}
			}

			return convertView;
		}

		private class ViewHolder {
			public ImageView ivHeadimage;
			public TextView tvContent;
			public ImageView ivImagefile;
			public TextView tvUsername;
			public TextView tvSendtime;
		}
	}
	
	class CheckCacheTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ImageUtil.checkCache(UserPubContentsActivity.this);
			return null;
		}
		
	}
	
	class DataAsyncTask extends AsyncTask<String, Void, List<PubContent>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showAlertDialog("温馨提示", "正在加载列表信息请稍后");
		}

		@Override
		protected List<PubContent> doInBackground(String... params) {			
			
			try {				
				result = HttpUtils.getByHttpClient(UserPubContentsActivity.this, params[0]);	
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					PubContent pubContent = new ObjectMapper().readValue(json, PubContent.class);
					mPubContents.add(pubContent);
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
			
			
			return mPubContents;
		}

		@Override
		protected void onPostExecute(List<PubContent> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAlertDialog.dismiss();
			if(!result.isEmpty()) {
				mAdapter = new MyAdapter(result);
//				mAdapter.appendToList(result);		
				lv.setAdapter(mAdapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						PubContent pubContent = (PubContent) mAdapter.getItem(position);
						IntentUtil.startActivity(UserPubContentsActivity.this, BulletinDetailActivity.class,
								new BasicNameValuePair("sendtime", pubContent.getSendtime()),
								new BasicNameValuePair("userhead", pubContent.getHeadimage()),
								new BasicNameValuePair("imagefile", pubContent.getImageFile()),
								new BasicNameValuePair("username", pubContent.getUsername()),
								new BasicNameValuePair("content", pubContent.getContent()),
								new BasicNameValuePair("position", String.valueOf(position)));
					}
				});
				lv.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						final PubContent pubContent = (PubContent) mAdapter.getItem(position);
						final AlertDialog.Builder builder = new AlertDialog.Builder(UserPubContentsActivity.this);
				        final AlertDialog ad = builder.create();
				        ad.show();        
				        builder.setMessage("是否要收藏此条公告?").setPositiveButton("是", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialogInterface, int i) {
				         	   ad.dismiss();				         	  
				         	   if(share.contains(UserLoginActivity.UID)) {
				         		   Collection collection = new Collection();
				         		   collection.setContent(pubContent.getContent());
				         		   collection.setHeadimage(pubContent.getHeadimage());
				         		   collection.setImageFile(pubContent.getImageFile());
				         		   collection.setSendtime(pubContent.getSendtime());
				         		   collection.setUsername(pubContent.getUsername());
				         		   collection.setUname(share.getString(UserLoginActivity.UID, ""));
				         		   new CollectionTask().execute(collection);
				         	   } else {
				         		   showShortToast("请先登录后再收藏");
				         	   }
				            }
				        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialogInterface, int i) {
				                ad.dismiss();
				            }
				        });
				        //dialog.setCancelable(true);
				        builder.show();
				        return true;
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
	
	class CollectionTask extends AsyncTask<Collection, Void, String> {

		@Override
		protected String doInBackground(Collection... params) {
			String result = HttpUtils.postByHttpURLConnection(Urls.USER_COLLECTION, params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				showShortToast(result);
			} else {
				showShortToast("网络问题，请稍后再试");
			}
		}
		
	}
}
