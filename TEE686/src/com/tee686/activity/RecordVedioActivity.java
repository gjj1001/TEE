package com.tee686.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.casit.tee686.R;
import com.tee686.https.NetWorkHelper;
import com.tee686.ui.base.BaseActivity;

public class RecordVedioActivity extends BaseActivity{

	private VideoView vv;
    private ImageButton ib;
    private String urlPath;
    private String localPath;
    
    private static final int READY_BUFF = 2000 * 1024;
    private static final int CACHE_BUFF = 500 * 1024;
    
    private boolean isready = false;
    private boolean iserror = false;
    private int errorCnt = 0;
    private int curPosition = 0;
    private long mediaLength = 0;
    private long readSize = 0;

    private final static int VIDEO_STATE_UPDATE = 0;
    private final static int CACHE_VIDEO_READY = 1;
    private final static int CACHE_VIDEO_UPDATE = 2;
    private final static int CACHE_VIDEO_END = 3;
     
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case VIDEO_STATE_UPDATE:
                double cachepercent = readSize * 100.00 / mediaLength * 1.0;
                String s = String.format("已缓存: [%.2f%%]", cachepercent);
     
                if (vv.isPlaying()) {
                    curPosition = vv.getCurrentPosition();
                    int duration = vv.getDuration();
                    duration = duration == 0 ? 1 : duration;
     
                    double playpercent = curPosition * 100.00 / duration * 1.0;
     
                    int i = curPosition / 1000;
                    int hour = i / (60 * 60);
                    int minute = i / 60 % 60;
                    int second = i % 60;
     
                    s += String.format(" 播放: %02d:%02d:%02d [%.2f%%]", hour,
                            minute, second, playpercent);
                }
     
//                tvcache.setText(s);
     
                mHandler.sendEmptyMessageDelayed(VIDEO_STATE_UPDATE, 1000);
                break;
     
            case CACHE_VIDEO_READY:
                isready = true;
                vv.setVideoPath(localPath);
                vv.start();
                break;
     
            case CACHE_VIDEO_UPDATE:
                if (iserror) {
                    vv.setVideoPath(localPath);
                    vv.start();
                    iserror = false;
                }
                break;
     
            case CACHE_VIDEO_END:
                if (iserror) {
                    vv.setVideoPath(localPath);
                    vv.start();
                    iserror = false;
                }
                break;
            }
     
            super.handleMessage(msg);
        }
    };
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q1_me_fc);
		Intent intent = getIntent();
		urlPath = intent.getStringExtra("path");
		localPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/VideoCache/" + System.currentTimeMillis() + ".mp4";
//		MediaController mc = new MediaController(this);  
		vv = (VideoView)findViewById(R.id.vv_q1_me_fc);

		vv.setVideoPath(urlPath);

//		vv.setMediaController(mc);
		vv.setOnCompletionListener(onCompListener);
		vv.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				finish();
				return true;
			}
		});
		vv.start();

        ib = (ImageButton)findViewById(R.id.me_fc_btn);
        ib.setVisibility(View.GONE);
        try {
			if(NetWorkHelper.isMobileDataEnable(this) || NetWorkHelper.isWifiDataEnable(this)) {
				//缓存在线视频
			    new Thread(new Runnable() {
			    	 
			        @Override
			        public void run() {
			            FileOutputStream out = null;
			            InputStream is = null;
			 
			            try {
			                URL url = new URL(urlPath);
			                HttpURLConnection httpConnection = (HttpURLConnection) url
			                        .openConnection();
			 
			               /* if (localPath == null) {
			                	localPath = Environment.getExternalStorageDirectory()
			                            .getAbsolutePath()
			                            + "/VideoCache/"
			                            + System.currentTimeMillis() + ".mp4";
			                }*/
			 
			                System.out.println("localPath: " + localPath);
			 
			                File cacheFile = new File(localPath);
			 
			                if (!cacheFile.exists()) {
			                    cacheFile.getParentFile().mkdirs();
			                    cacheFile.createNewFile();
			                }
			 
			                readSize = cacheFile.length();
			                out = new FileOutputStream(cacheFile, true);
			 
			                httpConnection.setRequestProperty("User-Agent", "NetFox");
			                httpConnection.setRequestProperty("RANGE", "bytes="
			                        + readSize + "-");
			 
			                is = httpConnection.getInputStream();
			 
			                mediaLength = httpConnection.getContentLength();
			                if (mediaLength == -1) {
			                    return;
			                }
			 
			                mediaLength += readSize;
			 
			                byte buf[] = new byte[4 * 1024];
			                int size = 0;
			                long lastReadSize = 0;
			 
			                mHandler.sendEmptyMessage(VIDEO_STATE_UPDATE);
			 
			                while ((size = is.read(buf)) != -1) {
			                    try {
			                        out.write(buf, 0, size);
			                        readSize += size;
			                    } catch (Exception e) {
			                        e.printStackTrace();
			                    }
			 
			                    if (!isready) {
			                        if ((readSize - lastReadSize) > READY_BUFF) {
			                            lastReadSize = readSize;
			                            mHandler.sendEmptyMessage(CACHE_VIDEO_READY);
			                        }
			                    } else {
			                        if ((readSize - lastReadSize) > CACHE_BUFF
			                                * (errorCnt + 1)) {
			                            lastReadSize = readSize;
			                            mHandler.sendEmptyMessage(CACHE_VIDEO_UPDATE);
			                        }
			                    }
			                }
			 
			                mHandler.sendEmptyMessage(CACHE_VIDEO_END);
			            } catch (Exception e) {
			                e.printStackTrace();
			            } finally {
			                if (out != null) {
			                    try {
			                        out.close();
			                    } catch (IOException e) {
			                        //
			                    }
			                }
			 
			                if (is != null) {
			                    try {
			                        is.close();
			                    } catch (IOException e) {
			                        //
			                    }
			                }
			            }
			 
			        }
			    }).start();
			} else {
				showShortToast("网络不可用，请稍后再试");
				finish();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	OnCompletionListener onCompListener = new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv.setVideoPath(urlPath);
			vv.start();
		}		
	};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
