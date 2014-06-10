package cn.wodong.capturevideo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.casit.tee686.R;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.tee686.activity.ReplyActivity;
import com.tee686.activity.UserLoginActivity;
import com.tee686.https.NetWorkHelper;
import com.tee686.service.base.MessageService;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.DateUtil;

/**
 * Main Activity that used to show all the actions. 
 * 
 * @author xiaodong
 * 
 */
public class MainActivity extends BaseActivity {

	private SurfaceView videoSurface = null;
	private RecorderManager recorderManager = null;
	CameraManager cameraManager;
	private ProgressView progressView = null;
	private VideoView videoView = null;
	private boolean isPlaying = false;
	// private TextView sign;
	private Runnable progressRunnable = null;
	private MessageService msgService;
	private SharedPreferences share;
	private String uname;

	private View finishView = null;
	private Button finishButton = null;
	private Button swithButton = null;
	private Button okButton = null;
	Handler handler = null;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_video);
		videoSurface = (SurfaceView) findViewById(R.id.cameraView);
		videoView = (VideoView) findViewById(R.id.mediaShow);
		swithButton = (Button) findViewById(R.id.switchCamera);
		// sign = (TextView) findViewById(R.id.press);
		progressView = (ProgressView) findViewById(R.id.progress);
		cameraManager = getCameraManager();
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);
		okButton = (Button) findViewById(R.id.btn_ok);
		Intent intent = getIntent();
		uname = intent.getStringExtra("reply_person");
		//
		finishView = findViewById(R.id.finishLayOut);
		finishButton = (Button) findViewById(R.id.finishButton);
		//
		recorderManager = new RecorderManager(getCameraManager(), videoSurface,
				this);
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				mp.setLooping(true);

			}
		});

		videoSurface.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					try {
						swithButton.setVisibility(View.INVISIBLE);
						// sign.setPressed(true);
						recorderManager.startRecord(true);
					} finally {
						muteAll(true);
					}
				} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
					try {
						// sign.setPressed(false);
						recorderManager.stopRecord();
					} finally {
						muteAll(false);
						//
					}
				}
				return true;
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int total = ((ViewGroup) progressView.getParent())
						.getMeasuredWidth();
				if (msg.arg1 < 3000) {
					// System.out.println("Clickable");
					// finishButton.setClickable(true);
					finishView.setVisibility(View.INVISIBLE);
					// finishButton
					// .setBackgroundResource(R.drawable.btn_capture_arrow);
				} else {
					// System.out.println("UnClickable");
					// finishButton.setClickable(false);
					finishView.setVisibility(View.VISIBLE);
					// finishButton
					// .setBackgroundResource(R.drawable.btn_capture_arrow_pressed);
				}
				double length = msg.arg1 * 1.0 / 3000 * total;
				progressView.setWidth((int) length);
				progressView.invalidate();
				super.handleMessage(msg);
				// //
			}
		};
		//
		progressRunnable = new ProgressRunnable();
		handler.post(progressRunnable);

	}

	public void muteAll(boolean isMute) {
		// ((AudioManager) this.getSystemService(Context.AUDIO_SERVICE))
		// .setStreamSolo(AudioManager.STREAM_SYSTEM, isMute);
		// ((AudioManager) this.getSystemService(Context.AUDIO_SERVICE))
		// .setStreamMute(AudioManager.STREAM_SYSTEM, isMute);
		List<Integer> streams = new ArrayList<Integer>();
		Field[] fields = AudioManager.class.getFields();
		for (Field field : fields) {
			if (field.getName().startsWith("STREAM_")
					&& Modifier.isStatic(field.getModifiers())
					&& field.getType() == int.class) {
				try {
					Integer stream = (Integer) field.get(null);
					streams.add(stream);
				} catch (IllegalArgumentException e) {
					// do nothing
				} catch (IllegalAccessException e) {
					// do nothing
				}
			}
		}
	}

	// @Override
	// protected void onPause() {
	// // TODO Auto-generated method stub
	// muteAll(false);
	// super.onPause();
	// }
	//
	// @Override
	// protected void onResume() {
	// muteAll(true);
	// super.onResume();
	// }

	public CameraManager getCameraManager() {
		if (cameraManager == null) {
			cameraManager = new CameraManager();
		}
		return cameraManager;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_help:
			showAlertDialog(R.string.action_help, "1.触摸屏幕开始录制，松开后录制结束，接着触摸屏幕继续录制，直到录满3秒位置。\n"
					+ "2.录制完毕后，点击向右的箭头将刚录制的视频进行播放。\n", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mAlertDialog.dismiss();
						}
					});
		}
		
		return true;		 
	}

	public void onBackPressed(View view) {
		swithButton.setVisibility(View.VISIBLE);
		if(okButton.isShown()) {
			okButton.setVisibility(View.GONE);
		}
		stopPlay();
		recorderManager.reset();
		videoView.setVisibility(SurfaceView.GONE);
		videoSurface.setVisibility(SurfaceView.VISIBLE);
		isPlaying = false;
		recorderManager.reset();	

	}

	public void onFinishPressed(View view) {
		if (!isPlaying && recorderManager.getVideoTempFiles().size() != 0) {
			startPlay();
			isPlaying = true;
			okButton.setVisibility(View.VISIBLE);
		} else {
			recorderManager.reset();
			videoView.setVisibility(SurfaceView.GONE);
			videoSurface.setVisibility(SurfaceView.VISIBLE);
			isPlaying = false;
		}
	}

	public void onCameraSwitchPressed(View view) {
		if (!isPlaying) {
			cameraManager.changeCamera(videoSurface.getHolder());
			// recorderManager.reset();
		}
	}

	public void onOkPressed(View view) {
		new UploadAsyncTask().execute();
	}
	
	public void startPlay() {
		combineFiles();
		recorderManager.reset();
		videoSurface.setVisibility(SurfaceView.GONE);
		videoView.setVisibility(SurfaceView.VISIBLE);
		videoView.setVideoPath(getFinalVideoFileName());
		videoView.start();
	}

	
	/**
	 * 每次录制生成一个小的视频文件，最后要把所有的视频文件进行合并，合并成一个整体的视频文件。 
	 * 采用了一个开源组件isoviewer实现此功能
	 */
	@SuppressWarnings("resource")
	private void combineFiles() {
		try {
			List<Track> videoTracks = new LinkedList<Track>();
			List<Track> audioTracks = new LinkedList<Track>();
			for (String fileName : recorderManager.getVideoTempFiles()) {
				try {
					Movie movie = MovieCreator.build(fileName);
					for (Track t : movie.getTracks()) {
						if (t.getHandler().equals("soun")) {
							audioTracks.add(t);
						}
						if (t.getHandler().equals("vide")) {
							videoTracks.add(t);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Movie result = new Movie();

			if (audioTracks.size() > 0) {
				result.addTrack(new AppendTrack(audioTracks
						.toArray(new Track[audioTracks.size()])));
			}
			if (videoTracks.size() > 0) {
				result.addTrack(new AppendTrack(videoTracks
						.toArray(new Track[videoTracks.size()])));
			}

			Container out = new DefaultMp4Builder().build(result);

			FileChannel fc = new RandomAccessFile(
					String.format(getFinalVideoFileName()), "rw").getChannel();
			out.writeContainer(fc);
			fc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFinalVideoFileName() {
		return recorderManager.getVideoParentpath() + "/video.mp4";
	}

	private void stopPlay() {
		try {
			videoView.stopPlayback();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy() {
		muteAll(false);
		super.onDestroy();
		recorderManager.reset();
		handler.removeCallbacks(progressRunnable);
	}

	private class ProgressRunnable implements Runnable {

		@Override
		public void run() {
			int time = 0;
			time = recorderManager.checkIfMax(new Date().getTime());
			Message message = new Message();
			message.arg1 = time;
			handler.sendMessage(message);
			// System.out.println(time);
			handler.postDelayed(this, 10);

		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(MainActivity.this, ReplyActivity.class);
        intent.putExtra("uname", uname);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
	}
	
	class UploadAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				if(NetWorkHelper.isMobileDataEnable(MainActivity.this) || NetWorkHelper.isWifiDataEnable(MainActivity.this)) {
					msgService = new MessageService(MainActivity.this);
					String send_date = DateUtil.getCurrentDateTime();
					File file = new File(getFinalVideoFileName());
					try {
						msgService.sendRecordMsg(file, 3000, uname, send_date, 
								share.getString(UserLoginActivity.UID, ""), 
								share.getString(UserLoginActivity.PIC, ""), 2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result) {
				stopPlay();
				recorderManager.reset();
				videoView.setVisibility(SurfaceView.GONE);
				videoSurface.setVisibility(SurfaceView.VISIBLE);
				isPlaying = false;
				recorderManager.reset();
				Intent intent = new Intent(MainActivity.this, ReplyActivity.class);
		        intent.putExtra("uname", uname);
		        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
		        startActivity(intent);
				finish();
			} else {
				showShortToast("网络问题，请稍后再试");
			}
		}
		
	}
	
}
