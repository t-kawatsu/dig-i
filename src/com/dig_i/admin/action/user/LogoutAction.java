package com.dig_i.admin.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.admin.action.AbstractAction;

public class LogoutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "user/logout")
	@Override
	public String execute() throws Exception {
		this.removeCurrentUser();
		return getLoginActionName();
	}

	public boolean isSecured() {
		return true;
	}
}
