package com.dig_i.sp.action.user;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.service.MailService;
import com.dig_i.front.util.crypt.BlowfishCrypt;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.UserMailAddressForm;
import com.dig_i.sp.form.UserMailNoticeFrequencyCodeForm;
import com.dig_i.sp.form.UserPasswordForm;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	@Resource
	private MailService mailService;
	@Resource
	private UserMailAddressForm userMailAddressForm;
	@Resource
	private UserPasswordForm userPasswordForm;
	@Resource
	private UserMailNoticeFrequencyCodeForm userMailNoticeFrequencyCodeForm;

	@Action(value = "user/update")
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		return SUCCESS;
	}

	@Action(value = "user/update-mail-address", results = {
			@Result(name = "input", location = "user/update-mail-address.ftl"),
			@Result(name = "success", location = "user/update-mail-address-confirm.ftl") })
	public String mailAddressAction() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}

		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!userMailAddressForm.validate(this)) {
			return INPUT;
		}

		String mailAddress = userMailAddressForm.getMailAddress();
		sessionManager.putNamespace("updateMailAddress", mailAddress);

		mailService.sendUpdateMailAddress(mailAddress);

		return SUCCESS;
	}

	@Action(value = "user/update-mail-address-complete", results = {
			@Result(name = "expired", location = "user/update-mail-address-expired.ftl"),
			@Result(name = "success", location = "user/update-complete.ftl") })
	public String mailAddressCompleteAction() throws Exception {
		String mailAddress = (String) sessionManager
				.getNamespace("updateMailAddress");

		if (StringUtils.isEmpty(mailAddress)) {
			return "expired";
		}

		sessionManager.removeNamespace("updateMailAddress");

		User user = getCurrentUser();
		user.setMailAddress(mailAddress);
		userDao.update(user);

		setCurrentUser(user);

		return SUCCESS;
	}

	@Action(value = "user/update-password", results = {
			@Result(name = "input", location = "user/update-password.ftl"),
			@Result(name = "success", location = "user/update-complete.ftl") })
	public String passwordAction() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!userPasswordForm.validate(this)) {
			return INPUT;
		}

		User user = getCurrentUser();
		userDao.updatePassword(user, userPasswordForm.getPassword());

		setCurrentUser(user);

		return SUCCESS;
	}

	@Action(value = "user/update-mail-notice-frequency-code", results = {
			@Result(name = "input", location = "user/update-mail-notice-frequency-code.ftl"),
			@Result(name = "success", location = "user/update-complete.ftl") })
	public String mailNoticeFrequencyCodeAction() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		User user = getCurrentUser();
		if (!"POST".equals(request.getMethod())) {
			userMailNoticeFrequencyCodeForm.setMailNoticeFrequencyCode(user
					.getMailNoticeFrequencyCode());
			return INPUT;
		}
		if (!userMailNoticeFrequencyCodeForm.validate(this)) {
			return INPUT;
		}

		user.setMailNoticeFrequencyCode(userMailNoticeFrequencyCodeForm
				.getMailNoticeFrequencyCode());
		userDao.update(user);

		setCurrentUser(user);

		return SUCCESS;
	}

	public String getDecryptedPassword() {
		return BlowfishCrypt.decrypt(getCurrentUser().getPassword());
	}

	public void setUserPasswordForm(UserPasswordForm userPasswordForm) {
		this.userPasswordForm = userPasswordForm;
	}

	public UserPasswordForm getUserPasswordForm() {
		return userPasswordForm;
	}

	public void setUserMailAddressForm(UserMailAddressForm userMailAddressForm) {
		this.userMailAddressForm = userMailAddressForm;
	}

	public UserMailAddressForm getUserMailAddressForm() {
		return userMailAddressForm;
	}

	public void setUserMailNoticeFrequencyCodeForm(
			UserMailNoticeFrequencyCodeForm userMailNoticeFrequencyCodeForm) {
		this.userMailNoticeFrequencyCodeForm = userMailNoticeFrequencyCodeForm;
	}

	public UserMailNoticeFrequencyCodeForm getUserMailNoticeFrequencyCodeForm() {
		return userMailNoticeFrequencyCodeForm;
	}
}
