package com.dig_i.sp.action.user;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.sp.action.AbstractAction;

public class LogoutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "user/logout")
	public String execute() throws Exception {
		logout();
		return TOP;
	}

}
