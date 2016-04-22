package com.dig_i.fb.action.index;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.fb.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "", results = { @Result(name = "success", location = "index/index.ftl") })
	public String execute() throws Exception {
		return SUCCESS;
	}
}
