package com.tee686.service.base;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

<<<<<<< HEAD
import cn.wodong.capturevideo.MainActivity;

import com.tee686.activity.ReplyActivity;
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
import com.tee686.config.Urls;
import com.tee686.db.MessageDbHelper.MessageColumns;
import com.tee686.entity.Message;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
import android.widget.Toast;

public class MessageService {
	private Context context;
<<<<<<< HEAD
	private String audioFlag = "false";
	private String vedioFlag = "false";
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
//	private String user;
//	private Socket socket;
//	private DataOutputStream output;
//	private DataInputStream input;
//	private Map<Integer, Bitmap> imgMap = new HashMap<Integer, Bitmap>();	//缓存在线用户头像数据
	public MessageService(Context context) {
		this.context = context;
	}
	
	/**
	 * 建立连接
	 * @return
	 */
	/*public void startConnect(User user, IhandleMessge handle) throws IOException{
		this.user = user;
		String ip = user.getIp();
		String port = user.getPort();
		long id = user.getId();
		try {
			SocketAddress socAddress = new InetSocketAddress(InetAddress.getByName(ip), Integer.parseInt(port)); 
			socket = new Socket();
			socket.connect(socAddress, 5*1000);
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			//处理用户登录
			String str = ContentFlag.ONLINE_FLAG + id;
			output.writeUTF(str);
			// 缓存其它登录者的头像数据
			int fileNums = input.readInt(); // 图片文件的数量
			for (int i = 0; i < fileNums; i++) {
				int tempId = Integer.parseInt(input.readUTF());
				byte[] datas = StreamTool.readStream(input);
			    Bitmap tempImg = BitmapFactory.decodeByteArray(datas, 0, datas.length);
				imgMap.put(tempId, tempImg);
			}
			//接收消息
			receiveMsg(handle);
		} catch (IOException e) {
			throw new IOException("fail connect to the server");
		} 
	}*/
	
	/**
	 * 应用退出
	 */
	/*public void quitApp() {
		String sendStr="";
		if(null!= user){
			sendStr = ContentFlag.OFFLINE_FLAG + user;
		}
		if (null!= socket && !socket.isClosed()) {
			if (socket.isConnected()) {
				if (!socket.isOutputShutdown()) {
					try {
						output.writeUTF(sendStr);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (null != input)  input.close();
							if (null != output) output.close();
							if (null != socket) socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}*/
	
	/**
	 * 接收消息
	 * @return
	 * @throws IOException 
	 */
	/*public void receiveMsg(IhandleMessge handle) throws IOException {
		try {
			while(true){
				String msgCtn = input.readUTF();
				if(msgCtn.startsWith(ContentFlag.ONLINE_FLAG)){				//处理登录消息
					String json = input.readUTF();
					Message msg = parseJsonToObject(json);
					Log.i(ContentFlag.TAG, msg.toString());
					byte[] datas = StreamTool.readStream(input);
				    Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
					msg.setBitmap(bitmap);
					handle.handleMsg(msg);
					imgMap.put(msg.getId(), bitmap);
				}else if(msgCtn.startsWith(ContentFlag.OFFLINE_FLAG)){		//处理退出消息
					String json = input.readUTF();
					Message msg = parseJsonToObject(json);
					msg.setBitmap(imgMap.get(msg.getId()));
					handle.handleMsg(msg);
					imgMap.remove(msg.getId());
				}else if(msgCtn.startsWith(ContentFlag.RECORD_FLAG)){		//处理语音消息
					String filename = msgCtn.substring(ContentFlag.RECORD_FLAG.length());
					File dir = new File(Environment.getExternalStorageDirectory() + "/recordMsg/");
					if(!dir.exists()) dir.mkdirs();
					File file = new File(dir, filename);
					String json = input.readUTF();
					Message msg = parseJsonToObject(json);
					msg.setRecord_path(file.getAbsolutePath());
					msg.setBitmap(imgMap.get(msg.getId()));
					msg.setIfyuyin(true);
					handle.handleMsg(msg);
					saveRecordFile(file);
				}else{														//处理普通消息
					Message msg = parseJsonToObject(msgCtn);
					msg.setBitmap(imgMap.get(msg.getId()));
					handle.handleMsg(msg);
				}
			}
		} catch (Exception e) {
			if (!socket.isClosed()) {
				throw new IOException("fail connect to the server");
			}
		}
	}*/
	
	/**
	 * 保存录音文件
	 * @param filename
	 * @param input2
	 * @throws IOException 
	 */
	/*private void saveRecordFile(File file) throws IOException {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			byte[] datas = StreamTool.readStream(input);
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(datas);
		}
	}*/

