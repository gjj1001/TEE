package com.tee686.activity;

import com.casit.tee686.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Q3_MEA_AA_LaxActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q3_mea_aa_lax);
		
		final Button display = (Button)findViewById(R.id.q3_mea_aa_lax_btn);
		final ImageView iv = (ImageView)findViewById(R.id.q3_mea_aa_lax_iv);
		
		display.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String textStr = display.getText().toString();
				if(textStr.equals("显示所有结构")) {
					display.setText("隐藏所有结构");
					iv.setImageResource(R.drawable.q3_mea_aa_lax_show);
				}
				else if(textStr.equals("隐藏所有结构")) {
					display.setText("显示所有结构");
					iv.setImageResource(R.drawable.q3_mea_aa_lax);
				}
			}			
		});
	}

}
