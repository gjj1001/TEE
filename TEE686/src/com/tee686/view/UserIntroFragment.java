package com.tee686.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.casit.tee686.R;
import com.readystatesoftware.viewbadger.BadgeView;
import com.tee686.activity.UserFanActivity;
import com.tee686.activity.UserLoginActivity;
import com.tee686.activity.UserMessageActivity;
import com.tee686.activity.UserObserverActivity;
import com.tee686.activity.UserPubContentsActivity;
import com.tee686.config.Constants;
import com.tee686.config.Urls;
import com.tee686.entity.PubContent;
import com.tee686.entity.Observer;
import com.tee686.entity.UserInfoItem;
import com.tee686.https.HttpUtils;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.IntentUtil;
import com.tee686.utils.ImageUtil.ImageCallback;
import com.tee686.utils.UserHeadUtil;

@SuppressLint("ValidFragment")
public class UserIntroFragment extends Fragment {
	UserInfoItem mUserInfoItem;
	private ImageView img;
	private ImageView info_img;
	private TextView txtName;
	private TextView txtRegTime;
	private TextView txtEP;
	private TextView txtEM;
	private TextView content;
	private TextView level;
	private TextView observers;
	private TextView fans;
	private TextView msgs;
	private LinearLayout layout;
	SimpleAdapter mAdapter;
	private Context mContext;
	private byte[] data;
	private PubContent pubContent;
	private List<Observer> listobservers = new ArrayList<Observer>();
	private List<Observer> listfans = new ArrayList<Observer>();
	private BadgeView fanBadgeView;
	private Set<String> tags = new HashSet<String>();
//	private BadgeView ObserveBadgeView;
//	private String[] items = new String[] { "选择本地图片", "拍照" };
	SharedPreferences share;
	LayoutInflater inflater;
	
	private PubAsyncTask pubTask;
	private CheckObserverTask checkObserverTask;
	private CheckFanTask checkFanTask;
	
	/* 头像名称   
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";   
      
     请求码   
    private static final int IMAGE_REQUEST_CODE = 0;    
    private static final int CAMERA_REQUEST_CODE = 1;    
    private static final int RESULT_REQUEST_CODE = 2;
	private static final int RESULT_CANCELED = KeyEvent.KEYCODE_BACK;*/

	public UserIntroFragment(UserInfoItem result) {
		mUserInfoItem = result;
	}

