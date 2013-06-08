package com.tee686.activity;

import java.io.File;

import com.casit.tee686.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MEA_AA_LaxActivity extends Activity{

	private static final int DOUBLE_CLICK_TIME = 350;        //双击间隔时间350毫秒
	private boolean waitDouble_v1 = true; 
	private boolean waitDouble_v2 = true; 
	private boolean waitDouble_v3 = true; 
	private VideoView vv1;
	private VideoView vv2;
	private ImageView iv3;
	private Handler meahandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_mea_aa_lax);
		
		meahandler = new Handler()
		{
			@Override  
		    public void handleMessage(Message msg) {
				TextView tv = (TextView)findViewById(R.id.textSketch_mea_aa_lax);
				switch(msg.what)
				{
				case 1:
					tv.setText(R.string.mea_aa_lax_q1text);
					break;
				case 2:
					tv.setText(R.string.mea_aa_lax_q2text);
					break;
				case 3:
					tv.setText(R.string.mea_aa_lax_q3text);
					break;
				default:
					break;
				}
			}   
		};
		
		vv1 = (VideoView)findViewById(R.id.vv1_mea_aa_lax);  
		vv2 = (VideoView)findViewById(R.id.vv2_mea_aa_lax); 
		iv3 = (ImageView)findViewById(R.id.iv3_mea_aa_lax);
		
		vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.xinzang3d));
		vv1.setOnTouchListener(v1Listener);
		vv1.setOnCompletionListener(vv1OnCompListener);
		vv1.seekTo(100);
		vv2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
		vv2.setOnTouchListener(v2Listener);
		vv2.setOnCompletionListener(vv2OnCompListener);
		vv2.seekTo(100);
		iv3.setOnClickListener(v3Listener);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		vv1.seekTo(100);
		vv2.seekTo(100);
		super.onResume();
	}

	OnCompletionListener vv1OnCompListener = new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.xinzang3d));
			//vv1.setVideoPath("file://" + getSDPath() + "/xinzang3d.mp4");
			//vv1.setVideoPath("file:///mnt/extSdCard/xinzang3d.mp4");
			vv1.seekTo(1);
		}		
	};
	
	OnCompletionListener vv2OnCompListener = new OnCompletionListener(){
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
			//vv2.setVideoPath("file://" + getSDPath() + "/mea1.3gp");
			//vv2.setVideoPath("file:///mnt/extSdCard/mea1.3gp");
			vv2.start();
		}		
	};
	
	OnTouchListener v1Listener = new OnTouchListener(){
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			if(waitDouble_v1) {
				waitDouble_v1 = false;        //与执行双击事件
				Thread thread = new Thread() {
					public void run() {
						try {
							Thread.sleep(DOUBLE_CLICK_TIME);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}    //等待双击时间，否则执行单击事件
						if(!waitDouble_v1){
							//如果过了等待事件还是预执行双击状态，则视为单击
							waitDouble_v1 = true;
							onvv1SingleTouch();
						}
					} 
				};
				thread.start();
			}
			else {
				waitDouble_v1 = true;
				onvv1DoubleTouch();    //执行双击				
			}
			return false;
		}
	};
	
	OnTouchListener v2Listener = new OnTouchListener(){
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			if(waitDouble_v2) {
				waitDouble_v2 = false;        //与执行双击事件
				Thread thread = new Thread() {
					public void run() {
						try {
							Thread.sleep(DOUBLE_CLICK_TIME);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}    //等待双击时间，否则执行单击事件
						if(!waitDouble_v2){
							//如果过了等待事件还是预执行双击状态，则视为单击
							waitDouble_v2 = true;
							onvv2SingleTouch();
						}
					} 
				};
				thread.start();
			}
			else {
				waitDouble_v2 = true;
				onvv2DoubleTouch();    //执行双击				
			}
			return false;
		}
	};
	
	//实现 第三象限：切面图 的双击动作
	OnClickListener v3Listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(waitDouble_v3) {
				waitDouble_v3 = false;        //与执行双击事件
				Thread thread = new Thread() {
					public void run() {
						try {
							Thread.sleep(DOUBLE_CLICK_TIME);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}    //等待双击时间，否则执行单击事件
						if(!waitDouble_v3){
							//如果过了等待事件还是预执行双击状态，则视为单击
							waitDouble_v3 = true;
							onQ3SingleClick();
						}
					} 
				};
				thread.start();
			}
			else {
				waitDouble_v3 = true;
				onQ3DoubleClick();    //执行双击				
			}
		}
		
	};
	
	private void onQ3SingleClick() {
		System.out.println("single");
		//Toast.makeText(MEA_AA_LaxActivity.this,"单击事件", Toast.LENGTH_LONG).show();
		//TextView tv = (TextView)findViewById(R.id.textSketch_mea_aa_lax);
		//tv.setText(R.string.mea_aa_lax_q3text);
		meahandler.sendEmptyMessage(3);
		
	}
	
	private void onQ3DoubleClick() {
		System.out.println("double");
		//Toast.makeText(MEA_AA_LaxActivity.this,"双击事件", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(MEA_AA_LaxActivity.this, Q3_MEA_AA_LaxActivity.class); 
        startActivity(intent); 	
	}
	
	private void onvv1SingleTouch(){
		meahandler.sendEmptyMessage(1);		
		if(vv1.isPlaying())
		{
			vv1.pause();
		}
		else
			vv1.start();
	}
	
	private void onvv2SingleTouch(){
		meahandler.sendEmptyMessage(2);
		if(vv2.isPlaying())
		{
			vv2.pause();
		}
		else
			vv2.start();
	}
	
	private void onvv1DoubleTouch(){
		if(vv1.isPlaying())
			vv1.pause();
		Intent intent = new Intent(MEA_AA_LaxActivity.this, Q1_MEA_AA_LaxActivity.class); 
        startActivity(intent);
	}
	
	private void onvv2DoubleTouch(){
		if(vv2.isPlaying())
			vv2.pause();
		Intent intent = new Intent(MEA_AA_LaxActivity.this, Q2_MEA_AA_LaxActivity.class); 
        startActivity(intent);
	}
	
    private String getSDPath(){ 
        File sdDir = null;
      //判断sd卡是否存在 
        boolean sdCardExist = Environment.getExternalStorageState()   
                            .equals(android.os.Environment.MEDIA_MOUNTED);   
        if(sdCardExist){
              //获取根目录 
          sdDir = Environment.getExternalStorageDirectory();
        }   
        return sdDir.toString(); 
    } 
}
