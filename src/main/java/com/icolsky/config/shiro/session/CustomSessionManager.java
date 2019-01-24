package com.icolsky.config.shiro.session;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.icolsky.model.entity.system.User;
import com.icolsky.vo.user.UserOnline;

/**
 * Created by FuChang Liu
 */
public class CustomSessionManager {

	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * 获取所有Seesion
	 * @return
	 */
	public Collection<Session> getSessions(){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		return sessions;
	}

	/**
	 * 获取所有的有效Session用户
	 * @return
	 */
	public List<UserOnline> getUserOnlines() {
		Collection<Session> sessions = getSessions();
		List<UserOnline> list = new ArrayList<UserOnline>();
		for (Session session : sessions) {
			UserOnline userOnline = getSessionBo(session);
			if(null != userOnline){
				list.add(userOnline);
			}
		}
		return list;
	}

	private UserOnline getSessionBo(Session session){
		//获取session登录信息。
		Object spcObj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if(null == spcObj) return null;

		//确保是 SimplePrincipalCollection对象。
		if(spcObj instanceof SimplePrincipalCollection){
			SimplePrincipalCollection spc = (SimplePrincipalCollection) spcObj;
			Object userObj = spc.getPrimaryPrincipal();  //获取用户登录, SampleRealm.doGetAuthenticationInfo()方法中
			if(userObj != null && userObj instanceof User){
				UserOnline userOnline = new UserOnline((User) userObj);
				userOnline.setLastAccess(session.getLastAccessTime());		//最后一次和系统交互的时间
				userOnline.setHost(session.getHost());     					//主机的ip地址
				userOnline.setSessionId(session.getId().toString());      	//session ID
				//userOnline.setLastTime(session.getLastAccessTime());  		//session最后一次与系统交互的时间
				userOnline.setStartTime(session.getStartTimestamp());     	//session创建时间
				return userOnline;
			}
		}
		return null;
	}

}
