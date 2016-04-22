package com.dig_i.sp.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dig_i.front.entity.User;
import com.dig_i.front.util.SessionManager;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("base")
@Namespace("/sp")
@InterceptorRefs({ @InterceptorRef("myStack"), })
abstract public class AbstractAction extends ActionSupport implements
		ServletRequestAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static String LOGIN = "sp-login";
	protected static String TOP = "sp-top";

	protected HttpServletRequest request;
	protected SessionManager sessionManager;

	public boolean getIsLogined() {
		return sessionManager.getCurrentUser() != null;
	}

	public User getCurrentUser() {
		return (User) sessionManager.getCurrentUser();
	}

	protected void logout() {
		sessionManager.removeCurrentUser();
	}

	protected void setCurrentUser(User user) {
		sessionManager.putCurrentUser(user);
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionManager = new SessionManager(session, ServletActionContext
				.getRequest().getContextPath(), ServletActionContext
				.getActionMapping().getNamespace(), ServletActionContext
				.getActionMapping().getName());
	}

}
