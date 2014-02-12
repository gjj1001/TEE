package com.tee686.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LevelListDrawable;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.api.JPushInterface;

import com.casit.tee686.R;
import com.tee686.adapter.ChatMsgViewAdapter;
import com.tee686.adapter.ExpressionGvAdapter;
import com.tee686.config.ContentFlag;
import com.tee686.config.Urls;
import com.tee686.entity.Message;
import com.tee686.https.HttpUtils;
import com.tee686.service.base.MessageService;
import com.tee686.service.base.RecordPlayService;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.ConversationPopupWindow;
import com.tee686.utils.DateUtil;
import com.tee686.utils.ExpressionUtil;
import com.tee686.utils.FileDealTool;
import com.tee686.utils.SystemConstant;

public class ReplyActivity extends BaseActivity {

	private MessageService msgService;
	private TextView title;
	private ListView lvMsgLisr;
	private EditText etCtn;
	private ImageView gohome;
	private Button btn_send;
	private Button btn_record;
	private ImageButton btn_open_record;
	private Dialog progressDialog;
	private Dialog recordDialog;
	private Dialog recordSendDialog;
	private ChatMsgViewAdapter ctAdapter;
//	private PopupWindow optionWindow;
//	private PopupWindow convertWindow;
//	private View parent;
	private View record_view;
	private View record_send_view;
	private View viewpager_layout;
	private ImageView ivRecord;
	private RelativeLayout express_spot_layout;
	private LinearLayout before_recored_layout;
	private LinearLayout start_recored_layout;
	private Vibrator mVibrator;
	private boolean loginFlag = false; //是否已经登录
	private List<Message> msgList = new ArrayList<Message>();	//保存所有消息内容
	private ViewPager viewPager;	//实现表情的滑动翻页
	private int imageIds[] = ExpressionUtil.getExpressRcIds();	//保存所有表情资源的id
//	private String comtime;
//	private String headimage;
	private String uname;
	private String result;
//	private String content;
	SharedPreferences share;
	Bundle bundle;
	RecordPlayService playService = new RecordPlayService();
	/**
	 * 消息处理器
	 */
	@SuppressLint("HandlerLeak")
	private Handler handle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			switch (what) {
			case 1:
				Message message = (Message) msg.obj;
				//当前用户登录
				/*if(user.getId() == message.getId()){
					view_loginer.setText(user.getName());
					view_image.setImageBitmap(message.getBitmap());
					if(progressDialog.isShowing()){
						progressDialog.dismiss();
					}
				}*/
				if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
				loginFlag = true;
				msgList.add(message);
				if(recordSendDialog.isShowing()) recordSendDialog.dismiss();
				ctAdapter.notifyDataSetChanged();
				lvMsgLisr.setSelection(msgList.size() - 1);
				break;
			case 2:
				loginFlag = false;
				progressDialog.dismiss();
				Toast.makeText(ReplyActivity.this, R.string.httpError,
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(ReplyActivity.this, R.string.record_short, Toast.LENGTH_SHORT).show();
				break;
			case 4:
				/**
				 * 动态捕捉麦克音量变化，更新UI
				 */
				int volume = (Integer) msg.obj; //读取到的音量大小
				LevelListDrawable levelDrawable = (LevelListDrawable) ivRecord.getDrawable();
				levelDrawable.setLevel(volume);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reply_main);         
        initControl();
        initSharePreferenced();
        title.setBackgroundResource(0);
        title.setText("会话");
        getFromBundle();
//        initMessage();
        if(savedInstanceState!=null) {
			result = savedInstanceState.getString("result");			
//			share.edit().putString("pubContents", result).commit();		
			List<Message> messages = new ArrayList<Message>();
			try {
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Message message = new ObjectMapper().readValue(json, Message.class);
					messages.add(message);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			ctAdapter = new ChatMsgViewAdapter(ReplyActivity.this, uname, msgList, lvMsgLisr);
			lvMsgLisr.setAdapter(ctAdapter);
			lvMsgLisr.setSelection(messages.size()-1);
//			mAdapter.appendToList(result);	
			
			lvMsgLisr.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					Message message = (Message) ctAdapter.getItem(position);
					List<String> tabs = new ArrayList<String>();
					tabs.add("删除");
					ConversationPopupWindow<String> util = new ConversationPopupWindow<String>(ReplyActivity.this, 
							 message.getReply_person(), message, share, ctAdapter, ctAdapter.msgList);
					util.showActionWindow(view, tabs);
					return true;
				}
			});
        } else {
        	 String url1 = String.format(Urls.USER_MESSAGE+"?send_person=%s&reply_person=%s", 
     				share.getString(UserLoginActivity.UID, ""), uname);
     		new DataAsyncTask().execute(url1);
        }
       
        etCtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(viewpager_layout.getVisibility() == View.VISIBLE){
					viewpager_layout.setVisibility(View.GONE);
				}
			}
		});
        gohome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				defaultFinish();
				overridePendingTransition(R.anim.hold, R.anim.umeng_fb_slide_out_from_right);
			}
		});
	}

	/*private void initMessage() {
		message.setBitmap(headimage);
		message.setSend_ctn(content);
		message.setSend_date(comtime);
		message.setSend_person(share.getString(UserLoginActivity.UID, ""));
		message.setReply_person(uname);
	}*/

	private void getFromBundle() {
		Intent intent = getIntent();
        bundle = intent.getExtras();
        /*String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		try {
			JSONObject json = new JSONObject(extras);
			comtime = json.optString("comtime");
			headimage = json.optString("headimage");
		} catch (JSONException e) {			
			e.printStackTrace();
		}	*/	
		if(bundle.containsKey(JPushInterface.EXTRA_NOTIFICATION_TITLE)) {
			uname = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
		} else if(bundle.containsKey(JPushInterface.EXTRA_TITLE)){
			uname = bundle.getString(JPushInterface.EXTRA_TITLE);
		} else {
			uname = intent.getStringExtra("uname");
		}
//		content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
	}

	private void initSharePreferenced() {		
		share = getSharedPreferences(UserLoginActivity.SharedName, MODE_PRIVATE);
	}

	private void initControl() {
		LayoutInflater inflater = this.getLayoutInflater();
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);	//自定义标题布局文件
        mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
