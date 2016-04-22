package com.dig_i.front.views.freemarker.template;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dig_i.front.dao.ImageStore;
import com.dig_i.front.entity.InformationImageSize;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class InformationImageMethod extends AssetsMethod {

	private ImageStore imageStore;

	public TemplateModel exec(@SuppressWarnings("rawtypes") List args)
			throws TemplateModelException {
		if (args.size() < 1 || StringUtils.isEmpty((String) args.get(0))) {
			throw new TemplateModelException("Wrong arguments");
		}

		Integer id = Integer.parseInt((String) args.get(0));
		String size = (String) args.get(1);

		String file = getImageStore().getImageFile(id,
				InformationImageSize.nameOf(size));

		return super.exec(Arrays.asList(file, "images", "external"));
	}

	private ImageStore getImageStore() {
		if (imageStore == null) {
			ApplicationContext context = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			imageStore = (ImageStore) context.getBean("informationDao");
		}
		return imageStore;
	}
}
