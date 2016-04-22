package com.dig_i.admin.action.language_mail;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.LanguageMailForm;
import com.dig_i.front.dao.LanguageMailDao;
import com.dig_i.front.entity.LanguageMail;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	@javax.annotation.Resource
	private LanguageMailForm languageMailForm;

	@Action(value = "language-mail/update", results = {
			@Result(name = "input", location = "language-mail/create.ftl"),
			@Result(name = "success", location = "language-mail/index", type = "redirect") })
	public String execute() throws Exception {
		LanguageMail languageMail = languageMailDao.findByCode(
				languageMailForm.getCode(), languageMailForm.getLanguageCode());
		if (languageMail == null) {
			throw new Exception();
		}

		if (!"POST".equals(this.request.getMethod())) {
			BeanUtils.copyProperties(languageMailForm, languageMail.getId());
			BeanUtils.copyProperties(languageMailForm, languageMail);
			return INPUT;
		}
		if (!languageMailForm.validate(this)) {
			return INPUT;
		}

		languageMailDao.update(languageMail, languageMailForm.getSubject(),
				languageMailForm.getBody());

		return SUCCESS;
	}

	public void setLanguageMailForm(LanguageMailForm languageMailForm) {
		this.languageMailForm = languageMailForm;
	}

	public LanguageMailForm getLanguageMailForm() {
		return languageMailForm;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