//        parent = findViewById(R.id.reply_main);
        lvMsgLisr = (ListView) findViewById(R.id.lv_message);
        lvMsgLisr.setOnTouchListener(new MyOnTouchListener());
        gohome = (ImageView) findViewById(R.id.details_imageview_gohome);
        record_view = inflater.inflate(R.layout.record_dialog, null);
        ivRecord = (ImageView) record_view.findViewById(R.id.iv_record);
        record_send_view = inflater.inflate(R.layout.record_send_dialog, null);
        btn_send = (Button) findViewById(R.id.sendMsg);
        title = (TextView) findViewById(R.id.tv_detail_title);
        btn_open_record = (ImageButton) findViewById(R.id.btn_open_record);
        etCtn = (EditText) findViewById(R.id.content);
        viewpager_layout = findViewById(R.id.viewpager_layout);
        express_spot_layout = (RelativeLayout) findViewById(R.id.express_spot_layout);
        before_recored_layout = (LinearLayout) findViewById(R.id.before_recored_layout);
        start_recored_layout = (LinearLayout) findViewById(R.id.start_recored_layout);
        viewPager = (ViewPager) findViewById(R.id.tabpager);
        btn_send.setOnClickListener(new SendBtnClickListener());
        btn_record = (Button) findViewById(R.id.btn_start_record);
        btn_record.setOnTouchListener(new MyRecordTouchListener());
        recordDialog = new Dialog(this, R.style.dialog);
        recordDialog.setContentView(record_view);
        recordSendDialog = new Dialog(this, R.style.dialog);
        recordSendDialog.setContentView(record_send_view);
        recordSendDialog.setCancelable(false);
        progressDialog = new Dialog(this, R.style.dialog);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
	}

	/**
     * 隐藏软键盘
     * @param view
     */
    public void hideSoftinput(View view){
    	InputMethodManager manager = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
    	if(manager.isActive()){
    		manager.hideSoftInputFromWindow(etCtn.getWindowToken(), 0);
    	}
    }
    
    public final class MyOnTouchListener implements View.OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				hideSoftinput(v);
			}
			return false;
		}
    }
    
	/**
     * 处理发送按钮事件
     * @author Administrator
     *
     */
	private final class SendBtnClickListener implements View.OnClickListener{
		public void onClick(View v) {
			String ctn = etCtn.getText().toString();		//待发送的消息
			if(ctn.equals("") || ctn == null){
				Toast.makeText(ReplyActivity.this, R.string.tip_input, Toast.LENGTH_SHORT).show();
				return;
			}
			new SendMsgTask().execute(Urls.USER_CONTEXT);
		}
    }
    
    /**
     * 处理回退事件
     */
	public void onBackPressed() {
		if(viewpager_layout.getVisibility() == View.VISIBLE){
			viewpager_layout.setVisibility(View.GONE);
			return;
		}
		defaultFinish();
		overridePendingTransition(R.anim.hold, R.anim.umeng_fb_slide_out_from_right);
	}

	@Override
	public void onDestroy() {
//		msgService.quitApp();
		//清除所有临时的录音文件
		FileDealTool.delRecordFile();
		Log.i(ContentFlag.TAG, "chatactivity destroy!");
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("result", result);
	}	

	@Override
	protected void onPause() {
		super.onPause();
	}
	/**
	 * 语音UI变更
	 * @param
	 */
    /*private void updateUI(final Message message) {    	
		new Thread(new Runnable() {
			public void run() {
				android.os.Message msg = handle.obtainMessage();
				msg.what = 1;
				msg.obj = message;
				handle.sendMessage(msg);
			}
		}).start();
	}*/
    
    /**
     * 打开语音聊天页面
     * @param view
     */
    public void openRecordWindow(View view){
    	InputMethodManager manager = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
    	if(manager.isActive()){
    		manager.hideSoftInputFromWindow(etCtn.getWindowToken(), 0);
    	}
    	if(viewpager_layout.getVisibility() == View.VISIBLE){
    		viewpager_layout.setVisibility(View.GONE);
    	}
    	if(before_recored_layout.getVisibility() == View.VISIBLE){
        	btn_open_record.setImageResource(R.drawable.btn_keyboard);
        	before_recored_layout.setVisibility(View.GONE);
        	start_recored_layout.setVisibility(View.VISIBLE);
    	}else{
        	btn_open_record.setImageResource(R.drawable.btn_intercon);
        	before_recored_layout.setVisibility(View.VISIBLE);
        	start_recored_layout.setVisibility(View.GONE);
        	manager.showSoftInput(etCtn, 0);
    	}
    }
    
    /**
     * 发送语音消息监听类
     * @author Administrator
     */
    private final class MyRecordTouchListener implements View.OnTouchListener{
    	private MediaPlayer mediaPlayer;
    	private File file = null;
    	private FileOutputStream os = null;
    	private long startTime;
    	private boolean ifexit = true;	//SD卡是否存在的标识
    	private RecordThread thread = null;
		public boolean onTouch(View v, MotionEvent event) {
			try {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					btn_record.setText(R.string.btn_press_voice);
					btn_record.setBackgroundResource(R.drawable.normal_button_blue);
					mediaPlayer = MediaPlayer.create(ReplyActivity.this, R.raw.play_completed);
					mediaPlayer.start();
					mVibrator.vibrate( new long[]{100,50}, -1);
					if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
						File tmpFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separatorChar+
								"recordMsg"+File.separatorChar);
						if(!tmpFile.exists()) {
							tmpFile.mkdirs();
						}
						file = new File(tmpFile, System.currentTimeMillis()+".pcm");
						os = new FileOutputStream(file); 
						
						/*if(!file.exists()) {
							file.mkdirs();
							file = new File(file, System.currentTimeMillis()+
								Base64.encode(uname.getBytes()) + ".pcm");
							dos = new FileOutputStream(file); 
						} else {
							file = new File(file, System.currentTimeMillis()+
									Base64.encode(uname.getBytes()) + ".pcm");
							dos = new FileOutputStream(file); 
						}*/												
					} else {
						Toast.makeText(ReplyActivity.this, R.string.sd_noexit,
								Toast.LENGTH_SHORT).show();
						ifexit = false;
						return true;
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					recordDialog.show();
					//开启声音捕捉监听线程
					thread = new RecordThread(os);
					thread.start();
					startTime = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_UP:
					recordDialog.dismiss();
					btn_record.setText(R.string.btn_normal_voice);
					btn_record.setBackgroundResource(R.drawable.normal_button);
					mediaPlayer.release();
					mediaPlayer = null;
					if(ifexit == false) return true;
					//recordDialog.dismiss();
					//关闭声音捕捉监听线程
					thread.pause();
					long endTime = System.currentTimeMillis();
					final long recordTime = endTime - startTime;
					if(recordTime < 1000){
						handle.sendEmptyMessage(3);
//						file.delete();
					}else{
						recordSendDialog.show();
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									msgService = new MessageService(ReplyActivity.this);
									String send_date = DateUtil.getCurrentDateTime();
									msgService.sendRecordMsg(file, recordTime, uname, send_date, 
											share.getString(UserLoginActivity.UID, ""), 
											share.getString(UserLoginActivity.PIC, ""));
									Message message = new Message("", share.getString(UserLoginActivity.UID, ""), 
											send_date, share.getString(UserLoginActivity.PIC, ""),
											uname);
									message.setIfyuyin(true);
									message.setRecordTime(recordTime);
									message.setRecord_path(file.getAbsolutePath());
									android.os.Message msg = handle.obtainMessage();
									msg.what = 1;
									msg.obj = message;
									handle.sendMessage(msg);
								} catch (Exception e) {
									e.printStackTrace();
									if(recordSendDialog.isShowing()) recordSendDialog.dismiss();
//									if(null!= file) file.delete();
									if(null!= mediaPlayer) mediaPlayer.release();
								}
							}
						}).start();
					}
					mVibrator.cancel();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
