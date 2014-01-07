package com.tee686.activity;

import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Intent;
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
import com.tee686.entity.PubContent;
import com.tee686.entity.UserInfoItem;
import com.tee686.https.HttpUtils;
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
	private Button button;
	
	private String headimg;
	private String username;
	private PubContent pubContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initControl();
		title.setText("个人资料");
		Intent intent = getIntent();
		getStringExtra(intent);
		String urlString = String.format(Urls.USER_INFO, username);
		new DataAsyncTask().execute(urlString);
		String urlString2 = String.format(Urls.USER_RECENTLY_PUBLISH, username);
		new PubAsyncTask().execute(urlString2);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		gohome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.umeng_fb_slide_in_from_left, R.anim.umeng_fb_slide_out_from_right);
            }
        });		
		
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
		button = (Button) findViewById(R.id.button_add_user);
		gohome = (ImageView) findViewById(R.id.details_imageview_gohome);
		title = (TextView) findViewById(R.id.details_textview_title);
	}

	private void getStringExtra(Intent intent) {
		headimg = intent.getStringExtra("userhead");
		username = intent.getStringExtra("username");
		
		txtName.setText(username);
		if(!headimg.equals("")) {
			ImageUtil.setThumbnailView(headimg, this.userHead, this, callback2, true);
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
		@Override
		protected UserInfoItem doInBackground(String... params) {
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
			}
		}
		
	}
	
	class PubAsyncTask extends AsyncTask<String, Void, PubContent> {

		private String result;
		@Override
		protected PubContent doInBackground(String... params) {
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
}
