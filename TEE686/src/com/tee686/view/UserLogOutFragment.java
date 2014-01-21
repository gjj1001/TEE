package com.tee686.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.casit.tee686.R;
import com.tee686.activity.UserLoginActivity;

@SuppressLint("ValidFragment")
public class UserLogOutFragment extends Fragment implements OnClickListener {

	private Button btnLogOut;
	private TextView mtxt;

	private Context mContext;
	private Activity mActivity;
	private boolean isShowtxt;

	public UserLogOutFragment(Activity activity,boolean isshow) {
		mActivity = activity;
		isShowtxt=isshow;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		mContext = inflater.getContext();
		View view = inflater.inflate(R.layout.user_login_log_out, null);
		btnLogOut = (Button) view.findViewById(R.id.user_button_logOut);
		mtxt = (TextView) view.findViewById(R.id.user_textview_error);
		showText(isShowtxt);
		btnLogOut.setOnClickListener(this);
		return view;
	}

	public void showText(boolean isShow) {
		if (isShow) {
			mtxt.setVisibility(View.VISIBLE);
		} else {
			mtxt.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_button_logOut:
			SharedPreferences share = mContext.getSharedPreferences(
					UserLoginActivity.SharedName, Context.MODE_PRIVATE);
			SharedPreferences.Editor edit = share.edit();
			edit.remove(UserLoginActivity.UID).remove(UserLoginActivity.PWD)
			.remove(UserLoginActivity.KEY).remove(UserLoginActivity.LEVEL).commit();
			mActivity.finish();
			break;
		}
	}
}
