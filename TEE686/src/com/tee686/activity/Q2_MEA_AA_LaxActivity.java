package com.tee686.activity;

import com.casit.tee686.R;

import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.VideoView;

import java.io.IOException;

public class Q2_MEA_AA_LaxActivity extends Activity {
	private VideoView vv;
    private MediaPlayer mp = null;
//	private ScaleGestureDetector mScaleGestureDetector = null;
    private PopupWindow structure = null;
    private View dialogView1 = null;
    private View dialogView2 = null;
    private View dialogView3 = null;
    private VideoView vv1 = null;
//    private SurfaceHolder surfaceHolder;
//    private MediaPlayer mp;
    private VideoView vv2 = null;
    private VideoView vv3 = null;
    private Dialog dialog1;
    private Dialog dialog2;
    private Dialog dialog3;
    private GestureDetector gestureDetector = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q2_mea_aa_lax);
//        mScaleGestureDetector = new ScaleGestureDetector(this,new ScaleGestureListener());

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
        ImageButton ib = (ImageButton)findViewById(R.id.q2_mea_aa_lax_ib);

        dialogView1 = getLayoutInflater().inflate(R.layout.structure1_mea_aa_lax, null);
        if (dialogView1 != null) {
            vv1 = (VideoView) dialogView1.findViewById(R.id.mea_aa_lax_vv_structure1);
        }
        vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));

        dialogView2 = getLayoutInflater().inflate(R.layout.structure2_mea_aa_lax, null);
        if (dialogView2 != null) {
            vv2 = (VideoView) dialogView2.findViewById(R.id.mea_aa_lax_vv_structure2);
        }
        vv2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));

        dialogView3 = getLayoutInflater().inflate(R.layout.structure3_mea_aa_lax, null);
        if (dialogView3 != null) {
            vv3 = (VideoView) dialogView3.findViewById(R.id.mea_aa_lax_vv_structure3);
        }
        vv3.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));

        dialog1 = new Dialog(Q2_MEA_AA_LaxActivity.this);
        dialog1.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(dialogView1);
        dialog2 = new Dialog(Q2_MEA_AA_LaxActivity.this);
        dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(dialogView2);
        dialog3 = new Dialog(Q2_MEA_AA_LaxActivity.this);
        dialog3.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog3.setContentView(dialogView3);

       /* structure = new PopupWindow(dialogView);
        iv1 = (ImageView)dialogView.findViewById(R.id.border_structure1);
        sv1 = (SurfaceView)dialogView.findViewById(R.id.mea_aa_lax_vv_structure1);*/
       /* surfaceHolder = sv1.getHolder();    //SurfaceHolder是SurfaceView的控制接口
        surfaceHolder.addCallback(this);  //因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
        surfaceHolder.setFixedSize(350, 286);   //显示的分辨率,不设置为视频默认
        surfaceHolder.setKeepScreenOn(true);    //设置屏幕长亮*/
        gestureDetector = new GestureDetector(this,new GestureDetector.OnGestureListener() {
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
//                    Q2_MEA_AA_LaxActivity.this.getWindowManager().removeView(dialogView);
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
        });


		s1.setOnClickListener(new OnClickListener(){

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
                //设置弹出dialog后窗口不变暗
                Window window = dialog1.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount =0f;
                window.setAttributes(lp);

                //PopupWindow结合SurfaceView实现
                /*if(structure.isShowing()) {
                    structure.update(0,15,sv1.getWidth(),sv1.getHeight());
                } else {
                    structure.showAtLocation(vv, Gravity.CENTER,0,15);
                    structure.update(0,15,sv1.getWidth(),sv1.getHeight());
                }*/
                /*try{
                    mp.start();
                    mp.setLooping(true);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }*/

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
			}});

        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if( mp == null || !mp.isPlaying()) {
                    mp = MediaPlayer.create(Q2_MEA_AA_LaxActivity.this, R.raw.gangnam_style);
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
		vv = (VideoView)findViewById(R.id.vv_q2_mea_aa_lax);
		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mea1));
		vv.setOnCompletionListener(onCompListener);
		vv.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{   //返回给GestureDetector来处理
        boolean result = gestureDetector.onTouchEvent(event);

        if(!result){
            if(event.getAction()==MotionEvent.ACTION_UP){

				/*if(!isControllerShow){
					showController();
					hideControllerDelay();
				}else {
					cancelDelayHide();
					hideController();
				}*/
            }
            result = super.onTouchEvent(event);
        }

        return result;
//        return mScaleGestureDetector.onTouchEvent(event);
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(mp != null) {
                mp.release();
            }
            finish();
            overridePendingTransition(R.anim.hold, R.anim.q2_zoomout);
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
        //Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mp=new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDisplay(surfaceHolder);
        //设置显示视频显示在SurfaceView上
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
			//一定要返回true才会进入onScale()这个函数
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
      //判断sd卡是否存在 
        boolean sdCardExist = Environment.getExternalStorageState()   
                            .equals(android.os.Environment.MEDIA_MOUNTED);   
        if(sdCardExist){
              //获取根目录 
          sdDir = Environment.getExternalStorageDirectory();
        }   
        return sdDir.toString(); 
    } */
}