	// [start]继承方法
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = inflater.getContext();
		View v = inflater.inflate(R.layout.user_center_intro_fragment, null);
		initControl(v);
//		initGridView();		
		setControl();		
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String url2 = String.format(Urls.USER_RECENTLY_PUBLISH, mUserInfoItem.getUsername());
		pubTask = new PubAsyncTask();
		pubTask.execute(url2);
		if(!listobservers.isEmpty()) {
			listobservers.clear();
		}
		if(!listfans.isEmpty()) {
			listfans.clear();
		}
		String url1 = String.format(Urls.USER_OBSERVER+"?uname=%s", mUserInfoItem.getUsername());
		checkObserverTask = new CheckObserverTask();
		checkObserverTask.execute(url1);
		String url3 = String.format(Urls.USER_OBSERVER+"?username=%s", mUserInfoItem.getUsername());
		checkFanTask = new CheckFanTask();
		checkFanTask.execute(url3);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ReceiverAction.CHECK_NEW_FAN);
		getActivity().registerReceiver(checkNewReceiver, filter);
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putByteArray("bitmap", data);
		tags.clear();
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null) {
			data = savedInstanceState.getByteArray("bitmap");
			img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
			img.setScaleType(ScaleType.CENTER_CROP);
		}
	}

	private void initControl(View v) {
		img = (ImageView) v.findViewById(R.id.user_imageview_icon);
		txtName = (TextView) v.findViewById(R.id.user_textview_name);
		txtRegTime = (TextView) v.findViewById(R.id.user_textview_reg_time);
		txtEP = (TextView) v.findViewById(R.id.user_textview_e_p);
		txtEM = (TextView) v.findViewById(R.id.user_textview_e_m);
		content = (TextView) v.findViewById(R.id.user_textView_add);
		level = (TextView) v.findViewById(R.id.user_textview_level);
		observers = (TextView) v.findViewById(R.id.user_textview_observers);
		fans = (TextView) v.findViewById(R.id.user_textview_fans);
		msgs = (TextView) v.findViewById(R.id.user_textview_msgs);
		info_img = (ImageView) v.findViewById(R.id.iv_user_info_img);
		share = getActivity().getSharedPreferences(UserLoginActivity.SharedName, 0);
		layout = (LinearLayout) v.findViewById(R.id.ll_badgeview);
		fanBadgeView = new BadgeView(getActivity(), layout);
//		ObserveBadgeView = new BadgeView(getActivity(), observers);
	}

	/*private void initGridView() {
		getData();
		mAdapter = new SimpleAdapter(mContext, mList,
				R.layout.user_gridview_item_medal, new String[] { "img" },
				new int[] { R.id.user_imageview_medal }) {

			@Override
			public void setViewImage(ImageView v, String value) {
				// TODO Auto-generated method stub
				super.setViewImage(v, value);
				ImageUtil.setThumbnailView(value, v, mContext,
						new imageCallback(), false);
			}

			class imageCallback implements ImageCallback {

				@Override
				public void loadImage(Bitmap bitmap, String imagePath) {
					// TODO Auto-generated method stub
					try {
						ImageView img = (ImageView) gvGrid
								.findViewWithTag(imagePath);
						img.setImageBitmap(bitmap);
					} catch (NullPointerException ex) {
						Log.e("error", "ImageView = null");
					}
				}

			}

		};
		gvGrid.setAdapter(mAdapter);
	}*/

	/*private void getData() {
		mList = new ArrayList<Map<String, Object>>();
		for (int i = 0, count = mUserInfoItem.getIcon().size(); i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", mUserInfoItem.getIcon().get(i).getImg());
			mList.add(map);
		}
	}*/

	private void setControl() {
		txtName.setText(mUserInfoItem.getUsername());
		txtRegTime.setText(mUserInfoItem.getRegtime());
		txtEP.setText(getString(R.string.user_center_e_reputation, mUserInfoItem.getTp()));
		txtEM.setText(getString(R.string.user_center_e_coin,  mUserInfoItem.getTm()));
		if(mUserInfoItem.getTp()>=100) {
			level.setText(R.string.user_center_lecturer);
		} else if(mUserInfoItem.getTp()>=50) {
			level.setText(R.string.user_center_assistant);
		}
		Editor editor = share.edit();
		editor.putInt(UserLoginActivity.LEVEL, mUserInfoItem.getTp());
		editor.putInt(UserLoginActivity.MONEY, mUserInfoItem.getTm());
		editor.putString(UserLoginActivity.PIC, mUserInfoItem.getHeadimgurl());
		editor.commit();
		String imgUrl = share.getString(UserLoginActivity.PIC, "");
		if(null != imgUrl && !"".equals(imgUrl)) {			
			new imgAsyncTask().execute(imgUrl);			
		} 
		/*ImageUtil.setThumbnailView(imgUrl, img, mContext,
				new myImageCallBack(), true);*/
		msgs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(fanBadgeView.isShown()) {
					fanBadgeView.hide(true);
				}
				IntentUtil.start_activity(
						getActivity(), UserMessageActivity.class,
						new BasicNameValuePair("uname", mUserInfoItem.getUsername()));
			}
		});
	}

	/**
	 * 设置用户头像
	 */
	/*private void showDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(getActivity())
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:
							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (hasSdcard()) {
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}
							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	protected boolean hasSdcard() {
		// TODO Auto-generated method stub
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}*/
	
	class imgAsyncTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {			
			img.setImageBitmap(UserHeadUtil.getUserHeadImage(params[0]));
			img.setScaleType(ScaleType.CENTER_CROP);
			return null;			
		}		
	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					setImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	*//**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 *//*
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	*//**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 *//*
	private void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			img.setBackgroundDrawable(drawable);
		}
	}*/
	class PubAsyncTask extends AsyncTask<String, Void, PubContent> {

		private String result;
		@Override
		protected PubContent doInBackground(String... params) {
			if(isCancelled()) {
				return null;
			}
			try {
				result = HttpUtils.getByHttpClient(getActivity(), params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pubContent = new ObjectMapper().readValue(result, PubContent.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pubContent;
		}
		
		@Override
		protected void onPostExecute(PubContent result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				content.setText(result.getContent());
				if(result.getImageFile()!=null) {
					ImageUtil.setThumbnailView(result.getImageFile(), UserIntroFragment.this.info_img,
							getActivity(), callback, true);
				}
				content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						IntentUtil.start_activity(
								getActivity(), UserPubContentsActivity.class,
								new BasicNameValuePair("uname", pubContent.getUsername()));
					}
				});
			}
		}
		
	}
	
	ImageCallback callback = new ImageCallback() {

		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) info_img.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};
	
	class CheckObserverTask extends AsyncTask<String, Void, Integer> {

		private String result;
		@Override
		protected Integer doInBackground(String... params) {
			if(isCancelled()) {
				return null;
			}
			try {
				result = HttpUtils.getByHttpClient(getActivity(), params[0]);
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Observer observer = new ObjectMapper().readValue(json, Observer.class);
					listobservers.add(observer);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listobservers.size();
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				observers.setText("关注："+result);
				observers.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						IntentUtil.start_activity(
								getActivity(), UserObserverActivity.class,
								new BasicNameValuePair("uname", mUserInfoItem.getUsername()));
					}
				});
				for(Observer observer : listobservers) {
					tags.add(observer.getUsername()+"_fans");
				}
				JPushInterface.setAliasAndTags(getActivity(),
						share.getString(UserLoginActivity.UID, "tee"), tags,
						new TagAliasCallback() {

							@Override
							public void gotResult(int code, String alias,
									Set<String> tags) {
								// TODO Auto-generated method stub
								switch (code) {
								case 0:
									Log.d("alias", "set alias success");
								default:
									Log.d("alias", "errorCode:" + code);
								}
							}
						});
			}
		}		
	}
	
	class CheckFanTask extends AsyncTask<String, Void, List<Observer>> {

		private String result;
		@Override
		protected List<Observer> doInBackground(String... params) {
			if(isCancelled()) {
				return null;
			}
			try {
				result = HttpUtils.getByHttpClient(getActivity(), params[0]);
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Observer observer = new ObjectMapper().readValue(json, Observer.class);
					listfans.add(observer);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listfans;
		}
		@Override
		protected void onPostExecute(List<Observer> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				fans.setText("粉丝："+result.size());
				fans.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(fanBadgeView.isShown()) {
							fanBadgeView.hide(true);
						}						
						IntentUtil.start_activity(
								getActivity(), UserFanActivity.class,
								new BasicNameValuePair("uname", mUserInfoItem.getUsername()));
					}
				});
				/*for(Observer observer : result) {
					tags.add(observer.getUname()+"_fans");
				}
				JPushInterface.setAliasAndTags(getActivity(),
						share.getString(UserLoginActivity.UID, "tee"), tags,
						new TagAliasCallback() {

							@Override
							public void gotResult(int code, String alias,
									Set<String> tags) {
								// TODO Auto-generated method stub
								switch (code) {
								case 0:
									Log.d("alias", "set alias success");
								default:
									Log.d("alias", "errorCode:" + code);
								}
							}
						});*/
			}
		}		
	}
	
	BroadcastReceiver checkNewReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// badgeView.setBackgroundResource(R.drawable.umeng_xp_point_selected);
			if(intent.getBooleanExtra("msg", false)) {
				fanBadgeView.setText(String.valueOf(intent.getIntExtra("num", 0)));
				fanBadgeView.show(true);
			} else {
				fanBadgeView.setText(String.valueOf(intent.getIntExtra("num", 0)));
				fanBadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
				fanBadgeView.show(true);
			}
			
		}
	};
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(checkNewReceiver);
		if (pubTask != null && pubTask.getStatus() == AsyncTask.Status.RUNNING) {
			pubTask.cancel(true);
		}
		if (checkObserverTask != null && checkObserverTask.getStatus() == AsyncTask.Status.RUNNING) {
			checkObserverTask.cancel(true);
		}
		if (checkFanTask != null && checkFanTask.getStatus() == AsyncTask.Status.RUNNING) {
			checkFanTask.cancel(true);
		}
	}
}
