package com.tee686.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class PushCacheColumn extends DatabaseColumn {
		
	public static final String TABLE_NAME = "push_cache";
	public static final String NOTIFICATION_TITLE = "ntitle";
	public static final String TITLE = "title";
	public static final String ALERT = "alert";
	public static final String MESSAGE = "message";
	public static final String RICHPUSH_HTML_PATH = "path";
	public static final String Timestamp = "timestamp";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);

	private static final Map<String, String> mColumnMap = new HashMap<String, String>();
	static {
		mColumnMap.put(_ID, "integer primary key autoincrement");
		mColumnMap.put(NOTIFICATION_TITLE, "text");
		mColumnMap.put(TITLE, "text");
		mColumnMap.put(ALERT, "text");
		mColumnMap.put(MESSAGE, "text");
		mColumnMap.put(RICHPUSH_HTML_PATH, "text");
		mColumnMap.put(Timestamp, "text");
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	public Uri getTableContent() {
		// TODO Auto-generated method stub
		return CONTENT_URI;
	}

	@Override
	protected Map<String, String> getTableMap() {
		// TODO Auto-generated method stub
		return mColumnMap;
	}

}
