package com.tee686.entity;


public class Comment {
	
	private String headimage;
	private String content;
	private String username;
	private String pubtime;
	private String comtime;
	private Integer comid;
	
	public Comment(String headimage, String content, String username,String pubtime,
			String comtime) {
		this.headimage = headimage;
		this.content = content;
		this.username = username;
		this.pubtime = pubtime;
		this.comtime = comtime;
		
	}
	
	public Comment() {}
	
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public String getComtime() {
		return comtime;
	}
	public void setComtime(String comtime) {
		this.comtime = comtime;
	}
	public Integer getComid() {
		return comid;
	}
	public void setComid(Integer comid) {
		this.comid = comid;
	}
	
	
}
