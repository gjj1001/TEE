package com.tee686.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lee.pullrefresh.ui.PullToRefreshBase;
import com.lee.pullrefresh.ui.PullToRefreshListView;
import com.lee.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.tee686.activity.UserCenterActivity;
import com.tee686.config.Urls;
import com.tee686.entity.Collection;
import com.tee686.entity.PubContent;
import com.tee686.https.HttpUtils;
import com.tee686.https.NetWorkHelper;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.ImageUtil.ImageCallback;
import com.tee686.utils.ImageUtil.ImageCallback1;
import com.tee686.utils.IntentUtil;
import com.tee686.utils.BulletinPopupWindow;
import com.tee686.ui.base.BaseActivity;
import com.casit.tee686.R;

//import android.widget.Button;

public class BulletinActivity extends BaseActivity {	
	
	private ImageButton newBulletin;
	private Button mCommunity;
	private Button refresh;
	private LinearLayout goHome;
	private LinearLayout listContent;
	private LinearLayout loadFailed;
	private ListView lv;
    private PullToRefreshListView mPullListView;
//	private List<Map<String, Object>> mlist;
	private MyAdapter mAdapter;
	SharedPreferences share;	
	Context context;
	private String result;
	private boolean mIsStart = true;
    private int mCurIndex = 0;
    private static final int mLoadDataCount = 50;
    @SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private List<PubContent> mPubContents = new ArrayList<PubContent>();
    private LinkedList<PubContent> mListItems = new LinkedList<PubContent>();
	
    private DataAsyncTask dataTask;
    private GetDataTask getTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		mPullListView = new PullToRefreshListView(this);
		setContentView(R.layout.bulletin);	
		mPullListView.setPullLoadEnabled(false);
        mPullListView.setScrollLoadEnabled(true);
        mCurIndex = mLoadDataCount;
		initControl();
		mCommunity.setVisibility(View.GONE);	
		initSharePreferences();	
		listContent.removeAllViews();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
		listContent.addView(mPullListView, layoutParams);
		new CheckCacheTask().execute();		
		if(savedInstanceState!=null) {
			result = savedInstanceState.getString("result");			
//			share.edit().putString("pubContents", result).commit();		
			List<PubContent> mPubContents = new ArrayList<PubContent>();
			try {
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					PubContent pubContent = new ObjectMapper().readValue(json, PubContent.class);
					mPubContents.add(pubContent);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}	
			if(!mPubContents.isEmpty()) {
				mListItems.clear();
				if(mCurIndex<mPubContents.size()){
					mListItems.addAll(mPubContents.subList(0, mCurIndex));
					mAdapter = new MyAdapter(mListItems);
				} else {
					mAdapter = new MyAdapter(mPubContents);
				}
				
//				mAdapter.appendToList(result);	
				lv = mPullListView.getRefreshableView();	
				lv.setAdapter(mAdapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						PubContent pubContent = (PubContent) mAdapter.getItem(position);
						IntentUtil.startActivity(BulletinActivity.this, BulletinDetailActivity.class,
								new BasicNameValuePair("sendtime", pubContent.getSendtime()),
								new BasicNameValuePair("userhead", pubContent.getHeadimage()),
								new BasicNameValuePair("imagefile", pubContent.getImageFile()),
								new BasicNameValuePair("username", pubContent.getUsername()),
								new BasicNameValuePair("content", pubContent.getContent()),
								new BasicNameValuePair("position", String.valueOf(position)));
					}
				});
				//权限管理：讲师以上级别才能拥有删除权限
				if(share.getInt(UserLoginActivity.LEVEL, 0)>=100) {
					lv.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							PubContent pubContent = (PubContent) mAdapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("收藏");
							tabs.add("删除");
							BulletinPopupWindow<String> util = new BulletinPopupWindow<String>(BulletinActivity.this, 
									pubContent, share, mAdapter, mAdapter.pubContents);
							util.showActionWindow(view, tabs);
							return true;
						}
					});
				} else {
					lv.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							/*final PubContent pubContent = (PubContent) mAdapter.getItem(position);
							final AlertDialog.Builder builder = new AlertDialog.Builder(BulletinActivity.this);
					        final AlertDialog ad = builder.create();
					        ad.show();        
					        builder.setMessage("是否要收藏此条公告?").setPositiveButton("是", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialogInterface, int i) {
					         	   ad.dismiss();				         	  
					         	   if(share.contains(UserLoginActivity.UID)) {
					         		   Collection collection = new Collection();
					         		   collection.setContent(pubContent.getContent());
					         		   collection.setHeadimage(pubContent.getHeadimage());
					         		   collection.setImageFile(pubContent.getImageFile());
					         		   collection.setSendtime(pubContent.getSendtime());
					         		   collection.setUsername(pubContent.getUsername());
					         		   collection.setUname(share.getString(UserLoginActivity.UID, ""));
					         		   new CollectionTask().execute(collection);
					         	   } else {
					         		   showShortToast("请先登录后再收藏");
					         	   }
					            }
					        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialogInterface, int i) {
					                ad.dismiss();
					            }
					        });
					        //dialog.setCancelable(true);
					        builder.show();
					        return true;*/
							PubContent pubContent = (PubContent) mAdapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("收藏");
//							tabs.add("删除");
							BulletinPopupWindow<String> util = new BulletinPopupWindow<String>(BulletinActivity.this, 
									pubContent, share, mAdapter, mAdapter.pubContents);
							util.showActionWindow(view, tabs);
							return true; 
						}
					});
				} 
				mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		            @Override
		            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		                mIsStart = true;
		                getTask = new GetDataTask();
		                getTask.execute(Urls.USER_DOAWLOAD_IMAGE);
		            }

		            @Override
		            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		                mIsStart = false;
		                getTask = new GetDataTask();
		                getTask.execute(Urls.USER_DOAWLOAD_IMAGE);
		            }
		        });
		        setLastUpdateTime();
		        
