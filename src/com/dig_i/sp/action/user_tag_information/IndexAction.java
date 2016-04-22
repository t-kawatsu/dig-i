package com.dig_i.sp.action.user_tag_information;

import java.security.InvalidParameterException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.TagDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.ResourceType;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.entity.UserTag;
import com.dig_i.sp.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DISP_PAR_PAGE_NUM = 10;
	private static final int DISP_RELATE_TAGS_NUM = 5;
	// private static final int DISP_MAX_PAGE_NUM = 30;

	@Resource
	private TagDao tagDao;
	@Resource
	private UserTagDao userTagDao;
	@Resource
	private InformationDao informationDao;

	private Integer userTagId;
	private ResourceType resourceType;
	private UserTag userTag;
	private List<Information> informations;
	private List<Tag> relateTags;
	private int cntTagRegisteredUsers;

	@Action(value = "user-tag-information/index/user-tag-id/{userTagId}", results = { @Result(name = "success", location = "user-tag-information/index.ftl") })
	public String execute() throws Exception {
		userTag = userTagDao.findById(userTagId);
		if (userTag == null) {
			throw new InvalidParameterException();
		}

		cntTagRegisteredUsers = userTagDao
				.countTagRegistingUsersByTagId(userTag.getTagId());

		relateTags = tagDao.findRelateTagsByTagId(userTag.getTagId(),
				DISP_RELATE_TAGS_NUM);

		String sessPageKey = getClass().getName();
		sessionManager.removeMorePage(sessPageKey, userTagId);

		informations = informationDao.findByUserTagIdAndResourceType(
				userTagId,
				resourceType,
				DISP_PAR_PAGE_NUM, null);
		
		sessionManager.putMorePage(sessPageKey, 
				userTagId, informationDao.detectIds(informations));
		
		// update last readed at
		userTagDao.updateLastReadedAt(userTag);

		return SUCCESS;
	}

	@Action(value = "user-tag-information/read-type-ajax/user-tag-id/{userTagId}/resource-type/{resourceType}", results = {
			@Result(name = "success", location = "user-tag-information/sorted.ftl"),
			@Result(name = "twitter", location = "common/_twitter-search.ftl") })
	public String readTypeAjaxAction() throws Exception {
		userTag = userTagDao.findById(userTagId);
		if (userTag == null) {
			throw new InvalidParameterException();
		}

		String sessPageKey = getClass().getName();
		sessionManager.removeMorePage(sessPageKey, userTagId);

		if (ResourceType.Twitter == resourceType) {
			return "twitter";
		}

		informations = informationDao.findByUserTagIdAndResourceType(
				userTagId,
				resourceType,
				DISP_PAR_PAGE_NUM, null);
		
		sessionManager.putMorePage(sessPageKey, 
				userTagId, informationDao.detectIds(informations));

		return SUCCESS;
	}

	@Action(value = "user-tag-information/more-ajax/user-tag-id/{userTagId}/resource-type/{resourceType}", results = { @Result(name = "success", location = "common/_informations.ftl") })
	public String moreAjaxAction() throws Exception {
		userTag = userTagDao.findById(userTagId);
		if (userTag == null) {
			throw new InvalidParameterException();
		}
		
		String sessPageKey = getClass().getName();
		List<Integer> ids = sessionManager.getMorePage(sessPageKey, userTagId);
		
		informations = informationDao.findByUserTagIdAndResourceType(
				userTagId, 
				resourceType,
				DISP_PAR_PAGE_NUM, ids);
		
		if(ids != null) {
			ids.addAll(informationDao.detectIds(informations));
		} else {
			ids = informationDao.detectIds(informations);
		}
		
		sessionManager.putMorePage(sessPageKey, userTagId, ids);
		
		return SUCCESS;
	}

	public void setUserTagId(Integer userTagId) {
		this.userTagId = userTagId;
	}

	public UserTag getUserTag() {
		return userTag;
	}
	
	public Tag getTag() {
		return userTag.getTag();
	}

	public void setResourceType(String resourceType) {
		this.resourceType = StringUtils.isEmpty(resourceType) ? null : ResourceType.valueOf(resourceType);
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public List<Tag> getRelateTags() {
		return relateTags;
	}

	public boolean getNoMoreItems() {
		return informations == null || informations.size() < DISP_PAR_PAGE_NUM;
	}

	public int getCntTagRegisteredUsers() {
		return cntTagRegisteredUsers;
	}

}
