package com.dig_i.admin.action.user;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private UserDao userDao;
	private Integer page;
	private SimplePager<User> pager;

	@Action(value = "user/index", results = { @Result(name = "success", location = "user/index.ftl") })
	public String execute() throws Exception {
		pager = userDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<User> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
