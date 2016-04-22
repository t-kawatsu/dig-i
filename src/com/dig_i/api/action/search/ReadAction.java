package com.dig_i.api.action.search;

import java.util.List;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.api.action.AbstractAction;
import com.dig_i.front.entity.Information;
import com.dig_i.front.service.InformationSearchService;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@javax.annotation.Resource
	private InformationSearchService searchService;
	private String word;
	private List<Information> informations;

	@Action(value = "search/read", results = {
			@Result(name = "success", type = "json", params = { "statusCode",
					"200", "contentType", "text/html", "noCache", "true",
					"root", "uploadResultJson" }),
			@Result(name = "error", type = "json", params = { "statusCode",
					"500", "contentType", "text/html", "noCache", "true",
					"root", "uploadResultJson" }) })
	public String execute() throws Exception {
		logger.info("API SEARCH: word:{}", word);
		
		String[] words = StringUtils.split(word, ",");
		URLCodec codec = new URLCodec();
		for(String row : words) {
			row = codec.decode(row, "UTF-8");
		}
		informations = searchService.recommendByWords(words, limit, page);
		uploadResultJson.put("items",
				apiService.convertInformationItems(informations));
		
		logger.info("API SEARCH: return {} informations", 
				informations == null ? 0 : informations.size());
		
		return SUCCESS;
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
}
