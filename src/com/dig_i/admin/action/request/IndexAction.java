package com.dig_i.admin.action.request;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.RequestDao;
import com.dig_i.front.entity.Request;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private RequestDao requestDao;
	private Integer page;
	private SimplePager<Request> pager;

	@Action(value = "request/index", results = { @Result(name = "success", location = "request/index.ftl") })
	public String execute() throws Exception {
		pager = requestDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<Request> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
