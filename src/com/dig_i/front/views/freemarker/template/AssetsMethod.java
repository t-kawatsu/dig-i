package com.dig_i.front.views.freemarker.template;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.SimpleScalar;

import org.apache.struts2.ServletActionContext;
import org.apache.commons.lang3.StringUtils;

public class AssetsMethod implements TemplateMethodModel {

	private static final String BASE_DIRECTORY = "/assets";
	private static final String DEFAULT_DIRECTORY = "/front";

	public TemplateModel exec(@SuppressWarnings("rawtypes") List args)
			throws TemplateModelException {
		String namespace = ServletActionContext.getActionMapping()
				.getNamespace();
		if (StringUtils.isEmpty(namespace) || "/".equals(namespace)) {
			namespace = DEFAULT_DIRECTORY;
		}
		String file = (String) args.get(0);
		String type = (String) args.get(1);
		String module = 3 <= args.size() ? '/' + (String) args.get(2)
				: namespace;
		String contextpath = ServletActionContext.getRequest().getContextPath();
		return new SimpleScalar(contextpath + BASE_DIRECTORY + module + '/'
				+ type + '/' + file);
	}
}
