package com.dig_i.admin.action.resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.admin.form.ResourceForm;
import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.service.RssResourceService;

public class UpdateAction extends AbstractAction {

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

	private Integer id;

	@Action(value = "resource/update/{id}", results = {
			@Result(name = "input", location = "resource/create-confirm.ftl"),
			@Result(name = "success", location = "resource/index", type = "redirect") })
	public String execute() throws Exception {
		Resource resource = resourceDao.findById(id);

		if (!"POST".equals(this.request.getMethod())) {
			BeanUtils.copyProperties(resourceForm, resource);
			return INPUT;
		}

		if (!resourceForm.validate(this)) {
			return INPUT;
		}

		BeanUtils.copyProperties(resource, resourceForm);
		resourceDao.update(resource);

		return SUCCESS;
	}

	public ResourceForm getResourceForm() {
		return resourceForm;
	}

	public void setResourceForm(ResourceForm resourceForm) {
		this.resourceForm = resourceForm;
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
