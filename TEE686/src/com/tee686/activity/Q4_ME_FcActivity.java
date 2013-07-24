package com.tee686.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.casit.tee686.R;

public class Q4_ME_FcActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q4_me_fc);
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.hold, R.anim.q4_zoomout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
