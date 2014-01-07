package com.tee686.utils;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.casit.tee686.R;
import com.tee686.activity.UserLoginActivity;
import com.tee686.config.Urls;
import com.tee686.entity.CategorysEntity;
import com.tee686.entity.Collection;
import com.tee686.entity.Comment;
import com.tee686.entity.PubContent;
import com.tee686.https.HttpUtils;

public class PopupWindowUtils<T> implements OnClickListener {
	Context context;
	PopupWindow popupWindow;
	ViewPager mViewpager;
	Object obj;
	SharedPreferences share;
	BaseAdapter adapter;
	List<Comment> list;
	EditText editText;

	public PopupWindowUtils(ViewPager viewpager) {
		mViewpager = viewpager;
	}
	
	public PopupWindowUtils(Context context, Object obj, SharedPreferences share, 
			BaseAdapter adapter, List<Comment> list, EditText editText) {
		this.context = context;
		this.obj = obj;
		this.share = share;
		this.adapter = adapter;
		this.list = list;
		this.editText = editText;
	}

	int width = 0;

	public void showActionWindow(View parent, List<T> tabs) {
		// final RingtoneclipModel currentData = model;
		// final int res_id = currentData.getId();
		int[] location = new int[2];
		int popWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.popupWindow_width);
		parent.getLocationOnScreen(location);
		View view = getView(context, tabs);
		popupWindow = new PopupWindow(view, popWidth,
				LayoutParams.WRAP_CONTENT);// new
											// PopupWindow(view,
											// popWidth,
											// LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的最右端
		int xPos = (int) (windowManager.getDefaultDisplay().getWidth()
				- popupWindow.getWidth() - context.getResources().getDimension(
				R.dimen.popupWindow_margin));
		// popupWindow.showAsDropDown(parent, -10,0);
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, xPos,
				location[1] + parent.getHeight() - 20);
	}

	@SuppressWarnings("unchecked")
	private View getView(Context context, List<T> tabs) {
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.back_popup_more);
		for (int i = 0; i < tabs.size(); i++) {
			if (i != tabs.size() - 1) {
				String name = "";
				if (tabs.get(i) instanceof String) {
					name = ((List<String>) tabs).get(i);
				} else if (tabs.get(i) instanceof CategorysEntity) {
					name = ((List<CategorysEntity>) tabs).get(i).getName();
				}
				Button btn = getButton(context, name, i);
				ImageView img = getImageView(context);
				layout.addView(btn);
				layout.addView(img);
			} else {
				String name = "";
				if (tabs.get(i) instanceof String) {
					name = ((List<String>) tabs).get(i);
				} else if (tabs.get(i) instanceof CategorysEntity) {
					name = ((List<CategorysEntity>) tabs).get(i).getName();
				}
				Button btn = getButton(context, name, i);
				layout.addView(btn);
			}
		}

		return layout;
	}

	private Button getButton(Context context, String text, int i) {
		Button btn = new Button(context);
		btn.setText(text);
		btn.setTextColor(context.getResources().getColor(R.color.white));
		btn.setTag(i);
		btn.setPadding(20, 15, 20, 15);
		btn.setBackgroundColor(Color.TRANSPARENT);
		btn.setOnClickListener(this);
		return btn;
	}

	private static ImageView getImageView(Context context) {
		ImageView img = new ImageView(context);
		img.setBackgroundResource(R.drawable.dis_popup_side);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		return img;
	}

	@Override
	public void onClick(View v) {
		switch(Integer.parseInt(v.getTag().toString())) {
		case 0://回复
			if (share.contains(UserLoginActivity.UID)) {
				Comment comment = (Comment) obj;
				editText.setText("回复"+comment.getUsername()+":");
				break;
			}
		case 1://删除
			Comment comment = (Comment) obj;
			String urlString = String.format(Urls.USER_COMMENT+"?comtime=%s", comment.getComtime());
			new DeleteTask().execute(urlString);
			break;
		}
//		mViewpager.setCurrentItem(Integer.parseInt(v.getTag().toString()));
		popupWindow.dismiss();
	}
	
	class CollectionTask extends AsyncTask<Collection, Void, String> {

		@Override
		protected String doInBackground(Collection... params) {
			String result = HttpUtils.postByHttpURLConnection(Urls.USER_COLLECTION, params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "网络问题，请稍后再试", Toast.LENGTH_SHORT).show();
			}
		}		
	}
	
	class DeleteTask extends AsyncTask<String, Void, String> {

		String result;
		@Override
		protected String doInBackground(String... params) {
			try {
				result = HttpUtils.getByHttpClient(context, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.remove(obj);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null) {
				adapter.notifyDataSetChanged();
				Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "网络问题，请稍后再试", Toast.LENGTH_SHORT).show();
			}
		}	
	}
}
