package com.dig_i.admin.action.language_help;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.commons.beanutils.BeanUtils;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.LanguageHelpForm;
import com.dig_i.front.dao.LanguageHelpDao;
import com.dig_i.front.entity.LanguageHelp;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageHelpDao languageHelpDao;
	@javax.annotation.Resource
	private LanguageHelpForm languageHelpForm;

	private Integer id;

	@Action(value = "language-help/update/{id}", results = {
			@Result(name = "input", location = "language-help/create.ftl"),
			@Result(name = "success", location = "language-help/index") })
	public String execute() throws Exception {
		LanguageHelp languageHelp = languageHelpDao.findById(id);
		if (languageHelp == null) {
			throw new Exception();
		}

		if (!"POST".equals(this.request.getMethod())) {
			BeanUtils.copyProperties(languageHelpForm, languageHelp);
			return INPUT;
		}
		if (!languageHelpForm.validate(this)) {
			return INPUT;
		}

		languageHelpDao.update(languageHelp, languageHelpForm.getTitle(),
				languageHelpForm.getDescription());

		return SUCCESS;
	}

	public void setLanguageHelpForm(LanguageHelpForm languageHelpForm) {
		this.languageHelpForm = languageHelpForm;
	}

	public LanguageHelpForm getLanguageHelpForm() {
		return languageHelpForm;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
