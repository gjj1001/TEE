package com.tee686.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.casit.tee686.R;

public class Q3_MEA_AA_LaxActivity extends Activity{
    private PopupWindow pw1 = null;
    private TextView tv1;
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
		this.setContentView(R.layout.q3_mea_aa_lax);
		
		final ImageButton display = (ImageButton)findViewById(R.id.q3_mea_aa_lax_btn);
		final ImageView iv = (ImageView)findViewById(R.id.q3_mea_aa_lax_iv);
		final TextView tv = (TextView)findViewById(R.id.q3_mea_aa_lax_tv);
        final ImageButton ib1 = (ImageButton)findViewById(R.id.q3_mea_aa_lax_ib1);
        final View view1 = getLayoutInflater().inflate(R.layout.structure_popupwindow_text, null);
        tv1 = (TextView)findViewById(R.id.structure_popupWindow_textView);
        pw1 = new PopupWindow(view1);
        pw1.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));

		display.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String textStr = tv.getText().toString();
				if(textStr.equals("显示所有结构")) {
					tv.setText("隐藏所有结构");
					iv.setImageResource(R.drawable.q3_mea_aa_lax_show);
				}
				else if(textStr.equals("隐藏所有结构")) {
					tv.setText("显示所有结构");
					iv.setImageResource(R.drawable.q3_mea_aa_lax);
				}
			}			
		});

        ib1.setLeft(50);
        ib1.setTop(200);
        ib1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pw1.isShowing()) {
                    pw1.dismiss();
                } else {
                    pw1.showAsDropDown(ib1);
                    pw1.update(ib1,400,200);//这里必须要加上update，否则无法显示PopupWindow
//                    pw1.update(0, 15, tv1.getWidth(), tv1.getHeight());
                }
            }
        });
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.hold, R.anim.q3_zoomout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
