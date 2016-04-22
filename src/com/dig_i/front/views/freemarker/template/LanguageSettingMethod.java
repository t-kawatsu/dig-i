package com.dig_i.front.views.freemarker.template;

import java.util.List;
import java.util.Locale;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.SimpleScalar;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dig_i.front.dao.LanguageSettingDao;

public class LanguageSettingMethod implements TemplateMethodModel {

	private LanguageSettingDao languageSettingDao;

	public TemplateModel exec(@SuppressWarnings("rawtypes") List args)
			throws TemplateModelException {
		if (args.size() < 3) {
			throw new TemplateModelException("Wrong arguments");
		}
		String code = (String) args.get(0);
		String key = (String) args.get(1);
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		Locale lang = new Locale((String) args.get(2));
		String value = getLanguageSettingDao()
				.getContentsValue(code, key, lang);
		return new SimpleScalar(value);
	}

	public LanguageSettingDao getLanguageSettingDao() {
		ApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext());
		if(languageSettingDao == null) {
			languageSettingDao = (LanguageSettingDao)context.getBean("languageSettingDao");
		}
		return languageSettingDao;
	}
}
