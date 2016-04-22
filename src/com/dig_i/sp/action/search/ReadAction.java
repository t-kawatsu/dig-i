package com.dig_i.sp.action.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.service.InformationSearchService;
import com.dig_i.sp.action.AbstractAction;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DISP_PAR_PAGE_NUM = 10;

	@javax.annotation.Resource
	private InformationSearchService searchService;
	@javax.annotation.Resource
	private InformationDao informationDao;
	private String word;
	private List<Information> informations;

	@Action(value = "search/read", results = {
			@Result(name = "input", location = "search/read.ftl"),
			@Result(name = "success", location = "search/read.ftl") })
	public String execute() throws Exception {
		
		String sessPageKey = getClass().getName();
		sessionManager.removeMorePage(sessPageKey, "");

		informations = searchService.searchByWord(word, DISP_PAR_PAGE_NUM, new ArrayList<Integer>());
		
		sessionManager.putMorePage(sessPageKey, 
				"", informationDao.detectIds(informations));
		
		return SUCCESS;
	}

	@Action(value = "search/read-more-ajax", results = { @Result(name = "success", location = "common/_informations.ftl") })
	public String moreAjaxAction() throws Exception {

		String sessPageKey = getClass().getName();
		List<Integer> ids = sessionManager.getMorePage(sessPageKey, "");
		
		informations = searchService.searchByWord(word, DISP_PAR_PAGE_NUM, ids);
		
		if(ids != null) {
			ids.addAll(informationDao.detectIds(informations));
		} else {
			ids = informationDao.detectIds(informations);
		}
		
		sessionManager.putMorePage(sessPageKey, "", ids);
		
		return SUCCESS;
	}

	public void validate() {
		if (StringUtils.isEmpty(word)) {
			addFieldError("word", getText("invalidate.required"));
		}
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public boolean getNoMoreItems() {
		return informations == null || informations.size() < DISP_PAR_PAGE_NUM;
	}

}
