package com.dig_i.sp.action.user_favorite_resource;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.front.dao.UserFavoriteResourceDao;
import com.dig_i.front.entity.UserFavoriteResource;
import com.dig_i.sp.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserFavoriteResourceDao userFavoriteResourceDao;

	private Integer resourceId;

	@Action(value = "user-favorite-resource/delete-ajax/resource-id/{resourceId}")
	public String execute() throws Exception {
		if (!getIsLogined()) {
			throw new Exception();
		}
		UserFavoriteResource userFavoriteResource = userFavoriteResourceDao
				.findByUserIdAndResourceId(getCurrentUser().getId(), resourceId);
		if (userFavoriteResource == null) {
			throw new Exception();
		}

		userFavoriteResourceDao.remove(userFavoriteResource);
		return NONE;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
}
