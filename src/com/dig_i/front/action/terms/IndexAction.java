package com.dig_i.front.action.terms;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="terms/index", results = { @Result(name = "success", location = "terms/index.ftl") })
	@Override
    public String execute() throws Exception {
        return SUCCESS;
    }

	@Action(value="terms/privacy", results = { @Result(name = "success", location = "terms/privacy.ftl") })
    public String privacyAction() throws Exception {
        return SUCCESS;
    }

}
