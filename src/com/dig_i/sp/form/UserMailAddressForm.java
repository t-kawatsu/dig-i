package com.dig_i.sp.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class UserMailAddressForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mailAddress;
	@javax.annotation.Resource
	private UserDao userDao;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if (StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("userMailAddressForm.mailAddress",
					as.getText("invalidate.required"));
		} else if (!getMailAddress()
				.matches("[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+")) {
			as.addFieldError("userMailAddressForm.mailAddress",
					as.getText("invalidate.email"));
		} else if (100 < getMailAddress().length()) {
			as.addFieldError(
					"userMailAddressForm.mailAddress",
					as.getText("invalidate.maxLength", null,
							Arrays.asList("100")));
		} else {
			if (0 < userDao.countByMailAddressAndStatus(getMailAddress(),
					UserStatus.LIVE)) { // , UserAccountType.PROPER)) {
				as.addFieldError("userMailAddressForm.mailAddress",
						as.getText("invalidate.alreadyExists"));
			}
		}

		return !as.hasFieldErrors();
	}
}
