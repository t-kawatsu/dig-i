package com.dig_i.admin.action.language_mail;

import java.util.Locale;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.entity.*;
import com.dig_i.front.dao.LanguageMailDao;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	private LanguageMail languageMail;
	private String code;
	private String languageCode;

	@Action(value = "language-mail/read", results = { @Result(name = "success", location = "language-mail/read.ftl") })
	public String execute() throws Exception {
		languageMail = languageMailDao.findByCode(code,
				new Locale(languageCode));
		if (languageMail == null) {
			throw new Exception();
		}
		return SUCCESS;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public LanguageMail getLanguageMail() {
		return languageMail;
	}

}
