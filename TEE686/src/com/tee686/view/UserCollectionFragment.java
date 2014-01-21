package com.tee686.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

import com.casit.tee686.R;
import com.tee686.config.Urls;
import com.tee686.entity.Collection;
import com.tee686.https.HttpUtils;
import com.tee686.activity.BulletinDetailActivity;
import com.tee686.activity.UserLoginActivity;
import com.tee686.utils.IntentUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
public class UserCollectionFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener{
	private ListView mlv;
	private List<Map<String, Object>> mlist;
	private SimpleAdapter mAdapter;
	private Activity mActivity;		
	private Cursor cursor;	
	private LayoutInflater inflater;
	private Collection col;
	
	SharedPreferences share; 
	private List<Collection> mlists = new ArrayList<Collection>();
	
	public UserCollectionFragment(Activity activity) {
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
		share = getActivity().getSharedPreferences(UserLoginActivity.SharedName, Context.MODE_PRIVATE);
		mlv = (ListView) view.findViewById(R.id.user_listview_collect);
		mlist = new ArrayList<Map<String, Object>>();
		new DataAsyncTask().execute(Urls.USER_COLLECTION);
		
		return view;
	}

	/*private void getData() {		
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
			
	}*/

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
		col = mlists.get(arg2);
		IntentUtil.startActivity(getActivity(), BulletinDetailActivity.class,
				new BasicNameValuePair("sendtime", col.getSendtime()),
				new BasicNameValuePair("userhead", col.getHeadimage()),
				new BasicNameValuePair("imagefile", col.getImageFile()),
				new BasicNameValuePair("username", col.getUsername()),
				new BasicNameValuePair("content", col.getContent()));		
	}	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog ad = builder.create();
        col = mlists.get(arg2);
        ad.show();        
        builder.setMessage("是否要删除此条收藏?").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
         	   ad.dismiss();
         	   new DeleteAsyncTask().execute(arg2);         	   
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
	class DataAsyncTask extends AsyncTask<String, Void, List<Collection>> {

		@Override
		protected List<Collection> doInBackground(String... params) {
			
			try {				
				String result = HttpUtils.getByHttpClient(getActivity(),
						params[0]+"?uname="+share.getString(UserLoginActivity.UID, ""));
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					Collection collection = new ObjectMapper().readValue(json, Collection.class);
					mlists.add(collection);
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
			
			
			return mlists;
		}

		@Override
		protected void onPostExecute(List<Collection> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.isEmpty()) {								
				for(int i=0; i<result.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("content", result.get(i).getContent());
					map.put("time", result.get(i).getSendtime());				
					mlist.add(map);
				}
				mAdapter = new SimpleAdapter(inflater.getContext(), mlist,
						R.layout.user_collect_list_item, new String[] { "content",
								"time" }, new int[] {R.id.user_textview_collectContent, 
					R.id.user_textview_collectTitle});		
				mlv.setAdapter(mAdapter);
				mlv.setOnItemClickListener(UserCollectionFragment.this);
				mlv.setOnItemLongClickListener(UserCollectionFragment.this);
			}
		}
		
	}	
		
	
	/**
	 * @author Jason
	 *从数据库中删除相关记录
	 */
	class DeleteAsyncTask extends AsyncTask<Integer, Void, String> {

		String result;
		@Override
		protected String doInBackground(Integer... params) {
			try {
				result = HttpUtils.getByHttpClient(getActivity(),
						Urls.USER_COLLECTION+"?sendtime="+col.getSendtime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      	    mlist.remove(params[0].intValue());
//      	    mAdapter.notifyDataSetChanged();
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);			
			if(result!=null) {
				mAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "网络问题，请稍后再试", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
