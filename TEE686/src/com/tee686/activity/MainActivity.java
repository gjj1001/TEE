package com.tee686.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.casit.tee686.R;

/**
 * Created by Jason on 13-7-5.
 */
public class MainActivity extends Activity {
    private ImageView iv1 = null;
    private ImageView iv2 = null;
    private ImageView iv3 = null;
    private ImageView iv4 = null;
//    private ImageButton ib = null;
//    private Dialog dialog;
//    private View dialogView = null;
//    private VideoView vv =null;
//    private final Timer timer = new Timer();

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*dialogView = getLayoutInflater().inflate(R.layout.demo_index, null);
        if (dialogView != null) {
            vv = (VideoView) dialogView.findViewById(R.id.demo_index_vv);
        }
        vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo));
        dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        vv.start();

        dialog.getWindow().setLayout(this.getWindowManager().getDefaultDisplay().getWidth(),this.getWindowManager().getDefaultDisplay().getHeight());
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount =0f;
        window.setAttributes(lp);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                dialog.dismiss();
                timer.cancel();
            }
        },10000);*/
        

        iv1 = (ImageView)findViewById(R.id.iv_me_lax);
        iv2 = (ImageView)findViewById(R.id.iv_me_fc);
        iv3 = (ImageView)findViewById(R.id.iv_tg_m_sax);
        iv4 = (ImageView)findViewById(R.id.iv_me_rvio);
        
		if(isPayed()) {
			iv4.setImageResource(R.drawable.me_rvio);
		}


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ME_LaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.me_lax_zoomin, R.anim.hold);
            }
        });



        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ME_FcActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.me_fc_zoomin, R.anim.hold);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TG_M_SaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q3_zoomin, R.anim.hold);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ME_RvioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.me_rvio_zoomin, R.anim.hold);
            }
        });

        /*ib = (ImageButton)findViewById(R.id.imgbtn_enter);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Section686Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });*/



    }


	private boolean isPayed() {
		File file = new File(Uri.parse("android.resource://"+getPackageName()+"/values/pay.xml").getPath());
		if(file.exists()) {
			return true;
		}
		return false;
	}


}
