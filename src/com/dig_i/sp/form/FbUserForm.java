package com.dig_i.sp.form;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class FbUserForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean agree;

	public Boolean getAgree() {
		return agree;
	}

	public void setAgree(Boolean agree) {
		this.agree = agree;
	}

	public boolean validate(ActionSupport as) {
		// agree
		if (BooleanUtils.isFalse(getAgree())) {
			as.addFieldError("fbUserForm.agree", as.getText("invalidate.agree"));
		}

		return !as.hasFieldErrors();
	}
}
