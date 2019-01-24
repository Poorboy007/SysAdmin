package com.icolsky.manager;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.icolsky.model.entity.system.User;
/**
 * Created by FuChang Liu
 */
public class UserManager {
	
	/**
	 * 加工密码，和登录一致
	 * @param user
	 * @return
	 */
	public static User MD5UserPassword(User user){
		//密码为   Password + Salt(当前毫秒值MD5), 然后2次MD5
		user.setSalt(UserManager.MD5Value(System.currentTimeMillis()+""));
		user.setPassword(MD5Pswd(user.getPassword(), user.getSalt()));
		return user;
	}

	/**
	 * 字符串返回值
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String MD5Pswd(String password ,String salt){
		//Md5Hash hash = new Md5Hash(password, salt, 2);
		Md5Hash hash = new Md5Hash(password);
		return hash.toString();
	}

	/**
	 * 字符串MD5
	 * @param value
	 * @return
	 */
	public static String MD5Value(String value){
		Md5Hash hash = new Md5Hash(value);
		return hash.toString();
	}

}
