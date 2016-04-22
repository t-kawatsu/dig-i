package com.dig_i.sp.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.service.UserService;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.LoginForm;

public class LoginAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;
	@Resource
	private LoginForm loginForm;

	@Action(value = "user/login", results = { @Result(name = "input", location = "user/login.ftl") })
	public String execute() throws Exception {
		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!loginForm.validate(this)) {
			return INPUT;
		}

		User user = userDao.findByMailAddressAndPasswordAndStatus(
				loginForm.getMailAddress(), loginForm.getPassword(),
				UserStatus.LIVE);
		user = userService.updateLoginState(user,
				request.getHeader("User-Agent"));

		setCurrentUser(user);
		return TOP;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

}
