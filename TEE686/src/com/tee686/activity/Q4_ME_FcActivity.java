package com.tee686.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.casit.tee686.R;

public class Q4_ME_FcActivity extends Activity {
	private VideoView vv;
    private MediaPlayer mp = null;
//	private ScaleGestureDetector mScaleGestureDetector = null;
//    private PopupWindow structure = null;
//    private View dialogView1 = null;
//    private View dialogView2 = null;
//    private View dialogView3 = null;
//    private ImageView iv = null;
//    private VideoView vv1 = null;
//    private SurfaceHolder surfaceHolder;
//    private MediaPlayer mp;
//    private VideoView vv2 = null;
//    private VideoView vv3 = null;
//    private Dialog dialog1;
//    private Dialog dialog2;
//    private Dialog dialog3;
//    private GestureDetector gestureDetector = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q2_me_fc);
//        mScaleGestureDetector = new ScaleGestureDetector(this,new ScaleGestureListener());

        vv = (VideoView)findViewById(R.id.vv_q2_me_fc);
		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q4_me_fc));
		vv.setOnCompletionListener(onCompListener);
		vv.start();

        final LinearLayout layouts1 = (LinearLayout)findViewById(R.id.q2_me_fc_s1);
		final LinearLayout layouts2 = (LinearLayout)findViewById(R.id.q2_me_fc_s2);
		final LinearLayout layouts3 = (LinearLayout)findViewById(R.id.q2_me_fc_s3);

		layouts1.setVisibility(View.INVISIBLE);
        layouts2.setVisibility(View.VISIBLE);
        layouts3.setVisibility(View.INVISIBLE);
        
        /*ImageButton s1 = (ImageButton)findViewById(R.id.q2_mea_aa_fc_btnS1);
        ImageButton s2 = (ImageButton)findViewById(R.id.q2_mea_aa_fc_btnS2);
        ImageButton s3 = (ImageButton)findViewById(R.id.q2_mea_aa_fc_btnS3);*/
        ImageButton ib = (ImageButton)findViewById(R.id.q2_me_fc_ib);

       /* dialogView1 = getLayoutInflater().inflate(R.layout.q2_dialog_iv, null);
        if (dialogView1 != null) {
            iv = (ImageView) dialogView1.findViewById(R.id.me_lax_iv_structure1);
            iv.setImageResource(R.drawable.me_fc);
        }*/

        /*dialogView2 = getLayoutInflater().inflate(R.layout.structure2_mea_aa_lax, null);
        if (dialogView2 != null) {
            vv2 = (VideoView) dialogView2.findViewById(R.id.mea_aa_lax_vv_structure2);
        }
        vv2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));

        dialogView3 = getLayoutInflater().inflate(R.layout.structure3_mea_aa_lax, null);
        if (dialogView3 != null) {
            vv3 = (VideoView) dialogView3.findViewById(R.id.mea_aa_lax_vv_structure3);
        }
        vv3.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));*/

        /*dialog1 = new Dialog(Q2_ME_FcActivity.this);
        dialog1.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(dialogView1);*/
        /*dialog2 = new Dialog(Q2_ME_LaxActivity.this);
        dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(dialogView2);
        dialog3 = new Dialog(Q2_ME_LaxActivity.this);
        dialog3.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog3.setContentView(dialogView3);*/

       /* structure = new PopupWindow(dialogView);
        iv1 = (ImageView)dialogView.findViewById(R.id.border_structure1);
        sv1 = (SurfaceView)dialogView.findViewById(R.id.mea_aa_lax_vv_structure1);*/
       /* surfaceHolder = sv1.getHolder();    //SurfaceHolder��SurfaceView�Ŀ��ƽӿ�
        surfaceHolder.addCallback(this);  //��Ϊ�����ʵ����SurfaceHolder.Callback�ӿڣ����Իص�����ֱ��this
        surfaceHolder.setFixedSize(350, 286);   //��ʾ�ķֱ���,������Ϊ��ƵĬ��
        surfaceHolder.setKeepScreenOn(true);    //������Ļ����*/
       /* gestureDetector = new GestureDetector(this,new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if(dialog1.isShowing()) {
                    dialog1.dismiss();
                    dialog2.dismiss();
                    dialog3.dismiss();
//                    Q2_ME_fcActivity.this.getWindowManager().removeView(dialogView);
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                return false;
            }
        });*/
        vv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
            	Intent intent = new Intent(Q4_ME_FcActivity.this, Q4_ME_Fc_DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);                
                return true;
            }
        });

		/*s1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layouts1.setVisibility(View.VISIBLE);
				layouts2.setVisibility(View.INVISIBLE);
				layouts3.setVisibility(View.INVISIBLE);

                vv1.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
                        vv1.start();
                    }
                });
                vv1.start();
                if(dialog2.isShowing()) {
                    dialog2.dismiss();
                } else if(dialog3.isShowing()){
                    dialog3.dismiss();
                }
                dialog1.getWindow().setGravity(Gravity.LEFT);
                dialog1.show();
                //���õ���dialog�󴰿ڲ��䰵
                Window window = dialog1.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount =0f;
                window.setAttributes(lp);

                //PopupWindow���SurfaceViewʵ��
                *//*if(structure.isShowing()) {
                    structure.update(0,15,sv1.getWidth(),sv1.getHeight());
                } else {
                    structure.showAtLocation(vv, Gravity.CENTER,0,15);
                    structure.update(0,15,sv1.getWidth(),sv1.getHeight());
                }*//*
                *//*try{
                    mp.start();
                    mp.setLooping(true);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }*//*

			}});
		s2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layouts1.setVisibility(View.INVISIBLE);
				layouts2.setVisibility(View.VISIBLE);
				layouts3.setVisibility(View.INVISIBLE);

                vv2.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
                        vv2.start();
                    }
                });
                vv2.start();
                if(dialog1.isShowing()) {
                    dialog1.dismiss();
                } else if(dialog3.isShowing()){
                    dialog3.dismiss();
                }
                dialog2.show();
                Window window = dialog2.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount =0f;
                window.setAttributes(lp);
			}});
		s3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layouts1.setVisibility(View.INVISIBLE);
				layouts2.setVisibility(View.INVISIBLE);
				layouts3.setVisibility(View.VISIBLE);

                vv3.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
                        vv3.start();
                    }
                });
                vv3.start();
                if(dialog2.isShowing()) {
                    dialog2.dismiss();
                } else if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
                dialog3.getWindow().setGravity(Gravity.BOTTOM);
                dialog3.show();
                Window window = dialog3.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount =0f;
                window.setAttributes(lp);
			}});*/

        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                

                /*dialog1.getWindow().setGravity(Gravity.CENTER);
                dialog1.show();
                //防止窗口变暗
                Window window = dialog1.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount =0f;
                window.setAttributes(lp);*/

                if( mp == null || !mp.isPlaying()) {
                    mp = MediaPlayer.create(Q4_ME_FcActivity.this, R.raw.audio_me_fc2);
                    try {
//                    mp.prepare();
                        mp.seekTo(0);
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                } else {
                    mp.stop();
                }
            }
        });
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		vv = (VideoView)findViewById(R.id.vv_q2_me_fc);
		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q4_me_fc));
		vv.setOnCompletionListener(onCompListener);
		vv.start();
	}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(mp != null) {
                mp.release();
            }
            finish();
            overridePendingTransition(R.anim.hold, R.anim.q4_zoomout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
// @Override
   /* protected void onDestroy() {
        super.onDestroy();
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        //Activity���ʱֹͣ���ţ��ͷ���Դ�����������������ʹ�˳�����������Ƶ���ŵ�����
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mp=new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDisplay(surfaceHolder);
        //������ʾ��Ƶ��ʾ��SurfaceView��
        try {
            mp.setDataSource(getAssets().openFd("mea1.3gp").getFileDescriptor());
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }*/


   /* public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
	{ 
		@Override
		public boolean onScale(ScaleGestureDetector detector) 
		{  // TODO Auto-generated method stub
			return false;        
		} 
		@Override  
		public boolean onScaleBegin(ScaleGestureDetector detector) 
		{ // TODO Auto-generated method stub   
			Toast.makeText(getApplicationContext(), "scalebegin", Toast.LENGTH_SHORT).show();
			return true;
		}
		@Override
		public void onScaleEnd(ScaleGestureDetector detector)
		{
			Toast.makeText(getApplicationContext(), "scaleend", Toast.LENGTH_SHORT).show();
		}
		
	}*/

	OnCompletionListener onCompListener = new OnCompletionListener(){
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
//			vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
			vv.start();
		}		
	};
	
   /* private String getSDPath(){
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
