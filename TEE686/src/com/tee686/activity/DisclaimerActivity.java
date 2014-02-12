package com.tee686.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.casit.tee686.R;
import com.tee686.ui.base.BaseActivity;

//import android.widget.Button;

public class DisclaimerActivity extends BaseActivity {	
       
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.disclaimer);     
		//设置activity窗口属性
        LayoutParams p = getWindow().getAttributes();
        p.height = (int)(getWindowManager().getDefaultDisplay().getHeight() * 1.0);
        p.width = (int)(getWindowManager().getDefaultDisplay().getWidth() * 0.75);        
        p.dimAmount = 0f;
        getWindow().setAttributes(p);        
        getWindow().setGravity(Gravity.CENTER); 
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
//			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}	
    
}
