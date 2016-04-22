package com.dig_i.admin.action.language_site_information;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.LanguageSiteInformationDao;
import com.dig_i.front.entity.LanguageSiteInformation;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSiteInformationDao languageSiteInformationDao;
	private Integer page;
	private SimplePager<LanguageSiteInformation> pager;

	@Action(value = "language-site-information/index", results = { @Result(name = "success", location = "language-site-information/index.ftl") })
	public String execute() throws Exception {
		pager = languageSiteInformationDao.paginate(DISP_ITEMS_NUM_PAR_PAGE,
				page);
		return SUCCESS;
	}

	public SimplePager<LanguageSiteInformation> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
