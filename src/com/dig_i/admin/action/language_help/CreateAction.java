package com.dig_i.admin.action.language_help;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.LanguageHelpForm;
import com.dig_i.front.dao.LanguageHelpDao;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageHelpDao languageHelpDao;
	@javax.annotation.Resource
	private LanguageHelpForm languageHelpForm;

	@Action(value = "language-help/create", results = {
			@Result(name = "input", location = "language-help/create.ftl"),
			@Result(name = "success", location = "language-help/index", type = "redirect") })
	public String execute() throws Exception {
		if (!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if (!languageHelpForm.validate(this)) {
			return INPUT;
		}

		languageHelpDao.persist(languageHelpForm.getTitle(),
				languageHelpForm.getDescription(),
				languageHelpForm.getLanguageCode());

		return SUCCESS;
	}

	public void setLanguageHelpForm(LanguageHelpForm languageHelpForm) {
		this.languageHelpForm = languageHelpForm;
	}

	public LanguageHelpForm getLanguageHelpForm() {
		return languageHelpForm;
	}
}
