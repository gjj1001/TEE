package com.tee686.entity;

public class Observer {

	private String uname;//关注者(粉丝)
	private String username;//被关注者
	private String headimg;//关注者头像
	private String headimage;//被关注者头像
	private Integer uid;
<<<<<<< HEAD
	private long msgtime;//最近会话时间
	private boolean ifmsg = false;//是否有会话
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}	
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
<<<<<<< HEAD
	public long getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(long msgtime) {
		this.msgtime = msgtime;
	}
	public boolean isIfmsg() {
		return ifmsg;
	}
	public void setIfmsg(boolean ifmsg) {
		this.ifmsg = ifmsg;
	}
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
	
	
	
}
