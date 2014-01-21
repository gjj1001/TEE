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
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.api.JPushInterface;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.Comment;
import com.tee686.entity.Message;
import com.tee686.https.HttpUtils;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.DateUtil;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.ImageUtil.ImageCallback;
import com.tee686.utils.IntentUtil;
import com.tee686.utils.DetailPopupWindow;

public class BulletinDetailActivity extends BaseActivity {

	private ListView bdListView;
	private ImageView userhead;
	private ImageView imgfile;
	private ImageView goHome;
	private TextView username;
	private TextView content;
	private TextView title;
	private EditText editText;
	private Button btSend;
	private ImageButton ok;
	private DetailAdapter adapter;
	private String pubtime;
	private String comtime;
	private String headimage;
	private String uname;
	private String author;
	private String imagefile;
	private String pubContent;
	private String comContent;
	private String result;
	private List<Comment> mComments = new ArrayList<Comment>();
	
	SharedPreferences share;
	Bundle bundle;
	private String userComment;
	private Comment comment;
	Message message = new Message();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bulletin_detail);
		initcontrol();
		initSharePreference();
		title.setText("公告内容");
		ok.setVisibility(View.GONE);
		getFromBundle();			
		userComment = String.format(Urls.USER_COMMENT_DATA, pubtime);
		new DataAsyncTask().execute(userComment);		
		
		goHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(BulletinDetailActivity.this, BulletinActivity.class);
