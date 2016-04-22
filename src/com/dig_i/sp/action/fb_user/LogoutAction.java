package com.dig_i.sp.action.fb_user;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserAccountType;
import com.dig_i.sp.action.AbstractAction;

public class LogoutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "fb-user/logout")
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return "sp-top";
		}
		User user = getCurrentUser();
		if (!user.hasAccountType(UserAccountType.FACEBOOK)) {
			return "sp-top";
		}

		// MyFacebookClient facebookClient = new MyDefaultFacebookClient();

		logout();
		return "sp-top";
	}

}
