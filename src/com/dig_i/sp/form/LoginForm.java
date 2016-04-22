package com.dig_i.sp.form;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class LoginForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mailAddress;
	private String password;
	@javax.annotation.Resource
	private UserDao userDao;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if (StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("loginForm.mailAddress",
					as.getText("invalidate.required"));
		}

		// password
		if (StringUtils.isEmpty(getPassword())) {
			as.addFieldError("loginForm.password",
					as.getText("invalidate.required"));
		}

		try {
			int cnt = userDao.countByMailAddressAndPasswordAndStatus(
					getMailAddress(), getPassword(), UserStatus.LIVE);
			if (cnt <= 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			as.addFieldError("loginForm.mailAddress",
					as.getText("invalidate.login"));
		}

		return !as.hasFieldErrors();
	}
}
