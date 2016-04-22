package com.dig_i.front.action.inquiry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.action.AbstractAction;
import com.dig_i.front.dao.InquiryDao;
import com.dig_i.front.entity.Inquiry;
import com.dig_i.front.form.InquiryForm;
import com.dig_i.front.service.MailService;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InquiryDao inquiryDao;
    @Resource
    private InquiryForm inquiryForm;
    @Resource
    private MailService mailService;
	
	@Action(value="inquiry/create-ajax",
		results={
			@Result(name="input", location="inquiry/create.ftl"),
            @Result(name="success", location="inquiry/create-complete.ftl")
		}
	)
	@Override
	public String execute() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!inquiryForm.validate(this)) {
            return INPUT;
        }   

        // save
        Inquiry inquiry = new Inquiry();
        BeanUtils.copyProperties(inquiry, inquiryForm);
        inquiry.setUseragent(request.getHeader("User-Agent"));
        inquiryDao.persist(inquiry);

        // send mail
        try {
        	mailService.sendInquiryToSupport(
        		getText("app.mail.support"), inquiry);
        } catch (Exception e) {}
        
        return SUCCESS;
	}

    public InquiryForm getInquiryForm() {
        return inquiryForm;
    }   

    public void setInquiryForm(InquiryForm inquiryForm) {
        this.inquiryForm = inquiryForm;
    }

}
