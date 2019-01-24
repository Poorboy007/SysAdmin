package com.icolsky.config.shiro.token;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.icolsky.config.shiro.realm.SampleRealm;
import com.icolsky.model.entity.system.User;

/**
 * Created by FuChang Liu
 */
public class TokenManager {

	@Autowired
	private static SampleRealm realm;

	/**
	 * 获取当前登录的用户User对象
	 * @return
	 */
	public static User getToken(){
		User token = (User) SecurityUtils.getSubject().getPrincipal();
		return token ;
	}

	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public static User login(User user){
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		SecurityUtils.getSubject().login(token);
		return getToken();
	}

	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 退出登录
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 清空当前用户权限信息
	 */
	public static void clearUserAuth(){
		realm.clearCachedAuthorizationInfo();
	}

}