	/**
	 * 解析json字符串
	 */
	public Message parseJsonToObject(String json){
		try {
			JSONArray arrays = new JSONArray(json);
			JSONObject jsonObject = arrays.getJSONObject(0);
			int userId = Integer.parseInt(jsonObject.getString(MessageColumns.ID));	//用户的ID
			String send_person = jsonObject.getString(MessageColumns.SEND_PERSON);	//发送者
			String send_ctn = jsonObject.getString(MessageColumns.SEND_CTN);			//发送内容
			String send_date = jsonObject.getString(MessageColumns.SEND_DATE);		//发送时间
			String msg_id = jsonObject.getString(MessageColumns.MSG_ID);
			Message msg = new Message();
			msg.setId(userId);
			msg.setSend_ctn(send_ctn);
			msg.setSend_person(send_person);
			msg.setSend_date(send_date);
			msg.setMsgId(msg_id);
			msg.setIfyuyin(false);
			if(jsonObject.has("recordTime")){
				String recordTime = jsonObject.getString("recordTime");
				msg.setRecordTime(Long.valueOf(recordTime));
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送消息
	 * @param ctn
	 * @throws IOException 
	 */
	/*public void sendMsg(String ctn) throws Exception {
		output.writeUTF(ctn);
	}*/
	
	/**
	 * 发送语音消息
	 * @param file
	 * @throws Exception 
	 */
	public void sendRecordMsg(File file, long recordTime, String reply_person, String send_date,
<<<<<<< HEAD
			String send_person, String bitmap, int flag) throws Exception{
=======
			String send_person, String bitmap) throws Exception{
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		FileInputStream inputStream = null;
		HttpURLConnection conn = null;
		int length = 0;
		try {
<<<<<<< HEAD
			switch (flag) {
			case 1://上传语音
				audioFlag = "true";
				HttpClient client = new DefaultHttpClient();
				// 设置上传参数
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				formparams.add(new BasicNameValuePair("audioflag", audioFlag));
				formparams.add(new BasicNameValuePair("recordtime", Long.toString(recordTime)));
				formparams.add(new BasicNameValuePair("reply_person", reply_person));
				formparams.add(new BasicNameValuePair("send_person", send_person));
				formparams.add(new BasicNameValuePair("bitmap", bitmap));
				formparams.add(new BasicNameValuePair("send_date", send_date));
				HttpPost post = new HttpPost(Urls.USER_CONVERSATION);	
				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(formparams, "UTF-8");
					post.addHeader("Accept",
							"text/javascript, text/html, application/xml, text/xml");
					post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
					post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
					post.addHeader("Connection", "Keep-Alive");
					post.addHeader("Cache-Control", "no-cache");
					post.addHeader("Content-Type",
							"application/x-www-form-urlencoded");
					post.setEntity(entity);
					HttpResponse response = client.execute(post);
					HttpEntity e = response.getEntity();
					if (200 == response.getStatusLine().getStatusCode()) {
						client.getConnectionManager().shutdown();
//						Toast.makeText(context, EntityUtils.toString(e), Toast.LENGTH_SHORT).show();
					} else {
						client.getConnectionManager().shutdown();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				//上传音频文件
				inputStream = new FileInputStream(file);
				/*String flagLine = ContentFlag.RECORD_FLAG + file.getName();
				//写入标识行
				output.writeUTF(flagLine);
				//写入语音时间
				output.writeLong(recordTime);
				byte[] buffer = new byte[2048];
				int length = 0;
				//写入文件的大小
				output.writeInt((int) file.length());*/
				
				try {
					URL url = new URL(Urls.USER_CONVERSATION);
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setDoInput(true);
					conn.setRequestMethod("POST");
					conn.setReadTimeout(5000);
					conn.setRequestProperty("Content-Type",	"text/plain; charset=UTF-8");
					conn.setRequestProperty("Content-Length", String.valueOf(file.length()));
					DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
					byte[] buffer = new byte[2048];
					while ((length = inputStream.read(buffer)) != -1) {
						dos.write(buffer, 0, length);
					}
					dos.flush();
					dos.close();
					if (conn.getResponseCode() == 200) {
//						Toast.makeText(context, "语音发送成功", Toast.LENGTH_SHORT).show();
					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					conn.disconnect();
				}
				break;
			case 2://上传视频
				vedioFlag = "true";
				HttpClient client1 = new DefaultHttpClient();
				// 设置上传参数
				List<NameValuePair> formparams1 = new ArrayList<NameValuePair>();
				formparams1.add(new BasicNameValuePair("vedioflag", vedioFlag));
				formparams1.add(new BasicNameValuePair("recordtime", Long.toString(recordTime)));
				formparams1.add(new BasicNameValuePair("reply_person", reply_person));
				formparams1.add(new BasicNameValuePair("send_person", send_person));
				formparams1.add(new BasicNameValuePair("bitmap", bitmap));
				formparams1.add(new BasicNameValuePair("send_date", send_date));
				HttpPost post1 = new HttpPost(Urls.USER_CAPTUREVEDIO);	
				UrlEncodedFormEntity entity1;
				try {
					entity1 = new UrlEncodedFormEntity(formparams1, "UTF-8");
					post1.addHeader("Accept",
							"text/javascript, text/html, application/xml, text/xml");
					post1.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
					post1.addHeader("Accept-Encoding", "gzip,deflate,sdch");
					post1.addHeader("Connection", "Keep-Alive");
					post1.addHeader("Cache-Control", "no-cache");
					post1.addHeader("Content-Type",
							"application/x-www-form-urlencoded");
					post1.setEntity(entity1);
					HttpResponse response = client1.execute(post1);
					HttpEntity e = response.getEntity();
					if (200 == response.getStatusLine().getStatusCode()) {
						client1.getConnectionManager().shutdown();
//						Toast.makeText(context, EntityUtils.toString(e), Toast.LENGTH_SHORT).show();
					} else {
						client1.getConnectionManager().shutdown();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				//上传视频文件
				inputStream = new FileInputStream(file);
				/*String flagLine = ContentFlag.RECORD_FLAG + file.getName();
				//写入标识行
				output.writeUTF(flagLine);
				//写入语音时间
				output.writeLong(recordTime);
				byte[] buffer = new byte[2048];
				int length = 0;
				//写入文件的大小
				output.writeInt((int) file.length());*/
				
				try {
					URL url = new URL(Urls.USER_CAPTUREVEDIO);
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setDoInput(true);
					conn.setRequestMethod("POST");
					conn.setReadTimeout(5000);
					conn.setRequestProperty("Content-Type",	"text/plain; charset=UTF-8");
					conn.setRequestProperty("Content-Length", String.valueOf(file.length()));
					DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
					byte[] buffer = new byte[2048];
					while ((length = inputStream.read(buffer)) != -1) {
						dos.write(buffer, 0, length);
					}
					dos.flush();
					dos.close();
					if (conn.getResponseCode() == 200) {
						
					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					conn.disconnect();
				}
				break;
			}			
			
			
=======
			// 将语音流以字符串形式存储下来
			String audioFlag = "no";
			HttpClient client = new DefaultHttpClient();
			// 设置上传参数
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("audioflag", audioFlag));
			formparams.add(new BasicNameValuePair("recordtime", Long.toString(recordTime)));
			formparams.add(new BasicNameValuePair("reply_person", reply_person));
			formparams.add(new BasicNameValuePair("send_person", send_person));
			formparams.add(new BasicNameValuePair("bitmap", bitmap));
			formparams.add(new BasicNameValuePair("send_date", send_date));
			HttpPost post = new HttpPost(Urls.USER_CONVERSATION);	
			UrlEncodedFormEntity entity;
			try {
				entity = new UrlEncodedFormEntity(formparams, "UTF-8");
				post.addHeader("Accept",
						"text/javascript, text/html, application/xml, text/xml");
				post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
				post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
				post.addHeader("Connection", "Keep-Alive");
				post.addHeader("Cache-Control", "no-cache");
				post.addHeader("Content-Type",
						"application/x-www-form-urlencoded");
				post.setEntity(entity);
				HttpResponse response = client.execute(post);
				HttpEntity e = response.getEntity();
				if (200 == response.getStatusLine().getStatusCode()) {
					client.getConnectionManager().shutdown();
//					Toast.makeText(context, EntityUtils.toString(e), Toast.LENGTH_SHORT).show();
				} else {
					client.getConnectionManager().shutdown();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			//上传音频文件
			inputStream = new FileInputStream(file);
			/*String flagLine = ContentFlag.RECORD_FLAG + file.getName();
			//写入标识行
			output.writeUTF(flagLine);
			//写入语音时间
			output.writeLong(recordTime);
			byte[] buffer = new byte[2048];
			int length = 0;
			//写入文件的大小
			output.writeInt((int) file.length());*/
			
			try {
				URL url = new URL(Urls.USER_CONVERSATION);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				conn.setReadTimeout(5000);
				conn.setRequestProperty("Content-Type",	"text/plain; charset=UTF-8");
				conn.setRequestProperty("Content-Length", String.valueOf(file.length()));
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				byte[] buffer = new byte[2048];
				while ((length = inputStream.read(buffer)) != -1) {
					dos.write(buffer, 0, length);
				}
				dos.flush();
				dos.close();
				if (conn.getResponseCode() == 200) {
//					Toast.makeText(context, "语音发送成功", Toast.LENGTH_SHORT).show();
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
			
		} catch (Exception e) {
			throw new Exception();
		}finally{
			try {
//				if (file != null) file.delete();
				if (inputStream != null) inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
