package com.dig_i.front.views.freemarker.template;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.SimpleScalar;

import org.apache.commons.lang3.StringUtils;

import com.dig_i.front.util.crypt.BlowfishCrypt;

public class MaskPasswordMethod implements TemplateMethodModel {

	private final String defMask = "*";

	public TemplateModel exec(@SuppressWarnings("rawtypes") List args)
			throws TemplateModelException {
		if (args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String password = (String) args.get(0);
		String mask = 2 <= args.size() ? (String) args.get(1) : defMask;
		boolean useDecript = 3 <= args.size() ? Boolean.valueOf((String) args.get(2)) : true;
		if(useDecript) {
			password = BlowfishCrypt.decrypt(password);
		}
		return new SimpleScalar(StringUtils.repeat(mask, password.length()));
	}
}
