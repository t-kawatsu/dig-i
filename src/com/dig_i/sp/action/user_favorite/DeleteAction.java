package com.dig_i.sp.action.user_favorite;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.front.dao.UserFavoriteDao;
import com.dig_i.front.entity.UserFavorite;
import com.dig_i.sp.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserFavoriteDao userFavoriteDao;

	private Integer informationId;

	@Action(value = "user-favorite/delete-ajax/information-id/{informationId}")
	public String execute() throws Exception {
		if (!getIsLogined()) {
			throw new Exception();
		}
		UserFavorite userFavorite = userFavoriteDao
				.findByUserIdAndInformationId(getCurrentUser().getId(),
						informationId);
		if (userFavorite == null) {
			throw new Exception();
		}

		userFavoriteDao.remove(userFavorite);
		return NONE;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}
}
