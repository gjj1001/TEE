package cn.wodong.capturevideo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.CameraProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceView;

/**
 * Recorder controller, used to start,stop record, and combine all the videos together
 * @author xiaodong
 *
 */
@SuppressLint("NewApi")
public class RecorderManager {
	private MediaRecorder mediaRecorder = null;
	private CameraManager cameraManager = null;
	private String parentPath = null;
	private List<String> videoTempFiles = new ArrayList<String>();
	private SurfaceView mySurfaceView = null;
	public static final int MAX_TIME = 3000;
	private boolean isMax = false;
	private long videoStartTime;
	private int totalTime = 0;
	private boolean isStart = false;
	Activity mainActivity = null;
	private final Semaphore semp = new Semaphore(1);

	public RecorderManager(CameraManager cameraManager,
			SurfaceView mySurfaceView, Activity mainActivity) {
		this.cameraManager = cameraManager;
		this.mySurfaceView = mySurfaceView;
		this.mainActivity = mainActivity;
		parentPath = generateParentFolder();
		reset();
	}

	private Camera getCamera() {
		return cameraManager.getCamera();
	}

	public boolean isStart() {
		return isStart;
	}

	public long getVideoStartTime() {
		return videoStartTime;
	}

	public int checkIfMax(long timeNow) {
		int during = 0;
		if (isStart) {
			during = (int) (totalTime + (timeNow - videoStartTime));
			if (during >= MAX_TIME) {
				stopRecord();
				during = MAX_TIME;
				isMax = true;
			}
		} else {
			during = totalTime;
			if (during >= MAX_TIME) {
				during = MAX_TIME;
			}
		}

		return during;
	}

	public void reset() {
		for (String file : videoTempFiles) {
			File tempFile = new File(file);
			if (tempFile.exists()) {
				tempFile.delete();
			}
		}
		videoTempFiles = new ArrayList<String>();
		isStart = false;
		totalTime = 0;
		isMax = false;
	}

	public List<String> getVideoTempFiles() {
		return videoTempFiles;
	}

	public String getVideoParentpath() {
		return parentPath;
	}

	public void startRecord(boolean isFirst) {
		if (isMax) {
			return;
		}
		semp.acquireUninterruptibly();
		getCamera().stopPreview();
		mediaRecorder = new MediaRecorder();
		cameraManager.getCamera().unlock();
		mediaRecorder.setCamera(cameraManager.getCamera());
		if (cameraManager.isUseBackCamera()) {
			mediaRecorder.setOrientationHint(90);
		} else {
			mediaRecorder.setOrientationHint(90 + 180);
		}
		Size defaultSize = cameraManager.getDefaultSize();

		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mediaRecorder.setProfile(CamcorderProfile
				.get(CameraProfile.QUALITY_HIGH));
		if (defaultSize != null) {
			mediaRecorder.setVideoSize(defaultSize.width, defaultSize.height);
		} else {
			mediaRecorder.setVideoSize(640, 480);
		}
		// camera.getParameters().setPictureSize(cameraSize.width,
		// cameraSize.height);
		// camera.setParameters(parameters);
		String fileName = parentPath + "/" + videoTempFiles.size() + ".mp4";
		mediaRecorder.setOutputFile(fileName);
		videoTempFiles.add(fileName);
		mediaRecorder.setPreviewDisplay(mySurfaceView.getHolder().getSurface());
		try {
			mediaRecorder.prepare();
		} catch (Exception e) {
			e.printStackTrace();
			stopRecord();
		}

		try {
			mediaRecorder.start();
			isStart = true;
			videoStartTime = new Date().getTime();

		} catch (Exception e) {
			e.printStackTrace();
			if (isFirst) {
				startRecord(false);
			} else {
				stopRecord();
			}
		}
	}

	public void stopRecord() {
		if (!isMax) {
			totalTime += new Date().getTime() - videoStartTime;
			videoStartTime = 0;
		}
		//
		isStart = false;

		//
		if (mediaRecorder == null) {
			return;
		}
		try {
			mediaRecorder.setPreviewDisplay(null);
			mediaRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				mediaRecorder.reset();
				mediaRecorder.release();
				mediaRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				getCamera().reconnect();
			} catch (Exception e) {
				// TODO: handle this exception...
			}
			getCamera().lock();
			semp.release();
		}

	}

	public String generateParentFolder() {
		String parentPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mycapture/video/temp";
		File tempFile = new File(parentPath);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		return parentPath;

	}
}
