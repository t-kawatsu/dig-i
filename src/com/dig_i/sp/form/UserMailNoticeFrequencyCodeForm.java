package com.dig_i.sp.form;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class UserMailNoticeFrequencyCodeForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MAIL_NOTICE_FREQUENCY_SETTING_CODE = "C001";

	private Integer mailNoticeFrequencyCode;
	private Map<String, String> codes;

	public void setMailNoticeFrequencyCode(Integer mailNoticeFrequencyCode) {
		this.mailNoticeFrequencyCode = mailNoticeFrequencyCode;
	}

	public Integer getMailNoticeFrequencyCode() {
		return mailNoticeFrequencyCode;
	}

	public Map<String, String> getMailNoticeFrequencyCodes() {
		if (codes == null) {
			codes = getSettingSelectList(MAIL_NOTICE_FREQUENCY_SETTING_CODE);
		}
		return codes;
	}

	public boolean validate(ActionSupport as) {
		String code = String.valueOf(getMailNoticeFrequencyCode());
		if (!getMailNoticeFrequencyCodes().containsKey(code)) {
			as.addFieldError(
					"userMailNoticeFrequencyCodeForm.mailNoticeFrequencyCode",
					as.getText("invalidate.unKnownValue"));
		}
		return !as.hasFieldErrors();
	}
}
