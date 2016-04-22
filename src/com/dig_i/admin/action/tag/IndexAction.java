package com.dig_i.admin.action.tag;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.TagDao;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private TagDao tagDao;
	private Integer page;
	private SimplePager<Tag> pager;

	@Action(value = "tag/index", results = { @Result(name = "success", location = "tag/index.ftl") })
	public String execute() throws Exception {
		pager = tagDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<Tag> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
