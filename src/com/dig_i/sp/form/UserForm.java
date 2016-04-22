package com.dig_i.sp.form;

import java.util.Arrays;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.entity.UserAccountType;
import com.dig_i.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class UserForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mailAddress;
	private String password;
	private String passwordCon;
	private Boolean agree;
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

	public String getPasswordCon() {
		return passwordCon;
	}

	public void setPasswordCon(String passwordCon) {
		this.passwordCon = passwordCon;
	}

	public Boolean getAgree() {
		return agree;
	}

	public void setAgree(Boolean agree) {
		this.agree = agree;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if (StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("userForm.mailAddress",
					as.getText("invalidate.required"));
		} else if (!getMailAddress()
				.matches("[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+")) {
			as.addFieldError("userForm.mailAddress",
					as.getText("invalidate.email"));
		} else if (100 < getMailAddress().length()) {
			as.addFieldError(
					"userForm.mailAddress",
					as.getText("invalidate.maxLength", null,
							Arrays.asList("100")));
		} else {
			if (0 < userDao.countByMailAddressAndStatusAndAccountType(
					getMailAddress(), UserStatus.LIVE, UserAccountType.PROPER)) {
				as.addFieldError("userForm.mailAddress",
						as.getText("invalidate.alreadyExists"));
			}
		}

		// password
		if (StringUtils.isEmpty(getPassword())) {
			as.addFieldError("userForm.password",
					as.getText("invalidate.required"));
		} else if (StringUtils.isEmpty(getPasswordCon())) {
			as.addFieldError("userForm.passwordCon",
					as.getText("invalidate.required"));
		} else if (!getPassword().equals(getPasswordCon())) {
			as.addFieldError("userForm.password",
					as.getText("invalidate.notSamePassword"));
		}

		// agree
		if (BooleanUtils.isFalse(getAgree())) {
			as.addFieldError("userForm.agree", as.getText("invalidate.agree"));
		}

		return !as.hasFieldErrors();
	}
}
