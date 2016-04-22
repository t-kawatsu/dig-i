package com.dig_i.admin.action.language_setting;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.admin.action.AbstractAction;
import com.dig_i.front.dao.LanguageSettingDao;
import com.dig_i.front.entity.LanguageSetting;
import com.dig_i.front.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSettingDao languageSettingDao;
	private Integer page;
	private SimplePager<LanguageSetting> pager;

	@Action(value = "language-setting/index", results = { @Result(name = "success", location = "language-setting/index.ftl") })
	public String execute() throws Exception {
		pager = languageSettingDao.paginate(DISP_ITEMS_NUM_PAR_PAGE, page);
		return SUCCESS;
	}

	public SimplePager<LanguageSetting> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
