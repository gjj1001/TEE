package com.tee686.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.casit.tee686.R;
import com.readystatesoftware.viewbadger.BadgeView;
import com.tee686.config.Constants;
import com.tee686.config.Urls;
import com.tee686.entity.UserInfoItem;
import com.tee686.https.HttpUtils;
import com.tee686.https.NetWorkHelper;
import com.tee686.indicator.PageIndicator;
import com.tee686.ui.base.BaseFragmentActivity;
import com.tee686.utils.IntentUtil;
import com.tee686.view.UserCollectFragment;
import com.tee686.view.UserCollectionFragment;
import com.tee686.view.UserIntroFragment;
import com.tee686.view.UserLogOutFragment;

public class UserCenterActivity extends BaseFragmentActivity implements
		OnClickListener {

	public static String UID = "uid";
	private Button mCommunity;
	private Button ref_buButton;
	private String result = "";
	private BadgeView badgeView;

	ViewPager mViewPager;
	TabPageAdapter mTabsAdapter;
	PageIndicator mIndicator;
	LinearLayout llGoHome;
//	Intent intent;
	private LinearLayout loadLayout;
	private LinearLayout loadFaillayout;

	ImageView ImgLeft;
	ImageView ImgRight;

	private SharedPreferences share;

	private UserInfoItem mUserInfoItem;
	private Intent intent;
	private ContentAsyncTask contentTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_center_activity);
		initControl();
		share = getSharedPreferences(UserLoginActivity.SharedName,
				Context.MODE_PRIVATE);
		/*if (savedInstanceState != null) {
			try {
				mUserInfoItem = new ObjectMapper().readValue(
						savedInstanceState.getString("json"),
						UserInfoItem.class);
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
			loadLayout.setVisibility(View.GONE);
			mTabsAdapter = new TabPageAdapter(this);
			mViewPager.setAdapter(mTabsAdapter);
			mIndicator.setViewPager(mViewPager);
			if (mUserInfoItem == null) {
				UserLogOutFragment fragment = new UserLogOutFragment(
						UserCenterActivity.this, true);

				mTabsAdapter.addTab(
						getString(R.string.user_center_get_info_error),
						fragment);
				return;
			}
			
			mTabsAdapter.addTab(getString(R.string.user_center_my_Collect),
					new UserCollectFragment(UserCenterActivity.this));			 
			mTabsAdapter.addTab(getString(R.string.user_center_my_Intro),
					new UserIntroFragment(mUserInfoItem));
			mTabsAdapter.addTab(getString(R.string.user_center_exit),
					new UserLogOutFragment(UserCenterActivity.this, false));
			mTabsAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(0);
		}*/ 
		if (!NetWorkHelper.checkNetState(this)) {
			loadLayout.setVisibility(View.GONE);
			loadFaillayout.setVisibility(View.VISIBLE);
		}
		else {
			initViewPager();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("json", result);
	}

	// [start]初始化
	private void initControl() {
		ImgLeft = (ImageView) findViewById(R.id.imageview_user_left);
		ImgRight = (ImageView) findViewById(R.id.imageview_user_right);
		mCommunity = (Button) findViewById(R.id.btn_community);		
		mCommunity.setOnClickListener(new myOnClickListener());
		ref_buButton = (Button) findViewById(R.id.bn_refresh);
		ref_buButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(NetWorkHelper.checkNetState(UserCenterActivity.this)) {
					mTabsAdapter = new TabPageAdapter(UserCenterActivity.this);
					mViewPager.setAdapter(mTabsAdapter);
					mIndicator.setViewPager(mViewPager);
					contentTask = new ContentAsyncTask();
					contentTask.execute(String.format(Urls.USER_INFO, share.getString(UID, "")));
					loadFaillayout.setVisibility(View.GONE);
				}				
			}
		});
		mViewPager = (ViewPager) findViewById(R.id.user_pager);
		mViewPager.setOffscreenPageLimit(3);
		mIndicator = (PageIndicator) findViewById(R.id.user_indicator);
		mIndicator
				.setOnPageChangeListener(new IndicatorOnPageChangedListener());
		llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		llGoHome.setOnClickListener(this);
		loadLayout = (LinearLayout) findViewById(R.id.view_loading);
		loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
		badgeView = new BadgeView(UserCenterActivity.this, mCommunity);
