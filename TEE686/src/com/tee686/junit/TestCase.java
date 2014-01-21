package com.tee686.junit;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.tee686.config.Urls;
import com.tee686.entity.UserInfoItem;

import android.annotation.SuppressLint;
import android.test.AndroidTestCase;

public class TestCase extends AndroidTestCase {
	UserInfoItem userInfoItem;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat format = new SimpleDateFormat("yyMMdd");

	public void testRegister() {
		StringBuffer result = new StringBuffer();
		userInfoItem = new UserInfoItem("13808005693", "新", "321",
				"FEMALE", "19850405", "", "", format.format(new Date()), "QQ","");
		try {
			URL url = new URL(Urls.USER_REGISTER);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
			conn.setRequestProperty("Content-Length", "100");
			OutputStream out = conn.getOutputStream();
			new ObjectMapper().writeValue(out, userInfoItem);
			out.flush();
			if (conn.getResponseCode() == 200) {
				byte[] buffer = new byte[1024];
				while (conn.getInputStream().read(buffer) != -1) {
					result.append(new String(buffer, "utf-8"));
				}
				System.out.println(result.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