//				if(null!= file) file.delete();
				if(null!= mediaPlayer) mediaPlayer.release();
			}
			return true;
		}
    }
    
    /**
     * 弹出popu窗口
     * @param view
     */
    /*public void outOperatePopuWindow(View view){
		if(loginFlag == true){
			tv_login.setText(R.string.btn_reverse);
		}else{
			tv_login.setText(R.string.btn_login);
		}
		if(null == optionWindow){
			optionWindow = new PopupWindow(popuCtnView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			optionWindow.setFocusable(true);
			optionWindow.setBackgroundDrawable(new BitmapDrawable());
		}
		optionWindow.showAsDropDown(btn_operate, 5, 0);
    }*/
    
	/**
     * 处理登录/注册事件
     * @param view
     */
    /*public void userLoginOrRegister(View view){
    	final Intent intentLogin = new Intent(this, UserLoginActivity.class);
    	final Intent intentRegis = new Intent(this, UserRegisActivity.class);
    	final List<User> userList = userService.queryResigterUser();
    	//未登录
    	if(loginFlag == false){
    		switch (view.getId()) {
			case R.id.user_login:
	        	if(userList.size() > 0){
	        		Bundle bundle = new Bundle();
	        		bundle.putSerializable("regisUsers", (Serializable)userList);
	        		intentLogin.putExtras(bundle);
	        		startActivity(intentLogin);
	        	}else{
	        		startActivity(intentRegis);
	        	}
				break;
			case R.id.user_register:
				startActivity(intentRegis);
				break;
			}
    	}else{
    		Button btn_convert = (Button) convert_view.findViewById(R.id.btn_convert);
    		Button btn_cancel = (Button) convert_view.findViewById(R.id.btn_cancel);
    		switch (view.getId()) {
			case R.id.user_login:
	    		btn_convert.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				msgService.quitApp();
	            		Bundle bundle = new Bundle();
	            		bundle.putSerializable("regisUsers", (Serializable)userList);
	            		intentLogin.putExtras(bundle);
	            		startActivity(intentLogin);
	    				convertWindow.dismiss();
	    			}
	    		});
				break;
			case R.id.user_register:
	    		btn_convert.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				msgService.quitApp();
	    				startActivity(intentRegis);
	    				convertWindow.dismiss();
	    			}
	    		});
				break;
			}
    		btn_cancel.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				convertWindow.dismiss();
    			}
    		});
    		if(null == convertWindow){
    			convertWindow = new PopupWindow(convert_view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    			convertWindow.setFocusable(true);
    			convertWindow.setBackgroundDrawable(new BitmapDrawable());
    			convertWindow.setAnimationStyle(R.style.popu_animation);
        		convertWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    		}else{
    			convertWindow.setContentView(convert_view);
    			convertWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    		}
    	}
    	optionWindow.dismiss();
    }*/
    
    /**
     * 显示表情对话框
     * @param view
     */
    public void showExpressionWindow(View view){
    	//判断软键盘是否打开
    	this.hideSoftinput(view);
    	//显示表情对话框
    	viewpager_layout.setVisibility(View.VISIBLE);
    	//获取屏幕当前分辨率
        Display currDisplay = getWindowManager().getDefaultDisplay();
        int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        //获得表情图片的宽度/高度
        Bitmap express = BitmapFactory.decodeResource(getResources(), R.drawable.f000);
    	int headWidth = express.getWidth();
    	int headHeight = express.getHeight();
    	Log.d(ContentFlag.TAG, displayWidth+":" + headWidth);
    	final int colmns = displayWidth/headWidth > 7 ? 7 : displayWidth/headWidth;	//每页显示的列数
    	final int rows = displayHeight/headHeight > 4 ? 4 : displayHeight/headHeight;	//每页显示的行数
    	final int pageItemCount = colmns * rows;		//每页显示的条目数
    	//计算总页数
		int totalPage = SystemConstant.express_counts % pageItemCount == 0 ? 
				SystemConstant.express_counts / pageItemCount : SystemConstant.express_counts / pageItemCount + 1;
		final List<View> listView = new ArrayList<View>();
		for (int index = 0; index < totalPage; index++) {
			listView.add(getViewPagerItem(index, colmns, pageItemCount));
		}
		express_spot_layout.removeAllViews();
		for (int i = 0; i < totalPage; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setId(i+1);
			if(i == 0){
				imageView.setBackgroundResource(R.drawable.d2);
			}else{
				imageView.setBackgroundResource(R.drawable.d1);
			}
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;
			layoutParams.rightMargin = 10;
			layoutParams.bottomMargin = 20;
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			if(i!= 0){
				layoutParams.addRule(RelativeLayout.ALIGN_TOP, i);
				layoutParams.addRule(RelativeLayout.RIGHT_OF, i);
			}
			express_spot_layout.addView(imageView, layoutParams);
		}
		Log.d(ContentFlag.TAG, express_spot_layout.getChildCount() + "");
		//填充viewPager的适配器
		viewPager.setAdapter(new PagerAdapter() {
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			public int getCount() {
				return listView.size();
			}
			
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(listView.get(position));
			}
			
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(listView.get(position));
				return listView.get(position);
			}
		});
		//注册监听器
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
    
    private final class MyPageChangeListener implements OnPageChangeListener{
    	private int curIndex = 0;
		public void onPageSelected(int index) {
			express_spot_layout.getChildAt(curIndex).setBackgroundResource(R.drawable.d1);
			express_spot_layout.getChildAt(index).setBackgroundResource(R.drawable.d2);
			curIndex = index;
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		public void onPageScrollStateChanged(int arg0) {
		}
    }
    
    private View getViewPagerItem(final int index, int colums, final int pageItemCount) {
    	LayoutInflater inflater = this.getLayoutInflater();
        View express_view = inflater.inflate(R.layout.express_gv, null);
    	GridView gridView = (GridView) express_view.findViewById(R.id.gv_express);
    	gridView.setNumColumns(colums);
    	gridView.setAdapter(new ExpressionGvAdapter(index, pageItemCount, imageIds, inflater));
		//注册监听事件
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int positon,
					long id) {
				Bitmap bitmap = null;
				int start = index * pageItemCount;	//起始位置
				positon = positon + start;
				bitmap = BitmapFactory.decodeResource(getResources(), imageIds[positon]);
				ImageSpan imageSpan = new ImageSpan(bitmap);
				String str = "";
				if(positon < 10){
					str = "[f00"+positon+"]";
				}else if(positon < 100){
					str = "[f0"+positon+"]";
				}else{
					str = "[f"+positon+"]";;
				}
				SpannableString spannableString = new SpannableString(str);
				spannableString.setSpan(imageSpan, 0, str.length(),
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				etCtn.append(spannableString);
				viewpager_layout.setVisibility(View.GONE);
			}
		});
		return express_view;
	}
    
    /**
     * 监听麦克风音量的变化
     * @author Administrator
     */
	public final class RecordThread extends Thread {
		private AudioRecord audioRecord;
		private int bufferSize = 100;   	  //录制缓冲大小
		private boolean isRun = true;
		private FileOutputStream output;  
		private int BLOW_BOUNDARY = 30;		  // 到达该值之后 触发事件
		public RecordThread(FileOutputStream os) {
			this.output = os;
			bufferSize = AudioRecord.getMinBufferSize(SystemConstant.SAMPLE_RATE_IN_HZ,
					SystemConstant.CHANNEL_CONFIG_IN,
					SystemConstant.AUDIO_FORMAT);
			// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
			// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
					SystemConstant.SAMPLE_RATE_IN_HZ, SystemConstant.CHANNEL_CONFIG_IN,
					SystemConstant.AUDIO_FORMAT, bufferSize);
		}

		public void run() {
			long time = 0;
			long currenttime = 0;
			long endtime = 0;
			int total = 0;
			int number = 0;
			audioRecord.startRecording();
			// 用于读取的 buffer
			byte[] buffer = new byte[bufferSize];
			try {
				while (isRun) {
					number++;
					currenttime = System.currentTimeMillis();
					int r = audioRecord.read(buffer, 0, bufferSize);// 读取到的数据
					int v = 0;
					for (int i = 0; i < buffer.length; i++) {
						v += Math.abs(buffer[i]);//取绝对值，因为可能为负						
					}
					output.write(buffer, 0, r);
					int value = Integer.valueOf(v / r);//算得当前所有值的平均值
					total = total + value;
					endtime = System.currentTimeMillis();
					time = time + (endtime - currenttime);
					//如果间隔时间大于500毫秒或者次数多于5次，才处理音频数据
					if (time >= 500 || number > 5) {
						int valume = total / number;
						total = 0;
						number = 0;
						time = 0;
						//声音的大小达到一定的值
						if (valume > BLOW_BOUNDARY) {
							// 发送消息通知到界面 触发动画
							android.os.Message msg = handle.obtainMessage();
							msg.obj = valume;
							msg.what = 4;
							handle.sendMessage(msg);
						}
					}
				}
			} catch (Exception e) {
				Log.d(ContentFlag.TAG, "stop recording!");
			} finally {
				audioRecord.stop();
				audioRecord.release();
				audioRecord = null;
//					output.close();
			}
		}

		public void pause() {
			isRun = false;
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void start() {
			if (isRun) {
				super.start();
			}
		}
	}
	
	class SendMsgTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Message value = new Message(etCtn.getText().toString(), share.getString(UserLoginActivity.UID, ""),
					DateUtil.getCurrentDateTime(), share.getString(UserLoginActivity.PIC, ""), uname);
			msgList.add(value);
			return HttpUtils.postByHttpURLConnection(params[0], value);	
		}
		
		@Override
		protected void onPostExecute(final String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {				
				etCtn.setText("");
				showShortToast(result);	
				ctAdapter.notifyDataSetChanged();
//				msgList.clear();
//				String url1 = String.format(Urls.USER_MESSAGE+"?send_person=%s&reply_person=%s", 
//						share.getString(UserLoginActivity.UID, ""), uname);
//				new DataAsyncTask().execute(url1);
			} else {
				showShortToast("网络连接失败，请稍后再试");
			}
		}		
	}
	
	/**
	 * @author Jason
	 *获得聊天列表数据
	 */
	class DataAsyncTask extends AsyncTask<String, Void, List<Message>> {
//		private HttpURLConnection conn;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			showAlertDialog("温馨提示", "正在加载列表信息请稍后");
		}

		@Override
		protected List<Message> doInBackground(String... params) {				
			
			try {				
				/*byte[] data = params[1].getBytes("utf-8");
				URL url = new URL(params[0]);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				conn.setReadTimeout(5000);
				conn.setRequestProperty("Content-Type",
						"text/plain; charset=UTF-8");
				conn.setRequestProperty("Content-Length",
						String.valueOf(data.length));
				OutputStream out = conn.getOutputStream();
				out.write(data);
				out.flush();
				out.close();
				if (conn.getResponseCode() == 200) {
					byte[] connbuffer = new byte[1024];
					ByteArrayOutputStream outStream = new ByteArrayOutputStream();
					InputStream in = conn.getInputStream();
					int len = 0;
					while ((len=in.read(connbuffer)) != -1) {
						outStream.write(connbuffer, 0, len);
					}
					result = new String(outStream.toByteArray());
					in.close();
					conn.disconnect();					
				}						*/
				result = HttpUtils.getByHttpClient(ReplyActivity.this, params[0]);	
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Message message = new ObjectMapper().readValue(json, Message.class);
					msgList.add(message);
				}
				
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*for(PubContent pubContent : mPubContents) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userhead", pubContent.getHeadimage());
				map.put("bulletincontent", pubContent.getContent());
				map.put("username", pubContent.getUsername());
				map.put("image", pubContent.getImageFile());
				map.put("sendtime", pubContent.getSendtime());
				mlist.add(map);
			}*/ 
			
			
			return msgList;
		}

		@Override
		protected void onPostExecute(List<Message> result) {
			ctAdapter = new ChatMsgViewAdapter(ReplyActivity.this, uname, msgList, lvMsgLisr);
			lvMsgLisr.setAdapter(ctAdapter);
			lvMsgLisr.setSelection(result.size()-1);
//			mAdapter.appendToList(result);	
			
			lvMsgLisr.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					Message message = (Message) ctAdapter.getItem(position);
					List<String> tabs = new ArrayList<String>();
					tabs.add("删除");
					ConversationPopupWindow<String> util = new ConversationPopupWindow<String>(ReplyActivity.this, 
							 message.getReply_person(), message, share, ctAdapter, ctAdapter.msgList);
					util.showActionWindow(view, tabs);
					return true;
				}
			});	
//			mAlertDialog.dismiss();
			if(result.isEmpty()) {				
				showShortToast("没有消息");				
			}			
		}
		
	}
}
