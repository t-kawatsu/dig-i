package com.dig_i.sp.action.user_favorite;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.sp.action.AbstractAction;
import com.dig_i.front.entity.*;
import com.dig_i.front.dao.UserFavoriteDao;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DISP_PAR_PAGE_NUM = 10;

	@Resource
	private UserFavoriteDao userFavoriteDao;

	private Integer page;
	private SimplePager<Information> pager;

	@Action(value = "user-favorite/index", results = { @Result(name = "success", location = "user-favorite/index.ftl") })
	public String execute() throws Exception {
		User user = getCurrentUser();
		if (user == null) {
			return LOGIN;
		}
		pager = userFavoriteDao.paginateByUserId(DISP_PAR_PAGE_NUM, page,
				user.getId());
		return SUCCESS;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}

	public SimplePager<Information> getPager() {
		return pager;
	}
}
