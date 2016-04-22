package com.dig_i.sp.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.service.UserService;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.UserSecessionForm;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;
	@Resource
	private UserSecessionForm userSecessionForm;

	@Action(value = "user/delete", results = {
			@Result(name = "input", location = "user/delete.ftl"),
			@Result(name = "success", location = "user/delete-complete.ftl") })
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!userSecessionForm.validate(this)) {
			return INPUT;
		}

		User user = getCurrentUser();
		userService.secession(user, userSecessionForm.getReasonCode(),
				userSecessionForm.getDescription());

		logout();
		return SUCCESS;
	}

	public void setUserSecessionForm(UserSecessionForm userSecessionForm) {
		this.userSecessionForm = userSecessionForm;
	}

	public UserSecessionForm getUserSecessionForm() {
		return userSecessionForm;
	}

}
