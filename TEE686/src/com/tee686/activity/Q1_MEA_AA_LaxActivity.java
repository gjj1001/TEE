package com.tee686.activity;

import java.io.File;

import com.casit.tee686.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class Q1_MEA_AA_LaxActivity extends Activity{

	private VideoView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q1_mea_aa_lax);
		
		MediaController mc = new MediaController(this);  
		vv = (VideoView)findViewById(R.id.vv_q1_mea_aa_lax);

		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.xinzang3d));

		vv.setMediaController(mc);
        mc.setMediaPlayer(vv);
		vv.setOnCompletionListener(onCompListener);
		vv.start();
	}
	
	OnCompletionListener onCompListener = new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.xinzang3d));

			vv.seekTo(1);
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
