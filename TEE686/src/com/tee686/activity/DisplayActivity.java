package com.tee686.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.casit.tee686.R;
import com.tee686.db.DBHelper;
import com.tee686.db.PushCacheColumn;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;

public class DisplayActivity extends InstrumentedActivity {

	public static final String PUSH = "pushNews";
	public static final String NOTIFICATION_TITLE = "ntitle";
	public static final String TITLE = "title";
	public static final String ALERT = "alert";
	public static final String MESSAGE = "message";
	public static final String RICHPUSH_HTML_PATH = "path";
	public static final String Timestamp = "timestamp";
	
	private static String mimeType = "text/html";
	private static String encoding = "utf-8";
	private static String linkCss = "<link rel=\"stylesheet\" href=\"file:///android_asset/pygments.css\" type=\"text/css\"/>";
	private String notification;
	private String message;
	Bundle bundle;
	WebView mWebView;	
	ImageView imgGoHome;
	SharedPreferences share;
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);
		initWebView();
		imgGoHome = (ImageView) findViewById(R.id.details_imageview_gohome);
        imgGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.umeng_fb_slide_in_from_left, R.anim.umeng_fb_slide_out_from_right);
            }
        });
		if(null!=savedInstanceState) {
			bundle = savedInstanceState;
		} else {
			Intent intent = getIntent();
			bundle = intent.getExtras();
		}		
		if(null != bundle) {			
			notification = bundle.getString(JPushInterface.EXTRA_ALERT);
			message = bundle.getString(JPushInterface.EXTRA_MESSAGE);	
			bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
			new StoreDataAsyncTask().execute();
			if(null != notification) {
				String content = linkCss + notification;
				mWebView.loadDataWithBaseURL(null, content, mimeType, encoding, null);
			} else if(null != message) {			
	            String content = linkCss + message;
	            mWebView.loadDataWithBaseURL(null, content, mimeType, encoding, null);
			} else {
				mWebView.setBackgroundResource(R.layout.load_failed_layout);
			}
		} else {
			share = getSharedPreferences(PUSH, MODE_PRIVATE);
			if(!"".equals(share.getString(NOTIFICATION_TITLE, ""))) {
				String content = linkCss + share.getString(ALERT, "");
				mWebView.loadDataWithBaseURL(null, content, mimeType, encoding, null);
			} else if(!"".equals(share.getString(MESSAGE, ""))) {
				String content = linkCss + share.getString(MESSAGE, "");
				mWebView.loadDataWithBaseURL(null, content, mimeType, encoding, null);
			} else {
				mWebView.setBackgroundResource(R.layout.load_failed_layout);
			}
		}			        
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState = bundle;
	}

	/**
	 * 设置webview各项参数
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		mWebView = (WebView) findViewById(R.id.detail_webView);
		mWebView.setBackgroundColor(0);
        mWebView.setBackgroundResource(R.color.detail_bgColor);
        mWebView.getSettings().setLoadsImagesAutomatically(true);        
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.umeng_fb_slide_in_from_left, R.anim.umeng_fb_slide_out_from_right);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}	
	
	class StoreDataAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);			
//			db = SQLiteDatabase.openOrCreateDatabase("/data/data/"+getPackageName()+"/"+DatabaseColumn.DATABASE_NAME, null);
			//把推送参数存入设备sqlite数据库
			DBHelper dbHelper = DBHelper.getInstance(DisplayActivity.this);
			ContentValues values = new ContentValues();
			values.put(NOTIFICATION_TITLE, bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
			values.put(TITLE, bundle.getString(JPushInterface.EXTRA_TITLE));
			values.put(ALERT, bundle.getString(JPushInterface.EXTRA_ALERT));
			values.put(MESSAGE, bundle.getString(JPushInterface.EXTRA_MESSAGE));
			values.put(RICHPUSH_HTML_PATH, bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH));
			values.put(Timestamp, format.format(new Date()));
			dbHelper.insert(PushCacheColumn.TABLE_NAME, values);
		}	
		
	}

}