//				intent.putExtra("pos", getIntent().getStringExtra("position"));
//				setResult(RESULT_OK, intent);
				finish();
     			overridePendingTransition(R.anim.hold, R.anim.umeng_fb_slide_out_from_right);
				
			}
		});
		
		btSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!share.contains(UserLoginActivity.UID)) {
					showShortToast("请先登录后再评论");					
				} else if(editText.getText().toString().equals("")) {
					showShortToast("请输入评论内容");
				} else if((comment!=null)?(editText.getText().toString().contains(comment.getUsername())):false) {
					new SendMsgTask().execute(Urls.USER_REPLY);
				} else {
					new CommentAsyncTask().execute(Urls.USER_COMMENT);
				}
			}
		});
		
		if(imagefile!=null) {
			imgfile.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LayoutInflater inflater = LayoutInflater.from(BulletinDetailActivity.this);
					View imgEntryView = inflater.inflate(R.layout.dialog_show_photo, null); // 加载自定义的布局文件
					final AlertDialog dialog = new AlertDialog.Builder(BulletinDetailActivity.this)
							.create();
					ImageView img = (ImageView) imgEntryView.findViewById(R.id.iv_bulletin_detail_photo);
					ImageUtil.setThumbnailView(imagefile, img, BulletinDetailActivity.this, callback3, true);
					dialog.setView(imgEntryView); // 自定义dialog
					dialog.show();
					// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
					imgEntryView.setOnClickListener(new OnClickListener() {
						public void onClick(View paramView) {
							dialog.cancel();
						}
					});
				}
			});			
		}
		
		userhead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtil.start_activity(BulletinDetailActivity.this, UserInfoActivity.class,
						new BasicNameValuePair("userhead", headimage),
						new BasicNameValuePair("username", uname));
			}
		});
	}
	

	/*private void initMessage() {
		message.setBitmap(headimage);
		message.setSend_ctn(pubContent);
		message.setSend_date(comtime);
		message.setSend_person(uname);
	}*/

	private void getFromBundle() {
		Intent intent = getIntent();
        bundle = intent.getExtras();
        if(bundle.containsKey(JPushInterface.EXTRA_NOTIFICATION_TITLE)) {
        	 String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
     		try {
     			JSONObject json = new JSONObject(extras);
     			pubtime = json.optString("pubtime");
     			comtime = json.optString("comtime");
     			headimage = json.optString("headimage");
     			imagefile = json.optString("imagefile");
     			uname = json.optString("author");
     			pubContent = json.optString("pubcontent");
     		} catch (JSONException e) {			
     			e.printStackTrace();
     		}
     		
     		this.username.setText(uname);
    		this.content.setText(pubContent);
    		if(!headimage.equals("")) {
    			ImageUtil.setThumbnailView(headimage, this.userhead, this, callback2, true);
    		}
    		if(imagefile!=null) {
    			ImageUtil.setThumbnailView(imagefile, this.imgfile, this, callback3, true);
    		}
        } else {
        	getStringExtra(intent);
        }
       
	}
	
	private void initSharePreference() {
		// TODO Auto-generated method stub
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);
	}

	/**
	 * @param intent
	 */
	private void getStringExtra(Intent intent) {
		pubtime = intent.getStringExtra("sendtime");
		headimage = intent.getStringExtra("userhead");
		imagefile = intent.getStringExtra("imagefile");
		uname = intent.getStringExtra("username");
		author = uname;
		pubContent = intent.getStringExtra("content");
		
		this.username.setText(uname);
		this.content.setText(pubContent);
		if(!headimage.equals("")) {
			ImageUtil.setThumbnailView(headimage, this.userhead, this, callback2, true);
		}
		if(imagefile!=null) {
			ImageUtil.setThumbnailView(imagefile, this.imgfile, this, callback3, true);
		}
	}

	private void initcontrol() {
		// TODO Auto-generated method stub
		bdListView = (ListView) findViewById(R.id.lv_bulletin_detail);
		userhead = (ImageView) findViewById(R.id.iv_userhead);
		imgfile = (ImageView) findViewById(R.id.iv_imagefile);
		username = (TextView) findViewById(R.id.tv_pub_username);
		content = (TextView) findViewById(R.id.tv_bulletincontent);
		editText = (EditText) findViewById(R.id.comment_content);
		btSend = (Button) findViewById(R.id.sendMsg);
		ok = (ImageButton) findViewById(R.id.ib_bulletin_ok);
		title = (TextView) findViewById(R.id.details_textview_title);
		goHome = (ImageView) findViewById(R.id.details_imageview_gohome);
	}
	
	ImageCallback callback1 = new ImageCallback() {
		
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) bdListView.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};
	
	ImageCallback callback2 = new ImageCallback() {
			
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) userhead.findViewWithTag(imagePath);
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
				ImageView img = (ImageView) imgfile.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};	
	
	class DetailAdapter extends BaseAdapter {

		private List<Comment> comList = new ArrayList<Comment>();
		
		public DetailAdapter(List<Comment> comList) {
			this.comList = comList;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return comList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Comment comment = comList.get(position);
			ViewHolder viewHolder;
			convertView = null;
			convertView = getLayoutInflater().inflate(R.layout.bulletin_detail_adapter, null);
			viewHolder = new ViewHolder();
			viewHolder.ivHeadimage = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_bulletincontent);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tv_pub_username);
			viewHolder.tvSendtime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			convertView.setTag(viewHolder);
			 /*else {
				viewHolder = (ViewHolder) convertView.getTag();
			}*/
			viewHolder.tvContent.setText(comment.getComContent());
			viewHolder.tvSendtime.setText(comment.getComtime());
			viewHolder.tvUsername.setText(comment.getUsername());
