package com.dig_i.admin.action.language_mail;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.LanguageMailDao;
import com.dig_i.front.entity.LanguageMail;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	private Integer page;
	private SimplePager<LanguageMail> pager;

	@Action(value = "language-mail/index", results = { @Result(name = "success", location = "language-mail/index.ftl") })
	public String execute() throws Exception {
		pager = languageMailDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<LanguageMail> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
