package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/auth/login时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipLogin {

	/**
	 * 用户在登录界面输入的用户名
	 */
	String username;

	/**
	 * 用户在登录界面输入的密码经过MD5处理后的字符串
	 */
	String password;

	/**
	 * 启动百度云推送服务后，百度返回的user_id（用于推送消息）
	 */
	String baidu_user_id;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            经MD5处理后的密码
	 * @param baidu_user_id
	 *            百度云推送user_id
	 */
	public AipLogin(String username, String password, String baidu_user_id) {
		this.username = username;
		this.password = password;
		this.baidu_user_id = baidu_user_id;
	}

}
