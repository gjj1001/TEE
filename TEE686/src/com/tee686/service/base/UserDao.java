package com.tee686.service.base;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import com.tee686.config.Urls;
import com.tee686.entity.UserJson;
import com.tee686.entity.UserResponse;
import com.tee686.https.HttpUtils;
import com.tee686.utils.Utility;

public class UserDao extends BaseDao {
	private Context mContext;

	public UserDao(Context context) {
		mContext = context;
	}

	public UserResponse mapperJson(String key) {
		// TODO Auto-generated method stub
		UserJson userJson;
		try {
			if (!key.contains(":")) {
				return null;
			}
			String url = String.format(Urls.KEYBindURL, key)
					+ Utility.getParams(key);
			String result = HttpUtils.getByHttpClient(mContext, url);
			userJson = mObjectMapper.readValue(result,
					new TypeReference<UserJson>() {
					});
			if (userJson == null) {
				return null;
			}
			return userJson.getResponse();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
