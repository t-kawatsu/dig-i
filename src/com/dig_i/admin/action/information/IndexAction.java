package com.dig_i.admin.action.information;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private InformationDao informationDao;
	private Integer page;
	private SimplePager<Information> pager;

	@Action(value = "information/index", results = { @Result(name = "success", location = "information/index.ftl") })
	public String execute() throws Exception {
		pager = informationDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<Information> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
