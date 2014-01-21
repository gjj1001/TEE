package com.tee686.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casit.tee686.R;
import com.tee686.db.DBHelper;
import com.tee686.db.PushCacheColumn;
import com.tee686.activity.DisplayActivity;
import com.tee686.utils.IntentUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class UserCollectFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener{
	private ListView mlv;
	private List<Map<String, Object>> mlist;
	private SimpleAdapter mAdapter;
	private Activity mActivity;		
	private Cursor cursor;	
	private LayoutInflater inflater;
	
	public UserCollectFragment(Activity activity) {
		mActivity = activity;
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		this.inflater = inflater;
		inflater.getContext();
		View view = inflater.inflate(R.layout.user_collect_list, null);
		mlv = (ListView) view.findViewById(R.id.user_listview_collect);
		mlist = new ArrayList<Map<String, Object>>();
		new DataAsyncTask().execute();
		
		return view;
	}

	private void getData() {		
		DBHelper dbHelper = DBHelper.getInstance(getActivity());
		cursor = dbHelper.query(PushCacheColumn.TABLE_NAME, null, null, null);//cursor现在位置处于第一条记录的前一位置
		if(cursor.moveToFirst()) {
			for (cursor.moveToLast(); !cursor.isBeforeFirst();cursor.moveToPrevious()) {
				Map<String, Object> map = new HashMap<String, Object>();				
				//判断ntitle是否存在
				if(null!=cursor.getString(3)) {
					map.put("content", cursor.getString(6));
					map.put("time", cursor.getString(0));				
					mlist.add(map);
				} else {
					map.put("content", cursor.getString(2));
					map.put("time", cursor.getString(0));				
					mlist.add(map);
				}					
			}
			
		}
			
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
		cursor.moveToPosition(cursor.getCount()-1-arg2);//修正降序排序指针位置
		new ShareAsyncTask().execute();
		// 转到详情页面
		IntentUtil.start_activity(mActivity, DisplayActivity.class);		
	}	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog ad = builder.create();
        ad.show();        
        builder.setMessage("是否要删除此条资讯?").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
         	   ad.dismiss();
         	   cursor.moveToPosition(cursor.getCount()-1-arg2);
         	   new DeleteAsyncTask().execute(arg2);
         	   Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ad.dismiss();
            }
        });
        //dialog.setCancelable(true);
        builder.show();
        return true;		
	}	
	
	/**
	 * @author Jason
	 *获得列表数据
	 */
	class DataAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			getData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAdapter = new SimpleAdapter(inflater.getContext(), mlist,
					R.layout.user_collect_list_item, new String[] { "content",
							"time" }, new int[] {
							R.id.user_textview_collectContent,
							R.id.user_textview_collectTitle });
			mlv.setAdapter(mAdapter);
			mlv.setOnItemClickListener(UserCollectFragment.this);
			mlv.setOnItemLongClickListener(UserCollectFragment.this);
		}
		
	}
	
	/**
	 * @author Jason
	 *从sqlite数据库中把数据写入sharedPreferences中，便于在Display页面中显示
	 */
	class ShareAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {			
			SharedPreferences share = getActivity().getSharedPreferences(DisplayActivity.PUSH, Context.MODE_PRIVATE);
			Editor editor = share.edit();
			editor.putString(DisplayActivity.NOTIFICATION_TITLE, cursor.getString(3));
			editor.putString(DisplayActivity.TITLE, cursor.getString(4));
			editor.putString(DisplayActivity.ALERT, cursor.getString(6));
			editor.putString(DisplayActivity.MESSAGE, cursor.getString(2));
			editor.putString(DisplayActivity.Timestamp, cursor.getString(0));
			editor.putString(DisplayActivity.RICHPUSH_HTML_PATH, cursor.getString(1));
			editor.commit();
			return null;
		}		
	}	
	
	/**
	 * @author Jason
	 *从数据库中删除相关记录
	 */
	class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			DBHelper dbHelper = DBHelper.getInstance(getActivity());			
      	    dbHelper.delete(PushCacheColumn.TABLE_NAME, cursor.getInt(5)); 
      	    cursor.requery();
      	    mlist.remove(params[0].intValue());			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);			
			mAdapter.notifyDataSetChanged();			
		}
		
	}
}
