package com.dig_i.sp.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class RequestForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean validate(ActionSupport as) {
		if (StringUtils.isEmpty(getDescription())) {
			as.addFieldError("requestForm.description",
					as.getText("invalidate.required"));
		} else if (1500 < getDescription().length()) {
			as.addFieldError(
					"requestForm.description",
					as.getText("invalidate.maxLength", null,
							Arrays.asList("1500")));
		}
		return !as.hasFieldErrors();
	}
}
