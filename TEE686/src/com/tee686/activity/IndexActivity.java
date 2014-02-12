package com.tee686.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.jpush.android.api.JPushInterface;
import com.casit.tee686.R;
import com.tee686.ui.base.BaseActivity;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

//import android.widget.Button;

public class IndexActivity extends BaseActivity {
	private ImageView imgBrand;		
	protected String mAppId;
	protected String mUserId;
	protected String mChannelId;
	SharedPreferences share;
	
    private OnClickListener enterListener = new OnClickListener()
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(IndexActivity.this, MainActivity.class);
	        startActivity(intent);
//	        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		}
    };	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		initSharedPreference();
		imgBrand = (ImageView)findViewById(R.id.image_brand);
        imgBrand.setOnClickListener(enterListener);	
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.update(this);
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();        
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
        JPushInterface.init(this); 
        /*JPushInterface.setAliasAndTags(this, share.getString(UserLoginActivity.UID, "tee"), null, new TagAliasCallback() {
			
			@Override
			public void gotResult(int code, String alias, Set<String> tags) {
				// TODO Auto-generated method stub
				switch(code) {
				case 0:
					Log.d("alias", "set alias success");
				default:
					Log.d("alias", "errorCode:"+code);	
				}
			}
		});*/
	}

	private void initSharedPreference() {
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);			
		if(!share.contains(UserLoginActivity.KEY)) {
			Intent intent = new Intent(this, Guide2Activity.class);
			startActivity(intent);
//				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub 
		switch(item.getItemId()) {
			case R.id.action_settings:
				/*Dialog dialog = new Dialog(this);
				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.about_me);
				dialog.getWindow().setGravity(Gravity.CENTER);		
				dialog.getWindow().setDimAmount(0);
				dialog.show();*/
				Intent intent = new Intent(IndexActivity.this, InfoActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
				break;
			case R.id.login:
				Intent intent1 = new Intent(IndexActivity.this, UserLoginActivity.class);
				startActivity(intent1);
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
				break;
			case R.id.feedback:						
				FeedbackAgent agent = new FeedbackAgent(this);
				agent.startFeedbackActivity();
		}
		return true;
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode == KeyEvent.KEYCODE_BACK) {
           final AlertDialog.Builder builder = new AlertDialog.Builder(this);
           final AlertDialog ad = builder.create();
           ad.show();
           builder.setMessage("需要退出应用结束本次学习吗?").setPositiveButton("是", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
            	   ad.dismiss();
            	   Intent intent = new Intent(IndexActivity.this, CheckNewService.class);
            	   stopService(intent);
            	   android.os.Process.killProcess(android.os.Process.myPid());
            	   IndexActivity.this.finish();
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
        return super.onKeyDown(keyCode, event);
    }
}
