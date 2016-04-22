package com.dig_i.admin.action.inquiry;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.InquiryDao;
import com.dig_i.front.entity.Inquiry;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InquiryDao inquiryDao;
	private Integer page;
	private SimplePager<Inquiry> pager;

	@Action(value = "inquiry/index", results = { @Result(name = "success", location = "inquiry/index.ftl") })
	public String execute() throws Exception {
		pager = inquiryDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<Inquiry> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
