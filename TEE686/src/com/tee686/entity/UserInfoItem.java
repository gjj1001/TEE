package com.tee686.entity;

public class UserInfoItem {
	private String mobile;
	private String username;
	private String pwd;
	private String sex;
	private String birthday;
	private String province;
	private String city;
	private Integer tm = 0; //用户积分
	private Integer tp = 0; //用户威望
	private Integer userid;
	private String regtime;
//	private String head_image_url;
	private String platform;//第三方登陆平台
	
	public UserInfoItem(String mobile, String username, String pwd, String sex,
			String birthday, String province, String city, String regtime,
			String platform) {
		super();
		this.mobile = mobile;
		this.username = username;
		this.pwd = pwd;
		this.sex = sex;
		this.birthday = birthday;
		this.province = province;
		this.city = city;
		this.regtime = regtime;		
		this.platform = platform;
	}
	
	public UserInfoItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getTm() {
		return tm;
	}
	public void setTm(Integer tm) {
		this.tm = tm;
	}
	public Integer getTp() {
		return tp;
	}
	public void setTp(Integer tp) {
		this.tp = tp;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	

	
}


