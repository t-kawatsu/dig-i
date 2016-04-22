package com.dig_i.sp.action.information;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.InformationSearchDao;
import com.dig_i.front.dao.UserFavoriteDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.InformationSearch;
import com.dig_i.sp.action.AbstractAction;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DISP_RELATE_WORDS_NUM = 7;

	@Resource
	private InformationDao informationDao;
	@Resource
	private InformationSearchDao informationSearchDao;
	@Resource
	private UserFavoriteDao userFavoriteDao;

	private Integer id;
	private Information information;
	private List<String> relateWords;
	private boolean isFavorited;
	private int cntUserFavorites;

	@Action(value = "information/read/{id}", results = { @Result(name = "success", location = "information/read.ftl") })
	public String execute() throws Exception {

		information = informationDao.findById(id);
		if (information == null) {
			throw new Exception();
		}

		InformationSearch informationSearch = informationSearchDao.findById(id);
		if (informationSearch == null) {
			throw new Exception();
		}

		relateWords = informationSearch.getRelateWords();
		if (relateWords != null && DISP_RELATE_WORDS_NUM < relateWords.size()) {
			relateWords = relateWords.subList(0, DISP_RELATE_WORDS_NUM);
		}

		cntUserFavorites = userFavoriteDao.countByInformationId(id);

		if (getIsLogined()) {
			isFavorited = userFavoriteDao.isUserFavorited(getCurrentUser()
					.getId(), id);
		}

		return SUCCESS;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Information getInformation() {
		return information;
	}

	public List<String> getRelateWords() {
		return relateWords;
	}

	public int getCntUserFavorites() {
		return cntUserFavorites;
	}

	public boolean getIsFavorited() {
		return isFavorited;
	}

}
