package com.dig_i.front.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.LanguageSiteInformation;

@Repository
public class LanguageSiteInformationDao extends
		AbstractDao<LanguageSiteInformation> {

	public List<LanguageSiteInformation> findActivates(Date now, int limit) {
		try {
			now = DateUtils.truncate(now, Calendar.HOUR);
		} catch (Exception e) {
			return null;
		}

		List<LanguageSiteInformation> result = getEntityManager()
				.createQuery(
						"SELECT lsi FROM LanguageSiteInformation lsi WHERE lsi.startAt <= :now ORDER BY lsi.startAt DESC",
						LanguageSiteInformation.class).setParameter("now", now)
				.setMaxResults(limit).getResultList();

		return result;
	}

}
