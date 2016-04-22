package com.dig_i.admin.action.language_help;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.LanguageHelpDao;
import com.dig_i.front.entity.LanguageHelp;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageHelpDao languageHelpDao;
	private Integer page;
	private SimplePager<LanguageHelp> pager;

	@Action(value = "language-help/index", results = { @Result(name = "success", location = "language-help/index.ftl") })
	public String execute() throws Exception {
		pager = languageHelpDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<LanguageHelp> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