//		intent = new Intent(this, CheckNewService.class);
	}

	private void initViewPager() {
		mTabsAdapter = new TabPageAdapter(this);
		mViewPager.setAdapter(mTabsAdapter);
		mIndicator.setViewPager(mViewPager);
		String url = String.format(Urls.USER_INFO, share.getString(UID, ""));
		contentTask = new ContentAsyncTask();
		contentTask.execute(url);
	}

	// [end]
	public class TabPageAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments;
		public List<String> tabs = new ArrayList<String>();

		/*private final int[] COLORS = new int[] { R.color.red, R.color.green,
				R.color.blue, R.color.white, R.color.black };*/

		public TabPageAdapter(UserCenterActivity userCenterActivity) {
			super(userCenterActivity.getSupportFragmentManager());
			mFragments = new ArrayList<Fragment>();

		}

		public void addTab(String title, Fragment fragment) {
			mFragments.add(fragment);
			tabs.add(title);
			notifyDataSetChanged();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabs.get(position);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	public class ContentAsyncTask extends AsyncTask<String, Void, UserInfoItem> {		

		private volatile boolean running = true;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loadLayout.setVisibility(View.VISIBLE);
		}

		@Override
		protected UserInfoItem doInBackground(String... params) {
			if(isCancelled()) {
				return null;
			}
			try {
				result = HttpUtils.getByHttpClient(UserCenterActivity.this, params[0]);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				mUserInfoItem = new ObjectMapper().readValue(result,
						UserInfoItem.class);
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
			return mUserInfoItem;		
			
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			loadLayout.setVisibility(View.GONE);
			UserLogOutFragment fragment = new UserLogOutFragment(
					UserCenterActivity.this, true);

			mTabsAdapter.addTab(
					getString(R.string.user_center_get_info_error),
					fragment);
		}

		@Override
		protected void onPostExecute(UserInfoItem result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loadLayout.setVisibility(View.GONE);
			if (result == null) {
				
				UserLogOutFragment fragment = new UserLogOutFragment(
						UserCenterActivity.this, true);

				mTabsAdapter.addTab(
						getString(R.string.user_center_get_info_error),
						fragment);
				return;
			}
			
			mTabsAdapter.addTab(getString(R.string.user_center_my_Collect),
					new UserCollectFragment(UserCenterActivity.this));		
			mTabsAdapter.addTab(getString(R.string.user_center_collection),
					new UserCollectionFragment(UserCenterActivity.this));
			mTabsAdapter.addTab(getString(R.string.user_center_my_Intro),
					new UserIntroFragment(result));
			mTabsAdapter.addTab(getString(R.string.user_center_exit),
					new UserLogOutFragment(UserCenterActivity.this, false));
			mTabsAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(2);
		}
	}

	private class IndicatorOnPageChangedListener implements
			OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			
			case 0: 
				ImgLeft.setVisibility(8);				
				break;
			case 1:
				ImgRight.setVisibility(0);	
				ImgLeft.setVisibility(0);
				break;
			case 3:
				ImgRight.setVisibility(8);				
				break;
			default:
				ImgLeft.setVisibility(0);
				ImgRight.setVisibility(0);
			}
		}

	}

	private class myOnClickListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_community:
				if(badgeView.isShown()) {
					badgeView.hide(true);
				}
				IntentUtil.start_activity(UserCenterActivity.this, BulletinActivity.class);
				finish();
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Linear_above_toHome:
			finish();
			break;
		}
	}	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(NetWorkHelper.checkNetState(this)) {
			intent = new Intent(this, CheckNewService.class);
			startService(intent);
		}		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ReceiverAction.CHECK_NEW_PUB);
		registerReceiver(checkNewReceiver, filter);
       
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(checkNewReceiver);
		intent = new Intent(this, CheckNewService.class);
 	    stopService(intent);
		if (contentTask != null
				&& contentTask.getStatus() == AsyncTask.Status.RUNNING) {
			contentTask.cancel(true);
		}
 	    
	}

	BroadcastReceiver checkNewReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub			
//			badgeView.setBackgroundResource(R.drawable.umeng_xp_point_selected);
			badgeView.setText(String.valueOf(intent.getIntExtra("num", 0)));
			badgeView.show(true);
		}
	};

}
