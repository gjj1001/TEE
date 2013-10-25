package com.tee686.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.UserInfoItem;
import com.tee686.entity.UserResponse;
import com.tee686.indicator.PageIndicator;
import com.tee686.ui.base.BaseFragmentActivity;
import com.tee686.utils.PopupWindowUtil;
import com.tee686.view.UserIntroFragment;
import com.tee686.view.UserLogOutFragment;

public class UserCenterActivity extends BaseFragmentActivity implements
		OnClickListener {
	
	public static String UID = "uid";
	private ImageView imgTitleButton;
	private String result = "";

	ViewPager mViewPager;
	TabPageAdapter mTabsAdapter;
	PageIndicator mIndicator;
	LinearLayout llGoHome;
	private LinearLayout loadLayout;
	private LinearLayout loadFaillayout;

	ImageView ImgLeft;
	ImageView ImgRight;

	private SharedPreferences share;

	private UserInfoItem mUserInfoItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.user_center_activity);
		initControl();
		share = getSharedPreferences(UserLoginActivity.SharedName,
				Context.MODE_PRIVATE);
		if(savedInstanceState != null) {
			try {
				mUserInfoItem = new ObjectMapper().readValue(savedInstanceState.getString("json"), UserInfoItem.class);
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
				UserLogOutFragment fragment=new UserLogOutFragment(
						UserCenterActivity.this,true);
				
				mTabsAdapter.addTab(getString(R.string.user_center_get_info_error),fragment);
				return;
			}
			/*mTabsAdapter.addTab(getString(R.string.user_center_my_Collect), new UserCollectFragment(result,
					UserCenterActivity.this));*/
			mTabsAdapter.addTab(getString(R.string.user_center_my_Intro), new UserIntroFragment(mUserInfoItem));
			mTabsAdapter.addTab(getString(R.string.user_center_exit), new UserLogOutFragment(
					UserCenterActivity.this,false));
			mTabsAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(0);
		} else {
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
		imgTitleButton = (ImageView) findViewById(R.id.imageview_user_title);
		imgTitleButton.setImageResource(R.drawable.button_user_more);
		imgTitleButton.setOnClickListener(new myOnClickListener());
		mViewPager = (ViewPager) findViewById(R.id.user_pager);
		mViewPager.setOffscreenPageLimit(2);
		mIndicator = (PageIndicator) findViewById(R.id.user_indicator);
		mIndicator
				.setOnPageChangeListener(new IndicatorOnPageChangedListener());
		llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		llGoHome.setOnClickListener(this);
		loadLayout = (LinearLayout) findViewById(R.id.view_loading);
		loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
	}

	private void initViewPager() {
		mTabsAdapter = new TabPageAdapter(this);
		mViewPager.setAdapter(mTabsAdapter);
		mIndicator.setViewPager(mViewPager);
		String url = String.format(Urls.USER_INFO, share.getString(UID, ""));
		new ContentAsyncTask().execute(url);
	}

	// [end]
	public class TabPageAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments;
		public List<String> tabs = new ArrayList<String>();

		private final int[] COLORS = new int[] { R.color.red, R.color.green,
				R.color.blue, R.color.white, R.color.black };

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

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loadLayout.setVisibility(View.VISIBLE);
		}

		@Override
		protected UserInfoItem doInBackground(String... params) {
			
			try {
				result = HttpUtil.queryStringForGet(params[0]);				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				mUserInfoItem = new ObjectMapper().readValue(result, UserInfoItem.class);
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
		protected void onPostExecute(UserInfoItem result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loadLayout.setVisibility(View.GONE);
			if (result == null) {
				UserLogOutFragment fragment=new UserLogOutFragment(
						UserCenterActivity.this,true);
				
				mTabsAdapter.addTab(getString(R.string.user_center_get_info_error),fragment);
				return;
			}
			/*mTabsAdapter.addTab(getString(R.string.user_center_my_Collect), new UserCollectFragment(result,
					UserCenterActivity.this));*/
			mTabsAdapter.addTab(getString(R.string.user_center_my_Intro), new UserIntroFragment(result));
			mTabsAdapter.addTab(getString(R.string.user_center_exit), new UserLogOutFragment(
					UserCenterActivity.this,false));
			mTabsAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(0);
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
			/*case 0:
				ImgLeft.setVisibility(8);
				break;*/
			case 1:
				ImgRight.setVisibility(8);
				ImgLeft.setVisibility(0);
				break;
			default:
				ImgLeft.setVisibility(8);
				ImgRight.setVisibility(0);
			}
		}

	}

	private class myOnClickListener implements OnClickListener {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imageview_user_title:
				new PopupWindowUtil(mViewPager).showActionWindow(v,
						UserCenterActivity.this, mTabsAdapter.tabs);
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
		
}