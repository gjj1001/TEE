package com.tee686.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.casit.tee686.R;
import com.tee686.ui.base.BaseActivity;

//import android.widget.Button;

public class Guide2Activity extends BaseActivity {	
    
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide2);     
		iv = (ImageView) findViewById(R.id.iv_guide2);
		//设置activity窗口属性
        LayoutParams p = getWindow().getAttributes();
        p.height = (int)(getWindowManager().getDefaultDisplay().getHeight() * 1.0);
        p.width = (int)(getWindowManager().getDefaultDisplay().getWidth() * 1.0);        
        p.dimAmount = 0f;
        getWindow().setAttributes(p);        
        getWindow().setGravity(Gravity.CENTER); 
        
        Intent intent = new Intent(this, Guide1Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
        
        iv.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					finish();
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					return true;
				}
				return false;
			}
		});
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				finish();
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		}, 10000);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}	
    
}
