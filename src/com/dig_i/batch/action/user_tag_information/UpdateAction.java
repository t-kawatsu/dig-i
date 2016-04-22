package com.dig_i.batch.action.user_tag_information;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.batch.action.AbstractAction;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.UserTag;
import com.dig_i.front.service.UserTagInformationService;
import com.dig_i.front.util.SimplePager;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PER_DETECT_TAG_NUM = 100;
	private static final int ASSIGNABLE_INFORMATION_NUM = 100;

	@javax.annotation.Resource
	private UserTagDao userTagDao;
	@javax.annotation.Resource
	private UserTagInformationService userTagInformationService;

	@Action(value = "user-tag-information/update")
	public String execute() throws Exception {
		SimplePager<UserTag> pager;
		Integer page = 0;
		while (true) {
			page++;
			pager = userTagDao.paginate(PER_DETECT_TAG_NUM, page);
			List<UserTag> userTags = pager.getItems();
			if (userTags == null || userTags.isEmpty()) {
				break;
			}

			for (UserTag row : userTags) {
				try {
					userTagInformationService.updateUserTagInformation(row,
							ASSIGNABLE_INFORMATION_NUM);
				} catch (Exception e) {
					logger.warn("{}", e);
					continue;
				}
			}
		}
		return NONE;
	}

}
