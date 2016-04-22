package com.dig_i.front.form;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.entity.MobileMailDomain;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class MailForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String domain;
	private Map<String, String> domains;

	//@RequiredStringValidator(message = "", key = "invalidate.required", shortCircuit = true)
	//@FieldExpressionValidator(message = "", key = "invalidate.email", shortCircuit = true, expression = "isMailAddress(getMailAddress())")
	public void setAccount(String account) {
		this.account = StringUtils.trim(account);
	}

	public String getAccount() {
		return account;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	public Map<String, String> getDomains() {
		if (domains == null) {
			domains = getEnumSelectList(MobileMailDomain.class);
		}
		return domains;
	}

	// @EmailValidator(message = "", key = "invalidate.email", shortCircuit =
	// true)
	public String getMailAddress() {
		return getAccount() + "@" + MobileMailDomain.valueOf(getDomain());
	}

	public boolean isMailAccount(String value) {
		String MATCH_MAIL = "[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+";
		return value.matches(MATCH_MAIL);
	}
	
	public boolean validate(ActionSupport as) {
		// mail address
		if (StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("mailForm.mailAddress",
					as.getText("invalidate.required"));
		} else if (!isMailAccount(getMailAddress())) {
			as.addFieldError("mailForm.mailAddress",
					as.getText("invalidate.email"));
		}
		if(getDomain() == null) {
			as.addFieldError("mailForm.mailAddress",
					as.getText("invalidate.email"));
		}
		return !as.hasFieldErrors();
	}
}
