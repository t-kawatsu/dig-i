package com.dig_i.sp.action.user_tag;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.UserTag;
import com.dig_i.front.service.UserTagService;
import com.dig_i.sp.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserTagDao userTagDao;
	@Resource
	private UserTagService userTagService;

	private Integer userTagId;

	@Action(value = "user-tag/delete-ajax/id/{userTagId}")
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return null;
		}
		UserTag userTag = userTagDao.findById(userTagId);
		if (userTag == null || userTag.getUserId() != getCurrentUser().getId()) {
			return null;
		}

		userTagService.deleteUserTag(userTag);

		return null;
	}

	public void setUserTagId(Integer userTagId) {
		this.userTagId = userTagId;
	}

}
