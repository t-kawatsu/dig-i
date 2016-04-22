package com.dig_i.sp.action.tag_information;

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

	private Integer tagId;
	private ResourceType resourceType;
	private Tag tag;
	private List<Information> informations;
	private List<Tag> relateTags;
	private int cntTagRegisteredUsers;
	private boolean isRegisteredTag;

	@Action(value = "tag-information/index/tag-id/{tagId}", results = { @Result(name = "success", location = "tag-information/index.ftl") })
	public String execute() throws Exception {

		tag = tagDao.findById(tagId);
		if (tag == null) {
			throw new InvalidParameterException();
		}

		cntTagRegisteredUsers = userTagDao.countTagRegistingUsersByTagId(tagId);

		relateTags = tagDao.findRelateTagsByTagId(tagId, DISP_RELATE_TAGS_NUM);
		
		String sessPageKey = getClass().getName();
		sessionManager.removeMorePage(sessPageKey, tagId);

		informations = informationDao.findByTagIdAndResourceType(
				tagId,
				resourceType,
				DISP_PAR_PAGE_NUM, null);
		
		sessionManager.putMorePage(sessPageKey, 
				tagId, informationDao.detectIds(informations));

		if (getIsLogined()) {
			isRegisteredTag = userTagDao.isUserRegisteredTag(getCurrentUser()
					.getId(), tagId);
		}

		return SUCCESS;
	}

	@Action(value = "tag-information/read-type-ajax/tag-id/{tagId}/resource-type/{resourceType}", results = {
			@Result(name = "success", location = "tag-information/sorted.ftl"),
			@Result(name = "twitter", location = "common/_twitter-search.ftl") })
	public String readTypeAjaxAction() throws Exception {

		tag = tagDao.findById(tagId);
		if (tag == null) {
			throw new InvalidParameterException();
		}
		
		String sessPageKey = getClass().getName();
		sessionManager.removeMorePage(sessPageKey, tagId);

		if (ResourceType.Twitter == resourceType) {
			return "twitter";
		}
		
		informations = informationDao.findByTagIdAndResourceType(
				tagId,
				resourceType,
				DISP_PAR_PAGE_NUM, null);
		
		sessionManager.putMorePage(sessPageKey, 
				tagId, informationDao.detectIds(informations));

		return SUCCESS;
	}

	@Action(value = "tag-information/more-ajax/tag-id/{tagId}/resource-type/{resourceType}", results = { @Result(name = "success", location = "common/_informations.ftl") })
	public String moreAjaxAction() throws Exception {

		tag = tagDao.findById(tagId);
		if (tag == null) {
			throw new InvalidParameterException();
		}

		String sessPageKey = getClass().getName();
		List<Integer> ids = sessionManager.getMorePage(sessPageKey, tagId);
		
		informations = informationDao.findByTagIdAndResourceType(
				tagId, 
				resourceType,
				DISP_PAR_PAGE_NUM, ids);
		
		if(ids != null) {
			ids.addAll(informationDao.detectIds(informations));
		} else {
			ids = informationDao.detectIds(informations);
		}
		
		sessionManager.putMorePage(sessPageKey, tagId, ids);

		return SUCCESS;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Tag getTag() {
		return tag;
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

	public boolean getIsRegisteredTag() {
		return isRegisteredTag;
	}

	public int getCntTagRegisteredUsers() {
		return cntTagRegisteredUsers;
	}

}
