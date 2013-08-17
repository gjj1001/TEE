package com.tee686.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.casit.tee686.R;

//import android.widget.Button;

public class IndexActivity extends Activity {

    private OnClickListener enterListener = new OnClickListener()
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(IndexActivity.this, MainActivity.class);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode == KeyEvent.KEYCODE_BACK) {
           final AlertDialog.Builder builder = new AlertDialog.Builder(this);
           final AlertDialog ad = builder.create();
           ad.show();
           builder.setMessage("需要退出应用结束本次学习吗?").setPositiveButton("是", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   IndexActivity.this.finish();
               }
           }).setNegativeButton("否", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   ad.dismiss();
               }
           });
           //dialog.setCancelable(true);
           builder.show();
           return true;
       }
        return super.onKeyDown(keyCode, event);
    }
}
