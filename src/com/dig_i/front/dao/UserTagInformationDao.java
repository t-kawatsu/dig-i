package com.dig_i.front.dao;

import java.util.List;

import com.dig_i.front.entity.UserTagInformation;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.UserTag;

import org.springframework.stereotype.Repository;

@Repository
public class UserTagInformationDao extends AbstractDao<UserTagInformation> {

	public int deleteByUserTagId(Integer userTagId) {
		return getEntityManager()
				.createQuery(
						"DELETE FROM UserTagInformation uti WHERE uti.id.userTagId = :userTagId")
				.setParameter("userTagId", userTagId).executeUpdate();
	}

	public List<Information> findAssignableInformationsByUserTag(
			UserTag userTag, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM UserTagInformation uti RIGHT JOIN uti.tagInformations ti JOIN ti.information i ");
		sb.append("WHERE ti.id.tagId = :tagId AND uti.id.userTagId IS NULL ");
		return getEntityManager().createQuery(sb.toString(), Information.class)
				.setParameter("tagId", userTag.getTagId()).setMaxResults(limit)
				.getResultList();
	}
}
