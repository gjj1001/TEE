package com.tee686.config;

public class Constants {

	public static final class TAGS {
		public static final String NEWS_TAG = "news";
		public static final String BLOG_TAG = "blog";
		public static final String WIKI_TAG = "wiki";
	}

	public static final class DBContentType {
		public static final String Content_list = "list";
		public static final String Content_content = "content";
		public static final String Discuss="discuss";
	}

	public static final class WebSourceType{
		public static final String Json="json";
		public static final String Xml="xml";
	}
	
	public static final class ReceiverAction {
		public static final String CHECK_NEW_PUB = "com.tee686.checkPub";
		public static final String CHECK_NEW_FAN = "com.tee686.checkfan";
		public static final String CHECK_NEW_MSG = "com.tee686.checkMsg";
	}
}
