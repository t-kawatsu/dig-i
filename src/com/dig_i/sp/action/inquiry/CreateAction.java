package com.dig_i.sp.action.inquiry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InquiryDao;
import com.dig_i.front.entity.Inquiry;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.front.form.InquiryForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InquiryDao inquiryDao;
	@Resource
	private InquiryForm inquiryForm;

	@Action(value = "inquiry/create", results = {
			@Result(name = "input", location = "inquiry/create.ftl"),
			@Result(name = "success", location = "inquiry/create-complete", type = "redirect") })
	public String execute() throws Exception {
		if (!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if (!inquiryForm.validate(this)) {
			return INPUT;
		}

		// save
		Inquiry inquiry = new Inquiry();
		BeanUtils.copyProperties(inquiry, inquiryForm);
		inquiry.setUseragent(this.request.getHeader("User-Agent"));
		if (getIsLogined()) {
			inquiry.setUserId(getCurrentUser().getId());
		}

		inquiryDao.persist(inquiry);

		return SUCCESS;
	}

	@Action(value = "inquiry/create-complete", results = { @Result(name = "success", location = "inquiry/create-complete.ftl") })
	public String completeAction() throws Exception {
		return SUCCESS;
	}

	public InquiryForm getInquiryForm() {
		return inquiryForm;
	}

	public void setInquiryForm(InquiryForm inquiryForm) {
		this.inquiryForm = inquiryForm;
	}
}
