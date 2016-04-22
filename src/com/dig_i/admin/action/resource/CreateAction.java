package com.dig_i.admin.action.resource;

import java.net.URL;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.URLConverter;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.ResourceForm;
import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.service.RssResourceService;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource(name = "rssResourceService")
	private RssResourceService resourceService;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private ResourceForm resourceForm;

	@Action(value = "resource/create", results = {
			@Result(name = "input", location = "resource/create.ftl"),
			@Result(name = "success", location = "resource/create-confirm.ftl") })
	public String execute() throws Exception {

		if (!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}

		if (!resourceForm.validateUrl(this)) {
			return INPUT;
		}

		Resource resource = resourceService.traversal(
				new URL(resourceForm.getUrl()));
		BeanUtils.copyProperties(resourceForm, resource);

		return SUCCESS;
	}

	@Action(value = "resource/create-complete", results = {
			@Result(name = "input", location = "resource/create-confirm.ftl"),
			@Result(name = "success", location = "resource/index", type = "redirect") })
	public String completeAction() throws Exception {

		if (!resourceForm.validate(this)) {
			return INPUT;
		}

		Resource resource = new Resource();
		ConvertUtils.register(new URLConverter(null), java.net.URL.class);
		BeanUtils.copyProperties(resource, resourceForm);
		resource.setLastBuildedAt(new Date());
		resourceDao.persist(resource);

		return SUCCESS;
	}

	public ResourceForm getResourceForm() {
		return resourceForm;
	}

	public void setResourceForm(ResourceForm resourceForm) {
		this.resourceForm = resourceForm;
	}

}
