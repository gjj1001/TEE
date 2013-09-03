package com.tee686.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.casit.tee686.R;

public class Q2_ME_Lax_DetailActivity extends Activity{
    private PopupWindow pw1, pw2, pw3, pw4, pw5;
    private List<PopupWindow> pwList = new ArrayList<PopupWindow>();
	private boolean[] flag = new boolean[5];
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.q3_me_lax);			
				
        final ImageView iv = (ImageView)findViewById(R.id.q3_me_lax_iv);
        final View view1 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_la, null);
        final View view2 = getLayoutInflater().inflate(R.layout.q2_pw_me_lax_av, null);
        final View view3 = getLayoutInflater().inflate(R.layout.q2_pw_me_lax_lvot, null);
        final View view4 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_rv, null);
        final View view5 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_lv, null);
        
        pw1 = new PopupWindow(view1);
        pw1.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
        pw2 = new PopupWindow(view2);
        pw2.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
        pw3 = new PopupWindow(view3);
        pw3.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
        pw4 = new PopupWindow(view4);
        pw4.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
        pw5 = new PopupWindow(view5);
        pw5.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));

      //设置activity窗口属性
        LayoutParams p = getWindow().getAttributes();
        p.height = (int)(getWindowManager().getDefaultDisplay().getHeight() * 1.0);
        p.width = (int)(getWindowManager().getDefaultDisplay().getWidth() * 0.75);        
        p.dimAmount = 0f;
        getWindow().setAttributes(p);        
        getWindow().setGravity(Gravity.LEFT);        

       /* ib1.setLeft(50);
        ib1.setTop(200);*/
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwIsShowing()) {
                	for(PopupWindow pw : pwList) {
                    	pw.dismiss();
                    }
                	pwList.clear();
                } else {
                	pw1.showAtLocation(iv, Gravity.CENTER, -100, -150);
                    pw1.update(160,60);
                    pw2.showAtLocation(iv, Gravity.CENTER, 10, -10);
                    pw2.update(190, 60);
                    pw3.showAtLocation(iv, Gravity.CENTER, -100, 80);
                    pw3.update(220, 60);
                    pw4.showAtLocation(iv, Gravity.CENTER, 100, 120);
                    pw4.update(160, 60);
                    pw5.showAtLocation(iv, Gravity.CENTER, -100, 180);
                    pw5.update(160, 60);
                    pwList.clear();
                }
            }
        });
	}
    protected boolean pwIsShowing() {
    	pwList.add(pw1);
		pwList.add(pw2);
		pwList.add(pw3);
		pwList.add(pw4);
		pwList.add(pw5);

    	for(int i=0; i<flag.length; i++) {
			flag[i] = pwList.get(i).isShowing();
		}
    	for(int i=0; i<flag.length; i++) {
    		if(flag[i] == true) {    			
    			return true;
    		}
    	}
    	return false;
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
