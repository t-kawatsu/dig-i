package com.dig_i.front.service;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.dao.UserTagInformationDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.UserTag;
import com.dig_i.front.entity.UserTagInformation;
import com.dig_i.front.entity.UserTagInformationId;

@Scope("prototype")
@Service
public class UserTagInformationService {

	@javax.annotation.Resource
	private InformationDao informationDao;
	@javax.annotation.Resource
	private UserTagInformationDao userTagInformationDao;
	@javax.annotation.Resource
	private UserTagDao userTagDao;

	@Transactional
	public void clearnTagInformation() {
		// clearn tag_informations
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM tag_informations WHERE information_id in ( ");
		sb.append(" SELECT x.information_id FROM ( ");
		sb.append("  SELECT ti.information_id FROM tag_informations ti LEFT JOIN informations i ON (ti.information_id = i.id) WHERE i.id IS NULL ");
		sb.append(" ) x ");
		sb.append("); ");
		informationDao.getEntityManager().createNativeQuery(sb.toString())
				.executeUpdate();

		// clearn user_tag_informations
		sb = new StringBuffer();
		sb.append("DELETE FROM user_tag_informations WHERE information_id in ( ");
		sb.append(" SELECT x.information_id FROM ( ");
		sb.append("  SELECT ti.information_id FROM user_tag_informations ti LEFT JOIN informations i ON (ti.information_id = i.id) WHERE i.id IS NULL ");
		sb.append(" ) x ");
		sb.append("); ");
		informationDao.getEntityManager().createNativeQuery(sb.toString())
				.executeUpdate();
	}

	public void updateUserTagInformation(UserTag userTag,
			int assignableInformationNum) {
		List<Information> informations = userTagInformationDao
				.findAssignableInformationsByUserTag(userTag,
						assignableInformationNum);
		if (informations == null || informations.isEmpty()) {
			return;
		}
		Date lastUpdatedAt = null;
		for (Information information : informations) {
			UserTagInformation userTagInformation = new UserTagInformation();
			UserTagInformationId id = new UserTagInformationId();
			id.setUserTagId(userTag.getId());
			id.setInformationId(information.getId());
			userTagInformation.setId(id);
			userTagInformationDao.persist(userTagInformation);

			if (lastUpdatedAt == null
					|| information.getPublishedAt().after(lastUpdatedAt)) {
				lastUpdatedAt = information.getPublishedAt();
			}
		}
		if (lastUpdatedAt != null) {
			userTag.setInformationUpdatedAt(lastUpdatedAt);
			userTagDao.update(userTag);
		}
	}
}
