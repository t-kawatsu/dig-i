package com.dig_i.admin.action.user_secession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.UserSecessionDao;
import com.dig_i.front.entity.UserSecession;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private UserSecessionDao userSecessionDao;
	private Integer page;
	private SimplePager<UserSecession> pager;

	@Action(value = "user-secession/index", results = { @Result(name = "success", location = "user-secession/index.ftl") })
	public String execute() throws Exception {
		pager = userSecessionDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<UserSecession> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
