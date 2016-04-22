package com.dig_i.api.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dig_i.api.service.ApiService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

@ParentPackage("base")
@Namespace("/api")
@InterceptorRefs({ @InterceptorRef("myApiStack"), })
abstract public class AbstractAction extends ActionSupport implements
		ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected ApiService apiService;

	protected HttpServletRequest request;

	protected Integer limit;
	protected Integer page;

	protected Map<String, Object> uploadResultJson = new HashMap<String, Object>();

	@Before
	public void beforeAction() {
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, Object> getUploadResultJson() {
		return uploadResultJson;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
