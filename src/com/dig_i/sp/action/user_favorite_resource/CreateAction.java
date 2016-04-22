package com.dig_i.sp.action.user_favorite_resource;

import java.security.InvalidParameterException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.dao.UserFavoriteResourceDao;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserFavoriteResource;
import com.dig_i.sp.action.AbstractAction;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private UserFavoriteResourceDao userFavoriteResourceDao;

	private Integer resourceId;
	private Resource resource;
	private boolean isFavorited;
	private int cntUserFavorites;

	@Action(value = "user-favorite-resource/toggle-ajax/resource-id/{resourceId}", results = {
			@Result(name = "success", location = "user-favorite-resource/_favorite.ftl") })
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		resource = resourceDao.findById(resourceId);
		if (resource == null) {
			throw new InvalidParameterException();
		}

		User user = getCurrentUser();
		UserFavoriteResource userFavoriteResource = userFavoriteResourceDao
				.findByUserIdAndResourceId(user.getId(), resourceId);
		if (userFavoriteResource != null) {
			userFavoriteResourceDao.remove(userFavoriteResource);
		} else {
			userFavoriteResourceDao.create(user.getId(), resourceId);
		}
		
		cntUserFavorites = userFavoriteResourceDao.countByResourceId(resourceId);
		isFavorited = userFavoriteResourceDao.isUserFavorited(user.getId(), resourceId);

		return SUCCESS;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public int getCntUserFavorites() {
		return cntUserFavorites;
	}

	public boolean getIsFavorited() {
		return isFavorited;
	}
}
