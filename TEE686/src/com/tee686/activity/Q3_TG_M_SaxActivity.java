package com.tee686.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.casit.tee686.R;

public class Q3_TG_M_SaxActivity extends Activity{

	private VideoView vv;
    private ImageButton ib;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q1_tg_m_sax);
		
		MediaController mc = new MediaController(this);  
		vv = (VideoView)findViewById(R.id.vv_q1_tg_m_sax);

		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q3_tg_m_sax));

		vv.setMediaController(mc);
		vv.setOnCompletionListener(onCompListener);
		vv.start();

        ib = (ImageButton)findViewById(R.id.tg_m_sax_btn);
        ib.setVisibility(View.GONE);
	}
	
	OnCompletionListener onCompListener = new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q3_tg_m_sax));

			vv.start();
		}		
	};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            /*Intent intent = new Intent(this, Section686Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            startActivity(intent);*/
            finish();
            overridePendingTransition(R.anim.hold, R.anim.q1_zoomout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*private String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                            .equals(android.os.Environment.MEDIA_MOUNTED);   
        if(sdCardExist){
              //��ȡ��Ŀ¼ 
          sdDir = Environment.getExternalStorageDirectory();
        }   
        return sdDir.toString(); 
    } */
    
}
