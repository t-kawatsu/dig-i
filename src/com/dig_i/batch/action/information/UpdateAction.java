package com.dig_i.batch.action.information;

import java.util.EnumSet;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.CollectionUtils;

import com.dig_i.batch.action.AbstractAction;
import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.dao.TagDao;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.ResourceType;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.service.TagBaseTraversalResource;
import com.dig_i.front.service.TraversalResource;
import com.dig_i.front.util.SimplePager;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PER_DETECT_RESOURCE_NUM = 100;

	@javax.annotation.Resource(name = "rssResourceService")
	private TraversalResource rssResourceService;
	@javax.annotation.Resource(name = "facebookResourceService")
	private TagBaseTraversalResource fbResourceService;
	@javax.annotation.Resource(name = "youtubeResourceService")
	private TagBaseTraversalResource ytResourceService;
	@javax.annotation.Resource(name = "amazonResourceService")
	private TagBaseTraversalResource amResourceService;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private TagDao tagDao;

	@Action(value = "information/update")
	public String execute() throws Exception {
		updateTraversalResource();
		updateTagBaseResource();
		return NONE;
	}
	
	private void updateTraversalResource() {
		Integer page = 0;
		while (true) {
			page++;
			List<Resource> resources = resourceDao.paginateByType(
					PER_DETECT_RESOURCE_NUM, page,
					EnumSet.of(ResourceType.RSS1_0, ResourceType.RSS2_0))
						.getItems();
			if (CollectionUtils.isEmpty(resources)) {
				break;
			}
			for (Resource row : resources) {
				try {
				    rssResourceService.update(row);
				} catch (Exception e) {
					logger.error("{}", e);
				}
			}
		}
	}

	private void updateTagBaseResource() {
		List<Resource> resources = resourceDao.findByTypes(EnumSet.of(
				ResourceType.Facebook, ResourceType.Youtube,
				ResourceType.Amazon));
		SimplePager<Tag> pager;
		Integer page = 0;
		while (true) {
			page++;
			pager = tagDao.paginate(PER_DETECT_RESOURCE_NUM, page);
			List<Tag> tags = pager.getItems();
			if (tags == null || tags.isEmpty()) {
				break;
			}
			for (Tag tag : tags) {
				for (Resource row : resources) {
				  try {
					switch (row.getType()) {
					case Facebook:
						fbResourceService.update(row, tag);
						break;
					case Youtube:
						ytResourceService.update(row, tag);
						break;
					case Amazon:
						amResourceService.update(row, tag);
						break;
					default:
					}
				  } catch (Exception e) {
					logger.error("{}", e);
				  }
				}
			}
		}
	}
}
