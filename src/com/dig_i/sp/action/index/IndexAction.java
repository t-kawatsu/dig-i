package com.dig_i.sp.action.index;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.LanguageSiteInformationDao;
import com.dig_i.front.dao.TagDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.LanguageSiteInformation;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserTag;
import com.dig_i.sp.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DISP_POPULAR_TAGS_NUM = 8;
	private static final int DISP_PAR_PAGE_NUM = 8;
	private static final int DISP_SITE_INFORMATION_NUM = 3;
	private static final int DISP_RELATE_TAGS_NUM = 5;

	@Resource
	private TagDao tagDao;
	@Resource
	private UserTagDao userTagDao;
	@Resource
	private InformationDao informationDao;
	@Resource
	private LanguageSiteInformationDao languageSiteInformationDao;

	private List<UserTag> userTags;
	private List<UserTag> myUserTags;
	private List<Tag> relateTags;
	private List<Tag> tags;
	private List<Information> informations;
	private List<LanguageSiteInformation> siteInformations;

	@Action(value = "", results = { @Result(name = "success", location = "index/index.ftl") })
	public String execute() throws Exception {

		// site information
		siteInformations = languageSiteInformationDao.findActivates(new Date(),
				DISP_SITE_INFORMATION_NUM);

		// if user is logged in.
		if (!getIsLogined()) {
			return SUCCESS;
		}

		User user = getCurrentUser();
		
		// popular tags
		userTags = userTagDao.findPopularTags(DISP_POPULAR_TAGS_NUM);

		// informations related popular tags
		informations = informationDao.findNewInformationsByUserTags(userTags,
				DISP_PAR_PAGE_NUM);

		// relate tags
		relateTags = userTagDao.findRelateTagsByUserTags(myUserTags,
				user.getId(), DISP_RELATE_TAGS_NUM);

		return SUCCESS;
	}

	@Action(value = "index/popular-ajax", results = { @Result(name = "success", location = "index/_popular-ajax.ftl") })
	public String popularAjaxAction() throws Exception {
		userTags = userTagDao.findPopularTags(DISP_POPULAR_TAGS_NUM);
		informations = informationDao.findNewInformationsByUserTags(userTags,
				DISP_PAR_PAGE_NUM);
		return SUCCESS;
	}

	@Action(value = "index/new-ajax", results = { @Result(name = "success", location = "index/_new-ajax.ftl") })
	public String newAjaxAction() throws Exception {
		tags = tagDao.findTagsOrderByNew(DISP_POPULAR_TAGS_NUM);
		return SUCCESS;
	}

	public List<UserTag> getUserTags() {
		return userTags;
	}

	public List<UserTag> getMyUserTags() {
		return myUserTags;
	}

	public List<Tag> getRelateTags() {
		return relateTags;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public List<LanguageSiteInformation> getSiteInformations() {
		return siteInformations;
	}

	public List<Tag> getTags() {
		return tags;
	}

}
