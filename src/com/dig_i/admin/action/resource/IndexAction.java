package com.dig_i.admin.action.resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	private Integer page;
	private SimplePager<Resource> pager;

	@Action(value = "resource/index", results = { @Result(name = "success", location = "resource/index.ftl") })
	public String execute() throws Exception {
		pager = resourceDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<Resource> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
