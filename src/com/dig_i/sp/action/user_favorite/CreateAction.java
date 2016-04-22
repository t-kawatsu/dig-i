package com.dig_i.sp.action.user_favorite;

import java.security.InvalidParameterException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.UserFavoriteDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserFavorite;
import com.dig_i.sp.action.AbstractAction;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InformationDao informationDao;
	@Resource
	private UserFavoriteDao userFavoriteDao;

	private Information information;
	private Integer informationId;
	private boolean isFavorited;
	private int cntUserFavorites;

	@Action(value = "user-favorite/toggle-ajax/information-id/{informationId}", results = {
			@Result(name = "success", location = "user-favorite/_bookmark.ftl") })
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		information = informationDao.findById(informationId);
		if (information == null) {
			throw new InvalidParameterException();
		}

		User user = getCurrentUser();
		UserFavorite userFavorite = userFavoriteDao
				.findByUserIdAndInformationId(user.getId(), informationId);
		if (userFavorite != null) {
			userFavoriteDao.remove(userFavorite);
		} else {
			userFavoriteDao.create(user.getId(), informationId);
		}
		
		cntUserFavorites = userFavoriteDao.countByInformationId(informationId);
		isFavorited = userFavoriteDao.isUserFavorited(user.getId(), informationId);

		return SUCCESS;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}
	
	public Information getInformation() {
		return information;
	}

	public int getCntUserFavorites() {
		return cntUserFavorites;
	}

	public boolean getIsFavorited() {
		return isFavorited;
	}

}
