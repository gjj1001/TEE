package com.tee686.config;

public class Urls {

	public static final String BASIC_URL = "http://210.75.239.227/payment";
//	public static final String BASIC_URL1 = "http://api.eoe.name/client";

	public static final String TOP_NEWS_URL = BASIC_URL
			+ "/news?k=lists&t=top";
	public static final String TOP_BLOG_URL = BASIC_URL
			+ "/blog?k=lists&t=top";
	public static final String TOP_WIKI_URL = BASIC_URL
			+ "/wiki?k=lists&t=top";
	/**
	 * 博客请求地址
	 */
	public static final String TOP_LIST = BASIC_URL + "/top";
	public static final String BLOGS_LIST = BASIC_URL + "/blog?k=lists";
	public static final String NEWS_LIST = BASIC_URL + "/news?k=lists";
	public static final String WIKI_LIST = BASIC_URL + "/wiki?k=lists";
	
	public static final String CHECK_NEW_PUBCONTENT = BASIC_URL + "/CheckNewPubContentServlet";

	/**
	 * searchURL
	 */
	public static final String BASE_SEARCH_URL = BASIC_URL + "/search?";

	public static final String USER_LOGIN = BASIC_URL + "/UserloginServlet?uname=%s&pwd=%s";
	public static final String USER_REGISTER = BASIC_URL + "/UserRegisterServlet";
	public static final String USER_INFO = BASIC_URL + "/UserInfoServlet?uname=%s";
	public static final String USER_LEVEL = BASIC_URL + "/UserLevelServlet?uname=%s&num=%s";
	public static final String USER_PUBLISH = BASIC_URL + "/UserPublishServlet";
	public static final String USER_DELETE_PUBLISH = BASIC_URL + "/UserDeletePubServlet?sendtime=%s";
	public static final String USER_DOAWLOAD_IMAGE = BASIC_URL + "/UserDownloadImageServlet";
	public static final String USER_COMMENT = BASIC_URL + "/UserCommentServlet";
	public static final String USER_COMMENT_DATA = BASIC_URL + "/UserCommentServlet?pubtime=%s";
	public static final String USER_RECENTLY_PUBLISH = BASIC_URL + "/UserRecentlyPubServlet?uname=%s";
	public static final String USER_COLLECTION = BASIC_URL + "/UserCollectionServlet";
	public static final String USER_REPLY = BASIC_URL + "/UserReplyServlet";
	public static final String USER_OBSERVER = BASIC_URL + "/UserObserverServlet";
	public static final String USER_FAN = BASIC_URL + "/UserFanServlet";

	/**
	 * 1 k 2 act 3 model 4 itemid
	 */
	public static final String DETAILS_ActionBar = BASIC_URL
			+ "/bar?k=%s&act=%s&model=%s&itemid=%s";

	public static final String userlike = "userlike";
	public static final String favorite = "favorite";
	public static final String add = "add";
	public static final String del = "del";
	public static final String like = "like";
	public static final String useless = "useless";
	public static final String news = "news";
	public static final String wiki = "wiki";
	public static final String blog = "blog";

	// 获取用户信息接口
	public static final String KEYBindURL = BASIC_URL + "/userinfo?key=%s";
}
