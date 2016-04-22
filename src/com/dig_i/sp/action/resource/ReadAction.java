package com.dig_i.sp.action.resource;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.dao.UserFavoriteResourceDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.Resource;
import com.dig_i.sp.action.AbstractAction;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DISP_PAR_PAGE_NUM = 10;

	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private InformationDao informationDao;
	@javax.annotation.Resource
	private UserFavoriteResourceDao userFavoriteResourceDao;

	private Integer id;
	private Resource resource;
	private List<Information> informations;
	private boolean isFavorited;
	private int cntUserFavorites;

	@Action(value = "resource/read/{id}", results = { @Result(name = "success", location = "resource/read.ftl") })
	public String execute() throws Exception {
		resource = resourceDao.findById(id);
		if (resource == null) {
			throw new Exception();
		}

		cntUserFavorites = userFavoriteResourceDao.countByResourceId(id);

		String sessPageKey = getClass().getName();
		sessionManager.removeMorePage(sessPageKey, id);
		
		informations = informationDao.findByResourceIdOrderByNew(
				id, DISP_PAR_PAGE_NUM);
		
		sessionManager.putMorePage(sessPageKey, 
				id, informationDao.detectIds(informations));

		if (getIsLogined()) {
			isFavorited = userFavoriteResourceDao.isUserFavorited(
					getCurrentUser().getId(), id);
		}

		return SUCCESS;
	}

	@Action(value = "resource/read-more-ajax/id/{id}", results = { @Result(name = "success", location = "common/_informations.ftl") })
	public String moreAjaxAction() throws Exception {
		resource = resourceDao.findById(id);
		if (resource == null) {
			throw new Exception();
		}
		
		String sessPageKey = getClass().getName();
		List<Integer> ids = sessionManager.getMorePage(sessPageKey, id);
		
		informations = informationDao.findByResourceIdOrderByNew(
				id, DISP_PAR_PAGE_NUM, ids);
		
		if(ids != null) {
			ids.addAll(informationDao.detectIds(informations));
		} else {
			ids = informationDao.detectIds(informations);
		}
		
		sessionManager.putMorePage(sessPageKey, id, ids);

		return SUCCESS;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public int getCntUserFavorites() {
		return cntUserFavorites;
	}

	public boolean getIsFavorited() {
		return isFavorited;
	}

	public boolean getNoMoreItems() {
		return informations == null || informations.size() < DISP_PAR_PAGE_NUM;
	}

}
