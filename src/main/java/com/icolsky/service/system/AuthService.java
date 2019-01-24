package com.icolsky.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icolsky.config.shiro.token.TokenManager;
import com.icolsky.manager.UserManager;
import com.icolsky.model.dao.system.PermissionDAO;
import com.icolsky.model.dao.system.UserDAO;
import com.icolsky.model.entity.system.Permission;
import com.icolsky.model.entity.system.User;
import com.icolsky.service.common.BaseService;
import com.icolsky.util.common.SimpleMenuUtil;
import com.icolsky.vo.common.RespResult;
import com.icolsky.vo.common.SimpleMenu;
import com.icolsky.vo.user.ActiveUser;

import lombok.extern.java.Log;

/**
 * Created by lijj on 6/5/17.
 */
@Log
@Service
public class AuthService extends BaseService {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private PermissionDAO permissionDao;

	/**
	 * 授权用户信息
	 * 
	 * @return
	 */
	public RespResult getAuthInfo() {
		User session = TokenManager.getToken();
		List<Permission> menus = permissionDao.findMenuByUserName(session.getUsername());
		List<SimpleMenu> simpleMenus = SimpleMenuUtil.getMenuTree(menus);
		List<String> functons = permissionDao.findFunctionByUserName(session.getUsername());
		ActiveUser activer = new ActiveUser(session, simpleMenus, functons);
		return new RespResult(RESULT_SUCCESS, activer);
	}

	/**
	 * 修改个人信息
	 * 
	 * @param user
	 * @return
	 */
	public RespResult updatePersonal(User user) {
		User session = TokenManager.getToken();
		user.setId(session.getId());
		if (userDao.updateUser(user) > 0) {
			session.setTel(user.getTel());
			session.setName(user.getName());
			session.setEmail(user.getEmail());
			session.setDescription(user.getDescription());
			return new RespResult(RESULT_SUCCESS);
		}
		return new RespResult(RESULT_ERROR);
	}

	/**
	 * 用户重置密码
	 * 
	 * @param password
	 * @param newPassword
	 * @return
	 */
	public RespResult resetPassword(String password, String newPassword) {
		if (!TokenManager.getToken().getPassword().equalsIgnoreCase(UserManager.MD5Value(password))) {
			return new RespResult(RESULT_ERROR, "用户密码有误");
		}
		User user = new User();
		user.setPassword(newPassword);
		user.setId(TokenManager.getToken().getId());
		return userDao.updateUser(UserManager.MD5UserPassword(user)) > 0 ? new RespResult(RESULT_SUCCESS) : new RespResult(RESULT_ERROR);
	}

}