//		        mPullListView.doPullRefreshing(true, 500);
				listContent.setVisibility(View.VISIBLE);
				loadFailed.setVisibility(View.GONE);
//				result.clear();
			} else if("".equals(result)) {
				showShortToast("未发表公告");
				listContent.setVisibility(View.VISIBLE);
				loadFailed.setVisibility(View.GONE);
			} else {
				listContent.setVisibility(View.GONE);
				loadFailed.setVisibility(View.VISIBLE);
			}
		} else {
			if(NetWorkHelper.checkNetState(this)) {
				dataTask = new DataAsyncTask();
				dataTask.execute(Urls.USER_DOAWLOAD_IMAGE);
			} else {
				showShortToast("获取数据失败");
				listContent.setVisibility(View.GONE);
				loadFailed.setVisibility(View.VISIBLE);
			}
			
		}		
				/*new SimpleAdapter(this, mlist, R.layout.bulletin_board, new String[] {"userhead", 
				"bulletincontent", "username", "image", "sendtime"}, new int[] {R.id.iv_userhead, 
				R.id.tv_bulletincontent, R.id.tv_username, R.id.iv_bulletin, R.id.tv_sendtime});*/
		
		goHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (share.contains(UserLoginActivity.KEY)
						&& !share.getString(UserLoginActivity.KEY, "").equals(
								"")) {
					IntentUtil.start_activity(BulletinActivity.this,
							UserCenterActivity.class);
					finish();
				} else {
					showLongToast(getResources().getString(
							R.string.user_center_error));
				}
			}
		});
				
		newBulletin.setOnClickListener(new OnClickListener() {				
			@Override
			public void onClick(View v) {
				if(share.contains(UserLoginActivity.UID)) {	
//					if(share.getInt(UserLoginActivity.LEVEL, 0)>=50) {
						IntentUtil.start_activity(BulletinActivity.this, EditActivity.class);
//						defaultFinish();
//					} 
//					else {
//						showShortToast("学员不能发布公告");
//					}
				}else {
					showShortToast("请先登录");
				}
			}
		});			
		
		
		
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DataAsyncTask().execute(Urls.USER_DOAWLOAD_IMAGE);
			}
		});
		
	}
	
	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mPubContents.isEmpty()) {
			new DataAsyncTask().execute(Urls.USER_DOAWLOAD_IMAGE);
		} else {
			mAdapter = new MyAdapter(mPubContents);
			lv.setAdapter(mAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					PubContent pubContent = (PubContent) mAdapter.getItem(position);
					IntentUtil.startActivity(BulletinActivity.this, BulletinDetailActivity.class,
							new BasicNameValuePair("sendtime", pubContent.getSendtime()),
							new BasicNameValuePair("userhead", pubContent.getHeadimage()),
							new BasicNameValuePair("imagefile", pubContent.getImageFile()),
							new BasicNameValuePair("username", pubContent.getUsername()),
							new BasicNameValuePair("content", pubContent.getContent()),
							new BasicNameValuePair("position", String.valueOf(position)));					
				}
			});
		}
		
		
	}*/

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK) {
			switch (requestCode) {
			case 1:
				lv.setSelection(Integer.valueOf(data.getStringExtra("pos")));
				break;

			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}*/

	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(this, IndexActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	}*/

	

	private void initSharePreferences() {
		// TODO Auto-generated method stub
		share = getSharedPreferences(UserLoginActivity.SharedName, Context.MODE_PRIVATE);
		/*if (share.contains("pubContents")) {
			try {
				JSONArray jsonArray = new JSONArray(share.getString("pubContents", ""));
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					PubContent pubContent = new ObjectMapper().readValue(json, PubContent.class);
					mPubContents.add(pubContent);
				}
				mAdapter = new MyAdapter();
				mAdapter.appendToList(mPubContents);
				lv.setAdapter(mAdapter);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} else {
			new DataAsyncTask().execute(Urls.USER_DOAWLOAD_IMAGE);
		}*/
	}	
	

	private void initControl() {
		newBulletin = (ImageButton) findViewById(R.id.newbulletin);		
		goHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		listContent = (LinearLayout) findViewById(R.id.list_content);
		loadFailed = (LinearLayout) findViewById(R.id.view_load_fail);
		mCommunity = (Button) findViewById(R.id.btn_community);
		refresh = (Button) findViewById(R.id.bn_refresh);
		lv = (ListView) findViewById(R.id.lv_community_content);
//		mlist = new ArrayList<Map<String,Object>>();
	}	
	
	/**
	 * @author Jason
	 *获得列表数据
	 */
	class DataAsyncTask extends AsyncTask<String, Void, List<PubContent>> {		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showAlertDialog("温馨提示", "正在加载列表信息请稍后");
		}

		@Override
		protected List<PubContent> doInBackground(String... params) {			
			if(isCancelled()) {
				return null;
			}
			try {				
				result = HttpUtils.getByHttpClient(BulletinActivity.this, params[0]);	
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					PubContent pubContent = new ObjectMapper().readValue(json, PubContent.class);
					mPubContents.add(pubContent);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mAlertDialog.dismiss();
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
			
			
			return mPubContents;
		}

		@Override
		protected void onPostExecute(List<PubContent> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(mAlertDialog.isShowing()) {
				mAlertDialog.dismiss();
			}			
			if(!result.isEmpty()) {
				if(mCurIndex<result.size()){
					mListItems.addAll(result.subList(0, mCurIndex));
					mAdapter = new MyAdapter(mListItems);
				} else {
					mAdapter = new MyAdapter(result);
				}
				
//				mAdapter.appendToList(result);	
				lv = mPullListView.getRefreshableView();
				lv.setAdapter(mAdapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						PubContent pubContent = (PubContent) mAdapter.getItem(position);
						IntentUtil.startActivity(BulletinActivity.this, BulletinDetailActivity.class,
								new BasicNameValuePair("sendtime", pubContent.getSendtime()),
								new BasicNameValuePair("userhead", pubContent.getHeadimage()),
								new BasicNameValuePair("imagefile", pubContent.getImageFile()),
								new BasicNameValuePair("username", pubContent.getUsername()),
								new BasicNameValuePair("content", pubContent.getContent()),
								new BasicNameValuePair("position", String.valueOf(position)));
					}
				});
				//权限管理：讲师以上级别才能拥有删除权限
				if(share.getInt(UserLoginActivity.LEVEL, 0)>=100) {
					lv.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							PubContent pubContent = (PubContent) mAdapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("收藏");
							tabs.add("删除");
							BulletinPopupWindow<String> util = new BulletinPopupWindow<String>(BulletinActivity.this, 
									pubContent, share, mAdapter, mAdapter.pubContents);
							util.showActionWindow(view, tabs);
							return true;
						}
					});
				} else {
					lv.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							/*final PubContent pubContent = (PubContent) mAdapter.getItem(position);
							final AlertDialog.Builder builder = new AlertDialog.Builder(BulletinActivity.this);
					        final AlertDialog ad = builder.create();
					        ad.show();        
					        builder.setMessage("是否要收藏此条公告?").setPositiveButton("是", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialogInterface, int i) {
					         	   ad.dismiss();				         	  
					         	   if(share.contains(UserLoginActivity.UID)) {
					         		   Collection collection = new Collection();
					         		   collection.setContent(pubContent.getContent());
					         		   collection.setHeadimage(pubContent.getHeadimage());
					         		   collection.setImageFile(pubContent.getImageFile());
					         		   collection.setSendtime(pubContent.getSendtime());
					         		   collection.setUsername(pubContent.getUsername());
					         		   collection.setUname(share.getString(UserLoginActivity.UID, ""));
					         		   new CollectionTask().execute(collection);
					         	   } else {
					         		   showShortToast("请先登录后再收藏");
					         	   }
					            }
					        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialogInterface, int i) {
					                ad.dismiss();
					            }
					        });
					        //dialog.setCancelable(true);
					        builder.show();
					        return true;*/
							PubContent pubContent = (PubContent) mAdapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("收藏");
//							tabs.add("删除");
							BulletinPopupWindow<String> util = new BulletinPopupWindow<String>(BulletinActivity.this, 
									pubContent, share, mAdapter, mAdapter.pubContents);
							util.showActionWindow(view, tabs);
							return true;
						}
					});
				} 
				mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		            @Override
		            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		                mIsStart = true;
		                getTask = new GetDataTask();
		                getTask.execute(Urls.USER_DOAWLOAD_IMAGE);
		            }

		            @Override
		            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		                mIsStart = false;
		                if(mCurIndex<mPubContents.size()) {
		                	getTask = new GetDataTask();
		                	getTask.execute(Urls.USER_DOAWLOAD_IMAGE);
		                } else {
		                	mPullListView.onPullDownRefreshComplete();
		                    mPullListView.onPullUpRefreshComplete();
		                    mPullListView.setHasMoreData(false);
		                }
		            }
		        });
		        setLastUpdateTime();
		        
