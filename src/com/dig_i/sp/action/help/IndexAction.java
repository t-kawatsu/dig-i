package com.dig_i.sp.action.help;

import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.sp.action.AbstractAction;
import com.dig_i.front.entity.*;
import com.dig_i.front.dao.LanguageHelpDao;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	LanguageHelpDao languageHelpDao;
	List<LanguageHelp> helps;

	@Action(value = "help/index", results = { @Result(name = "success", location = "help/index.ftl") })
	public String execute() throws Exception {
		helps = languageHelpDao.findAll();
		return SUCCESS;
	}

	public List<LanguageHelp> getHelps() {
		return helps;
	}
}
