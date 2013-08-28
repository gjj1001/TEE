package com.tee686.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.casit.tee686.R;

public class Q2_ME_Fc_DetailActivity extends Activity{
    private PopupWindow pw1, pw2, pw3, pw4, pw5, pw8;   
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
		this.setContentView(R.layout.q3_me_fc);			
			
        final ImageView iv = (ImageView)findViewById(R.id.q3_me_fc_iv);
        final View view1 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_la, null);
        final View view2 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_al, null);
        final View view3 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_ra, null);
        final View view4 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_rv, null);
        final View view5 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_lv, null);
//        final View view6 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_is, null);
//        final View view7 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_amvl, null);
        final View view8 = getLayoutInflater().inflate(R.layout.q2_pw_me_fc_stvl, null);
        
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
//        pw6 = new PopupWindow(view6);
//        pw6.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
//        pw7 = new PopupWindow(view7);
//        pw7.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
        pw8 = new PopupWindow(view8);
        pw8.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));

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
                } else {
                    pw1.showAtLocation(iv, Gravity.CENTER, 50, -200);
                    pw1.update(160,60);
                    pw2.showAtLocation(iv, Gravity.CENTER, 180, 100);
                    pw2.update(160, 60);
                    pw3.showAtLocation(iv, Gravity.CENTER, -200, -100);
                    pw3.update(160, 60);
                    pw4.showAtLocation(iv, Gravity.CENTER, -100, 120);
                    pw4.update(160, 60);
                    pw5.showAtLocation(iv, Gravity.CENTER, 100, 60);
                    pw5.update(160, 60);
//                    pw6.showAtLocation(iv, Gravity.CENTER, 0, 50);
//                    pw6.update(210, 70);
//                    pw7.showAtLocation(iv, Gravity.CENTER, 50, 20);
//                    pw7.update(220, 60);
                    pw8.showAtLocation(iv, Gravity.CENTER, -60, 30);
                    pw8.update(220, 60);
                }
            }
        });
	}
    /**判断是否有PopupWindow在打开状态，全部都没有打开返回false
     * @return
     */
    protected boolean pwIsShowing() {		
		pwList.add(pw1);
		pwList.add(pw2);
		pwList.add(pw3);
		pwList.add(pw4);
		pwList.add(pw5);
//		pwList.add(pw6);
//		pwList.add(pw7);
		pwList.add(pw8);
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
            /*Intent intent = new Intent(this, Q2_ME_FcActivity.class);
            startActivity(intent);*/        	
            finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	
    
}
