package com.dig_i.sp.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.dao.TagDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.form.AbstractForm;
import com.dig_i.front.service.InformationSearchService;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class TagForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String filteredName;
	@javax.annotation.Resource
	private TagDao tagDao;
	@javax.annotation.Resource
	private UserTagDao userTagDao;
	@javax.annotation.Resource
	private InformationSearchService informationSearchService;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		filteredName = informationSearchService.searchWordFilter(name);
		this.name = name;
	}

	public String getFilteredName() {
		return filteredName;
	}

	public boolean validate(ActionSupport as, Integer userId) {
		
		if(!validateWord(as, userId)) {
			validateRegistable(as, userId);
		}

		return !as.hasFieldErrors();
	}
	
	public boolean validateWord(ActionSupport as, Integer userId) {
		// name
		if (StringUtils.isEmpty(getName())) {
			as.addFieldError("tagForm.name", as.getText("invalidate.required"));
		} else if (20 < getName().length()) {
			as.addFieldError(
					"tagForm.name",
					as.getText("invalidate.maxLength", null,
							Arrays.asList("20")));
		} else if (informationSearchService
				.isUnRegistableWord(getFilteredName())) {
			as.addFieldError(
					"tagForm.name",
					as.getText("invalidate.unRegistableWord", null,
							Arrays.asList(getName())));
		}

		return !as.hasFieldErrors();
	}
	
	public boolean validateRegistable(ActionSupport as, Integer userId) {

		Tag tag = tagDao.findByName(getFilteredName());
		if (tag != null
				&& 0 < userTagDao
						.countByUserIdAndTagId(userId, tag.getId())) {
			as.addFieldError("tagForm.name",
					as.getText("invalidate.alreadyExists"));
			return false;
		}

		if (Integer.parseInt(as.getText("app.tag.regist.limit")) <= userTagDao
				.countByUserId(userId)) {
			as.addFieldError("tagForm.name",
					as.getText("invalidate.registLimit", null, Arrays.asList(as.getText("app.tag.regist.limit"))));
			return false;
		}

		return !as.hasFieldErrors();
	}
}
