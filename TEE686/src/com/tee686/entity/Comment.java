package com.tee686.entity;


public class Comment {
	
	private String headimage;
	private String imagefile;
	private String comContent;
	private String pubContent;
	private String username;
	private String author;
	private String reply;
	private String pubtime;
	private String comtime;
	private Integer comid;
	
	public Comment(String headimage, String comContent, String username,String pubtime,
			String comtime, String reply, String imagefile, String pubContent, String author) {
		this.headimage = headimage;
		this.comContent = comContent;
		this.username = username;
		this.pubtime = pubtime;
		this.comtime = comtime;
		this.reply = reply;
		this.imagefile = imagefile;
		this.pubContent = pubContent;
		this.author = author;
	}
	
	public Comment() {}
	
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
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

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getImagefile() {
		return imagefile;
	}

	public void setImagefile(String imagefile) {
		this.imagefile = imagefile;
	}

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comContent) {
		this.comContent = comContent;
	}

	public String getPubContent() {
		return pubContent;
	}

	public void setPubContent(String pubContent) {
		this.pubContent = pubContent;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
