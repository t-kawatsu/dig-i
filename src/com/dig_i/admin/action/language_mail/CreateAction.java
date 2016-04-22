package com.dig_i.admin.action.language_mail;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.LanguageMailForm;
import com.dig_i.front.dao.LanguageMailDao;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	@javax.annotation.Resource
	private LanguageMailForm languageMailForm;

	@Action(value = "language-mail/create", results = {
			@Result(name = "input", location = "language-mail/create.ftl"),
			@Result(name = "success", location = "language-mail/index", type = "redirect") })
	public String execute() throws Exception {
		if (!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if (!languageMailForm.validate(this)) {
			return INPUT;
		}

		languageMailDao.persist(languageMailForm.getCode(),
				languageMailForm.getSubject(), languageMailForm.getBody(),
				languageMailForm.getLanguageCode());

		return SUCCESS;
	}

	public void setLanguageMailForm(LanguageMailForm languageMailForm) {
		this.languageMailForm = languageMailForm;
	}

	public LanguageMailForm getLanguageMailForm() {
		return languageMailForm;
	}
}
