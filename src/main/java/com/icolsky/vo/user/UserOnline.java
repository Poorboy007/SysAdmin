package com.icolsky.vo.user;

import java.io.Serializable;
import java.util.Date;

import com.icolsky.model.entity.system.User;

/**
 * Created by lijj on 6/5/17.
 */
public class UserOnline extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	private String host;
	private Date startTime;		//Session创建时间
	private Date lastAccess;	//Session最后交互时间

	public UserOnline() {
	}
	
	public UserOnline(User user) {

	}
	
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

}
