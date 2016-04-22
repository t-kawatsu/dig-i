package com.dig_i.front.action.error;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.action.AbstractAction;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> uploadResultJson;

	@Action(value = "error/500", results = {
			@Result(name = "ajax", location = "error/500-ajax.ftl"),
			@Result(name = "html", location = "error/500.ftl"),
			@Result(name = "json", type = "json", params = { "statusCode",
					"500", "contentType", "application/json", "noCache",
					"true", "root", "uploadResultJson" }) })
	@Override
	public String execute() throws Exception {
		/*
		 * if(!StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
		 * return "ajax"; }
		 */
		return "html";
	}

	@Action(value = "error/404", results = {
			@Result(name = "ajax", location = "error/404-ajax.ftl"),
			@Result(name = "html", location = "error/404.ftl"),
			@Result(name = "json", type = "json", params = { "statusCode",
					"404", "contentType", "application/json", "noCache",
					"true", "root", "uploadResultJson" }) })
	public String error404Action() throws Exception {
		/*
		 * if(!StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
		 * return "ajax"; }
		 */
		return "html";
	}

	public Map<String, Object> getUploadResultJson() {
		return uploadResultJson;
	}
}
