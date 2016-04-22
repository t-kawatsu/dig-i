package com.dig_i.front.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dig_i.front.dao.InformationSearchDao;
import com.dig_i.front.dao.TagInformationDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.entity.TagInformation;
import com.dig_i.front.entity.TagInformationId;

@Scope("prototype")
@Service
public class TagInformationService {

	@javax.annotation.Resource
	private TagInformationDao tagInformationDao;
	@javax.annotation.Resource
	private InformationSearchDao informationSearchDao;


	public void updateTagInformation(Tag tag, int assignableInformationNum) {
		List<Information> informations = 
				findAssignableInformationsByTag(tag, assignableInformationNum);
		if (informations == null) {
			return;
		}
		for (Information information : informations) {
			TagInformation tagInformation = new TagInformation();
			TagInformationId id = new TagInformationId();
			id.setTagId(tag.getId());
			id.setInformationId(information.getId());
			tagInformation.setId(id);
			tagInformationDao.getEntityManager().merge(tagInformation);
			tagInformationDao.persist(tagInformation);
		}
	}
	
	private List<Information> findAssignableInformationsByTag(Tag tag, int limit) {
		List<Information> informations = informationSearchDao.findByWord(tag.getName(), limit, 1);
		if(CollectionUtils.isEmpty(informations)) {
			return null;
		}
		List<Integer> ids = new ArrayList<Integer>();
		for (Information i : informations) {
			ids.add(i.getId());
		}
		List<TagInformation> tagInformations = tagInformationDao.findByInformationIds(ids);
		if(CollectionUtils.isEmpty(tagInformations)) {
			return informations;
		}
		List<Information> ret = new ArrayList<Information>();
		info:for(Information i : informations) {
			for(TagInformation row : tagInformations) {
				if(row.getId().getInformationId().equals(i.getId())) {
					continue info;
				}
			}
			ret.add(i);
		}
		return ret;
		/*
		String ftsw = queryWordFilter(tag.getName());
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM InformationSearch si JOIN si.information i LEFT JOIN si.tagInformations ti ");
		sb.append("WHERE ti.id.tagId is NULL AND match_against(si.description, :word) > 0 ");
		sb.append("GROUP BY si.id ");
		sb.append("ORDER BY si.id DESC");
		return getEntityManager().createQuery(sb.toString(), Information.class)
				.setParameter("word", ftsw).setMaxResults(limit)
				.getResultList();
		*/
	}
}
