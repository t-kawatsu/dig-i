package com.dig_i.batch.action.tag_information;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.CollectionUtils;

import com.dig_i.batch.action.AbstractAction;
import com.dig_i.front.dao.TagDao;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.service.TagInformationService;
import com.dig_i.front.util.SimplePager;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PER_DETECT_TAG_NUM = 100;
	private static final int ASSIGNABLE_INFORMATION_NUM = 100;

	@javax.annotation.Resource
	private TagDao tagDao;
	@javax.annotation.Resource
	private TagInformationService tagInformationService;

	@Action(value = "tag-information/update")
	public String execute() throws Exception {
		SimplePager<Tag> pager;
		Integer page = 0;
		while (true) {
			page++;
			pager = tagDao.paginate(PER_DETECT_TAG_NUM, page);
			List<Tag> tags = pager.getItems();
			if (CollectionUtils.isEmpty(tags)) {
				break;
			}
			for (Tag row : tags) {
				logger.info("tag : {}", row);
				try {
					tagInformationService.updateTagInformation(row,
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