//			viewHolder.ivHeadimage.setTag(pubContent.getHeadimage());
//			viewHolder.ivImagefile.setTag(pubContent.getImageFile());
			synchronized (viewHolder) {
				if (!"".equals(comment.getHeadimage())) {
					// new HeadImgAsyncTask().execute(pubContent.getHeadimage());
					ImageUtil.setThumbnailView(comment.getHeadimage(),
							viewHolder.ivHeadimage, BulletinDetailActivity.this, callback1, true);
				}
				
			}		
			viewHolder.ivHeadimage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					IntentUtil.start_activity(BulletinDetailActivity.this, UserInfoActivity.class,
							new BasicNameValuePair("userhead", comment.getHeadimage()), 
							new BasicNameValuePair("username", comment.getUsername()));
				}
			});
			
			return convertView;
		}
		
	}
	
	private class ViewHolder {
		public ImageView ivHeadimage;
		public TextView tvContent;
		public TextView tvUsername;
		public TextView tvSendtime;
	}

	/**
	 * @author Jason
	 *获得列表数据
	 */
	class DataAsyncTask extends AsyncTask<String, Void, List<Comment>> {

//		private HttpURLConnection conn;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			showAlertDialog("温馨提示", "正在加载列表信息请稍后");
		}

		@Override
		protected List<Comment> doInBackground(String... params) {				
			
			try {				
				/*byte[] data = params[1].getBytes("utf-8");
				URL url = new URL(params[0]);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				conn.setReadTimeout(5000);
				conn.setRequestProperty("Content-Type",
						"text/plain; charset=UTF-8");
				conn.setRequestProperty("Content-Length",
						String.valueOf(data.length));
				OutputStream out = conn.getOutputStream();
				out.write(data);
				out.flush();
				out.close();
				if (conn.getResponseCode() == 200) {
					byte[] connbuffer = new byte[1024];
					ByteArrayOutputStream outStream = new ByteArrayOutputStream();
					InputStream in = conn.getInputStream();
					int len = 0;
					while ((len=in.read(connbuffer)) != -1) {
						outStream.write(connbuffer, 0, len);
					}
					result = new String(outStream.toByteArray());
					in.close();
					conn.disconnect();					
				}						*/
				result = HttpUtils.getByHttpClient(BulletinDetailActivity.this, params[0]);	
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Comment comment = new ObjectMapper().readValue(json, Comment.class);
					mComments.add(comment);
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
			
			
			return mComments;
		}

		@Override
		protected void onPostExecute(List<Comment> result) {
			// TODO Auto-generated method stub
			
//			mAlertDialog.dismiss();
			if(!result.isEmpty()) {				
				adapter = new DetailAdapter(result);
				bdListView.setAdapter(adapter);
				bdListView.setSelection(result.size()-1);
//				mAdapter.appendToList(result);	
				if(share.getInt(UserLoginActivity.LEVEL, 0)>=100) {
					bdListView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							comment = (Comment) adapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("回复");
							tabs.add("删除");
							DetailPopupWindow<String> util = new DetailPopupWindow<String>(BulletinDetailActivity.this, 
									 comment, share, adapter, adapter.comList, editText);
							util.showActionWindow(view, tabs);
						}
					});
				} else {
					bdListView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							comment = (Comment) adapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("回复");
							DetailPopupWindow<String> util = new DetailPopupWindow<String>(BulletinDetailActivity.this, 
									 comment, share, adapter, adapter.comList, editText);
							util.showActionWindow(view, tabs);
						}
					});
				}
				
			} else {
				showShortToast("没有评论");				
			}
			
		}
		
	}
	
	class CommentAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			Comment value = new Comment(share.getString(UserLoginActivity.PIC, ""), editText.getText().toString(),
					share.getString(UserLoginActivity.UID, ""), pubtime, DateUtil.getCurrentDateTime(),null, null, null, null);
			return HttpUtils.postByHttpURLConnection(params[0], value);	
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				showShortToast(result);
				editText.setText("");
				mComments.clear();
//				adapter.notifyDataSetChanged();
				new DataAsyncTask().execute(userComment);
				String url = String.format(Urls.USER_LEVEL, share.getString(UserLoginActivity.UID, ""), 1);
				new UpdateTmAsyncTask().execute(url);
			} else {
				showShortToast("网络连接失败，请稍后再试");
			}
			
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			finish();
 			overridePendingTransition(R.anim.hold, R.anim.umeng_fb_slide_out_from_right);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public class UpdateTmAsyncTask extends AsyncTask<String, Void, String> {	
		
		@Override
		protected String doInBackground(String... params) {
			try {
				result = HttpUtils.getByHttpClient(BulletinDetailActivity.this, params[0]);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return result;		
			
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				showShortToast(result);
			}
		}
	}
	
	class SendMsgTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Comment value = new Comment(share.getString(UserLoginActivity.PIC, ""), editText.getText().toString(),
					share.getString(UserLoginActivity.UID, ""), pubtime, DateUtil.getCurrentDateTime(),
					comment.getUsername(), imagefile, pubContent, author);
			return HttpUtils.postByHttpURLConnection(params[0], value);	
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!"".equals(result)) {
				
				editText.setText("");
				mComments.clear();
				showShortToast(result);				
//				adapter.notifyDataSetChanged();
				new DataAsyncTask().execute(userComment);
				//回复v币加1
				String url = String.format(Urls.USER_LEVEL, share.getString(UserLoginActivity.UID, ""), 1);
				new UpdateTmAsyncTask().execute(url);
			} else {
				showShortToast("此用户已注销，暂时不能回复");
			}
		}
		
	}
}
