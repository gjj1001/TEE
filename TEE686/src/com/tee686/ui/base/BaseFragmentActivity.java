package com.tee686.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import com.casit.tee686.R;

import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		MobclickAgent.onError(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	}
	
	public void defaultFinish()
	{
		super.finish();
	}
}
