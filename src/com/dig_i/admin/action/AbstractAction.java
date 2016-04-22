package com.dig_i.admin.action;

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

import com.dig_i.front.entity.AdminUser;
import com.dig_i.front.interceptor.LoginAuthenticationAware;
import com.dig_i.front.interceptor.LoginAuthorizationAware;
import com.dig_i.front.util.SessionManager;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("base")
@Namespace("/admin")
@InterceptorRefs({ @InterceptorRef("myAdminStack"), })
abstract public class AbstractAction extends ActionSupport implements
		SessionAware, ServletRequestAware, LoginAuthenticationAware,
		LoginAuthorizationAware<AdminUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final int DISP_ITEMS_NUM_PAR_PAGE = 10;
	protected static final String TOP = "admin-top";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected HttpServletRequest request;
	protected SessionManager sessionManager;
	protected AdminUser user;

	public boolean getIsLogined() {
		return sessionManager.getCurrentUser() != null;
	}

	public void setCurrentUser(AdminUser user) {
		sessionManager.putCurrentUser(user);
	}

	public void removeCurrentUser() {
		sessionManager.removeCurrentUser();
	}

	public AdminUser getCurrentUser() {
		return (AdminUser) sessionManager.getCurrentUser();
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.sessionManager = new SessionManager(session, ServletActionContext
				.getRequest().getContextPath(), ServletActionContext
				.getActionMapping().getNamespace(), ServletActionContext
				.getActionMapping().getName());
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public boolean isSecured() {
		return false;
	}

	public String getLoginActionName() {
		return "admin-login";
	}
}
