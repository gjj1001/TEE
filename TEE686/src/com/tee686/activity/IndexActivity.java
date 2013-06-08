package com.tee686.activity;

import com.casit.tee686.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.ImageButton;

public class IndexActivity extends Activity {

    private OnClickListener enterListener = new OnClickListener()
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(IndexActivity.this, Section686Activity.class); 
	        startActivity(intent); 
		}
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
        ImageButton btn_enter = (ImageButton)findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(enterListener);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

}
