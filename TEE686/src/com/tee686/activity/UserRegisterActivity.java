package com.tee686.activity;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.UserInfoItem;
import com.tee686.https.NetWorkHelper;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.IntentUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegisterActivity extends BaseActivity {

	public static String SharedName = "register";
	public static String UID = "uid";// 用户名
	public static String PWD = "pwd";// 密码
	public static String KEY = "key";// key
	public static String BIR = "bir";// 生日
	public static String PVC = "pvc";// 省份
	public static String CITY = "city";// 城市
	public static String SEX = "sex";// 性别
	public static String PIC = "pic";// 头像图片地址
	public static String TEL = "tel";// 手机号
	public static String PLA = "plat";// 第三方登陆平台
//	public static String REG = "reg";// 注册时间
	private String info;

	private EditText username;
	private EditText pwd;
	private TextView mobile;	
	private Button mCommunity;	
	private Button register;
	private LinearLayout gohome;
	private SharedPreferences share;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");	
	UserInfoItem userInfoItem;
//	Pattern p = Pattern.compile("\\w+[\\n\\r\\t]*");
	
	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		initControl();
		
		// 获取本机手机号
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getLine1Number() != null && !"".equals(tm.getLine1Number())) {
			mobile.setText(tm.getLine1Number());
		}
		
		initSharedPreference();

		gohome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showLongToast(getResources().getString(R.string.user_center_error));
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAlertDialog("免责申明", "本APP所载的各种信息和数据等仅供参考，禁止涉及对用户隐私信息的一切讨论，本APP所载的观点和评论仅代表用户的个人立场，用户据此发生的一切纠纷与本公司无关。本APP公告栏中的信息资料皆为用户个人发表，本公司并不对相关资料的准确性、充足性或完整性做出任何保证，也不对相关资料的任何错误或遗漏负任何法律责任。", "同意", "不同意", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						checkUsername(username.getText().toString(), pwd.getText().toString());
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
					
				}, new DialogInterface.OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						mAlertDialog.dismiss();
					}
				});
				
			}
		});
		
		mCommunity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentUtil.start_activity(UserRegisterActivity.this, BulletinActivity.class);
			}
		});
	}	

	protected void checkUsername(String name, String pwd) {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(this, R.string.user_username, Toast.LENGTH_SHORT)
					.show();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, R.string.user_pwd, Toast.LENGTH_SHORT).show();
			return;
		} else if (!NetWorkHelper.checkNetState(this)) {
			Toast.makeText(this, R.string.httpisNull, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// String loginUser = String.format(Urls.USER_LOGIN, name, pwd);
		new RegisterAsyncTask().execute( Urls.USER_REGISTER);
	}

	private void initSharedPreference() {
		// TODO Auto-generated method stub
		share = getSharedPreferences(SharedName, MODE_PRIVATE);
		if (share.contains(UID)) {
			username.setText(share.getString(UID, ""));						
		}
		
	}

	private void initControl() {
		// TODO Auto-generated method stub
		username = (EditText) findViewById(R.id.register_username);
		pwd = (EditText) findViewById(R.id.register_pwd);
		mobile = (TextView) findViewById(R.id.phone_number);		
		mCommunity = (Button) findViewById(R.id.btn_community);		
		register = (Button) findViewById(R.id.user_register);
		gohome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
	}

	class RegisterAsyncTask extends AsyncTask<String, Void, Boolean> {
		private HttpURLConnection conn;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showAlertDialog("温馨提示", "正在注册请稍等一下~");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			StringBuffer result = new StringBuffer();
			userInfoItem = new UserInfoItem(mobile.getText().toString(), username.getText().toString(),
					pwd.getText().toString(), share.getString(SEX, ""), share.getString(BIR, ""), share.getString(PVC, ""), 
					share.getString(CITY, ""), format.format(new Date()), share.getString(PLA, ""), share.getString(PIC, ""));
			try {				
				byte[] data = new ObjectMapper().writeValueAsBytes(userInfoItem);
				URL url = new URL(params[0]);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				conn.setReadTimeout(5000);
				conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
				conn.setRequestProperty("Content-Length", String.valueOf(data.length));
				OutputStream out = conn.getOutputStream();
				out.write(data);
				out.flush();
				out.close();
				if(conn.getResponseCode() == 200) {
					byte[] buffer = new byte[1024];
					InputStream in = conn.getInputStream();
					while(in.read(buffer) != -1) {
						result.append(new String(buffer, "utf-8"));
					}
					info = result.toString();
					in.close();
					conn.disconnect();	
					return true;
				} 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}
			return false;
			
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAlertDialog.dismiss();
			if (result) {
				showLongToast(info);
				Editor editor = getSharedPreferences(SharedName, MODE_PRIVATE)
						.edit();
				editor.putString(UID, username.getText().toString());
				editor.putString(PWD, pwd.getText().toString());				
				editor.putString(TEL, mobile.getText().toString());							
				editor.commit();
				IntentUtil.start_activity(UserRegisterActivity.this,
						UserCenterActivity.class);
				finish();
			} else {
				showLongToast("网络出现问题，请稍后再试");				
				pwd.setText("");
			}
		}
	}	
}
