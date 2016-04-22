package com.dig_i.sp.action.user_tag;

import javax.annotation.Resource;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.entity.*;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.sp.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserTagDao userTagDao;

	private List<UserTag> userTags;

	@Action(value = "user-tag/index", results = { @Result(name = "success", location = "user-tag/index.ftl") })
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}

		User user = getCurrentUser();
		userTags = userTagDao
				.findByUserIdOrderByInformationUpdatedAt(user.getId(),
						Integer.parseInt(getText("app.tag.regist.limit")));

		return SUCCESS;
	}

	public void setUserTags(List<UserTag> userTags) {
		this.userTags = userTags;
	}

	public List<UserTag> getUserTags() {
		return userTags;
	}
}
