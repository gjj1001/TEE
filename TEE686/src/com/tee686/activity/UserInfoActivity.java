package com.tee686.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.casit.tee686.R;
import com.tee686.ui.base.BaseActivity;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.ImageUtil.ImageCallback;

public class UserInfoActivity extends BaseActivity {

	private ImageView userHead;
	private ImageView imgFile;
	private TextView txtName;
	private TextView txtRegTime;
	private TextView txtEP;
	private TextView txtEM;
	private TextView title;
	private TextView brand;
	private TextView zone;
	private TextView content;
	private Button button;
	
	private String headimg;
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initControl();
		Intent intent = getIntent();
		getStringExtra(intent);
	}
	
	private void initControl() {
		// TODO Auto-generated method stub
		userHead = (ImageView) findViewById(R.id.user_imageview_icon);
		imgFile = (ImageView) findViewById(R.id.iv_user_info_img);
		txtName = (TextView) findViewById(R.id.user_textview_name);
		txtRegTime = (TextView) findViewById(R.id.user_textview_reg_time);
		txtEP = (TextView) findViewById(R.id.user_textview_e_p);
		txtEM = (TextView) findViewById(R.id.user_textview_e_m);
		title = (TextView) findViewById(R.id.user_textview_level);
		brand = (TextView) findViewById(R.id.user_textview_img);
		zone = (TextView) findViewById(R.id.user_textview_zone);
		content = (TextView) findViewById(R.id.user_textView_add);
		button = (Button) findViewById(R.id.button_add_user);
	}

	private void getStringExtra(Intent intent) {
		headimg = intent.getStringExtra("userhead");
		username = intent.getStringExtra("username");
		
		this.txtName.setText(username);
		if(!headimg.equals("")) {
			ImageUtil.setThumbnailView(headimg, this.userHead, this, callback2, true);
		}		
	}
	
	ImageCallback callback2 = new ImageCallback() {
		
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) userHead.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};
}
