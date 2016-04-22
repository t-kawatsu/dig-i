package com.dig_i.front.action.index;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.action.AbstractAction;
import com.dig_i.front.form.MailForm;
import com.dig_i.front.service.MailService;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private MailService mailService;
	@javax.annotation.Resource
	private MailForm mailForm;
	private boolean successSendMail;

	@Action(value = "", results = { @Result(name = "success", location = "index/index.ftl") })
	//@SkipValidation
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	@Action(value = "index/send-site-url-ajax", results = {
			@Result(name = "success", location = "index/send-site-url-complete.ftl"),
			@Result(name = "input", location = "index/send-site-url.ftl") })
	public String sendAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!mailForm.validate(this)) {
            return INPUT;
        }   

		mailService.sendSpUrlMail(getMailForm().getMailAddress());
		successSendMail = true;
		return SUCCESS;
	}

	//@VisitorFieldValidator(message = "")
	public void setMailForm(MailForm mailForm) {
		this.mailForm = mailForm;
	}

	public MailForm getMailForm() {
		return mailForm;
	}

	public boolean getSuccessSendMail() {
		return successSendMail;
	}
}