//		        mPullListView.doPullRefreshing(true, 500);
				listContent.setVisibility(View.VISIBLE);
				loadFailed.setVisibility(View.GONE);
//				result.clear();
			} else {
				showShortToast("获取数据失败");
				listContent.setVisibility(View.GONE);
				loadFailed.setVisibility(View.VISIBLE);
			}
			
		}
		
	}
	
	class CheckCacheTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ImageUtil.checkCache(BulletinActivity.this);
			return null;
		}
		
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
				showShortToast(result);
			} else {
				showShortToast("网络问题，请稍后再试");
			}
		}
		
	}
	
	private class GetDataTask extends AsyncTask<String, Void, List<PubContent>> {

        @Override
        protected List<PubContent> doInBackground(String... params) {
            // Simulates a background job.
        	if(isCancelled()) {
				return null;
			}
        	try {
            	Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            try {				
				result = "";
            	result = HttpUtils.getByHttpClient(BulletinActivity.this, params[0]);	
				StringBuilder sb = new StringBuilder(result);
				result = sb.deleteCharAt(result.lastIndexOf(",")).toString();
//				share.edit().putString("pubContents", result).commit();		
				JSONArray jsonArray = new JSONArray(result);
            	mPubContents.clear();
				for(int i=0; i<jsonArray.length(); i++) {
					String json = jsonArray.getString(i);
					PubContent pubContent = new ObjectMapper().readValue(json, PubContent.class);
					mPubContents.add(pubContent);
				}
				
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
			
			
			return mPubContents;
        }

        @Override
        protected void onPostExecute(List<PubContent> result) {
            boolean hasMoreData = true;
            if (mIsStart) {
            	mListItems.clear();
            	if(mCurIndex<result.size()){
					mListItems.addAll(result.subList(0, mCurIndex));
            		mAdapter = new MyAdapter(mListItems);
				} else {
					mAdapter = new MyAdapter(result);
				}
            	lv.setAdapter(mAdapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						PubContent pubContent = (PubContent) mAdapter.getItem(position);
						IntentUtil.startActivity(BulletinActivity.this, BulletinDetailActivity.class,
								new BasicNameValuePair("sendtime", pubContent.getSendtime()),
								new BasicNameValuePair("userhead", pubContent.getHeadimage()),
								new BasicNameValuePair("imagefile", pubContent.getImageFile()),
								new BasicNameValuePair("username", pubContent.getUsername()),
								new BasicNameValuePair("content", pubContent.getContent()),
								new BasicNameValuePair("position", String.valueOf(position)));
					}
				});
				//权限管理：讲师以上级别才能拥有删除权限
				if(share.getInt(UserLoginActivity.LEVEL, 0)>=100) {
					lv.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							PubContent pubContent = (PubContent) mAdapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("收藏");
							tabs.add("删除");
							BulletinPopupWindow<String> util = new BulletinPopupWindow<String>(BulletinActivity.this, 
									pubContent, share, mAdapter, mAdapter.pubContents);
							util.showActionWindow(view, tabs);
							return true;
						}
					});
				} else {
					lv.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							/*final PubContent pubContent = (PubContent) mAdapter.getItem(position);
							final AlertDialog.Builder builder = new AlertDialog.Builder(BulletinActivity.this);
					        final AlertDialog ad = builder.create();
					        ad.show();        
					        builder.setMessage("是否要收藏此条公告?").setPositiveButton("是", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialogInterface, int i) {
					         	   ad.dismiss();				         	  
					         	   if(share.contains(UserLoginActivity.UID)) {
					         		   Collection collection = new Collection();
					         		   collection.setContent(pubContent.getContent());
					         		   collection.setHeadimage(pubContent.getHeadimage());
					         		   collection.setImageFile(pubContent.getImageFile());
					         		   collection.setSendtime(pubContent.getSendtime());
					         		   collection.setUsername(pubContent.getUsername());
					         		   collection.setUname(share.getString(UserLoginActivity.UID, ""));
					         		   new CollectionTask().execute(collection);
					         	   } else {
					         		   showShortToast("请先登录后再收藏");
					         	   }
					            }
					        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialogInterface, int i) {
					                ad.dismiss();
					            }
					        });
					        //dialog.setCancelable(true);
					        builder.show();
					        return true;*/
							PubContent pubContent = (PubContent) mAdapter.getItem(position);
							List<String> tabs = new ArrayList<String>();
							tabs.add("收藏");
//							tabs.add("删除");
							BulletinPopupWindow<String> util = new BulletinPopupWindow<String>(BulletinActivity.this, 
									pubContent, share, mAdapter, mAdapter.pubContents);
							util.showActionWindow(view, tabs);
							return true;
						}
					});
				} 
                showShortToast("内容刷新完成");
            } else {
                int start = mCurIndex;
                int end = mCurIndex + mLoadDataCount;
                if (end >= result.size()) {
                    end = result.size();
                    hasMoreData = false;
                }
                
                for (int i = start; i < end; ++i) {
                	mListItems.add(result.get(i));
                }
                
                mCurIndex = end;
                mAdapter.notifyDataSetChanged();
            }
            
           
            mPullListView.onPullDownRefreshComplete();
            mPullListView.onPullUpRefreshComplete();
            mPullListView.setHasMoreData(hasMoreData);
            setLastUpdateTime();

            super.onPostExecute(result);
        }
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("result", result);
		if (mRunningTask != null && mRunningTask.isCancelled() == false) {
			mRunningTask.cancel(false);
			mRunningTask = null;
		}
		if (mAlertDialog != null) {
			mAlertDialog.dismiss();
			mAlertDialog = null;
		}
	}

	private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        mPullListView.setLastUpdatedLabel(text);
    }

    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        
        return mDateFormat.format(new Date(time));
    }

	ImageCallback callback = new ImageCallback() {

		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) lv.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};
	
	ImageCallback1 callback1 = new ImageCallback1() {
		
		@Override
		public void loadImage(Bitmap bitmap, String imagePath, View v) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) v.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};

	class MyAdapter extends BaseAdapter {
		
		private List<PubContent> pubContents = new ArrayList<PubContent>();	
		
		public MyAdapter() {}		
		
		public MyAdapter(List<PubContent> pubContents) {
			this.pubContents = pubContents;
		}
		/*public void appendToList(List<PubContent> lists) {
			if (lists == null) {
				return;
			}
			pubContents.addAll(lists);
			notifyDataSetChanged();
		}*/
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pubContents.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pubContents.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PubContent pubContent = pubContents.get(position);
			ViewHolder viewHolder = null;
			convertView = getLayoutInflater().inflate(R.layout.bulletin_board, null);
			viewHolder = new ViewHolder();
			viewHolder.ivHeadimage = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_bulletincontent);
			viewHolder.ivImagefile = (ImageView) convertView
					.findViewById(R.id.iv_imagefile);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tv_pub_username);
			viewHolder.tvSendtime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
