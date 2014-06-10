package com.tee686.entity;

/**
 * 消息pojo
 * 
 * @author Administrator
 * 
 */
public class Message {
	private int id;
	private String send_ctn;
	private String send_person;//发送消息的人
	private String reply_person;//接收消息的人
	private String send_date;
	private String record_path;
	private boolean ifyuyin = false; // 是否是语音消息
	private boolean ifvedio = false; // 是否是视频消息
	private long recordTime; // 语音消息持续的时间
	private String bitmap; //用户头像
	private String msgId;
	public String getSend_ctn() {
		return send_ctn;
	}

	public void setSend_ctn(String send_ctn) {
		this.send_ctn = send_ctn;
	}

	public String getSend_person() {
		return send_person;
	}

	public void setSend_person(String send_person) {
		this.send_person = send_person;
	}

	public String getSend_date() {
		return send_date;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getBitmap() {
		return bitmap;
	}

	public void setBitmap(String bitmap) {
		this.bitmap = bitmap;
	}

	public Message(String send_ctn, String send_person, String send_date,
			String bitmap, String reply_person) {
		super();
		this.send_ctn = send_ctn;
		this.send_person = send_person;
		this.send_date = send_date;
		this.bitmap = bitmap;
		this.reply_person = reply_person;
	}

	public Message() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecord_path() {
		return record_path;
	}

	public void setRecord_path(String record_path) {
		this.record_path = record_path;
	}

	public boolean isIfyuyin() {
		return ifyuyin;
	}

	public void setIfyuyin(boolean ifyuyin) {
		this.ifyuyin = ifyuyin;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getReply_person() {
		return reply_person;
	}

	public void setReply_person(String reply_person) {
		this.reply_person = reply_person;
	}

	public boolean isIfvedio() {
		return ifvedio;
	}

	public void setIfVedio(boolean ifvedio) {
		this.ifvedio = ifvedio;
	}
	
}
