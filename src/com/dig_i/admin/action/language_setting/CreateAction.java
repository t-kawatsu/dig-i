package com.dig_i.admin.action.language_setting;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.LanguageSettingForm;
import com.dig_i.front.dao.LanguageSettingDao;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSettingDao languageSettingDao;
	@Resource
	private LanguageSettingForm languageSettingForm;

	@Action(value = "language-setting/create", results = {
			@Result(name = "input", location = "language-setting/create.ftl"),
			@Result(name = "success", location = "language-setting/index", type = "redirect") })
	public String execute() throws Exception {
		if (!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if (!languageSettingForm.validate(this)) {
			return INPUT;
		}

		languageSettingDao.persist(languageSettingForm.getCode(),
				languageSettingForm.getLanguageCode(),
				languageSettingForm.getContents());

		return SUCCESS;
	}

	public void setLanguageSettingForm(LanguageSettingForm languageSettingForm) {
		this.languageSettingForm = languageSettingForm;
	}

	public LanguageSettingForm getLanguageSettingForm() {
		return languageSettingForm;
	}
}
