package com.dig_i.front.views.freemarker;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.ScopesHashModel;
import com.opensymphony.xwork2.util.ValueStack;
import freemarker.template.TemplateException;
import freemarker.template.Configuration;

import com.dig_i.front.views.freemarker.template.AssetsMethod;
import com.dig_i.front.views.freemarker.template.UrlMethod;
import com.dig_i.front.views.freemarker.template.PathInfoMethod;
import com.dig_i.front.views.freemarker.template.InformationImageMethod;
import com.dig_i.front.views.freemarker.template.StripTagsMethod;
import com.dig_i.front.views.freemarker.template.CutStrMethod;
import com.dig_i.front.views.freemarker.template.MaskPasswordMethod;
import com.dig_i.front.views.freemarker.template.LanguageSettingMethod;
import com.dig_i.front.views.freemarker.template.ResourceImageMethod;

public class CustomFreemarkerManager extends FreemarkerManager {

	@Override
	protected Configuration createConfiguration(ServletContext servletContext)
			throws TemplateException {
		Configuration cfg = super.createConfiguration(servletContext);
		cfg.setDateFormat("yyyy/MM/dd kk:mm:ss");
		cfg.setDateTimeFormat("yyyy/MM/dd kk:mm:ss");
		cfg.setNumberFormat("0.######");
		//cfg.setWhitespaceStripping(true);
		/*
		 * //cfg.setDefaultEncoding("UTF-8");
		 * cfg.setSetting(Configuration.OUTPUT_ENCODING_KEY, "UTF-8");
		 * cfg.addAutoImport("my", "/WEB-INF/content/lib/my.ftl");
		 */
		return cfg;
	}

	/*
	 * @Override protected void loadSettings(ServletContext servletContext) {
	 * super.loadSettings(servletContext); }
	 */
	@Override
	public void populateContext(ScopesHashModel model, ValueStack stack,
			Object action, HttpServletRequest request,
			HttpServletResponse response) {
		super.populateContext(model, stack, action, request, response);
		model.put("assets", new AssetsMethod());
		model.put("url", new UrlMethod());
		model.put("pathinfo", new PathInfoMethod());
		model.put("informationImage", new InformationImageMethod());
		model.put("resourceImage", new ResourceImageMethod());
		model.put("stripTags", new StripTagsMethod());
		model.put("cutStr", new CutStrMethod());
		model.put("maskPassword", new MaskPasswordMethod());
		model.put("languageSetting", new LanguageSettingMethod());
	}
}
