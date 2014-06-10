package com.tee686.entity;

import java.io.Serializable;

public class PushMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7083605841572822000L;
	private String content;
	private String time;
	private Integer pmid;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getPmid() {
		return pmid;
	}
	public void setPmid(Integer pmid) {
		this.pmid = pmid;
	}
	
	
}
