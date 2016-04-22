package com.dig_i.sp.action.user;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.TmpUserDao;
import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.TmpUser;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserAccountType;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.service.MailService;
import com.dig_i.front.service.UserService;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.UserForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	@Resource
	private TmpUserDao tmpUserDao;
	@Resource
	private UserService userService;
	@Resource
	private MailService mailService;
	@Resource
	private UserForm userForm;

	private TmpUser tmpUser;
	private String token;

	@Action(value = "user/create", results = {
			@Result(name = "input", location = "user/create.ftl"),
			@Result(name = "success", location = "user/create-tmp-complete.ftl") })
	public String execute() throws Exception {
		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!userForm.validate(this)) {
			return INPUT;
		}

		tmpUser = tmpUserDao.create(userForm.getPassword(),
				userForm.getMailAddress());

		mailService.sendCreateTmpUserComplete(tmpUser.getMailAddress(),
				tmpUser.getToken());

		return SUCCESS;
	}

	@Action(value = "user/create-complete", results = {
			@Result(name = "input", location = "user/create-complete.ftl"),
			@Result(name = "expired", location = "user/create-expired.ftl"),
			@Result(name = "success", location = "user/create-complete.ftl") })
	public String completeAction() throws Exception {
		if (StringUtils.isEmpty(token)) {
			throw new Exception();
		}

		TmpUser tmpUser = tmpUserDao.findByToken(token);
		if (tmpUser == null) {
			return "expired";
		}

		User user = userDao.findByMailAddressAndStatus(
				tmpUser.getMailAddress(), UserStatus.LIVE);
		if (user != null) {
			return "success";
		}

		userService.create(tmpUser, LanguageCode.JA,
				request.getHeader("User-Agent"), UserAccountType.PROPER);
		tmpUserDao.remove(tmpUser);

		setCurrentUser(user);

		return SUCCESS;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public UserForm getUserForm() {
		return userForm;
	}

	public TmpUser getTmpUser() {
		return tmpUser;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
