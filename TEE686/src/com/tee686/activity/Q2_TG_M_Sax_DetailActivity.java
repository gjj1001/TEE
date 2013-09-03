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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.casit.tee686.R;

public class Q2_TG_M_Sax_DetailActivity extends Activity{
    private PopupWindow pw1, pw2, pw3, pw4, pw5, pw6;
    private List<PopupWindow> pwList = new ArrayList<PopupWindow>();
	private boolean[] flag = new boolean[6];
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP && pw1.isShowing()) {
            pw1.dismiss();
        }

        return super.onTouchEvent(event);
    }*/

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.q3_tg_m_sax);
				
		final ImageView iv = (ImageView)findViewById(R.id.q3_tg_m_sax_iv);        
        final View view1 = getLayoutInflater().inflate(R.layout.q2_pw_tg_m_sax_alpm, null);
        final View view2 = getLayoutInflater().inflate(R.layout.q2_pw_tg_m_sax_i, null);
        final View view3 = getLayoutInflater().inflate(R.layout.q2_pw_tg_m_sax_a, null);
        final View view4 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_rv, null);
        final View view5 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_lv, null);
        final View view6 = getLayoutInflater().inflate(R.layout.q2_pw_tg_m_sax_pmpm, null);
       
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
        pw6 = new PopupWindow(view6);
        pw6.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));

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
                	pw1.showAtLocation(iv, Gravity.CENTER, -200, -20);
                    pw1.update(220,60);
                    pw2.showAtLocation(iv, Gravity.CENTER, -220, 70);
                    pw2.update(110, 60);
                    pw3.showAtLocation(iv, Gravity.CENTER, -30, -230);
                    pw3.update(110, 60);
                    pw4.showAtLocation(iv, Gravity.CENTER, 220, 0);
                    pw4.update(160, 60);
                    pw5.showAtLocation(iv, Gravity.CENTER, -30, 0);
                    pw5.update(160, 60);
                    pw6.showAtLocation(iv, Gravity.CENTER, -30, 100);
                    pw6.update(220, 60);
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
		pwList.add(pw6);

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
