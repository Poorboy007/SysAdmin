package com.icolsky.common.constants;

/**
 * Created by FuChang Liu
 * 常量
 */
public class BaseConstant {

	/**
	 * 时间格式常量
	 */
	public static class Date{
		public static final String STD_DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
		public static final String STD_DATE_FORMAT_2 = "yyyy-MM-dd HH:mm";
		public static final String STD_DATE_FORMAT_3 = "yyyy-MM-dd";
	}

	/**
	 * 系统常量
	 */
	public static class System{
		public static final String SID_KEY = "true";
		public static final String CONTENT_TYPE = "application/json";
	}

	/**
	 * 响应代码
	 */
	public static class ResultCode {
		public static final Integer SUCCESS = 200;    		// 请求成功
		public static final Integer ERROR = 400;        	// 请求失败
		public static final Integer NOT_LOGIN = 401;      	// 用户未登陆

		public static final Integer IMPORT_ERROR = 420;     // 导入数据异常
		public static final Integer FREQUENT_OPERATION=425; // 用户频繁操作
	}


	/**
	 * 正则表达式
	 */
	public static class Regular {
		public static final String STANDARD_MAC = "([A-Fa-f0-9]{2}[:-]){5}[A-Fa-f0-9]{2}";
		public static final String SIMPLE_MAC = "([A-Fa-f0-9]{2}){6}";
	}


	/**
	 * 分页常量
	 */
	public static class Page{

		public static final Integer PAGE_NUM = 1;
		public static final Integer PAGE_SIZE = 20;

	}


	/**
	 * 响应信息
	 */
	public static class ResultMsg {
		public static final String INVALID_LOGIN_MSG		= "用户名或密码错误.";
		public static final String INVALID_VALIDATE_CODE	= "验证码错误或过期.";
		public static final String RESULT_ERROR_MSG 		= "系统异常...";
		public static final String RESULT_NOT_LOGIN_MSG 	= "用户未登陆或失效，请重新登陆.";
	}
}
