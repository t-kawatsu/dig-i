package com.dig_i.sp.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class UserPasswordForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	private String passwordCon;

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

	public boolean validate(ActionSupport as) {
		// password
		if (StringUtils.isEmpty(getPassword())) {
			as.addFieldError("userPasswordForm.password",
					as.getText("invalidate.required"));
		} else if (!(6 <= getPassword().length() && getPassword().length() <= 12)) {
			as.addFieldError(
					"userPasswordForm.password",
					as.getText("invalidate.between", null,
							Arrays.asList("6", "12")));
		} else if (StringUtils.isEmpty(getPasswordCon())) {
			as.addFieldError("userPasswordForm.passwordCon",
					as.getText("invalidate.required"));
		} else if (!getPassword().equals(getPasswordCon())) {
			as.addFieldError("userPasswordForm.password",
					as.getText("invalidate.notSamePassword"));
		}

		return !as.hasFieldErrors();
	}
}
