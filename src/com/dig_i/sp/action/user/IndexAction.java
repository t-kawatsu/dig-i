package com.dig_i.sp.action.user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.UserFavoriteDao;
import com.dig_i.front.dao.UserFavoriteResourceDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserTag;
import com.dig_i.sp.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Integer DISP_USER_TAGS_NUM = 3;

	@Resource
	private UserTagDao userTagDao;
	@Resource
	private UserFavoriteDao userFavoriteDao;
	@Resource
	private UserFavoriteResourceDao userFavoriteResourceDao;

	private int cntUserTags;
	private int cntUserFavorite;
	private int cntUserFavoriteResource;
	private List<UserTag> userTags;

	@Action(value = "user/index", results = { @Result(name = "success", location = "user/index.ftl") })
	public String execute() throws Exception {
		User user = getCurrentUser();
		if (user == null) {
			return LOGIN;
		}

		cntUserTags = userTagDao.countByUserId(user.getId());
		if (0 < cntUserTags) {
			userTags = userTagDao.findByUserIdOrderByInformationUpdatedAt(
					user.getId(), DISP_USER_TAGS_NUM);
		}

		cntUserFavorite = userFavoriteDao.countByUserId(user.getId());
		cntUserFavoriteResource = userFavoriteResourceDao.countByUserId(user
				.getId());

		return SUCCESS;
	}

	public int getDispUserTagsNum() {
		return DISP_USER_TAGS_NUM;
	}

	public int getCntUserTags() {
		return cntUserTags;
	}

	public int getCntUserFavorite() {
		return cntUserFavorite;
	}

	public int getCntUserFavoriteResource() {
		return cntUserFavoriteResource;
	}

	public List<UserTag> getUserTags() {
		return userTags;
	}
}
