package com.dig_i.sp.action.resource;

import java.util.EnumSet;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.ResourceType;
import com.dig_i.front.util.SimplePager;
import com.dig_i.sp.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DISP_PAR_PAGE_NUM = 10;

	@javax.annotation.Resource
	private ResourceDao resourceDao;

	private List<Resource> resources;
	private Integer page;
	private SimplePager<Resource> pager;

	@Action(value = "resource/index", results = { @Result(name = "success", location = "resource/index.ftl") })
	public String execute() throws Exception {
		pager = resourceDao.paginateByType(DISP_PAR_PAGE_NUM, page,
				EnumSet.of(ResourceType.RSS1_0, ResourceType.RSS2_0));
		return SUCCESS;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}

	public SimplePager<Resource> getPager() {
		return pager;
	}
}
