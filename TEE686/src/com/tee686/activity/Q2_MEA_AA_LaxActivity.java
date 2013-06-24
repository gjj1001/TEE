package com.tee686.activity;

import java.io.File;

import com.casit.tee686.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class Q2_MEA_AA_LaxActivity extends Activity{
	private VideoView vv;
	private ScaleGestureDetector mScaleGestureDetector = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q2_mea_aa_lax);
		mScaleGestureDetector = new ScaleGestureDetector(this,new ScaleGestureListener());
		vv = (VideoView)findViewById(R.id.vv_q2_mea_aa_lax);
		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
		vv.setOnCompletionListener(onCompListener);
		vv.start();
		final LinearLayout layouts1 = (LinearLayout)findViewById(R.id.q2_mea_aa_lax_s1);
		final LinearLayout layouts2 = (LinearLayout)findViewById(R.id.q2_mea_aa_lax_s2);
		final LinearLayout layouts3 = (LinearLayout)findViewById(R.id.q2_mea_aa_lax_s3);
        ImageButton s1 = (ImageButton)findViewById(R.id.q2_mea_aa_lax_btnS1);
        ImageButton s2 = (ImageButton)findViewById(R.id.q2_mea_aa_lax_btnS2);
        ImageButton s3 = (ImageButton)findViewById(R.id.q2_mea_aa_lax_btnS3);
		s1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layouts1.setVisibility(View.VISIBLE);
				layouts2.setVisibility(View.INVISIBLE);
				layouts3.setVisibility(View.INVISIBLE);
			}});
		s2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layouts1.setVisibility(View.INVISIBLE);
				layouts2.setVisibility(View.VISIBLE);
				layouts3.setVisibility(View.INVISIBLE);
			}});
		s3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layouts1.setVisibility(View.INVISIBLE);
				layouts2.setVisibility(View.INVISIBLE);
				layouts3.setVisibility(View.VISIBLE);
			}});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		vv = (VideoView)findViewById(R.id.vv_q2_mea_aa_lax);
		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
		vv.setOnCompletionListener(onCompListener);
		vv.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{   //返回给ScaleGestureDetector来处理         
		return mScaleGestureDetector.onTouchEvent(event); 
	}
	
	public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
	{ 
		@Override
		public boolean onScale(ScaleGestureDetector detector) 
		{  // TODO Auto-generated method stub
			return false;        
		} 
		@Override  
		public boolean onScaleBegin(ScaleGestureDetector detector) 
		{ // TODO Auto-generated method stub   
			//一定要返回true才会进入onScale()这个函数
			Toast.makeText(getApplicationContext(), "scalebegin", Toast.LENGTH_SHORT).show();
			return true;
		}
		@Override
		public void onScaleEnd(ScaleGestureDetector detector)
		{
			Toast.makeText(getApplicationContext(), "scaleend", Toast.LENGTH_SHORT).show();
		}
		
	}

	OnCompletionListener onCompListener = new OnCompletionListener(){
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
			vv.start();
		}		
	};
	
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
