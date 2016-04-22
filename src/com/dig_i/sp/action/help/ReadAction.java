package com.dig_i.sp.action.help;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.sp.action.AbstractAction;
import com.dig_i.front.entity.*;
import com.dig_i.front.dao.LanguageHelpDao;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageHelpDao languageHelpDao;
	private LanguageHelp help;
	private Integer id;

	@Action(value = "help/read-ajax/{id}", results = { @Result(name = "success", location = "help/read-ajax.ftl") })
	public String execute() throws Exception {
		help = languageHelpDao.findById(id);
		if (help == null) {
			throw new Exception();
		}
		return SUCCESS;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LanguageHelp getHelp() {
		return help;
	}
}
