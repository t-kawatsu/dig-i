package com.dig_i.sp.action.request;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.RequestDao;
import com.dig_i.front.entity.Request;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.RequestForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private RequestDao requestDao;
	@Resource
	private RequestForm requestForm;

	@Action(value = "request/create", results = {
			@Result(name = "input", location = "request/create.ftl"),
			@Result(name = "success", location = "request/create-complete", type = "redirect") })
	public String execute() throws Exception {
		if (!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if (!requestForm.validate(this)) {
			return INPUT;
		}

		// save
		Request request = new Request();
		BeanUtils.copyProperties(request, requestForm);
		request.setUseragent(this.request.getHeader("User-Agent"));
		if (getIsLogined()) {
			request.setUserId(getCurrentUser().getId());
		}
		requestDao.persist(request);

		return SUCCESS;
	}

	@Action(value = "request/create-complete", results = { @Result(name = "success", location = "request/create-complete.ftl") })
	public String completeAction() throws Exception {
		return SUCCESS;
	}

	public RequestForm getRequestForm() {
		return requestForm;
	}

	public void setRequestForm(RequestForm requestForm) {
		this.requestForm = requestForm;
	}
}
