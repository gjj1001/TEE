package com.tee686.activity;

import com.casit.tee686.R;
import com.casit.tee686.R.layout;
import com.casit.tee686.R.menu;
import com.tee686.activity.Section686Activity.SpinnerSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Section6641Activity extends Activity {
    private Spinner spinner;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seciton6641);
		
		//View vHeader = inflater.inflate(R.layout.header, null);
		spinner=(Spinner)findViewById(R.id.spinnermenu);
		String[] spinnerStr = getResources().getStringArray(R.array.spinner_list);
		//将可选内容与ArrayAdapter连接起来  
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list, android.R.layout.simple_spinner_item);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerStr);
		//设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter 添加到spinner中  
		spinner.setAdapter(adapter);
		
		spinner.setSelection(1, true);
		//添加事件Spinner事件监听 
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		//设置默认值  
		spinner.setVisibility(View.VISIBLE);
		
	}
	
	@Override
	public void onResume()
	{	
		spinner.setSelection(1,true);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seciton6641, menu);
		return true;
	}
	
	public class SpinnerSelectedListener implements OnItemSelectedListener {
		
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent;
			switch(arg2){
				case 0:
					Toast.makeText(arg0.getContext(),"You choose " 
				+ arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();
					intent = new Intent(Section6641Activity.this, Section686Activity.class); 
					startActivity(intent);
					break;
				case 1:
					Toast.makeText(arg0.getContext(),"You choose " 
				+ arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();
					//intent = new Intent(Section6641Activity.this, Section6641Activity.class); 
			        //startActivity(intent);
					break;
				case 2:
					Toast.makeText(arg0.getContext(),"You choose " 
				+ arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();
					intent = new Intent(Section6641Activity.this, Section2345Activity.class); 
			        startActivity(intent);
					break;
				case 3:
					Toast.makeText(arg0.getContext(),"You choose " 
				+ arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();
					intent = new Intent(Section6641Activity.this, Section223Activity.class); 
			        startActivity(intent);
					break;
				default:
					break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}

	}
}
