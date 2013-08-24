package com.tee686.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.casit.tee686.R;

public class ME_FcActivity extends Activity{

	private VideoView vv;
    private ImageButton ib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q1_me_fc);
				
		vv = (VideoView)findViewById(R.id.vv_q1_me_fc);
		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.me_fc));
		
//		vv.setOnCompletionListener(onCompListener);
		vv.start();

        ib = (ImageButton)findViewById(R.id.me_fc_btn);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ME_FcActivity.this, Section686Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sec", 2);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
	}
	
	/*OnCompletionListener onCompListener = new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.me_fc));

			vv.start();
		}		
	};*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            /*Intent intent = new Intent(this, Section686Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            startActivity(intent);*/
            finish();
            overridePendingTransition(R.anim.hold, R.anim.me_fc_zoomout);
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
    protected void onResume() {
        super.onResume();
        vv = (VideoView)findViewById(R.id.vv_q1_me_fc);
        vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.me_fc));
//        vv.setOnCompletionListener(onCompListener);
        vv.start();
    }
}