//			convertView.setTag(viewHolder);
			/*} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}*/
			viewHolder.tvContent.setText(pubContent.getContent());
			viewHolder.tvSendtime.setText(pubContent.getSendtime());
			viewHolder.tvUsername.setText(pubContent.getUsername());
//			viewHolder.ivHeadimage.setTag(pubContent.getHeadimage());
//			viewHolder.ivImagefile.setTag(pubContent.getImageFile());
		
			if (!"".equals(pubContent.getHeadimage())) {
				// new HeadImgAsyncTask().execute(pubContent.getHeadimage());
				ImageUtil.setThumbnailView(pubContent.getHeadimage(),
						viewHolder.ivHeadimage, BulletinActivity.this, callback, true);
			}
			if (pubContent.getImageFile() != null) {					
				ImageUtil.setThumbnailView(pubContent.getImageFile(),
						viewHolder.ivImagefile, BulletinActivity.this, callback1, true, viewHolder.ivImagefile);
				
				/*if(headImage!=null) {
					viewHolder.ivHeadimage.setImageBitmap(headImage);
				}*/
				
			}
			
			/*viewHolder.ivImagefile.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					viewHolder.ivImagefile.setImageResource(R.drawable.umeng_update_close_bg_normal);
				}
			});*/
			
			return convertView;
		}

		private class ViewHolder {
			public ImageView ivHeadimage;
			public TextView tvContent;
			public ImageView ivImagefile;
			public TextView tvUsername;
			public TextView tvSendtime;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dataTask != null && dataTask.getStatus() == AsyncTask.Status.RUNNING) {
			dataTask.cancel(true);
		}
		if (getTask != null && getTask.getStatus() == AsyncTask.Status.RUNNING) {
			getTask.cancel(true);
		}
	}

		
}
