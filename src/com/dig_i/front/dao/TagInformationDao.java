package com.dig_i.front.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.TagInformation;

@Repository
public class TagInformationDao extends AbstractDao<TagInformation> {

	public List<TagInformation> findByTagId(Integer tagId, int limit) {
		if (tagId == null) {
			return null;
		}
		return getEntityManager()
				.createQuery(
						"SELECT ti FROM TagInformation ti WHERE ti.id.tagId = :tagId",
						TagInformation.class).setParameter("tagId", tagId)
				.setMaxResults(limit).getResultList();
	}
	
	public List<TagInformation> findByInformationIds(List<Integer> ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ti FROM TagInformation ti WHERE ti.id.informationId in :ids");
		return getEntityManager()
				.createQuery(sb.toString(), TagInformation.class)
				.setParameter("ids", ids).getResultList();
	}
}
